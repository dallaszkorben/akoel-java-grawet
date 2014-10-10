package hu.akoel.grawit.core.operations;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import hu.akoel.grawit.ElementProgressInterface;
import hu.akoel.grawit.Properties;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamElementDataModel;
import hu.akoel.grawit.enums.SelectorType;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.enums.list.ElementTypeListEnum;
import hu.akoel.grawit.exceptions.ElementException;
import hu.akoel.grawit.exceptions.ElementInvalidSelectorException;
import hu.akoel.grawit.exceptions.ElementNotFoundSelectorException;
import hu.akoel.grawit.exceptions.ElementTimeoutException;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;

public class GainTextPatternOperation implements ElementOperationInterface{
	
	private static final String NAME = "GAINTEXT";
	private static final String ATTR_PATTERN = "pattern";
	
	private Pattern pattern;
	private Matcher matcher;
	
	//--- Data model
	private String stringPattern;
	//---
	
	public GainTextPatternOperation( String stringPattern ){
		this.stringPattern = stringPattern;
		
		common( stringPattern );
	}
	
	public GainTextPatternOperation( Element element, Tag rootTag, Tag tag ) throws XMLMissingAttributePharseException{
		
		if( !element.hasAttribute( ATTR_PATTERN ) ){
			throw new XMLMissingAttributePharseException( rootTag, tag, ATTR_PATTERN );			
		}
		stringPattern = element.getAttribute( ATTR_PATTERN );	
		
		common( stringPattern );
		
	}
	
	private void common( String stringPattern ){
		
		if( stringPattern.trim().length() == 0 ){
			pattern = null;
		}else{		
			pattern = Pattern.compile( stringPattern );
		}
		
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
		
/*		
		while( !webElement.isDisplayed() ){
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {}
		}	
*/		
		String origText = "";
		
		//Ha FIELD
		if( element.getBaseElement().getElementType().equals(ElementTypeListEnum.FIELD)){
			origText = webElement.getAttribute("value");	
		
		//TEXT
		}else if( element.getBaseElement().getElementType().equals(ElementTypeListEnum.TEXT)){
			
			origText = webElement.getText();
		
		//LINK
		}else if( element.getBaseElement().getElementType().equals(ElementTypeListEnum.LINK)){
			
			origText = webElement.getText();
			
		}
		
// TODO kell ide a LIST es a checkbox meg a radiobutton is		
		
		//Execute the operation = Elmenti az elem tartalmat a valtozoba		
		if( null == pattern ){
			baseElement.setGainedValue( origText );
		}else{
			matcher = pattern.matcher( origText );
			if( matcher.find() ){
				String resultText = matcher.group();
				baseElement.setGainedValue( resultText );
			}			
		}
		
		if( null != elementProgress ){
			elementProgress.elementEnded( element.getName() );
		}
	}
	
	public String getStringPattern(){
		return stringPattern;
	}

	@Override
	public void setXMLAttribute(Document document, Element element) {
		Attr attr = document.createAttribute( ATTR_PATTERN );
		attr.setValue( stringPattern );
		element.setAttributeNode(attr);		
	}
	
}

