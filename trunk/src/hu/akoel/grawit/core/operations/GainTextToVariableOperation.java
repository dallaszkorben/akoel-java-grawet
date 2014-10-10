package hu.akoel.grawit.core.operations;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.ElementProgressInterface;
import hu.akoel.grawit.Properties;
import hu.akoel.grawit.core.treenodedatamodel.VariableDataModelInterface;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.variable.VariableElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.variable.VariableNodeDataModel;
import hu.akoel.grawit.core.treenodedatamodel.variable.VariableRootDataModel;
import hu.akoel.grawit.enums.SelectorType;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.enums.list.ElementTypeListEnum;
import hu.akoel.grawit.exceptions.ElementException;
import hu.akoel.grawit.exceptions.ElementInvalidSelectorException;
import hu.akoel.grawit.exceptions.ElementNotFoundSelectorException;
import hu.akoel.grawit.exceptions.ElementTimeoutException;
import hu.akoel.grawit.exceptions.XMLBaseConversionPharseException;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;

/**
 * 
 * @author afoldvarszky
 *
 */
public class GainTextToVariableOperation implements ElementOperationInterface{
	
	private static final String NAME = "GAINTEXTTOVARIABLE";
	private static final String ATTR_PATTERN = "pattern";
	private static final String ATTR_FILL_VARIABLE_ELEMENT_PATH = "fillvariableelementpath";
	
	private Pattern pattern;
	private Matcher matcher;
	
	//--- Data model
	private VariableElementDataModel variableElementDataModel;
	private String stringPattern;
	//---
	
	public GainTextToVariableOperation( VariableElementDataModel variableElementDataModel, String stringPattern ){
		this.stringPattern = stringPattern;		
		this.variableElementDataModel = variableElementDataModel;
		
		common( stringPattern );
	}
	
	public GainTextToVariableOperation( Element element, VariableRootDataModel variableRootDataModel, Tag rootTag, Tag tag, String nameAttrName, String nameAttrValue ) throws XMLMissingAttributePharseException, XMLBaseConversionPharseException{
		
		VariableDataModelInterface variableDataModelForFillOut = variableRootDataModel;
		
		if( !element.hasAttribute( ATTR_FILL_VARIABLE_ELEMENT_PATH ) ){
			throw new XMLMissingAttributePharseException( rootTag, tag, ATTR_FILL_VARIABLE_ELEMENT_PATH );		
		}
		String variableElementPathString = element.getAttribute(ATTR_FILL_VARIABLE_ELEMENT_PATH);				
		variableElementPathString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + variableElementPathString;  
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
	    DocumentBuilder builder;
	    Document document = null;
	    try{  
	        builder = factory.newDocumentBuilder();  
	        document = builder.parse( new InputSource( new StringReader( variableElementPathString ) ) );  
	    } catch (Exception e) {  
	    
	    	//Nem sikerult az atalakitas
	    	throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, nameAttrValue, ATTR_FILL_VARIABLE_ELEMENT_PATH, element.getAttribute(ATTR_FILL_VARIABLE_ELEMENT_PATH), e );
	    } 

	    //Megkeresem a VARIABLEROOT-ben a VARIABLEELEMENT-hez vezeto utat
	    Node actualNode = document;
	    while( actualNode.hasChildNodes() ){
		
	    	actualNode = actualNode.getFirstChild();
	    	Element actualElement = (Element)actualNode;
	    	String tagName = actualElement.getTagName();
	    	String attrName = null;
	    	
	    	//Ha VARIABLENODE
	    	if( tagName.equals( VariableNodeDataModel.TAG.getName() ) ){
	    		attrName = actualElement.getAttribute(VariableNodeDataModel.ATTR_NAME);	    		
	    		variableDataModelForFillOut = (VariableDataModelInterface) CommonOperations.getDataModelByNameInLevel( variableDataModelForFillOut, Tag.VARIABLENODE, attrName );

	    		if( null == variableDataModelForFillOut ){

	    			throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, nameAttrValue, ATTR_FILL_VARIABLE_ELEMENT_PATH, element.getAttribute(ATTR_FILL_VARIABLE_ELEMENT_PATH) );
	    		}
	    		
	    	//Ha VARIABLEELEMENT
	    	}else if( tagName.equals( VariableElementDataModel.TAG.getName() ) ){
	    		attrName = actualElement.getAttribute(VariableElementDataModel.ATTR_NAME);
	    		variableDataModelForFillOut = (VariableDataModelInterface) CommonOperations.getDataModelByNameInLevel( variableDataModelForFillOut, Tag.VARIABLEELEMENT, attrName );
	    		
	    		if( null == variableDataModelForFillOut ){

	    			throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, getName(), ATTR_FILL_VARIABLE_ELEMENT_PATH, element.getAttribute(ATTR_FILL_VARIABLE_ELEMENT_PATH) );
	    		}
	    		
	    	}else{
	    		
	    		throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, getName(), ATTR_FILL_VARIABLE_ELEMENT_PATH, element.getAttribute(ATTR_FILL_VARIABLE_ELEMENT_PATH) );	    		
	    	}
	    }	    
	    try{
	    	
	    	this.variableElementDataModel = (VariableElementDataModel)variableDataModelForFillOut;
	    	
	    }catch(ClassCastException e){

	    	//Nem sikerult az utvonalat megtalalni
	    	throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, nameAttrValue, ATTR_FILL_VARIABLE_ELEMENT_PATH, element.getAttribute(ATTR_FILL_VARIABLE_ELEMENT_PATH ), e );
	    }
		
		if( !element.hasAttribute( ATTR_PATTERN ) ){
			throw new XMLMissingAttributePharseException( rootTag, tag, ATTR_PATTERN );			
		}
		this.stringPattern = element.getAttribute( ATTR_PATTERN );	
	    
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
				
		String origText = "";

		//GAIN TEXT
		//Ha LIST
		if( element.getBaseElement().getElementType().equals(ElementTypeListEnum.LIST)){

			Select select = new Select(webElement);
			origText = select.getFirstSelectedOption().getText();
			
		//Ha FIELD/CHECKBOX/RADIOBUTTON
		}else{		
			origText = webElement.getText();
		}
		ArrayList<Object> parameters = new ArrayList<>();
	
		//EXECUTE OPERATION = Elmenti az elem tartalmat a valtozoba		
		if( null == pattern ){
			parameters.add( origText );
			variableElementDataModel.setParameters( parameters );
		}else{
			matcher = pattern.matcher( origText );
			if( matcher.find() ){
				String resultText = matcher.group();
				parameters.add( resultText );
				variableElementDataModel.setParameters(parameters);
			}			
		}
		
		if( null != elementProgress ){
			elementProgress.elementEnded( element.getName() );
		}
	}
	
	public String getStringPattern(){
		return stringPattern;
	}

	public VariableElementDataModel getVariableElement() {
		return variableElementDataModel;
	}
	
	@Override
	public void setXMLAttribute(Document document, Element element) {
		Attr attr = document.createAttribute( ATTR_PATTERN );
		attr.setValue( stringPattern );
		element.setAttributeNode(attr);		
		
		attr = document.createAttribute( ATTR_FILL_VARIABLE_ELEMENT_PATH );
		attr.setValue( variableElementDataModel.getPathTag() );
		element.setAttributeNode( attr );	
	}
	
}

