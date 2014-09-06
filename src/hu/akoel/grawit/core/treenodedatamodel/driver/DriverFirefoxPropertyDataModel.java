package hu.akoel.grawit.core.treenodedatamodel.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.DriverDataModelInterface;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.exceptions.XMLCastPharseException;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;
import hu.akoel.grawit.exceptions.XMLMissingTagPharseException;

public class DriverFirefoxPropertyDataModel extends DriverDataModelInterface{

	private static final long serialVersionUID = 2921936584954476404L;

	public static Tag TAG = Tag.DRIVERFIREFOXPROPERTY;
	
	public static final String ATTR_KEY = "key";
	public static final String ATTR_VALUE = "value";
	
	private String key;
	private String value;
	
	public DriverFirefoxPropertyDataModel( String key, String value ){
		super( );
		this.key = key;
		this.value = value;
	}
	
	public DriverFirefoxPropertyDataModel( Element element ) throws XMLMissingAttributePharseException, XMLMissingTagPharseException, XMLCastPharseException{
		
		//tag
		if( !element.getTagName().equals( TAG.getName() ) ){
			Element parentElement = (Element)element.getParentNode();
			throw new XMLMissingTagPharseException( getRootTag().getName(), parentElement.getTagName(), parentElement.getAttribute( ATTR_KEY ), TAG.getName() );
		}	
		
		//Key
		if( !element.hasAttribute( ATTR_KEY ) ){
			throw new XMLMissingAttributePharseException( getRootTag(), TAG, ATTR_KEY );			
		}
		this.key = element.getAttribute( ATTR_KEY );		
		
		//Value
		if( !element.hasAttribute( ATTR_VALUE ) ){
			throw new XMLMissingAttributePharseException( DriverNodeDataModel.getRootTag(), Tag.DRIVERFIREFOXPROPERTY, ATTR_NAME, getName(), ATTR_VALUE );			
		}		
		this.value = element.getAttribute( ATTR_VALUE );		

	}
	
	public static String getModelNameToShowStatic(){
		return CommonOperations.getTranslation( "tree.nodetype.driver.firefox.property" );
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
		return getKey();
	}

	public String getKey(){
		return key;
	}
	
	public void setKey( String key ){
		this.key = key;
	}

	public String getValue(){
		return value;
	}
	
	public void setValue( String value ){
		this.value = value;
	}

	@Override
	public Element getXMLElement(Document document) {
		Attr attr;
		
		//
		//Node element
		//
		Element elementElement = document.createElement( TAG.getName() );
		
		//Key
		attr = document.createAttribute( ATTR_KEY );
		attr.setValue( getName() );
		elementElement.setAttributeNode(attr);	
		
		//Value
		attr = document.createAttribute( ATTR_VALUE );
		attr.setValue( getValue() );
		elementElement.setAttributeNode(attr);	
		
		return elementElement;	
	}

	@Override
	public void add( DriverDataModelInterface node ) {
	}

	@Override
	public WebDriver getDriver() {
		FirefoxProfile profile = new FirefoxProfile();

//TODO ide jonnek a profilok		
		
		return new FirefoxDriver(profile);
	}

}
