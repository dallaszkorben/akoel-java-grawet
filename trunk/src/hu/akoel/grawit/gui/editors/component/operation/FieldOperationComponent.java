package hu.akoel.grawit.gui.editors.component.operation;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.enums.list.ElementTypeListEnum;
import hu.akoel.grawit.enums.list.operation.LinkOperationListEnum;
import hu.akoel.grawit.enums.list.operation.ListEnumInterface;
import hu.akoel.grawit.gui.editors.component.EditorComponentInterface;
import hu.akoel.grawit.gui.editors.component.keyvaluepair.KeyValuePairBooleanValue;
import hu.akoel.grawit.gui.editors.component.keyvaluepair.KeyValuePairIntegerValue;
import hu.akoel.grawit.gui.editors.component.keyvaluepair.KeyValuePairStringValue;
import hu.akoel.grawit.gui.editors.component.keyvaluepair.KeyValuePairValueTypeInterface;

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

public class FieldOperationComponent extends JPanel implements EditorComponentInterface{

	private static final long serialVersionUID = -6108131072338954554L;

	private JTextField fieldType;
	private JComboBox<LinkOperationListEnum> comboOperationList;
		
	private JLabel labelType;
	private JLabel labelOperations;	

	public LinkOperationListEnum getSelectedOperation(){
		return(LinkOperationListEnum)comboOperationList.getSelectedItem();
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
	public FieldOperationComponent( ElementTypeListEnum elementType ){
		super();

		common( elementType, null );
		
	}
	
	/**
	 * 
	 * Mar letezo
	 * 
	 * @param key
	 * @param value
	 */
	public FieldOperationComponent( ElementTypeListEnum elementType, ListEnumInterface operation ){
		super();
		
		common( elementType, operation );		
		
	}
	
	private void common( ElementTypeListEnum elementType, ListEnumInterface operation ){
		
		labelType = new JLabel("Tipus: ");
		labelOperations = new JLabel("Operation: ");
		
		fieldType = new JTextField( elementType.getTranslatedName() );		
		comboOperationList = new JComboBox<>();
		for( int i = 0; i < LinkOperationListEnum.getSize(); i++ ){
			comboOperationList.addItem( LinkOperationListEnum.getElementFieldOperationByIndex(i) );
		}
		
		comboOperationList.addItemListener( new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {

				int index = operationList.getSelectedIndex();					

				//Ha megvaltoztattam a tipust
				if( e.getStateChange() == java.awt.event.ItemEvent.SELECTED ){ 
					
					if( operationList.getItemAt(index).equals( stringElement ) ){
						setValueContainer( stringElement );
						
					}else if( operationList.getItemAt(index).equals( booleanElement ) ){
						setValueContainer( booleanElement );
						
					}else if( operationList.getItemAt(index).equals( integerElement ) ){
						setValueContainer( integerElement );
						
					}		
				}				
			}
		});	
		
		//Azert kell, hogy a setEditable() hatasara ne szurkuljon el a felirat
		comboOperationList.setRenderer(new MyRenderer());
				
		operationList.addItem( stringElement );
		operationList.addItem( booleanElement );
		operationList.addItem( integerElement );
		
		//Kenyszeritem, hogy a kovetkezo setSelectedItem() hatasara vegrehajtsa a az itemStateChanged() metodust
		operationList.setSelectedIndex(-1);
		if( value instanceof Boolean ){
			booleanElement.setValue((Boolean)value);
			operationList.setSelectedItem( booleanElement );
		}else if( value instanceof String ){
			stringElement.setValue((String)value);
			operationList.setSelectedItem( stringElement );
		}else if( value instanceof Integer ){
			integerElement.setValue((Integer)value);
			operationList.setSelectedItem( integerElement );
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
		this.add( operationList, c );
		
		c.gridy = 0;
		this.add( labelValueType, c );
		c.gridy = 1;

	}	
	
	@Override
	public void setEnableModify(boolean enable) {
		fieldKey.setEditable( enable );		
		
		operationList.setEnabled( enable );
		operationList.setEditable( false );		
		
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
