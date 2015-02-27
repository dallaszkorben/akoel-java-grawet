package hu.akoel.grawit.enums.list.elementtypeoperations.compare;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.enums.list.elementtypeoperations.ElementTypeOperationsListEnumInterface;

public enum ListElementTypeOperationsCompareListEnum implements ElementTypeOperationsListEnumInterface{		
	COMPARE_TO_CONSTANT( 0, CommonOperations.getTranslation( "editor.label.step.elementtype.list.comparetoconstant") ),
	COMPARE_TO_STORED( 1, CommonOperations.getTranslation( "editor.label.step.elementtype.list.comparetostored") ),
	COMPARE_TO_STRING( 2, CommonOperations.getTranslation( "editor.label.step.elementtype.list.comparetostring") ),
	;
	
	private String translatedName;
	private int index;
	
	private ListElementTypeOperationsCompareListEnum( int index, String translatedName ){
	
		this.index = index;
		this.translatedName = translatedName;
	}
	
	@Override
	public String getTranslatedName(){
		return translatedName;
	}	
	
	public static int getSize(){
		return ListElementTypeOperationsCompareListEnum.values().length;
	}
	
	@Override
	public int getIndex(){
		return index;
	}
	
	public static ListElementTypeOperationsCompareListEnum getElementFieldOperationByIndex( int index ){
		switch (index){
		case 0: return COMPARE_TO_CONSTANT;
		case 1: return COMPARE_TO_STORED;
		case 2: return COMPARE_TO_STRING;
		default: return COMPARE_TO_STRING;		
		
		}
	}
	
}
