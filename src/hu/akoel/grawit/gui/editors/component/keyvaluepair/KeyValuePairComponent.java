package hu.akoel.grawit.gui.editors.component.keyvaluepair;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.gui.editors.component.EditorComponentInterface;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

public class KeyValuePairComponent extends JPanel implements EditorComponentInterface{

	private static final long serialVersionUID = -6108131072338954554L;

	private JComboBox<KeyValuePairValueTypeInterface> fieldValueType;
	private JTextField fieldKey;
	
	private JLabel labelKey;
	private JLabel labelValue;	
	private JLabel labelValueType;
	private JPanel valueContainer;

	public String getKey(){
		return fieldKey.getText().trim();
	}
	
	public String getLabelKeyText(){
		return labelKey.getText();
	}
	
	public String getLabelValueText(){
		return labelValue.getText();
	}
	
	public Object getValue(){
		return ((KeyValuePairValueTypeInterface)valueContainer.getComponent(0)).getValue();
	}
	
	/**
	 * Uj
	 * 
	 */
	public KeyValuePairComponent( ){
		super();

		common( "", "" );
		
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
	KeyValuePairIntegerValue integerElement;
		
	private void common( String key, Object value ){
		
		labelKey = new JLabel( CommonOperations.getTranslation("editor.label.key"));
		labelValue = new JLabel( CommonOperations.getTranslation("editor.label.value"));
		labelValueType = new JLabel( CommonOperations.getTranslation("editor.label.valuetype"));
		
		valueContainer = new JPanel();
		valueContainer.setLayout( new BorderLayout() );
			
		this.setLayout( new GridBagLayout() );
			
		stringElement = new KeyValuePairStringValue( );
		booleanElement = new KeyValuePairBooleanValue( );
		integerElement = new KeyValuePairIntegerValue( );
		
		//Key feltoltese
		fieldKey = new JTextField( key );
		
		//Value Type feltoltese
		fieldValueType = new JComboBox<KeyValuePairValueTypeInterface>();
		
		fieldValueType.addItemListener( new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {

				int index = fieldValueType.getSelectedIndex();					

				//Ha megvaltoztattam a tipust
				if( e.getStateChange() == java.awt.event.ItemEvent.SELECTED ){ 
					
					if( fieldValueType.getItemAt(index).equals( stringElement ) ){
						setValueContainer( stringElement );
						
					}else if( fieldValueType.getItemAt(index).equals( booleanElement ) ){
						setValueContainer( booleanElement );
						
					}else if( fieldValueType.getItemAt(index).equals( integerElement ) ){
						setValueContainer( integerElement );
						
					}		
				}				
			}
		});	
		fieldValueType.addItem( stringElement );
		fieldValueType.addItem( booleanElement );
		fieldValueType.addItem( integerElement );
		
		//Kenyszeritem, hogy a kovetkezo setSelectedItem() hatasara vegrehajtsa a az itemStateChanged() metodust
		fieldValueType.setSelectedIndex(-1);
		if( value instanceof Boolean ){
			booleanElement.setValue((Boolean)value);
			fieldValueType.setSelectedItem( booleanElement );
		}else if( value instanceof String ){
			stringElement.setValue((String)value);
			fieldValueType.setSelectedItem( stringElement );
		}else if( value instanceof Integer ){
			integerElement.setValue((Integer)value);
			fieldValueType.setSelectedItem( integerElement );
		}
		
		//Azert kell, hogy a setEditable() hatasara ne szurkuljon el a felirat
		fieldValueType.setRenderer(new MyRenderer());
		
		int gridY = 1;
		int gridX = 0;
		GridBagConstraints c = new GridBagConstraints();		
		c.insets = new Insets(0,0,0,0);

		//Key
		c.gridy = gridY;
		c.gridx = gridX;
		c.gridwidth = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.6;
		c.anchor = GridBagConstraints.WEST;
		this.add( fieldKey, c );
		
		c.gridy = 0;
		this.add( labelKey, c );
		c.gridy = 1;
		
		//Value
		gridX++;
		c.gridy = gridY;
		c.gridx = gridX;
		c.gridwidth = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.2;
		c.anchor = GridBagConstraints.WEST;
		this.add( valueContainer, c );
		
		c.gridy = 0;
		this.add( labelValue, c );
		c.gridy = 1;
		
		//Type
		gridX++;
		c.gridy = gridY;
		c.gridx = gridX;
		c.gridwidth = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0;
		c.anchor = GridBagConstraints.WEST;
		this.add( fieldValueType, c );
		
		c.gridy = 0;
		this.add( labelValueType, c );
		c.gridy = 1;

	}	
	
	@Override
	public void setEnableModify(boolean enable) {
		fieldKey.setEditable( enable );		
		
		fieldValueType.setEnabled( enable );
		fieldValueType.setEditable( false );		
		
		stringElement.setEnableModify(enable);
		booleanElement.setEnableModify(enable);

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
	
	class MyRenderer extends BasicComboBoxRenderer {

		private static final long serialVersionUID = -4562181616721578685L;

		@Override
		public Component getListCellRendererComponent(JList list, Object value,	int index, boolean isSelected, boolean cellHasFocus) {

			Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

			return c;
		}
	}

}
