package hu.akoel.grawit.core.operations;

import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import hu.akoel.grawit.core.treenodedatamodel.base.BaseElementDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.NormalBaseElementDataModel;
import hu.akoel.grawit.exceptions.ElementException;
import hu.akoel.grawit.exceptions.ElementInvalidOperationException;
import hu.akoel.grawit.gui.interfaces.progress.ProgressIndicatorInterface;

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
	public void doOperation(WebDriver driver, BaseElementDataModelAdapter baseElement, WebElement webElement, ProgressIndicatorInterface elementProgress, String tab, Set<String> definedElementSet, boolean needToPrintSource ) throws ElementException {

		if( needToPrintSource ){
			elementProgress.printSource( tab + "webElement.click();");
		}
		
		try{
			
			webElement.click();
		
		}catch (WebDriverException webDriverException){
			throw new ElementInvalidOperationException( getName(), baseElement.getName(), ((NormalBaseElementDataModel)baseElement).getSelector(), webDriverException );
		}
		
		//new Actions(driver).click(webElement).perform();		
		////Sajnos csak a javascipt hivassal mukodik. a webElement.click() hatasara nem tortenik semmi
		////Feltehetoleg idozitesi problema, mert debug-kor mukodik
		//JavascriptExecutor executor = (JavascriptExecutor)driver;
		//executor.executeScript("arguments[0].click();", webElement);
		
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
