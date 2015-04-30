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
  public void CAPTUREPOSContractAuthorizedApprovedforfinance(){

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
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

    //Script: Open Window (CLEARPARAMETERS) - script269213529
    ScriptClass script269213529 = new ScriptClass(){
      @Override
      public void runScript() throws Exception{
        String url = parameters.get(0);
        driver.get( url );
      }
    };
    script269213529.clearParameters();

    script269213529.addParameter( "http://tomcat01.statlogics.local:8146/RFBANK_TEST2_Logic/auth.action" );

    try{
      script269213529.runScript();
    }catch( Exception e ){
      e.printStackTrace();
      fail( e.getMessage() );
    }

    //Script: Logout (EXECUTE_SCRIPT) - script101919530
    ScriptClass script101919530 = new ScriptClass(){
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
      script101919530.runScript();
    }catch( Exception e ){
      e.printStackTrace();
      fail( e.getMessage() );
    }

    //Element: Version - Text (GAINTEXTTOELEMENT) - store793099164
    by = By.cssSelector( "#RFTail" );
    webElement = driver.findElement( by );
    origText = webElement.getText();
    pattern = Pattern.compile( "\\d+[.]\\d+[.]\\d+[.]\\d" );
    matcher = pattern.matcher( origText );
    String store793099164 = null;
    if( matcher.find() ){
      store793099164 = matcher.group();
    }

    //Element: Version - Text (COMPARETEXTTOCONSTANT) - store793099164
    by = By.cssSelector( "#RFTail" );
    webElement = driver.findElement( by );
    origText = webElement.getText();
    pattern = Pattern.compile( "v\\d+[.]\\d+[.]\\d+[.]\\d+" );
    matcher = pattern.matcher( origText );
    if( matcher.find() ){
        origText = matcher.group();
    }
    if( !origText.equals( "v2.8.4.2" ) ){
      fail("Stopped because the element 'store793099164': '" + origText + "' does NOT equal to 'v2.8.4.2' but it should.");
    }

    //Element: English link (LEFTCLICK) - store886683478
    by = By.cssSelector( "#languageSwitch > a:nth-child(2)" );
    webElement = driver.findElement( by );
    webElement.click();

    //Element: username (FILLVARIABLE) - store341541906
    by = By.id( "username" );
    webElement = driver.findElement( by );
    webElement.sendKeys("myuser1");     //username

    //Element: password (FILLVARIABLE) - store524600868
    by = By.id( "password" );
    webElement = driver.findElement( by );
    try {Thread.sleep( 1000 );} catch (InterruptedException e) {}
    webElement.sendKeys("a");     //password

    //Element: Login button (LEFTCLICK) - store677221865
    by = By.id( "loginButton" );
    webElement = driver.findElement( by );
    webElement.click();

    //Script: Change Frame (CLEARPARAMETERS) - script1270080542
    ScriptClass script1270080542 = new ScriptClass(){
      @Override
      public void runScript() throws Exception{
        //There is proper parameter (frame name, waiting time [ms])
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
    script1270080542.clearParameters();

    script1270080542.addParameter( "menuFrame" );

    script1270080542.addParameter( "1000" );

    try{
      script1270080542.runScript();
    }catch( Exception e ){
      e.printStackTrace();
      fail( e.getMessage() );
    }

    //Element: POS menu (LEFTCLICK) - store454237493
    by = By.cssSelector( "#pos_menu" );
    webElement = driver.findElement( by );
    webElement.click();

    //Element: POS - Application (menu) (LEFTCLICK) - store539725240
    by = By.cssSelector( "#pos_application > a:nth-child(1)" );
    webElement = driver.findElement( by );
    try {Thread.sleep( 1000 );} catch (InterruptedException e) {}
    webElement.click();

    script1270080542.clearParameters();

    script1270080542.addParameter( "mainFrame" );

    script1270080542.addParameter( "1000" );

    try{
      script1270080542.runScript();
    }catch( Exception e ){
      e.printStackTrace();
      fail( e.getMessage() );
    }

    //Element: Merchant (FILLVARIABLE) - store1304005166
    by = By.id( "intermediaryCode_widget" );
    webElement = driver.findElement( by );
    webElement.sendKeys("mymerchant");     //Merchant

    //Element: Merchant (TAB) - store1304005166
    by = By.id( "intermediaryCode_widget" );
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);

    //Element: Purpose 1 (SELECTVARIABLEELEMENT) - store127451204
    by = By.id( "asset_products0_purposeId" );
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByVisibleText( "mygroupofgoods" );

    //Element: Factory 1 (SELECTVARIABLEELEMENT) - store449094668
    by = By.id( "asset_products0_factoryId" );
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByVisibleText( "mybrand" );

    //Element: Description 1 (FILLVARIABLE) - store359958434
    by = By.id( "asset_products0_credObjName" );
    webElement = driver.findElement( by );
    webElement.sendKeys("qwzyoyyfdzfyyyj");     //Description 1

    //Element: Price 1 (FILLVARIABLE) - store517668571
    by = By.id( "asset_products0_price-input" );
    webElement = driver.findElement( by );
    webElement.sendKeys("12000");     //Price 1

    //Element: Actions (SELECTVARIABLEELEMENT) - store819852292
    by = By.id( "loanProduct_actionId" );
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByVisibleText( "myposaction" );

    //Element: Construction (SELECTVARIABLEELEMENT) - store305452339
    by = By.id( "loanProduct_constrCode" );
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByVisibleText( "myPOSCheap2Nofee" );

    //Element: Duration (SELECTVARIABLEELEMENT) - store1638639520
    by = By.id( "loanProduct_durationPlusTermDelay" );
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByVisibleText( "12" );

    //Element: Term delay (SELECTVARIABLEELEMENT) - store380074780
    by = By.id( "loanProduct_termDelay" );
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByVisibleText( "0" );

    //Element: Down payment (FILLVARIABLE) - store1570887051
    by = By.id( "loanProduct_downPay-input" );
    webElement = driver.findElement( by );
    webElement.sendKeys("0");     //Down payment

    //Element: AS1 Type (SELECTVARIABLEELEMENT) - store382226767
    by = By.id( "additionalServices_additionalServices0_typeId" );
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByVisibleText( "myPOSLife-InsuranceService" );

    //Element: Calculate Button (LEFTCLICK) - store39689586
    by = By.id( "simulateButton" );
    webElement = driver.findElement( by );
    webElement.click();

    //Element: Next button (LEFTCLICK) - store896182464
    by = By.id( "nextButton" );
    webElement = driver.findElement( by );
    webElement.click();

    //Element: Research (SELECTVARIABLEELEMENT) - store732467677
    by = By.id( "research" );
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByValue( "ADVERTISING_EXCEPT_THE_INTERNET" );

    //Element: Surname (FILLVARIABLE) - store1768820755
    by = By.id( "surname" );
    webElement = driver.findElement( by );
    webElement.sendKeys("íñäìðéâõòïì÷÷íö");     //Surname

    //Element: First name (FILLVARIABLE) - store1369999080
    by = By.id( "firstname" );
    webElement = driver.findElement( by );
    webElement.sendKeys("óðùûëèãýöàáÿæûñ");     //First name

    //Element: Patronymic name (FILLVARIABLE) - store1668603714
    by = By.id( "patronymic" );
    webElement = driver.findElement( by );
    webElement.sendKeys("íëíôæéúõàÿîóíïô");     //Patronymic name

    //Element: Changed name (SELECTVARIABLEELEMENT) - store1189829301
    by = By.id( "changeName" );
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByValue( "NO" );

    //Element: Date of birth (FILLVARIABLE) - store810230356
    by = By.id( "birthDate" );
    webElement = driver.findElement( by );
    webElement.sendKeys("12/07/1979");     //Date of birth

    //Element: Place of birth (FILLVARIABLE) - store470766196
    by = By.id( "placebirth" );
    webElement = driver.findElement( by );
    webElement.sendKeys("rcsdeajlkkuanxa");     //Place of birth

    //Element: Sex (SELECTVARIABLEELEMENT) - store1933809849
    by = By.id( "sexId" );
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByValue( "MALE" );

    //Element: Mother`s maiden surname (FILLVARIABLE) - store1206566742
    by = By.id( "motherSurname" );
    webElement = driver.findElement( by );
    webElement.sendKeys("ëâöøúàåÿìöíäùøà");     //Mother`s maiden surname

    //Element: Citizenship (SELECTVARIABLEELEMENT) - store843416471
    by = By.id( "citizen" );
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByValue( "RUSSIA" );

    //Element: Education (SELECTVARIABLEELEMENT) - store1755923944
    by = By.id( "educationId" );
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByValue( "SECONDARY" );

    //Element: Passport-Series (FILLVARIABLE) - store941000800
    by = By.id( "passpSerie" );
    webElement = driver.findElement( by );
    webElement.sendKeys("3077");     //Passport-Series

    //Element: Passport-Number (FILLVARIABLE) - store129002173
    by = By.id( "passpNb" );
    webElement = driver.findElement( by );
    webElement.sendKeys("495733");     //Passport-Number

    //Element: Passport-Issued by (FILLVARIABLE) - store1427779473
    by = By.id( "passpIssue" );
    webElement = driver.findElement( by );
    webElement.sendKeys("tzddhdojjlprnst");     //Passport-Issued by

    //Element: Passport-Unit code (FILLVARIABLE) - store996460015
    by = By.id( "unitCode" );
    webElement = driver.findElement( by );
    webElement.sendKeys("043382");     //Passport-Unit code

    //Element: Paasort-Issued on (FILLSTRING) - store721967371
    by = By.id( "passpIssDate" );
    webElement = driver.findElement( by );
    webElement.sendKeys("17/03/2014");     //Paasort-Issued on

    //Element: Registration in Info-Bank (SELECTVARIABLEELEMENT) - store2047788555
    by = By.id( "registrationInfoBank" );
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByValue( "NO" );

    //Element: Registration address-Region (SELECTVARIABLEELEMENT) - store220025445
    by = By.id( "registrationAddress_region" );
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByValue( "7798" );

    //Element: Registration address-City (FILLVARIABLE) - store554331695
    by = By.id( "registrationAddress_city" );
    webElement = driver.findElement( by );
    webElement.sendKeys("íøî÷ëáúúëäíûþùô");     //Registration address-City

    //Element: Registration address-Street (FILLVARIABLE) - store88259975
    by = By.id( "registrationAddress_street" );
    webElement = driver.findElement( by );
    webElement.sendKeys("úóøíçåôíííõøùçí");     //Registration address-Street

    //Element: Registration address-Street type (SELECTVARIABLEELEMENT) - store380771047
    by = By.id( "registrationAddress_streettypeId" );
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByValue( "27909" );

    //Element: Registration address-House (FILLVARIABLE) - store1229410591
    by = By.id( "registrationAddress_house" );
    webElement = driver.findElement( by );
    webElement.sendKeys("bljifuudxzx");     //Registration address-House

    //Element: Registration address-Period of living on the given address (FILLVARIABLE) - store1611119015
    by = By.id( "registrationAddress_sinceDate" );
    webElement = driver.findElement( by );
    webElement.sendKeys("01/2000");     //Registration address-Period of living on the given address

    //Element: Button-Populate (LEFTCLICK) - store1662426514
    by = By.id( "copy_registration_address" );
    webElement = driver.findElement( by );
    webElement.click();

    //Element: Living address-Address for correspondence (SELECTVARIABLEELEMENT) - store1581698835
    by = By.id( "postingAddrId" );
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByValue( "COINCIDES_WITH_THE_REGISTRATION_ADDRESS" );

    //Element: Living address-Type of dwelling (SELECTVARIABLEELEMENT) - store398981917
    by = By.id( "dwellingId" );
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByValue( "OWNER" );

    //Element: Marital status (SELECTVARIABLEELEMENT) - store108204562
    by = By.id( "maritalstatusId" );
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByValue( "SINGLE" );

    //Element: Number of children (FILLVARIABLE) - store689085202
    by = By.id( "nbChildren" );
    webElement = driver.findElement( by );
    webElement.sendKeys("0");     //Number of children

    //Element: Number of dependents (FILLVARIABLE) - store1726140270
    by = By.id( "dependants" );
    webElement = driver.findElement( by );
    webElement.sendKeys("0");     //Number of dependents

    //Element: Private motor transport (SELECTVARIABLEELEMENT) - store1189444746
    by = By.id( "hasCar" );
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByValue( "NO" );

    //Element: Web-camera (SELECTVARIABLEELEMENT) - store322977683
    by = By.id( "webCamera" );
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByValue( "NO" );

    //Element: Reason for refusal web-camera (SELECTVARIABLEELEMENT) - store887686556
    by = By.id( "reasonForRefusalWebCamera" );
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByValue( "NO_WEB_CAMERA" );

    //Element: Button-Next (LEFTCLICK) - store968552030
    by = By.id( "nextButton" );
    webElement = driver.findElement( by );
    webElement.click();

    //Element: Button-Next (LEFTCLICK) - store968552030
    by = By.id( "nextButton" );
    webElement = driver.findElement( by );
    webElement.click();

    //Element: Occupation (SELECTVARIABLEELEMENT) - store1149143094
    by = By.id( "activityId" );
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByValue( "RUNS_HIS_OWN_BUSINESS_FARMER" );

    //Element: Workplace name (FILLVARIABLE) - store1894348699
    by = By.id( "workName" );
    webElement = driver.findElement( by );
    webElement.sendKeys("êöòâþðÿ÷þ÷è");     //Workplace name

    //Element: Workplace type (SELECTVARIABLEELEMENT) - store1514758782
    by = By.id( "workplaceType" );
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByValue( "PRIVATE" );

    //Element: Period of working in this company (FILLVARIABLE) - store1702974147
    by = By.id( "sinceactiv" );
    webElement = driver.findElement( by );
    webElement.sendKeys("01/2000");     //Period of working in this company

    //Element: Political of official person (SELECTVARIABLEELEMENT) - store394650627
    by = By.id( "politicalOrOfficialPerson" );
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByValue( "NO" );

    //Element: ZIP (FILLVARIABLE) - store724079135
    by = By.id( "workAddressDTO_zip" );
    webElement = driver.findElement( by );
    webElement.sendKeys("986051");     //ZIP

    //Element: Region (SELECTVARIABLEELEMENT) - store1698159300
    by = By.id( "workAddressDTO_region" );
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByValue( "7797" );

    //Element: City (FILLVARIABLE) - store985105507
    by = By.id( "workAddressDTO_city" );
    webElement = driver.findElement( by );
    webElement.sendKeys("vpupznrqjhe");     //City

    //Element: Street (FILLVARIABLE) - store1597870416
    by = By.id( "workAddressDTO_street" );
    webElement = driver.findElement( by );
    webElement.sendKeys("kmzaqndbdmh");     //Street

    //Element: Street type (SELECTVARIABLEELEMENT) - store203961109
    by = By.id( "workAddressDTO_streettypeId" );
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByValue( "27913" );

    //Element: House (FILLVARIABLE) - store61494637
    by = By.id( "workAddressDTO_house" );
    webElement = driver.findElement( by );
    webElement.sendKeys("äëèçáøêìóðü");     //House

    //Element: Next BUTTON (LEFTCLICK) - store1868641841
    by = By.id( "nextButton" );
    webElement = driver.findElement( by );
    webElement.click();

    //Element: Income-Personal monthly salary (FILLVARIABLE) - store1916257956
    by = By.id( "custIncome-input" );
    webElement = driver.findElement( by );
    webElement.sendKeys("111000");     //Income-Personal monthly salary

    //Element: Income-Other personal monthly income (FILLSTRING) - store684530340
    by = By.id( "otherInc-input" );
    webElement = driver.findElement( by );
    webElement.sendKeys("55");     //Income-Other personal monthly income

    //Element: Expenses-Monthly amount rent and/or mortgage loan (FILLSTRING) - store1603319010
    by = By.id( "dwellingCredit-input" );
    webElement = driver.findElement( by );
    webElement.sendKeys("1000");     //Expenses-Monthly amount rent and/or mortgage loan

    //Element: Expenses-Monthly mandatory charges (FILLSTRING) - store970392255
    by = By.id( "householdCharge-input" );
    webElement = driver.findElement( by );
    webElement.sendKeys("100");     //Expenses-Monthly mandatory charges

    //Element: Expenses-Monthly amount other credits (FILLSTRING) - store1660527645
    by = By.id( "otherCredit-input" );
    webElement = driver.findElement( by );
    webElement.sendKeys("100");     //Expenses-Monthly amount other credits

    //Element: Bank data-Bank account (SELECTVARIABLEELEMENT) - store948725129
    by = By.id( "bankInfo_bankAccountYes" );
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByValue( "NO" );

    //Element: Agreement for receiving credit card (SELECTVARIABLEELEMENT) - store2015058881
    by = By.id( "agreementForCC" );
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByValue( "YES" );

    //Element: Agreement for concession of rights to third parties (SELECTVARIABLEELEMENT) - store1014325634
    by = By.id( "agreementForConcession" );
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByValue( "NO" );

    //Element: SAVE Button (LEFTCLICK) - store1949502630
    by = By.id( "saveButton" );
    webElement = driver.findElement( by );
    webElement.click();

    //Element: Successfull Saving Indicator (GAINTEXTTOELEMENT) - store933320754
    by = By.cssSelector( ".success > ul:nth-child(1) > li:nth-child(1) > span:nth-child(1)" );
    webElement = driver.findElement( by );
    origText = webElement.getText();
    pattern = Pattern.compile( "[\\d]+" );
    matcher = pattern.matcher( origText );
    String store933320754 = null;
    if( matcher.find() ){
      store933320754 = matcher.group();
    }

    //Element: Application number of POS - Storage (GAINTEXTTOELEMENT) - store80490579
    by = By.cssSelector( "#flowIndicator > h1:nth-child(1)" );
    webElement = driver.findElement( by );
    origText = webElement.getText();
    pattern = Pattern.compile( "[\\d]+" );
    matcher = pattern.matcher( origText );
    String store80490579 = null;
    if( matcher.find() ){
      store80490579 = matcher.group();
    }

    //Element: Application number of POS - Storage (OUTPUTSTOREDELEMENT) - store80490579
    by = By.cssSelector( "#flowIndicator > h1:nth-child(1)" );
    webElement = driver.findElement( by );
    System.out.println( "App number: " + store80490579 );

    //Element: NEXT Button (LEFTCLICK) - store883766749
    by = By.id( "nextButton" );
    webElement = driver.findElement( by );
    webElement.click();

    //Element: Print Button (LEFTCLICK) - store39530650
    by = By.id( "print_button" );
    webElement = driver.findElement( by );
    webElement.click();

    //Script: Close PDF Window (EXECUTE_SCRIPT) - script306824177
    ScriptClass script306824177 = new ScriptClass(){
      @Override
      public void runScript() throws Exception{
        try {
        Thread.sleep(2000);
        } catch (InterruptedException e) {
        System.out.println("HIBA");
        e.printStackTrace();
        }
        String parentFrameName = "mainFrame";
        CharSequence partOfTitle = "_application";
        String parentWindowHandle = driver.getWindowHandle(); // save the current window handle.
        WebDriver popup = null;
        java.util.Set<String> set =  driver.getWindowHandles();
        java.util.Iterator<String> windowIterator = set.iterator();
        while(windowIterator.hasNext()) {
        String windowHandle = windowIterator.next();
        popup = driver.switchTo().window(windowHandle);
        if (popup.getTitle().contains( partOfTitle )) {
        break;
        }
        }
        popup.close();
        driver.switchTo().window(parentWindowHandle);
        driver.switchTo().defaultContent();
        //	driver.switchTo().activeElement();
        driver.switchTo().frame( parentFrameName);
        try {
        Thread.sleep(2000);
        } catch (InterruptedException e) {
        System.out.println("HIBA");
        e.printStackTrace();
        }
      }
    };
    try{
      script306824177.runScript();
    }catch( Exception e ){
      e.printStackTrace();
      fail( e.getMessage() );
    }

    //Element: Do you print and sign the application for consumer credit (LEFTCLICK) - store1635890495
    by = By.id( "signed-label" );
    webElement = driver.findElement( by );
    webElement.click();

    //Element: Decision - BUTTON (LEFTCLICK) - store1181690227
    by = By.id( "decisionButton" );
    webElement = driver.findElement( by );
    webElement.click();

    //Element: Date to (CLEAR) - store1518393243
    by = By.id( "toDate" );
    webElement = driver.findElement( by );
    webElement.clear();

    //Element: Date to (FILLSTRING) - store1518393243
    by = By.id( "toDate" );
    webElement = driver.findElement( by );
    webElement.sendKeys("01/01/2222");     //Date to

    //Element: Application number (CLEAR) - store28669693
    by = By.id( "applicationNb" );
    webElement = driver.findElement( by );
    webElement.clear();

    //Element: Application number (FILLELEMENT) - store28669693
    by = By.id( "applicationNb" );
    webElement = driver.findElement( by );
    webElement.sendKeys("13000002452");     //Application number

    //Element: Application number (GAINVALUETOELEMENT) - store28669693
    by = By.id( "applicationNb" );
    webElement = driver.findElement( by );
    origText = "";
    origText = webElement.getAttribute("value");
    String store28669693 = origText;

    //Element: Application number (COMPAREVALUETOSTOREDELEMENT) - store28669693
    by = By.id( "applicationNb" );
    webElement = driver.findElement( by );
    origText = "";
    origText = webElement.getAttribute("value");
    if( !origText.equals( store28669693 ) ){
      fail("Stopped because the element 'store28669693': '" + origText + "' does NOT equal to '" + store80490579 + "' but it should.");
    }

    //Element: Search - BUTTON (LEFTCLICK) - store807248711
    by = By.id( "searchButton" );
    webElement = driver.findElement( by );
    try {Thread.sleep( 1000 );} catch (InterruptedException e) {}
    webElement.click();

    //Element: Status From POS List (GAINTEXTTOELEMENT) - store787439869
    by = By.cssSelector( "#\31  > td:nth-child(4)" );
    webElement = driver.findElement( by );
    origText = webElement.getText();
    String store787439869 = origText;

    //Cycle starts
    startDate = Calendar.getInstance().getTime();
    actualLoop = 0;
    oneLoopLength = 2;
    maxLoopNumber = 20;
    while( actualLoop++ < maxLoopNumber ){

      //
      //Evaluation
      //
      //Element: Status From POS List (COMPARETEXTTOSTRING) - store787439869
      by = By.cssSelector( "#\31  > td:nth-child(4)" );
      webElement = driver.findElement( by );
      origText = webElement.getText();
      if( origText.equals( "Accepted, Document missing" ) ){
        break; //because the element 'store787439869' equals to 'Accepted, Document missing + "'.
      }
    } //while()
    //Element: Application number field on the POS status list (LEFTCLICK) - store1364361709
    by = By.cssSelector( "#\31  > td:nth-child(2)" );
    webElement = driver.findElement( by );
    webElement.click();

    //Element: MENU - Accept documents (LEFTCLICK) - store1545138461
    by = By.id( "menuItem1-ACCEPTDOCUMENTS" );
    webElement = driver.findElement( by );
    webElement.click();

    //Element: Check document (LEFTCLICK) - store1417001585
    by = By.id( "model_documents-1" );
    webElement = driver.findElement( by );
    webElement.click();

    //Element: Save button (LEFTCLICK) - store1924116869
    by = By.id( "saveButton" );
    webElement = driver.findElement( by );
    webElement.click();

    //Element: Date to (CLEAR) - store1518393243
    by = By.id( "toDate" );
    webElement = driver.findElement( by );
    webElement.clear();

    //Element: Date to (FILLSTRING) - store1518393243
    by = By.id( "toDate" );
    webElement = driver.findElement( by );
    webElement.sendKeys("01/01/2222");     //Date to

    //Element: Application number (CLEAR) - store28669693
    by = By.id( "applicationNb" );
    webElement = driver.findElement( by );
    webElement.clear();

    //Element: Application number (FILLELEMENT) - store28669693
    by = By.id( "applicationNb" );
    webElement = driver.findElement( by );
    webElement.sendKeys("13000002452");     //Application number

    //Element: Application number (GAINVALUETOELEMENT) - store28669693
    by = By.id( "applicationNb" );
    webElement = driver.findElement( by );
    origText = "";
    origText = webElement.getAttribute("value");
    store28669693 = origText;

    //Element: Application number (COMPAREVALUETOSTOREDELEMENT) - store28669693
    by = By.id( "applicationNb" );
    webElement = driver.findElement( by );
    origText = "";
    origText = webElement.getAttribute("value");
    if( !origText.equals( store28669693 ) ){
      fail("Stopped because the element 'store28669693': '" + origText + "' does NOT equal to '" + store80490579 + "' but it should.");
    }

    //Element: Search - BUTTON (LEFTCLICK) - store807248711
    by = By.id( "searchButton" );
    webElement = driver.findElement( by );
    try {Thread.sleep( 1000 );} catch (InterruptedException e) {}
    webElement.click();

    //Element: Status From POS List (GAINTEXTTOELEMENT) - store787439869
    by = By.cssSelector( "#\31  > td:nth-child(4)" );
    webElement = driver.findElement( by );
    origText = webElement.getText();
    store787439869 = origText;

    //Cycle starts
    startDate = Calendar.getInstance().getTime();
    actualLoop = 0;
    oneLoopLength = 2;
    maxLoopNumber = 20;
    while( actualLoop++ < maxLoopNumber ){

      //
      //Evaluation
      //
      //Element: Status From POS List (COMPARETEXTTOSTRING) - store787439869
      by = By.cssSelector( "#\31  > td:nth-child(4)" );
      webElement = driver.findElement( by );
      origText = webElement.getText();
      if( origText.equals( "Finally approved" ) ){
        break; //because the element 'store787439869' equals to 'Finally approved + "'.
      }
    } //while()
    //Element: Application number field on the POS status list (LEFTCLICK) - store83821348
    by = By.cssSelector( "#\31  > td:nth-child(2)" );
    webElement = driver.findElement( by );
    webElement.click();

    //Element: MENU ELEMENT - Print Contract (LEFTCLICK) - store683332200
    by = By.id( "menuItem1-PRINTCONTRACT" );
    webElement = driver.findElement( by );
    webElement.click();

    //Element: Application number (COMPARETEXTTOSTOREDELEMENT) - store316197391
    by = By.id( "printContractApplicationNumber" );
    webElement = driver.findElement( by );
    origText = webElement.getText();
    if( !origText.equals( store316197391 ) ){
      fail("Stopped because the element 'store316197391': '" + origText + "' does NOT equal to '" + store80490579 + "' but it should.");
    }

    //Element: Print Button (LEFTCLICK) - store1081867686
    by = By.id( "printContractDialog_print" );
    webElement = driver.findElement( by );
    webElement.click();

    try{
      script306824177.runScript();
    }catch( Exception e ){
      e.printStackTrace();
      fail( e.getMessage() );
    }

    //Element: Date to (CLEAR) - store1518393243
    by = By.id( "toDate" );
    webElement = driver.findElement( by );
    webElement.clear();

    //Element: Date to (FILLSTRING) - store1518393243
    by = By.id( "toDate" );
    webElement = driver.findElement( by );
    webElement.sendKeys("01/01/2222");     //Date to

    //Element: Application number (CLEAR) - store28669693
    by = By.id( "applicationNb" );
    webElement = driver.findElement( by );
    webElement.clear();

    //Element: Application number (FILLELEMENT) - store28669693
    by = By.id( "applicationNb" );
    webElement = driver.findElement( by );
    webElement.sendKeys("13000002452");     //Application number

    //Element: Application number (GAINVALUETOELEMENT) - store28669693
    by = By.id( "applicationNb" );
    webElement = driver.findElement( by );
    origText = "";
    origText = webElement.getAttribute("value");
    store28669693 = origText;

    //Element: Application number (COMPAREVALUETOSTOREDELEMENT) - store28669693
    by = By.id( "applicationNb" );
    webElement = driver.findElement( by );
    origText = "";
    origText = webElement.getAttribute("value");
    if( !origText.equals( store28669693 ) ){
      fail("Stopped because the element 'store28669693': '" + origText + "' does NOT equal to '" + store80490579 + "' but it should.");
    }

    //Element: Search - BUTTON (LEFTCLICK) - store807248711
    by = By.id( "searchButton" );
    webElement = driver.findElement( by );
    try {Thread.sleep( 1000 );} catch (InterruptedException e) {}
    webElement.click();

    //Element: Status From POS List (GAINTEXTTOELEMENT) - store787439869
    by = By.cssSelector( "#\31  > td:nth-child(4)" );
    webElement = driver.findElement( by );
    origText = webElement.getText();
    store787439869 = origText;

    //Cycle starts
    startDate = Calendar.getInstance().getTime();
    actualLoop = 0;
    oneLoopLength = 2;
    maxLoopNumber = 20;
    while( actualLoop++ < maxLoopNumber ){

      //
      //Evaluation
      //
      //Element: Status From POS List (COMPARETEXTTOSTRING) - store787439869
      by = By.cssSelector( "#\31  > td:nth-child(4)" );
      webElement = driver.findElement( by );
      origText = webElement.getText();
      if( origText.equals( "Contract printed" ) ){
        break; //because the element 'store787439869' equals to 'Contract printed + "'.
      }
    } //while()
    //Element: Application number field on the POS status list (LEFTCLICK) - store1735491022
    by = By.cssSelector( "#\31  > td:nth-child(2)" );
    webElement = driver.findElement( by );
    webElement.click();

    //Element: MENU ELEMENT - Sign (LEFTCLICK) - store412879134
    by = By.id( "menuItem1-SIGNCONTRACT" );
    webElement = driver.findElement( by );
    webElement.click();

    //Element: OK Button (LEFTCLICK) - store158218645
    by = By.id( "confirmDialog_ok" );
    webElement = driver.findElement( by );
    webElement.click();

    //Element: Date to (CLEAR) - store1518393243
    by = By.id( "toDate" );
    webElement = driver.findElement( by );
    webElement.clear();

    //Element: Date to (FILLSTRING) - store1518393243
    by = By.id( "toDate" );
    webElement = driver.findElement( by );
    webElement.sendKeys("01/01/2222");     //Date to

    //Element: Application number (CLEAR) - store28669693
    by = By.id( "applicationNb" );
    webElement = driver.findElement( by );
    webElement.clear();

    //Element: Application number (FILLELEMENT) - store28669693
    by = By.id( "applicationNb" );
    webElement = driver.findElement( by );
    webElement.sendKeys("13000002452");     //Application number

    //Element: Application number (GAINVALUETOELEMENT) - store28669693
    by = By.id( "applicationNb" );
    webElement = driver.findElement( by );
    origText = "";
    origText = webElement.getAttribute("value");
    store28669693 = origText;

    //Element: Application number (COMPAREVALUETOSTOREDELEMENT) - store28669693
    by = By.id( "applicationNb" );
    webElement = driver.findElement( by );
    origText = "";
    origText = webElement.getAttribute("value");
    if( !origText.equals( store28669693 ) ){
      fail("Stopped because the element 'store28669693': '" + origText + "' does NOT equal to '" + store80490579 + "' but it should.");
    }

    //Element: Search - BUTTON (LEFTCLICK) - store807248711
    by = By.id( "searchButton" );
    webElement = driver.findElement( by );
    try {Thread.sleep( 1000 );} catch (InterruptedException e) {}
    webElement.click();

    //Element: Status From POS List (GAINTEXTTOELEMENT) - store787439869
    by = By.cssSelector( "#\31  > td:nth-child(4)" );
    webElement = driver.findElement( by );
    origText = webElement.getText();
    store787439869 = origText;

    //Cycle starts
    startDate = Calendar.getInstance().getTime();
    actualLoop = 0;
    oneLoopLength = 2;
    maxLoopNumber = 20;
    while( actualLoop++ < maxLoopNumber ){

      //
      //Evaluation
      //
      //Element: Status From POS List (COMPARETEXTTOSTRING) - store787439869
      by = By.cssSelector( "#\31  > td:nth-child(4)" );
      webElement = driver.findElement( by );
      origText = webElement.getText();
      if( origText.equals( "Contract signed" ) ){
        break; //because the element 'store787439869' equals to 'Contract signed + "'.
      }
    } //while()
    //Element: Application number field on the POS status list (LEFTCLICK) - store793401663
    by = By.cssSelector( "#\31  > td:nth-child(2)" );
    webElement = driver.findElement( by );
    webElement.click();

    //Element: MENU ELEMENT - Contract checking (LEFTCLICK) - store432052606
    by = By.id( "menuItem1-CONTRACTCHECKING" );
    webElement = driver.findElement( by );
    webElement.click();

    //Element: Operation Ok Button (LEFTCLICK) - store1206528782
    by = By.id( "saveButton" );
    webElement = driver.findElement( by );
    webElement.click();

    //Element: Found application number (COMPARETEXTTOSTOREDELEMENT) - store130332943
    by = By.cssSelector( ".table > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2)" );
    webElement = driver.findElement( by );
    origText = webElement.getText();
    if( !origText.equals( store130332943 ) ){
      fail("Stopped because the element 'store130332943': '" + origText + "' does NOT equal to '" + store80490579 + "' but it should.");
    }

    //Element: Status Close Ok Button (LEFTCLICK) - store833528384
    by = By.id( "saveButton" );
    webElement = driver.findElement( by );
    webElement.click();

    //Element: Date to (CLEAR) - store1518393243
    by = By.id( "toDate" );
    webElement = driver.findElement( by );
    webElement.clear();

    //Element: Date to (FILLSTRING) - store1518393243
    by = By.id( "toDate" );
    webElement = driver.findElement( by );
    webElement.sendKeys("01/01/2222");     //Date to

    //Element: Application number (CLEAR) - store28669693
    by = By.id( "applicationNb" );
    webElement = driver.findElement( by );
    webElement.clear();

    //Element: Application number (FILLELEMENT) - store28669693
    by = By.id( "applicationNb" );
    webElement = driver.findElement( by );
    webElement.sendKeys("13000002452");     //Application number

    //Element: Application number (GAINVALUETOELEMENT) - store28669693
    by = By.id( "applicationNb" );
    webElement = driver.findElement( by );
    origText = "";
    origText = webElement.getAttribute("value");
    store28669693 = origText;

    //Element: Application number (COMPAREVALUETOSTOREDELEMENT) - store28669693
    by = By.id( "applicationNb" );
    webElement = driver.findElement( by );
    origText = "";
    origText = webElement.getAttribute("value");
    if( !origText.equals( store28669693 ) ){
      fail("Stopped because the element 'store28669693': '" + origText + "' does NOT equal to '" + store80490579 + "' but it should.");
    }

    //Element: Search - BUTTON (LEFTCLICK) - store807248711
    by = By.id( "searchButton" );
    webElement = driver.findElement( by );
    try {Thread.sleep( 1000 );} catch (InterruptedException e) {}
    webElement.click();

    //Element: Status From POS List (GAINTEXTTOELEMENT) - store787439869
    by = By.cssSelector( "#\31  > td:nth-child(4)" );
    webElement = driver.findElement( by );
    origText = webElement.getText();
    store787439869 = origText;

    //Cycle starts
    startDate = Calendar.getInstance().getTime();
    actualLoop = 0;
    oneLoopLength = 2;
    maxLoopNumber = 20;
    while( actualLoop++ < maxLoopNumber ){

      //
      //Evaluation
      //
      //Element: Status From POS List (COMPARETEXTTOSTRING) - store787439869
      by = By.cssSelector( "#\31  > td:nth-child(4)" );
      webElement = driver.findElement( by );
      origText = webElement.getText();
      if( origText.equals( "Contract authorized" ) ){
        break; //because the element 'store787439869' equals to 'Contract authorized + "'.
      }
    } //while()
    //Element: Application number field on the POS status list (LEFTCLICK) - store1857801419
    by = By.cssSelector( "#\31  > td:nth-child(2)" );
    webElement = driver.findElement( by );
    webElement.click();

    //Element: MENU ELEMENT - To Approve for Financing (LEFTCLICK) - store1670495154
    by = By.id( "menuItem1-APPROVETOFINANCE" );
    webElement = driver.findElement( by );
    webElement.click();

    //Element: Found application number (COMPARETEXTTOSTOREDELEMENT) - store136175479
    by = By.cssSelector( "#prompt_application_information-section > table:nth-child(2) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2)" );
    webElement = driver.findElement( by );
    origText = webElement.getText();
    if( !origText.equals( store136175479 ) ){
      fail("Stopped because the element 'store136175479': '" + origText + "' does NOT equal to '" + store80490579 + "' but it should.");
    }

    //Element: Approve Button (LEFTCLICK) - store1470721315
    by = By.id( "approveButton" );
    webElement = driver.findElement( by );
    webElement.click();

    //Element: Date to (CLEAR) - store1518393243
    by = By.id( "toDate" );
    webElement = driver.findElement( by );
    webElement.clear();

    //Element: Date to (FILLSTRING) - store1518393243
    by = By.id( "toDate" );
    webElement = driver.findElement( by );
    webElement.sendKeys("01/01/2222");     //Date to

    //Element: Application number (CLEAR) - store28669693
    by = By.id( "applicationNb" );
    webElement = driver.findElement( by );
    webElement.clear();

    //Element: Application number (FILLELEMENT) - store28669693
    by = By.id( "applicationNb" );
    webElement = driver.findElement( by );
    webElement.sendKeys("13000002452");     //Application number

    //Element: Application number (GAINVALUETOELEMENT) - store28669693
    by = By.id( "applicationNb" );
    webElement = driver.findElement( by );
    origText = "";
    origText = webElement.getAttribute("value");
    store28669693 = origText;

    //Element: Application number (COMPAREVALUETOSTOREDELEMENT) - store28669693
    by = By.id( "applicationNb" );
    webElement = driver.findElement( by );
    origText = "";
    origText = webElement.getAttribute("value");
    if( !origText.equals( store28669693 ) ){
      fail("Stopped because the element 'store28669693': '" + origText + "' does NOT equal to '" + store80490579 + "' but it should.");
    }

    //Element: Search - BUTTON (LEFTCLICK) - store807248711
    by = By.id( "searchButton" );
    webElement = driver.findElement( by );
    try {Thread.sleep( 1000 );} catch (InterruptedException e) {}
    webElement.click();

    //Element: Status From POS List (GAINTEXTTOELEMENT) - store787439869
    by = By.cssSelector( "#\31  > td:nth-child(4)" );
    webElement = driver.findElement( by );
    origText = webElement.getText();
    store787439869 = origText;

    //Cycle starts
    startDate = Calendar.getInstance().getTime();
    actualLoop = 0;
    oneLoopLength = 2;
    maxLoopNumber = 20;
    while( actualLoop++ < maxLoopNumber ){

      //
      //Evaluation
      //
      //Element: Status From POS List (COMPARETEXTTOSTRING) - store787439869
      by = By.cssSelector( "#\31  > td:nth-child(4)" );
      webElement = driver.findElement( by );
      origText = webElement.getText();
      if( origText.equals( "Contract authorized" ) ){
        break; //because the element 'store787439869' equals to 'Contract authorized + "'.
      }
    } //while()
    //Script: Kill RFBANK Window Closer javascript (EXECUTE_SCRIPT) - script685706392
    ScriptClass script685706392 = new ScriptClass(){
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
      script685706392.runScript();
    }catch( Exception e ){
      e.printStackTrace();
      fail( e.getMessage() );
    }

    //Script: Close Window (EXECUTE_SCRIPT) - script2071221321
    ScriptClass script2071221321 = new ScriptClass(){
      @Override
      public void runScript() throws Exception{
        driver.close();
      }
    };
    try{
      script2071221321.runScript();
    }catch( Exception e ){
      e.printStackTrace();
      fail( e.getMessage() );
    }

  }

}
