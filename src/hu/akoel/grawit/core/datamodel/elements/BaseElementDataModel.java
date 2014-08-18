package hu.akoel.grawit.core.datamodel.elements;

import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.IdentificationType;
import hu.akoel.grawit.VariableSample;
import hu.akoel.grawit.core.datamodel.BaseDataModelInterface;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;
import hu.akoel.grawit.exceptions.XMLPharseException;
import hu.akoel.grawit.exceptions.XMLWrongAttributePharseException;

public class BaseElementDataModel extends BaseDataModelInterface{
	private static final long serialVersionUID = -8916078747948054716L;

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
		if( !element.hasAttribute("name") ){
			throw new XMLMissingAttributePharseException( "base", "element", "name" );			
		}
		String nameString = element.getAttribute("name");		
		this.name = nameString;
		
		//identifier             
		if( !element.hasAttribute("identifier") ){
			throw new XMLMissingAttributePharseException( "base", "element", "identifier" );			
		}
		String identifierString = element.getAttribute("identifier");
		this.identifier = identifierString;
		
		//identificationtype
		if( !element.hasAttribute("identificationtype" ) ){
			throw new XMLMissingAttributePharseException( "base", "element", "identificationtype" );
		}
		String identificationTypeString = element.getAttribute("identificationtype");
		if( IdentificationType.ID.name().equals( identificationTypeString ) ){
			identificationType = IdentificationType.ID;
		}else if( IdentificationType.CSS.name().equals( identificationTypeString ) ){
			identificationType = IdentificationType.CSS;
		}else{			
			throw new XMLWrongAttributePharseException( "base", "element", "identificationtype", identificationTypeString ); 
		}
		
		//frame
		if( !element.hasAttribute("frame") ){
			throw new XMLMissingAttributePharseException( "base", "element", "frame" );
		}
		String frameString = element.getAttribute("frame");

		//variablesemple
		String variablesempleString = element.getAttribute("variablesemple");
		if( nameString.isEmpty() ){
			throw new XMLMissingAttributePharseException( "base", "element", "variablesample" );			
		}		
		if( VariableSample.NO.name().equals(variablesempleString)){
			variableSample = VariableSample.NO;
		}else if( VariableSample.PRE.name().equals(variablesempleString)){
			variableSample = VariableSample.PRE;
		}else if( VariableSample.POST.name().equals(variablesempleString)){
			variableSample = VariableSample.POST;
		}else{
			throw new XMLWrongAttributePharseException( "base", "element", "variablesemple", variablesempleString );
		}		
	}
	
	private void common( String name, String identifier, IdentificationType identificationType, VariableSample variableSample, String frame ){		
		this.name = name;
		this.identifier = identifier;
		this.identificationType = identificationType;
		this.variableSample = variableSample;
		this.frame = frame;
	}

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
	
	public String getNameToString(){
		return getName();
	}
	
	public String getTypeToString(){
		return CommonOperations.getTranslation( "tree.nodetype.baseelement");
	}
	
	@Override
	public String getPathToString() {		
		StringBuffer out = new StringBuffer();
		for( TreeNode node: this.getPath() ){
			out.append( ((BaseDataModelInterface)node).getNameToString() );
			out.append("/");
		}		
		return out.toString();
	}	
	

	@Override
	public Element getXMLElement(Document document) {
		Attr attr;
//System.err.println("   Elem");		
		//Node element
		Element elementElement = document.createElement("element");
		attr = document.createAttribute("name");
		attr.setValue( getName() );
		elementElement.setAttributeNode(attr);	
		
		attr = document.createAttribute("frame");
		attr.setValue( getFrame() );
		elementElement.setAttributeNode(attr);	

		attr = document.createAttribute("variablesemple");
		attr.setValue( getVariableSample().name() );
		elementElement.setAttributeNode(attr);
		
		attr = document.createAttribute("identifier");
		attr.setValue( getIdentifier() );
		elementElement.setAttributeNode(attr);	

		attr = document.createAttribute("identificationtype");
		attr.setValue( getIdentificationType().name() );
		elementElement.setAttributeNode(attr);	

		return elementElement;	
	}
}
