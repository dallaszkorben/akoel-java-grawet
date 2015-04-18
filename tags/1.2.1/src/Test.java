import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.UnexpectedTagNameException;
import org.openqa.selenium.support.ui.Select;

import org.openqa.selenium.WebDriverException;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

public class Test{ 

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

  public static void main( String[] args ){
    new Test();
  }

  public Test(){

    profile = new FirefoxProfile();
    profile.setPreference( "pdfjs.disabled", true );
    profile.setPreference( "media.navigator.permission.disabled", true );
    profile.setPreference( "plugin.state.nppdf", 2 );
    driver = new FirefoxDriver(profile);

    //Script: Open Window (CLEARPARAMETERS) - script21834804
    ScriptClass script21834804 = new ScriptClass(){
      @Override
      public void runScript() throws Exception{
        String url = parameters.get(0);
        driver.get( url );
      }
    };
    script21834804.clearParameters();

    //Script: Open Window (ADDCONSTANTTOPARAMETERS) - script21834804
    script21834804.addParameter( "http://www.sislands.com/coin70/week4/jstxtfld.htm" );

    //Script: Open Window (EXECUTE_SCRIPT) - script21834804
    try{
      script21834804.runScript();
    }catch( Exception e ){
      e.printStackTrace();
      System.exit(-1);
    }

    //Element: Field (GAINVALUETOELEMENT) - store17480161
    wait = new WebDriverWait(driver, 10);
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > form:nth-child(5) > div:nth-child(2) > center:nth-child(2) > p:nth-child(2) > font:nth-child(1) > input:nth-child(1)" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    origText = "";
    origText = webElement.getAttribute("value");
    String store17480161 = origText;

    //Element: Field (COMPAREVALUETOCONSTANT) - store17480161
    wait = new WebDriverWait(driver, 10);
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > form:nth-child(5) > div:nth-child(2) > center:nth-child(2) > p:nth-child(2) > font:nth-child(1) > input:nth-child(1)" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    origText = "";
    origText = webElement.getAttribute("value");
    if( !origText.equals( "Are you happy?" ) ){
      System.err.println("Stopped because the element 'store17480161': '" + origText + "' does NOT equal to 'Are you happy?' but it should.");
      System.exit(-1);
    }

    //Element: Field (COMPAREVALUETOSTRING) - store17480161
    wait = new WebDriverWait(driver, 10);
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > form:nth-child(5) > div:nth-child(2) > center:nth-child(2) > p:nth-child(2) > font:nth-child(1) > input:nth-child(1)" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    origText = "";
    origText = webElement.getAttribute("value");
    if( !origText.equals( "Are you happy?" ) ){
      System.err.println("Stopped because the element 'store17480161': '" + origText + "' does NOT equal to 'Are you happy?' but it should.");
      System.exit(-1);
    }

    //Element: Field (COMPAREVALUETOSTOREDELEMENT) - store17480161
    wait = new WebDriverWait(driver, 10);
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > form:nth-child(5) > div:nth-child(2) > center:nth-child(2) > p:nth-child(2) > font:nth-child(1) > input:nth-child(1)" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    origText = "";
    origText = webElement.getAttribute("value");
    if( !origText.equals( store17480161 ) ){
      System.err.println("Stopped because the element 'store17480161': '" + origText + "' does NOT equal to '" + store17480161 + "' but it should.");
      System.exit(-1);
    }

    //Element: Field (OUTPUTSTOREDELEMENT) - store17480161
    wait = new WebDriverWait(driver, 10);
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > form:nth-child(5) > div:nth-child(2) > center:nth-child(2) > p:nth-child(2) > font:nth-child(1) > input:nth-child(1)" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    System.out.println( "Content of filed: " + store17480161 );

    //Script: Close Window (EXECUTE_SCRIPT) - script26290525
    ScriptClass script26290525 = new ScriptClass(){
      @Override
      public void runScript() throws Exception{
        driver.close();
      }
    };
    try{
      script26290525.runScript();
    }catch( Exception e ){
      e.printStackTrace();
      System.exit(-1);
    }

  }
}
