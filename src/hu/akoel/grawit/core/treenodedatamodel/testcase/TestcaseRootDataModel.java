package hu.akoel.grawit.core.treenodedatamodel.testcase;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.DriverDataModelInterface;
import hu.akoel.grawit.core.treenodedatamodel.TestcaseDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.driver.DriverBrowserDataModelInterface;
import hu.akoel.grawit.core.treenodedatamodel.driver.DriverExplorerDataModel;
import hu.akoel.grawit.core.treenodedatamodel.driver.DriverFirefoxDataModel;
import hu.akoel.grawit.core.treenodedatamodel.driver.DriverFirefoxPropertyDataModel;
import hu.akoel.grawit.core.treenodedatamodel.driver.DriverNodeDataModel;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.variable.VariableRootDataModel;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.exceptions.XMLBaseConversionPharseException;
import hu.akoel.grawit.exceptions.XMLExtraRootTagPharseException;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;
import hu.akoel.grawit.exceptions.XMLPharseException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;



public class TestcaseRootDataModel extends TestcaseNodeDataModel{

	private static final long serialVersionUID = 5361088361756620748L;

	private static final Tag TAG = Tag.TESTCASEROOT;
	
	public static final String ATTR_NAME = "";
	public static final String ATTR_DRIVER_PATH = "driverpath";
	
	private DriverBrowserDataModelInterface<?> driver;
	
	public TestcaseRootDataModel(){
		super( "", "" );
		this.driver = null;
	}
	
	public TestcaseRootDataModel( DriverBrowserDataModelInterface<?> driver ){
		super( "", "" );
		this.driver = driver;
	}
	
	public TestcaseRootDataModel( Document doc, VariableRootDataModel variableRootDataModel, BaseRootDataModel baseRootDataModel, ParamRootDataModel paramRootDataModel, DriverDataModelInterface driverDataModel ) throws XMLPharseException{		
		super("","");
		
		NodeList nList = doc.getElementsByTagName( TAG.getName() );
		
		//Ha tobb mint  1 db basepage tag van, akkor az gaz
		if( nList.getLength() > 1 ){
			
			throw new XMLExtraRootTagPharseException( TAG );
			
		}else if( nList.getLength() == 1 ){
		
			//Clonozom, hogy az eredeti ROOT megmaradjon, amit atadok parameterkent a TestcaseNodeDataModel() Objektumnak
			DriverDataModelInterface dDataModel = (DriverDataModelInterface) driverDataModel.clone();
			
			Node testcaseRootNode = nList.item(0);
			if (testcaseRootNode.getNodeType() == Node.ELEMENT_NODE) {
			
				Element testcaseRootElement = (Element)testcaseRootNode;
				
				//Ha a root az
				if( testcaseRootElement.getTagName().equals( Tag.TESTCASEROOT.getName() ) ){
				
					//========
					//
					// Driver
					//
				    //========
					DocumentBuilderFactory factory = null;  
					DocumentBuilder builder = null;
					Document document = null;
					Node actualNode = null;
		
					
/*NamedNodeMap attributes = (NamedNodeMap) testcaseRootElement.getAttributes();	
int numAttrs = attributes.getLength();
for (int i = 0; i < numAttrs; i++) {
	Attr attr = (Attr) attributes.item(i);
	String attrName = attr.getNodeName();
	String attrValue = attr.getNodeValue();	
	System.out.println("Found attribute: " + attrName + " with value: " + attrValue);
}
System.out.println();
*/
					
//TODO ezt majd torolni kell, ha mar nem letezik olyan xml amiben nics driver path					
if( testcaseRootElement.hasAttribute( ATTR_DRIVER_PATH ) ){

					if( !testcaseRootElement.hasAttribute( ATTR_DRIVER_PATH ) ){
						throw new XMLMissingAttributePharseException( getRootTag(), getTag(), ATTR_NAME, getName(), ATTR_DRIVER_PATH );			
					}	
					String driverPathString = testcaseRootElement.getAttribute(ATTR_DRIVER_PATH );				
					driverPathString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + driverPathString;  
					factory = DocumentBuilderFactory.newInstance();  
				    try  
				    {  
				        builder = factory.newDocumentBuilder();  
				        document = builder.parse( new InputSource( new StringReader( driverPathString ) ) );  
				    } catch (Exception e) {  
				    
				    	//Nem sikerult az atalakitas
				    	throw new XMLBaseConversionPharseException( getRootTag(), getTag(), ATTR_NAME, getName(), ATTR_DRIVER_PATH, testcaseRootElement.getAttribute(ATTR_DRIVER_PATH ), e );
				    } 	    
				    
				    //Megkeresem a DRIVERROOT-ben az DRIVER-hez vezeto utat
				    //Node 
				    actualNode = document;
				    while( actualNode.hasChildNodes() ){
					
				    	actualNode = actualNode.getFirstChild();
				    	Element actualElement = (Element)actualNode;
				    	String tagName = actualElement.getTagName();
				    	String attrName = null;
				    	
				    	if( tagName.equals( DriverNodeDataModel.TAG.getName() ) ){
				    		attrName = actualElement.getAttribute(DriverNodeDataModel.ATTR_NAME);	    		
				    		dDataModel = (DriverDataModelInterface) CommonOperations.getDataModelByNameInLevel( dDataModel, Tag.DRIVERNODE, attrName );

				    		if( null == dDataModel ){

				    			throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_DRIVER_PATH, testcaseRootElement.getAttribute(ATTR_DRIVER_PATH ) );
				    		}
				    		
				    	//Ha DRIVERFIREFOX
				    	}else if( tagName.equals( DriverFirefoxDataModel.TAG.getName() ) ){
				    		attrName = actualElement.getAttribute(DriverFirefoxDataModel.ATTR_NAME);
				    		dDataModel = (DriverDataModelInterface) CommonOperations.getDataModelByNameInLevel( dDataModel, Tag.DRIVERFIREFOX, attrName );
				    		
				    		if( null == dDataModel ){
				    		
				    			throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_DRIVER_PATH, testcaseRootElement.getAttribute(ATTR_DRIVER_PATH) );
				    		}
				    		
				    	//Ha DRIVERFIREFOXPROPERY
				    	}else if( tagName.equals( DriverFirefoxPropertyDataModel.TAG.getName() ) ){
				    		attrName = actualElement.getAttribute(DriverFirefoxPropertyDataModel.ATTR_NAME);
				    		dDataModel = (DriverDataModelInterface) CommonOperations.getDataModelByNameInLevel( dDataModel, Tag.DRIVERFIREFOXPROPERTY, attrName );
				    		
				    		if( null == dDataModel ){
				    			
				    			throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_DRIVER_PATH, testcaseRootElement.getAttribute(ATTR_DRIVER_PATH) );
				    		}
				    	
				    	//Ha EXPLORER
				    	}else if( tagName.equals( DriverExplorerDataModel.TAG.getName() ) ){
				    		attrName = actualElement.getAttribute(DriverExplorerDataModel.ATTR_NAME);
				    		dDataModel = (DriverDataModelInterface) CommonOperations.getDataModelByNameInLevel( dDataModel, Tag.DRIVEREXPLORER, attrName );
					    		
				    		if( null == dDataModel ){
					    		
				    			throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_DRIVER_PATH, testcaseRootElement.getAttribute(ATTR_DRIVER_PATH) );
				    		}				    		
//TODO hianyzik innen az Explorer Capability feldolgozasa				    	
				    	}else{
				    		
				    		throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_DRIVER_PATH, testcaseRootElement.getAttribute(ATTR_DRIVER_PATH) );	    		
				    	}
				    }	    
				    try{
				    	
				    	driver = (DriverBrowserDataModelInterface<?>)dDataModel;
				    	
				    }catch(ClassCastException e){

				    	//Nem sikerult az utvonalat megtalalni
				    	throw new XMLBaseConversionPharseException( getRootTag(), getTag(), ATTR_NAME, getName(), ATTR_DRIVER_PATH, testcaseRootElement.getAttribute(ATTR_DRIVER_PATH), e );
				    }
}else{
	driver = null;
}
				}
				
				NodeList nodeList = testcaseRootNode.getChildNodes();
				for( int i = 0; i < nodeList.getLength(); i++ ){
			
					Node testcaseNode = nodeList.item( i );
				
					if (testcaseNode.getNodeType() == Node.ELEMENT_NODE) {
						Element testcaseElement = (Element)testcaseNode;
														
						//Ha ujabb TESTCASENODE van alatta
						if( testcaseElement.getTagName().equals( Tag.TESTCASENODE.getName() ) ){
							this.add(new TestcaseNodeDataModel(testcaseElement, variableRootDataModel, baseRootDataModel, paramRootDataModel, driverDataModel ));
						}
					}
				}
			}
		}
	}
	
	public static Tag getTagStatic(){
		return TAG;
	}

	@Override
	public Tag getTag() {
		//return TAG;
		return getTagStatic();
	}

	@Override
	public String getName(){
		//return "/";
		return "Test Case Root";
	}
	
	@Override
	public String getNodeTypeToShow(){
		return CommonOperations.getTranslation( "tree.nodetype.testcase.root");
	}
	
	public void setDriverDataModel( DriverBrowserDataModelInterface<?> driver ){
		this.driver = driver;
	}
	
	public DriverBrowserDataModelInterface<?> getDriverDataModel(){
		return driver;
	}
	
	@Override
	public Element getXMLElement(Document document) {
	
		Attr attr;
		
		//TestcaseElement
		Element testcaseElement = document.createElement( TAG.getName() );

		//========
		//
		// DETAILS
		//
		//========
		attr = document.createAttribute( ATTR_DETAILS );
		attr.setValue( getDetails() );
		testcaseElement.setAttributeNode(attr);	
		
		//========
		//
		// Driver
		//
		//========
		attr = document.createAttribute( ATTR_DRIVER_PATH );
		if( null != driver ){
			attr.setValue( driver.getPathTag() );
			testcaseElement.setAttributeNode(attr);
		}
		
		int childrens = this.getChildCount();
		for( int i = 0; i < childrens; i++ ){
			
			Object object = this.getChildAt( i );
			
			if( !object.equals(this) && object instanceof TestcaseDataModelAdapter ){
				
				Element element = ((TestcaseDataModelAdapter)object).getXMLElement( document );
				testcaseElement.appendChild( element );		    		
		    	
			}
		}

		return testcaseElement;		
	}
	
	@Override
	public Object clone(){
		
		//Leklonozza a NODE-ot
		TestcaseRootDataModel cloned = (TestcaseRootDataModel)super.clone();
	
		//Es a valtozokat is leklonozza
		if( driver instanceof DriverFirefoxDataModel ){
			cloned.driver = (DriverBrowserDataModelInterface<?>) ((DriverBrowserDataModelInterface<?>)driver).cloneWithParent();
		}else if( driver instanceof DriverExplorerDataModel ){
			cloned.driver = (DriverBrowserDataModelInterface<?>) ((DriverBrowserDataModelInterface<?>)driver).cloneWithParent();
		}
		
		return cloned;
		
	}
}
