package hu.akoel.grawit.exceptions;

import hu.akoel.grawit.core.treenodedatamodel.base.NormalBaseElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.step.StepLoopCollectorDataModel;
import hu.akoel.grawit.gui.output.message.AttributedOutputMessage;
import hu.akoel.grawit.gui.output.message.LinkOutputMessage;

public class LoopExceededMaxValueException extends StepException{

	private static final long serialVersionUID = 7550969335856707072L;
	
	public LoopExceededMaxValueException( StepLoopCollectorDataModel loopCollector, NormalBaseElementDataModel baseElement, Exception e ){
		super( loopCollector, null, new ElementEmptyException( baseElement, e ) );
			
		this.clearMessage();
		
		this.insertMessage( new AttributedOutputMessage( "The LOOP exceeded the maximum value but the LOOP condition is still not TRUE\n", this.ATTRIBUTE_HEAD ) );

		this.insertMessage( new AttributedOutputMessage( "Loop collector name: ", this.ATTRIBUTE_LABEL ) );
		this.insertMessage( new LinkOutputMessage( loopCollector ) );
		this.insertMessage( new AttributedOutputMessage( "\n", this.ATTRIBUTE_NONE ) );
		
		this.insertMessage( new AttributedOutputMessage( "Loop collector base element name: ", this.ATTRIBUTE_LABEL ) );
		this.insertMessage( new LinkOutputMessage( loopCollector.getCompareBaseElement() ) );
		this.insertMessage( new AttributedOutputMessage( "\n", this.ATTRIBUTE_NONE ) );

		this.insertMessage( new AttributedOutputMessage( "Loop collector operation: ", this.ATTRIBUTE_LABEL ) );
		this.insertMessage( new AttributedOutputMessage( loopCollector.getElementOperation().getOperationNameToString() + "\n", this.ATTRIBUTE_VALUE ) );

		this.insertMessage( new AttributedOutputMessage( "Max loop number: ", this.ATTRIBUTE_LABEL ) );
		this.insertMessage( new AttributedOutputMessage( loopCollector.getMaxLoopNumber() + "\n", this.ATTRIBUTE_VALUE ) );

		
		this.insertMessage( new AttributedOutputMessage( "Element name: ", this.ATTRIBUTE_LABEL ) );
		this.insertMessage( new LinkOutputMessage( baseElement ) );
		this.insertMessage( new AttributedOutputMessage( "\n", this.ATTRIBUTE_NONE ) );

		this.insertMessage( new AttributedOutputMessage( "Element selector: ", this.ATTRIBUTE_LABEL ) );
		this.insertMessage( new AttributedOutputMessage( baseElement.getSelector() + "\n", this.ATTRIBUTE_VALUE ) );
	}
}
