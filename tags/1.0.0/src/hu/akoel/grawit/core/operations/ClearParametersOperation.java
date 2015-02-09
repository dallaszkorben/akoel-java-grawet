package hu.akoel.grawit.core.operations;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import hu.akoel.grawit.core.treenodedatamodel.BaseElementDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.ScriptBaseElementDataModel;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.exceptions.ElementException;
import hu.akoel.grawit.exceptions.XMLBaseConversionPharseException;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;
import hu.akoel.grawit.gui.interfaces.progress.ElementProgressInterface;

public class ClearParametersOperation extends ElementOperationAdapter{
	
	private static final String NAME = "CLEARPARAMETER";	
	
	//--- Data model
	//---
	
	public ClearParametersOperation(){
	}

	public ClearParametersOperation( Element element, BaseRootDataModel baseRootDataModel, Tag rootTag, Tag tag, String nameAttrName, String nameAttrValue ) throws XMLBaseConversionPharseException, XMLMissingAttributePharseException{		
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

		//HA SPECIALBASEELEMENT - annak kell lennie
		if( baseElement instanceof ScriptBaseElementDataModel ){

			((ScriptBaseElementDataModel)baseElement).clearParameters();
			
		}
		
	}
	
	@Override
	public void setXMLAttribute(Document document, Element element) {		
	}

	@Override
	public Object clone() {
		
		return new ClearParametersOperation();
	}
	
	@Override
	public String getOperationToString() {		
		return "ClearParameters()";
	}
}
