package hu.akoel.grawit.enums.list;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.enums.list.elementtypeoperations.ElementTypeOperationsListEnumInterface;

public enum ContainTypeListEnum implements ElementTypeOperationsListEnumInterface{		
	CONTAINS( 0, CommonOperations.getTranslation( "editor.label.step.containtype.contains") ),
	NOCONTAINS( 1, CommonOperations.getTranslation( "editor.label.step.containtype.nocontains") ),
	;
	
	private String translatedName;
	private int index;
	
	private ContainTypeListEnum( int index, String translatedName ){
	
		this.index = index;
		this.translatedName = translatedName;
	}
	
	@Override
	public String getTranslatedName(){
		return translatedName;
	}	
	
	public static int getSize(){
		return ContainTypeListEnum.values().length;
	}
	
	@Override
	public int getIndex(){
		return index;
	}
	
	public static ContainTypeListEnum getCompareTypeByIndex( int index ){
		switch (index){
		case 0:	return CONTAINS;
		case 1:	return NOCONTAINS;
		default: return CONTAINS;		
		}
	}
	
}
