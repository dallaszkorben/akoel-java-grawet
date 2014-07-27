package hu.akoel.grawet.exceptions;

public class ElementException extends Exception{
	private String elementName;
	private String elementId;

	private static final long serialVersionUID = 3601836630818056477L;

	public ElementException( String elementName, String elementId, Exception e ){
		super( "Unable to find element: \nElement name: " + elementName + "\nElement id: " + elementId, e );
		this.elementName = elementName;
		this.elementId = elementId;
	}
	
	public String getElementName() {
		return elementName;
	}

	public String getElementId() {
		return elementId;
	}
}
