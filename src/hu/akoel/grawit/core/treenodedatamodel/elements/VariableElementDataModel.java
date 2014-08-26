package hu.akoel.grawit.core.treenodedatamodel.elements;

import java.util.ArrayList;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.gargoylesoftware.htmlunit.javascript.host.NodeList;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.parameter.ElementParameter;
import hu.akoel.grawit.core.treenodedatamodel.VariableDataModelInterface;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.enums.VariableType;
import hu.akoel.grawit.exceptions.XMLBaseConversionPharseException;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;
import hu.akoel.grawit.exceptions.XMLMissingTagPharseException;

public class VariableElementDataModel extends VariableDataModelInterface implements ElementParameter{

	private static final long serialVersionUID = 598870035128239461L;
	
	private static Tag TAG = Tag.VARIABLEELEMENT; 				//element
	private static Tag TAG_PARAMETER = Tag.VARIABLEPARAMETER;	//parameter
	
	private static String ATTR_TYPE = "type";
	private static String ATTR_VALUE = "value";
	
	private String name;
	private VariableType type;
	private ArrayList<Object> parameters;	
	
	public VariableElementDataModel( String name, VariableType type, ArrayList<Object> parameters){
		this.name = name;
		this.type = type;
		this.parameters = parameters;
	}
	
	public VariableElementDataModel( Element element ) throws XMLMissingAttributePharseException, XMLMissingTagPharseException{
		
		//name
		if( !element.hasAttribute( ATTR_NAME ) ){
			throw new XMLMissingAttributePharseException( getRootTag(), TAG, ATTR_NAME );			
		}
		name = element.getAttribute( ATTR_NAME );		

		//type
		if( !element.hasAttribute( ATTR_TYPE ) ){
			throw new XMLMissingAttributePharseException( getRootTag(), TAG, ATTR_TYPE );			
		}
		if( VariableType.STRING_PARAMETER.name().equals( element.getAttribute( ATTR_TYPE )) ){
			type = VariableType.STRING_PARAMETER;
		
		}else if( VariableType.RANDOM_STRING_PARAMETER.name().equals( element.getAttribute( ATTR_TYPE )) ){
			type = VariableType.RANDOM_STRING_PARAMETER;
			
		}else if( VariableType.RANDOM_INTEGER_PARAMETER.name().equals( element.getAttribute( ATTR_TYPE )) ){
			type = VariableType.RANDOM_INTEGER_PARAMETER;
			
		}else if( VariableType.RANDOM_DECIMAL_PARAMETER.name().equals( element.getAttribute( ATTR_TYPE )) ){
			type = VariableType.RANDOM_DECIMAL_PARAMETER;
			
		}
		
		parameters = new ArrayList<Object>();
		NodeList paramNodeList = (NodeList) element.getChildNodes();
		int nodeListLength = paramNodeList.getLength();
		for( int i = 0; i < nodeListLength; i++ ){
			Element e = (Element)paramNodeList.item( i );
			if( !e.getTagName().equals( TAG_PARAMETER )){
				throw new XMLMissingTagPharseException(getRootTag(), getTag(), name, TAG_PARAMETER );
			}
			if( !e.hasAttribute( ATTR_VALUE ) ){
				throw new XMLMissingAttributePharseException( getRootTag(), TAG_PARAMETER, ATTR_VALUE );			
			}
			
			//TODO na itt kell atalakitani
			e.getAttribute( ATTR_VALUE );		
		}
		
		
	}
	
	@Override
	public String getModelNameToShow() {
		return CommonOperations.getTranslation( "tree.nodetype.paramelement" );
	}

	public static Tag getTagStatic(){
		return TAG;
	}
	
	@Override
	public Tag getTag() {
		return getTagStatic();
	}

	@Override
	public String getValue() {
		return type.getValue(parameters);
	}

		
	@Override
	public String getName() {
		return name;
	}

	@Override
	public Element getXMLElement(Document document) {
		Attr attr;
		
		//
		//Node element
		//
		Element elementElement = document.createElement( TAG.getName() );
		
		//Name
		attr = document.createAttribute( ATTR_NAME );
		attr.setValue( getName() );
		elementElement.setAttributeNode(attr);	
		
		//Type
		attr = document.createAttribute( ATTR_TYPE );
		attr.setValue( type.name() );
		elementElement.setAttributeNode(attr);
		
		for( Object object: parameters ){
			Element parameterElement = document.createElement( TAG_PARAMETER.getName() );
			attr = document.createAttribute( ATTR_VALUE );
			attr.setValue( object.toString() );
			parameterElement.setAttributeNode( attr );
			elementElement.appendChild( parameterElement );
		}

		return elementElement;	
	}



}
