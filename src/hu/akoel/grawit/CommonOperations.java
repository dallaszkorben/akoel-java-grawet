package hu.akoel.grawit;

import hu.akoel.grawit.core.treenodedatamodel.DataModelInterface;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.enums.ParameterType;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JTextField;

import org.apache.commons.lang3.StringUtils;
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
	
	//private static final String NUMLIST = "1234567890";
	
//	private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	private static SimpleDateFormat yearMonthFormat = new SimpleDateFormat("MM/yyyy");
	private static SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
//	private static Calendar calendarBegin = new GregorianCalendar(1970, 01, 01 );
//	private static Calendar calendarEnd = new GregorianCalendar(1994, 01, 01 );

	public static enum Browser{
		FIREFOX,
		EXPLORER,
		CHROME,
		OPERA		
	}
	
	public static Locale getLocale(){
		return locale;
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
	
	public static DateFormat getDateFormat(){
		return DateFormat.getDateInstance(DateFormat.SHORT, getLocale() );
//		return dateFormat;
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
	
	public static String getRandomStringDouble( double from, double to, int decimalSize ) {
		
		double randomValue = from + (to - from) * rnd.nextDouble();		
		DecimalFormat df = new DecimalFormat( StringUtils.rightPad("#.", decimalSize, "#") );
		
		return df.format( randomValue );
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
	  * Megkeresi a parameterkent megadott DataModelInterface aktualis szintjen a name nevu es tag Tag-u node-ot
	  * Ha nem talalja, akkor null-t ad vissza, ha megtalalja, akkor pedig a node-ot
	  * 
	  * @param actualBaseDataModel
	  * @param name
	  * @return
	  */
	 public static DataModelInterface getDataModelByNameInLevel( DataModelInterface actualBaseDataModel, Tag tag, String name ){
		 int childCount = actualBaseDataModel.getChildCount();
		 for( int i = 0; i < childCount; i++ ){
			 DataModelInterface dm = (DataModelInterface)actualBaseDataModel.getChildAt( i );
			 if( dm.getName().equals( name ) && dm.getTag().equals( tag )){
				 return dm;
			 }
		 }
		 return null;
	 }	 
	 
	 public static class ValueVerifier extends InputVerifier{
			private ArrayList<Object> parameterList;
			private String defaultValue;
			private int parameterOrder;
			private ParameterType type;
			
			String goodValue = defaultValue;
			
			public ValueVerifier( ArrayList<Object> parameterList, ParameterType type, String defaultValue, int parameterOrder ){
				this.parameterList = parameterList;
				this.defaultValue = defaultValue;
				this.parameterOrder = parameterOrder;
				this.type = type;
			
				goodValue = defaultValue;
			}	
			
			@Override
			public boolean verify(JComponent input) {
				JTextField text = (JTextField)input;
				String possibleValue = text.getText();

				try {
					//Kiprobalja, hogy konvertalhato-e
					Object value = getConverted(possibleValue);//type.getParameterClass(parameterOrder).getConstructor(String.class).newInstance(possibleValue);
					parameterList.set( parameterOrder, value );
					goodValue = possibleValue;
					
				} catch (Exception e) {
					text.setText( goodValue );
					return false;
				}				
				return true;
			}
			
			public Object getConverted( String possibleValue ) throws Exception{
				return type.getParameterClass(parameterOrder).getConstructor(String.class).newInstance(possibleValue);
			}
	 } 	 
}
