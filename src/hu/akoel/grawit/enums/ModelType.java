package hu.akoel.grawit.enums;

public enum ModelType{
	BASE("basepage"),
	PARAM("parampage");
	
	private String name;
	
	private ModelType( String name ){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
}
