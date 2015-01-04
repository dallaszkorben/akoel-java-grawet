package hu.akoel.grawit.enums.list.elementtypeoperations.compare;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.enums.list.elementtypeoperations.ElementTypeOperationsListEnumInterface;

public enum LinkElementTypeOperationsCompareListEnum implements ElementTypeOperationsListEnumInterface{		
	COMPARETEXT_TO_VARIABLE( 0, CommonOperations.getTranslation( "editor.label.param.elementtype.link.comparetexttovariable") ),
	COMPARETEXT_TO_STORED( 1, CommonOperations.getTranslation( "editor.label.param.elementtype.link.comparetexttostored") ),
	COMPARETEXT_TO_STRING( 2, CommonOperations.getTranslation( "editor.label.param.elementtype.link.comparetexttostring") ),
	;
	
	private String translatedName;
	private int index;
	
	private LinkElementTypeOperationsCompareListEnum( int index, String translatedName ){
	
		this.index = index;
		this.translatedName = translatedName;
	}
	
	@Override
	public String getTranslatedName(){
		return translatedName;
	}	
	
	public static int getSize(){
		return LinkElementTypeOperationsCompareListEnum.values().length;
	}
	
	@Override
	public int getIndex(){
		return index;
	}
	
	public static LinkElementTypeOperationsCompareListEnum getElementFieldOperationByIndex( int index ){
		switch (index){
		case 0: return COMPARETEXT_TO_VARIABLE;
		case 1: return COMPARETEXT_TO_STORED;
		case 2: return COMPARETEXT_TO_STRING;
		default: return COMPARETEXT_TO_VARIABLE;
		}
	}
	
}
