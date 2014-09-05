package hu.akoel.grawit;

import java.net.URI;

import javax.tools.SimpleJavaFileObject;

public class JavaSourceFromString extends SimpleJavaFileObject {
	final String sourceCode;

	public JavaSourceFromString(String name, String sourceCode) {
		super(URI.create("string:///" + name.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
		this.sourceCode = sourceCode;
	}

	@Override
	public CharSequence getCharContent(boolean ignoreEncodingErrors) {
		return sourceCode;
	}
	
	public String getSourceCode(){
		return sourceCode;
	}
	
	public URI getURI(){
		return this.uri;
	}
}
