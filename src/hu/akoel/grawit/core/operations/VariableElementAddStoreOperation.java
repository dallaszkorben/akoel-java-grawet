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
import hu.akoel.grawit.core.treenodedatamodel.BaseDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.BaseElementDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseCollectorDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseFolderDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.NormalBaseElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.ScriptBaseElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.VariableBaseElementDataModel;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.exceptions.ElementException;
import hu.akoel.grawit.exceptions.XMLBaseConversionPharseException;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;
import hu.akoel.grawit.gui.interfaces.progress.ElementProgressInterface;

public class VariableElementAddStoreOperation extends ElementOperationAdapter{
	
	private static final String NAME = "ADDSTORETOVARIABLE";	
	private static final String ADD_STORED_PATH = "storedbaseelementpath";
	
	//--- Data model
	private BaseElementDataModelAdapter baseElementDataModel;
	//---
	
	public VariableElementAddStoreOperation( BaseElementDataModelAdapter baseElementDataModel ){
		this.baseElementDataModel = baseElementDataModel;
	}

	public VariableElementAddStoreOperation( Element element, BaseRootDataModel baseRootDataModel, Tag rootTag, Tag tag, String nameAttrName, String nameAttrValue ) throws XMLBaseConversionPharseException, XMLMissingAttributePharseException{		
		
		BaseDataModelAdapter baseDataModelForAddToParameters = baseRootDataModel;
		
		if( !element.hasAttribute( ADD_STORED_PATH ) ){
			throw new XMLMissingAttributePharseException( rootTag, tag, ADD_STORED_PATH );		
		}	
		String baseElementPathString = element.getAttribute(ADD_STORED_PATH);				
		baseElementPathString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + baseElementPathString;  
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
	    DocumentBuilder builder;
	    Document document = null;
	    try{  
	        builder = factory.newDocumentBuilder();  
	        document = builder.parse( new InputSource( new StringReader( baseElementPathString ) ) );  
	    } catch (Exception e) {  
	    
	    	//Nem sikerult az atalakitas
	    	throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, nameAttrValue, ADD_STORED_PATH, element.getAttribute(ADD_STORED_PATH), e );
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
	    		baseDataModelForAddToParameters = (BaseDataModelAdapter) CommonOperations.getDataModelByNameInLevel( baseDataModelForAddToParameters, Tag.BASEFOLDER, attrName );

	    		if( null == baseDataModelForAddToParameters ){

	    			throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, nameAttrValue, ADD_STORED_PATH, element.getAttribute(ADD_STORED_PATH) );
	    		}
	    	
	    		
	    	//HA BASEELEMENT
	    	}else if( tagName.equals( BaseElementDataModelAdapter.TAG.getName() ) ){	
	    		
	    		attrName = actualElement.getAttribute(NormalBaseElementDataModel.ATTR_NAME);
				
				baseDataModelForAddToParameters = (BaseDataModelAdapter) CommonOperations.getDataModelByNameInLevel( baseDataModelForAddToParameters, Tag.BASEELEMENT, attrName );
				if( null == baseDataModelForAddToParameters ){

	    			throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, nameAttrValue, ADD_STORED_PATH, element.getAttribute(ADD_STORED_PATH) );
	    		}
				
	    	//Ha BASEPAGE
	    	}else if( tagName.equals( BaseCollectorDataModel.TAG.getName() ) ){
	    		attrName = actualElement.getAttribute(BaseCollectorDataModel.ATTR_NAME);
	    		baseDataModelForAddToParameters = (BaseDataModelAdapter) CommonOperations.getDataModelByNameInLevel( baseDataModelForAddToParameters, Tag.BASECOLLECTOR, attrName );

	    		if( null == baseDataModelForAddToParameters ){

	    			throw new XMLBaseConversionPharseException( rootTag, tag,  nameAttrName, nameAttrValue, ADD_STORED_PATH, element.getAttribute(ADD_STORED_PATH) );
	    		}
	    	
	    		
	    	}else{
	    		
	    		throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, nameAttrValue, ADD_STORED_PATH, element.getAttribute(ADD_STORED_PATH) );	    		
	    	}
	    }	    
	    try{
	    	
	    	this.baseElementDataModel = (BaseElementDataModelAdapter)baseDataModelForAddToParameters;
	    	
	    }catch(ClassCastException e){

	    	//Nem sikerult az utvonalat megtalalni
	    	throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, nameAttrValue, ADD_STORED_PATH, element.getAttribute(ADD_STORED_PATH ), e );
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
	public void doOperation(WebDriver driver, BaseElementDataModelAdapter baseElement, WebElement webElement, ElementProgressInterface elementProgress) throws ElementException {

		//HA VARIABLEBASEELEMENT - annak kell lennie
		if( baseElement instanceof VariableBaseElementDataModel ){

			(( VariableBaseElementDataModel)baseElement).setValue( baseElementDataModel.getStoredValue() );
			
		}
		
	}
	
	@Override
	public void setXMLAttribute(Document document, Element element) {		
		Attr attr = document.createAttribute( ADD_STORED_PATH );
		attr.setValue( baseElementDataModel.getPathTag() );
		element.setAttributeNode( attr );	
	}

	@Override
	public Object clone() {
	
		return new VariableElementAddStoreOperation(baseElementDataModel);
	}
		
	@Override
	public String getOperationToString() {		
		return "AddElementStorageToVariableElement()";
	}
}
