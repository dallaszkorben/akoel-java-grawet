import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.UnexpectedTagNameException;

public class Test{ 
	
	WebDriverWait wait = null;
	By by = null;
	WebElement webElement = null;
	Select select = null;
	Integer index = 0;
	WebDriver driver = null;
	FirefoxProfile profile = null;
	JavascriptExecutor executor = null;
	
	public static void main( String[] args ){
		new Test();
	}
	
	public Test(){
			
		profile = new FirefoxProfile();
		profile.setPreference( "pdfjs.disabled", true );
		
		profile.setPreference( "plugin.state.nppdf", 2 );
		
		profile.setPreference( "media.navigator.permission.disabled", true );
		
		
		//driver = new FirefoxDriver(profile);
		driver = new InternetExplorerDriver();
		
		
		driver.get( "http://tomcat01.statlogics.local:8090/RFBANK_TEST_Logic/auth.action" );

		//Version-Gain
		wait = new WebDriverWait(driver, 10);
		by = By.cssSelector( "#RFTail" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );

		//Version-Output
		wait = new WebDriverWait(driver, 10);
		by = By.cssSelector( "#RFTail" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );

		//Version-Verify
		wait = new WebDriverWait(driver, 10);
		by = By.cssSelector( "#RFTail" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );

		//English link
		wait = new WebDriverWait(driver, 10);
		by = By.cssSelector( "#languageSwitch > a:nth-child(2)" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );
		executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", webElement);

		//Username
		wait = new WebDriverWait(driver, 10);
		by = By.id( "username" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );
		webElement.sendKeys("myuser1");     //Username
		webElement.sendKeys(Keys.TAB);

		//Password
		wait = new WebDriverWait(driver, 10);
		by = By.id( "password" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );
		webElement.sendKeys("a");     //Password
		webElement.sendKeys(Keys.TAB);

		//Login button
		wait = new WebDriverWait(driver, 10);
		by = By.id( "loginButton" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );
		executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", webElement);

		driver.switchTo().defaultContent();
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt( "menuFrame" ) );
		driver.switchTo().defaultContent();
		driver.switchTo().frame( "menuFrame" );

		//POS menu
		wait = new WebDriverWait(driver, 10);
		by = By.cssSelector( "#pos_menu" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );
		executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", webElement);

		driver.switchTo().defaultContent();
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt( "menuFrame" ) );
		driver.switchTo().defaultContent();
		driver.switchTo().frame( "menuFrame" );

		//!!! changes in the css selector
		//POS application menu
		wait = new WebDriverWait(driver, 10);
		by = By.cssSelector( "#pos_application > a:nth-child(1)" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );
		executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", webElement);
	
		driver.switchTo().defaultContent();
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt( "mainFrame" ) );
		driver.switchTo().defaultContent();
		driver.switchTo().frame( "mainFrame" );

		//Merchant
		wait = new WebDriverWait(driver, 10);
		by = By.id( "intermediaryCode_widget" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );
		webElement.sendKeys("mymerchant");     //Merchant
		webElement.sendKeys(Keys.TAB);
	
		driver.switchTo().defaultContent();
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt( "mainFrame" ) );
		driver.switchTo().defaultContent();
		driver.switchTo().frame( "mainFrame" );

		//Merchant - Tab
		wait = new WebDriverWait(driver, 10);
		by = By.id( "intermediaryCode_widget" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );
		webElement.sendKeys( Keys.TAB );
		
		//Purpose
		wait = new WebDriverWait(driver, 10);
		by = By.id( "asset_products0_purposeId" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );
		try{
			select = new Select(webElement);
		}catch(UnexpectedTagNameException e){
		}
		select.selectByVisibleText( "mygroupofgoods" );

		//Factory
		wait = new WebDriverWait(driver, 10);
		by = By.id( "asset_products0_factoryId" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );
		try{
			select = new Select(webElement);
		}catch(UnexpectedTagNameException e){
		}
		select.selectByVisibleText( "mybrand" );

		//Description
		wait = new WebDriverWait(driver, 10);
		by = By.id( "asset_products0_credObjName" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );
		webElement.sendKeys("xsscyzjirhtqych");     //Description
		webElement.sendKeys(Keys.TAB);

		//Price
		wait = new WebDriverWait(driver, 10);
		by = By.id( "asset_products0_price-input" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );
		webElement.sendKeys("12000");     //Price
		webElement.sendKeys(Keys.TAB);

		

		
		
		//Action
		wait = new WebDriverWait(driver, 10);
		by = By.id( "loanProduct_actionId" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );
		try{
			select = new Select(webElement);
		}catch(UnexpectedTagNameException e){
		}
		select.selectByVisibleText( "myposaction" );

		//Construction - myPOSCheap2Cred
		wait = new WebDriverWait(driver, 10);
		by = By.id( "loanProduct_constrCode" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );
		try{
			select = new Select(webElement);
		}catch(UnexpectedTagNameException e){
		}
		select.selectByVisibleText( "myPOSCheap2Cred" );

		//Duration - 12 months
		wait = new WebDriverWait(driver, 10);
		by = By.id( "loanProduct_durationPlusTermDelay" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );
		try{
			select = new Select(webElement);
		}catch(UnexpectedTagNameException e){
		}
		select.selectByVisibleText( "12" );

		//Term delay - 0
		wait = new WebDriverWait(driver, 10);
		by = By.id( "loanProduct_termDelay" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );
		try{
			select = new Select(webElement);
		}catch(UnexpectedTagNameException e){
		}
		select.selectByVisibleText( "0" );

		//Downpayment - 0
		wait = new WebDriverWait(driver, 10);
		by = By.id( "loanProduct_downPay-input" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );
		webElement.sendKeys("0");     //Downpayment - 0
		webElement.sendKeys(Keys.TAB);

		//AS type - myPOSLife-InsuranceService
		wait = new WebDriverWait(driver, 10);
		by = By.id( "additionalServices_additionalServices0_typeId" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );
		try{
			select = new Select(webElement);
		}catch(UnexpectedTagNameException e){
		}
		select.selectByVisibleText( "myPOSLife-InsuranceService" );

		//Calculate Button
		wait = new WebDriverWait(driver, 10);
		by = By.id( "simulateButton" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );
		executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", webElement);
		
		//Next button
		wait = new WebDriverWait(driver, 10);
		by = By.id( "nextButton" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );
		executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", webElement);

		//Research
		wait = new WebDriverWait(driver, 10);
		by = By.id( "research" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );
		try{
			select = new Select(webElement);
		}catch(UnexpectedTagNameException e){
		}
		select.selectByValue( "ADVERTISING_EXCEPT_THE_INTERNET" );

		//Surname
		wait = new WebDriverWait(driver, 10);
		by = By.id( "surname" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );
		webElement.sendKeys("‎שדעתזקך‎חועהיא");     //Surname
		webElement.sendKeys(Keys.TAB);

		//First name
		wait = new WebDriverWait(driver, 10);
		by = By.id( "firstname" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );
		webElement.sendKeys("הי‎הקאטüאףוחוהט");     //First name
		webElement.sendKeys(Keys.TAB);

		//Patronymic
		wait = new WebDriverWait(driver, 10);
		by = By.id( "patronymic" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );
		webElement.sendKeys("ךדםמדהמûוסשצפüח");     //Patronymic
		webElement.sendKeys(Keys.TAB);

		//Changed name - NO
		wait = new WebDriverWait(driver, 10);
		by = By.id( "changeName" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );
		try{
			select = new Select(webElement);
		}catch(UnexpectedTagNameException e){
		}
		select.selectByValue( "NO" );

		//Date of birth
		wait = new WebDriverWait(driver, 10);
		by = By.id( "birthDate" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );
		webElement.sendKeys("18/11/1984");     //Date of birth
		webElement.sendKeys(Keys.TAB);

		//Place of birth
		wait = new WebDriverWait(driver, 10);
		by = By.id( "placebirth" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );
		webElement.sendKeys("qnisfrjggxnystb");     //Place of birth
		webElement.sendKeys(Keys.TAB);

		//Sex - MALE
		wait = new WebDriverWait(driver, 10);
		by = By.id( "sexId" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );
		try{
			select = new Select(webElement);
		}catch(UnexpectedTagNameException e){
		}
		select.selectByValue( "MALE" );

		//Mothers maiden name
		wait = new WebDriverWait(driver, 10);
		by = By.id( "motherSurname" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );
		webElement.sendKeys("זשםגאםחü‎ÿגשצכת");     //Mothers maiden name
		webElement.sendKeys(Keys.TAB);

		//Citizenship-RUSSIA
		wait = new WebDriverWait(driver, 10);
		by = By.id( "citizen" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );
		try{
			select = new Select(webElement);
		}catch(UnexpectedTagNameException e){
		}
		select.selectByValue( "RUSSIA" );

		//Education - secondary
		wait = new WebDriverWait(driver, 10);
		by = By.id( "educationId" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );
		try{
			select = new Select(webElement);
		}catch(UnexpectedTagNameException e){
		}
		select.selectByValue( "SECONDARY" );

		//Passport - series
		wait = new WebDriverWait(driver, 10);
		by = By.id( "passpSerie" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );
		webElement.sendKeys("9529");     //Passport - series
		webElement.sendKeys(Keys.TAB);

		//Passport - Number
		wait = new WebDriverWait(driver, 10);
		by = By.id( "passpNb" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );
		webElement.sendKeys("818950");     //Passport - Number
		webElement.sendKeys(Keys.TAB);

		//Passport - Issued
		wait = new WebDriverWait(driver, 10);
		by = By.id( "passpIssue" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );
		webElement.sendKeys("gadftzyovtxwqge");     //Passport - Issued
		webElement.sendKeys(Keys.TAB);

		//Passport - Unit Code
		wait = new WebDriverWait(driver, 10);
		by = By.id( "unitCode" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );
		webElement.sendKeys("154794");     //Passport - Unit Code
		webElement.sendKeys(Keys.TAB);

		//Passport - Issued on
		wait = new WebDriverWait(driver, 10);
		by = By.id( "passpIssDate" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );
		webElement.sendKeys("26/11/2011");     //Passport - Issued on
		webElement.sendKeys(Keys.TAB);

		//Registration in Info-Bank
		wait = new WebDriverWait(driver, 10);
		by = By.id( "registrationInfoBank" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );
		try{
			select = new Select(webElement);
		}catch(UnexpectedTagNameException e){
		}
		select.selectByValue( "NO" );

		//Registration address - Region
		wait = new WebDriverWait(driver, 10);
		by = By.id( "registrationAddress_region" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );
		try{
			select = new Select(webElement);
		}catch(UnexpectedTagNameException e){
		}
		select.selectByValue( "7798" );

		//Registration address-City
		wait = new WebDriverWait(driver, 10);
		by = By.id( "registrationAddress_city" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );
		webElement.sendKeys("צןעםדךןÿûחאדפהל");     //Registration address-City
		webElement.sendKeys(Keys.TAB);

		//Registration address-Street
		wait = new WebDriverWait(driver, 10);
		by = By.id( "registrationAddress_street" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );
		webElement.sendKeys("ינדצשופוץבטרחמק");     //Registration address-Street
		webElement.sendKeys(Keys.TAB);

		//Registration address-Street type
		wait = new WebDriverWait(driver, 10);
		by = By.id( "registrationAddress_streettypeId" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );
		try{
			select = new Select(webElement);
		}catch(UnexpectedTagNameException e){
		}
		select.selectByValue( "27909" );

		//Registration address-House
		wait = new WebDriverWait(driver, 10);
		by = By.id( "registrationAddress_house" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );
		webElement.sendKeys("rccyxvfaegh");     //Registration address-House
		webElement.sendKeys(Keys.TAB);

		//Registration address-Period of living on the given address
		wait = new WebDriverWait(driver, 10);
		by = By.id( "registrationAddress_sinceDate" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );
		webElement.sendKeys("01/2000");     //Registration address-Period of living on the given address
		webElement.sendKeys(Keys.TAB);

		//Populate button
		wait = new WebDriverWait(driver, 10);
		by = By.id( "copy_registration_address" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );
		executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", webElement);

		//Living address-Address for correspondece
		wait = new WebDriverWait(driver, 10);
		by = By.id( "postingAddrId" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );
		try{
			select = new Select(webElement);
		}catch(UnexpectedTagNameException e){
		}
		select.selectByValue( "COINCIDES_WITH_THE_REGISTRATION_ADDRESS" );

		//Living address-Type of dwelling
		wait = new WebDriverWait(driver, 10);
		by = By.id( "dwellingId" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );
		try{
			select = new Select(webElement);
		}catch(UnexpectedTagNameException e){
		}
		select.selectByValue( "OWNER" );

		//Marital status-Single
		wait = new WebDriverWait(driver, 10);
		by = By.id( "maritalstatusId" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );
		try{
			select = new Select(webElement);
		}catch(UnexpectedTagNameException e){
		}
		select.selectByValue( "SINGLE" );

		//Number of children-0
		wait = new WebDriverWait(driver, 10);
		by = By.id( "nbChildren" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );
		webElement.sendKeys("0");     //Number of children-0
		webElement.sendKeys(Keys.TAB);

		//Number of dependents
		wait = new WebDriverWait(driver, 10);
		by = By.id( "dependants" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );
		webElement.sendKeys("0");     //Number of dependents
		webElement.sendKeys(Keys.TAB);

		//Private motor transport-NO
		wait = new WebDriverWait(driver, 10);
		by = By.id( "hasCar" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );
		try{
			select = new Select(webElement);
		}catch(UnexpectedTagNameException e){
		}
		select.selectByValue( "NO" );

		//Next button
		wait = new WebDriverWait(driver, 10);
		by = By.id( "nextButton" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );
		executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", webElement);

		//Occupation
		wait = new WebDriverWait(driver, 10);
		by = By.id( "activityId" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );
		try{
			select = new Select(webElement);
		}catch(UnexpectedTagNameException e){
		}
		select.selectByValue( "RUNS_HIS_OWN_BUSINESS_FARMER" );

		//Workpalce name
		wait = new WebDriverWait(driver, 10);
		by = By.id( "workName" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );
		webElement.sendKeys("יזעיגנסהיחכ");     //Workpalce name
		webElement.sendKeys(Keys.TAB);

		//Workplace type
		wait = new WebDriverWait(driver, 10);
		by = By.id( "workplaceType" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );
		try{
			select = new Select(webElement);
		}catch(UnexpectedTagNameException e){
		}
		select.selectByValue( "PRIVATE" );

		//Period of working in this company
		wait = new WebDriverWait(driver, 10);
		by = By.id( "sinceactiv" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );
		webElement.sendKeys("01/2000");     //Period of working in this company
		webElement.sendKeys(Keys.TAB);

		//Political of official person
		wait = new WebDriverWait(driver, 10);
		by = By.id( "politicalOrOfficialPerson" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );
		try{
			select = new Select(webElement);
		}catch(UnexpectedTagNameException e){
		}
		select.selectByValue( "NO" );

		//ZIP
		wait = new WebDriverWait(driver, 10);
		by = By.id( "workAddressDTO_zip" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );
		webElement.sendKeys("194085");     //ZIP
		webElement.sendKeys(Keys.TAB);

		//Region
		wait = new WebDriverWait(driver, 10);
		by = By.id( "workAddressDTO_region" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );
		try{
			select = new Select(webElement);
		}catch(UnexpectedTagNameException e){
		}
		select.selectByValue( "7797" );

		//City
		wait = new WebDriverWait(driver, 10);
		by = By.id( "workAddressDTO_city" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );
		webElement.sendKeys("ohpfaanorkg");     //City
		webElement.sendKeys(Keys.TAB);

		//Street
		wait = new WebDriverWait(driver, 10);
		by = By.id( "workAddressDTO_street" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );
		webElement.sendKeys("oaocdchynif");     //Street
		webElement.sendKeys(Keys.TAB);

		//Street type
		wait = new WebDriverWait(driver, 10);
		by = By.id( "workAddressDTO_streettypeId" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );
		try{
			select = new Select(webElement);
		}catch(UnexpectedTagNameException e){
		}
		select.selectByValue( "27913" );

		//House
		wait = new WebDriverWait(driver, 10);
		by = By.id( "workAddressDTO_house" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );
		webElement.sendKeys("קבקצ‎ובםכדת");     //House
		webElement.sendKeys(Keys.TAB);

		//Building
		wait = new WebDriverWait(driver, 10);
		by = By.id( "workAddressDTO_building" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );
		webElement.sendKeys("5919");     //Building
		webElement.sendKeys(Keys.TAB);

		//Corpus
		wait = new WebDriverWait(driver, 10);
		by = By.id( "workAddressDTO_corpus" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );
		webElement.sendKeys("0370");     //Corpus
		webElement.sendKeys(Keys.TAB);

		//Office
		wait = new WebDriverWait(driver, 10);
		by = By.id( "workAddressDTO_office" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );
		webElement.sendKeys("7352");     //Office
		webElement.sendKeys(Keys.TAB);

		//Next button
		wait = new WebDriverWait(driver, 10);
		by = By.id( "nextButton" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );
		executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", webElement);

		//Peronal monthly salary
		wait = new WebDriverWait(driver, 10);
		by = By.id( "custIncome-input" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );
		webElement.sendKeys("111000");     //Peronal monthly salary
		webElement.sendKeys(Keys.TAB);

		//Other personal monthly income
		wait = new WebDriverWait(driver, 10);
		by = By.id( "otherInc-input" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );
		webElement.sendKeys("55");     //Other personal monthly income
		webElement.sendKeys(Keys.TAB);

		//Monthly amount rent and/or mortgage loan
		wait = new WebDriverWait(driver, 10);
		by = By.id( "dwellingCredit-input" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );
		webElement.sendKeys("1000");     //Monthly amount rent and/or mortgage loan
		webElement.sendKeys(Keys.TAB);

		//Monthly mandatory charges
		wait = new WebDriverWait(driver, 10);
		by = By.id( "householdCharge-input" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );
		webElement.sendKeys("100");     //Monthly mandatory charges
		webElement.sendKeys(Keys.TAB);

		//Monthly amount other credits
		wait = new WebDriverWait(driver, 10);
		by = By.id( "otherCredit-input" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );
		webElement.sendKeys("100");     //Monthly amount other credits
		webElement.sendKeys(Keys.TAB);

		//Bank account
		wait = new WebDriverWait(driver, 10);
		by = By.id( "bankInfo_bankAccountYes" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );
		try{
			select = new Select(webElement);
		}catch(UnexpectedTagNameException e){
		}
		select.selectByValue( "YES" );

		//Bank card (Yes)
		wait = new WebDriverWait(driver, 10);
		by = By.id( "bankInfo_bankCardYesNo" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );
		executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", webElement);

		//Year of opening of the account
		wait = new WebDriverWait(driver, 10);
		by = By.id( "bankInfo_bankStartYear" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );
		webElement.sendKeys("2000");     //Year of opening of the account
		webElement.sendKeys(Keys.TAB);

		//Special
		wait = new WebDriverWait(driver, 10);
		by = By.id( "special" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );
		webElement.sendKeys("a");     //Special
		webElement.sendKeys(Keys.TAB);

		//Agreement for receiving credit card
		wait = new WebDriverWait(driver, 10);
		by = By.id( "agreementForCC" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );
		try{
			select = new Select(webElement);
		}catch(UnexpectedTagNameException e){
		}
		select.selectByValue( "YES" );

		//Agreement for concession of rights to third parties
		wait = new WebDriverWait(driver, 10);
		by = By.id( "agreementForConcession" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );
		try{
			select = new Select(webElement);
		}catch(UnexpectedTagNameException e){
		}
		select.selectByValue( "NO" );

		//Next button
		wait = new WebDriverWait(driver, 10);
		by = By.id( "nextButton" );
		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
		webElement = driver.findElement( by );
		executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", webElement);

	

	}
}
