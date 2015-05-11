package hu.akoel.grawit.exceptions;

import hu.akoel.grawit.core.operation.interfaces.SelectOperationAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.NormalBaseElementDataModel;
import hu.akoel.grawit.gui.output.message.AttributedOutputMessage;
import hu.akoel.grawit.gui.output.message.LinkOutputMessage;

public class ElementListSelectionNotFoundComponentException extends ElementException{

	private static final long serialVersionUID = 3601836630818056477L;
	
	public ElementListSelectionNotFoundComponentException( NormalBaseElementDataModel baseElement, String notFoundComponent, SelectOperationAdapter operation, Exception e ){
		super( baseElement, "", e );
		
		this.insertMessage( new AttributedOutputMessage( "The Selection on the list has failed because the component was not found\n", this.ATTRIBUTE_HEAD ) );
		
		this.insertMessage( new AttributedOutputMessage( "Not found component: ", this.ATTRIBUTE_LABEL ) );
		this.insertMessage( new AttributedOutputMessage( notFoundComponent + "\n", this.ATTRIBUTE_VALUE ) );

		this.insertMessage( new AttributedOutputMessage( "Selection type: ", this.ATTRIBUTE_LABEL ) );
		this.insertMessage( new AttributedOutputMessage( operation.getSelectionBy() + "\n", this.ATTRIBUTE_VALUE ) );
		
		this.insertMessage( new AttributedOutputMessage( "Element name: ", this.ATTRIBUTE_LABEL ) );
		this.insertMessage( new LinkOutputMessage( baseElement ) );
		this.insertMessage( new AttributedOutputMessage( "\n", this.ATTRIBUTE_NONE ) );

		this.insertMessage( new AttributedOutputMessage( "Element selector: ", this.ATTRIBUTE_LABEL ) );
		this.insertMessage( new AttributedOutputMessage( baseElement.getSelector() + "\n", this.ATTRIBUTE_VALUE ) );
	}
}
