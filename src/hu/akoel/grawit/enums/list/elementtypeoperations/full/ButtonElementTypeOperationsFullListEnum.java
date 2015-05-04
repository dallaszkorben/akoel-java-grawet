package hu.akoel.grawit.enums.list.elementtypeoperations.full;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.enums.list.elementtypeoperations.ElementTypeOperationsListEnumInterface;

public enum ButtonElementTypeOperationsFullListEnum implements ElementTypeOperationsListEnumInterface{		
	CLICK( 0, CommonOperations.getTranslation( "editor.label.step.elementtype.button.click") ),
	MOVE_TO_ELEMENT( 1, CommonOperations.getTranslation( "editor.label.step.elementtype.button.movetoelement") ),	
	;
	
	private String translatedName;
	private int index;
	
	private ButtonElementTypeOperationsFullListEnum( int index, String translatedName ){
	
		this.index = index;
		this.translatedName = translatedName;
	}
	
	@Override
	public String getTranslatedName(){
		return translatedName;
	}	
	
	public static int getSize(){
		return ButtonElementTypeOperationsFullListEnum.values().length;
	}
	
	@Override
	public int getIndex(){
		return index;
	}
	
	public static ButtonElementTypeOperationsFullListEnum getElementButtonOperationByIndex( int index ){
		switch (index){
		case 0:	return CLICK;
		case 1:	return MOVE_TO_ELEMENT;
		default: return CLICK;
		}
	}
	
}
