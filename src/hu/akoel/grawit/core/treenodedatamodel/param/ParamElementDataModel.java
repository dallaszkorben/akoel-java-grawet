package hu.akoel.grawit.core.treenodedatamodel.param;

import java.io.StringReader;

import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
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
import hu.akoel.grawit.core.operations.CompareListToStoredElementOperation;
import hu.akoel.grawit.core.operations.CompareListToStringOperation;
import hu.akoel.grawit.core.operations.CompareListToVariableOperation;
import hu.akoel.grawit.core.operations.CompareTextToStoredElementOperation;
import hu.akoel.grawit.core.operations.CompareTextToStringOperation;
import hu.akoel.grawit.core.operations.CompareTextToVariableOperation;
import hu.akoel.grawit.core.operations.CompareValueToStoredElementOperation;
import hu.akoel.grawit.core.operations.CompareValueToStringOperation;
import hu.akoel.grawit.core.operations.CompareValueToVariableOperation;
import hu.akoel.grawit.core.operations.ElementOperationAdapter;
import hu.akoel.grawit.core.operations.FillWithBaseElementOperation;
import hu.akoel.grawit.core.operations.FillWithStringOperation;
import hu.akoel.grawit.core.operations.FillWithVariableElementOperation;
import hu.akoel.grawit.core.operations.GainListToElementStorageOperation;
import hu.akoel.grawit.core.operations.GainListToVariableOperation;
import hu.akoel.grawit.core.operations.GainTextToElementOperation;
import hu.akoel.grawit.core.operations.GainValueToElementStorageOperation;
import hu.akoel.grawit.core.operations.GainValueToVariableOperation;
import hu.akoel.grawit.core.operations.OutputStoredElementOperation;
import hu.akoel.grawit.core.operations.SelectBaseElementOperation;
import hu.akoel.grawit.core.operations.SelectStringOperation;
import hu.akoel.grawit.core.operations.SelectVariableElementOperation;
import hu.akoel.grawit.core.operations.SpecialBaseAddStoreToParametersOperation;
import hu.akoel.grawit.core.operations.SpecialBaseAddStringToParametersOperation;
import hu.akoel.grawit.core.operations.SpecialBaseAddVariableToParametersOperation;
import hu.akoel.grawit.core.operations.SpecialBaseClearParametersOperation;
import hu.akoel.grawit.core.operations.SpecialBaseExecuteOperation;
import hu.akoel.grawit.core.operations.TabOperation;
import hu.akoel.grawit.core.treenodedatamodel.BaseDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.BaseElementDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.DataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.ParamDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseNodeDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.BasePageDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.NormalBaseElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.SpecialBaseElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.variable.VariableRootDataModel;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.enums.list.ElementTypeListEnum;
import hu.akoel.grawit.exceptions.CompilationException;
import hu.akoel.grawit.exceptions.ElementException;
import hu.akoel.grawit.exceptions.XMLBaseConversionPharseException;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;
import hu.akoel.grawit.exceptions.XMLPharseException;
import hu.akoel.grawit.exceptions.XMLWrongAttributePharseException;

public class ParamElementDataModel extends ParamDataModelAdapter {
	
	private static final long serialVersionUID = -8916078747948054716L;

	public static Tag TAG = Tag.PARAMELEMENT;
	
	private static final String ATTR_BASE_ELEMENT_PATH = "baseelementpath";
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
	public ParamElementDataModel( Element element, BaseDataModelAdapter baseDataModel, VariableRootDataModel variableRootDataModel ) throws XMLPharseException{

		//Engedelyezi a Node Ki/Be kapcsolasat
		this.setEnabledToTurnOnOff( true );
		
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
		
		//Ha meghatarozott oldalon levo Elem-rol van szo
//		if( this.getParent() instanceof ParamPageSpecificDataModel ){
			
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
				String attrName = actualElement.getAttribute(BaseNodeDataModel.ATTR_NAME);	  
	    	
				//Ha BASENODE
				if( tagName.equals( BaseNodeDataModel.TAG.getName() ) ){
					//attrName = actualElement.getAttribute(BaseNodeDataModel.ATTR_NAME);	    		
					baseDataModel = (BaseDataModelAdapter) CommonOperations.getDataModelByNameInLevel( baseDataModel, Tag.BASENODE, attrName );

					if( null == baseDataModel ){

						throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_BASE_ELEMENT_PATH, element.getAttribute(ATTR_BASE_ELEMENT_PATH) );
					}
					
				//Ha BASEPAGE
				}else if( tagName.equals( BasePageDataModel.TAG.getName() ) ){
					baseDataModel = (BaseDataModelAdapter) CommonOperations.getDataModelByNameInLevel( baseDataModel, Tag.BASEPAGE, attrName );
					if( null == baseDataModel ){

						throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_BASE_ELEMENT_PATH, element.getAttribute(ATTR_BASE_ELEMENT_PATH) );
					}
					
				//Ha BASELEMENT
				}else if( tagName.equals( NormalBaseElementDataModel.TAG.getName() ) ){
	    	
					//String attrName = actualElement.getAttribute(DataModelAdapter.ATTR_NAME);	    
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
	    

		//Ha nem meghatarozott oldalhoz kotott elemrol van szo
//		}else if( this.getParent() instanceof ParamPageNoSpecificDataModel ){

			
			
//		}
	    
//TODO duplikacio megszuntetese. ugyan ez van a SpecialElementTypeComponent.java-ban is, csak egy kicsit maskent	    
	    
		//=============================
		//
		// Operation a muvelet alapjan
		//
		//=============================
		if( !element.hasAttribute( ATTR_OPERATION ) ){
			throw new XMLMissingAttributePharseException( getRootTag(), TAG, ATTR_OPERATION );			
		}
		String operationString = element.getAttribute( ATTR_OPERATION );		

		//Ha SpecialBase ElementDataModel
		if( baseElement instanceof SpecialBaseElementDataModel ){

			if( baseElement.getElementType().equals( ElementTypeListEnum.SPECIAL ) ){

				//ADD_STORED_TO_PARAMETERS
				if( operationString.equals( SpecialBaseAddStoreToParametersOperation.getStaticName() ) ){
					elementOperation = new SpecialBaseAddStoreToParametersOperation( element, (BaseRootDataModel)baseElement.getRoot(), getRootTag(), getTag(), ATTR_NAME, getName()  );
					
				//ADD_VARIABLE_TO_PARAMETERS
				}else if( operationString.equals( SpecialBaseAddVariableToParametersOperation.getStaticName() ) ){
					elementOperation = new SpecialBaseAddVariableToParametersOperation( element, variableRootDataModel, getRootTag(), getTag(), ATTR_NAME, getName() );
					
				//ADD_STRING_TO_PARAMETERS
				}else if( operationString.equals( SpecialBaseAddStringToParametersOperation.getStaticName() ) ){
					elementOperation = new SpecialBaseAddStringToParametersOperation( element, getRootTag(), getTag() );
					
				//CLEAR_PARAMETERS
				}else if( operationString.equals( SpecialBaseClearParametersOperation.getStaticName() ) ){
					elementOperation = new SpecialBaseClearParametersOperation();
					
				//EXECUTE_SCRIPT
				}else if( operationString.equals( SpecialBaseExecuteOperation.getStaticName() ) ){
					elementOperation = new SpecialBaseExecuteOperation();

				}
				
			//Minden egyeb esetben error
			}else{
				throw new XMLWrongAttributePharseException( BaseDataModelAdapter.getRootTag(), SpecialBaseElementDataModel.TAG, DataModelAdapter.ATTR_NAME, baseElement.getName(), SpecialBaseElementDataModel.ATTR_ELEMENT_TYPE, baseElement.getElementType().name() );
			}
			
			
		//Ha NormalBase ElementDataModel
		}else if( baseElement instanceof NormalBaseElementDataModel ){

			//
			// A tipus az erosebb. Ha a tipushoz nem megfelelo operation van rendelve, akkor egy 
			// alapertelmezett operationt kap
			//
			//-------------
			// Link	a tipus
			//-------------
			if( baseElement.getElementType().equals( ElementTypeListEnum.LINK ) ){
			
				//CLICK
				if( operationString.equals( ClickOperation.getStaticName() ) ){
				
					elementOperation = new ClickOperation();
					
				//COMPARE TEXT TO VARIABLE
				}else if( operationString.equals( CompareTextToVariableOperation.getStaticName() ) ){
				
					elementOperation = new CompareTextToVariableOperation( element, variableRootDataModel, getRootTag(), getTag(), ATTR_NAME, getName() );
				
				//COMPARE TEXT TO STORED
				}else if( operationString.equals( CompareTextToStoredElementOperation.getStaticName() ) ){
				
					elementOperation = new CompareTextToStoredElementOperation( element, (BaseRootDataModel)baseElement.getRoot(), getRootTag(), getTag(), ATTR_NAME, getName() );
				
				//COMPARE TEXT TO STRING
				}else if( operationString.equals( CompareTextToStringOperation.getStaticName() ) ){
				
					elementOperation = new CompareTextToStringOperation( element, getRootTag(), getTag() );
					
				//GAIN TEXT TO ELEMENT
				}else if( operationString.equals( GainTextToElementOperation.getStaticName() ) ){
										
					elementOperation = new GainTextToElementOperation( element, getRootTag(), getTag() );				
			
				//OUTPUT STORED
				}else if( operationString.equals( OutputStoredElementOperation.getStaticName() ) ){ 
				
					elementOperation = new OutputStoredElementOperation( element, getRootTag(), getTag() );
				
				//Ha nem a tipusnak megfelelo a muvelet, akkor azt Click-nek vesszuk
				}else{
				
					elementOperation = new ClickOperation();
				}
		
			//---------------
			// Button a tipus
			//---------------
			}else if( ((NormalBaseElementDataModel)baseElement).getElementType().equals(ElementTypeListEnum.BUTTON)){
			
				//CLICK
				if( operationString.equals( ClickOperation.getStaticName() ) ){

					elementOperation = new ClickOperation();
				
					//Ha nem a tipusnak megfelelo az muvelet, akkor is Click az operation
				}else{
					
					elementOperation = new ClickOperation();				
				}
			
			//-----------------
			// Checkbox a tipus
			//-----------------
			}else if( ((NormalBaseElementDataModel)baseElement).getElementType().equals( ElementTypeListEnum.CHECKBOX) ){
			
				//CLICK
				if( operationString.equals( ClickOperation.getStaticName() ) ){
				
					elementOperation = new ClickOperation();
				
				//COMPARE VALUE TO VARIABLE
				}else if( operationString.equals( CompareValueToVariableOperation.getStaticName() ) ){
				
					elementOperation = new CompareValueToVariableOperation( element, variableRootDataModel, getRootTag(), getTag(), ATTR_NAME, getName() );

				//COMPARE VALUE TO STORED
				}else if( operationString.equals( CompareValueToStoredElementOperation.getStaticName() ) ){
				
					elementOperation = new CompareValueToStoredElementOperation( element, (BaseRootDataModel)baseElement.getRoot(), getRootTag(), getTag(), ATTR_NAME, getName() );
				
				//COMPARE VALUE TO STRING
				}else if( operationString.equals( CompareValueToStringOperation.getStaticName() ) ){
				
					elementOperation = new CompareValueToStringOperation( element, getRootTag(), getTag() );				
				
				//GAIN VALUE TO VARIABLE
				}else if( operationString.equals( GainValueToVariableOperation.getStaticName() ) ){
					
					elementOperation = new GainValueToVariableOperation( element, variableRootDataModel, getRootTag(), getTag(), ATTR_NAME, getName() );

				//GAIN VALUE TO ELEMENT STORAGE
				}else if( operationString.equals( GainValueToElementStorageOperation.getStaticName() ) ){
						
					elementOperation = new GainValueToElementStorageOperation( element, getRootTag(), getTag() );

				//OUTPUT STORED
				}else if( operationString.equals( OutputStoredElementOperation.getStaticName() ) ){ 
				
					elementOperation = new OutputStoredElementOperation( element, getRootTag(), getTag() );
				
				//Ha nem a tipusnak megfelelo az muvelet, akkor is Click az operation
				}else{
				
					elementOperation = new ClickOperation();
				
				}
			
			//---------------------
			// Radio button a tipus
			//---------------------
			}else if( ((NormalBaseElementDataModel)baseElement).getElementType().equals( ElementTypeListEnum.RADIOBUTTON ) ){
			
				//CLICK
				if( operationString.equals( ClickOperation.getStaticName() ) ){
				
					elementOperation = new ClickOperation();
				
				//COMPARE TEXT TO VARIABLE
				}else if( operationString.equals( CompareTextToVariableOperation.getStaticName() ) ){
	
					elementOperation = new CompareTextToVariableOperation( element, variableRootDataModel, getRootTag(), getTag(), ATTR_NAME, getName() );
	
				//COMPARE TEXT TO STORED
				}else if( operationString.equals( CompareTextToStoredElementOperation.getStaticName() ) ){
	
					elementOperation = new CompareTextToStoredElementOperation( element, (BaseRootDataModel)baseElement.getRoot(), getRootTag(), getTag(), ATTR_NAME, getName() );
	
				//COMPARE TEXT TO STRING
				}else if( operationString.equals( CompareTextToStringOperation.getStaticName() ) ){
	
					elementOperation = new CompareTextToStringOperation( element, getRootTag(), getTag() );
				
				//COMPARE VALUE TO VARIABLE
				}else if( operationString.equals( CompareValueToVariableOperation.getStaticName() ) ){
				
					elementOperation = new CompareValueToVariableOperation( element, variableRootDataModel, getRootTag(), getTag(), ATTR_NAME, getName() );

				//COMPARE VALUE TO STORED
				}else if( operationString.equals( CompareValueToStoredElementOperation.getStaticName() ) ){
				
					elementOperation = new CompareValueToStoredElementOperation( element, (BaseRootDataModel)baseElement.getRoot(), getRootTag(), getTag(), ATTR_NAME, getName() );
				
				//COMPARE VALUE TO STRING
				}else if( operationString.equals( CompareValueToStringOperation.getStaticName() ) ){
				
					elementOperation = new CompareValueToStringOperation( element, getRootTag(), getTag() );
				
				//GAIN VALUE TO VARIABLE
				}else if( operationString.equals( GainValueToVariableOperation.getStaticName() ) ){
					
					elementOperation = new GainValueToVariableOperation( element, variableRootDataModel, getRootTag(), getTag(), ATTR_NAME, getName() );

				//GAIN VALUE TO ELEMENT STORAGE
				}else if( operationString.equals( GainValueToElementStorageOperation.getStaticName() ) ){
						
					elementOperation = new GainValueToElementStorageOperation( element, getRootTag(), getTag() );

				//OUTPUT STORED
				}else if( operationString.equals( OutputStoredElementOperation.getStaticName() ) ){ 
				
					elementOperation = new OutputStoredElementOperation( element, getRootTag(), getTag() );
				
				//Ha nem a tipusnak megfelelo az muvelet, akkor is Click az operation
				}else{
					
					elementOperation = new ClickOperation();
					
				}
		
			//--------------
			// Field a tipus
			//--------------
			}else if( ((NormalBaseElementDataModel)baseElement).getElementType().equals( ElementTypeListEnum.FIELD ) ){
			
				//CLEAR
				if( operationString.equals( ClearOperation.getStaticName() ) ){
			
					elementOperation = new ClearOperation();
				
				//CLICK
				}else if( operationString.equals( ClickOperation.getStaticName() ) ){
				
					elementOperation = new ClickOperation();
				
				//TAB
				}else if( operationString.equals( TabOperation.getStaticName() ) ){
				
					elementOperation = new TabOperation();
				
				//FILL VARIABLE
				}else if( operationString.equals( FillWithVariableElementOperation.getStaticName() ) ){
				
					elementOperation = new FillWithVariableElementOperation( element, variableRootDataModel, getRootTag(), getTag(), ATTR_NAME, getName() );
				
				//FILL BASEELEMENT
				}else if( operationString.equals( FillWithBaseElementOperation.getStaticName() ) ){
				
					elementOperation = new FillWithBaseElementOperation( element, (BaseRootDataModel)baseElement.getRoot(), getRootTag(), getTag(), ATTR_NAME, getName() );
				
				//FILL STRING
				}else if( operationString.equals( FillWithStringOperation.getStaticName() ) ){
				
					elementOperation = new FillWithStringOperation( element, getRootTag(), getTag() );
				
				//COMPARE VALUE TO VARIABLE
				}else if( operationString.equals( CompareValueToVariableOperation.getStaticName() ) ){
				
					elementOperation = new CompareValueToVariableOperation( element, variableRootDataModel, getRootTag(), getTag(), ATTR_NAME, getName() );

				//COMPARE VALUE TO STORED
				}else if( operationString.equals( CompareValueToStoredElementOperation.getStaticName() ) ){
				
					elementOperation = new CompareValueToStoredElementOperation( element, (BaseRootDataModel)baseElement.getRoot(), getRootTag(), getTag(), ATTR_NAME, getName() );
				
				//COMPARE VALUE TO STRING
				}else if( operationString.equals( CompareValueToStringOperation.getStaticName() ) ){
				
					elementOperation = new CompareValueToStringOperation( element, getRootTag(), getTag() );
			
				//GAIN VALUE TO VARIABLE
				}else if( operationString.equals( GainValueToVariableOperation.getStaticName() ) ){
					
					elementOperation = new GainValueToVariableOperation( element, variableRootDataModel, getRootTag(), getTag(), ATTR_NAME, getName() );

				//GAIN VALUE TO ELEMENT STORAGE
				}else if( operationString.equals( GainValueToElementStorageOperation.getStaticName() ) ){
						
					elementOperation = new GainValueToElementStorageOperation( element, getRootTag(), getTag() );

				//OUTPUT STORED
				}else if( operationString.equals( OutputStoredElementOperation.getStaticName() ) ){ 
				
					elementOperation = new OutputStoredElementOperation( element, getRootTag(), getTag() );
				
				//Ha nem a tipusnak megfelelo az muvelet, akkor Clear lesz a muvelet
				}else{
					
					elementOperation = new ClearOperation();
				}
		
			//---------
			// Text
			//---------
			}else if( ((NormalBaseElementDataModel)baseElement).getElementType().equals( ElementTypeListEnum.TEXT ) ){
			
				//COMPARE TEXT TO VARIABLE
				if( operationString.equals( CompareTextToVariableOperation.getStaticName() ) ){
				
					elementOperation = new CompareTextToVariableOperation( element, variableRootDataModel, getRootTag(), getTag(), ATTR_NAME, getName() );
				
				//COMPARE TEXT TO STORED
				}else if( operationString.equals( CompareTextToStoredElementOperation.getStaticName() ) ){
				
					elementOperation = new CompareTextToStoredElementOperation( element, (BaseRootDataModel)baseElement.getRoot(), getRootTag(), getTag(), ATTR_NAME, getName() );
				
				//COMPARE TEXT TO STRING
				}else if( operationString.equals( CompareTextToStringOperation.getStaticName() ) ){
				
					elementOperation = new CompareTextToStringOperation( element, getRootTag(), getTag() );
				
				//GAIN TEXT TO ELEMENT
				}else if( operationString.equals( GainTextToElementOperation.getStaticName() ) ){
									
					elementOperation = new GainTextToElementOperation( element, getRootTag(), getTag() );				
				
				//OUTPUT STORED
				}else if( operationString.equals( OutputStoredElementOperation.getStaticName() ) ){ 
	
					elementOperation = new OutputStoredElementOperation( element, getRootTag(), getTag() );
				
				//Ha nem a tipusnak megfelelo az muvelet
				}else{
				
					//Default muvelet
					elementOperation = new GainTextToElementOperation( element, getRootTag(), getTag() );	
				
					//throw new XMLWrongAttributePharseException( BaseDataModelInterface.getRootTag(), BaseElementDataModel.TAG, DataModelAdapter.ATTR_NAME, baseElement.getName(), BaseElementDataModel.ATTR_ELEMENT_TYPE, baseElement.getElementType().name() );
					//TODO ez nem jo uzenet
				}
			
			//---------
			// List
			//---------
			}else if( ((NormalBaseElementDataModel)baseElement).getElementType().equals( ElementTypeListEnum.LIST ) ){
						
				//Select Variable Element
				if( operationString.equals( SelectVariableElementOperation.getStaticName() ) ){
				
					elementOperation = new SelectVariableElementOperation( element, variableRootDataModel, getRootTag(), getTag(), ATTR_NAME, getName() );
				
				//Select Base Element
				}else if( operationString.equals( SelectBaseElementOperation.getStaticName() ) ){
				
					elementOperation = new SelectBaseElementOperation( element, (BaseRootDataModel)baseElement.getRoot(), getRootTag(), getTag(), ATTR_NAME, getName() );
		
				//Select String
				}else if( operationString.equals( SelectStringOperation.getStaticName() ) ){
				
					elementOperation = new SelectStringOperation( element, getRootTag(), getTag(), ATTR_NAME, getName() );
		
				//Click
				}else if( operationString.equals( ClickOperation.getStaticName() ) ){
				
					elementOperation = new ClickOperation( );
				
				//Tab
				}else if( operationString.equals( TabOperation.getStaticName() ) ){
				
					elementOperation = new TabOperation( );
				
				//COMPARE TO VARIABLE
				}else if( operationString.equals( CompareListToVariableOperation.getStaticName() ) ){
				
					elementOperation = new CompareListToVariableOperation( element, variableRootDataModel, getRootTag(), getTag(), ATTR_NAME, getName() );

				//COMPARE TO STORED
				}else if( operationString.equals( CompareListToStoredElementOperation.getStaticName() ) ){
				
					elementOperation = new CompareListToStoredElementOperation( element, (BaseRootDataModel)baseElement.getRoot(), getRootTag(), getTag(), ATTR_NAME, getName() );
				
				//COMPARE TO STRING
				}else if( operationString.equals( CompareListToStringOperation.getStaticName() ) ){
				
					elementOperation = new CompareListToStringOperation( element, getRootTag(), getTag() );
				
				//GAIN TO VARIABLE
				}else if( operationString.equals( GainListToVariableOperation.getStaticName() ) ){
						
					elementOperation = new GainListToVariableOperation( element, variableRootDataModel, getRootTag(), getTag(), ATTR_NAME, getName() );

				//GAIN TO ELEMENT
				}else if( operationString.equals( GainListToElementStorageOperation.getStaticName() ) ){
								
					elementOperation = new GainListToElementStorageOperation( element, getRootTag(), getTag() );
			
				//OUTPUT STORED
				}else if( operationString.equals( OutputStoredElementOperation.getStaticName() ) ){ 
					
					elementOperation = new OutputStoredElementOperation( element, getRootTag(), getTag() );				
			
				}else{
				
					elementOperation = new ClickOperation( );
				
				}
			
			//Minden egyeb esetben error
			}else{
				throw new XMLWrongAttributePharseException( BaseDataModelAdapter.getRootTag(), NormalBaseElementDataModel.TAG, DataModelAdapter.ATTR_NAME, baseElement.getName(), NormalBaseElementDataModel.ATTR_ELEMENT_TYPE, ((NormalBaseElementDataModel)baseElement).getElementType().name() );
			}
		}

	}

	/**
	 * 
	 * Executes the defined Operation with the defined Parameter
	 * @throws ElementException 
	 * 
	 */
	public void doAction( WebDriver driver, ElementProgressInterface elementProgress ) throws ElementException, CompilationException{
		this.getElementOperation().doAction( driver, this, elementProgress );
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

	public void setBaseElement( BaseElementDataModelAdapter baseElement ){
		this.baseElement = baseElement;
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
		//BaseElementPath
		//
		//========
		attr = document.createAttribute( ATTR_BASE_ELEMENT_PATH );

		if( this.getParent() instanceof ParamPageSpecificDataModel ){
		
			attr.setValue( baseElement.getOpenTag() + baseElement.getCloseTag() );
			
		}else if( this.getParent() instanceof ParamPageNoSpecificDataModel ){

			attr.setValue( baseElement.getPathTag() );
/*
			attr.setValue( basePage.getPathTag() );
			pageElement.setAttributeNode(attr);		

			int childrens = this.getChildCount();
			for( int i = 0; i < childrens; i++ ){
				
				Object object = this.getChildAt( i );
				
				if( !object.equals(this) && object instanceof ParamDataModelAdapter ){
					
					Element element = ((ParamDataModelAdapter)object).getXMLElement( document );
					pageElement.appendChild( element );		    		
			    	
				}
			}
*/			
		}
		
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
	
//		cloned.baseElement = (BaseElementDataModel) this.baseElement.clone();
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
	
}
