package hu.akoel.grawit;

import hu.akoel.grawit.enums.list.elementtypeoperations.ElementTypeOperationsListEnumInterface;

import java.awt.Component;

import javax.swing.JList;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

/**
 * 
 * @author akoel
 *
 * @param <E>
 */
public class ListRenderer<E extends ElementTypeOperationsListEnumInterface> extends BasicComboBoxRenderer {

	private static final long serialVersionUID = 321816528340469926L;

	@Override
	public Component getListCellRendererComponent(@SuppressWarnings("rawtypes") JList list, Object value,   int index, boolean isSelected, boolean cellHasFocus) {
		
		if( null != value ){
		
			@SuppressWarnings("unchecked")
			Component c = super.getListCellRendererComponent(list, ((E)value).getTranslatedName(), index, isSelected, cellHasFocus);

			return c;
		
		}else{ 
			return null;
		}
	}
	  
}
