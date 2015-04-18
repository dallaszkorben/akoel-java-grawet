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
import hu.akoel.grawit.core.treenodedatamodel.base.BaseCollectorDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseElementDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseFolderDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.NormalBaseElementDataModel;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.enums.list.ContainTypeListEnum;
import hu.akoel.grawit.enums.list.ListCompareByListEnum;
import hu.akoel.grawit.enums.list.ListSelectionByListEnum;
import hu.akoel.grawit.exceptions.ElementListContainOperationException;
import hu.akoel.grawit.exceptions.ElementException;
import hu.akoel.grawit.exceptions.XMLBaseConversionPharseException;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;
import hu.akoel.grawit.gui.interfaces.progress.ElementProgressInterface;

public class ContainListStoredElementOperation extends ElementOperationAdapter implements HasElementOperationInterface{
	
	private static final String NAME = "CONTAINLISTSTOREDELEMENT";	
	private static final String ATTR_CONTAIN_BASE_ELEMENT_PATH = "containebaseelementpath";
	private static final String ATTR_CONTAIN_TYPE = "type";
	private static final String ATTR_PATTERN = "pattern";
	private static final String ATTR_CONTAIN_BY = "containby";
	
	private Pattern pattern;
	private String stringPattern;
	private ListCompareByListEnum containBy;
	private BaseElementDataModelAdapter baseElementForSearch;
	private ContainTypeListEnum containType;
		
	public ContainListStoredElementOperation( BaseElementDataModelAdapter baseElementForSearch, ContainTypeListEnum containTypeListEnumm, String stringPattern, ListCompareByListEnum containBy ){
		this.baseElementForSearch = baseElementForSearch;
		this.containType = containTypeListEnumm;
		this.stringPattern = stringPattern;
		this.containBy = containBy;
		
		common( stringPattern );
		
	}

	public ContainListStoredElementOperation( Element element, BaseRootDataModel baseRootDataModel, Tag rootTag, Tag tag, String nameAttrName, String nameAttrValue ) throws XMLBaseConversionPharseException, XMLMissingAttributePharseException{		
		
		BaseDataModelAdapter baseDataModelForCompareList = baseRootDataModel;
		
		//CONTAIN BY
		String stringCompareBy = "";
		if( !element.hasAttribute( ATTR_CONTAIN_BY ) ){
			stringCompareBy = ListCompareByListEnum.BYVALUE.name();
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
		
		//ATTR_COMPARE_BASE_ELEMENT_PATH
		if( !element.hasAttribute( ATTR_CONTAIN_BASE_ELEMENT_PATH ) ){
			throw new XMLMissingAttributePharseException( rootTag, tag, ATTR_CONTAIN_BASE_ELEMENT_PATH );		
		}	
		String baseElementPathString = element.getAttribute(ATTR_CONTAIN_BASE_ELEMENT_PATH);				
		baseElementPathString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + baseElementPathString;  
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
	    DocumentBuilder builder;
	    Document document = null;
	    try{  
	        builder = factory.newDocumentBuilder();  
	        document = builder.parse( new InputSource( new StringReader( baseElementPathString ) ) );  
	    } catch (Exception e) {  
	    
	    	//Nem sikerult az atalakitas
	    	throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, nameAttrValue, ATTR_CONTAIN_BASE_ELEMENT_PATH, element.getAttribute(ATTR_CONTAIN_BASE_ELEMENT_PATH), e );
	    } 

	    //Megkeresem a PARAMELEMENTROOT-ben a PARAMELEMENT-hez vezeto utat
	    Node actualNode = document;
    
	    while( actualNode.hasChildNodes() ){
		
	    	actualNode = actualNode.getFirstChild();
	    	Element actualElement = (Element)actualNode;
	    	String tagName = actualElement.getTagName();
	    	String attrName = null;
	    	
	    	//Ha BASEFOLDER
	    	if( tagName.equals( BaseFolderDataModel.TAG.getName() ) ){
	    		attrName = actualElement.getAttribute(BaseFolderDataModel.ATTR_NAME);	    		
	    		baseDataModelForCompareList = (BaseDataModelAdapter) CommonOperations.getDataModelByNameInLevel( baseDataModelForCompareList, Tag.BASEFOLDER, attrName );

	    		if( null == baseDataModelForCompareList ){

	    			throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, nameAttrValue, ATTR_CONTAIN_BASE_ELEMENT_PATH, element.getAttribute(ATTR_CONTAIN_BASE_ELEMENT_PATH) );
	    		}
	    		
	    	//HA BASEELEMENT
	    	}else if( tagName.equals( BaseElementDataModelAdapter.TAG.getName() ) ){	
		    		
	    		attrName = actualElement.getAttribute(NormalBaseElementDataModel.ATTR_NAME);
		    		
	    		baseDataModelForCompareList = (BaseDataModelAdapter) CommonOperations.getDataModelByNameInLevel( baseDataModelForCompareList, Tag.BASEELEMENT, attrName );
	    		if( null == baseDataModelForCompareList ){

	    			throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, nameAttrValue, ATTR_CONTAIN_BASE_ELEMENT_PATH, element.getAttribute(ATTR_CONTAIN_BASE_ELEMENT_PATH) );
	    		}	    		
	    		
	    	//Ha BASEPAGE
	    	}else if( tagName.equals( BaseCollectorDataModel.TAG.getName() ) ){
	    		attrName = actualElement.getAttribute(BaseCollectorDataModel.ATTR_NAME);
	    		baseDataModelForCompareList = (BaseDataModelAdapter) CommonOperations.getDataModelByNameInLevel( baseDataModelForCompareList, Tag.BASECOLLECTOR, attrName );

	    		if( null == baseDataModelForCompareList ){

	    			throw new XMLBaseConversionPharseException( rootTag, tag,  nameAttrName, nameAttrValue, ATTR_CONTAIN_BASE_ELEMENT_PATH, element.getAttribute(ATTR_CONTAIN_BASE_ELEMENT_PATH) );
	    		}
	    		
	    	}else{
	    		
	    		throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, nameAttrValue, ATTR_CONTAIN_BASE_ELEMENT_PATH, element.getAttribute(ATTR_CONTAIN_BASE_ELEMENT_PATH) );	    		
	    	}
	    }	    
	    try{
	    	
	    	this.baseElementForSearch = (BaseElementDataModelAdapter)baseDataModelForCompareList;
	    	
	    }catch(ClassCastException e){

	    	//Nem sikerult az utvonalat megtalalni
	    	throw new XMLBaseConversionPharseException( rootTag, tag, nameAttrName, nameAttrValue, ATTR_CONTAIN_BASE_ELEMENT_PATH, element.getAttribute(ATTR_CONTAIN_BASE_ELEMENT_PATH ), e );
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
		
	@Override
	public BaseElementDataModelAdapter getBaseElementForSearch() {
		return baseElementForSearch;
	}

	public static String getStaticName(){
		return NAME;
	}
	
	@Override
	public String getName() {		
		return getStaticName();
	}
		
	public ContainTypeListEnum getContainType(){
		return containType;
	}
	
	@Override
	public void doOperation(WebDriver driver, BaseElementDataModelAdapter baseElement, WebElement webElement, ElementProgressInterface elementProgress, String tab) throws ElementException {

		if( null != elementProgress ){
		//
		// SOURCE Starts
		//		
		elementProgress.outputCommand( tab + "select = new Select(webElement);" );
		elementProgress.outputCommand( tab + "optionList = select.getOptions();" );
		elementProgress.outputCommand( tab + "found = false;" );		
		elementProgress.outputCommand( tab + "for( WebElement option: optionList ){" );		
		elementProgress.outputCommand( tab + CommonOperations.TAB_BY_SPACE + "optionText = \"\";" );		
		
		//VALUE
		if( containBy.equals( ListCompareByListEnum.BYVALUE ) ){			
			elementProgress.outputCommand( tab + CommonOperations.TAB_BY_SPACE + "optionText = option.getAttribute(\"value\");" );
			
		//TEXT
		}else if( containBy.equals( ListCompareByListEnum.BYVISIBLETEXT ) ){		
			elementProgress.outputCommand( tab + CommonOperations.TAB_BY_SPACE + "optionText = option.getText();" );		
		}		
		if( null != pattern ){			
			elementProgress.outputCommand( tab + CommonOperations.TAB_BY_SPACE + "pattern = Pattern.compile( \"" + pattern.pattern().replace("\\", "\\\\") + "\" );" );
			elementProgress.outputCommand( tab + CommonOperations.TAB_BY_SPACE + "matcher = pattern.matcher( origText );");	
			elementProgress.outputCommand( tab + CommonOperations.TAB_BY_SPACE + "if( matcher.find() ){" );	
			elementProgress.outputCommand( tab + CommonOperations.TAB_BY_SPACE + CommonOperations.TAB_BY_SPACE + CommonOperations.TAB_BY_SPACE + "optionText = matcher.group();" );
			elementProgress.outputCommand( tab + CommonOperations.TAB_BY_SPACE + "}" );		
		}	
		elementProgress.outputCommand( tab + CommonOperations.TAB_BY_SPACE + "if( optionText.equals( " + getBaseElementForSearch().getNameAsVariable() + " ) ){" );	
		elementProgress.outputCommand( tab + CommonOperations.TAB_BY_SPACE + CommonOperations.TAB_BY_SPACE + CommonOperations.TAB_BY_SPACE + "found = true;" );
		elementProgress.outputCommand( tab + CommonOperations.TAB_BY_SPACE + CommonOperations.TAB_BY_SPACE + CommonOperations.TAB_BY_SPACE + "break;" );
		elementProgress.outputCommand( tab + CommonOperations.TAB_BY_SPACE + "}" );			
		elementProgress.outputCommand( tab + "} //for( WebElement option: optionList )" );
		
		//Tartalmaznia kell a listanak a Stringben tarolt erteket DE nincs a listaban
		if( containType.equals( ContainTypeListEnum.CONTAINS ) ){			
			elementProgress.outputCommand( tab + "if( !found ){" );
			elementProgress.outputCommand( tab + CommonOperations.TAB_BY_SPACE + "System.err.println(\"Stopped because for the list '" + baseElement.getNameAsVariable() + "' the expection is: '" + ContainTypeListEnum.CONTAINS.getTranslatedName() + "' BUT '" + getBaseElementForSearch().getStoredValue() + "' is NOT in the list\");");
			elementProgress.outputCommand( tab + CommonOperations.TAB_BY_SPACE + "System.exit(-1);");
			elementProgress.outputCommand( tab + "}" );			
		//Nem szabad tartalmaznia DE megis a listaban van 	
		}else if( containType.equals( ContainTypeListEnum.NOCONTAINS ) ){			
			elementProgress.outputCommand( tab + "if( found ){" );
			elementProgress.outputCommand( tab + CommonOperations.TAB_BY_SPACE + "System.err.println(\"Stopped because for the list '" + baseElement.getNameAsVariable() + "' the expection is: '" + ContainTypeListEnum.NOCONTAINS.getTranslatedName() + "' BUT '" + getBaseElementForSearch().getStoredValue() + "' IS in the list\");");
			elementProgress.outputCommand( tab + CommonOperations.TAB_BY_SPACE + "System.exit(-1);");
			elementProgress.outputCommand( tab + "}" );
		}		
		}
		
		//
		// CODE Starts
		//
		Select select = new Select(webElement);
		
		//Osszegyujti az menu teljes tartalmat
		List<WebElement> optionList = select.getOptions();
	
		String optionText;
		
		boolean found = false;
		
		//Vegig megy a lista elemeken
		for( WebElement option: optionList ){
			
			optionText = "";
			
			//VALUE
			if( containBy.equals( ListCompareByListEnum.BYVALUE ) ){
				
				optionText = option.getAttribute("value");
				
			//TEXT
			}else if( containBy.equals( ListCompareByListEnum.BYVISIBLETEXT ) ){
				
				optionText = option.getText();	
			}	
			
			if( null != pattern ){
				Matcher matcher = pattern.matcher( optionText );
				
				if( matcher.find() ){
					
					optionText = matcher.group();
				}
				
			}
			
			//Ha megtalalta a listaban a keresett erteket
			if( optionText.equals( getBaseElementForSearch().getStoredValue() ) ){
				found = true;
				break;
			}
			
		}
		
		//Tartalmaznia kell a listanak a Stringben tarolt erteket DE nincs a listaban
		if( containType.equals( ContainTypeListEnum.CONTAINS ) && !found ){
			
			if( baseElement instanceof NormalBaseElementDataModel ){

				throw new ElementListContainOperationException( (NormalBaseElementDataModel)baseElement, containType, baseElementForSearch.getStoredValue(), false, new Exception() );

			}
			
		//Nem szabad tartalmaznia DE megis a listaban van 	
		}else if( containType.equals( ContainTypeListEnum.NOCONTAINS ) && found ){

			if( baseElement instanceof NormalBaseElementDataModel ){
					
				throw new ElementListContainOperationException( (NormalBaseElementDataModel)baseElement, containType, baseElementForSearch.getStoredValue(), true, new Exception() );
			}
		}
	}
	
	@Override
	public void setXMLAttribute(Document document, Element element) {		
		Attr attr = document.createAttribute( ATTR_CONTAIN_BASE_ELEMENT_PATH );
		attr.setValue( baseElementForSearch.getPathTag() );
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
		
		BaseElementDataModelAdapter baseElementDataModel = (BaseElementDataModelAdapter) this.baseElementForSearch.clone();
			
		String stringPattern = new String( this.stringPattern );
				
		return new ContainListStoredElementOperation(baseElementDataModel, containType, stringPattern, containBy);
	}
	
	@Override
	public String getOperationNameToString() {		
		return "CompareListToStoredElement()";
	}
	
}
