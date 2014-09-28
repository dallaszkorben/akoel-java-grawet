package hu.akoel.grawit.enums.list.elementtypeoperations;

import hu.akoel.grawit.CommonOperations;

public enum LinkElementTypeOperationsListEnum implements ElementTypeOperationsListEnumInterface{		
	CLICK( 0, CommonOperations.getTranslation( "editor.label.param.elementtype.link.click") ),
	GAINTEXTPATTERN( 1, CommonOperations.getTranslation( "editor.label.param.elementtype.link.gaintextpattern") ),	
	OUTPUTVALUE( 2, CommonOperations.getTranslation( "editor.label.param.elementtype.link.outputvalue") ),	
	;
	
	private String translatedName;
	private int index;
	
	private LinkElementTypeOperationsListEnum( int index, String translatedName ){
	
		this.index = index;
		this.translatedName = translatedName;
	}
	
	@Override
	public String getTranslatedName(){
		return translatedName;
	}	
	
	public static int getSize(){
		return LinkElementTypeOperationsListEnum.values().length;
	}
	
	@Override
	public int getIndex(){
		return index;
	}
	
	public static LinkElementTypeOperationsListEnum getElementFieldOperationByIndex( int index ){
		switch (index){
		case 0:	return CLICK;
		case 1:	return GAINTEXTPATTERN;
		case 2:	return OUTPUTVALUE;
		default: return CLICK;
		}
	}
	
}
