package hu.akoel.grawit.exceptions;

import hu.akoel.grawit.core.operations.CompareOperationInterface;
import hu.akoel.grawit.core.operations.ElementOperationAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.NormalBaseElementDataModel;
import hu.akoel.grawit.enums.list.CompareTypeListEnum;

public class ElementCompareOperationException extends ElementException{

	private static final long serialVersionUID = 3601836630818056477L;

	private StringBuilder message = new StringBuilder(100);
	
	public ElementCompareOperationException( NormalBaseElementDataModel baseElement, String foundValue, CompareOperationInterface operation, Exception e ){
		super( baseElement, "", e );
		
		this.message.append( "The element has NOT the expected value\n" );
		this.message.append( "Found value: " + foundValue + "\n" );
		this.message.append( "Compare: " + operation.getName() + " " + operation.getOperationNameToString() + "\n" );
		this.message.append( "Element name: " + baseElement.getName() + "\n" );
		this.message.append( "Element selector: " + baseElement.getSelector() + "\n" );
	}
	
	public ElementCompareOperationException( CompareTypeListEnum compareType, String compareValue, String elementName, String elementSelector, String elementValue, Exception e ){
		super( "The '" + elementName + "' element has not the expected value.\n   Found value: " + elementValue + "\n   Compare value: " + compareValue + "\n   Expected relation: " + (compareType.equals( CompareTypeListEnum.EQUAL ) ? "=" : "!="), e );
		this.compareType = compareType;
		this.compareValue = compareValue;
		this.elementName = elementName;
		this.elementValue = elementValue;
	}
	

	
	
}
