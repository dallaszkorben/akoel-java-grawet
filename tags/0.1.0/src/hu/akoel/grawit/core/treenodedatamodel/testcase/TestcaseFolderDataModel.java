package hu.akoel.grawit.core.treenodedatamodel.testcase;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.DriverDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.variable.VariableRootDataModel;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;
import hu.akoel.grawit.exceptions.XMLPharseException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class TestcaseFolderDataModel extends TestcaseNodeDataModelAdapter{

	private static final long serialVersionUID = -2139557326147525999L;
	
	public static final Tag TAG = Tag.TESTCASEFOLDER;
	
	public TestcaseFolderDataModel( String name, String details ){
		super( name, details );
	}
	
	/**
	 * XML alapjan legyartja a TESTCASENODE-ot es az alatta elofordulo 
	 * TESTCASENODE-okat, illetve TESTCASECASE-eket
	 * 
	 * @param element
	 * @throws XMLMissingAttributePharseException 
	 */
	public TestcaseFolderDataModel( Element element, VariableRootDataModel variableRootDataModel, BaseRootDataModel baseRootDataModel, ParamRootDataModel paramRootDataModel, DriverDataModelAdapter driverRootDataModel ) throws XMLPharseException{

		super( element, variableRootDataModel, baseRootDataModel, paramRootDataModel, driverRootDataModel  );
		
/*		//========
		//
		// Name
		//
		//========	
		if( !element.hasAttribute( ATTR_NAME ) ){
			throw new XMLMissingAttributePharseException( TestcaseFolderDataModel.getRootTag(), Tag.TESTCASENODE, ATTR_NAME );			
		}
		String nameString = element.getAttribute( ATTR_NAME );
		this.name = nameString;
	
		//========
		//
		// Details
		//
		//========	
		if( !element.hasAttribute( ATTR_DETAILS ) ){
			throw new XMLMissingAttributePharseException( TestcaseFolderDataModel.getRootTag(), Tag.TESTCASENODE, ATTR_NAME, getName(), ATTR_DETAILS );			
		}		
		String detailsString = element.getAttribute( ATTR_DETAILS );		
		this.details = detailsString;
*/		
		//========
		//
		// Gyermekei
		//
		//========	
		NodeList nodelist = element.getChildNodes();
		for( int i = 0; i < nodelist.getLength(); i++ ){
			Node node = nodelist.item( i );
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element testcaseElement = (Element)node;
				
				//Ha TESTCASECASE van alatta
				if( testcaseElement.getTagName().equals( Tag.TESTCASECASE.getName() )){
					this.add(new TestcaseCaseDataModel(testcaseElement, variableRootDataModel, baseRootDataModel, paramRootDataModel, driverRootDataModel ));
				
				//Ha ujabb TESTCASEFOLDER van alatta
				}else if( testcaseElement.getTagName().equals( Tag.TESTCASEFOLDER.getName() )){
					this.add(new TestcaseFolderDataModel(testcaseElement, variableRootDataModel, baseRootDataModel, paramRootDataModel, driverRootDataModel ));
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
	public void add(TestcaseDataModelAdapter node) {
		super.add( (MutableTreeNode)node );
	}
*/	
	
	public static String  getModelNameToShowStatic(){
		return CommonOperations.getTranslation( "tree.nodetype.testcase.folder");
	}
	
	@Override
	public String getNodeTypeToShow(){
		return getModelNameToShowStatic();
	}
/*	
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
*/
	@Override
	public Element getXMLElement(Document document) {
		Attr attr;

		Element nodeElement = super.getXMLElement(document);
		
/*		//========
		//
		// Name
		//
		//========	
		Element nodeElement = document.createElement( TestcaseFolderDataModel.this.getTag().getName() );
		attr = document.createAttribute( ATTR_NAME );
		attr.setValue( getName() );
		nodeElement.setAttributeNode(attr);	
		
		//========
		//
		// Details
		//
		//========	
		attr = document.createAttribute( ATTR_DETAILS );
		attr.setValue( getDetails() );
		nodeElement.setAttributeNode(attr);	
*/	
		
/*		
		//========
		//
		// Gyermekei
		//
		//========	
		int childrens = this.getChildCount();
		for( int i = 0; i < childrens; i++ ){
			
			Object object = this.getChildAt( i );
			
			if( !object.equals(this) && object instanceof TestcaseDataModelAdapter ){
				
				Element element = ((TestcaseDataModelAdapter)object).getXMLElement( document );
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
		TestcaseFolderDataModel cloned = (TestcaseFolderDataModel)super.clone();
	
		//Ha vannak gyerekei 
		if( null != this.children ){
			
			//Akkor azokat is leklonozza
			cloned.children = new Vector<>();
			
			for( Object o : this.children ){
				
				if( o instanceof TestcaseDataModelAdapter ){					
					
					TestcaseDataModelAdapter child = (TestcaseDataModelAdapter) ((TestcaseDataModelAdapter)o).clone();
					
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
		
		TestcaseFolderDataModel cloned = (TestcaseFolderDataModel) this.clone();
		
		//Le kell masolni a felmenoit is, egyebkent azok automatikusan null-ok
		cloned.setParent( (MutableTreeNode) this.getParent() );
		
		return cloned;
	}
*/	
}
