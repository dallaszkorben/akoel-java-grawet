package hu.akoel.grawit.core.operations;

import hu.akoel.grawit.enums.list.CompareTypeListEnum;
import hu.akoel.grawit.enums.list.ListCompareByListEnum;

public interface ListOperationInterface {

	public ListCompareByListEnum getCompareBy();

	public CompareTypeListEnum getCompareType();
}
