package hu.akoel.grawit.enums.list;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.enums.list.operation.OperationListEnumInterface;

public enum ListEnumListSelectionBy implements OperationListEnumInterface{		
	BYVALUE( 0, CommonOperations.getTranslation( "editor.label.param.operation.list.byvalue") ),
	BYINDEX( 1, CommonOperations.getTranslation( "editor.label.param.operation.list.byindex") ),
	BYVISIBLETEXT( 2, CommonOperations.getTranslation( "editor.label.param.operation.list.byvisibletext") ),
	;
	
	private String translatedName;
	private int index;
	
	private ListEnumListSelectionBy( int index, String translatedName ){
	
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
		return ListEnumListSelectionBy.values().length;
	}
	
	public static ListEnumListSelectionBy getListSelectionTypeByOrder( int index ){
		switch (index){
		case 0:	return BYVALUE;
		case 1: return BYINDEX;
		case 2: return BYVISIBLETEXT;
		default: return BYVALUE;
		}
	}
}
