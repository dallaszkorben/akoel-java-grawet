package hu.akoel.grawit.exceptions;

import hu.akoel.grawit.core.operation.interfaces.CompareElementOperationInterface;
import hu.akoel.grawit.core.treenodedatamodel.base.NormalBaseElementDataModel;
import hu.akoel.grawit.enums.list.CompareTypeListEnum;
import hu.akoel.grawit.exception.message.AttributedMessage;
import hu.akoel.grawit.exception.message.LinkMessage;

public class ElementListSizeCompareOperationException extends ElementException{

	private static final long serialVersionUID = 3601836630818056477L;
	
	public ElementListSizeCompareOperationException( NormalBaseElementDataModel baseElement, String foundValue, CompareElementOperationInterface operation, Exception e ){
		super( baseElement, "", e );
		
		this.insertMessage( new AttributedMessage( "The comparation on the list size has failed\n", this.ATTRIBUTE_HEAD ) );
		
		this.insertMessage( new AttributedMessage( "Found size: ", this.ATTRIBUTE_LABEL ) );
		this.insertMessage( new AttributedMessage( foundValue + "\n", this.ATTRIBUTE_VALUE ) );

		this.insertMessage( new AttributedMessage( "Compare with: ", this.ATTRIBUTE_LABEL ) );
		this.insertMessage( new AttributedMessage( operation.getCompareTo() + "\n", this.ATTRIBUTE_VALUE ) );
		
		this.insertMessage( new AttributedMessage( "Expected relation: ", this.ATTRIBUTE_LABEL ) );
		this.insertMessage( new AttributedMessage( (operation.getCompareType().equals( CompareTypeListEnum.EQUAL ) ? "=" : "!=") + "\n", this.ATTRIBUTE_VALUE ) );

		this.insertMessage( new AttributedMessage( "Element name: ", this.ATTRIBUTE_LABEL ) );
		this.insertMessage( new LinkMessage( baseElement ) );
		this.insertMessage( new AttributedMessage( "\n", this.ATTRIBUTE_NONE ) );

		this.insertMessage( new AttributedMessage( "Element selector: ", this.ATTRIBUTE_LABEL ) );
		this.insertMessage( new AttributedMessage( baseElement.getSelector() + "\n", this.ATTRIBUTE_VALUE ) );
	}
}
