package hu.akoel.grawit.core.operations;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
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
import hu.akoel.grawit.enums.list.CompareTypeListEnum;
import hu.akoel.grawit.enums.list.ElementTypeListEnum;
import hu.akoel.grawit.exceptions.ElementCompareOperationException;
import hu.akoel.grawit.exceptions.ElementException;
import hu.akoel.grawit.exceptions.ElementInvalidSelectorException;
import hu.akoel.grawit.exceptions.ElementNotFoundSelectorException;
import hu.akoel.grawit.exceptions.ElementTimeoutException;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;

public class CompareValueToStringOperation implements ElementOperationInterface{
	
	private static final String NAME = "COMPAREVALUETOSTRING";
	private static final String ATTR_STRING = "string";
	private static final String ATTR_COMPARE_TYPE = "type";
	private static final String ATTR_PATTERN = "pattern";
	
	private Pattern pattern;
	private Matcher matcher;
	private String stringPattern;
	
	//--- Data model
	private String stringToCompare;
	private CompareTypeListEnum compareType;
	//---
	
	public CompareValueToStringOperation( String stringToCompare, CompareTypeListEnum compareType, String stringPattern ){
		this.stringToCompare = stringToCompare;
		this.compareType = compareType;
		this.stringPattern = stringPattern;
		
		common( stringPattern );
	}
	
	public CompareValueToStringOperation( Element element, Tag rootTag, Tag tag ) throws XMLMissingAttributePharseException{
		
		//ATTR_COMPARE_TYPE
		if( !element.hasAttribute( ATTR_COMPARE_TYPE ) ){
			throw new XMLMissingAttributePharseException( rootTag, tag, ATTR_COMPARE_TYPE );		
		}	
		String typeString = element.getAttribute(ATTR_COMPARE_TYPE);
		this.compareType = CompareTypeListEnum.valueOf( typeString );
		
		//ATTR_STRING
		if( !element.hasAttribute( ATTR_STRING ) ){
			throw new XMLMissingAttributePharseException( rootTag, tag, ATTR_STRING );			
		}
		stringToCompare = element.getAttribute( ATTR_STRING );	
		
		//PATTERN
		if( !element.hasAttribute( ATTR_PATTERN ) ){
			stringPattern = "";
		}else{
			stringPattern = element.getAttribute( ATTR_PATTERN );
		}
		
		common( stringPattern );
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
		
		//
		// Execute the OPERATION
		//		
		String origText = "";
		
		//COMPARE VALUE
		//Ha LIST
		if( element.getBaseElement().getElementType().equals(ElementTypeListEnum.LIST)){

			Select select = new Select(webElement);
			origText = select.getFirstSelectedOption().getAttribute("value");
			
		//CHECKBOX/RADIOBUTTON
		}else if( element.getBaseElement().getElementType().equals(ElementTypeListEnum.CHECKBOX) || element.getBaseElement().getElementType().equals(ElementTypeListEnum.CHECKBOX) ){
			
			if( webElement.isSelected() ){
				origText = "SELECTED";
			}else{
				origText = "NOT SELECTED";
			}
			
		//Ha FIELD
		}else{		
			origText = webElement.getAttribute("value");
		}
		
		if( null != pattern ){
			matcher = pattern.matcher( origText );
			if( matcher.find() ){
				origText = matcher.group();
			}			
		}
		
		if( compareType.equals( CompareTypeListEnum.EQUAL ) ){
			
			if( !origText.equals( stringToCompare ) ){
				throw new ElementCompareOperationException(compareType, stringToCompare, element.getName(), baseElement.getSelector(), origText, new Exception() );
			}
			
		}else if( compareType.equals( CompareTypeListEnum.DIFFERENT ) ){
			
			if( origText.equals( stringToCompare ) ){
				throw new ElementCompareOperationException(compareType, stringToCompare, element.getName(), baseElement.getSelector(), origText, new Exception() );
			}
			
		}
		
		if( null != elementProgress ){
			elementProgress.elementEnded( element.getName() );
		}
	}

	private void common( String stringPattern ){
		
		if( stringPattern.trim().length() == 0 ){
			pattern = null;
		}else{		
			pattern = Pattern.compile( stringPattern );
		}
		
	}
	
	public String getStringToShow() {
		return stringToCompare;
	}

	public static String getStaticName(){
		return NAME;
	}
	
	@Override
	public String getName() {		
		return getStaticName();
	}
		
	public CompareTypeListEnum getCompareType(){
		return compareType;
	}

	@Override
	public void setXMLAttribute(Document document, Element element) {
		
		Attr attr = document.createAttribute( ATTR_STRING );
		attr.setValue( stringToCompare );
		element.setAttributeNode(attr);	
		
		attr = document.createAttribute( ATTR_COMPARE_TYPE );
		attr.setValue( compareType.name() );
		element.setAttributeNode( attr );	
	}
	
}
