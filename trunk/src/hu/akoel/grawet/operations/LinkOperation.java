package hu.akoel.grawet.operations;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import hu.akoel.grawet.elements.ElementBase;
import hu.akoel.grawet.elements.ParameterizedElement;

public class LinkOperation implements ElementOperation{

	/**
	 * 
	 * Executes a Click action on the WebElement (Link)
	 * 
	 */
	@Override
	public void doAction( WebDriver driver, ParameterizedElement element ) {
		ElementBase elementBase = element.getElement();
		
		//Searching for the element - waiting for it
		WebDriverWait wait = new WebDriverWait(driver, 10);		
		wait.until(ExpectedConditions.elementToBeClickable( elementBase.getBy() ) );		

		WebElement webElement = driver.findElement(elementBase.getBy());
	
		//Execute the operation
		//element.getDriver().findElement(element.getBy()).click();
		
		//Sajnos csak a javascipt hivassal mukodik. a webElement.click() hatasara nem tortenik semmi
		//Feltehetoleg idozitesi problema, mert debug-kor mukodik
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", webElement);
		
	}
}
