package hu.akoel.grawit.gui.tree.datamodel;

import hu.akoel.grawit.CommonOperations;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ParamPageRootDataModel extends ParamPageNodeDataModel{

	private static final long serialVersionUID = 9062567931430247371L;

	public ParamPageRootDataModel(){
		super( "", "" );
	}
	
	public String getNameToString(){
		return "haliho Root";
	}
	
	public String getTypeToString(){
		return CommonOperations.getTranslation( "tree.nodetype.root");
	}
	
	@Override
	public Element getXMLElement(Document document) {
		
		//ParamPageElement
		Element paramPageElement = document.createElement("parampage");

		int childrens = this.getChildCount();
		for( int i = 0; i < childrens; i++ ){
			
			Object object = this.getChildAt( i );
			
			if( !object.equals(this) && object instanceof PageBaseDataModelInterface ){
				
				Element element = ((PageBaseDataModelInterface)object).getXMLElement( document );
				paramPageElement.appendChild( element );		    		
		    	
			}
		}
		
		return paramPageElement;		
	}
	
}
