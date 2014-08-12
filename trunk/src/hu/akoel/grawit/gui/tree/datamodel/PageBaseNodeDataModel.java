package hu.akoel.grawit.gui.tree.datamodel;

import javax.swing.tree.DefaultMutableTreeNode;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class PageBaseNodeDataModel extends DefaultMutableTreeNode implements DataModelInterface{

	private static final long serialVersionUID = -5125611897338677880L;
	private String name;
	private String details;
	
	public PageBaseNodeDataModel( String name, String details ){
		super( );
		this.name = name;
		this.details = details;
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
			
			if( !object.equals(this) && object instanceof DataModelInterface ){
				
				Element element = ((DataModelInterface)object).getXMLElement( document );
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
