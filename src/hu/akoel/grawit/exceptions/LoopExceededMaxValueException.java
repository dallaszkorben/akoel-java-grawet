package hu.akoel.grawit.exceptions;

import hu.akoel.grawit.core.treenodedatamodel.base.NormalBaseElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.step.StepLoopCollectorDataModel;
import hu.akoel.grawit.exception.message.AttributedMessage;
import hu.akoel.grawit.exception.message.LinkMessage;

public class LoopExceededMaxValueException extends StepException{

	private static final long serialVersionUID = 7550969335856707072L;
	
	public LoopExceededMaxValueException( StepLoopCollectorDataModel loopCollector, NormalBaseElementDataModel baseElement, Exception e ){
		super( loopCollector, null, new ElementEmptyException( baseElement, e ) );
			
		this.clearMessage();
		
		this.insertMessage( new AttributedMessage( "The LOOP exceeded the maximum value but the LOOP condition is still not TRUE\n", this.ATTRIBUTE_HEAD ) );

		this.insertMessage( new AttributedMessage( "Loop collector name: ", this.ATTRIBUTE_LABEL ) );
		this.insertMessage( new LinkMessage( loopCollector ) );
		this.insertMessage( new AttributedMessage( "\n", this.ATTRIBUTE_NONE ) );
		
		this.insertMessage( new AttributedMessage( "Loop collector base element name: ", this.ATTRIBUTE_LABEL ) );
		this.insertMessage( new LinkMessage( loopCollector.getCompareBaseElement() ) );
		this.insertMessage( new AttributedMessage( "\n", this.ATTRIBUTE_NONE ) );

		this.insertMessage( new AttributedMessage( "Loop collector operation: ", this.ATTRIBUTE_LABEL ) );
		this.insertMessage( new AttributedMessage( loopCollector.getElementOperation().getOperationNameToString() + "\n", this.ATTRIBUTE_VALUE ) );

		this.insertMessage( new AttributedMessage( "Max loop number: ", this.ATTRIBUTE_LABEL ) );
		this.insertMessage( new AttributedMessage( loopCollector.getMaxLoopNumber() + "\n", this.ATTRIBUTE_VALUE ) );

		
		this.insertMessage( new AttributedMessage( "Element name: ", this.ATTRIBUTE_LABEL ) );
		this.insertMessage( new LinkMessage( baseElement ) );
		this.insertMessage( new AttributedMessage( "\n", this.ATTRIBUTE_NONE ) );

		this.insertMessage( new AttributedMessage( "Element selector: ", this.ATTRIBUTE_LABEL ) );
		this.insertMessage( new AttributedMessage( baseElement.getSelector() + "\n", this.ATTRIBUTE_VALUE ) );
	}
}
