package hu.akoel.grawit.exceptions;

import hu.akoel.grawit.core.operation.interfaces.SelectOperationAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.NormalBaseElementDataModel;
import hu.akoel.grawit.exception.message.AttributedMessage;
import hu.akoel.grawit.exception.message.LinkMessage;

public class ElementListSelectionNotFoundComponentException extends ElementException{

	private static final long serialVersionUID = 3601836630818056477L;
	
	public ElementListSelectionNotFoundComponentException( NormalBaseElementDataModel baseElement, String notFoundComponent, SelectOperationAdapter operation, Exception e ){
		super( baseElement, "", e );
		
		this.insertMessage( new AttributedMessage( "The Selection on the list has failed because the component was not found\n", this.ATTRIBUTE_HEAD ) );
		
		this.insertMessage( new AttributedMessage( "Not found component: ", this.ATTRIBUTE_LABEL ) );
		this.insertMessage( new AttributedMessage( notFoundComponent + "\n", this.ATTRIBUTE_VALUE ) );

		this.insertMessage( new AttributedMessage( "Selection type: ", this.ATTRIBUTE_LABEL ) );
		this.insertMessage( new AttributedMessage( operation.getSelectionBy() + "\n", this.ATTRIBUTE_VALUE ) );
		
		this.insertMessage( new AttributedMessage( "Element name: ", this.ATTRIBUTE_LABEL ) );
		this.insertMessage( new LinkMessage( baseElement ) );
		this.insertMessage( new AttributedMessage( "\n", this.ATTRIBUTE_NONE ) );

		this.insertMessage( new AttributedMessage( "Element selector: ", this.ATTRIBUTE_LABEL ) );
		this.insertMessage( new AttributedMessage( baseElement.getSelector() + "\n", this.ATTRIBUTE_VALUE ) );
	}
}
