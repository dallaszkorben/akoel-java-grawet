package hu.akoel.grawet.page;

import hu.akoel.grawet.exceptions.PageException;

import org.openqa.selenium.WebDriver;

public class OpenPage implements ExecutablePageInterface{
	WebDriver driver;
	String name;
	String url;
	PageProgressInterface pageProgressInterface = null;
	
	public OpenPage( String name, String url, WebDriver driver ){
		this.name = name;
		this.url = url;
		this.driver = driver;
	}

	@Override
	public String getName() {		
		return name;
	}

	@Override
	public void setPageProgressInterface( PageProgressInterface pageProgressInterface ) {
		this.pageProgressInterface = pageProgressInterface;		
	}
	
	@Override
	public void doAction() throws PageException {

		//Jelzi, hogy elindult az oldal feldolgozasa
		if( null != pageProgressInterface ){
			pageProgressInterface.pageStarted( getName() );
		}	
		
//		try{		

		//Megnyitja az oldalt
		driver.get(url);			
			
		//Ha valamilyen problema tortent az oldal kezelese soran
//		}catch(Exception e){
			
//			throw new PageException(this.getName(), "Unable to open page: " + url, e );
//		}
			
		//Jelzi, hogy befejezodott az oldal feldolgozasa
		if( null != pageProgressInterface ){
			pageProgressInterface.pageEnded( getName() );
		}
	}


}
