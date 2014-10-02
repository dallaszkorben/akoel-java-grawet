package hu.akoel.grawit.gui.editors.component.elementtype;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.operations.ClickOperation;
import hu.akoel.grawit.core.operations.ElementOperationInterface;
import hu.akoel.grawit.core.operations.GainTextPatternOperation;
import hu.akoel.grawit.core.operations.OutputValueOperation;
import hu.akoel.grawit.enums.list.ElementTypeListEnum;
import hu.akoel.grawit.enums.list.elementtypeoperations.LinkElementTypeOperationsListEnum;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class LinkElementTypeComponent<E extends LinkElementTypeOperationsListEnum> extends ElementTypeComponentInterface<E>{

	private static final long serialVersionUID = -6108131072338954554L;

	private JTextField fieldType;
	private JLabel labelType;
	
	private JComboBox<E> comboOperationList;
	private JLabel labelOperations;
	
	private JTextField fieldPattern;	
	private JLabel labelPattern;
	
	//Message - Mezo ertekenek megjelenitese
	private JLabel labelMessage;
	private JTextField fieldMessage;
	
	private JLabel labelFiller;
	
	/**
	 * Uj
	 * 
	 */
	public LinkElementTypeComponent( ElementTypeListEnum elementType ){
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
	public LinkElementTypeComponent( ElementTypeListEnum elementType , ElementOperationInterface elementOperation ){
		super();
		
		common( elementType, elementOperation );		
		
	}
	
	private void common( ElementTypeListEnum elementType, ElementOperationInterface elementOperation ){
		
		labelType = new JLabel( CommonOperations.getTranslation("editor.label.param.type") + ": ");
		labelOperations = new JLabel( CommonOperations.getTranslation("editor.label.param.operation") + ": ");
		labelPattern = new JLabel( CommonOperations.getTranslation("editor.label.param.pattern") + ": ");
		labelFiller = new JLabel();
		
		fieldType = new JTextField( elementType.getTranslatedName() );
		fieldType.setEditable(false);		
		fieldPattern = new JTextField();
		fieldMessage = new JTextField();
		
		comboOperationList = new JComboBox<>();
		for( int i = 0; i < E.getSize(); i++ ){
			comboOperationList.addItem( (E) E.getElementFieldOperationByIndex(i) );
		}
		
		comboOperationList.addItemListener( new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {

				int index = comboOperationList.getSelectedIndex();					

				//Ha megvaltoztattam a tipust
				if( e.getStateChange() == java.awt.event.ItemEvent.SELECTED ){ 
					
					setValueContainer( comboOperationList.getItemAt(index) );
					
				}				
			}
		});	
		
		//Azert kell, hogy a setEditable() hatasara ne szurkuljon el a felirat
		comboOperationList.setRenderer(new ElementTypeComponentRenderer());
		
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
		
		this.setLayout( new GridBagLayout() );
		
		GridBagConstraints c = new GridBagConstraints();		
		c.insets = new Insets(0,0,0,0);

		//Type
		c.gridy = 0;
		c.gridx = 0;
		c.gridwidth = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0;
		c.anchor = GridBagConstraints.WEST;
		this.add( labelType, c );
		
		c.gridx = 1;
		c.weightx = 0;
		this.add( fieldType, c );
		
		//Operation
		c.gridy = 0;
		c.gridx = 2;
		c.gridwidth = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0;
		c.anchor = GridBagConstraints.WEST;
		this.add( labelOperations, c );
		
		c.gridx = 3;
		c.weightx = 0;
		this.add( comboOperationList, c );
		c.gridy = 1;
	
		//Filler
/*		c.gridy = 0;
		c.gridx = 4;
		c.gridwidth = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.anchor = GridBagConstraints.WEST;
		this.add( labelFiller, c );
*/
		
		//Kenyszeritem, hogy a kovetkezo setSelectedItem() hatasara vegrehajtsa a az itemStateChanged() metodust
		comboOperationList.setSelectedIndex(-1);
		
		//Kezdo ertek beallitasa
		if( null == elementOperation ){
			comboOperationList.setSelectedIndex(E.CLICK.getIndex());
		}else{
			
			if( elementOperation instanceof ClickOperation  ){
				
				comboOperationList.setSelectedIndex(E.CLICK.getIndex());
				
			}else if( elementOperation instanceof GainTextPatternOperation ){
				
				comboOperationList.setSelectedIndex(E.GAINTEXTPATTERN.getIndex());
				fieldPattern.setText( ((GainTextPatternOperation)elementOperation).getStringPattern());
				
			}else if ( elementOperation instanceof OutputValueOperation ){
				
				fieldMessage.setText( ((OutputValueOperation)elementOperation).getMessageToShow());
				comboOperationList.setSelectedIndex( E.OUTPUTVALUE.getIndex() );		
				
			}
			
		}
	}	
	
	@Override
	public E getSelectedOperation( ElementTypeListEnum elementType ){
		return(E)comboOperationList.getSelectedItem();
	}
	
	public String getPattern(){
		return fieldPattern.getText();
	}
	
	
	@Override
	public void setEnableModify(boolean enable) {
		comboOperationList.setEnabled( enable );		
		
		if( null != fieldPattern  && fieldPattern.isVisible() ){
			fieldPattern.setEditable( enable );
		}
		
		if( null != fieldMessage  && fieldMessage.isVisible() ){
			fieldMessage.setEditable( enable );
		}
		
	}

	@Override
	public Component getComponent() {
		return this;
	}

	private void setValueContainer( E selectedOperation ){
		
		GridBagConstraints c = new GridBagConstraints();		
		c.insets = new Insets(0,0,0,0);
		
		this.remove( labelPattern );
		this.remove( fieldPattern );
		this.remove( labelFiller );
		this.remove( fieldMessage );
		this.remove( labelMessage );
		
		if( selectedOperation.equals( E.CLICK ) ){
		
			//Filler
			c.gridy = 0;
			c.gridx = 4;
			c.gridwidth = 1;
			c.weighty = 0;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 1;
			c.anchor = GridBagConstraints.WEST;
			this.add( labelFiller, c );
			
		}else if( selectedOperation.equals( E.OUTPUTVALUE ) ){
			
			c.gridy = 0;
			c.gridx = 4;
			c.gridwidth = 1;
			c.weighty = 0;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 0;
			c.anchor = GridBagConstraints.WEST;
			this.add( labelMessage, c );
		
			c.gridx = 5;
			c.weightx = 1;
			this.add( fieldMessage, c );
			
		}else if( selectedOperation.equals( E.GAINTEXTPATTERN ) ){
			
			//Filler
			c.gridy = 0;
			c.gridx = 4;
			c.gridwidth = 1;
			c.weighty = 0;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 0;
			c.anchor = GridBagConstraints.WEST;
			this.add( labelPattern, c );
		
			c.gridx = 5;
			c.weightx = 1;
			this.add( fieldPattern, c );
			
		}		
		
		this.revalidate();
		this.repaint();
	}
	
/*	class MyRenderer extends BasicComboBoxRenderer {

		private static final long serialVersionUID = -4562181616721578685L;

		@Override
		public Component getListCellRendererComponent(JList list, Object value,	int index, boolean isSelected, boolean cellHasFocus) {

			Component c = super.getListCellRendererComponent(list, ((E)value).getTranslatedName(), index, isSelected, cellHasFocus);

			return c;
		}
	}
*/
	@Override
	public ElementOperationInterface getElementOperation() {
		
		//CLICK
		if( comboOperationList.getSelectedIndex() == E.CLICK.getIndex() ){
			return new ClickOperation();
			
		//GAINTEXT
		}else if( comboOperationList.getSelectedIndex() == E.GAINTEXTPATTERN.getIndex() ){
			return new GainTextPatternOperation( fieldPattern.getText() );
		
		//OUTPUTVALUE
		}else if( comboOperationList.getSelectedIndex() == E.OUTPUTVALUE.getIndex() ){
			return new OutputValueOperation( fieldMessage.getText() );
		}
		
		return null;
	
	}

}
