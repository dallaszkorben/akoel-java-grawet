package hu.akoel.grawit.enums.list.elementtypeoperations;

import hu.akoel.grawit.CommonOperations;

public enum TextElementTypeOperationsListEnum implements ElementTypeOperationsListEnumInterface{		
	COMPARETEXT_TO_VARIABLE( 0, CommonOperations.getTranslation( "editor.label.param.elementtype.text.comparetexttovariable") ),
	COMPARETEXT_TO_STORED( 1, CommonOperations.getTranslation( "editor.label.param.elementtype.text.comparetexttostored") ),
	COMPARETEXT_TO_STRING( 2, CommonOperations.getTranslation( "editor.label.param.elementtype.text.comparetexttostring") ),
	GAINTEXT_TO_ELEMENT( 3, CommonOperations.getTranslation( "editor.label.param.elementtype.text.gainvaluetoelement") ),
	OUTPUTSTORED( 4, CommonOperations.getTranslation( "editor.label.param.elementtype.text.outputstored") ),
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
		case 0: return COMPARETEXT_TO_VARIABLE;
		case 1: return COMPARETEXT_TO_STORED;
		case 2: return COMPARETEXT_TO_STRING;
		case 3: return GAINTEXT_TO_ELEMENT;
		case 4:	return OUTPUTSTORED;
		default: return OUTPUTSTORED;
		}
	}
	
}
