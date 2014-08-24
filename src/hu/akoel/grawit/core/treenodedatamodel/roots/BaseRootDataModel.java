package hu.akoel.grawit.core.treenodedatamodel.roots;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.BaseDataModelInterface;
import hu.akoel.grawit.core.treenodedatamodel.nodes.BaseNodeDataModel;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.exceptions.XMLExtraRootTagPharseException;
import hu.akoel.grawit.exceptions.XMLPharseException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class BaseRootDataModel extends BaseNodeDataModel{

	private static final long serialVersionUID = 5361088361756620748L;

	private static final Tag TAG = Tag.BASEROOT;
	
	public static final String ATTR_NAME = "";
	
	public BaseRootDataModel(){
		super( "", "" );
	}
	
	public BaseRootDataModel( Document doc ) throws XMLPharseException{
		super("","");
		
		NodeList nList = doc.getElementsByTagName( TAG.getName() );
		
		//Ha nem pontosan 1 db basepage tag van, akkor az gaz
		if( nList.getLength() != 1 ){
			
			throw new XMLExtraRootTagPharseException( TAG );
		}
		
		Node basePageNode = nList.item(0);
		if (basePageNode.getNodeType() == Node.ELEMENT_NODE) {
			
			NodeList nodeList = basePageNode.getChildNodes();
			for( int i = 0; i < nodeList.getLength(); i++ ){
			
				Node baseNode = nodeList.item( i );
				
				if (baseNode.getNodeType() == Node.ELEMENT_NODE) {
					Element baseElement = (Element)baseNode;
					
					//Ha ujabb BASENODE van alatta
					//if( baseElement.getTagName().equals( BaseNodeDataModel.getTagStatic().getName() ) ){
					if( baseElement.getTagName().equals( Tag.BASENODE.getName() ) ){
						this.add(new BaseNodeDataModel(baseElement));
					}
				}
			}
		}		
	}
	
/*	public static Tag getTagStatic(){
		return TAG;
	}
*/
	@Override
	public Tag getTag() {
		return TAG;
		//return getTagStatic();
	}

	@Override
	public String getName(){
		return "Base Root";
	}
	
	@Override
	public String getModelNameToShow(){
		return CommonOperations.getTranslation( "tree.nodetype.root");
	}
	
	@Override
	public Element getXMLElement(Document document) {
		
		//PageBaseElement
		Element pageBaseElement = document.createElement( TAG.getName() );

		int childrens = this.getChildCount();
		for( int i = 0; i < childrens; i++ ){
			
			Object object = this.getChildAt( i );
			
			if( !object.equals(this) && object instanceof BaseDataModelInterface ){
				
				Element element = ((BaseDataModelInterface)object).getXMLElement( document );
				pageBaseElement.appendChild( element );		    		
		    	
			}
		}

		return pageBaseElement;		
	}
	
}
