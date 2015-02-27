package hu.akoel.grawit.core.treenodedatamodel.constant;

import java.util.Vector;

import javax.swing.tree.MutableTreeNode;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.ConstantDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseRootDataModel;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;
import hu.akoel.grawit.exceptions.XMLPharseException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ConstantFolderNodeDataModel extends ConstantDataModelAdapter{

	private static final long serialVersionUID = -5125611897338677880L;
	
	public static final Tag TAG = Tag.CONSTANTFOLDER;
	
	public static final String ATTR_DETAILS = "details";
	
	private String name;
	private String details;
	
	public ConstantFolderNodeDataModel( String name, String details ){
		super( );
		this.name = name;
		this.details = details;
	}
	
	/**
	 * XML alapjan legyartja a CONSTANTNODE-ot es az alatta elofordulo 
	 * CONSTANTNODE-okat, illetve CONSTANTPAGE-eket
	 * 
	 * @param element
	 * @throws XMLMissingAttributePharseException 
	 */
	public ConstantFolderNodeDataModel( Element element, BaseRootDataModel baseRootDataModel ) throws XMLPharseException{
		
		if( !element.hasAttribute( ATTR_NAME ) ){
			throw new XMLMissingAttributePharseException( ConstantFolderNodeDataModel.getRootTag(), Tag.CONSTANTFOLDER, ATTR_NAME );			
		}
		String nameString = element.getAttribute( ATTR_NAME );
		this.name = nameString;
		
		if( !element.hasAttribute( ATTR_DETAILS ) ){
			throw new XMLMissingAttributePharseException( ConstantFolderNodeDataModel.getRootTag(), Tag.CONSTANTFOLDER, ATTR_NAME, getName(), ATTR_DETAILS );			
		}		
		String detailsString = element.getAttribute( ATTR_DETAILS );		
		this.details = detailsString;
		
		NodeList nodelist = element.getChildNodes();
		for( int i = 0; i < nodelist.getLength(); i++ ){
			Node node = nodelist.item( i );
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element constantElement = (Element)node;
				
				//Ha CONSTANTELEMENT van alatta
				if( constantElement.getTagName().equals( Tag.CONSTANTELEMENT.getName() )){
					this.add(new ConstantElementDataModel(constantElement, baseRootDataModel ));
				
				//Ha ujabb CONSTANTNODE van alatta
				}else if( constantElement.getTagName().equals( Tag.CONSTANTFOLDER.getName() )){
					this.add(new ConstantFolderNodeDataModel(constantElement, baseRootDataModel ));
				}
			}
		}
	}
	
	@Override
	public Tag getTag() {
		return TAG;
	}

	@Override
	public void add(ConstantDataModelAdapter node) {
		super.add( (MutableTreeNode)node );
	}
	
	public static String  getModelNameToShowStatic(){
		return CommonOperations.getTranslation( "tree.nodetype.constant.folder");
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
		Element nodeElement = document.createElement( ConstantFolderNodeDataModel.this.getTag().getName() );
		attr = document.createAttribute( ATTR_NAME );
		attr.setValue( getName() );
		nodeElement.setAttributeNode(attr);	
		
		attr = document.createAttribute( ATTR_DETAILS );
		attr.setValue( getDetails() );
		nodeElement.setAttributeNode(attr);	
	
		int childrens = this.getChildCount();
		for( int i = 0; i < childrens; i++ ){
			
			Object object = this.getChildAt( i );
			
			if( !object.equals(this) && object instanceof ConstantDataModelAdapter ){
				
				Element element = ((ConstantDataModelAdapter)object).getXMLElement( document );
				nodeElement.appendChild( element );		    		
		    	
			}
		}
	
		return nodeElement;		
	}

	@Override
	public Object clone(){
		
		//Leklonozza a NODE-ot
		ConstantFolderNodeDataModel cloned = (ConstantFolderNodeDataModel)super.clone();

		//Ha vannak gyerekei (NODE vagy ELEMENT)
		if( null != this.children ){
			
			//Akkor azokat is leklonozza
			cloned.children = new Vector<>();
			
			for( Object o : this.children ){
				
				if( o instanceof ConstantDataModelAdapter ){
					
					ConstantDataModelAdapter child = (ConstantDataModelAdapter) ((ConstantDataModelAdapter)o).clone();
					
					//Szulo megadasa, mert hogy nem lett hozzaadva direkt modon a Tree-hez
					child.setParent( cloned );					
					
					cloned.children.add(child);
				}
			}
		}
		
		//Es a valtozokat is leklonozza
		cloned.name = new String( this.name );
		cloned.details = new String( this.details );
		
		//Le kell masolni a felmenoit is, egyebkent azok automatikusan null-ok
		cloned.setParent( (MutableTreeNode) this.getParent() );
		
		return cloned;
		
	}
	
}
