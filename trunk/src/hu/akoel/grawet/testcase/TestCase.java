package hu.akoel.grawet.testcase;

import hu.akoel.grawet.exceptions.CompilationException;
import hu.akoel.grawet.exceptions.PageException;
import hu.akoel.grawet.pages.ExecutablePageInterface;
import hu.akoel.grawet.pages.OpenPage;
import hu.akoel.grawet.pages.TestCasedPage;

import java.util.HashSet;

import org.openqa.selenium.WebDriver;

public class TestCase {
	private String name;

	private HashSet<TestCasedPage> pageSet = new HashSet<>();
	
	public TestCase( String name ){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	/**
	 * Add a new ParameterizedPage to the test case
	 * 
	 * @param parameterizedPage
	 */
	public TestCasedPage addPage( ExecutablePageInterface parameterizedPage ){
		TestCasedPage testCasedPage = new TestCasedPage( parameterizedPage );
		pageSet.add( testCasedPage );
		return testCasedPage;
	}
	
	public void doAction( WebDriver driver ){
		TestCasedPage actualTestCasedPage = null;
		for( TestCasedPage testCasedPage: pageSet ){
			if( testCasedPage.getExecutablePageInterface() instanceof OpenPage ){
				actualTestCasedPage = testCasedPage;
				break;
			}
		}
		
		while( null != actualTestCasedPage ){
		
			try {
				actualTestCasedPage.doAction( driver );
				
//TODO ha hiba volt akkor nem szabad tovabb mennie. Jelzest kell adni es leallitani a teszteset futasat				
			} catch (PageException e) {				
				e.printStackTrace();
			} catch (CompilationException e) {				
				e.printStackTrace();
			}
			
			actualTestCasedPage = actualTestCasedPage.getAfter();
		}
	}
	
	/**
	 * Makes a connection between two pages
	 * 
	 * @param beforePage
	 * @param afterPage
	 */
	public void connect( TestCasedPage beforePage, TestCasedPage afterPage ){
		if( !pageSet.contains( beforePage ) ){
			throw new Error( "Serious problem found. The Page: " + beforePage.getName() + " in not in the TestCase: " + getName() + " but was tried to make a connection.");	
		}
		if( !pageSet.contains( afterPage ) ){
			throw new Error( "Serious problem found. The Page: " + beforePage.getName() + " in not in the TestCase: " + getName() + " but was tried to make a connection.");			
		}
		
		beforePage.setAfter(afterPage);
		afterPage.setBefore(beforePage);
		
	}
	
	public void disconnect( TestCasedPage beforePage, TestCasedPage afterPage ){
		if( !pageSet.contains( beforePage ) ){
			throw new Error( "Serious problem found. The Page: " + beforePage.getName() + " in not in the TestCase: " + getName() + " but was tried to make a disconnection.");	
		}
		if( !pageSet.contains( afterPage ) ){
			throw new Error( "Serious problem found. The Page: " + beforePage.getName() + " in not in the TestCase: " + getName() + " but was tried to make a disconnection.");			
		}
		
		beforePage.setAfter( null );
		afterPage.setBefore( null );
		
	}
}

