package hu.akoel.grawit.gui.editors.component.elementtype;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.operations.ClearOperation;
import hu.akoel.grawit.core.operations.ClickOperation;
import hu.akoel.grawit.core.operations.ElementOperationInterface;
import hu.akoel.grawit.core.operations.FillElementOperation;
import hu.akoel.grawit.core.operations.FillStringOperation;
import hu.akoel.grawit.core.operations.FillVariableOperation;
import hu.akoel.grawit.core.operations.GainTextPatternOperation;
import hu.akoel.grawit.core.operations.TabOperation;
import hu.akoel.grawit.enums.list.ElementTypeListEnum;
import hu.akoel.grawit.enums.list.elementtypeoperations.FieldElementTypeOperationsListEnum;
import hu.akoel.grawit.enums.list.elementtypeoperations.LinkElementTypeOperationsListEnum;
import hu.akoel.grawit.enums.list.elementtypeoperations.ElementTypeOperationsListEnumInterface;
import hu.akoel.grawit.gui.editors.component.EditorComponentInterface;
import hu.akoel.grawit.gui.editors.component.elementtype.ElementTypeComponentInterface.MyRenderer;
import hu.akoel.grawit.gui.editors.component.keyvaluepair.KeyValuePairBooleanValue;
import hu.akoel.grawit.gui.editors.component.keyvaluepair.KeyValuePairIntegerValue;
import hu.akoel.grawit.gui.editors.component.keyvaluepair.KeyValuePairStringValue;
import hu.akoel.grawit.gui.editors.component.keyvaluepair.KeyValuePairValueTypeInterface;
import hu.akoel.grawit.gui.editors.component.treeselector.BaseElementTreeSelectorComponent;
import hu.akoel.grawit.gui.editors.component.treeselector.VariableTreeSelectorComponent;

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

public class FieldElementTypeComponent<E extends FieldElementTypeOperationsListEnum> extends ElementTypeComponentInterface<E>{

	private static final long serialVersionUID = -6108131072338954554L;

	private JTextField fieldType;
	private JComboBox<E> comboOperationList;
		
	private JLabel labelType;
	private JLabel labelOperations;	
	
	private JLabel labelVariableSelector;
	private VariableTreeSelectorComponent fieldVariableSelector;
	
	private JLabel labelFieldBaseElementSelector;
	private BaseElementTreeSelectorComponent fieldBaseElementSelector;
	
	private JLabel labelString;
	private JTextField fieldString;

	@Override
	public E getSelectedOperation(ElementTypeListEnum elementType) {
		return(E)comboOperationList.getSelectedItem();
	}
	
	/**
	 * Uj
	 * 
	 */
	public FieldElementTypeComponent( ElementTypeListEnum elementType ){
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
	public FieldElementTypeComponent( ElementTypeListEnum elementType, ElementTypeOperationsListEnumInterface operation ){
		super();
		
		common( elementType, operation );		
		
	}
	
	private void common( ElementTypeListEnum elementType, ElementTypeOperationsListEnumInterface elementOperation ){
		
		labelType = new JLabel("Tipus: ");
		labelOperations = new JLabel("Muvelet: ");
		labelString = new JLabel("String: ");
		labelVariableSelector = new JLabel( "Valtozo: " );
		labelFieldBaseElementSelector = new JLabel( "Bazis elem: " );
		
		fieldType = new JTextField( elementType.getTranslatedName() );
		fieldType.setEditable(false);
		
		comboOperationList = new JComboBox<>();
		for( int i = 0; i < LinkElementTypeOperationsListEnum.getSize(); i++ ){
			comboOperationList.addItem( (E) E.getElementFieldOperationByIndex(i) );
		}
		
		comboOperationList.addItemListener( new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {

				int index = comboOperationList.getSelectedIndex();				

				//Ha megvaltoztattam a tipust
				if( e.getStateChange() == java.awt.event.ItemEvent.SELECTED ){ 

					setValueContainer( comboOperationList.getItemAt(index));
					
				}				
			}
		});	
		
				
		//Azert kell, hogy a setEditable() hatasara ne szurkuljon el a felirat
		comboOperationList.setRenderer(new MyRenderer());
				
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

		//Kenyszeritem, hogy a kovetkezo setSelectedItem() hatasara vegrehajtsa a az itemStateChanged() metodust
		comboOperationList.setSelectedIndex(-1);
		
		//Kezdo ertek beallitasa
		if( null == elementOperation ){
			
			comboOperationList.setSelectedIndex(E.CLICK.getIndex());
			
		}else{
			
			if( elementOperation instanceof ClickOperation  ){
				
				comboOperationList.setSelectedIndex(E.CLICK.getIndex());
				
			}else if( elementOperation instanceof TabOperation ){
				
				comboOperationList.setSelectedIndex(E.TAB.getIndex());
				
			}else if( elementOperation instanceof ClearOperation ){
				
				comboOperationList.setSelectedIndex(E.CLEAR.getIndex());
				
			}else if( elementOperation instanceof FillVariableOperation ){
				
				comboOperationList.setSelectedIndex(E.FILL_VARIABLE.getIndex());

			}else if( elementOperation instanceof FillElementOperation ){
				
				comboOperationList.setSelectedIndex(E.FILL_ELEMENT.getIndex());

			}else if( elementOperation instanceof FillStringOperation ){
				
				comboOperationList.setSelectedIndex(E.FILL_STRING.getIndex());

				//fieldPattern.setText( ((GainTextPatternOperation)elementOperation).getStringPattern());
				
			}
			
		}
	}	
	
	
	@Override
	public void setEnableModify(boolean enable) {
		
		comboOperationList.setEnabled( enable );		
		
		if( null != fieldString  && fieldString.isVisible() ){
			fieldString.setEditable( enable );

		}else if( null != fieldBaseElementSelector && fieldBaseElementSelector.isVisible() ){
			fieldBaseElementSelector.setEnableModify(enable);
		
		}else if( null != fieldVariableSelector && fieldVariableSelector.isVisible() ){
			fieldVariableSelector.setEnableModify( enable );
		}
		

	}

	@Override
	public Component getComponent() {
		return this;
	}

	private void setValueContainer( E selected ){
		
		//Fill element
		if( selected.equals( E.FILL_ELEMENT ) ){
			
		//Fill variable
		}else if( selected.equals( E.FILL_VARIABLE ) ){
			
		//Fill string
		}else if( selected.equals( E.FILL_STRING ) ){
		
		//Clear
		}else if( selected.equals( E.CLEAR) ){
			
		//Tab
		}else if( selected.equals( E.TAB ) ){

		//Click
		}else if( selected.equals( E.CLICK ) ){
			
		}		

		this.revalidate();
		this.repaint();
	}

	@Override
	public ElementOperationInterface getElementOperation() {
		
		//Fill element
		if( comboOperationList.getSelectedIndex() ==  E.FILL_ELEMENT.getIndex() ){
			return new FillElementOperation( fieldBaseElementSelector.getSelectedDataModel() );
			
		//Fill variable
		}else if(comboOperationList.getSelectedIndex() ==  E.FILL_VARIABLE.getIndex() ){
			return new FillVariableOperation( fieldVariableSelector.getSelectedDataModel() );
			
		//Fill string
		}else if( comboOperationList.getSelectedIndex() ==  E.FILL_STRING.getIndex() ){
			return new FillStringOperation( fieldString.getText() );
			
		//Clear
		}else if( comboOperationList.getSelectedIndex() ==  E.CLEAR.getIndex() ){
			return new ClearOperation();
			
		//Tab
		}else if( comboOperationList.getSelectedIndex() ==  E.TAB.getIndex() ){
			return new TabOperation();
			
		//Click
		}else if( comboOperationList.getSelectedIndex() ==  E.CLICK.getIndex() ){
			return new ClickOperation();
		}
	
		return null;
	}
}
