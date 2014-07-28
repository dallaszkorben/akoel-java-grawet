package hu.akoel.grawet.page;

import hu.akoel.grawet.exceptions.CompilationException;
import hu.akoel.grawet.exceptions.PageException;

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

	public void doAction() throws PageException, CompilationException {
		
		//Jelzi, hogy elindult az oldal feldolgozasa
		if( null != executablePageInterface.getPageProgressInterface() ){
			executablePageInterface.getPageProgressInterface().pageStarted( getName() );
		}	
		
		executablePageInterface.doAction();
		
		//Jelzi, hogy befejezodott az oldal feldolgozasa
		if( null != executablePageInterface.getPageProgressInterface() ){
			executablePageInterface.getPageProgressInterface().pageEnded( getName() );
		}
	}
	

}
