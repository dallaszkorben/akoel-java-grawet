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
  public void Cycletest(){

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

    //Script: Open Window (CLEARPARAMETERS) - script5343
    ScriptClass script5343 = new ScriptClass(){
      @Override
      public void runScript() throws Exception{
        String url = parameters.get(0);
        driver.get( url );
      }
    };
    script5343.clearParameters();

    script5343.addParameter( "http://sislands.com/coin70/week4/chkBoxTest.htm" );

    try{
      script5343.runScript();
    }catch( Exception e ){
      e.printStackTrace();
      fail( e.getMessage() );
    }

    //Element: Checkbox1 (GAINVALUETOELEMENT) - store16433842
    by = By.cssSelector( "body > center:nth-child(1) > form:nth-child(4) > input:nth-child(1)" );
    webElement = driver.findElement( by );
    origText = "";
    if( webElement.isSelected() ){
      origText = "on";
    }else{
      origText = "off";
    }
    String store16433842 = origText;

    //Element: Checkbox1 (OUTPUTSTOREDELEMENT) - store16433842
    by = By.cssSelector( "body > center:nth-child(1) > form:nth-child(4) > input:nth-child(1)" );
    webElement = driver.findElement( by );
    System.out.println( "Status of Checkbox 1: " + store16433842 );

    //Element: Checkbox1 (COMPAREVALUETOSTRING) - store16433842
    by = By.cssSelector( "body > center:nth-child(1) > form:nth-child(4) > input:nth-child(1)" );
    webElement = driver.findElement( by );
    origText = "";
    if( webElement.isSelected() ){
      origText = "on";
    }else{
      origText = "off";
    }
    if( !origText.equals( "off" ) ){
      fail("Stopped because the element 'store16433842': '" + origText + "' does NOT equal to 'off' but it should.");
    }

    //Element: Checkbox1 (LEFTCLICK) - store16433842
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
      //Element: Checkbox1 (COMPAREVALUETOSTRING) - store16433842
      by = By.cssSelector( "body > center:nth-child(1) > form:nth-child(4) > input:nth-child(1)" );
      webElement = driver.findElement( by );
      origText = "";
      if( webElement.isSelected() ){
        origText = "on";
      }else{
        origText = "off";
      }
      if( !origText.equals( "on" ) ){
        break; //because the element 'store16433842' does NOT equal to 'on'.
      }

      //
      //Execution
      //
      //Element: Checkbox1 (GAINVALUETOELEMENT) - store16433842
      by = By.cssSelector( "body > center:nth-child(1) > form:nth-child(4) > input:nth-child(1)" );
      webElement = driver.findElement( by );
      origText = "";
      if( webElement.isSelected() ){
        origText = "on";
      }else{
        origText = "off";
      }
      store16433842 = origText;

      //Element: Checkbox1 (OUTPUTSTOREDELEMENT) - store16433842
      by = By.cssSelector( "body > center:nth-child(1) > form:nth-child(4) > input:nth-child(1)" );
      webElement = driver.findElement( by );
      System.out.println( "Status: " + store16433842 );

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

}
