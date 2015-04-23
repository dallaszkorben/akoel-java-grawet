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
import java.util.Enumeration;
import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.tree.TreeNode;

abstract class ScriptClass{
  ArrayList<String> parameters = new ArrayList<>();
  abstract public void runScript() throws Exception;
  public void addParameter( String parameter ){
    this.parameters.add( parameter );
  }
  
  
  
  
  
  
  
  
	public static void printDescendants(TreeNode root) {
	    System.out.println(root);
	    Enumeration children = root.children();
	    if (children != null) {
	    	while (children.hasMoreElements()) {
	    		printDescendants((TreeNode) children.nextElement());
	    	}
	    }
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
    driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

    //Script: Open Window (CLEARPARAMETERS) - script520101572
    ScriptClass script520101572 = new ScriptClass(){
      @Override
      public void runScript() throws Exception{
        String url = parameters.get(0);
        driver.get( url );
      }
    };
    script520101572.clearParameters();

    //Script: Open Window (ADDCONSTANTTOPARAMETERS) - script520101572
    script520101572.addParameter( "http://tomcat01.statlogics.local:8090/RFBANK_TEST_Logic/auth.action" );

    //Script: Open Window (EXECUTE_SCRIPT) - script520101572
    try{
      script520101572.runScript();
    }catch( Exception e ){
      e.printStackTrace();
      System.exit(-1);
    }

    //Script: Logout (EXECUTE_SCRIPT) - script789719329
    ScriptClass script789719329 = new ScriptClass(){
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
e.printStackTrace();
        }
      }
    };
    try{
      script789719329.runScript();
    }catch( Exception e ){
      e.printStackTrace();
      System.exit(-1);
    }
System.err.println("tovabbment");


    //Element: Version - Text (GAINTEXTTOELEMENT) - store1879266046
    by = By.cssSelector( "#RFTail" );
    webElement = driver.findElement( by );
    origText = webElement.getText();
    pattern = Pattern.compile( "\\d+[.]\\d+[.]\\d+[.]\\d" );
    matcher = pattern.matcher( origText );
    String store1879266046 = null;
    if( matcher.find() ){
      store1879266046 = matcher.group();
    }

    //Element: Version - Text (COMPARETEXTTOCONSTANT) - store1879266046
    by = By.cssSelector( "#RFTail" );
    webElement = driver.findElement( by );
    origText = webElement.getText();
    pattern = Pattern.compile( "v\\d+[.]\\d+[.]\\d+[.]\\d+" );
    matcher = pattern.matcher( origText );
    if( matcher.find() ){
        origText = matcher.group();
    }
    if( !origText.equals( "v3.0.0.0" ) ){
      System.err.println("Stopped because the element 'store1879266046': '" + origText + "' does NOT equal to 'v3.0.0.0' but it should.");
      System.exit(-1);
    }

    //Element: English link (LEFTCLICK) - store2084265514
    by = By.cssSelector( "#languageSwitch > a:nth-child(2)" );
    webElement = driver.findElement( by );
    webElement.click();

    //Element: username (FILLVARIABLE) - store528209117
    by = By.id( "username" );
    webElement = driver.findElement( by );
    webElement.sendKeys("myuser1");     //username

    //Element: password (FILLVARIABLE) - store419228879
    by = By.id( "password" );
    webElement = driver.findElement( by );
    webElement.sendKeys("a");     //password

    //Element: Login button (LEFTCLICK) - store1520913553
    by = By.id( "loginButton" );
    webElement = driver.findElement( by );
    webElement.click();

    //Script: Change Frame (CLEARPARAMETERS) - script1312957238
    ScriptClass script1312957238 = new ScriptClass(){
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
    script1312957238.clearParameters();

    //Script: Change Frame (ADDSTRINGTOPARAMETERS) - script1312957238
    script1312957238.addParameter( "menuFrame" );

    //Script: Change Frame (ADDSTRINGTOPARAMETERS) - script1312957238
    script1312957238.addParameter( "3" );

    //Script: Change Frame (EXECUTE_SCRIPT) - script1312957238
    try{
      script1312957238.runScript();
    }catch( Exception e ){
      e.printStackTrace();
      System.exit(-1);
    }

    //Element: POS menu (LEFTCLICK) - store1034291024
    by = By.cssSelector( "#pos_menu" );
    webElement = driver.findElement( by );
    webElement.click();

    //Element: POS - Application (menu) (LEFTCLICK) - store929754858
    by = By.cssSelector( "#pos_application > a:nth-child(1)" );
    wait = new WebDriverWait(driver, 3); //EXPLICIT WAIT
    wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
    webElement = driver.findElement( by );
    webElement.click();

    //Script: Change Frame (CLEARPARAMETERS) - script1312957238
    script1312957238.clearParameters();

    //Script: Change Frame (ADDSTRINGTOPARAMETERS) - script1312957238
    script1312957238.addParameter( "mainFrame" );

    //Script: Change Frame (ADDSTRINGTOPARAMETERS) - script1312957238
    script1312957238.addParameter( "3" );

    //Script: Change Frame (EXECUTE_SCRIPT) - script1312957238
    try{
      script1312957238.runScript();
    }catch( Exception e ){
      e.printStackTrace();
      System.exit(-1);
    }

    //Element: Merchant (FILLVARIABLE) - store1450697740
    by = By.id( "intermediaryCode_widget" );
    webElement = driver.findElement( by );
    webElement.sendKeys("mymerchant");     //Merchant

    //Element: Merchant (TAB) - store1450697740
    by = By.id( "intermediaryCode_widget" );
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);

    //Element: Purpose 1 (SELECTVARIABLEELEMENT) - store2117899083
    by = By.id( "asset_products0_purposeId" );
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByVisibleText( "mygroupofgoods" );

    //Element: Factory 1 (SELECTVARIABLEELEMENT) - store60161231
    by = By.id( "asset_products0_factoryId" );
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByVisibleText( "mybrand" );

    //Element: Description 1 (FILLVARIABLE) - store156438461
    by = By.id( "asset_products0_credObjName" );
    webElement = driver.findElement( by );
    webElement.sendKeys("vpwzdpocnrzlniy");     //Description 1

    //Element: Price 1 (FILLVARIABLE) - store1665483898
    by = By.id( "asset_products0_price-input" );
    webElement = driver.findElement( by );
    webElement.sendKeys("12000");     //Price 1

    //Element: Actions (SELECTVARIABLEELEMENT) - store1146929261
    by = By.id( "loanProduct_actionId" );
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByVisibleText( "myposaction" );

    //Element: Construction (SELECTVARIABLEELEMENT) - store703106002
    by = By.id( "loanProduct_constrCode" );
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByVisibleText( "myPOSCheap2Nofee" );

    //Element: Duration (SELECTVARIABLEELEMENT) - store993402916
    by = By.id( "loanProduct_durationPlusTermDelay" );
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByVisibleText( "12" );

    //Element: Term delay (SELECTVARIABLEELEMENT) - store1353874654
    by = By.id( "loanProduct_termDelay" );
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByVisibleText( "0" );

    //Element: Down payment (FILLVARIABLE) - store33151243
    by = By.id( "loanProduct_downPay-input" );
    webElement = driver.findElement( by );
    webElement.sendKeys("0");     //Down payment

    //Element: AS1 Type (SELECTVARIABLEELEMENT) - store2094604707
    by = By.id( "additionalServices_additionalServices0_typeId" );
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByVisibleText( "myPOSLife-InsuranceService" );

    //Element: Calculate Button (LEFTCLICK) - store1774682536
    by = By.id( "simulateButton" );
    webElement = driver.findElement( by );
    webElement.click();

    //Element: Next button (LEFTCLICK) - store876556985
    by = By.id( "nextButton" );
    webElement = driver.findElement( by );
    webElement.click();

    //Element: Research (SELECTVARIABLEELEMENT) - store1736607658
    by = By.id( "research" );
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByValue( "ADVERTISING_EXCEPT_THE_INTERNET" );

    //Element: Surname (FILLVARIABLE) - store910076067
    by = By.id( "surname" );
    webElement = driver.findElement( by );
    webElement.sendKeys("òþçõõýðïèúáêáæê");     //Surname

    //Element: First name (FILLVARIABLE) - store407858410
    by = By.id( "firstname" );
    webElement = driver.findElement( by );
    webElement.sendKeys("üþóõÿêðøíðöõòåï");     //First name

    //Element: Patronymic name (FILLVARIABLE) - store1186674269
    by = By.id( "patronymic" );
    webElement = driver.findElement( by );
    webElement.sendKeys("ÿèàóèëë÷õÿïûðúõ");     //Patronymic name

    //Element: Changed name (SELECTVARIABLEELEMENT) - store58009432
    by = By.id( "changeName" );
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByValue( "NO" );

    //Element: Fatca (SELECTVARIABLEELEMENT) - store1535800224
    by = By.id( "fatca" );
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByValue( "NO" );

    //Element: Date of birth (FILLVARIABLE) - store1226711300
    by = By.id( "birthDate" );
    webElement = driver.findElement( by );
    webElement.sendKeys("09/12/1977");     //Date of birth

    //Element: Place of birth (FILLVARIABLE) - store1667367736
    by = By.id( "placebirth" );
    webElement = driver.findElement( by );
    webElement.sendKeys("tbdyblbfilggrxu");     //Place of birth

    //Element: Sex (SELECTVARIABLEELEMENT) - store174596784
    by = By.id( "sexId" );
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByValue( "MALE" );

    //Element: Mother`s maiden surname (FILLVARIABLE) - store100214309
    by = By.id( "motherSurname" );
    webElement = driver.findElement( by );
    webElement.sendKeys("øêÿüäõðïÿ÷ëöåðå");     //Mother`s maiden surname

    //Element: Citizenship (SELECTVARIABLEELEMENT) - store1192067070
    by = By.id( "citizen" );
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByValue( "RUSSIA" );

    //Element: Education (SELECTVARIABLEELEMENT) - store1830192341
    by = By.id( "educationId" );
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByValue( "SECONDARY" );

    //Element: Passport-Series (FILLVARIABLE) - store2103523132
    by = By.id( "passpSerie" );
    webElement = driver.findElement( by );
    webElement.sendKeys("7433");     //Passport-Series

    //Element: Passport-Number (FILLVARIABLE) - store2138522689
    by = By.id( "passpNb" );
    webElement = driver.findElement( by );
    webElement.sendKeys("832150");     //Passport-Number

    //Element: Passport-Issued by (FILLVARIABLE) - store925865051
    by = By.id( "passpIssue" );
    webElement = driver.findElement( by );
    webElement.sendKeys("zyltxlrvwenaqht");     //Passport-Issued by

    //Element: Passport-Unit code (FILLVARIABLE) - store1684617389
    by = By.id( "unitCode" );
    webElement = driver.findElement( by );
    webElement.sendKeys("033558");     //Passport-Unit code

    //Element: Paasort-Issued on (FILLSTRING) - store932075826
    by = By.id( "passpIssDate" );
    webElement = driver.findElement( by );
    webElement.sendKeys("17/03/2014");     //Paasort-Issued on

    //Element: Registration in Info-Bank (SELECTVARIABLEELEMENT) - store1197346608
    by = By.id( "registrationInfoBank" );
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByValue( "NO" );

    //Element: Registration address-Region (SELECTVARIABLEELEMENT) - store505155463
    by = By.id( "registrationAddress_region" );
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByValue( "7798" );

    //Element: Registration address-City (FILLVARIABLE) - store1107783377
    by = By.id( "registrationAddress_city" );
    webElement = driver.findElement( by );
    webElement.sendKeys("àúÿûõñîäõéâóíñÿ");     //Registration address-City

    //Element: Registration address-Street (FILLVARIABLE) - store7847242
    by = By.id( "registrationAddress_street" );
    webElement = driver.findElement( by );
    webElement.sendKeys("ûñçùðàéúñáúøðùï");     //Registration address-Street

    //Element: Registration address-Street type (SELECTVARIABLEELEMENT) - store948424869
    by = By.id( "registrationAddress_streettypeId" );
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByValue( "27909" );

    //Element: Registration address-House (FILLVARIABLE) - store1298256531
    by = By.id( "registrationAddress_house" );
    webElement = driver.findElement( by );
    webElement.sendKeys("pbiikzthigo");     //Registration address-House

    //Element: Registration address-Period of living on the given address (FILLVARIABLE) - store458766135
    by = By.id( "registrationAddress_sinceDate" );
    webElement = driver.findElement( by );
    webElement.sendKeys("01/2000");     //Registration address-Period of living on the given address

    //Element: Button-Populate (LEFTCLICK) - store729631074
    by = By.id( "copy_registration_address" );
    webElement = driver.findElement( by );
    webElement.click();

    //Element: Living address-Address for correspondence (SELECTVARIABLEELEMENT) - store742058656
    by = By.id( "postingAddrId" );
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByValue( "COINCIDES_WITH_THE_REGISTRATION_ADDRESS" );

    //Element: Living address-Type of dwelling (SELECTVARIABLEELEMENT) - store419474348
    by = By.id( "dwellingId" );
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByValue( "OWNER" );

    //Element: Marital status (SELECTVARIABLEELEMENT) - store1957870019
    by = By.id( "maritalstatusId" );
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByValue( "SINGLE" );

    //Element: Number of children (FILLVARIABLE) - store1592596667
    by = By.id( "nbChildren" );
    webElement = driver.findElement( by );
    webElement.sendKeys("0");     //Number of children

    //Element: Number of dependents (FILLVARIABLE) - store902792749
    by = By.id( "dependants" );
    webElement = driver.findElement( by );
    webElement.sendKeys("0");     //Number of dependents

    //Element: Private motor transport (SELECTVARIABLEELEMENT) - store919946431
    by = By.id( "hasCar" );
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByValue( "NO" );

    //Element: Web-camera (SELECTVARIABLEELEMENT) - store1475069776
    by = By.id( "webCamera" );
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByValue( "NO" );

    //Element: Reason for refusal web-camera (SELECTVARIABLEELEMENT) - store1282070063
    by = By.id( "reasonForRefusalWebCamera" );
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByValue( "NO_WEB_CAMERA" );

    //Element: Button-Next (LEFTCLICK) - store1391687516
    by = By.id( "nextButton" );
    webElement = driver.findElement( by );
    webElement.click();

    //Element: Occupation (SELECTVARIABLEELEMENT) - store1738640214
    by = By.id( "activityId" );
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByValue( "RUNS_HIS_OWN_BUSINESS_FARMER" );

    //Element: Workplace name (FILLVARIABLE) - store617041086
    by = By.id( "workName" );
    webElement = driver.findElement( by );
    webElement.sendKeys("äâìçÿùñðùåÿ");     //Workplace name

    //Element: Workplace type (SELECTVARIABLEELEMENT) - store1065341763
    by = By.id( "workplaceType" );
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByValue( "PRIVATE" );

    //Element: Period of working in this company (FILLVARIABLE) - store1180984304
    by = By.id( "sinceactiv" );
    webElement = driver.findElement( by );
    webElement.sendKeys("01/2000");     //Period of working in this company

    //Element: Political of official person (SELECTVARIABLEELEMENT) - store1780527950
    by = By.id( "politicalOrOfficialPerson" );
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByValue( "NO" );

    //Element: ZIP (FILLVARIABLE) - store331984654
    by = By.id( "workAddressDTO_zip" );
    webElement = driver.findElement( by );
    webElement.sendKeys("931052");     //ZIP

    //Element: Region (SELECTVARIABLEELEMENT) - store759480497
    by = By.id( "workAddressDTO_region" );
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByValue( "7797" );

    //Element: City (FILLVARIABLE) - store885903524
    by = By.id( "workAddressDTO_city" );
    webElement = driver.findElement( by );
    webElement.sendKeys("dngihedmawv");     //City

    //Element: Street (FILLVARIABLE) - store330816896
    by = By.id( "workAddressDTO_street" );
    webElement = driver.findElement( by );
    webElement.sendKeys("ziamddcipuw");     //Street

    //Element: Street type (SELECTVARIABLEELEMENT) - store2132249404
    by = By.id( "workAddressDTO_streettypeId" );
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByValue( "27913" );

    //Element: House (FILLVARIABLE) - store344702588
    by = By.id( "workAddressDTO_house" );
    webElement = driver.findElement( by );
    webElement.sendKeys("îãáøéæüöëòå");     //House

    //Element: Next BUTTON (LEFTCLICK) - store989033015
    by = By.id( "nextButton" );
    webElement = driver.findElement( by );
    webElement.click();

    //Element: Income-Personal monthly salary (FILLVARIABLE) - store2062873072
    by = By.id( "custIncome-input" );
    webElement = driver.findElement( by );
    webElement.sendKeys("12345.67");     //Income-Personal monthly salary

    //Element: Income-Other personal monthly income (FILLSTRING) - store1506233971
    by = By.id( "otherInc-input" );
    webElement = driver.findElement( by );
    webElement.sendKeys("55");     //Income-Other personal monthly income

    //Element: Expenses-Monthly amount rent and/or mortgage loan (FILLSTRING) - store1088196295
    by = By.id( "dwellingCredit-input" );
    webElement = driver.findElement( by );
    webElement.sendKeys("1000");     //Expenses-Monthly amount rent and/or mortgage loan

    //Element: Expenses-Monthly mandatory charges (FILLSTRING) - store559116132
    by = By.id( "householdCharge-input" );
    webElement = driver.findElement( by );
    webElement.sendKeys("100");     //Expenses-Monthly mandatory charges

    //Element: Expenses-Monthly amount other credits (FILLSTRING) - store1508239367
    by = By.id( "otherCredit-input" );
    webElement = driver.findElement( by );
    webElement.sendKeys("100");     //Expenses-Monthly amount other credits

    //Element: Bank data-Bank account (SELECTVARIABLEELEMENT) - store1388755438
    by = By.id( "bankInfo_bankAccountYes" );
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByValue( "YES" );

    //Element: Bank data-Bank card (Yes, No) - check box (LEFTCLICK) - store634803053
    by = By.id( "bankInfo_bankCardYesNo" );
    webElement = driver.findElement( by );
    webElement.click();

    //Element: Bank data-Year of opening of the account (FILLVARIABLE) - store842792314
    by = By.id( "bankInfo_bankStartYear" );
    webElement = driver.findElement( by );
    webElement.sendKeys("2000");     //Bank data-Year of opening of the account

    //Element: Special (FILLSTRING) - store363130
    by = By.id( "special" );
    webElement = driver.findElement( by );
    webElement.sendKeys("a");     //Special

    //Element: Agreement for receiving credit card (SELECTVARIABLEELEMENT) - store1336605337
    by = By.id( "agreementForCC" );
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByValue( "YES" );

    //Element: Agreement for concession of rights to third parties (SELECTVARIABLEELEMENT) - store1124280099
    by = By.id( "agreementForConcession" );
    webElement = driver.findElement( by );
    webElement.sendKeys(Keys.TAB);
    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
    select = new Select(webElement);
    select.selectByValue( "NO" );

    //Element: SAVE Button (LEFTCLICK) - store452648305
    by = By.id( "saveButton" );
    webElement = driver.findElement( by );
    webElement.click();

    //Element: Successfull Saving Indicator (GAINTEXTTOELEMENT) - store731348469
    by = By.cssSelector( ".success > ul:nth-child(1) > li:nth-child(1) > span:nth-child(1)" );
    webElement = driver.findElement( by );
    origText = webElement.getText();
    pattern = Pattern.compile( "[\\d]+" );
    matcher = pattern.matcher( origText );
    String store731348469 = null;
    if( matcher.find() ){
      store731348469 = matcher.group();
    }

    //Element: Application number of POS - Storage (GAINTEXTTOELEMENT) - store1193118872
    by = By.cssSelector( "#flowIndicator > h1:nth-child(1)" );
    webElement = driver.findElement( by );
    origText = webElement.getText();
    pattern = Pattern.compile( "[\\d]+" );
    matcher = pattern.matcher( origText );
    String store1193118872 = null;
    if( matcher.find() ){
      store1193118872 = matcher.group();
    }

    //Element: Application number of POS - Storage (OUTPUTSTOREDELEMENT) - store1193118872
    by = By.cssSelector( "#flowIndicator > h1:nth-child(1)" );
    webElement = driver.findElement( by );
    System.out.println( "App number: " + store1193118872 );

    //Script: Change Frame (CLEARPARAMETERS) - script1312957238
    script1312957238.clearParameters();

    //Script: Change Frame (ADDSTRINGTOPARAMETERS) - script1312957238
    script1312957238.addParameter( "menuFrame" );

    //Script: Change Frame (ADDSTRINGTOPARAMETERS) - script1312957238
    script1312957238.addParameter( "3" );

    //Script: Change Frame (EXECUTE_SCRIPT) - script1312957238
    try{
      script1312957238.runScript();
    }catch( Exception e ){
      e.printStackTrace();
      System.exit(-1);
    }

    //Element: POS - Status list (menu) (LEFTCLICK) - store2044751973
    by = By.cssSelector( "#pos_application_statusreport > a:nth-child(1)" );
    webElement = driver.findElement( by );
    webElement.click();

    //Script: Change Frame (CLEARPARAMETERS) - script1312957238
    script1312957238.clearParameters();

    //Script: Change Frame (ADDSTRINGTOPARAMETERS) - script1312957238

    script1312957238.addParameter( "mainFrame" );

    //Script: Change Frame (ADDSTRINGTOPARAMETERS) - script1312957238

    script1312957238.addParameter( "3" );

    //Script: Change Frame (EXECUTE_SCRIPT) - script1312957238

    try{
      script1312957238.runScript();
    }catch( Exception e ){
      e.printStackTrace();
      System.exit(-1);
    }

    //Element: Date to (CLEAR) - store1025630013
    by = By.id( "toDate" );
    webElement = driver.findElement( by );
    webElement.clear();

    //Element: Date to (FILLSTRING) - store1025630013
    by = By.id( "toDate" );
    webElement = driver.findElement( by );
    webElement.sendKeys("01/01/2222");     //Date to

    //Element: Application number (CLEAR) - store258017425
    by = By.id( "applicationNb" );
    webElement = driver.findElement( by );
    webElement.clear();

    //Element: Application number (FILLELEMENT) - store258017425
    by = By.id( "applicationNb" );
    webElement = driver.findElement( by );
    webElement.sendKeys("13000275713");     //Application number

    //Element: Application number (GAINVALUETOELEMENT) - store258017425
    by = By.id( "applicationNb" );
    webElement = driver.findElement( by );
    origText = "";
    origText = webElement.getAttribute("value");
    String store258017425 = origText;

    //Element: Application number (COMPAREVALUETOSTOREDELEMENT) - store258017425
    by = By.id( "applicationNb" );
    webElement = driver.findElement( by );
    origText = "";
    origText = webElement.getAttribute("value");
    if( !origText.equals( store258017425 ) ){
      System.err.println("Stopped because the element 'store258017425': '" + origText + "' does NOT equal to '" + store1193118872 + "' but it should.");
      System.exit(-1);
    }

    //Element: Search - BUTTON (LEFTCLICK) - store378913387
    by = By.id( "searchButton" );
    webElement = driver.findElement( by );
    webElement.click();

    //Element: Status From POS List (GAINTEXTTOELEMENT) - store1864330014
    by = By.cssSelector( "#\31  > td:nth-child(4)" );
    webElement = driver.findElement( by );
    origText = webElement.getText();
    String store1864330014 = origText;

    //Element: Status From POS List (COMPARETEXTTOSTRING) - store1864330014
    by = By.cssSelector( "#\31  > td:nth-child(4)" );
    webElement = driver.findElement( by );
    origText = webElement.getText();
    if( origText.equals( "Interrupted" ) ){
      System.err.println("Stopped because the element 'store1864330014': '" + origText + "' equals to 'Interrupted' but it should NOT.");
      System.exit(-1);
    }
    //Script: Kill RFBANK Window Closer javascript (EXECUTE_SCRIPT) - script1900959551
    ScriptClass script1900959551 = new ScriptClass(){
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
      script1900959551.runScript();
    }catch( Exception e ){
      e.printStackTrace();
      System.exit(-1);
    }

    //Script: Close Window (EXECUTE_SCRIPT) - script1088677314
    ScriptClass script1088677314 = new ScriptClass(){
      @Override
      public void runScript() throws Exception{
        driver.close();
      }
    };
    try{
      script1088677314.runScript();
    }catch( Exception e ){
      e.printStackTrace();
      System.exit(-1);
    }

  }
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  

}
