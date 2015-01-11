package hu.akoel.grawit.exceptions;

public class StoppedByUserException extends Exception{

	private static final long serialVersionUID = 3601836630818056477L;

	public StoppedByUserException(){
		super( );
	}
	
	public String getMessage(){
		return "Stopped by User";
	}

}
