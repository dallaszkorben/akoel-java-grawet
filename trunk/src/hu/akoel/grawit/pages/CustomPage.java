package hu.akoel.grawit.pages;

import hu.akoel.grawit.exceptions.CompilationException;
import hu.akoel.grawit.exceptions.PageException;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.List;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;
import javax.tools.JavaCompiler.CompilationTask;

import org.openqa.selenium.WebDriver;

public class CustomPage implements CustomPageInterface {
	private String name;
	private String source;
	
	private PageProgressInterface pageProgressInterface = null;
		
	private CompilationTask task;
	private JavaSourceFromString javaFile;
	private DiagnosticCollector<JavaFileObject> diagnostics;
	private String classOutputFolder = "";
	
	private String customClassName = "CustomClass";
	private String customMethodName = "doAction";
	
	public CustomPage( String name, String source ){
		this.name = name;
		this.source = source;
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
	public void doAction( WebDriver driver ) throws PageException, CompilationException {
		
		//Kod legyartasa
		generateTheCode( getSurce() );
		
		//Kod forditasa
		boolean success = compileTheCode();

		//Ha sikerult a forditas
		if( success ){
			
			//Akkor futtatja a kodit
			runTheCode( driver );

		//Forditas alatt hiba tortent
		}else{
			
			//Mivel csak egy forrast forditottam, ezert csak maximum 1 elemu lesz a lista
			List<Diagnostic<? extends JavaFileObject>> diagList = getDiagnostic();
			if( !diagList.isEmpty() ){
				
				//Dob egy exceptiont  Diagnostic<? extends JavaFileObject>
				throw new CompilationException( this.getName(), javaFile, diagList.get( 0 ) );
			}
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
		out.println("  	public void doAction(WebDriver driver) throws hu.akoel.grawit.exceptions.PageException{");
		out.println( source );    
		out.println("  	}");
		out.println("}");
		out.close();
		
		javaFile = new JavaSourceFromString( customClassName, writer.toString() );
		 
	    Iterable<? extends JavaFileObject> compilationUnits = Arrays.asList(javaFile);
	    task = compiler.getTask(null, null, diagnostics, null, null, compilationUnits);
	}
	
	private boolean compileTheCode(){
		boolean success = task.call();
		return success;
	}
	
	private void runTheCode( WebDriver driver ) throws PageException{
		try {	    	  
			
			File f = new File(classOutputFolder);

			URL url = f.toURI().toURL();
			URL[] urls = new URL[] { url };
			ClassLoader loader = new URLClassLoader(urls);
			Class<?> thisClass = loader.loadClass( customClassName );
			Object instance = thisClass.newInstance();
			//Method thisMethod = thisClass.getDeclaredMethod("doAction", new Class[] { String[].class });
			Method thisMethod = thisClass.getDeclaredMethod( customMethodName, WebDriver.class );
			//thisMethod.invoke(instance, new Object[] {null});	
			thisMethod.invoke(instance, driver);
			//loader = null;	    	  
	    	  
        //Class.forName("HelloWorld").getDeclaredMethod("main", new Class[] { String[].class }).invoke(null, new Object[] { null });
		//TODO sajat hibakezelot tenni ra				
		} catch (ClassNotFoundException e) {
			System.err.println("Class not found: " + e);
			throw new Error( e );
		} catch (NoSuchMethodException e) {
	        System.err.println("No such method: " + e);
	        throw new Error( e );
	        //e.printStackTrace();
		} catch (IllegalAccessException e) {
	        System.err.println("Illegal access: " + e);
	        throw new Error( e );
		} catch (InstantiationException e) {
			System.err.println("Instantiation exception: " + e);
			throw new Error( e );
		} catch (MalformedURLException e) {				
			System.err.println("Malformed URL exception: " + e);
			throw new Error( e );
		} catch (InvocationTargetException e) {
			if( e.getCause() instanceof PageException ){
				//Hogy kezelni tudjam a nem megtalalt elemet
				//A tobbi hiba mind programozasi hiba, tehat Error
				//TODO valszeg le kell zarnom a program futasat a tobbi esetben 
				throw new PageException( this.getName(), ((PageException)e.getCause()).getElementName(), ((PageException)e.getCause()).getElementId(), ((PageException)e.getCause()).getCause() );				
			}else{
				System.err.println("Invocation target Exception: " + e.getCause());
				throw new Error(e);
			}
		}
	}

	public List<Diagnostic<? extends JavaFileObject>> getDiagnostic(){
		return diagnostics.getDiagnostics();
	}
	

}
