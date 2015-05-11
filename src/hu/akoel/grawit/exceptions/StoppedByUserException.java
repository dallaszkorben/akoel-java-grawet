package hu.akoel.grawit.exceptions;

import hu.akoel.grawit.gui.output.message.AttributedOutputMessage;

public class StoppedByUserException extends PrintOutExceptionAdapter{

	private static final long serialVersionUID = 3601836630818056477L;

	public StoppedByUserException(){
		super( "", new Exception() );
		
		this.clearMessage();		
		this.insertMessage( new AttributedOutputMessage( "Stopped by User\n", this.ATTRIBUTE_INFORMATION ) );
	}
	
}
