package hu.akoel.grawit.core.treenodedatamodel.base;

import javax.swing.tree.MutableTreeNode;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.BaseDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.BaseElementDataModelAdapter;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.enums.list.ElementTypeListEnum;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;
import hu.akoel.grawit.exceptions.XMLPharseException;

public class BaseCollectorDataModel extends BaseNodeDataModelAdapter  {//BaseDataModelAdapter{

	private static final long serialVersionUID = 8871077064641984017L;
	
	public static final Tag TAG = Tag.BASECOLLECTOR;
	
	public static final String ATTR_DETAILS = "details";
		
	public BaseCollectorDataModel( String name, String details ){
		super( name, details );

	}
		
	/**
	 * XML alapjan gyartja le a BASEPAGE-et es az alatta elofordulo
	 * BASEELEMENT-eket
	 * 
	 * @param element
	 * @throws XMLPharseException 
	 */
	public BaseCollectorDataModel( Element element ) throws XMLPharseException{
		super( element );

		//========
		//
		// Gyermekein
		//
		//========
		NodeList nodelist = element.getChildNodes();
		for( int i = 0; i < nodelist.getLength(); i++ ){
			Node node = nodelist.item( i );
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element baseElement = (Element)node;

				if( baseElement.getTagName().equals( Tag.BASEELEMENT.getName() )  ){
				
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
		return CommonOperations.getTranslation( "tree.nodetype.base.collector");
	}
	
	@Override
	public String getNodeTypeToShow(){
		return getModelNameToShowStatic();
	}
	
	@Override
	public Element getXMLElement(Document document) {
		//Attr attr;
	
		Element pageElement = super.getXMLElement(document); 

/*		
		//Node element
		Element pageElement = document.createElement( BaseCollectorDataModel.this.getTag().getName() );
		
		//NAME attributum
		attr = document.createAttribute( ATTR_NAME );
		attr.setValue( getName() );
		pageElement.setAttributeNode(attr);	
		
		//DETAILS attributum
		attr = document.createAttribute( ATTR_DETAILS );
		attr.setValue( getDetails() );
		pageElement.setAttributeNode(attr);		
		
		int childrens = this.getChildCount();
		for( int i = 0; i < childrens; i++ ){
			
			Object object = this.getChildAt( i );
			
			if( !object.equals(this) && object instanceof BaseDataModelAdapter ){
				
				Element element = ((BaseDataModelAdapter)object).getXMLElement( document );
				pageElement.appendChild( element );		    		
		    	
			}
		}
*/		
		return pageElement;	
	}
/*	
	@Override
	public Object clone(){
		
		//Leklonozza a PAGE-et
		BaseCollectorDataModel cloned = (BaseCollectorDataModel)super.clone();
	
		//Ha vannak gyerekei (NODE vagy ELEMENT)
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
		
		BaseCollectorDataModel cloned = (BaseCollectorDataModel) this.clone();
		
		//Le kell masolni a felmenoit is, egyebkent azok automatikusan null-ok
		cloned.setParent( (MutableTreeNode) this.getParent() );
		
		return cloned;
	}
*/	
}
