package hu.akoel.grawit.enums.list;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.enums.list.elementtypeoperations.ElementTypeOperationsListEnumInterface;

public enum CompareTypeListEnum implements ElementTypeOperationsListEnumInterface{		
	EQUAL( 0, CommonOperations.getTranslation( "editor.label.step.comparetype.equal") ),
	DIFFERENT( 1, CommonOperations.getTranslation( "editor.label.step.comparetype.different") ),
	;
	
	private String translatedName;
	private int index;
	
	private CompareTypeListEnum( int index, String translatedName ){
	
		this.index = index;
		this.translatedName = translatedName;
	}
	
	@Override
	public String getTranslatedName(){
		return translatedName;
	}	
	
	public static int getSize(){
		return CompareTypeListEnum.values().length;
	}
	
	@Override
	public int getIndex(){
		return index;
	}
	
	public static CompareTypeListEnum getCompareTypeByIndex( int index ){
		switch (index){
		case 0:	return EQUAL;
		case 1:	return DIFFERENT;
		default: return EQUAL;		
		}
	}
	
}
