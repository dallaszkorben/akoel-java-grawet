package hu.akoel.grawit.core.parameter;

import hu.akoel.grawit.core.elements.ParamElement;

public class VariableParameter implements ElementParameter{
	String name;
	ParamElement element;

	public VariableParameter( String name, ParamElement element ){
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
