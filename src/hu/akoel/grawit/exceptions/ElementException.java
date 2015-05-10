package hu.akoel.grawit.exceptions;

import hu.akoel.grawit.core.treenodedatamodel.base.BaseElementDataModelAdapter;

public abstract class ElementException extends PrintOutExceptionAdapter{

	private static final long serialVersionUID = -7365125686038678803L;
	private BaseElementDataModelAdapter baseElement;

	public ElementException( BaseElementDataModelAdapter baseElement, String message, Throwable exception ){
		super( message, exception );
		this.baseElement = baseElement;
	}
	
	public BaseElementDataModelAdapter getBaseElement(){
		return baseElement;
	}
}
