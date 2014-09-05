package hu.akoel.grawit.core.treenodedatamodel.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.DriverDataModelInterface;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.exceptions.XMLCastPharseException;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;
import hu.akoel.grawit.exceptions.XMLMissingTagPharseException;

public class DriverExplorerDataModel extends DriverDataModelInterface{

	private static final long serialVersionUID = 598870035128239461L;
	
	public static Tag TAG = Tag.DRIVEREXPLORER;

	public static final String ATTR_DETAILS = "details";
	
	private String name;
	private String details;
	
	public DriverExplorerDataModel( String name, String details ){
		super( );
		this.name = name;
		this.details = details;
	}
	
	public DriverExplorerDataModel( Element element ) throws XMLMissingAttributePharseException, XMLMissingTagPharseException, XMLCastPharseException{
		
		//tag
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
			throw new XMLMissingAttributePharseException( DriverNodeDataModel.getRootTag(), Tag.DRIVERNODE, ATTR_NAME, getName(), ATTR_DETAILS );			
		}		
		this.details = element.getAttribute( ATTR_DETAILS );	
		
	}
	
	public static String getModelNameToShowStatic(){
		return CommonOperations.getTranslation( "tree.nodetype.driver.explorer" );
	}
	
	@Override
	public String getModelNameToShow(){
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
	
	@Override
	public Element getXMLElement(Document document) {
		Attr attr;
		
		//
		//Node element
		//
		Element elementElement = document.createElement( TAG.getName() );
		
		//Name
		attr = document.createAttribute( ATTR_NAME );
		attr.setValue( getName() );
		elementElement.setAttributeNode(attr);	
		
		//Details
		attr = document.createAttribute( ATTR_DETAILS );
		attr.setValue( getDetails() );
		elementElement.setAttributeNode(attr);	
				
		return elementElement;	
	}

	@Override
	public void add( DriverDataModelInterface node ) {
	}

	@Override
	public WebDriver getDriver() {
		
		DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
		
//TODO ide jonnek a capabilities-ek		
//		capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);

		
		return new InternetExplorerDriver(capabilities);
	}

}
