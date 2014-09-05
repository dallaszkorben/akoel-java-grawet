package hu.akoel.grawit.core.pages;

import org.openqa.selenium.WebDriver;

import hu.akoel.grawit.ExecutablePageInterface;
import hu.akoel.grawit.exceptions.CompilationException;
import hu.akoel.grawit.exceptions.PageException;

public class TestCasedPage{
	ExecutablePageInterface executablePageInterface;
	
	private TestCasedPage before = null;
	private TestCasedPage after = null;
	
	public TestCasedPage( ExecutablePageInterface executablePageInterface ) {
		this.executablePageInterface = executablePageInterface;		
	}

	public ExecutablePageInterface  getExecutablePageInterface() {
		return executablePageInterface;
	}
	
	public void setBefore( TestCasedPage before ){
		this.before = before;
	}
	
	public void setAfter( TestCasedPage after ){
		this.after = after;
	}

	public TestCasedPage getBefore() {
		return before;
	}

	public TestCasedPage getAfter() {
		return after;
	}

	public String getName() {		
		return executablePageInterface.getName();
	}

	public void doAction( WebDriver driver ) throws PageException, CompilationException {
		
		//Jelzi, hogy elindult az oldal feldolgozasa
		if( null != executablePageInterface.getPageProgressInterface() ){
			executablePageInterface.getPageProgressInterface().pageStarted( getName() );
		}	
		
		executablePageInterface.doAction( driver );
		
		//Jelzi, hogy befejezodott az oldal feldolgozasa
		if( null != executablePageInterface.getPageProgressInterface() ){
			executablePageInterface.getPageProgressInterface().pageEnded( getName() );
		}
	}
	

}
