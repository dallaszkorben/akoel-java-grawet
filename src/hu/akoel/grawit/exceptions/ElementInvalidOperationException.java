package hu.akoel.grawit.exceptions;

import hu.akoel.grawit.core.operation.interfaces.ElementOperationAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.NormalBaseElementDataModel;
import hu.akoel.grawit.gui.output.message.AttributedOutputMessage;
import hu.akoel.grawit.gui.output.message.LinkOutputMessage;

public class ElementInvalidOperationException extends ElementException{
	private static final long serialVersionUID = 3601836630818056477L;
	
	public ElementInvalidOperationException( NormalBaseElementDataModel baseElement, ElementOperationAdapter operation, Exception e ){
		super( baseElement, "", e );
		
		this.insertMessage( new AttributedOutputMessage( "Invalid operation\n", this.ATTRIBUTE_HEAD ) );

		this.insertMessage( new AttributedOutputMessage( "Operation: ", this.ATTRIBUTE_LABEL ) );
		this.insertMessage( new AttributedOutputMessage( operation.getName() + " " + operation.getOperationNameToString() + "\n", this.ATTRIBUTE_VALUE ) );

		this.insertMessage( new AttributedOutputMessage( "Element name: ", this.ATTRIBUTE_LABEL ) );
		this.insertMessage( new LinkOutputMessage( baseElement ) );
		this.insertMessage( new AttributedOutputMessage( "\n", this.ATTRIBUTE_NONE ) );

		this.insertMessage( new AttributedOutputMessage( "Element selector: ", this.ATTRIBUTE_LABEL ) );
		this.insertMessage( new AttributedOutputMessage( baseElement.getSelector() + "\n", this.ATTRIBUTE_VALUE ) );
	}
}
