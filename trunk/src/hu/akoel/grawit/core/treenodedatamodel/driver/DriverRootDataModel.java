package hu.akoel.grawit.core.treenodedatamodel.driver;

import java.util.Vector;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.DriverDataModelAdapter;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.exceptions.XMLPharseException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DriverRootDataModel extends DriverFolderDataModel{

	private static final long serialVersionUID = -7771576293637635927L;

	private static final Tag TAG = Tag.DRIVERROOT;
	
	public static final String ATTR_NAME = "";
	
	public DriverRootDataModel(){
		super( "", "" );
	}
	
	public DriverRootDataModel( Document doc ) throws XMLPharseException{
		super("","");
		
		NodeList rootList = doc.getElementsByTagName( TAG.getName() );
		
		//Ha pontosan 1 db root van definialva	
		if( rootList.getLength() == 1 ){
				
			Node rootNode = rootList.item(0);
			if (rootNode.getNodeType() == Node.ELEMENT_NODE) {
					
				NodeList nodeList = rootNode.getChildNodes();
				for( int i = 0; i < nodeList.getLength(); i++ ){
					
					Node node = nodeList.item( i );
						
					if (node.getNodeType() == Node.ELEMENT_NODE) {
						Element element = (Element)node;
							
						//Ha ujabb DRIVERNODE van alatta
						if( element.getTagName().equals( Tag.DRIVERFOLDER.getName() ) ){
							this.add(new DriverFolderDataModel(element));
						}
					}
				}
			}
		}
	}
	
	@Override
	public String getName(){
		//return "/";
		return "Driver Root";
	}

	@Override
	public Tag getTag(){
		return TAG;
	}

	@Override
	public String getNodeTypeToShow(){
		return CommonOperations.getTranslation( "tree.nodetype.driver.root");
	}
	
	@Override
	public Element getXMLElement(Document document) {
		
		//ParamPageElement
		Element paramPageElement = document.createElement( TAG.getName() );

		int childrens = this.getChildCount();
		for( int i = 0; i < childrens; i++ ){
			
			Object object = this.getChildAt( i );
			
			if( !object.equals(this) && object instanceof DriverDataModelAdapter ){
				
				Element element = ((DriverDataModelAdapter)object).getXMLElement( document );
				paramPageElement.appendChild( element );		    		
		    	
			}
		}
		
		return paramPageElement;		
	}
	
	@Override
	public Object clone(){
		
		DriverRootDataModel cloned = (DriverRootDataModel)super.clone();
	
		if( null != this.children ){
			cloned.children = (Vector<?>) this.children.clone();
		}
		
		return cloned;
		
	}
}
