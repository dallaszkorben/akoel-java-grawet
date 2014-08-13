package hu.akoel.grawit.core.operations;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import hu.akoel.grawit.IdentificationType;
import hu.akoel.grawit.VariableSample;
import hu.akoel.grawit.core.elements.ElementBase;
import hu.akoel.grawit.core.elements.ParameterizedElement;
import hu.akoel.grawit.core.parameter.ElementParameter;
import hu.akoel.grawit.exceptions.ElementException;

public class FieldOperation implements ElementOperation{
	private ElementParameter parameter;
	
	public FieldOperation( ElementParameter parameter ){
		this.parameter = parameter;
	}
	
	/**
	 * 
	 * Executes a Click action on the WebElement (Checkbox)
	 * 
	 */
	@Override
	public void doAction( WebDriver driver, ParameterizedElement element ) throws ElementException{
		ElementBase elementBase = element.getElement();
		
		//Searching for the element - waiting for it
		WebDriverWait wait = new WebDriverWait(driver, 10);
		
		By by = null;
		
		//ID
		if( elementBase.getIdentificationType().equals(IdentificationType.ID)){
			by = By.id( elementBase.getIdentifier() );
		//CSS
		}else if( elementBase.getIdentificationType().equals(IdentificationType.CSS)){
			by = By.cssSelector( elementBase.getIdentifier() );
		}
		
		wait.until(ExpectedConditions.elementToBeClickable( by ) );		

		WebElement webElement = driver.findElement( by );
		
		//throw new ElementException( elementBase.getName(), elementBase.getBy().toString(), e );
		
		//Ha valtozokent van deffinialva es muvelet elott kell menteni az erteket
		if( elementBase.getVariableSample().equals( VariableSample.PRE ) ){
				
			//Elmenti az elem tartalmat a valtozoba
			element.setVariableValue( webElement.getText() );
		}
		
		//Execute the operation
		webElement.clear();
		webElement.sendKeys( parameter.getValue() );
		
		//Ha valtozokent van deffinialva es muvelet utan kell menteni az erteket
		if( elementBase.getVariableSample().equals( VariableSample.POST ) ){
				
			//Elmenti az elem tartalmat a valtozoba
			//webElement.sendKeys(Keys.TAB);
			element.setVariableValue( webElement.getAttribute("value") );
		
		}
	}
}
