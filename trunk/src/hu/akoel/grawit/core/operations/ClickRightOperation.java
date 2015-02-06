package hu.akoel.grawit.core.operations;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import hu.akoel.grawit.core.treenodedatamodel.BaseElementDataModelAdapter;
import hu.akoel.grawit.exceptions.ElementException;
import hu.akoel.grawit.gui.interfaces.progress.ElementProgressInterface;

public class ClickRightOperation extends ElementOperationAdapter{

	private static final String NAME = "RIGHTCLICK";
	
	public static String getStaticName(){
		return NAME;
	}
	
	@Override
	public String getName() {
		return getStaticName();
	}
	
	@Override
	public void doOperation(WebDriver driver, BaseElementDataModelAdapter baseElement, WebElement webElement, ElementProgressInterface elementProgress) throws ElementException {


		new Actions(driver).contextClick(webElement).perform();
		
/*		try{
		
		}catch (WebDriverException webDriverException){
			throw new ElementInvalidOperationException( getName(), baseElement.getName(), ((NormalBaseElementDataModel)baseElement).getSelector(), webDriverException );
		}
*/		
	}
	
	@Override
	public void setXMLAttribute(Document document, Element element) {
		//No parameter, no operation			
	}

	@Override
	public Object clone() {		
		return new ClickRightOperation();
	}

	@Override
	public String getOperationToString() {		
		return "RightClick()";
	}
}
