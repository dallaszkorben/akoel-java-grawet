package hu.akoel.grawit.gui.editors.component.elementtype.compare;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.ListRenderer;
import hu.akoel.grawit.core.operation.interfaces.ElementOperationAdapter;
import hu.akoel.grawit.core.operations.CompareTextToStoredElementOperation;
import hu.akoel.grawit.core.operations.CompareTextToStringOperation;
import hu.akoel.grawit.core.operations.CompareTextToConstantOperation;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.constant.ConstantRootDataModel;
import hu.akoel.grawit.enums.list.CompareTypeListEnum;
import hu.akoel.grawit.enums.list.ElementTypeListEnum;
import hu.akoel.grawit.enums.list.elementtypeoperations.compare.TextElementTypeOperationsCompareListEnum;
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

public class TextElementTypeComponentCompare<E extends TextElementTypeOperationsCompareListEnum> extends ElementTypeComponentCompareInterface<E>{

	private static final long serialVersionUID = -6108131072338954554L;

	//Type
	private JLabel labelType;
	private JTextField fieldType;
	
	//Operation
	private JLabel labelOperations;	
	private JComboBox<E> comboOperationList;
	
	//Pattern
	private JLabel labelPattern;
	private JTextField fieldPattern;
	
	//Message
	private JLabel labelMessage;
	private JTextField fieldMessage;
	
	//Constant selector - Mezo kitoltes
	private JLabel labelConstantSelector;
	private ConstantTreeSelectorComponent fieldConstantSelector;
	
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
	public TextElementTypeComponentCompare( ElementTypeListEnum elementType, BaseRootDataModel baseRootDataModel, ConstantRootDataModel constantRootDataModel ){
		super();

		common( elementType, null, baseRootDataModel, constantRootDataModel );
		
	}
	
	/**
	 * 
	 * Mar letezo
	 * 
	 * @param key
	 * @param value
	 */
	public TextElementTypeComponentCompare( ElementTypeListEnum elementType , ElementOperationAdapter elementOperation, BaseRootDataModel baseRootDataModel, ConstantRootDataModel constantRootDataModel ){
		super();
		
		common( elementType, elementOperation, baseRootDataModel, constantRootDataModel );		
		
	}
	
	private void common( ElementTypeListEnum elementType, ElementOperationAdapter elementOperation, BaseRootDataModel baseRootDataModel, ConstantRootDataModel constantRootDataModel ){
		
		labelType = new JLabel( CommonOperations.getTranslation("editor.label.step.type") + ": ");
		labelOperations = new JLabel( CommonOperations.getTranslation("editor.label.step.operation") + ": ");
		labelPattern = new JLabel( CommonOperations.getTranslation("editor.label.step.pattern") + ": ");
		labelMessage = new JLabel( CommonOperations.getTranslation("editor.label.step.message") + ": ");
		labelString = new JLabel( CommonOperations.getTranslation("editor.label.step.string") + ": ");
		labelConstantSelector = new JLabel( CommonOperations.getTranslation("editor.label.step.constant") + ": ");
		labelBaseElementSelector = new JLabel( CommonOperations.getTranslation("editor.label.step.baseelement") + ": ");
		labelCompareType = new JLabel( CommonOperations.getTranslation("editor.label.step.comparetype") + ": ");
		labelFiller = new JLabel();
		
		fieldType = new JTextField( elementType.getTranslatedName() );
		fieldType.setEditable(false);
		
		fieldPattern = new JTextField();
		fieldMessage = new JTextField();
		
		//OPERATION
		comboOperationList = new JComboBox<>();
		for( int i = 0; i < E.getSize(); i++ ){
			comboOperationList.addItem( (E) E.getElementTextOperationByIndex(i) );
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
		fieldBaseElementSelector = new BaseElementTreeSelectorComponent( baseRootDataModel );
		fieldString = new JTextField( "" );
		
		//Default value for CompareType
		comboCompareTypeList.setSelectedIndex( CompareTypeListEnum.EQUAL.getIndex() );
		
		if( null == elementOperation ){

			fieldString.setText( "" );
			comboOperationList.setSelectedIndex(E.COMPARETEXT_TO_STRING.getIndex());

		//COMPARE TEXT TO STRING
		}else if( elementOperation instanceof CompareTextToStringOperation ){
								
			fieldString.setText( ((CompareTextToStringOperation)elementOperation).getCompareTo() );
			comboCompareTypeList.setSelectedIndex( ((CompareTextToStringOperation)elementOperation).getCompareType().getIndex() );
			comboOperationList.setSelectedIndex(E.COMPARETEXT_TO_STRING.getIndex());

		//COMPARE TEXT TO CONSTANT
		}else if( elementOperation instanceof CompareTextToConstantOperation ){
				
			fieldConstantSelector = new ConstantTreeSelectorComponent( constantRootDataModel, ((CompareTextToConstantOperation)elementOperation).getConstantElement() );				
			comboOperationList.setSelectedIndex(E.COMPARETEXT_TO_CONSTANT.getIndex());
			comboCompareTypeList.setSelectedIndex( ((CompareTextToConstantOperation)elementOperation).getCompareType().getIndex() );

		//COMPARE TEXT TO STORED
		}else if( elementOperation instanceof CompareTextToStoredElementOperation ){
								
			fieldBaseElementSelector = new BaseElementTreeSelectorComponent( baseRootDataModel, ((CompareTextToStoredElementOperation)elementOperation).getBaseElementForSearch() );
			comboCompareTypeList.setSelectedIndex( ((CompareTextToStoredElementOperation)elementOperation).getCompareType().getIndex() );
			comboOperationList.setSelectedIndex(E.COMPARETEXT_TO_STORED.getIndex());

		}
	}	
	
	@Override
	public E getSelectedOperation( ElementTypeListEnum elementType ){
		return(E)comboOperationList.getSelectedItem();
	}
	
	public String getPattern(){
		return fieldPattern.getText();
	}	
	
	public String getMessage(){
		return fieldMessage.getText();
	}	
	
	@Override
	public void setEnableModify(boolean enable) {
		comboOperationList.setEnabled( enable );		
		
		fieldString.setEditable( enable );

		fieldBaseElementSelector.setEnableModify(enable);
		
		fieldConstantSelector.setEnableModify( enable );

		fieldMessage.setEditable( enable );

		comboCompareTypeList.setEnabled( enable );
		
		fieldPattern.setEditable( enable );		
		
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
		this.remove( labelConstantSelector );
		this.remove( fieldConstantSelector );	
		this.remove( fieldMessage );
		this.remove( labelMessage );
		this.remove( comboCompareTypeList );
		this.remove( labelCompareType );
		
		this.remove( labelFiller );
		
		//Compare element
		if( selectedOperation.equals( E.COMPARETEXT_TO_STORED ) ){
			
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
			
		//Compare constant
		}else if( selectedOperation.equals( E.COMPARETEXT_TO_CONSTANT ) ){
			
			c.gridy = 0;
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
			
		//Compare string
		}else if( selectedOperation.equals( E.COMPARETEXT_TO_STRING ) ){
		
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
			
		}
		
		//Compare element
		if( selectedOperation.equals( E.COMPARETEXT_TO_STORED ) || selectedOperation.equals( E.COMPARETEXT_TO_CONSTANT ) || selectedOperation.equals( E.COMPARETEXT_TO_STRING ) ){
				
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
		
		//COMPARE TEXT TO STORED
		if( comboOperationList.getSelectedIndex() ==  E.COMPARETEXT_TO_STORED.getIndex() ){
			return new CompareTextToStoredElementOperation( fieldBaseElementSelector.getSelectedDataModel(), (CompareTypeListEnum)(comboCompareTypeList.getSelectedItem()), fieldPattern.getText() );
					
		//COMPARE TEXT TO CONSTANT
		}else if(comboOperationList.getSelectedIndex() ==  E.COMPARETEXT_TO_CONSTANT.getIndex() ){
			return new CompareTextToConstantOperation( fieldConstantSelector.getSelectedDataModel(), (CompareTypeListEnum)(comboCompareTypeList.getSelectedItem()), fieldPattern.getText() );
					
		//COMPARE TEXT TO STRING
		}else if( comboOperationList.getSelectedIndex() ==  E.COMPARETEXT_TO_STRING.getIndex() ){
			return new CompareTextToStringOperation( fieldString.getText(), (CompareTypeListEnum)(comboCompareTypeList.getSelectedItem()), fieldPattern.getText() );
	
		}
		
		return null;
	
	}
	
}
