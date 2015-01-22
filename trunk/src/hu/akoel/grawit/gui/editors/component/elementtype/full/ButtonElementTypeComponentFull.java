package hu.akoel.grawit.gui.editors.component.elementtype.full;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.ListRenderer;
import hu.akoel.grawit.core.operations.ClickLeftOperation;
import hu.akoel.grawit.core.operations.ElementOperationAdapter;
import hu.akoel.grawit.core.treenodedatamodel.BaseElementDataModelAdapter;
import hu.akoel.grawit.enums.list.ElementTypeListEnum;
import hu.akoel.grawit.enums.list.elementtypeoperations.full.ButtonElementTypeOperationsFullListEnum;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ButtonElementTypeComponentFull<E extends ButtonElementTypeOperationsFullListEnum> extends ElementTypeComponentFullInterface<E>{

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
	public ButtonElementTypeComponentFull( BaseElementDataModelAdapter baseElement ){
		super();

		common( baseElement, null );
		
	}
	
	/**
	 * 
	 * Mar letezo
	 * 
	 * @param key
	 * @param value
	 */
	public ButtonElementTypeComponentFull( BaseElementDataModelAdapter baseElement , ElementOperationAdapter elementOperation ){
		super();
		
		common( baseElement, elementOperation );		
		
	}
	
	private void common( BaseElementDataModelAdapter baseElement, ElementOperationAdapter elementOperation ){
		
		ElementTypeListEnum elementType = baseElement.getElementType();
		
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
					
					if( comboOperationList.getItemAt(index).equals( E.CLICK ) ){
						setValueContainer( false );
						
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
			comboOperationList.setSelectedIndex(E.CLICK.getIndex());
		}else{
			
			if( elementOperation instanceof ClickLeftOperation  ){
				
				comboOperationList.setSelectedIndex(E.CLICK.getIndex());
				
			//Minden egyeb esetben
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
	}

	@Override
	public Component getComponent() {
		return this;
	}

	private void setValueContainer( boolean show ){
		
		GridBagConstraints c = new GridBagConstraints();		
		c.insets = new Insets(0,0,0,0);
		
		this.remove( labelFiller );
		
		if( show ){		
			
		}else{
			
			//Filler
			c.gridy = 0;
			c.gridx = 4;
			c.gridwidth = 1;
			c.weighty = 0;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 1;
			c.anchor = GridBagConstraints.WEST;
			this.add( labelFiller, c );
			
		}
		
		this.revalidate();
		this.repaint();
	}
	
/*	class MyRenderer extends BasicComboBoxRenderer {

		private static final long serialVersionUID = -4562181616721578685L;

		@Override
		public Component getListCellRendererComponent(JList list, Object value,	int index, boolean isSelected, boolean cellHasFocus) {

			Component c = super.getListCellRendererComponent(list, ((ButtonElementTypeOperationsListEnum)value).getTranslatedName(), index, isSelected, cellHasFocus);

			return c;
		}
	}
*/
	@Override
	public ElementOperationAdapter getElementOperation() {
		
		//CLICK
		if( comboOperationList.getSelectedIndex() == E.CLICK.getIndex() ){
			return new ClickLeftOperation();
		}
		
		return null;
	
	}

}
