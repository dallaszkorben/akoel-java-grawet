package hu.akoel.grawit.enums.list;

import hu.akoel.grawit.CommonOperations;

public enum ListSelectionBy implements ListEnumInterface{		
	BYVALUE( 0, CommonOperations.getTranslation( "editor.label.param.operation.list.byvalue") ),
	BYINDEX( 1, CommonOperations.getTranslation( "editor.label.param.operation.list.byindex") ),
	BYVISIBLETEXT( 2, CommonOperations.getTranslation( "editor.label.param.operation.list.byvisibletext") ),
	;
	
	private String translatedName;
	private int index;
	
	private ListSelectionBy( int index, String translatedName ){
	
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
		return ListSelectionBy.values().length;
	}
	
	public static ListSelectionBy getListSelectionTypeByOrder( int index ){
		switch (index){
		case 0:	return BYVALUE;
		case 1: return BYINDEX;
		case 2: return BYVISIBLETEXT;
		default: return BYVALUE;
		}
	}
}
