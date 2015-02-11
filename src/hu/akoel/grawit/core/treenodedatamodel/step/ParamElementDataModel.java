package hu.akoel.grawit.core.treenodedatamodel.step;

import java.io.StringReader;

import javax.swing.tree.MutableTreeNode;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.openqa.selenium.WebDriver;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.operations.ElementOperationAdapter;
import hu.akoel.grawit.core.treenodedatamodel.BaseDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.BaseElementDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.DataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.ParamDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseCollectorDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseFolderDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.NormalBaseElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.variable.VariableRootDataModel;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.exceptions.CompilationException;
import hu.akoel.grawit.exceptions.ElementException;
import hu.akoel.grawit.exceptions.XMLBaseConversionPharseException;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;
import hu.akoel.grawit.exceptions.XMLPharseException;
import hu.akoel.grawit.gui.interfaces.progress.ElementProgressInterface;

public class ParamElementDataModel extends ParamDataModelAdapter {
	
	private static final long serialVersionUID = -8916078747948054716L;

	public static Tag TAG = Tag.PARAMELEMENT;
	
	private static final String ATTR_BASE_ELEMENT_PATH = "baseelementabsolutepath";
	private static final String ATTR_OPERATION = "operation";
	private static final String ATTR_ON = "on";
	
	//Adatmodel ---
	private String name;
	private BaseElementDataModelAdapter baseElement;
	private ElementOperationAdapter elementOperation;
	//----

	/**
	 * 
	 * Modify 
	 * 
	 * @param name
	 * @param baseElement
	 * @param operation
	 */
	public ParamElementDataModel( String name, BaseElementDataModelAdapter baseElement, ElementOperationAdapter operation ){
		this.name = name;
		this.baseElement = baseElement;
		this.elementOperation = operation;
		
		//Engedelyezi a Node Ki/Be kapcsolasat
		this.setEnabledToTurnOnOff( true );
		
/*		//A gyujtoben beallitja az utoljara vegrehajtott baseElement eleresi utvonalat
		//Ezt a szulojetol vaszi, ha az Collector		
		MutableTreeNode mtn = (MutableTreeNode)this.getParent();
		if( mtn instanceof ParamCollectorDataModelAdapter ){
			((ParamCollectorDataModelAdapter)mtn).setLastBaseElement( baseElement );
		}
*/
		
	}
	
	/**
	 * Capture new
	 * 
	 * XML alapjan gyartja le a PARAMELEMENT-et
	 * 
	 * @param element
	 * @param baseDataModel
	 * @param variableDataModel
	 * @throws XMLPharseException
	 */
	public ParamElementDataModel( Element element, BaseRootDataModel baseRootDataModel, VariableRootDataModel variableRootDataModel ) throws XMLPharseException{

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
		
		//=============
		//
		// BaseElement
		//
		//=============
		if( !element.hasAttribute( ATTR_BASE_ELEMENT_PATH ) ){
			throw new XMLMissingAttributePharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_BASE_ELEMENT_PATH );			
		}	
		String baseElementPathString = element.getAttribute(ATTR_BASE_ELEMENT_PATH);				
		baseElementPathString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + baseElementPathString;  
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
		DocumentBuilder builder;
		Document document = null;
		try {  
			builder = factory.newDocumentBuilder();  
			document = builder.parse( new InputSource( new StringReader( baseElementPathString ) ) );  
		} catch (Exception e) {  
	    
			//Nem sikerult az atalakitas
			throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_BASE_ELEMENT_PATH, element.getAttribute(ATTR_BASE_ELEMENT_PATH), e );
		} 
	    
		//Megkeresem a BASEROOT-ban az utvonalat a BASEELEMENT-hez
		Node actualNode = document;
		while( actualNode.hasChildNodes() ){
		
			actualNode = actualNode.getFirstChild();
			Element actualElement = (Element)actualNode;
			String tagName = actualElement.getTagName();
			String attrName = actualElement.getAttribute(BaseFolderDataModel.ATTR_NAME);	  
	    	
			//Ha BASENODE
			if( tagName.equals( BaseFolderDataModel.TAG.getName() ) ){
				baseDataModel = (BaseDataModelAdapter) CommonOperations.getDataModelByNameInLevel( baseDataModel, Tag.BASEFOLDER, attrName );

				if( null == baseDataModel ){

					throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_BASE_ELEMENT_PATH, element.getAttribute(ATTR_BASE_ELEMENT_PATH) );
				}
					
			//Ha BASEPAGE
			}else if( tagName.equals( BaseCollectorDataModel.TAG.getName() ) ){
				baseDataModel = (BaseDataModelAdapter) CommonOperations.getDataModelByNameInLevel( baseDataModel, Tag.BASECOLLECTOR, attrName );
				if( null == baseDataModel ){

					throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_BASE_ELEMENT_PATH, element.getAttribute(ATTR_BASE_ELEMENT_PATH) );
				}
					
			//Ha BASELEMENT
			}else if( tagName.equals( NormalBaseElementDataModel.TAG.getName() ) ){
  
				baseDataModel = (BaseDataModelAdapter) CommonOperations.getDataModelByNameInLevel( baseDataModel, Tag.BASEELEMENT, attrName );

				if( null == baseDataModel ){

					throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_BASE_ELEMENT_PATH, element.getAttribute(ATTR_BASE_ELEMENT_PATH) );	    		
				}
	    		
				try{
	    	    	
					this.baseElement = (BaseElementDataModelAdapter) baseDataModel;
	    	    	
				}catch(ClassCastException e){

					throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_BASE_ELEMENT_PATH, element.getAttribute(ATTR_BASE_ELEMENT_PATH), e );
				}	    						    		
			}	    	
		}
		
//TODO duplikacio megszuntetese. ugyan ez van a SpecialElementTypeComponent.java-ban TestCaseControlLoop is, csak egy kicsit maskent	    
	    
		//=============================
		//
		// Operation a muvelet alapjan
		//
		//=============================
		if( !element.hasAttribute( ATTR_OPERATION ) ){
			throw new XMLMissingAttributePharseException( getRootTag(), TAG, ATTR_OPERATION );			
		}
		String operationString = element.getAttribute( ATTR_OPERATION );		

		elementOperation = CommonOperations.getElementOperation( element, baseElement, (DataModelAdapter)this, elementOperation, getRootTag(), ATTR_OPERATION, variableRootDataModel );

	}

	/**
	 * 
	 * Executes the defined Operation with the defined Parameter
	 * @throws ElementException 
	 * 
	 */
	public void doAction( WebDriver driver, ElementProgressInterface elementProgress ) throws ElementException, CompilationException{
		this.getElementOperation().doAction( driver, this.getBaseElement(), elementProgress );
	}
	
	public void setName( String name ){
		this.name = name;
	}
	
	@Override
	public String getName(){
		return this.name;
	}
	
	public ElementOperationAdapter getElementOperation() {
		return elementOperation;
	}

	public void setOperation(ElementOperationAdapter elementOperation) {
		this.elementOperation = elementOperation;
	}	

	/**
	 * Az Elemhez rendel egy BaseElement-et - kotelezo
	 * @param baseElement
	 */
	public void setBaseElement( BaseElementDataModelAdapter baseElement ){
		this.baseElement = baseElement;
		
		//A Gyujtoben beallitja az utoljara vegrehajtott baseElement eleresi utvonalat
		MutableTreeNode mtn = (MutableTreeNode)this.getParent();
		if( mtn instanceof ParamCollectorDataModelAdapter ){
			((ParamCollectorDataModelAdapter)mtn).setLastBaseElement( baseElement );
		}
		
	}
	
	public BaseElementDataModelAdapter getBaseElement(){
		return baseElement;
	}
	
	@Override
	public void add( ParamDataModelAdapter node ) {
		super.add( (MutableTreeNode)node );
	}
	
	public static Tag getTagStatic(){
		return TAG;
	}

	@Override
	public Tag getTag(){
		return getTagStatic();
	}
	
	public static String  getModelNameToShowStatic(){
		return CommonOperations.getTranslation( "tree.nodetype.param.element");
	}
	
	@Override
	public String getNodeTypeToShow(){
		return getModelNameToShowStatic();
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
		attr = document.createAttribute( ATTR_BASE_ELEMENT_PATH );
		attr.setValue( baseElement.getPathTag() );
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
		
		//Minden Operation a sajat attributumaiert felelos
		getElementOperation().setXMLAttribute( document, elementElement );
				
		return elementElement;	
	}
	
	@Override
	public Object clone(){
		
		ParamElementDataModel cloned = (ParamElementDataModel)super.clone();

		//Leklonozza az Operation-t is
		cloned.elementOperation = (ElementOperationAdapter) this.elementOperation.clone();

		//Le kell masolni a felmenoit is, egyebkent azok automatikusan null-ok
		cloned.setParent( (MutableTreeNode) this.getParent() );
		
		return cloned;
		
	}
	
}
