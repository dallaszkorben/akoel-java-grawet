package hu.akoel.grawit.exceptions;

import hu.akoel.grawit.core.treenodedatamodel.base.NormalBaseElementDataModel;

public class ElementInvalidSelectorException extends ElementException{

	private static final long serialVersionUID = 3601836630818056477L;

	private StringBuilder message = new StringBuilder(100);
	
	public ElementInvalidSelectorException( NormalBaseElementDataModel baseElement, Exception e ){
		super( baseElement, "", e );
		
		this.message.append( "Invalid selector\n" ); 
		this.message.append( "Element name: " + baseElement.getName() + "\n" );
		this.message.append( "Element selector: " + baseElement.getSelector() + "\n" );
	}

	@Override
	public String getMessage() {		
		return this.message.toString();
	}
}
