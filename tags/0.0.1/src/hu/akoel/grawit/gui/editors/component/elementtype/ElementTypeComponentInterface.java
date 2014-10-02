package hu.akoel.grawit.gui.editors.component.elementtype;

import java.awt.Component;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import hu.akoel.grawit.core.operations.ElementOperationInterface;
import hu.akoel.grawit.enums.list.ElementTypeListEnum;
import hu.akoel.grawit.enums.list.elementtypeoperations.ElementTypeOperationsListEnumInterface;
import hu.akoel.grawit.gui.editors.component.EditorComponentInterface;

public abstract class ElementTypeComponentInterface<E extends ElementTypeOperationsListEnumInterface> extends JPanel implements EditorComponentInterface{

	private static final long serialVersionUID = 5532333700050427886L;

	public abstract ElementOperationInterface getElementOperation();
	
	public abstract E getSelectedOperation( ElementTypeListEnum elementType );
	
	class ElementTypeComponentRenderer extends BasicComboBoxRenderer {

        private static final long serialVersionUID = -4562181616721578685L;

        @Override
        public Component getListCellRendererComponent(@SuppressWarnings("rawtypes") JList list, Object value,   int index, boolean isSelected, boolean cellHasFocus) {

                @SuppressWarnings("unchecked")
                Component c = super.getListCellRendererComponent(list, ((E)value).getTranslatedName(), index, isSelected, cellHasFocus);

                return c;
        }
}         
}
