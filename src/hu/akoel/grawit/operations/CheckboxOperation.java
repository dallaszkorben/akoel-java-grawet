package hu.akoel.grawit.operations;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import hu.akoel.grawit.IdentificationType;
import hu.akoel.grawit.elements.ElementBase;
import hu.akoel.grawit.elements.ParameterizedElement;

public class CheckboxOperation implements ElementOperation{
	
	
	/**
	 * 
	 * Executes a Click action on the WebElement (Checkbox)
	 * 
	 */
	@Override
	public void doAction( WebDriver driver, ParameterizedElement element ) {
		ElementBase elementBase = element.getElement();
		
		//Searching for the element - waiting for it
		WebDriverWait wait = new WebDriverWait( driver, 10 );	
		
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

		//Sajnos csak a javascipt hivassal mukodik. a webElement.click() hatasara nem tortenik semmi
		//Feltehetoleg idozitesi problema, mert debug-kor mukodik
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", webElement);
		
	}
}
