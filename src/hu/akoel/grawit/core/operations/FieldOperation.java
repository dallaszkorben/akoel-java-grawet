package hu.akoel.grawit.core.operations;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import hu.akoel.grawit.core.datamodel.elements.BaseElementDataModel;
import hu.akoel.grawit.core.datamodel.elements.ParamElementDataModel;
import hu.akoel.grawit.core.parameter.ElementParameter;
import hu.akoel.grawit.enums.IdentificationType;
import hu.akoel.grawit.enums.VariableSample;
import hu.akoel.grawit.exceptions.ElementException;

public class FieldOperation implements ElementOperationInterface{
	private ElementParameter parameter;
	
	public FieldOperation( ElementParameter parameter ){
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
		
		wait.until(ExpectedConditions.elementToBeClickable( by ) );		

		WebElement webElement = driver.findElement( by );
		
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
}
