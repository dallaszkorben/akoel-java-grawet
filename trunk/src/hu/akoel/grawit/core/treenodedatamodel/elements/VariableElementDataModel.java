package hu.akoel.grawit.core.treenodedatamodel.elements;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.parameter.ElementParameter;
import hu.akoel.grawit.core.treenodedatamodel.VariableDataModelInterface;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;
import hu.akoel.grawit.exceptions.XMLPharseException;

public class VariableElementDataModel extends VariableDataModelInterface{

	private static final long serialVersionUID = 3196835083345855496L;

	private static Tag TAG = Tag.VARIABLEELEMENT;
	
	private static final String ATTR_TYPE = "type";
	
	private ElementParameter elementParameter;
	
	public VariableElementDataModel( ElementParameter elementParameter ){
		this.elementParameter = elementParameter;
	}
	
	public VariableElementDataModel( Element element ) throws XMLPharseException{
	
		//name
		if( !element.hasAttribute( ATTR_NAME ) ){
			throw new XMLMissingAttributePharseException( getRootTag(), TAG, ATTR_NAME );			
		}
		String nameString = element.getAttribute( ATTR_NAME );	
		
		//TODO elementParameter elkeszitese
		elementParameter.setName( nameString );
			    
	}

	public ElementParameter getElementParameter(){
		return elementParameter;
	}

	@Override
	public String getName() {
		return elementParameter.getName();
	}
		
	@Override
	public void add( VariableDataModelInterface node ) {
//		super.add( (MutableTreeNode)node );
	}
	
	public static Tag getTagStatic(){
		return TAG;
	}

	@Override
	public Tag getTag(){
		return getTagStatic();
	}
	
	@Override
	public String getModelNameToShow(){
		return CommonOperations.getTranslation( "tree.nodetype.paramelement" );
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

		//Variable type
		attr = document.createAttribute( ATTR_TYPE );
		attr.setValue( elementParameter.getType().name() );
		elementElement.setAttributeNode(attr);	

		//Variable parameterei
		for( int i = 0; i < elementParameter.getParameterNumber(); i++ ){
			Element element = elementParameter.getXMLElement(document, i);
			elementElement.appendChild( element );		
		}
				
		return elementElement;	
	}


	
}
