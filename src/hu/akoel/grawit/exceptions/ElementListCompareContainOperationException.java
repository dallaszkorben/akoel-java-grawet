package hu.akoel.grawit.exceptions;

import hu.akoel.grawit.core.operation.interfaces.ContainListOperationInterface;
import hu.akoel.grawit.core.treenodedatamodel.base.NormalBaseElementDataModel;
import hu.akoel.grawit.enums.list.ContainTypeListEnum;
import hu.akoel.grawit.exception.message.AttributedMessage;
import hu.akoel.grawit.exception.message.LinkMessage;

public class ElementListCompareContainOperationException extends ElementException{

	private static final long serialVersionUID = 3601836630818056477L;
	
	public ElementListCompareContainOperationException( NormalBaseElementDataModel baseElement, String searchingValue, ContainListOperationInterface operation, Exception e ){
		super( baseElement, "", e );
		
		this.insertMessage( new AttributedMessage( "The comparison on the list has failed\n", this.ATTRIBUTE_HEAD ) );
		
		this.insertMessage( new AttributedMessage( "Searching value: ", this.ATTRIBUTE_LABEL ) );
		this.insertMessage( new AttributedMessage( searchingValue + "\n", this.ATTRIBUTE_VALUE ) );

		this.insertMessage( new AttributedMessage( "Searching result: ", this.ATTRIBUTE_LABEL ) );
		this.insertMessage( new AttributedMessage( (operation.getContainType().equals( ContainTypeListEnum.CONTAINS ) ? "NOT found" : "found" + "\n" ) + "\n", this.ATTRIBUTE_VALUE ) );

		this.insertMessage( new AttributedMessage( "Expected relation: ", this.ATTRIBUTE_LABEL ) );
		this.insertMessage( new AttributedMessage( operation.getContainType().equals( ContainTypeListEnum.CONTAINS ) ? "CONTAIN" : "NOT CONTAIN" + "\n", this.ATTRIBUTE_VALUE ) );

		this.insertMessage( new AttributedMessage( "Element name: ", this.ATTRIBUTE_LABEL ) );
		this.insertMessage( new LinkMessage( baseElement ) );
		this.insertMessage( new AttributedMessage( "\n", this.ATTRIBUTE_NONE ) );

		this.insertMessage( new AttributedMessage( "Element selector: ", this.ATTRIBUTE_LABEL ) );
		this.insertMessage( new AttributedMessage( baseElement.getSelector() + "\n", this.ATTRIBUTE_VALUE ) );
	}
}
