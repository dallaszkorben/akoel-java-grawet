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
import hu.akoel.grawit.core.treenodedatamodel.constant.ConstantDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.constant.ConstantElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.constant.ConstantFolderNodeDataModel;
import hu.akoel.grawit.core.treenodedatamodel.constant.ConstantRootDataModel;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.enums.list.ListSelectionByListEnum;
import hu.akoel.grawit.exceptions.XMLBaseConversionPharseException;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;

public class SelectConstantElementOperation extends SelectOperationAdapter implements HasConstantOperationInterface{
	
	private static final String NAME = "SELECTVARIABLEELEMENT";
	private static final String ATTR_SELECTION_BY = "selectionby";
	private static final String ATTR_SELECT_CONSTANT_ELEMENT_PATH = "selectconstantelementpath";
	
	//--- Data model
	private ConstantElementDataModel constantElementDataModel;
	private ListSelectionByListEnum selectionBy;
	//----
	
	public SelectConstantElementOperation( ConstantElementDataModel constantElementDataModel, ListSelectionByListEnum selectionBy ){
		this.constantElementDataModel = constantElementDataModel;
		this.selectionBy = selectionBy;
	}
	
	public SelectConstantElementOperation( Element element, ConstantRootDataModel constantRootDataModel, Tag rootTag, Tag tag, String nameAttrName, String nameAttrValue ) throws XMLBaseConversionPharseException, XMLMissingAttributePharseException{
		
		ConstantDataModelAdapter constantDataModelForSelect = constantRootDataModel;
		
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
		
		//SELECT CONSTANT ELEMENT
		if( !element.hasAttribute( ATTR_SELECT_CONSTANT_ELEMENT_PATH ) ){
			throw new XMLMissingAttributePharseException( rootTag, tag, ATTR_SELECT_CONSTANT_ELEMENT_PATH );	
		}
		String constantElementPathString = element.getAttribute(ATTR_SELECT_CONSTANT_ELEMENT_PATH);				
		constantElementPathString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + constantElementPathString;  
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
	    DocumentBuilder builder;
	    Document document = null;
	    try{  
	        builder = factory.newDocumentBuilder();  
	        document = builder.parse( new InputSource( new StringReader( constantElementPathString ) ) );  
	    } catch (Exception e) {  
	    
	    	//Nem sikerult az atalakitas
	    	throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, nameAttrValue, ATTR_SELECT_CONSTANT_ELEMENT_PATH, element.getAttribute(ATTR_SELECT_CONSTANT_ELEMENT_PATH), e );
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
	    		constantDataModelForSelect = (ConstantDataModelAdapter) CommonOperations.getDataModelByNameInLevel( constantDataModelForSelect, Tag.CONSTANTFOLDER, attrName );

	    		if( null == constantDataModelForSelect ){

	    			throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, nameAttrValue, ATTR_SELECT_CONSTANT_ELEMENT_PATH, element.getAttribute(ATTR_SELECT_CONSTANT_ELEMENT_PATH) );
	    		}
	    		
	    	//Ha CONSTANTELEMENT
	    	}else if( tagName.equals( ConstantElementDataModel.TAG.getName() ) ){
	    		attrName = actualElement.getAttribute(ConstantElementDataModel.ATTR_NAME);
	    		constantDataModelForSelect = (ConstantDataModelAdapter) CommonOperations.getDataModelByNameInLevel( constantDataModelForSelect, Tag.CONSTANTELEMENT, attrName );
	    		
	    		if( null == constantDataModelForSelect ){

	    			throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, getName(), ATTR_SELECT_CONSTANT_ELEMENT_PATH, element.getAttribute(ATTR_SELECT_CONSTANT_ELEMENT_PATH) );
	    		}
	    		
	    	}else{
	    		
	    		throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, getName(), ATTR_SELECT_CONSTANT_ELEMENT_PATH, element.getAttribute(ATTR_SELECT_CONSTANT_ELEMENT_PATH) );	    		
	    	}
	    }	    
	    try{
	    	
	    	this.constantElementDataModel = (ConstantElementDataModel)constantDataModelForSelect;
	    	
	    }catch(ClassCastException e){

	    	//Nem sikerult az utvonalat megtalalni
	    	throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, nameAttrValue, ATTR_SELECT_CONSTANT_ELEMENT_PATH, element.getAttribute(ATTR_SELECT_CONSTANT_ELEMENT_PATH ), e );
	    }
	}
	
	@Override
	public ListSelectionByListEnum getSelectionBy() {
		return selectionBy;
	}

	@Override
	public String getStringToSelection() {	
		return constantElementDataModel.getValue();
	}

	@Override
	public ConstantElementDataModel getConstantElement() {
		return constantElementDataModel;
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
		Attr attr = document.createAttribute( ATTR_SELECT_CONSTANT_ELEMENT_PATH );
		attr.setValue( constantElementDataModel.getPathTag() );
		element.setAttributeNode( attr );		
		
		attr = document.createAttribute( ATTR_SELECTION_BY );
		attr.setValue( selectionBy.name() );
		element.setAttributeNode( attr );	
	}

	@Override
	public Object clone() {
		
		return new SelectConstantElementOperation(constantElementDataModel, selectionBy);
	}

	@Override
	public String getOperationToString() {		
		return "SelectListElementByConstant()";
	}
}
