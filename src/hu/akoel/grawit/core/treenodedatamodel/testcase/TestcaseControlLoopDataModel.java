package hu.akoel.grawit.core.treenodedatamodel.testcase;

import java.io.StringReader;
import java.util.Vector;

import javax.swing.tree.MutableTreeNode;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.operations.ElementOperationAdapter;
import hu.akoel.grawit.core.treenodedatamodel.BaseDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.BaseElementDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.DataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.ParamDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.TestcaseDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseNodeDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.BasePageDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.NormalBaseElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.variable.VariableRootDataModel;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.exceptions.XMLBaseConversionPharseException;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;
import hu.akoel.grawit.exceptions.XMLPharseException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class TestcaseControlLoopDataModel extends TestcaseDataModelAdapter{

	private static final long serialVersionUID = 5361088361756620748L;

	private static final Tag TAG = Tag.TESTCASECONTROLLOOP;
	
	private static final String ATTR_COMPARE_BASE_ELEMENT_PATH = "compareelementabsolutepath";
	private static final String ATTR_OPERATION = "operation";
	private static final String ATTR_MAXLOOP = "maxloop";
	private static final String ATTR_ON = "on";
	
	//Adatmodel ---
	private String name;
	private BaseElementDataModelAdapter compareBaseElement;
	private ElementOperationAdapter elementOperation;
	private Integer maxLoop = null;
	//----
	
	public TestcaseControlLoopDataModel( String name, BaseElementDataModelAdapter compareBaseElement, Integer maxLoop, ElementOperationAdapter operation ){
		this.name = name;
		this.compareBaseElement = compareBaseElement;
		this.maxLoop = maxLoop;
		this.elementOperation = operation;
		
		//Engedelyezi a Node Ki/Be kapcsolasat
		this.setEnabledToTurnOnOff( true );

	}
	
	public TestcaseControlLoopDataModel( Element element, VariableRootDataModel variableRootDataModel, BaseRootDataModel baseRootDataModel, ParamDataModelAdapter paramDataModel ) throws XMLPharseException{
		
		BaseDataModelAdapter baseDataModel = baseRootDataModel;
		
		//Engedelyezi a Node Ki/Be kapcsolasat
		this.setEnabledToTurnOnOff( true );
		
		//========
		//
		// Name
		//
		//========
		if( !element.hasAttribute( ATTR_NAME ) ){
			throw new XMLMissingAttributePharseException( getRootTag(), TAG, ATTR_NAME );			
		}
		String nameString = element.getAttribute( ATTR_NAME );		
		this.name = nameString;
		
		//========
		//
		// On
		//
		//========		
		if( !element.hasAttribute( ATTR_ON ) ){
			this.setOn( Boolean.TRUE );
		}else{
			String onString = element.getAttribute( ATTR_ON );
			this.setOn( Boolean.parseBoolean( onString ));
		}				
		
		//========
		//
		// MaxLoop
		//
		//========		
		if( !element.hasAttribute( ATTR_MAXLOOP ) ){
			this.maxLoop = null;
		}else{
			String maxLoopString = element.getAttribute( ATTR_MAXLOOP );
			this.maxLoop = Integer.parseInt( maxLoopString );
		}				
		
		//=============
		//
		// BaseElement
		//
		//=============
		if( !element.hasAttribute( ATTR_COMPARE_BASE_ELEMENT_PATH ) ){
			throw new XMLMissingAttributePharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_COMPARE_BASE_ELEMENT_PATH );			
		}	
		String baseElementPathString = element.getAttribute(ATTR_COMPARE_BASE_ELEMENT_PATH);				
		baseElementPathString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + baseElementPathString;  
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
		DocumentBuilder builder;
		Document document = null;
		try {  
			builder = factory.newDocumentBuilder();  
			document = builder.parse( new InputSource( new StringReader( baseElementPathString ) ) );  
		} catch (Exception e) {  
	    
			//Nem sikerult az atalakitas
				throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_COMPARE_BASE_ELEMENT_PATH, element.getAttribute(ATTR_COMPARE_BASE_ELEMENT_PATH), e );
		} 
	    
		
		//Megkeresem a BASEROOT-ban az utvonalat a BASEELEMENT-hez
		Node actualNode = document;
		while( actualNode.hasChildNodes() ){
		
			actualNode = actualNode.getFirstChild();
			Element actualElement = (Element)actualNode;
			String tagName = actualElement.getTagName();
			String attrName = actualElement.getAttribute(BaseNodeDataModel.ATTR_NAME);	  
	    	
			//Ha BASENODE
			if( tagName.equals( BaseNodeDataModel.TAG.getName() ) ){
				baseDataModel = (BaseDataModelAdapter) CommonOperations.getDataModelByNameInLevel( baseDataModel, Tag.BASENODE, attrName );

				if( null == baseDataModel ){

					throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_COMPARE_BASE_ELEMENT_PATH, element.getAttribute(ATTR_COMPARE_BASE_ELEMENT_PATH) );
				}
					
			//Ha BASEPAGE
			}else if( tagName.equals( BasePageDataModel.TAG.getName() ) ){
				baseDataModel = (BaseDataModelAdapter) CommonOperations.getDataModelByNameInLevel( baseDataModel, Tag.BASEPAGE, attrName );
				if( null == baseDataModel ){

					throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_COMPARE_BASE_ELEMENT_PATH, element.getAttribute(ATTR_COMPARE_BASE_ELEMENT_PATH) );
				}
					
			//Ha BASELEMENT
			}else if( tagName.equals( NormalBaseElementDataModel.TAG.getName() ) ){
  
				baseDataModel = (BaseDataModelAdapter) CommonOperations.getDataModelByNameInLevel( baseDataModel, Tag.BASEELEMENT, attrName );

				if( null == baseDataModel ){

					throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_COMPARE_BASE_ELEMENT_PATH, element.getAttribute(ATTR_COMPARE_BASE_ELEMENT_PATH) );	    		
				}
	    		
				try{
	    	    	
					this.compareBaseElement = (BaseElementDataModelAdapter) baseDataModel;
	    	    	
				}catch(ClassCastException e){

					throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_COMPARE_BASE_ELEMENT_PATH, element.getAttribute(ATTR_COMPARE_BASE_ELEMENT_PATH), e );
				}
	    						    		
			}
	    	
		}
		
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
				
				//Ha TESTCASEPARAM van alatta
				if( testcaseElement.getTagName().equals( Tag.TESTCASEPARAMPAGE.getName() )){
					
					this.add(new TestcaseParamPageDataModel(testcaseElement, paramDataModel ));
					
				}
			}
		}
		
		elementOperation = CommonOperations.getElementOperation( element, compareBaseElement, (DataModelAdapter)this, elementOperation, getRootTag(), ATTR_OPERATION, variableRootDataModel );
		
	}
	
	
	public static Tag getTagStatic(){
		return TAG;
	}

	@Override
	public Tag getTag() {
		//return TAG;
		return getTagStatic();
	}

	public void setName( String name ){
		this.name = name;
	}
	
	@Override
	public String getName(){
		return this.name;
	}
	
	public static String  getModelNameToShowStatic(){
		return CommonOperations.getTranslation( "tree.nodetype.testcase.control.loop");
	}
	
	@Override
	public String getNodeTypeToShow(){
		return getModelNameToShowStatic();
	}
	
	public ElementOperationAdapter getElementOperation() {
		return elementOperation;
	}

	public void setOperation(ElementOperationAdapter elementOperation) {
		this.elementOperation = elementOperation;
	}	

	public void setCompareBaseElement( BaseElementDataModelAdapter compareBaseElement ){
		this.compareBaseElement = compareBaseElement;
	}
	
	public BaseElementDataModelAdapter getCompareBaseElement(){
		return compareBaseElement;
	}
	
	@Override
	public Element getXMLElement(Document document) {
		Attr attr;
	
		//========
		//
		//Node element
		//
		//========
		Element elementElement = document.createElement( TAG.getName() );
		
		//========
		//
		//Name
		//
		//========
		attr = document.createAttribute( ATTR_NAME );
		attr.setValue( getName() );
		elementElement.setAttributeNode(attr);	

		//========
		//
		//BaseElementAbsolutePath
		//
		//========
		attr = document.createAttribute( ATTR_COMPARE_BASE_ELEMENT_PATH );
		attr.setValue( compareBaseElement.getPathTag() );
		elementElement.setAttributeNode(attr);

		//========
		//
		//Operation
		//
		//========
		attr = document.createAttribute( ATTR_OPERATION );
		attr.setValue( getElementOperation().getName() );
		elementElement.setAttributeNode(attr);
		
		//========
		//
		// On
		//
		//========
		attr = document.createAttribute( ATTR_ON );
		attr.setValue( this.isOn().toString() );
		elementElement.setAttributeNode(attr);
		
		
		int childrens = this.getChildCount();
		for( int i = 0; i < childrens; i++ ){
			
			Object object = this.getChildAt( i );
			
			if( !object.equals(this) && object instanceof TestcaseDataModelAdapter ){
				
				Element element = ((TestcaseDataModelAdapter)object).getXMLElement( document );
				elementElement.appendChild( element );		    		
		    	
			}
		}
		
		//Minden Operation a sajat attributumaiert felelos
		getElementOperation().setXMLAttribute( document, elementElement );
				
		return elementElement;	
	}	

	@Override
	public Object clone(){
		
		//Leklonozza a NODE-ot
		TestcaseControlLoopDataModel cloned = (TestcaseControlLoopDataModel)super.clone();
		
		//Ha vannak gyerekei (NODE vagy CASE)
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
	
		cloned.compareBaseElement = (BaseElementDataModelAdapter) this.compareBaseElement.clone();
		cloned.elementOperation = (ElementOperationAdapter) this.elementOperation.clone();
		
		return cloned;
		
	}

	@Override
	public Object cloneWithParent() {
		
		ParamElementDataModel cloned = (ParamElementDataModel) this.clone();
		
		//Le kell masolni a felmenoit is, egyebkent azok automatikusan null-ok
		cloned.setParent( (MutableTreeNode) this.getParent() );
			
		return cloned;
	}
	
	@Override
	public void add( TestcaseDataModelAdapter node ) {
		super.add( (MutableTreeNode)node );
	}
	

}
