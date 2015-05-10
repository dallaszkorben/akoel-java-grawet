package hu.akoel.grawit.core.operations;

import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import hu.akoel.grawit.core.operation.interfaces.ElementOperationAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseElementDataModelAdapter;
import hu.akoel.grawit.exceptions.ElementException;
import hu.akoel.grawit.gui.interfaces.progress.ProgressIndicatorInterface;

public class MoveToElementOperation extends ElementOperationAdapter{

	private static final String NAME = "MOVETOELEMENT";
	
	public static String getStaticName(){
		return NAME;
	}
	
	@Override
	public String getName() {
		return getStaticName();
	}
	
	@Override
	public void doOperation(WebDriver driver, BaseElementDataModelAdapter baseElement, WebElement webElement, ProgressIndicatorInterface elementProgress, String tab, Set<String> definedElementSet, boolean needToPrintSource ) throws ElementException {

		if( needToPrintSource ){
			elementProgress.printSource( tab + "new Actions(driver).moveToElement(webElement).perform();" );
		}	
		
		new Actions(driver).moveToElement(webElement).perform();

	}
	
	@Override
	public void setXMLAttribute(Document document, Element element) {
		//No parameter, no operation			
	}

	@Override
	public Object clone() {		
		return new MoveToElementOperation();
	}

	@Override
	public String getOperationNameToString() {		
		return "MoveToElement()";
	}
}
