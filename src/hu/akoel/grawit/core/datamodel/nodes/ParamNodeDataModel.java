package hu.akoel.grawit.core.datamodel.nodes;

import javax.swing.tree.MutableTreeNode;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.datamodel.ParamDataModelInterface;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

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
	
	public String getTypeToString(){
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
