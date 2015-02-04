package hu.akoel.grawit.core.treenodedatamodel.base;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.BaseElementDataModelAdapter;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.enums.list.ElementTypeListEnum;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;
import hu.akoel.grawit.exceptions.XMLPharseException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class BaseFolderDataModel extends BaseNodeDataModelAdapter{

	private static final long serialVersionUID = -5125611897338677880L;
	
	public static final Tag TAG = Tag.BASEFOLDER;
	
	public BaseFolderDataModel( String name, String details ){
		super( name, details );
	}
	
	/**
	 * XML alapjan legyartja a BASENODE-ot es az alatta elofordulo 
	 * BASENODE-okat, illetve BASEPAGE-eket
	 * 
	 * @param element
	 * @throws XMLMissingAttributePharseException 
	 */
	public BaseFolderDataModel( Element element ) throws XMLPharseException{
		
		super(element);
		
		//========
		//
		// Gyermekei
		//
		//========
		NodeList nodelist = element.getChildNodes();
		for( int i = 0; i < nodelist.getLength(); i++ ){
			Node node = nodelist.item( i );
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element baseElement = (Element)node;
				
				//Ha BASEPAGE van alatta
				//if( baseElement.getTagName().equals( BasePageDataModel.getTagStatic().getName() )){
				if( baseElement.getTagName().equals( Tag.BASECOLLECTOR.getName() )){
					this.add(new BaseCollectorDataModel(baseElement));
				
				//Ha ujabb BASEFOLDER van alatta
				//}else if( baseElement.getTagName().equals( TestcaseNodeDataModel.getTagStatic().getName() )){
				}else if( baseElement.getTagName().equals( Tag.BASEFOLDER.getName() )){
					this.add(new BaseFolderDataModel(baseElement));
					
				//Ha normal elem
				}else if( baseElement.getTagName().equals( Tag.BASEELEMENT.getName() ) ){
					
					//element type             
					if( !baseElement.hasAttribute( BaseElementDataModelAdapter.ATTR_ELEMENT_TYPE ) ){
						throw new XMLMissingAttributePharseException( getRootTag(), getTag(), ATTR_NAME, getName(), BaseElementDataModelAdapter.ATTR_ELEMENT_TYPE );			
					}
					String elementTypeString = baseElement.getAttribute( BaseElementDataModelAdapter.ATTR_ELEMENT_TYPE );
					
					//SCRIPT
					if( elementTypeString.equals( ElementTypeListEnum.SCRIPT.name() )){
						
						this.add( new ScriptBaseElementDataModel(baseElement));
						
					//FIELD, TEXT, LINK, LIST, BUTTON, RADIOBUTTON, CHECKBOX
					}else{						
					
						this.add(new NormalBaseElementDataModel(baseElement));
					}
										
				}
			}
		}
		
	}
	
	public static Tag getTagStatic(){
		return TAG;
	}

	@Override
	public Tag getTag() {
		return getTagStatic();
	}
	
	public static String  getModelNameToShowStatic(){
		return CommonOperations.getTranslation( "tree.nodetype.base.folder");
	}
	
	@Override
	public String getNodeTypeToShow(){
		return getModelNameToShowStatic();
	}
	

	@Override
	public Element getXMLElement(Document document) {
//		Attr attr;

		Element nodeElement = super.getXMLElement(document);
		
		return nodeElement;		
	}

	
}
