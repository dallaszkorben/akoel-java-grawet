package hu.akoel.grawit.exceptions;

import hu.akoel.grawit.core.operations.ElementOperationAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.NormalBaseElementDataModel;

public class ElementInvalidOperationException extends ElementException{
	private static final long serialVersionUID = 3601836630818056477L;

	private StringBuilder message = new StringBuilder(100);
	
	public ElementInvalidOperationException( NormalBaseElementDataModel baseElement, ElementOperationAdapter operation, Exception e ){
		super( baseElement, "", e );
		
		this.message.append( "Invalid operation\n" ); 
		this.message.append( "Operation: " + operation.getName() + " " + operation.getOperationNameToString() + "\n" );
		this.message.append( "Element name: " + baseElement.getName() + "\n" );
		this.message.append( "Element selector: " + baseElement.getSelector() + "\n" );
	}

	@Override
	public String getMessage() {		
		return message.toString();
	}

}
