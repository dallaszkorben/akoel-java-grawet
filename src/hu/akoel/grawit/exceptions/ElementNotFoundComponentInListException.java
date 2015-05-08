package hu.akoel.grawit.exceptions;

import hu.akoel.grawit.core.operations.SelectOperationAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.NormalBaseElementDataModel;

public class ElementNotFoundComponentInListException extends ElementException{

	private static final long serialVersionUID = 3601836630818056477L;

	private StringBuilder message = new StringBuilder(100);
	
	public ElementNotFoundComponentInListException( NormalBaseElementDataModel baseElement, String notFoundComponent, SelectOperationAdapter operation, Exception e ){
		super( baseElement, "", e );
		
		this.message.append( "The Selection on the list has failed because the component was not found\n" );
		this.message.append( "Not found component: " + notFoundComponent + "\n" );
		this.message.append( "Selection type: " + operation.getSelectionBy() + "\n" );
		this.message.append( "Element name: " + baseElement.getName() + "\n" );
		this.message.append( "Element selector: " + baseElement.getSelector() + "\n" );
	}

	@Override
	public String getMessage() {		
		return this.message.toString();
	}	
}
