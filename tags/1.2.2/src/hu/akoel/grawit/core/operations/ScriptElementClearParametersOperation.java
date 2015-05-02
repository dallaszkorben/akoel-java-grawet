package hu.akoel.grawit.core.operations;

import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import hu.akoel.grawit.core.treenodedatamodel.base.BaseElementDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.ScriptBaseElementDataModel;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.exceptions.ElementException;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;
import hu.akoel.grawit.gui.interfaces.progress.ProgressIndicatorInterface;

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
	public void doOperation(WebDriver driver, BaseElementDataModelAdapter baseElement, WebElement webElement, ProgressIndicatorInterface elementProgress, String tab, Set<String> definedElementSet, boolean needToPrintSource ) throws ElementException {
		
		//HA SPECIALBASEELEMENT - annak kell lennie
		if( baseElement instanceof ScriptBaseElementDataModel ){

			if( needToPrintSource ){
				outputScripClass(driver, baseElement, webElement, elementProgress, tab, definedElementSet );				
				elementProgress.printSource( tab + baseElement.getNameAsScript() + ".clearParameters();" );
				elementProgress.printSource( "" );
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
