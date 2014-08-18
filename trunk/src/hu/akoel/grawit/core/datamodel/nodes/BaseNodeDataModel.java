package hu.akoel.grawit.core.datamodel.nodes;

import javax.swing.tree.MutableTreeNode;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.datamodel.BaseDataModelInterface;
import hu.akoel.grawit.core.datamodel.elements.BaseElementDataModel;
import hu.akoel.grawit.core.datamodel.pages.BasePageDataModel;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class BaseNodeDataModel extends BaseDataModelInterface{

	private static final long serialVersionUID = -5125611897338677880L;
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
	 */
	public BaseNodeDataModel( Element element ){
		String nameString = element.getAttribute("name");
		String detailsString = element.getAttribute("details");
		this.name = nameString;
		this.details = detailsString;
		
//TODO throw exception, fent pedig elkapni
		
		NodeList nodelist = element.getChildNodes();
		for( int i = 0; i < nodelist.getLength(); i++ ){
			Node node = nodelist.item( i );
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element baseElement = (Element)node;
				
				//Ha BASEPAGE van alatta
				if( baseElement.getTagName().equals("page")){
					this.add(new BasePageDataModel(baseElement));
				
				//Ha ujabb BASENODE van alatta
				}else if( baseElement.getTagName().equals("node")){
					this.add(new BaseNodeDataModel(baseElement));
				}
			}
		}
	}
	
	@Override
	public void add(BaseDataModelInterface node) {
		super.add( (MutableTreeNode)node );
	}
	
	@Override
	public String getNameToString(){
		return name;
	}
	
	@Override
	public String getTypeToString(){
		return CommonOperations.getTranslation( "tree.nodetype.node");
	}
	
	@Override
	public String getPathToString() {		
		return this.getPath().toString();
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
			
			if( !object.equals(this) && object instanceof BaseDataModelInterface ){
				
				Element element = ((BaseDataModelInterface)object).getXMLElement( document );
				nodeElement.appendChild( element );		    		
		    	
			}
		}
		
/*		//Enumeration<?> e = this.preorderEnumeration();
		//Enumeration<?> e = this.breadthFirstEnumeration();
		Enumeration<?> e = this.depthFirstEnumeration();
		while(e.hasMoreElements()){
			
			Object object = e.nextElement();
			
			if( !object.equals(this) && object instanceof DataModelInterface ){
				
				Element element = ((DataModelInterface)object).getXMLElement( document );
				//nodeElement.appendChild( element );		    		
		    	
			}
		}
*/			
		return nodeElement;		
	}


}
