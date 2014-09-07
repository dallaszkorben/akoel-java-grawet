package hu.akoel.grawit.gui.editors.component.keyvaluepair;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JComponent;

public class KeyValuePairBooleanValue implements KeyValuePairValueTypeInterface<Boolean>{
	
	private static final String NAME = "Boolean";
	
	private JComboBox<Content> field;
	
	private enum Content{
		
		TRUE( 0, true, "True" ),
		FALSE( 1, false, "False" ),
		;
		
		private int index;
		private Boolean value;
		private String label;
		
		private Content( int index, Boolean value, String label ){
			this.index = index;
			this.value = value;
			this.label = label;
		}
		
		public Boolean getValue(){
			return value;
		}
		
		public String toString(){
			return label;
		}
		
		public int getIndex(){
			return index;
		}
		
		public static Content getContentByIndex( int index ){
			switch (index){
			case 0:	return TRUE;
			case 1: return FALSE;
			default: return FALSE;
			}
		}
	}
	
	
	public KeyValuePairBooleanValue( ){
		this.field = new JComboBox<>();		
		
/*		field.addItemListener( new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {

				int index = field.getSelectedIndex();					

				//Ha megvaltoztattam a tipust
				if( e.getStateChange() == java.awt.event.ItemEvent.SELECTED ){ 
					
					Content content = Content.getContentByIndex(index);
					setValue( content.getValue() );
					KeyValuePairBooleanValue.this.parent.setValueContainer( field );
		
				}				
			}
		});	
*/		
		field.addItem( Content.TRUE );
		field.addItem( Content.FALSE );
		
		
	}
	
	public String toString(){
		return NAME;
	}
	
	@Override
	public JComponent getComponent() {
		return field;
	}

	@Override
	public void setValue(Boolean value) {
		
		if( value ){
			field.setSelectedItem( Content.TRUE );
		}else{
			field.setSelectedItem( Content.FALSE );
		}
		
	}

	@Override
	public Boolean getValue() {
		return ((Content)field.getSelectedItem()).getValue();
	}



}
