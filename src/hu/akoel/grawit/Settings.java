package hu.akoel.grawit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Properties;

public class Settings {

	private static final String GRAVIT_SETTING_DIRECTORY = ".grawit";
	private static final String GRAWIT_SETTING_FILE = "setting.properties";
		
	private static final String DEFAULT_LANGUAGE = new String("en");
	private static final String DEFAULT_COUNTRY = new String( "US");
	
	private static final Integer DEFAULT_WAITING_TIME = 10;	

	private static Locale locale;
	private static Settings instance = null;
	
	private static String language;
	private static String country;	
	private static Integer waitingTime;
	
	protected Settings(){
		
		language = DEFAULT_LANGUAGE;
		country = DEFAULT_COUNTRY;
		waitingTime = DEFAULT_WAITING_TIME;
		
		locale = new Locale( language, country );	
		
	}
	
	public static Settings getInstance(){
		
		if( null == instance ){
			instance = new Settings();
		}
		
		return instance;
	}
	
	public Integer getWaitingTime(){
		return waitingTime;
	}
	
	public void setWaitingTime( Integer waitingTime ){
		Settings.waitingTime = waitingTime;
	}
	
	public Locale getLocale(){
		return Settings.locale;
	}
	
	public void setLocale( Locale locale ){
		Settings.locale = locale;
	}
	
	public static File getSettingsDirectory( ) {
	    
		//User HOME konyvtaraban kell elhelyezkednie a properties file-nak, hogy irhasson bele
		String userHome = System.getProperty("user.home");
	    if(userHome == null) {
	        throw new IllegalStateException("user.home==null");
	    }
	    File home = new File(userHome);
	    File settingsDirectory = new File( home, GRAVIT_SETTING_DIRECTORY );
	    if(!settingsDirectory.exists()) {
	        if(!settingsDirectory.mkdir()) {
	            throw new IllegalStateException(settingsDirectory.toString());
	        }
	    }
	    return settingsDirectory;
	}
	
	public static File getSettingsFile( File settingDirectory ) throws IOException{
		
		 File settingsFile = new File( settingDirectory, GRAWIT_SETTING_FILE );
		 if(!settingsFile.exists()) {
			 settingsFile.createNewFile();
			 
		 }
		
		 return settingsFile;
	}
	
	public void load(){
		
		File sDir = getSettingsDirectory();
		File sFile = null;
		boolean needToWriteBack = false;
		
		try{
			sFile = getSettingsFile( sDir );
			
			Properties properties = new Properties();
			properties.load( new FileInputStream( sFile ) );
			
			//
			// Valtozok beolvasasa
			//
			String stringLanguage = properties.getProperty("language");
			String stringCountry = properties.getProperty("country");			
			String stringWaitingTime = properties.getProperty("waitingtime");			
			
			//
			// Ha a valtozo nem letezett a fajlban, akkor azt default ertekkel visszairom
			//
			
			//Language
			if( null == stringLanguage || stringLanguage.trim().length() == 0 ){
				stringLanguage = DEFAULT_LANGUAGE;
				properties.setProperty("language", stringLanguage );
				needToWriteBack = true;
			}
			language = stringLanguage;
			
			//Country
			if( null == stringCountry || stringCountry.trim().length() == 0 ){
				stringCountry = DEFAULT_COUNTRY;
				properties.setProperty("country", stringCountry );
				needToWriteBack = true;
			}
			country = stringCountry;
			
			//Waiting time
			if( null == stringWaitingTime || stringWaitingTime.trim().length() == 0 ){
				stringWaitingTime = DEFAULT_WAITING_TIME.toString();
				properties.setProperty("waitingtime", stringWaitingTime );
				needToWriteBack = true;
			}
			waitingTime = Integer.valueOf( stringWaitingTime );

			//
			// Beolvasott ertekek alapjan ujabb tulajdonsagok letrehozasa
			//
			locale = new Locale( language, country );
		
			if( needToWriteBack ){
				SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
				Calendar calendar = GregorianCalendar.getInstance();			
				properties.store( new FileOutputStream( sFile ), "It was neccessary to substitute missing/wrong keys - " +  sdf.format( calendar.getTime() ) );
			}
		
		//Gond van a fajlkezelessel
		}catch( IOException e ){			
			//marad a default ertek	
		}		
		
	}
	
	public void save(){
//TODO XML mentes		
	}
}
