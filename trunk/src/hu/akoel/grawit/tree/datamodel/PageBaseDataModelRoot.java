package hu.akoel.grawit.tree.datamodel;

import java.util.Enumeration;

import javax.swing.tree.DefaultMutableTreeNode;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class PageBaseDataModelRoot extends DefaultMutableTreeNode implements DataModelInterface{

	private static final long serialVersionUID = 5361088361756620748L;

	public PageBaseDataModelRoot(){
		super( "BasePage" );
	}
	
	@Override
	public Element getXMLElement(Document document) {
//System.err.println("Root");
		
		//PageBaseElement
		Element pageBaseElement = document.createElement("pagebase");
			
//		//RootElement
//		Element rootElement = document.createElement("root");
//		pageBaseElement.appendChild(rootElement);
			
		
		int childrens = this.getChildCount();
		for( int i = 0; i < childrens; i++ ){
			
			Object object = this.getChildAt( i );
			
			if( !object.equals(this) && object instanceof DataModelInterface ){
				
				Element element = ((DataModelInterface)object).getXMLElement( document );
				pageBaseElement.appendChild( element );		    		
		    	
			}
		}
		
/*		//Enumeration<?> e = this.preorderEnumeration();
		Enumeration<?> e = this.breadthFirstEnumeration();
		//Enumeration<?> e = this.depthFirstEnumeration();
		while(e.hasMoreElements()){
			
			Object object = e.nextElement();
			
			if( !object.equals(this) && object instanceof DataModelInterface ){
				
				Element element = ((DataModelInterface)object).getXMLElement( document );
				rootElement.appendChild( element );		    		
		    	
			}
		}
*/			
		return pageBaseElement;		
	}
	
}
