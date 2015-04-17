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

    //Script: Open Window (CLEARPARAMETERS) - script1610686732
    ScriptClass script1610686732 = new ScriptClass(){
      @Override
      public void runScript() throws Exception{
        String url = parameters.get(0);
        driver.get( url );
      }
    };
    script1610686732.clearParameters();

    //Script: Open Window (ADDCONSTANTTOPARAMETERS) - script1610686732
    ScriptClass script1610686732 = new ScriptClass(){
      @Override
      public void runScript() throws Exception{
        String url = parameters.get(0);
        driver.get( url );
      }
    };
    script1610686732.addParameter( "http://tomcat01.statlogics.local:8090/RFBANK_TEST_Logic/auth.action" );

    //Script: Open Window (EXECUTE_SCRIPT) - script1610686732
    ScriptClass script1610686732 = new ScriptClass(){
      @Override
      public void runScript() throws Exception{
        String url = parameters.get(0);
        driver.get( url );
      }
    };
    try{
      script1610686732.runScript();
    }catch( Exception e ){
      e.printStackTrace();
      System.exit(-1);
      }

    //Script: Logout (EXECUTE_SCRIPT) - script2118595073
    ScriptClass script2118595073 = new ScriptClass(){
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
      script2118595073.runScript();
    }catch( Exception e ){
      e.printStackTrace();
      System.exit(-1);
      }

    //Element: Version - Text (GAINTEXTTOELEMENT) - store514408199
    wait = new WebDriverWait(driver, 10);
    by = By.cssSelector( "#RFTail" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    origText = webElement.getText();
    pattern = Pattern.compile( "\\d+[.]\\d+[.]\\d+[.]\\d" );
    matcher = pattern.matcher( origText );
    if( matcher.find() ){
    String   store514408199 = matcher.group();
    }

    //Element: Version - Text (COMPARETEXTTOVARIABLE) - store514408199
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

    //Element: English link (LEFTCLICK) - store743428206
    wait = new WebDriverWait(driver, 10);
    by = By.cssSelector( "#languageSwitch > a:nth-child(2)" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.click();

    //Element: username (FILLVARIABLE) - store1091963180
    wait = new WebDriverWait(driver, 10);
    by = By.id( "username" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.sendKeys("myuser1");     //username

    //Element: password (FILLVARIABLE) - store844034217
    wait = new WebDriverWait(driver, 10);
    by = By.id( "password" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.sendKeys("a");     //password

    //Element: Login button (LEFTCLICK) - store1612498823
    wait = new WebDriverWait(driver, 10);
    by = By.id( "loginButton" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.click();

    //Script: Change Frame (CLEARPARAMETERS) - script255871402
    ScriptClass script255871402 = new ScriptClass(){
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
    script255871402.clearParameters();

    //Script: Change Frame (ADDSTRINGTOPARAMETERS) - script255871402
    ScriptClass script255871402 = new ScriptClass(){
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
    script255871402.addParameter( "menuFrame" );

    //Script: Change Frame (ADDSTRINGTOPARAMETERS) - script255871402
    ScriptClass script255871402 = new ScriptClass(){
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
    script255871402.addParameter( "3" );

    //Script: Change Frame (EXECUTE_SCRIPT) - script255871402
    ScriptClass script255871402 = new ScriptClass(){
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
    try{
      script255871402.runScript();
    }catch( Exception e ){
      e.printStackTrace();
      System.exit(-1);
      }

    //Element: POS menu (LEFTCLICK) - store796626784
    wait = new WebDriverWait(driver, 10);
    by = By.cssSelector( "#pos_menu" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.click();

    //Element: POS - Application (menu) (LEFTCLICK) - store2062058570
    wait = new WebDriverWait(driver, 10);
    by = By.cssSelector( "#pos_application > a:nth-child(1)" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.click();

    //Script: Change Frame (CLEARPARAMETERS) - script255871402
    ScriptClass script255871402 = new ScriptClass(){
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
    script255871402.clearParameters();

    //Script: Change Frame (ADDSTRINGTOPARAMETERS) - script255871402
    ScriptClass script255871402 = new ScriptClass(){
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
    script255871402.addParameter( "mainFrame" );

    //Script: Change Frame (ADDSTRINGTOPARAMETERS) - script255871402
    ScriptClass script255871402 = new ScriptClass(){
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
    script255871402.addParameter( "3" );

    //Script: Change Frame (EXECUTE_SCRIPT) - script255871402
    ScriptClass script255871402 = new ScriptClass(){
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
    try{
      script255871402.runScript();
    }catch( Exception e ){
      e.printStackTrace();
      System.exit(-1);
      }

    //Element: Merchant (FILLVARIABLE) - store1021725169
    wait = new WebDriverWait(driver, 10);
    by = By.id( "intermediaryCode_widget" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.sendKeys("mymerchant");     //Merchant

    //Element: Merchant (TAB) - store1021725169
    wait = new WebDriverWait(driver, 10);
    by = By.id( "intermediaryCode_widget" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);

    //Element: Purpose 1 (SELECTVARIABLEELEMENT) - store994629287
    wait = new WebDriverWait(driver, 10);
    by = By.id( "asset_products0_purposeId" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByVisibleText( "mygroupofgoods" );

    //Element: Factory 1 (SELECTVARIABLEELEMENT) - store664739753
    wait = new WebDriverWait(driver, 10);
    by = By.id( "asset_products0_factoryId" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByVisibleText( "mybrand" );

    //Element: Description 1 (FILLVARIABLE) - store1989199870
    wait = new WebDriverWait(driver, 10);
    by = By.id( "asset_products0_credObjName" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.sendKeys("cyjjurjbybsetst");     //Description 1

    //Element: Price 1 (FILLVARIABLE) - store2102693792
    wait = new WebDriverWait(driver, 10);
    by = By.id( "asset_products0_price-input" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.sendKeys("12000");     //Price 1

    //Element: Actions (SELECTVARIABLEELEMENT) - store1703914137
    wait = new WebDriverWait(driver, 10);
    by = By.id( "loanProduct_actionId" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByVisibleText( "myposaction" );

    //Element: Construction (SELECTVARIABLEELEMENT) - store1876187925
    wait = new WebDriverWait(driver, 10);
    by = By.id( "loanProduct_constrCode" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByVisibleText( "myPOSCheap2Nofee" );

    //Element: Duration (SELECTVARIABLEELEMENT) - store672996778
    wait = new WebDriverWait(driver, 10);
    by = By.id( "loanProduct_durationPlusTermDelay" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByVisibleText( "12" );

    //Element: Term delay (SELECTVARIABLEELEMENT) - store2133828730
    wait = new WebDriverWait(driver, 10);
    by = By.id( "loanProduct_termDelay" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByVisibleText( "0" );

    //Element: Down payment (FILLVARIABLE) - store1503738246
    wait = new WebDriverWait(driver, 10);
    by = By.id( "loanProduct_downPay-input" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.sendKeys("0");     //Down payment

    //Element: AS1 Type (SELECTVARIABLEELEMENT) - store1706368518
    wait = new WebDriverWait(driver, 10);
    by = By.id( "additionalServices_additionalServices0_typeId" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByVisibleText( "myPOSLife-InsuranceService" );

    //Element: Calculate Button (LEFTCLICK) - store10472389
    wait = new WebDriverWait(driver, 10);
    by = By.id( "simulateButton" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.click();

    //Element: Next button (LEFTCLICK) - store1662906633
    wait = new WebDriverWait(driver, 10);
    by = By.id( "nextButton" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.click();

    //Element: Research (SELECTVARIABLEELEMENT) - store646960275
    wait = new WebDriverWait(driver, 10);
    by = By.id( "research" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByValue( "ADVERTISING_EXCEPT_THE_INTERNET" );

    //Element: Surname (FILLVARIABLE) - store1795996804
    wait = new WebDriverWait(driver, 10);
    by = By.id( "surname" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.sendKeys("éâëíõýëçèäãôøèô");     //Surname

    //Element: First name (FILLVARIABLE) - store1828991982
    wait = new WebDriverWait(driver, 10);
    by = By.id( "firstname" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.sendKeys("óåãîóþõòïýåóðäò");     //First name

    //Element: Patronymic name (FILLVARIABLE) - store1830863633
    wait = new WebDriverWait(driver, 10);
    by = By.id( "patronymic" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.sendKeys("éÿáøûûåëñêéâêò÷");     //Patronymic name

    //Element: Changed name (SELECTVARIABLEELEMENT) - store771744259
    wait = new WebDriverWait(driver, 10);
    by = By.id( "changeName" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByValue( "NO" );

    //Element: Fatca (SELECTVARIABLEELEMENT) - store623126883
    wait = new WebDriverWait(driver, 10);
    by = By.id( "fatca" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByValue( "NO" );

    //Element: Date of birth (FILLVARIABLE) - store8621074
    wait = new WebDriverWait(driver, 10);
    by = By.id( "birthDate" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.sendKeys("06/11/1981");     //Date of birth

    //Element: Place of birth (FILLVARIABLE) - store1359340144
    wait = new WebDriverWait(driver, 10);
    by = By.id( "placebirth" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.sendKeys("mkstwinkufyhaci");     //Place of birth

    //Element: Sex (SELECTVARIABLEELEMENT) - store814807491
    wait = new WebDriverWait(driver, 10);
    by = By.id( "sexId" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByValue( "MALE" );

    //Element: Mother`s maiden surname (FILLVARIABLE) - store1661643736
    wait = new WebDriverWait(driver, 10);
    by = By.id( "motherSurname" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.sendKeys("ôèùùñùïüþýøëøéè");     //Mother`s maiden surname

    //Element: Citizenship (SELECTVARIABLEELEMENT) - store862704672
    wait = new WebDriverWait(driver, 10);
    by = By.id( "citizen" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByValue( "RUSSIA" );

    //Element: Education (SELECTVARIABLEELEMENT) - store586137137
    wait = new WebDriverWait(driver, 10);
    by = By.id( "educationId" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByValue( "SECONDARY" );

    //Element: Passport-Series (FILLVARIABLE) - store1899693695
    wait = new WebDriverWait(driver, 10);
    by = By.id( "passpSerie" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.sendKeys("7350");     //Passport-Series

    //Element: Passport-Number (FILLVARIABLE) - store1731632160
    wait = new WebDriverWait(driver, 10);
    by = By.id( "passpNb" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.sendKeys("338748");     //Passport-Number

    //Element: Passport-Issued by (FILLVARIABLE) - store1660334624
    wait = new WebDriverWait(driver, 10);
    by = By.id( "passpIssue" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.sendKeys("oviwekoeysskmzr");     //Passport-Issued by

    //Element: Passport-Unit code (FILLVARIABLE) - store651537582
    wait = new WebDriverWait(driver, 10);
    by = By.id( "unitCode" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.sendKeys("091891");     //Passport-Unit code

    //Element: Paasort-Issued on (FILLSTRING) - store1061939863
    wait = new WebDriverWait(driver, 10);
    by = By.id( "passpIssDate" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.sendKeys("17/03/2014");     //Paasort-Issued on

    //Element: Registration in Info-Bank (SELECTVARIABLEELEMENT) - store712629000
    wait = new WebDriverWait(driver, 10);
    by = By.id( "registrationInfoBank" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByValue( "NO" );

    //Element: Registration address-Region (SELECTVARIABLEELEMENT) - store1363027059
    wait = new WebDriverWait(driver, 10);
    by = By.id( "registrationAddress_region" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByValue( "7798" );

    //Element: Registration address-City (FILLVARIABLE) - store291346344
    wait = new WebDriverWait(driver, 10);
    by = By.id( "registrationAddress_city" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.sendKeys("êñêã÷öñýÿùïôðòû");     //Registration address-City

    //Element: Registration address-Street (FILLVARIABLE) - store906031742
    wait = new WebDriverWait(driver, 10);
    by = By.id( "registrationAddress_street" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.sendKeys("åçðýèÿüñäåîúìåî");     //Registration address-Street

    //Element: Registration address-Street type (SELECTVARIABLEELEMENT) - store33806293
    wait = new WebDriverWait(driver, 10);
    by = By.id( "registrationAddress_streettypeId" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByValue( "27909" );

    //Element: Registration address-House (FILLVARIABLE) - store1500176858
    wait = new WebDriverWait(driver, 10);
    by = By.id( "registrationAddress_house" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.sendKeys("nthhtfgzeyt");     //Registration address-House

    //Element: Registration address-Period of living on the given address (FILLVARIABLE) - store377766288
    wait = new WebDriverWait(driver, 10);
    by = By.id( "registrationAddress_sinceDate" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.sendKeys("01/2000");     //Registration address-Period of living on the given address

    //Element: Button-Populate (LEFTCLICK) - store81026782
    wait = new WebDriverWait(driver, 10);
    by = By.id( "copy_registration_address" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.click();

    //Element: Living address-Address for correspondence (SELECTVARIABLEELEMENT) - store1723437402
    wait = new WebDriverWait(driver, 10);
    by = By.id( "postingAddrId" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByValue( "COINCIDES_WITH_THE_REGISTRATION_ADDRESS" );

    //Element: Living address-Type of dwelling (SELECTVARIABLEELEMENT) - store549597005
    wait = new WebDriverWait(driver, 10);
    by = By.id( "dwellingId" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByValue( "OWNER" );

    //Element: Marital status (SELECTVARIABLEELEMENT) - store1035397405
    wait = new WebDriverWait(driver, 10);
    by = By.id( "maritalstatusId" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByValue( "SINGLE" );

    //Element: Number of children (FILLVARIABLE) - store20766399
    wait = new WebDriverWait(driver, 10);
    by = By.id( "nbChildren" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.sendKeys("0");     //Number of children

    //Element: Number of dependents (FILLVARIABLE) - store1778126057
    wait = new WebDriverWait(driver, 10);
    by = By.id( "dependants" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.sendKeys("0");     //Number of dependents

    //Element: Private motor transport (SELECTVARIABLEELEMENT) - store1351831506
    wait = new WebDriverWait(driver, 10);
    by = By.id( "hasCar" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByValue( "NO" );

    //Element: Web-camera (SELECTVARIABLEELEMENT) - store515260056
    wait = new WebDriverWait(driver, 10);
    by = By.id( "webCamera" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByValue( "NO" );

    //Element: Reason for refusal web-camera (SELECTVARIABLEELEMENT) - store1657678802
    wait = new WebDriverWait(driver, 10);
    by = By.id( "reasonForRefusalWebCamera" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByValue( "NO_WEB_CAMERA" );

    //Element: Button-Next (LEFTCLICK) - store1635896946
    wait = new WebDriverWait(driver, 10);
    by = By.id( "nextButton" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.click();

    //Element: Occupation (SELECTVARIABLEELEMENT) - store456915704
    wait = new WebDriverWait(driver, 10);
    by = By.id( "activityId" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByValue( "RUNS_HIS_OWN_BUSINESS_FARMER" );

    //Element: Workplace name (FILLVARIABLE) - store473350188
    wait = new WebDriverWait(driver, 10);
    by = By.id( "workName" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.sendKeys("âöøáùúéàöäé");     //Workplace name

    //Element: Workplace type (SELECTVARIABLEELEMENT) - store27334452
    wait = new WebDriverWait(driver, 10);
    by = By.id( "workplaceType" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByValue( "PRIVATE" );

    //Element: Period of working in this company (FILLVARIABLE) - store1494115161
    wait = new WebDriverWait(driver, 10);
    by = By.id( "sinceactiv" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.sendKeys("01/2000");     //Period of working in this company

    //Element: Political of official person (SELECTVARIABLEELEMENT) - store2003653159
    wait = new WebDriverWait(driver, 10);
    by = By.id( "politicalOrOfficialPerson" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByValue( "NO" );

    //Element: ZIP (FILLVARIABLE) - store1012850532
    wait = new WebDriverWait(driver, 10);
    by = By.id( "workAddressDTO_zip" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.sendKeys("382214");     //ZIP

    //Element: Region (SELECTVARIABLEELEMENT) - store770288288
    wait = new WebDriverWait(driver, 10);
    by = By.id( "workAddressDTO_region" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByValue( "7797" );

    //Element: City (FILLVARIABLE) - store1812951865
    wait = new WebDriverWait(driver, 10);
    by = By.id( "workAddressDTO_city" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.sendKeys("qbldxhjqttb");     //City

    //Element: Street (FILLVARIABLE) - store456112425
    wait = new WebDriverWait(driver, 10);
    by = By.id( "workAddressDTO_street" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.sendKeys("squsvlahjaq");     //Street

    //Element: Street type (SELECTVARIABLEELEMENT) - store970946365
    wait = new WebDriverWait(driver, 10);
    by = By.id( "workAddressDTO_streettypeId" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByValue( "27913" );

    //Element: House (FILLVARIABLE) - store534225626
    wait = new WebDriverWait(driver, 10);
    by = By.id( "workAddressDTO_house" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.sendKeys("áõâÿõôàúøþä");     //House

    //Element: Next BUTTON (LEFTCLICK) - store825867896
    wait = new WebDriverWait(driver, 10);
    by = By.id( "nextButton" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.click();

    //Element: Income-Personal monthly salary (FILLVARIABLE) - store900484301
    wait = new WebDriverWait(driver, 10);
    by = By.id( "custIncome-input" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.sendKeys("111000");     //Income-Personal monthly salary

    //Element: Income-Other personal monthly income (FILLSTRING) - store746711858
    wait = new WebDriverWait(driver, 10);
    by = By.id( "otherInc-input" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.sendKeys("55");     //Income-Other personal monthly income

    //Element: Expenses-Monthly amount rent and/or mortgage loan (FILLSTRING) - store1823678636
    wait = new WebDriverWait(driver, 10);
    by = By.id( "dwellingCredit-input" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.sendKeys("1000");     //Expenses-Monthly amount rent and/or mortgage loan

    //Element: Expenses-Monthly mandatory charges (FILLSTRING) - store563675062
    wait = new WebDriverWait(driver, 10);
    by = By.id( "householdCharge-input" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.sendKeys("100");     //Expenses-Monthly mandatory charges

    //Element: Expenses-Monthly amount other credits (FILLSTRING) - store1994917560
    wait = new WebDriverWait(driver, 10);
    by = By.id( "otherCredit-input" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.sendKeys("100");     //Expenses-Monthly amount other credits

    //Element: Bank data-Bank account (SELECTVARIABLEELEMENT) - store1565986645
    wait = new WebDriverWait(driver, 10);
    by = By.id( "bankInfo_bankAccountYes" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByValue( "YES" );

    //Element: Bank data-Bank card (Yes, No) - check box (LEFTCLICK) - store1072862333
    wait = new WebDriverWait(driver, 10);
    by = By.id( "bankInfo_bankCardYesNo" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.click();

    //Element: Bank data-Year of opening of the account (FILLVARIABLE) - store592672743
    wait = new WebDriverWait(driver, 10);
    by = By.id( "bankInfo_bankStartYear" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.sendKeys("2000");     //Bank data-Year of opening of the account

    //Element: Special (FILLSTRING) - store1215727869
    wait = new WebDriverWait(driver, 10);
    by = By.id( "special" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.sendKeys("a");     //Special

    //Element: Agreement for receiving credit card (SELECTVARIABLEELEMENT) - store2131642120
    wait = new WebDriverWait(driver, 10);
    by = By.id( "agreementForCC" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByValue( "YES" );

    //Element: Agreement for concession of rights to third parties (SELECTVARIABLEELEMENT) - store71933436
    wait = new WebDriverWait(driver, 10);
    by = By.id( "agreementForConcession" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByValue( "NO" );

    //Element: SAVE Button (LEFTCLICK) - store1165636288
    wait = new WebDriverWait(driver, 10);
    by = By.id( "saveButton" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.click();

    //Element: Successfull Saving Indicator (GAINTEXTTOELEMENT) - store2115562712
    wait = new WebDriverWait(driver, 10);
    by = By.cssSelector( ".success > ul:nth-child(1) > li:nth-child(1) > span:nth-child(1)" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    origText = webElement.getText();
    pattern = Pattern.compile( "[\\d]+" );
    matcher = pattern.matcher( origText );
    if( matcher.find() ){
    String   store2115562712 = matcher.group();
    }

    //Element: Application number of POS - Storage (GAINTEXTTOELEMENT) - store97856834
    wait = new WebDriverWait(driver, 10);
    by = By.cssSelector( "#flowIndicator > h1:nth-child(1)" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    origText = webElement.getText();
    pattern = Pattern.compile( "[\\d]+" );
    matcher = pattern.matcher( origText );
    if( matcher.find() ){
    String   store97856834 = matcher.group();
    }

    //Element: Application number of POS - Storage (OUTPUTSTOREDELEMENT) - store97856834
    wait = new WebDriverWait(driver, 10);
    by = By.cssSelector( "#flowIndicator > h1:nth-child(1)" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    System.out.println( "App number: " + store97856834 );

    //Script: Change Frame (CLEARPARAMETERS) - script255871402
    ScriptClass script255871402 = new ScriptClass(){
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
    script255871402.clearParameters();

    //Script: Change Frame (ADDSTRINGTOPARAMETERS) - script255871402
    ScriptClass script255871402 = new ScriptClass(){
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
    script255871402.addParameter( "menuFrame" );

    //Script: Change Frame (ADDSTRINGTOPARAMETERS) - script255871402
    ScriptClass script255871402 = new ScriptClass(){
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
    script255871402.addParameter( "3" );

    //Script: Change Frame (EXECUTE_SCRIPT) - script255871402
    ScriptClass script255871402 = new ScriptClass(){
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
    try{
      script255871402.runScript();
    }catch( Exception e ){
      e.printStackTrace();
      System.exit(-1);
      }

    //Element: POS - Status list (menu) (LEFTCLICK) - store1650418173
    wait = new WebDriverWait(driver, 10);
    by = By.cssSelector( "#pos_application_statusreport > a:nth-child(1)" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.click();

    //Script: Change Frame (CLEARPARAMETERS) - script255871402
    ScriptClass script255871402 = new ScriptClass(){
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
    script255871402.clearParameters();

    //Script: Change Frame (ADDSTRINGTOPARAMETERS) - script255871402
    ScriptClass script255871402 = new ScriptClass(){
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
    script255871402.addParameter( "mainFrame" );

    //Script: Change Frame (ADDSTRINGTOPARAMETERS) - script255871402
    ScriptClass script255871402 = new ScriptClass(){
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
    script255871402.addParameter( "3" );

    //Script: Change Frame (EXECUTE_SCRIPT) - script255871402
    ScriptClass script255871402 = new ScriptClass(){
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
    try{
      script255871402.runScript();
    }catch( Exception e ){
      e.printStackTrace();
      System.exit(-1);
      }

    //Element: Date to (CLEAR) - store276259590
    wait = new WebDriverWait(driver, 10);
    by = By.id( "toDate" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.clear();

    //Element: Date to (FILLSTRING) - store276259590
    wait = new WebDriverWait(driver, 10);
    by = By.id( "toDate" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.sendKeys("01/01/2222");     //Date to

    //Element: Application number (CLEAR) - store447805538
    wait = new WebDriverWait(driver, 10);
    by = By.id( "applicationNb" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.clear();

    //Element: Application number (FILLELEMENT) - store447805538
    wait = new WebDriverWait(driver, 10);
    by = By.id( "applicationNb" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.sendKeys("13000265708");     //Application number

    //Element: Application number (GAINVALUETOELEMENT) - store447805538
    wait = new WebDriverWait(driver, 10);
    by = By.id( "applicationNb" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    origText = webElement.getAttribute("value");
    String store447805538 = origText;

    //Element: Application number (COMPAREVALUETOSTOREDELEMENT) - store447805538
    wait = new WebDriverWait(driver, 10);
    by = By.id( "applicationNb" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    origText = webElement.getAttribute("value");

    //Element: Search - BUTTON (LEFTCLICK) - store1640496723
    wait = new WebDriverWait(driver, 10);
    by = By.id( "searchButton" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.click();

    //Element: Status From POS List (GAINTEXTTOELEMENT) - store1378638587
    wait = new WebDriverWait(driver, 10);
    by = By.cssSelector( "#\31  > td:nth-child(4)" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    origText = webElement.getText();
    String store1378638587 = webElement.getText();

    //Element: Status From POS List (COMPARETEXTTOSTRING) - store1378638587
    wait = new WebDriverWait(driver, 10);
    by = By.cssSelector( "#\31  > td:nth-child(4)" );
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    origText = webElement.getText();
    System.err.println("Stopped because origText.equals( Interrupted) BUT it should NOT be");
    System.exit(-1);
    //Script: Kill RFBANK Window Closer javascript (EXECUTE_SCRIPT) - script189247287
    ScriptClass script189247287 = new ScriptClass(){
      @Override
      public void runScript() throws Exception{
        //org.openqa.selenium.support.ui.WebDriverWait  wait = new org.openqa.selenium.support.ui.WebDriverWait(driver, 10);
        //Switch to the 'mainFrame' frame
        //driver.switchTo().defaultContent();
        //wait.until(org.openqa.selenium.support.ui.ExpectedConditions.frameToBeAvailableAndSwitchToIt( "mainFrame" ) );
        //driver.switchTo().defaultContent();
        //driver.switchTo().frame( "mainFrame" );
        org.openqa.selenium.JavascriptExecutor executor = ((org.openqa.selenium.JavascriptExecutor) driver);
        executor.executeScript("window.onbeforeunload = function() {}; window.onunload = function() {};");
      }
    };
    try{
      script189247287.runScript();
    }catch( Exception e ){
      e.printStackTrace();
      System.exit(-1);
      }

    //Script: Close Window (EXECUTE_SCRIPT) - script1780534289
    ScriptClass script1780534289 = new ScriptClass(){
      @Override
      public void runScript() throws Exception{
        driver.close();
      }
    };
    try{
      script1780534289.runScript();
    }catch( Exception e ){
      e.printStackTrace();
      System.exit(-1);
      }

  }
}
