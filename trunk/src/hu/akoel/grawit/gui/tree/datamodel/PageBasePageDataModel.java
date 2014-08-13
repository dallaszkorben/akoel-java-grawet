package hu.akoel.grawit.gui.tree.datamodel;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.pages.PageBase;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class PageBasePageDataModel  extends DataModelInterface{
	private PageBase pageBase;

	private static final long serialVersionUID = 8871077064641984017L;
	
	public PageBasePageDataModel( PageBase pageBase ){
		super();
		this.pageBase = pageBase;
	}

	public String getNameToString(){
		return pageBase.getName();
	}
	
	public String getTypeToString(){
		return CommonOperations.getTranslation( "tree.nodetype.pagebase");
	}
	
	public PageBase getPageBase(){
		return pageBase;
	}

	@Override
	public Element getXMLElement(Document document) {
		Attr attr;
//System.err.println("  Page");		
		//Node element
		Element pageElement = document.createElement("page");
		
		//NAME attributum
		attr = document.createAttribute("name");
		attr.setValue( pageBase.getName() );
		pageElement.setAttributeNode(attr);	
		
		//DETAILS attributum
		attr = document.createAttribute("details");
		attr.setValue( pageBase.getDetails() );
		pageElement.setAttributeNode(attr);
		

		int childrens = this.getChildCount();
		for( int i = 0; i < childrens; i++ ){
			
			Object object = this.getChildAt( i );
			
			if( !object.equals(this) && object instanceof DataModelInterface ){
				
				Element element = ((DataModelInterface)object).getXMLElement( document );
				pageElement.appendChild( element );		    		
		    	
			}
		}
		
/*		Enumeration<?> e = this.preorderEnumeration();
		while(e.hasMoreElements()){
			
			Object object = e.nextElement();
			
			if( !object.equals(this) && object instanceof DataModelInterface ){
				
				Element element = ((DataModelInterface)object).getXMLElement( document );
				//pageElement.appendChild( element );		    		
		    	
			}
		}
*/			
		return pageElement;	
	}
}
