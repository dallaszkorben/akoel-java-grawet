package hu.akoel.grawit.exceptions;

import hu.akoel.grawit.exception.message.AttributedMessage;

public class StoppedByUserException extends PrintOutExceptionAdapter{

	private static final long serialVersionUID = 3601836630818056477L;

	public StoppedByUserException(){
		super( "", new Exception() );
		
		this.clearMessage();		
		this.insertMessage( new AttributedMessage( "Stopped by User\n", this.ATTRIBUTE_INFORMATION ) );
	}
	
}
