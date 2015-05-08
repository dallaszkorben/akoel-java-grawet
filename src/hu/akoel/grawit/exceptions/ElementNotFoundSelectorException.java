package hu.akoel.grawit.exceptions;

import hu.akoel.grawit.core.treenodedatamodel.base.NormalBaseElementDataModel;

public class ElementNotFoundSelectorException extends ElementException{

	private static final long serialVersionUID = 3601836630818056477L;

	private StringBuilder message = new StringBuilder(100);
	
	public ElementNotFoundSelectorException( NormalBaseElementDataModel baseElement, Exception e ){
		super( baseElement, "", e );
		
		this.message.append( "Unable to find element by selector\n" );
		this.message.append( "Element name: " + baseElement.getName() + "\n" );
		this.message.append( "Element selector: " + baseElement.getSelector() + "\n" );
	}

	@Override
	public String getMessage() {
		return message.toString();
	}	

}
