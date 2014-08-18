package hu.akoel.grawit.core.datamodel.pages;

import java.io.IOException;

import javax.swing.tree.MutableTreeNode;
import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.openqa.selenium.WebDriver;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.datamodel.ParamDataModelInterface;
import hu.akoel.grawit.core.datamodel.elements.BaseElementDataModel;
import hu.akoel.grawit.core.datamodel.elements.ParamElementDataModel;
import hu.akoel.grawit.core.pages.BasePageChangeListener;
import hu.akoel.grawit.core.pages.CustomPageInterface;
import hu.akoel.grawit.core.pages.ExecutablePageInterface;
import hu.akoel.grawit.core.pages.PageProgressInterface;
import hu.akoel.grawit.exceptions.CompilationException;
import hu.akoel.grawit.exceptions.ElementException;
import hu.akoel.grawit.exceptions.PageException;

public class ParamPageDataModel  extends ParamDataModelInterface implements ExecutablePageInterface, BasePageChangeListener{
	
	private static final long serialVersionUID = -5098304990124055586L;
	
	private static final String TAG_NAME = "page";
	
	private static final String ATTR_NAME = "name";
	private static final String ATTR_DETAILS = "details";
	
	private String name;
	private BasePageDataModel basePage;	
	
	private PageProgressInterface pageProgressInterface = null;	
	
	public ParamPageDataModel( String name, BasePageDataModel basePage  ){
		super();
		
		this.name = name;
		this.basePage = basePage;

	}

	public static String getTagNameStatic(){
		return TAG_NAME;
	}

	@Override
	public String getTagName() {
		return getTagNameStatic();
	}

	@Override	
	public String getIDValue(){
		return getName();
	}
	
	@Override
	public String getIDName() {
		return ATTR_NAME;
	}
	
	public BasePageDataModel getBasePage(){
		return basePage;
	}
	
	@Override
	public void add(ParamDataModelInterface node) {
		super.add( (MutableTreeNode)node );
	}
	
	@Override
	public String getTypeToString(){
		return CommonOperations.getTranslation( "tree.nodetype.parampage");
	}
	
	@Override
	public Element getXMLElement(Document document) {
		Attr attr;

		//Node element
		Element pageElement = document.createElement("page");
		
		//NAME attributum
		attr = document.createAttribute("name");
		attr.setValue( getName() );
		pageElement.setAttributeNode(attr);	

		//PAGEBASEPAGE attributum
		attr = document.createAttribute("basepagepath");
		attr.setValue( basePage.getTaggedPathToString() );
		pageElement.setAttributeNode(attr);
		

		int childrens = this.getChildCount();
		for( int i = 0; i < childrens; i++ ){
			
			Object object = this.getChildAt( i );
			
			if( !object.equals(this) && object instanceof ParamDataModelInterface ){
				
				Element element = ((ParamDataModelInterface)object).getXMLElement( document );
				pageElement.appendChild( element );		    		
		    	
			}
		}
		
		return pageElement;	
	}

	@Override
	public void removeBaseElement(BaseElementDataModel element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {		
		return name;
	}
	
	public void setName( String name ){
		this.name = name;
	}

	@Override
	public void doAction(WebDriver driver) throws PageException, CompilationException {
//		//Jelzi, hogy elindult az oldal feldolgozasa
//		if( null != pageProgressInterface ){
//			pageProgressInterface.pageStarted( getName() );
//		}

		//Ha implementalta a Custom Page Interface-t, akkor a forraskodot kell vegrehajtania
		if( this instanceof CustomPageInterface ){
			
			//Megszerzi a forraskodot
			String script = ((CustomPageInterface)this).getSurce();
			
			//TODO megcsinalni a futasidoben a forditast es futtatast			
			JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
			StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
			
			//File javaFile = new File("c:/src/com/juixe/Entity.java");
			// getJavaFileObjects’ param is a vararg
			Iterable fileObjects = fileManager.getJavaFileObjects(script);
			compiler.getTask(null, fileManager, null, null, null, fileObjects).call();
			// Add more compilation tasks
			try {
				fileManager.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			
		//Kulonben normal ParamPage-kent az ParamElement-eken hajtja vegre sorban az ElementOperationInterface-okat
		}else{

			int childrenCount = this.getChildCount();
			for( int i = 0; i < childrenCount; i++ ){
				ParamElementDataModel parameterElement = (ParamElementDataModel)this.getChildAt( i );
			
				// Ha az alapertelmezettol kulonbozo frame van meghatarozva, akkor valt
				String frame = parameterElement.getBaseElement().getFrame();
				if( null != frame ){
					driver.switchTo().defaultContent();
					driver.switchTo().frame("menuFrame");		
				}
				
				try{			
					parameterElement.doAction( driver );
				}catch (ElementException e){
					throw new PageException( this.getName(), e.getElementName(), e.getElementId(), e);
				}
				
			}
		}
		
//		//Jelzi, hogy befejezodott az oldal feldolgozasa
//		if( null != pageProgressInterface ){
//			pageProgressInterface.pageEnded( getName() );
//		}
		
	}

	public void setPageProgressInterface( PageProgressInterface pageProgressInterface ){
		this.pageProgressInterface = pageProgressInterface;
	}
	
	@Override
	public PageProgressInterface getPageProgressInterface() {		
		return this.pageProgressInterface;
	}

}
