package hu.akoel.grawit.enums.list.elementtypeoperations;

import hu.akoel.grawit.CommonOperations;

public enum TextElementTypeOperationsListEnum implements ElementTypeOperationsListEnumInterface{		
	GAINTEXT_TO_VARIABLE( 0, CommonOperations.getTranslation( "editor.label.param.elementtype.text.gaintexttovariable") ),
	GAINTEXT_TO_ELEMENT( 1, CommonOperations.getTranslation( "editor.label.param.elementtype.text.gainvaluetoelement") ),
	OUTPUTGAINED( 2, CommonOperations.getTranslation( "editor.label.param.elementtype.text.outputgained") ),
	COMPARE_VARIABLE( 3, CommonOperations.getTranslation( "editor.label.param.elementtype.text.comparevariable") ),
	COMPARE_ELEMENT( 4, CommonOperations.getTranslation( "editor.label.param.elementtype.text.compareelement") ),
	COMPARE_STRING( 5, CommonOperations.getTranslation( "editor.label.param.elementtype.text.comparestring") ),	
	;
	
	private String translatedName;
	private int index;
	
	private TextElementTypeOperationsListEnum( int index, String translatedName ){
	
		this.index = index;
		this.translatedName = translatedName;
	}
	
	@Override
	public String getTranslatedName(){
		return translatedName;
	}	
	
	public static int getSize(){
		return TextElementTypeOperationsListEnum.values().length;
	}
	
	@Override
	public int getIndex(){
		return index;
	}
	
	public static TextElementTypeOperationsListEnum getElementTextOperationByIndex( int index ){
		switch (index){		
		case 0: return GAINTEXT_TO_VARIABLE;
		case 1: return GAINTEXT_TO_ELEMENT;
		case 3:	return OUTPUTGAINED;
		case 4: return COMPARE_VARIABLE;
		case 5: return COMPARE_ELEMENT;
		case 6: return COMPARE_STRING;		
		default: return OUTPUTGAINED;
		}
	}
	
}
