package hu.akoel.grawit.core.treenodedatamodel.base;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.enums.list.ElementTypeListEnum;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;
import hu.akoel.grawit.exceptions.XMLPharseException;

public class VariableBaseElementDataModel extends BaseElementDataModelAdapter{
	private static final long serialVersionUID = -8916078747948054716L;
	
	private ElementTypeListEnum elementType = ElementTypeListEnum.VARIABLE;
	
	//Adatmodel ---
	//private String script;
	//----	
	
	/**
	 * 
	 * Modify
	 * 
	 * @param name
	 * @param elementType
	 * @param identifier
	 * @param identificationType
	 * @param frame
	 */
	public VariableBaseElementDataModel(String name ){
		super( name );
	}

	/**
	 * Capture new
	 * 
	 * XML alapjan gyartja le
	 * 
	 * @param element
	 * @throws XMLPharseException 
	 */
	public VariableBaseElementDataModel( Element element ) throws XMLPharseException{
		super( element );
		
		//element type             
		if( !element.hasAttribute( ATTR_ELEMENT_TYPE ) ){
			throw new XMLMissingAttributePharseException( getRootTag(), getTag(), ATTR_NAME, getName(), ATTR_ELEMENT_TYPE );			
		}
		String elementTypeString = element.getAttribute( ATTR_ELEMENT_TYPE );
		this.elementType = ElementTypeListEnum.valueOf( elementTypeString );
				
	}
	
	public static Tag getTagStatic(){
		return TAG;
	}

	@Override
	public Tag getTag() {
		return getTagStatic();
	}
	
	public ElementTypeListEnum getElementType(){
		return elementType;
	}
		
	public void setValue( String value ){
		this.setStoredValue( value );
	}
	
	public String getValue(){
		return this.getStoredValue();
	}

	public static String  getModelNameToShowStatic(){
		return CommonOperations.getTranslation( "tree.nodetype.base.variableelement");
	}

	@Override
	public Element getXMLElement(Document document) {
		
		Element elementElement = super.getXMLElement(document);
		
		Attr attr;
		
		//Element type
		attr = document.createAttribute( ATTR_ELEMENT_TYPE );
		attr.setValue( getElementType().name() );
		elementElement.setAttributeNode(attr);	
		
		return elementElement;	
	}

	@Override
	public String getNodeTypeToShow() {
		return getModelNameToShowStatic();
	}
	
	@Override
	public Object clone(){
		
		//Leklonozza az BaseElement-et
		VariableBaseElementDataModel cloned = (VariableBaseElementDataModel)super.clone();
	
		cloned.elementType = this.elementType;	
		
		return cloned;
		
	}

}
