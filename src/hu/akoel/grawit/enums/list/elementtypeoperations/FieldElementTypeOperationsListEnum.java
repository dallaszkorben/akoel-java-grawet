package hu.akoel.grawit.enums.list.elementtypeoperations;

import hu.akoel.grawit.CommonOperations;

public enum FieldElementTypeOperationsListEnum implements ElementTypeOperationsListEnumInterface{		
	FILL_VARIABLE( 0, CommonOperations.getTranslation( "editor.label.param.elementtype.field.fillvariable") ),
	FILL_ELEMENT( 1, CommonOperations.getTranslation( "editor.label.param.elementtype.field.fillelement") ),
	FILL_STRING( 2, CommonOperations.getTranslation( "editor.label.param.elementtype.field.fillstring") ),
	CLEAR( 3, CommonOperations.getTranslation( "editor.label.param.elementtype.field.clear") ),
	TAB( 4, CommonOperations.getTranslation( "editor.label.param.elementtype.field.tab") ),
	CLICK( 5, CommonOperations.getTranslation( "editor.label.param.elementtype.field.click") ),
	OUTPUTVALUE( 6, CommonOperations.getTranslation( "editor.label.param.elementtype.field.outputvalue") ),
	COMPARE_VARIABLE( 7, CommonOperations.getTranslation( "editor.label.param.elementtype.field.comparevariable") ),
	COMPARE_ELEMENT( 8, CommonOperations.getTranslation( "editor.label.param.elementtype.field.compareelement") ),
	COMPARE_STRING( 9, CommonOperations.getTranslation( "editor.label.param.elementtype.field.comparestring") ),
	;
	
	private String translatedName;
	private int index;
	
	private FieldElementTypeOperationsListEnum( int index, String translatedName ){
	
		this.index = index;
		this.translatedName = translatedName;
	}
	
	@Override
	public String getTranslatedName(){
		return translatedName;
	}	
	
	public static int getSize(){
		return FieldElementTypeOperationsListEnum.values().length;
	}
	
	@Override
	public int getIndex(){
		return index;
	}
	
	public static FieldElementTypeOperationsListEnum getElementFieldOperationByIndex( int index ){
		switch (index){
		case 0:	return FILL_VARIABLE;
		case 1:	return FILL_ELEMENT;
		case 2:	return FILL_STRING;
		case 3: return CLEAR;
		case 4: return TAB;
		case 5: return CLICK;
		case 6: return OUTPUTVALUE;
		case 7: return COMPARE_VARIABLE;
		case 8: return COMPARE_ELEMENT;
		case 9: return COMPARE_STRING;
		default: return FILL_STRING;
		}
	}
	
}
