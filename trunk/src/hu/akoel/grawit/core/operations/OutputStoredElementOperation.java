package hu.akoel.grawit.core.operations;

import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import hu.akoel.grawit.core.treenodedatamodel.base.BaseElementDataModelAdapter;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.exceptions.ElementException;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;
import hu.akoel.grawit.gui.interfaces.progress.ProgressIndicatorInterface;

public class OutputStoredElementOperation extends ElementOperationAdapter{
	
	private static final String NAME = "OUTPUTSTOREDELEMENT";
	private static final String ATTR_MESSAGE = "message";

	//--- Data model
	private String label;
	//---
	
	public OutputStoredElementOperation( String message ){
		this.label = message;
	}
	
	public OutputStoredElementOperation( Element element, Tag rootTag, Tag tag ) throws XMLMissingAttributePharseException{
		
		if( !element.hasAttribute( ATTR_MESSAGE ) ){
			throw new XMLMissingAttributePharseException( rootTag, tag, ATTR_MESSAGE );			
		}
		label = element.getAttribute( ATTR_MESSAGE );
				
	}
	
	public static String getStaticName(){
		return NAME;
	}
	
	@Override
	public String getName() {		
		return getStaticName();
	}
		
	public String getLabelToShow() {
		return label;
	}

	@Override
	public void doOperation(WebDriver driver, BaseElementDataModelAdapter baseElement, WebElement webElement, ProgressIndicatorInterface elementProgress, String tab, Set<String> definedElementSet, boolean needToPrintSource ) throws ElementException {
		
		if( needToPrintSource ){

			elementProgress.printSource( tab + "System.out.println( \"" + label + ": \" + " + baseElement.getNameAsVariable() + " );" );
			
			elementProgress.printOutput( label, baseElement.getStoredValue(), null );			
			
		}
	}
	
	@Override
	public void setXMLAttribute(Document document, Element element) {
		Attr attr = document.createAttribute( ATTR_MESSAGE );
		attr.setValue( label );
		element.setAttributeNode(attr);				
	}

	@Override
	public Object clone() {
		
		String message = new String( this.label );
		
		return new OutputStoredElementOperation(message);
	}

	@Override
	public String getOperationNameToString() {		
		return "OutputElementStorage()";
	}

	
}
