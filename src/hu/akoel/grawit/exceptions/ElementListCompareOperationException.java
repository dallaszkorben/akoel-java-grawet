package hu.akoel.grawit.exceptions;

import hu.akoel.grawit.core.operations.CompareOperationInterface;
import hu.akoel.grawit.core.treenodedatamodel.base.NormalBaseElementDataModel;
import hu.akoel.grawit.enums.list.ContainTypeListEnum;

public class ElementListCompareOperationException extends ElementException{

	private static final long serialVersionUID = 3601836630818056477L;

	private StringBuilder message = new StringBuilder(100);
	
	public ElementListCompareOperationException( NormalBaseElementDataModel baseElement, boolean found, String searchingValue, CompareOperationInterface operation, Exception e ){
		super( baseElement, "", e );
		
		this.message.append( "The comparation on the list has failed\n" );
		this.message.append( "Searching value: " + searchingValue + "\n" );
		this.message.append( "Searching result: " + (found ? "Found" : "NOT found" ) + "\n" );
		this.message.append( "Expected relation: " + (operation.getContainType().equals( ContainTypeListEnum.CONTAINS ) ? "CONTAIN" : "NOT CONTAIN" + "\n" ) );
		this.message.append( "Element name: " + baseElement.getName() + "\n" );
		this.message.append( "Element selector: " + baseElement.getSelector() + "\n" );
	}

	@Override
	public String getMessage() {
		return message.toString();
	}
	

}
