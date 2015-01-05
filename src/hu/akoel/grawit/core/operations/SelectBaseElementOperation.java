package hu.akoel.grawit.core.operations;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.BaseDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.BaseElementDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseNodeDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.BasePageDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.NormalBaseElementDataModel;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.enums.list.ListSelectionByListEnum;
import hu.akoel.grawit.exceptions.XMLBaseConversionPharseException;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;

public class SelectBaseElementOperation extends SelectOperationAdapter{
	
	private static final String NAME = "SELECTBASEELEMENT";
	private static final String ATTR_SELECTION_BY = "selectionby";
	private static final String ATTR_SELECT_BASE_ELEMENT_PATH = "selectbaseelementpath";
	
	//--- Data model
	private BaseElementDataModelAdapter baseElementDataModel;
	private ListSelectionByListEnum selectionBy;
	//----
	
	public SelectBaseElementOperation( BaseElementDataModelAdapter baseElementDataModel, ListSelectionByListEnum selectionBy ){
		this.baseElementDataModel = baseElementDataModel;
		this.selectionBy = selectionBy;
	}
	
	public SelectBaseElementOperation( Element element, BaseRootDataModel baseRootDataModel, Tag rootTag, Tag tag, String nameAttrName, String nameAttrValue ) throws XMLBaseConversionPharseException, XMLMissingAttributePharseException{
		
		BaseDataModelAdapter baseDataModelForSelect = baseRootDataModel;
		
		//SELECTION BY
		if( !element.hasAttribute( ATTR_SELECTION_BY ) ){
			throw new XMLMissingAttributePharseException( rootTag, tag, ATTR_SELECTION_BY );			
		}
		String stringSelectionBy = element.getAttribute( ATTR_SELECTION_BY );		
		if( stringSelectionBy.equals( ListSelectionByListEnum.BYINDEX.name() ) ){
			selectionBy = ListSelectionByListEnum.BYINDEX;
		}else if( stringSelectionBy.equals( ListSelectionByListEnum.BYVALUE.name() ) ){
			selectionBy = ListSelectionByListEnum.BYVALUE;
		}else if( stringSelectionBy.equals( ListSelectionByListEnum.BYVISIBLETEXT.name() ) ){
			selectionBy = ListSelectionByListEnum.BYVISIBLETEXT;
		}else{
			selectionBy = ListSelectionByListEnum.BYVISIBLETEXT;
		}		
		
		//SELECT BASE ELEMENT					
		if( !element.hasAttribute( ATTR_SELECT_BASE_ELEMENT_PATH ) ){
			throw new XMLMissingAttributePharseException( rootTag, tag, ATTR_SELECT_BASE_ELEMENT_PATH );		
		}	
		String baseElementPathString = element.getAttribute( ATTR_SELECT_BASE_ELEMENT_PATH );				
		baseElementPathString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + baseElementPathString;  
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
	    DocumentBuilder builder;
	    Document document = null;
	    try{  
	        builder = factory.newDocumentBuilder();  
	        document = builder.parse( new InputSource( new StringReader( baseElementPathString ) ) );  
	    } catch (Exception e) {  
	    
	    	//Nem sikerult az atalakitas
	    	throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, nameAttrValue, ATTR_SELECT_BASE_ELEMENT_PATH, element.getAttribute(ATTR_SELECT_BASE_ELEMENT_PATH), e );
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
	    		baseDataModelForSelect = (BaseDataModelAdapter) CommonOperations.getDataModelByNameInLevel( baseDataModelForSelect, Tag.BASENODE, attrName );

	    		if( null == baseDataModelForSelect ){

	    			throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, nameAttrValue, ATTR_SELECT_BASE_ELEMENT_PATH, element.getAttribute(ATTR_SELECT_BASE_ELEMENT_PATH) );
	    		}

	    		//HA BASEELEMENT
		    	}else if( tagName.equals( BaseElementDataModelAdapter.TAG.getName() ) ){	
			    		
		    		attrName = actualElement.getAttribute(NormalBaseElementDataModel.ATTR_NAME);
			    		
		    		baseDataModelForSelect = (BaseDataModelAdapter) CommonOperations.getDataModelByNameInLevel( baseDataModelForSelect, Tag.BASEELEMENT, attrName );
		    		if( null == baseDataModelForSelect ){

		    			throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, nameAttrValue, ATTR_SELECT_BASE_ELEMENT_PATH, element.getAttribute(ATTR_SELECT_BASE_ELEMENT_PATH) );
		    		}
		    		
/*	    	//Ha NORMALBASEELEMENT
	    	}else if( tagName.equals( NormalBaseElementDataModel.TAG.getName() ) ){
	    		attrName = actualElement.getAttribute(NormalBaseElementDataModel.ATTR_NAME);
	    		baseDataModelForSelect = (BaseDataModelAdapter) CommonOperations.getDataModelByNameInLevel( baseDataModelForSelect, Tag.NORMALBASEELEMENT, attrName );
	
	    		if( null == baseDataModelForSelect ){

	    			throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, nameAttrValue, ATTR_SELECT_BASE_ELEMENT_PATH, element.getAttribute(ATTR_SELECT_BASE_ELEMENT_PATH) );
	    		}

	    	//Ha SPECIALBASEELEMENT
	    	}else if( tagName.equals( SpecialBaseElementDataModel.TAG.getName() ) ){
	    		attrName = actualElement.getAttribute(SpecialBaseElementDataModel.ATTR_NAME);
	    		baseDataModelForSelect = (BaseDataModelAdapter) CommonOperations.getDataModelByNameInLevel( baseDataModelForSelect, Tag.SPECIALBASEELEMENT, attrName );
		
	    		if( null == baseDataModelForSelect ){

	    			throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, nameAttrValue, ATTR_SELECT_BASE_ELEMENT_PATH, element.getAttribute(ATTR_SELECT_BASE_ELEMENT_PATH) );
	    		}
*/	    	
	    	//Ha BASEPAGE
	    	}else if( tagName.equals( BasePageDataModel.TAG.getName() ) ){
	    		attrName = actualElement.getAttribute(BasePageDataModel.ATTR_NAME);
	    		baseDataModelForSelect = (BaseDataModelAdapter) CommonOperations.getDataModelByNameInLevel( baseDataModelForSelect, Tag.BASEPAGE, attrName );

	    		if( null == baseDataModelForSelect ){

	    			throw new XMLBaseConversionPharseException( rootTag, tag,  nameAttrName, nameAttrValue, ATTR_SELECT_BASE_ELEMENT_PATH, element.getAttribute(ATTR_SELECT_BASE_ELEMENT_PATH) );
	    		}
	    	
	    		
	    	}else{
	    		
	    		throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, nameAttrValue, ATTR_SELECT_BASE_ELEMENT_PATH, element.getAttribute(ATTR_SELECT_BASE_ELEMENT_PATH) );	    		
	    	}
	    }	    
	    try{
	    	
	    	this.baseElementDataModel = (BaseElementDataModelAdapter)baseDataModelForSelect;
	    	
	    }catch(ClassCastException e){

	    	//Nem sikerult az utvonalat megtalalni
	    	throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, nameAttrValue, ATTR_SELECT_BASE_ELEMENT_PATH, element.getAttribute( ATTR_SELECT_BASE_ELEMENT_PATH ), e );
	    }
	}
	
	@Override
	public ListSelectionByListEnum getSelectionBy() {
		return selectionBy;
	}

	@Override
	public String getStringToSelection() {	
		return baseElementDataModel.getStoredValue();
	}

	public BaseElementDataModelAdapter getBaseElement() {
		return baseElementDataModel;
	}
	
	public static String getStaticName(){
		return NAME;
	}
	
	@Override
	public String getName() {		
		return getStaticName();
	}

	@Override
	public void setXMLAttribute(Document document, Element element) {
		Attr attr = document.createAttribute( ATTR_SELECT_BASE_ELEMENT_PATH );
		attr.setValue( baseElementDataModel.getPathTag() );
		element.setAttributeNode( attr );		
		
		attr = document.createAttribute( ATTR_SELECTION_BY );
		attr.setValue( selectionBy.name() );
		element.setAttributeNode( attr );	
	}

	@Override
	public Object clone() {
		
		//Fontos, hogy cloneWithParent() mert szukseges, hogy legyen szuloje
		BaseElementDataModelAdapter baseElementDataModel = (BaseElementDataModelAdapter) this.baseElementDataModel.cloneWithParent();
		ListSelectionByListEnum selectionBy = this.selectionBy;
		
		return new SelectBaseElementOperation(baseElementDataModel, selectionBy);
	}


}
