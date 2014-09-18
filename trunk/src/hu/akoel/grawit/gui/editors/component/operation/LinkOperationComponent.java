package hu.akoel.grawit.gui.editors.component.operation;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.enums.list.ListEnumElementType;
import hu.akoel.grawit.enums.list.operation.LinkOperationListEnum;
import hu.akoel.grawit.enums.list.operation.OperationListEnumInterface;
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

public class LinkOperationComponent extends JPanel implements EditorComponentInterface{

	private static final long serialVersionUID = -6108131072338954554L;

	private JTextField fieldType;
	private JComboBox<LinkOperationListEnum> comboOperationList;
	private JTextField fieldPattern;
//	private JPanel parameterContainer;
		
	private JLabel labelType;
	private JLabel labelOperations;	
	private JLabel labelPattern;
	
	public LinkOperationListEnum getSelectedOperation(){
		return(LinkOperationListEnum)comboOperationList.getSelectedItem();
	}
	
	public String getPattern(){
		return fieldPattern.getText();
	}
	
	/**
	 * Uj
	 * 
	 */
	public LinkOperationComponent( ListEnumElementType elementType ){
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
	public LinkOperationComponent( ListEnumElementType elementType, OperationListEnumInterface operation ){
		super();
		
		common( elementType, operation );		
		
	}
	
	private void common( ListEnumElementType elementType, OperationListEnumInterface operation ){
		
//		parameterContainer = new JPanel();
//		parameterContainer.setLayout( new BorderLayout() );
		
		labelType = new JLabel("Tipus: ");
		labelOperations = new JLabel("Operation: ");
		labelPattern = new JLabel("Pattern: ");
		
		fieldType = new JTextField( elementType.getTranslatedName() );
		fieldType.setEditable(false);
		
		comboOperationList = new JComboBox<>();
		for( int i = 0; i < LinkOperationListEnum.getSize(); i++ ){
			comboOperationList.addItem( LinkOperationListEnum.getElementFieldOperationByIndex(i) );
		}
		
		comboOperationList.addItemListener( new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {

				int index = comboOperationList.getSelectedIndex();					

				//Ha megvaltoztattam a tipust
				if( e.getStateChange() == java.awt.event.ItemEvent.SELECTED ){ 
					
					if( comboOperationList.getItemAt(index).equals( LinkOperationListEnum.CLICK ) ){
						setValueContainer( false );
						
					}else if( comboOperationList.getItemAt(index).equals( LinkOperationListEnum.GAINTEXTPATTERN ) ){
						setValueContainer( true );
						
					}		
				}				
			}
		});	
		
		//Azert kell, hogy a setEditable() hatasara ne szurkuljon el a felirat
		comboOperationList.setRenderer(new MyRenderer());
			
		//Kenyszeritem, hogy a kovetkezo setSelectedItem() hatasara vegrehajtsa a az itemStateChanged() metodust
		comboOperationList.setSelectedIndex(-1);
		
		//Kezdo ertek beallitasa
		if( null == operation ){
			comboOperationList.setSelectedIndex(LinkOperationListEnum.CLICK.getIndex());
		}else{
			
			if( operation.equals( LinkOperationListEnum.CLICK ) ){
				
				
			}else if( operation.equals( LinkOperationListEnum.GAINTEXTPATTERN ) ){
				operation.g
			}
			comboOperationList.setSelectedItem( operation );
		}
		

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
		
		int gridY = 0;
		int gridX = 0;
		GridBagConstraints c = new GridBagConstraints();		
		c.insets = new Insets(0,0,0,0);

		//Type
		c.gridy = gridY;
		c.gridx = gridX;
		c.gridwidth = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.6;
		c.anchor = GridBagConstraints.WEST;
		this.add( labelType, c );
		
		c.gridx = 1;
		this.add( fieldType, c );
		
		//Operation
		gridY++;
		gridX = 0;
		c.gridy = gridY;
		c.gridx = gridX;
		c.gridwidth = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.2;
		c.anchor = GridBagConstraints.WEST;
		this.add( labelOperations, c );
		
		c.gridx = 1;
		this.add( comboOperationList, c );
		c.gridy = 1;
		
		//Pattern

	}	
	
	
	
	@Override
	public void setEnableModify(boolean enable) {
		comboOperationList.setEditable( enable );		
		
		if( null != fieldPattern  && fieldPattern.isVisible() ){
			fieldPattern.setEditable( enable );
		}
	}

	@Override
	public Component getComponent() {
		return this;
	}

	private void setValueContainer( boolean show ){
		this.remove( labelPattern );
		this.remove( fieldPattern );
		
		if( show ){
			GridBagConstraints c = new GridBagConstraints();		
			c.insets = new Insets(0,0,0,0);
		
			c.gridy = 2;
			c.gridx = 0;
			c.gridwidth = 1;
			c.weighty = 0;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 0;
			c.anchor = GridBagConstraints.WEST;
			this.add( labelPattern, c );
		
			c.gridx = 1;
			this.add( comboOperationList, c );
			c.gridy = 1;
		}
		
		this.revalidate();
		this.repaint();
	}
	
	class MyRenderer extends BasicComboBoxRenderer {

		private static final long serialVersionUID = -4562181616721578685L;

		@Override
		public Component getListCellRendererComponent(JList list, Object value,	int index, boolean isSelected, boolean cellHasFocus) {

			Component c = super.getListCellRendererComponent(list, ((LinkOperationListEnum)value).getTranslatedName(), index, isSelected, cellHasFocus);

			return c;
		}
	}

}
