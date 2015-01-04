package hu.akoel.grawit.gui.editors.component.elementtype.compare;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.ListRenderer;
import hu.akoel.grawit.core.operations.ClickOperation;
import hu.akoel.grawit.core.operations.ElementOperationAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.variable.VariableRootDataModel;
import hu.akoel.grawit.enums.list.ElementTypeListEnum;
import hu.akoel.grawit.enums.list.elementtypeoperations.compare.ScriptElementTypeOperationsCompareListEnum;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ScriptElementTypeComponentCompare<E extends ScriptElementTypeOperationsCompareListEnum> extends ElementTypeComponentCompareInterface<E>{

	private static final long serialVersionUID = -6108131072338954554L;
	
	//Type
	private JLabel labelType;
	private JTextField fieldType;
	
	//Operation
	private JLabel labelOperations;	
	private JComboBox<E> comboOperationList;	
	
	//String - Mezo kitoltes
	private JLabel labelString;
	private JTextField fieldString;
	
	private JLabel labelFiller;
	
	public ScriptElementTypeComponentCompare( ElementTypeListEnum elementType , ElementOperationAdapter elementOperation, BaseRootDataModel baseRootDataModel, VariableRootDataModel variableRootDataModel ){
		super();
		
		common( elementType, elementOperation, baseRootDataModel, variableRootDataModel );		
		
	}
	
	@SuppressWarnings("unchecked")
	private void common( ElementTypeListEnum elementType , ElementOperationAdapter elementOperation, BaseRootDataModel baseRootDataModel, VariableRootDataModel variableRootDataModel ){
		
		labelType = new JLabel( CommonOperations.getTranslation("editor.label.param.type") + ": ");
		labelOperations = new JLabel( CommonOperations.getTranslation("editor.label.param.operation") + ": ");
		labelString = new JLabel( CommonOperations.getTranslation("editor.label.param.string") + ": ");
		labelFiller = new JLabel("");
		
		fieldType = new JTextField( elementType.getTranslatedName() );
		fieldType.setEditable(false);

		//OPERATION
		comboOperationList = new JComboBox<>();
		for( int i = 0; i < E.getSize(); i++ ){
			comboOperationList.addItem( (E) E.getOperationByIndex(i) );
		}		
		comboOperationList.addItemListener( new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {

				int index = comboOperationList.getSelectedIndex();				

				//Ha megvaltoztattam a tipust
				if( e.getStateChange() == java.awt.event.ItemEvent.SELECTED ){ 

					setValueContainer();
					
				}				
			}
		});			
				
		//Azert kell, hogy a setEditable() hatasara ne szurkuljon el a felirat
		//comboOperationList.setRenderer(new ElementTypeComponentRenderer());
		comboOperationList.setRenderer(new ListRenderer<E>());

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
		
		//Kenyszeritem, hogy a kovetkezo setSelectedItem() hatasara vegrehajtsa a az itemStateChanged() metodust
		comboOperationList.setSelectedIndex(-1);
		
		//Valtozok letrehozase
		fieldString = new JTextField( "" );
		
		//Kezdo ertek beallitasa
		if( null == elementOperation ){
			
			comboOperationList.setSelectedIndex(E.NONE.getIndex());
			
		//CLEAR PARAMETERS
		}else if( elementOperation instanceof ClickOperation  ){
				
			comboOperationList.setSelectedIndex(E.NONE.getIndex());			
			
		}		
		
	}	
	
	@SuppressWarnings("unchecked")
	@Override
	public E getSelectedOperation(ElementTypeListEnum elementType) {
		return(E)comboOperationList.getSelectedItem();
	}
	
	@Override
	public void setEnableModify(boolean enable) {
		
		comboOperationList.setEnabled( enable );		
		fieldString.setEditable( enable );

	}

	@Override
	public Component getComponent() {
		return this;
	}

	private void setValueContainer(){

		GridBagConstraints c = new GridBagConstraints();		
		c.insets = this.getInsets(); //new Insets(0,0,0,0);		
		
//		this.remove( labelString );
//		this.remove( fieldString );
		this.remove( labelFiller );
		
		c.gridy = 0;
		c.gridx = 4;
		c.gridwidth = 2;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.anchor = GridBagConstraints.WEST;	
		this.add( labelFiller, c );

		this.revalidate();
		this.repaint();
	}

	@Override
	public ElementOperationAdapter getElementOperation() {
		
		//NONE
/*		if( comboOperationList.getSelectedIndex() ==  E.NONE.getIndex() ){
			return new SpecialBaseAddStoreToParametersOperation( fieldBaseElementSelector.getSelectedDataModel() );

		}
*/	
		return null;
	}
	
}
