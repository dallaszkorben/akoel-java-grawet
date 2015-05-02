package hu.akoel.grawit.gui.editors.component.elementtype.full;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.ListRenderer;
import hu.akoel.grawit.core.operations.ClearOperation;
import hu.akoel.grawit.core.operations.ClickLeftOperation;
import hu.akoel.grawit.core.operations.CompareValueToStoredElementOperation;
import hu.akoel.grawit.core.operations.CompareValueToStringOperation;
import hu.akoel.grawit.core.operations.CompareValueToConstantOperation;
import hu.akoel.grawit.core.operations.ElementOperationAdapter;
import hu.akoel.grawit.core.operations.FillWithBaseElementOperation;
import hu.akoel.grawit.core.operations.FillWithStringOperation;
import hu.akoel.grawit.core.operations.FillWithConstantElementOperation;
import hu.akoel.grawit.core.operations.GainValueToElementStorageOperation;
import hu.akoel.grawit.core.operations.OutputStoredElementOperation;
import hu.akoel.grawit.core.operations.TabOperation;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseElementDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.constant.ConstantRootDataModel;
import hu.akoel.grawit.enums.list.CompareTypeListEnum;
import hu.akoel.grawit.enums.list.ElementTypeListEnum;
import hu.akoel.grawit.enums.list.elementtypeoperations.full.FieldElementTypeOperationsFullListEnum;
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

public class FieldElementTypeComponentFull<E extends FieldElementTypeOperationsFullListEnum> extends ElementTypeComponentFullInterface<E>{

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
	
	public FieldElementTypeComponentFull( BaseElementDataModelAdapter baseElement, ElementOperationAdapter elementOperation, BaseRootDataModel baseRootDataModel, ConstantRootDataModel constantRootDataModel ){
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
//		fieldBaseElementSelector = new BaseElementTreeSelectorComponent( baseRootDataModel );
			
		fieldString = new JTextField( "" );
		
		//Default value for CompareType
		comboCompareTypeList.setSelectedIndex( CompareTypeListEnum.EQUAL.getIndex() );
		
		//Kezdo ertek beallitasa
		if( null == elementOperation ){
			
			comboOperationList.setSelectedIndex(E.CLICK.getIndex());
		
		//Modositas
		}else{
			
			//!!!Fontos a beallitasok sorrendje!!!
			
			//CLICK
			if( elementOperation instanceof ClickLeftOperation  ){
				
				comboOperationList.setSelectedIndex(E.CLICK.getIndex());
				
			//TAB
			}else if( elementOperation instanceof TabOperation ){
				
				comboOperationList.setSelectedIndex(E.TAB.getIndex());
				
			//CLEAR
			}else if( elementOperation instanceof ClearOperation ){
				
				comboOperationList.setSelectedIndex(E.CLEAR.getIndex());
							
			//FILL_CONSTANT
			}else if( elementOperation instanceof FillWithConstantElementOperation ){
								
				fieldConstantSelector = new ConstantTreeSelectorComponent( constantRootDataModel, ((FillWithConstantElementOperation)elementOperation).getConstantElement() );
				comboOperationList.setSelectedIndex(E.FILL_CONSTANT.getIndex());

			//FILL_BASELEMENT
			}else if( elementOperation instanceof FillWithBaseElementOperation ){
							
				fieldBaseElementSelector = new BaseElementTreeSelectorComponent( baseRootDataModel, ((FillWithBaseElementOperation)elementOperation).getBaseElementForSearch(), true );
				comboOperationList.setSelectedIndex(E.FILL_ELEMENT.getIndex());

			//FILL_STRING
			}else if( elementOperation instanceof FillWithStringOperation ){
								
				fieldString.setText( ((FillWithStringOperation)elementOperation).getStringToShow() );
				comboOperationList.setSelectedIndex(E.FILL_STRING.getIndex());
				
			//COMPARE VALUE TO CONSTANT
			}else if( elementOperation instanceof CompareValueToConstantOperation ){
				
				fieldConstantSelector = new ConstantTreeSelectorComponent( constantRootDataModel, ((CompareValueToConstantOperation)elementOperation).getConstantElement() );				
				comboOperationList.setSelectedIndex(E.COMPAREVALUE_TO_CONSTANT.getIndex());
				comboCompareTypeList.setSelectedIndex( ((CompareValueToConstantOperation)elementOperation).getCompareType().getIndex() );
				fieldPattern.setText( (( CompareValueToConstantOperation)elementOperation).getStringPattern());

			//COMPARE VALUE TO STORED
			}else if( elementOperation instanceof CompareValueToStoredElementOperation ){
					
				fieldBaseElementSelector = new BaseElementTreeSelectorComponent( baseRootDataModel, ((CompareValueToStoredElementOperation)elementOperation).getBaseElementForSearch(), true );								
				comboCompareTypeList.setSelectedIndex( ((CompareValueToStoredElementOperation)elementOperation).getCompareType().getIndex() );
				comboOperationList.setSelectedIndex(E.COMPAREVALUE_TO_STORED.getIndex());
				fieldPattern.setText( ((CompareValueToStoredElementOperation)elementOperation).getStringPattern());
				
			//COMPARE VALUE TO STRING
			}else if( elementOperation instanceof CompareValueToStringOperation ){
								
				fieldString.setText( ((CompareValueToStringOperation)elementOperation).getStringToShow() );
				comboCompareTypeList.setSelectedIndex( ((CompareValueToStringOperation)elementOperation).getCompareType().getIndex() );
				comboOperationList.setSelectedIndex(E.COMPAREVALUE_TO_STRING.getIndex());
				fieldPattern.setText( ((CompareValueToStringOperation)elementOperation).getStringPattern());
				
			//GAIN VALUE TO ELEMENT STORAGE
			}else if( elementOperation instanceof GainValueToElementStorageOperation ){
			
				comboOperationList.setSelectedIndex(E.GAINVALUE_TO_ELEMENTSTORAGE.getIndex());
				fieldPattern.setText( ((GainValueToElementStorageOperation)elementOperation).getStringPattern());	

			//OUTPUT STORED
			}else if ( elementOperation instanceof OutputStoredElementOperation ){
				
				fieldMessage.setText( ((OutputStoredElementOperation)elementOperation).getLabelToShow());
				comboOperationList.setSelectedIndex( E.OUTPUTSTORED.getIndex() );
				
			//Ha megvaltozott az alapElem es kulonbozik a tipusa
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
		if( selectedOperation.equals( E.FILL_ELEMENT ) || selectedOperation.equals( E.COMPAREVALUE_TO_STORED ) ){
			
			//ELEMENT SELECTOR
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
			
		//Fill constant / Compare value to constant
		}else if( selectedOperation.equals( E.FILL_CONSTANT ) || selectedOperation.equals( E.COMPAREVALUE_TO_CONSTANT ) ){
			
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
			
		//Fill string / Compare value to string
		}else if( selectedOperation.equals( E.FILL_STRING ) || selectedOperation.equals( E.COMPAREVALUE_TO_STRING ) ){
		
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
	
/*		//GAINVALUE TO ELEMENT
		}else if( selectedOperation.equals( E.GAINVALUE_TO_ELEMENTSTORAGE ) ){
			
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
*/
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

		if( 
				selectedOperation.equals( E.FILL_ELEMENT ) || 
				selectedOperation.equals( E.FILL_CONSTANT ) ||
				selectedOperation.equals( E.FILL_STRING ) ||
				selectedOperation.equals( E.COMPAREVALUE_TO_STORED )  || 
				selectedOperation.equals( E.COMPAREVALUE_TO_CONSTANT ) || 
				selectedOperation.equals( E.COMPAREVALUE_TO_STRING ) ||
				selectedOperation.equals( E.GAINVALUE_TO_ELEMENTSTORAGE )
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
		if( selectedOperation.equals( E.COMPAREVALUE_TO_STORED ) || selectedOperation.equals( E.COMPAREVALUE_TO_CONSTANT ) || selectedOperation.equals( E.COMPAREVALUE_TO_STRING ) ){
				
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
		
		//Fill element
		if( comboOperationList.getSelectedIndex() ==  E.FILL_ELEMENT.getIndex() ){
			return new FillWithBaseElementOperation( fieldBaseElementSelector.getSelectedDataModel() );
			
		//Fill constant
		}else if(comboOperationList.getSelectedIndex() ==  E.FILL_CONSTANT.getIndex() ){
			return new FillWithConstantElementOperation( fieldConstantSelector.getSelectedDataModel() );
			
		//Fill string
		}else if( comboOperationList.getSelectedIndex() ==  E.FILL_STRING.getIndex() ){
			return new FillWithStringOperation( fieldString.getText() );
			
		//Clear
		}else if( comboOperationList.getSelectedIndex() ==  E.CLEAR.getIndex() ){
			return new ClearOperation();
			
		//Tab
		}else if( comboOperationList.getSelectedIndex() ==  E.TAB.getIndex() ){
			return new TabOperation();
			
		//Click
		}else if( comboOperationList.getSelectedIndex() ==  E.CLICK.getIndex() ){
			return new ClickLeftOperation();			
		
		//COMPARE VALUE TO STORED
		}else if( comboOperationList.getSelectedIndex() ==  E.COMPAREVALUE_TO_STORED.getIndex() ){
			return new CompareValueToStoredElementOperation( fieldBaseElementSelector.getSelectedDataModel(), (CompareTypeListEnum)(comboCompareTypeList.getSelectedItem()), fieldPattern.getText() );
				
		//COMPARE VALUE TO CONSTANT
		}else if(comboOperationList.getSelectedIndex() ==  E.COMPAREVALUE_TO_CONSTANT.getIndex() ){
			return new CompareValueToConstantOperation( fieldConstantSelector.getSelectedDataModel(), (CompareTypeListEnum)(comboCompareTypeList.getSelectedItem()), fieldPattern.getText() );
				
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
