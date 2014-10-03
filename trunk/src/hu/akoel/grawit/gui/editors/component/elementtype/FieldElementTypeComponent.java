package hu.akoel.grawit.gui.editors.component.elementtype;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.operations.ClearOperation;
import hu.akoel.grawit.core.operations.ClickOperation;
import hu.akoel.grawit.core.operations.CompareBaseElementOperation;
import hu.akoel.grawit.core.operations.CompareStringOperation;
import hu.akoel.grawit.core.operations.CompareVariableElementOperation;
import hu.akoel.grawit.core.operations.ElementOperationInterface;
import hu.akoel.grawit.core.operations.FillBaseElementOperation;
import hu.akoel.grawit.core.operations.FillStringOperation;
import hu.akoel.grawit.core.operations.FillVariableElementOperation;
import hu.akoel.grawit.core.operations.GainTextPatternOperation;
import hu.akoel.grawit.core.operations.OutputValueOperation;
import hu.akoel.grawit.core.operations.TabOperation;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.variable.VariableRootDataModel;
import hu.akoel.grawit.enums.list.CompareTypeListEnum;
import hu.akoel.grawit.enums.list.ElementTypeListEnum;
import hu.akoel.grawit.enums.list.elementtypeoperations.FieldElementTypeOperationsListEnum;
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

public class FieldElementTypeComponent<E extends FieldElementTypeOperationsListEnum> extends ElementTypeComponentInterface<E>{

	private static final long serialVersionUID = -6108131072338954554L;
	
	//Type
	private JLabel labelType;
	private JTextField fieldType;
	
	//Operation
	private JLabel labelOperations;	
	private JComboBox<E> comboOperationList;	
	
	//Pattern
	private JTextField fieldPattern;	
	private JLabel labelPattern;
	
	//Variable selector - Mezo kitoltes
	private JLabel labelVariableSelector;
	private VariableTreeSelectorComponent fieldVariableSelector;
	
	//BaseElement selector - Mezo kitoltes
	private JLabel labelBaseElementSelector;
	private BaseElementTreeSelectorComponent fieldBaseElementSelector;
	
	//String - Mezo kitoltes
	private JLabel labelString;
	private JTextField fieldString;

	//Message - Mezo ertekenek megjelenitese
	private JLabel labelMessage;
	private JTextField fieldMessage;
	
	//Compare type
	private JLabel labelCompareType;
	private JComboBox<CompareTypeListEnum> comboCompareTypeList;
	
	private JLabel labelFiller;
	

	/**
	 * Uj
	 * 
	 */
	public FieldElementTypeComponent( ElementTypeListEnum elementType, BaseRootDataModel baseRootDataModel, VariableRootDataModel variableRootDataModel ){
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
	public FieldElementTypeComponent( ElementTypeListEnum elementType , ElementOperationInterface elementOperation, BaseRootDataModel baseRootDataModel, VariableRootDataModel variableRootDataModel ){
		super();
		
		common( elementType, elementOperation, baseRootDataModel, variableRootDataModel );		
		
	}
	
	private void common( ElementTypeListEnum elementType , ElementOperationInterface elementOperation, BaseRootDataModel baseRootDataModel, VariableRootDataModel variableRootDataModel ){
		
		labelType = new JLabel( CommonOperations.getTranslation("editor.label.param.type") + ": ");
		labelOperations = new JLabel( CommonOperations.getTranslation("editor.label.param.operation") + ": ");
		labelPattern = new JLabel( CommonOperations.getTranslation("editor.label.param.pattern") + ": ");
		labelString = new JLabel( CommonOperations.getTranslation("editor.label.param.string") + ": ");
		labelVariableSelector = new JLabel( CommonOperations.getTranslation("editor.label.param.variable") + ": ");
		labelBaseElementSelector = new JLabel( CommonOperations.getTranslation("editor.label.param.baseelement") + ": ");
		labelMessage = new JLabel( CommonOperations.getTranslation("editor.label.param.message") + ": ");
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

					setValueContainer( comboOperationList.getItemAt(index));
					
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
			
			//CLICK
			if( elementOperation instanceof ClickOperation  ){
				
				comboOperationList.setSelectedIndex(E.CLICK.getIndex());
				
			//TAB
			}else if( elementOperation instanceof TabOperation ){
				
				comboOperationList.setSelectedIndex(E.TAB.getIndex());
				
			//CLEAR
			}else if( elementOperation instanceof ClearOperation ){
				
				comboOperationList.setSelectedIndex(E.CLEAR.getIndex());
				
			//GAIN
			}else if( elementOperation instanceof GainTextPatternOperation ){
				
				comboOperationList.setSelectedIndex(E.GAINTEXTPATTERN.getIndex());
				fieldPattern.setText( ((GainTextPatternOperation)elementOperation).getStringPattern());
				
			//FILL_VARIABLE
			}else if( elementOperation instanceof FillVariableElementOperation ){
								
				fieldVariableSelector = new VariableTreeSelectorComponent( variableRootDataModel, ((FillVariableElementOperation)elementOperation).getVariableElement() );
				comboOperationList.setSelectedIndex(E.FILL_VARIABLE.getIndex());

			//FILL_BASELEMENT
			}else if( elementOperation instanceof FillBaseElementOperation ){
								
				fieldBaseElementSelector = new BaseElementTreeSelectorComponent( baseRootDataModel, ((FillBaseElementOperation)elementOperation).getBaseElement() );
				comboOperationList.setSelectedIndex(E.FILL_ELEMENT.getIndex());

			//FILL_STRING
			}else if( elementOperation instanceof FillStringOperation ){
								
				fieldString.setText( ((FillStringOperation)elementOperation).getStringToShow() );
				comboOperationList.setSelectedIndex(E.FILL_STRING.getIndex());
				
			//OUTPUT
			}else if ( elementOperation instanceof OutputValueOperation ){
				
				fieldMessage.setText( ((OutputValueOperation)elementOperation).getMessageToShow());
				comboOperationList.setSelectedIndex( E.OUTPUTVALUE.getIndex() );
				
			//COMPARE_VARIABLE
			}else if( elementOperation instanceof CompareVariableElementOperation ){
				
				fieldVariableSelector = new VariableTreeSelectorComponent( variableRootDataModel, ((CompareVariableElementOperation)elementOperation).getVariableElement() );				
				comboOperationList.setSelectedIndex(E.COMPARE_VARIABLE.getIndex());
				comboCompareTypeList.setSelectedIndex( ((CompareVariableElementOperation)elementOperation).getCompareType().getIndex() );

			//COMPARE_BASEELEMENT
			}else if( elementOperation instanceof CompareBaseElementOperation ){
								
				fieldBaseElementSelector = new BaseElementTreeSelectorComponent( baseRootDataModel, ((CompareBaseElementOperation)elementOperation).getBaseElement() );
				comboCompareTypeList.setSelectedIndex( ((CompareBaseElementOperation)elementOperation).getCompareType().getIndex() );
				comboOperationList.setSelectedIndex(E.COMPARE_ELEMENT.getIndex());
				
			//COMPARESTRING
			}else if( elementOperation instanceof CompareStringOperation ){
								
				fieldString.setText( ((CompareStringOperation)elementOperation).getStringToShow() );
				comboCompareTypeList.setSelectedIndex( ((CompareStringOperation)elementOperation).getCompareType().getIndex() );
				comboOperationList.setSelectedIndex(E.COMPARE_STRING.getIndex());
				
				
			}else{
				comboOperationList.setSelectedIndex(E.CLICK.getIndex());
			}
		}		
		
	}	
	
	@Override
	public E getSelectedOperation(ElementTypeListEnum elementType) {
		return(E)comboOperationList.getSelectedItem();
	}
	
	@Override
	public void setEnableModify(boolean enable) {
		
		comboOperationList.setEnabled( enable );		
		
		fieldString.setEditable( enable );

		fieldBaseElementSelector.setEnableModify(enable);
		
		fieldVariableSelector.setEnableModify( enable );

		fieldMessage.setEditable( enable );

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
		this.remove( labelBaseElementSelector );
		this.remove( fieldBaseElementSelector );
		this.remove( labelString );
		this.remove( fieldString );
		this.remove( labelVariableSelector );
		this.remove( fieldVariableSelector );	
		this.remove( labelFiller );	
		this.remove( fieldMessage );
		this.remove( labelMessage );
		this.remove( labelCompareType );
		this.remove( comboCompareTypeList );
/*		
Component[] components = this.getComponents();

for( int i = 0; i < components.length; i++ ){
	if( components[i] == labelBaseElementSelector ){
		this.remove( labelBaseElementSelector );
	
	}else if( components[i] == fieldBaseElementSelector ){
		this.remove( fieldBaseElementSelector );
		
	}else if( components[i] == labelString ){
		this.remove( labelString );
		
	}else if( components[i] == fieldString ){
		this.remove( fieldString );
		
	}else if( components[i] == labelVariableSelector ){
		this.remove( labelVariableSelector );
		
	}else if( components[i] == fieldVariableSelector ){
		this.remove( fieldVariableSelector );	
		
	}else if( components[i] == labelFiller ){
		this.remove( labelFiller );	
		
	}else if( components[i] == fieldMessage ){
		this.remove( fieldMessage );
	
	}else if( components[i] == labelMessage ){
		this.remove( labelMessage );
		
	}else if( components[i] == labelCompareType ){
		this.remove( labelCompareType );
		
	}else if( components[i] == comboCompareTypeList ){
		this.remove( comboCompareTypeList );		

		
	}
}*/
		
		//Fill element / Compare element
		if( selectedOperation.equals( E.FILL_ELEMENT ) || selectedOperation.equals( E.COMPARE_ELEMENT ) ){
			
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
			
		//Fill variable / Compare variable
		}else if( selectedOperation.equals( E.FILL_VARIABLE ) || selectedOperation.equals( E.COMPARE_VARIABLE ) ){
			
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
			
		//Fill string / Compare string
		}else if( selectedOperation.equals( E.FILL_STRING ) || selectedOperation.equals( E.COMPARE_STRING ) ){
		
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
			
		//Clear
		}else if( selectedOperation.equals( E.CLEAR) ){
			
			//Filler
			c.gridy = 0;
			c.gridx = 4;
			c.gridwidth = 1;
			c.weighty = 0;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 1;
			c.anchor = GridBagConstraints.WEST;
			this.add( labelFiller, c );
			
		//Tab
		}else if( selectedOperation.equals( E.TAB ) ){

			//Filler
			c.gridy = 0;
			c.gridx = 4;
			c.gridwidth = 1;
			c.weighty = 0;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 1;
			c.anchor = GridBagConstraints.WEST;
			this.add( labelFiller, c );
			
		//Click
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
		
		//Output value
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
			
			//Filler
/*			c.gridy = 0;
			c.gridx = 4;
			c.gridwidth = 1;
			c.weighty = 0;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 1;
			c.anchor = GridBagConstraints.WEST;
			this.add( labelFiller, c );
*/			
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
		
		//Fill element
		if( comboOperationList.getSelectedIndex() ==  E.FILL_ELEMENT.getIndex() ){
			return new FillBaseElementOperation( fieldBaseElementSelector.getSelectedDataModel() );
			
		//Fill variable
		}else if(comboOperationList.getSelectedIndex() ==  E.FILL_VARIABLE.getIndex() ){
			return new FillVariableElementOperation( fieldVariableSelector.getSelectedDataModel() );
			
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
