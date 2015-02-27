package hu.akoel.grawit.gui.editors.component.elementtype.full;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.ListRenderer;
import hu.akoel.grawit.core.operations.ClickLeftOperation;
import hu.akoel.grawit.core.operations.CompareTextToStoredElementOperation;
import hu.akoel.grawit.core.operations.CompareTextToStringOperation;
import hu.akoel.grawit.core.operations.CompareTextToConstantOperation;
import hu.akoel.grawit.core.operations.ElementOperationAdapter;
import hu.akoel.grawit.core.operations.GainTextToElementStorageOperation;
import hu.akoel.grawit.core.operations.OutputStoredElementOperation;
import hu.akoel.grawit.core.treenodedatamodel.BaseElementDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.constant.ConstantRootDataModel;
import hu.akoel.grawit.enums.list.CompareTypeListEnum;
import hu.akoel.grawit.enums.list.ElementTypeListEnum;
import hu.akoel.grawit.enums.list.elementtypeoperations.full.LinkElementTypeOperationsFullListEnum;
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

public class LinkElementTypeComponentFull<E extends LinkElementTypeOperationsFullListEnum> extends ElementTypeComponentFullInterface<E>{

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
	private ConstantTreeSelectorComponent fieldVariableSelector;
	
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
	public LinkElementTypeComponentFull( BaseElementDataModelAdapter baseElement, BaseRootDataModel baseRootDataModel, ConstantRootDataModel constantRootDataModel ){
		super();

		common( baseElement, null, baseRootDataModel, constantRootDataModel );
		
	}
	
	/**
	 * 
	 * Mar letezo
	 * 
	 * @param key
	 * @param value
	 */
	public LinkElementTypeComponentFull( BaseElementDataModelAdapter baseElement , ElementOperationAdapter elementOperation, BaseRootDataModel baseRootDataModel, ConstantRootDataModel constantRootDataModel ){
		super();
		
		common( baseElement, elementOperation, baseRootDataModel, constantRootDataModel );		
		
	}
	
	private void common( BaseElementDataModelAdapter baseElement, ElementOperationAdapter elementOperation, BaseRootDataModel baseRootDataModel, ConstantRootDataModel constantRootDataModel ){
		
		ElementTypeListEnum elementType = baseElement.getElementType();
		
		labelType = new JLabel( CommonOperations.getTranslation("editor.label.step.type") + ": ");
		labelOperations = new JLabel( CommonOperations.getTranslation("editor.label.step.operation") + ": ");
		labelPattern = new JLabel( CommonOperations.getTranslation("editor.label.step.pattern") + ": ");
		labelMessage = new JLabel( CommonOperations.getTranslation("editor.label.step.message") + ": ");
		labelString = new JLabel( CommonOperations.getTranslation("editor.label.step.string") + ": ");
		labelVariableSelector = new JLabel( CommonOperations.getTranslation("editor.label.step.constant") + ": ");
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
		//comboOperationList.setRenderer(new ElementTypeComponentRenderer());
		comboOperationList.setRenderer( new ListRenderer<E>() );
		
		//COMPARE TYPE
		comboCompareTypeList = new JComboBox<CompareTypeListEnum>();
		for( int i = 0; i < CompareTypeListEnum.getSize(); i++ ){
			comboCompareTypeList.addItem( CompareTypeListEnum.getCompareTypeByIndex(i) );
		}
						
		//Azert kell, hogy a setEditable() hatasara ne szurkuljon el a felirat
		comboCompareTypeList.setRenderer( new ListRenderer<CompareTypeListEnum>() );
		//comboCompareTypeList.setRenderer(new CompareTypeRenderer());
				
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
		fieldVariableSelector = new ConstantTreeSelectorComponent( constantRootDataModel );
		
		//Arra az esetre, ha a muvelethez hasznalt baseElement meg nem kivalasztott akkor az alap alapElemet javasolja hasznalni
		fieldBaseElementSelector = new BaseElementTreeSelectorComponent( baseRootDataModel, baseElement, false );		
		//fieldBaseElementSelector = new BaseElementTreeSelectorComponent( baseRootDataModel );
		
		fieldString = new JTextField( "" );
		
		//Default value for CompareType
		comboCompareTypeList.setSelectedIndex( CompareTypeListEnum.EQUAL.getIndex() );
		
		//Kezdo ertek beallitasa
		if( null == elementOperation ){
			comboOperationList.setSelectedIndex(E.CLICK.getIndex());
		}else{
			
			//CLICK
			if( elementOperation instanceof ClickLeftOperation  ){
				
				comboOperationList.setSelectedIndex(E.CLICK.getIndex());
				
			//COMPARE TEXT TO VARIABLE
			}else if( elementOperation instanceof CompareTextToConstantOperation ){
				
				fieldVariableSelector = new ConstantTreeSelectorComponent( constantRootDataModel, ((CompareTextToConstantOperation)elementOperation).getConstantElement() );				
				comboOperationList.setSelectedIndex(E.COMPARETEXT_TO_CONSTANT.getIndex());
				comboCompareTypeList.setSelectedIndex( ((CompareTextToConstantOperation)elementOperation).getCompareType().getIndex() );

			//COMPARE TEXT TO STORED
			}else if( elementOperation instanceof CompareTextToStoredElementOperation ){
								
				fieldBaseElementSelector = new BaseElementTreeSelectorComponent( baseRootDataModel, ((CompareTextToStoredElementOperation)elementOperation).getBaseElement() );
				comboCompareTypeList.setSelectedIndex( ((CompareTextToStoredElementOperation)elementOperation).getCompareType().getIndex() );
				comboOperationList.setSelectedIndex(E.COMPARETEXT_TO_STORED.getIndex());
				
			//COMPARE TEXT TO STRING
			}else if( elementOperation instanceof CompareTextToStringOperation ){
								
				fieldString.setText( ((CompareTextToStringOperation)elementOperation).getStringToShow() );
				comboCompareTypeList.setSelectedIndex( ((CompareTextToStringOperation)elementOperation).getCompareType().getIndex() );
				comboOperationList.setSelectedIndex(E.COMPARETEXT_TO_STRING.getIndex());
			
			//GAIN TEXT TO ELEMENT
			}else if( elementOperation instanceof GainTextToElementStorageOperation ){
					
				comboOperationList.setSelectedIndex(E.GAINTEXT_TO_ELEMENT.getIndex());
				fieldPattern.setText( ((GainTextToElementStorageOperation)elementOperation).getStringPattern());			
				
			//OUTPUT STORED
			}else if ( elementOperation instanceof OutputStoredElementOperation ){
				
				comboOperationList.setSelectedIndex( E.OUTPUTSTORED.getIndex() );		
				fieldMessage.setText( ((OutputStoredElementOperation)elementOperation).getMessageToShow());
								
			//Ha megvaltozott az alapElem es kulonbozik a tipusa
			}else{
				comboOperationList.setSelectedIndex(E.CLICK.getIndex());
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
			this.add( labelVariableSelector, c );
		
			c.gridx = 5;
			c.weightx = 1;
			this.add( fieldVariableSelector, c );
			
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
			
/*		//GAINTEXT TO VARIABLE
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
*/			
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
		
		//CLICK
		if( comboOperationList.getSelectedIndex() == E.CLICK.getIndex() ){
			return new ClickLeftOperation();
				
		//COMPARE TEXT TO STORED
		}else if( comboOperationList.getSelectedIndex() ==  E.COMPARETEXT_TO_STORED.getIndex() ){
			return new CompareTextToStoredElementOperation( fieldBaseElementSelector.getSelectedDataModel(), (CompareTypeListEnum)(comboCompareTypeList.getSelectedItem()), fieldPattern.getText() );
						
		//COMPARE TEXT TO VARIABLE
		}else if(comboOperationList.getSelectedIndex() ==  E.COMPARETEXT_TO_CONSTANT.getIndex() ){
			return new CompareTextToConstantOperation( fieldVariableSelector.getSelectedDataModel(), (CompareTypeListEnum)(comboCompareTypeList.getSelectedItem()), fieldPattern.getText() );
						
		//COMPARE TEXT TO STRING
		}else if( comboOperationList.getSelectedIndex() ==  E.COMPARETEXT_TO_STRING.getIndex() ){
			return new CompareTextToStringOperation( fieldString.getText(), (CompareTypeListEnum)(comboCompareTypeList.getSelectedItem()), fieldPattern.getText() );

		//GAINTEXT TO ELEMENT
		}else if( comboOperationList.getSelectedIndex() == E.GAINTEXT_TO_ELEMENT.getIndex() ){
			return new GainTextToElementStorageOperation( fieldPattern.getText() );

		//OUTPUTSTORED
		}else if( comboOperationList.getSelectedIndex() == E.OUTPUTSTORED.getIndex() ){
			return new OutputStoredElementOperation( fieldMessage.getText() );
						
		}
		
		return null;
	
	}
	

}
