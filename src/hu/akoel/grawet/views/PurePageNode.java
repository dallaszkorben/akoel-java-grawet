package hu.akoel.grawet.views;

public class PurePageNode {
	String name;
	String details;
	
	public PurePageNode( String name, String details ){
		this.name = name;
		this.details = details;
	}
	
	public String getName(){
		return name;
	}
	
	public String getDetails(){
		return details;
	}
	
	public String toString(){
		return name;
	}
}
