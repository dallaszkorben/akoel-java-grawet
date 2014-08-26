package hu.akoel.grawit.enums;

public enum Tag{
	BASEROOT("basepage"),
	BASENODE("node"),
	BASEPAGE("page"),
	BASEELEMENT("element"),
	
	PARAMROOT("parampage"),
	PARAMNODE("node"),
	PARAMPAGE("page"),
	PARAMELEMENT("element"),
	
	VARIABLEROOT("variablepage"),
	VARIABLENODE("node"),
	VARIABLEELEMENT("element"),
	
//	VARIABLETYPE("variabletype"),
	VARIABLEPARAMETER("parameter"),	
	;
	private String name;
	
	private Tag( String name ){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
}
