package hu.akoel.grawit.gui.editor.component;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.enums.VariableType;

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

public class VariableParametersRandomStringComponent extends JPanel implements VariableParametersComponentInterface{

	private static final long serialVersionUID = -9146846149045859640L;
	
	private static final String DEFAULT_SAMPLE = "abcdefghijklmnopqrstuvwxyz0123456789";
	private static final int PARAMETERORDER_SAMPLE = 0;
	private static final int PARAMETERORDER_LENGTH = 1;
	
	private JTextField fieldSample;
	private JTextField fieldLength;
	private VariableType type;
	
	private ArrayList<Object> parameterList;

	/**
	 * Uj lista
	 * 
	 * @param type
	 */
	public VariableParametersRandomStringComponent( VariableType type ){
		super();

		//parameter lista letrehozasa es feltoltese default ertekekkel
		this.parameterList = new ArrayList<>();
		this.parameterList.add( DEFAULT_SAMPLE );
		this.parameterList.add( 15 );
		
		common( type );		
		
	}
	
	/**
	 * Letezo lista
	 * 
	 * @param type
	 * @param parameterList
	 */
	public VariableParametersRandomStringComponent( VariableType type, ArrayList<Object> parameterList ){
		super();
		
		//Parameter lista feltoltese a letezo ertekekkel
		this.parameterList = parameterList;
		
		common( type );		
		
	}
	
	private void common( VariableType type ){
		this.type = type;
		
		this.setLayout( new GridBagLayout() );
		
		//
		// Sample field
		//
		
		JLabel labelSample = new JLabel( CommonOperations.getTranslation("editor.title.variabletype.randomstring.samplestring") );
		
		fieldSample = new JTextField( parameterList.get(0).toString());
		
		fieldSample.setInputVerifier(new InputVerifier() {
			String goodValue = "";
			
			@Override
			public boolean verify(JComponent input) {
				JTextField text = (JTextField)input;
				String possibleValue = text.getText();

				try {
					//Kiprobalja, hogy konvertalhato-e
					Object value = VariableParametersRandomStringComponent.this.type.getParameterClass(0).getConstructor(String.class).newInstance(possibleValue);
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
		
		c.gridy = 0;
		c.gridx = 0;
		c.gridwidth = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.anchor = GridBagConstraints.WEST;
		this.add( labelSample, c );
		
		gridY++;
		c.gridy = 1;
		c.gridx = 0;
		c.gridwidth = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.anchor = GridBagConstraints.WEST;
		this.add( fieldSample, c );		

		//
		// Length field
		//
		JLabel labelLength = new JLabel( CommonOperations.getTranslation("editor.title.variabletype.randomstring.length") );

		fieldLength = new JTextField( parameterList.get(1).toString());
		
		fieldLength.setInputVerifier(new InputVerifier() {
			String goodValue = "";
			
			@Override
			public boolean verify(JComponent input) {
				JTextField text = (JTextField)input;
				String possibleValue = text.getText();

				try {
					//Kiprobalja, hogy konvertalhato-e
					Object value = VariableParametersRandomStringComponent.this.type.getParameterClass(0).getConstructor(String.class).newInstance(possibleValue);
					parameterList.set( PARAMETERORDER_LENGTH, value );
					goodValue = possibleValue;
					
				} catch (Exception e) {
					text.setText( goodValue );
					return false;
				}				
				return true;
			}
		});
		
		gridY=0;
		//c = new GridBagConstraints();		
		//c.insets = new Insets(0,0,0,0);
		
		c.gridy = 0;
		c.gridx = 1;
		c.gridwidth = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0;
		c.anchor = GridBagConstraints.WEST;
		this.add( labelLength, c );
		
		gridY++;
		c.gridy = 1;
		c.gridx = 1;
		c.gridwidth = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0;
		c.anchor = GridBagConstraints.WEST;
		this.add( fieldLength, c );
		
	}	
	
	@Override
	public void setEnableModify(boolean enable) {
		fieldSample.setEditable( enable );		
		fieldLength.setEditable( enable );
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
