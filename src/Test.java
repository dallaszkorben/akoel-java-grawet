import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.UnexpectedTagNameException;

public class Test {

	WebDriverWait wait = null;
	By by = null;
	WebElement webElement = null;
	Select select = null;
	Integer index = 0;
	WebDriver driver = null;
	FirefoxProfile profile = null;
	JavascriptExecutor executor = null;

	public static void main(String[] args) {
		new Test();
	}

	public Test() {

		profile = new FirefoxProfile();
		profile.setPreference("pdfjs.disabled", true);
		profile.setPreference("media.navigator.permission.disabled", true);
		profile.setPreference("plugin.state.nppdf", 2);
		driver = new FirefoxDriver(profile);
		driver.get("http://tomcat01.statlogics.local:8090/RFBANK_TEST_Logic/auth.action");
		
		// Version - Text
		wait = new WebDriverWait(driver, 10);
		by = By.cssSelector("#RFTail");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done

		// Version - Text
		wait = new WebDriverWait(driver, 10);
		by = By.cssSelector("#RFTail");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done

		// English link
		wait = new WebDriverWait(driver, 10);
		by = By.cssSelector("#languageSwitch > a:nth-child(2)");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.click();

		// username
		wait = new WebDriverWait(driver, 10);
		by = By.id("username");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("myuser1"); // username
		webElement.sendKeys(Keys.TAB);

		// password
		wait = new WebDriverWait(driver, 10);
		by = By.id("password");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("a"); // password
		webElement.sendKeys(Keys.TAB);

		// Login button
		wait = new WebDriverWait(driver, 10);
		by = By.id("loginButton");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.click();

		// POS menu
		wait = new WebDriverWait(driver, 10);
		by = By.cssSelector("#pos_menu");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.click();

		// POS - Application (menu)
		wait = new WebDriverWait(driver, 10);
		by = By.cssSelector("#pos_application > a:nth-child(1)");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.click();

		// Merchant
		wait = new WebDriverWait(driver, 10);
		by = By.id("intermediaryCode_widget");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("mymerchant"); // Merchant
		webElement.sendKeys(Keys.TAB);

		// Merchant
		wait = new WebDriverWait(driver, 10);
		by = By.id("intermediaryCode_widget");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys(Keys.TAB);

		// Purpose 1
		wait = new WebDriverWait(driver, 10);
		by = By.id("asset_products0_purposeId");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		try {
			select = new Select(webElement);
		} catch (UnexpectedTagNameException e) {
		}
		select.selectByVisibleText("mygroupofgoods");

		// Factory 1
		wait = new WebDriverWait(driver, 10);
		by = By.id("asset_products0_factoryId");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		try {
			select = new Select(webElement);
		} catch (UnexpectedTagNameException e) {
		}
		select.selectByVisibleText("mybrand");

		// Description 1
		wait = new WebDriverWait(driver, 10);
		by = By.id("asset_products0_credObjName");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("xhwxajovobprlbz"); // Description 1
		webElement.sendKeys(Keys.TAB);

		// Price 1
		wait = new WebDriverWait(driver, 10);
		by = By.id("asset_products0_price-input");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("12000"); // Price 1
		webElement.sendKeys(Keys.TAB);

		// Actions
		wait = new WebDriverWait(driver, 10);
		by = By.id("loanProduct_actionId");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		try {
			select = new Select(webElement);
		} catch (UnexpectedTagNameException e) {
		}
		select.selectByVisibleText("myposaction");

		// Construction
		wait = new WebDriverWait(driver, 10);
		by = By.id("loanProduct_constrCode");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		try {
			select = new Select(webElement);
		} catch (UnexpectedTagNameException e) {
		}
		select.selectByVisibleText("myPOSCheap2Nofee");

		// Product1+
		wait = new WebDriverWait(driver, 10);
		by = By.id("addButton0");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.click();

		// Purpose 2
		wait = new WebDriverWait(driver, 10);
		by = By.id("asset_products1_purposeId");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		try {
			select = new Select(webElement);
		} catch (UnexpectedTagNameException e) {
		}
		select.selectByVisibleText("mygroupofgoods");

		// Factory 2
		wait = new WebDriverWait(driver, 10);
		by = By.id("asset_products1_factoryId");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		try {
			select = new Select(webElement);
		} catch (UnexpectedTagNameException e) {
		}
		select.selectByVisibleText("mybrand");

		// Description 2
		wait = new WebDriverWait(driver, 10);
		by = By.id("asset_products1_credObjName");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("nnqfzpryrcydusn"); // Description 2
		webElement.sendKeys(Keys.TAB);

		// Price 2
		wait = new WebDriverWait(driver, 10);
		by = By.id("asset_products1_price-input");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("12000"); // Price 2
		webElement.sendKeys(Keys.TAB);

		// Duration
		wait = new WebDriverWait(driver, 10);
		by = By.id("loanProduct_durationPlusTermDelay");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		try {
			select = new Select(webElement);
		} catch (UnexpectedTagNameException e) {
		}
		select.selectByVisibleText("12");

		// Term delay
		wait = new WebDriverWait(driver, 10);
		by = By.id("loanProduct_termDelay");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		try {
			select = new Select(webElement);
		} catch (UnexpectedTagNameException e) {
		}
		select.selectByVisibleText("1");

		// Down payment
		wait = new WebDriverWait(driver, 10);
		by = By.id("loanProduct_downPay-input");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("12000"); // Down payment
		webElement.sendKeys(Keys.TAB);

		// AS1 Type
		wait = new WebDriverWait(driver, 10);
		by = By.id("additionalServices_additionalServices0_typeId");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		try {
			select = new Select(webElement);
		} catch (UnexpectedTagNameException e) {
		}
		select.selectByVisibleText("myPOSLife-InsuranceService");

		// AS1Company
		wait = new WebDriverWait(driver, 10);
		by = By.id("additionalServices_additionalServices0_companyId");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		try {
			select = new Select(webElement);
		} catch (UnexpectedTagNameException e) {
		}
		select.selectByVisibleText("mycomp");

		// AS1 Construction
		wait = new WebDriverWait(driver, 10);
		by = By.id("additionalServices_additionalServices0_constructionId");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		try {
			select = new Select(webElement);
		} catch (UnexpectedTagNameException e) {
		}
		select.selectByVisibleText("myPOSLifeInsuranceConstruction");

		// AS1 +
		wait = new WebDriverWait(driver, 10);
		by = By.id("addAdditionalServiceButton0");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.click();

		// AS2 Type
		wait = new WebDriverWait(driver, 10);
		by = By.id("additionalServices_additionalServices1_typeId");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		try {
			select = new Select(webElement);
		} catch (UnexpectedTagNameException e) {
		}
		select.selectByVisibleText("myPOSLifeAndHealt-InsuranceService");

		// AS2 Company
		wait = new WebDriverWait(driver, 10);
		by = By.id("additionalServices_additionalServices1_companyId");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		try {
			select = new Select(webElement);
		} catch (UnexpectedTagNameException e) {
		}
		select.selectByVisibleText("mycomp");

		// AS2 Construction
		wait = new WebDriverWait(driver, 10);
		by = By.id("additionalServices_additionalServices1_constructionId");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		try {
			select = new Select(webElement);
		} catch (UnexpectedTagNameException e) {
		}
		select.selectByVisibleText("myPOSLifeandhealthInsuranceConstruction");

		// AS2 +
		wait = new WebDriverWait(driver, 10);
		by = By.id("addAdditionalServiceButton1");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.click();

		// AS3 Type
		wait = new WebDriverWait(driver, 10);
		by = By.id("additionalServices_additionalServices2_typeId");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		try {
			select = new Select(webElement);
		} catch (UnexpectedTagNameException e) {
		}
		select.selectByVisibleText("myPOSRenessans-SMSService");

		// AS3 Company
		wait = new WebDriverWait(driver, 10);
		by = By.id("additionalServices_additionalServices2_companyId");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		try {
			select = new Select(webElement);
		} catch (UnexpectedTagNameException e) {
		}
		select.selectByVisibleText("mycomp");

		// AS3 Construction
		wait = new WebDriverWait(driver, 10);
		by = By.id("additionalServices_additionalServices2_constructionId");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		try {
			select = new Select(webElement);
		} catch (UnexpectedTagNameException e) {
		}
		select.selectByVisibleText("myPOSRenessansSmsConstruction");

		// AS3 +
		wait = new WebDriverWait(driver, 10);
		by = By.id("addAdditionalServiceButton2");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.click();

		// AS4 Type
		wait = new WebDriverWait(driver, 10);
		by = By.id("additionalServices_additionalServices3_typeId");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		try {
			select = new Select(webElement);
		} catch (UnexpectedTagNameException e) {
		}
		select.selectByVisibleText("myPOSBoomerang-BoomerangService");

		// AS4 Company
		wait = new WebDriverWait(driver, 10);
		by = By.id("additionalServices_additionalServices3_companyId");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		try {
			select = new Select(webElement);
		} catch (UnexpectedTagNameException e) {
		}
		select.selectByVisibleText("mycomp");

		// AS4 Construction
		wait = new WebDriverWait(driver, 10);
		by = By.id("additionalServices_additionalServices3_constructionId");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		try {
			select = new Select(webElement);
		} catch (UnexpectedTagNameException e) {
		}
		select.selectByVisibleText("myPOSBoomerangBoomerangConstruction");

		// AS4 Object of insurance
		wait = new WebDriverWait(driver, 10);
		by = By.id("additionalServices_additionalServices3_activationCodeBumerang");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("454830"); // AS4 Object of insurance
		webElement.sendKeys(Keys.TAB);

		// AS4 +
		wait = new WebDriverWait(driver, 10);
		by = By.id("addAdditionalServiceButton3");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.click();

		// AS5 Type
		wait = new WebDriverWait(driver, 10);
		by = By.id("additionalServices_additionalServices4_typeId");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		try {
			select = new Select(webElement);
		} catch (UnexpectedTagNameException e) {
		}
		select.selectByVisibleText("myPOSDwelling-DwellingService");

		// AS5 Company
		wait = new WebDriverWait(driver, 10);
		by = By.id("additionalServices_additionalServices4_companyId");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		try {
			select = new Select(webElement);
		} catch (UnexpectedTagNameException e) {
		}
		select.selectByVisibleText("mycomp");

		// AS5 Construction
		wait = new WebDriverWait(driver, 10);
		by = By.id("additionalServices_additionalServices4_constructionId");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		try {
			select = new Select(webElement);
		} catch (UnexpectedTagNameException e) {
		}
		select.selectByVisibleText("myPOSDwellingDwellingConstruction");

		// AS5 +
		wait = new WebDriverWait(driver, 10);
		by = By.id("addAdditionalServiceButton4");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.click();

		// AS6 Type
		wait = new WebDriverWait(driver, 10);
		by = By.id("additionalServices_additionalServices5_typeId");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		try {
			select = new Select(webElement);
		} catch (UnexpectedTagNameException e) {
		}
		select.selectByVisibleText("myPOSProtection-ProtectionService");

		// AS6 Company
		wait = new WebDriverWait(driver, 10);
		by = By.id("additionalServices_additionalServices5_companyId");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		try {
			select = new Select(webElement);
		} catch (UnexpectedTagNameException e) {
		}
		select.selectByVisibleText("mycomp");

		// AS6 Construction
		wait = new WebDriverWait(driver, 10);
		by = By.id("additionalServices_additionalServices5_constructionId");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		try {
			select = new Select(webElement);
		} catch (UnexpectedTagNameException e) {
		}
		select.selectByVisibleText("myPOSProtectionProtectionConstruction");

		// AS6 +
		wait = new WebDriverWait(driver, 10);
		by = By.id("addAdditionalServiceButton5");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.click();

		// AS7 Type
		wait = new WebDriverWait(driver, 10);
		by = By.id("additionalServices_additionalServices6_typeId");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		try {
			select = new Select(webElement);
		} catch (UnexpectedTagNameException e) {
		}
		select.selectByVisibleText("myPOSSMSInforming-SMSService");

		// AS7 Company
		wait = new WebDriverWait(driver, 10);
		by = By.id("additionalServices_additionalServices6_companyId");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		try {
			select = new Select(webElement);
		} catch (UnexpectedTagNameException e) {
		}
		select.selectByVisibleText("mycomp");

		// AS7 Construction
		wait = new WebDriverWait(driver, 10);
		by = By.id("additionalServices_additionalServices6_constructionId");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		try {
			select = new Select(webElement);
		} catch (UnexpectedTagNameException e) {
		}
		select.selectByVisibleText("myPOSSMSInformingSMSConstruction");

		// AS7 +
		wait = new WebDriverWait(driver, 10);
		by = By.id("addAdditionalServiceButton6");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.click();

		// AS8 Type
		wait = new WebDriverWait(driver, 10);
		by = By.id("additionalServices_additionalServices7_typeId");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		try {
			select = new Select(webElement);
		} catch (UnexpectedTagNameException e) {
		}
		select.selectByVisibleText("myPOSSoyuznik-SMSService");

		// AS8 Company
		wait = new WebDriverWait(driver, 10);
		by = By.id("additionalServices_additionalServices7_companyId");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		try {
			select = new Select(webElement);
		} catch (UnexpectedTagNameException e) {
		}
		select.selectByVisibleText("mycomp");

		// AS8 Construction
		wait = new WebDriverWait(driver, 10);
		by = By.id("additionalServices_additionalServices7_constructionId");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		try {
			select = new Select(webElement);
		} catch (UnexpectedTagNameException e) {
		}
		select.selectByVisibleText("myPOSSoyuznikSmsConstruction");

		// Calculate Button
		wait = new WebDriverWait(driver, 10);
		by = By.id("simulateButton");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.click();

		// Repayment schedule button
		wait = new WebDriverWait(driver, 10);
		by = By.id("showRepaymentSchedule");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.click();

		// Close
		wait = new WebDriverWait(driver, 10);
		by = By.id("repayment_dialog_close");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.click();

		// Next button
		wait = new WebDriverWait(driver, 10);
		by = By.id("nextButton");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.click();

		// Shop assistant name
		wait = new WebDriverWait(driver, 10);
		by = By.id("shopAssistantName");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("öùþþúðåóóûç"); // Shop assistant name
		webElement.sendKeys(Keys.TAB);

		// Agent
		wait = new WebDriverWait(driver, 10);
		by = By.id("agentId");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		try {
			select = new Select(webElement);
		} catch (UnexpectedTagNameException e) {
		}
		select.selectByVisibleText("myagent");

		// Research
		wait = new WebDriverWait(driver, 10);
		by = By.id("research");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		try {
			select = new Select(webElement);
		} catch (UnexpectedTagNameException e) {
		}
		select.selectByValue("ADVERTISING_EXCEPT_THE_INTERNET");

		// Surname
		wait = new WebDriverWait(driver, 10);
		by = By.id("surname");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("ìëúîûûðøæõéíæïì"); // Surname
		webElement.sendKeys(Keys.TAB);

		// First name
		wait = new WebDriverWait(driver, 10);
		by = By.id("firstname");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("ÿáöäëìãðíåæìàíæ"); // First name
		webElement.sendKeys(Keys.TAB);

		// Patronymic name
		wait = new WebDriverWait(driver, 10);
		by = By.id("patronymic");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("óÿûýôýüïïúîæòíê"); // Patronymic name
		webElement.sendKeys(Keys.TAB);

		// Changed name
		wait = new WebDriverWait(driver, 10);
		by = By.id("changeName");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		try {
			select = new Select(webElement);
		} catch (UnexpectedTagNameException e) {
		}
		select.selectByValue("YES");

		// Fatca
		wait = new WebDriverWait(driver, 10);
		by = By.id("fatca");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		try {
			select = new Select(webElement);
		} catch (UnexpectedTagNameException e) {
		}
		select.selectByValue("NO");

		// Old surname
		wait = new WebDriverWait(driver, 10);
		by = By.id("surnameOld");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("üüöâöòäùùèã"); // Old surname
		webElement.sendKeys(Keys.TAB);

		// Old first name
		wait = new WebDriverWait(driver, 10);
		by = By.id("firstnameOld");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("óáþúøÿ÷öêýì"); // Old first name
		webElement.sendKeys(Keys.TAB);

		// Old patronymic name
		wait = new WebDriverWait(driver, 10);
		by = By.id("patronymicOld");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("ìüêûáýüÿõãç"); // Old patronymic name
		webElement.sendKeys(Keys.TAB);

		// Date of birth
		wait = new WebDriverWait(driver, 10);
		by = By.id("birthDate");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("03/11/1971"); // Date of birth
		webElement.sendKeys(Keys.TAB);

		// Place of birth
		wait = new WebDriverWait(driver, 10);
		by = By.id("placebirth");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("udqgvrwwautweau"); // Place of birth
		webElement.sendKeys(Keys.TAB);

		// Sex
		wait = new WebDriverWait(driver, 10);
		by = By.id("sexId");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		try {
			select = new Select(webElement);
		} catch (UnexpectedTagNameException e) {
		}
		select.selectByValue("MALE");

		// Mother`s maiden surname
		wait = new WebDriverWait(driver, 10);
		by = By.id("motherSurname");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("äéøÿøø÷äøúîàþýò"); // Mother`s maiden surname
		webElement.sendKeys(Keys.TAB);

		// Credit history subject code
		wait = new WebDriverWait(driver, 10);
		by = By.id("crHistSubCode");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("512631546425201"); // Credit history subject code
		webElement.sendKeys(Keys.TAB);

		// CHS
		wait = new WebDriverWait(driver, 10);
		by = By.id("chs");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		try {
			select = new Select(webElement);
		} catch (UnexpectedTagNameException e) {
		}
		select.selectByVisibleText("CHS generated");

		// Citizenship
		wait = new WebDriverWait(driver, 10);
		by = By.id("citizen");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		try {
			select = new Select(webElement);
		} catch (UnexpectedTagNameException e) {
		}
		select.selectByValue("RUSSIA");

		// Education
		wait = new WebDriverWait(driver, 10);
		by = By.id("educationId");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		try {
			select = new Select(webElement);
		} catch (UnexpectedTagNameException e) {
		}
		select.selectByValue("SECONDARY");

		// Passport-Series
		wait = new WebDriverWait(driver, 10);
		by = By.id("passpSerie");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("0320"); // Passport-Series
		webElement.sendKeys(Keys.TAB);

		// Passport-Number
		wait = new WebDriverWait(driver, 10);
		by = By.id("passpNb");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("977690"); // Passport-Number
		webElement.sendKeys(Keys.TAB);

		// Passport-Issued by
		wait = new WebDriverWait(driver, 10);
		by = By.id("passpIssue");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("ntmnxhxwwhiksah"); // Passport-Issued by
		webElement.sendKeys(Keys.TAB);

		// Passport-Unit code
		wait = new WebDriverWait(driver, 10);
		by = By.id("unitCode");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("099401"); // Passport-Unit code
		webElement.sendKeys(Keys.TAB);

		// Paasort-Issued on
		wait = new WebDriverWait(driver, 10);
		by = By.id("passpIssDate");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("20/04/2012"); // Paasort-Issued on
		webElement.sendKeys(Keys.TAB);

		// Foreign passport-Series
		wait = new WebDriverWait(driver, 10);
		by = By.id("intPasspSerie");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("44"); // Foreign passport-Series
		webElement.sendKeys(Keys.TAB);

		// Foreign passport-Number
		wait = new WebDriverWait(driver, 10);
		by = By.id("intPasspNb");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("8672373"); // Foreign passport-Number
		webElement.sendKeys(Keys.TAB);

		// Foreign passport-Issued by
		wait = new WebDriverWait(driver, 10);
		by = By.id("intPasspIssue");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("ëåçáûòòëýöñ"); // Foreign passport-Issued by
		webElement.sendKeys(Keys.TAB);

		// Foreign passport-Issued on
		wait = new WebDriverWait(driver, 10);
		by = By.id("intPasspIssDate");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("20/04/2012"); // Foreign passport-Issued on
		webElement.sendKeys(Keys.TAB);

		// Driving licence-Series
		wait = new WebDriverWait(driver, 10);
		by = By.id("drivingLicSerie");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("4816"); // Driving licence-Series
		webElement.sendKeys(Keys.TAB);

		// Driving licence-Number
		wait = new WebDriverWait(driver, 10);
		by = By.id("drivingLicNb");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("219189"); // Driving licence-Number
		webElement.sendKeys(Keys.TAB);

		// Driving licence-Issued by
		wait = new WebDriverWait(driver, 10);
		by = By.id("drivingLicIssue");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("ãâ÷âëäüôàìù"); // Driving licence-Issued by
		webElement.sendKeys(Keys.TAB);

		// Driving licence-Issued on
		wait = new WebDriverWait(driver, 10);
		by = By.id("drivingLicIssDate");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("20/04/2012"); // Driving licence-Issued on
		webElement.sendKeys(Keys.TAB);

		// Home phone-Code
		wait = new WebDriverWait(driver, 10);
		by = By.id("homePhoneDTO_phonePref");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("52635"); // Home phone-Code
		webElement.sendKeys(Keys.TAB);

		// Home phone-Number
		wait = new WebDriverWait(driver, 10);
		by = By.id("homePhoneDTO_phone");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("74522"); // Home phone-Number
		webElement.sendKeys(Keys.TAB);

		// Home phone-Contact info
		wait = new WebDriverWait(driver, 10);
		by = By.id("homePhoneDTO_phoneContact");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("ýöáèÿëñèðüè"); // Home phone-Contact info
		webElement.sendKeys(Keys.TAB);

		// Work phone-Code
		wait = new WebDriverWait(driver, 10);
		by = By.id("workPhoneDTO_phonePref");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("77916"); // Work phone-Code
		webElement.sendKeys(Keys.TAB);

		// Work phone-Number
		wait = new WebDriverWait(driver, 10);
		by = By.id("workPhoneDTO_phone");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("01099"); // Work phone-Number
		webElement.sendKeys(Keys.TAB);

		// Work phone-Extension
		wait = new WebDriverWait(driver, 10);
		by = By.id("workPhoneDTO_phoneExt");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("37101"); // Work phone-Extension
		webElement.sendKeys(Keys.TAB);

		// Work phone-Contact info
		wait = new WebDriverWait(driver, 10);
		by = By.id("workPhoneDTO_phoneContact");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("ôÿïüöýìòøýô"); // Work phone-Contact info
		webElement.sendKeys(Keys.TAB);

		// HR phone-Code
		wait = new WebDriverWait(driver, 10);
		by = By.id("workingPlaceHrPhoneDTO_phonePref");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("42135"); // HR phone-Code
		webElement.sendKeys(Keys.TAB);

		// HR phone-Number
		wait = new WebDriverWait(driver, 10);
		by = By.id("workingPlaceHrPhoneDTO_phone");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("98147"); // HR phone-Number
		webElement.sendKeys(Keys.TAB);

		// HR phone-Extension
		wait = new WebDriverWait(driver, 10);
		by = By.id("workingPlaceHrPhoneDTO_phoneExt");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("97951"); // HR phone-Extension
		webElement.sendKeys(Keys.TAB);

		// HR phone-Contact info
		wait = new WebDriverWait(driver, 10);
		by = By.id("workingPlaceHrPhoneDTO_phoneContact");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("ùþôâüçñîëöå"); // HR phone-Contact info
		webElement.sendKeys(Keys.TAB);

		// Accounts phone-Code
		wait = new WebDriverWait(driver, 10);
		by = By.id("accountsPhoneDTO_phonePref");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("80040"); // Accounts phone-Code
		webElement.sendKeys(Keys.TAB);

		// Accounts phone-Number
		wait = new WebDriverWait(driver, 10);
		by = By.id("accountsPhoneDTO_phone");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("31683"); // Accounts phone-Number
		webElement.sendKeys(Keys.TAB);

		// Accounts phone-Extension
		wait = new WebDriverWait(driver, 10);
		by = By.id("accountsPhoneDTO_phoneExt");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("25904"); // Accounts phone-Extension
		webElement.sendKeys(Keys.TAB);

		// Accounts phone-Contact info
		wait = new WebDriverWait(driver, 10);
		by = By.id("accountsPhoneDTO_phoneContact");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("÷öäâöèýçùåò"); // Accounts phone-Contact info
		webElement.sendKeys(Keys.TAB);

		// Relatives phone-Code
		wait = new WebDriverWait(driver, 10);
		by = By.id("relativesPhoneDTO_phonePref");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("97636"); // Relatives phone-Code
		webElement.sendKeys(Keys.TAB);

		// Relatives phone-Number
		wait = new WebDriverWait(driver, 10);
		by = By.id("relativesPhoneDTO_phone");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("17673"); // Relatives phone-Number
		webElement.sendKeys(Keys.TAB);

		// Relatives phone-Contact info
		wait = new WebDriverWait(driver, 10);
		by = By.id("relativesPhoneDTO_phoneContact");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("ïèîòêýôõñüà"); // Relatives phone-Contact info
		webElement.sendKeys(Keys.TAB);

		// Neighbours phone-Code
		wait = new WebDriverWait(driver, 10);
		by = By.id("neighborsPhoneDTO_phonePref");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("63566"); // Neighbours phone-Code
		webElement.sendKeys(Keys.TAB);

		// Neighbours phone-Number
		wait = new WebDriverWait(driver, 10);
		by = By.id("neighborsPhoneDTO_phone");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("67773"); // Neighbours phone-Number
		webElement.sendKeys(Keys.TAB);

		// Neighbours phone-Contact
		wait = new WebDriverWait(driver, 10);
		by = By.id("neighborsPhoneDTO_phoneContact");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("õïòíèçûñòìâ"); // Neighbours phone-Contact
		webElement.sendKeys(Keys.TAB);

		// Fax-Code
		wait = new WebDriverWait(driver, 10);
		by = By.id("faxDTO_phonePref");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("77869"); // Fax-Code
		webElement.sendKeys(Keys.TAB);

		// Fax-Number
		wait = new WebDriverWait(driver, 10);
		by = By.id("faxDTO_phone");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("72876"); // Fax-Number
		webElement.sendKeys(Keys.TAB);

		// Mobile
		wait = new WebDriverWait(driver, 10);
		by = By.id("mobile");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("9432534031"); // Mobile
		webElement.sendKeys(Keys.TAB);

		// email
		wait = new WebDriverWait(driver, 10);
		by = By.id("email");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("znbqvgoenfi"); // email
		webElement.sendKeys(Keys.TAB);

		// email
		wait = new WebDriverWait(driver, 10);
		by = By.id("email");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("@"); // email
		webElement.sendKeys(Keys.TAB);

		// email
		wait = new WebDriverWait(driver, 10);
		by = By.id("email");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("xudjbaeistz"); // email
		webElement.sendKeys(Keys.TAB);

		// email
		wait = new WebDriverWait(driver, 10);
		by = By.id("email");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("."); // email
		webElement.sendKeys(Keys.TAB);

		// email
		wait = new WebDriverWait(driver, 10);
		by = By.id("email");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("mt"); // email
		webElement.sendKeys(Keys.TAB);

		// Registration address-Zip
		wait = new WebDriverWait(driver, 10);
		by = By.id("registrationAddress_zip");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("966672"); // Registration address-Zip
		webElement.sendKeys(Keys.TAB);

		// Registration in Info-Bank
		wait = new WebDriverWait(driver, 10);
		by = By.id("registrationInfoBank");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		try {
			select = new Select(webElement);
		} catch (UnexpectedTagNameException e) {
		}
		select.selectByValue("YES");

		// Registration address-Region
		wait = new WebDriverWait(driver, 10);
		by = By.id("registrationAddress_region");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		try {
			select = new Select(webElement);
		} catch (UnexpectedTagNameException e) {
		}
		select.selectByValue("7798");

		// Registration address-City
		wait = new WebDriverWait(driver, 10);
		by = By.id("registrationAddress_city");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("üëäñåêìúýôçãâþã"); // Registration address-City
		webElement.sendKeys(Keys.TAB);

		// Registration address-Street
		wait = new WebDriverWait(driver, 10);
		by = By.id("registrationAddress_street");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("üòéæ÷èöçùøåäÿùõ"); // Registration address-Street
		webElement.sendKeys(Keys.TAB);

		// Registration address-Street type
		wait = new WebDriverWait(driver, 10);
		by = By.id("registrationAddress_streettypeId");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		try {
			select = new Select(webElement);
		} catch (UnexpectedTagNameException e) {
		}
		select.selectByValue("27909");

		// Registration address-House
		wait = new WebDriverWait(driver, 10);
		by = By.id("registrationAddress_house");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("dipsmwhmpuj"); // Registration address-House
		webElement.sendKeys(Keys.TAB);

		// Registration address-Period of living on the given address
		wait = new WebDriverWait(driver, 10);
		by = By.id("registrationAddress_sinceDate");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("01/2000"); // Registration address-Period of living
										// on the given address
		webElement.sendKeys(Keys.TAB);

		// Registration address-Building
		wait = new WebDriverWait(driver, 10);
		by = By.id("registrationAddress_building");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("4061"); // Registration address-Building
		webElement.sendKeys(Keys.TAB);

		// Registration address-Corpus
		wait = new WebDriverWait(driver, 10);
		by = By.id("registrationAddress_corpus");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("6855"); // Registration address-Corpus
		webElement.sendKeys(Keys.TAB);

		// Registration address-Apartment
		wait = new WebDriverWait(driver, 10);
		by = By.id("registrationAddress_appartment");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("4411"); // Registration address-Apartment
		webElement.sendKeys(Keys.TAB);

		// Button-Populate
		wait = new WebDriverWait(driver, 10);
		by = By.id("copy_registration_address");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.click();

		// Living address-Zip code
		wait = new WebDriverWait(driver, 10);
		by = By.id("livingAddress_zip");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("159593"); // Living address-Zip code
		webElement.sendKeys(Keys.TAB);

		// Living address-Address for correspondence
		wait = new WebDriverWait(driver, 10);
		by = By.id("postingAddrId");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		try {
			select = new Select(webElement);
		} catch (UnexpectedTagNameException e) {
		}
		select.selectByValue("COINCIDES_WITH_THE_REGISTRATION_ADDRESS");

		// Living address-Type of dwelling
		wait = new WebDriverWait(driver, 10);
		by = By.id("dwellingId");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		try {
			select = new Select(webElement);
		} catch (UnexpectedTagNameException e) {
		}
		select.selectByValue("OWNER");

		// Living address-Building
		wait = new WebDriverWait(driver, 10);
		by = By.id("livingAddress_building");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("8379"); // Living address-Building
		webElement.sendKeys(Keys.TAB);

		// Living address-Corpus
		wait = new WebDriverWait(driver, 10);
		by = By.id("livingAddress_corpus");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("1405"); // Living address-Corpus
		webElement.sendKeys(Keys.TAB);

		// Living address-Apartment
		wait = new WebDriverWait(driver, 10);
		by = By.id("livingAddress_appartment");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("4157"); // Living address-Apartment
		webElement.sendKeys(Keys.TAB);

		// Marital status
		wait = new WebDriverWait(driver, 10);
		by = By.id("maritalstatusId");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		try {
			select = new Select(webElement);
		} catch (UnexpectedTagNameException e) {
		}
		select.selectByValue("MARRIED");

		// Number of children
		wait = new WebDriverWait(driver, 10);
		by = By.id("nbChildren");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("12"); // Number of children
		webElement.sendKeys(Keys.TAB);

		// Number of dependents
		wait = new WebDriverWait(driver, 10);
		by = By.id("dependants");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("12"); // Number of dependents
		webElement.sendKeys(Keys.TAB);

		// Private motor transport
		wait = new WebDriverWait(driver, 10);
		by = By.id("hasCar");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		try {
			select = new Select(webElement);
		} catch (UnexpectedTagNameException e) {
		}
		select.selectByValue("YES");

		// Car type
		wait = new WebDriverWait(driver, 10);
		by = By.id("carType");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		try {
			select = new Select(webElement);
		} catch (UnexpectedTagNameException e) {
		}
		select.selectByValue("USED_DOMESTIC_CAR_NON_COMMERCIAL");

		// Make of the car
		wait = new WebDriverWait(driver, 10);
		by = By.id("carMake");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("âøé÷èùêäâúò"); // Make of the car
		webElement.sendKeys(Keys.TAB);

		// Model of the car
		wait = new WebDriverWait(driver, 10);
		by = By.id("carModel");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("øéüæèôûæëöù"); // Model of the car
		webElement.sendKeys(Keys.TAB);

		// Year of manufacture of the car
		wait = new WebDriverWait(driver, 10);
		by = By.id("carYear");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("2014"); // Year of manufacture of the car
		webElement.sendKeys(Keys.TAB);

		// Plate`s number
		wait = new WebDriverWait(driver, 10);
		by = By.id("carPlateNb");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.sendKeys("02549321400256398250"); // Plate`s number
		webElement.sendKeys(Keys.TAB);

		// source of the car
		wait = new WebDriverWait(driver, 10);
		by = By.id("carOrigineId");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		try {
			select = new Select(webElement);
		} catch (UnexpectedTagNameException e) {
		}
		select.selectByValue("PURCHASE_SECOND_HAND");

		// Web-camera
		wait = new WebDriverWait(driver, 10);
		by = By.id("webCamera");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		try {
			select = new Select(webElement);
		} catch (UnexpectedTagNameException e) {
		}
		select.selectByValue("YES");

		// Address of insurance object
		wait = new WebDriverWait(driver, 10);
		by = By.id("insDwellingAddress");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		try {
			select = new Select(webElement);
		} catch (UnexpectedTagNameException e) {
		}
		select.selectByValue("COINCIDES_WITH_REGISTRATION_ADDRESS");

		// Address of location of purchase
		wait = new WebDriverWait(driver, 10);
		by = By.id("purchaseAddressId");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		try {
			select = new Select(webElement);
		} catch (UnexpectedTagNameException e) {
		}
		select.selectByValue("COINCIDES_WITH_THE_REGISTRATION_ADDRESS");

		// Button-Next
		wait = new WebDriverWait(driver, 10);
		by = By.id("nextButton");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
		webElement.click();

		// Face capture section TITLE
		wait = new WebDriverWait(driver, 10);
		by = By.cssSelector(".sectionHeader");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		webElement = driver.findElement(by);
		// Done
	}
}