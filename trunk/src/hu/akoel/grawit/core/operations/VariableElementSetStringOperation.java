package hu.akoel.grawit.core.operations;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import hu.akoel.grawit.core.treenodedatamodel.BaseElementDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.ScriptBaseElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.VariableBaseElementDataModel;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.exceptions.ElementException;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;
import hu.akoel.grawit.gui.interfaces.progress.ElementProgressInterface;

public class VariableElementSetStringOperation extends ElementOperationAdapter{
	
	private static final String NAME = "SETSTRINGTOVARIABLE";
	private static final String SET_STRING_PATH = "string";
	
	//--- Data model
	private String stringToParameter;
	//---
	
	public VariableElementSetStringOperation( String stringToParameter ){
		this.stringToParameter = stringToParameter;
		
	}
	
	public VariableElementSetStringOperation( Element element, Tag rootTag, Tag tag ) throws XMLMissingAttributePharseException{
		
		//ADD_STRING_TO_PARAMETERS_PATH
		if( !element.hasAttribute( SET_STRING_PATH ) ){
			throw new XMLMissingAttributePharseException( rootTag, tag, SET_STRING_PATH );			
		}
		stringToParameter = element.getAttribute( SET_STRING_PATH );	
		
	}

	public String getStringToShow() {
		return stringToParameter;
	}

	public static String getStaticName(){
		return NAME;
	}
	
	@Override
	public String getName() {		
		return getStaticName();
	}
		
	@Override
	public void doOperation(WebDriver driver, BaseElementDataModelAdapter baseElement, WebElement webElement, ElementProgressInterface elementProgress) throws ElementException {
		
		//HA VARIABLEBASEELEMENT - annak kell lennie
		if( baseElement instanceof VariableBaseElementDataModel ){

			((VariableBaseElementDataModel)baseElement).setValue( stringToParameter );
			
		}	
	}
	
	@Override
	public void setXMLAttribute(Document document, Element element) {
		
		Attr attr = document.createAttribute( SET_STRING_PATH );
		attr.setValue( stringToParameter );
		element.setAttributeNode(attr);	
		
	}

	@Override
	public Object clone() {

		String stringToCompare = new String( this.stringToParameter );
		
		return new VariableElementSetStringOperation(stringToCompare);
	}
		
	@Override
	public String getOperationToString() {		
		return "SetStringToVariableElement()";
	}
	
}
