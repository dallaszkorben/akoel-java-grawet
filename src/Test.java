import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.JavascriptExecutor;
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

    //Script: Open Window (CLEARPARAMETERS) - script25388972
    ScriptClass script25388972 = new ScriptClass(){
      @Override
      public void runScript() throws Exception{
        String url = parameters.get(0);
        driver.get( url );
      }
    };
    script25388972.clearParameters();

    //Script: Open Window (ADDCONSTANTTOPARAMETERS) - script25388972
    ScriptClass script25388972 = new ScriptClass(){
      @Override
      public void runScript() throws Exception{
        String url = parameters.get(0);
        driver.get( url );
      }
    };
    script25388972.addParameter( "http://sislands.com/coin70/week4/chkBoxTest.htm" );

    //Script: Open Window (EXECUTE_SCRIPT) - script25388972
    ScriptClass script25388972 = new ScriptClass(){
      @Override
      public void runScript() throws Exception{
        String url = parameters.get(0);
        driver.get( url );
      }
    };
    try{
      script25388972.runScript();
    }catch( Exception e ){
      e.printStackTrace();
      System.exit(-1);
    }

    //Element: Checkbox1 (GAINVALUETOELEMENT) - store11882627
    by = By.cssSelector( "body > center:nth-child(1) > form:nth-child(4) > input:nth-child(1)" );
    webElement = driver.findElement( by );
    origText = "";
    if( webElement.isSelected() ){
      origText = "on";
    }else{
      origText = "off";
    }
    String store11882627 = origText;

    //Element: Checkbox1 (OUTPUTSTOREDELEMENT) - store11882627
    by = By.cssSelector( "body > center:nth-child(1) > form:nth-child(4) > input:nth-child(1)" );
    webElement = driver.findElement( by );
    System.out.println( "Status of Checkbox 1: " + store11882627 );

    //Element: Checkbox1 (COMPAREVALUETOSTRING) - store11882627
    by = By.cssSelector( "body > center:nth-child(1) > form:nth-child(4) > input:nth-child(1)" );
    webElement = driver.findElement( by );
    origText = "";
    if( webElement.isSelected() ){
      origText = "on";
    }else{
      origText = "off";
    }
    if( !origText.equals( "off" ) ){
      System.err.println("Stopped because the element 'store11882627': '" + origText + "' does NOT equal to 'off' but it should.");
      System.exit(-1);
    }

    //Element: Checkbox1 (LEFTCLICK) - store11882627
    by = By.cssSelector( "body > center:nth-child(1) > form:nth-child(4) > input:nth-child(1)" );
    webElement = driver.findElement( by );
    webElement.click();

    //Ide jon a ciklusom
    while(){
        //most indul el a kiertekeles
        //Element: Checkbox1 (COMPAREVALUETOSTRING) - store11882627
        by = By.cssSelector( "body > center:nth-child(1) > form:nth-child(4) > input:nth-child(1)" );
        webElement = driver.findElement( by );
        origText = "";
        if( webElement.isSelected() ){
          origText = "on";
        }else{
          origText = "off";
        }
        if( !origText.equals( "on" ) ){
          System.err.println("Stopped because the element 'store11882627': '" + origText + "' does NOT equal to 'on' but it should.");
          System.exit(-1);
        }

        //a kiertekeles vege
        
        
        
        
        //Element: Checkbox1 (GAINVALUETOELEMENT) - store11882627
        by = By.cssSelector( "body > center:nth-child(1) > form:nth-child(4) > input:nth-child(1)" );
        webElement = driver.findElement( by );
        origText = "";
        if( webElement.isSelected() ){
          origText = "on";
        }else{
          origText = "off";
        }
        String store11882627 = origText;

        //Element: Checkbox1 (OUTPUTSTOREDELEMENT) - store11882627
        by = By.cssSelector( "body > center:nth-child(1) > form:nth-child(4) > input:nth-child(1)" );
        webElement = driver.findElement( by );
        System.out.println( "Status: " + store11882627 );

        //most indul el a kiertekeles
        //Element: Checkbox1 (COMPAREVALUETOSTRING) - store11882627
        by = By.cssSelector( "body > center:nth-child(1) > form:nth-child(4) > input:nth-child(1)" );
        webElement = driver.findElement( by );
        origText = "";
        if( webElement.isSelected() ){
          origText = "on";
        }else{
          origText = "off";
        }
        if( !origText.equals( "on" ) ){
          System.err.println("Stopped because the element 'store11882627': '" + origText + "' does NOT equal to 'on' but it should.");
          System.exit(-1);
        }

        //a kiertekeles vege
        //Element: Checkbox1 (GAINVALUETOELEMENT) - store11882627
        by = By.cssSelector( "body > center:nth-child(1) > form:nth-child(4) > input:nth-child(1)" );
        webElement = driver.findElement( by );
        origText = "";
        if( webElement.isSelected() ){
          origText = "on";
        }else{
          origText = "off";
        }
        String store11882627 = origText;

        //Element: Checkbox1 (OUTPUTSTOREDELEMENT) - store11882627
        by = By.cssSelector( "body > center:nth-child(1) > form:nth-child(4) > input:nth-child(1)" );
        webElement = driver.findElement( by );
        System.out.println( "Status: " + store11882627 );

        //most indul el a kiertekeles
        //Element: Checkbox1 (COMPAREVALUETOSTRING) - store11882627
        by = By.cssSelector( "body > center:nth-child(1) > form:nth-child(4) > input:nth-child(1)" );
        webElement = driver.findElement( by );
        origText = "";
        if( webElement.isSelected() ){
          origText = "on";
        }else{
          origText = "off";
        }
        if( !origText.equals( "on" ) ){
          System.err.println("Stopped because the element 'store11882627': '" + origText + "' does NOT equal to 'on' but it should.");
          System.exit(-1);
        }

        //a kiertekeles vege
        //Element: Checkbox1 (GAINVALUETOELEMENT) - store11882627
        by = By.cssSelector( "body > center:nth-child(1) > form:nth-child(4) > input:nth-child(1)" );
        webElement = driver.findElement( by );
        origText = "";
        if( webElement.isSelected() ){
          origText = "on";
        }else{
          origText = "off";
        }
        String store11882627 = origText;

        //Element: Checkbox1 (OUTPUTSTOREDELEMENT) - store11882627
        by = By.cssSelector( "body > center:nth-child(1) > form:nth-child(4) > input:nth-child(1)" );
        webElement = driver.findElement( by );
        System.out.println( "Status: " + store11882627 );

        //most indul el a kiertekeles
        //Element: Checkbox1 (COMPAREVALUETOSTRING) - store11882627
        by = By.cssSelector( "body > center:nth-child(1) > form:nth-child(4) > input:nth-child(1)" );
        webElement = driver.findElement( by );
        origText = "";
        if( webElement.isSelected() ){
          origText = "on";
        }else{
          origText = "off";
        }
        if( !origText.equals( "on" ) ){
          System.err.println("Stopped because the element 'store11882627': '" + origText + "' does NOT equal to 'on' but it should.");
          System.exit(-1);
        }

        //a kiertekeles vege
        //Element: Checkbox1 (GAINVALUETOELEMENT) - store11882627
        by = By.cssSelector( "body > center:nth-child(1) > form:nth-child(4) > input:nth-child(1)" );
        webElement = driver.findElement( by );
        origText = "";
        if( webElement.isSelected() ){
          origText = "on";
        }else{
          origText = "off";
        }
        String store11882627 = origText;

        //Element: Checkbox1 (OUTPUTSTOREDELEMENT) - store11882627
        by = By.cssSelector( "body > center:nth-child(1) > form:nth-child(4) > input:nth-child(1)" );
        webElement = driver.findElement( by );
        System.out.println( "Status: " + store11882627 );

        //most indul el a kiertekeles
        //Element: Checkbox1 (COMPAREVALUETOSTRING) - store11882627
        by = By.cssSelector( "body > center:nth-child(1) > form:nth-child(4) > input:nth-child(1)" );
        webElement = driver.findElement( by );
        origText = "";
        if( webElement.isSelected() ){
          origText = "on";
        }else{
          origText = "off";
        }
        if( !origText.equals( "on" ) ){
          System.err.println("Stopped because the element 'store11882627': '" + origText + "' does NOT equal to 'on' but it should.");
          System.exit(-1);
        }

        //a kiertekeles vege
        //Element: Checkbox1 (GAINVALUETOELEMENT) - store11882627
        by = By.cssSelector( "body > center:nth-child(1) > form:nth-child(4) > input:nth-child(1)" );
        webElement = driver.findElement( by );
        origText = "";
        if( webElement.isSelected() ){
          origText = "on";
        }else{
          origText = "off";
        }
        String store11882627 = origText;

        //Element: Checkbox1 (OUTPUTSTOREDELEMENT) - store11882627
        by = By.cssSelector( "body > center:nth-child(1) > form:nth-child(4) > input:nth-child(1)" );
        webElement = driver.findElement( by );
        System.out.println( "Status: " + store11882627 );

  }
}
