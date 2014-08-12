package hu.akoel.grawit.i.pages;

import org.openqa.selenium.WebDriver;

import hu.akoel.grawit.exceptions.PageException;

public class ClosePage implements ExecutablePageInterface{
	private String name;
	private PageProgressInterface pageProgressInterface = null;
	
	public ClosePage( String name ){
		this.name = name;
	}

	@Override
	public String getName() {		
		return name;
	}

	public void setPageProgressInterface( PageProgressInterface pageProgressInterface ){
		this.pageProgressInterface = pageProgressInterface;
	}
	
	@Override
	public PageProgressInterface getPageProgressInterface() {
		return this.pageProgressInterface;
	}
	
	@Override
	public void doAction(WebDriver driver) throws PageException {

//		//Jelzi, hogy elindult az oldal feldolgozasa
//		if( null != getPageProgressInterface() ){
//			getPageProgressInterface().pageStarted( getName() );
//		}
		
		driver.close();
		
		//Az osszes nyitott ablakot bezarja
		driver.quit();
		
		//Csak az aktualis ablakot zarja be
		//driver.close();
		
		driver = null;

//		//Jelzi, hogy befejezodott az oldal feldolgozasa
//		if( null != getPageProgressInterface() ){
//			getPageProgressInterface().pageEnded( getName() );
//		}
	}


}
