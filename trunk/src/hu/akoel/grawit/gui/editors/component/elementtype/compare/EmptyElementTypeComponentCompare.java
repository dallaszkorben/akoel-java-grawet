package hu.akoel.grawit.gui.editors.component.elementtype.compare;

import hu.akoel.grawit.core.operations.ElementOperationAdapter;
import hu.akoel.grawit.enums.list.ElementTypeListEnum;
import hu.akoel.grawit.enums.list.elementtypeoperations.ElementTypeOperationsListEnumInterface;

import java.awt.Component;

public class EmptyElementTypeComponentCompare extends ElementTypeComponentCompareInterface<ElementTypeOperationsListEnumInterface>{

	private static final long serialVersionUID = -7736373552416187606L;
	
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
		return null;
	}

	@Override
	public ElementTypeOperationsListEnumInterface getSelectedOperation(	ElementTypeListEnum elementType) {
		return null;
	}

}
