package hu.akoel.grawit.exceptions;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import hu.akoel.grawit.core.treenodedatamodel.step.StepCollectorDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.step.StepElementDataModel;
import hu.akoel.grawit.exception.message.AttributedMessage;
import hu.akoel.grawit.exception.message.LinkMessage;
import hu.akoel.grawit.exception.message.OutputMessage;

public class StepException extends PrintOutExceptionAdapter{
	
	private static final long serialVersionUID = 9044143626429149660L;
	private StepCollectorDataModelAdapter stepCollector;
	private StepElementDataModel stepElement;
	private ElementException elementException;
	private ArrayList<OutputMessage> outputMessageArray = new ArrayList<>();
	
	public SimpleAttributeSet ATTRIBUTE_HEAD;
	public SimpleAttributeSet ATTRIBUTE_LABEL;
	public SimpleAttributeSet ATTRIBUTE_VALUE;
	public SimpleAttributeSet ATTRIBUTE_INFORMATION;
	public SimpleAttributeSet ATTRIBUTE_NONE;
	
	public StepException( StepCollectorDataModelAdapter stepCollector, StepElementDataModel stepElement, ElementException elementException ) {
		super( "", elementException );
		this.stepCollector = stepCollector;
		this.elementException = elementException;
		this.stepElement = stepElement;

		ATTRIBUTE_HEAD = new SimpleAttributeSet();
		StyleConstants.setForeground( ATTRIBUTE_HEAD, Color.RED );
		StyleConstants.setBold( ATTRIBUTE_HEAD, true);
		
		ATTRIBUTE_LABEL = new SimpleAttributeSet();
		StyleConstants.setForeground( ATTRIBUTE_LABEL, Color.BLACK );
		StyleConstants.setBold( ATTRIBUTE_LABEL, false);
		
		ATTRIBUTE_VALUE = new SimpleAttributeSet();
		StyleConstants.setForeground( ATTRIBUTE_VALUE, Color.RED );
		StyleConstants.setBold( ATTRIBUTE_VALUE, false);		
		StyleConstants.setItalic( ATTRIBUTE_VALUE, true);
		
		ATTRIBUTE_INFORMATION = new SimpleAttributeSet();
		StyleConstants.setForeground( ATTRIBUTE_INFORMATION, Color.BLUE );
		StyleConstants.setBold( ATTRIBUTE_INFORMATION, true );
		
		ATTRIBUTE_NONE = new SimpleAttributeSet();

		if( null != stepElement ){
			this.insertMessage( new AttributedMessage( "Step element name: ", this.ATTRIBUTE_LABEL ) );
			this.insertMessage( new LinkMessage( stepElement ) );
			this.insertMessage( new AttributedMessage( "\n", this.ATTRIBUTE_NONE ) );			
		}else if( null != stepCollector ){
			this.insertMessage( new AttributedMessage( "Step collector name: ", this.ATTRIBUTE_LABEL ) );
			this.insertMessage( new LinkMessage( stepCollector ) );
			this.insertMessage( new AttributedMessage( "\n", this.ATTRIBUTE_NONE ) );
		}	
	}
	
	/**
	 * Kiirja a teljes uzenetet FORMAZVA
	 * @param document
	 */
	public void printMessage( DefaultStyledDocument document ){
		elementException.printMessage(document);
		super.printMessage(document);
	}
	
	@Override
	public String getMessage(){
		StringBuilder builder = new StringBuilder(100);
		builder.append( elementException.getMessage() );
		builder.append( super.getMessage() );
		return builder.toString();
	}	
}
