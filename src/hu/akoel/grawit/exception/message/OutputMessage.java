package hu.akoel.grawit.exception.message;

import javax.swing.text.DefaultStyledDocument;

public interface OutputMessage {

	public String getMessage();
	
	public void printOut( DefaultStyledDocument document );
	
}
