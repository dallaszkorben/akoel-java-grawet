package hu.akoel.grawet;

import java.io.PrintWriter;
import java.io.StringWriter;
import hu.akoel.grawet.CommonOperations.Browser;
import hu.akoel.grawet.element.ParameterizedElement;
import hu.akoel.grawet.element.PureElement;
import hu.akoel.grawet.operation.ButtonOperation;
import hu.akoel.grawet.operation.FieldOperation;
import hu.akoel.grawet.page.ClosePage;
import hu.akoel.grawet.page.CustomPage;
import hu.akoel.grawet.page.OpenPage;
import hu.akoel.grawet.page.PageProgressInterface;
import hu.akoel.grawet.page.ParameterizedPage;
import hu.akoel.grawet.page.PurePage;
import hu.akoel.grawet.page.TestCasedPage;
import hu.akoel.grawet.parameter.StringParameter;
import hu.akoel.grawet.testcase.TestCase;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Grawet {


	
	WebDriver driver;

	public static void main(String args[]) {
		new Grawet();
	}

	public Grawet(){
		
		//Hasznalando nyelv beallitasa
		CommonOperations.setLocal( "en", "US" );
		//CommonOperations.setLocal( new Locale.Builder().setLanguage("hu").setRegion("HU").build());
		 
		//String url = "http://appltest01.statlogics.local:8090/RFBANK_TEST_Logic/";
		//String url = "http://www.cib.hu/";		
		String url = "http://www.google.com/";
		driver = CommonOperations.getDriver(Browser.FIREFOX);
		
		PageProgress pageProgress = new PageProgress();
		
 		//
 		// RFBANK
 		//
		
		// LOGIN PAGE
/*		
		PurePage loginPage = new PurePage( "Login page");
		
		PureElement englishLink = new PureElement(driver, "EnglishLanguage", By.cssSelector("#languageSwitch > a:nth-child(2)"), VariableSample.NO );
		loginPage.addElement(englishLink);
		PureElement username = new PureElement(driver, "Username", By.id("username"), VariableSample.POST );
		loginPage.addElement(username);
		PureElement password = new PureElement(driver, "Password", By.id("password"), VariableSample.NO );
		loginPage.addElement(password);
		PureElement nextButton = new PureElement(driver, "NextButton", By.id("loginButton"), VariableSample.NO );
		loginPage.addElement(nextButton);
		
		ParameterizedPage pLoginPage = new ParameterizedPage( "Login page", loginPage );

		pLoginPage.addElement( englishLink, new LinkOperation());
		pLoginPage.addElement( username,  new FieldOperation( new StringParameter( "username", "myuser1") ) );
		pLoginPage.addElement( password,  new FieldOperation( new StringParameter( "password", "a") ) );
		pLoginPage.addElement( nextButton,  new ButtonOperation() );
	


		//MAIN MENU
		
		PurePage mainMenuPage = new PurePage( "Main Menu Page" );

		PureElement posLink = new PureElement(driver, "POS menu", By.id("pos_menu"), VariableSample.NO, "menuFrame" );
		mainMenuPage.addElement(posLink);

		PureElement posApplicationLink = new PureElement(driver, "POS application", By.cssSelector("#pos_application > a:nth-child(1)"), VariableSample.NO, "menuFrame" );
		mainMenuPage.addElement(posApplicationLink);

		PureElement logoutButton = new PureElement(driver, "LOGOUT button", By.id("logout"), VariableSample.NO, "menuFrame" );
		mainMenuPage.addElement(logoutButton);		
		
		ParameterizedPage pMainMenuPage = new ParameterizedPage( "Menu page", mainMenuPage );		
		pMainMenuPage.addElement( posLink, new LinkOperation());
		pMainMenuPage.addElement( posApplicationLink, new LinkOperation());
	
		
		//LOGOUT
		ParameterizedPage pLogoutPage = new ParameterizedPage( "Logout page", mainMenuPage );		
		pLogoutPage.addElement( logoutButton, new ButtonOperation());
		
		
		
		
		//Execution
		pLoginPage.execute();
		pMainMenuPage.execute();
		pLogoutPage.execute();
		
		
		//CLOSE
		driver.close();
		driver.quit();
		driver = null;
*/		
		
		
		
		//
		// GOOGLE
		//
		
		OpenPage openPage = new OpenPage("Open page", url, driver );
		openPage.setPageProgressInterface( pageProgress );
		
		
		PurePage elsoOldal = new PurePage( "Google kereso");
		
		PureElement searchField = new PureElement(driver, "SearchField", By.id("gbqfq"), VariableSample.POST );
		elsoOldal.addElement(searchField);

		PureElement searchButton = new PureElement(driver, "SearchButton", By.id("gbqfb"), VariableSample.NO );
		elsoOldal.addElement(searchButton);
		
		
		
		ParameterizedPage firstPage = new ParameterizedPage( "Google kereso", elsoOldal );
		firstPage.setPageProgressInterface( pageProgress );
		ParameterizedElement pe = firstPage.addElement(searchField, new FieldOperation( new StringParameter( "search", "hitel") ) );
		firstPage.addElement(searchButton, new ButtonOperation(  ) );
		
		
		StringWriter writer = new StringWriter();
		PrintWriter out = new PrintWriter(writer);
		//out.println("org.openqa.selenium.WebElement webElement = pureElement.getDriver().findElement(pureElement.getBy());");    
		//out.println("org.openqa.selenium.WebElement webElement = driver.findElement( org.openqa.selenium.By.id(\"gb_70\") );");
		
		out.println("		String id = \"gb_70\";");
		out.println("		org.openqa.selenium.support.ui.WebDriverWait wait = new org.openqa.selenium.support.ui.WebDriverWait(driver, 5);");
		out.println("		try{");
		out.println("			wait.until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable( org.openqa.selenium.By.id( id ) ) );");
		out.println("		}catch (org.openqa.selenium.TimeoutException e) {");
		out.println("			throw new hu.akoel.grawet.exceptions.PageException( \"CustomPage\" , \"Bejelentkezes gomb\", org.openqa.selenium.By.id( id ).toString(), e );");
		out.println("		}");
		out.println("		org.openqa.selenium.WebElement webElement = driver.findElement( org.openqa.selenium.By.id( id ) );");		
		out.println("		webElement.click();");
		out.close();
		
		CustomPage customPage = new CustomPage("Custom page", writer.toString(), driver );
		customPage.setPageProgressInterface( pageProgress );
		
		
		ClosePage closePage = new ClosePage("close page", driver );
		closePage.setPageProgressInterface( pageProgress );
		
		TestCase testCase = new TestCase( "My test case" );
		TestCasedPage tcOpenPage = testCase.addPage( openPage );
		TestCasedPage tcFirstPage = testCase.addPage( firstPage );	
		TestCasedPage tcCustomPage = testCase.addPage( customPage );
		TestCasedPage tcClosePage = testCase.addPage( closePage );
		
		testCase.connect( tcOpenPage, tcFirstPage );
		testCase.connect(tcFirstPage, tcCustomPage);
		testCase.connect(tcCustomPage, tcClosePage);
		
		testCase.doAction();
		
		
	}
}

class PageProgress implements PageProgressInterface {
	@Override
	public void pageStarted(String name) {
		System.err.println(name + " page started");
	}

	@Override
	public void pageEnded(String name) {
		System.err.println(name + " page ended");
	}
}
