package hu.akoel.grawit.core.treenodedatamodel.param;

import java.io.StringReader;

import javax.swing.tree.MutableTreeNode;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.openqa.selenium.WebDriver;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.operations.ButtonOperation;
import hu.akoel.grawit.core.operations.CheckboxOperation;
import hu.akoel.grawit.core.operations.ElementOperationInterface;
import hu.akoel.grawit.core.operations.FieldOperation;
import hu.akoel.grawit.core.operations.LinkOperation;
import hu.akoel.grawit.core.operations.RadioButtonOperation;
import hu.akoel.grawit.core.treenodedatamodel.BaseDataModelInterface;
import hu.akoel.grawit.core.treenodedatamodel.ParamDataModelInterface;
import hu.akoel.grawit.core.treenodedatamodel.VariableDataModelInterface;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.variable.VariableElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.variable.VariableNodeDataModel;
import hu.akoel.grawit.enums.Operation;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.exceptions.ElementInvalidSelectorException;
import hu.akoel.grawit.exceptions.ElementNotFoundException;
import hu.akoel.grawit.exceptions.XMLBaseConversionPharseException;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;
import hu.akoel.grawit.exceptions.XMLPharseException;
import hu.akoel.grawit.exceptions.XMLWrongAttributePharseException;

public class ParamElementDataModel extends ParamDataModelInterface {
	
	private static final long serialVersionUID = -8916078747948054716L;

	public static Tag TAG = Tag.PARAMELEMENT;
	
	private static final String ATTR_BASE_ELEMENT_PATH = "baseelementpath";
	private static final String ATTR_OPERATION = "operation";
	private static final String ATTR_VARIABLE_ELEMENT_PATH = "variableelementpath";
	
	private String name;
	private ElementOperationInterface elementOperation;
	private BaseElementDataModel baseElement;	
	private String variableValue = "";	

	public ParamElementDataModel( String name, BaseElementDataModel baseElement, ElementOperationInterface operation){
		this.name = name;
		this.baseElement = baseElement;
		this.elementOperation = operation;
	}
	
	public ParamElementDataModel( Element element, BaseDataModelInterface baseDataModel, VariableDataModelInterface variableDataModel ) throws XMLPharseException{

		//
		// Name
		//
		if( !element.hasAttribute( ATTR_NAME ) ){
			throw new XMLMissingAttributePharseException( getRootTag(), TAG, ATTR_NAME );			
		}
		String nameString = element.getAttribute( ATTR_NAME );		
		this.name = nameString;
		
		//
		// Operation
		//
		if( !element.hasAttribute( ATTR_OPERATION ) ){
			throw new XMLMissingAttributePharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_OPERATION );			
		}
		String operatorString = element.getAttribute( ATTR_OPERATION );
		if( Operation.BUTTON.name().equals(operatorString ) ){
			this.elementOperation = new ButtonOperation();
		}else if( Operation.CHECKBOX.name().equals(operatorString) ){
			this.elementOperation = new CheckboxOperation();
		}else if( Operation.FIELD.name().equals( operatorString ) ){
			
			String variableElementPathString = element.getAttribute(ATTR_VARIABLE_ELEMENT_PATH);				
			variableElementPathString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + variableElementPathString;  
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
		    DocumentBuilder builder;
		    Document document = null;
		    try{  
		        builder = factory.newDocumentBuilder();  
		        document = builder.parse( new InputSource( new StringReader( variableElementPathString ) ) );  
		    } catch (Exception e) {  
		    
		    	//Nem sikerult az atalakitas
		    	throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_VARIABLE_ELEMENT_PATH, element.getAttribute(ATTR_VARIABLE_ELEMENT_PATH), e );
		    } 

		    //Megkeresem a VARIABLEROOT-ben a VARIABLEELEMENT-hez vezeto utat
		    Node actualNode = document;
		    while( actualNode.hasChildNodes() ){
			
		    	actualNode = actualNode.getFirstChild();
		    	Element actualElement = (Element)actualNode;
		    	String tagName = actualElement.getTagName();
		    	String attrName = null;
		    	
		    	//Ha VARIABLENODE
		    	if( tagName.equals( VariableNodeDataModel.TAG.getName() ) ){
		    		attrName = actualElement.getAttribute(VariableNodeDataModel.ATTR_NAME);	    		
		    		variableDataModel = (VariableDataModelInterface) CommonOperations.getDataModelByNameInLevel( variableDataModel, Tag.VARIABLENODE, attrName );

		    		if( null == variableDataModel ){

		    			throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_VARIABLE_ELEMENT_PATH, element.getAttribute(ATTR_VARIABLE_ELEMENT_PATH) );
		    		}
		    		
		    	//Ha VARIABLEELEMENT
		    	}else if( tagName.equals( VariableElementDataModel.TAG.getName() ) ){
		    		attrName = actualElement.getAttribute(VariableElementDataModel.ATTR_NAME);

		    		variableDataModel = (VariableDataModelInterface) CommonOperations.getDataModelByNameInLevel( variableDataModel, Tag.VARIABLEELEMENT, attrName );
		    		
//		    		throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_VARIABLE_ELEMENT_PATH, element.getAttribute(ATTR_VARIABLE_ELEMENT_PATH ) );	    		
		    	}else{
		    		
		    		throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_VARIABLE_ELEMENT_PATH, element.getAttribute(ATTR_VARIABLE_ELEMENT_PATH) );	    		
		    	}
		    }	    
		    try{
		    	
		    	this.elementOperation = new FieldOperation( (VariableElementDataModel)variableDataModel );
		    	
		    }catch(ClassCastException e){

		    	//Nem sikerult az utvonalat megtalalni
		    	throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_VARIABLE_ELEMENT_PATH, element.getAttribute(ATTR_VARIABLE_ELEMENT_PATH ), e );
		    }
		    
			
//TODO folytatni ----

//this.elementOperation = new LinkOperation();
		}else if( Operation.LINK.name().equals( operatorString ) ){
			this.elementOperation = new LinkOperation();
		}else if( Operation.RADIOBUTTON.name().equals( operatorString ) ){
			this.elementOperation = new RadioButtonOperation();
		}else{
			throw new XMLWrongAttributePharseException(getRootTag(), TAG, ATTR_NAME, getName(), ATTR_OPERATION, operatorString );
		}

		//
		// BaseElement
		//
		if( !element.hasAttribute( ATTR_BASE_ELEMENT_PATH ) ){
			throw new XMLMissingAttributePharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_BASE_ELEMENT_PATH );			
		}	
		String paramElementPathString = element.getAttribute(ATTR_BASE_ELEMENT_PATH);				
		paramElementPathString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + paramElementPathString;  
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
	    DocumentBuilder builder;
	    Document document = null;
	    try  
	    {  
	        builder = factory.newDocumentBuilder();  
	        document = builder.parse( new InputSource( new StringReader( paramElementPathString ) ) );  
	    } catch (Exception e) {  
	    
	    	//Nem sikerult az atalakitas
	    	throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_BASE_ELEMENT_PATH, element.getAttribute(ATTR_BASE_ELEMENT_PATH), e );
	    } 
	    
		//Megkeresem a BASEROOT-ban az utvonalat az BASEELEMENT-hez
	    Node actualNode = document;
	    if( actualNode.hasChildNodes() ){
		
	    	actualNode = actualNode.getFirstChild();
	    	Element actualElement = (Element)actualNode;
	    	String tagName = actualElement.getTagName();
	    	
	    	//Ha ELEMENT
	    	if( tagName.equals( BaseElementDataModel.TAG.getName() ) ){
	    		String attrName = actualElement.getAttribute(BaseElementDataModel.ATTR_NAME);	    		
	    		baseDataModel = (BaseDataModelInterface) CommonOperations.getDataModelByNameInLevel( baseDataModel, BaseElementDataModel.TAG, attrName );

	    		if( null == baseDataModel ){

	    			throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_BASE_ELEMENT_PATH, element.getAttribute(ATTR_BASE_ELEMENT_PATH) );	    		}
	    		
	    	}else{
	    		throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_BASE_ELEMENT_PATH, element.getAttribute(ATTR_BASE_ELEMENT_PATH) );	    		
	    	}
	    }else{
	    	throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_BASE_ELEMENT_PATH, element.getAttribute(ATTR_BASE_ELEMENT_PATH) );
    	}
	    
	    try{
	    	
	    	baseElement = (BaseElementDataModel)baseDataModel;
	    	
	    }catch(ClassCastException e){

	    	throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_BASE_ELEMENT_PATH, element.getAttribute(ATTR_BASE_ELEMENT_PATH), e );
	    }		
		
	}

	/**
	 * 
	 * Executes the defined Operation with the defined Parameter
	 * @throws ElementNotFoundException 
	 * 
	 */
	public void doAction( WebDriver driver ) throws ElementNotFoundException, ElementInvalidSelectorException{
		this.getElementOperation().doAction( driver, this );
	}
	
	public void setName( String name ){
		this.name = name;
	}
	
	@Override
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
	
	public static Tag getTagStatic(){
		return TAG;
	}

	@Override
	public Tag getTag(){
		return getTagStatic();
	}
	
	public static String  getModelNameToShowStatic(){
		return CommonOperations.getTranslation( "tree.nodetype.param.element");
	}
	
	@Override
	public String getModelNameToShow(){
		return getModelNameToShowStatic();
	}
	
	@Override
	public Element getXMLElement(Document document) {
		Attr attr;
	
		//
		//Node element
		//
		Element elementElement = document.createElement( TAG.getName() );
		
		//Name
		attr = document.createAttribute( ATTR_NAME );
		attr.setValue( getName() );
		elementElement.setAttributeNode(attr);	
		
		//BaseElementPath
		attr = document.createAttribute( ATTR_BASE_ELEMENT_PATH );
		//attr.setValue( baseElement.getTaggedPathToString() );
		attr.setValue( baseElement.getOpenTag() + baseElement.getCloseTag() );
		elementElement.setAttributeNode(attr);

		//Operation
		attr = document.createAttribute( ATTR_OPERATION );
		attr.setValue( getElementOperation().getOperation().name() );
		elementElement.setAttributeNode(attr);
		
		//VariableElementPath
		attr = document.createAttribute( ATTR_VARIABLE_ELEMENT_PATH );
		VariableDataModelInterface variableDataModel = elementOperation.getVariableElement();
		if( null == variableDataModel ){
			attr.setValue("");
		}else{
			attr.setValue( variableDataModel.getPathTag() );
		}
		elementElement.setAttributeNode( attr );
		
		
		return elementElement;	
	}
	
}
