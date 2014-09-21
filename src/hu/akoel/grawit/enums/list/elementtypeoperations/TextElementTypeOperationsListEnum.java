package hu.akoel.grawit.enums.list.elementtypeoperations;

import hu.akoel.grawit.CommonOperations;

public enum TextElementTypeOperationsListEnum implements ElementTypeOperationsListEnumInterface{		
	GAINTEXTPATTERN( 0, CommonOperations.getTranslation( "editor.label.param.elementtype.link.gaintextpattern") ),	
	;
	
	private String translatedName;
	private int index;
	
	private TextElementTypeOperationsListEnum( int index, String translatedName ){
	
		this.index = index;
		this.translatedName = translatedName;
	}
	
	@Override
	public String getTranslatedName(){
		return translatedName;
	}	
	
	public static int getSize(){
		return TextElementTypeOperationsListEnum.values().length;
	}
	
	@Override
	public int getIndex(){
		return index;
	}
	
	public static TextElementTypeOperationsListEnum getElementTextOperationByIndex( int index ){
		switch (index){
		case 0:	return GAINTEXTPATTERN;
		default: return GAINTEXTPATTERN;
		}
	}
	
}
