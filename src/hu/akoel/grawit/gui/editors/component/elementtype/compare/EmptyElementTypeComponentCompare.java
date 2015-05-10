package hu.akoel.grawit.gui.editors.component.elementtype.compare;

import hu.akoel.grawit.core.operation.interfaces.ElementOperationAdapter;
import hu.akoel.grawit.core.operations.NoneOperation;
import hu.akoel.grawit.enums.list.ElementTypeListEnum;
import hu.akoel.grawit.enums.list.elementtypeoperations.ElementTypeOperationsListEnumInterface;

import java.awt.Component;

public class EmptyElementTypeComponentCompare extends ElementTypeComponentCompareInterface<ElementTypeOperationsListEnumInterface>{

	private static final long serialVersionUID = -7736373552416187606L;
	private ElementOperationAdapter operation = new NoneOperation();
	
	public EmptyElementTypeComponentCompare(){
		super();
	}	

	@Override
	public Component getComponent() {
		return this;
	}

	@Override
	public void setEnableModify(boolean enable) {
	}

	@Override
	public ElementOperationAdapter getElementOperation() {
		return operation;
	}

	@Override
	public ElementTypeOperationsListEnumInterface getSelectedOperation(	ElementTypeListEnum elementType) {
		return null;
	}

}
