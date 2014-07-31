package hu.akoel.grawet.operations;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import hu.akoel.grawet.elements.ElementBase;
import hu.akoel.grawet.elements.ParameterizedElement;

public class CheckboxOperation implements ElementOperation{
	
	
	/**
	 * 
	 * Executes a Click action on the WebElement (Checkbox)
	 * 
	 */
	@Override
	public void doAction( ParameterizedElement element ) {
		ElementBase elementBase = element.getElement();
		
		//Searching for the element - waiting for it
		WebDriverWait wait = new WebDriverWait( elementBase.getDriver(), 10 );		
		wait.until(ExpectedConditions.elementToBeClickable( elementBase.getBy() ) );		

		WebElement webElement = elementBase.getDriver().findElement(elementBase.getBy());
/*		
		//Ha valtozokent van deffinialva es muvelet elott kell menteni az erteket
		if( element.isVariable() ){
			if( element.getVariableSample().equals( VariableSample.PRE ) ){
				
				//Elmenti az elem tartalmat a valtozoba
				element.setVariableValue( webElement.getText() );
			}
		}
*/		
		//Execute the operation
		//element.getDriver().findElement(element.getBy()).click();
		
		//Sajnos csak a javascipt hivassal mukodik. a webElement.click() hatasara nem tortenik semmi
		//Feltehetoleg idozitesi problema, mert debug-kor mukodik
		JavascriptExecutor executor = (JavascriptExecutor)elementBase.getDriver();
		executor.executeScript("arguments[0].click();", webElement);
		
	}
}
