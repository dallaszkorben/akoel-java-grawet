package hu.akoel.grawit.core.treenodedatamodel.testcase;

import java.io.StringReader;

import javax.swing.tree.MutableTreeNode;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.SpecialDataModelInterface;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamNodeDataModel;
import hu.akoel.grawit.core.treenodedatamodel.special.SpecialCloseDataModel;
import hu.akoel.grawit.core.treenodedatamodel.special.SpecialNodeDataModel;
import hu.akoel.grawit.core.treenodedatamodel.special.SpecialOpenDataModel;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.exceptions.XMLBaseConversionPharseException;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;
import hu.akoel.grawit.exceptions.XMLPharseException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
//TODO csak atneveztem. meg kell szerkeszteni
public class TestcaseCustomDataModel extends TestcaseDataModelInterface{

	private static final long serialVersionUID = 5313170692938571481L;

	public static final Tag TAG = Tag.TESTCASECUSTOM;
	
	public static final String ATTR_DETAILS = "details";
	public static final String ATTR_SPECIAL_PAGE_PATH = "specialpagepath";
	
	private String name;
	private String details;
	private SpecialDataModelInterface specialPage;
	
	public TestcaseCustomDataModel( String name, String details, SpecialDataModelInterface specialPage ){
		super( );
		this.name = name;
		this.details = details;
		this.specialPage = specialPage;
	}
	
	/**
	 * XML alapjan legyartja a TESTCASESPECIAL-et es az alatta elofordulo 
	 * 
	 * @param element
	 * @throws XMLMissingAttributePharseException 
	 */
	public TestcaseCustomDataModel( Element element, SpecialDataModelInterface specialDataModel ) throws XMLPharseException{
		
		if( !element.hasAttribute( ATTR_NAME ) ){
			throw new XMLMissingAttributePharseException( TestcaseCustomDataModel.getRootTag(), Tag.TESTCASECUSTOM, ATTR_NAME );			
		}
		String nameString = element.getAttribute( ATTR_NAME );
		this.name = nameString;
		
		if( !element.hasAttribute( ATTR_DETAILS ) ){
			throw new XMLMissingAttributePharseException( TestcaseCustomDataModel.getRootTag(), Tag.TESTCASECUSTOM, ATTR_NAME, getName(), ATTR_DETAILS );			
		}		
		String detailsString = element.getAttribute( ATTR_DETAILS );		
		this.details = detailsString;
		
		
		
		
		
		
		
		
		
		
		
		if( !element.hasAttribute( ATTR_SPECIAL_PAGE_PATH ) ){
			throw new XMLMissingAttributePharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_SPECIAL_PAGE_PATH );			
		}	
		String paramElementPathString = element.getAttribute(ATTR_SPECIAL_PAGE_PATH);				
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
	    	throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_SPECIAL_PAGE_PATH, element.getAttribute(ATTR_SPECIAL_PAGE_PATH), e );
	    } 
	    
	    
	    //Megkeresem a SPECIALROOT-ben a SPECIAL-hez vezeto utat
	    Node actualNode = document;
	    while( actualNode.hasChildNodes() ){
		
	    	actualNode = actualNode.getFirstChild();
	    	Element actualElement = (Element)actualNode;
	    	String tagName = actualElement.getTagName();
	    	String attrName = null;
	    	
	    	//Ha SPECIALNODE
	    	if( tagName.equals( SpecialNodeDataModel.TAG.getName() ) ){
	    		attrName = actualElement.getAttribute(ParamNodeDataModel.ATTR_NAME);	    		
	    		specialDataModel = (SpecialDataModelInterface) CommonOperations.getDataModelByNameInLevel( specialDataModel, Tag.SPECIALNODE, attrName );

	    		if( null == specialDataModel ){

	    			throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_SPECIAL_PAGE_PATH, element.getAttribute(ATTR_SPECIAL_PAGE_PATH) );
	    		}
	    		
	    	//Ha PARAMOPEN
	    	}else if( tagName.equals( SpecialOpenDataModel.TAG.getName() ) ){
	    		attrName = actualElement.getAttribute(SpecialOpenDataModel.ATTR_NAME);
	    		specialDataModel = (SpecialDataModelInterface) CommonOperations.getDataModelByNameInLevel( specialDataModel, Tag.SPECIALOPEN, attrName );
	    		
	    		throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_SPECIAL_PAGE_PATH, element.getAttribute(ATTR_SPECIAL_PAGE_PATH) );
	    		
	    	//Ha PARAMCLOSE
	    	}else if( tagName.equals( SpecialCloseDataModel.TAG.getName() ) ){
	    		attrName = actualElement.getAttribute(SpecialCloseDataModel.ATTR_NAME);
	    		specialDataModel = (SpecialDataModelInterface) CommonOperations.getDataModelByNameInLevel( specialDataModel, Tag.SPECIALCLOSE, attrName );
	    		
	    		throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_SPECIAL_PAGE_PATH, element.getAttribute(ATTR_SPECIAL_PAGE_PATH) );
	    		
	    	}else{
	    		
	    		throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_SPECIAL_PAGE_PATH, element.getAttribute(ATTR_SPECIAL_PAGE_PATH) );	    		
	    	}
	    }	    
	    try{
	    	
	    	specialPage = (SpecialDataModelInterface)specialDataModel;
	    	
	    }catch(ClassCastException e){

	    	//Nem sikerult az utvonalat megtalalni
	    	throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_SPECIAL_PAGE_PATH, element.getAttribute(ATTR_SPECIAL_PAGE_PATH), e );
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
	public void add(TestcaseDataModelInterface node) {
		super.add( (MutableTreeNode)node );
	}
	
	public static String  getModelNameToShowStatic(){
		return CommonOperations.getTranslation( "tree.nodetype.testcase.special");
	}
	
	@Override
	public String getModelNameToShow(){
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

	public void setSpecialPage( SpecialDataModelInterface specialPage ){
		this.specialPage = specialPage;		
	}
	
	public SpecialDataModelInterface getSpecialPage(){
		return specialPage;
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
		
		attr = document.createAttribute( ATTR_SPECIAL_PAGE_PATH );
		attr.setValue( specialPage.getPathTag() );
		nodeElement.setAttributeNode( attr );
			
		return nodeElement;		
	}

}
