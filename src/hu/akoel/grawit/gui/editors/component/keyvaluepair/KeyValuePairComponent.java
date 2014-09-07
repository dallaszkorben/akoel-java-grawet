package hu.akoel.grawit.gui.editors.component.keyvaluepair;

import hu.akoel.grawit.gui.editors.component.EditorComponentInterface;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class KeyValuePairComponent extends JPanel implements EditorComponentInterface{

	private static final long serialVersionUID = -6108131072338954554L;

	private JComboBox<KeyValuePairValueTypeInterface<?>> fieldValueType;
	private JComponent fieldValue;
	private JTextField fieldKey;
	
	private JPanel valueContainer;

	public String getKey(){
		return fieldKey.getText();
	}
	
	public Object getValue(){
		return ((KeyValuePairValueTypeInterface<?>)valueContainer.getComponent(0)).getValue();
	}
	
	/**
	 * Uj
	 * 
	 */
	public KeyValuePairComponent( ){
		super();

		common( "ez.a.key", Boolean.FALSE );
		
	}
	
	/**
	 * 
	 * Mar letezo
	 * 
	 * @param key
	 * @param value
	 */
	public KeyValuePairComponent( String key, Object value ){
		super();
		
		common( key, value );		
		
	}
	
	KeyValuePairStringValue stringElement;
	KeyValuePairBooleanValue booleanElement;
		
	private void common( String key, Object value ){
		
		valueContainer = new JPanel();
		valueContainer.setLayout( new BorderLayout() );
			
		this.setLayout( new GridBagLayout() );
			
		stringElement = new KeyValuePairStringValue( );
		booleanElement = new KeyValuePairBooleanValue( );
		
		//Key feltoltese
		fieldKey = new JTextField( key );
		
		//Value Type feltoltese
		fieldValueType = new JComboBox<KeyValuePairValueTypeInterface<?>>();
		
		fieldValueType.addItemListener( new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {

				int index = fieldValueType.getSelectedIndex();					

				//Ha megvaltoztattam a tipust
				if( e.getStateChange() == java.awt.event.ItemEvent.SELECTED ){ 
					
					if( fieldValueType.getItemAt(index).equals( stringElement ) ){
						setValueContainer( stringElement.getComponent() );
						
					}else if( fieldValueType.getItemAt(index).equals( booleanElement ) ){
						setValueContainer( booleanElement.getComponent() );
						
					}		
				}				
			}
		});	
		fieldValueType.addItem( stringElement );
		fieldValueType.addItem( booleanElement );	
		
		//Kegyszeritem, hogy a kovetkezo setSelectedItem() hatasara vegrehajtsa a az itemStateChanged() metodust
		fieldValueType.setSelectedIndex(-1);
		if( value instanceof Boolean ){
			booleanElement.setValue((Boolean)value);
			fieldValueType.setSelectedItem( booleanElement );
		}else if( value instanceof String ){
			stringElement.setValue((String)value);
			fieldValueType.setSelectedItem( stringElement );
		}
		
		
		//fieldString.setInputVerifier( new CommonOperations.ValueVerifier(parameterList, type, DEFAULT_VALUE, PARAMETERORDER_VALUE) );
		/*fieldString.setInputVerifier(new InputVerifier() {
			String goodValue = "";
			
			@Override
			public boolean verify(JComponent input) {
				JTextField text = (JTextField)input;
				String possibleValue = text.getText();

				try {
					//Kiprobalja, hogy konvertalhato-e
					Object value = VariableParametersStringComponent.this.type.getParameterClass(0).getConstructor(String.class).newInstance(possibleValue);
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
		int gridX = 0;
		GridBagConstraints c = new GridBagConstraints();		
		c.insets = new Insets(0,0,0,0);
		
		c.gridy = gridY;
		c.gridx = gridX;
		c.gridwidth = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.6;
		c.anchor = GridBagConstraints.WEST;
		this.add( fieldKey, c );
		
		gridX++;
		c.gridy = gridY;
		c.gridx = gridX;
		c.gridwidth = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.3;
		c.anchor = GridBagConstraints.WEST;
		this.add( valueContainer, c );
		
		gridX++;
		c.gridy = gridY;
		c.gridx = gridX;
		c.gridwidth = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.2;
		c.anchor = GridBagConstraints.WEST;
		this.add( fieldValueType, c );

	}	
	
	@Override
	public void setEnableModify(boolean enable) {
		fieldKey.setEditable( enable );		
//		fieldValue.setEditable( enable );
		fieldKey.setEditable( enable );
	}

	@Override
	public Component getComponent() {
		return this;
	}

	private void setValueContainer( JComponent component ){
		this.valueContainer.removeAll();
		this.valueContainer.add( component, BorderLayout.CENTER );
		this.revalidate();
		this.repaint();
	}

}
