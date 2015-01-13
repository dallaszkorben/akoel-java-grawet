package hu.akoel.grawit.enums.list;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.enums.list.elementtypeoperations.ElementTypeOperationsListEnumInterface;

public enum ListCompareByListEnum implements ElementTypeOperationsListEnumInterface{		
	BYVALUE( 0, CommonOperations.getTranslation( "editor.label.param.operation.list.byvalue") ),	
	BYVISIBLETEXT( 1, CommonOperations.getTranslation( "editor.label.param.operation.list.byvisibletext") ),
	;
	
	private String translatedName;
	private int index;
	
	private ListCompareByListEnum( int index, String translatedName ){
	
		this.index = index;
		this.translatedName = translatedName;
	}
	
	@Override
	public String getTranslatedName(){
		return translatedName;
	}	
	
	@Override
	public int getIndex(){
		return index;
	}
	
	public static int getSize(){
		return ListCompareByListEnum.values().length;
	}
	
	public static ListCompareByListEnum getListSelectionTypeByOrder( int index ){
		switch (index){
		case 0:	return BYVALUE;
		case 1: return BYVISIBLETEXT;
		default: return BYVALUE;
		}
	}
}
