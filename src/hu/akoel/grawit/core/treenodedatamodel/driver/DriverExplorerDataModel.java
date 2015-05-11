package hu.akoel.grawit.core.treenodedatamodel.driver;

import java.io.File;
import java.util.Vector;

import javax.swing.tree.MutableTreeNode;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.exceptions.XMLCastPharseException;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;
import hu.akoel.grawit.exceptions.XMLMissingTagPharseException;
import hu.akoel.grawit.gui.interfaces.progress.ProgressIndicatorInterface;

public class DriverExplorerDataModel extends DriverBrowserDataModelInterface<DriverExplorerCapabilityDataModel>{

	private static final long serialVersionUID = 598870035128239461L;
	
	public static Tag TAG = Tag.DRIVEREXPLORER;

	public static final String ATTR_DETAILS = "details";
	public static final String ATTR_WEBDRIVERPATH = "webdriverpath";
	
	private String name;
	private String details;
	private File webDriverPath;
	
	public DriverExplorerDataModel( String name, String details, File webDriverPath ){
		super( );
		this.name = name;
		this.details = details;
		this.webDriverPath = webDriverPath;
	}
	
	public DriverExplorerDataModel( Element element ) throws XMLMissingAttributePharseException, XMLMissingTagPharseException, XMLCastPharseException{
		
		//Tag
		if( !element.getTagName().equals( TAG.getName() ) ){
			Element parentElement = (Element)element.getParentNode();
			throw new XMLMissingTagPharseException( getRootTag().getName(), parentElement.getTagName(), parentElement.getAttribute( ATTR_NAME ), TAG.getName() );
		}		
		
		//Name
		if( !element.hasAttribute( ATTR_NAME ) ){
			throw new XMLMissingAttributePharseException( getRootTag(), TAG, ATTR_NAME );			
		}
		this.name = element.getAttribute( ATTR_NAME );		
		
		//Details
		if( !element.hasAttribute( ATTR_DETAILS ) ){
			throw new XMLMissingAttributePharseException( DriverFolderDataModel.getRootTag(), Tag.DRIVEREXPLORER, ATTR_NAME, getName(), ATTR_DETAILS );			
		}		
		this.details = element.getAttribute( ATTR_DETAILS );	
		
		//WebDriver file
		if( !element.hasAttribute( ATTR_WEBDRIVERPATH ) ){
			throw new XMLMissingAttributePharseException( DriverFolderDataModel.getRootTag(), Tag.DRIVEREXPLORER, ATTR_NAME, getName(), ATTR_WEBDRIVERPATH );			
		}
				
		String stringFile = element.getAttribute( ATTR_WEBDRIVERPATH );		
		this.webDriverPath = new File( stringFile );
		
		NodeList nodelist = element.getChildNodes();
		for( int i = 0; i < nodelist.getLength(); i++ ){
			Node node = nodelist.item( i );
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element driverElement = (Element)node;
				
				//Ha DRIVEREXPLORERPCAPABILITY van alatta
				if( driverElement.getTagName().equals( Tag.DRIVEREXPLORERCAPABILITY.getName() )){

					this.add(new DriverExplorerCapabilityDataModel(driverElement));

				}
			}
		}
	}
	
	public static String getModelNameToShowStatic(){
		return CommonOperations.getTranslation( "tree.nodetype.driver.explorer" );
	}
	
	@Override
	public String getNodeTypeToShow(){
		return getModelNameToShowStatic();
	}

	public static Tag getTagStatic(){
		return TAG;
	}
	
	@Override
	public Tag getTag() {
		return getTagStatic();
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName( String name ){
		this.name = name;
	}
	
	public String getDetails(){
		return details;
	}
	
	public void setDetails( String details ){
		this.details = details;
	}
	
	public File getWebDriverPath(){
		return this.webDriverPath;
	}
	
	public void setWebDriverFile( File webDriverFile ){
		this.webDriverPath = webDriverFile;
	}
	
	@Override
	public void add(DriverExplorerCapabilityDataModel node) {
		super.add( (MutableTreeNode)node );
	}
	
	@Override
	public Element getXMLElement(Document document) {
		Attr attr;
		
		//
		//Node element
		//
		Element elementExplorer = document.createElement( TAG.getName() );
		
		//Name
		attr = document.createAttribute( ATTR_NAME );
		attr.setValue( getName() );
		elementExplorer.setAttributeNode(attr);	
		
		//Details
		attr = document.createAttribute( ATTR_DETAILS );
		attr.setValue( getDetails() );
		elementExplorer.setAttributeNode(attr);	
		
		//Webdriver path
		attr = document.createAttribute( ATTR_WEBDRIVERPATH );
		attr.setValue( getWebDriverPath().getAbsolutePath() );
		elementExplorer.setAttributeNode(attr);	
		
		int childrens = this.getChildCount();
		for( int i = 0; i < childrens; i++ ){
			
			Object object = this.getChildAt( i );
			
			if( !object.equals(this) && object instanceof DriverDataModelAdapter ){
				
				Element elementCapability = ((DriverDataModelAdapter)object).getXMLElement( document );
				elementExplorer.appendChild( elementCapability );		    		
		    	
			}
		}
		
		return elementExplorer;	
	}



	@Override
	public WebDriver getDriver(  ProgressIndicatorInterface elementProgres, String tab ) {
		
		elementProgres.printSource( tab + "DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();");	
		elementProgres.printSource( tab + "capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);");
		
		DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
		capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);

		int childCount = getChildCount();
		for( int index = 0; index < childCount; index++ ){
			String key = ((DriverExplorerCapabilityDataModel)getChildAt(index)).getName();
			Object value = ((DriverExplorerCapabilityDataModel)getChildAt(index)).getValue();
			
			elementProgres.printSource(  tab + "capabilities.setCapability( \"" + key + "\", \"" + String.valueOf( value ) + "\" );");
			
			capabilities.setCapability(key, value);
		}
		
		elementProgres.printSource(  tab + "driver = new InternetExplorerDriver(capabilities);");
		elementProgres.printSource(  "" );
		
		return new InternetExplorerDriver(capabilities);
	}

	@Override
	public Object clone(){
		
		DriverExplorerDataModel cloned = (DriverExplorerDataModel)super.clone();
	
		if( null != this.children ){
			cloned.children = (Vector<?>) this.children.clone();
		}
		
		//Le kell masolni a felmenoit is, egyebkent azok automatikusan null-ok
		cloned.setParent( (MutableTreeNode) this.getParent() );
		
		return cloned;
		
	}
	
}
