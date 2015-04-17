import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
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

    //Script: Open Window (CLEARPARAMETERS) - script207841178
    ScriptClass script207841178 = new ScriptClass(){
      @Override
      public void runScript() throws Exception{
        String url = parameters.get(0);
        driver.get( url );
      }
    };
    script207841178.clearParameters();

    //Script: Open Window (ADDCONSTANTTOPARAMETERS) - script207841178

    script207841178.addParameter( "http://tomcat01.statlogics.local:8090/RFBANK_TEST_Logic/auth.action" );

    //Script: Open Window (EXECUTE_SCRIPT) - script207841178

    try{
      script207841178.runScript();
    }catch( Exception e ){
      e.printStackTrace();
      System.exit(-1);
      }

    //Script: Logout (EXECUTE_SCRIPT) - script1203821418
    ScriptClass script1203821418 = new ScriptClass(){
      @Override
      public void runScript() throws Exception{
        WebDriverWait wait = new WebDriverWait(driver, 1);
        By by = By.id("logout");
        try{
        wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
        WebElement  webElement = driver.findElement( by );
        //Click
        org.openqa.selenium.JavascriptExecutor executor = (org.openqa.selenium.JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", webElement);
        }catch( WebDriverException e){
        }
      }
    };
    try{
      script1203821418.runScript();
    }catch( Exception e ){
      e.printStackTrace();
      System.exit(-1);
      }

    //Element: Version - Text (GAINTEXTTOELEMENT) - store105275044
    wait = new WebDriverWait(driver, 10);
    by = By.cssSelector( "#RFTail" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    origText = webElement.getText();
    pattern = Pattern.compile( "\\d+[.]\\d+[.]\\d+[.]\\d" );
    matcher = pattern.matcher( origText );
    if( matcher.find() ){
    String   store105275044 = matcher.group();
    }

    //Element: Version - Text (COMPARETEXTTOVARIABLE) - store105275044
    wait = new WebDriverWait(driver, 10);
    by = By.cssSelector( "#RFTail" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    origText = webElement.getText();
    pattern = Pattern.compile( "v\\d+[.]\\d+[.]\\d+[.]\\d+" );
    matcher = pattern.matcher( origText );
    if( matcher.find() ){
        origText = matcher.group();
    }

    //Element: English link (LEFTCLICK) - store1411643378
    wait = new WebDriverWait(driver, 10);
    by = By.cssSelector( "#languageSwitch > a:nth-child(2)" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.click();

    //Element: username (FILLVARIABLE) - store1910347846
    wait = new WebDriverWait(driver, 10);
    by = By.id( "username" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.sendKeys("myuser1");     //username

    //Element: password (FILLVARIABLE) - store2069701889
    wait = new WebDriverWait(driver, 10);
    by = By.id( "password" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.sendKeys("a");     //password

    //Element: Login button (LEFTCLICK) - store99647459
    wait = new WebDriverWait(driver, 10);
    by = By.id( "loginButton" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.click();

    //Script: Change Frame (CLEARPARAMETERS) - script1522190323
    ScriptClass script1522190323 = new ScriptClass(){
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
    script1522190323.clearParameters();

    //Script: Change Frame (ADDSTRINGTOPARAMETERS) - script1522190323
    script1522190323.addParameter( "menuFrame" );

    //Script: Change Frame (ADDSTRINGTOPARAMETERS) - script1522190323
    script1522190323.addParameter( "3" );

    //Script: Change Frame (EXECUTE_SCRIPT) - script1522190323
    try{
      script1522190323.runScript();
    }catch( Exception e ){
      e.printStackTrace();
      System.exit(-1);
      }

    //Element: POS menu (LEFTCLICK) - store2119716982
    wait = new WebDriverWait(driver, 10);
    by = By.cssSelector( "#pos_menu" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.click();

    //Element: POS - Application (menu) (LEFTCLICK) - store1279472540
    wait = new WebDriverWait(driver, 10);
    by = By.cssSelector( "#pos_application > a:nth-child(1)" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.click();

    //Script: Change Frame (CLEARPARAMETERS) - script1522190323
    script1522190323.clearParameters();

    //Script: Change Frame (ADDSTRINGTOPARAMETERS) - script1522190323
    script1522190323.addParameter( "mainFrame" );

    //Script: Change Frame (ADDSTRINGTOPARAMETERS) - script1522190323
    script1522190323.addParameter( "3" );

    //Script: Change Frame (EXECUTE_SCRIPT) - script1522190323
    try{
      script1522190323.runScript();
    }catch( Exception e ){
      e.printStackTrace();
      System.exit(-1);
      }

    //Element: Merchant (FILLVARIABLE) - store11954150
    wait = new WebDriverWait(driver, 10);
    by = By.id( "intermediaryCode_widget" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.sendKeys("mymerchant");     //Merchant

    //Element: Merchant (TAB) - store11954150
    wait = new WebDriverWait(driver, 10);
    by = By.id( "intermediaryCode_widget" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);

    //Element: Purpose 1 (SELECTVARIABLEELEMENT) - store1178858079
    wait = new WebDriverWait(driver, 10);
    by = By.id( "asset_products0_purposeId" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    select = new Select(webElement);
    select.selectByVisibleText( "mygroupofgoods" );

    //Element: Factory 1 (SELECTVARIABLEELEMENT) - store1777380050
    wait = new WebDriverWait(driver, 10);
    by = By.id( "asset_products0_factoryId" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    select = new Select(webElement);
    select.selectByVisibleText( "mybrand" );

    //Element: Description 1 (FILLVARIABLE) - store1655061437
    wait = new WebDriverWait(driver, 10);
    by = By.id( "asset_products0_credObjName" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.sendKeys("owyktqfwboxvguw");     //Description 1

    //Element: Price 1 (FILLVARIABLE) - store1220887329
    wait = new WebDriverWait(driver, 10);
    by = By.id( "asset_products0_price-input" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.sendKeys("12000");     //Price 1

    //Element: Actions (SELECTVARIABLEELEMENT) - store335255576
    wait = new WebDriverWait(driver, 10);
    by = By.id( "loanProduct_actionId" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    select = new Select(webElement);
    select.selectByVisibleText( "myposaction" );

    //Element: Construction (SELECTVARIABLEELEMENT) - store454730149
    wait = new WebDriverWait(driver, 10);
    by = By.id( "loanProduct_constrCode" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    select = new Select(webElement);
    select.selectByVisibleText( "myPOSCheap2Nofee" );

    //Element: Product1+ (LEFTCLICK) - store1806564442
    wait = new WebDriverWait(driver, 10);
    by = By.id( "addButton0" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.click();

    //Element: Purpose 2 (SELECTVARIABLEELEMENT) - store1210763549
    wait = new WebDriverWait(driver, 10);
    by = By.id( "asset_products1_purposeId" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    select = new Select(webElement);
    select.selectByVisibleText( "mygroupofgoods" );

    //Element: Factory 2 (SELECTVARIABLEELEMENT) - store330027158
    wait = new WebDriverWait(driver, 10);
    by = By.id( "asset_products1_factoryId" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    select = new Select(webElement);
    select.selectByVisibleText( "mybrand" );

    //Element: Description 2 (FILLVARIABLE) - store553775320
    wait = new WebDriverWait(driver, 10);
    by = By.id( "asset_products1_credObjName" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.sendKeys("xzchhwrvmjvbrxc");     //Description 2

    //Element: Price 2 (FILLVARIABLE) - store1021725169
    wait = new WebDriverWait(driver, 10);
    by = By.id( "asset_products1_price-input" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.sendKeys("12000");     //Price 2

    //Element: Duration (SELECTVARIABLEELEMENT) - store971276740
    wait = new WebDriverWait(driver, 10);
    by = By.id( "loanProduct_durationPlusTermDelay" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    select = new Select(webElement);
    select.selectByVisibleText( "12" );

    //Element: Term delay (SELECTVARIABLEELEMENT) - store1191212714
    wait = new WebDriverWait(driver, 10);
    by = By.id( "loanProduct_termDelay" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    select = new Select(webElement);
    select.selectByVisibleText( "1" );

    //Element: Down payment (FILLVARIABLE) - store444692817
    wait = new WebDriverWait(driver, 10);
    by = By.id( "loanProduct_downPay-input" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.sendKeys("12000");     //Down payment
    webElement.sendKeys(Keys.TAB); 

    wait = new WebDriverWait(driver, 10);
    by = By.id( "additionalServices_additionalServicesAdded" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB); 
    
    
    wait = new WebDriverWait(driver, 10);
    by = By.id( "additionalServices_additionalServices0_typeId" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    select = new Select(webElement);
    select.selectByVisibleText( "myPOSLife-InsuranceService" );

    
    
/*    
    
    
    //Element: AS1 Type (SELECTVARIABLEELEMENT) - store808025905
    wait = new WebDriverWait(driver, 10);
    by = By.id( "additionalServices_additionalServices0_typeId" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    select = new Select(webElement);
    select.selectByVisibleText( "myPOSLife-InsuranceService" );
*/
/*    //Element: AS1Company (SELECTVARIABLEELEMENT) - store299913414
    wait = new WebDriverWait(driver, 10);
    by = By.id( "additionalServices_additionalServices0_companyId" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    select = new Select(webElement);
    select.selectByVisibleText( "mycomp" );
*/    
  }
}
