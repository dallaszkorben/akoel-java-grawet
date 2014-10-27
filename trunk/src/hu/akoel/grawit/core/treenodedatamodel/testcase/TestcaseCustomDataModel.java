package hu.akoel.grawit.core.treenodedatamodel.testcase;

import java.io.StringReader;
import javax.swing.tree.MutableTreeNode;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.ElementProgressInterface;
import hu.akoel.grawit.PageProgressInterface;
import hu.akoel.grawit.core.treenodedatamodel.SpecialDataModelInterface;
import hu.akoel.grawit.core.treenodedatamodel.TestcaseDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamNodeDataModel;
import hu.akoel.grawit.core.treenodedatamodel.special.SpecialCustomDataModel;
import hu.akoel.grawit.core.treenodedatamodel.special.SpecialNodeDataModel;
import hu.akoel.grawit.core.treenodedatamodel.special.SpecialPageModelInterface;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.exceptions.CompilationException;
import hu.akoel.grawit.exceptions.PageException;
import hu.akoel.grawit.exceptions.XMLBaseConversionPharseException;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;
import hu.akoel.grawit.exceptions.XMLPharseException;

import org.openqa.selenium.WebDriver;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

public class TestcaseCustomDataModel extends TestcasePageModelInterface{

	private static final long serialVersionUID = 5313170692938571481L;

	public static final Tag TAG = Tag.TESTCASECUSTOMPAGE;
	
	public static final String ATTR_DETAILS = "details";
	public static final String ATTR_CUSTOM_PAGE_PATH = "custompagepath";
	
	private String name;
	private String details;
	private SpecialPageModelInterface customPage;
	
	public TestcaseCustomDataModel( String name, String details, SpecialCustomDataModel customPage ){
		super( );
		this.name = name;
		this.details = details;
		this.customPage = customPage;
	}
	
	/**
	 * XML alapjan legyartja a TESTCASESPECIAL-et es az alatta elofordulo 
	 * 
	 * @param element
	 * @throws XMLMissingAttributePharseException 
	 */
	public TestcaseCustomDataModel( Element element, SpecialDataModelInterface customDataModel ) throws XMLPharseException{
		
		if( !element.hasAttribute( ATTR_NAME ) ){
			throw new XMLMissingAttributePharseException( TestcaseCustomDataModel.getRootTag(), Tag.TESTCASECUSTOMPAGE, ATTR_NAME );			
		}
		String nameString = element.getAttribute( ATTR_NAME );
		this.name = nameString;
		
		if( !element.hasAttribute( ATTR_DETAILS ) ){
			throw new XMLMissingAttributePharseException( TestcaseCustomDataModel.getRootTag(), Tag.TESTCASECUSTOMPAGE, ATTR_NAME, getName(), ATTR_DETAILS );			
		}		
		String detailsString = element.getAttribute( ATTR_DETAILS );		
		this.details = detailsString;
		
		
		
		
		
		
		
		
		
		
		
		if( !element.hasAttribute( ATTR_CUSTOM_PAGE_PATH ) ){
			throw new XMLMissingAttributePharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_CUSTOM_PAGE_PATH );			
		}	
		String paramElementPathString = element.getAttribute(ATTR_CUSTOM_PAGE_PATH);				
		paramElementPathString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + paramElementPathString;  
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
	    DocumentBuilder builder;
	    Document document = null;
	    try  
	    {  
	        builder = factory.newDocumentBuilder();  
	        document = builder.parse( new InputSource( new StringReader( paramElementPathString ) ) );  
	    } catch (Exception e) {  
	    
	    	//Nem sikerult az atalakitas
	    	throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_CUSTOM_PAGE_PATH, element.getAttribute(ATTR_CUSTOM_PAGE_PATH), e );
	    } 
	    
	    
	    //Megkeresem a SPECIALROOT-ben a CUSTOMER-hez vezeto utat
	    Node actualNode = document;
	    while( actualNode.hasChildNodes() ){
		
	    	actualNode = actualNode.getFirstChild();
	    	Element actualElement = (Element)actualNode;
	    	String tagName = actualElement.getTagName();
	    	String attrName = null;
	    	
	    	//Ha SPECIALNODE
	    	if( tagName.equals( SpecialNodeDataModel.TAG.getName() ) ){
	    		attrName = actualElement.getAttribute(ParamNodeDataModel.ATTR_NAME);	    		
	    		customDataModel = (SpecialDataModelInterface)CommonOperations.getDataModelByNameInLevel( customDataModel, Tag.SPECIALNODE, attrName );

	    		if( null == customDataModel ){

	    			throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_CUSTOM_PAGE_PATH, element.getAttribute(ATTR_CUSTOM_PAGE_PATH) );
	    		}
	    		
	    	//Ha PARAMCUSTOM
	    	}else if( tagName.equals( SpecialCustomDataModel.TAG.getName() ) ){
	    		attrName = actualElement.getAttribute(SpecialCustomDataModel.ATTR_NAME);
	    		customDataModel = (SpecialDataModelInterface)CommonOperations.getDataModelByNameInLevel( customDataModel, Tag.SPECIALCUSTOM, attrName );
	    		
	    		if( null == customDataModel ){
	    			
	    			throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_CUSTOM_PAGE_PATH, element.getAttribute(ATTR_CUSTOM_PAGE_PATH) );
	    		}
	    		
	    	}
	    }	    
	    try{
	    	
	    	customPage = (SpecialCustomDataModel)customDataModel;
	    	
	    }catch(ClassCastException e){

	    	//Nem sikerult az utvonalat megtalalni
	    	throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_CUSTOM_PAGE_PATH, element.getAttribute(ATTR_CUSTOM_PAGE_PATH), e );
	    } 
	    

	}
	
	public static Tag getTagStatic(){
		return TAG;
	}

	@Override
	public Tag getTag() {
		return getTagStatic();
	}

	@Override
	public void add(TestcaseDataModelAdapter node) {
		//TODO delete super.add( (MutableTreeNode)node );
	}
	
	public static String  getModelNameToShowStatic(){
		return CommonOperations.getTranslation( "tree.nodetype.testcase.custom");
	}
	
	@Override
	public String getNodeTypeToShow(){
		return getModelNameToShowStatic();
	}
	
	@Override
	public String getName(){
		return name;
	}
	
	public String getDetails(){
		return details;
	}
	
	public void setDetails( String details ){
		this.details = details;
	}
	
	public void setName( String name ){
		this.name = name;
	}
	
	public String toString(){
		return name;
	}

	public void setCustomPage( SpecialCustomDataModel customPage ){
		this.customPage = customPage;		
	}
	
	public SpecialCustomDataModel getCustomPage(){
		return (SpecialCustomDataModel)customPage;
	}
	
	@Override
	public Element getXMLElement(Document document) {
		Attr attr;
		
		//Node element
		Element nodeElement = document.createElement( TestcaseCustomDataModel.this.getTag().getName() );
		attr = document.createAttribute( ATTR_NAME );
		attr.setValue( getName() );
		nodeElement.setAttributeNode(attr);	
		
		attr = document.createAttribute( ATTR_DETAILS );
		attr.setValue( getDetails() );
		nodeElement.setAttributeNode(attr);	
		
		attr = document.createAttribute( ATTR_CUSTOM_PAGE_PATH );
		attr.setValue( customPage.getPathTag() );
		nodeElement.setAttributeNode( attr );
			
		return nodeElement;		
	}

	@Override
	public void doAction(WebDriver driver, PageProgressInterface pageProgress, ElementProgressInterface elementProgress ) throws PageException, CompilationException {
		
		customPage.doAction(driver, pageProgress, elementProgress );
		
	}

	@Override
	public Object clone(){
		
		//Leklonozza a CUSTOM PAGE-et
		TestcaseCustomDataModel cloned = (TestcaseCustomDataModel)super.clone();
		
		//Es a valtozokat is leklonozza
		cloned.name = new String( this.name );
		cloned.details = new String( this.details );
		
		return cloned;
		
	}
	
	@Override
	public Object cloneWithParent() {
		
		TestcaseCustomDataModel cloned = (TestcaseCustomDataModel) this.clone();
		
		//Le kell masolni a felmenoit is, egyebkent azok automatikusan null-ok
		cloned.setParent( (MutableTreeNode) this.getParent() );
		
		return cloned;
	}
}
