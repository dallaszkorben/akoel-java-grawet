package hu.akoel.grawit.core.treenodedatamodel.base;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;
import javax.tools.JavaCompiler.CompilationTask;

import org.openqa.selenium.WebDriver;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.JavaSourceFromString;
import hu.akoel.grawit.WorkingDirectory;
import hu.akoel.grawit.core.operations.SpecialBaseExecuteOperation;
import hu.akoel.grawit.core.treenodedatamodel.BaseElementDataModelAdapter;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.enums.list.ElementTypeListEnum;
import hu.akoel.grawit.exceptions.CompilationException;
import hu.akoel.grawit.exceptions.ElementException;
import hu.akoel.grawit.exceptions.InvocationTargetScriptBaseElementException;
import hu.akoel.grawit.exceptions.PageException;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;
import hu.akoel.grawit.exceptions.XMLPharseException;

public class ScriptBaseElementDataModel extends BaseElementDataModelAdapter{
	private static final long serialVersionUID = -8916078747948054716L;
	
	private ElementTypeListEnum elementType = ElementTypeListEnum.SCRIPT;
	
	public static final String ATTR_SCRIPT = "script";
	
	private ArrayList<String> parameters = new ArrayList<>();
	
	private static String customClassName = WorkingDirectory.getDynamicallyCompiledClassName();//"CustomClass"; //Ez a nev le lesz majd cserelve
	private static String customMethodName = "doAction";
	
	private static final String codePre = 
			"import org.openqa.selenium.WebDriver;\n" +
			"import org.openqa.selenium.WebElement;\n" +
			"import org.openqa.selenium.support.ui.WebDriverWait;\n" +
			"import org.openqa.selenium.By;\n" +
			"import org.openqa.selenium.support.ui.ExpectedConditions;\n" +
			"import org.openqa.selenium.WebDriverException;\n" +
			" \n" +
			"import java.util.ArrayList;\n" +
			" \n" +
			"import hu.akoel.grawit.core.treenodedatamodel.BaseElementDataModelAdapter;\n" +
			
			"public class " + customClassName + " {\n" +		
			"   public " + customClassName + "() {}\n" +		
			"   public void " + customMethodName + "(WebDriver driver, ArrayList<String> parameters, BaseElementDataModelAdapter baseElement ) throws hu.akoel.grawit.exceptions.PageException{\n";
	private static final String codePost = 
			"\n   }\n" +
			"}\n";
	
	private JavaSourceFromString javaFile;
	private DiagnosticCollector<JavaFileObject> diagnostics;
	
	//Adatmodel ---
	private String script;
	//----

	private String classPath = WorkingDirectory.getClassDirectory().getAbsolutePath() + System.getProperty("file.separator");
	
private int identification = this.hashCode();
private StandardJavaFileManager stdFileManager;

	/**
	 * 
	 * Modify
	 * 
	 * @param name
	 * @param elementType
	 * @param identifier
	 * @param identificationType
	 * @param frame
	 */
	public ScriptBaseElementDataModel(String name, String script ){
		super( name );
		this.script = script;
	}

	/**
	 * Capture new
	 * 
	 * XML alapjan gyartja le
	 * 
	 * @param element
	 * @throws XMLPharseException 
	 */
	public ScriptBaseElementDataModel( Element element ) throws XMLPharseException{
		super( element );
		
		//element type             
		if( !element.hasAttribute( ATTR_ELEMENT_TYPE ) ){
			throw new XMLMissingAttributePharseException( getRootTag(), getTag(), ATTR_NAME, getName(), ATTR_ELEMENT_TYPE );			
		}
		String elementTypeString = element.getAttribute( ATTR_ELEMENT_TYPE );
		this.elementType = ElementTypeListEnum.valueOf( elementTypeString );
		
		//source
		if( !element.hasAttribute( ATTR_SCRIPT ) ){
			throw new XMLMissingAttributePharseException( getRootTag(), TAG, ATTR_SCRIPT );			
		}
		String scriptString = element.getAttribute( ATTR_SCRIPT );		
		this.script = scriptString;
				
	}
	
	public static Tag getTagStatic(){
		return TAG;
	}

	@Override
	public Tag getTag() {
		return getTagStatic();
	}
	
	public ElementTypeListEnum getElementType(){
		return elementType;
	}
		
	public void addParameter( String parameter ){
		this.parameters.add( parameter );
	}
	
	public void clearParameters(){
		this.parameters.clear();
	}
	
	public Iterator<String> getParameterIterator(){
		return parameters.iterator();
	}

	public String getScript(){
		return script;
	}
	
	public void setScript( String script ){
		this.script = script;
	}

	public static String  getModelNameToShowStatic(){
		return CommonOperations.getTranslation( "tree.nodetype.base.scriptelement");
	}

	public void doAction( WebDriver driver ) throws CompilationException, ElementException {
		
		identification++;		
		String actualClassName = getFileName(customClassName, identification);

		//Kod legyartasa
		CompilationTask task = generateTheCode( getScript(), actualClassName );

		//Kod forditasa
		boolean success = compileTheCode( task );

		//Ha sikerult a forditas
		if( success ){
		
			//Akkor futtatja a kodot
			runTheCode( driver, actualClassName );

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
		
	private CompilationTask generateTheCode( String source, String actualClassName ){
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		diagnostics = new DiagnosticCollector<JavaFileObject>();
		
stdFileManager = compiler.getStandardFileManager(null, null, null);
try {
	stdFileManager.setLocation( StandardLocation.CLASS_OUTPUT, Arrays.asList(new File( classPath )));
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
		//Legyartom a kodot
		StringWriter writer = new StringWriter();	
		PrintWriter out = new PrintWriter(writer);
		
		//out.println( codePre );
		out.println( codePre.replace( customClassName, actualClassName ) );
		out.println( source );
		out.println( codePost );

		out.close();

		javaFile = new JavaSourceFromString( actualClassName, writer.toString() );

	    Iterable<? extends JavaFileObject> compilationUnits = Arrays.asList(javaFile);

	    CompilationTask task = null;
	    
	    task = compiler.getTask(null, stdFileManager, diagnostics, null, null, compilationUnits);

	    return task;
	}
	
	private boolean compileTheCode( CompilationTask task ){
		boolean success = task.call();
		return success;
	}
	
	private void runTheCode( WebDriver driver, String actualClassName ) throws ElementException{
		try {	    	  
						
			//File f = new File(workingPath);
			File f = WorkingDirectory.getClassDirectory();

			URL url = f.toURI().toURL();
			URL[] urls = new URL[] { url };
			
			@SuppressWarnings("resource")
			ClassLoader loader = new URLClassLoader(urls);
			Class<?> thisClass = loader.loadClass( actualClassName );
			Object instance = thisClass.newInstance();
			//Method thisMethod = thisClass.getDeclaredMethod("doAction", new Class[] { String[].class });
			
			Method thisMethod = thisClass.getDeclaredMethod( customMethodName, WebDriver.class, ArrayList.class, BaseElementDataModelAdapter.class );

			//thisMethod.invoke(instance, new Object[] {null});	
			thisMethod.invoke( instance, driver, parameters, this );

			// Torli a letrehozott class file-t
			deleteClassFile(actualClassName);
			
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
								
			throw new InvocationTargetScriptBaseElementException( 
					SpecialBaseExecuteOperation.getStaticName(),
					this.getName(),
					e);
				
		}
	}

	private String getFileName(String customClassName, Integer identification ){
		return customClassName + "_" + identification;
	}
	
	private void deleteClassFile( String actualClassName ){
		
		//---------------------------------
		// Torli a letrehozott class file-t
		//---------------------------------
		//File fileToDelete = new File( workingPath + customClassName + identification + ".class");
		File fileToDelete = new File( classPath + actualClassName + ".class");
		fileToDelete.delete();
		
		//---------------------------------
		// Lezarja a filemanager-t
		//---------------------------------
		try {
			stdFileManager.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isCodeOk(){
		
		identification++;		
		String actualClassName = getFileName(customClassName, identification);

		//Kod legyartasa
		CompilationTask task = generateTheCode( getScript(), actualClassName );

		//Kod forditasa
		boolean success = compileTheCode( task );

		//Torli a letrehozott class file-t
		deleteClassFile(actualClassName);
		
		return success;
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
	public String getNodeTypeToShow(){
		return getModelNameToShowStatic();
	}
	
	@Override
	public Element getXMLElement(Document document) {
		
		Element elementElement = super.getXMLElement(document);
		
		Attr attr;
		
		//Element type
		attr = document.createAttribute( ATTR_ELEMENT_TYPE );
		attr.setValue( getElementType().name() );
		elementElement.setAttributeNode(attr);	
		
		//Source
		attr = document.createAttribute( ATTR_SCRIPT );
		attr.setValue( getScript() );
		elementElement.setAttributeNode(attr);	

		return elementElement;	
	}

	@Override
	public Object clone(){
		
		//Leklonozza az BaseElement-et
		ScriptBaseElementDataModel cloned = (ScriptBaseElementDataModel)super.clone();
	
		cloned.elementType = this.elementType;	
		
		cloned.script = new String( this.getScript() );
		
		return cloned;
		
	}

}
