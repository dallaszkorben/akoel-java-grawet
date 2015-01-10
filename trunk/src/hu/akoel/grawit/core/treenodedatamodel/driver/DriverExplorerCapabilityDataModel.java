package hu.akoel.grawit.core.treenodedatamodel.driver;

import javax.swing.tree.MutableTreeNode;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.DriverDataModelAdapter;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.exceptions.XMLCastPharseException;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;
import hu.akoel.grawit.exceptions.XMLMissingTagPharseException;

public class DriverExplorerCapabilityDataModel extends DriverDataModelAdapter{

	private static final long serialVersionUID = 2921936584954476404L;

	public static Tag TAG = Tag.DRIVEREXPLORERCAPABILITY;
	
	public static final String ATTR_VALUE = "value";
	public static final String ATTR_TYPE = "type";
	public static final String ATTR_DETAILS = "details";
	
	private String key;
	private Object value;
	private String details;
	
	public DriverExplorerCapabilityDataModel( String name, Object value, String details ){
		super( );
		this.key = name;
		this.value = value;
		this.details = details;
	}
	
	public DriverExplorerCapabilityDataModel( Element element ) throws XMLMissingAttributePharseException, XMLMissingTagPharseException, XMLCastPharseException{
		
		//tag
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
		//Integer
		}else if( stringType.equals( Integer.class.getSimpleName() ) ){
			this.value = new Integer( stringValue );
		}	
		
		//Details
		if( !element.hasAttribute( ATTR_DETAILS ) ){
			throw new XMLMissingAttributePharseException( getRootTag(), TAG, ATTR_DETAILS );			
		}
		this.details = element.getAttribute( ATTR_DETAILS );

	}
	
	public static String getModelNameToShowStatic(){
		return CommonOperations.getTranslation( "tree.nodetype.driver.firefox.property" );
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
		return key;
	}

	public void setName( String name ){
		this.key = name;
	}

	public Object getValue(){
		return value;
	}
	
	public void setValue( Object value ){
		this.value = value;
	}

	public void setDetails( String details ){
		this.details = details;
	}
	
	public String getDetails(){
		return this.details;
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
		
		//Details
		attr = document.createAttribute( ATTR_DETAILS );
		attr.setValue( getDetails() );
		elementElement.setAttributeNode(attr);	
		
		return elementElement;	
	}

	@Override
	public void add( DriverDataModelAdapter node ) {
	}

	@Override
	public Object clone(){
		
		DriverExplorerCapabilityDataModel cloned = (DriverExplorerCapabilityDataModel)super.clone();
	
		return cloned;
		
	}

	@Override
	public Object cloneWithParent() {
		
		DriverExplorerCapabilityDataModel cloned = (DriverExplorerCapabilityDataModel) this.clone();
		
		//Le kell masolni a felmenoit is, egyebkent azok automatikusan null-ok
		cloned.setParent( (MutableTreeNode) this.getParent() );
		
		return cloned;
	}
}
