package hu.akoel.grawit.core.treenodedatamodel.testcase;

import java.io.StringReader;

import javax.swing.tree.MutableTreeNode;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.Player;
import hu.akoel.grawit.core.treenodedatamodel.ParamDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.TestcaseDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamNodeDataModel;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamPageDataModel;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.exceptions.CompilationException;
import hu.akoel.grawit.exceptions.PageException;
import hu.akoel.grawit.exceptions.StoppedByUserException;
import hu.akoel.grawit.exceptions.XMLBaseConversionPharseException;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;
import hu.akoel.grawit.exceptions.XMLPharseException;
import hu.akoel.grawit.gui.interfaces.progress.ElementProgressInterface;
import hu.akoel.grawit.gui.interfaces.progress.PageProgressInterface;

import org.openqa.selenium.WebDriver;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

public class TestcaseParamPageDataModel extends TestcasePageModelInterface{

	private static final long serialVersionUID = 5313170692938571481L;

	public static final Tag TAG = Tag.TESTCASEPARAMPAGE;
	
	public static final String ATTR_DETAILS = "details";
	public static final String ATTR_PARAM_PAGE_PATH = "parampagepath";
	private static final String ATTR_ON = "on";
	
	private String name;
	private String details;
	
	private ParamPageDataModel paramPage;
	
	public TestcaseParamPageDataModel( String name, String details, ParamPageDataModel paramPage ){
		super( );
		this.name = name;
		this.details = details;
		this.paramPage = paramPage;
		
		//Engedelyezi a Node Ki/Be kapcsolasat
		this.setEnabledToTurnOnOff( true );
	}
	
	/**
	 * XML alapjan legyartja a TESTCASEPAGE-et es az alatta elofordulo 
	 * 
	 * @param element
	 * @throws XMLMissingAttributePharseException 
	 */
	public TestcaseParamPageDataModel( Element element, ParamDataModelAdapter paramDataModel ) throws XMLPharseException{
		
		//Engedelyezi a Node Ki/Be kapcsolasat
		this.setEnabledToTurnOnOff( true );
		
		//========
		//
		// Name
		//
		//========	
		if( !element.hasAttribute( ATTR_NAME ) ){
			throw new XMLMissingAttributePharseException( TestcaseParamPageDataModel.getRootTag(), Tag.TESTCASEPARAMPAGE, ATTR_NAME );			
		}
		String nameString = element.getAttribute( ATTR_NAME );
		this.name = nameString;
		
		//========
		//
		// On
		//
		//========		
		if( !element.hasAttribute( ATTR_ON ) ){
			this.setOn( Boolean.TRUE );
		}else{
			String enabledString = element.getAttribute( ATTR_ON );
			this.setOn( Boolean.parseBoolean( enabledString ));
		}	
		
		//========
		//
		// Details
		//
		//========	
		if( !element.hasAttribute( ATTR_DETAILS ) ){
			throw new XMLMissingAttributePharseException( TestcaseParamPageDataModel.getRootTag(), Tag.TESTCASEPARAMPAGE, ATTR_NAME, getName(), ATTR_DETAILS );			
		}		
		String detailsString = element.getAttribute( ATTR_DETAILS );		
		this.details = detailsString;

		
		//========
		//
		// Param page path
		//
		//========	
		if( !element.hasAttribute( ATTR_PARAM_PAGE_PATH ) ){
			throw new XMLMissingAttributePharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_PARAM_PAGE_PATH );			
		}	
		String paramElementPathString = element.getAttribute(ATTR_PARAM_PAGE_PATH);				
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
	    	throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_PARAM_PAGE_PATH, element.getAttribute(ATTR_PARAM_PAGE_PATH), e );
	    } 
	    	    
	    //Megkeresem a PARAMPAGEROOT-ben a PARAMPAGE-hez vezeto utat
	    Node actualNode = document;
	    while( actualNode.hasChildNodes() ){
		
	    	actualNode = actualNode.getFirstChild();
	    	Element actualElement = (Element)actualNode;
	    	String tagName = actualElement.getTagName();
	    	String attrName = null;
	    	
	    	//Ha PARAMNODE
	    	if( tagName.equals( ParamNodeDataModel.TAG.getName() ) ){
	    		attrName = actualElement.getAttribute(ParamNodeDataModel.ATTR_NAME);	    		
	    		paramDataModel = (ParamDataModelAdapter) CommonOperations.getDataModelByNameInLevel( paramDataModel, Tag.PARAMNODE, attrName );

	    		if( null == paramDataModel ){

	    			throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_PARAM_PAGE_PATH, element.getAttribute(ATTR_PARAM_PAGE_PATH) );
	    		}
	    		
	    	//Ha PARAMPAGE
	    	}else if( tagName.equals( ParamPageDataModel.TAG.getName() ) ){
	    		attrName = actualElement.getAttribute(ParamPageDataModel.ATTR_NAME);
	    		paramDataModel = (ParamDataModelAdapter) CommonOperations.getDataModelByNameInLevel( paramDataModel, Tag.PARAMPAGE, attrName );
	    		if( null == paramDataModel ){

	    			throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_PARAM_PAGE_PATH, element.getAttribute(ATTR_PARAM_PAGE_PATH) );
	    		}
	    		
	    	//Ha PARAMELEMENT - ez nem lehet, torold ki ezt a feltetelt
	    	}else if( tagName.equals( ParamElementDataModel.TAG.getName() ) ){
	    		attrName = actualElement.getAttribute(ParamElementDataModel.ATTR_NAME);

	    		throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_PARAM_PAGE_PATH, element.getAttribute(ATTR_PARAM_PAGE_PATH) );	    		
	    	}else{
	    		
	    		throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_PARAM_PAGE_PATH, element.getAttribute(ATTR_PARAM_PAGE_PATH) );	    		
	    	}
	    }	    
	    try{
	    	
	    	paramPage = (ParamPageDataModel)paramDataModel;
	    	
	    }catch(ClassCastException e){

	    	//Nem sikerult az utvonalat megtalalni
	    	throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_PARAM_PAGE_PATH, element.getAttribute(ATTR_PARAM_PAGE_PATH), e );
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
		super.add( (MutableTreeNode)node );
	}
	
	public static String  getModelNameToShowStatic(){
		return CommonOperations.getTranslation( "tree.nodetype.testcase.page");
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

	public void setParamPage( ParamPageDataModel paramPage ){
		this.paramPage = paramPage;		
	}
	
	public ParamPageDataModel getParamPage(){
		return paramPage;
	}
	
	@Override
	public Element getXMLElement(Document document) {
		Attr attr;
		
		//========
		//
		// Node element
		//
		//========
		Element nodeElement = document.createElement( TestcaseParamPageDataModel.this.getTag().getName() );
		attr = document.createAttribute( ATTR_NAME );
		attr.setValue( getName() );
		nodeElement.setAttributeNode(attr);	
		
		//========
		//
		// On
		//
		//========
		attr = document.createAttribute( ATTR_ON );
		attr.setValue( this.isOn().toString() );
		nodeElement.setAttributeNode(attr);

		//========
		//
		// Details
		//
		//========
		attr = document.createAttribute( ATTR_DETAILS );
		attr.setValue( getDetails() );
		nodeElement.setAttributeNode(attr);	
		
		//========
		//
		// Param page path
		//
		//========
		attr = document.createAttribute( ATTR_PARAM_PAGE_PATH );
		attr.setValue( paramPage.getPathTag() );
		nodeElement.setAttributeNode( attr );
			
		return nodeElement;		
	}

	@Override
	public void doAction(WebDriver driver, Player player, PageProgressInterface pageProgress, ElementProgressInterface elementProgress ) throws PageException, CompilationException, StoppedByUserException {
		
		//Ha Be van kapcsolava a TestParamPage oldal
		if( this.isOn() ){
			paramPage.doAction( driver, player, pageProgress, elementProgress );
		}
	}
	
	@Override
	public Object clone(){
		
		//Leklonozza a PARAM PAGE-et
		TestcaseParamPageDataModel cloned = (TestcaseParamPageDataModel)super.clone();
	
		//Es a valtozokat is leklonozza
		cloned.name = new String( this.name );
		cloned.details = new String( this.details );
		
		return cloned;
		
	}
	
	@Override
	public Object cloneWithParent() {
		
		TestcaseParamPageDataModel cloned = (TestcaseParamPageDataModel) this.clone();
		
		//Le kell masolni a felmenoit is, egyebkent azok automatikusan null-ok
		cloned.setParent( (MutableTreeNode) this.getParent() );
		
		return cloned;
	}
}
