package hu.akoel.grawit.exceptions;

import hu.akoel.grawit.enums.list.CompareTypeListEnum;

public class ElementCompareOperationException extends ElementException{

	private String compareValue;
	private String elementName;
	private String elementSelector;
	private String elementValue;
	private CompareTypeListEnum compareType;

	private static final long serialVersionUID = 3601836630818056477L;

	public ElementCompareOperationException( CompareTypeListEnum compareType, String compareValue, String elementName, String elementSelector, String elementValue, Exception e ){
		super( "The '" + elementName + "' element has not the expected value.\n   Found value: " + elementValue + "\n   Compare value: " + compareValue + "\n   Expected relation: " + (compareType.equals( CompareTypeListEnum.EQUAL ) ? "=" : "!="), e );
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
