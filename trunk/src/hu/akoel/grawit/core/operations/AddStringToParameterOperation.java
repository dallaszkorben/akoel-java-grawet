package hu.akoel.grawit.core.operations;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import hu.akoel.grawit.ElementProgressInterface;
import hu.akoel.grawit.core.treenodedatamodel.base.SpecialBaseElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamElementDataModel;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.exceptions.ElementException;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;

public class AddStringToParameterOperation extends ElementOperationAdapter{
	
	private static final String NAME = "ADDSTRINGTOPARAMETER";
	private static final String ATTR_STRING = "string";
	
	//--- Data model
	private String stringToParameter;
	//---
	
	public AddStringToParameterOperation( String stringToParameter ){
		this.stringToParameter = stringToParameter;
		
	}
	
	public AddStringToParameterOperation( Element element, Tag rootTag, Tag tag ) throws XMLMissingAttributePharseException{
		
		//ATTR_STRING
		if( !element.hasAttribute( ATTR_STRING ) ){
			throw new XMLMissingAttributePharseException( rootTag, tag, ATTR_STRING );			
		}
		stringToParameter = element.getAttribute( ATTR_STRING );	
		
	}

	public String getStringToShow() {
		return stringToParameter;
	}

	public static String getStaticName(){
		return NAME;
	}
	
	@Override
	public String getName() {		
		return getStaticName();
	}
		
	@Override
	public void doOperation(WebDriver driver, ParamElementDataModel element, WebElement webElement, ElementProgressInterface elementProgress) throws ElementException {
		
		//HA SPECIALBASEELEMENT - annak kell lennie
		if( element.getBaseElement() instanceof SpecialBaseElementDataModel ){

			((SpecialBaseElementDataModel)element.getBaseElement()).addParameter( stringToParameter );
			
		}
	
	}
	
	@Override
	public void setXMLAttribute(Document document, Element element) {
		
		Attr attr = document.createAttribute( ATTR_STRING );
		attr.setValue( stringToParameter );
		element.setAttributeNode(attr);	
		
	}

	@Override
	public Object clone() {

		String stringToCompare = new String( this.stringToParameter );
		
		return new AddStringToParameterOperation(stringToCompare);
	}
	
}
