package hu.akoel.grawit.parameter;

import hu.akoel.grawit.elements.ParameterizedElement;

public class VariableParameter implements ElementParameter{
	String name;
	ParameterizedElement element;

	public VariableParameter( String name, ParameterizedElement element ){
		this.name = name;
		this.element = element;
	}
	
	@Override
	public String getName() {		
		return name;
	}

	@Override
	public String getValue() {
		return element.getVariableValue();
	}

}
