package hu.akoel.grawet;

import java.awt.EventQueue;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import hu.akoel.grawet.CommonOperations.Browser;
import hu.akoel.grawet.elements.ElementBase;
import hu.akoel.grawet.elements.ParameterizedElement;
import hu.akoel.grawet.operations.ButtonOperation;
import hu.akoel.grawet.operations.FieldOperation;
import hu.akoel.grawet.pages.ClosePage;
import hu.akoel.grawet.pages.CustomPage;
import hu.akoel.grawet.pages.OpenPage;
import hu.akoel.grawet.pages.PageBase;
import hu.akoel.grawet.pages.PageProgressInterface;
import hu.akoel.grawet.pages.ParameterizedPage;
import hu.akoel.grawet.pages.TestCasedPage;
import hu.akoel.grawet.parameter.StringParameter;
import hu.akoel.grawet.testcase.TestCase;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Grawet {
	private static final String title = "Grawet";
	private static final String version = "1.0.0";
	
	WebDriver driver;

	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
            	new Grawet();
            }
        });
		
	}

	public Grawet(){
		
		//
		// Hasznalando nyelv beallitasa
		//
		//CommonOperations.setLocal( "en", "US" );
		CommonOperations.setLocal( new Locale.Builder().setLanguage("hu").setRegion("HU").build());
		 
		//
		// Window
		//		
		GUIFrame frame = new GUIFrame( title + " " + version );
		

		
        
        
        
       
/*		
		//String url = "http://appltest01.statlogics.local:8090/RFBANK_TEST_Logic/";
		//String url = "http://www.cib.hu/";		
		String url = "http://www.google.com/";
		driver = CommonOperations.getDriver(Browser.FIREFOX);
        
		PageProgress pageProgress = new PageProgress();
		
 		//
		// GOOGLE
		//
		
		OpenPage openPage = new OpenPage("Open page", url );
		openPage.setPageProgressInterface( pageProgress );
		
		PageBase elsoOldal = new PageBase( "Google kereso");
		
		ElementBase searchField = new ElementBase("SearchField", By.id("gbqfq"), VariableSample.POST );
		elsoOldal.addElement(searchField);

		ElementBase searchButton = new ElementBase("SearchButton", By.id("gbqfb"), VariableSample.NO );
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
		
		CustomPage customPage = new CustomPage("Custom page", writer.toString() );
		customPage.setPageProgressInterface( pageProgress );
		
		
		ClosePage closePage = new ClosePage("close page" );
		closePage.setPageProgressInterface( pageProgress );
		
		TestCase testCase = new TestCase( "My test case" );
		TestCasedPage tcOpenPage = testCase.addPage( openPage );
		TestCasedPage tcFirstPage = testCase.addPage( firstPage );	
		TestCasedPage tcCustomPage = testCase.addPage( customPage );
		TestCasedPage tcClosePage = testCase.addPage( closePage );
		
		testCase.connect( tcOpenPage, tcFirstPage );
		testCase.connect(tcFirstPage, tcCustomPage);
		testCase.connect(tcCustomPage, tcClosePage);
		
		testCase.doAction(driver);
*/		
		
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
