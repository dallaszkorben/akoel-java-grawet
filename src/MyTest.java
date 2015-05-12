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

    profile = new FirefoxProfile();
    profile.setPreference( "pdfjs.disabled", true );
    profile.setPreference( "media.navigator.permission.disabled", true );
    profile.setPreference( "plugin.state.nppdf", 2 );
    driver = new FirefoxDriver(profile);

    //IMPLICIT WAIT
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

    //Script: Open Window (CLEARPARAMETERS) - script18984755
    ScriptClass script18984755 = new ScriptClass(){
      @Override
      public void runScript() throws Exception{
        String url = parameters.get(0);
        driver.get( url );
      }
    };
    script18984755.clearParameters();

    script18984755.addParameter( "http://sislands.com/coin70/week4/chkBoxTest.htm" );

    try{
      script18984755.runScript();
    }catch( Exception e ){
      e.printStackTrace();
      fail( e.getMessage() );
    }

    //Element: Checkbox1 (GAINVALUETOELEMENT) - store32499980
    by = By.cssSelector( "body > center:nth-child(1) > form:nth-child(4) > input:nth-child(1)" );
    webElement = driver.findElement( by );
    origText = "";
    if( webElement.isSelected() ){
      origText = "on";
    }else{
      origText = "off";
    }
    String store32499980 = origText;

    //Element: Checkbox1 (OUTPUTSTOREDELEMENT) - store32499980
    by = By.cssSelector( "body > center:nth-child(1) > form:nth-child(4) > input:nth-child(1)" );
    webElement = driver.findElement( by );
    System.out.println( "Status of Checkbox 1: " + store32499980 );

    //Element: Checkbox1 (COMPAREVALUETOSTRING) - store32499980
    by = By.cssSelector( "body > center:nth-child(1) > form:nth-child(4) > input:nth-child(1)" );
    webElement = driver.findElement( by );
    origText = "";
    if( webElement.isSelected() ){
      origText = "on";
    }else{
      origText = "off";
    }
    if( !origText.equals( "off" ) ){
      fail("Stopped because the element 'store32499980': '" + origText + "' does NOT equal to 'off' but it should.");
    }

    //Element: Checkbox1 (LEFTCLICK) - store32499980
    by = By.cssSelector( "body > center:nth-child(1) > form:nth-child(4) > input:nth-child(1)" );
    webElement = driver.findElement( by );
    webElement.click();

    //Script: Close Window (EXECUTE_SCRIPT) - script7690110
    ScriptClass script7690110 = new ScriptClass(){
      @Override
      public void runScript() throws Exception{
        driver.quit();
      }
    };
    try{
      script7690110.runScript();
    }catch( Exception e ){
      e.printStackTrace();
      fail( e.getMessage() );
    }

  }

  @Test
  public void TextCycleFail(){

    int actualLoop = 0;
    int maxLoopNumber;
    int oneLoopLength;
    Date actualDate;
    Date startDate;

    profile = new FirefoxProfile();
    profile.setPreference( "pdfjs.disabled", true );
    profile.setPreference( "media.navigator.permission.disabled", true );
    profile.setPreference( "plugin.state.nppdf", 2 );
    driver = new FirefoxDriver(profile);

    //IMPLICIT WAIT
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

    //Script: Open Window (CLEARPARAMETERS) - script18984755
    ScriptClass script18984755 = new ScriptClass(){
      @Override
      public void runScript() throws Exception{
        String url = parameters.get(0);
        driver.get( url );
      }
    };
    script18984755.clearParameters();

    script18984755.addParameter( "http://sislands.com/coin70/week4/chkBoxTest.htm" );

    try{
      script18984755.runScript();
    }catch( Exception e ){
      e.printStackTrace();
      fail( e.getMessage() );
    }

    //Element: Checkbox1 (GAINVALUETOELEMENT) - store32499980
    by = By.cssSelector( "body > center:nth-child(1) > form:nth-child(4) > input:nth-child(1)" );
    webElement = driver.findElement( by );
    origText = "";
    if( webElement.isSelected() ){
      origText = "on";
    }else{
      origText = "off";
    }
    String store32499980 = origText;

    //Element: Checkbox1 (OUTPUTSTOREDELEMENT) - store32499980
    by = By.cssSelector( "body > center:nth-child(1) > form:nth-child(4) > input:nth-child(1)" );
    webElement = driver.findElement( by );
    System.out.println( "Status of Checkbox 1: " + store32499980 );

    //Element: Checkbox1 (COMPAREVALUETOSTRING) - store32499980
    by = By.cssSelector( "body > center:nth-child(1) > form:nth-child(4) > input:nth-child(1)" );
    webElement = driver.findElement( by );
    origText = "";
    if( webElement.isSelected() ){
      origText = "on";
    }else{
      origText = "off";
    }
    if( !origText.equals( "off" ) ){
      fail("Stopped because the element 'store32499980': '" + origText + "' does NOT equal to 'off' but it should.");
    }

    //Element: Checkbox1 (LEFTCLICK) - store32499980
    by = By.cssSelector( "body > center:nth-child(1) > form:nth-child(4) > input:nth-child(1)" );
    webElement = driver.findElement( by );
    webElement.click();

    //Cycle starts
    startDate = Calendar.getInstance().getTime();
    actualLoop = 0;
    oneLoopLength = 1;
    maxLoopNumber = 5;
    while( actualLoop++ < maxLoopNumber ){

      //
      //Evaluation
      //
      //Element: Checkbox1 (COMPAREVALUETOSTRING) - store32499980
      by = By.cssSelector( "body > center:nth-child(1) > form:nth-child(4) > input:nth-child(1)" );
      webElement = driver.findElement( by );
      origText = "";
      if( webElement.isSelected() ){
        origText = "on";
      }else{
        origText = "off";
      }
      if( !origText.equals( "on" ) ){
        break; //because the element 'store32499980' does NOT equal to 'on'.
      }

      //
      //Execution
      //
      //Element: Checkbox1 (GAINVALUETOELEMENT) - store32499980
      by = By.cssSelector( "body > center:nth-child(1) > form:nth-child(4) > input:nth-child(1)" );
      webElement = driver.findElement( by );
      origText = "";
      if( webElement.isSelected() ){
        origText = "on";
      }else{
        origText = "off";
      }
      store32499980 = origText;

      //Element: Checkbox1 (OUTPUTSTOREDELEMENT) - store32499980
      by = By.cssSelector( "body > center:nth-child(1) > form:nth-child(4) > input:nth-child(1)" );
      webElement = driver.findElement( by );
      System.out.println( "Status: " + store32499980 );

      if( actualLoop >= maxLoopNumber ){
        fail( "Stopped because the loop exceeded the max value but the LOOP condition is still TRUE for the 'Checkbox1' element." );
      }

      //Waiting before the next cycle
      actualDate = Calendar.getInstance().getTime();
      long differenceTime = actualDate.getTime() - startDate.getTime();
      long neededToWait = oneLoopLength * 1000L * actualLoop - differenceTime;
      try{ Thread.sleep( neededToWait ); } catch(InterruptedException ex) {}

    } //while()
  }

  @Test
  public void TestRadiobutton(){

    int actualLoop = 0;
    int maxLoopNumber;
    int oneLoopLength;
    Date actualDate;
    Date startDate;

    profile = new FirefoxProfile();
    profile.setPreference( "pdfjs.disabled", true );
    profile.setPreference( "media.navigator.permission.disabled", true );
    profile.setPreference( "plugin.state.nppdf", 2 );
    driver = new FirefoxDriver(profile);

    //IMPLICIT WAIT
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

    //Script: Open Window (CLEARPARAMETERS) - script18984755
    ScriptClass script18984755 = new ScriptClass(){
      @Override
      public void runScript() throws Exception{
        String url = parameters.get(0);
        driver.get( url );
      }
    };
    script18984755.clearParameters();

    script18984755.addParameter( "http://www.sislands.com/coin70/week4/rdoBtnTest.htm" );

    try{
      script18984755.runScript();
    }catch( Exception e ){
      e.printStackTrace();
      fail( e.getMessage() );
    }

    //Element: Radiobutton 1 (GAINVALUETOELEMENT) - store31842038
    by = By.cssSelector( "body > center:nth-child(1) > form:nth-child(3) > input:nth-child(1)" );
    webElement = driver.findElement( by );
    origText = "";
    if( webElement.isSelected() ){
      origText = "on";
    }else{
      origText = "off";
    }
    String store31842038 = origText;

    //Element: Radiobutton 1 (OUTPUTSTOREDELEMENT) - store31842038
    by = By.cssSelector( "body > center:nth-child(1) > form:nth-child(3) > input:nth-child(1)" );
    webElement = driver.findElement( by );
    System.out.println( "Status of radiobutton 1: " + store31842038 );

    //Element: Radiobutton 1 (COMPAREVALUETOSTRING) - store31842038
    by = By.cssSelector( "body > center:nth-child(1) > form:nth-child(3) > input:nth-child(1)" );
    webElement = driver.findElement( by );
    origText = "";
    if( webElement.isSelected() ){
      origText = "on";
    }else{
      origText = "off";
    }
    if( !origText.equals( "off" ) ){
      fail("Stopped because the element 'store31842038': '" + origText + "' does NOT equal to 'off' but it should.");
    }

    //Element: Radiobutton 1 (LEFTCLICK) - store31842038
    by = By.cssSelector( "body > center:nth-child(1) > form:nth-child(3) > input:nth-child(1)" );
    webElement = driver.findElement( by );
    webElement.click();

    //Element: Radiobutton 1 (COMPAREVALUETOSTRING) - store31842038
    by = By.cssSelector( "body > center:nth-child(1) > form:nth-child(3) > input:nth-child(1)" );
    webElement = driver.findElement( by );
    origText = "";
    if( webElement.isSelected() ){
      origText = "on";
    }else{
      origText = "off";
    }
    if( !origText.equals( "on" ) ){
      fail("Stopped because the element 'store31842038': '" + origText + "' does NOT equal to 'on' but it should.");
    }

    //Script: Close Window (EXECUTE_SCRIPT) - script7690110
    ScriptClass script7690110 = new ScriptClass(){
      @Override
      public void runScript() throws Exception{
        driver.quit();
      }
    };
    try{
      script7690110.runScript();
    }catch( Exception e ){
      e.printStackTrace();
      fail( e.getMessage() );
    }

  }

  @Test
  public void TestList(){

    int actualLoop = 0;
    int maxLoopNumber;
    int oneLoopLength;
    Date actualDate;
    Date startDate;

    profile = new FirefoxProfile();
    profile.setPreference( "pdfjs.disabled", true );
    profile.setPreference( "media.navigator.permission.disabled", true );
    profile.setPreference( "plugin.state.nppdf", 2 );
    driver = new FirefoxDriver(profile);

    //IMPLICIT WAIT
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

    //Script: Open Window (CLEARPARAMETERS) - script18984755
    ScriptClass script18984755 = new ScriptClass(){
      @Override
      public void runScript() throws Exception{
        String url = parameters.get(0);
        driver.get( url );
      }
    };
    script18984755.clearParameters();

    script18984755.addParameter( "http://www.sislands.com/coin70/week4/selectex.htm" );

    try{
      script18984755.runScript();
    }catch( Exception e ){
      e.printStackTrace();
      fail( e.getMessage() );
    }

    //Element: Pulldown list (COMPARELISTSIZETOINTEGER) - store16699596
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > form:nth-child(4) > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > select:nth-child(2)" );
    webElement = driver.findElement( by );
    origText = "0";
    select = new Select(webElement);
    origText = String.valueOf( select.getOptions().size() );
    if( !origText.equals( "6" ) ){
      fail("Stopped because the selected element in the Select 'store16699596': '" + origText + "' does NOT equal to '6' but it should.");
    }

    //Element: Pulldown list (GAINLISTTOELEMENT) - store16699596
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > form:nth-child(4) > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > select:nth-child(2)" );
    webElement = driver.findElement( by );
    origText = "";
    select = new Select(webElement);
    origText = select.getFirstSelectedOption().getText();
    String store16699596 = origText;

    //Element: Normal list (CONTAINLISTSTOREDELEMENT) - store8801334
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > form:nth-child(4) > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2) > div:nth-child(1) > p:nth-child(1) > select:nth-child(3)" );
    webElement = driver.findElement( by );
    select = new Select(webElement);
    optionList = select.getOptions();
    found = false;
    for( WebElement option: optionList ){
      optionText = "";
      optionText = option.getText();
      if( optionText.equals( store16699596 ) ){
          found = true;
          break;
      }
    } //for( WebElement option: optionList )
    if( !found ){
      fail("Stopped because for the list 'store16699596' the expection is: 'Tartalmazza' BUT 'probiscus' is NOT in the list");
    }

    //Element: Normal list (CONTAINLISTSTRING) - store8801334
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > form:nth-child(4) > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2) > div:nth-child(1) > p:nth-child(1) > select:nth-child(3)" );
    webElement = driver.findElement( by );
    select = new Select(webElement);
    optionList = select.getOptions();
    found = false;
    for( WebElement option: optionList ){
      optionText = "";
      optionText = option.getText();
      if( optionText.equals( "spider" ) ){
          found = true;
          break;
      }
    } //for( WebElement option: optionList )
    if( !found ){
      fail("Stopped because for the list 'store8801334' the expection is: 'Tartalmazza' BUT 'spider' is NOT in the list");
    }

    //Element: Normal list (CONTAINLISTCONSTANT) - store8801334
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > form:nth-child(4) > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2) > div:nth-child(1) > p:nth-child(1) > select:nth-child(3)" );
    webElement = driver.findElement( by );
    select = new Select(webElement);
    optionList = select.getOptions();
    found = false;
    for( WebElement option: optionList ){
      optionText = "";
      optionText = option.getText();
      if( optionText.equals( "lemur" ) ){
          found = true;
          break;
      }
    } //for( WebElement option: optionList )
    if( !found ){
      fail("Stopped because for the list 'store8801334' the expection is: 'Tartalmazza' BUT 'lemur' is NOT in the list");
    }

    //Element: Normal list (SELECTVARIABLEELEMENT) - store8801334
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > form:nth-child(4) > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2) > div:nth-child(1) > p:nth-child(1) > select:nth-child(3)" );
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByVisibleText( "lemur" );

    //Element: Normal list (SELECTSTRING) - store8801334
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > form:nth-child(4) > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2) > div:nth-child(1) > p:nth-child(1) > select:nth-child(3)" );
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByVisibleText( "spider" );

    //Element: Normal list (COMPARELISTTOSTRING) - store8801334
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > form:nth-child(4) > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2) > div:nth-child(1) > p:nth-child(1) > select:nth-child(3)" );
    webElement = driver.findElement( by );
    origText = "";
    select = new Select(webElement);
    origText = select.getFirstSelectedOption().getText();
    if( !origText.equals( "spider" ) ){
      fail("Stopped because the selected element in the Select 'store8801334': '" + origText + "' does NOT equal to 'spider' but it should.");
    }

    //Element: Normal list (COMPARELISTTOCONSTANT) - store8801334
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > form:nth-child(4) > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2) > div:nth-child(1) > p:nth-child(1) > select:nth-child(3)" );
    webElement = driver.findElement( by );
    origText = "";
    select = new Select(webElement);
    origText = select.getFirstSelectedOption().getText();
    if( !origText.equals( "spider" ) ){
      fail( "Stopped because the selected element in the Select 'store8801334': '" + origText + "' does NOT equal to 'spider' but it should." );
    }

    //Element: Normal list (GAINLISTTOELEMENT) - store8801334
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > form:nth-child(4) > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2) > div:nth-child(1) > p:nth-child(1) > select:nth-child(3)" );
    webElement = driver.findElement( by );
    origText = "";
    select = new Select(webElement);
    origText = select.getFirstSelectedOption().getText();
    String store8801334 = origText;

    //Element: Normal list (COMPARELISTTOSTOREDELEMENT) - store8801334
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > form:nth-child(4) > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2) > div:nth-child(1) > p:nth-child(1) > select:nth-child(3)" );
    webElement = driver.findElement( by );
    origText = "";
    select = new Select(webElement);
    origText = select.getFirstSelectedOption().getText();
    if( !origText.equals( store8801334 ) ){
      fail("Stopped because the selected element in the Select 'store8801334': '" + origText + "' does NOT equal to '" + store8801334 + "' but it should.");
    }

    //Element: Normal list (COMPARELISTTOCONSTANT) - store8801334
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > form:nth-child(4) > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2) > div:nth-child(1) > p:nth-child(1) > select:nth-child(3)" );
    webElement = driver.findElement( by );
    origText = "";
    select = new Select(webElement);
    origText = select.getFirstSelectedOption().getText();
    if( !origText.equals( "spider" ) ){
      fail( "Stopped because the selected element in the Select 'store8801334': '" + origText + "' does NOT equal to 'spider' but it should." );
    }

    //Element: Normal list (COMPARELISTTOSTRING) - store8801334
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > form:nth-child(4) > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2) > div:nth-child(1) > p:nth-child(1) > select:nth-child(3)" );
    webElement = driver.findElement( by );
    origText = "";
    select = new Select(webElement);
    origText = select.getFirstSelectedOption().getText();
    if( !origText.equals( "spider" ) ){
      fail("Stopped because the selected element in the Select 'store8801334': '" + origText + "' does NOT equal to 'spider' but it should.");
    }

    //Element: Normal list (OUTPUTSTOREDELEMENT) - store8801334
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > form:nth-child(4) > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2) > div:nth-child(1) > p:nth-child(1) > select:nth-child(3)" );
    webElement = driver.findElement( by );
    System.out.println( "Selected element in the normal list: " + store8801334 );

    //Script: Close Window (EXECUTE_SCRIPT) - script7690110
    ScriptClass script7690110 = new ScriptClass(){
      @Override
      public void runScript() throws Exception{
        driver.quit();
      }
    };
    try{
      script7690110.runScript();
    }catch( Exception e ){
      e.printStackTrace();
      fail( e.getMessage() );
    }

  }

  @Test
  public void TestText(){

    int actualLoop = 0;
    int maxLoopNumber;
    int oneLoopLength;
    Date actualDate;
    Date startDate;

    profile = new FirefoxProfile();
    profile.setPreference( "pdfjs.disabled", true );
    profile.setPreference( "media.navigator.permission.disabled", true );
    profile.setPreference( "plugin.state.nppdf", 2 );
    driver = new FirefoxDriver(profile);

    //IMPLICIT WAIT
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

    //Script: Open Window (CLEARPARAMETERS) - script18984755
    ScriptClass script18984755 = new ScriptClass(){
      @Override
      public void runScript() throws Exception{
        String url = parameters.get(0);
        driver.get( url );
      }
    };
    script18984755.clearParameters();

    script18984755.addParameter( "http://www.sislands.com/coin70/week4/jstxtfld.htm" );

    try{
      script18984755.runScript();
    }catch( Exception e ){
      e.printStackTrace();
      fail( e.getMessage() );
    }

    //Element: Main Title (GAINTEXTTOELEMENT) - store1784079
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > h2:nth-child(2) > font:nth-child(1)" );
    webElement = driver.findElement( by );
    origText = webElement.getText();
    String store1784079 = origText;

    //Element: Main Title (COMPARETEXTTOCONSTANT) - store1784079
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > h2:nth-child(2) > font:nth-child(1)" );
    webElement = driver.findElement( by );
    origText = webElement.getText();
    if( !origText.equals( "Manipulating the Value of a Text Field" ) ){
      fail("Stopped because the element 'store1784079': '" + origText + "' does NOT equal to 'Manipulating the Value of a Text Field' but it should.");
    }

    //Element: Main Title (COMPARETEXTTOSTRING) - store1784079
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > h2:nth-child(2) > font:nth-child(1)" );
    webElement = driver.findElement( by );
    origText = webElement.getText();
    if( !origText.equals( "Manipulating the Value of a Text Field" ) ){
      fail("Stopped because the element 'store1784079': '" + origText + "' does NOT equal to 'Manipulating the Value of a Text Field' but it should.");
    }

    //Element: Main Title (COMPARETEXTTOSTOREDELEMENT) - store1784079
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > h2:nth-child(2) > font:nth-child(1)" );
    webElement = driver.findElement( by );
    origText = webElement.getText();
    if( !origText.equals( store1784079 ) ){
      fail("Stopped because the element 'store1784079': '" + origText + "' does NOT equal to 'store1784079': " + store1784079 + " but it should.");
    }

    //Element: Main Title (OUTPUTSTOREDELEMENT) - store1784079
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > h2:nth-child(2) > font:nth-child(1)" );
    webElement = driver.findElement( by );
    System.out.println( "Title: " + store1784079 );

    //Script: Close Window (EXECUTE_SCRIPT) - script7690110
    ScriptClass script7690110 = new ScriptClass(){
      @Override
      public void runScript() throws Exception{
        driver.quit();
      }
    };
    try{
      script7690110.runScript();
    }catch( Exception e ){
      e.printStackTrace();
      fail( e.getMessage() );
    }

  }

  @Test
  public void TestFieldStatic(){

    int actualLoop = 0;
    int maxLoopNumber;
    int oneLoopLength;
    Date actualDate;
    Date startDate;

    profile = new FirefoxProfile();
    profile.setPreference( "pdfjs.disabled", true );
    profile.setPreference( "media.navigator.permission.disabled", true );
    profile.setPreference( "plugin.state.nppdf", 2 );
    driver = new FirefoxDriver(profile);

    //IMPLICIT WAIT
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

    //Script: Open Window (CLEARPARAMETERS) - script18984755
    ScriptClass script18984755 = new ScriptClass(){
      @Override
      public void runScript() throws Exception{
        String url = parameters.get(0);
        driver.get( url );
      }
    };
    script18984755.clearParameters();

    script18984755.addParameter( "http://www.sislands.com/coin70/week4/jstxtfld.htm" );

    try{
      script18984755.runScript();
    }catch( Exception e ){
      e.printStackTrace();
      fail( e.getMessage() );
    }

    //Element: Field (GAINVALUETOELEMENT) - store18875067
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > form:nth-child(5) > div:nth-child(2) > center:nth-child(2) > p:nth-child(2) > font:nth-child(1) > input:nth-child(1)" );
    wait = new WebDriverWait(driver, 5); //EXPLICIT WAIT
    try {Thread.sleep( 4000 );} catch (InterruptedException e) {}
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    origText = "";
    origText = webElement.getAttribute("value");
    String store18875067 = origText;
    try {Thread.sleep( 3000 );} catch (InterruptedException e) {}

    //Element: Field (COMPAREVALUETOCONSTANT) - store18875067
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > form:nth-child(5) > div:nth-child(2) > center:nth-child(2) > p:nth-child(2) > font:nth-child(1) > input:nth-child(1)" );
    wait = new WebDriverWait(driver, 5); //EXPLICIT WAIT
    try {Thread.sleep( 4000 );} catch (InterruptedException e) {}
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    origText = "";
    origText = webElement.getAttribute("value");
    if( !origText.equals( "Are you happy?" ) ){
      fail("Stopped because the element 'store18875067': '" + origText + "' does NOT equal to 'Are you happy?' but it should.");
    }
    try {Thread.sleep( 3000 );} catch (InterruptedException e) {}

    //Element: Field (COMPAREVALUETOSTRING) - store18875067
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > form:nth-child(5) > div:nth-child(2) > center:nth-child(2) > p:nth-child(2) > font:nth-child(1) > input:nth-child(1)" );
    wait = new WebDriverWait(driver, 5); //EXPLICIT WAIT
    try {Thread.sleep( 4000 );} catch (InterruptedException e) {}
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    origText = "";
    origText = webElement.getAttribute("value");
    if( !origText.equals( "Are you happy?" ) ){
      fail("Stopped because the element 'store18875067': '" + origText + "' does NOT equal to 'Are you happy?' but it should.");
    }
    try {Thread.sleep( 3000 );} catch (InterruptedException e) {}

    //Element: Field (COMPAREVALUETOSTOREDELEMENT) - store18875067
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > form:nth-child(5) > div:nth-child(2) > center:nth-child(2) > p:nth-child(2) > font:nth-child(1) > input:nth-child(1)" );
    wait = new WebDriverWait(driver, 5); //EXPLICIT WAIT
    try {Thread.sleep( 4000 );} catch (InterruptedException e) {}
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    origText = "";
    origText = webElement.getAttribute("value");
    if( !origText.equals( store18875067 ) ){
      fail("Stopped because the element 'store18875067': '" + origText + "' does NOT equal to 'store18875067': " + store18875067 + " but it should.");
    }
    try {Thread.sleep( 3000 );} catch (InterruptedException e) {}

    //Element: Field (OUTPUTSTOREDELEMENT) - store18875067
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > form:nth-child(5) > div:nth-child(2) > center:nth-child(2) > p:nth-child(2) > font:nth-child(1) > input:nth-child(1)" );
    wait = new WebDriverWait(driver, 5); //EXPLICIT WAIT
    try {Thread.sleep( 4000 );} catch (InterruptedException e) {}
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    System.out.println( "Content of filed: " + store18875067 );
    try {Thread.sleep( 3000 );} catch (InterruptedException e) {}

    //Script: Close Window (EXECUTE_SCRIPT) - script7690110
    ScriptClass script7690110 = new ScriptClass(){
      @Override
      public void runScript() throws Exception{
        driver.quit();
      }
    };
    try{
      script7690110.runScript();
    }catch( Exception e ){
      e.printStackTrace();
      fail( e.getMessage() );
    }

  }

  @Test
  public void TestFieldDynamic(){

    int actualLoop = 0;
    int maxLoopNumber;
    int oneLoopLength;
    Date actualDate;
    Date startDate;

    profile = new FirefoxProfile();
    profile.setPreference( "pdfjs.disabled", true );
    profile.setPreference( "media.navigator.permission.disabled", true );
    profile.setPreference( "plugin.state.nppdf", 2 );
    driver = new FirefoxDriver(profile);

    //IMPLICIT WAIT
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

    //Script: Open Window (CLEARPARAMETERS) - script18984755
    ScriptClass script18984755 = new ScriptClass(){
      @Override
      public void runScript() throws Exception{
        String url = parameters.get(0);
        driver.get( url );
      }
    };
    script18984755.clearParameters();

    script18984755.addParameter( "http://www.sislands.com/coin70/week4/jstxtfld.htm" );

    try{
      script18984755.runScript();
    }catch( Exception e ){
      e.printStackTrace();
      fail( e.getMessage() );
    }

    //Element: Field (GAINVALUETOELEMENT) - store18875067
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > form:nth-child(5) > div:nth-child(2) > center:nth-child(2) > p:nth-child(2) > font:nth-child(1) > input:nth-child(1)" );
    wait = new WebDriverWait(driver, 5); //EXPLICIT WAIT
    try {Thread.sleep( 4000 );} catch (InterruptedException e) {}
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    origText = "";
    origText = webElement.getAttribute("value");
    String store18875067 = origText;
    try {Thread.sleep( 3000 );} catch (InterruptedException e) {}

    //Element: Field (OUTPUTSTOREDELEMENT) - store18875067
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > form:nth-child(5) > div:nth-child(2) > center:nth-child(2) > p:nth-child(2) > font:nth-child(1) > input:nth-child(1)" );
    wait = new WebDriverWait(driver, 5); //EXPLICIT WAIT
    try {Thread.sleep( 4000 );} catch (InterruptedException e) {}
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    System.out.println( "Content of field: " + store18875067 );
    try {Thread.sleep( 3000 );} catch (InterruptedException e) {}

    //Element: Field (COMPAREVALUETOCONSTANT) - store18875067
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > form:nth-child(5) > div:nth-child(2) > center:nth-child(2) > p:nth-child(2) > font:nth-child(1) > input:nth-child(1)" );
    wait = new WebDriverWait(driver, 5); //EXPLICIT WAIT
    try {Thread.sleep( 4000 );} catch (InterruptedException e) {}
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    origText = "";
    origText = webElement.getAttribute("value");
    if( !origText.equals( "Are you happy?" ) ){
      fail("Stopped because the element 'store18875067': '" + origText + "' does NOT equal to 'Are you happy?' but it should.");
    }
    try {Thread.sleep( 3000 );} catch (InterruptedException e) {}

    //Element: Yes link (MOVETOELEMENT) - store20913331
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > p:nth-child(6) > a:nth-child(1)" );
    webElement = driver.findElement( by );
    new Actions(driver).moveToElement(webElement).perform();

    //Element: Field (GAINVALUETOELEMENT) - store18875067
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > form:nth-child(5) > div:nth-child(2) > center:nth-child(2) > p:nth-child(2) > font:nth-child(1) > input:nth-child(1)" );
    wait = new WebDriverWait(driver, 5); //EXPLICIT WAIT
    try {Thread.sleep( 4000 );} catch (InterruptedException e) {}
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    origText = "";
    origText = webElement.getAttribute("value");
    store18875067 = origText;
    try {Thread.sleep( 3000 );} catch (InterruptedException e) {}

    //Element: Field (OUTPUTSTOREDELEMENT) - store18875067
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > form:nth-child(5) > div:nth-child(2) > center:nth-child(2) > p:nth-child(2) > font:nth-child(1) > input:nth-child(1)" );
    wait = new WebDriverWait(driver, 5); //EXPLICIT WAIT
    try {Thread.sleep( 4000 );} catch (InterruptedException e) {}
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    System.out.println( "Content of field: " + store18875067 );
    try {Thread.sleep( 3000 );} catch (InterruptedException e) {}

    //Element: Field (COMPAREVALUETOCONSTANT) - store18875067
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > form:nth-child(5) > div:nth-child(2) > center:nth-child(2) > p:nth-child(2) > font:nth-child(1) > input:nth-child(1)" );
    wait = new WebDriverWait(driver, 5); //EXPLICIT WAIT
    try {Thread.sleep( 4000 );} catch (InterruptedException e) {}
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    origText = "";
    origText = webElement.getAttribute("value");
    if( !origText.equals( "Clap clap!" ) ){
      fail("Stopped because the element 'store18875067': '" + origText + "' does NOT equal to 'Clap clap!' but it should.");
    }
    try {Thread.sleep( 3000 );} catch (InterruptedException e) {}

    //Element: No link (MOVETOELEMENT) - store7979301
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > p:nth-child(6) > a:nth-child(2)" );
    webElement = driver.findElement( by );
    new Actions(driver).moveToElement(webElement).perform();

    //Element: Field (GAINVALUETOELEMENT) - store18875067
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > form:nth-child(5) > div:nth-child(2) > center:nth-child(2) > p:nth-child(2) > font:nth-child(1) > input:nth-child(1)" );
    wait = new WebDriverWait(driver, 5); //EXPLICIT WAIT
    try {Thread.sleep( 4000 );} catch (InterruptedException e) {}
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    origText = "";
    origText = webElement.getAttribute("value");
    store18875067 = origText;
    try {Thread.sleep( 3000 );} catch (InterruptedException e) {}

    //Element: Field (OUTPUTSTOREDELEMENT) - store18875067
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > form:nth-child(5) > div:nth-child(2) > center:nth-child(2) > p:nth-child(2) > font:nth-child(1) > input:nth-child(1)" );
    wait = new WebDriverWait(driver, 5); //EXPLICIT WAIT
    try {Thread.sleep( 4000 );} catch (InterruptedException e) {}
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    System.out.println( "Content of field: " + store18875067 );
    try {Thread.sleep( 3000 );} catch (InterruptedException e) {}

    //Element: Field (COMPAREVALUETOCONSTANT) - store18875067
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > form:nth-child(5) > div:nth-child(2) > center:nth-child(2) > p:nth-child(2) > font:nth-child(1) > input:nth-child(1)" );
    wait = new WebDriverWait(driver, 5); //EXPLICIT WAIT
    try {Thread.sleep( 4000 );} catch (InterruptedException e) {}
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    origText = "";
    origText = webElement.getAttribute("value");
    if( !origText.equals( "Sour puss!" ) ){
      fail("Stopped because the element 'store18875067': '" + origText + "' does NOT equal to 'Sour puss!' but it should.");
    }
    try {Thread.sleep( 3000 );} catch (InterruptedException e) {}

    //Script: Close Window (EXECUTE_SCRIPT) - script7690110
    ScriptClass script7690110 = new ScriptClass(){
      @Override
      public void runScript() throws Exception{
        driver.quit();
      }
    };
    try{
      script7690110.runScript();
    }catch( Exception e ){
      e.printStackTrace();
      fail( e.getMessage() );
    }

  }

}
