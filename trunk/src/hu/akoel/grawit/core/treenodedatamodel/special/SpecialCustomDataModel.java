package hu.akoel.grawit.core.treenodedatamodel.special;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.SpecialDataModelInterface;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;
import hu.akoel.grawit.exceptions.XMLPharseException;

public class SpecialCustomDataModel extends SpecialDataModelInterface{

	private static final long serialVersionUID = -4450434610253862372L;

	public static Tag TAG = Tag.SPECIALCUSTOM;
	
	public static final String ATTR_SCRIPT = "script";
	
	private String name;
	private String script;

	public SpecialCustomDataModel(String name, String script ){
		common( name, script );	
	}

	/**
	 * XML alapjan gyartja le a SPECIALCLOSE-t
	 * 
	 * @param element
	 * @throws XMLPharseException 
	 */
	public SpecialCustomDataModel( Element element ) throws XMLPharseException{
		
		//name
		if( !element.hasAttribute( ATTR_NAME ) ){
			throw new XMLMissingAttributePharseException( getRootTag(), TAG, ATTR_NAME );			
		}
		String nameString = element.getAttribute( ATTR_NAME );		
		this.name = nameString;
		
		//source
		if( !element.hasAttribute( ATTR_SCRIPT ) ){
			throw new XMLMissingAttributePharseException( getRootTag(), TAG, ATTR_SCRIPT );			
		}
		String scriptString = element.getAttribute( ATTR_SCRIPT );		
		this.script = scriptString;
		
		
	}
	
	private void common( String name, String script ){		
		this.name = name;
		this.script = script;
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
	
	public String getScript(){
		return script;
	}
	
	public void setScript( String script ){
		this.script = script;
	}
	
	@Override
	public void add(SpecialDataModelInterface node) {
	}
	
	public static String  getModelNameToShowStatic(){
		return CommonOperations.getTranslation( "tree.nodetype.special.custom");
	}
	
	@Override
	public String getModelNameToShow(){
		return getModelNameToShowStatic();
	}
	
	@Override
	public Element getXMLElement(Document document) {
		Attr attr;

		//Node element
		Element elementElement = document.createElement( SpecialCustomDataModel.this.getTag().getName() );

		//Name
		attr = document.createAttribute( ATTR_NAME );
		attr.setValue( getName() );
		elementElement.setAttributeNode(attr);	

		//Source
		attr = document.createAttribute( ATTR_SCRIPT );
		attr.setValue( getScript() );
		elementElement.setAttributeNode(attr);	

		return elementElement;	
	}

	@Override
	public Object clone(){
		
		SpecialCustomDataModel cloned = (SpecialCustomDataModel)super.clone();
	
		return cloned;
		
	}

}
