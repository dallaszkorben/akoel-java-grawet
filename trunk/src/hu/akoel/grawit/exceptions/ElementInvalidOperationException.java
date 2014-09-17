package hu.akoel.grawit.exceptions;

import hu.akoel.grawit.enums.list.Operation;

public class ElementInvalidOperationException extends ElementException{
	private Operation operation;
	private String elementName;
	private String elementId;

	private static final long serialVersionUID = 3601836630818056477L;

	public ElementInvalidOperationException( Operation operation, String elementName, String elementSelector, Exception e ){
		super( "Invalid operation:\n   Operation: " + operation.getTranslatedName() + "\n   Element name: '" + elementName + "'\n   Element selector: " + elementSelector, e );
		this.operation = operation;
		this.elementName = elementName;
		this.elementId = elementSelector;
	}
	
	public String getElementName() {
		return elementName;
	}

	public String getElementSelector() {
		return elementId;
	}
	
	public Operation getOperation(){
		return operation;
	}
}
