package hu.akoel.grawit.enums.list.elementtypeoperations.full;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.enums.list.elementtypeoperations.ElementTypeOperationsListEnumInterface;

public enum ListElementTypeOperationsFullListEnum implements ElementTypeOperationsListEnumInterface{		
	SELECT_CONSTANT( 0, CommonOperations.getTranslation( "editor.label.step.elementtype.list.selectconstant") ),
	SELECT_BASE( 1, CommonOperations.getTranslation( "editor.label.step.elementtype.list.selectbase") ),
	SELECT_STRING( 2, CommonOperations.getTranslation( "editor.label.step.elementtype.list.selectstring") ),
	TAB( 3, CommonOperations.getTranslation( "editor.label.step.elementtype.list.tab") ),
	CLICK( 4, CommonOperations.getTranslation( "editor.label.step.elementtype.list.click") ),
	MOVE_TO_ELEMENT( 5, CommonOperations.getTranslation( "editor.label.step.elementtype.list.movetoelement") ),
	COMPARE_TO_CONSTANT( 6, CommonOperations.getTranslation( "editor.label.step.elementtype.list.comparetoconstant") ),
	COMPARE_TO_STORED( 7, CommonOperations.getTranslation( "editor.label.step.elementtype.list.comparetostored") ),
	COMPARE_TO_STRING( 8, CommonOperations.getTranslation( "editor.label.step.elementtype.list.comparetostring") ),
	COMPARE_SIZE_TO_INTEGER( 9, CommonOperations.getTranslation( "editor.label.step.elementtype.list.comparesizetointeger") ),
	CONTAIN_CONSTANT( 10, CommonOperations.getTranslation( "editor.label.step.elementtype.list.containsconstant") ),
	CONTAIN_STORED( 11, CommonOperations.getTranslation( "editor.label.step.elementtype.list.containsstored") ),
	CONTAIN_STRING( 12, CommonOperations.getTranslation( "editor.label.step.elementtype.list.containsstring") ),
	GAIN_TO_ELEMENT( 13, CommonOperations.getTranslation( "editor.label.step.elementtype.list.gaintoelement") ),
	OUTPUTSTORED( 14, CommonOperations.getTranslation( "editor.label.step.elementtype.list.outputstored") ),	
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
		case 0:	return SELECT_CONSTANT;
		case 1:	return SELECT_BASE;
		case 2:	return SELECT_STRING;
		case 3: return TAB;
		case 4: return CLICK;
		case 5: return MOVE_TO_ELEMENT;
		case 6: return COMPARE_TO_CONSTANT;
		case 7: return COMPARE_TO_STORED;
		case 8: return COMPARE_TO_STRING;
		case 9: return COMPARE_SIZE_TO_INTEGER;
		case 10: return CONTAIN_CONSTANT;
		case 11: return CONTAIN_STORED;
		case 12: return CONTAIN_STRING;		
		case 13: return GAIN_TO_ELEMENT;	
		case 14: return OUTPUTSTORED;
		default: return MOVE_TO_ELEMENT;		
		
		}
	}
	
}
