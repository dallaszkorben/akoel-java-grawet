package hu.akoel.grawit.core.operations;

import java.io.StringReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
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
import hu.akoel.grawit.enums.list.ListCompareByListEnum;
import hu.akoel.grawit.enums.list.ListSelectionByListEnum;
import hu.akoel.grawit.exceptions.ElementCompareOperationException;
import hu.akoel.grawit.exceptions.ElementException;
import hu.akoel.grawit.exceptions.XMLBaseConversionPharseException;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;
import hu.akoel.grawit.gui.interfaces.progress.ElementProgressInterface;

public class CompareListToConstantOperation extends ElementOperationAdapter implements HasConstantOperationInterface{
	
	private static final String NAME = "COMPARELISTTOCONSTANT";	
	private static final String ATTR_COMPARE_CONSTANT_ELEMENT_PATH = "compareconstantelementpath";
	private static final String ATTR_COMPARE_TYPE = "type";
	private static final String ATTR_PATTERN = "pattern";
	private static final String ATTR_COMPARE_BY = "compareby";
	
	// Model
	private String stringPattern;
	private ListCompareByListEnum compareBy;
	private ConstantElementDataModel constantElementDataModel;
	private CompareTypeListEnum compareType;
	//---
	
	private Pattern pattern;
	
	public CompareListToConstantOperation( ConstantElementDataModel constantElementDataModel, CompareTypeListEnum compareType, String stringPattern, ListCompareByListEnum compareBy ){
		this.constantElementDataModel = constantElementDataModel;
		this.compareType = compareType;
		this.stringPattern = stringPattern;
		this.compareBy = compareBy;
		
		common( stringPattern );
	}
	
	public CompareListToConstantOperation( Element element, ConstantRootDataModel constantRootDataModel, Tag rootTag, Tag tag, String nameAttrName, String nameAttrValue ) throws XMLBaseConversionPharseException, XMLMissingAttributePharseException{
		
		ConstantDataModelAdapter constantDataModelForFillOut = constantRootDataModel;
		
		//COMPARE BY
		String stringCompareBy = "";
		if( !element.hasAttribute( ATTR_COMPARE_BY ) ){
			stringCompareBy = ListCompareByListEnum.BYVALUE.name();
			//throw new XMLMissingAttributePharseException( rootTag, tag, ATTR_COMPARE_BY );			
		}
		stringCompareBy = element.getAttribute( ATTR_COMPARE_BY );		
		if( stringCompareBy.equals( ListSelectionByListEnum.BYVALUE.name() ) ){
			compareBy = ListCompareByListEnum.BYVALUE;
		}else if( stringCompareBy.equals( ListSelectionByListEnum.BYVISIBLETEXT.name() ) ){
			compareBy = ListCompareByListEnum.BYVISIBLETEXT;
		}else{
			compareBy = ListCompareByListEnum.BYVISIBLETEXT;
		}	
		
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
	    		
	    	//Ha CONSTANT ELEMENT
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
	
	private void common( String stringPattern ){
		
		if( stringPattern.trim().length() == 0 ){
			pattern = null;
		}else{		
			pattern = Pattern.compile( stringPattern );
		}
		
	}
	
	public ListCompareByListEnum getCompareBy(){
		return compareBy;
	}
	
	public static String getStaticName(){
		return NAME;
	}
	
	@Override
	public String getName() {		
		return getStaticName();
	}
	
	@Override
	public ConstantElementDataModel getConstantElement() {
		return constantElementDataModel;
	}

	public CompareTypeListEnum getCompareType(){
		return compareType;
	}

	@Override
	public void doOperation(WebDriver driver, BaseElementDataModelAdapter baseElement, WebElement webElement, ElementProgressInterface elementProgress, String tab) throws ElementException {
		
		//
		// SOURCE Starts
		//		
		elementProgress.outputCommand( tab + "origText = \"\";" );
		elementProgress.outputCommand( tab + "select = new Select(webElement);" );
		
		//VALUE
		if( compareBy.equals( ListCompareByListEnum.BYVALUE ) ){
			elementProgress.outputCommand( tab + "origText = select.getFirstSelectedOption().getAttribute(\"value\");" );
			
		//TEXT
		}else if( compareBy.equals( ListCompareByListEnum.BYVISIBLETEXT ) ){
			elementProgress.outputCommand( tab + "origText = select.getFirstSelectedOption().getText();" );
		}		
		if( null != pattern ){
			elementProgress.outputCommand( tab + "pattern = Pattern.compile( \"" + pattern.pattern().replace("\\", "\\\\") + "\" );" );
			elementProgress.outputCommand( tab + "matcher = pattern.matcher( origText );");				
			elementProgress.outputCommand( tab + "if( matcher.find() ){" );	
			elementProgress.outputCommand( tab + CommonOperations.TAB_BY_SPACE + "origText = matcher.group();" );
			elementProgress.outputCommand( tab + "}" );
		}
		if( compareType.equals( CompareTypeListEnum.EQUAL ) ){			
			elementProgress.outputCommand( tab + "if( !origText.equals( \"" + constantElementDataModel.getValue() + "\" ) ){" );
			elementProgress.outputCommand( tab + CommonOperations.TAB_BY_SPACE + "System.err.println(\"Stopped because the selected element in the Select '" + baseElement.getNameAsVariable() + "': '\" + origText + \"' does NOT equal to '" + constantElementDataModel.getValue() + "' but it should.\");");
			elementProgress.outputCommand( tab + CommonOperations.TAB_BY_SPACE + "System.exit(-1);");
			elementProgress.outputCommand( tab + "}" );
		}else if( compareType.equals( CompareTypeListEnum.DIFFERENT ) ){
			elementProgress.outputCommand( tab + "if( origText.equals( \"" + constantElementDataModel.getValue() + "\" ) ){" );
			elementProgress.outputCommand( tab + CommonOperations.TAB_BY_SPACE + "System.err.println(\"Stopped because the selected element in the Select '" + baseElement.getNameAsVariable() + "': '\" + origText + \"' equals to '" + constantElementDataModel.getValue() + "' but it should NOT.\");");
			elementProgress.outputCommand( tab + CommonOperations.TAB_BY_SPACE + "System.exit(-1);");				
			elementProgress.outputCommand( tab + "}" );
		}		
		
		//
		// CODE Starts
		//
		String origText = "";
		Select select = new Select(webElement);
		
		//VALUE
		if( compareBy.equals( ListCompareByListEnum.BYVALUE ) ){
			
			origText = select.getFirstSelectedOption().getAttribute("value");
			
		//TEXT
		}else if( compareBy.equals( ListCompareByListEnum.BYVISIBLETEXT ) ){

			origText = select.getFirstSelectedOption().getText();			
		}	
		
		if( null != pattern ){
			Matcher matcher = pattern.matcher( origText );
			
			if( matcher.find() ){
				
				origText = matcher.group();
			}
		}		

		if( compareType.equals( CompareTypeListEnum.EQUAL ) ){
			
			if( !origText.equals( constantElementDataModel.getValue() ) ){
				
				if( baseElement instanceof NormalBaseElementDataModel ){
					throw new ElementCompareOperationException(compareType, constantElementDataModel.getValue(), baseElement.getName(), ((NormalBaseElementDataModel)baseElement).getSelector(), origText, new Exception() );
				//Special
				}else{
					throw new ElementCompareOperationException(compareType, constantElementDataModel.getValue(), baseElement.getName(), "special", origText, new Exception() );
				}
			}
			
		}else if( compareType.equals( CompareTypeListEnum.DIFFERENT ) ){
			
			if( origText.equals( constantElementDataModel.getValue() ) ){
				
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
		
		attr = document.createAttribute( ATTR_COMPARE_BY );
		attr.setValue( compareBy.name() );
		element.setAttributeNode( attr );	
		
		attr = document.createAttribute( ATTR_PATTERN );
		attr.setValue( stringPattern );
		element.setAttributeNode(attr);	
	}

	@Override
	public Object clone() {
		
		String stringPattern = new String( this.stringPattern );
		
		return new CompareListToConstantOperation(constantElementDataModel, compareType, stringPattern, compareBy); 

	}

	@Override
	public String getOperationNameToString() {		
		return "CompareListToString()";
	}
	
}
