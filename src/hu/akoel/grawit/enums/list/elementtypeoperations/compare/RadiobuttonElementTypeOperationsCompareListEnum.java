package hu.akoel.grawit.enums.list.elementtypeoperations.compare;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.enums.list.elementtypeoperations.ElementTypeOperationsListEnumInterface;

public enum RadiobuttonElementTypeOperationsCompareListEnum implements ElementTypeOperationsListEnumInterface{		
	COMPAREVALUE_TO_VARIABLE( 0, CommonOperations.getTranslation( "editor.label.param.elementtype.radiobutton.comparevaluetovariable") ),
	COMPAREVALUE_TO_STORED( 1, CommonOperations.getTranslation( "editor.label.param.elementtype.radiobutton.comparevaluetostored") ),
	COMPAREVALUE_TO_STRING( 2, CommonOperations.getTranslation( "editor.label.param.elementtype.radiobutton.comparevaluetostring") ),
	;
	
	private String translatedName;
	private int index;
	
	private RadiobuttonElementTypeOperationsCompareListEnum( int index, String translatedName ){
	
		this.index = index;
		this.translatedName = translatedName;
	}
	
	@Override
	public String getTranslatedName(){
		return translatedName;
	}	
	
	public static int getSize(){
		return RadiobuttonElementTypeOperationsCompareListEnum.values().length;
	}
	
	@Override
	public int getIndex(){
		return index;
	}
	
	public static RadiobuttonElementTypeOperationsCompareListEnum getElementRadiobuttonOperationByIndex( int index ){
		switch (index){
		case 0:	return COMPAREVALUE_TO_VARIABLE;
		case 1:	return COMPAREVALUE_TO_STORED;
		case 2:	return COMPAREVALUE_TO_STRING;
		default: return COMPAREVALUE_TO_STRING;
		}
	}
	
}
