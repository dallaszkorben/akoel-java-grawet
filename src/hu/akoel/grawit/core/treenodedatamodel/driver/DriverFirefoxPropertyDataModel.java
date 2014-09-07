package hu.akoel.grawit.core.treenodedatamodel.driver;

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
	
	public static final String ATTR_VALUE = "value";
	public static final String ATTR_TYPE = "type";
	
	private String key;
	private Object value;
	
	public DriverFirefoxPropertyDataModel( String name, Object value ){
		super( );
		this.key = name;
		this.value = value;
	}
	
	public DriverFirefoxPropertyDataModel( Element element ) throws XMLMissingAttributePharseException, XMLMissingTagPharseException, XMLCastPharseException{
		
		//Tag
		if( !element.getTagName().equals( TAG.getName() ) ){
			Element parentElement = (Element)element.getParentNode();
			throw new XMLMissingTagPharseException( getRootTag().getName(), parentElement.getTagName(), parentElement.getAttribute( ATTR_NAME ), TAG.getName() );
		}	
		
		//Key
		if( !element.hasAttribute( ATTR_NAME ) ){
			throw new XMLMissingAttributePharseException( getRootTag(), TAG, ATTR_NAME );			
		}
		this.key = element.getAttribute( ATTR_NAME );		
		
		//Type
		if( !element.hasAttribute( ATTR_TYPE ) ){
			throw new XMLMissingAttributePharseException( getRootTag(), TAG, ATTR_TYPE );			
		}
		String stringType = element.getAttribute( ATTR_TYPE );
		
		//Value
		if( !element.hasAttribute( ATTR_VALUE ) ){
			throw new XMLMissingAttributePharseException( DriverNodeDataModel.getRootTag(), Tag.DRIVERFIREFOXPROPERTY, ATTR_NAME, getName(), ATTR_VALUE );			
		}		
		String stringValue = element.getAttribute( ATTR_VALUE );	
		//String
		if( stringType.equals( String.class.getSimpleName() ) ){
			this.value = stringValue;
		//Boolean
		}else if( stringType.equals( Boolean.class.getSimpleName() ) ){
			this.value = new Boolean( stringValue );
		}
		
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
		return key;
	}
	
	public void setName( String name ){
		this.key = name;
	}
	
/*
	public String getKey(){
		return key;
	}
	
	public void setKey( String key ){
		this.key = key;
	}
*/
	public Object getValue(){
		return value;
	}
	
	public void setValue( Object value ){
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
		attr = document.createAttribute( ATTR_NAME );
		attr.setValue( getName() );
		elementElement.setAttributeNode(attr);	
		
		//Value
		attr = document.createAttribute( ATTR_VALUE );
		attr.setValue( getValue().toString() );
		elementElement.setAttributeNode(attr);	
		
		//Type
		attr = document.createAttribute( ATTR_TYPE );
		attr.setValue( getValue().getClass().getSimpleName() );
		elementElement.setAttributeNode(attr);						
		
		return elementElement;	
	}

	@Override
	public void add( DriverDataModelInterface node ) {
	}

	@Override
	public Object clone(){
		
		DriverFirefoxPropertyDataModel cloned = (DriverFirefoxPropertyDataModel)super.clone();
	
		return cloned;
		
	}
}
