package hu.akoel.grawit.exceptions;

import hu.akoel.grawit.enums.list.CompareTypeListEnum;

public class LoopExceededMaxValueException extends ElementException{

	private String compareValue;
	private String elementName;
	private String elementSelector;
	private String elementValue;
	private CompareTypeListEnum compareType;

	private static final long serialVersionUID = 7550969335856707072L;
	
	public LoopExceededMaxValueException( String elementName, Exception e ){
		super( "The LOOP exceeded the maximum value but the LOOP condition is still not TRUE for the '" + elementName + "' base element", e );
		this.compareType = compareType;
		this.compareValue = compareValue;
		this.elementName = elementName;
		this.elementValue = elementValue;
	}
	
	public CompareTypeListEnum getCompareType() {
		return compareType;
	}
	
	public String getElementName() {
		return elementName;
	}

	public String getElementValue() {
		return elementValue;
	}
	
	public String getElementSelector(){
		return elementSelector;
	}
	
	public String getCompareValue(){
		return compareValue;
	}
	
	
}
