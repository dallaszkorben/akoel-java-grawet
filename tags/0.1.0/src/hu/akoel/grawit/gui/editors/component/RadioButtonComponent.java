package hu.akoel.grawit.gui.editors.component;

import java.awt.Component;

import javax.swing.JRadioButton;

public class RadioButtonComponent extends JRadioButton implements EditorComponentInterface{

	private static final long serialVersionUID = -6952449600532826526L;

	public RadioButtonComponent( String text ){
		super( text );
	}
	
	@Override
	public void setEnableModify(boolean enable) {
		this.setEnabled( enable );		
	}

	@Override
	public Component getComponent() {		
		return this;
	}

}
