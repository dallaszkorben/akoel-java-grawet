package hu.akoel.grawit.core.operations;

import java.io.StringReader;
import java.util.List;
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
import hu.akoel.grawit.enums.list.ContainTypeListEnum;
import hu.akoel.grawit.enums.list.ListCompareByListEnum;
import hu.akoel.grawit.enums.list.ListSelectionByListEnum;
import hu.akoel.grawit.exceptions.ElementListContainOperationException;
import hu.akoel.grawit.exceptions.ElementException;
import hu.akoel.grawit.exceptions.XMLBaseConversionPharseException;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;
import hu.akoel.grawit.gui.interfaces.progress.ElementProgressInterface;

public class ContainListConstantOperation extends ElementOperationAdapter implements HasConstantOperationInterface{
	
	private static final String NAME = "CONTAINVARIABLE";	
	private static final String ATTR_CONTAIN_CONSTANT_ELEMENT_PATH = "containvariableelementpath";
	private static final String ATTR_CONTAIN_TYPE = "type";
	private static final String ATTR_PATTERN = "pattern";
	private static final String ATTR_CONTAIN_BY = "containby";
	
	// Model
	private String stringPattern;
	private ListCompareByListEnum containBy;
	private ConstantElementDataModel constantElementDataModel;
	private ContainTypeListEnum containType;
	//---
	
	private Pattern pattern;
	
	public ContainListConstantOperation( ConstantElementDataModel constantElementDataModel, ContainTypeListEnum containType, String stringPattern, ListCompareByListEnum compareBy ){
		this.constantElementDataModel = constantElementDataModel;
		this.containType = containType;
		this.stringPattern = stringPattern;
		this.containBy = compareBy;
		
		common( stringPattern );
	}
	
	public ContainListConstantOperation( Element element, ConstantRootDataModel constantRootDataModel, Tag rootTag, Tag tag, String nameAttrName, String nameAttrValue ) throws XMLBaseConversionPharseException, XMLMissingAttributePharseException{
		
		ConstantDataModelAdapter constantDataModelForFillOut = constantRootDataModel;
		
		//COMPARE BY
		String stringCompareBy = "";
		if( !element.hasAttribute( ATTR_CONTAIN_BY ) ){
			stringCompareBy = ListCompareByListEnum.BYVALUE.name();
			//throw new XMLMissingAttributePharseException( rootTag, tag, ATTR_COMPARE_BY );			
		}
		stringCompareBy = element.getAttribute( ATTR_CONTAIN_BY );		
		if( stringCompareBy.equals( ListSelectionByListEnum.BYVALUE.name() ) ){
			containBy = ListCompareByListEnum.BYVALUE;
		}else if( stringCompareBy.equals( ListSelectionByListEnum.BYVISIBLETEXT.name() ) ){
			containBy = ListCompareByListEnum.BYVISIBLETEXT;
		}else{
			containBy = ListCompareByListEnum.BYVISIBLETEXT;
		}	
		
		//ATTR_COMPARE_TYPE
		if( !element.hasAttribute( ATTR_CONTAIN_TYPE ) ){
			throw new XMLMissingAttributePharseException( rootTag, tag, ATTR_CONTAIN_TYPE );		
		}	
		String typeString = element.getAttribute(ATTR_CONTAIN_TYPE);
		this.containType = ContainTypeListEnum.valueOf( typeString );
		
		//ATTR_COMPARE_CONSTANT_ELEMENT_PATH
		if( !element.hasAttribute( ATTR_CONTAIN_CONSTANT_ELEMENT_PATH ) ){
			throw new XMLMissingAttributePharseException( rootTag, tag, ATTR_CONTAIN_CONSTANT_ELEMENT_PATH );		
		}
		String constantElementPathString = element.getAttribute(ATTR_CONTAIN_CONSTANT_ELEMENT_PATH);				
		constantElementPathString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + constantElementPathString;  
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
	    DocumentBuilder builder;
	    Document document = null;
	    try{  
	        builder = factory.newDocumentBuilder();  
	        document = builder.parse( new InputSource( new StringReader( constantElementPathString ) ) );  
	    } catch (Exception e) {  
	    
	    	//Nem sikerult az atalakitas
	    	throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, nameAttrValue, ATTR_CONTAIN_CONSTANT_ELEMENT_PATH, element.getAttribute(ATTR_CONTAIN_CONSTANT_ELEMENT_PATH), e );
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

	    			throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, nameAttrValue, ATTR_CONTAIN_CONSTANT_ELEMENT_PATH, element.getAttribute(ATTR_CONTAIN_CONSTANT_ELEMENT_PATH) );
	    		}
	    		
	    	//Ha CONSTANTELEMENT
	    	}else if( tagName.equals( ConstantElementDataModel.TAG.getName() ) ){
	    		attrName = actualElement.getAttribute(ConstantElementDataModel.ATTR_NAME);
	    		constantDataModelForFillOut = (ConstantDataModelAdapter) CommonOperations.getDataModelByNameInLevel( constantDataModelForFillOut, Tag.CONSTANTELEMENT, attrName );
	    		
	    		if( null == constantDataModelForFillOut ){

	    			throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, getName(), ATTR_CONTAIN_CONSTANT_ELEMENT_PATH, element.getAttribute(ATTR_CONTAIN_CONSTANT_ELEMENT_PATH) );
	    		}
	    		
	    	}else{
	    		
	    		throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, getName(), ATTR_CONTAIN_CONSTANT_ELEMENT_PATH, element.getAttribute(ATTR_CONTAIN_CONSTANT_ELEMENT_PATH) );	    		
	    	}
	    }	    
	    try{
	    	
	    	this.constantElementDataModel = (ConstantElementDataModel)constantDataModelForFillOut;
	    	
	    }catch(ClassCastException e){

	    	//Nem sikerult az utvonalat megtalalni
	    	throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, nameAttrValue, ATTR_CONTAIN_CONSTANT_ELEMENT_PATH, element.getAttribute(ATTR_CONTAIN_CONSTANT_ELEMENT_PATH ), e );
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
	
	public ListCompareByListEnum getContainBy(){
		return containBy;
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

	public ContainTypeListEnum getContainType(){
		return containType;
	}

	@Override
	public void doOperation(WebDriver driver, BaseElementDataModelAdapter baseElement, WebElement webElement, ElementProgressInterface elementProgress, String tab) throws ElementException {
		
		//
		// Execute the OPERATION
		//		

		elementProgress.outputCommand( tab + "select = new Select(webElement);" );
		elementProgress.outputCommand( tab + "optionList = select.getOptions();" );
		elementProgress.outputCommand( tab + "boolean found = false;" );		
		elementProgress.outputCommand( tab + "for( WebElement option: optionList ){" );
		
		Select select = new Select(webElement);
		
		//Osszegyujti a menu teljes tartalmat
		List<WebElement> optionList = select.getOptions();
	
		String optionText;
		
		boolean found = false;
		
		//Vegig megy a lista elemeken
		for( WebElement option: optionList ){
			
			elementProgress.outputCommand( tab + "optionText = \"\";" );
			
			optionText = "";
			
			//VALUE
			if( containBy.equals( ListCompareByListEnum.BYVALUE ) ){
				
				elementProgress.outputCommand( tab + "optionText = option.getAttribute(\"value\");" );
						
				optionText = option.getAttribute("value");
				
			//TEXT
			}else if( containBy.equals( ListCompareByListEnum.BYVISIBLETEXT ) ){
				
				elementProgress.outputCommand( tab + "optionText = option.getText();" ); 
				
				optionText = option.getText();	
			}	
			
			
			
			if( null != pattern ){
				Matcher matcher = pattern.matcher( optionText );
				if( matcher.find() ){
					optionText = matcher.group();
					
					elementProgress.outputCommand( tab + "pattern = Pattern.compile( " + pattern.pattern() + " );" );
					elementProgress.outputCommand( tab + "matcher = pattern.matcher( origText );");
					elementProgress.outputCommand( tab + "origText = matcher.group();" );
				}			
			}
			
			elementProgress.outputCommand( tab + "if( optionText.equals( " + constantElementDataModel.getValue() + " ) ){" );
			elementProgress.outputCommand( tab + CommonOperations.TAB_BY_SPACE + "found = true;" );
			elementProgress.outputCommand( tab + CommonOperations.TAB_BY_SPACE + "break;" );
			elementProgress.outputCommand( tab + "}" );
			
			//Ha megtalalta a listaban a keresett erteket
			if( optionText.equals( constantElementDataModel.getValue() ) ){
				found = true;
				break;
			}
			
		}
		
		elementProgress.outputCommand( tab + "} //for( WebElement option: optionList )" );
		
		//Tartalmaznia kell a listanak a Stringben tarolt erteket DE nincs a listaban
		if( containType.equals( ContainTypeListEnum.CONTAINS ) && !found ){
			
			elementProgress.outputCommand( tab + "System.err.println(\"Stopped because the expection is: " + ContainTypeListEnum.CONTAINS.getTranslatedName() + " BUT " + constantElementDataModel.getValue() + " is NOT in the list\")");
			elementProgress.outputCommand( tab + "System.exit(-1)");
			
			if( baseElement instanceof NormalBaseElementDataModel ){

				throw new ElementListContainOperationException( (NormalBaseElementDataModel)baseElement, containType, constantElementDataModel.getValue(), false, new Exception() );

			}
			
		//Nem szabad tartalmaznia DE megis a listaban van 	
		}else if( containType.equals( ContainTypeListEnum.NOCONTAINS ) && found ){
		
			elementProgress.outputCommand( tab + "System.err.println(\"Stopped because the expection is: " + ContainTypeListEnum.NOCONTAINS.getTranslatedName() + " BUT " + constantElementDataModel.getValue() + " IS in the list\")");
			elementProgress.outputCommand( tab + "System.exit(-1)");

			if( baseElement instanceof NormalBaseElementDataModel ){
					
				throw new ElementListContainOperationException( (NormalBaseElementDataModel)baseElement, containType, constantElementDataModel.getValue(), true, new Exception() );
			}
		}
	}

	@Override
	public void setXMLAttribute(Document document, Element element) {
		Attr attr = document.createAttribute( ATTR_CONTAIN_CONSTANT_ELEMENT_PATH );
		attr.setValue( constantElementDataModel.getPathTag() );
		element.setAttributeNode( attr );		
		
		attr = document.createAttribute( ATTR_CONTAIN_TYPE );
		attr.setValue( containType.name() );
		element.setAttributeNode( attr );
		
		attr = document.createAttribute( ATTR_CONTAIN_BY );
		attr.setValue( containBy.name() );
		element.setAttributeNode( attr );	
		
		attr = document.createAttribute( ATTR_PATTERN );
		attr.setValue( stringPattern );
		element.setAttributeNode(attr);	
	}

	@Override
	public Object clone() {
		
		String stringPattern = new String( this.stringPattern );
		
		return new ContainListConstantOperation(constantElementDataModel, containType, stringPattern, containBy); 

	}

	@Override
	public String getOperationNameToString() {		
		return "CompareListToConstant()";
	}
	
}
