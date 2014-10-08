package hu.akoel.grawit.exceptions;

import hu.akoel.grawit.CommonOperations;

public class ParameterError extends Error{

	private static final long serialVersionUID = -4973447549654930925L;

	/**
	 * 
	 * Ha a hivo osztalyanal magasabb osztalyt es metodusat kell kijelezni a hiba forrasanak
	 */
	public ParameterError( int shift, String message ){
		super( "\n" + CommonOperations.getClassName(3+shift) + "." + CommonOperations.getMethodName(3+shift) + "()" + " method has Wrong parameter. \n" +
				"Called from " + CommonOperations.getClassName(4+shift) + "." + CommonOperations.getMethodName(4+shift) + "() line: " + String.valueOf( CommonOperations.getLineNumber(4+shift) ) + "\n"
				+ message );
	}
	
	/**
	 * 
	 * Ha a hivo osztalyat es metodusat kell kijeleznem a hiba forrasanak
	 * 
	 */
	public ParameterError( String message ){
		super( CommonOperations.getClassName(3) + "." + CommonOperations.getMethodName(3) + "()" + " method has Wrong parameter. " + message );
	}
	
}