package hu.akoel.grawit.gui.editors.component.elementtype.full;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.ListRenderer;
import hu.akoel.grawit.core.operations.ClickOperation;
import hu.akoel.grawit.core.operations.CompareValueToStoredElementOperation;
import hu.akoel.grawit.core.operations.CompareValueToStringOperation;
import hu.akoel.grawit.core.operations.CompareValueToVariableOperation;
import hu.akoel.grawit.core.operations.ElementOperationAdapter;
import hu.akoel.grawit.core.operations.GainValueToElementStorageOperation;
import hu.akoel.grawit.core.operations.OutputStoredElementOperation;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.variable.VariableRootDataModel;
import hu.akoel.grawit.enums.list.CompareTypeListEnum;
import hu.akoel.grawit.enums.list.ElementTypeListEnum;
import hu.akoel.grawit.enums.list.elementtypeoperations.full.CheckboxElementTypeOperationsFullListEnum;
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
import javax.swing.JTextField;

public class CheckboxElementTypeComponentFull<E extends CheckboxElementTypeOperationsFullListEnum> extends ElementTypeComponentFullInterface<E>{

	private static final long serialVersionUID = -1938952416450007421L;
	
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
	
	public CheckboxElementTypeComponentFull( ElementTypeListEnum elementType , ElementOperationAdapter elementOperation, BaseRootDataModel baseRootDataModel, VariableRootDataModel variableRootDataModel ){
		super();
		
		common( elementType, elementOperation, baseRootDataModel, variableRootDataModel );	
		
	}
	
	private void common( ElementTypeListEnum elementType , ElementOperationAdapter elementOperation, BaseRootDataModel baseRootDataModel, VariableRootDataModel variableRootDataModel ){
		
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
			comboOperationList.addItem( (E) E.getElementCheckboxOperationByIndex(i) );
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
		//comboOperationList.setRenderer(new ElementTypeComponentRenderer());
		comboOperationList.setRenderer(new ListRenderer<E>());
		
		//COMPARE TYPE
		comboCompareTypeList = new JComboBox<CompareTypeListEnum>();
		for( int i = 0; i < CompareTypeListEnum.getSize(); i++ ){
			comboCompareTypeList.addItem( CompareTypeListEnum.getCompareTypeByIndex(i) );
		}
		
		//Azert kell, hogy a setEditable() hatasara ne szurkuljon el a felirat
		//comboCompareTypeList.setRenderer(new CompareTypeRenderer());
		comboCompareTypeList.setRenderer(new ListRenderer<CompareTypeListEnum>());
		
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
			
			//!!!Fontos a beallitasok sorrendje!!!
			
			//CLICK
			if( elementOperation instanceof ClickOperation  ){
				
				comboOperationList.setSelectedIndex(E.CLICK.getIndex());
	
			//COMPARE VALUE TO VARIABLE
			}else if( elementOperation instanceof CompareValueToVariableOperation ){
				
				fieldVariableSelector = new VariableTreeSelectorComponent( variableRootDataModel, ((CompareValueToVariableOperation)elementOperation).getVariableElement() );				
				comboOperationList.setSelectedIndex(E.COMPAREVALUE_TO_VARIABLE.getIndex());
				comboCompareTypeList.setSelectedIndex( ((CompareValueToVariableOperation)elementOperation).getCompareType().getIndex() );

			//COMPARE VALUE TO STORED
			}else if( elementOperation instanceof CompareValueToStoredElementOperation ){
								
				fieldBaseElementSelector = new BaseElementTreeSelectorComponent( baseRootDataModel, ((CompareValueToStoredElementOperation)elementOperation).getBaseElement() );
				comboCompareTypeList.setSelectedIndex( ((CompareValueToStoredElementOperation)elementOperation).getCompareType().getIndex() );
				comboOperationList.setSelectedIndex(E.COMPAREVALUE_TO_STORED.getIndex());
				
			//COMPARE VALUE TO STRING
			}else if( elementOperation instanceof CompareValueToStringOperation ){
								
				fieldString.setText( ((CompareValueToStringOperation)elementOperation).getStringToShow() );
				comboCompareTypeList.setSelectedIndex( ((CompareValueToStringOperation)elementOperation).getCompareType().getIndex() );
				comboOperationList.setSelectedIndex(E.COMPAREVALUE_TO_STRING.getIndex());
				
			//GAIN VALUE TO ELEMENT STORAGE
			}else if( elementOperation instanceof GainValueToElementStorageOperation ){
			
				comboOperationList.setSelectedIndex(E.GAINVALUE_TO_ELEMENTSTORAGE.getIndex());
				//fieldPattern.setText( ((GainValueToElementStorageOperation)elementOperation).getStringPattern());	

			//OUTPUT STORED
			}else if ( elementOperation instanceof OutputStoredElementOperation ){
				
				fieldMessage.setText( ((OutputStoredElementOperation)elementOperation).getMessageToShow());
				comboOperationList.setSelectedIndex( E.OUTPUTSTORED.getIndex() );
				
			}else{
				comboOperationList.setSelectedIndex(E.CLICK.getIndex());
			}
		}
	}	
	
	@Override
	public E getSelectedOperation( ElementTypeListEnum elementType ){
		return(E)comboOperationList.getSelectedItem();
	}
	
	@Override
	public void setEnableModify(boolean enable) {
		comboOperationList.setEnabled( enable );		
		fieldString.setEditable( enable );
		fieldBaseElementSelector.setEnableModify(enable);		
		fieldVariableSelector.setEnableModify( enable );
		fieldMessage.setEditable( enable );		
		fieldPattern.setEditable( enable );
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
	
		//Compare value to STORED
		if( selectedOperation.equals( E.COMPAREVALUE_TO_STORED ) ){
			
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
			
		//Compare value to variable
		}else if( selectedOperation.equals( E.COMPAREVALUE_TO_VARIABLE ) ){
			
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
			
		//Compare value to string
		}else if( selectedOperation.equals( E.COMPAREVALUE_TO_STRING ) ){
		
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
		
		//GAIN VALUE TO ELEMENT STORAGE
		}else if( selectedOperation.equals( E.GAINVALUE_TO_ELEMENTSTORAGE ) ){
			
			//Filler
			c.gridy = 0;
			c.gridx = 4;
			c.gridwidth = 1;
			c.weighty = 0;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 1;
			c.anchor = GridBagConstraints.WEST;
			this.add( labelFiller, c );

		//Output STORED
		}else if( selectedOperation.equals( E.OUTPUTSTORED ) ){
	
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
			
		}	

		//Compare element
		if( selectedOperation.equals( E.COMPAREVALUE_TO_STORED ) || selectedOperation.equals( E.COMPAREVALUE_TO_VARIABLE ) || selectedOperation.equals( E.COMPAREVALUE_TO_STRING ) ){
				
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
	public ElementOperationAdapter getElementOperation() {
		
		//CLICK
		if( comboOperationList.getSelectedIndex() == E.CLICK.getIndex() ){
			return new ClickOperation();
		
		//COMPARE VALUE TO STORED
		}else if( comboOperationList.getSelectedIndex() ==  E.COMPAREVALUE_TO_STORED.getIndex() ){
			return new CompareValueToStoredElementOperation( fieldBaseElementSelector.getSelectedDataModel(), (CompareTypeListEnum)(comboCompareTypeList.getSelectedItem()), fieldPattern.getText() );
				
		//COMPARE VALUE TO VARIABLE
		}else if(comboOperationList.getSelectedIndex() ==  E.COMPAREVALUE_TO_VARIABLE.getIndex() ){
			return new CompareValueToVariableOperation( fieldVariableSelector.getSelectedDataModel(), (CompareTypeListEnum)(comboCompareTypeList.getSelectedItem()), fieldPattern.getText() );
				
		//COMPARE VALUE TO STRING
		}else if( comboOperationList.getSelectedIndex() ==  E.COMPAREVALUE_TO_STRING.getIndex() ){
			return new CompareValueToStringOperation( fieldString.getText(), (CompareTypeListEnum)(comboCompareTypeList.getSelectedItem()), fieldPattern.getText() );
		
		//GAINVALUE TO ELEMENT
		}else if( comboOperationList.getSelectedIndex() == E.GAINVALUE_TO_ELEMENTSTORAGE.getIndex() ){
			return new GainValueToElementStorageOperation( fieldPattern.getText() );		
			
		//OUTPUTSTORED
		}else if( comboOperationList.getSelectedIndex() == E.OUTPUTSTORED.getIndex() ){
			return new OutputStoredElementOperation( fieldMessage.getText() );						
		}
		
		return null;
	
	}

}
