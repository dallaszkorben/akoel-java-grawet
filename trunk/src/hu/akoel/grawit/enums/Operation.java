package hu.akoel.grawit.enums;

import hu.akoel.grawit.CommonOperations;

public enum Operation{		
	FIELD_VARIABLE( 0, CommonOperations.getTranslation( "editor.label.param.operation.fillfieldwithvariable") ),
	FIELD_ELEMENT( 1, CommonOperations.getTranslation( "editor.label.param.operation.fillfieldwithelement") ),
	FIELD_CLEAR( 2, CommonOperations.getTranslation( "editor.label.param.operation.clear") ),
	LIST_VARIABLE( 3, CommonOperations.getTranslation( "editor.label.param.operation.list") ),
	LINK( 4, CommonOperations.getTranslation( "editor.label.param.operation.link") ),
	BUTTON( 5, CommonOperations.getTranslation( "editor.label.param.operation.button") ),
	RADIOBUTTON( 6, CommonOperations.getTranslation( "editor.label.param.operation.radiobutton") ),
	CHECKBOX( 7, CommonOperations.getTranslation( "editor.label.param.operation.checkbox") ),	
	TAB( 8, CommonOperations.getTranslation( "editor.label.param.operation.tab") ),
	
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
	
	public static int getSize(){
		return Operation.values().length;
	}
	
	public int getIndex(){
		return index;
	}
	
	public static Operation getOperationByIndex( int index ){
		switch (index){
		case 0:	return FIELD_VARIABLE;
		case 1:	return FIELD_ELEMENT;
		case 2: return FIELD_CLEAR;
		case 3: return LIST_VARIABLE;
		case 4: return LINK;
		case 5: return BUTTON;
		case 6: return RADIOBUTTON;
		case 7: return CHECKBOX;
		case 8: return TAB;		
		default: return LINK;
		}
	}
}
