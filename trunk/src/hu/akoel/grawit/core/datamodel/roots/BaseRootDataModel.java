package hu.akoel.grawit.core.datamodel.roots;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.datamodel.BaseDataModelInterface;
import hu.akoel.grawit.core.datamodel.nodes.BaseNodeDataModel;
import hu.akoel.grawit.exceptions.XMLExtraTagPharseException;
import hu.akoel.grawit.exceptions.XMLPharseException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class BaseRootDataModel extends BaseNodeDataModel{

	private static final long serialVersionUID = 5361088361756620748L;

	private static final String TAG_NAME = "basepage";
	
	public BaseRootDataModel(){
		super( "", "" );
	}
	
	public BaseRootDataModel( Document doc ) throws XMLPharseException{
		super("","");
		
		NodeList nList = doc.getElementsByTagName( TAG_NAME );
		
		//Ha nem pontosan 1 db basepage tag van, akkor az gaz
		if( nList.getLength() != 1 ){
			
			throw new XMLExtraTagPharseException( "base", "basepage" );
		}
		
		Node basePageNode = nList.item(0);
		if (basePageNode.getNodeType() == Node.ELEMENT_NODE) {
			
			NodeList nodeList = basePageNode.getChildNodes();
			for( int i = 0; i < nodeList.getLength(); i++ ){
			
				Node baseNode = nodeList.item( i );
				
				if (baseNode.getNodeType() == Node.ELEMENT_NODE) {
					Element baseElement = (Element)baseNode;
					
					//Ha ujabb BASENODE van alatta
					if( baseElement.getTagName().equals( BaseNodeDataModel.getTagName() ) ){
						this.add(new BaseNodeDataModel(baseElement));
					}
				}
			}
		}		
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
		Element pageBaseElement = document.createElement( TAG_NAME );

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
