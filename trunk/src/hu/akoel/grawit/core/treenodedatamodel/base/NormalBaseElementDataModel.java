package hu.akoel.grawit.core.treenodedatamodel.base;

import javax.swing.tree.MutableTreeNode;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.BaseDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.BaseElementDataModelAdapter;
import hu.akoel.grawit.enums.SelectorType;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.enums.list.ElementTypeListEnum;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;
import hu.akoel.grawit.exceptions.XMLPharseException;
import hu.akoel.grawit.exceptions.XMLWrongAttributePharseException;

public class NormalBaseElementDataModel extends BaseElementDataModelAdapter{
	private static final long serialVersionUID = -8916078747948054716L;
	
	public static final String ATTR_ELEMENT_TYPE="elementtype";
	public static final String ATTR_IDENTIFIER = "identifier";
	public static final String ATTR_IDENTIFICATION_TYPE = "identificationtype";
	public static final String ATTR_FRAME = "frame";
	public static final String ATTR_WAITINGTIME_FOR_APPEARANCE = "waitingtimeforappearance";
	public static final String ATTR_WAITINGTIME_BEFORE_OPERATION = "waitingtimebeforeoperation";
	public static final String ATTR_WAITINGTIME_AFTER_OPERATION = "waitingtimeafteroperation";
	
	//Adatmodel ---
	private ElementTypeListEnum elementType;
	private String frame;
	private String identifier;
	private SelectorType identificationType;
	private Integer waitingTimeForAppearance = null;
	private Integer waitingTimeBeforeOperation = null;
	private Integer waitingTimeAfterOperation = null;
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
	public NormalBaseElementDataModel(String name, ElementTypeListEnum elementType, String identifier, SelectorType identificationType, Integer waitingTimeForAppearance, Integer waitingTimeBeforeOperation, Integer waitingTimeAfterOperation, String frame){
		super( name );
		
		this.elementType = elementType;
		this.identifier = identifier;
		this.identificationType = identificationType;
		this.waitingTimeForAppearance = waitingTimeForAppearance;
		this.waitingTimeBeforeOperation = waitingTimeBeforeOperation;
		this.waitingTimeAfterOperation = waitingTimeAfterOperation;
		this.frame = frame;
	}

	/**
	 * Capture new
	 * 
	 * XML alapjan gyartja le a BASEELEMENT-et
	 * 
	 * @param element
	 * @throws XMLPharseException 
	 */
	public NormalBaseElementDataModel( Element element ) throws XMLPharseException{
		super( element );
		
		//frame             
		if( !element.hasAttribute( ATTR_FRAME ) ){
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
		
		String waitingTimeString;
		//waiting time for appearance
		if( !element.hasAttribute( ATTR_WAITINGTIME_FOR_APPEARANCE ) ){			
			//TODO majd visszarakni, hogy ha nem talalja, akkor hiba
			//throw new XMLMissingAttributePharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_WAITINGTIME_FOR_APPEARANCE );
		}else{
			waitingTimeString = element.getAttribute( ATTR_WAITINGTIME_FOR_APPEARANCE );
			try{
				waitingTimeForAppearance = new Integer( waitingTimeString );
			}catch( Exception e ){}			
		}
		
		//waiting time before operation
		if( !element.hasAttribute( ATTR_WAITINGTIME_BEFORE_OPERATION ) ){			
			//TODO majd visszarakni, hogy ha nem talalja, akkor hiba
			//throw new XMLMissingAttributePharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_WAITINGTIME_BEFORE_OPERATION );
		}else{
			waitingTimeString = element.getAttribute( ATTR_WAITINGTIME_BEFORE_OPERATION );
			try{
				waitingTimeBeforeOperation = new Integer( waitingTimeString );
			}catch( Exception e ){}			
		}
		
		//waiting time after operation
		if( !element.hasAttribute( ATTR_WAITINGTIME_AFTER_OPERATION ) ){			
			//TODO majd visszarakni, hogy ha nem talalja, akkor hiba
			//throw new XMLMissingAttributePharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_WAITINGTIME_AFTER_OPERATION );
		}else{
			waitingTimeString = element.getAttribute( ATTR_WAITINGTIME_AFTER_OPERATION );
			try{
				waitingTimeAfterOperation = new Integer( waitingTimeString );
			}catch( Exception e ){}			
		}						
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
	
	public void setElementType( ElementTypeListEnum elementType ){
		this.elementType = elementType;
	}
	
	public Integer getWaitingTimeForAppearance(){
		return this.waitingTimeForAppearance;
	}
	
	public void setWaitingTimeForAppearance( Integer waitingTime ){
		this.waitingTimeForAppearance = waitingTime;
	}

	public Integer getWaitingTimeBeforeOperation(){
		return this.waitingTimeBeforeOperation;
	}
	
	public void setWaitingTimeBeforeOperation( Integer waitingTime ){
		this.waitingTimeBeforeOperation = waitingTime;
	}

	public Integer getWaitingTimeAfterOperation(){
		return this.waitingTimeAfterOperation;
	}
	
	public void setWaitingTimeAfterOperation( Integer waitingTime ){
		this.waitingTimeAfterOperation = waitingTime;
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
	
	public static String  getModelNameToShowStatic(){
		return CommonOperations.getTranslation( "tree.nodetype.base.normalelement");
	}
	
	@Override
	public String getNodeTypeToShow(){
		return getModelNameToShowStatic();
	}
	
	@Override
	public Element getXMLElement(Document document) {
		
		Element elementElement = super.getXMLElement(document);
		
		Attr attr;

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
		
		attr = document.createAttribute( ATTR_WAITINGTIME_FOR_APPEARANCE );
		if( null == getWaitingTimeForAppearance() ){
			attr.setValue( "" );	
		}else{
			attr.setValue( getWaitingTimeForAppearance().toString() );
		}
		elementElement.setAttributeNode(attr);

		attr = document.createAttribute( ATTR_WAITINGTIME_BEFORE_OPERATION );
		if( null == getWaitingTimeBeforeOperation() ){
			attr.setValue( "" );	
		}else{
			attr.setValue( getWaitingTimeBeforeOperation().toString() );
		}
		elementElement.setAttributeNode(attr);

		attr = document.createAttribute( ATTR_WAITINGTIME_AFTER_OPERATION );
		if( null == getWaitingTimeAfterOperation() ){
			attr.setValue( "" );	
		}else{
			attr.setValue( getWaitingTimeAfterOperation().toString() );
		}		
		elementElement.setAttributeNode(attr);	

		return elementElement;	
	}

	@Override
	public Object clone(){
		
		//Leklonozza az BaseElement-et
		NormalBaseElementDataModel cloned = (NormalBaseElementDataModel)super.clone();
	
		//Es a valtozoit is klonozni kell
		cloned.frame = new String(this.frame);
		cloned.identifier = new String( this.identifier );
		cloned.identificationType = this.identificationType;	//TODO kedes, hogy jo-e
		cloned.elementType = this.elementType;					//TODO Kerde, hogy jo-e
		if( null != this.waitingTimeForAppearance )
			cloned.waitingTimeForAppearance = new Integer(this.waitingTimeForAppearance);		
		if( null != this.waitingTimeBeforeOperation )
			cloned.waitingTimeBeforeOperation = new Integer(this.waitingTimeBeforeOperation);
		if( null != this.waitingTimeAfterOperation )
			cloned.waitingTimeAfterOperation = new Integer(this.waitingTimeAfterOperation);
		return cloned;
		
	}

}
