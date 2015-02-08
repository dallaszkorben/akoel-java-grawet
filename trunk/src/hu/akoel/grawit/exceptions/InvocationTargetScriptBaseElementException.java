package hu.akoel.grawit.exceptions;

import hu.akoel.grawit.core.treenodedatamodel.base.ScriptBaseElementDataModel;

public class InvocationTargetScriptBaseElementException extends ElementException{
	private String elementName;

	private static final long serialVersionUID = 3601836630818056477L;

	public InvocationTargetScriptBaseElementException( String operationString, String elementName, Exception e ){
		super( "InvocationTargetException at run an " + ScriptBaseElementDataModel.class.getSimpleName() + ":\n   Element name: '" + elementName + "'", e );
		this.elementName = elementName;
	}
	
	public String getElementName() {
		return elementName;
	}

	@Override
	public String getElementSelector() {
		return "";
	}
	
}
