package hu.akoel.grawit.core.operations;

import org.openqa.selenium.WebDriver;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import hu.akoel.grawit.ElementProgressInterface;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamElementDataModel;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.exceptions.ElementException;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;

public class OutputGainedOperation implements ElementOperationInterface{
	
	private static final String NAME = "OUTPUTGAINED";
	private static final String ATTR_MESSAGE = "message";
	
	//--- Data model
	private String message;
	//---
	
	public OutputGainedOperation( String message ){
		this.message = message;
	}
	
	public OutputGainedOperation( Element element, Tag rootTag, Tag tag ) throws XMLMissingAttributePharseException{
		
		if( !element.hasAttribute( ATTR_MESSAGE ) ){
			throw new XMLMissingAttributePharseException( rootTag, tag, ATTR_MESSAGE );			
		}
		message = element.getAttribute( ATTR_MESSAGE );
				
	}
	
	public static String getStaticName(){
		return NAME;
	}
	
	@Override
	public String getName() {		
		return getStaticName();
	}
		
	/**
	 * 
	 * Executes the action on the WebElement (Field)
	 * 
	 */
	@Override
	public void doAction( WebDriver driver, ParamElementDataModel element, ElementProgressInterface elementProgress ) throws ElementException{
		
		if( null != elementProgress ){
			
			elementProgress.elementStarted( element.getName() );
	
			BaseElementDataModel baseElement = element.getBaseElement();
			elementProgress.outputValue( baseElement.getGainedValue(), message );
			
			elementProgress.elementEnded( element.getName() );
		}
	}

	public String getMessageToShow() {
		return message;
	}


	@Override
	public void setXMLAttribute(Document document, Element element) {
		Attr attr = document.createAttribute( ATTR_MESSAGE );
		attr.setValue( message );
		element.setAttributeNode(attr);				
	}

}
