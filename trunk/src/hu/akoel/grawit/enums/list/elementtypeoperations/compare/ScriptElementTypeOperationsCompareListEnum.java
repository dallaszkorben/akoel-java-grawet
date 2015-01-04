package hu.akoel.grawit.enums.list.elementtypeoperations.compare;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.enums.list.elementtypeoperations.ElementTypeOperationsListEnumInterface;

public enum ScriptElementTypeOperationsCompareListEnum implements ElementTypeOperationsListEnumInterface{	
	NONE( 0, "------------" ),	
	;
	
	private String translatedName;
	private int index;
	
	private ScriptElementTypeOperationsCompareListEnum( int index, String translatedName ){
	
		this.index = index;
		this.translatedName = translatedName;
	}
	
	@Override
	public String getTranslatedName(){
		return translatedName;
	}	
	
	public static int getSize(){
		return ScriptElementTypeOperationsCompareListEnum.values().length;
	}
	
	@Override
	public int getIndex(){
		return index;
	}
	
	public static ScriptElementTypeOperationsCompareListEnum getOperationByIndex( int index ){
		switch (index){		
		default: return NONE;
		}
	}
	
}
