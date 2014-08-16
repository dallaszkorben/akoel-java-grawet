package hu.akoel.grawit.gui.editor.component;

import java.awt.Component;

import javax.swing.JCheckBox;

public class CheckBoxComponent extends JCheckBox implements EditorComponentInterface{

	private static final long serialVersionUID = 3901255004305182750L;

	public CheckBoxComponent( String text ){
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
