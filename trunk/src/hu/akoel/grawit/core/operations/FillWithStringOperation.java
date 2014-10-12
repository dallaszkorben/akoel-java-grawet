package hu.akoel.grawit.core.operations;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import hu.akoel.grawit.ElementProgressInterface;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamElementDataModel;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.exceptions.ElementException;
import hu.akoel.grawit.exceptions.ElementInvalidOperationException;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;

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
		
	/**
	 * 
	 * Executes the action on the WebElement (Field)
	 * 
	 */
/*	@Override
	public void doAction( WebDriver driver, ParamElementDataModel element, ElementProgressInterface elementProgress ) throws ElementException{
	
		if( null != elementProgress ){
			elementProgress.elementStarted( element.getName() );
		}
		
		BaseElementDataModel baseElement = element.getBaseElement();
		By by = null;
		WebElement webElement = null;
		
		//WAITING TIME
		Integer waitingTime = baseElement.getWaitingTime();
		if( null == waitingTime ){
			waitingTime = Properties.getInstance().getWaitingTime();
		}
		WebDriverWait wait = new WebDriverWait(driver, waitingTime);
						
		//Selector meszerzese
		if( baseElement.getSelectorType().equals(SelectorType.ID)){
			by = By.id( baseElement.getSelector() );
		//CSS
		}else if( baseElement.getSelectorType().equals(SelectorType.CSS)){
			by = By.cssSelector( baseElement.getSelector() );
		}
						
		//Varakozik az elem megjeleneseig, de max 10 mp-ig
		try{
			wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
			//wait.until(ExpectedConditions.elementToBeClickable( by ) );
		
		}catch( org.openqa.selenium.TimeoutException timeOutException ){
			throw new ElementTimeoutException( element.getName(), baseElement.getSelector(), timeOutException );
		}
		
		try{
			webElement = driver.findElement( by );
		}catch ( org.openqa.selenium.InvalidSelectorException invalidSelectorException ){
			throw new ElementInvalidSelectorException(element.getName(), baseElement.getSelector(), invalidSelectorException );
		}catch ( org.openqa.selenium.NoSuchElementException noSuchElementException ){
			throw new ElementNotFoundSelectorException( element.getName(), baseElement.getSelector(), noSuchElementException );
		}
		
		if( null == webElement ){
			throw new ElementNotFoundSelectorException( element.getName(), baseElement.getSelector(), new Exception() );
		}
		
		try{
			//Execute the operation
			webElement.sendKeys( stringToShow );
			webElement.sendKeys(Keys.TAB);
		}catch (WebDriverException webDriverException){
			throw new ElementInvalidOperationException( getName(), element.getName(), baseElement.getSelector(), webDriverException );
		}
				
		if( null != elementProgress ){
			elementProgress.elementEnded( element.getName() );
		}
	}
*/
	public String getStringToShow() {
		return stringToShow;
	}

	@Override
	public void doOperation(WebDriver driver, ParamElementDataModel element, WebElement webElement, ElementProgressInterface elementProgress) throws ElementException {
		
		try{
			//Execute the operation
			webElement.sendKeys( stringToShow );
			webElement.sendKeys(Keys.TAB);
		}catch (WebDriverException webDriverException){
			throw new ElementInvalidOperationException( getName(), element.getName(), element.getBaseElement().getSelector(), webDriverException );
		}
	}

	@Override
	public void setXMLAttribute(Document document, Element element) {
		Attr attr = document.createAttribute( ATTR_STRING );
		attr.setValue( stringToShow );
		element.setAttributeNode(attr);		
	}
	
}
