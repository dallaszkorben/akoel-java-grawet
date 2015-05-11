package hu.akoel.grawit.exceptions;

import hu.akoel.grawit.core.treenodedatamodel.base.ScriptBaseElementDataModel;
import hu.akoel.grawit.gui.output.message.AttributedOutputMessage;
import hu.akoel.grawit.gui.output.message.LinkOutputMessage;

public class ScriptElementExecutionException extends ElementException{

	private static final long serialVersionUID = 3601836630818056477L;
	
	public ScriptElementExecutionException( ScriptBaseElementDataModel baseElement, Throwable e ){
		super( baseElement, "", e );
		
		this.insertMessage( new AttributedOutputMessage( "Script Element Exception\n", this.ATTRIBUTE_HEAD ) );

		this.insertMessage( new AttributedOutputMessage( "Message: ", this.ATTRIBUTE_LABEL ) );
		this.insertMessage( new AttributedOutputMessage( e.getMessage() + "\n", this.ATTRIBUTE_VALUE ) );

		this.insertMessage( new AttributedOutputMessage( "Base Element name: ", this.ATTRIBUTE_LABEL ) );
		this.insertMessage( new LinkOutputMessage( baseElement ) );
		this.insertMessage( new AttributedOutputMessage( "\n", this.ATTRIBUTE_NONE ) );

	}
}
