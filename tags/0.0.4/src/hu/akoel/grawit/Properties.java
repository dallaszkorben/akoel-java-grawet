package hu.akoel.grawit;

import java.util.Locale;

public class Properties {

	private static Properties instance = null;
	
	private static String language = new String("en");
	private static String country = new String( "US");
	private static Locale locale = new Locale( language, country );
	
	private static Integer waitingTime = 10;	
	
	protected Properties(){}
	
	public static Properties getInstance(){
		
		if( null == instance ){
			instance = new Properties();
		}
		
		return instance;
	}
	
	public Integer getWaitingTime(){
		return waitingTime;
	}
	
	public void setWaitingTime( Integer waitingTime ){
		this.waitingTime = waitingTime;
	}
	
	public Locale getLocale(){
		return locale;
	}
	
	public void setLocale( Locale locale ){
		this.locale = locale;
	}
	
	public void load(){
//TODO XML olvasas		
	}
	
	public void save(){
//TODO XML mentes		
	}
}
