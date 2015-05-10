package hu.akoel.grawit.exceptions;

import hu.akoel.grawit.core.operation.interfaces.ElementOperationAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.NormalBaseElementDataModel;
import hu.akoel.grawit.exception.message.AttributedMessage;
import hu.akoel.grawit.exception.message.LinkMessage;

public class ElementInvalidOperationException extends ElementException{
	private static final long serialVersionUID = 3601836630818056477L;
	
	public ElementInvalidOperationException( NormalBaseElementDataModel baseElement, ElementOperationAdapter operation, Exception e ){
		super( baseElement, "", e );
		
		this.insertMessage( new AttributedMessage( "Invalid operation\n", this.ATTRIBUTE_HEAD ) );

		this.insertMessage( new AttributedMessage( "Operation: ", this.ATTRIBUTE_LABEL ) );
		this.insertMessage( new AttributedMessage( operation.getName() + " " + operation.getOperationNameToString() + "\n", this.ATTRIBUTE_VALUE ) );

		this.insertMessage( new AttributedMessage( "Element name: ", this.ATTRIBUTE_LABEL ) );
		this.insertMessage( new LinkMessage( baseElement ) );
		this.insertMessage( new AttributedMessage( "\n", this.ATTRIBUTE_NONE ) );

		this.insertMessage( new AttributedMessage( "Element selector: ", this.ATTRIBUTE_LABEL ) );
		this.insertMessage( new AttributedMessage( baseElement.getSelector() + "\n", this.ATTRIBUTE_VALUE ) );
	}
}
