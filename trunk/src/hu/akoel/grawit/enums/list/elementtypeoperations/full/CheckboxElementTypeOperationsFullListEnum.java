package hu.akoel.grawit.enums.list.elementtypeoperations.full;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.enums.list.elementtypeoperations.ElementTypeOperationsListEnumInterface;

public enum CheckboxElementTypeOperationsFullListEnum implements ElementTypeOperationsListEnumInterface{		
	CLICK( 0, CommonOperations.getTranslation( "editor.label.step.elementtype.checkbox.click") ),	
	COMPAREVALUE_TO_CONSTANT( 1, CommonOperations.getTranslation( "editor.label.step.elementtype.checkbox.comparevaluetoconstant") ),
	COMPAREVALUE_TO_STORED( 2, CommonOperations.getTranslation( "editor.label.step.elementtype.checkbox.comparevaluetostored") ),
	COMPAREVALUE_TO_STRING( 3, CommonOperations.getTranslation( "editor.label.step.elementtype.checkbox.comparevaluetostring") ),
	GAINVALUE_TO_ELEMENTSTORAGE( 4, CommonOperations.getTranslation( "editor.label.step.elementtype.checkbox.gainvaluetoelementstorage") ),
	OUTPUTSTORED( 5, CommonOperations.getTranslation( "editor.label.step.elementtype.checkbox.outputstored") ),
	;
	
	private String translatedName;
	private int index;
	
	private CheckboxElementTypeOperationsFullListEnum( int index, String translatedName ){
	
		this.index = index;
		this.translatedName = translatedName;
	}
	
	@Override
	public String getTranslatedName(){
		return translatedName;
	}	
	
	public static int getSize(){
		return CheckboxElementTypeOperationsFullListEnum.values().length;
	}
	
	@Override
	public int getIndex(){
		return index;
	}
	
	public static CheckboxElementTypeOperationsFullListEnum getElementCheckboxOperationByIndex( int index ){
		switch (index){
		case 0:	return CLICK;
		case 1:	return COMPAREVALUE_TO_CONSTANT;
		case 2:	return COMPAREVALUE_TO_STORED;
		case 3:	return COMPAREVALUE_TO_STRING;
//		case 4:	return GAINVALUE_TO_VARIABLE;
		case 4:	return GAINVALUE_TO_ELEMENTSTORAGE;
		case 5:	return OUTPUTSTORED;
		default: return CLICK;
		}
	}
	
}
