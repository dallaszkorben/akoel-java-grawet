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
import hu.akoel.grawit.ElementProgressInterface;
import hu.akoel.grawit.core.treenodedatamodel.BaseDataModelInterface;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseNodeDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.BasePageDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamElementDataModel;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.enums.list.CompareTypeListEnum;
import hu.akoel.grawit.enums.list.ListCompareByListEnum;
import hu.akoel.grawit.enums.list.ListSelectionByListEnum;
import hu.akoel.grawit.exceptions.ElementCompareOperationException;
import hu.akoel.grawit.exceptions.ElementException;
import hu.akoel.grawit.exceptions.XMLBaseConversionPharseException;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;

public class CompareListToStoredElementOperation extends ElementOperationAdapter{
	
	private static final String NAME = "COMPARELISTTOSTOREDELEMENT";	
	private static final String ATTR_COMPARE_BASE_ELEMENT_PATH = "comparebaseelementpath";
	private static final String ATTR_COMPARE_TYPE = "type";
	private static final String ATTR_PATTERN = "pattern";
	private static final String ATTR_COMPARE_BY = "compareby";
	
	private Pattern pattern;
	private String stringPattern;
	private ListCompareByListEnum compareBy;
	private BaseElementDataModel baseElementDataModel;
	private CompareTypeListEnum compareType;
		
	public CompareListToStoredElementOperation( BaseElementDataModel baseElementDataModel, CompareTypeListEnum compareType, String stringPattern, ListCompareByListEnum compareBy ){
		this.baseElementDataModel = baseElementDataModel;
		this.compareType = compareType;
		this.stringPattern = stringPattern;
		this.compareBy = compareBy;
		
		common( stringPattern );
	}

	public CompareListToStoredElementOperation( Element element, BaseRootDataModel baseRootDataModel, Tag rootTag, Tag tag, String nameAttrName, String nameAttrValue ) throws XMLBaseConversionPharseException, XMLMissingAttributePharseException{		
		
		BaseDataModelInterface baseDataModelForFillOut = baseRootDataModel;
		
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
		
		//ATTR_COMPARE_BASE_ELEMENT_PATH
		if( !element.hasAttribute( ATTR_COMPARE_BASE_ELEMENT_PATH ) ){
			throw new XMLMissingAttributePharseException( rootTag, tag, ATTR_COMPARE_BASE_ELEMENT_PATH );		
		}	
		String baseElementPathString = element.getAttribute(ATTR_COMPARE_BASE_ELEMENT_PATH);				
		baseElementPathString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + baseElementPathString;  
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
	    DocumentBuilder builder;
	    Document document = null;
	    try{  
	        builder = factory.newDocumentBuilder();  
	        document = builder.parse( new InputSource( new StringReader( baseElementPathString ) ) );  
	    } catch (Exception e) {  
	    
	    	//Nem sikerult az atalakitas
	    	throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, nameAttrValue, ATTR_COMPARE_BASE_ELEMENT_PATH, element.getAttribute(ATTR_COMPARE_BASE_ELEMENT_PATH), e );
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
	    		baseDataModelForFillOut = (BaseDataModelInterface) CommonOperations.getDataModelByNameInLevel( baseDataModelForFillOut, Tag.BASENODE, attrName );

	    		if( null == baseDataModelForFillOut ){

	    			throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, nameAttrValue, ATTR_COMPARE_BASE_ELEMENT_PATH, element.getAttribute(ATTR_COMPARE_BASE_ELEMENT_PATH) );
	    		}
	    		
	    	//Ha BASEELEMENT
	    	}else if( tagName.equals( BaseElementDataModel.TAG.getName() ) ){
	    		attrName = actualElement.getAttribute(BaseElementDataModel.ATTR_NAME);
	    		baseDataModelForFillOut = (BaseDataModelInterface) CommonOperations.getDataModelByNameInLevel( baseDataModelForFillOut, Tag.BASEELEMENT, attrName );
	
	    		if( null == baseDataModelForFillOut ){

	    			throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, nameAttrValue, ATTR_COMPARE_BASE_ELEMENT_PATH, element.getAttribute(ATTR_COMPARE_BASE_ELEMENT_PATH) );
	    		}

	    	
	    	//Ha BASEPAGE
	    	}else if( tagName.equals( BasePageDataModel.TAG.getName() ) ){
	    		attrName = actualElement.getAttribute(BasePageDataModel.ATTR_NAME);
	    		baseDataModelForFillOut = (BaseDataModelInterface) CommonOperations.getDataModelByNameInLevel( baseDataModelForFillOut, Tag.BASEPAGE, attrName );

	    		if( null == baseDataModelForFillOut ){

	    			throw new XMLBaseConversionPharseException( rootTag, tag,  nameAttrName, nameAttrValue, ATTR_COMPARE_BASE_ELEMENT_PATH, element.getAttribute(ATTR_COMPARE_BASE_ELEMENT_PATH) );
	    		}
	    		
	    	}else{
	    		
	    		throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, nameAttrValue, ATTR_COMPARE_BASE_ELEMENT_PATH, element.getAttribute(ATTR_COMPARE_BASE_ELEMENT_PATH) );	    		
	    	}
	    }	    
	    try{
	    	
	    	this.baseElementDataModel = (BaseElementDataModel)baseDataModelForFillOut;
	    	
	    }catch(ClassCastException e){

	    	//Nem sikerult az utvonalat megtalalni
	    	throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, nameAttrValue, ATTR_COMPARE_BASE_ELEMENT_PATH, element.getAttribute(ATTR_COMPARE_BASE_ELEMENT_PATH ), e );
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
	
	public BaseElementDataModel getBaseElement() {
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
	public void doOperation(WebDriver driver, ParamElementDataModel element, WebElement webElement, ElementProgressInterface elementProgress) throws ElementException {

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
			
			if( !origText.equals( baseElementDataModel.getStoredValue() ) ){
				throw new ElementCompareOperationException(compareType, baseElementDataModel.getStoredValue(), element.getName(), element.getBaseElement().getSelector(), origText, new Exception() );
			}
			
		}else if( compareType.equals( CompareTypeListEnum.DIFFERENT ) ){
			
			if( origText.equals( baseElementDataModel.getStoredValue() ) ){
				throw new ElementCompareOperationException(compareType, baseElementDataModel.getStoredValue(), element.getName(), element.getBaseElement().getSelector(), origText, new Exception() );
			}			
		}		
	}
	
	@Override
	public void setXMLAttribute(Document document, Element element) {		
		Attr attr = document.createAttribute( ATTR_COMPARE_BASE_ELEMENT_PATH );
		attr.setValue( baseElementDataModel.getPathTag() );
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
		
		BaseElementDataModel baseElementDataModel = (BaseElementDataModel) this.baseElementDataModel.clone();
		CompareTypeListEnum compareType = this.compareType;		//TODO ez kedes, hogy jo-e
		String stringPattern = new String( this.stringPattern );
		ListCompareByListEnum compareBy = this.compareBy;		//TODO ez kerdes, hogy jo-e
				
		return new CompareListToStoredElementOperation(baseElementDataModel, compareType, stringPattern, compareBy);
	}
	
}
