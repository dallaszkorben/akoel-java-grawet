package hu.akoel.grawit.core.operations;

import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import hu.akoel.grawit.core.treenodedatamodel.base.BaseElementDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.NormalBaseElementDataModel;
import hu.akoel.grawit.exceptions.ElementInvalidOperationException;
import hu.akoel.grawit.gui.interfaces.progress.ProgressIndicatorInterface;

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
	public void doOperation(WebDriver driver, BaseElementDataModelAdapter baseElement, WebElement webElement, ProgressIndicatorInterface elementProgress, String tab, Set<String> definedElementSet, boolean needToPrintSource ) throws ElementInvalidOperationException {
		
		if( needToPrintSource ){
			elementProgress.printSource( tab + "webElement.clear();" );
		}
		
		if( baseElement instanceof NormalBaseElementDataModel ){
			try{
				webElement.clear();			
			}catch (WebDriverException webDriverException){
				throw new ElementInvalidOperationException((NormalBaseElementDataModel)baseElement, this, webDriverException );
			}
		}
		
	}
	
	@Override
	public void setXMLAttribute(Document document, Element element) {
		//No parameter, no operation				
	}
	
	@Override
	public Object clone(){
		
		ClearOperation cloned = new ClearOperation();
		
		return cloned;
		
	}

	@Override
	public String getOperationNameToString() {		
		return "Clear Field()";
	}
}
