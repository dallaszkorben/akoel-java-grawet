package hu.akoel.grawit.core.operations;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.BaseElementDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.ConstantDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.ScriptBaseElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.constant.ConstantElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.constant.ConstantFolderNodeDataModel;
import hu.akoel.grawit.core.treenodedatamodel.constant.ConstantRootDataModel;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.exceptions.ElementException;
import hu.akoel.grawit.exceptions.XMLBaseConversionPharseException;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;
import hu.akoel.grawit.gui.interfaces.progress.ElementProgressInterface;

public class SpecialBaseAddVariableToParametersOperation extends ElementOperationAdapter implements HasVariableOperationInterface{
	
	private static final String NAME = "ADDVARIABLETOPARAMETERS";	
	private static final String ADD_VARIABLE_TO_PARAMETERS_PATH = "variablepath";
	
	//--- Data model
	private ConstantElementDataModel variableElementDataModel;
	//---
	
	public SpecialBaseAddVariableToParametersOperation( ConstantElementDataModel variableElementDataModel ){
		this.variableElementDataModel = variableElementDataModel;
	}
	
	public SpecialBaseAddVariableToParametersOperation( Element element, ConstantRootDataModel variableRootDataModel, Tag rootTag, Tag tag, String nameAttrName, String nameAttrValue ) throws XMLBaseConversionPharseException, XMLMissingAttributePharseException{
		
		ConstantDataModelAdapter variableDataModelForFillOut = variableRootDataModel;
		
		if( !element.hasAttribute( ADD_VARIABLE_TO_PARAMETERS_PATH ) ){
			throw new XMLMissingAttributePharseException( rootTag, tag, ADD_VARIABLE_TO_PARAMETERS_PATH );		
		}
		String variableElementPathString = element.getAttribute(ADD_VARIABLE_TO_PARAMETERS_PATH);				
		variableElementPathString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + variableElementPathString;  
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
	    DocumentBuilder builder;
	    Document document = null;
	    try{  
	        builder = factory.newDocumentBuilder();  
	        document = builder.parse( new InputSource( new StringReader( variableElementPathString ) ) );  
	    } catch (Exception e) {  
	    
	    	//Nem sikerult az atalakitas
	    	throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, nameAttrValue, ADD_VARIABLE_TO_PARAMETERS_PATH, element.getAttribute(ADD_VARIABLE_TO_PARAMETERS_PATH), e );
	    } 

	    //Megkeresem a VARIABLEROOT-ben a VARIABLEELEMENT-hez vezeto utat
	    Node actualNode = document;
	    while( actualNode.hasChildNodes() ){
		
	    	actualNode = actualNode.getFirstChild();
	    	Element actualElement = (Element)actualNode;
	    	String tagName = actualElement.getTagName();
	    	String attrName = null;
	    	
	    	//Ha VARIABLENODE
	    	if( tagName.equals( ConstantFolderNodeDataModel.TAG.getName() ) ){
	    		attrName = actualElement.getAttribute(ConstantFolderNodeDataModel.ATTR_NAME);	    		
	    		variableDataModelForFillOut = (ConstantDataModelAdapter) CommonOperations.getDataModelByNameInLevel( variableDataModelForFillOut, Tag.CONSTANTFOLDER, attrName );

	    		if( null == variableDataModelForFillOut ){

	    			throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, nameAttrValue, ADD_VARIABLE_TO_PARAMETERS_PATH, element.getAttribute(ADD_VARIABLE_TO_PARAMETERS_PATH) );
	    		}
	    		
	    	//Ha VARIABLEELEMENT
	    	}else if( tagName.equals( ConstantElementDataModel.TAG.getName() ) ){
	    		attrName = actualElement.getAttribute(ConstantElementDataModel.ATTR_NAME);
	    		variableDataModelForFillOut = (ConstantDataModelAdapter) CommonOperations.getDataModelByNameInLevel( variableDataModelForFillOut, Tag.CONSTANTELEMENT, attrName );
	    		
	    		if( null == variableDataModelForFillOut ){

	    			throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, getName(), ADD_VARIABLE_TO_PARAMETERS_PATH, element.getAttribute(ADD_VARIABLE_TO_PARAMETERS_PATH) );
	    		}
	    		
	    	}else{
	    		
	    		throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, getName(), ADD_VARIABLE_TO_PARAMETERS_PATH, element.getAttribute(ADD_VARIABLE_TO_PARAMETERS_PATH) );	    		
	    	}
	    }	    
	    try{
	    	
	    	this.variableElementDataModel = (ConstantElementDataModel)variableDataModelForFillOut;
	    	
	    }catch(ClassCastException e){

	    	//Nem sikerult az utvonalat megtalalni
	    	throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, nameAttrValue, ADD_VARIABLE_TO_PARAMETERS_PATH, element.getAttribute(ADD_VARIABLE_TO_PARAMETERS_PATH ), e );
	    }
	}
	
	public static String getStaticName(){
		return NAME;
	}
	
	@Override
	public String getName() {		
		return getStaticName();
	}
	
	@Override
	public ConstantElementDataModel getVariableElement() {
		return variableElementDataModel;
	}

	@Override
	public void doOperation(WebDriver driver, BaseElementDataModelAdapter baseElement, WebElement webElement, ElementProgressInterface elementProgress) throws ElementException {

		//HA SPECIALBASEELEMENT - annak kell lennie
		if( baseElement instanceof ScriptBaseElementDataModel ){

			((ScriptBaseElementDataModel)baseElement).addParameter( variableElementDataModel.getValue() );
			
		}

	}

	@Override
	public void setXMLAttribute(Document document, Element element) {
		Attr attr = document.createAttribute( ADD_VARIABLE_TO_PARAMETERS_PATH );		
		attr.setValue( variableElementDataModel.getPathTag() );
		element.setAttributeNode( attr );			
	}

	@Override
	public Object clone() {

		return new SpecialBaseAddVariableToParametersOperation(variableElementDataModel);
	}
	
	@Override
	public String getOperationToString() {		
		return "AddVariableToScriptElementParameter()";
	}
	
}
