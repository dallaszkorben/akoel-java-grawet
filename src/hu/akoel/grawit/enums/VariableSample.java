package hu.akoel.grawit.enums;

import hu.akoel.grawit.CommonOperations;

public enum VariableSample {
	NO(0, CommonOperations.getTranslation( "editor.label.base.variablesample.no") ),
	PRE(1, CommonOperations.getTranslation( "editor.label.base.variablesample.pre") ),
	POST(2, CommonOperations.getTranslation( "editor.label.base.variablesample.post") );
	
	private int index;
	private String translatedName;
	
	private VariableSample( int index, String translatedName ){
		this.index = index;
		this.translatedName = translatedName;
	}
	
	public int getIndex(){
		return index;
	}
	
	public String getTranslatedName(){
		return translatedName;
	}
	
	public static VariableSample getVariableSampleByIndex( int index ){
		switch(index){
		case 0: return NO;
		case 1: return PRE;
		case 2: return POST;
		default: return NO;
		}
	}
}
