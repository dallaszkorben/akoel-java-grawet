package hu.akoel.grawit.exceptions;

public class ElementInvalidOperationException extends ElementException{
	private String operation;
	private String elementName;
	private String elementId;

	private static final long serialVersionUID = 3601836630818056477L;

	public ElementInvalidOperationException( String operationString, String elementName, String elementSelector, Exception e ){
		super( "Invalid operation:\n   Operation: " + operationString + "\n   Element name: '" + elementName + "'\n   Element selector: " + elementSelector, e );
		this.operation = operationString;
		this.elementName = elementName;
		this.elementId = elementSelector;
	}
	
	public String getElementName() {
		return elementName;
	}

	public String getElementSelector() {
		return elementId;
	}
	
	public String getOperation(){
		return operation;
	}
}
