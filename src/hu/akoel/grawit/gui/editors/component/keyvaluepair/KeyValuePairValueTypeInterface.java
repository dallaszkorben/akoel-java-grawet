package hu.akoel.grawit.gui.editors.component.keyvaluepair;

import javax.swing.JComponent;

public interface KeyValuePairValueTypeInterface<E extends Object> {

	public String toString();
	
	public JComponent getComponent();
	
	public void setValue( E value );
	
	public E getValue();
}
