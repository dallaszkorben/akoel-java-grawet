package hu.akoel.grawit.enums;

import hu.akoel.grawit.CommonOperations;

public enum VariableType {

	STRING_PARAMETER( 0, CommonOperations.getTranslation("editor.title.variabletype.string")),
	RANDOM_STRING_PARAMETER( 1, CommonOperations.getTranslation("editor.title.variabletype.randomstring")),
	RANDOM_INTEGER_PARAMETER( 2, CommonOperations.getTranslation("editor.title.variabletype.randominteger")),
	RANDOM_DECIMAL_PARAMETER( 3, CommonOperations.getTranslation("editor.title.variabletype.randomdecimal"));
	
	private int index;
	private String translatedName;
	
	private VariableType( int index, String translatedName ){
		this.index = index;
		this.translatedName = translatedName;		
	}
	
	public int getIndex(){
		return index;
	}
	
	public String getTranslatedName(){
		return translatedName;
	}
	
	public static VariableType getVariableParameterTypeByIndex( int index ){
		switch(index){
		case 0: return STRING_PARAMETER;
		case 1: return RANDOM_STRING_PARAMETER;
		case 2: return RANDOM_INTEGER_PARAMETER;
		case 3: return RANDOM_DECIMAL_PARAMETER;
		default: return STRING_PARAMETER;
		}
	}
}
