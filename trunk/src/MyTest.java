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
  public void COMPAREPOSWEBCAMMODIFY(){

    profile = new FirefoxProfile();
    profile.setPreference( "pdfjs.disabled", true );
    profile.setPreference( "media.navigator.permission.disabled", true );
    profile.setPreference( "plugin.state.nppdf", 2 );
    driver = new FirefoxDriver(profile);

    //IMPLICIT WAIT
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

    //Script: Open Window (CLEARPARAMETERS) - script1297080391
    ScriptClass script1297080391 = new ScriptClass(){
      @Override
      public void runScript() throws Exception{
        String url = parameters.get(0);
        driver.get( url );
      }
    };
    script1297080391.clearParameters();

    script1297080391.addParameter( "http://tomcat01.statlogics.local:8090/RFBANK_TEST_Logic/auth.action" );

    try{
      script1297080391.runScript();
    }catch( Exception e ){
      e.printStackTrace();
      fail( e.getMessage() );
    }

    //Element: Version - Text (GAINTEXTTOELEMENT) - store1796512590
    by = By.cssSelector( "#RFTail" );
    webElement = driver.findElement( by );
    origText = webElement.getText();
    pattern = Pattern.compile( "\\d+[.]\\d+[.]\\d+[.]\\d" );
    matcher = pattern.matcher( origText );
    String store1796512590 = null;
    if( matcher.find() ){
      store1796512590 = matcher.group();
    }

    //Element: Version - Text (COMPARETEXTTOCONSTANT) - store1796512590
    by = By.cssSelector( "#RFTail" );
    webElement = driver.findElement( by );
    origText = webElement.getText();
    pattern = Pattern.compile( "v\\d+[.]\\d+[.]\\d+[.]\\d+" );
    matcher = pattern.matcher( origText );
    if( matcher.find() ){
        origText = matcher.group();
    }
    if( !origText.equals( "v3.0.0.0" ) ){
      fail("Stopped because the element 'store1796512590': '" + origText + "' does NOT equal to 'v3.0.0.0' but it should.");
    }

    //Element: English link (LEFTCLICK) - store2006974182
    by = By.cssSelector( "#languageSwitch > a:nth-child(2)" );
    webElement = driver.findElement( by );
    webElement.click();

    //Element: username (FILLVARIABLE) - store917454955
    by = By.id( "username" );
    webElement = driver.findElement( by );
    webElement.sendKeys("myuser1");     //username

    //Element: password (FILLVARIABLE) - store310466498
    by = By.id( "password" );
    webElement = driver.findElement( by );
    webElement.sendKeys("a");     //password

    //Element: Login button (LEFTCLICK) - store570181184
    by = By.id( "loginButton" );
    webElement = driver.findElement( by );
    webElement.click();

    //Script: Change Frame (CLEARPARAMETERS) - script960932754
    ScriptClass script960932754 = new ScriptClass(){
      @Override
      public void runScript() throws Exception{
        //There is proper parameter (frame name, waiting time [s])
        if( parameters.size() == 2 ){
        String frameName = parameters.get( 0 );
        Integer waitingTime = new Integer( parameters.get(1)  );
        org.openqa.selenium.support.ui.WebDriverWait wait = new WebDriverWait(driver, waitingTime);
        driver.switchTo().defaultContent();
        wait.until( org.openqa.selenium.support.ui.ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameName));
        driver.switchTo().defaultContent();
        driver.switchTo().frame( frameName );
        //Parameters are not good
        }else{
        hu.akoel.grawit.exceptions.ElementException e = new hu.akoel.grawit.exceptions.ElementScriptParameterException(  "More or less than 2 parameters.", "ChangeFrame"  );
        hu.akoel.grawit.exceptions.PageException p = new hu.akoel.grawit.exceptions.PageException( "wathever page", "Missing or wrong parameter", e );
        throw p;
        }
      }
    };
    script960932754.clearParameters();
    script960932754.addParameter( "menuFrame" );
    script960932754.addParameter( "3" );
    try{
      script960932754.runScript();
    }catch( Exception e ){
      e.printStackTrace();
      fail( e.getMessage() );
    }

    //Element: POS menu (LEFTCLICK) - store63319065
    by = By.cssSelector( "#pos_menu" );
    webElement = driver.findElement( by );
    webElement.click();

    //Element: POS - Application (menu) (LEFTCLICK) - store1096104111
    by = By.cssSelector( "#pos_application_statusreport > a:nth-child(1)" );
    wait = new WebDriverWait(driver, 3); //EXPLICIT WAIT
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.click();

    script960932754.clearParameters();
    script960932754.addParameter( "mainFrame" );
    script960932754.addParameter( "3" );
    try{
      script960932754.runScript();
    }catch( Exception e ){
      e.printStackTrace();
      fail( e.getMessage() );
    }

    //Element: Date to (CLEAR) - store2035206604
    by = By.id( "toDate" );
    webElement = driver.findElement( by );
    webElement.clear();

    //Element: Date to (FILLSTRING) - store2035206604
    by = By.id( "toDate" );
    webElement = driver.findElement( by );
    webElement.sendKeys("01/01/2222");     //Date to

    //Element: Application number (CLEAR) - store45947534
    by = By.id( "applicationNb" );
    webElement = driver.findElement( by );
    webElement.clear();

    //Element: Application number (FILLELEMENT) - store45947534
    by = By.id( "applicationNb" );
    webElement = driver.findElement( by );
    webElement.sendKeys("13000000446");     //Application number

    //Element: Application number (GAINVALUETOELEMENT) - store45947534
    by = By.id( "applicationNb" );
    webElement = driver.findElement( by );
    origText = "";
    origText = webElement.getAttribute("value");
    String store45947534 = origText;


    //Element: Search - BUTTON (LEFTCLICK) - store309169245
    by = By.id( "searchButton" );
    webElement = driver.findElement( by );
    webElement.click();
System.err.println("itt lesz valami");
    //Element: Status From POS List (GAINTEXTTOELEMENT) - store2106724763

    by = By.cssSelector( "#hello > td:nth-child(4)" );
    webElement = driver.findElement( by );
    origText = webElement.getText();
    String store2106724763 = origText;

System.err.println("tul vagyok az elson");
    //Element: Application number field on the POS status list (LEFTCLICK) - store2085887233
    by = By.cssSelector( "#\\31  > td:nth-child(2)" );
    webElement = driver.findElement( by );
    webElement.click();
System.err.println("tul vagyok");
    //Element: MENU - Modify (LEFTCLICK) - store1618067391
    by = By.id( "menuItem1-MODIFYAPPLICATION" );
    webElement = driver.findElement( by );
    webElement.click();


    //Element: Close (LEFTCLICK) - store23892797
    by = By.id( "repayment_dialog_close" );
    webElement = driver.findElement( by );
    webElement.click();

    //Element: Next button (LEFTCLICK) - store1235502153
    by = By.id( "nextButton" );
    webElement = driver.findElement( by );
    webElement.click();

    //Element: Shop assistant name (COMPAREVALUETOSTOREDELEMENT) - store837995875
    by = By.id( "shopAssistantName" );
    webElement = driver.findElement( by );
  }

}
