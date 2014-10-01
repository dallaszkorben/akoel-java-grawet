package hu.akoel.grawit.enums.list;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.enums.list.elementtypeoperations.ElementTypeOperationsListEnumInterface;

public enum ParameterTypeListEnum implements ElementTypeOperationsListEnumInterface{	
	
	STRING_PARAMETER( 0, CommonOperations.getTranslation("editor.label.variable.parametertype.string"), new Class<?>[]{String.class}),
	RANDOM_STRING_PARAMETER( 1, CommonOperations.getTranslation("editor.label.variable.parametertype.randomstring"), new Class<?>[]{String.class, Integer.class}),
	RANDOM_INTEGER_PARAMETER( 2, CommonOperations.getTranslation("editor.label.variable.parametertype.randominteger"), new Class<?>[]{Integer.class, Integer.class}),
	RANDOM_DOUBLE_PARAMETER( 3, CommonOperations.getTranslation("editor.label.variable.parametertype.randomdouble"), new Class<?>[]{Double.class, Double.class, Integer.class}),
	INTEGER_PARAMETER( 4, CommonOperations.getTranslation("editor.label.variable.parametertype.integer"), new Class<?>[]{Integer.class}),
	RANDOM_DATE_PARAMETER( 5, CommonOperations.getTranslation("editor.label.variable.parametertype.randomdate"), new Class<?>[]{String.class, String.class, String.class, String.class }),
	TODAY_DATE_PARAMETER( 6, CommonOperations.getTranslation("editor.label.variable.parametertype.todaydate"), new Class<?>[]{String.class, String.class }),
	;
	
	private int index;
	private String translatedName;
	private ArrayList<Class<?>> parameterClassList;
	
	private ParameterTypeListEnum( int index, String translatedName, Class<?>[] parameterClassList ){
		this.index = index;
		this.translatedName = translatedName;	
		this.parameterClassList = new ArrayList<Class<?>>(Arrays.asList(parameterClassList));
	}
	
	public static int getSize(){
		return ParameterTypeListEnum.values().length;
	}
	
	@Override
	public int getIndex(){
		return index;
	}
	
	@Override
	public String getTranslatedName(){
		return translatedName;
	}
	
	/** 
	 * A parameterkent megadott parameter lista alapjan legyartja a tipusnak megfelelo valtozot
	 * 
	 * @param parameters
	 * @return
	 */
	public String getValue( ArrayList<Object> parameters ){
		
		if( this.equals( STRING_PARAMETER ) ){
			
			return (String)parameters.get(0);
		
		}else if( this.equals( INTEGER_PARAMETER ) ){
			
			return (String)parameters.get(0);
		
		}else if( this.equals( RANDOM_STRING_PARAMETER ) ){
			
			return CommonOperations.getRandomString( (String)parameters.get(0), (Integer)parameters.get(1) );
			
		}else if( this.equals( RANDOM_INTEGER_PARAMETER ) ){
			
			return CommonOperations.getRandomStringIntegerRange( (Integer)parameters.get(0), (Integer)parameters.get(1) );
			
		}else if( this.equals( RANDOM_DOUBLE_PARAMETER ) ){
			
			return CommonOperations.getRandomStringDouble( (Double)parameters.get(0), (Double)parameters.get(1), (Integer)parameters.get(2));
			
		}else if( this.equals( RANDOM_DATE_PARAMETER ) ){
			SimpleDateFormat sdf = new SimpleDateFormat( (String)parameters.get(2) );
			Calendar fromCalendar = Calendar.getInstance();
			Calendar toCalendar = Calendar.getInstance();
				
				Date fromDate;
				try {
					fromDate = sdf.parse( (String)parameters.get(0) );
					Date toDate = sdf.parse( (String)parameters.get(1));
					fromCalendar.setTime(fromDate);
					toCalendar.setTime(toDate);				
				} catch (ParseException e) {
					//e.printStackTrace();
				}				
				return CommonOperations.getRandomStringDate( fromCalendar, toCalendar, sdf );
			
		}else if( this.equals( TODAY_DATE_PARAMETER ) ){
			SimpleDateFormat sdf = new SimpleDateFormat( (String)parameters.get(0) );
			Calendar calendar = GregorianCalendar.getInstance();
			return sdf.format( calendar );
		
		}
	
		return null;
	}
	
	public Class<?> getParameterClass( int index ){
		return parameterClassList.get(index);
	}
	
	public int getParameterLength(){
		return parameterClassList.size();
	}
	
	public static ParameterTypeListEnum getVariableParameterTypeByIndex( int index ){
		switch(index){
		case 0: return STRING_PARAMETER;		
		case 1: return RANDOM_STRING_PARAMETER;
		case 2: return RANDOM_INTEGER_PARAMETER;
		case 3: return RANDOM_DOUBLE_PARAMETER;
		case 4: return INTEGER_PARAMETER;
		case 5: return RANDOM_DATE_PARAMETER;
		case 6: return TODAY_DATE_PARAMETER;
		default: return STRING_PARAMETER;
		}
	}
}
