package hu.akoel.grawit.gui.editors.component;

import hu.akoel.grawit.enums.list.elementtypeoperations.ElementTypeOperationsListEnumInterface;

import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

public class ComboBoxComponent<E extends ElementTypeOperationsListEnumInterface> extends JComboBox<E> implements EditorComponentInterface {

	private static final long serialVersionUID = -7942683931683851883L;

	public ComboBoxComponent() {
		super();

		this.setEnableModify(true);

		// Azert kell, hogy Browse eseten ne legyen halvany a kivalasztott elem. Nem tudom mi tortenik
		this.setRenderer(new MyRenderer());
	}

	@Override
	public void setEnableModify(boolean enable) {
		this.setEnabled(enable);
		this.setEditable(false);
	}

	@Override
	public Component getComponent() {
		return this;
	}

	class MyRenderer extends BasicComboBoxRenderer {

		private static final long serialVersionUID = -4562181616721578685L;

		@Override
		public Component getListCellRendererComponent(JList list, Object value,	int index, boolean isSelected, boolean cellHasFocus) {

			// DisableItem item = (DisableItem) value;			
			Component c = super.getListCellRendererComponent(list, ((ElementTypeOperationsListEnumInterface)value).getTranslatedName(), index, isSelected, cellHasFocus);
			// if (!item.isEnabled ()) {
			// c.setForeground (Color.GRAY);
			// }
			return c;
		}
	}
}
