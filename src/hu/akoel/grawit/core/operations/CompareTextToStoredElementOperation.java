package hu.akoel.grawit.core.operations;

import java.io.StringReader;
import java.util.Set;
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
import hu.akoel.grawit.core.operation.interfaces.CompareElementOperationInterface;
import hu.akoel.grawit.core.operation.interfaces.ElementOperationAdapter;
import hu.akoel.grawit.core.operation.interfaces.HasElementOperationInterface;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseCollectorDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseElementDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseFolderDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.NormalBaseElementDataModel;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.enums.list.CompareTypeListEnum;
import hu.akoel.grawit.exceptions.ElementCompareOperationException;
import hu.akoel.grawit.exceptions.ElementException;
import hu.akoel.grawit.exceptions.XMLBaseConversionPharseException;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;
import hu.akoel.grawit.gui.interfaces.progress.ProgressIndicatorInterface;

public class CompareTextToStoredElementOperation extends ElementOperationAdapter implements HasElementOperationInterface, CompareElementOperationInterface{
	
	//TODO rendbe kell tenni a neveket!!!
	
	private static final String NAME = "COMPARETEXTTOSTOREDELEMENT";	
	private static final String ATTR_COMPARE_STORED_BASE_ELEMENT_PATH = "storedbaseelementpath";
	private static final String ATTR_COMPARE_TYPE = "type";
	private static final String ATTR_PATTERN = "pattern";
	
	private boolean isInLoop = false;
	
	private Pattern pattern;
	
	//--- Data model
	private String stringPattern;
	private BaseElementDataModelAdapter compareWithBaseElement;
	private CompareTypeListEnum compareType;
	//---
	
	public CompareTextToStoredElementOperation( BaseElementDataModelAdapter compareWithBaseElement, CompareTypeListEnum compareType, String stringPattern ){
		this.compareWithBaseElement = compareWithBaseElement;
		this.compareType = compareType;
		this.stringPattern = stringPattern;
		
		common( stringPattern );
	}

	public CompareTextToStoredElementOperation( Element element, BaseRootDataModel baseRootDataModel, Tag rootTag, Tag tag, String nameAttrName, String nameAttrValue ) throws XMLBaseConversionPharseException, XMLMissingAttributePharseException{		
		
		BaseDataModelAdapter baseDataModelForCompareText = baseRootDataModel;
		
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
	    		baseDataModelForCompareText = (BaseDataModelAdapter) CommonOperations.getDataModelByNameInLevel( baseDataModelForCompareText, Tag.BASEFOLDER, attrName );

	    		if( null == baseDataModelForCompareText ){

	    			throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, nameAttrValue, ATTR_COMPARE_STORED_BASE_ELEMENT_PATH, element.getAttribute(ATTR_COMPARE_STORED_BASE_ELEMENT_PATH) );
	    		}
	
	    	//HA BASEELEMENT
	    	}else if( tagName.equals( BaseElementDataModelAdapter.TAG.getName() ) ){	
			    		
	    		attrName = actualElement.getAttribute(NormalBaseElementDataModel.ATTR_NAME);
			    		
	    		baseDataModelForCompareText = (BaseDataModelAdapter) CommonOperations.getDataModelByNameInLevel( baseDataModelForCompareText, Tag.BASEELEMENT, attrName );
	    		if( null == baseDataModelForCompareText ){
		    			
	    			throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, nameAttrValue, ATTR_COMPARE_STORED_BASE_ELEMENT_PATH, element.getAttribute(ATTR_COMPARE_STORED_BASE_ELEMENT_PATH) );
	    		}	
    	
	    	//Ha BASEPAGE
	    	}else if( tagName.equals( BaseCollectorDataModel.TAG.getName() ) ){
	    		attrName = actualElement.getAttribute(BaseCollectorDataModel.ATTR_NAME);
	    		baseDataModelForCompareText = (BaseDataModelAdapter) CommonOperations.getDataModelByNameInLevel( baseDataModelForCompareText, Tag.BASECOLLECTOR, attrName );

	    		if( null == baseDataModelForCompareText ){

	    			throw new XMLBaseConversionPharseException( rootTag, tag,  nameAttrName, nameAttrValue, ATTR_COMPARE_STORED_BASE_ELEMENT_PATH, element.getAttribute(ATTR_COMPARE_STORED_BASE_ELEMENT_PATH) );
	    		}
	    	
	    		
	    	}else{
	    		
	    		throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, nameAttrValue, ATTR_COMPARE_STORED_BASE_ELEMENT_PATH, element.getAttribute(ATTR_COMPARE_STORED_BASE_ELEMENT_PATH) );	    		
	    	}
	    	
	    }	    
	    	
	    try{
	    	
	    	this.compareWithBaseElement = (BaseElementDataModelAdapter)baseDataModelForCompareText;
	    	
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
		return compareWithBaseElement;
	}

	public static String getStaticName(){
		return NAME;
	}
	
	@Override
	public String getName() {		
		return getStaticName();
	}
		
	@Override
	public CompareTypeListEnum getCompareType(){
		return compareType;
	}
	
	@Override
	public boolean isInLoop(){
		return this.isInLoop;
	}
	
	@Override
	public void setIsInLoop( boolean isInLoop ){
		this.isInLoop = isInLoop;
	}

	@Override
	public void doOperation(WebDriver driver, BaseElementDataModelAdapter baseElement, WebElement webElement, ProgressIndicatorInterface elementProgress, String tab, Set<String> definedElementSet, boolean needToPrintSource ) throws ElementException {
		
		//
		// SOURCE Starts
		//	
		if( needToPrintSource ){
		
			elementProgress.printSource( tab + "origText = webElement.getText();" );
			if( null != pattern ){
				elementProgress.printSource( tab + "pattern = Pattern.compile( \"" + pattern.pattern().replace("\\", "\\\\") + "\" );" );
				elementProgress.printSource( tab + "matcher = pattern.matcher( origText );");				
				elementProgress.printSource( tab + "if( matcher.find() ){" );			
				elementProgress.printSource( tab + CommonOperations.TAB_BY_SPACE + CommonOperations.TAB_BY_SPACE + "origText = matcher.group();" );
				elementProgress.printSource( tab + "}" );
			}
		
			if( compareType.equals( CompareTypeListEnum.EQUAL ) ){		
				elementProgress.printSource( tab + "if( !origText.equals( " + compareWithBaseElement.getNameAsVariable() + " ) ){" );
				if( isInLoop() ){
					elementProgress.printSource( tab + CommonOperations.TAB_BY_SPACE + "break; //because the element '" + baseElement.getNameAsVariable() + "': '\" + origText + \"' does NOT equal to '" + getBaseElementForSearch().getNameAsVariable() + "': \" + " + getBaseElementForSearch().getNameAsVariable() + " + \".\");");
				}else{
					elementProgress.printSource( tab + CommonOperations.TAB_BY_SPACE + "fail(\"Stopped because the element '" + baseElement.getNameAsVariable() + "': '\" + origText + \"' does NOT equal to '" + getBaseElementForSearch().getNameAsVariable() + "': \" + " + getBaseElementForSearch().getNameAsVariable() + " + \" but it should.\");");
				}
				elementProgress.printSource( tab + "}" );
			
			}else if( compareType.equals( CompareTypeListEnum.DIFFERENT ) ){
				elementProgress.printSource( tab + "if( origText.equals( " + compareWithBaseElement.getNameAsVariable() + " ) ){" );
				if( isInLoop() ){
					elementProgress.printSource( tab + CommonOperations.TAB_BY_SPACE + "break; //because the element '" + baseElement.getNameAsVariable() + "' equals to '\" + " + getBaseElementForSearch().getNameAsVariable() + " + \"'.");
				}else{
					elementProgress.printSource( tab + CommonOperations.TAB_BY_SPACE + "fail(\"Stopped because the element '" + baseElement.getNameAsVariable() + "': '\" + origText + \"' equals to '" + getBaseElementForSearch().getNameAsVariable() + "': \" + " + getBaseElementForSearch().getNameAsVariable() + " + \" but it should NOT.\");");
				}
				elementProgress.printSource( tab + "}" );
			}
		}
		
		//
		// Execute the OPERATION
		//	
		String origText = "";		
		origText = webElement.getText();
		
		if( null != pattern ){
			Matcher matcher = pattern.matcher( origText );
			
			if( matcher.find() ){
				
				origText = matcher.group();
			}	
		}		

		if( compareType.equals( CompareTypeListEnum.EQUAL ) ){
			
			if( !origText.equals( compareWithBaseElement.getStoredValue() ) ){
				
				//elementProgress.printSource( tab + "fail(\"Stopped because !origText.equals( " + baseElement.getNameAsVariable() + " ) BUT is should be\");" );
				
//				if( baseElement instanceof NormalBaseElementDataModel ){
				throw new ElementCompareOperationException( (NormalBaseElementDataModel)baseElement, origText, this, new Exception() );
//					throw new ElementCompareOperationException(compareType, compareWithBaseElement.getStoredValue(), baseElement.getName(), ((NormalBaseElementDataModel)baseElement).getSelector(), origText, new Exception() );
//				//Special
//				}else{
//					throw new ElementCompareOperationException(compareType, compareWithBaseElement.getStoredValue(), baseElement.getName(), "special", origText, new Exception() );
//				}
			}
			
		}else if( compareType.equals( CompareTypeListEnum.DIFFERENT ) ){
			
			if( origText.equals( compareWithBaseElement.getStoredValue() ) ){
				
				//elementProgress.printSource( tab + "fail(\"Stopped because origText.equals( " + baseElement.getNameAsVariable() + " ) BUT it should NOT be\");" );
//				if( baseElement instanceof NormalBaseElementDataModel ){
				throw new ElementCompareOperationException( (NormalBaseElementDataModel)baseElement, origText, this, new Exception() );
//					throw new ElementCompareOperationException(compareType, compareWithBaseElement.getStoredValue(), baseElement.getName(), ((NormalBaseElementDataModel)baseElement).getSelector(), origText, new Exception() );
//				//Special
//				}else{
//					throw new ElementCompareOperationException(compareType, compareWithBaseElement.getStoredValue(), baseElement.getName(), "special", origText, new Exception() );
//				}
			}			
		}
	}
	
	@Override
	public void setXMLAttribute(Document document, Element element) {		
		Attr attr = document.createAttribute( ATTR_COMPARE_STORED_BASE_ELEMENT_PATH );
		attr.setValue( compareWithBaseElement.getPathTag() );
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

		return new CompareTextToStoredElementOperation(compareWithBaseElement, compareType, stringPattern);
	}
	
	@Override
	public String getOperationNameToString() {		
		return "CompareTextToStoredElement()";
	}

	@Override
	public String getCompareTo() {
		return compareWithBaseElement.getStoredValue();
	}
}
