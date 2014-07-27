package hu.akoel.grawet;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

public class CommonOperations {
	private static Random rnd = new Random();
	
	private static final String NUMLIST = "1234567890";
	
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	private static SimpleDateFormat yearMonthFormat = new SimpleDateFormat("MM/yyyy");
	private static SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
	private static Calendar calendarBegin = new GregorianCalendar(1970, 01, 01 );
	private static Calendar calendarEnd = new GregorianCalendar(1994, 01, 01 );

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
	

}
