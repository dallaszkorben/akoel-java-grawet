package hu.akoel.grawit.gui.editors.component.elementtype;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.ListRenderer;
import hu.akoel.grawit.core.operations.ClickOperation;
import hu.akoel.grawit.core.operations.ElementOperationInterface;
import hu.akoel.grawit.core.operations.GainTextToElementOperation;
import hu.akoel.grawit.core.operations.GainTextToVariableOperation;
import hu.akoel.grawit.core.operations.GainValueToElementOperation;
import hu.akoel.grawit.core.operations.GainValueToVariableOperation;
import hu.akoel.grawit.core.operations.SelectBaseElementOperation;
import hu.akoel.grawit.core.operations.SelectStringOperation;
import hu.akoel.grawit.core.operations.SelectVariableElementOperation;
import hu.akoel.grawit.core.operations.TabOperation;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.variable.VariableRootDataModel;
import hu.akoel.grawit.enums.list.ElementTypeListEnum;
import hu.akoel.grawit.enums.list.ListSelectionByListEnum;
import hu.akoel.grawit.enums.list.elementtypeoperations.ListElementTypeOperationsListEnum;
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

public class ListElementTypeComponent<E extends ListElementTypeOperationsListEnum> extends ElementTypeComponentInterface<E>{

	private static final long serialVersionUID = -6108131072338954554L;
	
	private JTextField fieldType;
	private JComboBox<E> comboOperationList;
		
	//Pattern
	private JTextField fieldPattern;	
	private JLabel labelPattern;
	
	//Type
	private JLabel labelType;
	private JLabel labelOperations;	
	
	//Variable selector - Mezo kitoltes
	private JLabel labelVariableSelector;
	private VariableTreeSelectorComponent fieldVariableSelector;
	
	//BaseElement selector - Mezo kitoltes
	private JLabel labelBaseElementSelector;
	private BaseElementTreeSelectorComponent fieldBaseElementSelector;
	
	//String - Mezo kitoltes
	private JLabel labelString;
	private JTextField fieldString;
	
	//Selection by
	private JLabel labelSelectionBy;
	private JComboBox<ListSelectionByListEnum> comboSelectionBy;
	
	private JLabel labelFiller;
	
	@Override
	public E getSelectedOperation(ElementTypeListEnum elementType) {
		return(E)comboOperationList.getSelectedItem();
	}
		
	/**
	 * 
	 * Mar letezo
	 * 
	 * @param key
	 * @param value
	 */	
	public ListElementTypeComponent( ElementTypeListEnum elementType , ElementOperationInterface elementOperation, BaseRootDataModel baseRootDataModel, VariableRootDataModel variableRootDataModel ){
		super();
		
		common( elementType, elementOperation, baseRootDataModel, variableRootDataModel );		
		
	}
	
	private void common( ElementTypeListEnum elementType , ElementOperationInterface elementOperation, BaseRootDataModel baseRootDataModel, VariableRootDataModel variableRootDataModel ){
		
		labelType = new JLabel( CommonOperations.getTranslation("editor.label.param.type") + ": ");
		labelPattern = new JLabel( CommonOperations.getTranslation("editor.label.param.pattern") + ": ");
		labelOperations = new JLabel( CommonOperations.getTranslation("editor.label.param.operation") + ": ");
		labelString = new JLabel( CommonOperations.getTranslation("editor.label.param.string") + ": ");
		labelVariableSelector = new JLabel( CommonOperations.getTranslation("editor.label.param.variable") + ": ");
		labelBaseElementSelector = new JLabel( CommonOperations.getTranslation("editor.label.param.baseelement") + ": ");
		labelSelectionBy = new JLabel( CommonOperations.getTranslation("editor.label.param.selectionby") + ": ");
		labelFiller = new JLabel();
		
		fieldType = new JTextField( elementType.getTranslatedName() );
		fieldType.setEditable(false);
		fieldPattern = new JTextField();

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
				
		comboSelectionBy = new JComboBox<>();
		for( int i = 0; i < ListSelectionByListEnum.getSize(); i++ ){
			comboSelectionBy.addItem((ListSelectionByListEnum)ListSelectionByListEnum.getListSelectionTypeByOrder(i) );
		}
		
		//Azert kell, hogy a setEditable() hatasara ne szurkuljon el a felirat
		//comboOperationList.setRenderer(new ElementTypeComponentRenderer() );
		comboOperationList.setRenderer( new ListRenderer<E>() );
		comboSelectionBy.setRenderer( new ListRenderer<ListSelectionByListEnum>() );		
		//comboSelectionBy.setRenderer( new MyRenderer() );
		
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
		
		//Default ertek
		comboSelectionBy.setSelectedIndex(ListSelectionByListEnum.BYVALUE.getIndex());
		
		//Valtozok letrehozase
		fieldVariableSelector = new VariableTreeSelectorComponent( variableRootDataModel );
		fieldBaseElementSelector = new BaseElementTreeSelectorComponent( baseRootDataModel );
		fieldString = new JTextField( "" );
		
		//Kezdo ertek beallitasa
		if( null == elementOperation ){
			
			comboOperationList.setSelectedIndex(E.CLICK.getIndex());
			comboSelectionBy.setSelectedIndex( ListSelectionByListEnum.BYVALUE.getIndex() );
			
		}else{
			
			//CLICK
			if( elementOperation instanceof ClickOperation  ){
				
				comboOperationList.setSelectedIndex(E.CLICK.getIndex());
				
			//TAB
			}else if( elementOperation instanceof TabOperation ){
				
				comboOperationList.setSelectedIndex(E.TAB.getIndex());
				
			//SELECT VARIABLE
			}else if( elementOperation instanceof SelectVariableElementOperation ){
								
				fieldVariableSelector = new VariableTreeSelectorComponent( variableRootDataModel, ((SelectVariableElementOperation)elementOperation).getVariableElement() );
				comboOperationList.setSelectedIndex(E.SELECT_VARIABLE.getIndex());
				
				comboSelectionBy.setSelectedIndex( ((SelectVariableElementOperation)elementOperation).getSelectionBy().getIndex() );
				
			//SELECT BASE 
			}else if( elementOperation instanceof SelectBaseElementOperation ){
								
				fieldBaseElementSelector = new BaseElementTreeSelectorComponent( baseRootDataModel, ((SelectBaseElementOperation)elementOperation).getBaseElement() );
				comboOperationList.setSelectedIndex(E.SELECT_BASE.getIndex());

				comboSelectionBy.setSelectedIndex( ((SelectBaseElementOperation)elementOperation).getSelectionBy().getIndex() );
			
			//SELECT STRING
			}else if( elementOperation instanceof SelectStringOperation ){
								
				fieldString.setText( ((SelectStringOperation)elementOperation).getStringToSelection() );
				comboOperationList.setSelectedIndex(E.SELECT_STRING.getIndex());
				
				comboSelectionBy.setSelectedIndex( ((SelectStringOperation)elementOperation).getSelectionBy().getIndex() );
				
			//GAIN VALUE TO VARIABLE
			}else if( elementOperation instanceof GainValueToVariableOperation ){
				
				fieldVariableSelector = new VariableTreeSelectorComponent( variableRootDataModel, ((GainValueToVariableOperation)elementOperation).getVariableElement() );
				comboOperationList.setSelectedIndex(E.GAINVALUE_TO_VARIABLE.getIndex());
				fieldPattern.setText( ((GainValueToVariableOperation)elementOperation).getStringPattern());	
			
			//GAIN TEXT TO VARIABLE
			}else if( elementOperation instanceof GainTextToVariableOperation ){
					
				fieldVariableSelector = new VariableTreeSelectorComponent( variableRootDataModel, ((GainTextToVariableOperation)elementOperation).getVariableElement() );
				comboOperationList.setSelectedIndex(E.GAINTEXT_TO_VARIABLE.getIndex());
				fieldPattern.setText( ((GainTextToVariableOperation)elementOperation).getStringPattern());		
				
			//GAIN VALUE TO ELEMENT
			}else if( elementOperation instanceof GainValueToElementOperation ){
				
				//fieldVariableSelector = new VariableTreeSelectorComponent( variableRootDataModel, ((GainValueToElementOperation)elementOperation).getVariableElement() );
				comboOperationList.setSelectedIndex(E.GAINVALUE_TO_ELEMENT.getIndex());
				fieldPattern.setText( ((GainValueToElementOperation)elementOperation).getStringPattern());	
			
			//GAIN TEXT TO ELEMENT
			}else if( elementOperation instanceof GainTextToElementOperation ){
					
				//fieldVariableSelector = new VariableTreeSelectorComponent( variableRootDataModel, ((GainTextToElementOperation)elementOperation).getVariableElement() );
				comboOperationList.setSelectedIndex(E.GAINTEXT_TO_ELEMENT.getIndex());
				fieldPattern.setText( ((GainTextToElementOperation)elementOperation).getStringPattern());			
				
			}			
		}
	}	
	
	
	@Override
	public void setEnableModify(boolean enable) {
		
		comboOperationList.setEnabled( enable );
		comboSelectionBy.setEnabled( enable );		
		fieldString.setEditable( enable );
		fieldBaseElementSelector.setEnableModify(enable);		
		fieldVariableSelector.setEnableModify( enable );
		fieldPattern.setEditable( enable );
	}

	@Override
	public Component getComponent() {
		return this;
	}

	private void setValueContainer( E selectedOperation ){

		GridBagConstraints c = new GridBagConstraints();		
		c.insets = new Insets(0,0,0,0);		
		
		this.remove( labelBaseElementSelector );
		this.remove( fieldBaseElementSelector );
		this.remove( labelString );
		this.remove( fieldString );
		this.remove( labelVariableSelector );
		this.remove( fieldVariableSelector );	
		this.remove( labelFiller );	
		this.remove( labelSelectionBy );
		this.remove( comboSelectionBy );
		this.remove( labelPattern );
		this.remove( fieldPattern );

		//Fill element
		if( selectedOperation.equals( E.SELECT_BASE ) ){
			
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
			
			putSelectionByElement( c );
			
		//Fill variable
		}else if( selectedOperation.equals( E.SELECT_VARIABLE ) ){
			
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
			
			putSelectionByElement( c );
			
		//Fill string
		}else if( selectedOperation.equals( E.SELECT_STRING ) ){
		
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
			
			putSelectionByElement( c );
			
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
			
		//GAINVALUE TO VARIABLE
		}else if( selectedOperation.equals( E.GAINVALUE_TO_VARIABLE ) ){
			
			//VARIABLE
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
				
			//PATTERN
			c.gridy = 1;
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
			
		//GAINTEXT TO VARIABLE
		}else if( selectedOperation.equals( E.GAINTEXT_TO_VARIABLE ) ){
				
			//VARIABLE
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
				
			//PATTERN
			c.gridy = 1;
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
		
		//GAINVALUE TO ELEMENT
		}else if( selectedOperation.equals( E.GAINVALUE_TO_ELEMENT ) ){
			
			//PATTERN
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
			
		//GAINTEXT TO ELEMENT
		}else if( selectedOperation.equals( E.GAINTEXT_TO_ELEMENT ) ){
			
			//PATTERN
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

	private void putSelectionByElement( GridBagConstraints c ){

		//Selection by
		c.gridy = 1;
		c.gridx = 2;
		c.gridwidth = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0;
		c.anchor = GridBagConstraints.WEST;
		this.add( labelSelectionBy, c );
		
		c.gridx = 3;
		c.weightx = 0;
		this.add( comboSelectionBy, c );
		c.gridy = 1;	
	}
	
	
	@Override
	public ElementOperationInterface getElementOperation() {
		
		//Fill element
		if( comboOperationList.getSelectedIndex() ==  E.SELECT_BASE.getIndex() ){
			return new SelectBaseElementOperation( fieldBaseElementSelector.getSelectedDataModel(), (ListSelectionByListEnum)comboSelectionBy.getSelectedItem() );
			
		//Fill variable
		}else if(comboOperationList.getSelectedIndex() ==  E.SELECT_VARIABLE.getIndex() ){
			return new SelectVariableElementOperation( fieldVariableSelector.getSelectedDataModel(), (ListSelectionByListEnum)comboSelectionBy.getSelectedItem() );
			
		//Fill string
		}else if( comboOperationList.getSelectedIndex() ==  E.SELECT_STRING.getIndex() ){
			return new SelectStringOperation( fieldString.getText(), (ListSelectionByListEnum)comboSelectionBy.getSelectedItem() );

		//Tab
		}else if( comboOperationList.getSelectedIndex() ==  E.TAB.getIndex() ){
			return new TabOperation();
			
		//Click
		}else if( comboOperationList.getSelectedIndex() ==  E.CLICK.getIndex() ){
			return new ClickOperation();
			
		//GAINVALUE TO VARIABLE
		}else if( comboOperationList.getSelectedIndex() == E.GAINVALUE_TO_VARIABLE.getIndex() ){
			return new GainValueToVariableOperation( fieldVariableSelector.getSelectedDataModel(), fieldPattern.getText() );
	
		//GAINTEXT TO VARIABLE
		}else if( comboOperationList.getSelectedIndex() == E.GAINTEXT_TO_VARIABLE.getIndex() ){
			return new GainTextToVariableOperation( fieldVariableSelector.getSelectedDataModel(), fieldPattern.getText() );
			
		}
	
		return null;
	}
	
	      
}
