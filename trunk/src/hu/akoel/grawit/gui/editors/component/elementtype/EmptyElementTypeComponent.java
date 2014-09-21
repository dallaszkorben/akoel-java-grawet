package hu.akoel.grawit.gui.editors.component.elementtype;

import hu.akoel.grawit.core.operations.ElementOperationInterface;
import hu.akoel.grawit.enums.list.ElementTypeListEnum;
import hu.akoel.grawit.enums.list.elementtypeoperations.ElementTypeOperationsListEnumInterface;

import java.awt.Component;

public class EmptyElementTypeComponent extends ElementTypeComponentInterface<ElementTypeOperationsListEnumInterface>{

	private static final long serialVersionUID = -7736373552416187606L;
	
	public EmptyElementTypeComponent(){
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
	public ElementOperationInterface getElementOperation() {
		return null;
	}

	@Override
	public ElementTypeOperationsListEnumInterface getSelectedOperation(	ElementTypeListEnum elementType) {
		return null;
	}

}
