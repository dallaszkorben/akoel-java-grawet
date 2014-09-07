package hu.akoel.grawit.gui.editors.component.keyvaluepair;

import hu.akoel.grawit.gui.editors.component.keyvaluepair.KeyValuePairComponent.MyRenderer;

import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

public class KeyValuePairBooleanValue extends JComboBox<Content> implements KeyValuePairValueTypeInterface{ //<Boolean>{

	private static final long serialVersionUID = -3161681018641274455L;

	private static final String NAME = "Boolean";
	
	public KeyValuePairBooleanValue( ){
		super();
		
		setRenderer(new MyRenderer());
		
		addItem( Content.TRUE );
		addItem( Content.FALSE );
				
	}
	
	@Override
	public String toString(){
		return NAME;
	}
	
	@Override
	public void setValue(Object value) {
		
		if( value instanceof Boolean && (Boolean)value ){
			setSelectedItem( Content.TRUE );
		}else{
			setSelectedItem( Content.FALSE );
		}
		
	}

	@Override
	public Object getValue() {
		return ((Content)getSelectedItem()).getValue();
	}

	@Override
	public void setEnableModify(boolean enable) {
		this.setEnabled( enable );
		this.setEditable( false );		
		
	}
	
	class MyRenderer extends BasicComboBoxRenderer {

		private static final long serialVersionUID = -4562181616721578685L;

		@Override
		public Component getListCellRendererComponent(JList list, Object value,	int index, boolean isSelected, boolean cellHasFocus) {

			Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

			return c;
		}
	}
}

enum Content{
	
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
