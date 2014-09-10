package hu.akoel.grawit.exceptions;

import hu.akoel.grawit.enums.ListSelectionType;

public class ElementNotFoundComponentException extends ElementException{
	private String elementName;
	private String elementSelector;

	private static final long serialVersionUID = 3601836630818056477L;

	public ElementNotFoundComponentException( String component, ListSelectionType listSelectionType, String elementName, String elementSelector, Exception e ){
		super( "Unable to find component: \n   Component: " + component + "\n" + "   List selection type: " + listSelectionType + "\n   Element name: " + elementName + "\n   Element selector: " + elementSelector, e );
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
