package hu.akoel.grawit.enums.list.elementtypeoperations;

import hu.akoel.grawit.CommonOperations;

public enum ListElementTypeOperationsListEnum implements ElementTypeOperationsListEnumInterface{		
	SELECT_VARIABLE( 0, CommonOperations.getTranslation( "editor.label.param.elementtype.list.selectvariable") ),
	SELECT_BASE( 1, CommonOperations.getTranslation( "editor.label.param.elementtype.list.selectbase") ),
	SELECT_STRING( 2, CommonOperations.getTranslation( "editor.label.param.elementtype.list.fillstring") ),
	TAB( 3, CommonOperations.getTranslation( "editor.label.param.elementtype.list.tab") ),
	CLICK( 4, CommonOperations.getTranslation( "editor.label.param.elementtype.list.click") ),
	GAINVALUE_TO_VARIABLE( 5, CommonOperations.getTranslation( "editor.label.param.elementtype.list.gainvaluetovariable") ),
	GAINTEXT_TO_VARIABLE( 6, CommonOperations.getTranslation( "editor.label.param.elementtype.list.gaintexttovariable") ),
	GAINVALUE_TO_ELEMENT( 7, CommonOperations.getTranslation( "editor.label.param.elementtype.list.gainvaluetoelement") ),
	GAINTEXT_TO_ELEMENT( 8, CommonOperations.getTranslation( "editor.label.param.elementtype.list.gaintexttoelement") ),
	OUTPUTGAINED( 9, CommonOperations.getTranslation( "editor.label.param.elementtype.list.outputgained") ),	
	;
	
	private String translatedName;
	private int index;
	
	private ListElementTypeOperationsListEnum( int index, String translatedName ){
	
		this.index = index;
		this.translatedName = translatedName;
	}
	
	@Override
	public String getTranslatedName(){
		return translatedName;
	}	
	
	public static int getSize(){
		return ListElementTypeOperationsListEnum.values().length;
	}
	
	@Override
	public int getIndex(){
		return index;
	}
	
	public static ListElementTypeOperationsListEnum getElementFieldOperationByIndex( int index ){
		switch (index){
		case 0:	return SELECT_VARIABLE;
		case 1:	return SELECT_BASE;
		case 2:	return SELECT_STRING;
		case 3: return TAB;
		case 4: return CLICK;
		case 5: return GAINVALUE_TO_VARIABLE;
		case 6: return GAINTEXT_TO_VARIABLE;	
		case 7: return GAINVALUE_TO_ELEMENT;
		case 8: return GAINTEXT_TO_ELEMENT;	
		case 9: return OUTPUTGAINED;
		default: return CLICK;		
		
		}
	}
	
}
