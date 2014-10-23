package hu.akoel.grawit.core.treenodedatamodel.base;

import java.util.Vector;

import javax.swing.tree.MutableTreeNode;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.BaseDataModelAdapter;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;
import hu.akoel.grawit.exceptions.XMLPharseException;

public class BasePageDataModel extends BaseDataModelAdapter{

	private static final long serialVersionUID = 8871077064641984017L;
	
	public static final Tag TAG = Tag.BASEPAGE;
	
	public static final String ATTR_DETAILS = "details";
	
	private String name ;
	private String details;
		
	public BasePageDataModel( String name, String details ){
		this.name = name;
		this.details = details;
	}
		
	/**
	 * XML alapjan gyartja le a BASEPAGE-et es az alatta elofordulo
	 * BASEELEMENT-eket
	 * 
	 * @param element
	 * @throws XMLPharseException 
	 */
	public BasePageDataModel( Element element ) throws XMLPharseException{
		
		if( !element.hasAttribute( ATTR_NAME ) ){
			throw new XMLMissingAttributePharseException( getRootTag(), getTag(), ATTR_NAME );			
		}
		String nameString = element.getAttribute( ATTR_NAME );
		this.name = nameString;
		
		if( !element.hasAttribute( ATTR_DETAILS ) ){
			throw new XMLMissingAttributePharseException( getRootTag(), getTag(), ATTR_NAME, getName(), ATTR_DETAILS );			
		}
		String detailsString = element.getAttribute( ATTR_DETAILS );
		this.details = detailsString;
		
		//Vegig a BASEELEMENT-ekent
		NodeList nodelist = element.getChildNodes();
		for( int i = 0; i < nodelist.getLength(); i++ ){
			Node node = nodelist.item( i );
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element baseElement = (Element)node;

				if( baseElement.getTagName().equals( Tag.BASEELEMENT.getName() )){					
					this.add(new BaseElementDataModel(baseElement));
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
		return name;
	}
	
	public String getDetails(){
		return details;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	@Override
	public void add(BaseDataModelAdapter node) {
		super.add( (MutableTreeNode)node );
	}

	public static String  getModelNameToShowStatic(){
		return CommonOperations.getTranslation( "tree.nodetype.base.page");
	}
	
	@Override
	public String getNodeTypeToShow(){
		return getModelNameToShowStatic();
	}

	public String toString(){
		return name;
	}
	
	@Override
	public Element getXMLElement(Document document) {
		Attr attr;
	
		//Node element
		Element pageElement = document.createElement( BasePageDataModel.this.getTag().getName() );
		
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
		
		return pageElement;	
	}
	
	@Override
	public Object clone(){
		
		//Leklonozza a PAGE-et
		BasePageDataModel cloned = (BasePageDataModel)super.clone();
	
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
}
