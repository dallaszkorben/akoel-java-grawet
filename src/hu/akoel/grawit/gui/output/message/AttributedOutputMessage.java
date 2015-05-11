package hu.akoel.grawit.gui.output.message;

import javax.swing.text.SimpleAttributeSet;

public class AttributedOutputMessage extends OutputMessageAdapter{
	private String message;
	private SimpleAttributeSet attribute;
	
	public AttributedOutputMessage( String message, SimpleAttributeSet attribute ){
		this.message = message;
		this.attribute = attribute;
	}

	@Override
	public String getMessage() {
		return this.message;
	}

	@Override
	public SimpleAttributeSet getAttribute() {		
		return this.attribute;
	}



}

