package hu.akoel.grawit.enums.list.elementtypeoperations.full;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.enums.list.elementtypeoperations.ElementTypeOperationsListEnumInterface;

public enum TextElementTypeOperationsFullListEnum implements ElementTypeOperationsListEnumInterface{		
	COMPARETEXT_TO_VARIABLE( 0, CommonOperations.getTranslation( "editor.label.step.elementtype.text.comparetexttovariable") ),
	COMPARETEXT_TO_STORED( 1, CommonOperations.getTranslation( "editor.label.step.elementtype.text.comparetexttostored") ),
	COMPARETEXT_TO_STRING( 2, CommonOperations.getTranslation( "editor.label.step.elementtype.text.comparetexttostring") ),
	LEFT_CLICK( 3, CommonOperations.getTranslation( "editor.label.step.elementtype.text.leftclick") ),
	RIGHT_CLICK( 4, CommonOperations.getTranslation( "editor.label.step.elementtype.text.rightclick") ),
	GAINTEXT_TO_ELEMENT( 5, CommonOperations.getTranslation( "editor.label.step.elementtype.text.gainvaluetoelement") ),	
	OUTPUTSTORED( 6, CommonOperations.getTranslation( "editor.label.step.elementtype.text.outputstored") ),
	;
	
	private String translatedName;
	private int index;
	
	private TextElementTypeOperationsFullListEnum( int index, String translatedName ){
	
		this.index = index;
		this.translatedName = translatedName;
	}
	
	@Override
	public String getTranslatedName(){
		return translatedName;
	}	
	
	public static int getSize(){
		return TextElementTypeOperationsFullListEnum.values().length;
	}
	
	@Override
	public int getIndex(){
		return index;
	}
	
	public static TextElementTypeOperationsFullListEnum getElementTextOperationByIndex( int index ){
		switch (index){		
		case 0: return COMPARETEXT_TO_VARIABLE;
		case 1: return COMPARETEXT_TO_STORED;
		case 2: return COMPARETEXT_TO_STRING;
		case 3: return LEFT_CLICK;
		case 4: return RIGHT_CLICK;		
		case 5: return GAINTEXT_TO_ELEMENT;
		case 6:	return OUTPUTSTORED;
		default: return OUTPUTSTORED;
		}
	}
	
}
