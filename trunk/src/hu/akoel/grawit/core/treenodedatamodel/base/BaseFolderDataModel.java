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

/*	@Override
	public void add(BaseDataModelAdapter node) {
		super.add( (MutableTreeNode)node );
	}
*/	
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
		
/*		//Node element
		Element nodeElement = document.createElement( BaseFolderDataModel.this.getTag().getName() );
		
		//NAME attributum
		attr = document.createAttribute( ATTR_NAME );
		attr.setValue( getName() );
		nodeElement.setAttributeNode(attr);	
		
		//DETAILS attributum
		attr = document.createAttribute( ATTR_DETAILS );
		attr.setValue( getDetails() );
		nodeElement.setAttributeNode(attr);	
	
		int childrens = this.getChildCount();
		for( int i = 0; i < childrens; i++ ){
			
			Object object = this.getChildAt( i );
			
			if( !object.equals(this) && object instanceof BaseDataModelAdapter ){
				
				Element element = ((BaseDataModelAdapter)object).getXMLElement( document );
				nodeElement.appendChild( element );		    		
		    	
			}
		}
*/	
		return nodeElement;		
	}

/*	
	@Override
	public Object clone(){
		
		//Leklonozza a NODE-ot
		BaseFolderDataModel cloned = (BaseFolderDataModel)super.clone();
	
		//Ha vannak gyerekei (NODE vagy PAGE)
		if( null != this.children ){
			
			//Akkor azokat is leklonozza
			cloned.children = new Vector<>();
			
			for( Object o : this.children ){
				
				if( o instanceof BaseDataModelAdapter ){					
					
					BaseDataModelAdapter child = (BaseDataModelAdapter) ((BaseDataModelAdapter)o).clone();
					
					//Szulo megadasa, mert hogy nem lett hozzaadva direkt modon a Tree-hez
					child.setParent( cloned );					
					
					cloned.children.add(child);
					
				}
			}
		}
		
		//Es a valtozokat is leklonozza
		cloned.name = new String( this.name );
		cloned.details = new String( this.details );
		
		return cloned;
		
	}
	
	@Override
	public Object cloneWithParent() {
		
		BaseFolderDataModel cloned = (BaseFolderDataModel) this.clone();
		
		//Le kell masolni a felmenoit is, egyebkent azok automatikusan null-ok
		cloned.setParent( (MutableTreeNode) this.getParent() );
		
		return cloned;
	}
*/
	
}
