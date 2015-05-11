package hu.akoel.grawit.exceptions;

import hu.akoel.grawit.gui.output.message.OutputMessageAdapter;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public abstract class PrintOutExceptionAdapter extends Exception{
	private static final long serialVersionUID = 5108367132756413342L;
	private ArrayList<OutputMessageAdapter> outputMessageArray = new ArrayList<>();

	public SimpleAttributeSet ATTRIBUTE_NONE;
	public SimpleAttributeSet ATTRIBUTE_HEAD;
	public SimpleAttributeSet ATTRIBUTE_LABEL;
	public SimpleAttributeSet ATTRIBUTE_VALUE;
	public SimpleAttributeSet ATTRIBUTE_INFORMATION;
	public SimpleAttributeSet ATTRIBUTE_CONTENT;
	
	public PrintOutExceptionAdapter( String message, Throwable exception ){
		super( message, exception );

		ATTRIBUTE_NONE = new SimpleAttributeSet();
		
		ATTRIBUTE_HEAD = new SimpleAttributeSet();
		StyleConstants.setForeground( ATTRIBUTE_HEAD, Color.RED );
		StyleConstants.setBold( ATTRIBUTE_HEAD, true);
		
		ATTRIBUTE_LABEL = new SimpleAttributeSet();
		StyleConstants.setForeground( ATTRIBUTE_LABEL, Color.BLACK );
		StyleConstants.setBold( ATTRIBUTE_LABEL, false);
		
		ATTRIBUTE_VALUE = new SimpleAttributeSet();
		StyleConstants.setForeground( ATTRIBUTE_VALUE, Color.RED );
		StyleConstants.setBold( ATTRIBUTE_VALUE, false);		
		StyleConstants.setItalic( ATTRIBUTE_VALUE, true);
		
		ATTRIBUTE_INFORMATION = new SimpleAttributeSet();
		StyleConstants.setForeground( ATTRIBUTE_INFORMATION, Color.BLUE );
		StyleConstants.setBold( ATTRIBUTE_INFORMATION, true );
		
		ATTRIBUTE_CONTENT = new SimpleAttributeSet();
		StyleConstants.setForeground( ATTRIBUTE_CONTENT, Color.GREEN );
		StyleConstants.setBold( ATTRIBUTE_CONTENT, false );
		StyleConstants.setItalic( ATTRIBUTE_CONTENT, true );
		

	}
	
	public void clearMessage(){
		outputMessageArray.clear();
	}
	
	@Override
	public String getMessage(){
		StringBuilder builder = new StringBuilder(100);
		for( OutputMessageAdapter message: outputMessageArray ){
			builder.append( message.getMessage() );
		}
		return builder.toString();
	}	
	
	/**
	 * Beszur egy uzenetet
	 * @param message
	 */
	public void insertMessage( OutputMessageAdapter message ){
		outputMessageArray.add( message );
	}
	
	/**
	 * Kiirja a teljes uzenetet FORMAZVA
	 * @param document
	 */
	public void printMessage( DefaultStyledDocument document ){
		for( OutputMessageAdapter message: outputMessageArray ){
			message.printOut(document);
		}
	}
}
