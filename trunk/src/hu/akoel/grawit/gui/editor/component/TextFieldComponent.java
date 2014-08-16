package hu.akoel.grawit.gui.editor.component;

import java.awt.Component;

import javax.swing.JTextField;

public class TextFieldComponent extends JTextField implements EditorComponentInterface{

	private static final long serialVersionUID = -4635265655452539977L;

	public TextFieldComponent( String text ){
		super( text );
	}
	
	@Override
	public void setEnableModify(boolean enable) {
		this.setEditable( enable );		
	}

	@Override
	public Component getComponent() {		
		return this;
	}

}
