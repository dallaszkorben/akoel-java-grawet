package hu.akoel.grawit.core.datamodel.pages;

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
import hu.akoel.grawit.core.datamodel.BaseDataModelInterface;
import hu.akoel.grawit.core.datamodel.DataModelInterface;
import hu.akoel.grawit.core.datamodel.ParamDataModelInterface;
import hu.akoel.grawit.core.datamodel.elements.BaseElementDataModel;
import hu.akoel.grawit.core.datamodel.elements.ParamElementDataModel;
import hu.akoel.grawit.core.datamodel.nodes.BaseNodeDataModel;
import hu.akoel.grawit.core.datamodel.roots.BaseRootDataModel;
import hu.akoel.grawit.core.pages.BasePageChangeListener;
import hu.akoel.grawit.core.pages.CustomPageInterface;
import hu.akoel.grawit.core.pages.ExecutablePageInterface;
import hu.akoel.grawit.core.pages.PageProgressInterface;
import hu.akoel.grawit.exceptions.CompilationException;
import hu.akoel.grawit.exceptions.ElementException;
import hu.akoel.grawit.exceptions.PageException;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;
import hu.akoel.grawit.exceptions.XMLPharseException;

public class ParamPageDataModel  extends ParamDataModelInterface implements ExecutablePageInterface, BasePageChangeListener{
	
	private static final long serialVersionUID = -5098304990124055586L;
	
	private static final String TAG_NAME = "page";
	
	private static final String ATTR_NAME = "name";
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
	public ParamPageDataModel( Element element, BaseDataModelInterface baseDataModel ) throws XMLPharseException{
		
		//name
		if( !element.hasAttribute( ATTR_NAME ) ){
			throw new XMLMissingAttributePharseException( getModelType().getName(), TAG_NAME, ATTR_NAME );			
		}
		String nameString = element.getAttribute( ATTR_NAME );		
		this.name = nameString;
		
		//BasePage
		if( !element.hasAttribute( ATTR_BASE_PAGE_PATH ) ){
			throw new XMLMissingAttributePharseException( getModelType().getName(), TAG_NAME, ATTR_BASE_PAGE_PATH );			
		}
		String paramPagePathString = element.getAttribute(ATTR_BASE_PAGE_PATH);				
		paramPagePathString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + paramPagePathString;  
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
	    DocumentBuilder builder; 
	    Document document = null;
	    try  
	    {  
	        builder = factory.newDocumentBuilder();  
	        document = builder.parse( new InputSource( new StringReader( paramPagePathString ) ) );  
	    } catch (Exception e) {  
	    	//TODO XMLParseException nem tudja olvasni a referenciat a basepage-re
	        e.printStackTrace();  
	    } 
		//Biztosan egy egyenes vonalu utvonal
	    Node actualNode = document;
	    while( actualNode.hasChildNodes() ){
		
	    	actualNode = actualNode.getFirstChild();
	    	Element actualElement = (Element)actualNode;
	    	String tagName = actualElement.getTagName();
	    	String attrName = null;
	    	
	    	//Ha BASENODE
	    	if( tagName.equals( BaseNodeDataModel.TAG_NAME ) ){
	    		attrName = actualElement.getAttribute(BaseNodeDataModel.ATTR_NAME);	    		
	    		baseDataModel = (BaseDataModelInterface) CommonOperations.getDataModelByNameInLevel( baseDataModel, BaseNodeDataModel.TAG_NAME, attrName );

	    		if( null == baseDataModel ){
	    			//TODO XMLParseException nem sikerult olvasni a referencia basePage-et
	    			throw new Error("XMLParseException nem sikerult olvasni a referencia basePage-et");
	    		}
	    		
	    	//Ha BASEPAGE
	    	}else if( tagName.equals( BasePageDataModel.TAG_NAME ) ){
	    		attrName = actualElement.getAttribute(BasePageDataModel.ATTR_NAME);
	    		baseDataModel = (BaseDataModelInterface) CommonOperations.getDataModelByNameInLevel( baseDataModel, BasePageDataModel.TAG_NAME, attrName );
	    		if( null == baseDataModel ){
	    			//TODO XMLParseException nem sikerult olvasni a referencia basePage-et
	    			throw new Error("XMLParseException nem sikerult olvasni a referencia basePage-et");
	    		}
	    		
	    	//Ha BASEELEMENT
	    	}else if( tagName.equals( BaseElementDataModel.TAG_NAME ) ){
	    		attrName = actualElement.getAttribute(BaseElementDataModel.ATTR_NAME);
	    		//todo XMLParseException nem talalja a referenciat
	    		throw new Error("XMLParseException nem sikerult olvasni a referencia basePage-et. itt nem lenne szabad lennie");
	    		
	    	}else{
	    		//todo XMLParseException nem talalja a referenciat
	    		throw new Error("XMLParseException nem sikerult olvasni a referencia basePage-et. itt nem lenne szabad lennie");
	    		
	    	}
	    }	    
	    try{
	    	basePage = (BasePageDataModel)baseDataModel;
	    }catch(ClassCastException e){
	    	//todo XMLParseException valami elromlott
    		throw new Error("XMLParseException nem sikerult olvasni a referencia basePage-et. itt nem lenne szabad lennie");
	    }
		
		//Vegig a PARAMELEMENT-ekent
		NodeList nodelist = element.getChildNodes();
		for( int i = 0; i < nodelist.getLength(); i++ ){
			Node node = nodelist.item( i );
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element paramElement = (Element)node;
				if( paramElement.getTagName().equals( ParamElementDataModel.getTagNameStatic() )){
					this.add(new ParamElementDataModel(paramElement, basePage ));
				}
			}
		}		


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
	public String getTypeToShow(){
		return CommonOperations.getTranslation( "tree.nodetype.parampage");
	}
	
	@Override
	public Element getXMLElement(Document document) {
		Attr attr;

		//Node element
		Element pageElement = document.createElement(TAG_NAME);
		
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
