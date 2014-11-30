package hu.akoel.grawit.core.treenodedatamodel.base;

import java.util.Vector;

import javax.swing.tree.MutableTreeNode;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.BaseDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.BaseElementDataModelAdapter;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.enums.list.ElementTypeListEnum;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;
import hu.akoel.grawit.exceptions.XMLPharseException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class BaseNodeDataModel extends BaseDataModelAdapter{

	private static final long serialVersionUID = -5125611897338677880L;
	
	public static final Tag TAG = Tag.BASENODE;
	
	public static final String ATTR_DETAILS = "details";
	
	private String name;
	private String details;
	
	public BaseNodeDataModel( String name, String details ){
		super( );
		this.name = name;
		this.details = details;
	}
	
	/**
	 * XML alapjan legyartja a BASENODE-ot es az alatta elofordulo 
	 * BASENODE-okat, illetve BASEPAGE-eket
	 * 
	 * @param element
	 * @throws XMLMissingAttributePharseException 
	 */
	public BaseNodeDataModel( Element element ) throws XMLPharseException{
		
		if( !element.hasAttribute( ATTR_NAME ) ){
			throw new XMLMissingAttributePharseException( BaseNodeDataModel.getRootTag(), Tag.BASENODE, ATTR_NAME );			
		}
		String nameString = element.getAttribute( ATTR_NAME );
		this.name = nameString;
		
		if( !element.hasAttribute( ATTR_DETAILS ) ){
			throw new XMLMissingAttributePharseException( BaseNodeDataModel.getRootTag(), Tag.BASENODE, ATTR_NAME, getName(), ATTR_DETAILS );			
		}		
		String detailsString = element.getAttribute( ATTR_DETAILS );		
		this.details = detailsString;
		
		NodeList nodelist = element.getChildNodes();
		for( int i = 0; i < nodelist.getLength(); i++ ){
			Node node = nodelist.item( i );
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element baseElement = (Element)node;
				
				//Ha BASEPAGE van alatta
				//if( baseElement.getTagName().equals( BasePageDataModel.getTagStatic().getName() )){
				if( baseElement.getTagName().equals( Tag.BASEPAGE.getName() )){
					this.add(new BasePageDataModel(baseElement));
				
				//Ha ujabb BASENODE van alatta
				//}else if( baseElement.getTagName().equals( TestcaseNodeDataModel.getTagStatic().getName() )){
				}else if( baseElement.getTagName().equals( Tag.BASENODE.getName() )){
					this.add(new BaseNodeDataModel(baseElement));
					
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
	public void add(BaseDataModelAdapter node) {
		super.add( (MutableTreeNode)node );
	}
	
	public static String  getModelNameToShowStatic(){
		return CommonOperations.getTranslation( "tree.nodetype.base.node");
	}
	
	@Override
	public String getNodeTypeToShow(){
		return getModelNameToShowStatic();
	}
	
	@Override
	public String getName(){
		return name;
	}
	
	public String getDetails(){
		return details;
	}
	
	public void setDetails( String details ){
		this.details = details;
	}
	
	public void setName( String name ){
		this.name = name;
	}
	
	public String toString(){
		return name;
	}

	@Override
	public Element getXMLElement(Document document) {
		Attr attr;
		
		//Node element
		Element nodeElement = document.createElement( BaseNodeDataModel.this.getTag().getName() );
		attr = document.createAttribute( ATTR_NAME );
		attr.setValue( getName() );
		nodeElement.setAttributeNode(attr);	
		
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
	
		return nodeElement;		
	}

	@Override
	public Object clone(){
		
		//Leklonozza a NODE-ot
		BaseNodeDataModel cloned = (BaseNodeDataModel)super.clone();
	
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
		
		BaseNodeDataModel cloned = (BaseNodeDataModel) this.clone();
		
		//Le kell masolni a felmenoit is, egyebkent azok automatikusan null-ok
		cloned.setParent( (MutableTreeNode) this.getParent() );
		
		return cloned;
	}
}
