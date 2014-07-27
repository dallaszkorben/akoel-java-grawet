package hu.akoel.grawet;

import hu.akoel.grawet.element.ParameterizedElement;
import hu.akoel.grawet.element.PureElement;
import hu.akoel.grawet.operation.ButtonOperation;
import hu.akoel.grawet.operation.FieldOperation;
import hu.akoel.grawet.page.ParameterizedPage;
import hu.akoel.grawet.page.PurePage;
import hu.akoel.grawet.parameter.StringParameter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

public class Proba {

	WebDriver driver;
	
	public static void main( String args[]){
		new Proba();
	}
	
	public Proba(){
		
		FirefoxProfile profile = new FirefoxProfile();
		profile.setPreference("pdfjs.disabled", true);		
		profile.setPreference("media.navigator.permission.disabled", true);
		driver =  new FirefoxDriver(profile);
		
		
		//String url = "http://appltest01.statlogics.local:8090/RFBANK_TEST_Logic/";
		//String url = "http://www.cib.hu/";		
		String url = "http://www.google.com/";
		
		try{		
			

			//Megnyitja az oldalt
			driver.get(url);			
			
		//Ha valamilyen problema tortent az oldal kezelese soran
		}catch(Exception e){
			e.printStackTrace();
		}

		
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
		// CIB
		//	
/*		PurePage elsoOldal = new PurePage( "Elso oldal");
		
		PureElement searchField = new PureElement(driver, "SearchField", By.cssSelector(".search-field"), VariableSample.NO );
		elsoOldal.addElement(searchField);
		PureElement searchButton = new PureElement(driver, "SearchButton", By.cssSelector(".btn"), VariableSample.NO );
		elsoOldal.addElement( searchButton );
		PureElement newSearchLink = new PureElement(driver, "NewSearchLink", By.cssSelector(".SearchMain > a:nth-child(3) > b:nth-child(1)"), VariableSample.NO );
		elsoOldal.addElement( newSearchLink );
		PureElement newSearchField = new PureElement(driver, "NewSearchField", By.cssSelector("#searchForm > input:nth-child(1)"), VariableSample.POST);
		elsoOldal.addElement( newSearchField );
		
		
		
		ParameterizedPage pElsoOldal = new ParameterizedPage( elsoOldal );
		
		ParameterizedElement pe = pElsoOldal.addElement(searchField, new FieldOperation( new StringParameter( "search", "kölcsön") ) );
		pElsoOldal.addElement(searchButton, new ButtonOperation() );
		pElsoOldal.addElement(newSearchLink, new LinkOperation() );
		pElsoOldal.addElement(newSearchField, new FieldOperation( new StringParameter( "ujkolcson", "hitel") ) );
		
		pElsoOldal.execute();
*/		

		//
		// GOOGLE
		//	
		PurePage elsoOldal = new PurePage( "Google kereso");
		
		PureElement searchField = new PureElement(driver, "SearchField", By.id("gbqfq"), VariableSample.POST );
		elsoOldal.addElement(searchField);

		PureElement searchButton = new PureElement(driver, "SearchButton", By.id("gbqfb"), VariableSample.NO );
		elsoOldal.addElement(searchButton);
		
		
		
		ParameterizedPage pElsoOldal = new ParameterizedPage( "Google kereso", elsoOldal );
		ParameterizedElement pe = pElsoOldal.addElement(searchField, new FieldOperation( new StringParameter( "search", "kölcsön") ) );
		pElsoOldal.addElement(searchButton, new ButtonOperation(  ) );
		
		pElsoOldal.execute();
		
		


		
		
	}
	
	public void closeWindow(){

		driver.close();
		
		//Az osszes nyitott ablakot bezarja
		driver.quit();
		
		//Csak az aktualis ablakot zarja be
		//driver.close();
		
		driver = null;

	}
}


