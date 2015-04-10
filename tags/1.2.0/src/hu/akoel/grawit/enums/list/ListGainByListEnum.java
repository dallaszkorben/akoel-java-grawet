package hu.akoel.grawit.enums.list;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.enums.list.elementtypeoperations.ElementTypeOperationsListEnumInterface;

public enum ListGainByListEnum implements ElementTypeOperationsListEnumInterface{		
	BYVALUE( 0, CommonOperations.getTranslation( "editor.label.step.operation.list.byvalue") ),	
	BYVISIBLETEXT( 1, CommonOperations.getTranslation( "editor.label.step.operation.list.byvisibletext") ),
	;
	
	private String translatedName;
	private int index;
	
	private ListGainByListEnum( int index, String translatedName ){
	
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
		return ListGainByListEnum.values().length;
	}
	
	public static ListGainByListEnum getListSelectionTypeByOrder( int index ){
		switch (index){
		case 0:	return BYVALUE;
		case 1: return BYVISIBLETEXT;
		default: return BYVALUE;
		}
	}
}
