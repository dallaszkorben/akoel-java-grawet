package hu.akoel.grawit.enums.list.elementtypeoperations;

import hu.akoel.grawit.CommonOperations;

public enum FieldElementTypeOperationsListEnum implements ElementTypeOperationsListEnumInterface{		
	FILL_VARIABLE( 0, CommonOperations.getTranslation( "editor.label.param.elementtype.field.fillvariable") ),
	FILL_ELEMENT( 1, CommonOperations.getTranslation( "editor.label.param.elementtype.field.fillelement") ),
	FILL_STRING( 2, CommonOperations.getTranslation( "editor.label.param.elementtype.field.fillstring") ),
	CLEAR( 3, CommonOperations.getTranslation( "editor.label.param.elementtype.field.clear") ),
	TAB( 4, CommonOperations.getTranslation( "editor.label.param.elementtype.field.tab") ),
	CLICK( 5, CommonOperations.getTranslation( "editor.label.param.elementtype.field.click") ),	
	COMPAREVALUE_TO_VARIABLE( 6, CommonOperations.getTranslation( "editor.label.param.elementtype.field.comparevaluetovariable") ),
	COMPAREVALUE_TO_STORED( 7, CommonOperations.getTranslation( "editor.label.param.elementtype.field.comparevaluetostored") ),
	COMPAREVALUE_TO_STRING( 8, CommonOperations.getTranslation( "editor.label.param.elementtype.field.comparevaluetostring") ),
	GAINVALUE_TO_ELEMENTSTORAGE( 9, CommonOperations.getTranslation( "editor.label.param.elementtype.field.gainvaluetoelementstorage") ),
	OUTPUTSTORED( 10, CommonOperations.getTranslation( "editor.label.param.elementtype.field.outputstored") ),
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
		case 6: return COMPAREVALUE_TO_VARIABLE;
		case 7: return COMPAREVALUE_TO_STORED;
		case 8: return COMPAREVALUE_TO_STRING;
		case 9: return GAINVALUE_TO_ELEMENTSTORAGE;
		case 10: return OUTPUTSTORED;
		default: return FILL_STRING;
		}
	}
	
}
