package hu.akoel.grawit;

import hu.akoel.grawit.core.datamodel.BaseDataModelInterface;
import hu.akoel.grawit.core.datamodel.elements.BaseElementDataModel;
import hu.akoel.grawit.core.datamodel.pages.BasePageDataModel;
import hu.akoel.grawit.core.datamodel.roots.BaseRootDataModel;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;

import com.opera.core.systems.OperaDriver;

public class CommonOperations {
	private static String language = new String("en");
	private static String country = new String( "US");
	private static Locale locale = new Locale( language, country );
	
	private static Random rnd = new Random();
	
	private static final String NUMLIST = "1234567890";
	
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	private static SimpleDateFormat yearMonthFormat = new SimpleDateFormat("MM/yyyy");
	private static SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
	private static Calendar calendarBegin = new GregorianCalendar(1970, 01, 01 );
	private static Calendar calendarEnd = new GregorianCalendar(1994, 01, 01 );

	public static enum Browser{
		FIREFOX,
		EXPLORER,
		CHROME,
		OPERA		
	}
	
	public static void setLocal( Locale l ){
		locale = l;
	}
	
	public static void setLocal( String language, String country ){
		locale = new Locale( language, country );
	}
	
	public static String getTranslation( String code ){
		return ResourceBundle.getBundle("hu.akoel.grawit.resourcebundle.Grawet", locale ).getString( code );
	}
	
	public static SimpleDateFormat getDateFormat(){
		return dateFormat;
	}

	public static SimpleDateFormat getYearFormat(){
		return yearFormat;
	}

	public static SimpleDateFormat getYearMonthFormat(){
		return yearMonthFormat;
	}
	
	public static String getRandomString( String sampleString, int size) {
		StringBuilder sb = new StringBuilder(size);
		for (int i = 0; i < size; i++)
			sb.append( sampleString.charAt( rnd.nextInt( sampleString.length() ) ) );
		return sb.toString();
	}
	
	public static String getRandomStringIntegerRange( int from, int to ){
		return String.valueOf( rnd.nextInt( from + to ) + from );
	}
	
	public static String getRandomStringDecimal(int intLength, int decimalLength) {
		StringBuilder sb = new StringBuilder(intLength);
		for (int i = 0; i < intLength; i++)
			sb.append( NUMLIST.charAt( rnd.nextInt( NUMLIST.length() ) ) );
		
		if( decimalLength != 0){
			sb.append('.');
			
			for (int i = 0; i < decimalLength; i++)
				sb.append( NUMLIST.charAt( rnd.nextInt( NUMLIST.length() ) ) );		
		}
		return sb.toString();
	}
	
	public static String getRandomStringDate( Calendar begin, Calendar end, SimpleDateFormat dateFormat ){
		long rangeBegin = begin.getTimeInMillis();
		long rangeEnd = end.getTimeInMillis();
		long diff = (long)((rangeEnd - rangeBegin + 1) * rnd.nextDouble());
		
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime( new Timestamp(diff + rangeBegin));
		
		return dateFormat.format( calendar.getTime() );		
	}
	
	public static String getRandomStringYear( int begin, int end ){
		Calendar calendarBegin = new GregorianCalendar(begin, 01, 01 );
		Calendar calendarEnd = new GregorianCalendar(end, 01, 01 );
		return getRandomStringDate( calendarBegin, calendarEnd, yearFormat );
	}
	
	public static String getRandomStringYearMonth( int beginYear, int beginMonth, int endYear, int endMonth, SimpleDateFormat yearMonthFormat ){
		Calendar calendarBegin = new GregorianCalendar(beginYear, beginMonth, 01 );
		Calendar calendarEnd = new GregorianCalendar(endYear, endMonth, 01 );
		return getRandomStringDate( calendarBegin, calendarEnd, yearMonthFormat );
	}
	
	
	public static Calendar getRandomDate( Calendar begin, Calendar end ){
		long rangeBegin = begin.getTimeInMillis();
		long rangeEnd = end.getTimeInMillis();
		long diff = (long)((rangeEnd - rangeBegin + 1) * rnd.nextDouble());
		
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime( new Timestamp(diff + rangeBegin));
		
		return calendar;
	}
	
	public static WebDriver getDriver( Browser browser ){
		WebDriver driver = null;
		
		if( browser.equals(Browser.FIREFOX ) ){
			FirefoxProfile profile = new FirefoxProfile();
			profile.setPreference("pdfjs.disabled", true);		
			profile.setPreference("media.navigator.permission.disabled", true);
			driver =  new FirefoxDriver(profile);
		}else if( browser.equals( Browser.CHROME )){
			driver = new ChromeDriver();
		}else if( browser.equals( Browser.EXPLORER)){
			driver = new InternetExplorerDriver();
		}else if( browser.equals( Browser.OPERA )){
			driver = new OperaDriver();
		}
		return driver;
	}

	 public static ImageIcon createImageIcon(String file) {
		 ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

		 //TODO kitalalni valami inteligensebb format
		 java.net.URL imgURL = classLoader.getResource( "hu/akoel/grawit/icons/" + file );		 

		 if (imgURL != null) {
			 return new ImageIcon(imgURL);
		 } else {	           
			 return null;
		 }
	 }
	 
	 public static String getMethodName(int node){
		 final StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		 return ste[ node ].getMethodName();
	 }
	 
	 public static String getClassName(int node){
		 final StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		 return ste[ node ].getClassName();
	 }
	 
	 public static int getLineNumber(int node){
		 final StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		 return ste[ node ].getLineNumber();
	 }
	 
	 /**
	  * 
	  * BasePage alapjan megkeresi a PageBaseRoot-on a hozza tartozo BasePagePageDataModel-t
	  *  
	  * @param basePageRootDataModel
	  * @param selectedBasePage
	  * @return
	  */
/*	 public static BasePagePageDataModel getBasePagePageDataModelByBasePage( BasePageRootDataModel basePageRootDataModel, BasePage selectedBasePage ){
			
		 //Vegig megyek a PAGEBASE fan
		 @SuppressWarnings("unchecked")
		 Enumeration<BaseDataModelInterface> e = basePageRootDataModel.depthFirstEnumeration();
	   
		 while (e.hasMoreElements()) {
			 BaseDataModelInterface node = e.nextElement();
	    	
			 //Ha a vizsgalt node PAGEBASEPAGE
			 if( node instanceof BasePagePageDataModel ){
	    		
				 //Akkor megnezem, hogy azonos-e a keresettel
				 if( ((BasePagePageDataModel)node).getBasePage().equals( selectedBasePage ) ){
						
					 return (BasePagePageDataModel)node;    			
				 }	
			 }	
		 }
		 return null;
	 }
*/	 
/*	 public static BasePageDataModel getBasePagePageDataModelByBaseElement( BaseRootDataModel baseRoot, BaseElementDataModel selectedBaseElement ){
			
		 //Vegig megyek a PAGEBASE fan
		 @SuppressWarnings("unchecked")
		 Enumeration<BaseDataModelInterface> e = baseRoot.depthFirstEnumeration();
	   
		 while (e.hasMoreElements()) {
			 BaseDataModelInterface node = e.nextElement();
	    	
			 //Ha a vizsgalt node PAGEBASEPAGE
			 if( node instanceof BaseElementDataModel ){
	    		
				 //Akkor megnezem, hogy azonos-e a keresettel
				 if( ((BaseElementDataModel)node).equals( selectedBaseElement ) ){
						
					 
					 return (BasePageDataModel)(node.getParent());    			
				 }	
			 }	
		 }
		 return null;
	 }
*/
	 /**
	  * BaseElement alapjan megkeresi a PageBasePage-en a hozza tartozo BasePageElementDataModel-t
	  * @param basePagePageDataModel
	  * @param selectedBaseElement
	  * @return
	  */
/*	 public static BasePageElementDataModel getBasePageElementDataModelByBaseElement( BasePagePageDataModel basePagePageDataModel, BaseElement selectedBaseElement ){
			
		 //Vegig megyek a PAGEBASE fan
		 @SuppressWarnings("unchecked")
		 Enumeration<BaseDataModelInterface> e = basePagePageDataModel.depthFirstEnumeration();
	   
		 while (e.hasMoreElements()) {
			 BaseDataModelInterface node = e.nextElement();
	    	
			 //Ha a vizsgalt node PAGEBASEELEMENT
			 if( node instanceof BasePageElementDataModel ){
	    		
				 //Akkor megnezem, hogy azonos-e a keresettel
				 if( ((BasePageElementDataModel)node).getBaseElement().equals( selectedBaseElement ) ){
						
					 return (BasePageElementDataModel)node;    			
				 }	
			 }	
		 }
		 return null;
	 }	
*/	 
	 
	 
}
