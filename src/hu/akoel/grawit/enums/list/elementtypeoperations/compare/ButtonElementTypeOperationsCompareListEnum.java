package hu.akoel.grawit.enums.list.elementtypeoperations.compare;

import hu.akoel.grawit.enums.list.elementtypeoperations.ElementTypeOperationsListEnumInterface;

public enum ButtonElementTypeOperationsCompareListEnum implements ElementTypeOperationsListEnumInterface{		
	NONE( 0, "------------" ),	
	//CLICK( 0, CommonOperations.getTranslation( "editor.label.step.elementtype.button.click") ),	
	;
	
	private String translatedName;
	private int index;
	
	private ButtonElementTypeOperationsCompareListEnum( int index, String translatedName ){
	
		this.index = index;
		this.translatedName = translatedName;
	}
	
	@Override
	public String getTranslatedName(){
		return translatedName;
	}	
	
	public static int getSize(){
		return ButtonElementTypeOperationsCompareListEnum.values().length;
	}
	
	@Override
	public int getIndex(){
		return index;
	}
	
	public static ButtonElementTypeOperationsCompareListEnum getElementButtonOperationByIndex( int index ){
		switch (index){
//		case 0:	return CLICK;
//		default: return CLICK;
		default: return NONE;
		}
	}
	
}
