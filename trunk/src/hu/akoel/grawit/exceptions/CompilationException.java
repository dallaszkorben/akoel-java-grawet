package hu.akoel.grawit.exceptions;

import hu.akoel.grawit.pages.JavaSourceFromString;

import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

public class CompilationException extends Exception{
	private static final long serialVersionUID = 9044143626429149660L;
	private String pageName = null;
	private JavaSourceFromString javaFile;
	Diagnostic<? extends JavaFileObject> diagnostic;

	public CompilationException(String pageName, JavaSourceFromString javaFile, Diagnostic<? extends JavaFileObject> diagnostic ) {
		super( "Compilation error: \n" +
				"Page name: " + pageName + "\n" + 
				"Column: " + diagnostic.getColumnNumber() + "\n" + 
				"Lines: " + diagnostic.getLineNumber()  + "\n" + 
				"Position: " + diagnostic.getPosition() + "\n" + 
				"Message: " + diagnostic.getMessage( null )  + "\n"	+
				"Source:"   + "\n" +
				javaFile.getSourceCode());
		
		this.pageName = pageName;
		this.javaFile = javaFile;
		this.diagnostic = diagnostic;
	}

	public CompilationException( String pageName, String message, Throwable e) {
		super( message + "\nPage name: " + pageName, e );
		this.pageName = pageName;
	}
	
	public String getPageName() {
		return pageName;
	}

	public JavaSourceFromString getJavaFile() {
		return javaFile;
	}

	public Diagnostic<? extends JavaFileObject> getDiagnosticList() {
		return diagnostic;
	}


}
