package hu.akoel.grawet.page;

import hu.akoel.grawet.exceptions.PageException;

import org.openqa.selenium.WebDriver;

public class ClosePage implements ExecutablePageInterface{
	WebDriver driver;
	String name;
	PageProgressInterface pageProgressInterface = null;
	
	public ClosePage( String name, WebDriver driver ){
		this.name = name;
		this.driver = driver;
	}

	@Override
	public String getName() {		
		return name;
	}

	@Override
	public void setPageProgressInterface( PageProgressInterface pageProgressInterface ){
		this.pageProgressInterface = pageProgressInterface;
	}
	
	@Override
	public void doAction() throws PageException {

		//Jelzi, hogy elindult az oldal feldolgozasa
		if( null != pageProgressInterface ){
			pageProgressInterface.pageStarted( getName() );
		}
		
		driver.close();
		
		//Az osszes nyitott ablakot bezarja
		driver.quit();
		
		//Csak az aktualis ablakot zarja be
		//driver.close();
		
		driver = null;

		//Jelzi, hogy befejezodott az oldal feldolgozasa
		if( null != pageProgressInterface ){
			pageProgressInterface.pageEnded( getName() );
		}
	}
}
