package hu.akoel.grawit.enums.list.operation;

import hu.akoel.grawit.CommonOperations;

public enum TextOperationListEnum implements ListEnumInterface{		
	GAINTEXTPATTERN( 0, CommonOperations.getTranslation( "editor.label.param.elementtype.link.gaintextpattern") ),	
	;
	
	private String translatedName;
	private int index;
	
	private TextOperationListEnum( int index, String translatedName ){
	
		this.index = index;
		this.translatedName = translatedName;
	}
	
	@Override
	public String getTranslatedName(){
		return translatedName;
	}	
	
	public static int getSize(){
		return TextOperationListEnum.values().length;
	}
	
	@Override
	public int getIndex(){
		return index;
	}
	
	public static TextOperationListEnum getElementTextOperationByIndex( int index ){
		switch (index){
		case 0:	return GAINTEXTPATTERN;
		default: return GAINTEXTPATTERN;
		}
	}
	
}
