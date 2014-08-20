package hu.akoel.grawit.core.datamodel.elements;

import javax.swing.tree.MutableTreeNode;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.datamodel.BaseDataModelInterface;
import hu.akoel.grawit.enums.IdentificationType;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.enums.VariableSample;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;
import hu.akoel.grawit.exceptions.XMLPharseException;
import hu.akoel.grawit.exceptions.XMLWrongAttributePharseException;

public class BaseElementDataModel extends BaseDataModelInterface{
	private static final long serialVersionUID = -8916078747948054716L;

	public static Tag TAG = Tag.BASEELEMENT;
	
//	public static final String ATTR_NAME = "name";
	public static final String ATTR_IDENTIFIER = "identifier";
	public static final String ATTR_IDENTIFICATION_TYPE = "identificationtype";
	public static final String ATTR_FRAME = "frame";
	public static final String ATTR_VARIABLE_SAMPLE = "variablesample";
	
	private String name;
	private VariableSample variableSample;
	private String frame;
	private String identifier;
	private IdentificationType identificationType;



	public BaseElementDataModel(String name, String identifier, IdentificationType identificationType, VariableSample variableSample, String frame){
		common( name, identifier, identificationType, variableSample, frame );	
	}

	public BaseElementDataModel( String name, String identifier, IdentificationType identificationType, VariableSample variableSample ){
		common( name, identifier, identificationType, variableSample, null );
	}

	public BaseElementDataModel( BaseElementDataModel element ){
		this.name = element.getName();
		this.identifier = getIdentifier();
		this.identificationType = getIdentificationType();
		this.variableSample = element.getVariableSample();
	}

	/**
	 * XML alapjan gyartja le a BASEELEMENT-et
	 * 
	 * @param element
	 * @throws XMLPharseException 
	 */
	public BaseElementDataModel( Element element ) throws XMLPharseException{
		
		//name
		if( !element.hasAttribute( ATTR_NAME ) ){
			throw new XMLMissingAttributePharseException( getRootTag(), TAG, ATTR_NAME );			
		}
		String nameString = element.getAttribute( ATTR_NAME );		
		this.name = nameString;
		
		//frame             
		if( !element.hasAttribute( ATTR_FRAME ) ){
			throw new XMLMissingAttributePharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_FRAME );			
		}
		String frameString = element.getAttribute( ATTR_FRAME );
		this.frame = frameString;
		
		//identifier             
		if( !element.hasAttribute( ATTR_IDENTIFIER ) ){
			throw new XMLMissingAttributePharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_IDENTIFIER );			
		}
		String identifierString = element.getAttribute( ATTR_IDENTIFIER );
		this.identifier = identifierString;
		
		//identificationtype
		if( !element.hasAttribute( ATTR_IDENTIFICATION_TYPE ) ){
			throw new XMLMissingAttributePharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_IDENTIFICATION_TYPE );
		}
		String identificationTypeString = element.getAttribute( ATTR_IDENTIFICATION_TYPE );
		if( IdentificationType.ID.name().equals( identificationTypeString ) ){
			identificationType = IdentificationType.ID;
		}else if( IdentificationType.CSS.name().equals( identificationTypeString ) ){
			identificationType = IdentificationType.CSS;
		}else{			
			throw new XMLWrongAttributePharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_IDENTIFICATION_TYPE, identificationTypeString ); 
		}
		
		//variablesemple
		String variablesempleString = element.getAttribute(ATTR_VARIABLE_SAMPLE);
		if( nameString.isEmpty() ){
			throw new XMLMissingAttributePharseException( getRootTag(), TAG, ATTR_VARIABLE_SAMPLE );			
		}		
		if( VariableSample.NO.name().equals(variablesempleString)){
			variableSample = VariableSample.NO;
		}else if( VariableSample.PRE.name().equals(variablesempleString)){
			variableSample = VariableSample.PRE;
		}else if( VariableSample.POST.name().equals(variablesempleString)){
			variableSample = VariableSample.POST;
		}else{
			throw new XMLWrongAttributePharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_VARIABLE_SAMPLE, variablesempleString );
		}		
	}
	
	private void common( String name, String identifier, IdentificationType identificationType, VariableSample variableSample, String frame ){		
		this.name = name;
		this.identifier = identifier;
		this.identificationType = identificationType;
		this.variableSample = variableSample;
		this.frame = frame;
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

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public IdentificationType getIdentificationType() {
		return identificationType;
	}

	public void setIdentificationType(IdentificationType identificationType) {
		this.identificationType = identificationType;
	}

	public VariableSample getVariableSample() {
		return variableSample;
	}

	public void setVariableSample(VariableSample variableSample) {
		this.variableSample = variableSample;
	}

	public String getFrame(){
		return frame;
	}
	
	public void setFrame( String frame ){
		this.frame = frame;
	}
	
	@Override
	public void add(BaseDataModelInterface node) {
		super.add( (MutableTreeNode)node );
	}
	
	public String getTypeToShow(){
		return CommonOperations.getTranslation( "tree.nodetype.baseelement");
	}

	@Override
	public Element getXMLElement(Document document) {
		Attr attr;

		//Node element
		Element elementElement = document.createElement( BaseElementDataModel.this.getTag().getName() );
		attr = document.createAttribute( ATTR_NAME );
		attr.setValue( getName() );
		elementElement.setAttributeNode(attr);	
		
		attr = document.createAttribute( ATTR_FRAME);
		attr.setValue( getFrame() );
		elementElement.setAttributeNode(attr);	

		attr = document.createAttribute( ATTR_VARIABLE_SAMPLE);
		attr.setValue( getVariableSample().name() );
		elementElement.setAttributeNode(attr);
		
		attr = document.createAttribute( ATTR_IDENTIFIER );
		attr.setValue( getIdentifier() );
		elementElement.setAttributeNode(attr);	

		attr = document.createAttribute( ATTR_IDENTIFICATION_TYPE );
		attr.setValue( getIdentificationType().name() );
		elementElement.setAttributeNode(attr);	

		return elementElement;	
	}


}
