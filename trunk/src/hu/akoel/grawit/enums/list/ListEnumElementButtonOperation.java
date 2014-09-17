package hu.akoel.grawit.enums.list;

import hu.akoel.grawit.CommonOperations;

public enum ListEnumElementButtonOperation implements ListEnumInterface{		
	CLICK( 0, CommonOperations.getTranslation( "editor.label.param.elementtype.link.click") ),	
	;
	
	private String translatedName;
	private int index;
	
	private ListEnumElementButtonOperation( int index, String translatedName ){
	
		this.index = index;
		this.translatedName = translatedName;
	}
	
	@Override
	public String getTranslatedName(){
		return translatedName;
	}	
	
	public static int getSize(){
		return ListEnumElementButtonOperation.values().length;
	}
	
	@Override
	public int getIndex(){
		return index;
	}
	
	public static ListEnumElementButtonOperation getElementButtonOperationByIndex( int index ){
		switch (index){
		case 0:	return CLICK;
		default: return CLICK;
		}
	}
	
}
