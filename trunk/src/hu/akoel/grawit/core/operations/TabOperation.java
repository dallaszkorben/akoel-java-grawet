package hu.akoel.grawit.core.operations;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import hu.akoel.grawit.ElementProgressInterface;
import hu.akoel.grawit.core.treenodedatamodel.base.NormalBaseElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamElementDataModel;
import hu.akoel.grawit.exceptions.ElementException;
import hu.akoel.grawit.exceptions.ElementInvalidOperationException;

public class TabOperation extends ElementOperationAdapter{
	
	private final static String NAME = "TAB";
	
	public static String getStaticName(){
		return NAME;
	}
	
	@Override
	public String getName() {		
		return getStaticName();
	}
	
	@Override
	public void doOperation(WebDriver driver, ParamElementDataModel element, WebElement webElement, ElementProgressInterface elementProgress) throws ElementException {
	
		if( element.getBaseElement() instanceof NormalBaseElementDataModel ){
		
			try{
				//Execute the operation
				//webElement.clear();
				//webElement.sendKeys( parameter.getValue() );
				webElement.sendKeys(Keys.TAB);
			}catch (WebDriverException webDriverException){
				throw new ElementInvalidOperationException( getName(), element.getName(), ((NormalBaseElementDataModel)element.getBaseElement()).getSelector(), webDriverException );
			}
		}
	}
	
	@Override
	public void setXMLAttribute(Document document, Element element) {
		//No attribute		
	}

	@Override
	public Object clone() {
		
		return new TabOperation();
	}

}
