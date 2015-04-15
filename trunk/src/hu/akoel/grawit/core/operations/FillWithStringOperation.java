package hu.akoel.grawit.core.operations;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import hu.akoel.grawit.core.treenodedatamodel.base.BaseElementDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.NormalBaseElementDataModel;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.exceptions.ElementException;
import hu.akoel.grawit.exceptions.ElementInvalidOperationException;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;
import hu.akoel.grawit.gui.interfaces.progress.ElementProgressInterface;

public class FillWithStringOperation extends ElementOperationAdapter{
	
	private static final String NAME = "FILLSTRING";
	private static final String ATTR_STRING = "string";
	
	//--- Data model
	private String stringToShow;
	//---
	
	public FillWithStringOperation( String stringToShow ){
		this.stringToShow = stringToShow;
	}
	
	public FillWithStringOperation( Element element, Tag rootTag, Tag tag ) throws XMLMissingAttributePharseException{
		
		if( !element.hasAttribute( ATTR_STRING ) ){
			throw new XMLMissingAttributePharseException( rootTag, tag, ATTR_STRING );			
		}
		stringToShow = element.getAttribute( ATTR_STRING );		
	}
	
	public static String getStaticName(){
		return NAME;
	}
	
	@Override
	public String getName() {		
		return getStaticName();
	}
		
	public String getStringToShow() {
		return stringToShow;
	}

	@Override
	public void doOperation(WebDriver driver, BaseElementDataModelAdapter baseElement, WebElement webElement, ElementProgressInterface elementProgress) throws ElementException {
		
		if( baseElement instanceof NormalBaseElementDataModel ){
		
			try{
				//Execute the operation
				elementProgress.outputCommand( "		webElement.sendKeys(\"" + stringToShow + "\");     //" + baseElement.getName() );
				elementProgress.outputCommand( "		webElement.sendKeys(Keys.TAB);" );

				webElement.sendKeys( stringToShow );
				webElement.sendKeys(Keys.TAB);
			}catch (WebDriverException webDriverException){
				throw new ElementInvalidOperationException( getName(), baseElement.getName(), ((NormalBaseElementDataModel)baseElement).getSelector(), webDriverException );
			}
		}
	}

	@Override
	public void setXMLAttribute(Document document, Element element) {
		Attr attr = document.createAttribute( ATTR_STRING );
		attr.setValue( stringToShow );
		element.setAttributeNode(attr);		
	}

	@Override
	public Object clone() {
		
		String stringToShow = new String( this.stringToShow );
		
		return new FillWithStringOperation(stringToShow);
	}
	
	@Override
	public String getOperationNameToString() {		
		return "FillFieldWithString()";
	}

}
