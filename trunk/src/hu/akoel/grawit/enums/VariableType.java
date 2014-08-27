package hu.akoel.grawit.enums;

import java.util.ArrayList;
import java.util.Arrays;

import hu.akoel.grawit.CommonOperations;

public enum VariableType {	
	
	STRING_PARAMETER( 0, CommonOperations.getTranslation("editor.title.variabletype.string"), new Class<?>[]{String.class}),
	RANDOM_STRING_PARAMETER( 1, CommonOperations.getTranslation("editor.title.variabletype.randomstring"), new Class<?>[]{String.class, Integer.class}),
	RANDOM_INTEGER_PARAMETER( 2, CommonOperations.getTranslation("editor.title.variabletype.randominteger"), new Class<?>[]{Integer.class, Integer.class}),
	RANDOM_DOUBLE_PARAMETER( 3, CommonOperations.getTranslation("editor.title.variabletype.randomdouble"), new Class<?>[]{Double.class, Double.class, Integer.class});
	
	private int index;
	private String translatedName;
	private ArrayList<Class<?>> parameterClassList;
	
	private VariableType( int index, String translatedName, Class<?>[] parameterClassList ){
		this.index = index;
		this.translatedName = translatedName;	
		this.parameterClassList = new ArrayList<Class<?>>(Arrays.asList(parameterClassList));
	}
	
	public int getIndex(){
		return index;
	}
	
	public String getTranslatedName(){
		return translatedName;
	}
	
	public String getValue( ArrayList<Object> parameters ){
		
		if( this.equals( STRING_PARAMETER ) ){
			
			return (String)parameters.get(0);
		
		}else if( this.equals( RANDOM_STRING_PARAMETER ) ){
			
			CommonOperations.getRandomString( (String)parameters.get(0), (Integer)parameters.get(1) );
			
		}else if( this.equals( RANDOM_INTEGER_PARAMETER ) ){
			
			CommonOperations.getRandomStringIntegerRange( (Integer)parameters.get(0), (Integer)parameters.get(1) );
			
		}else if( this.equals( RANDOM_DOUBLE_PARAMETER ) ){
			
			CommonOperations.getRandomStringDouble( (Double)parameters.get(0), (Double)parameters.get(0), (Integer)parameters.get(0));
		}

//TODO ne felejtsd el folytatni		
		
		return null;
	}
	
	public Class<?> getParameterClass( int index ){
		return parameterClassList.get(index);
	}
	
	public int getParameterLength(){
		return parameterClassList.size();
	}
	
	public static VariableType getVariableParameterTypeByIndex( int index ){
		switch(index){
		case 0: return STRING_PARAMETER;
		case 1: return RANDOM_STRING_PARAMETER;
		case 2: return RANDOM_INTEGER_PARAMETER;
		case 3: return RANDOM_DOUBLE_PARAMETER;
		default: return STRING_PARAMETER;
		}
	}
}
