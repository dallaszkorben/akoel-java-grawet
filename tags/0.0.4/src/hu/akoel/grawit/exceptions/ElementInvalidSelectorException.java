package hu.akoel.grawit.exceptions;

public class ElementInvalidSelectorException extends ElementException{
	private String elementName;
	private String elementId;

	private static final long serialVersionUID = 3601836630818056477L;

	public ElementInvalidSelectorException( String elementName, String elementSelector, Exception e ){
		super( "Invalid selector:\n   Element name: '" + elementName + "'\n   Element selector: " + elementSelector, e );
		this.elementName = elementName;
		this.elementId = elementSelector;
	}
	
	public String getElementName() {
		return elementName;
	}

	public String getElementSelector() {
		return elementId;
	}
}
