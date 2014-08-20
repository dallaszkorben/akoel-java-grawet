package hu.akoel.grawit.core.datamodel.nodes;

import javax.swing.tree.MutableTreeNode;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.datamodel.ParamDataModelInterface;
import hu.akoel.grawit.core.datamodel.pages.BasePageDataModel;
import hu.akoel.grawit.core.datamodel.pages.ParamPageDataModel;
import hu.akoel.grawit.core.datamodel.roots.BaseRootDataModel;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;
import hu.akoel.grawit.exceptions.XMLPharseException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ParamNodeDataModel extends ParamDataModelInterface{

	private static final long serialVersionUID = -2466202302741284519L;
	
	private static final String TAG_NAME = "node";
	
	private static final String ATTR_NAME = "name";
	private static final String ATTR_DETAILS = "details";
		
	private String name;
	private String details;
	
	public ParamNodeDataModel( String name, String details ){
		super( );
		this.name = name;
		this.details = details;
	}
	
	public ParamNodeDataModel( Element element, BaseRootDataModel baseRootDataModel ) throws XMLPharseException{
		
		if( !element.hasAttribute( ATTR_NAME ) ){
			throw new XMLMissingAttributePharseException( ParamNodeDataModel.getModelType().getName(), ParamNodeDataModel.getTagNameStatic(), ATTR_NAME );			
		}
		String nameString = element.getAttribute( ATTR_NAME );
		this.name = nameString;
		
		if( !element.hasAttribute( ATTR_DETAILS ) ){
			throw new XMLMissingAttributePharseException( ParamNodeDataModel.getModelType().getName(), ParamNodeDataModel.getTagNameStatic(), ATTR_DETAILS );			
		}		
		String detailsString = element.getAttribute( ATTR_DETAILS );		
		this.details = detailsString;
		
		NodeList nodelist = element.getChildNodes();
		for( int i = 0; i < nodelist.getLength(); i++ ){
			Node node = nodelist.item( i );
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element baseElement = (Element)node;
				
				//Ha BASEPAGE van alatta
				if( baseElement.getTagName().equals( ParamPageDataModel.getTagNameStatic() )){
					this.add(new ParamPageDataModel(baseElement, baseRootDataModel) );
				
				//Ha ujabb BASENODE van alatta
				}else if( baseElement.getTagName().equals( ParamNodeDataModel.getTagNameStatic() )){
					this.add(new ParamNodeDataModel(baseElement, baseRootDataModel ) );
				}
			}
		}
	}

	public static String getTagNameStatic(){
		return TAG_NAME;
	}

	@Override
	public String getTagName() {
		return getTagNameStatic();
	}

	@Override
	public String getIDName() {
		return ATTR_NAME;
	}
	
	@Override
	public String getIDValue(){
		return getName();
	}
	
	@Override
	public void add(ParamDataModelInterface node) {
		super.add( (MutableTreeNode)node );
	}
	
	public String getTypeToShow(){
		return CommonOperations.getTranslation( "tree.nodetype.node");
	}
	
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
		Element nodeElement = document.createElement("node");
		attr = document.createAttribute("name");
		attr.setValue( getName() );
		nodeElement.setAttributeNode(attr);	
		
		attr = document.createAttribute("details");
		attr.setValue( getDetails() );
		nodeElement.setAttributeNode(attr);	
	
		int childrens = this.getChildCount();
		for( int i = 0; i < childrens; i++ ){
			
			Object object = this.getChildAt( i );
			
			if( !object.equals(this) && object instanceof ParamDataModelInterface ){
				
				Element element = ((ParamDataModelInterface)object).getXMLElement( document );
				nodeElement.appendChild( element );		    		
		    	
			}
		}
			
		return nodeElement;		
	}

}
