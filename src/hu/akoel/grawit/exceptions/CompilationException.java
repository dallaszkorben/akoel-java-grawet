package hu.akoel.grawit.exceptions;

import hu.akoel.grawit.JavaSourceFromString;
import hu.akoel.grawit.core.treenodedatamodel.base.ScriptBaseElementDataModel;
import hu.akoel.grawit.exception.message.AttributedMessage;
import hu.akoel.grawit.exception.message.LinkMessage;

import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

public class CompilationException extends ElementException{
	private static final long serialVersionUID = 9044143626429149660L;

	public CompilationException( ScriptBaseElementDataModel baseElement, JavaSourceFromString javaFile, Diagnostic<? extends JavaFileObject> diagnostic ) {
		super( baseElement, "", new Exception() );

		this.insertMessage( new AttributedMessage( "Compilation error\n", this.ATTRIBUTE_HEAD ) );
		
		this.insertMessage( new AttributedMessage( "Element name: ", this.ATTRIBUTE_LABEL ) );
		this.insertMessage( new LinkMessage( baseElement ) );
		this.insertMessage( new AttributedMessage( "\n", this.ATTRIBUTE_NONE ) );
		
		this.insertMessage( new AttributedMessage( "Column: ", this.ATTRIBUTE_LABEL ) );
		this.insertMessage( new AttributedMessage( diagnostic.getColumnNumber() + "\n", this.ATTRIBUTE_VALUE ) );

		this.insertMessage( new AttributedMessage( "Lines: ", this.ATTRIBUTE_LABEL ) );
		this.insertMessage( new AttributedMessage( diagnostic.getLineNumber()  + "\n", this.ATTRIBUTE_VALUE ) );
		
		this.insertMessage( new AttributedMessage( "Position: ", this.ATTRIBUTE_LABEL ) );
		this.insertMessage( new AttributedMessage( diagnostic.getPosition() + "\n", this.ATTRIBUTE_VALUE ) );
		
		this.insertMessage( new AttributedMessage( "Message: ", this.ATTRIBUTE_LABEL ) );
		this.insertMessage( new AttributedMessage( diagnostic.getMessage( null ) + "\n", this.ATTRIBUTE_VALUE ) );
		
		this.insertMessage( new AttributedMessage( "Source: \n", this.ATTRIBUTE_LABEL ) );
		this.insertMessage( new AttributedMessage( javaFile.getSourceCode() + "\n", this.ATTRIBUTE_CONTENT ) );
	}
}
