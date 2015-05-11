package hu.akoel.grawit.exceptions;

import hu.akoel.grawit.JavaSourceFromString;
import hu.akoel.grawit.core.treenodedatamodel.base.ScriptBaseElementDataModel;
import hu.akoel.grawit.gui.output.message.AttributedOutputMessage;
import hu.akoel.grawit.gui.output.message.LinkOutputMessage;

import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

public class CompilationException extends ElementException{
	private static final long serialVersionUID = 9044143626429149660L;

	public CompilationException( ScriptBaseElementDataModel baseElement, JavaSourceFromString javaFile, Diagnostic<? extends JavaFileObject> diagnostic ) {
		super( baseElement, "", new Exception() );

		this.insertMessage( new AttributedOutputMessage( "Compilation error\n", this.ATTRIBUTE_HEAD ) );
		
		this.insertMessage( new AttributedOutputMessage( "Element name: ", this.ATTRIBUTE_LABEL ) );
		this.insertMessage( new LinkOutputMessage( baseElement ) );
		this.insertMessage( new AttributedOutputMessage( "\n", this.ATTRIBUTE_NONE ) );
		
		this.insertMessage( new AttributedOutputMessage( "Column: ", this.ATTRIBUTE_LABEL ) );
		this.insertMessage( new AttributedOutputMessage( diagnostic.getColumnNumber() + "\n", this.ATTRIBUTE_VALUE ) );

		this.insertMessage( new AttributedOutputMessage( "Lines: ", this.ATTRIBUTE_LABEL ) );
		this.insertMessage( new AttributedOutputMessage( diagnostic.getLineNumber()  + "\n", this.ATTRIBUTE_VALUE ) );
		
		this.insertMessage( new AttributedOutputMessage( "Position: ", this.ATTRIBUTE_LABEL ) );
		this.insertMessage( new AttributedOutputMessage( diagnostic.getPosition() + "\n", this.ATTRIBUTE_VALUE ) );
		
		this.insertMessage( new AttributedOutputMessage( "Message: ", this.ATTRIBUTE_LABEL ) );
		this.insertMessage( new AttributedOutputMessage( diagnostic.getMessage( null ) + "\n", this.ATTRIBUTE_VALUE ) );
		
		this.insertMessage( new AttributedOutputMessage( "Source: \n", this.ATTRIBUTE_LABEL ) );
		this.insertMessage( new AttributedOutputMessage( javaFile.getSourceCode() + "\n", this.ATTRIBUTE_CONTENT ) );
	}
}
