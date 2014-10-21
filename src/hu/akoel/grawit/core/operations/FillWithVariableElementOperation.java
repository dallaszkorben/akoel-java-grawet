package hu.akoel.grawit.core.operations;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.ElementProgressInterface;
import hu.akoel.grawit.core.treenodedatamodel.VariableDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.variable.VariableElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.variable.VariableNodeDataModel;
import hu.akoel.grawit.core.treenodedatamodel.variable.VariableRootDataModel;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.exceptions.ElementException;
import hu.akoel.grawit.exceptions.ElementInvalidOperationException;
import hu.akoel.grawit.exceptions.XMLBaseConversionPharseException;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;

public class FillWithVariableElementOperation extends ElementOperationAdapter{
	
	private static final String NAME = "FILLVARIABLE";	
	private static final String ATTR_FILL_VARIABLE_ELEMENT_PATH = "fillvariableelementpath";
	
	//--- Data model
	private VariableElementDataModel variableElementDataModel;
	//---
	
	public FillWithVariableElementOperation( VariableElementDataModel variableElementDataModel ){
		this.variableElementDataModel = variableElementDataModel;
	}
	
	public FillWithVariableElementOperation( Element element, VariableRootDataModel variableRootDataModel, Tag rootTag, Tag tag, String nameAttrName, String nameAttrValue ) throws XMLBaseConversionPharseException, XMLMissingAttributePharseException{
		
		VariableDataModelAdapter variableDataModelForFillOut = variableRootDataModel;
		
		if( !element.hasAttribute( ATTR_FILL_VARIABLE_ELEMENT_PATH ) ){
			throw new XMLMissingAttributePharseException( rootTag, tag, ATTR_FILL_VARIABLE_ELEMENT_PATH );		
		}
		String variableElementPathString = element.getAttribute(ATTR_FILL_VARIABLE_ELEMENT_PATH);				
		variableElementPathString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + variableElementPathString;  
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
	    DocumentBuilder builder;
	    Document document = null;
	    try{  
	        builder = factory.newDocumentBuilder();  
	        document = builder.parse( new InputSource( new StringReader( variableElementPathString ) ) );  
	    } catch (Exception e) {  
	    
	    	//Nem sikerult az atalakitas
	    	throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, nameAttrValue, ATTR_FILL_VARIABLE_ELEMENT_PATH, element.getAttribute(ATTR_FILL_VARIABLE_ELEMENT_PATH), e );
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
	    		variableDataModelForFillOut = (VariableDataModelAdapter) CommonOperations.getDataModelByNameInLevel( variableDataModelForFillOut, Tag.VARIABLENODE, attrName );

	    		if( null == variableDataModelForFillOut ){

	    			throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, nameAttrValue, ATTR_FILL_VARIABLE_ELEMENT_PATH, element.getAttribute(ATTR_FILL_VARIABLE_ELEMENT_PATH) );
	    		}
	    		
	    	//Ha VARIABLEELEMENT
	    	}else if( tagName.equals( VariableElementDataModel.TAG.getName() ) ){
	    		attrName = actualElement.getAttribute(VariableElementDataModel.ATTR_NAME);
	    		variableDataModelForFillOut = (VariableDataModelAdapter) CommonOperations.getDataModelByNameInLevel( variableDataModelForFillOut, Tag.VARIABLEELEMENT, attrName );
	    		
	    		if( null == variableDataModelForFillOut ){

	    			throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, getName(), ATTR_FILL_VARIABLE_ELEMENT_PATH, element.getAttribute(ATTR_FILL_VARIABLE_ELEMENT_PATH) );
	    		}
	    		
	    	}else{
	    		
	    		throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, getName(), ATTR_FILL_VARIABLE_ELEMENT_PATH, element.getAttribute(ATTR_FILL_VARIABLE_ELEMENT_PATH) );	    		
	    	}
	    }	    
	    try{
	    	
	    	this.variableElementDataModel = (VariableElementDataModel)variableDataModelForFillOut;
	    	
	    }catch(ClassCastException e){

	    	//Nem sikerult az utvonalat megtalalni
	    	throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, nameAttrValue, ATTR_FILL_VARIABLE_ELEMENT_PATH, element.getAttribute(ATTR_FILL_VARIABLE_ELEMENT_PATH ), e );
	    }
	}
	
	public static String getStaticName(){
		return NAME;
	}
	
	@Override
	public String getName() {		
		return getStaticName();
	}
	
	public VariableElementDataModel getVariableElement() {
		return variableElementDataModel;
	}

	@Override
	public void doOperation(WebDriver driver, ParamElementDataModel element, WebElement webElement, ElementProgressInterface elementProgress) throws ElementException {

		try{
			//Execute the operation
			webElement.sendKeys( variableElementDataModel.getValue() );
			webElement.sendKeys(Keys.TAB);
		}catch (WebDriverException webDriverException){
			throw new ElementInvalidOperationException( getName(), element.getName(), element.getBaseElement().getSelector(), webDriverException );
		}
		
	}

	@Override
	public void setXMLAttribute(Document document, Element element) {
		Attr attr = document.createAttribute( ATTR_FILL_VARIABLE_ELEMENT_PATH );
		attr.setValue( variableElementDataModel.getPathTag() );
		element.setAttributeNode( attr );			
	}
	
}
