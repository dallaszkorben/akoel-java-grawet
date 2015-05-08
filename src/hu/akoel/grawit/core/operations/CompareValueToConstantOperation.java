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
import hu.akoel.grawit.core.treenodedatamodel.base.BaseElementDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.NormalBaseElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.constant.ConstantDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.constant.ConstantElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.constant.ConstantFolderNodeDataModel;
import hu.akoel.grawit.core.treenodedatamodel.constant.ConstantRootDataModel;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.enums.list.CompareTypeListEnum;
import hu.akoel.grawit.enums.list.ElementTypeListEnum;
import hu.akoel.grawit.exceptions.ElementCompareOperationException;
import hu.akoel.grawit.exceptions.ElementException;
import hu.akoel.grawit.exceptions.XMLBaseConversionPharseException;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;
import hu.akoel.grawit.gui.interfaces.progress.ProgressIndicatorInterface;

public class CompareValueToConstantOperation extends ElementOperationAdapter implements HasConstantOperationInterface, CompareOperationInterface{
	
	private static final String NAME = "COMPAREVALUETOCONSTANT";	
	private static final String ATTR_COMPARE_CONSTANT_ELEMENT_PATH = "compareconstantelementpath";
	private static final String ATTR_COMPARE_TYPE = "type";
	private static final String ATTR_PATTERN = "pattern";
	
	private boolean isInLoop = false;
	
	private Pattern pattern;
	
	//--- Data model
	private String stringPattern;
	private ConstantElementDataModel constantElementDataModel;
	private CompareTypeListEnum compareType;
	//---
	
	public CompareValueToConstantOperation( ConstantElementDataModel constantElementDataModel, CompareTypeListEnum compareType, String stringPattern ){
		this.constantElementDataModel = constantElementDataModel;
		this.compareType = compareType;
		this.stringPattern = stringPattern;
		
		common( stringPattern );
	}
	
	public CompareValueToConstantOperation( Element element, ConstantRootDataModel constantRootDataModel, Tag rootTag, Tag tag, String nameAttrName, String nameAttrValue ) throws XMLBaseConversionPharseException, XMLMissingAttributePharseException{
		
		ConstantDataModelAdapter constantDataModelForFillOut = constantRootDataModel;
		
		//ATTR_COMPARE_TYPE
		if( !element.hasAttribute( ATTR_COMPARE_TYPE ) ){
			throw new XMLMissingAttributePharseException( rootTag, tag, ATTR_COMPARE_TYPE );		
		}	
		String typeString = element.getAttribute(ATTR_COMPARE_TYPE);
		this.compareType = CompareTypeListEnum.valueOf( typeString );
		
		//ATTR_COMPARE_CONSTANT_ELEMENT_PATH
		if( !element.hasAttribute( ATTR_COMPARE_CONSTANT_ELEMENT_PATH ) ){
			throw new XMLMissingAttributePharseException( rootTag, tag, ATTR_COMPARE_CONSTANT_ELEMENT_PATH );		
		}
		String constantElementPathString = element.getAttribute(ATTR_COMPARE_CONSTANT_ELEMENT_PATH);				
		constantElementPathString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + constantElementPathString;  
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
	    DocumentBuilder builder;
	    Document document = null;
	    try{  
	        builder = factory.newDocumentBuilder();  
	        document = builder.parse( new InputSource( new StringReader( constantElementPathString ) ) );  
	    } catch (Exception e) {  
	    
	    	//Nem sikerult az atalakitas
	    	throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, nameAttrValue, ATTR_COMPARE_CONSTANT_ELEMENT_PATH, element.getAttribute(ATTR_COMPARE_CONSTANT_ELEMENT_PATH), e );
	    } 

	    //Megkeresem a CONSTANTROOT-ben a CONSTANTELEMENT-hez vezeto utat
	    Node actualNode = document;
	    while( actualNode.hasChildNodes() ){
		
	    	actualNode = actualNode.getFirstChild();
	    	Element actualElement = (Element)actualNode;
	    	String tagName = actualElement.getTagName();
	    	String attrName = null;
	    	
	    	//Ha CONSTANTNODE
	    	if( tagName.equals( ConstantFolderNodeDataModel.TAG.getName() ) ){
	    		attrName = actualElement.getAttribute(ConstantFolderNodeDataModel.ATTR_NAME);	    		
	    		constantDataModelForFillOut = (ConstantDataModelAdapter) CommonOperations.getDataModelByNameInLevel( constantDataModelForFillOut, Tag.CONSTANTFOLDER, attrName );

	    		if( null == constantDataModelForFillOut ){

	    			throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, nameAttrValue, ATTR_COMPARE_CONSTANT_ELEMENT_PATH, element.getAttribute(ATTR_COMPARE_CONSTANT_ELEMENT_PATH) );
	    		}
	    		
	    	//Ha CONSTANTELEMENT
	    	}else if( tagName.equals( ConstantElementDataModel.TAG.getName() ) ){
	    		attrName = actualElement.getAttribute(ConstantElementDataModel.ATTR_NAME);
	    		constantDataModelForFillOut = (ConstantDataModelAdapter) CommonOperations.getDataModelByNameInLevel( constantDataModelForFillOut, Tag.CONSTANTELEMENT, attrName );
	    		
	    		if( null == constantDataModelForFillOut ){

	    			throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, getName(), ATTR_COMPARE_CONSTANT_ELEMENT_PATH, element.getAttribute(ATTR_COMPARE_CONSTANT_ELEMENT_PATH) );
	    		}
	    		
	    	}else{
	    		
	    		throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, getName(), ATTR_COMPARE_CONSTANT_ELEMENT_PATH, element.getAttribute(ATTR_COMPARE_CONSTANT_ELEMENT_PATH) );	    		
	    	}
	    }	    
	    try{
	    	
	    	this.constantElementDataModel = (ConstantElementDataModel)constantDataModelForFillOut;
	    	
	    }catch(ClassCastException e){

	    	//Nem sikerult az utvonalat megtalalni
	    	throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, nameAttrValue, ATTR_COMPARE_CONSTANT_ELEMENT_PATH, element.getAttribute(ATTR_COMPARE_CONSTANT_ELEMENT_PATH ), e );
	    }
	    
	    //PATTERN
	    if( !element.hasAttribute( ATTR_PATTERN ) ){
			stringPattern = "";
		}else{
			stringPattern = element.getAttribute( ATTR_PATTERN );
		}
		
		common( stringPattern );

	}
	
	public String getStringPattern(){
		return stringPattern;
	}
	
	public static String getStaticName(){
		return NAME;
	}
	
	@Override
	public String getName() {		
		return getStaticName();
	}
	
	private void common( String stringPattern ){
		
		if( stringPattern.trim().length() == 0 ){
			pattern = null;
		}else{		
			pattern = Pattern.compile( stringPattern );
		}
		
	}
	
	@Override
	public ConstantElementDataModel getConstantElement() {
		return constantElementDataModel;
	}

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
		
			elementProgress.printSource( tab + "origText = \"\";" );
		
			//CHECKBOX/RADIOBUTTON
			if( baseElement.getElementType().equals(ElementTypeListEnum.CHECKBOX) || baseElement.getElementType().equals(ElementTypeListEnum.RADIOBUTTON ) ){
			
				elementProgress.printSource( tab + "if( webElement.isSelected() ){" );
				elementProgress.printSource( tab + CommonOperations.TAB_BY_SPACE + "origText = \"on\";" );
				elementProgress.printSource( tab + "}else{" );
				elementProgress.printSource( tab + CommonOperations.TAB_BY_SPACE + "origText = \"off\";" );
				elementProgress.printSource( tab + "}" );
		
			//Ha FIELD/CHECKBOX
			}else{			
				elementProgress.printSource( tab + "origText = webElement.getAttribute(\"value\");" );			
			}
		
			if( null != pattern ){
				elementProgress.printSource( tab + "pattern = Pattern.compile( \"" + pattern.pattern().replace("\\", "\\\\") + "\" );" );
				elementProgress.printSource( tab + "matcher = pattern.matcher( origText );");				
				elementProgress.printSource( tab + "if( matcher.find() ){" );			
				elementProgress.printSource( tab + CommonOperations.TAB_BY_SPACE + CommonOperations.TAB_BY_SPACE + "origText = matcher.group();" );
				elementProgress.printSource( tab + "}" );					
			}
		
			if( compareType.equals( CompareTypeListEnum.EQUAL ) ){
				elementProgress.printSource( tab + "if( !origText.equals( \"" + constantElementDataModel.getValue() + "\" ) ){" );			
				if( isInLoop() ){
					elementProgress.printSource( tab + CommonOperations.TAB_BY_SPACE + "break; //because the element '" + baseElement.getNameAsVariable() + "': '\" + origText + \"' does NOT equal to '" + constantElementDataModel.getValue() + " + \"'." );
				}else{
					elementProgress.printSource( tab + CommonOperations.TAB_BY_SPACE + "fail(\"Stopped because the element '" + baseElement.getNameAsVariable() + "': '\" + origText + \"' does NOT equal to '" + constantElementDataModel.getValue() + "' but it should.\");");
				}
				elementProgress.printSource( tab + "}" );
			
			}else if( compareType.equals( CompareTypeListEnum.DIFFERENT ) ){
				elementProgress.printSource( tab + "if( origText.equals( \"" + constantElementDataModel.getValue() + "\" ) ){" );			
				if( isInLoop() ){
					elementProgress.printSource( tab + CommonOperations.TAB_BY_SPACE + "break; //because the element '" + baseElement.getNameAsVariable() + "': '\" + origText + \"' equals to '" + constantElementDataModel.getValue() + " + \"'." );
				}else{
					elementProgress.printSource( tab + CommonOperations.TAB_BY_SPACE + "fail(\"Stopped because the element '" + baseElement.getNameAsVariable() + "': '\" + origText + \"' equals to '" + constantElementDataModel.getValue() + "' but it should NOT.\");");
				}
				elementProgress.printSource( tab + "}" );
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
			
		//Ha FIELD/CHECKBOX
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
			
			if( !origText.equals( constantElementDataModel.getValue() ) ){
				
				//Normal
				if( baseElement instanceof NormalBaseElementDataModel ){
					
					throw new ElementCompareOperationException(compareType, constantElementDataModel.getValue(), baseElement.getName(), ((NormalBaseElementDataModel)baseElement).getSelector(), origText, new Exception() );
				
				//Special
				}else{
					
					throw new ElementCompareOperationException(compareType, constantElementDataModel.getValue(), baseElement.getName(), "special", origText, new Exception() );
				}
			}
			
		}else if( compareType.equals( CompareTypeListEnum.DIFFERENT ) ){
			
			if( origText.equals( constantElementDataModel.getValue() ) ){
				
				//Normal
				if( baseElement instanceof NormalBaseElementDataModel ){
					
					throw new ElementCompareOperationException(compareType, constantElementDataModel.getValue(), baseElement.getName(), ((NormalBaseElementDataModel)baseElement).getSelector(), origText, new Exception() );
					
				//Special
				}else{
					
					throw new ElementCompareOperationException(compareType, constantElementDataModel.getValue(), baseElement.getName(), "special", origText, new Exception() );
				}
			}			
		}	
	}
	
	@Override
	public void setXMLAttribute(Document document, Element element) {
		
		Attr attr = document.createAttribute( ATTR_COMPARE_CONSTANT_ELEMENT_PATH );
		attr.setValue( constantElementDataModel.getPathTag() );
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
		
		//Tovabb nem klonozok, mert az mar hivatkozas egy elemre		
		return new CompareValueToConstantOperation(constantElementDataModel, compareType, stringPattern);
	}

	@Override
	public String getOperationNameToString() {		
		return "CompareValueToConstant()";
	}
	
}
