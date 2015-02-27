package hu.akoel.grawit.gui.editors.component.elementtype.compare;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.ListRenderer;
import hu.akoel.grawit.core.operations.CompareListToStoredElementOperation;
import hu.akoel.grawit.core.operations.CompareListToStringOperation;
import hu.akoel.grawit.core.operations.CompareListToVariableOperation;
import hu.akoel.grawit.core.operations.ElementOperationAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.constant.ConstantRootDataModel;
import hu.akoel.grawit.enums.list.CompareTypeListEnum;
import hu.akoel.grawit.enums.list.ElementTypeListEnum;
import hu.akoel.grawit.enums.list.ListCompareByListEnum;
import hu.akoel.grawit.enums.list.ListGainByListEnum;
import hu.akoel.grawit.enums.list.ListSelectionByListEnum;
import hu.akoel.grawit.enums.list.elementtypeoperations.compare.ListElementTypeOperationsCompareListEnum;
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

public class ListElementTypeComponentCompare<E extends ListElementTypeOperationsCompareListEnum> extends ElementTypeComponentCompareInterface<E>{

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
	
	//Gain by
	private JLabel labelGainBy;
	private JComboBox<ListGainByListEnum> comboGainBy;

	//Compare by
	private JLabel labelCompareBy;
	private JComboBox<ListCompareByListEnum> comboCompareBy;
	
	//Message - Mezo ertekenek megjelenitese
	private JLabel labelMessage;
	private JTextField fieldMessage;
	
	//Compare type
	private JLabel labelCompareType;
	private JComboBox<CompareTypeListEnum> comboCompareType;
	
	private JLabel labelFiller;
	
	@Override
	public E getSelectedOperation(ElementTypeListEnum elementType) {
		return(E)comboOperationList.getSelectedItem();
	}

	public ListElementTypeComponentCompare( ElementTypeListEnum elementType , ElementOperationAdapter elementOperation, BaseRootDataModel baseRootDataModel, ConstantRootDataModel variableRootDataModel ){
		super();
		
		common( elementType, elementOperation, baseRootDataModel, variableRootDataModel );		
		
	}
	
	private void common( ElementTypeListEnum elementType , ElementOperationAdapter elementOperation, BaseRootDataModel baseRootDataModel, ConstantRootDataModel variableRootDataModel ){
		
		labelType = new JLabel( CommonOperations.getTranslation("editor.label.step.type") + ": ");
		labelPattern = new JLabel( CommonOperations.getTranslation("editor.label.step.pattern") + ": ");
		labelOperations = new JLabel( CommonOperations.getTranslation("editor.label.step.operation") + ": ");
		labelString = new JLabel( CommonOperations.getTranslation("editor.label.step.string") + ": ");
		labelVariableSelector = new JLabel( CommonOperations.getTranslation("editor.label.step.variable") + ": ");
		labelBaseElementSelector = new JLabel( CommonOperations.getTranslation("editor.label.step.baseelement") + ": ");
		labelMessage = new JLabel( CommonOperations.getTranslation("editor.label.step.message") + ": ");
		labelCompareType = new JLabel( CommonOperations.getTranslation("editor.label.step.comparetype") + ": ");
		labelSelectionBy = new JLabel( CommonOperations.getTranslation("editor.label.step.selectionby") + ": ");
		labelCompareBy = new JLabel( CommonOperations.getTranslation("editor.label.step.compareby") + ": ");
		labelGainBy = new JLabel( CommonOperations.getTranslation("editor.label.step.gainby") + ": ");
		labelFiller = new JLabel();
		
		fieldType = new JTextField( elementType.getTranslatedName() );
		fieldType.setEditable(false);
		fieldPattern = new JTextField();
		fieldMessage = new JTextField();

		comboOperationList = new JComboBox<E>();
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
		comboGainBy = new JComboBox<>();
		for( int i = 0; i < ListGainByListEnum.getSize(); i++ ){
			comboGainBy.addItem((ListGainByListEnum)ListGainByListEnum.getListSelectionTypeByOrder(i) );
		}
		comboCompareBy = new JComboBox<>();
		for( int i = 0; i < ListCompareByListEnum.getSize(); i++ ){
			comboCompareBy.addItem((ListCompareByListEnum)ListCompareByListEnum.getListSelectionTypeByOrder(i) );
		}
		
		//COMPARE TYPE
		comboCompareType = new JComboBox<CompareTypeListEnum>();
		for( int i = 0; i < CompareTypeListEnum.getSize(); i++ ){
			comboCompareType.addItem( CompareTypeListEnum.getCompareTypeByIndex(i) );
		}
		
		//Azert kell, hogy a setEditable() hatasara ne szurkuljon el a felirat
		comboOperationList.setRenderer( new ListRenderer<E>() );				
		comboCompareType.setRenderer(new ListRenderer<CompareTypeListEnum>());
		comboSelectionBy.setRenderer( new ListRenderer<ListSelectionByListEnum>() );
		comboGainBy.setRenderer( new ListRenderer<ListGainByListEnum>() );
		comboCompareBy.setRenderer( new ListRenderer<ListCompareByListEnum>() );
		
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
		comboOperationList.setSelectedIndex( -1 );
		comboCompareType.setSelectedIndex( -1 );	
		
		//Default ertek		
		comboCompareType.setSelectedIndex( CompareTypeListEnum.EQUAL.getIndex() );
		comboSelectionBy.setSelectedIndex(ListSelectionByListEnum.BYVALUE.getIndex());
		comboGainBy.setSelectedIndex(ListGainByListEnum.BYVALUE.getIndex());
		comboCompareBy.setSelectedIndex(ListCompareByListEnum.BYVALUE.getIndex());
		
		//Valtozok letrehozase
		fieldVariableSelector = new VariableTreeSelectorComponent( variableRootDataModel );
		fieldBaseElementSelector = new BaseElementTreeSelectorComponent( baseRootDataModel );
		fieldString = new JTextField( "" );
		
		//Kezdo ertek beallitasa
		if( null == elementOperation ){
			
			fieldString.setText( "" );
			comboOperationList.setSelectedIndex(E.COMPARE_TO_STRING.getIndex());
	
		//COMPARE TO VARIABLE
		}else if( elementOperation instanceof CompareListToVariableOperation ){
				
			fieldVariableSelector = new VariableTreeSelectorComponent( variableRootDataModel, ((CompareListToVariableOperation)elementOperation).getVariableElement() );				
			comboOperationList.setSelectedIndex(E.COMPARE_TO_VARIABLE.getIndex());
			comboCompareType.setSelectedIndex( ((CompareListToVariableOperation)elementOperation).getCompareType().getIndex() );

		//COMPARE TO STORED
		}else if( elementOperation instanceof CompareListToStoredElementOperation ){
								
			fieldBaseElementSelector = new BaseElementTreeSelectorComponent( baseRootDataModel, ((CompareListToStoredElementOperation)elementOperation).getBaseElement() );
			comboCompareType.setSelectedIndex( ((CompareListToStoredElementOperation)elementOperation).getCompareType().getIndex() );
			comboOperationList.setSelectedIndex(E.COMPARE_TO_STORED.getIndex());
				
		//COMPARE TO STRING
		}else if( elementOperation instanceof CompareListToStringOperation ){
								
			fieldString.setText( ((CompareListToStringOperation)elementOperation).getStringToShow() );
			comboCompareType.setSelectedIndex( ((CompareListToStringOperation)elementOperation).getCompareType().getIndex() );
			comboOperationList.setSelectedIndex(E.COMPARE_TO_STRING.getIndex());
			
		}
	}	
	
	
	@Override
	public void setEnableModify(boolean enable) {
		
		comboOperationList.setEnabled( enable );
		comboSelectionBy.setEnabled( enable );		
		fieldString.setEditable( enable );
		fieldBaseElementSelector.setEnableModify(enable);		
		fieldVariableSelector.setEnableModify( enable );
		fieldMessage.setEditable( enable );		
		fieldPattern.setEditable( enable );
		comboCompareType.setEnabled( enable );
		comboCompareBy.setEnabled( enable );
		comboGainBy.setEnabled( enable );
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
		this.remove( labelMessage );
		this.remove( fieldMessage );
		this.remove( labelCompareType );
		this.remove( comboCompareType );
		this.remove( labelCompareBy );
		this.remove( comboCompareBy );
		this.remove( labelGainBy );	
		this.remove( comboGainBy );
		
		//COMPARE TO STORED
		if( selectedOperation.equals( E.COMPARE_TO_STORED)){
	
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
	
			putCompareBy( c );
	
		//COMPARE TO VARIABLE
		}else if( selectedOperation.equals( E.COMPARE_TO_VARIABLE ) ){
	
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
	
			putCompareBy( c );
	
		//COMPARE TO STRING
		}else if( selectedOperation.equals( E.COMPARE_TO_STRING ) ){

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
	
			putCompareBy( c );
	
		}	
	
		this.revalidate();
		this.repaint();
	}

	private void putCompareBy( GridBagConstraints c ){

		//VALUE/TEXT
		c.gridy = 2;
		c.gridx = 2;
		c.gridwidth = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0;
		c.anchor = GridBagConstraints.WEST;
		this.add( labelCompareBy, c );
		
		c.gridx = 3;
		c.weightx = 1;
		this.add( comboCompareBy, c );
		
		//EQUAL/DIFFERENT
		c.gridy = 2;
		c.gridx = 4;
		c.gridwidth = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0;
		c.anchor = GridBagConstraints.WEST;
		this.add( labelCompareType, c );
		
		c.gridx = 5;
		c.weightx = 1;
		this.add( comboCompareType, c );
	}
	
	@Override
	public ElementOperationAdapter getElementOperation() {

		//COMPARE TO STORED
		if( comboOperationList.getSelectedIndex() ==  E.COMPARE_TO_STORED.getIndex() ){						
			return new CompareListToStoredElementOperation( fieldBaseElementSelector.getSelectedDataModel(), (CompareTypeListEnum)(comboCompareType.getSelectedItem()), fieldPattern.getText(), (ListCompareByListEnum)(comboCompareBy.getSelectedItem()) );
				
		//COMPARE TO VARIABLE
		}else if(comboOperationList.getSelectedIndex() ==  E.COMPARE_TO_VARIABLE.getIndex() ){
			return new CompareListToVariableOperation( fieldVariableSelector.getSelectedDataModel(), (CompareTypeListEnum)(comboCompareType.getSelectedItem()), fieldPattern.getText(), (ListCompareByListEnum)(comboCompareBy.getSelectedItem()) );
				
		//COMPARE TO STRING
		}else if( comboOperationList.getSelectedIndex() ==  E.COMPARE_TO_STRING.getIndex() ){
			return new CompareListToStringOperation( fieldString.getText(), (CompareTypeListEnum)(comboCompareType.getSelectedItem()), fieldPattern.getText(), (ListCompareByListEnum)(comboCompareBy.getSelectedItem()) );
			
		}
	
		return null;
	}
	
	      
}
