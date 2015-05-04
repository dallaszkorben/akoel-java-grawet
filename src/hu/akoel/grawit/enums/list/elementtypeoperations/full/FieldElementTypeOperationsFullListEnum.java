package hu.akoel.grawit.enums.list.elementtypeoperations.full;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.enums.list.elementtypeoperations.ElementTypeOperationsListEnumInterface;

public enum FieldElementTypeOperationsFullListEnum implements ElementTypeOperationsListEnumInterface{		
	FILL_CONSTANT( 0, CommonOperations.getTranslation( "editor.label.step.elementtype.field.fillconstant") ),
	FILL_ELEMENT( 1, CommonOperations.getTranslation( "editor.label.step.elementtype.field.fillelement") ),
	FILL_STRING( 2, CommonOperations.getTranslation( "editor.label.step.elementtype.field.fillstring") ),
	CLEAR( 3, CommonOperations.getTranslation( "editor.label.step.elementtype.field.clear") ),
	TAB( 4, CommonOperations.getTranslation( "editor.label.step.elementtype.field.tab") ),
	CLICK( 5, CommonOperations.getTranslation( "editor.label.step.elementtype.field.click") ),
	MOVE_TO_ELEMENT( 6, CommonOperations.getTranslation( "editor.label.step.elementtype.field.movetoelement") ),	
	COMPAREVALUE_TO_CONSTANT( 7, CommonOperations.getTranslation( "editor.label.step.elementtype.field.comparevaluetoconstant") ),
	COMPAREVALUE_TO_STORED( 8, CommonOperations.getTranslation( "editor.label.step.elementtype.field.comparevaluetostored") ),
	COMPAREVALUE_TO_STRING( 9, CommonOperations.getTranslation( "editor.label.step.elementtype.field.comparevaluetostring") ),
	GAINVALUE_TO_ELEMENTSTORAGE( 10, CommonOperations.getTranslation( "editor.label.step.elementtype.field.gainvaluetoelementstorage") ),
	OUTPUTSTORED( 11, CommonOperations.getTranslation( "editor.label.step.elementtype.field.outputstored") ),
	;
	
	private String translatedName;
	private int index;
	
	private FieldElementTypeOperationsFullListEnum( int index, String translatedName ){
	
		this.index = index;
		this.translatedName = translatedName;
	}
	
	@Override
	public String getTranslatedName(){
		return translatedName;
	}	
	
	public static int getSize(){
		return FieldElementTypeOperationsFullListEnum.values().length;
	}
	
	@Override
	public int getIndex(){
		return index;
	}
	
	public static FieldElementTypeOperationsFullListEnum getElementFieldOperationByIndex( int index ){
		switch (index){
		case 0:	return FILL_CONSTANT;
		case 1:	return FILL_ELEMENT;
		case 2:	return FILL_STRING;
		case 3: return CLEAR;
		case 4: return TAB;
		case 5: return CLICK;
		case 6: return MOVE_TO_ELEMENT;	
		case 7: return COMPAREVALUE_TO_CONSTANT;
		case 8: return COMPAREVALUE_TO_STORED;
		case 9: return COMPAREVALUE_TO_STRING;
		case 10: return GAINVALUE_TO_ELEMENTSTORAGE;
		case 11: return OUTPUTSTORED;
		default: return MOVE_TO_ELEMENT;
		}
	}
	
}
