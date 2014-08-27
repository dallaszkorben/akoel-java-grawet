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

public class VariableParametersRandomIntegerComponent extends JPanel implements VariableParametersComponentInterface{

	private static final long serialVersionUID = 9007331207402054910L;
	
	private static final String DEFAULT_FROM = "1";
	private static final String DEFAULT_TO = "100";
	private static final int PARAMETERORDER_FROM = 0;
	private static final int PARAMETERORDER_TO = 1;
	
	private JTextField fieldFrom;
	private JTextField fieldTo;
	private VariableType type;
	
	private ArrayList<Object> parameterList;

	/**
	 * Uj lista
	 * 
	 * @param type
	 */
	public VariableParametersRandomIntegerComponent( VariableType type ){
		super();

		//parameter lista letrehozasa es feltoltese default ertekekkel
		this.parameterList = new ArrayList<>();
		this.parameterList.add( DEFAULT_FROM );
		this.parameterList.add( DEFAULT_TO );
		
		common( type );		
		
	}
	
	/**
	 * Letezo lista
	 * 
	 * @param type
	 * @param parameterList
	 */
	public VariableParametersRandomIntegerComponent( VariableType type, ArrayList<Object> parameterList ){
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
		
		JLabel labelFrom = new JLabel( CommonOperations.getTranslation("editor.title.variabletype.randominteger.from") );
		
		fieldFrom = new JTextField( parameterList.get(PARAMETERORDER_FROM).toString());
		fieldFrom.setColumns(5);
		fieldFrom.setInputVerifier(new InputVerifier() {
			String goodValue = DEFAULT_FROM;
			
			@Override
			public boolean verify(JComponent input) {
				JTextField text = (JTextField)input;
				String possibleValue = text.getText();

				try {
					//Kiprobalja, hogy konvertalhato-e
					Object value = VariableParametersRandomIntegerComponent.this.type.getParameterClass(PARAMETERORDER_FROM).getConstructor(String.class).newInstance(possibleValue);
					parameterList.set( PARAMETERORDER_FROM, value );
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
		c.weightx = 0;
		c.anchor = GridBagConstraints.WEST;
		this.add( labelFrom, c );
		
		gridY++;
		c.gridy = 1;
		c.gridx = 0;
		c.gridwidth = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0;
		c.anchor = GridBagConstraints.WEST;
		this.add( fieldFrom, c );		

		//
		// Length field
		//
		JLabel labelTo = new JLabel( CommonOperations.getTranslation("editor.title.variabletype.randominteger.to") );

		fieldTo = new JTextField( parameterList.get(PARAMETERORDER_TO).toString());
		fieldTo.setColumns(5);
		fieldTo.setInputVerifier(new InputVerifier() {
			String goodValue = DEFAULT_TO;
			
			@Override
			public boolean verify(JComponent input) {
				JTextField text = (JTextField)input;
				String possibleValue = text.getText();

				try {
					//Kiprobalja, hogy konvertalhato-e
					Object value = VariableParametersRandomIntegerComponent.this.type.getParameterClass(PARAMETERORDER_TO).getConstructor(String.class).newInstance(possibleValue);
					parameterList.set( PARAMETERORDER_TO, value );
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
		this.add( labelTo, c );
		
		gridY++;
		c.gridy = 1;
		c.gridx = 1;
		c.gridwidth = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0;
		c.anchor = GridBagConstraints.WEST;
		this.add( fieldTo, c );
		
		//Kitolto
		gridY++;
		c.gridy = 1;
		c.gridx = 2;
		c.gridwidth = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.anchor = GridBagConstraints.WEST;
		this.add( new JLabel(), c );
		
	}	
	
	@Override
	public void setEnableModify(boolean enable) {
		fieldFrom.setEditable( enable );		
		fieldTo.setEditable( enable );
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
