package hu.akoel.grawet.pages;

import hu.akoel.grawet.exceptions.PageException;

import org.openqa.selenium.WebDriver;

public class ClosePage implements ExecutablePageInterface{
	private WebDriver driver;
	private String name;
	private PageProgressInterface pageProgressInterface = null;
	
	public ClosePage( String name, WebDriver driver ){
		this.name = name;
		this.driver = driver;
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
	public void doAction() throws PageException {

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
