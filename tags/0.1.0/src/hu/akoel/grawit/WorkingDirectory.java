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

import org.apache.commons.io.FileUtils;

public class WorkingDirectory {

	private static final String GRAWIT_USER_HOME = System.getProperty("user.home");
	private static final String GRAWIT_WORKING_DIRECTORY = ".grawit";
	private static final String GRAWIT_CLASS_DIRECTORY = GRAWIT_WORKING_DIRECTORY + System.getProperty("file.separator") + "class";
	private static final String GRAWIT_SETTING_FILE = "setting.properties";
	private static final String GRAWIT_DYNAMICALLY_COMPILED_CLASS = "GeneratedClass";
	
	private static final String DEFAULT_LANGUAGE = new String("en");
	private static final String DEFAULT_COUNTRY = new String( "US");	
	private static final Integer DEFAULT_WAITING_TIME = 10;
	
	
	private static final String PROPERTY_NAME_LANGUAGE = "language";
	private static final String PROPERTY_NAME_COUNTRY = "country";
	private static final String PROPERTY_NAME_WAITING_TIME = "waitingtime";

	private static Locale locale;
	private static WorkingDirectory instance = null;
	
	private static String language;
	private static String country;	
	private static Integer waitingTime;
	
	protected WorkingDirectory(){
		
		language = DEFAULT_LANGUAGE;
		country = DEFAULT_COUNTRY;
		waitingTime = DEFAULT_WAITING_TIME;
		
		locale = new Locale( language, country );	
		
	}
	
	public static WorkingDirectory getInstance(){
		
		//Ha meg nem letezik a peldany
		if( null == instance ){
			
			//akkor letrehozza
			instance = new WorkingDirectory();
			
			//Es inicializalja
			WorkingDirectory.load();
			
		}
		
		return instance;
	}
	
	public Integer getWaitingTime(){
		return waitingTime;
	}
	
	public void setWaitingTime( Integer waitingTime ){
		WorkingDirectory.waitingTime = waitingTime;
	}
	
	public Locale getLocale(){
		return WorkingDirectory.locale;
	}
	
	public void setLocale( Locale locale ){
		WorkingDirectory.locale = locale;
	}
	
	public static String getDynamicallyCompiledClassName(){
		return GRAWIT_DYNAMICALLY_COMPILED_CLASS;
	}
	
	public static File getWorkingDirectory(){
		return getDirectory(GRAWIT_WORKING_DIRECTORY);
	}
	
	public static File getClassDirectory(){
		return getDirectory( GRAWIT_CLASS_DIRECTORY );
	}
	
	private static File getDirectory( String relativeDirectory ) {
	    
	    if(GRAWIT_USER_HOME == null) {
	        throw new IllegalStateException(GRAWIT_USER_HOME + "==null");
	    }
	    File home = new File(GRAWIT_USER_HOME);
	    File settingsDirectory = new File( home, relativeDirectory );
	    if(!settingsDirectory.exists()) {
	        if(!settingsDirectory.mkdir()) {
	            throw new IllegalStateException(settingsDirectory.toString());
	        }
	    }
	    return settingsDirectory;
	}
	
	private static File getSettingsFile( File settingDirectory ) throws IOException{
		
		 File settingsFile = new File( settingDirectory, GRAWIT_SETTING_FILE );
		 if(!settingsFile.exists()) {
			 settingsFile.createNewFile();
			 
		 }
		
		 return settingsFile;
	}
	
	private static void load(){
		
		File workingDirectory = getWorkingDirectory();
		File classDirectory = getClassDirectory();
		
		//Kitakaritja a Class konyvtarat
		try {
			FileUtils.cleanDirectory(classDirectory);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		
		File settingFile = null;
		boolean needToWriteBack = false;
		
		try{
			settingFile = getSettingsFile( workingDirectory );
			
			Properties properties = new Properties();
			properties.load( new FileInputStream( settingFile ) );
			
			//
			// Valtozok beolvasasa
			//
			String stringLanguage = properties.getProperty( PROPERTY_NAME_LANGUAGE );
			String stringCountry = properties.getProperty( PROPERTY_NAME_COUNTRY );			
			String stringWaitingTime = properties.getProperty( PROPERTY_NAME_WAITING_TIME );
			
			//
			// Ha a valtozo nem letezett a fajlban, akkor azt default ertekkel visszairom
			//
			
			//Language
			if( null == stringLanguage || stringLanguage.trim().length() == 0 ){
				stringLanguage = DEFAULT_LANGUAGE;
				properties.setProperty( PROPERTY_NAME_LANGUAGE, stringLanguage );
				needToWriteBack = true;
			}
			language = stringLanguage;
			
			//Country
			if( null == stringCountry || stringCountry.trim().length() == 0 ){
				stringCountry = DEFAULT_COUNTRY;
				properties.setProperty( PROPERTY_NAME_COUNTRY, stringCountry );
				needToWriteBack = true;
			}
			country = stringCountry;
			
			//Waiting time
			if( null == stringWaitingTime || stringWaitingTime.trim().length() == 0 ){
				stringWaitingTime = DEFAULT_WAITING_TIME.toString();
				properties.setProperty( PROPERTY_NAME_WAITING_TIME, stringWaitingTime );
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
				properties.store( new FileOutputStream( settingFile ), "It was neccessary to substitute missing/wrong keys - " +  sdf.format( calendar.getTime() ) );
			}
		
		//Gond van a fajlkezelessel
		}catch( IOException e ){			
			//marad a default ertek	
		}		
		
	}
	
}
