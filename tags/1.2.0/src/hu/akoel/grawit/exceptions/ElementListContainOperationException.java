package hu.akoel.grawit.exceptions;

import hu.akoel.grawit.core.treenodedatamodel.base.NormalBaseElementDataModel;
import hu.akoel.grawit.enums.list.ContainTypeListEnum;

public class ElementListContainOperationException extends ElementException{

	private String searchingValue;
	private ContainTypeListEnum containType;
	private NormalBaseElementDataModel baseElement;

	private static final long serialVersionUID = 3601836630818056477L;

	public ElementListContainOperationException( NormalBaseElementDataModel baseElement, ContainTypeListEnum containType, String searchingValue, boolean found, Exception e ){
		super( "The '" + ((NormalBaseElementDataModel)baseElement).getName() + "' list has not the expected value.\n   Searching value: " + searchingValue + "\n   Found: " + (found ? "YES" : "NO" ) + "\n   Expected relation: " + (containType.equals( ContainTypeListEnum.CONTAINS ) ? "CONTAIN" : "NOT CONTAIN"), e );
		this.baseElement = baseElement;
		this.containType = containType;
		this.searchingValue = searchingValue;
	}
	
	public ContainTypeListEnum getCompareType() {
		return containType;
	}
	
	public String getElementName() {
		return baseElement.getName();
	}
	
	public String getElementSelector(){
		return baseElement.getSelector();
	}

	public String getSearchingValue(){
		return searchingValue;
	}
}
