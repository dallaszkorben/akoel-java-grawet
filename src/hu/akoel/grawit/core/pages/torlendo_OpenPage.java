package hu.akoel.grawit.core.pages;

import hu.akoel.grawit.ExecutablePageInterface;
import hu.akoel.grawit.PageProgressInterface;
import hu.akoel.grawit.exceptions.PageException;

import org.openqa.selenium.WebDriver;

public class torlendo_OpenPage implements ExecutablePageInterface{
	private String name;
	private String url;
	private PageProgressInterface pageProgressInterface = null;
	
	public torlendo_OpenPage( String name, String url ){
		this.name = name;
		this.url = url;
	}

	@Override
	public String getName() {		
		return name;
	}

	public void setPageProgressInterface( PageProgressInterface pageProgressInterface ) {
		this.pageProgressInterface = pageProgressInterface;		
	}
	
	@Override
	public PageProgressInterface getPageProgressInterface() {
		return this.pageProgressInterface;
	}
	
	@Override
	public void doAction( WebDriver driver ) throws PageException {

//		//Jelzi, hogy elindult az oldal feldolgozasa
//		if( null != getPageProgressInterface() ){
//			getPageProgressInterface().pageStarted( getName() );
//		}	
		
//		try{		

		//Megnyitja az oldalt
		driver.get(url);			
			
		//Ha valamilyen problema tortent az oldal kezelese soran
//		}catch(Exception e){
			
//			throw new PageException(this.getName(), "Unable to open page: " + url, e );
//		}
			
		//Jelzi, hogy befejezodott az oldal feldolgozasa
//		if( null != getPageProgressInterface() ){
//			getPageProgressInterface().pageEnded( getName() );
//		}
	}



}
