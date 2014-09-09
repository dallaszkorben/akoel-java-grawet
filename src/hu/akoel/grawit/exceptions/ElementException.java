package hu.akoel.grawit.exceptions;

public abstract class ElementException extends Exception{

	private static final long serialVersionUID = -7365125686038678803L;

	public ElementException( String message, Throwable exception ){
		super( message, exception );
	}
	public abstract String getElementName();

	public abstract String getElementSelector();

}
