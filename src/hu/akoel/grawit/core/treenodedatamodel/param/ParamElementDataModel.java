package hu.akoel.grawit.core.treenodedatamodel.param;

import java.io.StringReader;
import java.util.Vector;

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
import hu.akoel.grawit.ElementProgressInterface;
import hu.akoel.grawit.core.operations.ClearOperation;
import hu.akoel.grawit.core.operations.ClickOperation;
import hu.akoel.grawit.core.operations.ElementOperationInterface;
import hu.akoel.grawit.core.operations.FillBaseElementOperation;
import hu.akoel.grawit.core.operations.FillStringOperation;
import hu.akoel.grawit.core.operations.FillVariableElementOperation;
import hu.akoel.grawit.core.operations.GainTextPatternOperation;
import hu.akoel.grawit.core.operations.SelectBaseElementOperation;
import hu.akoel.grawit.core.operations.SelectStringOperation;
import hu.akoel.grawit.core.operations.SelectVariableElementOperation;
import hu.akoel.grawit.core.operations.TabOperation;
import hu.akoel.grawit.core.treenodedatamodel.BaseDataModelInterface;
import hu.akoel.grawit.core.treenodedatamodel.DataModelInterface;
import hu.akoel.grawit.core.treenodedatamodel.ParamDataModelInterface;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.variable.VariableRootDataModel;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.enums.list.ElementTypeListEnum;
import hu.akoel.grawit.exceptions.ElementException;
import hu.akoel.grawit.exceptions.XMLBaseConversionPharseException;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;
import hu.akoel.grawit.exceptions.XMLPharseException;
import hu.akoel.grawit.exceptions.XMLWrongAttributePharseException;

public class ParamElementDataModel extends ParamDataModelInterface {
	
	private static final long serialVersionUID = -8916078747948054716L;

	public static Tag TAG = Tag.PARAMELEMENT;
	
	private static final String ATTR_BASE_ELEMENT_PATH = "baseelementpath";
	private static final String ATTR_OPERATION = "operation";
	
	//Adatmodel ---
	private String name;
	private BaseElementDataModel baseElement;
	private ElementOperationInterface elementOperation;
	//----

	/**
	 * 
	 * Modify 
	 * 
	 * @param name
	 * @param baseElement
	 * @param operation
	 */
	public ParamElementDataModel( String name, BaseElementDataModel baseElement, ElementOperationInterface operation){
		this.name = name;
		this.baseElement = baseElement;
		this.elementOperation = operation;		
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
	public ParamElementDataModel( Element element, BaseDataModelInterface baseDataModel, VariableRootDataModel variableRootDataModel ) throws XMLPharseException{

		//VariableDataModelInterface variableDataModel = (VariableDataModelInterface)variableRootDataModel;
		//BaseDataModelInterface baseDataModelForFieldParamElement = (BaseDataModelInterface)baseDataModel.getRoot();;
		
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
	    try  
	    {  
	        builder = factory.newDocumentBuilder();  
	        document = builder.parse( new InputSource( new StringReader( baseElementPathString ) ) );  
	    } catch (Exception e) {  
	    
	    	//Nem sikerult az atalakitas
	    	throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_BASE_ELEMENT_PATH, element.getAttribute(ATTR_BASE_ELEMENT_PATH), e );
	    } 
	    
		//Megkeresem a BASEROOT-ban az utvonalat az BASEELEMENT-hez
	    Node actualNode = document;
	    if( actualNode.hasChildNodes() ){
		
	    	actualNode = actualNode.getFirstChild();
	    	Element actualElement = (Element)actualNode;
	    	String tagName = actualElement.getTagName();
	    	
	    	//Ha ELEMENT
	    	if( tagName.equals( BaseElementDataModel.TAG.getName() ) ){
	    		String attrName = actualElement.getAttribute(BaseElementDataModel.ATTR_NAME);	    		
	    		baseDataModel = (BaseDataModelInterface) CommonOperations.getDataModelByNameInLevel( baseDataModel, BaseElementDataModel.TAG, attrName );

	    		if( null == baseDataModel ){

	    			throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_BASE_ELEMENT_PATH, element.getAttribute(ATTR_BASE_ELEMENT_PATH) );	    		}
	    		
	    	}else{
	    		throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_BASE_ELEMENT_PATH, element.getAttribute(ATTR_BASE_ELEMENT_PATH) );	    		
	    	}
	    }else{
	    	throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_BASE_ELEMENT_PATH, element.getAttribute(ATTR_BASE_ELEMENT_PATH) );
    	}
	    
	    try{
	    	
	    	this.baseElement = (BaseElementDataModel)baseDataModel;
	    	
	    }catch(ClassCastException e){

	    	throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_BASE_ELEMENT_PATH, element.getAttribute(ATTR_BASE_ELEMENT_PATH), e );
	    }	
	    
	    
		//=============================
		//
		// Operation a muvelet alapjan
		//
		//=============================
		if( !element.hasAttribute( ATTR_OPERATION ) ){
			throw new XMLMissingAttributePharseException( getRootTag(), TAG, ATTR_OPERATION );			
		}
		String operationString = element.getAttribute( ATTR_OPERATION );		
		
		//
		// A tipus az erosebb. Ha a tipushoz nem megfelelo operation van rendelve, akkor egy 
		// alapertelmezett operationt kap
		//
		//-------------
		// Link	a tipus
		//-------------
		if( baseElement.getElementType().equals( ElementTypeListEnum.LINK ) ){
			
			// Click az operation
			if( operationString.equals( ClickOperation.getStaticName() ) ){
				
				elementOperation = new ClickOperation();
				
			// Gain text az operation
			}else if( operationString.equals( GainTextPatternOperation.getStaticName() ) ){
				
				elementOperation = new GainTextPatternOperation( element, getRootTag(), getTag() );
				
			//Ha nem a tipusnak megfelelo a muvelet, akkor azt Click-nek vesszuk
			}else{
				
				elementOperation = new ClickOperation();
			}
		
		//---------------
		// Button a tipus
		//---------------
		}else if( baseElement.getElementType().equals(ElementTypeListEnum.BUTTON)){
			
			//Click az operation
			if( operationString.equals( ClickOperation.getStaticName() ) ){

				elementOperation = new ClickOperation();
				
			//Ha nem a tipusnak megfelelo az muvelet, akkor is Click az operation
			}else{
					
				elementOperation = new ClickOperation();				
			}
			
		//-----------------
		// Checkbox a tipus
		//-----------------
		}else if( baseElement.getElementType().equals( ElementTypeListEnum.CHECKBOX) ){
			
			//Click az operation
			if( operationString.equals( ClickOperation.getStaticName() ) ){
				
				elementOperation = new ClickOperation();
				
			//Ha nem a tipusnak megfelelo az muvelet, akkor is Click az operation
			}else{
				
				elementOperation = new ClickOperation();
				
			}
			
		//---------------------
		// Radio button a tipus
		//---------------------
		}else if( baseElement.getElementType().equals( ElementTypeListEnum.RADIOBUTTON ) ){
			
			if( operationString.equals( ClickOperation.getStaticName() ) ){
				
				elementOperation = new ClickOperation();
				
			//Ha nem a tipusnak megfelelo az muvelet, akkor is Click az operation
			}else{
					
				elementOperation = new ClickOperation();
					
			}
		
		//--------------
		// Field a tipus
		//--------------
		}else if( baseElement.getElementType().equals( ElementTypeListEnum.FIELD ) ){
			
			if( operationString.equals( ClearOperation.getStaticName() ) ){
			
				elementOperation = new ClearOperation();
				
			}else if( operationString.equals( ClickOperation.getStaticName() ) ){
				
				elementOperation = new ClickOperation();
				
			}else if( operationString.equals( TabOperation.getStaticName() ) ){
				
				elementOperation = new TabOperation();
				
			}else if( operationString.equals( FillVariableElementOperation.getStaticName() ) ){
				
				elementOperation = new FillVariableElementOperation( element, variableRootDataModel, getRootTag(), getTag(), ATTR_NAME, getName() );
				
			}else if( operationString.equals( FillBaseElementOperation.getStaticName() ) ){
				
				elementOperation = new FillBaseElementOperation( element, (BaseRootDataModel)baseElement.getRoot(), getRootTag(), getTag(), ATTR_NAME, getName() );
				
			}else if( operationString.equals( FillStringOperation.getStaticName() ) ){
				
				elementOperation = new FillStringOperation( element, getRootTag(), getTag() );
				
			//Ha nem a tipusnak megfelelo az muvelet, akkor Clear lesz a muvelet
			}else{
					
				elementOperation = new ClearOperation();
			}
		
		//---------
		// Text
		//---------
		}else if( baseElement.getElementType().equals( ElementTypeListEnum.TEXT ) ){
			
			if( operationString.equals( GainTextPatternOperation.getStaticName() ) ){
				
				elementOperation = new GainTextPatternOperation( element, getRootTag(), getTag() );
				
			//Ha nem a tipusnak megfelelo az muvelet, akkor is gaintext a muvelet
			}else{
				
				elementOperation = new GainTextPatternOperation( element, getRootTag(), getTag() );
				
			}
			
		//---------
		// List
		//---------
		}else if( baseElement.getElementType().equals( ElementTypeListEnum.LIST ) ){
						
			if( operationString.equals( SelectVariableElementOperation.getStaticName() ) ){
				
				elementOperation = new SelectVariableElementOperation( element, variableRootDataModel, getRootTag(), getTag(), ATTR_NAME, getName() );
				
			}else if( operationString.equals( SelectBaseElementOperation.getStaticName() ) ){
				
				elementOperation = new SelectBaseElementOperation( element, (BaseRootDataModel)baseElement.getRoot(), getRootTag(), getTag(), ATTR_NAME, getName() );
		
			}else if( operationString.equals( SelectStringOperation.getStaticName() ) ){
				
				elementOperation = new SelectStringOperation( element, getRootTag(), getTag(), ATTR_NAME, getName() );
			}
			
		//Minden egyeb esetben error
		}else{
			throw new XMLWrongAttributePharseException( BaseDataModelInterface.getRootTag(), BaseElementDataModel.TAG, DataModelInterface.ATTR_NAME, baseElement.getName(), BaseElementDataModel.ATTR_ELEMENT_TYPE, baseElement.getElementType().name() );
		}

	}

	/**
	 * 
	 * Executes the defined Operation with the defined Parameter
	 * @throws ElementException 
	 * 
	 */
	public void doAction( WebDriver driver, ElementProgressInterface elementProgress ) throws ElementException{
		this.getElementOperation().doAction( driver, this, elementProgress );
	}
	
	public void setName( String name ){
		this.name = name;
	}
	
	@Override
	public String getName(){
		return this.name;
	}
	
	public ElementOperationInterface getElementOperation() {
		return elementOperation;
	}

	public void setOperation(ElementOperationInterface elementOperation) {
		this.elementOperation = elementOperation;
	}	

	public void setBaseElement( BaseElementDataModel baseElement ){
		this.baseElement = baseElement;
	}
	
	public BaseElementDataModel getBaseElement(){
		return baseElement;
	}
	
	@Override
	public void add( ParamDataModelInterface node ) {
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
	
		//
		//Node element
		//
		Element elementElement = document.createElement( TAG.getName() );
		
		//Name
		attr = document.createAttribute( ATTR_NAME );
		attr.setValue( getName() );
		elementElement.setAttributeNode(attr);	
		
		//BaseElementPath
		attr = document.createAttribute( ATTR_BASE_ELEMENT_PATH );
		//attr.setValue( baseElement.getTaggedPathToString() );
		attr.setValue( baseElement.getOpenTag() + baseElement.getCloseTag() );
		elementElement.setAttributeNode(attr);

		//Operation
		attr = document.createAttribute( ATTR_OPERATION );
		attr.setValue( getElementOperation().getName() );
		elementElement.setAttributeNode(attr);
		
		//Minden Operation a sajat attributumaiert felelos
		getElementOperation().setXMLAttribute( document, elementElement );
				
		return elementElement;	
	}
	
	@Override
	public Object clone(){
		
		ParamElementDataModel cloned = (ParamElementDataModel)super.clone();
	
		if( null != this.children ){
			cloned.children = (Vector<?>) this.children.clone();
		}
		
		return cloned;
		
	}
	
}
