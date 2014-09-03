package hu.akoel.grawit.core.treenodedatamodel.base;

import javax.swing.tree.MutableTreeNode;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.BaseDataModelInterface;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;
import hu.akoel.grawit.exceptions.XMLPharseException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class BaseNodeDataModel extends BaseDataModelInterface{

	private static final long serialVersionUID = -5125611897338677880L;
	
	public static final Tag TAG = Tag.BASENODE;
	
//	public static final String ATTR_NAME = "name";
	public static final String ATTR_DETAILS = "details";
	
	private String name;
	private String details;
	
	public BaseNodeDataModel( String name, String details ){
		super( );
		this.name = name;
		this.details = details;
	}
	
	/**
	 * XML alapjan legyartja a BASENODE-ot es az alatta elofordulo 
	 * BASENODE-okat, illetve BASEPAGE-eket
	 * 
	 * @param element
	 * @throws XMLMissingAttributePharseException 
	 */
	public BaseNodeDataModel( Element element ) throws XMLPharseException{
		
		if( !element.hasAttribute( ATTR_NAME ) ){
			throw new XMLMissingAttributePharseException( BaseNodeDataModel.getRootTag(), Tag.BASENODE, ATTR_NAME );			
		}
		String nameString = element.getAttribute( ATTR_NAME );
		this.name = nameString;
		
		if( !element.hasAttribute( ATTR_DETAILS ) ){
			throw new XMLMissingAttributePharseException( BaseNodeDataModel.getRootTag(), Tag.BASENODE, ATTR_NAME, getName(), ATTR_DETAILS );			
		}		
		String detailsString = element.getAttribute( ATTR_DETAILS );		
		this.details = detailsString;
		
		NodeList nodelist = element.getChildNodes();
		for( int i = 0; i < nodelist.getLength(); i++ ){
			Node node = nodelist.item( i );
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element baseElement = (Element)node;
				
				//Ha BASEPAGE van alatta
				//if( baseElement.getTagName().equals( BasePageDataModel.getTagStatic().getName() )){
				if( baseElement.getTagName().equals( Tag.BASEPAGE.getName() )){
					this.add(new BasePageDataModel(baseElement));
				
				//Ha ujabb BASENODE van alatta
				//}else if( baseElement.getTagName().equals( TestcaseNodeDataModel.getTagStatic().getName() )){
				}else if( baseElement.getTagName().equals( Tag.BASENODE.getName() )){
					this.add(new BaseNodeDataModel(baseElement));
				}
			}
		}
	}
	
/*	public static Tag getTagStatic(){
		return TAG;
	}
*/
	@Override
	public Tag getTag() {
		return TAG;
		//return getTagStatic();
	}

	@Override
	public void add(BaseDataModelInterface node) {
		super.add( (MutableTreeNode)node );
	}
	
	public static String  getModelNameToShowStatic(){
		return CommonOperations.getTranslation( "tree.nodetype.base.node");
	}
	
	@Override
	public String getModelNameToShow(){
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
		Element nodeElement = document.createElement( BaseNodeDataModel.this.getTag().getName() );
		attr = document.createAttribute( ATTR_NAME );
		attr.setValue( getName() );
		nodeElement.setAttributeNode(attr);	
		
		attr = document.createAttribute( ATTR_DETAILS );
		attr.setValue( getDetails() );
		nodeElement.setAttributeNode(attr);	
	
		int childrens = this.getChildCount();
		for( int i = 0; i < childrens; i++ ){
			
			Object object = this.getChildAt( i );
			
			if( !object.equals(this) && object instanceof BaseDataModelInterface ){
				
				Element element = ((BaseDataModelInterface)object).getXMLElement( document );
				nodeElement.appendChild( element );		    		
		    	
			}
		}
	
		return nodeElement;		
	}

}
