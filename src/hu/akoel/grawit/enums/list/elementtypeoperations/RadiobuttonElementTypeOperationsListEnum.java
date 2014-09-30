package hu.akoel.grawit.enums.list.elementtypeoperations;

import hu.akoel.grawit.CommonOperations;

public enum RadiobuttonElementTypeOperationsListEnum implements ElementTypeOperationsListEnumInterface{		
	CLICK( 0, CommonOperations.getTranslation( "editor.label.param.elementtype.radiobutton.click") ),	
	;
	
	private String translatedName;
	private int index;
	
	private RadiobuttonElementTypeOperationsListEnum( int index, String translatedName ){
	
		this.index = index;
		this.translatedName = translatedName;
	}
	
	@Override
	public String getTranslatedName(){
		return translatedName;
	}	
	
	public static int getSize(){
		return RadiobuttonElementTypeOperationsListEnum.values().length;
	}
	
	@Override
	public int getIndex(){
		return index;
	}
	
	public static RadiobuttonElementTypeOperationsListEnum getElementRadiobuttonOperationByIndex( int index ){
		switch (index){
		case 0:	return CLICK;
		default: return CLICK;
		}
	}
	
}
