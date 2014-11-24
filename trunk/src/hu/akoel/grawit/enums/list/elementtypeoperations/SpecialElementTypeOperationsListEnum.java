package hu.akoel.grawit.enums.list.elementtypeoperations;

import hu.akoel.grawit.CommonOperations;

public enum SpecialElementTypeOperationsListEnum implements ElementTypeOperationsListEnumInterface{	
	ADD_VARIABLE_TO_PARAMETERS( 0, CommonOperations.getTranslation( "editor.label.param.elementtype.special.addvariabletoparameters") ),
	ADD_STORED_TO_PARAMETERS( 1, CommonOperations.getTranslation( "editor.label.param.elementtype.special.addstoredtoparameters") ),
	ADD_STRING_TO_PARAMETERS( 2, CommonOperations.getTranslation( "editor.label.param.elementtype.special.addstringtoparameters") ),
	CLEAR_PARAMETERS( 3, CommonOperations.getTranslation( "editor.label.param.elementtype.special.clearparameters") ),
	EXECUTE_SCRIPT( 4, CommonOperations.getTranslation( "editor.label.param.elementtype.special.executescript") ),
	;
	
	private String translatedName;
	private int index;
	
	private SpecialElementTypeOperationsListEnum( int index, String translatedName ){
	
		this.index = index;
		this.translatedName = translatedName;
	}
	
	@Override
	public String getTranslatedName(){
		return translatedName;
	}	
	
	public static int getSize(){
		return SpecialElementTypeOperationsListEnum.values().length;
	}
	
	@Override
	public int getIndex(){
		return index;
	}
	
	public static SpecialElementTypeOperationsListEnum getOperationByIndex( int index ){
		switch (index){		
		case 0:	return ADD_VARIABLE_TO_PARAMETERS;
		case 1:	return ADD_STORED_TO_PARAMETERS;
		case 2:	return ADD_STRING_TO_PARAMETERS;
		case 3:	return CLEAR_PARAMETERS;
		case 4:	return EXECUTE_SCRIPT;
		default: return CLEAR_PARAMETERS;
		}
	}
	
}
