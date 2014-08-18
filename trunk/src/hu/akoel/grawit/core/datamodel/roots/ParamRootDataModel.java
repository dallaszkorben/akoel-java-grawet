package hu.akoel.grawit.core.datamodel.roots;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.datamodel.ParamDataModelInterface;
import hu.akoel.grawit.gui.tree.datamodel.ParamPageNodeDataModel;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ParamRootDataModel extends ParamPageNodeDataModel{

	private static final long serialVersionUID = 9062567931430247371L;

	public ParamRootDataModel(){
		super( "", "" );
	}
	
	public String getNameToString(){
		return "Page Root";
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
			
			if( !object.equals(this) && object instanceof ParamDataModelInterface ){
				
				Element element = ((ParamDataModelInterface)object).getXMLElement( document );
				paramPageElement.appendChild( element );		    		
		    	
			}
		}
		
		return paramPageElement;		
	}
	
}
