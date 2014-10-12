package hu.akoel.grawit.core.operations;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import hu.akoel.grawit.ElementProgressInterface;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamElementDataModel;
import hu.akoel.grawit.exceptions.ElementInvalidOperationException;

public class ClearOperation extends ElementOperationAdapter{
	private static final String NAME = "CLEAR";
	
	public static String getStaticName(){
		return NAME;
	}
	
	@Override
	public String getName() {		
		return getStaticName();
	}
	
	@Override
	public void doOperation(WebDriver driver, ParamElementDataModel element, WebElement webElement, ElementProgressInterface elementProgress) throws ElementInvalidOperationException {

		try{
			
			//Execute the operation
			webElement.clear();
			
		}catch (WebDriverException webDriverException){
			throw new ElementInvalidOperationException( getName(), element.getName(), element.getBaseElement().getSelector(), webDriverException );
		}
		
	}
	
	@Override
	public void setXMLAttribute(Document document, Element element) {
		//No parameter, no operation				
	}
	
}
