import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.UnexpectedTagNameException;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.concurrent.TimeUnit;
import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

abstract class ScriptClass{
  ArrayList<String> parameters = new ArrayList<>();
  abstract public void runScript() throws Exception;
  public void addParameter( String parameter ){
    this.parameters.add( parameter );
  }
  public void clearParameters(){
    this.parameters.clear();
  }
  public Iterator<String> getParameterIterator(){
    return parameters.iterator();
  }
}

public class MyTest{ 

  WebDriverWait wait = null;
  By by = null;
  WebElement webElement = null;
  Select select = null;
  Integer index = 0;
  WebDriver driver = null;
  FirefoxProfile profile = null;
  JavascriptExecutor executor = null;
  List<WebElement> optionList;
  boolean found = false;
  String origText;
  String optionText;
  Matcher matcher;
  Pattern pattern;

  //For running as an Application from command line
  public static void main( String[] args ){
    Result result = JUnitCore.runClasses( MyTest.class);
    for (Failure failure : result.getFailures()) {
      System.out.println(failure.toString());
    }
  }

  @Test
  public void TestCheckbox(){

    int actualLoop = 0;
    int maxLoopNumber;
    int oneLoopLength;
    Date actualDate;
    Date startDate;

    DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
    capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
    driver = new InternetExplorerDriver(capabilities);

    //IMPLICIT WAIT
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

    //Script: Open Window (CLEARPARAMETERS) - script1435712256
    ScriptClass script1435712256 = new ScriptClass(){
      @Override
      public void runScript() throws Exception{
        String url = parameters.get(0);
        driver.get( url );
      }
    };
    script1435712256.clearParameters();

    script1435712256.addParameter( "http://sislands.com/coin70/week4/chkBoxTest.htm" );

    try{
      script1435712256.runScript();
    }catch( Exception e ){
      e.printStackTrace();
      fail( e.getMessage() );
    }

    //Element: Checkbox1 (GAINVALUETOELEMENT) - store1685520375
    by = By.cssSelector( "body > center:nth-child(1) > form:nth-child(4) > input:nth-child(1)" );
    webElement = driver.findElement( by );
    origText = "";
    if( webElement.isSelected() ){
      origText = "on";
    }else{
      origText = "off";
    }
    String store1685520375 = origText;

    //Element: Checkbox1 (OUTPUTSTOREDELEMENT) - store1685520375
    by = By.cssSelector( "body > center:nth-child(1) > form:nth-child(4) > input:nth-child(1)" );
    webElement = driver.findElement( by );
    System.out.println( "Status of Checkbox 1: " + store1685520375 );

    //Element: Checkbox1 (COMPAREVALUETOSTRING) - store1685520375
    by = By.cssSelector( "body > center:nth-child(1) > form:nth-child(4) > input:nth-child(1)" );
    webElement = driver.findElement( by );
    origText = "";
    if( webElement.isSelected() ){
      origText = "on";
    }else{
      origText = "off";
    }
    if( !origText.equals( "off" ) ){
      fail("Stopped because the element 'store1685520375': '" + origText + "' does NOT equal to 'off' but it should.");
    }

    //Element: Checkbox1 (LEFTCLICK) - store1685520375
    by = By.cssSelector( "body > center:nth-child(1) > form:nth-child(4) > input:nth-child(1)" );
    webElement = driver.findElement( by );
    webElement.click();

    //Script: Close Window (EXECUTE_SCRIPT) - script889628900
    ScriptClass script889628900 = new ScriptClass(){
      @Override
      public void runScript() throws Exception{
        driver.quit();
      }
    };
    try{
      script889628900.runScript();
    }catch( Exception e ){
      e.printStackTrace();
      fail( e.getMessage() );
    }

  }

}
