package hu.akoel.grawit.core.operations;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import hu.akoel.grawit.ElementProgressInterface;
import hu.akoel.grawit.core.treenodedatamodel.base.ScriptBaseElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamElementDataModel;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.exceptions.ElementException;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;

public class SpecialBaseClearParametersOperation extends ElementOperationAdapter{
	
	private static final String NAME = "CLEARPARAMETERS";
	
	//--- Data model
	//---
	
	public SpecialBaseClearParametersOperation(){
	}
	
	public SpecialBaseClearParametersOperation( Element element, Tag rootTag, Tag tag ) throws XMLMissingAttributePharseException{
	}

	public static String getStaticName(){
		return NAME;
	}
	
	@Override
	public String getName() {		
		return getStaticName();
	}
		
	@Override
	public void doOperation(WebDriver driver, ParamElementDataModel element, WebElement webElement, ElementProgressInterface elementProgress) throws ElementException {
		
		//HA SPECIALBASEELEMENT - annak kell lennie
		if( element.getBaseElement() instanceof ScriptBaseElementDataModel ){

			((ScriptBaseElementDataModel)element.getBaseElement()).clearParameters();
			
		}
	
	}
	
	@Override
	public void setXMLAttribute(Document document, Element element) {	
	}

	@Override
	public Object clone() {
		
		return new SpecialBaseClearParametersOperation();
	}
	
}
