package hu.akoel.grawit;

public enum VariableSample {
	NO(0),
	PRE(1),
	POST(2);
	
	private int index;
	private VariableSample( int index ){
		this.index = index;
	}
	
	public int getIndex(){
		return index;
	}
}
