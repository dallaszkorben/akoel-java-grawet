package hu.akoel.grawit.exceptions;

import hu.akoel.grawit.core.treenodedatamodel.base.ScriptBaseElementDataModel;

public class InvocationTargetScriptBaseElementException extends ElementException{

	private static final long serialVersionUID = 3601836630818056477L;

	private StringBuilder message = new StringBuilder(100);
	
	public InvocationTargetScriptBaseElementException( ScriptBaseElementDataModel baseElement, Exception e ){
		super( baseElement, "", e );
		
		this.message.append( "InvocationTargetException at run an " + ScriptBaseElementDataModel.class.getSimpleName() + ":\n" ); 
		this.message.append( "Element name: " + baseElement.getName() + "\n" );
	}

	@Override
	public String getMessage() {
		return message.toString();
	}
}
