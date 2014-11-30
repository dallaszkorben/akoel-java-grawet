package hu.akoel.grawit.gui.editors.component.keyvaluepair;

import javax.swing.JTextField;

public class KeyValuePairStringValue extends JTextField implements KeyValuePairValueTypeInterface{

	private static final long serialVersionUID = 2058346296365138651L;
	
	private static final String NAME = "String";
	
	public KeyValuePairStringValue( ){
		super();
	}
	
	@Override
	public String toString(){
		return NAME;
	}
		
	@Override
	public void setValue(Object value) {
		if( value instanceof String ){
			this.setText( (String)value );
		}
	}

	@Override
	public Object getValue() {		
		return getText().trim();
	}

	@Override
	public void setEnableModify(boolean enable) {
		this.setEditable( enable );		
	}
	
	

}
