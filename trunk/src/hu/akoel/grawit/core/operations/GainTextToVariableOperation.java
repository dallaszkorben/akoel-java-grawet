package hu.akoel.grawit.core.operations;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.ElementProgressInterface;
import hu.akoel.grawit.core.treenodedatamodel.VariableDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.variable.VariableElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.variable.VariableNodeDataModel;
import hu.akoel.grawit.core.treenodedatamodel.variable.VariableRootDataModel;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.exceptions.ElementException;
import hu.akoel.grawit.exceptions.XMLBaseConversionPharseException;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;

/**
 * 
 * @author afoldvarszky
 *
 */
public class GainTextToVariableOperation extends ElementOperationAdapter{
	
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
		
		VariableDataModelAdapter variableDataModelForFillOut = variableRootDataModel;
		
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
	    		variableDataModelForFillOut = (VariableDataModelAdapter) CommonOperations.getDataModelByNameInLevel( variableDataModelForFillOut, Tag.VARIABLENODE, attrName );

	    		if( null == variableDataModelForFillOut ){

	    			throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, nameAttrValue, ATTR_FILL_VARIABLE_ELEMENT_PATH, element.getAttribute(ATTR_FILL_VARIABLE_ELEMENT_PATH) );
	    		}
	    		
	    	//Ha VARIABLEELEMENT
	    	}else if( tagName.equals( VariableElementDataModel.TAG.getName() ) ){
	    		attrName = actualElement.getAttribute(VariableElementDataModel.ATTR_NAME);
	    		variableDataModelForFillOut = (VariableDataModelAdapter) CommonOperations.getDataModelByNameInLevel( variableDataModelForFillOut, Tag.VARIABLEELEMENT, attrName );
	    		
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
		
	public String getStringPattern(){
		return stringPattern;
	}

	public VariableElementDataModel getVariableElement() {
		return variableElementDataModel;
	}
	
	@Override
	public void doOperation(WebDriver driver, ParamElementDataModel element, WebElement webElement, ElementProgressInterface elementProgress) throws ElementException {
		
		String origText = "";

		
		//GAIN TEXT
		//Ha LIST
/*		if( element.getBaseElement().getElementType().equals(ElementTypeListEnum.LIST)){

			Select select = new Select(webElement);
			origText = select.getFirstSelectedOption().getText();
*/			
		//Ha FIELD/CHECKBOX/RADIOBUTTON
		//}else{		
			origText = webElement.getText();
		//}
		ArrayList<Object> parameters = new ArrayList<>();
	
		//EXECUTE_SCRIPT OPERATION = Elmenti az elem tartalmat a valtozoba		
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

	@Override
	public Object clone() {

		//Fontos, hogy cloneWithParent() mert szukseges, hogy legyen szuloje
		VariableElementDataModel variableElementDataModel = (VariableElementDataModel) this.variableElementDataModel.cloneWithParent();
		String stringPattern = new String( this.stringPattern );
		
		return new GainTextToVariableOperation(variableElementDataModel, stringPattern);
	}


}

