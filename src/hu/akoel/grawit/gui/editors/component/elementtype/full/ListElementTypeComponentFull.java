package hu.akoel.grawit.gui.editors.component.elementtype.full;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.IntTextField;
import hu.akoel.grawit.ListRenderer;
import hu.akoel.grawit.core.operation.interfaces.ElementOperationAdapter;
import hu.akoel.grawit.core.operations.ClickLeftOperation;
import hu.akoel.grawit.core.operations.CompareListSizeToIntegerOperation;
import hu.akoel.grawit.core.operations.CompareSelectedListToStoredElementOperation;
import hu.akoel.grawit.core.operations.CompareSelectedListToStringOperation;
import hu.akoel.grawit.core.operations.CompareSelectedListToConstantOperation;
import hu.akoel.grawit.core.operations.CompareContainListStoredElementOperation;
import hu.akoel.grawit.core.operations.CompareContainListStringOperation;
import hu.akoel.grawit.core.operations.CompareContainListConstantOperation;
import hu.akoel.grawit.core.operations.GainListToElementStorageOperation;
import hu.akoel.grawit.core.operations.MoveToElementOperation;
import hu.akoel.grawit.core.operations.OutputStoredElementOperation;
import hu.akoel.grawit.core.operations.SelectBaseElementOperation;
import hu.akoel.grawit.core.operations.SelectStringOperation;
import hu.akoel.grawit.core.operations.SelectConstantElementOperation;
import hu.akoel.grawit.core.operations.TabOperation;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseElementDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.constant.ConstantRootDataModel;
import hu.akoel.grawit.enums.list.CompareTypeListEnum;
import hu.akoel.grawit.enums.list.ContainTypeListEnum;
import hu.akoel.grawit.enums.list.ElementTypeListEnum;
import hu.akoel.grawit.enums.list.ListCompareByListEnum;
import hu.akoel.grawit.enums.list.ListGainByListEnum;
import hu.akoel.grawit.enums.list.ListSelectionByListEnum;
import hu.akoel.grawit.enums.list.elementtypeoperations.full.ListElementTypeOperationsFullListEnum;
import hu.akoel.grawit.gui.editors.component.treeselector.BaseElementTreeSelectorComponent;
import hu.akoel.grawit.gui.editors.component.treeselector.ConstantTreeSelectorComponent;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.InputVerifier;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

public class ListElementTypeComponentFull<E extends ListElementTypeOperationsFullListEnum>
		extends ElementTypeComponentFullInterface<E> {

	private static final long serialVersionUID = -6108131072338954554L;

	private JTextField fieldType;
	private JComboBox<E> comboOperationList;

	// Pattern
	private JTextField fieldPattern;
	private JLabel labelPattern;

	// Type
	private JLabel labelType;
	private JLabel labelOperations;

	// Constant selector - Mezo kitoltes
	private JLabel labelConstantSelector;
	private ConstantTreeSelectorComponent fieldConstantSelector;

	// BaseElement selector - Mezo kitoltes
	private JLabel labelBaseElementSelector;
	private BaseElementTreeSelectorComponent fieldBaseElementSelector;

	// String - Mezo kitoltes
	private JLabel labelString;
	private JTextField fieldString;

	private JLabel labelSize;
	private JTextField fieldSize;

	// Selection by
	private JLabel labelSelectionBy;
	private JComboBox<ListSelectionByListEnum> comboSelectionBy;

	// Gain by
	private JLabel labelGainBy;
	private JComboBox<ListGainByListEnum> comboGainBy;

	// Compare Selected List element by
	private JLabel labelCompareSelectedListElementBy;
	private JComboBox<ListCompareByListEnum> comboCompareSelectedListElementBy;

	// Compare Selected List element type
	private JLabel labelCompareSelectedListElementType;
	private JComboBox<CompareTypeListEnum> comboCompareSelectedListElementType;

	// Contain List by
	private JLabel labelContainListBy;
	private JComboBox<ListCompareByListEnum> comboContainListBy;

	// Contain List type
	private JLabel labelContainListType;
	private JComboBox<ContainTypeListEnum> comboContainListType;

	// Message - Mezo ertekenek megjelenitese
	private JLabel labelMessage;
	private JTextField fieldMessage;

	// FILLER
	private JLabel labelFiller;

	@Override
	public E getSelectedOperation(ElementTypeListEnum elementType) {
		return (E) comboOperationList.getSelectedItem();
	}

	public ListElementTypeComponentFull(
			BaseElementDataModelAdapter baseElement,
			ElementOperationAdapter elementOperation,
			BaseRootDataModel baseRootDataModel,
			ConstantRootDataModel constantRootDataModel) {
		super();

		common(baseElement, elementOperation, baseRootDataModel,
				constantRootDataModel);

	}

	private void common( BaseElementDataModelAdapter baseElement, ElementOperationAdapter elementOperation, BaseRootDataModel baseRootDataModel, ConstantRootDataModel constantRootDataModel ){
		
		ElementTypeListEnum elementType = baseElement.getElementType();
		
		labelType = new JLabel( CommonOperations.getTranslation("editor.label.step.type") + ": ");
		labelPattern = new JLabel( CommonOperations.getTranslation("editor.label.step.pattern") + ": ");
		labelOperations = new JLabel( CommonOperations.getTranslation("editor.label.step.operation") + ": ");
		labelSize = new JLabel( CommonOperations.getTranslation("editor.label.step.size") + ": ");
		labelString = new JLabel( CommonOperations.getTranslation("editor.label.step.string") + ": ");
		labelConstantSelector = new JLabel( CommonOperations.getTranslation("editor.label.step.constant") + ": ");
		labelBaseElementSelector = new JLabel( CommonOperations.getTranslation("editor.label.step.baseelement") + ": ");
		labelMessage = new JLabel( CommonOperations.getTranslation("editor.label.step.message") + ": ");		
		labelCompareSelectedListElementType = new JLabel( CommonOperations.getTranslation("editor.label.step.comparetype") + ": ");
		labelContainListType = new JLabel( CommonOperations.getTranslation("editor.label.step.containtype") + ": ");
		labelSelectionBy = new JLabel( CommonOperations.getTranslation("editor.label.step.selectionby") + ": ");		
		labelCompareSelectedListElementBy = new JLabel( CommonOperations.getTranslation("editor.label.step.compareby") + ": ");
		labelContainListBy = new JLabel( CommonOperations.getTranslation("editor.label.step.containsby") + ": ");
		
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
		
		//COMPARE
		comboCompareSelectedListElementBy = new JComboBox<>();
		for( int i = 0; i < ListCompareByListEnum.getSize(); i++ ){
			comboCompareSelectedListElementBy.addItem((ListCompareByListEnum)ListCompareByListEnum.getListSelectionTypeByOrder(i) );
		}
		comboCompareSelectedListElementType = new JComboBox<CompareTypeListEnum>();
		for( int i = 0; i < CompareTypeListEnum.getSize(); i++ ){
			comboCompareSelectedListElementType.addItem( CompareTypeListEnum.getCompareTypeByIndex(i) );
		}

		//CONTAIN
		comboContainListBy = new JComboBox<>();
		for( int i = 0; i < ListCompareByListEnum.getSize(); i++ ){
			comboContainListBy.addItem((ListCompareByListEnum)ListCompareByListEnum.getListSelectionTypeByOrder(i) );
		}
		comboContainListType = new JComboBox<ContainTypeListEnum>();
		for( int i = 0; i < ContainTypeListEnum.getSize(); i++ ){
			comboContainListType.addItem( ContainTypeListEnum.getCompareTypeByIndex(i) );
		}
		
		//
		//Azert kell, hogy a setEditable() hatasara ne szurkuljon el a felirat, es hogy legyen forditas
		//
		comboOperationList.setRenderer( new ListRenderer<E>() );
		comboSelectionBy.setRenderer( new ListRenderer<ListSelectionByListEnum>() );
		comboGainBy.setRenderer( new ListRenderer<ListGainByListEnum>() );

		comboCompareSelectedListElementType.setRenderer(new ListRenderer<CompareTypeListEnum>());
		comboCompareSelectedListElementBy.setRenderer( new ListRenderer<ListCompareByListEnum>() );

		comboContainListType.setRenderer(new ListRenderer<ContainTypeListEnum>());
		comboContainListBy.setRenderer( new ListRenderer<ListCompareByListEnum>() );
		
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
		comboCompareSelectedListElementType.setSelectedIndex( -1 );	
		
		//
		//Default ertek
		//
		comboSelectionBy.setSelectedIndex(ListSelectionByListEnum.BYVALUE.getIndex());
		comboGainBy.setSelectedIndex(ListGainByListEnum.BYVALUE.getIndex());
		
		comboCompareSelectedListElementType.setSelectedIndex( CompareTypeListEnum.EQUAL.getIndex() );
		comboCompareSelectedListElementBy.setSelectedIndex(ListCompareByListEnum.BYVALUE.getIndex());
		
		comboContainListType.setSelectedIndex( ContainTypeListEnum.CONTAINS.getIndex() );
		comboContainListBy.setSelectedIndex(ListCompareByListEnum.BYVALUE.getIndex());				
		
		//Valtozok letrehozase
		fieldConstantSelector = new ConstantTreeSelectorComponent( constantRootDataModel );
		
		//Arra az esetre, ha a muvelethez hasznalt baseElement meg nem kivalasztott akkor az alap alapElemet javasolja hasznalni
		fieldBaseElementSelector = new BaseElementTreeSelectorComponent( baseRootDataModel, baseElement, false );
		//fieldBaseElementSelector = new BaseElementTreeSelectorComponent( baseRootDataModel );
		
		fieldString = new JTextField( "" );		
		fieldSize = new IntTextField( 0, 4 );
		
/*		fieldSize.settteInputVerifier( new InputVerifier() {
			String goodValue = String.valueOf( 0 );			
			@Override
			public boolean verify(JComponent input) {
				JTextField text = (JTextField)input;
				String possibleValue = text.getText();
				try{
					goodValue = String.valueOf( Integer.valueOf( possibleValue) );
				}catch( Exception e){					
					return false;
				}finally{
					text.setText( goodValue);
				}
				return true;
			}
		});	
*/		

		//Kezdo ertek beallitasa
		if( null == elementOperation ){
			
			comboOperationList.setSelectedIndex(E.CLICK.getIndex());
			comboSelectionBy.setSelectedIndex( ListSelectionByListEnum.BYVALUE.getIndex() );
			comboGainBy.setSelectedIndex(ListGainByListEnum.BYVALUE.getIndex());
			
			comboCompareSelectedListElementBy.setSelectedIndex(ListCompareByListEnum.BYVALUE.getIndex());
			comboContainListBy.setSelectedIndex(ListCompareByListEnum.BYVALUE.getIndex());
			
		}else{
			
			//CLICK
			if( elementOperation instanceof ClickLeftOperation  ){
				
				comboOperationList.setSelectedIndex(E.CLICK.getIndex());
				
			//MOVE TO ELEMENT
			}else if( elementOperation instanceof MoveToElementOperation ){
					
				comboOperationList.setSelectedIndex(E.MOVE_TO_ELEMENT.getIndex());
					
			//TAB
			}else if( elementOperation instanceof TabOperation ){
				
				comboOperationList.setSelectedIndex(E.TAB.getIndex());
				
			//SELECT CONSTANT
			}else if( elementOperation instanceof SelectConstantElementOperation ){
								
				fieldConstantSelector = new ConstantTreeSelectorComponent( constantRootDataModel, ((SelectConstantElementOperation)elementOperation).getConstantElement() );
				comboOperationList.setSelectedIndex(E.SELECT_CONSTANT.getIndex());
				
				comboSelectionBy.setSelectedIndex( ((SelectConstantElementOperation)elementOperation).getSelectionBy().getIndex() );
				
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
		
			//CONTAIN CONSTANT
			}else if( elementOperation instanceof CompareContainListConstantOperation ){
					
					fieldConstantSelector = new ConstantTreeSelectorComponent( constantRootDataModel, ((CompareContainListConstantOperation)elementOperation).getConstantElement() );				
					comboOperationList.setSelectedIndex(E.CONTAIN_CONSTANT.getIndex());
					comboContainListType.setSelectedIndex( ((CompareContainListConstantOperation)elementOperation).getContainType().getIndex() );
					comboContainListBy.setSelectedIndex( ((CompareContainListConstantOperation)elementOperation).getContainBy().getIndex() );

			//CONTAIN TO STORED
			}else if( elementOperation instanceof CompareContainListStoredElementOperation ){
									
					fieldBaseElementSelector = new BaseElementTreeSelectorComponent( baseRootDataModel, ((CompareContainListStoredElementOperation)elementOperation).getBaseElementForSearch() );					
					comboOperationList.setSelectedIndex(E.CONTAIN_STORED.getIndex());
					comboContainListType.setSelectedIndex( ((CompareContainListStoredElementOperation)elementOperation).getContainType().getIndex() );
					comboContainListBy.setSelectedIndex( ((CompareContainListStoredElementOperation)elementOperation).getContainBy().getIndex() );
					
			//CONTAIN TO STRING
			}else if( elementOperation instanceof CompareContainListStringOperation ){
									
					fieldString.setText( ((CompareContainListStringOperation)elementOperation).getCompareTo() );
					comboOperationList.setSelectedIndex(E.CONTAIN_STRING.getIndex());
					comboContainListType.setSelectedIndex( ((CompareContainListStringOperation)elementOperation).getContainType().getIndex() );					
					comboContainListBy.setSelectedIndex( ((CompareContainListStringOperation)elementOperation).getContainBy().getIndex() );
				
			//COMPARE TO CONSTANT
			}else if( elementOperation instanceof CompareSelectedListToConstantOperation ){
				
				fieldConstantSelector = new ConstantTreeSelectorComponent( constantRootDataModel, ((CompareSelectedListToConstantOperation)elementOperation).getConstantElement() );				
				comboOperationList.setSelectedIndex(E.COMPARE_TO_CONSTANT.getIndex());
				comboCompareSelectedListElementType.setSelectedIndex( ((CompareSelectedListToConstantOperation)elementOperation).getCompareType().getIndex() );
				comboCompareSelectedListElementBy.setSelectedIndex( ((CompareSelectedListToConstantOperation)elementOperation).getCompareBy().getIndex() );

			//COMPARE TO STORED
			}else if( elementOperation instanceof CompareSelectedListToStoredElementOperation ){
								
				fieldBaseElementSelector = new BaseElementTreeSelectorComponent( baseRootDataModel, ((CompareSelectedListToStoredElementOperation)elementOperation).getBaseElementForSearch() );
				comboCompareSelectedListElementType.setSelectedIndex( ((CompareSelectedListToStoredElementOperation)elementOperation).getCompareType().getIndex() );
				comboOperationList.setSelectedIndex(E.COMPARE_TO_STORED.getIndex());
				comboCompareSelectedListElementBy.setSelectedIndex( ((CompareSelectedListToStoredElementOperation)elementOperation).getCompareBy().getIndex() );
				
			//COMPARE TO STRING
			}else if( elementOperation instanceof CompareSelectedListToStringOperation ){
								
				fieldString.setText( ((CompareSelectedListToStringOperation)elementOperation).getCompareTo() );
				comboCompareSelectedListElementType.setSelectedIndex( ((CompareSelectedListToStringOperation)elementOperation).getCompareType().getIndex() );
				comboOperationList.setSelectedIndex(E.COMPARE_TO_STRING.getIndex());
				comboCompareSelectedListElementBy.setSelectedIndex( ((CompareSelectedListToStringOperation)elementOperation).getCompareBy().getIndex() );
		
			//COMPARE LIST SIZE TO STRING
			}else if( elementOperation instanceof CompareListSizeToIntegerOperation ){
									
				fieldSize.setText( ((CompareListSizeToIntegerOperation)elementOperation).getIntegerToShow() );
				comboCompareSelectedListElementType.setSelectedIndex( ((CompareListSizeToIntegerOperation)elementOperation).getCompareType().getIndex() );
				comboOperationList.setSelectedIndex(E.COMPARE_SIZE_TO_INTEGER.getIndex());
								
			//GAIN TO ELEMENT
			}else if( elementOperation instanceof GainListToElementStorageOperation ){
				
				comboOperationList.setSelectedIndex(E.GAIN_TO_ELEMENT.getIndex());
				comboGainBy.setSelectedIndex( ((GainListToElementStorageOperation)elementOperation).getGainBy().getIndex() );
				fieldPattern.setText( ((GainListToElementStorageOperation)elementOperation).getStringPattern());	
				
			//OUTPUT STORED
			}else if ( elementOperation instanceof OutputStoredElementOperation ){
				
				fieldMessage.setText( ((OutputStoredElementOperation)elementOperation).getLabelToShow());
				comboOperationList.setSelectedIndex( E.OUTPUTSTORED.getIndex() );

			//Ha megvaltozott az alapElem es kulonbozik a tipusa
			}else{
				
				comboOperationList.setSelectedIndex(E.CLICK.getIndex());
				comboSelectionBy.setSelectedIndex( ListSelectionByListEnum.BYVALUE.getIndex() );
				comboGainBy.setSelectedIndex(ListGainByListEnum.BYVALUE.getIndex());
				comboCompareSelectedListElementBy.setSelectedIndex(ListCompareByListEnum.BYVALUE.getIndex());				
			}
		}
	}	

	@Override
	public void setEnableModify(boolean enable) {

		comboOperationList.setEnabled(enable);
		comboSelectionBy.setEnabled(enable);
		fieldString.setEditable(enable);
		fieldSize.setEditable(enable);
		fieldBaseElementSelector.setEnableModify(enable);
		fieldConstantSelector.setEnableModify(enable);
		fieldMessage.setEditable(enable);
		fieldPattern.setEditable(enable);
		comboCompareSelectedListElementType.setEnabled(enable);
		comboCompareSelectedListElementBy.setEnabled(enable);
		comboContainListType.setEnabled(enable);
		comboContainListBy.setEnabled(enable);
		comboGainBy.setEnabled(enable);
	}

	@Override
	public Component getComponent() {
		return this;
	}

	/**
	 * Megvaltoztattam a muveletet
	 * 
	 * @param selectedOperation
	 */
	private void setValueContainer(E selectedOperation) {

		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(0, 0, 0, 0);

		this.remove(labelBaseElementSelector);
		this.remove(fieldBaseElementSelector);
		this.remove(labelString);
		this.remove(fieldString);
		this.remove(labelSize);
		this.remove(fieldSize);
		this.remove(labelConstantSelector);
		this.remove(fieldConstantSelector);
		this.remove(labelFiller);
		this.remove(labelSelectionBy);
		this.remove(comboSelectionBy);
		this.remove(labelPattern);
		this.remove(fieldPattern);
		this.remove(labelMessage);
		this.remove(fieldMessage);
		this.remove(labelCompareSelectedListElementType);
		this.remove(comboCompareSelectedListElementType);
		this.remove(labelCompareSelectedListElementBy);
		this.remove(comboCompareSelectedListElementBy);
		this.remove(labelContainListType);
		this.remove(comboContainListType);
		this.remove(labelContainListBy);
		this.remove(comboContainListBy);
		this.remove(labelGainBy);
		this.remove(comboGainBy);

		// Fill element
		if (selectedOperation.equals(E.SELECT_BASE)) {

			c.gridy = 0;
			c.gridx = 4;
			c.gridwidth = 1;
			c.weighty = 0;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 0;
			c.anchor = GridBagConstraints.WEST;
			this.add(labelBaseElementSelector, c);

			c.gridx = 5;
			c.weightx = 1;
			this.add(fieldBaseElementSelector, c);

			putSelectionBy(c);

			// Fill constant
		} else if (selectedOperation.equals(E.SELECT_CONSTANT)) {

			c.gridy = 0;
			c.gridx = 4;
			c.gridwidth = 1;
			c.weighty = 0;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 0;
			c.anchor = GridBagConstraints.WEST;
			this.add(labelConstantSelector, c);

			c.gridx = 5;
			c.weightx = 1;
			this.add(fieldConstantSelector, c);

			putSelectionBy(c);

			// Fill string
		} else if (selectedOperation.equals(E.SELECT_STRING)) {

			c.gridy = 0;
			c.gridx = 4;
			c.gridwidth = 1;
			c.weighty = 0;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 0;
			c.anchor = GridBagConstraints.WEST;
			this.add(labelString, c);

			c.gridx = 5;
			c.weightx = 1;
			this.add(fieldString, c);

			putSelectionBy(c);

			// COMPARE TO STORED
		} else if (selectedOperation.equals(E.COMPARE_TO_STORED)) {

			c.gridy = 0;
			c.gridx = 4;
			c.gridwidth = 1;
			c.weighty = 0;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 0;
			c.anchor = GridBagConstraints.WEST;
			this.add(labelBaseElementSelector, c);

			c.gridx = 5;
			c.weightx = 1;
			this.add(fieldBaseElementSelector, c);

			putListCompareSelectedListElementBy(c);

			// COMPARE TO CONSTANT
		} else if (selectedOperation.equals(E.COMPARE_TO_CONSTANT)) {

			c.gridy = 0;
			c.gridx = 4;
			c.gridwidth = 1;
			c.weighty = 0;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 0;
			c.anchor = GridBagConstraints.WEST;
			this.add(labelConstantSelector, c);

			c.gridx = 5;
			c.weightx = 1;
			this.add(fieldConstantSelector, c);

			putListCompareSelectedListElementBy(c);

			// COMPARE TO STRING
		} else if (selectedOperation.equals(E.COMPARE_TO_STRING)) {

			c.gridy = 0;
			c.gridx = 4;
			c.gridwidth = 1;
			c.weighty = 0;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 0;
			c.anchor = GridBagConstraints.WEST;
			this.add(labelString, c);

			c.gridx = 5;
			c.weightx = 1;
			this.add(fieldString, c);

			putListCompareSelectedListElementBy(c);

			// COMPARE LIST SIZE TO STRING
		} else if (selectedOperation.equals(E.COMPARE_SIZE_TO_INTEGER)) {

			c.gridy = 0;
			c.gridx = 4;
			c.gridwidth = 1;
			c.weighty = 0;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 0;
			c.anchor = GridBagConstraints.WEST;
			this.add(labelSize, c);

			c.gridx = 5;
			c.weightx = 1;
			this.add(fieldSize, c);

			putComparableType(c);

			// CONTAIN STORED
		} else if (selectedOperation.equals(E.CONTAIN_STORED)) {

			c.gridy = 0;
			c.gridx = 4;
			c.gridwidth = 1;
			c.weighty = 0;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 0;
			c.anchor = GridBagConstraints.WEST;
			this.add(labelBaseElementSelector, c);

			c.gridx = 5;
			c.weightx = 1;
			this.add(fieldBaseElementSelector, c);

			putListContainBy(c);

			// CONTAIN TO CONSTANT
		} else if (selectedOperation.equals(E.CONTAIN_CONSTANT)) {

			c.gridy = 0;
			c.gridx = 4;
			c.gridwidth = 1;
			c.weighty = 0;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 0;
			c.anchor = GridBagConstraints.WEST;
			this.add(labelConstantSelector, c);

			c.gridx = 5;
			c.weightx = 1;
			this.add(fieldConstantSelector, c);

			putListContainBy(c);

			// CONTAIN TO STRING
		} else if (selectedOperation.equals(E.CONTAIN_STRING)) {

			c.gridy = 0;
			c.gridx = 4;
			c.gridwidth = 1;
			c.weighty = 0;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 0;
			c.anchor = GridBagConstraints.WEST;
			this.add(labelString, c);

			c.gridx = 5;
			c.weightx = 1;
			this.add(fieldString, c);

			putListContainBy(c);

			// Tab
		} else if (selectedOperation.equals(E.TAB)) {

			// Filler
			c.gridy = 0;
			c.gridx = 4;
			c.gridwidth = 1;
			c.weighty = 0;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 1;
			c.anchor = GridBagConstraints.WEST;
			this.add(labelFiller, c);

			// Click
		} else if (selectedOperation.equals(E.CLICK)) {

			// Filler
			c.gridy = 0;
			c.gridx = 4;
			c.gridwidth = 1;
			c.weighty = 0;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 1;
			c.anchor = GridBagConstraints.WEST;
			this.add(labelFiller, c);

			// Move to Element
		} else if (selectedOperation.equals(E.MOVE_TO_ELEMENT)) {

			// Filler
			c.gridy = 0;
			c.gridx = 4;
			c.gridwidth = 1;
			c.weighty = 0;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 1;
			c.anchor = GridBagConstraints.WEST;
			this.add(labelFiller, c);

			// GAIN TO ELEMENT
		} else if (selectedOperation.equals(E.GAIN_TO_ELEMENT)) {

			// PATTERN
			c.gridy = 0;
			c.gridx = 4;
			c.gridwidth = 1;
			c.weighty = 0;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 0;
			c.anchor = GridBagConstraints.WEST;
			this.add(labelPattern, c);

			c.gridx = 5;
			c.weightx = 1;
			this.add(fieldPattern, c);

			putGainBy(c);

			// Output STORED
		} else if (selectedOperation.equals(E.OUTPUTSTORED)) {

			c.gridy = 0;
			c.gridx = 4;
			c.gridwidth = 1;
			c.weighty = 0;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 0;
			c.anchor = GridBagConstraints.WEST;
			this.add(labelMessage, c);

			c.gridx = 5;
			c.weightx = 1;
			this.add(fieldMessage, c);
		}

		this.revalidate();
		this.repaint();
	}

	/**
	 * Elhelyezi az Elvart viszont-t (ComparableType)
	 * 
	 * @param c
	 */
	private void putComparableType(GridBagConstraints c) {

		// EQUAL/DIFFERENT
		c.gridy = 2;
		c.gridx = 4;
		c.gridwidth = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0;
		c.anchor = GridBagConstraints.WEST;
		this.add(labelCompareSelectedListElementType, c);

		c.gridx = 5;
		c.weightx = 1;
		this.add(comboCompareSelectedListElementType, c);
	}

	/**
	 * Elhelyezi az Osszehasonlitas alapja-t es az (ComparableBy) Elvart
	 * viszont-t (ComparableType)
	 * 
	 * @param c
	 */
	private void putListCompareSelectedListElementBy(GridBagConstraints c) {

		// VALUE/TEXT
		c.gridy = 2;
		c.gridx = 2;
		c.gridwidth = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0;
		c.anchor = GridBagConstraints.WEST;
		this.add(labelCompareSelectedListElementBy, c);

		c.gridx = 3;
		c.weightx = 1;
		this.add(comboCompareSelectedListElementBy, c);

		// EQUAL/DIFFERENT
		c.gridy = 2;
		c.gridx = 4;
		c.gridwidth = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0;
		c.anchor = GridBagConstraints.WEST;
		this.add(labelCompareSelectedListElementType, c);

		c.gridx = 5;
		c.weightx = 1;
		this.add(comboCompareSelectedListElementType, c);
	}

	/**
	 * Elhelyezi Lista Contains eseten
	 * 
	 * 
	 * @param c
	 */
	private void putListContainBy(GridBagConstraints c) {

		// VALUE/TEXT
		c.gridy = 2;
		c.gridx = 2;
		c.gridwidth = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0;
		c.anchor = GridBagConstraints.WEST;
		this.add(labelContainListBy, c);

		c.gridx = 3;
		c.weightx = 1;
		this.add(comboContainListBy, c);

		// EQUAL/DIFFERENT
		c.gridy = 2;
		c.gridx = 4;
		c.gridwidth = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0;
		c.anchor = GridBagConstraints.WEST;
		this.add(labelContainListType, c);

		c.gridx = 5;
		c.weightx = 1;
		this.add(comboContainListType, c);
	}

	private void putSelectionBy(GridBagConstraints c) {

		// Selection by
		c.gridy = 1;
		c.gridx = 2;
		c.gridwidth = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0;
		c.anchor = GridBagConstraints.WEST;
		this.add(labelSelectionBy, c);

		c.gridx = 3;
		c.weightx = 0;
		this.add(comboSelectionBy, c);
		c.gridy = 1;
	}

	private void putGainBy(GridBagConstraints c) {

		// Selection by
		c.gridy = 1;
		c.gridx = 2;
		c.gridwidth = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0;
		c.anchor = GridBagConstraints.WEST;
		this.add(labelGainBy, c);

		c.gridx = 3;
		c.weightx = 0;
		this.add(comboGainBy, c);
		c.gridy = 1;
	}

	@Override
	public ElementOperationAdapter getElementOperation() {

		// Fill element
		if (comboOperationList.getSelectedIndex() == E.SELECT_BASE.getIndex()) {
			return new SelectBaseElementOperation(
					fieldBaseElementSelector.getSelectedDataModel(),
					(ListSelectionByListEnum) comboSelectionBy
							.getSelectedItem());

			// Fill constant
		} else if (comboOperationList.getSelectedIndex() == E.SELECT_CONSTANT
				.getIndex()) {
			return new SelectConstantElementOperation(
					fieldConstantSelector.getSelectedDataModel(),
					(ListSelectionByListEnum) comboSelectionBy
							.getSelectedItem());

			// Fill string
		} else if (comboOperationList.getSelectedIndex() == E.SELECT_STRING
				.getIndex()) {
			return new SelectStringOperation(fieldString.getText(),
					(ListSelectionByListEnum) comboSelectionBy
							.getSelectedItem());

			// Tab
		} else if (comboOperationList.getSelectedIndex() == E.TAB.getIndex()) {
			return new TabOperation();

			// Click
		} else if (comboOperationList.getSelectedIndex() == E.CLICK.getIndex()) {
			return new ClickLeftOperation();

			// Move to Element
		} else if (comboOperationList.getSelectedIndex() == E.MOVE_TO_ELEMENT
				.getIndex()) {
			return new MoveToElementOperation();

			// CONTAIN STORED
		} else if (comboOperationList.getSelectedIndex() == E.CONTAIN_STORED
				.getIndex()) {

			return new CompareContainListStoredElementOperation(
					fieldBaseElementSelector.getSelectedDataModel(),
					(ContainTypeListEnum) (comboContainListType
							.getSelectedItem()), fieldPattern.getText(),
					(ListCompareByListEnum) (comboContainListBy
							.getSelectedItem()));

			// CONTAIN CONSTANT
		} else if (comboOperationList.getSelectedIndex() == E.CONTAIN_CONSTANT
				.getIndex()) {
			return new CompareContainListConstantOperation(
					fieldConstantSelector.getSelectedDataModel(),
					(ContainTypeListEnum) (comboContainListType
							.getSelectedItem()), fieldPattern.getText(),
					(ListCompareByListEnum) (comboContainListBy
							.getSelectedItem()));

			// CONTAIN STRING
		} else if (comboOperationList.getSelectedIndex() == E.CONTAIN_STRING
				.getIndex()) {
			return new CompareContainListStringOperation(fieldString.getText(),
					(ContainTypeListEnum) (comboContainListType
							.getSelectedItem()), fieldPattern.getText(),
					(ListCompareByListEnum) (comboContainListBy
							.getSelectedItem()));

			// COMPARE TO STORED
		} else if (comboOperationList.getSelectedIndex() == E.COMPARE_TO_STORED
				.getIndex()) {

			return new CompareSelectedListToStoredElementOperation(
					fieldBaseElementSelector.getSelectedDataModel(),
					(CompareTypeListEnum) (comboCompareSelectedListElementType
							.getSelectedItem()), fieldPattern.getText(),
					(ListCompareByListEnum) (comboCompareSelectedListElementBy
							.getSelectedItem()));

			// COMPARE TO CONSTANT
		} else if (comboOperationList.getSelectedIndex() == E.COMPARE_TO_CONSTANT
				.getIndex()) {
			return new CompareSelectedListToConstantOperation(
					fieldConstantSelector.getSelectedDataModel(),
					(CompareTypeListEnum) (comboCompareSelectedListElementType
							.getSelectedItem()), fieldPattern.getText(),
					(ListCompareByListEnum) (comboCompareSelectedListElementBy
							.getSelectedItem()));

			// COMPARE TO STRING
		} else if (comboOperationList.getSelectedIndex() == E.COMPARE_TO_STRING
				.getIndex()) {
			return new CompareSelectedListToStringOperation(fieldString.getText(),
					(CompareTypeListEnum) (comboCompareSelectedListElementType
							.getSelectedItem()), fieldPattern.getText(),
					(ListCompareByListEnum) (comboCompareSelectedListElementBy
							.getSelectedItem()));

			// COMPARE LIST SIZE TO INTEGER
		} else if (comboOperationList.getSelectedIndex() == E.COMPARE_SIZE_TO_INTEGER
				.getIndex()) {
			return new CompareListSizeToIntegerOperation(fieldSize.getText(),
					(CompareTypeListEnum) (comboCompareSelectedListElementType
							.getSelectedItem()));

			// GAIN TO ELEMENT
		} else if (comboOperationList.getSelectedIndex() == E.GAIN_TO_ELEMENT
				.getIndex()) {
			return new GainListToElementStorageOperation(
					fieldPattern.getText(),
					(ListGainByListEnum) (comboGainBy.getSelectedItem()));

			// OUTPUTSTORED
		} else if (comboOperationList.getSelectedIndex() == E.OUTPUTSTORED
				.getIndex()) {
			return new OutputStoredElementOperation(fieldMessage.getText());

		}

		return null;
	}

}
