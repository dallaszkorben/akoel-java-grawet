package hu.akoel.grawit.enums;

public enum Tag{
	BASE("basepage"),
	PARAM("parampage"),
	BASENODE("node"),
	BASEPAGE("page"),
	BASEELEMENT("element"),
	PARAMNODE("node"),
	PARAMPAGE("page"),
	PARAMELEMET("element");
	
	private String name;
	
	private Tag( String name ){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
}
