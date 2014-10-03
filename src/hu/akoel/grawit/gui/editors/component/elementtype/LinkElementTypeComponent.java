package hu.akoel.grawit.gui.editors.component.elementtype;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.operations.ClickOperation;
import hu.akoel.grawit.core.operations.CompareBaseElementOperation;
import hu.akoel.grawit.core.operations.CompareStringOperation;
import hu.akoel.grawit.core.operations.CompareVariableElementOperation;
import hu.akoel.grawit.core.operations.ElementOperationInterface;
import hu.akoel.grawit.core.operations.GainTextPatternOperation;
import hu.akoel.grawit.core.operations.OutputValueOperation;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.variable.VariableRootDataModel;
import hu.akoel.grawit.enums.list.CompareTypeListEnum;
import hu.akoel.grawit.enums.list.ElementTypeListEnum;
import hu.akoel.grawit.enums.list.elementtypeoperations.LinkElementTypeOperationsListEnum;
import hu.akoel.grawit.gui.editors.component.treeselector.BaseElementTreeSelectorComponent;
import hu.akoel.grawit.gui.editors.component.treeselector.VariableTreeSelectorComponent;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

public class LinkElementTypeComponent<E extends LinkElementTypeOperationsListEnum> extends ElementTypeComponentInterface<E>{

	private static final long serialVersionUID = -6108131072338954554L;

	//Type
	private JTextField fieldType;
	private JLabel labelType;
	
	//Operation
	private JComboBox<E> comboOperationList;
	private JLabel labelOperations;
	
	//Pattern
	private JTextField fieldPattern;	
	private JLabel labelPattern;
	
	//Message - Mezo ertekenek megjelenitese
	private JLabel labelMessage;
	private JTextField fieldMessage;
	
	//Variable selector - Mezo kitoltes
	private JLabel labelVariableSelector;
	private VariableTreeSelectorComponent fieldVariableSelector;
	
	//BaseElement selector - Mezo kitoltes
	private JLabel labelBaseElementSelector;
	private BaseElementTreeSelectorComponent fieldBaseElementSelector;
	
	//String - Mezo kitoltes
	private JLabel labelString;
	private JTextField fieldString;
	
	//Compare type
	private JLabel labelCompareType;
	private JComboBox<CompareTypeListEnum> comboCompareTypeList;
	
	private JLabel labelFiller;
	
	/**
	 * Uj
	 * 
	 */
	public LinkElementTypeComponent( ElementTypeListEnum elementType, BaseRootDataModel baseRootDataModel, VariableRootDataModel variableRootDataModel ){
		super();

		common( elementType, null, baseRootDataModel, variableRootDataModel );
		
	}
	
	/**
	 * 
	 * Mar letezo
	 * 
	 * @param key
	 * @param value
	 */
	public LinkElementTypeComponent( ElementTypeListEnum elementType , ElementOperationInterface elementOperation, BaseRootDataModel baseRootDataModel, VariableRootDataModel variableRootDataModel ){
		super();
		
		common( elementType, elementOperation, baseRootDataModel, variableRootDataModel );		
		
	}
	
	private void common( ElementTypeListEnum elementType, ElementOperationInterface elementOperation, BaseRootDataModel baseRootDataModel, VariableRootDataModel variableRootDataModel ){
		
		labelType = new JLabel( CommonOperations.getTranslation("editor.label.param.type") + ": ");
		labelOperations = new JLabel( CommonOperations.getTranslation("editor.label.param.operation") + ": ");
		labelPattern = new JLabel( CommonOperations.getTranslation("editor.label.param.pattern") + ": ");
		labelMessage = new JLabel( CommonOperations.getTranslation("editor.label.param.message") + ": ");
		labelString = new JLabel( CommonOperations.getTranslation("editor.label.param.string") + ": ");
		labelVariableSelector = new JLabel( CommonOperations.getTranslation("editor.label.param.variable") + ": ");
		labelBaseElementSelector = new JLabel( CommonOperations.getTranslation("editor.label.param.baseelement") + ": ");
		labelCompareType = new JLabel( CommonOperations.getTranslation("editor.label.param.comparetype") + ": ");
		labelFiller = new JLabel();
		
		fieldType = new JTextField( elementType.getTranslatedName() );
		fieldType.setEditable(false);		
		fieldPattern = new JTextField();
		fieldMessage = new JTextField();
		
		//OPERATION
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
		
		//COMPARE TYPE
		comboCompareTypeList = new JComboBox<CompareTypeListEnum>();
		for( int i = 0; i < CompareTypeListEnum.getSize(); i++ ){
			comboCompareTypeList.addItem( CompareTypeListEnum.getCompareTypeByIndex(i) );
		}
						
		//Azert kell, hogy a setEditable() hatasara ne szurkuljon el a felirat
		comboCompareTypeList.setRenderer(new CompareTypeRenderer());
				
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
		comboCompareTypeList.setSelectedIndex( -1 );		
		
		//Valtozok letrehozase
		fieldVariableSelector = new VariableTreeSelectorComponent( variableRootDataModel );
		fieldBaseElementSelector = new BaseElementTreeSelectorComponent( baseRootDataModel );
		fieldString = new JTextField( "" );
		
		//Default value for CompareType
		comboCompareTypeList.setSelectedIndex( CompareTypeListEnum.EQUAL.getIndex() );
		
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
				
			}else if( elementOperation instanceof CompareVariableElementOperation ){
				
				fieldVariableSelector = new VariableTreeSelectorComponent( variableRootDataModel, ((CompareVariableElementOperation)elementOperation).getVariableElement() );				
				comboOperationList.setSelectedIndex(E.COMPARE_VARIABLE.getIndex());
				comboCompareTypeList.setSelectedIndex( ((CompareVariableElementOperation)elementOperation).getCompareType().getIndex() );

			}else if( elementOperation instanceof CompareBaseElementOperation ){
								
				fieldBaseElementSelector = new BaseElementTreeSelectorComponent( baseRootDataModel, ((CompareBaseElementOperation)elementOperation).getBaseElement() );
				comboCompareTypeList.setSelectedIndex( ((CompareBaseElementOperation)elementOperation).getCompareType().getIndex() );
				comboOperationList.setSelectedIndex(E.COMPARE_ELEMENT.getIndex());
				

			}else if( elementOperation instanceof CompareStringOperation ){
								
				fieldString.setText( ((CompareStringOperation)elementOperation).getStringToShow() );
				comboCompareTypeList.setSelectedIndex( ((CompareStringOperation)elementOperation).getCompareType().getIndex() );
				comboOperationList.setSelectedIndex(E.COMPARE_STRING.getIndex());
		
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
		
		fieldPattern.setEditable( enable );
		
		fieldMessage.setEditable( enable );
		
		fieldString.setEditable( enable );

		fieldBaseElementSelector.setEnableModify(enable);
		
		fieldVariableSelector.setEnableModify( enable );

		comboCompareTypeList.setEnabled( enable );
		
		
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
		this.remove( labelMessage );		
		this.remove( fieldMessage );
		this.remove( labelBaseElementSelector );
		this.remove( fieldBaseElementSelector );
		this.remove( labelString );
		this.remove( fieldString );
		this.remove( labelVariableSelector );
		this.remove( fieldVariableSelector );	
		this.remove( fieldMessage );
		this.remove( labelMessage );
		this.remove( comboCompareTypeList );
		this.remove( labelCompareType );
		
		//Compare element
		if( selectedOperation.equals( E.COMPARE_ELEMENT ) ){
			
			c.gridy = 0;
			c.gridx = 4;
			c.gridwidth = 1;
			c.weighty = 0;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 0;
			c.anchor = GridBagConstraints.WEST;
			this.add( labelBaseElementSelector, c );
		
			c.gridx = 5;
			c.weightx = 1;
			this.add( fieldBaseElementSelector, c );
			
		//Compare variable
		}else if( selectedOperation.equals( E.COMPARE_VARIABLE ) ){
			
			c.gridy = 0;
			c.gridx = 4;
			c.gridwidth = 1;
			c.weighty = 0;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 0;
			c.anchor = GridBagConstraints.WEST;
			this.add( labelVariableSelector, c );
		
			c.gridx = 5;
			c.weightx = 1;
			this.add( fieldVariableSelector, c );
			
		//Compare string
		}else if( selectedOperation.equals( E.COMPARE_STRING ) ){
		
			c.gridy = 0;
			c.gridx = 4;
			c.gridwidth = 1;
			c.weighty = 0;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 0;
			c.anchor = GridBagConstraints.WEST;
			this.add( labelString, c );
		
			c.gridx = 5;
			c.weightx = 1;
			this.add( fieldString, c );
		
		//CLICK
		}else if( selectedOperation.equals( E.CLICK ) ){
		
			//Filler
			c.gridy = 0;
			c.gridx = 4;
			c.gridwidth = 1;
			c.weighty = 0;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 1;
			c.anchor = GridBagConstraints.WEST;
			this.add( labelFiller, c );
			
		//OUTPUT
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
			
		//GAIN
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
		
		//Compare element
		if( selectedOperation.equals( E.COMPARE_ELEMENT ) || selectedOperation.equals( E.COMPARE_VARIABLE ) || selectedOperation.equals( E.COMPARE_STRING ) ){
				
			c.gridy = 1;
			c.gridx = 2;
			c.gridwidth = 1;
			c.weighty = 0;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 0;
			c.anchor = GridBagConstraints.WEST;
			this.add( labelCompareType, c );
			
			c.gridx = 3;
			c.weightx = 1;
			this.add( comboCompareTypeList, c );
							
		}		
		
		this.revalidate();
		this.repaint();
	}
	
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
			
		//Compare element
		}else if( comboOperationList.getSelectedIndex() ==  E.COMPARE_ELEMENT.getIndex() ){
			return new CompareBaseElementOperation( fieldBaseElementSelector.getSelectedDataModel(), (CompareTypeListEnum)(comboCompareTypeList.getSelectedItem()) );
						
		//Compare variable
		}else if(comboOperationList.getSelectedIndex() ==  E.COMPARE_VARIABLE.getIndex() ){
			return new CompareVariableElementOperation( fieldVariableSelector.getSelectedDataModel(), (CompareTypeListEnum)(comboCompareTypeList.getSelectedItem()) );
						
		//Compare string
		}else if( comboOperationList.getSelectedIndex() ==  E.COMPARE_STRING.getIndex() ){
			return new CompareStringOperation( fieldString.getText(), (CompareTypeListEnum)(comboCompareTypeList.getSelectedItem()) );

		}
		
		return null;
	
	}
	
	class CompareTypeRenderer extends BasicComboBoxRenderer {

		private static final long serialVersionUID = 321816528340469926L;

		@Override
        public Component getListCellRendererComponent(@SuppressWarnings("rawtypes") JList list, Object value,   int index, boolean isSelected, boolean cellHasFocus) {

                @SuppressWarnings("unchecked")
                Component c = super.getListCellRendererComponent(list, ((CompareTypeListEnum)value).getTranslatedName(), index, isSelected, cellHasFocus);

                return c;
        }
	}    

}
