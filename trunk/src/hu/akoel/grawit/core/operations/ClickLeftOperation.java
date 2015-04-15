package hu.akoel.grawit.core.operations;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import hu.akoel.grawit.core.treenodedatamodel.base.BaseElementDataModelAdapter;
import hu.akoel.grawit.exceptions.ElementException;
import hu.akoel.grawit.gui.interfaces.progress.ElementProgressInterface;

public class ClickLeftOperation extends ElementOperationAdapter{

	private static final String NAME = "LEFTCLICK";
	
	public static String getStaticName(){
		return NAME;
	}
	
	@Override
	public String getName() {
		return getStaticName();
	}
	
	@Override
	public ArrayList<String> doOperation(WebDriver driver, BaseElementDataModelAdapter baseElement, WebElement webElement, ElementProgressInterface elementProgress) throws ElementException {
		ArrayList<String> returnArray = new ArrayList<>();
		
		webElement.click();		

		//new Actions(driver).click(webElement).perform();		
		
		////Sajnos csak a javascipt hivassal mukodik. a webElement.click() hatasara nem tortenik semmi
		////Feltehetoleg idozitesi problema, mert debug-kor mukodik
		//JavascriptExecutor executor = (JavascriptExecutor)driver;
		//executor.executeScript("arguments[0].click();", webElement);
		returnArray.add("webElement.click()");
		return returnArray;
	}
	
	@Override
	public void setXMLAttribute(Document document, Element element) {
		//No parameter, no operation			
	}

	@Override
	public Object clone() {		
		return new ClickLeftOperation();
	}

	@Override
	public String getOperationNameToString() {		
		return "LeftClick()";
	}
}
