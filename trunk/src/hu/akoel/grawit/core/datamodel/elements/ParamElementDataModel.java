package hu.akoel.grawit.core.datamodel.elements;

import javax.swing.tree.MutableTreeNode;

import org.openqa.selenium.WebDriver;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.datamodel.ParamDataModelInterface;
import hu.akoel.grawit.core.datamodel.pages.BasePageDataModel;
import hu.akoel.grawit.core.datamodel.pages.ParamPageDataModel;
import hu.akoel.grawit.core.operations.ButtonOperation;
import hu.akoel.grawit.core.operations.CheckboxOperation;
import hu.akoel.grawit.core.operations.ElementOperationInterface;
import hu.akoel.grawit.core.operations.LinkOperation;
import hu.akoel.grawit.core.operations.ElementOperationInterface.Operation;
import hu.akoel.grawit.core.operations.FieldOperation;
import hu.akoel.grawit.core.operations.RadioButtonOperation;
import hu.akoel.grawit.core.parameter.StringParameter;
import hu.akoel.grawit.exceptions.ElementException;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;
import hu.akoel.grawit.exceptions.XMLPharseException;
import hu.akoel.grawit.exceptions.XMLWrongAttributePharseException;

public class ParamElementDataModel extends ParamDataModelInterface{
	
	private static final long serialVersionUID = -8916078747948054716L;

	private static String TAG_NAME = "element";
	
	private static final String ATTR_NAME = "name";
	private static final String ATTR_BASE_ELEMENT_PATH = "baseelementpath";
	private static final String ATTR_OPERATION = "operation";
	
	private String name;
	private ElementOperationInterface elementOperation;
	private BaseElementDataModel baseElement;	
	private String variableValue = "";
	

	public ParamElementDataModel( String name, BaseElementDataModel baseElement, ElementOperationInterface operation){
		this.name = name;
		this.baseElement = baseElement;
		this.elementOperation = operation;
	}
	
	public ParamElementDataModel( Element element ) throws XMLPharseException{

		//name
		if( !element.hasAttribute( ATTR_NAME ) ){
			throw new XMLMissingAttributePharseException( getModelType().getName(), TAG_NAME, ATTR_NAME );			
		}
		String nameString = element.getAttribute( ATTR_NAME );		
		this.name = nameString;
		
		//Operation
		if( !element.hasAttribute( ATTR_OPERATION ) ){
			throw new XMLMissingAttributePharseException( getModelType().getName(), TAG_NAME, ATTR_OPERATION );			
		}
		String operatorString = element.getAttribute( ATTR_NAME );
		if( Operation.BUTTON.name().equals(operatorString ) ){
			this.elementOperation = new ButtonOperation();
		}else if( Operation.CHECKBOX.name().equals(operatorString) ){
			this.elementOperation = new CheckboxOperation();
		}else if( Operation.FIELD.name().equals( operatorString ) ){
			this.elementOperation = new FieldOperation( new StringParameter("", ""));
		}else if( Operation.LINK.name().equals( operatorString ) ){
			this.elementOperation = new LinkOperation();
		}else if( Operation.RADIOBUTTON.name().equals( operatorString ) ){
			this.elementOperation = new RadioButtonOperation();
		}else{
			throw new XMLWrongAttributePharseException(getModelType().getName(), TAG_NAME, ATTR_OPERATION, operatorString );
		}

		//BaseElement
		if( !element.hasAttribute( ATTR_BASE_ELEMENT_PATH ) ){
			throw new XMLMissingAttributePharseException( getModelType().getName(), TAG_NAME, ATTR_OPERATION );			
		}
		ParamPageDataModel paramPage = (ParamPageDataModel)this.getParent();
		BasePageDataModel basePage = paramPage.getBasePage();
	}

	/**
	 * 
	 * Executes the defined Operation with the defined Parameter
	 * @throws ElementException 
	 * 
	 */
	public void doAction( WebDriver driver ) throws ElementException{
		this.getElementOperation().doAction( driver, this );
	}
	
	public void setName( String name ){
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
	
	public ElementOperationInterface getElementOperation() {
		return elementOperation;
	}

	public void setOperation(ElementOperationInterface elementOperation) {
		this.elementOperation = elementOperation;
	}	
	
	public String getVariableValue() {
		return variableValue;
	}

	public void setVariableValue(String variableValue) {
		this.variableValue = variableValue;
	}

	public void setBaseElement( BaseElementDataModel baseElement ){
		this.baseElement = baseElement;
	}
	
	public BaseElementDataModel getBaseElement(){
		return baseElement;
	}
	
	@Override
	public void add( ParamDataModelInterface node ) {
		super.add( (MutableTreeNode)node );
	}
	
	public static String getTagNameStatic(){
		return TAG_NAME;
	}

	@Override
	public String getTagName() {
		return getTagNameStatic();
	}

	@Override
	public String getIDName() {
		return ATTR_NAME;
	}
	
	@Override
	public String getIDValue(){
		return getName();
	}
	
	public String getTypeToString(){
		return CommonOperations.getTranslation( "tree.nodetype.paramelement");
	}
	
	@Override
	public Element getXMLElement(Document document) {
		Attr attr;
	
		//
		//Node element
		//
		Element elementElement = document.createElement( TAG_NAME );
		
		//Name
		attr = document.createAttribute( ATTR_NAME );
		attr.setValue( getName() );
		elementElement.setAttributeNode(attr);	
		
		//BaseElementPath
		attr = document.createAttribute( ATTR_BASE_ELEMENT_PATH );
		attr.setValue( baseElement.getTaggedPathToString() );
		elementElement.setAttributeNode(attr);

		attr = document.createAttribute( ATTR_OPERATION );
		attr.setValue( getElementOperation().getOperation().name() );
		elementElement.setAttributeNode(attr);
		
		return elementElement;	
	}
	
}
