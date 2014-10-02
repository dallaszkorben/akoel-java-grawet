package hu.akoel.grawit.exceptions;

public class ElementCompareOperationException extends ElementException{

	private String expectedValue;
	private String elementName;
	private String elementSelector;
	private String elementValue;

	private static final long serialVersionUID = 3601836630818056477L;

	public ElementCompareOperationException( String expectedValue, String elementName, String elementSelector, String elementValue, Exception e ){
		super( "The '" + elementName + "' element has not the expected value.\nExpected value: " + expectedValue + "\nFound value: " + elementValue, e );
		this.expectedValue = expectedValue;
		this.elementName = elementName;
		this.elementValue = elementValue;
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
	
	public String getExpectedValue(){
		return expectedValue;
	}
	
	
}
