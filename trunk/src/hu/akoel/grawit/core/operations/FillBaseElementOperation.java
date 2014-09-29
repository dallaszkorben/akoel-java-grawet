package hu.akoel.grawit.core.operations;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.ElementProgressInterface;
import hu.akoel.grawit.core.treenodedatamodel.BaseDataModelInterface;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseNodeDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.BasePageDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamElementDataModel;
import hu.akoel.grawit.enums.SelectorType;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.enums.list.VariableSampleListEnum;
import hu.akoel.grawit.exceptions.ElementException;
import hu.akoel.grawit.exceptions.ElementInvalidOperationException;
import hu.akoel.grawit.exceptions.ElementInvalidSelectorException;
import hu.akoel.grawit.exceptions.ElementNotFoundSelectorException;
import hu.akoel.grawit.exceptions.ElementTimeoutException;
import hu.akoel.grawit.exceptions.XMLBaseConversionPharseException;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;

public class FillBaseElementOperation implements ElementOperationInterface{
	
	private static final String NAME = "FILLELEMENT";	
	private static final String ATTR_FILL_BASE_ELEMENT_PATH = "fillelementpath";
	
	//--- Data model
	private BaseElementDataModel baseElementDataModel;
	//---
	
	public FillBaseElementOperation( BaseElementDataModel baseElementDataModel ){
		this.baseElementDataModel = baseElementDataModel;
	}

	public FillBaseElementOperation( Element element, BaseRootDataModel baseRootDataModel, Tag rootTag, Tag tag, String nameAttrName, String nameAttrValue ) throws XMLBaseConversionPharseException, XMLMissingAttributePharseException{		
		
		BaseDataModelInterface baseDataModelForFillOut = baseRootDataModel;
		
		if( !element.hasAttribute( ATTR_FILL_BASE_ELEMENT_PATH ) ){
			throw new XMLMissingAttributePharseException( rootTag, tag, ATTR_FILL_BASE_ELEMENT_PATH );		
		}	
		String baseElementPathString = element.getAttribute(ATTR_FILL_BASE_ELEMENT_PATH);				
		baseElementPathString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + baseElementPathString;  
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
	    DocumentBuilder builder;
	    Document document = null;
	    try{  
	        builder = factory.newDocumentBuilder();  
	        document = builder.parse( new InputSource( new StringReader( baseElementPathString ) ) );  
	    } catch (Exception e) {  
	    
	    	//Nem sikerult az atalakitas
	    	throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, nameAttrValue, ATTR_FILL_BASE_ELEMENT_PATH, element.getAttribute(ATTR_FILL_BASE_ELEMENT_PATH), e );
	    } 

	    //Megkeresem a PARAMELEMENTROOT-ben a PARAMELEMENT-hez vezeto utat
	    Node actualNode = document;
    
	    while( actualNode.hasChildNodes() ){
		
	    	actualNode = actualNode.getFirstChild();
	    	Element actualElement = (Element)actualNode;
	    	String tagName = actualElement.getTagName();
	    	String attrName = null;
	    	
	    	//Ha BASENODE
	    	if( tagName.equals( BaseNodeDataModel.TAG.getName() ) ){
	    		attrName = actualElement.getAttribute(BaseNodeDataModel.ATTR_NAME);	    		
	    		baseDataModelForFillOut = (BaseDataModelInterface) CommonOperations.getDataModelByNameInLevel( baseDataModelForFillOut, Tag.BASENODE, attrName );

	    		if( null == baseDataModelForFillOut ){

	    			throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, nameAttrValue, ATTR_FILL_BASE_ELEMENT_PATH, element.getAttribute(ATTR_FILL_BASE_ELEMENT_PATH) );
	    		}
	    		
	    	//Ha BASEELEMENT
	    	}else if( tagName.equals( BaseElementDataModel.TAG.getName() ) ){
	    		attrName = actualElement.getAttribute(BaseElementDataModel.ATTR_NAME);
	    		baseDataModelForFillOut = (BaseDataModelInterface) CommonOperations.getDataModelByNameInLevel( baseDataModelForFillOut, Tag.BASEELEMENT, attrName );
	
	    		if( null == baseDataModelForFillOut ){

	    			throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, nameAttrValue, ATTR_FILL_BASE_ELEMENT_PATH, element.getAttribute(ATTR_FILL_BASE_ELEMENT_PATH) );
	    		}

	    	
	    	//Ha BASEPAGE
	    	}else if( tagName.equals( BasePageDataModel.TAG.getName() ) ){
	    		attrName = actualElement.getAttribute(BasePageDataModel.ATTR_NAME);
	    		baseDataModelForFillOut = (BaseDataModelInterface) CommonOperations.getDataModelByNameInLevel( baseDataModelForFillOut, Tag.BASEPAGE, attrName );

	    		if( null == baseDataModelForFillOut ){

	    			throw new XMLBaseConversionPharseException( rootTag, tag,  nameAttrName, nameAttrValue, ATTR_FILL_BASE_ELEMENT_PATH, element.getAttribute(ATTR_FILL_BASE_ELEMENT_PATH) );
	    		}
	    	
	    		
	    	}else{
	    		
	    		throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, nameAttrValue, ATTR_FILL_BASE_ELEMENT_PATH, element.getAttribute(ATTR_FILL_BASE_ELEMENT_PATH) );	    		
	    	}
	    }	    
	    try{
	    	
	    	this.baseElementDataModel = (BaseElementDataModel)baseDataModelForFillOut;
	    	
	    }catch(ClassCastException e){

	    	//Nem sikerult az utvonalat megtalalni
	    	throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, nameAttrValue, ATTR_FILL_BASE_ELEMENT_PATH, element.getAttribute(ATTR_FILL_BASE_ELEMENT_PATH ), e );
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
	
		BaseElementDataModel baseElement = element.getBaseElement();
		By by = null;
		WebElement webElement = null;
		
		if( null != elementProgress ){
			elementProgress.elementStarted( element.getName(), baseElement.getVariableValue() );
		}
		
		//Searching for the element - waiting for it
		WebDriverWait wait = new WebDriverWait(driver, 10);
						
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
		//throw new ElementException( elementBase.getName(), elementBase.getBy().toString(), e );
		
/*		//Ha valtozokent van deffinialva es muvelet elott kell menteni az erteket
		if( baseElement.getVariableSample().equals( VariableSampleListEnum.PRE ) ){
				
			//Elmenti az elem tartalmat a valtozoba
			baseElement.setVariableValue( webElement.getText() );
		}
*/		
		try{
			
			//Execute the operation
			//webElement.clear();
			webElement.sendKeys( baseElementDataModel.getVariableValue() );
			webElement.sendKeys(Keys.TAB);
			
		}catch (WebDriverException webDriverException){
			throw new ElementInvalidOperationException( getName(), element.getName(), baseElement.getSelector(), webDriverException );
		}
		
		//Ha valtozokent van deffinialva es muvelet utan kell menteni az erteket
/*		if( baseElement.getVariableSample().equals( VariableSampleListEnum.POST ) ){
				
			//Elmenti az elem tartalmat a valtozoba
			//webElement.sendKeys(Keys.TAB);
			baseElement.setVariableValue( webElement.getAttribute("value") );		
		}
*/		
		if( null != elementProgress ){
			elementProgress.elementEnded( element.getName(), baseElement.getVariableValue() );
		}
	}

	public BaseElementDataModel getBaseElement() {
		return baseElementDataModel;
	}

	@Override
	public void setXMLAttribute(Document document, Element element) {		
		Attr attr = document.createAttribute( ATTR_FILL_BASE_ELEMENT_PATH );
		attr.setValue( baseElementDataModel.getPathTag() );
		element.setAttributeNode( attr );	
	}
	
}
