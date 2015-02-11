package hu.akoel.grawit.exceptions;

public class ElementScriptParameterException extends ElementException{
	private String operation;
	private String elementName;
	private String elementId;

	private static final long serialVersionUID = 3601836630818051477L;

	public ElementScriptParameterException( String message, String scriptName ){
		super( "Not proper parameters for '" + scriptName + " script.\n" + message, new Exception());
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
