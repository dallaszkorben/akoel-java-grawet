package hu.akoel.grawit.core.operations;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import hu.akoel.grawit.core.treenodedatamodel.base.BaseElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.variable.VariableElementDataModel;
import hu.akoel.grawit.enums.IdentificationType;
import hu.akoel.grawit.enums.Operation;
import hu.akoel.grawit.exceptions.ElementException;
import hu.akoel.grawit.exceptions.ElementInvalidSelectorException;
import hu.akoel.grawit.exceptions.ElementNotFoundException;
import hu.akoel.grawit.exceptions.ElementTimeoutException;

public class ButtonOperation implements ElementOperationInterface{

	@Override
	public Operation getOperation() {
		return Operation.BUTTON;
	}
	
	/**
	 * 
	 * Executes a Click action on the WebElement (Buton)
	 * @throws ElementInvalidSelectorException 
	 * @throws ElementNotFoundException 
	 * @throws ElementTimeoutException 
	 * 
	 */
	@Override
	public void doAction( WebDriver driver, ParamElementDataModel element ) throws ElementException {
		BaseElementDataModel baseElement = element.getBaseElement();
		
		//Searching for the element - waiting for it
		WebDriverWait wait = new WebDriverWait(driver, 10);
		
		By by = null;
		
		//ID
		if( baseElement.getIdentificationType().equals(IdentificationType.ID)){
			by = By.id( baseElement.getIdentifier() );
		//CSS	
		}else if( baseElement.getIdentificationType().equals(IdentificationType.CSS)){
			by = By.cssSelector( baseElement.getIdentifier() );
		}
		
		WebElement webElement = null;
		
		try{
			webElement = driver.findElement( by );
		}catch ( org.openqa.selenium.InvalidSelectorException invalidSelectorException ){
			throw new ElementInvalidSelectorException(element.getName(), baseElement.getIdentifier(), invalidSelectorException );
		}catch ( org.openqa.selenium.NoSuchElementException noSuchElementException ){
			throw new ElementNotFoundException( element.getName(), baseElement.getIdentifier(), noSuchElementException );
		}
		
		if( null == webElement ){
			throw new ElementNotFoundException( element.getName(), baseElement.getIdentifier(), new Exception() );
		}
		
		try{
			wait.until(ExpectedConditions.elementToBeClickable( by ) );
		
		}catch( org.openqa.selenium.TimeoutException timeOutException ){
			throw new ElementTimeoutException( element.getName(), baseElement.getIdentifier(), timeOutException );
		}
	
		//Execute the operation
		//element.getDriver().findElement(element.getBy()).click();
		
		//Sajnos csak a javascipt hivassal mukodik. a webElement.click() hatasara nem tortenik semmi
		//Feltehetoleg idozitesi problema, mert debug-kor mukodik
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", webElement);
		
	}

	@Override
	public VariableElementDataModel getVariableElement() {
		return null;
	}

}
