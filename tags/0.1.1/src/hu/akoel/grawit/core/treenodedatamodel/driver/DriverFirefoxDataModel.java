package hu.akoel.grawit.core.treenodedatamodel.driver;

import java.util.Vector;

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
import hu.akoel.grawit.core.treenodedatamodel.DriverDataModelAdapter;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.exceptions.XMLCastPharseException;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;
import hu.akoel.grawit.exceptions.XMLMissingTagPharseException;
import hu.akoel.grawit.gui.interfaces.progress.ElementProgressInterface;

public class DriverFirefoxDataModel extends DriverBrowserDataModelInterface<DriverFirefoxPropertyDataModel>{

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
			throw new XMLMissingAttributePharseException( DriverFolderDataModel.getRootTag(), Tag.DRIVERFOLDER, ATTR_NAME, getName(), ATTR_DETAILS );			
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
	public String getNodeTypeToShow(){
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
			
			if( !object.equals(this) && object instanceof DriverDataModelAdapter ){
				
				Element elementProperty = ((DriverDataModelAdapter)object).getXMLElement( document );
				elementFirefox.appendChild( elementProperty );		    		
		    	
			}
		}
		
		return elementFirefox;	
	}

	@Override
//	public void add( DriverDataModelInterface node ) {
	public void add( DriverFirefoxPropertyDataModel node ) {	
		super.add( (MutableTreeNode)node );
	}

	@Override
	public WebDriver getDriver( ElementProgressInterface elementProgres ) {
		FirefoxProfile profile = new FirefoxProfile();

elementProgres.outputCommand( "		profile = new FirefoxProfile();");		
		
		int childCount = getChildCount();
		for( int index = 0; index < childCount; index++ ){
			String key = ((DriverFirefoxPropertyDataModel)getChildAt(index)).getName();
			Object value = ((DriverFirefoxPropertyDataModel)getChildAt(index)).getValue();
			if( value instanceof String ){
elementProgres.outputCommand( "		profile.setPreference( \"" + key + "\", \"" + (String)value + "\" );");					
				profile.setPreference(key, (String)value );				
			}else if( value instanceof Boolean ){
elementProgres.outputCommand( "		profile.setPreference( \"" + key + "\", " + (Boolean)value + " );");
				profile.setPreference(key, (Boolean)value );
			}else if( value instanceof Integer ){
elementProgres.outputCommand( "		profile.setPreference( \"" + key + "\", " + (Integer)value + " );");
				profile.setPreference(key, (Integer)value );
			}
		}		

elementProgres.outputCommand( "		driver = new FirefoxDriver(profile);");
//TODO ide jonnek a profilok		
		
		return new FirefoxDriver(profile);
	}

	@Override
	public Object clone(){
		
		DriverFirefoxDataModel cloned = (DriverFirefoxDataModel)super.clone();
	
		if( null != this.children ){
			cloned.children = (Vector<?>) this.children.clone();
		}
		
		return cloned;
		
	}
	
	@Override
	public Object cloneWithParent() {
		
		DriverFirefoxDataModel cloned = (DriverFirefoxDataModel) this.clone();
		
		//Le kell masolni a felmenoit is, egyebkent azok automatikusan null-ok
		cloned.setParent( (MutableTreeNode) this.getParent() );
		
		return cloned;
	}
}
