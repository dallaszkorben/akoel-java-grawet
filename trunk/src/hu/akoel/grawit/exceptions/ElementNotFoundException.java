package hu.akoel.grawit.exceptions;

public class ElementNotFoundException extends ElementException{
	private String elementName;
	private String elementSelector;

	private static final long serialVersionUID = 3601836630818056477L;

	public ElementNotFoundException( String elementName, String elementSelector, Exception e ){
		super( "Unable to find element: \n   Element name: " + elementName + "\n   Element selector: " + elementSelector, e );
		this.elementName = elementName;
		this.elementSelector = elementSelector;
	}
	
	public String getElementName() {
		return elementName;
	}

	public String getElementSelector() {
		return elementSelector;
	}
}
