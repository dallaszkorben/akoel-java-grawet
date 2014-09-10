package hu.akoel.grawit.enums;

import hu.akoel.grawit.CommonOperations;

public enum Operation{		
	FIELD( 0, CommonOperations.getTranslation( "editor.label.param.operation.field") ),
	LIST( 1, CommonOperations.getTranslation( "editor.label.param.operation.list") ),
	LINK( 2, CommonOperations.getTranslation( "editor.label.param.operation.link") ),
	BUTTON( 3, CommonOperations.getTranslation( "editor.label.param.operation.button") ),
	RADIOBUTTON( 4, CommonOperations.getTranslation( "editor.label.param.operation.radiobutton") ),
	CHECKBOX( 5, CommonOperations.getTranslation( "editor.label.param.operation.checkbox") ),	
	TAB( 6, CommonOperations.getTranslation( "editor.label.param.operation.tab") ),
	;
	
	private String translatedName;
	private int index;
	
	private Operation( int index, String translatedName ){
	
		this.index = index;
		this.translatedName = translatedName;
	}
	
	public String getTranslatedName(){
		return translatedName;
	}	
	
	public int getIndex(){
		return index;
	}
	
	public static Operation getOperationByIndex( int index ){
		switch (index){
		case 0:	return FIELD;
		case 1: return LIST;
		case 2: return LINK;
		case 3: return BUTTON;
		case 4: return RADIOBUTTON;
		case 5: return CHECKBOX;
		case 6: return TAB;
		default: return LINK;
		}
	}
}
