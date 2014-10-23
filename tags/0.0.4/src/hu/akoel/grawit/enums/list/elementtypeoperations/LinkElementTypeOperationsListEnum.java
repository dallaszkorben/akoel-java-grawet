package hu.akoel.grawit.enums.list.elementtypeoperations;

import hu.akoel.grawit.CommonOperations;

public enum LinkElementTypeOperationsListEnum implements ElementTypeOperationsListEnumInterface{		
	CLICK( 0, CommonOperations.getTranslation( "editor.label.param.elementtype.link.click") ),
	COMPARETEXT_TO_VARIABLE( 1, CommonOperations.getTranslation( "editor.label.param.elementtype.link.comparetexttovariable") ),
	COMPARETEXT_TO_STORED( 2, CommonOperations.getTranslation( "editor.label.param.elementtype.link.comparetexttostored") ),
	COMPARETEXT_TO_STRING( 3, CommonOperations.getTranslation( "editor.label.param.elementtype.link.comparetexttostring") ),
	GAINTEXT_TO_VARIABLE( 4, CommonOperations.getTranslation( "editor.label.param.elementtype.link.gaintexttovariable") ),
	GAINTEXT_TO_ELEMENT( 5, CommonOperations.getTranslation( "editor.label.param.elementtype.link.gaintexttoelement") ),
	OUTPUTSTORED( 6, CommonOperations.getTranslation( "editor.label.param.elementtype.link.outputstored") ),	
	;
	
	private String translatedName;
	private int index;
	
	private LinkElementTypeOperationsListEnum( int index, String translatedName ){
	
		this.index = index;
		this.translatedName = translatedName;
	}
	
	@Override
	public String getTranslatedName(){
		return translatedName;
	}	
	
	public static int getSize(){
		return LinkElementTypeOperationsListEnum.values().length;
	}
	
	@Override
	public int getIndex(){
		return index;
	}
	
	public static LinkElementTypeOperationsListEnum getElementFieldOperationByIndex( int index ){
		switch (index){
		case 0:	return CLICK;
		case 1: return COMPARETEXT_TO_VARIABLE;
		case 2: return COMPARETEXT_TO_STORED;
		case 3: return COMPARETEXT_TO_STRING;
		case 4: return GAINTEXT_TO_VARIABLE;
		case 5: return GAINTEXT_TO_ELEMENT;
		case 6:	return OUTPUTSTORED;
		default: return CLICK;
		}
	}
	
}
