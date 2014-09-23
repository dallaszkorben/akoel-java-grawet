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
import hu.akoel.grawit.core.treenodedatamodel.VariableDataModelInterface;
import hu.akoel.grawit.core.treenodedatamodel.variable.VariableElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.variable.VariableNodeDataModel;
import hu.akoel.grawit.core.treenodedatamodel.variable.VariableRootDataModel;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.enums.list.ListSelectionByListEnum;
import hu.akoel.grawit.exceptions.XMLBaseConversionPharseException;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;

public class SelectVariableElementOperation extends SelectOperationAdapter{
	
	private static final String NAME = "SELECTVARIABLEELEMENT";
	private static final String ATTR_SELECTION_BY = "selectionby";
	private static final String ATTR_SELECT_VARIABLE_ELEMENT_PATH = "selectvariableelementpath";
//	private static final String ATTR_SELECT_BASE_ELEMENT_PATH = "selectbaseelementpath";
//	private static final String ATTR_SELECT_STRING = "selectstring";
	
	//--- Data model
	private VariableElementDataModel variableElementDataModel;
	private ListSelectionByListEnum selectionBy;
	//----
	
	public SelectVariableElementOperation( VariableElementDataModel variableElementDataModel, ListSelectionByListEnum selectionBy ){
		this.variableElementDataModel = variableElementDataModel;
		this.selectionBy = selectionBy;
	}
	
	public SelectVariableElementOperation( Element element, VariableRootDataModel variableRootDataModel, Tag rootTag, Tag tag, String nameAttrName, String nameAttrValue ) throws XMLBaseConversionPharseException, XMLMissingAttributePharseException{
		
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
		
		//SELECT VARIABLE ELEMENT
		if( !element.hasAttribute( ATTR_SELECT_VARIABLE_ELEMENT_PATH ) ){
			throw new XMLMissingAttributePharseException( rootTag, tag, ATTR_SELECT_VARIABLE_ELEMENT_PATH );	
		}
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
	
	@Override
	public ListSelectionByListEnum getSelectionBy() {
		return selectionBy;
	}

	@Override
	public String getStringToSelection() {	
		return variableElementDataModel.getValue();
	}

	public VariableElementDataModel getVariableElement() {
		return variableElementDataModel;
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
		Attr attr = document.createAttribute( ATTR_SELECT_VARIABLE_ELEMENT_PATH );
		attr.setValue( variableElementDataModel.getPathTag() );
		element.setAttributeNode( attr );		
		
		attr = document.createAttribute( ATTR_SELECTION_BY );
		attr.setValue( selectionBy.name() );
		element.setAttributeNode( attr );	
	}


}
