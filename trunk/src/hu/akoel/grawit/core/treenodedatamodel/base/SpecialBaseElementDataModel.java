package hu.akoel.grawit.core.treenodedatamodel.base;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.enums.list.ElementTypeListEnum;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;
import hu.akoel.grawit.exceptions.XMLPharseException;

public class SpecialBaseElementDataModel extends BaseElementDataModelAdapter{
	private static final long serialVersionUID = -8916078747948054716L;

	public static Tag TAG = Tag.SPECIALBASEELEMENT;
	
	private ElementTypeListEnum elementType = ElementTypeListEnum.SPECIAL;
	
	public static final String ATTR_ELEMENT_TYPE="elementtype";
//	public static final String ATTR_IDENTIFIER = "identifier";
//	public static final String ATTR_IDENTIFICATION_TYPE = "identificationtype";
//	public static final String ATTR_FRAME = "frame";
//	public static final String ATTR_WAITINGTIME = "waitingtime";
	
	//Adatmodel ---
//	private ElementTypeListEnum elementType;
//	private String frame;
//	private String identifier;
//	private SelectorType identificationType;
//	private Integer waitingTime = null;
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
	public SpecialBaseElementDataModel(String name){
		super( name );
		
/*		this.elementType = elementType;
		this.identifier = identifier;
		this.identificationType = identificationType;
		this.waitingTime = waitingTime;
		this.frame = frame;
*/		
	}

	/**
	 * Capture new
	 * 
	 * XML alapjan gyartja le a BASEELEMENT-et
	 * 
	 * @param element
	 * @throws XMLPharseException 
	 */
	public SpecialBaseElementDataModel( Element element ) throws XMLPharseException{
		super( element );
		
		//element type             
		if( !element.hasAttribute( ATTR_ELEMENT_TYPE ) ){
			throw new XMLMissingAttributePharseException( getRootTag(), getTag(), ATTR_NAME, getName(), ATTR_ELEMENT_TYPE );			
		}
		String elementTypeString = element.getAttribute( ATTR_ELEMENT_TYPE );
		this.elementType = ElementTypeListEnum.valueOf( elementTypeString );
		
		//frame             
/*		if( !element.hasAttribute( ATTR_FRAME ) ){
			throw new XMLMissingAttributePharseException( getRootTag(), getTag(), ATTR_NAME, getName(), ATTR_FRAME );			
		}
		String frameString = element.getAttribute( ATTR_FRAME );
		this.frame = frameString;
		
		//identifier             
		if( !element.hasAttribute( ATTR_IDENTIFIER ) ){
			throw new XMLMissingAttributePharseException( getRootTag(), getTag(), ATTR_NAME, getName(), ATTR_IDENTIFIER );			
		}
		String identifierString = element.getAttribute( ATTR_IDENTIFIER );
		this.identifier = identifierString;
		
		//element type             
		if( !element.hasAttribute( ATTR_ELEMENT_TYPE ) ){
			throw new XMLMissingAttributePharseException( getRootTag(), getTag(), ATTR_NAME, getName(), ATTR_ELEMENT_TYPE );			
		}
		String elementTypeString = element.getAttribute( ATTR_ELEMENT_TYPE );
		this.elementType = ElementTypeListEnum.valueOf( elementTypeString );
		
		//identificationtype
		if( !element.hasAttribute( ATTR_IDENTIFICATION_TYPE ) ){
			throw new XMLMissingAttributePharseException( getRootTag(), getTag(), ATTR_NAME, getName(), ATTR_IDENTIFICATION_TYPE );
		}
		String identificationTypeString = element.getAttribute( ATTR_IDENTIFICATION_TYPE );
		if( SelectorType.ID.name().equals( identificationTypeString ) ){
			identificationType = SelectorType.ID;
		}else if( SelectorType.CSS.name().equals( identificationTypeString ) ){
			identificationType = SelectorType.CSS;
		}else{			
			throw new XMLWrongAttributePharseException( getRootTag(), getTag(), ATTR_NAME, getName(), ATTR_IDENTIFICATION_TYPE, identificationTypeString ); 
		}		
		
		//waiting time
		if( !element.hasAttribute( ATTR_WAITINGTIME ) ){			
			//TODO majd visszarakni, hogy ha nem talalja, akkor hiba
			//throw new XMLMissingAttributePharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_WAITINGTIME );
		}else{
			String waitingTimeString = element.getAttribute( ATTR_WAITINGTIME );
			try{
				waitingTime = new Integer( waitingTimeString );
			}catch( Exception e ){}
			
		}
*/				
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
/*	
	public void setElementType( ElementTypeListEnum elementType ){
		this.elementType = elementType;
	}
	
	public Integer getWaitingTime(){
		return this.waitingTime;
	}
	
	public void setWaitingTime( Integer waitingTime ){
		this.waitingTime = waitingTime;
	}
	
	public String getSelector() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public SelectorType getSelectorType() {
		return identificationType;
	}

	public void setIdentificationType(SelectorType identificationType) {
		this.identificationType = identificationType;
	}

	public String getFrame(){
		return frame;
	}
	
	public void setFrame( String frame ){
		this.frame = frame;
	}
	
	@Override
	public void add(BaseDataModelAdapter node) {
		super.add( (MutableTreeNode)node );
	}
*/	
	public static String  getModelNameToShowStatic(){
		return CommonOperations.getTranslation( "tree.nodetype.base.specialelement");
	}
	
	@Override
	public String getNodeTypeToShow(){
		return getModelNameToShowStatic();
	}
	
	@Override
	public Element getXMLElement(Document document) {
		
		Element elementElement = super.getXMLElement(document);
		
		Attr attr;
		
		//Element type
		attr = document.createAttribute( ATTR_ELEMENT_TYPE );
		attr.setValue( getElementType().name() );
		elementElement.setAttributeNode(attr);	
/*
		//Node element
		attr = document.createAttribute( ATTR_FRAME);
		attr.setValue( getFrame() );
		elementElement.setAttributeNode(attr);	
		
		attr = document.createAttribute( ATTR_IDENTIFIER );
		attr.setValue( getSelector() );
		elementElement.setAttributeNode(attr);	
		
		attr = document.createAttribute( ATTR_ELEMENT_TYPE );
		attr.setValue( getElementType().name() );
		elementElement.setAttributeNode(attr);	

		attr = document.createAttribute( ATTR_IDENTIFICATION_TYPE );
		attr.setValue( getSelectorType().name() );
		elementElement.setAttributeNode(attr);	
		
		attr = document.createAttribute( ATTR_WAITINGTIME );
		if( null == getWaitingTime() ){
			attr.setValue( "" );	
		}else{
			attr.setValue( getWaitingTime().toString() );
		}
		
		elementElement.setAttributeNode(attr);	
*/
		return elementElement;	
	}

	@Override
	public Object clone(){
		
		//Leklonozza az BaseElement-et
		SpecialBaseElementDataModel cloned = (SpecialBaseElementDataModel)super.clone();
	
		cloned.elementType = this.elementType;	
		
		//Es a valtozoit is klonozni kell
/*		cloned.frame = new String(this.frame);
		cloned.identifier = new String( this.identifier );
		cloned.identificationType = this.identificationType;	//TODO kedes, hogy jo-e
		cloned.elementType = this.elementType;					//TODO Kerde, hogy jo-e
		if( null != this.waitingTime )
			cloned.waitingTime = new Integer(this.waitingTime);		
*/		
		return cloned;
		
	}
	
	@Override
	public Object cloneWithParent() {
		
		SpecialBaseElementDataModel cloned = (SpecialBaseElementDataModel) super.cloneWithParent();
		
		return cloned;
	}

}
