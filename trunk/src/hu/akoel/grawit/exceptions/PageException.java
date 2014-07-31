package hu.akoel.grawit.exceptions;

public class PageException extends Exception{
	private static final long serialVersionUID = 9044143626429149660L;
	private String pageName = null;
	private String elementName = null;
	private String elementId = null;

	public PageException(String pageName, String elementName, String elementId, Throwable e) {
		super( "Unable to find element: \nPage name: " + pageName + "\nElement name: " + elementName + "\nElement id: " + elementId, e );
		this.pageName = pageName;
		this.elementName = elementName;
		this.elementId = elementId;
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
}
