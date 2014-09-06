package hu.akoel.grawit.core.treenodedatamodel.driver;

import javax.swing.tree.MutableTreeNode;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.DriverDataModelInterface;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.exceptions.XMLCastPharseException;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;
import hu.akoel.grawit.exceptions.XMLMissingTagPharseException;

public class DriverFirefoxDataModel extends DriverDataModelInterface{

	private static final long serialVersionUID = 598870035128239461L;
	
	public static Tag TAG = Tag.DRIVERFIREFOX;
	
	public static final String ATTR_DETAILS = "details";
	
	private String name;
	private String details;
	
	public DriverFirefoxDataModel( String name, String details ){
		super( );
		this.name = name;
		this.details = details;
	}
	
	public DriverFirefoxDataModel( Element element ) throws XMLMissingAttributePharseException, XMLMissingTagPharseException, XMLCastPharseException{
		
		//tag
		if( !element.getTagName().equals( TAG.getName() ) ){
			Element parentElement = (Element)element.getParentNode();
			throw new XMLMissingTagPharseException( getRootTag().getName(), parentElement.getTagName(), parentElement.getAttribute( ATTR_NAME ), TAG.getName() );
		}	
		
		//Name
		if( !element.hasAttribute( ATTR_NAME ) ){
			throw new XMLMissingAttributePharseException( getRootTag(), TAG, ATTR_NAME );			
		}
		this.name = element.getAttribute( ATTR_NAME );		
		
		//Details
		if( !element.hasAttribute( ATTR_DETAILS ) ){
			throw new XMLMissingAttributePharseException( DriverNodeDataModel.getRootTag(), Tag.DRIVERNODE, ATTR_NAME, getName(), ATTR_DETAILS );			
		}		
		this.details = element.getAttribute( ATTR_DETAILS );		

		NodeList nodelist = element.getChildNodes();
		for( int i = 0; i < nodelist.getLength(); i++ ){
			Node node = nodelist.item( i );
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element driverElement = (Element)node;
				
				//Ha DRIVERFIREFOXPROPERTY van alatta
				if( driverElement.getTagName().equals( Tag.DRIVERFIREFOXPROPERTY.getName() )){

					this.add(new DriverFirefoxPropertyDataModel(driverElement));

				}
			}
		}
	}
	
	public static String getModelNameToShowStatic(){
		return CommonOperations.getTranslation( "tree.nodetype.driver.firefox" );
	}
	
	@Override
	public String getModelNameToShow(){
		return getModelNameToShowStatic();
	}

	public static Tag getTagStatic(){
		return TAG;
	}
	
	@Override
	public Tag getTag() {
		return getTagStatic();
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName( String name ){
		this.name = name;
	}
	
	public String getDetails(){
		return details;
	}
	
	public void setDetails( String details ){
		this.details = details;
	}

	@Override
	public Element getXMLElement(Document document) {
		Attr attr;
		
		//
		//Node element
		//
		Element elementFirefox = document.createElement( TAG.getName() );
		
		//Name
		attr = document.createAttribute( ATTR_NAME );
		attr.setValue( getName() );
		elementFirefox.setAttributeNode(attr);	
		
		//Details
		attr = document.createAttribute( ATTR_DETAILS );
		attr.setValue( getDetails() );
		elementFirefox.setAttributeNode(attr);	
		
		int childrens = this.getChildCount();
		for( int i = 0; i < childrens; i++ ){
			
			Object object = this.getChildAt( i );
			
			if( !object.equals(this) && object instanceof DriverDataModelInterface ){
				
				Element elementProperty = ((DriverDataModelInterface)object).getXMLElement( document );
				elementFirefox.appendChild( elementProperty );		    		
		    	
			}
		}
		
		return elementFirefox;	
	}

	@Override
	public void add( DriverDataModelInterface node ) {
		super.add( (MutableTreeNode)node );
	}

	@Override
	public WebDriver getDriver() {
		FirefoxProfile profile = new FirefoxProfile();

//TODO ide jonnek a profilok		
		
		return new FirefoxDriver(profile);
	}

}
