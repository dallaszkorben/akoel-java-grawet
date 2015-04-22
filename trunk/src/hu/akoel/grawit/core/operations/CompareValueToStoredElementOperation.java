package hu.akoel.grawit.core.operations;

import java.io.StringReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseCollectorDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseElementDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseFolderDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.NormalBaseElementDataModel;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.enums.list.CompareTypeListEnum;
import hu.akoel.grawit.enums.list.ElementTypeListEnum;
import hu.akoel.grawit.exceptions.ElementCompareOperationException;
import hu.akoel.grawit.exceptions.ElementException;
import hu.akoel.grawit.exceptions.XMLBaseConversionPharseException;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;
import hu.akoel.grawit.gui.interfaces.progress.ElementProgressInterface;

public class CompareValueToStoredElementOperation extends ElementOperationAdapter implements HasElementOperationInterface{
	
	private static final String NAME = "COMPAREVALUETOSTOREDELEMENT";	
	private static final String ATTR_COMPARE_STORED_BASE_ELEMENT_PATH = "comparestoredbaseelementpath";
	private static final String ATTR_COMPARE_TYPE = "type";
	private static final String ATTR_PATTERN = "pattern";
	
	private Pattern pattern;
	
	//--- Data model
	private String stringPattern;
	private BaseElementDataModelAdapter baseElementDataModel;
	private CompareTypeListEnum compareType;
	//----
	
	public CompareValueToStoredElementOperation( BaseElementDataModelAdapter baseElementDataModel, CompareTypeListEnum compareType, String stringPattern ){
		this.baseElementDataModel = baseElementDataModel;
		this.compareType = compareType;
		this.stringPattern = stringPattern;
		
		common( stringPattern );
	}

	public CompareValueToStoredElementOperation( Element element, BaseRootDataModel baseRootDataModel, Tag rootTag, Tag tag, String nameAttrName, String nameAttrValue ) throws XMLBaseConversionPharseException, XMLMissingAttributePharseException{		
		
		BaseDataModelAdapter baseDataModelForCompareValue = baseRootDataModel;
		
		//ATTR_COMPARE_TYPE
		if( !element.hasAttribute( ATTR_COMPARE_TYPE ) ){
			throw new XMLMissingAttributePharseException( rootTag, tag, ATTR_COMPARE_TYPE );		
		}	
		String typeString = element.getAttribute(ATTR_COMPARE_TYPE);
		this.compareType = CompareTypeListEnum.valueOf( typeString );
		
		//ATTR_COMPARE_BASE_ELEMENT_PATH
		if( !element.hasAttribute( ATTR_COMPARE_STORED_BASE_ELEMENT_PATH ) ){
			throw new XMLMissingAttributePharseException( rootTag, tag, ATTR_COMPARE_STORED_BASE_ELEMENT_PATH );		
		}	
		String baseElementPathString = element.getAttribute(ATTR_COMPARE_STORED_BASE_ELEMENT_PATH);				
		baseElementPathString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + baseElementPathString;  
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
	    DocumentBuilder builder;
	    Document document = null;
	    try{  
	        builder = factory.newDocumentBuilder();  
	        document = builder.parse( new InputSource( new StringReader( baseElementPathString ) ) );  
	    } catch (Exception e) {  
	    
	    	//Nem sikerult az atalakitas
	    	throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, nameAttrValue, ATTR_COMPARE_STORED_BASE_ELEMENT_PATH, element.getAttribute(ATTR_COMPARE_STORED_BASE_ELEMENT_PATH), e );
	    } 

	    //Megkeresem a PARAMELEMENTROOT-ben a PARAMELEMENT-hez vezeto utat
	    Node actualNode = document;
    
	    while( actualNode.hasChildNodes() ){
		
	    	actualNode = actualNode.getFirstChild();
	    	Element actualElement = (Element)actualNode;
	    	String tagName = actualElement.getTagName();
	    	String attrName = null;
	    	
	    	//Ha BASENODE
	    	if( tagName.equals( BaseFolderDataModel.TAG.getName() ) ){
	    		attrName = actualElement.getAttribute(BaseFolderDataModel.ATTR_NAME);	    		
	    		baseDataModelForCompareValue = (BaseDataModelAdapter) CommonOperations.getDataModelByNameInLevel( baseDataModelForCompareValue, Tag.BASEFOLDER, attrName );

	    		if( null == baseDataModelForCompareValue ){

	    			throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, nameAttrValue, ATTR_COMPARE_STORED_BASE_ELEMENT_PATH, element.getAttribute(ATTR_COMPARE_STORED_BASE_ELEMENT_PATH) );
	    		}

	    		//HA BASEELEMENT
		    	}else if( tagName.equals( BaseElementDataModelAdapter.TAG.getName() ) ){	
			    		
		    		attrName = actualElement.getAttribute(NormalBaseElementDataModel.ATTR_NAME);
			    		
		    		baseDataModelForCompareValue = (BaseDataModelAdapter) CommonOperations.getDataModelByNameInLevel( baseDataModelForCompareValue, Tag.BASEELEMENT, attrName );
		    		if( null == baseDataModelForCompareValue ){

		    			throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, nameAttrValue, ATTR_COMPARE_STORED_BASE_ELEMENT_PATH, element.getAttribute(ATTR_COMPARE_STORED_BASE_ELEMENT_PATH) );
		    		}	
		    		
	    	//Ha BASEPAGE
	    	}else if( tagName.equals( BaseCollectorDataModel.TAG.getName() ) ){
	    		attrName = actualElement.getAttribute(BaseCollectorDataModel.ATTR_NAME);
	    		baseDataModelForCompareValue = (BaseDataModelAdapter) CommonOperations.getDataModelByNameInLevel( baseDataModelForCompareValue, Tag.BASECOLLECTOR, attrName );

	    		if( null == baseDataModelForCompareValue ){

	    			throw new XMLBaseConversionPharseException( rootTag, tag,  nameAttrName, nameAttrValue, ATTR_COMPARE_STORED_BASE_ELEMENT_PATH, element.getAttribute(ATTR_COMPARE_STORED_BASE_ELEMENT_PATH) );
	    		}
	    	
	    		
	    	}else{
	    		
	    		throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, nameAttrValue, ATTR_COMPARE_STORED_BASE_ELEMENT_PATH, element.getAttribute(ATTR_COMPARE_STORED_BASE_ELEMENT_PATH) );	    		
	    	}
	    }
	    
	    try{
	    	
	    	this.baseElementDataModel = (BaseElementDataModelAdapter)baseDataModelForCompareValue;
	    	
	    }catch(ClassCastException e){

	    	//Nem sikerult az utvonalat megtalalni
	    	throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, nameAttrValue, ATTR_COMPARE_STORED_BASE_ELEMENT_PATH, element.getAttribute(ATTR_COMPARE_STORED_BASE_ELEMENT_PATH ), e );
	    }
	    
	    //PATTERN
	    if( !element.hasAttribute( ATTR_PATTERN ) ){
			stringPattern = "";
		}else{
			stringPattern = element.getAttribute( ATTR_PATTERN );
		}
		
		common( stringPattern );
	    
	}
	
	private void common( String stringPattern ){
		
		if( stringPattern.trim().length() == 0 ){
			pattern = null;
		}else{		
			pattern = Pattern.compile( stringPattern );
		}
		
	}
	
	public String getStringPattern(){
		return stringPattern;
	}
	
	@Override
	public BaseElementDataModelAdapter getBaseElementForSearch() {
		return baseElementDataModel;
	}

	public static String getStaticName(){
		return NAME;
	}
	
	@Override
	public String getName() {		
		return getStaticName();
	}
		
	public CompareTypeListEnum getCompareType(){
		return compareType;
	}
	
	@Override
	public void doOperation(WebDriver driver, BaseElementDataModelAdapter baseElement, WebElement webElement, ElementProgressInterface elementProgress, String tab) throws ElementException {
		
		if( null != elementProgress ){
		//
		// SOURCE Starts
		//	
		elementProgress.printCommand( tab + "origText = \"\";" );
		
		//CHECKBOX/RADIOBUTTON
		if( baseElement.getElementType().equals(ElementTypeListEnum.CHECKBOX) || baseElement.getElementType().equals(ElementTypeListEnum.RADIOBUTTON ) ){			
			elementProgress.printCommand( tab + "if( webElement.isSelected() ){" );
			elementProgress.printCommand( tab + CommonOperations.TAB_BY_SPACE + "origText = \"on\";" );
			elementProgress.printCommand( tab + "}else{" );
			elementProgress.printCommand( tab + CommonOperations.TAB_BY_SPACE + "origText = \"off\";" );
			elementProgress.printCommand( tab + "}" );
		
		//Ha FIELD/CHECKBOX
		}else{
			
			elementProgress.printCommand( tab + "origText = webElement.getAttribute(\"value\");" );			
		}		
		if( null != pattern ){
			elementProgress.printCommand( tab + "pattern = Pattern.compile( \"" + pattern.pattern().replace("\\", "\\\\") + "\" );" );
			elementProgress.printCommand( tab + "matcher = pattern.matcher( origText );");				
			elementProgress.printCommand( tab + "if( matcher.find() ){" );			
			elementProgress.printCommand( tab + CommonOperations.TAB_BY_SPACE + CommonOperations.TAB_BY_SPACE + "origText = matcher.group();" );
			elementProgress.printCommand( tab + "}" );					
		}
		if( compareType.equals( CompareTypeListEnum.EQUAL ) ){
			elementProgress.printCommand( tab + "if( !origText.equals( " + baseElement.getNameAsVariable() + " ) ){" );
			elementProgress.printCommand( tab + CommonOperations.TAB_BY_SPACE + "System.err.println(\"Stopped because the element '" + baseElement.getNameAsVariable() + "': '\" + origText + \"' does NOT equal to '\" + " + getBaseElementForSearch().getNameAsVariable() + " + \"' but it should.\");");
			elementProgress.printCommand( tab + CommonOperations.TAB_BY_SPACE + "System.exit(-1);");
			elementProgress.printCommand( tab + "}" );
		}else if( compareType.equals( CompareTypeListEnum.DIFFERENT ) ){
			elementProgress.printCommand( tab + "if( origText.equals( " + baseElement.getNameAsVariable() + " ) ){" );
			elementProgress.printCommand( tab + CommonOperations.TAB_BY_SPACE + "System.err.println(\"Stopped because the element '" + baseElement.getNameAsVariable() + "': '\" + origText + \"' equals to '\" + " + getBaseElementForSearch().getNameAsVariable() + " + \"' but it should NOT.\");");				
			elementProgress.printCommand( tab + CommonOperations.TAB_BY_SPACE + "System.exit(-1);");
			elementProgress.printCommand( tab + "}" );
		}
		}
		
		//
		// Execute the OPERATION
		//		
		String origText = "";
		
		//CHECKBOX/RADIOBUTTON
		if( baseElement.getElementType().equals(ElementTypeListEnum.CHECKBOX) || baseElement.getElementType().equals(ElementTypeListEnum.RADIOBUTTON) ){
			
			if( webElement.isSelected() ){
				origText = "on";
			}else{
				origText = "off";
			}
			
		//Ha FIELD
		}else{
			
			origText = webElement.getAttribute("value");
		}
		
		if( null != pattern ){
			Matcher matcher = pattern.matcher( origText );
			
			if( matcher.find() ){
				
				origText = matcher.group();
			}
		}		
		
		if( compareType.equals( CompareTypeListEnum.EQUAL ) ){
			
			if( !origText.equals( baseElementDataModel.getStoredValue() ) ){
				
				if( baseElement instanceof NormalBaseElementDataModel ){
					throw new ElementCompareOperationException(compareType, baseElementDataModel.getStoredValue(), baseElement.getName(), ((NormalBaseElementDataModel)baseElement).getSelector(), origText, new Exception() );
				//Special
				}else{
					throw new ElementCompareOperationException(compareType, baseElementDataModel.getStoredValue(), baseElement.getName(), "special", origText, new Exception() );
				}
			}
			
		}else if( compareType.equals( CompareTypeListEnum.DIFFERENT ) ){
			
			if( origText.equals( baseElementDataModel.getStoredValue() ) ){
				
				if( baseElement instanceof NormalBaseElementDataModel ){
					throw new ElementCompareOperationException(compareType, baseElementDataModel.getStoredValue(), baseElement.getName(), ((NormalBaseElementDataModel)baseElement).getSelector(), origText, new Exception() );
				//Special
				}else{
					throw new ElementCompareOperationException(compareType, baseElementDataModel.getStoredValue(), baseElement.getName(), "special", origText, new Exception() );
				}

			}			
		}
	}
	
	@Override
	public void setXMLAttribute(Document document, Element element) {		
		Attr attr = document.createAttribute( ATTR_COMPARE_STORED_BASE_ELEMENT_PATH );
		attr.setValue( baseElementDataModel.getPathTag() );
		element.setAttributeNode( attr );
		
		attr = document.createAttribute( ATTR_COMPARE_TYPE );
		attr.setValue( compareType.name() );
		element.setAttributeNode( attr );	

		attr = document.createAttribute( ATTR_PATTERN );
		attr.setValue( stringPattern );
		element.setAttributeNode(attr);	
	}

	@Override
	public Object clone() {
		
		String stringPattern = new String( this.stringPattern );
		
		return new CompareValueToStoredElementOperation(baseElementDataModel, compareType, stringPattern);
	}
	
	@Override
	public String getOperationNameToString() {		
		return "CompareValueToStoredElement()";
	}
}
