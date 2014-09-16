package hu.akoel.grawit.enums;

import hu.akoel.grawit.CommonOperations;

public enum ListSelectionType{		
	BYVALUE( 0, CommonOperations.getTranslation( "editor.label.param.operation.list.byvalue") ),
	BYINDEX( 1, CommonOperations.getTranslation( "editor.label.param.operation.list.byindex") ),
	BYVISIBLETEXT( 2, CommonOperations.getTranslation( "editor.label.param.operation.list.byvisibletext") ),
	;
	
	private String translatedName;
	private int index;
	
	private ListSelectionType( int index, String translatedName ){
	
		this.index = index;
		this.translatedName = translatedName;
	}
	
	public String getTranslatedName(){
		return translatedName;
	}	
	
	public int getIndex(){
		return index;
	}
	
	public static int getSize(){
		return ListSelectionType.values().length;
	}
	
	public static ListSelectionType getListSelectionTypeByOrder( int index ){
		switch (index){
		case 0:	return BYVALUE;
		case 1: return BYINDEX;
		case 2: return BYVISIBLETEXT;
		default: return BYVALUE;
		}
	}
}
