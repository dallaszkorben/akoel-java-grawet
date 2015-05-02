package hu.akoel.grawit.core.operations;

import java.io.StringReader;
import java.util.Set;

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
import hu.akoel.grawit.core.treenodedatamodel.base.BaseElementDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.ScriptBaseElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.constant.ConstantDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.constant.ConstantElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.constant.ConstantFolderNodeDataModel;
import hu.akoel.grawit.core.treenodedatamodel.constant.ConstantRootDataModel;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.exceptions.ElementException;
import hu.akoel.grawit.exceptions.XMLBaseConversionPharseException;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;
import hu.akoel.grawit.gui.interfaces.progress.ProgressIndicatorInterface;

public class ScriptElementAddConstantToParametersOperation extends ScriptOperationAdapter implements HasConstantOperationInterface{
	
	private static final String NAME = "ADDCONSTANTTOPARAMETERS";	
	private static final String ADD_CONSTANT_TO_PARAMETERS_PATH = "constantpath";
	
	//--- Data model
	private ConstantElementDataModel constantElementDataModel;
	//---
	
	public ScriptElementAddConstantToParametersOperation( ConstantElementDataModel constantElementDataModel ){
		this.constantElementDataModel = constantElementDataModel;
	}
	
	public ScriptElementAddConstantToParametersOperation( Element element, ConstantRootDataModel constantRootDataModel, Tag rootTag, Tag tag, String nameAttrName, String nameAttrValue ) throws XMLBaseConversionPharseException, XMLMissingAttributePharseException{
		
		ConstantDataModelAdapter constantDataModelForFillOut = constantRootDataModel;
		
		if( !element.hasAttribute( ADD_CONSTANT_TO_PARAMETERS_PATH ) ){
			throw new XMLMissingAttributePharseException( rootTag, tag, ADD_CONSTANT_TO_PARAMETERS_PATH );		
		}
		String constantElementPathString = element.getAttribute(ADD_CONSTANT_TO_PARAMETERS_PATH);				
		constantElementPathString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + constantElementPathString;  
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
	    DocumentBuilder builder;
	    Document document = null;
	    try{  
	        builder = factory.newDocumentBuilder();  
	        document = builder.parse( new InputSource( new StringReader( constantElementPathString ) ) );  
	    } catch (Exception e) {  
	    
	    	//Nem sikerult az atalakitas
	    	throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, nameAttrValue, ADD_CONSTANT_TO_PARAMETERS_PATH, element.getAttribute(ADD_CONSTANT_TO_PARAMETERS_PATH), e );
	    } 

	    //Megkeresem a CONSTANTROOT-ben a CONSTANTELEMENT-hez vezeto utat
	    Node actualNode = document;
	    while( actualNode.hasChildNodes() ){
		
	    	actualNode = actualNode.getFirstChild();
	    	Element actualElement = (Element)actualNode;
	    	String tagName = actualElement.getTagName();
	    	String attrName = null;
	    	
	    	//Ha CONSTANTNODE
	    	if( tagName.equals( ConstantFolderNodeDataModel.TAG.getName() ) ){
	    		attrName = actualElement.getAttribute(ConstantFolderNodeDataModel.ATTR_NAME);	    		
	    		constantDataModelForFillOut = (ConstantDataModelAdapter) CommonOperations.getDataModelByNameInLevel( constantDataModelForFillOut, Tag.CONSTANTFOLDER, attrName );

	    		if( null == constantDataModelForFillOut ){

	    			throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, nameAttrValue, ADD_CONSTANT_TO_PARAMETERS_PATH, element.getAttribute(ADD_CONSTANT_TO_PARAMETERS_PATH) );
	    		}
	    		
	    	//Ha CONSTANTELEMENT
	    	}else if( tagName.equals( ConstantElementDataModel.TAG.getName() ) ){
	    		attrName = actualElement.getAttribute(ConstantElementDataModel.ATTR_NAME);
	    		constantDataModelForFillOut = (ConstantDataModelAdapter) CommonOperations.getDataModelByNameInLevel( constantDataModelForFillOut, Tag.CONSTANTELEMENT, attrName );
	    		
	    		if( null == constantDataModelForFillOut ){

	    			throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, getName(), ADD_CONSTANT_TO_PARAMETERS_PATH, element.getAttribute(ADD_CONSTANT_TO_PARAMETERS_PATH) );
	    		}
	    		
	    	}else{
	    		
	    		throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, getName(), ADD_CONSTANT_TO_PARAMETERS_PATH, element.getAttribute(ADD_CONSTANT_TO_PARAMETERS_PATH) );	    		
	    	}
	    }	    
	    try{
	    	
	    	this.constantElementDataModel = (ConstantElementDataModel)constantDataModelForFillOut;
	    	
	    }catch(ClassCastException e){

	    	//Nem sikerult az utvonalat megtalalni
	    	throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, nameAttrValue, ADD_CONSTANT_TO_PARAMETERS_PATH, element.getAttribute(ADD_CONSTANT_TO_PARAMETERS_PATH ), e );
	    }
	}
	
	public static String getStaticName(){
		return NAME;
	}
	
	@Override
	public String getName() {		
		return getStaticName();
	}
	
	@Override
	public ConstantElementDataModel getConstantElement() {
		return constantElementDataModel;
	}

	@Override
	public void doOperation(WebDriver driver, BaseElementDataModelAdapter baseElement, WebElement webElement, ProgressIndicatorInterface elementProgress, String tab, Set<String> definedElementSet, boolean needToPrintSource ) throws ElementException {

		//HA SPECIALBASEELEMENT - annak kell lennie
		if( baseElement instanceof ScriptBaseElementDataModel ){

			if( needToPrintSource ){
				outputScripClass(driver, baseElement, webElement, elementProgress, tab, definedElementSet );		
				elementProgress.printSource( tab + baseElement.getNameAsScript() + ".addParameter( \"" + constantElementDataModel.getValue() + "\" );" );
				elementProgress.printSource( "" );
			}
			
			((ScriptBaseElementDataModel)baseElement).addParameter( constantElementDataModel.getValue() );
			
		}

	}

	@Override
	public void setXMLAttribute(Document document, Element element) {
		Attr attr = document.createAttribute( ADD_CONSTANT_TO_PARAMETERS_PATH );		
		attr.setValue( constantElementDataModel.getPathTag() );
		element.setAttributeNode( attr );			
	}

	@Override
	public Object clone() {

		return new ScriptElementAddConstantToParametersOperation(constantElementDataModel);
	}
	
	@Override
	public String getOperationNameToString() {		
		return "AddConstantToScriptElementParameter()";
	}
	
}
