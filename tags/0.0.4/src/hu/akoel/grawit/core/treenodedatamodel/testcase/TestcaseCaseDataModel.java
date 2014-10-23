package hu.akoel.grawit.core.treenodedatamodel.testcase;

import java.io.StringReader;
import java.util.Vector;

import javax.swing.tree.MutableTreeNode;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.DriverDataModelInterface;
import hu.akoel.grawit.core.treenodedatamodel.ParamDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.SpecialDataModelInterface;
import hu.akoel.grawit.core.treenodedatamodel.TestcaseDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.driver.DriverBrowserDataModelInterface;
import hu.akoel.grawit.core.treenodedatamodel.driver.DriverFirefoxDataModel;
import hu.akoel.grawit.core.treenodedatamodel.driver.DriverFirefoxPropertyDataModel;
import hu.akoel.grawit.core.treenodedatamodel.driver.DriverNodeDataModel;
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

public class TestcaseCaseDataModel extends TestcaseDataModelAdapter{

	private static final long serialVersionUID = -2139557326147525999L;

	public static final Tag TAG = Tag.TESTCASECASE;
	
	public static final String ATTR_DETAILS = "details";
	public static final String ATTR_OPEN_PAGE_PATH = "openpagepath";
	public static final String ATTR_CLOSE_PAGE_PATH = "closepagepath";
	public static final String ATTR_DRIVER_PATH = "driverpath";
	private static final String ATTR_ON = "on";
	
	private String name;
	private String details;
	private SpecialOpenDataModel openPage;
	private SpecialCloseDataModel closePage;
	private DriverBrowserDataModelInterface<?> driver;
	
	public TestcaseCaseDataModel( String name, String details, SpecialOpenDataModel openPage, SpecialCloseDataModel closePage, DriverBrowserDataModelInterface<?> driver ){
		super( );
		this.name = name;
		this.details = details;
		this.openPage = openPage;
		this.closePage = closePage;
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
	public TestcaseCaseDataModel( Element element, SpecialDataModelInterface specialDataModel, ParamDataModelAdapter paramDataModel, DriverDataModelInterface driverDataModel ) throws XMLPharseException{
		
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
		
		SpecialDataModelInterface specialDataModelForOpen = (SpecialDataModelInterface)specialDataModel.clone();
		SpecialDataModelInterface specialDataModelForClose = (SpecialDataModelInterface)specialDataModel.clone();
		//DriverDataModelInterface driverDataModel = (DriverDataModelInterface)driverDataModel.clone();
		
		//========
		//
		// ClosePage
		//
		//========
		if( !element.hasAttribute( ATTR_CLOSE_PAGE_PATH ) ){
			throw new XMLMissingAttributePharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_CLOSE_PAGE_PATH );			
		}	
		String closeElementPathString = element.getAttribute(ATTR_CLOSE_PAGE_PATH);		
		//Ha ures az oldal lezaras
		if( closeElementPathString.trim().length() == 0 ){
			closePage = null;
		
		//Ha van oldallezaras
		}else{
			closeElementPathString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + closeElementPathString;  
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
			DocumentBuilder builder;
			Document document = null;
			try {  
				builder = factory.newDocumentBuilder();  
				document = builder.parse( new InputSource( new StringReader( closeElementPathString ) ) );  
			} catch (Exception e) {  
	    
				//Nem sikerult az atalakitas
				throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_CLOSE_PAGE_PATH, element.getAttribute(ATTR_CLOSE_PAGE_PATH ), e );
			} 	    
	    
			//Megkeresem a SPECIALROOT-ben a CLOSE-hoz vezeto utat
			Node actualNode = document;
			while( actualNode.hasChildNodes() ){
		
				actualNode = actualNode.getFirstChild();
				Element actualElement = (Element)actualNode;
				String tagName = actualElement.getTagName();
				String attrName = null;
	    	
				//Ha SPECIALNODE
				if( tagName.equals( SpecialNodeDataModel.TAG.getName() ) ){
					attrName = actualElement.getAttribute(SpecialNodeDataModel.ATTR_NAME);	    		
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
		}
		
		//========
		//
		// OpenPage
		//
		//========
		if( !element.hasAttribute( ATTR_OPEN_PAGE_PATH ) ){
			throw new XMLMissingAttributePharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_OPEN_PAGE_PATH );			
		}	
		String openElementPathString = element.getAttribute(ATTR_OPEN_PAGE_PATH);				
		openElementPathString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + openElementPathString;  
		DocumentBuilderFactory  factory = DocumentBuilderFactory.newInstance();  
		DocumentBuilder builder;
	    Document document = null;
	    try{  
	        builder = factory.newDocumentBuilder();  
	        document = builder.parse( new InputSource( new StringReader( openElementPathString ) ) );  
	    } catch (Exception e) {  
	    
	    	//Nem sikerult az atalakitas
	    	throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_OPEN_PAGE_PATH, element.getAttribute(ATTR_OPEN_PAGE_PATH ), e );
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
	    		attrName = actualElement.getAttribute(SpecialNodeDataModel.ATTR_NAME);	    		
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
	    		
	    		if( null == specialDataModelForOpen ){
	    			
	    			throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_DRIVER_PATH, element.getAttribute(ATTR_DRIVER_PATH) );
	    		}
	    		
	    	//}else{
	    		
	    	//	throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_OPEN_PAGE_PATH, element.getAttribute(ATTR_OPEN_PAGE_PATH) );	    		
	    	}
	    }	    
	    try{
	    	
	    	driver = (DriverBrowserDataModelInterface)driverDataModel;
	    	
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
				
				//Ha TESTCASECUSTOM van alatta
				if( testcaseElement.getTagName().equals( Tag.TESTCASECUSTOMPAGE.getName() )){
					
					this.add(new TestcaseCustomDataModel(testcaseElement, specialDataModel ));
				
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

		//===============
		//
		// Open page path
		//
		//===============
		attr = document.createAttribute( ATTR_OPEN_PAGE_PATH );
		attr.setValue( openPage.getPathTag() );
		nodeElement.setAttributeNode(attr);	

		//===============
		//
		// Close page path
		//
		//===============
		attr = document.createAttribute( ATTR_CLOSE_PAGE_PATH );
		if( null == closePage ){
			attr.setValue( "" );
		}else{
			attr.setValue( closePage.getPathTag() );
		}
		nodeElement.setAttributeNode(attr);	
		
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
}
