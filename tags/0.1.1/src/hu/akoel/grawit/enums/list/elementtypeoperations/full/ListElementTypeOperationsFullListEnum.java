package hu.akoel.grawit.enums.list.elementtypeoperations.full;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.enums.list.elementtypeoperations.ElementTypeOperationsListEnumInterface;

public enum ListElementTypeOperationsFullListEnum implements ElementTypeOperationsListEnumInterface{		
	SELECT_VARIABLE( 0, CommonOperations.getTranslation( "editor.label.param.elementtype.list.selectvariable") ),
	SELECT_BASE( 1, CommonOperations.getTranslation( "editor.label.param.elementtype.list.selectbase") ),
	SELECT_STRING( 2, CommonOperations.getTranslation( "editor.label.param.elementtype.list.selectstring") ),
	TAB( 3, CommonOperations.getTranslation( "editor.label.param.elementtype.list.tab") ),
	CLICK( 4, CommonOperations.getTranslation( "editor.label.param.elementtype.list.click") ),
	COMPARE_TO_VARIABLE( 5, CommonOperations.getTranslation( "editor.label.param.elementtype.list.comparetovariable") ),
	COMPARE_TO_STORED( 6, CommonOperations.getTranslation( "editor.label.param.elementtype.list.comparetostored") ),
	COMPARE_TO_STRING( 7, CommonOperations.getTranslation( "editor.label.param.elementtype.list.comparetostring") ),
//	GAIN_TO_VARIABLE( 8, CommonOperations.getTranslation( "editor.label.param.elementtype.list.gaintovariable") ),
	GAIN_TO_ELEMENT( 8, CommonOperations.getTranslation( "editor.label.param.elementtype.list.gaintoelement") ),
	OUTPUTSTORED( 9, CommonOperations.getTranslation( "editor.label.param.elementtype.list.outputstored") ),	
	;
	
	private String translatedName;
	private int index;
	
	private ListElementTypeOperationsFullListEnum( int index, String translatedName ){
	
		this.index = index;
		this.translatedName = translatedName;
	}
	
	@Override
	public String getTranslatedName(){
		return translatedName;
	}	
	
	public static int getSize(){
		return ListElementTypeOperationsFullListEnum.values().length;
	}
	
	@Override
	public int getIndex(){
		return index;
	}
	
	public static ListElementTypeOperationsFullListEnum getElementFieldOperationByIndex( int index ){
		switch (index){
		case 0:	return SELECT_VARIABLE;
		case 1:	return SELECT_BASE;
		case 2:	return SELECT_STRING;
		case 3: return TAB;
		case 4: return CLICK;
		case 5: return COMPARE_TO_VARIABLE;
		case 6: return COMPARE_TO_STORED;
		case 7: return COMPARE_TO_STRING;
//		case 8: return GAIN_TO_VARIABLE;
		case 8: return GAIN_TO_ELEMENT;	
		case 9: return OUTPUTSTORED;
		default: return CLICK;		
		
		}
	}
	
}
