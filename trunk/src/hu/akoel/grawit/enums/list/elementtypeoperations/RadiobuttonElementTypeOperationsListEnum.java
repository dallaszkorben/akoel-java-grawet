package hu.akoel.grawit.enums.list.elementtypeoperations;

import hu.akoel.grawit.CommonOperations;

public enum RadiobuttonElementTypeOperationsListEnum implements ElementTypeOperationsListEnumInterface{		
	CLICK( 0, CommonOperations.getTranslation( "editor.label.param.elementtype.radiobutton.click") ),
	COMPAREVALUE_TO_VARIABLE( 1, CommonOperations.getTranslation( "editor.label.param.elementtype.radiobutton.comparevaluetovariable") ),
	COMPAREVALUE_TO_GAINED( 2, CommonOperations.getTranslation( "editor.label.param.elementtype.radiobutton.comparevaluetogained") ),
	COMPAREVALUE_TO_STRING( 3, CommonOperations.getTranslation( "editor.label.param.elementtype.radiobutton.comparevaluetostring") ),
	GAINVALUE_TO_VARIABLE( 4, CommonOperations.getTranslation( "editor.label.param.elementtype.radiobutton.gainvaluetovariable") ),
	GAINVALUE_TO_ELEMENT( 5, CommonOperations.getTranslation( "editor.label.param.elementtype.radiobutton.gainvaluetoelement") ),
	OUTPUTGAINED( 6, CommonOperations.getTranslation( "editor.label.param.elementtype.radiobutton.outputgained") ),
	;
	
	private String translatedName;
	private int index;
	
	private RadiobuttonElementTypeOperationsListEnum( int index, String translatedName ){
	
		this.index = index;
		this.translatedName = translatedName;
	}
	
	@Override
	public String getTranslatedName(){
		return translatedName;
	}	
	
	public static int getSize(){
		return RadiobuttonElementTypeOperationsListEnum.values().length;
	}
	
	@Override
	public int getIndex(){
		return index;
	}
	
	public static RadiobuttonElementTypeOperationsListEnum getElementRadiobuttonOperationByIndex( int index ){
		switch (index){
		case 0:	return CLICK;
		case 1:	return COMPAREVALUE_TO_VARIABLE;
		case 2:	return COMPAREVALUE_TO_GAINED;
		case 3:	return COMPAREVALUE_TO_STRING;
		case 4:	return GAINVALUE_TO_VARIABLE;
		case 5:	return GAINVALUE_TO_ELEMENT;
		case 6:	return OUTPUTGAINED;
		default: return CLICK;
		}
	}
	
}
