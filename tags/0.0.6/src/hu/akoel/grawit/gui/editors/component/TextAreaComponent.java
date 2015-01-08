package hu.akoel.grawit.gui.editors.component;

import java.awt.Component;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JViewport;

public class TextAreaComponent extends JScrollPane implements EditorComponentInterface{
	private JTextArea textArea;

	private static final long serialVersionUID = 2451396795626697844L;

	public TextAreaComponent( String text ){
		super( new JTextArea(text) );
		common();
	}
	
	public TextAreaComponent( String text, int rows, int columns ){
		super( new JTextArea(text, rows, columns) );
		common();
	}
	
	private void common(){
		JViewport viewport = getViewport(); 
		this.textArea = (JTextArea)viewport.getView(); 
	}
	
	@Override
	public void setEnableModify(boolean enable) {
		this.textArea.setEditable( enable );		
	}

	@Override
	public Component getComponent() {		
		return this;
	}
	
	public String getText(){
		return textArea.getText();
	}
	
	public void setText( String text ){
		textArea.setText( text );
	}

}
