package hu.akoel.grawit.core.treenodedatamodel.param;

import java.io.StringReader;
import java.lang.annotation.ElementType;
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
import hu.akoel.grawit.core.operations.ElementOperationInterface;
import hu.akoel.grawit.core.operations.Torlendo_ButtonOperation;
import hu.akoel.grawit.core.operations.Torlendo_CheckboxOperation;
import hu.akoel.grawit.core.operations.Torlendo_ClearOperation;
import hu.akoel.grawit.core.operations.Torlendo_ElementOperationInterface;
import hu.akoel.grawit.core.operations.Torlendo_FieldParamElementOperation;
import hu.akoel.grawit.core.operations.Torlendo_FieldVariableOperation;
import hu.akoel.grawit.core.operations.Torlendo_GainTextPatternOperation;
import hu.akoel.grawit.core.operations.Torlendo_LinkOperation;
import hu.akoel.grawit.core.operations.Torlendo_ListVariableOperation;
import hu.akoel.grawit.core.operations.Torlendo_RadioButtonOperation;
import hu.akoel.grawit.core.operations.Torlendo_TabOperation;
import hu.akoel.grawit.core.treenodedatamodel.BaseDataModelInterface;
import hu.akoel.grawit.core.treenodedatamodel.ParamDataModelInterface;
import hu.akoel.grawit.core.treenodedatamodel.VariableDataModelInterface;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseNodeDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.BasePageDataModel;
import hu.akoel.grawit.core.treenodedatamodel.special.SpecialNodeDataModel;
import hu.akoel.grawit.core.treenodedatamodel.variable.VariableElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.variable.VariableNodeDataModel;
import hu.akoel.grawit.core.treenodedatamodel.variable.VariableRootDataModel;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.enums.list.ListEnumElementButtonOperation;
import hu.akoel.grawit.enums.list.ListEnumElementCheckboxOperation;
import hu.akoel.grawit.enums.list.ListEnumElementFieldOperation;
import hu.akoel.grawit.enums.list.ListEnumElementLinkOperation;
import hu.akoel.grawit.enums.list.ListEnumElementRadiobuttonOperation;
import hu.akoel.grawit.enums.list.ListEnumElementTextOperation;
import hu.akoel.grawit.enums.list.ListEnumElementType;
import hu.akoel.grawit.enums.list.ListEnumListSelectionBy;
import hu.akoel.grawit.enums.list.Torlendo_Operation;
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
	
	private static final String ATTR_FILL_BASE_ELEMENT_PATH = "fillbaseelementpath";
	private static final String ATTR_FILL_VARIABLE_ELEMENT_PATH = "fillvariableelementpath";
	private static final String ATTR_LIST_SELECTION_TYPE = "listselectiontype";
	private static final String ATTR_GAIN_TEXT_PATTERN = "gaintextpattern";
	
	//Adatmodel ---
	private String name;
	private BaseElementDataModel baseElement;
	private ElementOperationInterface elementOperation;
	//private Torlendo_ElementOperationInterface elementOperation;
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

		VariableDataModelInterface variableDataModel = (VariableDataModelInterface)variableRootDataModel;
		BaseDataModelInterface baseDataModelForFieldParamElement = (BaseDataModelInterface)baseDataModel.getRoot();;
		
		//
		// Name
		//
		if( !element.hasAttribute( ATTR_NAME ) ){
			throw new XMLMissingAttributePharseException( getRootTag(), TAG, ATTR_NAME );			
		}
		String nameString = element.getAttribute( ATTR_NAME );		
		this.name = nameString;
		
		//
		// Operation
		//
		if( !element.hasAttribute( ATTR_OPERATION ) ){
			throw new XMLMissingAttributePharseException( getRootTag(), TAG, ATTR_OPERATION );			
		}
		String operationString = element.getAttribute( ATTR_OPERATION );		
				
		// Link		
		if( baseElement.getElementType().equals( ListEnumElementType.LINK ) ){
			
			if( operationString.equals( ListEnumElementLinkOperation.CLICK ) ){
				
				
			}else if( operationString.equals( ListEnumElementLinkOperation.GAINTEXTPATTERN ) ){
				
				
			//Ha nem a tipusnak megfelelo az muvelet
			}else{
				
				
			}
			
		// Button
		}else if( baseElement.getElementType().equals(ListEnumElementType.BUTTON)){
			
			if( operationString.equals( ListEnumElementButtonOperation.CLICK ) ){

			//Ha nem a tipusnak megfelelo az muvelet
			}else{
					
				
			}
			
		// Checkbox
		}else if( baseElement.getElementType().equals( ListEnumElementType.CHECKBOX) ){
			
			if( operationString.equals( ListEnumElementCheckboxOperation.CLICK ) ){
				
			//Ha nem a tipusnak megfelelo az muvelet
			}else{
				
			}
			
		// Radio button
		}else if( baseElement.getElementType().equals( ListEnumElementType.RADIOBUTTON ) ){
			
			if( operationString.equals( ListEnumElementRadiobuttonOperation.CLICK ) ){
				
			//Ha nem a tipusnak megfelelo az muvelet
			}else{
					
			}
			
		// Field
		}else if( baseElement.getElementType().equals( ListEnumElementType.FIELD ) ){
			
			if( operationString.equals( ListEnumElementFieldOperation.CLEAR ) ){
			
			}else if( operationString.equals( ListEnumElementFieldOperation.CLICK ) ){
				
			}else if( operationString.equals( ListEnumElementFieldOperation.TAB ) ){
				
			}else if( operationString.equals( ListEnumElementFieldOperation.FILL_VARIABLE ) ){
				
			}else if( operationString.equals( ListEnumElementFieldOperation.FILL_ELEMENT ) ){
				
			}else if( operationString.equals( ListEnumElementFieldOperation.FILL_STRING ) ){
				
			//Ha nem a tipusnak megfelelo az muvelet
			}else{
					
			}
			
		// Text
		}else if( baseElement.getElementType().equals( ListEnumElementType.TEXT ) ){
			
			if( operationString.equals( ListEnumElementTextOperation.GAINTEXTPATTERN ) ){
				
			//Ha nem a tipusnak megfelelo az muvelet
			}else{
					
			}
			
		//Minden egyeb esetben error
		}else{
			throw new XMLWrongAttributePharseException( baseElement.getRootTag(), baseElement.TAG, baseElement.ATTR_NAME, baseElement.getName(), baseElement.ATTR_ELEMENT_TYPE, baseElement.getElementType().name() );
		}
		
		
		
		
		
		
		//
		// Operation
		//
		if( !element.hasAttribute( ATTR_OPERATION ) ){
			throw new XMLMissingAttributePharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_OPERATION );			
		}
		String operatorString = element.getAttribute( ATTR_OPERATION );
		
		//BUTTON
		if( Torlendo_Operation.BUTTON.name().equals(operatorString ) ){
			this.elementOperation = new Torlendo_ButtonOperation();
		
		//CHECKBOX
		}else if( Torlendo_Operation.CHECKBOX.name().equals(operatorString) ){
			this.elementOperation = new Torlendo_CheckboxOperation();
		
		//FIELD_VARIABLE/LIST_VARIABLE
		}else if( Torlendo_Operation.FIELD_VARIABLE.name().equals( operatorString ) || Torlendo_Operation.LIST_VARIABLE.name().equals( operatorString ) ){
			
			String variableElementPathString = element.getAttribute(ATTR_FILL_VARIABLE_ELEMENT_PATH);				
			variableElementPathString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + variableElementPathString;  
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
		    DocumentBuilder builder;
		    Document document = null;
		    try{  
		        builder = factory.newDocumentBuilder();  
		        document = builder.parse( new InputSource( new StringReader( variableElementPathString ) ) );  
		    } catch (Exception e) {  
		    
		    	//Nem sikerult az atalakitas
		    	throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_FILL_VARIABLE_ELEMENT_PATH, element.getAttribute(ATTR_FILL_VARIABLE_ELEMENT_PATH), e );
		    } 

		    //Megkeresem a VARIABLEROOT-ben a VARIABLEELEMENT-hez vezeto utat
		    Node actualNode = document;
		    while( actualNode.hasChildNodes() ){
			
		    	actualNode = actualNode.getFirstChild();
		    	Element actualElement = (Element)actualNode;
		    	String tagName = actualElement.getTagName();
		    	String attrName = null;
		    	
		    	//Ha VARIABLENODE
		    	if( tagName.equals( VariableNodeDataModel.TAG.getName() ) ){
		    		attrName = actualElement.getAttribute(VariableNodeDataModel.ATTR_NAME);	    		
		    		variableDataModel = (VariableDataModelInterface) CommonOperations.getDataModelByNameInLevel( variableDataModel, Tag.VARIABLENODE, attrName );

		    		if( null == variableDataModel ){

		    			throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_FILL_VARIABLE_ELEMENT_PATH, element.getAttribute(ATTR_FILL_VARIABLE_ELEMENT_PATH) );
		    		}
		    		
		    	//Ha VARIABLEELEMENT
		    	}else if( tagName.equals( VariableElementDataModel.TAG.getName() ) ){
		    		attrName = actualElement.getAttribute(VariableElementDataModel.ATTR_NAME);
		    		variableDataModel = (VariableDataModelInterface) CommonOperations.getDataModelByNameInLevel( variableDataModel, Tag.VARIABLEELEMENT, attrName );
		    		
		    		if( null == variableDataModel ){

		    			throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_FILL_VARIABLE_ELEMENT_PATH, element.getAttribute(ATTR_FILL_VARIABLE_ELEMENT_PATH) );
		    		}
		    		
		    	}else{
		    		
		    		throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_FILL_VARIABLE_ELEMENT_PATH, element.getAttribute(ATTR_FILL_VARIABLE_ELEMENT_PATH) );	    		
		    	}
		    }	    
		    try{
		    	
		    	if( Torlendo_Operation.FIELD_VARIABLE.name().equals(operatorString ) ){
		    		this.elementOperation = new Torlendo_FieldVariableOperation( (VariableElementDataModel)variableDataModel );
		    	}else{
		    		
		    		if( !element.hasAttribute( ATTR_LIST_SELECTION_TYPE ) ){
		    			throw new XMLMissingAttributePharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_LIST_SELECTION_TYPE );
		    		}
		    		String listSelectionTypeString = element.getAttribute( ATTR_LIST_SELECTION_TYPE );		
		    		ListEnumListSelectionBy listSelectionType = ListEnumListSelectionBy.valueOf(listSelectionTypeString );
		    				    		
		    		this.elementOperation = new Torlendo_ListVariableOperation(listSelectionType, (VariableElementDataModel)variableDataModel);
		    	}
		    	
		    }catch(ClassCastException e){

		    	//Nem sikerult az utvonalat megtalalni
		    	throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_FILL_VARIABLE_ELEMENT_PATH, element.getAttribute(ATTR_FILL_VARIABLE_ELEMENT_PATH ), e );
		    }

		//FIELD_ELEMENT
		}else if( Torlendo_Operation.FIELD_ELEMENT.name().equals( operatorString )){
			
			String paramElementPathString = element.getAttribute(ATTR_FILL_BASE_ELEMENT_PATH);				
			paramElementPathString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + paramElementPathString;  
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
		    DocumentBuilder builder;
		    Document document = null;
		    try{  
		        builder = factory.newDocumentBuilder();  
		        document = builder.parse( new InputSource( new StringReader( paramElementPathString ) ) );  
		    } catch (Exception e) {  
		    
		    	//Nem sikerult az atalakitas
		    	throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_FILL_BASE_ELEMENT_PATH, element.getAttribute(ATTR_FILL_BASE_ELEMENT_PATH), e );
		    } 

		    //Megkeresem a PARAMELEMENTROOT-ben a PARAMELEMENT-hez vezeto utat
		    Node actualNode = document;
	    
		    while( actualNode.hasChildNodes() ){
			
		    	actualNode = actualNode.getFirstChild();
		    	Element actualElement = (Element)actualNode;
		    	String tagName = actualElement.getTagName();
		    	String attrName = null;
		    	
		    	//Ha BASENODE
		    	if( tagName.equals( BaseNodeDataModel.TAG.getName() ) ){
		    		attrName = actualElement.getAttribute(BaseNodeDataModel.ATTR_NAME);	    		
		    		baseDataModelForFieldParamElement = (BaseDataModelInterface) CommonOperations.getDataModelByNameInLevel( baseDataModelForFieldParamElement, Tag.BASENODE, attrName );

		    		if( null == baseDataModelForFieldParamElement ){

		    			throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_BASE_ELEMENT_PATH, element.getAttribute(ATTR_BASE_ELEMENT_PATH) );
		    		}
		    		
		    	//Ha BASEELEMENT
		    	}else if( tagName.equals( BaseElementDataModel.TAG.getName() ) ){
		    		attrName = actualElement.getAttribute(BaseElementDataModel.ATTR_NAME);
		    		baseDataModelForFieldParamElement = (BaseDataModelInterface) CommonOperations.getDataModelByNameInLevel( baseDataModelForFieldParamElement, Tag.BASEELEMENT, attrName );
		
		    		if( null == baseDataModelForFieldParamElement ){

		    			throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_BASE_ELEMENT_PATH, element.getAttribute(ATTR_BASE_ELEMENT_PATH) );
		    		}

		    	
		    	//Ha BASEPAGE
		    	}else if( tagName.equals( BasePageDataModel.TAG.getName() ) ){
		    		attrName = actualElement.getAttribute(BasePageDataModel.ATTR_NAME);
		    		baseDataModelForFieldParamElement = (BaseDataModelInterface) CommonOperations.getDataModelByNameInLevel( baseDataModelForFieldParamElement, Tag.BASEPAGE, attrName );

		    		if( null == baseDataModelForFieldParamElement ){

		    			throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_FILL_BASE_ELEMENT_PATH, element.getAttribute(ATTR_FILL_BASE_ELEMENT_PATH) );
		    		}
		    	
		    		
		    	}else{
		    		
		    		throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_FILL_BASE_ELEMENT_PATH, element.getAttribute(ATTR_FILL_BASE_ELEMENT_PATH) );	    		
		    	}
		    }	    
		    try{
		    	
		    	if( Torlendo_Operation.FIELD_ELEMENT.name().equals(operatorString ) ){
		    		this.elementOperation = new Torlendo_FieldParamElementOperation( (BaseElementDataModel)baseDataModelForFieldParamElement );
/*		    	}else{
		    		
		    		if( !element.hasAttribute( ATTR_LIST_SELECTION_TYPE ) ){
		    			throw new XMLMissingAttributePharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_LIST_SELECTION_TYPE );
		    		}
		    		String listSelectionTypeString = element.getAttribute( ATTR_LIST_SELECTION_TYPE );		
		    		ListSelectionType listSelectionType = ListSelectionType.valueOf(listSelectionTypeString );
		    				    		
		    		this.elementOperation = new ListParamElementOperation(listSelectionType, (ParamElementDataModel)paramElementDataModel);
*/		    	}
		    	
		    }catch(ClassCastException e){

		    	//Nem sikerult az utvonalat megtalalni
		    	throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_FILL_VARIABLE_ELEMENT_PATH, element.getAttribute(ATTR_FILL_VARIABLE_ELEMENT_PATH ), e );
		    }
		
		//GAIN TEXT PATTERN
		}else if( Torlendo_Operation.GAINTEXTPATTERN.name().equals( operatorString ) ){
				
			String stringPattern = element.getAttribute(ATTR_GAIN_TEXT_PATTERN );				
			this.elementOperation = new Torlendo_GainTextPatternOperation(stringPattern);
						
		//LINK
		}else if( Torlendo_Operation.LINK.name().equals( operatorString ) ){
			this.elementOperation = new Torlendo_LinkOperation();
			
		//RADIOBUTTON
		}else if( Torlendo_Operation.RADIOBUTTON.name().equals( operatorString ) ){
			this.elementOperation = new Torlendo_RadioButtonOperation();
			
		//TAB
		}else if( Torlendo_Operation.TAB.name().equals( operatorString ) ){
			this.elementOperation = new Torlendo_TabOperation();
			
		//CLEAR
		}else if( Torlendo_Operation.FIELD_CLEAR.name().equals( operatorString ) ){
			this.elementOperation = new Torlendo_ClearOperation();

		//Minden egyeb esetben error
		}else{
			throw new XMLWrongAttributePharseException(getRootTag(), TAG, ATTR_NAME, getName(), ATTR_OPERATION, operatorString );
		}

		//
		// BaseElement
		//
		if( !element.hasAttribute( ATTR_BASE_ELEMENT_PATH ) ){
			throw new XMLMissingAttributePharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_BASE_ELEMENT_PATH );			
		}	
		String paramElementPathString = element.getAttribute(ATTR_BASE_ELEMENT_PATH);				
		paramElementPathString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + paramElementPathString;  
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
	    DocumentBuilder builder;
	    Document document = null;
	    try  
	    {  
	        builder = factory.newDocumentBuilder();  
	        document = builder.parse( new InputSource( new StringReader( paramElementPathString ) ) );  
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
	    	
	    	baseElement = (BaseElementDataModel)baseDataModel;
	    	
	    }catch(ClassCastException e){

	    	throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_BASE_ELEMENT_PATH, element.getAttribute(ATTR_BASE_ELEMENT_PATH), e );
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
	
	public Torlendo_ElementOperationInterface getElementOperation() {
		return elementOperation;
	}

	public void setOperation(Torlendo_ElementOperationInterface elementOperation) {
		this.elementOperation = elementOperation;
	}	
/*	
	public String getVariableValue() {
		return variableValue;
	}

	public void setVariableValue(String variableValue) {
		this.variableValue = variableValue;
	}
*/
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
		attr.setValue( getElementOperation().getOperation().name() );
		elementElement.setAttributeNode(attr);

		//Ha FIELD_VARIABLE/LIST_VARIABLE, akkor biztos hogy kell VariableElementPath parameter
		if( getElementOperation().getOperation().equals( Torlendo_Operation.FIELD_VARIABLE ) || getElementOperation().getOperation().equals( Torlendo_Operation.LIST_VARIABLE ) ){
		
			//VariableElementPath
			attr = document.createAttribute( ATTR_FILL_VARIABLE_ELEMENT_PATH );
			VariableDataModelInterface variableDataModel = null;
			if( getElementOperation().getOperation().equals( Torlendo_Operation.FIELD_VARIABLE ) ){
				
				variableDataModel = ((Torlendo_FieldVariableOperation)getElementOperation()).getVariableElement();
				
			}else if( getElementOperation().getOperation().equals( Torlendo_Operation.LIST_VARIABLE ) ){
				
				variableDataModel = ((Torlendo_ListVariableOperation)getElementOperation()).getVariableElement();
				
			}			
			
			if( null == variableDataModel ){ //Ez nem lehet
				attr.setValue(""); 
			}else{
				attr.setValue( variableDataModel.getPathTag() );
			}
			elementElement.setAttributeNode( attr );
		
		//Ha FIELD_ELEMENT, akkor kell ParamElementPath parameter	
		}else if( getElementOperation().getOperation().equals( Torlendo_Operation.FIELD_ELEMENT ) ){
			
			//BaseElementPath
			attr = document.createAttribute( ATTR_FILL_BASE_ELEMENT_PATH );
			BaseElementDataModel baseElementDataModel = null;
			if( getElementOperation().getOperation().equals( Torlendo_Operation.FIELD_ELEMENT ) ){
				
				baseElementDataModel = ((Torlendo_FieldParamElementOperation)getElementOperation()).getBaseElement();
				
//			}else if( getElementOperation().getOperation().equals( Operation.LIST_VARIABLE ) ){
				
//				variableDataModel = ((ListVariableOperation)getElementOperation()).getVariableElement();
				
			}			
			
			if( null == baseElementDataModel ){ //Ez nem lehet
				attr.setValue(""); 
			}else{
				attr.setValue( baseElementDataModel.getPathTag() );
			}
			elementElement.setAttributeNode( attr );
		
		//Ha GAIN TEXT	
		}else if( getElementOperation().getOperation().equals( Torlendo_Operation.GAINTEXTPATTERN ) ){
			
			//Gain text pattern
			attr = document.createAttribute( ATTR_GAIN_TEXT_PATTERN );
			
			String stringPattern = ((Torlendo_GainTextPatternOperation)getElementOperation()).getStringPattern();
			
			attr.setValue( stringPattern );			
			elementElement.setAttributeNode( attr );
		}
			
		if( getElementOperation().getOperation().equals( Torlendo_Operation.LIST_VARIABLE ) ){
			
			//List selectionType
			attr = document.createAttribute( ATTR_LIST_SELECTION_TYPE );
			ListEnumListSelectionBy listSelectionType = ((Torlendo_ListVariableOperation)getElementOperation()).getListSelectionType();
			if( null == listSelectionType ){ //Ez nem lehet
				attr.setValue("");
			}else{
				attr.setValue( listSelectionType.name() );
			}
			elementElement.setAttributeNode( attr );
		}
				
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
