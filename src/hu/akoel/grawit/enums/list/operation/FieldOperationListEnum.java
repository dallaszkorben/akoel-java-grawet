package hu.akoel.grawit.enums.list.operation;

import hu.akoel.grawit.CommonOperations;

public enum FieldOperationListEnum implements ListEnumInterface{		
	FILL_VARIABLE( 0, CommonOperations.getTranslation( "editor.label.param.elementtype.field.fillvariable") ),
	FILL_ELEMENT( 1, CommonOperations.getTranslation( "editor.label.param.elementtype.field.fillelement") ),
	FILL_STRING( 2, CommonOperations.getTranslation( "editor.label.param.elementtype.field.fillstring") ),
	CLEAR( 3, CommonOperations.getTranslation( "editor.label.param.elementtype.field.clear") ),
	TAB( 4, CommonOperations.getTranslation( "editor.label.param.elementtype.field.tab") ),
	CLICK( 5, CommonOperations.getTranslation( "editor.label.param.elementtype.field.click") ),
	;
	
	private String translatedName;
	private int index;
	
	private FieldOperationListEnum( int index, String translatedName ){
	
		this.index = index;
		this.translatedName = translatedName;
	}
	
	@Override
	public String getTranslatedName(){
		return translatedName;
	}	
	
	public static int getSize(){
		return FieldOperationListEnum.values().length;
	}
	
	@Override
	public int getIndex(){
		return index;
	}
	
	public static FieldOperationListEnum getElementFieldOperationByIndex( int index ){
		switch (index){
		case 0:	return FILL_VARIABLE;
		case 1:	return FILL_ELEMENT;
		case 2:	return FILL_STRING;
		case 3: return CLEAR;
		case 4: return TAB;
		case 5: return CLICK;
		default: return FILL_STRING;
		}
	}
	
}
