package hu.akoel.grawit.enums.list.elementtypeoperations;

import hu.akoel.grawit.CommonOperations;

public enum LinkElementTypeOperationsListEnum implements ElementTypeOperationsListEnumInterface{		
	CLICK( 0, CommonOperations.getTranslation( "editor.label.param.elementtype.link.click") ),
	GAINTEXTPATTERN( 1, CommonOperations.getTranslation( "editor.label.param.elementtype.link.gaintextpattern") ),	
	OUTPUTVALUE( 2, CommonOperations.getTranslation( "editor.label.param.elementtype.link.outputvalue") ),	
	COMPARE_VARIABLE( 3, CommonOperations.getTranslation( "editor.label.param.elementtype.link.comparevariable") ),
	COMPARE_ELEMENT( 4, CommonOperations.getTranslation( "editor.label.param.elementtype.link.compareelement") ),
	COMPARE_STRING( 5, CommonOperations.getTranslation( "editor.label.param.elementtype.link.comparestring") ),
	GAINTEXT_TO_VARIABLE( 6, CommonOperations.getTranslation( "editor.label.param.elementtype.link.gaintexttovariable") ),
	GAINTEXT_TO_ELEMENT( 6, CommonOperations.getTranslation( "editor.label.param.elementtype.link.gaintexttoelement") ),
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
		case 1:	return GAINTEXTPATTERN;
		case 2:	return OUTPUTVALUE;
		case 3: return COMPARE_VARIABLE;
		case 4: return COMPARE_ELEMENT;
		case 5: return COMPARE_STRING;
		case 6: return GAINTEXT_TO_VARIABLE;
		case 7: return GAINTEXT_TO_ELEMENT;
		default: return CLICK;
		}
	}
	
}
