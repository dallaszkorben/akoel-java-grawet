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

public class ConstantRandomStringComponent extends JPanel implements ConstantComponentInterface{

	private static final long serialVersionUID = -9146846149045859640L;
	
	private static final String DEFAULT_SAMPLE = "abcdefghijklmnopqrstuvwxyz0123456789";
	private static final String DEFAULT_LENGTH = "15";
	private static final int PARAMETERORDER_SAMPLE = 0;
	private static final int PARAMETERORDER_LENGTH = 1;
	
	private JTextField fieldSample;
	private JTextField fieldLength;
	private ConstantTypeListEnum type;
	
	private ArrayList<Object> parameterList;

	/**
	 * Uj lista
	 * 
	 * @param type
	 */
	public ConstantRandomStringComponent( ConstantTypeListEnum type ){
		super();

		//parameter lista letrehozasa es feltoltese default ertekekkel
		this.parameterList = new ArrayList<>();
		this.parameterList.add( DEFAULT_SAMPLE );
		this.parameterList.add( DEFAULT_LENGTH );
		
		common( type );		
		
	}
	
	/**
	 * Letezo lista
	 * 
	 * @param type
	 * @param parameterList
	 */
	public ConstantRandomStringComponent( ConstantTypeListEnum type, ArrayList<Object> parameterList ){
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
		
		JLabel labelSample = new JLabel( CommonOperations.getTranslation("editor.label.variable.parametertype.randomstring.samplestring") );
		
		fieldSample = new JTextField( parameterList.get(PARAMETERORDER_SAMPLE).toString());
		
		fieldSample.setInputVerifier(new InputVerifier() {
			String goodValue = DEFAULT_SAMPLE;
			
			@Override
			public boolean verify(JComponent input) {
				JTextField text = (JTextField)input;
				String possibleValue = text.getText();

				try {
					//Kiprobalja, hogy konvertalhato-e
					Object value = ConstantRandomStringComponent.this.type.getParameterClass(PARAMETERORDER_SAMPLE).getConstructor(String.class).newInstance(possibleValue);
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
		JLabel labelLength = new JLabel( CommonOperations.getTranslation("editor.label.variable.parametertype.randomstring.length") );

		fieldLength = new JTextField( parameterList.get(PARAMETERORDER_LENGTH).toString());
		fieldLength.setColumns(5);
		fieldLength.setInputVerifier(new InputVerifier() {
			String goodValue = DEFAULT_LENGTH;
			
			@Override
			public boolean verify(JComponent input) {
				JTextField text = (JTextField)input;
				String possibleValue = text.getText();

				try {
					//Kiprobalja, hogy konvertalhato-e
					Object value = ConstantRandomStringComponent.this.type.getParameterClass(PARAMETERORDER_LENGTH).getConstructor(String.class).newInstance(possibleValue);
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
