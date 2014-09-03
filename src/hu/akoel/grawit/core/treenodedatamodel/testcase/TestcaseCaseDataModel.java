package hu.akoel.grawit.core.treenodedatamodel.testcase;

import java.io.StringReader;

import javax.swing.tree.MutableTreeNode;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.ParamDataModelInterface;
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
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class TestcaseCaseDataModel extends TestcaseDataModelInterface{

	private static final long serialVersionUID = -2139557326147525999L;

	public static final Tag TAG = Tag.TESTCASECASE;
	
	public static final String ATTR_DETAILS = "details";
	public static final String ATTR_OPEN_PAGE_PATH = "openpagepath";
	public static final String ATTR_CLOSE_PAGE_PATH = "closepagepath";
	
	private String name;
	private String details;
	private SpecialOpenDataModel openPage;
	private SpecialCloseDataModel closePage;
	
	public TestcaseCaseDataModel( String name, String details, SpecialOpenDataModel openPage, SpecialCloseDataModel closePage ){
		super( );
		this.name = name;
		this.details = details;
		this.openPage = openPage;
		this.closePage = closePage;
		
	}
	
	/**
	 * XML alapjan legyartja a TESTCASECASE-ot es az alatta elofordulo 
	 * TESTCASECASE-okat, illetve TESTCASEPAGE-eket
	 * 
	 * @param element
	 * @throws XMLMissingAttributePharseException 
	 */
	public TestcaseCaseDataModel( Element element, SpecialDataModelInterface specialDataModel, ParamDataModelInterface paramDataModel ) throws XMLPharseException{
		
		if( !element.hasAttribute( ATTR_NAME ) ){
			throw new XMLMissingAttributePharseException( TestcaseCaseDataModel.getRootTag(), Tag.TESTCASENODE, ATTR_NAME );			
		}
		String nameString = element.getAttribute( ATTR_NAME );
		this.name = nameString;
		
		if( !element.hasAttribute( ATTR_DETAILS ) ){
			throw new XMLMissingAttributePharseException( TestcaseCaseDataModel.getRootTag(), Tag.TESTCASENODE, ATTR_NAME, getName(), ATTR_DETAILS );			
		}		
		String detailsString = element.getAttribute( ATTR_DETAILS );		
		this.details = detailsString;
		
		SpecialDataModelInterface specialDataModelForOpen = (SpecialDataModelInterface)specialDataModel.clone();
		SpecialDataModelInterface specialDataModelForClose = (SpecialDataModelInterface)specialDataModel.clone();
		
		//
		//closePage
		//
		if( !element.hasAttribute( ATTR_CLOSE_PAGE_PATH ) ){
			throw new XMLMissingAttributePharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_CLOSE_PAGE_PATH );			
		}	
		String closeElementPathString = element.getAttribute(ATTR_CLOSE_PAGE_PATH);				
		closeElementPathString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + closeElementPathString;  
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
	    DocumentBuilder builder;
	    Document document = null;
	    try  
	    {  
	        builder = factory.newDocumentBuilder();  
	        document = builder.parse( new InputSource( new StringReader( closeElementPathString ) ) );  
	    } catch (Exception e) {  
	    
	    	//Nem sikerult az atalakitas
	    	throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_CLOSE_PAGE_PATH, element.getAttribute(ATTR_CLOSE_PAGE_PATH ), e );
	    } 	    
	    
	    //Megkeresem a SPECIALROOT-ben az OPEN-hez vezeto utat
	    Node actualNode = document;
	    while( actualNode.hasChildNodes() ){
		
	    	actualNode = actualNode.getFirstChild();
	    	Element actualElement = (Element)actualNode;
	    	String tagName = actualElement.getTagName();
	    	String attrName = null;
	    	
	    	//Ha SPECIALNODE
	    	if( tagName.equals( SpecialNodeDataModel.TAG.getName() ) ){
	    		attrName = actualElement.getAttribute(ParamNodeDataModel.ATTR_NAME);	    		
	    		specialDataModelForClose = (SpecialDataModelInterface) CommonOperations.getDataModelByNameInLevel( specialDataModelForClose, Tag.SPECIALNODE, attrName );

	    		if( null == specialDataModelForClose ){

	    			throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_CLOSE_PAGE_PATH, element.getAttribute(ATTR_CLOSE_PAGE_PATH) );
	    		}
	    		
	    	//Ha PARAMOPEN
	    	}else if( tagName.equals( SpecialOpenDataModel.TAG.getName() ) ){
	    		attrName = actualElement.getAttribute(SpecialOpenDataModel.ATTR_NAME);
	    		specialDataModelForClose = (SpecialDataModelInterface) CommonOperations.getDataModelByNameInLevel( specialDataModelForClose, Tag.SPECIALCLOSE, attrName );
	    		
	    		if( null == specialDataModelForClose ){
	    			throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_CLOSE_PAGE_PATH, element.getAttribute(ATTR_CLOSE_PAGE_PATH) );
	    		}
	    		
	    	//Ha PARAMCLOSE
	    	}else if( tagName.equals( SpecialCloseDataModel.TAG.getName() ) ){
	    		attrName = actualElement.getAttribute(SpecialCloseDataModel.ATTR_NAME);
	    		specialDataModelForClose = (SpecialDataModelInterface) CommonOperations.getDataModelByNameInLevel( specialDataModelForClose, Tag.SPECIALCLOSE, attrName );
	    		
	    		if( null == specialDataModelForClose ){
	    			throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_CLOSE_PAGE_PATH, element.getAttribute(ATTR_CLOSE_PAGE_PATH) );
	    		}
	    		
	    	}else{
	    		
	    		throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_CLOSE_PAGE_PATH, element.getAttribute(ATTR_CLOSE_PAGE_PATH) );	    		
	    	}
	    }	    
	    try{
	    	
	    	closePage = (SpecialCloseDataModel)specialDataModelForClose;
	    	
	    }catch(ClassCastException e){

	    	//Nem sikerult az utvonalat megtalalni
	    	throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_CLOSE_PAGE_PATH, element.getAttribute(ATTR_CLOSE_PAGE_PATH), e );
	    }	
		
		//
		//openPage
		//
		if( !element.hasAttribute( ATTR_OPEN_PAGE_PATH ) ){
			throw new XMLMissingAttributePharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_OPEN_PAGE_PATH );			
		}	
		String openElementPathString = element.getAttribute(ATTR_OPEN_PAGE_PATH);				
		openElementPathString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + openElementPathString;  
		//DocumentBuilderFactory 
		factory = DocumentBuilderFactory.newInstance();  
	    //DocumentBuilder builder;
	    //Document document = null;
	    try  
	    {  
	        builder = factory.newDocumentBuilder();  
	        document = builder.parse( new InputSource( new StringReader( openElementPathString ) ) );  
	    } catch (Exception e) {  
	    
	    	//Nem sikerult az atalakitas
	    	throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_OPEN_PAGE_PATH, element.getAttribute(ATTR_OPEN_PAGE_PATH ), e );
	    } 	    
	    
	    //Megkeresem a SPECIALROOT-ben az OPEN-hez vezeto utat
	    //Node 
	    actualNode = document;
	    while( actualNode.hasChildNodes() ){
		
	    	actualNode = actualNode.getFirstChild();
	    	Element actualElement = (Element)actualNode;
	    	String tagName = actualElement.getTagName();
	    	String attrName = null;
	    	
	    	//Ha SPECIALNODE
	    	if( tagName.equals( SpecialNodeDataModel.TAG.getName() ) ){
	    		attrName = actualElement.getAttribute(ParamNodeDataModel.ATTR_NAME);	    		
	    		specialDataModelForOpen = (SpecialDataModelInterface) CommonOperations.getDataModelByNameInLevel( specialDataModelForOpen, Tag.SPECIALNODE, attrName );

	    		if( null == specialDataModelForOpen ){

	    			throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_OPEN_PAGE_PATH, element.getAttribute(ATTR_OPEN_PAGE_PATH) );
	    		}
	    		
	    	//Ha PARAMOPEN
	    	}else if( tagName.equals( SpecialOpenDataModel.TAG.getName() ) ){
	    		attrName = actualElement.getAttribute(SpecialOpenDataModel.ATTR_NAME);
	    		specialDataModelForOpen = (SpecialDataModelInterface) CommonOperations.getDataModelByNameInLevel( specialDataModelForOpen, Tag.SPECIALOPEN, attrName );
	    		
	    		if( null == specialDataModelForOpen ){
	    		
	    			throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_OPEN_PAGE_PATH, element.getAttribute(ATTR_OPEN_PAGE_PATH) );
	    		}
	    		
	    	//Ha PARAMCLOSE
	    	}else if( tagName.equals( SpecialCloseDataModel.TAG.getName() ) ){
	    		attrName = actualElement.getAttribute(SpecialCloseDataModel.ATTR_NAME);
	    		specialDataModelForOpen = (SpecialDataModelInterface) CommonOperations.getDataModelByNameInLevel( specialDataModelForOpen, Tag.SPECIALCLOSE, attrName );
	    		
	    		if( null == specialDataModelForOpen ){
	    			
	    			throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_OPEN_PAGE_PATH, element.getAttribute(ATTR_OPEN_PAGE_PATH) );
	    		}
	    		
	    	}else{
	    		
	    		throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_OPEN_PAGE_PATH, element.getAttribute(ATTR_OPEN_PAGE_PATH) );	    		
	    	}
	    }	    
	    try{
	    	
	    	openPage = (SpecialOpenDataModel)specialDataModelForOpen;
	    	
	    }catch(ClassCastException e){

	    	//Nem sikerult az utvonalat megtalalni
	    	throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_OPEN_PAGE_PATH, element.getAttribute(ATTR_OPEN_PAGE_PATH), e );
	    }
		

		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		NodeList nodelist = element.getChildNodes();
		for( int i = 0; i < nodelist.getLength(); i++ ){
			Node node = nodelist.item( i );
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element testcaseElement = (Element)node;
				
				//Ha TESTCASEPAGE van alatta
				if( testcaseElement.getTagName().equals( Tag.TESTCASEPAGE.getName() )){
					
					this.add(new TestcasePageDataModel(testcaseElement, paramDataModel ));
				
				}
			}
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
		return CommonOperations.getTranslation( "tree.nodetype.testcase.case");
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

	@Override
	public Element getXMLElement(Document document) {
		Attr attr;
		
		//Node element
		Element nodeElement = document.createElement( TestcaseCaseDataModel.this.getTag().getName() );
		attr = document.createAttribute( ATTR_NAME );
		attr.setValue( getName() );
		nodeElement.setAttributeNode(attr);	
		
		attr = document.createAttribute( ATTR_DETAILS );
		attr.setValue( getDetails() );
		nodeElement.setAttributeNode(attr);	

		attr = document.createAttribute( ATTR_OPEN_PAGE_PATH );
		attr.setValue( openPage.getPathTag() );
		nodeElement.setAttributeNode(attr);	

		attr = document.createAttribute( ATTR_CLOSE_PAGE_PATH );
		attr.setValue( closePage.getPathTag() );
		nodeElement.setAttributeNode(attr);	
	
		int childrens = this.getChildCount();
		for( int i = 0; i < childrens; i++ ){
			
			Object object = this.getChildAt( i );
			
			if( !object.equals(this) && object instanceof TestcaseDataModelInterface ){
				
				Element element = ((TestcaseDataModelInterface)object).getXMLElement( document );
				nodeElement.appendChild( element );		    		
		    	
			}
		}
	
		return nodeElement;		
	}

	public void setSpecialOpenDataModel( SpecialOpenDataModel openPage ){
		this.openPage = openPage;
	}

	public void setSpecialCloseDataModel( SpecialCloseDataModel closePage ){
		this.closePage = closePage;
	}

	public SpecialOpenDataModel getOpenPage(){
		return openPage;
	}
	
	public SpecialCloseDataModel getClosePage(){
		return closePage;
	}

}
