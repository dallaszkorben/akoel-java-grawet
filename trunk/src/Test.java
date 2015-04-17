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
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

abstract class ScriptClass{
  ArrayList<String> parameters = new ArrayList<>();
  abstract public void runScript();
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
  ArrayList<WebElement> optionList;
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

    //Script: Open Window (CLEARPARAMETERS) - script1980722644
    ScriptClass script1980722644 = new ScriptClass(){
      @Override
      public void runScript(){
        String url = parameters.get(0);
        driver.get( url );
      }
    };
    script1980722644.clearParameters();

    //Script: Open Window (ADDCONSTANTTOPARAMETERS) - script1980722644
    script1980722644.addParameter( "http://sislands.com/coin70/week4/chkBoxTest.htm" );

    //Script: Open Window (EXECUTE_SCRIPT) - script1980722644
    script1980722644.runScript();

    //Element: Checkbox1 (GAINVALUETOELEMENT) - store95369748
    wait = new WebDriverWait(driver, 10);
    by = By.cssSelector( "body > center:nth-child(1) > form:nth-child(4) > input:nth-child(1)" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    if( webElement.isSelected() ){
      origText = "on";
    }else{
      origText = "off";
    }
    String store95369748 = origText;

    //Element: Checkbox1 (OUTPUTSTOREDELEMENT) - store95369748
    wait = new WebDriverWait(driver, 10);
    by = By.cssSelector( "body > center:nth-child(1) > form:nth-child(4) > input:nth-child(1)" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    System.out.println( "Status of Checkbox 1: " + store95369748 );

    //Element: Checkbox1 (COMPAREVALUETOSTRING) - store95369748
    wait = new WebDriverWait(driver, 10);
    by = By.cssSelector( "body > center:nth-child(1) > form:nth-child(4) > input:nth-child(1)" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    origText = "";
    if( webElement.isSelected() ){
      origText = "on";
    }else{
      origText = "off";
    }

    //Element: Checkbox1 (LEFTCLICK) - store95369748
    wait = new WebDriverWait(driver, 10);
    by = By.cssSelector( "body > center:nth-child(1) > form:nth-child(4) > input:nth-child(1)" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.click();

    //Script: Close Window (EXECUTE_SCRIPT) - script1861465561
    ScriptClass script1861465561 = new ScriptClass(){
      @Override
      public void runScript(){
        driver.close();
      }
    };
    script1861465561.runScript();

  }
}
