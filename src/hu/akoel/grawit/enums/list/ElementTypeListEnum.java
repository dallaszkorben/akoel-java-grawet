package hu.akoel.grawit.enums.list;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.enums.list.elementtypeoperations.ElementTypeOperationsListEnumInterface;

public enum ElementTypeListEnum implements ElementTypeOperationsListEnumInterface{		
	FIELD( 0, true, CommonOperations.getTranslation( "editor.label.step.elementtype.field") ),
	TEXT( 1, true, CommonOperations.getTranslation( "editor.label.step.elementtype.text") ),
	LINK( 2, true, CommonOperations.getTranslation( "editor.label.step.elementtype.link") ),
	LIST( 3, true, CommonOperations.getTranslation( "editor.label.step.elementtype.list") ),
	BUTTON( 4, true, CommonOperations.getTranslation( "editor.label.step.elementtype.button") ),
	RADIOBUTTON( 5, true, CommonOperations.getTranslation( "editor.label.step.elementtype.radiobutton") ),
	CHECKBOX( 6, true, CommonOperations.getTranslation( "editor.label.step.elementtype.checkbox") ),
	
	SCRIPT( 7, false, CommonOperations.getTranslation( "editor.label.step.elementtype.script") ),	
	;
	
	private String translatedName;
	private boolean toList;
	private int index;
	
	private ElementTypeListEnum( int index, boolean toList, String translatedName ){
	
		this.index = index;
		this.toList = toList;
		this.translatedName = translatedName;
	}
	
	@Override
	public String getTranslatedName(){
		return translatedName;
	}	
	
	public static int getSize(){
		
		int size = 0;
		for( int i = 0; i < ElementTypeListEnum.values().length; i++ ){
			
			if( getElementTypeByIndex( i ).toList ){
				size++;
			}
			
		}
		return size;
		
		//return ElementTypeListEnum.values().length;
	}
	
	@Override
	public int getIndex(){
		return index;
	}
	
	public static ElementTypeListEnum getElementTypeByIndex( int index ){
		switch (index){
		case 0:	return FIELD;
		case 1:	return TEXT;
		case 2: return LINK;
		case 3: return LIST;
		case 4: return BUTTON;
		case 5: return RADIOBUTTON;
		case 6: return CHECKBOX;
		case 7: return SCRIPT;
		default: return FIELD;		
		}
	}
	
}
