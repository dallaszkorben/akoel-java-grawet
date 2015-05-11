package hu.akoel.grawit.gui.output.message;

import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;

public abstract class OutputMessageAdapter {

	public abstract String getMessage();
	
	public abstract SimpleAttributeSet getAttribute();
	
	public void printOut(DefaultStyledDocument document) {
		try {
			document.insertString( document.getLength(), getMessage(), getAttribute() );
		} catch (BadLocationException e) {}		
	}
	
}
