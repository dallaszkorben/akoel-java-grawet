package hu.akoel.grawit.enums.list;

import hu.akoel.grawit.CommonOperations;

public enum ListEnumElementRadiobuttonOperation implements ListEnumInterface{		
	CLICK( 0, CommonOperations.getTranslation( "editor.label.param.elementtype.link.click") ),	
	;
	
	private String translatedName;
	private int index;
	
	private ListEnumElementRadiobuttonOperation( int index, String translatedName ){
	
		this.index = index;
		this.translatedName = translatedName;
	}
	
	@Override
	public String getTranslatedName(){
		return translatedName;
	}	
	
	public static int getSize(){
		return ListEnumElementRadiobuttonOperation.values().length;
	}
	
	@Override
	public int getIndex(){
		return index;
	}
	
	public static ListEnumElementRadiobuttonOperation getElementRadiobuttonOperationByIndex( int index ){
		switch (index){
		case 0:	return CLICK;
		default: return CLICK;
		}
	}
	
}
