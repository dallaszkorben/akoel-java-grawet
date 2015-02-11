package hu.akoel.grawit.enums.list.elementtypeoperations.full;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.enums.list.elementtypeoperations.ElementTypeOperationsListEnumInterface;

public enum RadiobuttonElementTypeOperationsFullListEnum implements ElementTypeOperationsListEnumInterface{		
	CLICK( 0, CommonOperations.getTranslation( "editor.label.step.elementtype.radiobutton.click") ),
	COMPAREVALUE_TO_VARIABLE( 1, CommonOperations.getTranslation( "editor.label.step.elementtype.radiobutton.comparevaluetovariable") ),
	COMPAREVALUE_TO_STORED( 2, CommonOperations.getTranslation( "editor.label.step.elementtype.radiobutton.comparevaluetostored") ),
	COMPAREVALUE_TO_STRING( 3, CommonOperations.getTranslation( "editor.label.step.elementtype.radiobutton.comparevaluetostring") ),
//	GAINVALUE_TO_VARIABLE( 4, CommonOperations.getTranslation( "editor.label.step.elementtype.radiobutton.gainvaluetovariable") ),
	GAINVALUE_TO_ELEMENTSTORAGE( 4, CommonOperations.getTranslation( "editor.label.step.elementtype.radiobutton.gainvaluetoelement") ),
	OUTPUTSTORED( 5, CommonOperations.getTranslation( "editor.label.step.elementtype.radiobutton.outputstored") ),
	;
	
	private String translatedName;
	private int index;
	
	private RadiobuttonElementTypeOperationsFullListEnum( int index, String translatedName ){
	
		this.index = index;
		this.translatedName = translatedName;
	}
	
	@Override
	public String getTranslatedName(){
		return translatedName;
	}	
	
	public static int getSize(){
		return RadiobuttonElementTypeOperationsFullListEnum.values().length;
	}
	
	@Override
	public int getIndex(){
		return index;
	}
	
	public static RadiobuttonElementTypeOperationsFullListEnum getElementRadiobuttonOperationByIndex( int index ){
		switch (index){
		case 0:	return CLICK;
		case 1:	return COMPAREVALUE_TO_VARIABLE;
		case 2:	return COMPAREVALUE_TO_STORED;
		case 3:	return COMPAREVALUE_TO_STRING;
//		case 4:	return GAINVALUE_TO_VARIABLE;
		case 4:	return GAINVALUE_TO_ELEMENTSTORAGE;
		case 5:	return OUTPUTSTORED;
		default: return CLICK;
		}
	}
	
}
