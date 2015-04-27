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

  @Test
  public void TestCheckbox(){

    profile = new FirefoxProfile();
    profile.setPreference( "pdfjs.disabled", true );
    profile.setPreference( "media.navigator.permission.disabled", true );
    profile.setPreference( "plugin.state.nppdf", 2 );
    driver = new FirefoxDriver(profile);

    //IMPLICIT WAIT
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

    //Script: Open Window (CLEARPARAMETERS) - script217606767
    ScriptClass script217606767 = new ScriptClass(){
      @Override
      public void runScript() throws Exception{
        String url = parameters.get(0);
        driver.get( url );
      }
    };
    script217606767.clearParameters();

    //Script: Open Window (ADDCONSTANTTOPARAMETERS) - script217606767
    ScriptClass script217606767 = new ScriptClass(){
      @Override
      public void runScript() throws Exception{
        String url = parameters.get(0);
        driver.get( url );
      }
    };
    script217606767.addParameter( "http://sislands.com/coin70/week4/chkBoxTest.htm" );

    //Script: Open Window (EXECUTE_SCRIPT) - script217606767
    ScriptClass script217606767 = new ScriptClass(){
      @Override
      public void runScript() throws Exception{
        String url = parameters.get(0);
        driver.get( url );
      }
    };
    try{
      script217606767.runScript();
    }catch( Exception e ){
      e.printStackTrace();
      fail( e.getMessage() );
    }

    //Element: Checkbox1 (GAINVALUETOELEMENT) - store2037419976
    by = By.cssSelector( "body > center:nth-child(1) > form:nth-child(4) > input:nth-child(1)" );
    webElement = driver.findElement( by );
    origText = "";
    if( webElement.isSelected() ){
      origText = "on";
    }else{
      origText = "off";
    }
    String store2037419976 = origText;

    //Element: Checkbox1 (OUTPUTSTOREDELEMENT) - store2037419976
    by = By.cssSelector( "body > center:nth-child(1) > form:nth-child(4) > input:nth-child(1)" );
    webElement = driver.findElement( by );
    System.out.println( "Status of Checkbox 1: " + store2037419976 );

    //Element: Checkbox1 (COMPAREVALUETOSTRING) - store2037419976
    by = By.cssSelector( "body > center:nth-child(1) > form:nth-child(4) > input:nth-child(1)" );
    webElement = driver.findElement( by );
    origText = "";
    if( webElement.isSelected() ){
      origText = "on";
    }else{
      origText = "off";
    }
    if( !origText.equals( "off" ) ){
      fail("Stopped because the element 'store2037419976': '" + origText + "' does NOT equal to 'off' but it should.");
    }

    //Element: Checkbox1 (LEFTCLICK) - store2037419976
    by = By.cssSelector( "body > center:nth-child(1) > form:nth-child(4) > input:nth-child(1)" );
    webElement = driver.findElement( by );
    webElement.click();

    //Script: Close Window (EXECUTE_SCRIPT) - script152282128
    ScriptClass script152282128 = new ScriptClass(){
      @Override
      public void runScript() throws Exception{
        driver.quit();
      }
    };
    try{
      script152282128.runScript();
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

    //Script: Open Window (CLEARPARAMETERS) - script217606767
    ScriptClass script217606767 = new ScriptClass(){
      @Override
      public void runScript() throws Exception{
        String url = parameters.get(0);
        driver.get( url );
      }
    };
    script217606767.clearParameters();

    //Script: Open Window (ADDCONSTANTTOPARAMETERS) - script217606767
    ScriptClass script217606767 = new ScriptClass(){
      @Override
      public void runScript() throws Exception{
        String url = parameters.get(0);
        driver.get( url );
      }
    };
    script217606767.addParameter( "http://www.sislands.com/coin70/week4/rdoBtnTest.htm" );

    //Script: Open Window (EXECUTE_SCRIPT) - script217606767
    ScriptClass script217606767 = new ScriptClass(){
      @Override
      public void runScript() throws Exception{
        String url = parameters.get(0);
        driver.get( url );
      }
    };
    try{
      script217606767.runScript();
    }catch( Exception e ){
      e.printStackTrace();
      fail( e.getMessage() );
    }

    //Element: Radiobutton 1 (GAINVALUETOELEMENT) - store932911332
    by = By.cssSelector( "body > center:nth-child(1) > form:nth-child(3) > input:nth-child(1)" );
    webElement = driver.findElement( by );
    origText = "";
    if( webElement.isSelected() ){
      origText = "on";
    }else{
      origText = "off";
    }
    String store932911332 = origText;

    //Element: Radiobutton 1 (OUTPUTSTOREDELEMENT) - store932911332
    by = By.cssSelector( "body > center:nth-child(1) > form:nth-child(3) > input:nth-child(1)" );
    webElement = driver.findElement( by );
    System.out.println( "Status of radiobutton 1: " + store932911332 );

    //Element: Radiobutton 1 (COMPAREVALUETOSTRING) - store932911332
    by = By.cssSelector( "body > center:nth-child(1) > form:nth-child(3) > input:nth-child(1)" );
    webElement = driver.findElement( by );
    origText = "";
    if( webElement.isSelected() ){
      origText = "on";
    }else{
      origText = "off";
    }
    if( !origText.equals( "off" ) ){
      fail("Stopped because the element 'store932911332': '" + origText + "' does NOT equal to 'off' but it should.");
    }

    //Element: Radiobutton 1 (LEFTCLICK) - store932911332
    by = By.cssSelector( "body > center:nth-child(1) > form:nth-child(3) > input:nth-child(1)" );
    webElement = driver.findElement( by );
    webElement.click();

    //Element: Radiobutton 1 (COMPAREVALUETOSTRING) - store932911332
    by = By.cssSelector( "body > center:nth-child(1) > form:nth-child(3) > input:nth-child(1)" );
    webElement = driver.findElement( by );
    origText = "";
    if( webElement.isSelected() ){
      origText = "on";
    }else{
      origText = "off";
    }
    if( !origText.equals( "on" ) ){
      fail("Stopped because the element 'store932911332': '" + origText + "' does NOT equal to 'on' but it should.");
    }

    //Script: Close Window (EXECUTE_SCRIPT) - script152282128
    ScriptClass script152282128 = new ScriptClass(){
      @Override
      public void runScript() throws Exception{
        driver.quit();
      }
    };
    try{
      script152282128.runScript();
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

    //Script: Open Window (CLEARPARAMETERS) - script217606767
    ScriptClass script217606767 = new ScriptClass(){
      @Override
      public void runScript() throws Exception{
        String url = parameters.get(0);
        driver.get( url );
      }
    };
    script217606767.clearParameters();

    //Script: Open Window (ADDCONSTANTTOPARAMETERS) - script217606767
    ScriptClass script217606767 = new ScriptClass(){
      @Override
      public void runScript() throws Exception{
        String url = parameters.get(0);
        driver.get( url );
      }
    };
    script217606767.addParameter( "http://www.sislands.com/coin70/week4/selectex.htm" );

    //Script: Open Window (EXECUTE_SCRIPT) - script217606767
    ScriptClass script217606767 = new ScriptClass(){
      @Override
      public void runScript() throws Exception{
        String url = parameters.get(0);
        driver.get( url );
      }
    };
    try{
      script217606767.runScript();
    }catch( Exception e ){
      e.printStackTrace();
      fail( e.getMessage() );
    }

    //Element: Pulldown list (GAINLISTTOELEMENT) - store465802925
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > form:nth-child(4) > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > select:nth-child(2)" );
    webElement = driver.findElement( by );
    origText = "";
    select = new Select(webElement);
    origText = select.getFirstSelectedOption().getText();
    String store465802925 = origText;

    //Element: Normal list (CONTAINLISTSTOREDELEMENT) - store1743833346
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > form:nth-child(4) > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2) > div:nth-child(1) > p:nth-child(1) > select:nth-child(3)" );
    webElement = driver.findElement( by );
    select = new Select(webElement);
    optionList = select.getOptions();
    found = false;
    for( WebElement option: optionList ){
      optionText = "";
      optionText = option.getText();
      if( optionText.equals( store465802925 ) ){
          found = true;
          break;
      }
    } //for( WebElement option: optionList )
    if( !found ){
      fail("Stopped because for the list 'store1743833346' the expection is: 'Tartalmazza' BUT 'probiscus' is NOT in the list");
    }

    //Element: Normal list (CONTAINLISTSTRING) - store1743833346
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
      fail("Stopped because for the list 'store1743833346' the expection is: 'Tartalmazza' BUT 'spider' is NOT in the list");
    }

    //Element: Normal list (CONTAINLISTCONSTANT) - store1743833346
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
      fail("Stopped because for the list 'store1743833346' the expection is: 'Tartalmazza' BUT 'lemur' is NOT in the list");
    }

    //Element: Normal list (SELECTVARIABLEELEMENT) - store1743833346
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > form:nth-child(4) > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2) > div:nth-child(1) > p:nth-child(1) > select:nth-child(3)" );
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByVisibleText( "lemur" );

    //Element: Normal list (SELECTSTRING) - store1743833346
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > form:nth-child(4) > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2) > div:nth-child(1) > p:nth-child(1) > select:nth-child(3)" );
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByVisibleText( "spider" );

    //Element: Normal list (COMPARELISTTOSTRING) - store1743833346
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > form:nth-child(4) > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2) > div:nth-child(1) > p:nth-child(1) > select:nth-child(3)" );
    webElement = driver.findElement( by );
    origText = "";
    select = new Select(webElement);
    origText = select.getFirstSelectedOption().getText();
    if( !origText.equals( "spider" ) ){
      fail("Stopped because the selected element in the Select 'store1743833346': '" + origText + "' does NOT equal to 'spider' but it should.");
    }

    //Element: Normal list (COMPARELISTTOCONSTANT) - store1743833346
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > form:nth-child(4) > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2) > div:nth-child(1) > p:nth-child(1) > select:nth-child(3)" );
    webElement = driver.findElement( by );
    origText = "";
    select = new Select(webElement);
    origText = select.getFirstSelectedOption().getText();
    if( !origText.equals( "spider" ) ){
      fail( "Stopped because the selected element in the Select 'store1743833346': '" + origText + "' does NOT equal to 'spider' but it should." );
    }

    //Element: Normal list (GAINLISTTOELEMENT) - store1743833346
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > form:nth-child(4) > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2) > div:nth-child(1) > p:nth-child(1) > select:nth-child(3)" );
    webElement = driver.findElement( by );
    origText = "";
    select = new Select(webElement);
    origText = select.getFirstSelectedOption().getText();
    String store1743833346 = origText;

    //Element: Normal list (COMPARELISTTOSTOREDELEMENT) - store1743833346
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > form:nth-child(4) > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2) > div:nth-child(1) > p:nth-child(1) > select:nth-child(3)" );
    webElement = driver.findElement( by );
    origText = "";
    select = new Select(webElement);
    origText = select.getFirstSelectedOption().getText();
    if( !origText.equals( store1743833346 ) ){
      fail("Stopped because the selected element in the Select 'store1743833346': '" + origText + "' does NOT equal to '" + store1743833346 + "' but it should.");
    }

    //Element: Normal list (COMPARELISTTOCONSTANT) - store1743833346
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > form:nth-child(4) > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2) > div:nth-child(1) > p:nth-child(1) > select:nth-child(3)" );
    webElement = driver.findElement( by );
    origText = "";
    select = new Select(webElement);
    origText = select.getFirstSelectedOption().getText();
    if( !origText.equals( "spider" ) ){
      fail( "Stopped because the selected element in the Select 'store1743833346': '" + origText + "' does NOT equal to 'spider' but it should." );
    }

    //Element: Normal list (COMPARELISTTOSTRING) - store1743833346
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > form:nth-child(4) > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2) > div:nth-child(1) > p:nth-child(1) > select:nth-child(3)" );
    webElement = driver.findElement( by );
    origText = "";
    select = new Select(webElement);
    origText = select.getFirstSelectedOption().getText();
    if( !origText.equals( "spider" ) ){
      fail("Stopped because the selected element in the Select 'store1743833346': '" + origText + "' does NOT equal to 'spider' but it should.");
    }

    //Element: Normal list (OUTPUTSTOREDELEMENT) - store1743833346
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > form:nth-child(4) > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2) > div:nth-child(1) > p:nth-child(1) > select:nth-child(3)" );
    webElement = driver.findElement( by );
    System.out.println( "Selected element in the normal list: " + store1743833346 );

    //Script: Close Window (EXECUTE_SCRIPT) - script152282128
    ScriptClass script152282128 = new ScriptClass(){
      @Override
      public void runScript() throws Exception{
        driver.quit();
      }
    };
    try{
      script152282128.runScript();
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

    //Script: Open Window (CLEARPARAMETERS) - script217606767
    ScriptClass script217606767 = new ScriptClass(){
      @Override
      public void runScript() throws Exception{
        String url = parameters.get(0);
        driver.get( url );
      }
    };
    script217606767.clearParameters();

    //Script: Open Window (ADDCONSTANTTOPARAMETERS) - script217606767
    ScriptClass script217606767 = new ScriptClass(){
      @Override
      public void runScript() throws Exception{
        String url = parameters.get(0);
        driver.get( url );
      }
    };
    script217606767.addParameter( "http://www.sislands.com/coin70/week4/jstxtfld.htm" );

    //Script: Open Window (EXECUTE_SCRIPT) - script217606767
    ScriptClass script217606767 = new ScriptClass(){
      @Override
      public void runScript() throws Exception{
        String url = parameters.get(0);
        driver.get( url );
      }
    };
    try{
      script217606767.runScript();
    }catch( Exception e ){
      e.printStackTrace();
      fail( e.getMessage() );
    }

    //Element: Main Title (GAINTEXTTOELEMENT) - store781592444
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > h2:nth-child(2) > font:nth-child(1)" );
    webElement = driver.findElement( by );
    origText = webElement.getText();
    String store781592444 = origText;

    //Element: Main Title (COMPARETEXTTOCONSTANT) - store781592444
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > h2:nth-child(2) > font:nth-child(1)" );
    webElement = driver.findElement( by );
    origText = webElement.getText();
    if( !origText.equals( "Manipulating the Value of a Text Field" ) ){
      fail("Stopped because the element 'store781592444': '" + origText + "' does NOT equal to 'Manipulating the Value of a Text Field' but it should.");
    }

    //Element: Main Title (COMPARETEXTTOSTRING) - store781592444
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > h2:nth-child(2) > font:nth-child(1)" );
    webElement = driver.findElement( by );
    origText = webElement.getText();
    if( !origText.equals( "Manipulating the Value of a Text Field" ) ){
      fail("Stopped because the element 'store781592444': '" + origText + "' does NOT equal to 'Manipulating the Value of a Text Field' but it should.");
    }

    //Element: Main Title (COMPARETEXTTOSTOREDELEMENT) - store781592444
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > h2:nth-child(2) > font:nth-child(1)" );
    webElement = driver.findElement( by );
    origText = webElement.getText();
    if( !origText.equals( store781592444 ) ){
      fail("Stopped because the element 'store781592444': '" + origText + "' does NOT equal to '" + store781592444 + "' but it should.");
    }

    //Element: Main Title (OUTPUTSTOREDELEMENT) - store781592444
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > h2:nth-child(2) > font:nth-child(1)" );
    webElement = driver.findElement( by );
    System.out.println( "Title: " + store781592444 );

    //Script: Close Window (EXECUTE_SCRIPT) - script152282128
    ScriptClass script152282128 = new ScriptClass(){
      @Override
      public void runScript() throws Exception{
        driver.quit();
      }
    };
    try{
      script152282128.runScript();
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

    //Script: Open Window (CLEARPARAMETERS) - script217606767
    ScriptClass script217606767 = new ScriptClass(){
      @Override
      public void runScript() throws Exception{
        String url = parameters.get(0);
        driver.get( url );
      }
    };
    script217606767.clearParameters();

    //Script: Open Window (ADDCONSTANTTOPARAMETERS) - script217606767
    ScriptClass script217606767 = new ScriptClass(){
      @Override
      public void runScript() throws Exception{
        String url = parameters.get(0);
        driver.get( url );
      }
    };
    script217606767.addParameter( "http://www.sislands.com/coin70/week4/jstxtfld.htm" );

    //Script: Open Window (EXECUTE_SCRIPT) - script217606767
    ScriptClass script217606767 = new ScriptClass(){
      @Override
      public void runScript() throws Exception{
        String url = parameters.get(0);
        driver.get( url );
      }
    };
    try{
      script217606767.runScript();
    }catch( Exception e ){
      e.printStackTrace();
      fail( e.getMessage() );
    }

    //Element: Field (GAINVALUETOELEMENT) - store1013411282
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > form:nth-child(5) > div:nth-child(2) > center:nth-child(2) > p:nth-child(2) > font:nth-child(1) > input:nth-child(1)" );
    webElement = driver.findElement( by );
    origText = "";
    origText = webElement.getAttribute("value");
    String store1013411282 = origText;

    //Element: Field (COMPAREVALUETOCONSTANT) - store1013411282
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > form:nth-child(5) > div:nth-child(2) > center:nth-child(2) > p:nth-child(2) > font:nth-child(1) > input:nth-child(1)" );
    webElement = driver.findElement( by );
    origText = "";
    origText = webElement.getAttribute("value");
    if( !origText.equals( "Are you happy?" ) ){
      fail("Stopped because the element 'store1013411282': '" + origText + "' does NOT equal to 'Are you happy?' but it should.");
    }

    //Element: Field (COMPAREVALUETOSTRING) - store1013411282
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > form:nth-child(5) > div:nth-child(2) > center:nth-child(2) > p:nth-child(2) > font:nth-child(1) > input:nth-child(1)" );
    webElement = driver.findElement( by );
    origText = "";
    origText = webElement.getAttribute("value");
    if( !origText.equals( "Are you happy?" ) ){
      fail("Stopped because the element 'store1013411282': '" + origText + "' does NOT equal to 'Are you happy?' but it should.");
    }

    //Element: Field (COMPAREVALUETOSTOREDELEMENT) - store1013411282
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > form:nth-child(5) > div:nth-child(2) > center:nth-child(2) > p:nth-child(2) > font:nth-child(1) > input:nth-child(1)" );
    webElement = driver.findElement( by );
    origText = "";
    origText = webElement.getAttribute("value");
    if( !origText.equals( store1013411282 ) ){
      fail("Stopped because the element 'store1013411282': '" + origText + "' does NOT equal to '" + store1013411282 + "' but it should.");
    }

    //Element: Field (OUTPUTSTOREDELEMENT) - store1013411282
    by = By.cssSelector( "body > div:nth-child(1) > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > form:nth-child(5) > div:nth-child(2) > center:nth-child(2) > p:nth-child(2) > font:nth-child(1) > input:nth-child(1)" );
    webElement = driver.findElement( by );
    System.out.println( "Content of filed: " + store1013411282 );

    //Script: Close Window (EXECUTE_SCRIPT) - script152282128
    ScriptClass script152282128 = new ScriptClass(){
      @Override
      public void runScript() throws Exception{
        driver.quit();
      }
    };
    try{
      script152282128.runScript();
    }catch( Exception e ){
      e.printStackTrace();
      fail( e.getMessage() );
    }

  }

}
