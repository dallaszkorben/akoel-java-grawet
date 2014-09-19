package hu.akoel.grawit.enums.list.operation;

import hu.akoel.grawit.CommonOperations;

public enum RadiobuttonOperationListEnumElement implements ListEnumInterface{		
	CLICK( 0, CommonOperations.getTranslation( "editor.label.param.elementtype.link.click") ),	
	;
	
	private String translatedName;
	private int index;
	
	private RadiobuttonOperationListEnumElement( int index, String translatedName ){
	
		this.index = index;
		this.translatedName = translatedName;
	}
	
	@Override
	public String getTranslatedName(){
		return translatedName;
	}	
	
	public static int getSize(){
		return RadiobuttonOperationListEnumElement.values().length;
	}
	
	@Override
	public int getIndex(){
		return index;
	}
	
	public static RadiobuttonOperationListEnumElement getElementRadiobuttonOperationByIndex( int index ){
		switch (index){
		case 0:	return CLICK;
		default: return CLICK;
		}
	}
	
}
