package hu.akoel.grawit.core.treenodedatamodel.param;

import java.io.IOException;
import java.io.StringReader;

import javax.swing.tree.MutableTreeNode;
import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.openqa.selenium.WebDriver;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.pages.torlendo_CustomPageInterface;
import hu.akoel.grawit.core.pages.ExecutablePageInterface;
import hu.akoel.grawit.core.pages.PageProgressInterface;
import hu.akoel.grawit.core.treenodedatamodel.BaseDataModelInterface;
import hu.akoel.grawit.core.treenodedatamodel.ParamDataModelInterface;
import hu.akoel.grawit.core.treenodedatamodel.VariableDataModelInterface;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseNodeDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.BasePageDataModel;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.exceptions.CompilationException;
import hu.akoel.grawit.exceptions.ElementException;
import hu.akoel.grawit.exceptions.PageException;
import hu.akoel.grawit.exceptions.XMLBaseConversionPharseException;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;
import hu.akoel.grawit.exceptions.XMLPharseException;

public class ParamPageDataModel  extends ParamDataModelInterface implements ExecutablePageInterface{//, BasePageChangeListener{
	
	private static final long serialVersionUID = -5098304990124055586L;
	
	public static final Tag TAG = Tag.PARAMPAGE;
	
	private static final String ATTR_BASE_PAGE_PATH = "basepagepath";
	
	private String name;

	private BasePageDataModel basePage;	
	
	private PageProgressInterface pageProgressInterface = null;	
	
	public ParamPageDataModel( String name, BasePageDataModel basePage  ){
		super();
		
		this.name = name;
		this.basePage = basePage;

	}

	/**
	 * XML alapjan gyartja le az objektumot
	 * 
	 * @param element
	 * @throws XMLPharseException
	 */
	public ParamPageDataModel( Element element, BaseDataModelInterface baseDataModel, VariableDataModelInterface variableDataModel ) throws XMLPharseException{
		
		//name
		if( !element.hasAttribute( ATTR_NAME ) ){
			throw new XMLMissingAttributePharseException( getRootTag(), TAG, ATTR_NAME );			
		}
		String nameString = element.getAttribute( ATTR_NAME );		
		this.name = nameString;
		
		//BasePage
		if( !element.hasAttribute( ATTR_BASE_PAGE_PATH ) ){
			throw new XMLMissingAttributePharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_BASE_PAGE_PATH );			
		}
		String paramPagePathString = element.getAttribute(ATTR_BASE_PAGE_PATH);				
		paramPagePathString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + paramPagePathString;  
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
	    DocumentBuilder builder; 
	    Document document = null;
	    try  
	    {  
	    	//attributum-kent tarolt utvonal atalakitasa Documentum-ma
	        builder = factory.newDocumentBuilder();  
	        document = builder.parse( new InputSource( new StringReader( paramPagePathString ) ) );  
	    } catch (Exception e) { 
	    	
	    	//Nem sikerult az atalakitas
	    	throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_BASE_PAGE_PATH, element.getAttribute(ATTR_BASE_PAGE_PATH), e );
	    	
	    } 
		//Megkeresem a BASEROOT-ben a BASEPAGE-hez vezeto utat
	    Node actualNode = document;
	    while( actualNode.hasChildNodes() ){
		
	    	actualNode = actualNode.getFirstChild();
	    	Element actualElement = (Element)actualNode;
	    	String tagName = actualElement.getTagName();
	    	String attrName = null;
	    	
	    	//Ha BASENODE
	    	if( tagName.equals( BaseNodeDataModel.TAG.getName() ) ){
	    		attrName = actualElement.getAttribute(BaseNodeDataModel.ATTR_NAME);	    		
	    		baseDataModel = (BaseDataModelInterface) CommonOperations.getDataModelByNameInLevel( baseDataModel, Tag.BASENODE, attrName );

	    		if( null == baseDataModel ){

	    			throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_BASE_PAGE_PATH, element.getAttribute(ATTR_BASE_PAGE_PATH) );
	    		}
	    		
	    	//Ha BASEPAGE
	    	}else if( tagName.equals( BasePageDataModel.TAG.getName() ) ){
	    		attrName = actualElement.getAttribute(BasePageDataModel.ATTR_NAME);
	    		baseDataModel = (BaseDataModelInterface) CommonOperations.getDataModelByNameInLevel( baseDataModel, Tag.BASEPAGE, attrName );
	    		if( null == baseDataModel ){

	    			throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_BASE_PAGE_PATH, element.getAttribute(ATTR_BASE_PAGE_PATH) );
	    		}
	    		
	    	//Ha BASEELEMENT
	    	}else if( tagName.equals( BaseElementDataModel.TAG.getName() ) ){
	    		attrName = actualElement.getAttribute(BaseElementDataModel.ATTR_NAME);

	    		throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_BASE_PAGE_PATH, element.getAttribute(ATTR_BASE_PAGE_PATH) );	    		
	    	}else{
	    		
	    		throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_BASE_PAGE_PATH, element.getAttribute(ATTR_BASE_PAGE_PATH) );	    		
	    	}
	    }	    
	    try{
	    	
	    	basePage = (BasePageDataModel)baseDataModel;
	    	
	    }catch(ClassCastException e){

	    	//Nem sikerult az utvonalat megtalalni
	    	throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_BASE_PAGE_PATH, element.getAttribute(ATTR_BASE_PAGE_PATH), e );
	    }
		
		//Vegig a PARAMELEMENT-ekent
		NodeList nodelist = element.getChildNodes();
		for( int i = 0; i < nodelist.getLength(); i++ ){
			Node node = nodelist.item( i );
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element paramElement = (Element)node;
				//if( paramElement.getTagName().equals( ParamElementDataModel.getTagStatic().getName() )){
				if( paramElement.getTagName().equals( Tag.PARAMELEMENT.getName() )){					
					this.add(new ParamElementDataModel(paramElement, basePage, variableDataModel ));
				}
			}
		}		


	}

	@Override
	public Tag getTag() {
		return TAG;
		//return getTagStatic();
	}
	
	public BasePageDataModel getBasePage(){
		return basePage;
	}
	
	@Override
	public void add(ParamDataModelInterface node) {
		super.add( (MutableTreeNode)node );
	}
	
	public static String  getModelNameToShowStatic(){
		return CommonOperations.getTranslation( "tree.nodetype.param.page");
	}
	
	@Override
	public String getModelNameToShow(){
		return getModelNameToShowStatic();
	}

	@Override
	public Element getXMLElement(Document document) {
		Attr attr;

		//Node element
		Element pageElement = document.createElement(TAG.getName());
		
		//NAME attributum
		attr = document.createAttribute(ATTR_NAME);
		attr.setValue( getName() );
		pageElement.setAttributeNode(attr);	

		//PAGEBASEPAGE attributum
		attr = document.createAttribute( ATTR_BASE_PAGE_PATH );
		attr.setValue( basePage.getPathTag() );
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

/*	@Override
	public void removeBaseElement(BaseElementDataModel element) {
		// TODO Auto-generated method stub Megoldani!!!!
		
	}
*/
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
		if( this instanceof torlendo_CustomPageInterface ){
			
			//Megszerzi a forraskodot
			String script = ((torlendo_CustomPageInterface)this).getSurce();
			
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
