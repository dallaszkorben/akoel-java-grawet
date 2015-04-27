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

    profile = new FirefoxProfile();
    profile.setPreference( "pdfjs.disabled", true );
    profile.setPreference( "media.navigator.permission.disabled", true );
    profile.setPreference( "plugin.state.nppdf", 2 );
    driver = new FirefoxDriver(profile);

    //IMPLICIT WAIT
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

    //Script: Open Window (CLEARPARAMETERS) - script1378268624
    ScriptClass script1378268624 = new ScriptClass(){
      @Override
      public void runScript() throws Exception{
        String url = parameters.get(0);
        driver.get( url );
      }
    };
    script1378268624.clearParameters();

    script1378268624.addParameter( "http://sislands.com/coin70/week4/chkBoxTest.htm" );

    try{
      script1378268624.runScript();
    }catch( Exception e ){
      e.printStackTrace();
      fail( e.getMessage() );
    }

    //Element: Checkbox1 (GAINVALUETOELEMENT) - store1348127627
    by = By.cssSelector( "body > center:nth-child(1) > form:nth-child(4) > input:nth-child(1)" );
    webElement = driver.findElement( by );
    origText = "";
    if( webElement.isSelected() ){
      origText = "on";
    }else{
      origText = "off";
    }
    String store1348127627 = origText;

    //Element: Checkbox1 (OUTPUTSTOREDELEMENT) - store1348127627
    by = By.cssSelector( "body > center:nth-child(1) > form:nth-child(4) > input:nth-child(1)" );
    webElement = driver.findElement( by );
    System.out.println( "Status of Checkbox 1: " + store1348127627 );

    //Element: Checkbox1 (COMPAREVALUETOSTRING) - store1348127627
    by = By.cssSelector( "body > center:nth-child(1) > form:nth-child(4) > input:nth-child(1)" );
    webElement = driver.findElement( by );
    origText = "";
    if( webElement.isSelected() ){
      origText = "on";
    }else{
      origText = "off";
    }
    if( !origText.equals( "off" ) ){
      fail("Stopped because the element 'store1348127627': '" + origText + "' does NOT equal to 'off' but it should.");
    }

    //Element: Checkbox1 (LEFTCLICK) - store1348127627
    by = By.cssSelector( "body > center:nth-child(1) > form:nth-child(4) > input:nth-child(1)" );
    webElement = driver.findElement( by );
    webElement.click();

    //Script: Close Window (EXECUTE_SCRIPT) - script1802147026
    ScriptClass script1802147026 = new ScriptClass(){
      @Override
      public void runScript() throws Exception{
        driver.quit();
      }
    };
    try{
      script1802147026.runScript();
    }catch( Exception e ){
      e.printStackTrace();
      fail( e.getMessage() );
    }

  }

  @Test
  public void TestRadiobutton(){

    profile = new FirefoxProfile();
    profile.setPreference( "pdfjs.disabled", true );
    profile.setPreference( "media.navigator.permission.disabled", true );
    profile.setPreference( "plugin.state.nppdf", 2 );
    driver = new FirefoxDriver(profile);

    //IMPLICIT WAIT
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

    //Script: Open Window (CLEARPARAMETERS) - script1378268624
    ScriptClass script1378268624 = new ScriptClass(){
      @Override
      public void runScript() throws Exception{
        String url = parameters.get(0);
        driver.get( url );
      }
    };
    script1378268624.clearParameters();

    script1378268624.addParameter( "http://www.sislands.com/coin70/week4/rdoBtnTest.htm" );

    try{
      script1378268624.runScript();
    }catch( Exception e ){
      e.printStackTrace();
      fail( e.getMessage() );
    }

    //Element: Radiobutton 1 (GAINVALUETOELEMENT) - store946691901
    by = By.cssSelector( "body > center:nth-child(1) > form:nth-child(3) > input:nth-child(1)" );
    webElement = driver.findElement( by );
    origText = "";
    if( webElement.isSelected() ){
      origText = "on";
    }else{
      origText = "off";
    }
    String store946691901 = origText;

    //Element: Radiobutton 1 (OUTPUTSTOREDELEMENT) - store946691901
    by = By.cssSelector( "body > center:nth-child(1) > form:nth-child(3) > input:nth-child(1)" );
    webElement = driver.findElement( by );
    System.out.println( "Status of radiobutton 1: " + store946691901 );

    //Element: Radiobutton 1 (COMPAREVALUETOSTRING) - store946691901
    by = By.cssSelector( "body > center:nth-child(1) > form:nth-child(3) > input:nth-child(1)" );
    webElement = driver.findElement( by );
    origText = "";
    if( webElement.isSelected() ){
      origText = "on";
    }else{
      origText = "off";
    }
    if( !origText.equals( "off" ) ){
      fail("Stopped because the element 'store946691901': '" + origText + "' does NOT equal to 'off' but it should.");
    }

    //Element: Radiobutton 1 (LEFTCLICK) - store946691901
    by = By.cssSelector( "body > center:nth-child(1) > form:nth-child(3) > input:nth-child(1)" );
    webElement = driver.findElement( by );
    webElement.click();

    //Element: Radiobutton 1 (COMPAREVALUETOSTRING) - store946691901
    by = By.cssSelector( "body > center:nth-child(1) > form:nth-child(3) > input:nth-child(1)" );
    webElement = driver.findElement( by );
    origText = "";
    if( webElement.isSelected() ){
      origText = "on";
    }else{
      origText = "off";
    }
    if( !origText.equals( "on" ) ){
      fail("Stopped because the element 'store946691901': '" + origText + "' does NOT equal to 'on' but it should.");
    }

    //Script: Close Window (EXECUTE_SCRIPT) - script1802147026
    ScriptClass script1802147026 = new ScriptClass(){
      @Override
      public void runScript() throws Exception{
        driver.quit();
      }
    };
    try{
      script1802147026.runScript();
    }catch( Exception e ){
      e.printStackTrace();
      fail( e.getMessage() );
    }

  }

  @Test
  public void TestList(){

    profile = new FirefoxProfile();
    profile.setPreference( "pdfjs.disabled", true );
    profile.setPreference( "media.navigator.permission.disabled", true );
    profile.setPreference( "plugin.state.nppdf", 2 );
    driver = new FirefoxDriver(profile);

    //IMPLICIT WAIT
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

    //Script: Open Window (CLEARPARAMETERS) - script1378268624
    ScriptClass script1378268624 = new ScriptClass(){
      @Override
      public void runScript() throws Exception{
        String url = parameters.get(0);
        driver.get( url );
      }
    };
    script1378268624.clearParameters();

    script1378268624.addParameter( "http://www.sislands.com/coin70/week4/selectex.htm" );

    try{
      script1378268624.runScript();
    }catch( Exception e ){
      e.printStackTrace();
      fail( e.getMessage() );
    }

    //Element: Pulldown list (GAINLISTTOELEMENT) - store573072589
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > form:nth-child(4) > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > select:nth-child(2)" );
    webElement = driver.findElement( by );
    origText = "";
    select = new Select(webElement);
    origText = select.getFirstSelectedOption().getText();
    String store573072589 = origText;

    //Element: Normal list (CONTAINLISTSTOREDELEMENT) - store892511573
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > form:nth-child(4) > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2) > div:nth-child(1) > p:nth-child(1) > select:nth-child(3)" );
    webElement = driver.findElement( by );
    select = new Select(webElement);
    optionList = select.getOptions();
    found = false;
    for( WebElement option: optionList ){
      optionText = "";
      optionText = option.getText();
      if( optionText.equals( store573072589 ) ){
          found = true;
          break;
      }
    } //for( WebElement option: optionList )
    if( !found ){
      fail("Stopped because for the list 'store892511573' the expection is: 'Tartalmazza' BUT 'probiscus' is NOT in the list");
    }

    //Element: Normal list (CONTAINLISTSTRING) - store892511573
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
      fail("Stopped because for the list 'store892511573' the expection is: 'Tartalmazza' BUT 'spider' is NOT in the list");
    }

    //Element: Normal list (CONTAINLISTCONSTANT) - store892511573
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
      fail("Stopped because for the list 'store892511573' the expection is: 'Tartalmazza' BUT 'lemur' is NOT in the list");
    }

    //Element: Normal list (SELECTVARIABLEELEMENT) - store892511573
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > form:nth-child(4) > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2) > div:nth-child(1) > p:nth-child(1) > select:nth-child(3)" );
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByVisibleText( "lemur" );

    //Element: Normal list (SELECTSTRING) - store892511573
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > form:nth-child(4) > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2) > div:nth-child(1) > p:nth-child(1) > select:nth-child(3)" );
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByVisibleText( "spider" );

    //Element: Normal list (COMPARELISTTOSTRING) - store892511573
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > form:nth-child(4) > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2) > div:nth-child(1) > p:nth-child(1) > select:nth-child(3)" );
    webElement = driver.findElement( by );
    origText = "";
    select = new Select(webElement);
    origText = select.getFirstSelectedOption().getText();
    if( !origText.equals( "spider" ) ){
      fail("Stopped because the selected element in the Select 'store892511573': '" + origText + "' does NOT equal to 'spider' but it should.");
    }

    //Element: Normal list (COMPARELISTTOCONSTANT) - store892511573
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > form:nth-child(4) > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2) > div:nth-child(1) > p:nth-child(1) > select:nth-child(3)" );
    webElement = driver.findElement( by );
    origText = "";
    select = new Select(webElement);
    origText = select.getFirstSelectedOption().getText();
    if( !origText.equals( "spider" ) ){
      fail( "Stopped because the selected element in the Select 'store892511573': '" + origText + "' does NOT equal to 'spider' but it should." );
    }

    //Element: Normal list (GAINLISTTOELEMENT) - store892511573
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > form:nth-child(4) > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2) > div:nth-child(1) > p:nth-child(1) > select:nth-child(3)" );
    webElement = driver.findElement( by );
    origText = "";
    select = new Select(webElement);
    origText = select.getFirstSelectedOption().getText();
    String store892511573 = origText;

    //Element: Normal list (COMPARELISTTOSTOREDELEMENT) - store892511573
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > form:nth-child(4) > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2) > div:nth-child(1) > p:nth-child(1) > select:nth-child(3)" );
    webElement = driver.findElement( by );
    origText = "";
    select = new Select(webElement);
    origText = select.getFirstSelectedOption().getText();
    if( !origText.equals( store892511573 ) ){
      fail("Stopped because the selected element in the Select 'store892511573': '" + origText + "' does NOT equal to '" + store892511573 + "' but it should.");
    }

    //Element: Normal list (COMPARELISTTOCONSTANT) - store892511573
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > form:nth-child(4) > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2) > div:nth-child(1) > p:nth-child(1) > select:nth-child(3)" );
    webElement = driver.findElement( by );
    origText = "";
    select = new Select(webElement);
    origText = select.getFirstSelectedOption().getText();
    if( !origText.equals( "spider" ) ){
      fail( "Stopped because the selected element in the Select 'store892511573': '" + origText + "' does NOT equal to 'spider' but it should." );
    }

    //Element: Normal list (COMPARELISTTOSTRING) - store892511573
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > form:nth-child(4) > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2) > div:nth-child(1) > p:nth-child(1) > select:nth-child(3)" );
    webElement = driver.findElement( by );
    origText = "";
    select = new Select(webElement);
    origText = select.getFirstSelectedOption().getText();
    if( !origText.equals( "spider" ) ){
      fail("Stopped because the selected element in the Select 'store892511573': '" + origText + "' does NOT equal to 'spider' but it should.");
    }

    //Element: Normal list (OUTPUTSTOREDELEMENT) - store892511573
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > form:nth-child(4) > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2) > div:nth-child(1) > p:nth-child(1) > select:nth-child(3)" );
    webElement = driver.findElement( by );
    System.out.println( "Selected element in the normal list: " + store892511573 );

    //Script: Close Window (EXECUTE_SCRIPT) - script1802147026
    ScriptClass script1802147026 = new ScriptClass(){
      @Override
      public void runScript() throws Exception{
        driver.quit();
      }
    };
    try{
      script1802147026.runScript();
    }catch( Exception e ){
      e.printStackTrace();
      fail( e.getMessage() );
    }

  }

  @Test
  public void TestText(){

    profile = new FirefoxProfile();
    profile.setPreference( "pdfjs.disabled", true );
    profile.setPreference( "media.navigator.permission.disabled", true );
    profile.setPreference( "plugin.state.nppdf", 2 );
    driver = new FirefoxDriver(profile);

    //IMPLICIT WAIT
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

    //Script: Open Window (CLEARPARAMETERS) - script1378268624
    ScriptClass script1378268624 = new ScriptClass(){
      @Override
      public void runScript() throws Exception{
        String url = parameters.get(0);
        driver.get( url );
      }
    };
    script1378268624.clearParameters();

    script1378268624.addParameter( "http://www.sislands.com/coin70/week4/jstxtfld.htm" );

    try{
      script1378268624.runScript();
    }catch( Exception e ){
      e.printStackTrace();
      fail( e.getMessage() );
    }

    //Element: Main Title (GAINTEXTTOELEMENT) - store2145870263
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > h2:nth-child(2) > font:nth-child(1)" );
    webElement = driver.findElement( by );
    origText = webElement.getText();
    String store2145870263 = origText;

    //Element: Main Title (COMPARETEXTTOCONSTANT) - store2145870263
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > h2:nth-child(2) > font:nth-child(1)" );
    webElement = driver.findElement( by );
    origText = webElement.getText();
    if( !origText.equals( "Manipulating the Value of a Text Field" ) ){
      fail("Stopped because the element 'store2145870263': '" + origText + "' does NOT equal to 'Manipulating the Value of a Text Field' but it should.");
    }

    //Element: Main Title (COMPARETEXTTOSTRING) - store2145870263
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > h2:nth-child(2) > font:nth-child(1)" );
    webElement = driver.findElement( by );
    origText = webElement.getText();
    if( !origText.equals( "Manipulating the Value of a Text Field" ) ){
      fail("Stopped because the element 'store2145870263': '" + origText + "' does NOT equal to 'Manipulating the Value of a Text Field' but it should.");
    }

    //Element: Main Title (COMPARETEXTTOSTOREDELEMENT) - store2145870263
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > h2:nth-child(2) > font:nth-child(1)" );
    webElement = driver.findElement( by );
    origText = webElement.getText();
    if( !origText.equals( store2145870263 ) ){
      fail("Stopped because the element 'store2145870263': '" + origText + "' does NOT equal to '" + store2145870263 + "' but it should.");
    }

    //Element: Main Title (OUTPUTSTOREDELEMENT) - store2145870263
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > h2:nth-child(2) > font:nth-child(1)" );
    webElement = driver.findElement( by );
    System.out.println( "Title: " + store2145870263 );

    //Script: Close Window (EXECUTE_SCRIPT) - script1802147026
    ScriptClass script1802147026 = new ScriptClass(){
      @Override
      public void runScript() throws Exception{
        driver.quit();
      }
    };
    try{
      script1802147026.runScript();
    }catch( Exception e ){
      e.printStackTrace();
      fail( e.getMessage() );
    }

  }

  @Test
  public void FieldText(){

    profile = new FirefoxProfile();
    profile.setPreference( "pdfjs.disabled", true );
    profile.setPreference( "media.navigator.permission.disabled", true );
    profile.setPreference( "plugin.state.nppdf", 2 );
    driver = new FirefoxDriver(profile);

    //IMPLICIT WAIT
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

    //Script: Open Window (CLEARPARAMETERS) - script1378268624
    ScriptClass script1378268624 = new ScriptClass(){
      @Override
      public void runScript() throws Exception{
        String url = parameters.get(0);
        driver.get( url );
      }
    };
    script1378268624.clearParameters();

    script1378268624.addParameter( "http://www.sislands.com/coin70/week4/jstxtfld.htm" );

    try{
      script1378268624.runScript();
    }catch( Exception e ){
      e.printStackTrace();
      fail( e.getMessage() );
    }

    //Element: Field (GAINVALUETOELEMENT) - store377478853
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > form:nth-child(5) > div:nth-child(2) > center:nth-child(2) > p:nth-child(2) > font:nth-child(1) > input:nth-child(1)" );
    webElement = driver.findElement( by );
    origText = "";
    origText = webElement.getAttribute("value");
    String store377478853 = origText;

    //Element: Field (COMPAREVALUETOCONSTANT) - store377478853
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > form:nth-child(5) > div:nth-child(2) > center:nth-child(2) > p:nth-child(2) > font:nth-child(1) > input:nth-child(1)" );
    webElement = driver.findElement( by );
    origText = "";
    origText = webElement.getAttribute("value");
    if( !origText.equals( "Are you happy?" ) ){
      fail("Stopped because the element 'store377478853': '" + origText + "' does NOT equal to 'Are you happy?' but it should.");
    }

    //Element: Field (COMPAREVALUETOSTRING) - store377478853
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > form:nth-child(5) > div:nth-child(2) > center:nth-child(2) > p:nth-child(2) > font:nth-child(1) > input:nth-child(1)" );
    webElement = driver.findElement( by );
    origText = "";
    origText = webElement.getAttribute("value");
    if( !origText.equals( "Are you happy?" ) ){
      fail("Stopped because the element 'store377478853': '" + origText + "' does NOT equal to 'Are you happy?' but it should.");
    }

    //Element: Field (COMPAREVALUETOSTOREDELEMENT) - store377478853
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > form:nth-child(5) > div:nth-child(2) > center:nth-child(2) > p:nth-child(2) > font:nth-child(1) > input:nth-child(1)" );
    webElement = driver.findElement( by );
    origText = "";
    origText = webElement.getAttribute("value");
    if( !origText.equals( store377478853 ) ){
      fail("Stopped because the element 'store377478853': '" + origText + "' does NOT equal to '" + store377478853 + "' but it should.");
    }

    //Element: Field (OUTPUTSTOREDELEMENT) - store377478853
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > form:nth-child(5) > div:nth-child(2) > center:nth-child(2) > p:nth-child(2) > font:nth-child(1) > input:nth-child(1)" );
    webElement = driver.findElement( by );
    System.out.println( "Content of filed: " + store377478853 );

    //Script: Close Window (EXECUTE_SCRIPT) - script1802147026
    ScriptClass script1802147026 = new ScriptClass(){
      @Override
      public void runScript() throws Exception{
        driver.quit();
      }
    };
    try{
      script1802147026.runScript();
    }catch( Exception e ){
      e.printStackTrace();
      fail( e.getMessage() );
    }

  }

}
