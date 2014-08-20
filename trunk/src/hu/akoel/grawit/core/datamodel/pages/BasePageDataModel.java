package hu.akoel.grawit.core.datamodel.pages;

import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

import org.apache.xerces.dom.AttrNSImpl;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.datamodel.BaseDataModelInterface;
import hu.akoel.grawit.core.datamodel.elements.BaseElementDataModel;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;
import hu.akoel.grawit.exceptions.XMLPharseException;

public class BasePageDataModel extends BaseDataModelInterface{

	private static final long serialVersionUID = 8871077064641984017L;
	
	public static final Tag TAG = Tag.BASEPAGE;
	
//	public static final String ATTR_NAME = "name";
	public static final String ATTR_DETAILS = "details";
	
	private String name ;
	private String details;
//	private ArrayList<BasePageChangeListener> changeListenerList = new ArrayList<>();
		
	public BasePageDataModel( String name, String details ){
		this.name = name;
		this.details = details;
	}
		
	/**
	 * XML alapjan gyartja le a BASEPAGE-et es az alatta elofordulo
	 * BASEELEMENT-eket
	 * 
	 * @param element
	 * @throws XMLPharseException 
	 */
	public BasePageDataModel( Element element ) throws XMLPharseException{
		
		if( !element.hasAttribute( ATTR_NAME ) ){
			throw new XMLMissingAttributePharseException( getRootTag(), getTag(), ATTR_NAME );			
		}
		String nameString = element.getAttribute( ATTR_NAME );
		this.name = nameString;
		
		if( !element.hasAttribute( ATTR_DETAILS ) ){
			throw new XMLMissingAttributePharseException( getRootTag(), getTag(), ATTR_NAME, getName(), ATTR_DETAILS );			
		}
		String detailsString = element.getAttribute( ATTR_DETAILS );
		this.details = detailsString;
		
		//Vegig a BASEELEMENT-ekent
		NodeList nodelist = element.getChildNodes();
		for( int i = 0; i < nodelist.getLength(); i++ ){
			Node node = nodelist.item( i );
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element baseElement = (Element)node;
				//if( baseElement.getTagName().equals( BaseElementDataModel.getTagStatic() )){
				if( baseElement.getTagName().equals( Tag.BASEELEMENT.getName() )){					
					this.add(new BaseElementDataModel(baseElement));
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
	public String getName(){
		return name;
	}
	
	public String getDetails(){
		return details;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	@Override
	public void add(BaseDataModelInterface node) {
		super.add( (MutableTreeNode)node );
	}

	public String getTypeToShow(){
		return CommonOperations.getTranslation( "tree.nodetype.basepage");
	}

	@Override
	public Element getXMLElement(Document document) {
		Attr attr;
	
		//Node element
		Element pageElement = document.createElement( BasePageDataModel.this.getTag().getName() );
		
		//NAME attributum
		attr = document.createAttribute( ATTR_NAME );
		attr.setValue( getName() );
		pageElement.setAttributeNode(attr);	
		
		//DETAILS attributum
		attr = document.createAttribute( ATTR_DETAILS );
		attr.setValue( getDetails() );
		pageElement.setAttributeNode(attr);		

		int childrens = this.getChildCount();
		for( int i = 0; i < childrens; i++ ){
			
			Object object = this.getChildAt( i );
			
			if( !object.equals(this) && object instanceof BaseDataModelInterface ){
				
				Element element = ((BaseDataModelInterface)object).getXMLElement( document );
				pageElement.appendChild( element );		    		
		    	
			}
		}
		
		return pageElement;	
	}
	
}
