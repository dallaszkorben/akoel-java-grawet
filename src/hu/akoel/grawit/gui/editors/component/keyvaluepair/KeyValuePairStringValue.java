package hu.akoel.grawit.gui.editors.component.keyvaluepair;

import javax.swing.JComponent;
import javax.swing.JTextField;

public class KeyValuePairStringValue implements KeyValuePairValueTypeInterface<String>{
	
	private static final String NAME = "String";
	
	private JTextField field;
	
	public KeyValuePairStringValue( ){
		this.field = new JTextField();
	}
	
	public String toString(){
		return NAME;
	}
	
	@Override
	public JComponent getComponent() {
		return field;
	}

	@Override
	public void setValue(String value) {
		this.field.setText( value );
	}

	@Override
	public String getValue() {		
		return field.getText();
	}

}
