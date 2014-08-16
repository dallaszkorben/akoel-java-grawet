package hu.akoel.grawit;

public enum VariableSample {
	NO(0, CommonOperations.getTranslation( "editor.title.variable.no") ),
	PRE(1, CommonOperations.getTranslation( "editor.title.variable.pre") ),
	POST(2, CommonOperations.getTranslation( "editor.title.variable.post") );
	
	private int index;
	private String translatedName;
	
	private VariableSample( int index, String translatedName ){
		this.index = index;
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
