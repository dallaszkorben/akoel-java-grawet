package hu.akoel.grawit.exceptions;

import hu.akoel.grawit.core.operation.interfaces.ContainListOperationInterface;
import hu.akoel.grawit.core.treenodedatamodel.base.NormalBaseElementDataModel;
import hu.akoel.grawit.enums.list.ContainTypeListEnum;
import hu.akoel.grawit.gui.output.message.AttributedOutputMessage;
import hu.akoel.grawit.gui.output.message.LinkOutputMessage;

public class ElementListCompareContainOperationException extends ElementException{

	private static final long serialVersionUID = 3601836630818056477L;
	
	public ElementListCompareContainOperationException( NormalBaseElementDataModel baseElement, String searchingValue, ContainListOperationInterface operation, Exception e ){
		super( baseElement, "", e );
		
		this.insertMessage( new AttributedOutputMessage( "The comparison on the list has failed\n", this.ATTRIBUTE_HEAD ) );
		
		this.insertMessage( new AttributedOutputMessage( "Searching value: ", this.ATTRIBUTE_LABEL ) );
		this.insertMessage( new AttributedOutputMessage( searchingValue + "\n", this.ATTRIBUTE_VALUE ) );

		this.insertMessage( new AttributedOutputMessage( "Searching result: ", this.ATTRIBUTE_LABEL ) );
		this.insertMessage( new AttributedOutputMessage( (operation.getContainType().equals( ContainTypeListEnum.CONTAINS ) ? "NOT found" : "found" + "\n" ) + "\n", this.ATTRIBUTE_VALUE ) );

		this.insertMessage( new AttributedOutputMessage( "Expected relation: ", this.ATTRIBUTE_LABEL ) );
		this.insertMessage( new AttributedOutputMessage( operation.getContainType().equals( ContainTypeListEnum.CONTAINS ) ? "CONTAIN" : "NOT CONTAIN" + "\n", this.ATTRIBUTE_VALUE ) );

		this.insertMessage( new AttributedOutputMessage( "Element name: ", this.ATTRIBUTE_LABEL ) );
		this.insertMessage( new LinkOutputMessage( baseElement ) );
		this.insertMessage( new AttributedOutputMessage( "\n", this.ATTRIBUTE_NONE ) );

		this.insertMessage( new AttributedOutputMessage( "Element selector: ", this.ATTRIBUTE_LABEL ) );
		this.insertMessage( new AttributedOutputMessage( baseElement.getSelector() + "\n", this.ATTRIBUTE_VALUE ) );
	}
}
