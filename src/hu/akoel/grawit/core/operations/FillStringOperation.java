package hu.akoel.grawit.core.operations;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.ElementProgressInterface;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamElementDataModel;
import hu.akoel.grawit.enums.SelectorType;
import hu.akoel.grawit.enums.list.ListEnumVariableSample;
import hu.akoel.grawit.exceptions.ElementException;
import hu.akoel.grawit.exceptions.ElementInvalidOperationException;
import hu.akoel.grawit.exceptions.ElementInvalidSelectorException;
import hu.akoel.grawit.exceptions.ElementNotFoundSelectorException;
import hu.akoel.grawit.exceptions.ElementTimeoutException;

public class FillStringOperation implements ElementOperationInterface{
	
	private static final String NAME = CommonOperations.getTranslation("editor.label.param.elementtype.field.fillvariable");
	
	private String stringToShow;
	
	public FillStringOperation( String stringToShow ){
		this.stringToShow = stringToShow;
	}
	
	@Override
	public String getTranslatedName() {
		return NAME;
	}
	
	/**
	 * 
	 * Executes the action on the WebElement (Field)
	 * 
	 */
	@Override
	public void doAction( WebDriver driver, ParamElementDataModel element, ElementProgressInterface elementProgress ) throws ElementException{
	
		if( null != elementProgress ){
			elementProgress.elementStarted( element.getName() );
		}
		
		BaseElementDataModel baseElement = element.getBaseElement();
		By by = null;
		WebElement webElement = null;
		
		//Searching for the element - waiting for it
		WebDriverWait wait = new WebDriverWait(driver, 10);
						
		//Selector meszerzese
		if( baseElement.getSelectorType().equals(SelectorType.ID)){
			by = By.id( baseElement.getSelector() );
		//CSS
		}else if( baseElement.getSelectorType().equals(SelectorType.CSS)){
			by = By.cssSelector( baseElement.getSelector() );
		}
						
		//Varakozik az elem megjeleneseig, de max 10 mp-ig
		try{
			wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
			//wait.until(ExpectedConditions.elementToBeClickable( by ) );
		
		}catch( org.openqa.selenium.TimeoutException timeOutException ){
			throw new ElementTimeoutException( element.getName(), baseElement.getSelector(), timeOutException );
		}
		
		try{
			webElement = driver.findElement( by );
		}catch ( org.openqa.selenium.InvalidSelectorException invalidSelectorException ){
			throw new ElementInvalidSelectorException(element.getName(), baseElement.getSelector(), invalidSelectorException );
		}catch ( org.openqa.selenium.NoSuchElementException noSuchElementException ){
			throw new ElementNotFoundSelectorException( element.getName(), baseElement.getSelector(), noSuchElementException );
		}
		
		if( null == webElement ){
			throw new ElementNotFoundSelectorException( element.getName(), baseElement.getSelector(), new Exception() );
		}
/*		
		while( !webElement.isDisplayed() ){
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {}
		}	
*/		
		//throw new ElementException( elementBase.getName(), elementBase.getBy().toString(), e );
		
		//Ha valtozokent van deffinialva es muvelet elott kell menteni az erteket
		if( baseElement.getVariableSample().equals( ListEnumVariableSample.PRE ) ){
				
			//Elmenti az elem tartalmat a valtozoba
			element.getBaseElement().setVariableValue( webElement.getText() );
		}
		
		try{
			//Execute the operation
			webElement.sendKeys( stringToShow );
			webElement.sendKeys(Keys.TAB);
		}catch (WebDriverException webDriverException){
			throw new ElementInvalidOperationException( getTranslatedName(), element.getName(), baseElement.getSelector(), webDriverException );
		}
		
		//Ha valtozokent van deffinialva es muvelet utan kell menteni az erteket
		if( baseElement.getVariableSample().equals( ListEnumVariableSample.POST ) ){
				
			//Elmenti az elem tartalmat a valtozoba
			element.getBaseElement().setVariableValue( webElement.getAttribute("value") );		
		}
		
		if( null != elementProgress ){
			elementProgress.elementEnded( element.getName() );
		}
	}

	public String getStringToShow() {
		return stringToShow;
	}

}
