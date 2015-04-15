package hu.akoel.grawit.core.operations;

import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseCollectorDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseElementDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseFolderDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.NormalBaseElementDataModel;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.exceptions.ElementException;
import hu.akoel.grawit.exceptions.ElementInvalidOperationException;
import hu.akoel.grawit.exceptions.XMLBaseConversionPharseException;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;
import hu.akoel.grawit.gui.interfaces.progress.ElementProgressInterface;

public class FillWithBaseElementOperation extends ElementOperationAdapter implements HasElementOperationInterface{
	
	private static final String NAME = "FILLELEMENT";	
	private static final String ATTR_FILL_BASE_ELEMENT_PATH = "fillelementpath";
	
	//--- Data model
	private BaseElementDataModelAdapter baseElementDataModel;
	//---
	
	public FillWithBaseElementOperation( BaseElementDataModelAdapter baseElementDataModel ){
		this.baseElementDataModel = baseElementDataModel;
	}

	public FillWithBaseElementOperation( Element element, BaseRootDataModel baseRootDataModel, Tag rootTag, Tag tag, String nameAttrName, String nameAttrValue ) throws XMLBaseConversionPharseException, XMLMissingAttributePharseException{		
		
		BaseDataModelAdapter baseDataModelForFillOut = baseRootDataModel;
		
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
	    	if( tagName.equals( BaseFolderDataModel.TAG.getName() ) ){
	    		attrName = actualElement.getAttribute(BaseFolderDataModel.ATTR_NAME);	    		
	    		baseDataModelForFillOut = (BaseDataModelAdapter) CommonOperations.getDataModelByNameInLevel( baseDataModelForFillOut, Tag.BASEFOLDER, attrName );

	    		if( null == baseDataModelForFillOut ){

	    			throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, nameAttrValue, ATTR_FILL_BASE_ELEMENT_PATH, element.getAttribute(ATTR_FILL_BASE_ELEMENT_PATH) );
	    		}
	    		
	    	//HA BASEELEMENT
	    	}else if( tagName.equals( BaseElementDataModelAdapter.TAG.getName() ) ){	
			    		
	    		attrName = actualElement.getAttribute(NormalBaseElementDataModel.ATTR_NAME);
			    		
	    		baseDataModelForFillOut = (BaseDataModelAdapter) CommonOperations.getDataModelByNameInLevel( baseDataModelForFillOut, Tag.BASEELEMENT, attrName );
	    		if( null == baseDataModelForFillOut ){

	    			throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, nameAttrValue, ATTR_FILL_BASE_ELEMENT_PATH, element.getAttribute(ATTR_FILL_BASE_ELEMENT_PATH) );
	    		}		    		
    	
	    	//Ha BASEPAGE
	    	}else if( tagName.equals( BaseCollectorDataModel.TAG.getName() ) ){
	    		attrName = actualElement.getAttribute(BaseCollectorDataModel.ATTR_NAME);
	    		baseDataModelForFillOut = (BaseDataModelAdapter) CommonOperations.getDataModelByNameInLevel( baseDataModelForFillOut, Tag.BASECOLLECTOR, attrName );

	    		if( null == baseDataModelForFillOut ){

	    			throw new XMLBaseConversionPharseException( rootTag, tag,  nameAttrName, nameAttrValue, ATTR_FILL_BASE_ELEMENT_PATH, element.getAttribute(ATTR_FILL_BASE_ELEMENT_PATH) );
	    		}
	    	
	    		
	    	}else{
	    		
	    		throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, nameAttrValue, ATTR_FILL_BASE_ELEMENT_PATH, element.getAttribute(ATTR_FILL_BASE_ELEMENT_PATH) );	    		
	    	}
	    }	    
	    try{
	    	
	    	this.baseElementDataModel = (BaseElementDataModelAdapter)baseDataModelForFillOut;
	    	
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
		
	@Override
	public BaseElementDataModelAdapter getBaseElement() {
		return baseElementDataModel;
	}

	@Override
	public ArrayList<String> doOperation(WebDriver driver, BaseElementDataModelAdapter baseElement, WebElement webElement, ElementProgressInterface elementProgress) throws ElementException {
		ArrayList<String> returnArray = new ArrayList<>();
		
		if( baseElement instanceof NormalBaseElementDataModel ){

			try{
			
				//Execute the operation
				webElement.sendKeys( baseElementDataModel.getStoredValue() );
				//webElement.sendKeys(Keys.TAB);
			
			}catch (WebDriverException webDriverException){
				throw new ElementInvalidOperationException( getName(), baseElement.getName(), ((NormalBaseElementDataModel)baseElement).getSelector(), webDriverException );
			}
			
			returnArray.add( CommonOperations.TAB_BY_SPACE + "webElement.sendKeys(\"" + baseElementDataModel.getStoredValue() + "\");     //" + baseElement.getName() );
		}
		
		return returnArray;
		
	}
	
	@Override
	public void setXMLAttribute(Document document, Element element) {		
		Attr attr = document.createAttribute( ATTR_FILL_BASE_ELEMENT_PATH );
		attr.setValue( baseElementDataModel.getPathTag() );
		element.setAttributeNode( attr );	
	}

	@Override
	public Object clone() {
				
		return new FillWithBaseElementOperation(baseElementDataModel);
	}
	
	@Override
	public String getOperationNameToString() {		
		return "FillFieldWithBaseElement()";
	}
	
}
