package hu.akoel.grawit.core.operation.interfaces;

import hu.akoel.grawit.enums.list.CompareTypeListEnum;
import hu.akoel.grawit.enums.list.ListCompareByListEnum;

public interface CompareListOperationInterface extends CompareOperationInterface{
	public ListCompareByListEnum getCompareBy();
	public CompareTypeListEnum getCompareType();
}
