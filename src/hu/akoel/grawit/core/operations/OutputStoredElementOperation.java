package hu.akoel.grawit.core.operations;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import hu.akoel.grawit.ElementProgressInterface;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamElementDataModel;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.exceptions.ElementException;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;

public class OutputStoredElementOperation extends ElementOperationAdapter{
	
	private static final String NAME = "OUTPUTSTOREDELEMENT";
	private static final String ATTR_MESSAGE = "message";

	private String message;
	
	public OutputStoredElementOperation( String message ){
		this.message = message;
	}
	
	public OutputStoredElementOperation( Element element, Tag rootTag, Tag tag ) throws XMLMissingAttributePharseException{
		
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
		
	public String getMessageToShow() {
		return message;
	}

	@Override
	public void doOperation(WebDriver driver, ParamElementDataModel element, WebElement webElement, ElementProgressInterface elementProgress ) throws ElementException {

		if( null != elementProgress ){
			elementProgress.outputValue( element.getBaseElement().getStoredValue(), message );
		}
		
	}
	
	@Override
	public void setXMLAttribute(Document document, Element element) {
		Attr attr = document.createAttribute( ATTR_MESSAGE );
		attr.setValue( message );
		element.setAttributeNode(attr);				
	}

}
