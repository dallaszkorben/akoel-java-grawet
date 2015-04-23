import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.concurrent.TimeUnit;
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

    //IMPLICIT WAIT
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

    //Script: Open Window (CLEARPARAMETERS) - script1129637737
    ScriptClass script1129637737 = new ScriptClass(){
      @Override
      public void runScript() throws Exception{
        String url = parameters.get(0);
        driver.get( url );
      }
    };
    script1129637737.clearParameters();

    //Script: Open Window (ADDCONSTANTTOPARAMETERS) - script1129637737
    script1129637737.addParameter( "http://sislands.com/coin70/week4/chkBoxTest.htm" );

    //Script: Open Window (EXECUTE_SCRIPT) - script1129637737
    try{
      script1129637737.runScript();
    }catch( Exception e ){
      e.printStackTrace();
      System.exit(-1);
    }

    //Element: Checkbox1 (GAINVALUETOELEMENT) - store690832813
    by = By.cssSelector( "body > center:nth-child(1) > form:nth-child(4) > input:nth-child(1)" );
    wait = new WebDriverWait(driver, 20); //EXPLICIT WAIT
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    try {Thread.sleep( 15000 );} catch (InterruptedException e) {}
    origText = "";
    if( webElement.isSelected() ){
      origText = "on";
    }else{
      origText = "off";
    }
    String store690832813 = origText;

    //Element: Checkbox1 (OUTPUTSTOREDELEMENT) - store690832813
    by = By.cssSelector( "body > center:nth-child(1) > form:nth-child(4) > input:nth-child(1)" );
    wait = new WebDriverWait(driver, 20); //EXPLICIT WAIT
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    try {Thread.sleep( 15000 );} catch (InterruptedException e) {}
    System.out.println( "Status of Checkbox 1: " + store690832813 );

    //Element: Checkbox1 (COMPAREVALUETOSTRING) - store690832813
    by = By.cssSelector( "body > center:nth-child(1) > form:nth-child(4) > input:nth-child(1)" );
    wait = new WebDriverWait(driver, 20); //EXPLICIT WAIT
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    try {Thread.sleep( 15000 );} catch (InterruptedException e) {}
    origText = "";
    if( webElement.isSelected() ){
      origText = "on";
    }else{
      origText = "off";
    }
    if( !origText.equals( "off" ) ){
      System.err.println("Stopped because the element 'store690832813': '" + origText + "' does NOT equal to 'off' but it should.");
      System.exit(-1);
    }

    //Element: Checkbox1 (LEFTCLICK) - store690832813
    by = By.cssSelector( "body > center:nth-child(1) > form:nth-child(4) > input:nth-child(1)" );
    wait = new WebDriverWait(driver, 20); //EXPLICIT WAIT
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    try {Thread.sleep( 15000 );} catch (InterruptedException e) {}
    webElement.click();

    //Script: Close Window (EXECUTE_SCRIPT) - script2066006279
    ScriptClass script2066006279 = new ScriptClass(){
      @Override
      public void runScript() throws Exception{
        driver.close();
      }
    };
    try{
      script2066006279.runScript();
    }catch( Exception e ){
      e.printStackTrace();
      System.exit(-1);
    }

  }
}
