package hu.akoel.grawit.gui.tree.datamodel;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.datamodel.BaseDataModelInterface;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class BasePageRootDataModel extends BasePageNodeDataModel{

	private static final long serialVersionUID = 5361088361756620748L;

	public BasePageRootDataModel(){
		super( "", "" );
	}
	
	public String getNameToString(){
		return "Base Root";
	}
	
	public String getTypeToString(){
		return CommonOperations.getTranslation( "tree.nodetype.root");
	}
	
	@Override
	public Element getXMLElement(Document document) {
		
		//PageBaseElement
		Element pageBaseElement = document.createElement("pagebase");

		int childrens = this.getChildCount();
		for( int i = 0; i < childrens; i++ ){
			
			Object object = this.getChildAt( i );
			
			if( !object.equals(this) && object instanceof BaseDataModelInterface ){
				
				Element element = ((BaseDataModelInterface)object).getXMLElement( document );
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
