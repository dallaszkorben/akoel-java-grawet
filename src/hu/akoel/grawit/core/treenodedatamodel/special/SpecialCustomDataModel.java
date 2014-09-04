package hu.akoel.grawit.core.treenodedatamodel.special;

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
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.pages.ExecutablePageInterface;
import hu.akoel.grawit.core.pages.JavaSourceFromString;
import hu.akoel.grawit.core.pages.PageProgressInterface;
import hu.akoel.grawit.core.treenodedatamodel.SpecialDataModelInterface;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.exceptions.CompilationException;
import hu.akoel.grawit.exceptions.PageException;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;
import hu.akoel.grawit.exceptions.XMLPharseException;

public class SpecialCustomDataModel extends SpecialDataModelInterface implements ExecutablePageInterface{

	private static final long serialVersionUID = -4450434610253862372L;

	public static Tag TAG = Tag.SPECIALCUSTOM;
	
	public static final String ATTR_SCRIPT = "script";
	
	private static final String codePre = 
			"import org.openqa.selenium.WebDriver;\n" +
			"public class CustomClass {\n" +		
			"	public CustomClass() {}\n" +		
			"  	public void doAction(WebDriver driver) throws hu.akoel.grawit.exceptions.PageException{\n";
	private static final String codePost = 
			"  	}\n" +
			"}\n";
	
	private String name;
	private String script;
	
	private PageProgressInterface pageProgressInterface = null;

	//private CompilationTask task;
	private JavaSourceFromString javaFile;
	private DiagnosticCollector<JavaFileObject> diagnostics;
	private String classOutputFolder = "";
	
	private String customClassName = "CustomClass";
	private String customMethodName = "doAction";
	
	public SpecialCustomDataModel(String name, String script ){
		common( name, script );	
	}

	/**
	 * XML alapjan gyartja le a SPECIALCLOSE-t
	 * 
	 * @param element
	 * @throws XMLPharseException 
	 */
	public SpecialCustomDataModel( Element element ) throws XMLPharseException{
		
		//name
		if( !element.hasAttribute( ATTR_NAME ) ){
			throw new XMLMissingAttributePharseException( getRootTag(), TAG, ATTR_NAME );			
		}
		String nameString = element.getAttribute( ATTR_NAME );		
		this.name = nameString;
		
		//source
		if( !element.hasAttribute( ATTR_SCRIPT ) ){
			throw new XMLMissingAttributePharseException( getRootTag(), TAG, ATTR_SCRIPT );			
		}
		String scriptString = element.getAttribute( ATTR_SCRIPT );		
		this.script = scriptString;
		
		
	}
	
	private void common( String name, String script ){		
		this.name = name;
		this.script = script;
	}

	public static Tag getTagStatic(){
		return TAG;
	}

	@Override
	public Tag getTag() {
		return getTagStatic();
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getScript(){
		return script;
	}
	
	public void setScript( String script ){
		this.script = script;
	}
	
	@Override
	public void add(SpecialDataModelInterface node) {
	}
	
	public static String  getModelNameToShowStatic(){
		return CommonOperations.getTranslation( "tree.nodetype.special.custom");
	}
	
	@Override
	public String getModelNameToShow(){
		return getModelNameToShowStatic();
	}
	
	public void setPageProgressInterface( PageProgressInterface pageProgressInterface ) {
		this.pageProgressInterface = pageProgressInterface;		
	}
	
	@Override
	public PageProgressInterface getPageProgressInterface() {
		return this.pageProgressInterface;
	}
	
	@Override
	public void doAction( WebDriver driver ) throws PageException, CompilationException {
		
		//Kod legyartasa
		CompilationTask task = generateTheCode();
		
		//Kod forditasa
		boolean success = compileTheCode( task );

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
	
	public  CompilationTask generateTheCode( ){
		return generateTheCode( getScript() );
	}
	
	private CompilationTask generateTheCode( String source ){
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		diagnostics = new DiagnosticCollector<JavaFileObject>();
				
		//Legyartom a kodot
		StringWriter writer = new StringWriter();
		PrintWriter out = new PrintWriter(writer);
		//out.println("import org.openqa.selenium.WebDriver;\n");
		//out.println("public class CustomClass {\n");		
		//out.println("	public CustomClass() {}\n");		
		//out.println("  	public void doAction(WebDriver driver) throws hu.akoel.grawit.exceptions.PageException{\n");
		out.println( codePre );
		out.println( source );
		out.println( codePost );
		//out.println("  	}\n");
		//out.println("}\n");
		out.close();
		
		javaFile = new JavaSourceFromString( customClassName, writer.toString() );
		 
	    Iterable<? extends JavaFileObject> compilationUnits = Arrays.asList(javaFile);
	    CompilationTask task = compiler.getTask(null, null, diagnostics, null, null, compilationUnits);
	    
	    return task;
	}
	
	public boolean compileTheCode( CompilationTask task ){
		boolean success = task.call();
		return success;
	}
	
	private void runTheCode( WebDriver driver ) throws PageException{
		try {	    	  
			
			File f = new File(classOutputFolder);

			URL url = f.toURI().toURL();
			URL[] urls = new URL[] { url };
			@SuppressWarnings("resource")
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
	
	public static String getCodePre(){
		return codePre;
	}
	
	public static String getCodePost(){
		return codePost;
	}
	
	@Override
	public Element getXMLElement(Document document) {
		Attr attr;

		//Node element
		Element elementElement = document.createElement( SpecialCustomDataModel.this.getTag().getName() );

		//Name
		attr = document.createAttribute( ATTR_NAME );
		attr.setValue( getName() );
		elementElement.setAttributeNode(attr);	

		//Source
		attr = document.createAttribute( ATTR_SCRIPT );
		attr.setValue( getScript() );
		elementElement.setAttributeNode(attr);	

		return elementElement;	
	}

	@Override
	public Object clone(){
		
		SpecialCustomDataModel cloned = (SpecialCustomDataModel)super.clone();
	
		return cloned;
		
	}

}
