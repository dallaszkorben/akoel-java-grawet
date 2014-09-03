package hu.akoel.grawit.core.treenodedatamodel.variable;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.ParamDataModelInterface;
import hu.akoel.grawit.core.treenodedatamodel.VariableDataModelInterface;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamNodeDataModel;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.exceptions.XMLExtraRootTagPharseException;
import hu.akoel.grawit.exceptions.XMLPharseException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class VariableRootDataModel extends VariableNodeDataModel{

	private static final long serialVersionUID = -4193611923372308352L;

	private static final Tag TAG = Tag.VARIABLEROOT;
	
	public static final String ATTR_NAME = "";
	
	public VariableRootDataModel(){
		super( "", "" );
	}
	
	public VariableRootDataModel( Document doc ) throws XMLPharseException{
		super("","");
		
		NodeList nList = doc.getElementsByTagName( TAG.getName() );
		
		//Ha nem pontosan 1 db variableroot tag van, akkor az gaz
		if( nList.getLength() != 1 ){
			
			throw new XMLExtraRootTagPharseException( TAG );
		}
		
		Node variablePageNode = nList.item(0);
		if (variablePageNode.getNodeType() == Node.ELEMENT_NODE) {
			
			NodeList nodeList = variablePageNode.getChildNodes();
			for( int i = 0; i < nodeList.getLength(); i++ ){
			
				Node variableNode = nodeList.item( i );
				
				if (variableNode.getNodeType() == Node.ELEMENT_NODE) {
					Element variableElement = (Element)variableNode;
					
					//Ha ujabb PARAMNODE van alatta
					if( variableElement.getTagName().equals( Tag.VARIABLENODE.getName() ) ){						
						this.add(new VariableNodeDataModel( variableElement ));
					
					//Ha rogton a rootban van elhelyezve egy elem
					}else if( variableElement.getTagName().equals( Tag.VARIABLEELEMENT.getName() ) ){
						this.add(new VariableElementDataModel(variableElement));
					}
				}
			}
		}		
	}
	
	@Override
	public String getName(){
		return "Variable Root";
	}

	@Override
	public Tag getTag(){
		return TAG;
	}

	@Override
	public String getModelNameToShow(){
		return CommonOperations.getTranslation( "tree.nodetype.variable.root");
	}
	
	@Override
	public Element getXMLElement(Document document) {
		
		//ParamPageElement
		Element paramPageElement = document.createElement( TAG.getName() );

		int childrens = this.getChildCount();
		for( int i = 0; i < childrens; i++ ){
			
			Object object = this.getChildAt( i );
			
			if( !object.equals(this) && object instanceof VariableDataModelInterface ){
				
				Element element = ((VariableDataModelInterface)object).getXMLElement( document );
				paramPageElement.appendChild( element );		    		
		    	
			}
		}
		
		return paramPageElement;		
	}
	
}
