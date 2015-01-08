package hu.akoel.grawit.core.operations;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import hu.akoel.grawit.core.treenodedatamodel.BaseElementDataModelAdapter;
import hu.akoel.grawit.exceptions.ElementException;
import hu.akoel.grawit.gui.interfaces.progress.ElementProgressInterface;

public class ClickOperation extends ElementOperationAdapter{

	private static final String NAME = "CLICK";
	
	public static String getStaticName(){
		return NAME;
	}
	
	@Override
	public String getName() {
		return getStaticName();
	}
	
	@Override
	public void doOperation(WebDriver driver, BaseElementDataModelAdapter baseElement, WebElement webElement, ElementProgressInterface elementProgress) throws ElementException {
	
		//Sajnos csak a javascipt hivassal mukodik. a webElement.click() hatasara nem tortenik semmi
		//Feltehetoleg idozitesi problema, mert debug-kor mukodik

elementProgress.outputCommand( "		executor = (JavascriptExecutor)driver;" );			
elementProgress.outputCommand( "		executor.executeScript(\"arguments[0].click();\", webElement);" );	

		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", webElement);
		
	}
	
	@Override
	public void setXMLAttribute(Document document, Element element) {
		//No parameter, no operation			
	}

	@Override
	public Object clone() {		
		return new ClickOperation();
	}

}
