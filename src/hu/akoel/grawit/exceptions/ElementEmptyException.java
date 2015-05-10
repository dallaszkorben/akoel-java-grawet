package hu.akoel.grawit.exceptions;

import hu.akoel.grawit.core.treenodedatamodel.base.NormalBaseElementDataModel;

public class ElementEmptyException extends ElementException{

	private static final long serialVersionUID = 3601836630815056477L;
	
	public ElementEmptyException( NormalBaseElementDataModel baseElement, Exception e ){
		super( baseElement, "", e );
	}
}
