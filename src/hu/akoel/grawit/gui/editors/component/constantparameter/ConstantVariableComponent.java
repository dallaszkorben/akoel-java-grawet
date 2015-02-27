package hu.akoel.grawit.gui.editors.component.constantparameter;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.enums.list.ConstantTypeListEnum;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ConstantVariableComponent extends JPanel implements ConstantComponentInterface{

	private static final long serialVersionUID = 5302725716904676695L;
	
	private static final String DEFAULT_VALUE = "";
	private static final int PARAMETERORDER_SAMPLE = 0;
	
	private JTextField fieldValue;
	private ConstantTypeListEnum type;
	
	private ArrayList<Object> parameterList;

	/**
	 * Uj lista
	 * 
	 * @param type
	 */
	public ConstantVariableComponent( ConstantTypeListEnum type ){
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
	public ConstantVariableComponent( ConstantTypeListEnum type, ArrayList<Object> parameterList ){
		super();
		
		//Parameter lista feltoltese a letezo ertekekkel
		this.parameterList = parameterList;
		
		common( type );		
		
	}
	
	private void common( ConstantTypeListEnum type ){
		this.type = type;
		
		this.setLayout( new GridBagLayout() );
		
		//
		// Sample field
		//
		
		JLabel labelValue = new JLabel( CommonOperations.getTranslation("editor.label.variable.parametertype.variable.defaultvalue") );
		
		fieldValue = new JTextField( parameterList.get(PARAMETERORDER_SAMPLE).toString());
		
		fieldValue.setInputVerifier(new InputVerifier() {
			String goodValue = DEFAULT_VALUE;
			
			@Override
			public boolean verify(JComponent input) {
				JTextField text = (JTextField)input;
				String possibleValue = text.getText();

				try {
					//Kiprobalja, hogy konvertalhato-e
					Object value = ConstantVariableComponent.this.type.getParameterClass(PARAMETERORDER_SAMPLE).getConstructor(String.class).newInstance(possibleValue);
					parameterList.set( PARAMETERORDER_SAMPLE, value );
					goodValue = possibleValue;
					
				} catch (Exception e) {
					text.setText( goodValue );
					return false;
				}				
				return true;
			}
		});
		
		int gridY = 0;
		GridBagConstraints c = new GridBagConstraints();		
		c.insets = new Insets(0,0,0,0);
		
		c.gridy = gridY;
		c.gridx = 0;
		c.gridwidth = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.anchor = GridBagConstraints.WEST;
		this.add( labelValue, c );
		
		gridY++;
		c.gridy = 1;
		c.gridx = 0;
		c.gridwidth = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.anchor = GridBagConstraints.WEST;
		this.add( fieldValue, c );		
		
	}	
	
	@Override
	public void setEnableModify(boolean enable) {
		fieldValue.setEditable( enable );		
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
