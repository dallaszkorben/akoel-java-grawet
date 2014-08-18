package hu.akoel.grawit;

public enum ModelType{
	BASE("base"),
	PARAM("param");
	
	private String name;
	
	private ModelType( String name ){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
}
