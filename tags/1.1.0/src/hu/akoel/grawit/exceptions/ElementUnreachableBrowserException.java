package hu.akoel.grawit.exceptions;

public class ElementUnreachableBrowserException extends ElementException{
	private String elementName;
	private String elementId;

	private static final long serialVersionUID = 3601836630818056477L;

	public ElementUnreachableBrowserException( Exception e ){
		super( "The browser in unreachable :\n" , e );
//		this.elementName = elementName;
//		this.elementId = elementSelector;
	}
	
	public String getElementName() {
		return elementName;
	}

	public String getElementSelector() {
		return elementId;
	}
}
