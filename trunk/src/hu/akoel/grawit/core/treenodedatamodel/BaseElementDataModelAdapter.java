package hu.akoel.grawit.core.treenodedatamodel;

import javax.swing.tree.MutableTreeNode;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.enums.list.ElementTypeListEnum;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;
import hu.akoel.grawit.exceptions.XMLPharseException;

public abstract class BaseElementDataModelAdapter extends BaseDataModelAdapter{
	private static final long serialVersionUID = -8916078747948054716L;

	public static final String ATTR_ELEMENT_TYPE="elementtype";
	
	public static Tag TAG = Tag.BASEELEMENT;
	
//	public static final String ATTR_ELEMENT_TYPE="elementtype";
//	public static final String ATTR_IDENTIFIER = "identifier";
//	public static final String ATTR_IDENTIFICATION_TYPE = "identificationtype";
//	public static final String ATTR_FRAME = "frame";
//	public static final String ATTR_WAITINGTIME = "waitingtime";
	
	//Adatmodel ---
	private String name;
//	private ElementTypeListEnum elementType;
//	private String frame;
//	private String identifier;
//	private SelectorType identificationType;
//	private Integer waitingTime = null;
	//----
	
	//Ide menti az erre a mezore hivatkozo ParamElement Mezo mentett erteket
	private String storedValue = "";
	//---

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
	public BaseElementDataModelAdapter(String name){
		this.name = name;
	}

	/**
	 * Capture new
	 * 
	 * XML alapjan gyartja le a BASEELEMENT-et
	 * 
	 * @param element
	 * @throws XMLPharseException 
	 */
	public BaseElementDataModelAdapter( Element element ) throws XMLPharseException{
		
		//name
		if( !element.hasAttribute( ATTR_NAME ) ){
			throw new XMLMissingAttributePharseException( getRootTag(), getTag(), ATTR_NAME );			
		}
		String nameString = element.getAttribute( ATTR_NAME );		
		this.name = nameString;
				
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public void add(BaseDataModelAdapter node) {
	}
	
	public abstract ElementTypeListEnum getElementType();
	
	/**
	 * 
	 * Visszaadja a valtozokent, az osztaly altal reprezentalt elem tartalmat elmentett erteket
	 * 
	 * @return
	 */
	public String getStoredValue() {
		return storedValue;
	}

	public void setStoredValue(String valueToStore) {
		this.storedValue = valueToStore;
	}
	
	@Override
	public Element getXMLElement(Document document) {
		Attr attr;

		//Node element
		Element elementElement = document.createElement( BaseElementDataModelAdapter.this.getTag().getName() );
		attr = document.createAttribute( ATTR_NAME );
		attr.setValue( getName() );
		elementElement.setAttributeNode(attr);	

		return elementElement;	
	}

	@Override
	public Object clone(){
		
		//Leklonozza az BaseElement-et
		BaseElementDataModelAdapter cloned = (BaseElementDataModelAdapter)super.clone();
	
		//Es a valtozoit is klonozni kell
		cloned.name = new String( this.name );		
	
		return cloned;
		
	}
	
	@Override
	public Object cloneWithParent() {
		
		BaseElementDataModelAdapter cloned = (BaseElementDataModelAdapter) this.clone();
		
		//Le kell masolni a felmenoit is, egyebkent azok automatikusan null-ok
		cloned.setParent( (MutableTreeNode) this.getParent() );
		
		return cloned;
	}
}
