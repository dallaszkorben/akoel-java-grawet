package hu.akoel.grawet.operation;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import hu.akoel.grawet.VariableSample;
import hu.akoel.grawet.element.ParameterizedElement;
import hu.akoel.grawet.element.PureElement;
import hu.akoel.grawet.parameter.ElementParameter;

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
	public void doAction( ParameterizedElement element ) {
		PureElement pureElement = element.getElement();
		
		//Searching for the element - waiting for it
		WebDriverWait wait = new WebDriverWait(pureElement.getDriver(), 10);		
		wait.until(ExpectedConditions.elementToBeClickable( pureElement.getBy() ) );		

		WebElement webElement = pureElement.getDriver().findElement(pureElement.getBy());
		
		//Ha valtozokent van deffinialva es muvelet elott kell menteni az erteket
		if( pureElement.getVariableSample().equals( VariableSample.PRE ) ){
				
			//Elmenti az elem tartalmat a valtozoba
			element.setVariableValue( webElement.getText() );
		}
		
		//Execute the operation
		webElement.clear();
		webElement.sendKeys( parameter.getValue() );
		
		//Ha valtozokent van deffinialva es muvelet utan kell menteni az erteket
		if( pureElement.getVariableSample().equals( VariableSample.POST ) ){
				
			//Elmenti az elem tartalmat a valtozoba
			//webElement.sendKeys(Keys.TAB);
			element.setVariableValue( webElement.getAttribute("value") );
System.err.println(webElement.getAttribute("value"));			
		}
	}
}
