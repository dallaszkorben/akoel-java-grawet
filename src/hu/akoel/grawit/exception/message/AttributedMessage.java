package hu.akoel.grawit.exception.message;

import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;

public class AttributedMessage implements OutputMessage{
	private String message;
	private SimpleAttributeSet attribute;
	
	public AttributedMessage( String message, SimpleAttributeSet attribute ){
		this.message = message;
		this.attribute = attribute;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public void printOut(DefaultStyledDocument document) {
		try {
			document.insertString( document.getLength(), message, attribute );
		} catch (BadLocationException e) {}		
	}

}

