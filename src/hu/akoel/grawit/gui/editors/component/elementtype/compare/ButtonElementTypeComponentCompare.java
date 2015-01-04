package hu.akoel.grawit.gui.editors.component.elementtype.compare;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.ListRenderer;
import hu.akoel.grawit.core.operations.ClickOperation;
import hu.akoel.grawit.core.operations.ElementOperationAdapter;
import hu.akoel.grawit.enums.list.ElementTypeListEnum;
import hu.akoel.grawit.enums.list.elementtypeoperations.compare.ButtonElementTypeOperationsCompareListEnum;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ButtonElementTypeComponentCompare<E extends ButtonElementTypeOperationsCompareListEnum> extends ElementTypeComponentCompareInterface<E>{

	private static final long serialVersionUID = -6108131072338954554L;

	private JTextField fieldType;
	private JComboBox<E> comboOperationList;
		
	private JLabel labelType;
	private JLabel labelOperations;	
	
	private JLabel labelFiller;
	
	/**
	 * Uj
	 * 
	 */
	public ButtonElementTypeComponentCompare( ElementTypeListEnum elementType ){
		super();

		common( elementType, null );
		
	}
	
	/**
	 * 
	 * Mar letezo
	 * 
	 * @param key
	 * @param value
	 */
	public ButtonElementTypeComponentCompare( ElementTypeListEnum elementType , ElementOperationAdapter elementOperation ){
		super();
		
		common( elementType, elementOperation );		
		
	}
	
	private void common( ElementTypeListEnum elementType, ElementOperationAdapter elementOperation ){
		
		labelType = new JLabel( CommonOperations.getTranslation("editor.label.param.type") + ": ");
		labelOperations = new JLabel( CommonOperations.getTranslation("editor.label.param.operation") + ": ");
		labelFiller = new JLabel();
		
		fieldType = new JTextField( elementType.getTranslatedName() );
		fieldType.setEditable(false);
		
		comboOperationList = new JComboBox<>();
		for( int i = 0; i < E.getSize(); i++ ){
			comboOperationList.addItem( (E) E.getElementButtonOperationByIndex(i) );
		}
		
		comboOperationList.addItemListener( new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {

				int index = comboOperationList.getSelectedIndex();					

				//Ha megvaltoztattam a tipust
				if( e.getStateChange() == java.awt.event.ItemEvent.SELECTED ){ 
					
					if( comboOperationList.getItemAt(index).equals( E.NONE ) ){
						setValueContainer();						
					}	
						
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
		c.gridy = 1;
	
		//Kenyszeritem, hogy a kovetkezo setSelectedItem() hatasara vegrehajtsa a az itemStateChanged() metodust
		comboOperationList.setSelectedIndex(-1);

		
		//Kezdo ertek beallitasa
		if( null == elementOperation ){
			comboOperationList.setSelectedIndex(E.NONE.getIndex());
		}else{
			
			if( elementOperation instanceof ClickOperation  ){
				
				comboOperationList.setSelectedIndex(E.NONE.getIndex());
				
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
	}

	@Override
	public Component getComponent() {
		return this;
	}

	private void setValueContainer( ){
		
		GridBagConstraints c = new GridBagConstraints();		
		c.insets = new Insets(0,0,0,0);
		
		this.remove( labelFiller );
		
		//Filler
		c.gridy = 0;
		c.gridx = 4;
		c.gridwidth = 1;
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
/*		if( comboOperationList.getSelectedIndex() == E.NONE.getIndex() ){
			return new ClickOperation();
		}
*/		
		return null;
	
	}

}
