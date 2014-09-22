package hu.akoel.grawit.core.operations;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.UnexpectedTagNameException;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.ElementProgressInterface;
import hu.akoel.grawit.core.treenodedatamodel.VariableDataModelInterface;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.variable.VariableElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.variable.VariableNodeDataModel;
import hu.akoel.grawit.core.treenodedatamodel.variable.VariableRootDataModel;
import hu.akoel.grawit.enums.SelectorType;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.enums.list.ListSelectionByListEnum;
import hu.akoel.grawit.enums.list.VariableSampleListEnum;
import hu.akoel.grawit.exceptions.ElementException;
import hu.akoel.grawit.exceptions.ElementInvalidOperationException;
import hu.akoel.grawit.exceptions.ElementInvalidSelectorException;
import hu.akoel.grawit.exceptions.ElementNotFoundComponentException;
import hu.akoel.grawit.exceptions.ElementNotFoundSelectorException;
import hu.akoel.grawit.exceptions.ElementTimeoutException;
import hu.akoel.grawit.exceptions.XMLBaseConversionPharseException;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;

public class SelectVariableOperation implements ElementOperationInterface{
	
	private static final String NAME = "SELECTVARIABLE";
	private static final String ATTR_SELECTION_BY = "selectionby";
	private static final String ATTR_SELECT_VARIABLE_ELEMENT_PATH = "selectvariableelementpath";
	private static final String ATTR_SELECT_BASE_ELEMENT_PATH = "selectbaseelementpath";
	private static final String ATTR_SELECT_STRING = "selectstring";
	
	//--- Data model
	private VariableElementDataModel variableElementDataModel;
	private ListSelectionByListEnum selectionBy;
	//----
	
	public SelectVariableOperation( VariableElementDataModel variableElementDataModel, ListSelectionByListEnum selectionBy ){
		this.variableElementDataModel = variableElementDataModel;
		this.selectionBy = selectionBy;
	}
	
	public SelectVariableOperation( Element element, VariableRootDataModel variableRootDataModel, Tag rootTag, Tag tag, String nameAttrName, String nameAttrValue ) throws XMLBaseConversionPharseException, XMLMissingAttributePharseException{
		
		VariableDataModelInterface variableDataModelForSelect = variableRootDataModel;
		
		//SELECTION BY
		if( !element.hasAttribute( ATTR_SELECTION_BY ) ){
			throw new XMLMissingAttributePharseException( rootTag, tag, ATTR_SELECTION_BY );			
		}
		String stringSelectionBy = element.getAttribute( ATTR_SELECTION_BY );
		if( stringSelectionBy.equals( ListSelectionByListEnum.BYINDEX ) ){
			selectionBy = ListSelectionByListEnum.BYINDEX;
		}else if( stringSelectionBy.equals( ListSelectionByListEnum.BYVALUE ) ){
			selectionBy = ListSelectionByListEnum.BYVALUE;
		}else if( stringSelectionBy.equals( ListSelectionByListEnum.BYVISIBLETEXT ) ){
			selectionBy = ListSelectionByListEnum.BYVISIBLETEXT;
		}else{
			selectionBy = ListSelectionByListEnum.BYVISIBLETEXT;
		}		

		//SELECT WITH VARIABLE ELEMENT
		if( element.hasAttribute( ATTR_SELECT_VARIABLE_ELEMENT_PATH ) ){
			
		//SELECT WITH BASE ELEMENT
		}else if( element.hasAttribute( ATTR_SELECT_BASE_ELEMENT_PATH ) ){
			
		//SELECT WITH STRING
		}else if( element.hasAttribute( ATTR_SELECT_VARIABLE_ELEMENT_PATH ) ){
			
		}
		
		//SELECT VARIABLE ELEMENT
		String variableElementPathString = element.getAttribute(ATTR_SELECT_VARIABLE_ELEMENT_PATH);				
		variableElementPathString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + variableElementPathString;  
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
	    DocumentBuilder builder;
	    Document document = null;
	    try{  
	        builder = factory.newDocumentBuilder();  
	        document = builder.parse( new InputSource( new StringReader( variableElementPathString ) ) );  
	    } catch (Exception e) {  
	    
	    	//Nem sikerult az atalakitas
	    	throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, nameAttrValue, ATTR_SELECT_VARIABLE_ELEMENT_PATH, element.getAttribute(ATTR_SELECT_VARIABLE_ELEMENT_PATH), e );
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
	    		variableDataModelForSelect = (VariableDataModelInterface) CommonOperations.getDataModelByNameInLevel( variableDataModelForSelect, Tag.VARIABLENODE, attrName );

	    		if( null == variableDataModelForSelect ){

	    			throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, nameAttrValue, ATTR_SELECT_VARIABLE_ELEMENT_PATH, element.getAttribute(ATTR_SELECT_VARIABLE_ELEMENT_PATH) );
	    		}
	    		
	    	//Ha VARIABLEELEMENT
	    	}else if( tagName.equals( VariableElementDataModel.TAG.getName() ) ){
	    		attrName = actualElement.getAttribute(VariableElementDataModel.ATTR_NAME);
	    		variableDataModelForSelect = (VariableDataModelInterface) CommonOperations.getDataModelByNameInLevel( variableDataModelForSelect, Tag.VARIABLEELEMENT, attrName );
	    		
	    		if( null == variableDataModelForSelect ){

	    			throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, getName(), ATTR_SELECT_VARIABLE_ELEMENT_PATH, element.getAttribute(ATTR_SELECT_VARIABLE_ELEMENT_PATH) );
	    		}
	    		
	    	}else{
	    		
	    		throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, getName(), ATTR_SELECT_VARIABLE_ELEMENT_PATH, element.getAttribute(ATTR_SELECT_VARIABLE_ELEMENT_PATH) );	    		
	    	}
	    }	    
	    try{
	    	
	    	this.variableElementDataModel = (VariableElementDataModel)variableDataModelForSelect;
	    	
	    }catch(ClassCastException e){

	    	//Nem sikerult az utvonalat megtalalni
	    	throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, nameAttrValue, ATTR_SELECT_VARIABLE_ELEMENT_PATH, element.getAttribute(ATTR_SELECT_VARIABLE_ELEMENT_PATH ), e );
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

		Select select = null;
		try{
			select = new Select(webElement);
		}catch (UnexpectedTagNameException e){
			throw new ElementInvalidOperationException( "List Selection", element.getName(), baseElement.getSelector(), e );			
		}
				
		try{

			if( selectionBy.equals( ListSelectionByListEnum.BYVALUE ) ){
		
				//Ha valtozokent van deffinialva es muvelet elott kell menteni az erteket
				if( baseElement.getVariableSample().equals( VariableSampleListEnum.PRE ) ){
					
					//Elmenti az elem tartalmat a valtozoba
					element.getBaseElement().setVariableValue( select.getFirstSelectedOption().getAttribute("value") );
				}			
			
				//Muvelet
				select.selectByValue( variableElementDataModel.getValue() );
			
				//Ha valtozokent van deffinialva es muvelet utan kell menteni az erteket
				if( baseElement.getVariableSample().equals( VariableSampleListEnum.POST ) ){
					
					//Elmenti az elem tartalmat a valtozoba
					//webElement.sendKeys(Keys.TAB);
					element.getBaseElement().setVariableValue( webElement.getAttribute("value") );
			
				}
			
			}else if( selectionBy.equals( ListSelectionByListEnum.BYINDEX ) ){
			
				//TODO ki kell talalni, hogy hogyan szerezheto meg a kivalasztott sorszama

				select.selectByIndex( Integer.valueOf( variableElementDataModel.getValue() ) );
			
			}else if( selectionBy.equals( ListSelectionByListEnum.BYVISIBLETEXT ) ){
			
				//TODO ki kell talalni, hogy hogyan szerezheto meg a kivalasztott szovege
			
				select.selectByVisibleText( variableElementDataModel.getValue() );
			}
			
		}catch(NoSuchElementException e ){
			
			throw new ElementNotFoundComponentException( variableElementDataModel.getValue(), selectionBy, element.getName(), baseElement.getSelector(), e );

		}catch (Exception e ){
			
		}		
		
		if( null != elementProgress ){
			elementProgress.elementEnded( element.getName() );
		}
	}

	public VariableElementDataModel getVariableElement() {
		return variableElementDataModel;
	}

	@Override
	public void setXMLAttribute(Document document, Element element) {
		Attr attr = document.createAttribute( ATTR_SELECT_VARIABLE_ELEMENT_PATH );
		attr.setValue( variableElementDataModel.getPathTag() );
		element.setAttributeNode( attr );		
		
		attr = document.createAttribute( ATTR_SELECTION_BY );
		attr.setValue( selectionBy.name() );
		element.setAttributeNode( attr );	
	}

}
