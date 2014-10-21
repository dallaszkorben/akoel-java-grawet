package hu.akoel.grawit.core.treenodedatamodel.variable;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.tree.MutableTreeNode;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.VariableDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseRootDataModel;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;
import hu.akoel.grawit.exceptions.XMLPharseException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class VariableNodeDataModel extends VariableDataModelAdapter{

	private static final long serialVersionUID = -5125611897338677880L;
	
	public static final Tag TAG = Tag.VARIABLENODE;
	
	public static final String ATTR_DETAILS = "details";
	
	private String name;
	private String details;
	
	public VariableNodeDataModel( String name, String details ){
		super( );
		this.name = name;
		this.details = details;
	}
	
	/**
	 * XML alapjan legyartja a VARIABLENODE-ot es az alatta elofordulo 
	 * VARIABLENODE-okat, illetve VARIABLEPAGE-eket
	 * 
	 * @param element
	 * @throws XMLMissingAttributePharseException 
	 */
	public VariableNodeDataModel( Element element, BaseRootDataModel baseRootDataModel ) throws XMLPharseException{
		
		if( !element.hasAttribute( ATTR_NAME ) ){
			throw new XMLMissingAttributePharseException( VariableNodeDataModel.getRootTag(), Tag.VARIABLENODE, ATTR_NAME );			
		}
		String nameString = element.getAttribute( ATTR_NAME );
		this.name = nameString;
		
		if( !element.hasAttribute( ATTR_DETAILS ) ){
			throw new XMLMissingAttributePharseException( VariableNodeDataModel.getRootTag(), Tag.VARIABLENODE, ATTR_NAME, getName(), ATTR_DETAILS );			
		}		
		String detailsString = element.getAttribute( ATTR_DETAILS );		
		this.details = detailsString;
		
		NodeList nodelist = element.getChildNodes();
		for( int i = 0; i < nodelist.getLength(); i++ ){
			Node node = nodelist.item( i );
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element variableElement = (Element)node;
				
				//Ha VARIABLEELEMENT van alatta
				if( variableElement.getTagName().equals( Tag.VARIABLEELEMENT.getName() )){
					this.add(new VariableElementDataModel(variableElement, baseRootDataModel ));
				
				//Ha ujabb VARIABLENODE van alatta
				}else if( variableElement.getTagName().equals( Tag.VARIABLENODE.getName() )){
					this.add(new VariableNodeDataModel(variableElement, baseRootDataModel ));
				}
			}
		}
	}
	
	@Override
	public Tag getTag() {
		return TAG;
	}

	@Override
	public void add(VariableDataModelAdapter node) {
		super.add( (MutableTreeNode)node );
	}
	
	public static String  getModelNameToShowStatic(){
		return CommonOperations.getTranslation( "tree.nodetype.variable.node");
	}
	
	@Override
	public String getNodeTypeToShow(){
		return getModelNameToShowStatic();
	}
	
	@Override
	public String getName(){
		return name;
	}
	
	public String getDetails(){
		return details;
	}
	
	public void setDetails( String details ){
		this.details = details;
	}
	
	public void setName( String name ){
		this.name = name;
	}
	
	public String toString(){
		return name;
	}

	@Override
	public Element getXMLElement(Document document) {
		Attr attr;
		
		//Node element
		Element nodeElement = document.createElement( VariableNodeDataModel.this.getTag().getName() );
		attr = document.createAttribute( ATTR_NAME );
		attr.setValue( getName() );
		nodeElement.setAttributeNode(attr);	
		
		attr = document.createAttribute( ATTR_DETAILS );
		attr.setValue( getDetails() );
		nodeElement.setAttributeNode(attr);	
	
		int childrens = this.getChildCount();
		for( int i = 0; i < childrens; i++ ){
			
			Object object = this.getChildAt( i );
			
			if( !object.equals(this) && object instanceof VariableDataModelAdapter ){
				
				Element element = ((VariableDataModelAdapter)object).getXMLElement( document );
				nodeElement.appendChild( element );		    		
		    	
			}
		}
	
		return nodeElement;		
	}

	@Override
	public Object clone(){
		
		//Leklonozza a NODE-ot
		VariableNodeDataModel cloned = (VariableNodeDataModel)super.clone();
	
		//Ha vannak gyerekei (NODE vagy ELEMENT)
		if( null != this.children ){
			
			//Akkor azokat is leklonozza
			cloned.children = new Vector<>();
			
			for( Object o : this.children ){
				
				if( o instanceof VariableDataModelAdapter ){
					cloned.children.add(((VariableDataModelAdapter)o).clone());
				}
			}
		}
		
		//Es a valtozokat is leklonozza
		cloned.name = new String( this.name );
		cloned.details = this.details;
		
		return cloned;
		
	}
	
}
