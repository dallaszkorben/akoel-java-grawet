package hu.akoel.grawit.core.operations;

import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import hu.akoel.grawit.core.treenodedatamodel.base.BaseElementDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.ScriptBaseElementDataModel;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.exceptions.ElementException;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;
import hu.akoel.grawit.gui.interfaces.progress.ProgressIndicatorInterface;

public class ScriptElementAddStringToParametersOperation extends ScriptOperationAdapter{
	
	private static final String NAME = "ADDSTRINGTOPARAMETERS";
	private static final String ADD_STRING_TO_PARAMETERS_PATH = "string";
	
	//--- Data model
	private String stringToParameter;
	//---
	
	public ScriptElementAddStringToParametersOperation( String stringToParameter ){
		this.stringToParameter = stringToParameter;
		
	}
	
	public ScriptElementAddStringToParametersOperation( Element element, Tag rootTag, Tag tag ) throws XMLMissingAttributePharseException{
		
		//ADD_STRING_TO_PARAMETERS_PATH
		if( !element.hasAttribute( ADD_STRING_TO_PARAMETERS_PATH ) ){
			throw new XMLMissingAttributePharseException( rootTag, tag, ADD_STRING_TO_PARAMETERS_PATH );			
		}
		stringToParameter = element.getAttribute( ADD_STRING_TO_PARAMETERS_PATH );	
		
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
	public void doOperation(WebDriver driver, BaseElementDataModelAdapter baseElement, WebElement webElement, ProgressIndicatorInterface elementProgress, String tab, Set<String> definedElementSet, boolean needToPrintSource ) throws ElementException {
		
		//HA SPECIALBASEELEMENT - annak kell lennie
		if( baseElement instanceof ScriptBaseElementDataModel ){

			if( needToPrintSource ){
				outputScripClass(driver, baseElement, webElement, elementProgress, tab, definedElementSet );		
				elementProgress.printSource( tab + baseElement.getNameAsScript() + ".addParameter( \"" + stringToParameter + "\" );" );
				elementProgress.printSource( "" );
			}
			
			((ScriptBaseElementDataModel)baseElement).addParameter( stringToParameter );
			
		}
	
	}
	
	@Override
	public void setXMLAttribute(Document document, Element element) {
		
		Attr attr = document.createAttribute( ADD_STRING_TO_PARAMETERS_PATH );
		attr.setValue( stringToParameter );
		element.setAttributeNode(attr);	
		
	}

	@Override
	public Object clone() {

		String stringToCompare = new String( this.stringToParameter );
		
		return new ScriptElementAddStringToParametersOperation(stringToCompare);
	}
		
	@Override
	public String getOperationNameToString() {		
		return "AddStringToScriptElementParameter()";
	}
	
}
