package hu.akoel.grawit.gui.editors.component.variableparameter;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.enums.list.ParameterType;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class VariableParametersIntegerComponent extends JPanel implements VariableParametersComponentInterface{

	private static final long serialVersionUID = -7128350501312573948L;

	private static final String DEFAULT_VALUE = "0";
	
	private static final int PARAMETERORDER_VALUE = 0;
	
	private JTextField fieldString;
	
	private ArrayList<Object> parameterList;

	/**
	 * Uj lista
	 * 
	 * @param type
	 */
	public VariableParametersIntegerComponent( ParameterType type ){
		super();

		//parameter lista letrehozasa es feltoltese default ertekekkel
		this.parameterList = new ArrayList<>();
		this.parameterList.add( DEFAULT_VALUE );
		
		common( type );
					
	}
	
	/**
	 * Letezo lista
	 * 
	 * @param type
	 * @param parameterList
	 */
	public VariableParametersIntegerComponent( ParameterType type, ArrayList<Object> parameterList ){
		super();
		
		//Parameter lista feltoltese a letezo ertekekkel
		this.parameterList = parameterList;
		
		common( type );		
		
	}
	
	private void common( ParameterType type ){
		
		this.setLayout( new GridBagLayout() );
		
		//Mezo feltoltese
		fieldString = new JTextField( parameterList.get(PARAMETERORDER_VALUE).toString());
		fieldString.setInputVerifier( new CommonOperations.ValueVerifier(parameterList, type, DEFAULT_VALUE, PARAMETERORDER_VALUE) );
		/*fieldString.setInputVerifier(new InputVerifier() {
			String goodValue = DEFAULT_VALUE;
			
			@Override
			public boolean verify(JComponent input) {
				JTextField text = (JTextField)input;
				String possibleValue = text.getText();

				try {
					//Kiprobalja, hogy konvertalhato-e
					Object value = VariableParameterIntegerComponent.this.type.getParameterClass(0).getConstructor(String.class).newInstance(possibleValue);
					parameterList.set( 0, value );
					goodValue = possibleValue;
					
				} catch (Exception e) {
					text.setText( goodValue );
					return false;
				}				
				return true;
			}
		});*/
		
		int gridY = 0;
		GridBagConstraints c = new GridBagConstraints();		
		c.insets = new Insets(0,0,0,0);
/*		
		c.gridy = gridY;
		c.gridx = 0;
		c.gridwidth = 0;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0;
		c.anchor = GridBagConstraints.WEST;
		this.add( labelString, c );
*/		
		gridY++;
		c.gridy = gridY;
		c.gridx = 0;
		c.gridwidth = 0;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.anchor = GridBagConstraints.WEST;
		this.add( fieldString, c );
	}	
	
	@Override
	public void setEnableModify(boolean enable) {
		fieldString.setEditable( enable );		
	}

	@Override
	public Component getComponent() {
		return this;
	}

	@Override
	public ArrayList<Object> getParameters() {
		return parameterList;
	}

}
