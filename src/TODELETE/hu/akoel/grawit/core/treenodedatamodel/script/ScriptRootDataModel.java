package TODELETE.hu.akoel.grawit.core.treenodedatamodel.script;

import java.util.Vector;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.ScriptDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.variable.VariableElementDataModel;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.exceptions.XMLExtraRootTagPharseException;
import hu.akoel.grawit.exceptions.XMLPharseException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ScriptRootDataModel extends ScriptNodeDataModel{

	private static final long serialVersionUID = 5361088361756620748L;

	private static final Tag TAG = Tag.SCRIPTROOT;
	
	public static final String ATTR_NAME = "";
	
	public ScriptRootDataModel(){
		super( "", "" );
	}
	
	public ScriptRootDataModel( Document doc ) throws XMLPharseException{
		super("","");
		
		NodeList nList = doc.getElementsByTagName( TAG.getName() );
		
		//Ha tobb mint  1 db basepage tag van, akkor az gaz
		if( nList.getLength() > 1 ){
					
			throw new XMLExtraRootTagPharseException( TAG );
					
		}else if( nList.getLength() == 1 ){
		
			Node scriptNode = nList.item(0);
			if (scriptNode.getNodeType() == Node.ELEMENT_NODE) {
			
				NodeList nodeList = scriptNode.getChildNodes();
				for( int i = 0; i < nodeList.getLength(); i++ ){
			
					Node baseNode = nodeList.item( i );
				
					if (baseNode.getNodeType() == Node.ELEMENT_NODE) {
						Element scriptElement = (Element)baseNode;
					
						//Ha ujabb SCRIPTNODE van alatta
						if( scriptElement.getTagName().equals( Tag.SCRIPTNODE.getName() ) ){
							this.add(new ScriptNodeDataModel(scriptElement));
							
						//Ha rogton a rootban van elhelyezve egy elem
						}else if( scriptElement.getTagName().equals( Tag.SCRIPTELEMENT.getName() ) ){
							this.add(new ScriptElementDataModel(scriptElement ));

						}
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

	@Override
	public String getName(){
		return "Script Root";
	}
	
	@Override
	public String getNodeTypeToShow(){
		return CommonOperations.getTranslation( "tree.nodetype.script.root");
	}
	
	@Override
	public Element getXMLElement(Document document) {
		
		//ScriptElement
		Element scriptElement = document.createElement( TAG.getName() );

		int childrens = this.getChildCount();
		for( int i = 0; i < childrens; i++ ){
			
			Object object = this.getChildAt( i );
			
			if( !object.equals(this) && object instanceof ScriptDataModelAdapter ){
				
				Element element = ((ScriptDataModelAdapter)object).getXMLElement( document );
				scriptElement.appendChild( element );		    		
		    	
			}
		}

		return scriptElement;		
	}
	
	@Override
	public Object clone(){
		
		ScriptRootDataModel cloned = (ScriptRootDataModel)super.clone();
	
		if( null != this.children ){
			cloned.children = (Vector<?>) this.children.clone();
		}
		
		return cloned;
		
	}
}
