package hu.akoel.grawit.gui.editors.component.operation;

import hu.akoel.grawit.core.operations.ClickOperation;
import hu.akoel.grawit.core.operations.ElementOperationInterface;
import hu.akoel.grawit.core.operations.GainTextPatternOperation;
import hu.akoel.grawit.enums.list.ElementTypeListEnum;
import hu.akoel.grawit.enums.list.operation.LinkOperationListEnum;
import hu.akoel.grawit.gui.editors.component.EditorComponentInterface;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

public class LinkOperationComponent extends JPanel implements EditorComponentInterface, OperationComponentInterface{

	private static final long serialVersionUID = -6108131072338954554L;

	private JTextField fieldType;
	private JComboBox<LinkOperationListEnum> comboOperationList;
	private JTextField fieldPattern;
		
	private JLabel labelType;
	private JLabel labelOperations;	
	private JLabel labelPattern;
	
	private JLabel labelFiller;
	
	/**
	 * Uj
	 * 
	 */
	public LinkOperationComponent( ElementTypeListEnum elementType ){
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
	public LinkOperationComponent( ElementTypeListEnum elementType , ElementOperationInterface elementOperation ){
		super();
		
		common( elementType, elementOperation );		
		
	}
	
	private void common( ElementTypeListEnum elementType, ElementOperationInterface elementOperation ){
		
		labelType = new JLabel("Tipus: ");
		labelOperations = new JLabel("Operation: ");
		labelPattern = new JLabel("Pattern: ");
		labelFiller = new JLabel();
		
		fieldType = new JTextField( elementType.getTranslatedName() );
		fieldType.setEditable(false);
		
		fieldPattern = new JTextField();
		
		comboOperationList = new JComboBox<>();
		for( int i = 0; i < LinkOperationListEnum.getSize(); i++ ){
			comboOperationList.addItem( LinkOperationListEnum.getElementFieldOperationByIndex(i) );
		}
		
		comboOperationList.addItemListener( new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {

				int index = comboOperationList.getSelectedIndex();					

				//Ha megvaltoztattam a tipust
				if( e.getStateChange() == java.awt.event.ItemEvent.SELECTED ){ 
					
					if( comboOperationList.getItemAt(index).equals( LinkOperationListEnum.CLICK ) ){
						setValueContainer( false );
						
					}else if( comboOperationList.getItemAt(index).equals( LinkOperationListEnum.GAINTEXTPATTERN ) ){
						setValueContainer( true );
						
					}		
				}				
			}
		});	
		
		//Azert kell, hogy a setEditable() hatasara ne szurkuljon el a felirat
		comboOperationList.setRenderer(new MyRenderer());
		
		//fieldString.setInputVerifier( new CommonOperations.ValueVerifier(parameterList, type, DEFAULT_VALUE, PARAMETERORDER_VALUE) );
		/*fieldString.setInputVerifier(new InputVerifier() {
			String goodValue = "";
			
			@Override
			public boolean verify(JComponent input) {
				JTextField text = (JTextField)input;
				String possibleValue = text.getText();

				try {
					//Kiprobalja, hogy konvertalhato-e
					Object value = VariableParametersStringComponent.this.type.getParameterClass(0).getConstructor(String.class).newInstance(possibleValue);
					parameterList.set( 0, value );
					goodValue = possibleValue;
					
				} catch (Exception e) {
					text.setText( goodValue );
					return false;
				}				
				return true;
			}
		});*/
		
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
	
		//Filler
/*		c.gridy = 0;
		c.gridx = 4;
		c.gridwidth = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.anchor = GridBagConstraints.WEST;
		this.add( labelFiller, c );
*/
		
		//Kenyszeritem, hogy a kovetkezo setSelectedItem() hatasara vegrehajtsa a az itemStateChanged() metodust
		comboOperationList.setSelectedIndex(-1);
		
		//Kezdo ertek beallitasa
		if( null == elementOperation ){
			comboOperationList.setSelectedIndex(LinkOperationListEnum.CLICK.getIndex());
		}else{
			
			if( elementOperation instanceof ClickOperation  ){
				
				comboOperationList.setSelectedIndex(LinkOperationListEnum.CLICK.getIndex());
				
			}else if( elementOperation instanceof GainTextPatternOperation ){
				
				comboOperationList.setSelectedIndex(LinkOperationListEnum.GAINTEXTPATTERN.getIndex());
				
			}
			
		}
	}	
	
	public LinkOperationListEnum getSelectedOperation( ElementTypeListEnum elementType ){
		return(LinkOperationListEnum)comboOperationList.getSelectedItem();
	}
	
	public String getPattern(){
		return fieldPattern.getText();
	}
	
	
	@Override
	public void setEnableModify(boolean enable) {
		comboOperationList.setEditable( enable );		
		
		if( null != fieldPattern  && fieldPattern.isVisible() ){
			fieldPattern.setEditable( enable );
		}
	}

	@Override
	public Component getComponent() {
		return this;
	}

	private void setValueContainer( boolean show ){
		
		GridBagConstraints c = new GridBagConstraints();		
		c.insets = new Insets(0,0,0,0);
		
		this.remove( labelPattern );
		this.remove( fieldPattern );
		this.remove( labelFiller );
		
		if( show ){		
		
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
	
	class MyRenderer extends BasicComboBoxRenderer {

		private static final long serialVersionUID = -4562181616721578685L;

		@Override
		public Component getListCellRendererComponent(JList list, Object value,	int index, boolean isSelected, boolean cellHasFocus) {

			Component c = super.getListCellRendererComponent(list, ((LinkOperationListEnum)value).getTranslatedName(), index, isSelected, cellHasFocus);

			return c;
		}
	}

	@Override
	public ElementOperationInterface getElementOperation() {
		
		//CLICK
		if( comboOperationList.getSelectedIndex() == LinkOperationListEnum.CLICK.getIndex() ){
			return new ClickOperation();
		}else if( comboOperationList.getSelectedIndex() == LinkOperationListEnum.GAINTEXTPATTERN.getIndex() ){
			return new GainTextPatternOperation( fieldPattern.getText() );			
		}
		
		return null;
	
	}

}
