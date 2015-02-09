package hu.akoel.grawit.gui.editors.component.elementtype.compare;

import javax.swing.JPanel;

import hu.akoel.grawit.core.operations.ElementOperationAdapter;
import hu.akoel.grawit.enums.list.ElementTypeListEnum;
import hu.akoel.grawit.enums.list.elementtypeoperations.ElementTypeOperationsListEnumInterface;
import hu.akoel.grawit.gui.editors.component.EditorComponentInterface;

public abstract class ElementTypeComponentCompareInterface<E extends ElementTypeOperationsListEnumInterface> extends JPanel implements EditorComponentInterface{

	private static final long serialVersionUID = 5532333700050427886L;

	public abstract ElementOperationAdapter getElementOperation();
	
	public abstract E getSelectedOperation( ElementTypeListEnum elementType );
	        
}
