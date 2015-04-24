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
import hu.akoel.grawit.core.treenodedatamodel.base.BaseCollectorDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseElementDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseFolderDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.NormalBaseElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.ScriptBaseElementDataModel;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.exceptions.ElementException;
import hu.akoel.grawit.exceptions.XMLBaseConversionPharseException;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;
import hu.akoel.grawit.gui.interfaces.progress.ElementProgressInterface;

public class ScriptElementAddStoreToParametersOperation extends ScriptOperationAdapter{
	
	private static final String NAME = "ADDSTORETOPARAMETERS";	
	private static final String ADD_STORED_TO_PARAMETERS_PATH = "storedbaseelementtoparameterspath";
	
	//--- Data model
	private BaseElementDataModelAdapter baseElementDataModel;
	//---
	
	public ScriptElementAddStoreToParametersOperation( BaseElementDataModelAdapter baseElementDataModel ){
		this.baseElementDataModel = baseElementDataModel;
	}

	public ScriptElementAddStoreToParametersOperation( Element element, BaseRootDataModel baseRootDataModel, Tag rootTag, Tag tag, String nameAttrName, String nameAttrValue ) throws XMLBaseConversionPharseException, XMLMissingAttributePharseException{		
		
		BaseDataModelAdapter baseDataModelForAddToParameters = baseRootDataModel;
		
		if( !element.hasAttribute( ADD_STORED_TO_PARAMETERS_PATH ) ){
			throw new XMLMissingAttributePharseException( rootTag, tag, ADD_STORED_TO_PARAMETERS_PATH );		
		}	
		String baseElementPathString = element.getAttribute(ADD_STORED_TO_PARAMETERS_PATH);				
		baseElementPathString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + baseElementPathString;  
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
	    DocumentBuilder builder;
	    Document document = null;
	    try{  
	        builder = factory.newDocumentBuilder();  
	        document = builder.parse( new InputSource( new StringReader( baseElementPathString ) ) );  
	    } catch (Exception e) {  
	    
	    	//Nem sikerult az atalakitas
	    	throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, nameAttrValue, ADD_STORED_TO_PARAMETERS_PATH, element.getAttribute(ADD_STORED_TO_PARAMETERS_PATH), e );
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

	    			throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, nameAttrValue, ADD_STORED_TO_PARAMETERS_PATH, element.getAttribute(ADD_STORED_TO_PARAMETERS_PATH) );
	    		}
	    	
	    		
	    	//HA BASEELEMENT
	    	}else if( tagName.equals( BaseElementDataModelAdapter.TAG.getName() ) ){	
	    		
	    		attrName = actualElement.getAttribute(NormalBaseElementDataModel.ATTR_NAME);
	    		
	    		//element type             
/*				if( !actualElement.hasAttribute( BaseElementDataModelAdapter.ATTR_ELEMENT_TYPE ) ){
					throw new XMLMissingAttributePharseException( rootTag, tag, nameAttrName, nameAttrValue, BaseElementDataModelAdapter.ATTR_ELEMENT_TYPE );			
				}
				String elementTypeString = actualElement.getAttribute( BaseElementDataModelAdapter.ATTR_ELEMENT_TYPE );
*/				
				baseDataModelForAddToParameters = (BaseDataModelAdapter) CommonOperations.getDataModelByNameInLevel( baseDataModelForAddToParameters, Tag.BASEELEMENT, attrName );
				if( null == baseDataModelForAddToParameters ){

	    			throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, nameAttrValue, ADD_STORED_TO_PARAMETERS_PATH, element.getAttribute(ADD_STORED_TO_PARAMETERS_PATH) );
	    		}
				
/*				//SPECIAL
				if( elementTypeString.equals( ElementTypeListEnum.SPECIAL.name() )){								
					
				//FIELD, TEXT, LINK, LIST, BUTTON, RADIOBUTTON, CHECKBOX
				}else{											
		    		
				}
*/	    		
/*	    	//Ha NORMALBASEELEMENT
	    	}else if( tagName.equals( NormalBaseElementDataModel.TAG.getName() ) ){
	    		attrName = actualElement.getAttribute(NormalBaseElementDataModel.ATTR_NAME);
	    		baseDataModelForFillOut = (BaseDataModelAdapter) CommonOperations.getDataModelByNameInLevel( baseDataModelForFillOut, Tag.NORMALBASEELEMENT, attrName );
	
	    		if( null == baseDataModelForFillOut ){

	    			throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, nameAttrValue, ADD_STORED_TO_PARAMETERS_PATH, element.getAttribute(ADD_STORED_TO_PARAMETERS_PATH) );
	    		}

	    	//Ha SPECIALBASEELEMENT
	    	}else if( tagName.equals( SpecialBaseElementDataModel.TAG.getName() ) ){
	    		attrName = actualElement.getAttribute(SpecialBaseElementDataModel.ATTR_NAME);
	    		baseDataModelForFillOut = (BaseDataModelAdapter) CommonOperations.getDataModelByNameInLevel( baseDataModelForFillOut, Tag.SPECIALBASEELEMENT, attrName );
		
	    		if( null == baseDataModelForFillOut ){

	    			throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, nameAttrValue, ADD_STORED_TO_PARAMETERS_PATH, element.getAttribute(ADD_STORED_TO_PARAMETERS_PATH) );
	    		}	    		
*/	    	
	    	//Ha BASEPAGE
	    	}else if( tagName.equals( BaseCollectorDataModel.TAG.getName() ) ){
	    		attrName = actualElement.getAttribute(BaseCollectorDataModel.ATTR_NAME);
	    		baseDataModelForAddToParameters = (BaseDataModelAdapter) CommonOperations.getDataModelByNameInLevel( baseDataModelForAddToParameters, Tag.BASECOLLECTOR, attrName );

	    		if( null == baseDataModelForAddToParameters ){

	    			throw new XMLBaseConversionPharseException( rootTag, tag,  nameAttrName, nameAttrValue, ADD_STORED_TO_PARAMETERS_PATH, element.getAttribute(ADD_STORED_TO_PARAMETERS_PATH) );
	    		}
	    	
	    		
	    	}else{
	    		
	    		throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, nameAttrValue, ADD_STORED_TO_PARAMETERS_PATH, element.getAttribute(ADD_STORED_TO_PARAMETERS_PATH) );	    		
	    	}
	    }	    
	    try{
	    	
	    	this.baseElementDataModel = (BaseElementDataModelAdapter)baseDataModelForAddToParameters;
	    	
	    }catch(ClassCastException e){

	    	//Nem sikerult az utvonalat megtalalni
	    	throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, nameAttrValue, ADD_STORED_TO_PARAMETERS_PATH, element.getAttribute(ADD_STORED_TO_PARAMETERS_PATH ), e );
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
	public void doOperation(WebDriver driver, BaseElementDataModelAdapter baseElement, WebElement webElement, ElementProgressInterface elementProgress, String tab ) throws ElementException {

		//HA SPECIALBASEELEMENT - annak kell lennie
		if( baseElement instanceof ScriptBaseElementDataModel ){

			if( null != elementProgress ){
				outputScripClass(driver, baseElement, webElement, elementProgress, tab );				
				elementProgress.printSource( tab + baseElement.getNameAsScript() + ".addParameter( " + CommonOperations.STORAGE_NAME_PREFIX + String.valueOf( baseElement.hashCode() ) + " ); //" + baseElementDataModel.getStoredValue() );
				elementProgress.printSource( "" );
			}			

			((ScriptBaseElementDataModel)baseElement).addParameter( baseElementDataModel.getStoredValue() );
			
		}
		
	}
	
	@Override
	public void setXMLAttribute(Document document, Element element) {		
		Attr attr = document.createAttribute( ADD_STORED_TO_PARAMETERS_PATH );
		attr.setValue( baseElementDataModel.getPathTag() );
		element.setAttributeNode( attr );	
	}

	@Override
	public Object clone() {
	
		return new ScriptElementAddStoreToParametersOperation(baseElementDataModel);
	}
		
	@Override
	public String getOperationNameToString() {		
		return "AddElementStorageToScriptElementParameter()";
	}
}
