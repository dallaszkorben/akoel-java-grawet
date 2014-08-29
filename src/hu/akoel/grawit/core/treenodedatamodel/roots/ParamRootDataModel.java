package hu.akoel.grawit.core.treenodedatamodel.roots;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.ParamDataModelInterface;
import hu.akoel.grawit.core.treenodedatamodel.nodes.ParamNodeDataModel;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.exceptions.XMLExtraRootTagPharseException;
import hu.akoel.grawit.exceptions.XMLPharseException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ParamRootDataModel extends ParamNodeDataModel{

	private static final long serialVersionUID = 9062567931430247371L;

	private static final Tag TAG = Tag.PARAMROOT;
	
	public static final String ATTR_NAME = "";
	
	public ParamRootDataModel(){
		super( "", "" );
	}
	
	public ParamRootDataModel( Document doc, BaseRootDataModel baseRootDataModel, VariableRootDataModel variableRootDataModel ) throws XMLPharseException{
		super("","");
		
		NodeList nList = doc.getElementsByTagName( TAG.getName() );
		
		//Ha nem pontosan 1 db parampage tag van, akkor az gaz
		if( nList.getLength() != 1 ){
			
			throw new XMLExtraRootTagPharseException( TAG );
		}
		
		Node paramPageNode = nList.item(0);
		if (paramPageNode.getNodeType() == Node.ELEMENT_NODE) {
			
			NodeList nodeList = paramPageNode.getChildNodes();
			for( int i = 0; i < nodeList.getLength(); i++ ){
			
				Node paramNode = nodeList.item( i );
				
				if (paramNode.getNodeType() == Node.ELEMENT_NODE) {
					Element paramElement = (Element)paramNode;
					
					//Ha ujabb PARAMNODE van alatta
					//if( paramElement.getTagName().equals( ParamNodeDataModel.getTagStatic() ) ){
					if( paramElement.getTagName().equals( Tag.PARAMNODE.getName() ) ){						
						this.add(new ParamNodeDataModel(paramElement, baseRootDataModel, variableRootDataModel ));
					}
				}
			}
		}		
	}
	
	@Override
	public String getName(){
		return "Param Root";
	}

	@Override
	public Tag getTag(){
		return TAG;
	}

	@Override
	public String getModelNameToShow(){
		return CommonOperations.getTranslation( "tree.nodetype.root");
	}
	
	@Override
	public Element getXMLElement(Document document) {
		
		//ParamPageElement
		Element paramPageElement = document.createElement( TAG.getName() );

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
