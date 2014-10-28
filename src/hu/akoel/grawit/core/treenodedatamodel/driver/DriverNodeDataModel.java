package hu.akoel.grawit.core.treenodedatamodel.driver;

import java.util.Vector;

import javax.swing.tree.MutableTreeNode;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.BaseDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.DriverDataModelInterface;
import hu.akoel.grawit.core.treenodedatamodel.special.SpecialNodeDataModel;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;
import hu.akoel.grawit.exceptions.XMLPharseException;

import org.openqa.selenium.WebDriver;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DriverNodeDataModel extends DriverDataModelInterface{

	private static final long serialVersionUID = -5861123418343409565L;

	public static final Tag TAG = Tag.DRIVERNODE;
	
	public static final String ATTR_DETAILS = "details";
	
	private String name;
	private String details;
	
	public DriverNodeDataModel( String name, String details ){
		super( );
		this.name = name;
		this.details = details;
	}
	
	/**
	 * XML alapjan legyartja a DRIVERNODE-ot es az alatta elofordulo 
	 * DRIVERNODE-okat, illetve DRIVEREXPLORER, DRIVERFIREFOX-eket
	 * 
	 * @param element
	 * @throws XMLMissingAttributePharseException 
	 */
	public DriverNodeDataModel( Element element ) throws XMLPharseException{
		
		if( !element.hasAttribute( ATTR_NAME ) ){
			throw new XMLMissingAttributePharseException( DriverNodeDataModel.getRootTag(), Tag.DRIVERNODE, ATTR_NAME );			
		}
		String nameString = element.getAttribute( ATTR_NAME );
		this.name = nameString;
		
		if( !element.hasAttribute( ATTR_DETAILS ) ){
			throw new XMLMissingAttributePharseException( DriverNodeDataModel.getRootTag(), Tag.DRIVERNODE, ATTR_NAME, getName(), ATTR_DETAILS );			
		}		
		String detailsString = element.getAttribute( ATTR_DETAILS );		
		this.details = detailsString;
		
		NodeList nodelist = element.getChildNodes();
		for( int i = 0; i < nodelist.getLength(); i++ ){
			Node node = nodelist.item( i );
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element driverElement = (Element)node;
				
				//Ha DRIVEREXPLORER van alatta
				if( driverElement.getTagName().equals( Tag.DRIVEREXPLORER.getName() )){
					this.add(new DriverExplorerDataModel(driverElement));

				//Ha DRIVERFIREFOX van alatta
				}else if( driverElement.getTagName().equals( Tag.DRIVERFIREFOX.getName() )){
					this.add(new DriverFirefoxDataModel(driverElement));

					
				//Ha ujabb DRIVERNODE van alatta
				}else if( driverElement.getTagName().equals( Tag.DRIVERNODE.getName() )){
					this.add(new DriverNodeDataModel(driverElement));
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

	@Override
	public void add(DriverDataModelInterface node) {
		super.add( (MutableTreeNode)node );
	}
	
	public static String  getModelNameToShowStatic(){
		return CommonOperations.getTranslation( "tree.nodetype.driver.node");
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
		Element nodeElement = document.createElement( DriverNodeDataModel.this.getTag().getName() );
		
		attr = document.createAttribute( ATTR_NAME );
		attr.setValue( getName() );
		nodeElement.setAttributeNode(attr);	
		
		attr = document.createAttribute( ATTR_DETAILS );
		attr.setValue( getDetails() );
		nodeElement.setAttributeNode(attr);	
	
		int childrens = this.getChildCount();
		for( int i = 0; i < childrens; i++ ){
			
			Object object = this.getChildAt( i );
			
			if( !object.equals(this) && object instanceof DriverDataModelInterface ){
				
				Element element = ((DriverDataModelInterface)object).getXMLElement( document );
				nodeElement.appendChild( element );		    		
		    	
			}
		}
	
		return nodeElement;		
	}

	@Override
	public Object clone(){
		
		DriverNodeDataModel cloned = (DriverNodeDataModel)super.clone();
	
		if( null != this.children ){
			cloned.children = (Vector<?>) this.children.clone();
		}
		
		return cloned;
		
	}
	
	@Override
	public Object cloneWithParent() {
		
		DriverNodeDataModel cloned = (DriverNodeDataModel) this.clone();
		
		//Le kell masolni a felmenoit is, egyebkent azok automatikusan null-ok
		cloned.setParent( (MutableTreeNode) this.getParent() );
		
		return cloned;
	}
}
