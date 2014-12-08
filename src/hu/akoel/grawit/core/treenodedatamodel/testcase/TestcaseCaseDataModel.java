package hu.akoel.grawit.core.treenodedatamodel.testcase;

import java.io.StringReader;
import java.util.Vector;

import javax.swing.tree.MutableTreeNode;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.DriverDataModelInterface;
import hu.akoel.grawit.core.treenodedatamodel.ParamDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.TestcaseDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.driver.DriverBrowserDataModelInterface;
import hu.akoel.grawit.core.treenodedatamodel.driver.DriverFirefoxDataModel;
import hu.akoel.grawit.core.treenodedatamodel.driver.DriverFirefoxPropertyDataModel;
import hu.akoel.grawit.core.treenodedatamodel.driver.DriverNodeDataModel;
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

public class TestcaseCaseDataModel extends TestcaseDataModelAdapter{

	private static final long serialVersionUID = -2139557326147525999L;

	public static final Tag TAG = Tag.TESTCASECASE;
	
	public static final String ATTR_DETAILS = "details";
	public static final String ATTR_DRIVER_PATH = "driverpath";
	private static final String ATTR_ON = "on";
	
	private String name;
	private String details;
	private DriverBrowserDataModelInterface<?> driver;
	
	public TestcaseCaseDataModel( String name, String details, DriverBrowserDataModelInterface<?> driver ){	
		super( );
		this.name = name;
		this.details = details;
		this.driver = driver;
		
		//Engedelyezi a Node Ki/Be kapcsolasat
		this.setEnabledToTurnOnOff( true );
		
	}
	
	/**
	 * XML alapjan legyartja a TESTCASECASE-ot es az alatta elofordulo 
	 * TESTCASECASE-okat, illetve TESTCASEPAGE-eket
	 * 
	 * @param element
	 * @throws XMLMissingAttributePharseException 
	 */
	public TestcaseCaseDataModel( Element element, ParamDataModelAdapter paramDataModel, DriverDataModelInterface driverDataModel ) throws XMLPharseException{
		
		//Engedelyezi a Node Ki/Be kapcsolasat
		this.setEnabledToTurnOnOff( true );
		
		//========
		//
		// Name
		//
		//========
		if( !element.hasAttribute( ATTR_NAME ) ){
			throw new XMLMissingAttributePharseException( TestcaseCaseDataModel.getRootTag(), Tag.TESTCASENODE, ATTR_NAME );			
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
			throw new XMLMissingAttributePharseException( TestcaseCaseDataModel.getRootTag(), Tag.TESTCASENODE, ATTR_NAME, getName(), ATTR_DETAILS );			
		}		
		String detailsString = element.getAttribute( ATTR_DETAILS );		
		this.details = detailsString;
	
		DocumentBuilderFactory factory = null;  
		DocumentBuilder builder = null;
		Document document = null;
		Node actualNode = null;
		
	    //========
		//
		// Driver
		//
	    //========
		if( !element.hasAttribute( ATTR_DRIVER_PATH ) ){
			throw new XMLMissingAttributePharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_DRIVER_PATH );			
		}	
		String driverPathString = element.getAttribute(ATTR_DRIVER_PATH );				
		driverPathString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + driverPathString;  
		factory = DocumentBuilderFactory.newInstance();  
	    try  
	    {  
	        builder = factory.newDocumentBuilder();  
	        document = builder.parse( new InputSource( new StringReader( driverPathString ) ) );  
	    } catch (Exception e) {  
	    
	    	//Nem sikerult az atalakitas
	    	throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_DRIVER_PATH, element.getAttribute(ATTR_DRIVER_PATH ), e );
	    } 	    
	    
	    //Megkeresem a DRIVERROOT-ben az DRIVER-hez vezeto utat
	    //Node 
	    actualNode = document;
	    while( actualNode.hasChildNodes() ){
		
	    	actualNode = actualNode.getFirstChild();
	    	Element actualElement = (Element)actualNode;
	    	String tagName = actualElement.getTagName();
	    	String attrName = null;
	    	
	    	//Ha DRIVERNODE
	    	if( tagName.equals( DriverNodeDataModel.TAG.getName() ) ){
	    		attrName = actualElement.getAttribute(DriverNodeDataModel.ATTR_NAME);	    		
	    		driverDataModel = (DriverDataModelInterface) CommonOperations.getDataModelByNameInLevel( driverDataModel, Tag.DRIVERNODE, attrName );

	    		if( null == driverDataModel ){

	    			throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_DRIVER_PATH, element.getAttribute(ATTR_DRIVER_PATH ) );
	    		}
	    		
	    	//Ha DRIVERFIREFOX
	    	}else if( tagName.equals( DriverFirefoxDataModel.TAG.getName() ) ){
	    		attrName = actualElement.getAttribute(DriverFirefoxDataModel.ATTR_NAME);
	    		driverDataModel = (DriverDataModelInterface) CommonOperations.getDataModelByNameInLevel( driverDataModel, Tag.DRIVERFIREFOX, attrName );
	    		
	    		if( null == driverDataModel ){
	    		
	    			throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_DRIVER_PATH, element.getAttribute(ATTR_DRIVER_PATH) );
	    		}
	    		
	    	//Ha DRIVERFIREFOXPROPERY
	    	}else if( tagName.equals( DriverFirefoxPropertyDataModel.TAG.getName() ) ){
	    		attrName = actualElement.getAttribute(DriverFirefoxPropertyDataModel.ATTR_NAME);
	    		driverDataModel = (DriverDataModelInterface) CommonOperations.getDataModelByNameInLevel( driverDataModel, Tag.DRIVERFIREFOXPROPERTY, attrName );
	    		
	    		if( null == driverDataModel ){
	    			
	    			throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_DRIVER_PATH, element.getAttribute(ATTR_DRIVER_PATH) );
	    		}
	    		
	    	//}else{
	    		
	    	//	throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_OPEN_PAGE_PATH, element.getAttribute(ATTR_OPEN_PAGE_PATH) );	    		
	    	}
	    }	    
	    try{
	    	
	    	driver = (DriverBrowserDataModelInterface<?>)driverDataModel;
	    	
	    }catch(ClassCastException e){

	    	//Nem sikerult az utvonalat megtalalni
	    	throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_DRIVER_PATH, element.getAttribute(ATTR_DRIVER_PATH), e );
	    }
				
		NodeList nodelist = element.getChildNodes();
		for( int i = 0; i < nodelist.getLength(); i++ ){
			Node node = nodelist.item( i );
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element testcaseElement = (Element)node;
				
				//Ha TESTCASEPARAM van alatta
				if( testcaseElement.getTagName().equals( Tag.TESTCASEPARAMPAGE.getName() )){
					
					this.add(new TestcaseParamPageDataModel(testcaseElement, paramDataModel ));
				
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
	public void add(TestcaseDataModelAdapter node) {
		super.add( (MutableTreeNode)node );
	}
	
	public static String  getModelNameToShowStatic(){
		return CommonOperations.getTranslation( "tree.nodetype.testcase.case");
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
	
	@Override
	public Element getXMLElement(Document document) {
		Attr attr;

		//========
		//
		//Node element
		//
		//========
		Element nodeElement = document.createElement( TestcaseCaseDataModel.this.getTag().getName() );
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
		// Driver
		//
		//========
		attr = document.createAttribute( ATTR_DRIVER_PATH );
		attr.setValue( driver.getPathTag() );
		nodeElement.setAttributeNode(attr);	
	
		int childrens = this.getChildCount();
		for( int i = 0; i < childrens; i++ ){
			
			Object object = this.getChildAt( i );
			
			if( !object.equals(this) && object instanceof TestcaseDataModelAdapter ){
				
				Element element = ((TestcaseDataModelAdapter)object).getXMLElement( document );
				nodeElement.appendChild( element );		    		
		    	
			}
		}
	
		return nodeElement;		
	}

	public void setDriverDataModel( DriverBrowserDataModelInterface<?> driver ){
		this.driver = driver;
	}
	
	public DriverBrowserDataModelInterface<?> getDriverDataModel(){
		return driver;
	}
	
	@Override
	public Object clone(){
		
		//Leklonozza a NODE-ot
		TestcaseCaseDataModel cloned = (TestcaseCaseDataModel)super.clone();
	
		//Ha vannak gyerekei (PAGE)
		if( null != this.children ){
			
			//Akkor azokat is leklonozza
			cloned.children = new Vector<>();
			
			for( Object o : this.children ){
				
				if( o instanceof TestcaseDataModelAdapter ){					
					
					TestcaseDataModelAdapter child = (TestcaseDataModelAdapter) ((TestcaseDataModelAdapter)o).clone();
					
					//Szulo megadasa, mert hogy nem lett hozzaadva direkt modon a Tree-hez
					child.setParent( cloned );					
					
					cloned.children.add(child);
					
				}
			}
		}
		
		//Es a valtozokat is leklonozza
		cloned.name = new String( this.name );
		cloned.details = new String( this.details );
		
		return cloned;
		
	}
	
	@Override
	public Object cloneWithParent() {
		
		TestcaseCaseDataModel cloned = (TestcaseCaseDataModel) this.clone();
		
		//Le kell masolni a felmenoit is, egyebkent azok automatikusan null-ok
		cloned.setParent( (MutableTreeNode) this.getParent() );
		
		return cloned;
	}
}
