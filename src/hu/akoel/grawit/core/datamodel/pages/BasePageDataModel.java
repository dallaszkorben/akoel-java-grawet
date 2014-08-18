package hu.akoel.grawit.core.datamodel.pages;

import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.datamodel.BaseDataModelInterface;
import hu.akoel.grawit.core.datamodel.elements.BaseElementDataModel;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;
import hu.akoel.grawit.exceptions.XMLPharseException;

public class BasePageDataModel extends BaseDataModelInterface{

	private static final long serialVersionUID = 8871077064641984017L;
	
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
		
		if( !element.hasAttribute("name") ){
			throw new XMLMissingAttributePharseException( "base", "page", "name" );			
		}
		String nameString = element.getAttribute("name");
		this.name = nameString;
		
		if( !element.hasAttribute("details") ){
			throw new XMLMissingAttributePharseException( "base", "page", "details" );			
		}
		String detailsString = element.getAttribute("details");
		this.details = detailsString;
		
		NodeList nodelist = element.getChildNodes();
		for( int i = 0; i < nodelist.getLength(); i++ ){
			Node node = nodelist.item( i );
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element baseElement = (Element)node;
				if( baseElement.getTagName().equals("element")){
					this.add(new BaseElementDataModel(baseElement));
				}
			}
		}		
	}
	
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

	public String getNameToString(){
		return getName();
	}
	
	@Override
	public String getPathToString() {
		StringBuffer out = new StringBuffer();
		for( TreeNode node: this.getPath() ){
			out.append( ((BaseDataModelInterface)node).getNameToString() );
			out.append("/");
		}		
		return out.toString();
	}
	
	public String getTypeToString(){
		return CommonOperations.getTranslation( "tree.nodetype.basepage");
	}

	@Override
	public Element getXMLElement(Document document) {
		Attr attr;
	
		//Node element
		Element pageElement = document.createElement("page");
		
		//NAME attributum
		attr = document.createAttribute("name");
		attr.setValue( getName() );
		pageElement.setAttributeNode(attr);	
		
		//DETAILS attributum
		attr = document.createAttribute("details");
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
