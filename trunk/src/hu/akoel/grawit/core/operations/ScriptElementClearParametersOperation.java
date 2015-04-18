package hu.akoel.grawit.core.operations;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import hu.akoel.grawit.core.treenodedatamodel.base.BaseElementDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.ScriptBaseElementDataModel;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.exceptions.ElementException;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;
import hu.akoel.grawit.gui.interfaces.progress.ElementProgressInterface;

public class ScriptElementClearParametersOperation extends ScriptOperationAdapter{
	
	private static final String NAME = "CLEARPARAMETERS";
	
	//--- Data model
	//---
	
	public ScriptElementClearParametersOperation(){
	}
	
	public ScriptElementClearParametersOperation( Element element, Tag rootTag, Tag tag ) throws XMLMissingAttributePharseException{
	}

	public static String getStaticName(){
		return NAME;
	}
	
	@Override
	public String getName() {		
		return getStaticName();
	}
		
	@Override
	public void doOperation(WebDriver driver, BaseElementDataModelAdapter baseElement, WebElement webElement, ElementProgressInterface elementProgress, String tab) throws ElementException {
		
		//HA SPECIALBASEELEMENT - annak kell lennie
		if( baseElement instanceof ScriptBaseElementDataModel ){

			if( null != elementProgress ){
				outputScripClass(driver, baseElement, webElement, elementProgress, tab );				
				elementProgress.outputCommand( tab + baseElement.getNameAsScript() + ".clearParameters();" );
				elementProgress.outputCommand( "" );
			}

			((ScriptBaseElementDataModel)baseElement).clearParameters();
			
		}	
	}
	
	@Override
	public void setXMLAttribute(Document document, Element element) {	
	}

	@Override
	public Object clone() {
		
		return new ScriptElementClearParametersOperation();
	}
	
	@Override
	public String getOperationNameToString() {		
		return "ClearParametersForScriptElement()";
	}
}
