package hu.akoel.grawit.core.treenodedatamodel.elements;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.datamodel.VariableTypeDataModel;
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
		elementParameter.setName( nameString );
		
		
/*		//Operation
		if( !element.hasAttribute( ATTR_OPERATION ) ){
			throw new XMLMissingAttributePharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_OPERATION );			
		}
		String operatorString = element.getAttribute( ATTR_OPERATION );
		if( Operation.BUTTON.name().equals(operatorString ) ){
			this.elementOperation = new ButtonOperation();
		}else if( Operation.CHECKBOX.name().equals(operatorString) ){
			this.elementOperation = new CheckboxOperation();
		}else if( Operation.FIELD.name().equals( operatorString ) ){
			this.elementOperation = new FieldOperation( new StringParameter("", ""));
		}else if( Operation.LINK.name().equals( operatorString ) ){
			this.elementOperation = new LinkOperation();
		}else if( Operation.RADIOBUTTON.name().equals( operatorString ) ){
			this.elementOperation = new RadioButtonOperation();
		}else{
			throw new XMLWrongAttributePharseException(getRootTag(), TAG, ATTR_NAME, getName(), ATTR_OPERATION, operatorString );
		}
*/
		//BaseElement
/*		if( !element.hasAttribute( ATTR_BASE_ELEMENT_PATH ) ){
			throw new XMLMissingAttributePharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_BASE_ELEMENT_PATH );			
		}	
		String paramElementPathString = element.getAttribute(ATTR_BASE_ELEMENT_PATH);				
		paramElementPathString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + paramElementPathString;  
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
	    DocumentBuilder builder;
	    Document document = null;
	    try  
	    {  
	        builder = factory.newDocumentBuilder();  
	        document = builder.parse( new InputSource( new StringReader( paramElementPathString ) ) );  
	    } catch (Exception e) {  
	    
	    	//Nem sikerult az atalakitas
	    	throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_BASE_ELEMENT_PATH, element.getAttribute(ATTR_BASE_ELEMENT_PATH), e );
	    }	    
*/			    
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
