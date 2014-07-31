package hu.akoel.grawit.parameter;

public class StringParameter implements ElementParameter{
	private String name;
	private String constant;
	
	public StringParameter( String name, String constant ){
		this.name = name;
		this.constant = constant;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getValue() {
		return constant;
	}
}
