package hu.akoel.grawit.core.treenodedatamodel.base;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.BaseDataModelInterface;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;
import hu.akoel.grawit.exceptions.XMLPharseException;

public class BaseOpenDataModel extends BaseDataModelInterface{

	private static final long serialVersionUID = 4021264120677994929L;

	public static Tag TAG = Tag.BASEOPEN;
	
	public static final String ATTR_URL = "url";
	
	private String name;
	private String url;

	public BaseOpenDataModel(String name, String url ){
		common( name, url );	
	}

	public BaseOpenDataModel( BaseOpenDataModel element ){
		this.name = element.getName();
		this.url = element.getURL();

	}

	/**
	 * XML alapjan gyartja le a BASEOPEN-t
	 * 
	 * @param element
	 * @throws XMLPharseException 
	 */
	public BaseOpenDataModel( Element element ) throws XMLPharseException{
		
		//name
		if( !element.hasAttribute( ATTR_NAME ) ){
			throw new XMLMissingAttributePharseException( getRootTag(), TAG, ATTR_NAME );			
		}
		String nameString = element.getAttribute( ATTR_NAME );		
		this.name = nameString;
		
		//frame             
		if( !element.hasAttribute( ATTR_URL ) ){
			throw new XMLMissingAttributePharseException( getRootTag(), TAG, ATTR_URL, getName(), ATTR_URL );			
		}
		String url = element.getAttribute( ATTR_URL );
		this.url = url;
		
	}
	
	private void common( String name, String url ){		
		this.name = name;
		this.url = url;
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

	public void setName(String name) {
		this.name = name;
	}

	public String getURL() {
		return url;
	}

	public void setURL(String url) {
		this.url = url;
	}
	
	@Override
	public void add(BaseDataModelInterface node) {
	}
	
	public static String  getModelNameToShowStatic(){
		return CommonOperations.getTranslation( "tree.nodetype.baseopen");
	}
	
	@Override
	public String getModelNameToShow(){
		return getModelNameToShowStatic();
	}
	
	@Override
	public Element getXMLElement(Document document) {
		Attr attr;

		//Node element
		Element elementElement = document.createElement( BaseOpenDataModel.this.getTag().getName() );
		attr = document.createAttribute( ATTR_NAME );
		attr.setValue( getName() );
		elementElement.setAttributeNode(attr);	
		
		attr = document.createAttribute( ATTR_URL);
		attr.setValue( getURL() );
		elementElement.setAttributeNode(attr);	

		return elementElement;	
	}


}
