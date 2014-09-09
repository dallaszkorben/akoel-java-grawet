package hu.akoel.grawit.core.operations;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import hu.akoel.grawit.core.treenodedatamodel.base.BaseElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.variable.VariableElementDataModel;
import hu.akoel.grawit.enums.IdentificationType;
import hu.akoel.grawit.enums.Operation;
import hu.akoel.grawit.enums.VariableSample;
import hu.akoel.grawit.exceptions.ElementException;
import hu.akoel.grawit.exceptions.ElementInvalidSelectorException;
import hu.akoel.grawit.exceptions.ElementNotFoundException;
import hu.akoel.grawit.exceptions.ElementTimeoutException;

public class FieldOperation implements ElementOperationInterface{
	private VariableElementDataModel parameter;
	
	public FieldOperation( VariableElementDataModel parameter ){
		this.parameter = parameter;
	}
	
	@Override
	public Operation getOperation() {
		return Operation.FIELD;
	}
	
	/**
	 * 
	 * Executes a Click action on the WebElement (Checkbox)
	 * 
	 */
	@Override
	public void doAction( WebDriver driver, ParamElementDataModel element ) throws ElementException{
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
		
		//throw new ElementException( elementBase.getName(), elementBase.getBy().toString(), e );
		
		//Ha valtozokent van deffinialva es muvelet elott kell menteni az erteket
		if( baseElement.getVariableSample().equals( VariableSample.PRE ) ){
				
			//Elmenti az elem tartalmat a valtozoba
			element.setVariableValue( webElement.getText() );
		}
		
		//Execute the operation
		webElement.clear();
		webElement.sendKeys( parameter.getValue() );
		
		//Ha valtozokent van deffinialva es muvelet utan kell menteni az erteket
		if( baseElement.getVariableSample().equals( VariableSample.POST ) ){
				
			//Elmenti az elem tartalmat a valtozoba
			//webElement.sendKeys(Keys.TAB);
			element.setVariableValue( webElement.getAttribute("value") );
		
		}
	}

	@Override
	public VariableElementDataModel getVariableElement() {
		return parameter;
	}
	
}
