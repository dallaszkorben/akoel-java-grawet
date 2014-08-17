package hu.akoel.grawit.gui.tree.datamodel;

import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.pages.BasePage;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class BasePagePageDataModel  extends BasePageDataModelInterface{
	private BasePage basePage;

	private static final long serialVersionUID = 8871077064641984017L;
	
	public BasePagePageDataModel( BasePage basePage ){
		super();
		this.basePage = basePage;
	}

	@Override
	public void add(BasePageDataModelInterface node) {
		super.add( (MutableTreeNode)node );
	}

	public String getNameToString(){
		return basePage.getName();
	}
	
	@Override
	public String getPathToString() {
		StringBuffer out = new StringBuffer();
		for( TreeNode node: this.getPath() ){
			out.append( ((BasePageDataModelInterface)node).getNameToString() );
			out.append("/");
		}		
		return out.toString();
	}
	
	public String getTypeToString(){
		return CommonOperations.getTranslation( "tree.nodetype.pagebase");
	}
	
	public BasePage getBasePage(){
		return basePage;
	}

	@Override
	public Element getXMLElement(Document document) {
		Attr attr;
	
		//Node element
		Element pageElement = document.createElement("page");
		
		//NAME attributum
		attr = document.createAttribute("name");
		attr.setValue( basePage.getName() );
		pageElement.setAttributeNode(attr);	
		
		//DETAILS attributum
		attr = document.createAttribute("details");
		attr.setValue( basePage.getDetails() );
		pageElement.setAttributeNode(attr);
		

		int childrens = this.getChildCount();
		for( int i = 0; i < childrens; i++ ){
			
			Object object = this.getChildAt( i );
			
			if( !object.equals(this) && object instanceof BasePageDataModelInterface ){
				
				Element element = ((BasePageDataModelInterface)object).getXMLElement( document );
				pageElement.appendChild( element );		    		
		    	
			}
		}
		
		return pageElement;	
	}


}
