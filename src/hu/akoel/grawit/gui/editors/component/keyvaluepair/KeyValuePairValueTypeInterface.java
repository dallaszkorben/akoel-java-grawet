package hu.akoel.grawit.gui.editors.component.keyvaluepair;

public interface KeyValuePairValueTypeInterface{ //<E extends Object> {

/*	public String toString();
	
	public JComponent getComponent();
	
	public void setValue( E value );
	
	public E getValue();
*/
	public void setValue( Object value );
	
	public Object getValue();
	
	public void setEnableModify(boolean enable);

}
