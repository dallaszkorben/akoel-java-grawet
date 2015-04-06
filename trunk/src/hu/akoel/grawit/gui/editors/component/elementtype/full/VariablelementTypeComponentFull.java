package hu.akoel.grawit.gui.editors.component.elementtype.full;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.ListRenderer;
import hu.akoel.grawit.core.operations.CompareValueToStringOperation;
import hu.akoel.grawit.core.operations.CompareValueToConstantOperation;
import hu.akoel.grawit.core.operations.ElementOperationAdapter;
import hu.akoel.grawit.core.operations.OutputStoredElementOperation;
import hu.akoel.grawit.core.operations.VariableElementSetConstantOperation;
import hu.akoel.grawit.core.operations.VariableElementSetStoreOperation;
import hu.akoel.grawit.core.operations.VariableElementSetStringOperation;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseElementDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.constant.ConstantRootDataModel;
import hu.akoel.grawit.enums.list.CompareTypeListEnum;
import hu.akoel.grawit.enums.list.ElementTypeListEnum;
import hu.akoel.grawit.enums.list.elementtypeoperations.full.VariableElementTypeOperationsFullListEnum;
import hu.akoel.grawit.gui.editors.component.treeselector.BaseElementTreeSelectorComponent;
import hu.akoel.grawit.gui.editors.component.treeselector.ConstantTreeSelectorComponent;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class VariablelementTypeComponentFull<E extends VariableElementTypeOperationsFullListEnum> extends ElementTypeComponentFullInterface<E>{

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
	
	//Constant selector - Mezo kitoltes
	private JLabel labelConstantSelector;
	private ConstantTreeSelectorComponent fieldConstantSelector;
	
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
	
	public VariablelementTypeComponentFull( BaseElementDataModelAdapter baseElement, ElementOperationAdapter elementOperation, BaseRootDataModel baseRootDataModel, ConstantRootDataModel constantRootDataModel ){
		super();
		
		common( baseElement, elementOperation, baseRootDataModel, constantRootDataModel );		
		
	}
	
	private void common( BaseElementDataModelAdapter baseElement, ElementOperationAdapter elementOperation, BaseRootDataModel baseRootDataModel, ConstantRootDataModel constantRootDataModel ){
		
		ElementTypeListEnum elementType = baseElement.getElementType();
		
		labelType = new JLabel( CommonOperations.getTranslation("editor.label.step.type") + ": ");
		labelOperations = new JLabel( CommonOperations.getTranslation("editor.label.step.operation") + ": ");
		labelPattern = new JLabel( CommonOperations.getTranslation("editor.label.step.pattern") + ": ");
		labelString = new JLabel( CommonOperations.getTranslation("editor.label.step.string") + ": ");
		labelConstantSelector = new JLabel( CommonOperations.getTranslation("editor.label.step.constant") + ": ");
		labelBaseElementSelector = new JLabel( CommonOperations.getTranslation("editor.label.step.baseelement") + ": ");
		labelMessage = new JLabel( CommonOperations.getTranslation("editor.label.step.message") + ": ");
		labelCompareType = new JLabel( CommonOperations.getTranslation("editor.label.step.comparetype") + ": ");
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
		fieldConstantSelector = new ConstantTreeSelectorComponent( constantRootDataModel );
					
		//Arra az esetre, ha a muvelethez hasznalt baseElement meg nem kivalasztott akkor az alap alapElemet javasolja hasznalni
		fieldBaseElementSelector = new BaseElementTreeSelectorComponent( baseRootDataModel, baseElement, false );
			
		fieldString = new JTextField( "" );
		
		//Default value for CompareType
		comboCompareTypeList.setSelectedIndex( CompareTypeListEnum.EQUAL.getIndex() );
		
		//Kezdo ertek beallitasa
		if( null == elementOperation ){
			
			comboOperationList.setSelectedIndex(E.OUTPUT.getIndex());
		
		//Modositas
		}else{
			
			//!!!Fontos a beallitasok sorrendje!!!
			
			//OUTPUT
			if( elementOperation instanceof OutputStoredElementOperation  ){
				
				comboOperationList.setSelectedIndex(E.OUTPUT.getIndex());
				
			//SET_CONSTANT_TO_PARAMETERS							
			}else if( elementOperation instanceof VariableElementSetConstantOperation ){
								
				fieldConstantSelector = new ConstantTreeSelectorComponent( constantRootDataModel, ((VariableElementSetConstantOperation)elementOperation).getConstantElement() );
				comboOperationList.setSelectedIndex(E.SET_CONSTANT	.getIndex());

			//SET_STORED_TO_PARAMETERS
			}else if( elementOperation instanceof VariableElementSetStoreOperation ){
							
				fieldBaseElementSelector = new BaseElementTreeSelectorComponent( baseRootDataModel, ((VariableElementSetStoreOperation)elementOperation).getBaseElement(), true );
				comboOperationList.setSelectedIndex(E.SET_STORED.getIndex());

			//SET_STRING_TO_PARAMETERS
			}else if( elementOperation instanceof VariableElementSetStringOperation ){
								
				fieldString.setText( ((VariableElementSetStringOperation)elementOperation).getStringToShow() );
				comboOperationList.setSelectedIndex(E.SET_STRING.getIndex());
				
			//COMPARE VALUE TO CONSTANT
			}else if( elementOperation instanceof CompareValueToConstantOperation ){
				
				fieldConstantSelector = new ConstantTreeSelectorComponent( constantRootDataModel, ((CompareValueToConstantOperation)elementOperation).getConstantElement() );				
				comboOperationList.setSelectedIndex(E.COMPAREVALUE_TO_CONSTANT.getIndex());
				comboCompareTypeList.setSelectedIndex( ((CompareValueToConstantOperation)elementOperation).getCompareType().getIndex() );
				fieldPattern.setText( (( CompareValueToConstantOperation)elementOperation).getStringPattern());

			//COMPARE VALUE TO STRING
			}else if( elementOperation instanceof CompareValueToStringOperation ){
								
				fieldString.setText( ((CompareValueToStringOperation)elementOperation).getStringToShow() );
				comboCompareTypeList.setSelectedIndex( ((CompareValueToStringOperation)elementOperation).getCompareType().getIndex() );
				comboOperationList.setSelectedIndex(E.COMPAREVALUE_TO_STRING.getIndex());
				fieldPattern.setText( ((CompareValueToStringOperation)elementOperation).getStringPattern());
				
			//Ha megvaltozott az alapElem es kulonbozik a tipusa
			}else{
				
				comboOperationList.setSelectedIndex(E.OUTPUT.getIndex());
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
		fieldConstantSelector.setEnableModify( enable );
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
		this.remove( labelConstantSelector );
		this.remove( fieldConstantSelector );	
		this.remove( labelFiller );	
		this.remove( fieldMessage );
		this.remove( labelMessage );
		this.remove( labelCompareType );
		this.remove( comboCompareTypeList );
		
		//Fill element / Compare value to element
		if( selectedOperation.equals( E.SET_STORED ) ){
			
			c.gridy = 1;
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
			
		//SET_CONSTANT / Compare value to constant
		}else if( selectedOperation.equals( E.SET_CONSTANT ) || selectedOperation.equals( E.COMPAREVALUE_TO_CONSTANT ) ){
			
			c.gridy = 1;
			c.gridx = 4;
			c.gridwidth = 1;
			c.weighty = 0;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 0;
			c.anchor = GridBagConstraints.WEST;
			this.add( labelConstantSelector, c );
		
			c.gridx = 5;
			c.weightx = 1;
			this.add( fieldConstantSelector, c );
			
		//SET_STRING / Compare value to string
		}else if( selectedOperation.equals( E.SET_STRING ) || selectedOperation.equals( E.COMPAREVALUE_TO_STRING ) ){
		
			c.gridy = 1;
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
			
		//SET_STORED
		}else if( selectedOperation.equals( E.SET_STORED) ){
			
			//Filler
			c.gridy = 0;
			c.gridx = 4;
			c.gridwidth = 1;
			c.weighty = 0;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 1;
			c.anchor = GridBagConstraints.WEST;
			this.add( labelFiller, c );

		//Output
		}else if( selectedOperation.equals( E.OUTPUT ) ){
	
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

		if( 
				selectedOperation.equals( E.SET_CONSTANT ) || 
				selectedOperation.equals( E.SET_STORED ) ||
				selectedOperation.equals( E.SET_STRING ) ||
				selectedOperation.equals( E.COMPAREVALUE_TO_CONSTANT )  || 
				selectedOperation.equals( E.COMPAREVALUE_TO_STRING ) || 
				selectedOperation.equals( E.OUTPUT )
				){
			
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
		
		//Compare element
		if( selectedOperation.equals( E.COMPAREVALUE_TO_CONSTANT ) || selectedOperation.equals( E.COMPAREVALUE_TO_STRING ) ){
				
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
		
		//SET_CONSTANT
		if(comboOperationList.getSelectedIndex() ==  E.SET_CONSTANT.getIndex() ){
			return new VariableElementSetConstantOperation( fieldConstantSelector.getSelectedDataModel() );
			
		//SET_STRING
		}else if(comboOperationList.getSelectedIndex() ==  E.SET_STRING.getIndex() ){
			return new VariableElementSetStringOperation( fieldString.getText() ); 

		//SET_STORED
		}else if(comboOperationList.getSelectedIndex() ==  E.SET_STORED.getIndex() ){
			return new VariableElementSetStoreOperation( fieldBaseElementSelector.getSelectedDataModel() ); 
			
		//COMPAREVALUE_TO_CONSTANT
		}else if(comboOperationList.getSelectedIndex() ==  E.COMPAREVALUE_TO_CONSTANT.getIndex() ){
			return new CompareValueToConstantOperation( fieldConstantSelector.getSelectedDataModel(), (CompareTypeListEnum)(comboCompareTypeList.getSelectedItem()), fieldPattern.getText() );
				
		//COMPARE VALUE TO STRING
		}else if( comboOperationList.getSelectedIndex() ==  E.COMPAREVALUE_TO_STRING.getIndex() ){
			return new CompareValueToStringOperation( fieldString.getText(), (CompareTypeListEnum)(comboCompareTypeList.getSelectedItem()), fieldPattern.getText() );
			
		//OUTPUT
		}else if( comboOperationList.getSelectedIndex() == E.OUTPUT.getIndex() ){
			return new OutputStoredElementOperation( fieldMessage.getText() );			
		}
	
		return null;
	}
	
}
