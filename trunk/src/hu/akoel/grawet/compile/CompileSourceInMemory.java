package hu.akoel.grawet.compile;

import hu.akoel.grawet.exceptions.PageException;
import hu.akoel.grawet.page.CustomPageInterface;
import hu.akoel.grawet.page.PageProgressInterface;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.List;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.ToolProvider;
import javax.tools.JavaCompiler.CompilationTask;

import org.openqa.selenium.WebDriver;

public class CompileSourceInMemory implements CustomPageInterface {
	private String name;
	private String source;
	private PageProgressInterface pageProgressInterface = null;
	
	private CompilationTask task;
	private DiagnosticCollector<JavaFileObject> diagnostics;
	private String classOutputFolder = "";
	
	private String customClassName = "CustomClass";
	private String customMethodName = "doAction";
	
	public CompileSourceInMemory( String name, String source ){
		this.name = name;
		this.source = source;		
		 
	}

	public CompileSourceInMemory( String source ){
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		diagnostics = new DiagnosticCollector<JavaFileObject>();
				
		//Legyartom a kodot
		StringWriter writer = new StringWriter();
		PrintWriter out = new PrintWriter(writer);
		out.println("import org.openqa.selenium.WebDriver;");
		out.println("public class CustomClass {");		
		out.println("	public CustomClass() {");
		out.println("  	}");		
		out.println("  	public void doAction(WebDriver driver) {");
		out.println( source );    
		out.println("  	}");
		out.println("}");
		out.close();
		
		JavaFileObject file = new JavaSourceFromString( customClassName, writer.toString() );
		 
	    Iterable<? extends JavaFileObject> compilationUnits = Arrays.asList(file);
	    task = compiler.getTask(null, null, diagnostics, null, null, compilationUnits);
		 
	}

	@Override
	public String getName() {
		return name;
	}

	public void setPageProgressInterface( PageProgressInterface pageProgressInterface ) {
		this.pageProgressInterface = pageProgressInterface;		
	}
		
	@Override
	public PageProgressInterface getPageProgressInterface() {		
		return this.pageProgressInterface;
	}

	@Override
	public String getSurce() {		
		return source;
	}
	
	@Override
	public void doAction() throws PageException {
		
		//Kod legyartasa
		generateTheCode( getSurce() );
		
		//Kod forditasa
		boolean success = compileTheCode();
		
if( !success){
	List<Diagnostic<? extends JavaFileObject>> diagList = getDiagnostic();
	for (Diagnostic<? extends JavaFileObject> diagnostic : diagList ) {
		System.out.println("Error code: " + diagnostic.getCode());
		System.out.println("Type: " + diagnostic.getKind());
		System.out.println("Position: " + diagnostic.getPosition());
		System.out.println("Start position: " + diagnostic.getStartPosition());
		System.out.println("End position: " + diagnostic.getEndPosition());
		System.out.println("Source: " + diagnostic.getSource());
		System.out.println("Message: " + diagnostic.getMessage(null));
	}
}


		if( success ){
			runTheCode();
		}
	}
	
	private void generateTheCode( String source ){
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		diagnostics = new DiagnosticCollector<JavaFileObject>();
				
		//Legyartom a kodot
		StringWriter writer = new StringWriter();
		PrintWriter out = new PrintWriter(writer);
		out.println("import org.openqa.selenium.WebDriver;");
		out.println("public class CustomClass {");		
		out.println("	public CustomClass() {");
		out.println("  	}");		
		out.println("  	public void doAction(WebDriver driver) {");
		out.println( source );    
		out.println("  	}");
		out.println("}");
		out.close();
		
		JavaFileObject file = new JavaSourceFromString( customClassName, writer.toString() );
		 
	    Iterable<? extends JavaFileObject> compilationUnits = Arrays.asList(file);
	    task = compiler.getTask(null, null, diagnostics, null, null, compilationUnits);
	}
	
	private boolean compileTheCode(){
		boolean success = task.call();
		return success;
	}
	
	private void runTheCode(){
		try {	    	  
			
			File f = new File(classOutputFolder);

			URL url = f.toURI().toURL();
			URL[] urls = new URL[] { url };
			ClassLoader loader = new URLClassLoader(urls);
			Class<?> thisClass = loader.loadClass( customClassName );
			Object instance = thisClass.newInstance();
			//Method thisMethod = thisClass.getDeclaredMethod("doAction", new Class[] { String[].class });
			Method thisMethod = thisClass.getDeclaredMethod( customMethodName, WebDriver.class );
			thisMethod.invoke(instance, new Object[] {null});	
			loader = null;	    	  
	    	  
        //Class.forName("HelloWorld").getDeclaredMethod("main", new Class[] { String[].class }).invoke(null, new Object[] { null });
		//TODO sajat hibakezelot tenni ra				
		} catch (ClassNotFoundException e) {
			System.err.println("Class not found: " + e);
		} catch (NoSuchMethodException e) {
	        System.err.println("No such method: " + e);
		} catch (IllegalAccessException e) {
	        System.err.println("Illegal access: " + e);
		} catch (InvocationTargetException e) {
	        System.err.println("Invocation target: " + e);
		} catch (InstantiationException e) {
			System.err.println("Instantiation exception: " + e);	
		} catch (MalformedURLException e) {				
			System.err.println("Malformed URL exception: " + e);
		}
	}

	public List<Diagnostic<? extends JavaFileObject>> getDiagnostic(){
		return diagnostics.getDiagnostics();
	}
	

}


class JavaSourceFromString extends SimpleJavaFileObject {
  final String code;

  JavaSourceFromString(String name, String code) {
    super(URI.create("string:///" + name.replace('.','/') + Kind.SOURCE.extension),Kind.SOURCE);
    this.code = code;
  }

  @Override
  public CharSequence getCharContent(boolean ignoreEncodingErrors) {
    return code;
  }
}
