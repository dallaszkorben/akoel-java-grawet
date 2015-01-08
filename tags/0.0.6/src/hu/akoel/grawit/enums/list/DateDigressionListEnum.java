package hu.akoel.grawit.enums.list;

import hu.akoel.grawit.enums.list.elementtypeoperations.ElementTypeOperationsListEnumInterface;

public enum DateDigressionListEnum implements ElementTypeOperationsListEnumInterface{		
	PLUS( 0, "+" ),
	MINUS( 1, "-" ),
	NONE( 2, " " ),
	;
	
	private String translatedName;
	private int index;
	
	private DateDigressionListEnum( int index, String translatedName ){
	
		this.index = index;
		this.translatedName = translatedName;

	}
	
	public static int getSize(){
		return DateDigressionListEnum.values().length;
	}
	
	@Override
	public String getTranslatedName(){
		return translatedName;
	}	
	
	@Override
	public int getIndex(){
		return index;
	}
	
	public static DateDigressionListEnum getDateDigressionByName( String name ){
		
		if( name.equals( PLUS.name() ) ){
			return PLUS;
		}else if( name.equals( MINUS.name() ) ){
			return MINUS;
		}else if( name.equals( NONE.name() ) ){
			return NONE;		
		}
		return NONE;
	}
	
	public static DateDigressionListEnum getDateDigressionByIndex( int index ){
		switch (index){
		case 0:	return PLUS;
		case 1: return MINUS;
		case 2: return NONE;	
		default: return NONE;
		}
	}
}
