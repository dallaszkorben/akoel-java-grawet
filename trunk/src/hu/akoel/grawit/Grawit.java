package hu.akoel.grawit;

import java.awt.EventQueue;
import java.util.Locale;

import hu.akoel.grawit.gui.GUIFrame;

import org.openqa.selenium.WebDriver;

public class Grawit {
	private static final String title = "Grawit";
	private static final String version = "1.0.0";

	private static int frameWidth = 1000;
	private static int frameHeight = 600;

	WebDriver driver;

	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Grawit();
			}
		});

	}

	public class CustomClass {
		public CustomClass() {
		}

		public void doAction(WebDriver driver) throws hu.akoel.grawit.exceptions.PageException{

		 org.openqa.selenium.JavascriptExecutor executor = ((org.openqa.selenium.JavascriptExecutor) driver); executor.executeScript("window.onbeforeunload = function() {}; window.onunload = function() {};"); 

		   }
	}

	public Grawit() {

		//
		// Hasznalando nyelv beallitasa
		//
		// CommonOperations.setLocal( "en", "US" );
		CommonOperations.setLocal(new Locale.Builder().setLanguage("hu")
				.setRegion("HU").build());

		//
		// Window
		//
		GUIFrame frame = new GUIFrame(title, version, frameWidth, frameHeight);

		/*
		 * //String url =
		 * "http://appltest01.statlogics.local:8090/RFBANK_TEST_Logic/";
		 * //String url = "http://www.cib.hu/"; String url =
		 * "http://www.google.com/"; driver =
		 * CommonOperations.getDriver(Browser.FIREFOX);
		 * 
		 * PageProgress pageProgress = new PageProgress();
		 * 
		 * // // GOOGLE //
		 * 
		 * OpenPage openPage = new OpenPage("Open page", url );
		 * openPage.setPageProgressInterface( pageProgress );
		 * 
		 * BasePage elsoOldal = new BasePage( "Google kereso");
		 * 
		 * BaseElement searchField = new BaseElement("SearchField",
		 * By.id("gbqfq"), VariableSample.POST );
		 * elsoOldal.addElement(searchField);
		 * 
		 * BaseElement searchButton = new BaseElement("SearchButton",
		 * By.id("gbqfb"), VariableSample.NO );
		 * elsoOldal.addElement(searchButton);
		 * 
		 * 
		 * 
		 * ParamPage firstPage = new ParamPage( "Google kereso", elsoOldal );
		 * firstPage.setPageProgressInterface( pageProgress ); ParamElement pe =
		 * firstPage.addElement(searchField, new FieldOperation( new
		 * StringParameter( "search", "hitel") ) );
		 * firstPage.addElement(searchButton, new ButtonOperation( ) );
		 * 
		 * 
		 * StringWriter writer = new StringWriter(); PrintWriter out = new
		 * PrintWriter(writer); //out.println(
		 * "org.openqa.selenium.WebElement webElement = pureElement.getDriver().findElement(pureElement.getBy());"
		 * ); //out.println(
		 * "org.openqa.selenium.WebElement webElement = driver.findElement( org.openqa.selenium.By.id(\"gb_70\") );"
		 * );
		 * 
		 * out.println("		String id = \"gb_70\";"); out.println(
		 * "		org.openqa.selenium.support.ui.WebDriverWait wait = new org.openqa.selenium.support.ui.WebDriverWait(driver, 5);"
		 * ); out.println("		try{"); out.println(
		 * "			wait.until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable( org.openqa.selenium.By.id( id ) ) );"
		 * );
		 * out.println("		}catch (org.openqa.selenium.TimeoutException e) {");
		 * out.println(
		 * "			throw new hu.akoel.grawit.exceptions.PageException( \"CustomPage\" , \"Bejelentkezes gomb\", org.openqa.selenium.By.id( id ).toString(), e );"
		 * ); out.println("		}"); out.println(
		 * "		org.openqa.selenium.WebElement webElement = driver.findElement( org.openqa.selenium.By.id( id ) );"
		 * ); out.println("		webElement.click();"); out.close();
		 * 
		 * CustomPage customPage = new CustomPage("Custom page",
		 * writer.toString() ); customPage.setPageProgressInterface(
		 * pageProgress );
		 * 
		 * 
		 * ClosePage closePage = new ClosePage("close page" );
		 * closePage.setPageProgressInterface( pageProgress );
		 * 
		 * TestCase testCase = new TestCase( "My test case" ); TestCasedPage
		 * tcOpenPage = testCase.addPage( openPage ); TestCasedPage tcFirstPage
		 * = testCase.addPage( firstPage ); TestCasedPage tcCustomPage =
		 * testCase.addPage( customPage ); TestCasedPage tcClosePage =
		 * testCase.addPage( closePage );
		 * 
		 * testCase.connect( tcOpenPage, tcFirstPage );
		 * testCase.connect(tcFirstPage, tcCustomPage);
		 * testCase.connect(tcCustomPage, tcClosePage);
		 * 
		 * testCase.doAction(driver);
		 */

	}
}
