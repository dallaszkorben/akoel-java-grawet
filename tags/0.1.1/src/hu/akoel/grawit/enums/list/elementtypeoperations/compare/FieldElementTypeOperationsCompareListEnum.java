package hu.akoel.grawit.enums.list.elementtypeoperations.compare;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.enums.list.elementtypeoperations.ElementTypeOperationsListEnumInterface;

public enum FieldElementTypeOperationsCompareListEnum implements ElementTypeOperationsListEnumInterface{		
	COMPAREVALUE_TO_VARIABLE( 0, CommonOperations.getTranslation( "editor.label.param.elementtype.field.comparevaluetovariable") ),
	COMPAREVALUE_TO_STORED( 1, CommonOperations.getTranslation( "editor.label.param.elementtype.field.comparevaluetostored") ),
	COMPAREVALUE_TO_STRING( 2, CommonOperations.getTranslation( "editor.label.param.elementtype.field.comparevaluetostring") ),
	;
	
	private String translatedName;
	private int index;
	
	private FieldElementTypeOperationsCompareListEnum( int index, String translatedName ){
	
		this.index = index;
		this.translatedName = translatedName;
	}
	
	@Override
	public String getTranslatedName(){
		return translatedName;
	}	
	
	public static int getSize(){
		return FieldElementTypeOperationsCompareListEnum.values().length;
	}
	
	@Override
	public int getIndex(){
		return index;
	}
	
	public static FieldElementTypeOperationsCompareListEnum getElementFieldOperationByIndex( int index ){
		switch (index){
		case 0: return COMPAREVALUE_TO_VARIABLE;
		case 1: return COMPAREVALUE_TO_STORED;
		case 2: return COMPAREVALUE_TO_STRING;
		default: return COMPAREVALUE_TO_STRING;
		}
	}
	
}
