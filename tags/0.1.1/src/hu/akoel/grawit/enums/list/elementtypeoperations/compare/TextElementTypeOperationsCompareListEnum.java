package hu.akoel.grawit.enums.list.elementtypeoperations.compare;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.enums.list.elementtypeoperations.ElementTypeOperationsListEnumInterface;

public enum TextElementTypeOperationsCompareListEnum implements ElementTypeOperationsListEnumInterface{		
	COMPARETEXT_TO_VARIABLE( 0, CommonOperations.getTranslation( "editor.label.param.elementtype.text.comparetexttovariable") ),
	COMPARETEXT_TO_STORED( 1, CommonOperations.getTranslation( "editor.label.param.elementtype.text.comparetexttostored") ),
	COMPARETEXT_TO_STRING( 2, CommonOperations.getTranslation( "editor.label.param.elementtype.text.comparetexttostring") ),	
	;
	
	private String translatedName;
	private int index;
	
	private TextElementTypeOperationsCompareListEnum( int index, String translatedName ){
	
		this.index = index;
		this.translatedName = translatedName;
	}
	
	@Override
	public String getTranslatedName(){
		return translatedName;
	}	
	
	public static int getSize(){
		return TextElementTypeOperationsCompareListEnum.values().length;
	}
	
	@Override
	public int getIndex(){
		return index;
	}
	
	public static TextElementTypeOperationsCompareListEnum getElementTextOperationByIndex( int index ){
		switch (index){		
		case 0: return COMPARETEXT_TO_VARIABLE;
		case 1: return COMPARETEXT_TO_STORED;
		case 2: return COMPARETEXT_TO_STRING;
		default: return COMPARETEXT_TO_STRING;
		}
	}
	
}
