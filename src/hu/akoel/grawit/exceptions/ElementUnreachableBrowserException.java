package hu.akoel.grawit.exceptions;

import hu.akoel.grawit.core.treenodedatamodel.base.NormalBaseElementDataModel;
import hu.akoel.grawit.exception.message.AttributedMessage;

public class ElementUnreachableBrowserException extends ElementException{

	private static final long serialVersionUID = 3601836630818056477L;
	
	public ElementUnreachableBrowserException( NormalBaseElementDataModel baseElement, Exception e ){
		super( baseElement, "", e );
		
		this.insertMessage( new AttributedMessage( "The browser in unreachable\n", this.ATTRIBUTE_HEAD ) );
	}
}
