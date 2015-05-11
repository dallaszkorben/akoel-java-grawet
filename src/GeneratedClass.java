import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.WebDriverException;
 
import java.util.ArrayList;
 
import hu.akoel.grawit.core.treenodedatamodel.base.BaseElementDataModelAdapter;

public class GeneratedClass {
   public GeneratedClass() {}
   public void doAction(WebDriver driver, ArrayList<String> parameters, BaseElementDataModelAdapter baseElement ) throws java.lang.Exception {
		//There is proper parameter (frame name, waiting time [s]) 
		if( parameters.size() == 3 ){
			String frameName = parameters.get( 0 );
			Integer waitingTime = new Integer( parameters.get(1)  );

			org.openqa.selenium.support.ui.WebDriverWait wait = new WebDriverWait(driver, waitingTime);

			driver.switchTo().defaultContent();

			wait.until( org.openqa.selenium.support.ui.ExpectedConditions.frameToBeAvailableAndSwitchToIt("frameName"));

			driver.switchTo().defaultContent();
			driver.switchTo().frame( frameName );

		//Parameters are not good
		}else{

	           throw new java.lang.Exception( "More or less than 2 parameters" );

		}
   }
}