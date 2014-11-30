package hu.akoel.grawit.gui.editors.component.keyvaluepair;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JTextField;

public class KeyValuePairIntegerValue extends JTextField implements KeyValuePairValueTypeInterface{

	private static final long serialVersionUID = 2065332431727922310L;
	
	private static final String DEFAULT = "0";
	
	private static final String NAME = "Integer";
	
	public KeyValuePairIntegerValue( ){
		super();
		setText( DEFAULT );
		setInputVerifier( new InputVerifier() {
			
			String goodValue = DEFAULT;
			
			@Override
			public boolean verify(JComponent input) {
				JTextField text = (JTextField)input;
				String possibleValue = text.getText();

				try {
					//Kiprobalja, hogy konvertalhato-e
					Integer.valueOf( possibleValue );

					goodValue = possibleValue;
					
				} catch (Exception e) {
					text.setText( goodValue );
					return false;
				}				
				return true;
				
			}
		});
	}
	
	@Override
	public String toString(){
		return NAME;
	}
		
	@Override
	public void setValue(Object value) {
		if( value instanceof Integer ){
			this.setText( String.valueOf(value) );
		}
	}

	@Override
	public Object getValue() {		
		return Integer.valueOf( getText() );
	}

	@Override
	public void setEnableModify(boolean enable) {
		this.setEditable( enable );		
	}
	
	

}
