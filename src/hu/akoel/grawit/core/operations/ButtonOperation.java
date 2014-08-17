package hu.akoel.grawit.core.operations;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import hu.akoel.grawit.IdentificationType;
import hu.akoel.grawit.core.datamodel.elements.BaseElementDataModel;
import hu.akoel.grawit.core.datamodel.elements.ParamElementDataModel;

public class ButtonOperation implements ElementOperationInterface{

	@Override
	public Operation getOperation() {
		return Operation.BUTTON;
	}
	
	/**
	 * 
	 * Executes a Click action on the WebElement (Buton)
	 * 
	 */
	@Override
	public void doAction( WebDriver driver, ParamElementDataModel element ) {
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
	
		//Execute the operation
		//element.getDriver().findElement(element.getBy()).click();
		
		//Sajnos csak a javascipt hivassal mukodik. a webElement.click() hatasara nem tortenik semmi
		//Feltehetoleg idozitesi problema, mert debug-kor mukodik
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", webElement);
		
	}

}
