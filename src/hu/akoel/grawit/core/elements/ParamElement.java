package hu.akoel.grawit.core.elements;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.openqa.selenium.WebDriver;

import hu.akoel.grawit.core.operations.ElementOperationInterface;
import hu.akoel.grawit.exceptions.ElementException;


public class ParamElement{
	private String name;
	private ElementOperationInterface operation;
	private BaseElement baseElement;
	
	private String variableValue = "";
	

	public ParamElement( String name, BaseElement baseElement, ElementOperationInterface operation){
		this.name = name;
		this.baseElement = baseElement;
		this.operation = operation;
	}
	
	/**
	 * 
	 * Executes the defined Operation with the defined Parameter
	 * @throws ElementException 
	 * 
	 */
	public void doAction( WebDriver driver ) throws ElementException{
		this.getElementOperation().doAction( driver, this );
	}
	
	public void setName( String name ){
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
	
	public BaseElement getBaseElement(){
		return baseElement;
	}	
	
	public ElementOperationInterface getElementOperation() {
		return operation;
	}

	public void setOperation(ElementOperationInterface operation) {
		this.operation = operation;
	}	
	
	public String getVariableValue() {
		return variableValue;
	}

	public void setVariableValue(String variableValue) {
		this.variableValue = variableValue;
	}

	/**
	 * 
	 * Akkor egyenlo a ket objektum, ha azonosak, vagy azonos az baseElement tagjuk
	 * 
	 */
	public boolean equals( Object obj ){
		if( null == obj ){
			return false;
		}
		if( !( obj instanceof ParamElement )){
			return false;
		}
		if( obj == this ){
			return true;
		}
		
		ParamElement eo = (ParamElement)obj;
		if( eo.getBaseElement() != this.getBaseElement() ){
			return false;
		}
		return true;
	}
	
	/**
	 * 
	 * Azonos Hash erteket kap minden objektum melynek azonos az elemet tagja
	 * 
	 */
	public int hashCode() {
        return new HashCodeBuilder(17, 31).
            append(baseElement).
            toHashCode();
    }
}

