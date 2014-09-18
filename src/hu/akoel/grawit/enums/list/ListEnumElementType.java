package hu.akoel.grawit.enums.list;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.enums.list.operation.OperationListEnumInterface;

public enum ListEnumElementType implements OperationListEnumInterface{		
	FIELD( 0, CommonOperations.getTranslation( "editor.label.param.elementtype.field") ),
	TEXT( 1, CommonOperations.getTranslation( "editor.label.param.elementtype.text") ),
	LINK( 2, CommonOperations.getTranslation( "editor.label.param.elementtype.link") ),
	LIST( 3, CommonOperations.getTranslation( "editor.label.param.elementtype.list") ),
	BUTTON( 4, CommonOperations.getTranslation( "editor.label.param.elementtype.button") ),
	RADIOBUTTON( 5, CommonOperations.getTranslation( "editor.label.param.elementtype.radiobutton") ),
	CHECKBOX( 6, CommonOperations.getTranslation( "editor.label.param.elementtype.checkbox") ),	
	;
	
	private String translatedName;
	private int index;
	
	private ListEnumElementType( int index, String translatedName ){
	
		this.index = index;
		this.translatedName = translatedName;
	}
	
	@Override
	public String getTranslatedName(){
		return translatedName;
	}	
	
	public static int getSize(){
		return ListEnumElementType.values().length;
	}
	
	@Override
	public int getIndex(){
		return index;
	}
	
	public static ListEnumElementType getElementTypeByIndex( int index ){
		switch (index){
		case 0:	return FIELD;
		case 1:	return TEXT;
		case 2: return LINK;
		case 3: return LIST;
		case 4: return BUTTON;
		case 5: return RADIOBUTTON;
		case 6: return CHECKBOX;
		default: return FIELD;		
		}
	}
	
}
