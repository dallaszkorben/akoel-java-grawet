package hu.akoel.grawit.enums.list;

import hu.akoel.grawit.CommonOperations;

public enum ElementLinkOperation implements ListEnumInterface{		
	CLICK( 0, CommonOperations.getTranslation( "editor.label.param.elementtype.link.click") ),
	GAINTEXTPATTERN( 1, CommonOperations.getTranslation( "editor.label.param.elementtype.link.gaintextpattern") ),	
	;
	
	private String translatedName;
	private int index;
	
	private ElementLinkOperation( int index, String translatedName ){
	
		this.index = index;
		this.translatedName = translatedName;
	}
	
	@Override
	public String getTranslatedName(){
		return translatedName;
	}	
	
	public static int getSize(){
		return ElementLinkOperation.values().length;
	}
	
	@Override
	public int getIndex(){
		return index;
	}
	
	public static ElementLinkOperation getElementFieldOperationByIndex( int index ){
		switch (index){
		case 0:	return CLICK;
		case 1:	return GAINTEXTPATTERN;
		default: return CLICK;
		}
	}
	
}
