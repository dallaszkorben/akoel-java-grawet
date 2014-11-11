package hu.akoel.grawit.core.operations;

import java.io.StringReader;

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
import hu.akoel.grawit.core.treenodedatamodel.BaseDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.BaseElementDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseNodeDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.BasePageDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.NormalBaseElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.SpecialBaseElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamElementDataModel;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.exceptions.ElementException;
import hu.akoel.grawit.exceptions.XMLBaseConversionPharseException;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;

public class AddStoredElementToParameterOperation extends ElementOperationAdapter{
	
	private static final String NAME = "ADDSTOREDELEMENTTOPARAMETER";	
	private static final String ATTR_STORED_BASE_ELEMENT_TO_PARAMETER_PATH = "storedbaseelementtoparameterpath";
	
	//--- Data model
	private BaseElementDataModelAdapter baseElementDataModel;
	//---
	
	public AddStoredElementToParameterOperation( BaseElementDataModelAdapter baseElementDataModel ){
		this.baseElementDataModel = baseElementDataModel;
	}

	public AddStoredElementToParameterOperation( Element element, BaseRootDataModel baseRootDataModel, Tag rootTag, Tag tag, String nameAttrName, String nameAttrValue ) throws XMLBaseConversionPharseException, XMLMissingAttributePharseException{		
		
		BaseDataModelAdapter baseDataModelForFillOut = baseRootDataModel;
		
		if( !element.hasAttribute( ATTR_STORED_BASE_ELEMENT_TO_PARAMETER_PATH ) ){
			throw new XMLMissingAttributePharseException( rootTag, tag, ATTR_STORED_BASE_ELEMENT_TO_PARAMETER_PATH );		
		}	
		String baseElementPathString = element.getAttribute(ATTR_STORED_BASE_ELEMENT_TO_PARAMETER_PATH);				
		baseElementPathString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + baseElementPathString;  
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
	    DocumentBuilder builder;
	    Document document = null;
	    try{  
	        builder = factory.newDocumentBuilder();  
	        document = builder.parse( new InputSource( new StringReader( baseElementPathString ) ) );  
	    } catch (Exception e) {  
	    
	    	//Nem sikerult az atalakitas
	    	throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, nameAttrValue, ATTR_STORED_BASE_ELEMENT_TO_PARAMETER_PATH, element.getAttribute(ATTR_STORED_BASE_ELEMENT_TO_PARAMETER_PATH), e );
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
	    		baseDataModelForFillOut = (BaseDataModelAdapter) CommonOperations.getDataModelByNameInLevel( baseDataModelForFillOut, Tag.BASENODE, attrName );

	    		if( null == baseDataModelForFillOut ){

	    			throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, nameAttrValue, ATTR_STORED_BASE_ELEMENT_TO_PARAMETER_PATH, element.getAttribute(ATTR_STORED_BASE_ELEMENT_TO_PARAMETER_PATH) );
	    		}
	    		
	    	//Ha NORMALBASEELEMENT
	    	}else if( tagName.equals( NormalBaseElementDataModel.TAG.getName() ) ){
	    		attrName = actualElement.getAttribute(NormalBaseElementDataModel.ATTR_NAME);
	    		baseDataModelForFillOut = (BaseDataModelAdapter) CommonOperations.getDataModelByNameInLevel( baseDataModelForFillOut, Tag.NORMALBASEELEMENT, attrName );
	
	    		if( null == baseDataModelForFillOut ){

	    			throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, nameAttrValue, ATTR_STORED_BASE_ELEMENT_TO_PARAMETER_PATH, element.getAttribute(ATTR_STORED_BASE_ELEMENT_TO_PARAMETER_PATH) );
	    		}

	    	//Ha SPECIALBASEELEMENT
	    	}else if( tagName.equals( SpecialBaseElementDataModel.TAG.getName() ) ){
	    		attrName = actualElement.getAttribute(SpecialBaseElementDataModel.ATTR_NAME);
	    		baseDataModelForFillOut = (BaseDataModelAdapter) CommonOperations.getDataModelByNameInLevel( baseDataModelForFillOut, Tag.SPECIALBASEELEMENT, attrName );
		
	    		if( null == baseDataModelForFillOut ){

	    			throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, nameAttrValue, ATTR_STORED_BASE_ELEMENT_TO_PARAMETER_PATH, element.getAttribute(ATTR_STORED_BASE_ELEMENT_TO_PARAMETER_PATH) );
	    		}
	    	
	    	//Ha BASEPAGE
	    	}else if( tagName.equals( BasePageDataModel.TAG.getName() ) ){
	    		attrName = actualElement.getAttribute(BasePageDataModel.ATTR_NAME);
	    		baseDataModelForFillOut = (BaseDataModelAdapter) CommonOperations.getDataModelByNameInLevel( baseDataModelForFillOut, Tag.BASEPAGE, attrName );

	    		if( null == baseDataModelForFillOut ){

	    			throw new XMLBaseConversionPharseException( rootTag, tag,  nameAttrName, nameAttrValue, ATTR_STORED_BASE_ELEMENT_TO_PARAMETER_PATH, element.getAttribute(ATTR_STORED_BASE_ELEMENT_TO_PARAMETER_PATH) );
	    		}
	    	
	    		
	    	}else{
	    		
	    		throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, nameAttrValue, ATTR_STORED_BASE_ELEMENT_TO_PARAMETER_PATH, element.getAttribute(ATTR_STORED_BASE_ELEMENT_TO_PARAMETER_PATH) );	    		
	    	}
	    }	    
	    try{
	    	
	    	this.baseElementDataModel = (BaseElementDataModelAdapter)baseDataModelForFillOut;
	    	
	    }catch(ClassCastException e){

	    	//Nem sikerult az utvonalat megtalalni
	    	throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, nameAttrValue, ATTR_STORED_BASE_ELEMENT_TO_PARAMETER_PATH, element.getAttribute(ATTR_STORED_BASE_ELEMENT_TO_PARAMETER_PATH ), e );
	    }
	    
	}
	
	public static String getStaticName(){
		return NAME;
	}
	
	@Override
	public String getName() {		
		return getStaticName();
	}
		
	public BaseElementDataModelAdapter getBaseElement() {
		return baseElementDataModel;
	}

	@Override
	public void doOperation(WebDriver driver, ParamElementDataModel element, WebElement webElement, ElementProgressInterface elementProgress) throws ElementException {

		//HA SPECIALBASEELEMENT - annak kell lennie
		if( element.getBaseElement() instanceof SpecialBaseElementDataModel ){

			((SpecialBaseElementDataModel)element.getBaseElement()).addParameter( baseElementDataModel.getStoredValue() );
			
		}
		
	}
	
	@Override
	public void setXMLAttribute(Document document, Element element) {		
		Attr attr = document.createAttribute( ATTR_STORED_BASE_ELEMENT_TO_PARAMETER_PATH );
		attr.setValue( baseElementDataModel.getPathTag() );
		element.setAttributeNode( attr );	
	}

	@Override
	public Object clone() {
		
		//Fontos, hogy cloneWithParent() mert szukseges, hogy legyen szuloje
		BaseElementDataModelAdapter baseElementDataModel = (BaseElementDataModelAdapter) this.baseElementDataModel.cloneWithParent();
		
		return new AddStoredElementToParameterOperation(baseElementDataModel);
	}
	
}
