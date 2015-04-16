package hu.akoel.grawit.core.operations;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import hu.akoel.grawit.core.treenodedatamodel.base.BaseElementDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.NormalBaseElementDataModel;
import hu.akoel.grawit.exceptions.ElementException;
import hu.akoel.grawit.exceptions.ElementInvalidOperationException;
import hu.akoel.grawit.gui.interfaces.progress.ElementProgressInterface;

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
	public void doOperation(WebDriver driver, BaseElementDataModelAdapter baseElement, WebElement webElement, ElementProgressInterface elementProgress, String tab) throws ElementException {
		
		if( baseElement instanceof NormalBaseElementDataModel ){
		
			try{
				//Execute the operation
				elementProgress.outputCommand( tab + "webElement.sendKeys(Keys.TAB);" );
				webElement.sendKeys(Keys.TAB);
			}catch (WebDriverException webDriverException){
				throw new ElementInvalidOperationException( getName(), baseElement.getName(), ((NormalBaseElementDataModel)baseElement).getSelector(), webDriverException );
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

	@Override
	public String getOperationNameToString() {		
		return "Tab()";
	}
}
