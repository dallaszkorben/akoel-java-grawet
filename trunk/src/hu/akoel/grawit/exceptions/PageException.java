package hu.akoel.grawit.exceptions;

public class PageException extends Exception{
	private static final long serialVersionUID = 9044143626429149660L;
	private String pageName = null;
	private String elementName = null;
	private String elementId = null;
	private ElementException elementException = null;

	public PageException(String pageName, String elementName, String elementId, ElementException elementException ) {
		//super( "Unable to find element: \n   Page name: " + pageName + "\n   Element name: " + elementName + "\n   Element id: " + elementId, elementException );
		super( elementException.getMessage() + "\n   Page name: " + pageName, elementException );
		this.pageName = pageName;
		this.elementName = elementName;
		this.elementId = elementId;
		this.elementException = elementException;
	}

	public PageException( String pageName, String message, Throwable e) {
		super( message + "\nPage name: " + pageName, e );
		this.pageName = pageName;
	}
	
	public String getPageName() {
		return pageName;
	}

	public String getElementName() {
		return elementName;
	}

	public String getElementId() {
		return elementId;
	}
	
	public ElementException getElementException(){
		return elementException;
		
	}
}
