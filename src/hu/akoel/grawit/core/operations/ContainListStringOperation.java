package hu.akoel.grawit.core.operations;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseElementDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.NormalBaseElementDataModel;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.enums.list.ContainTypeListEnum;
import hu.akoel.grawit.enums.list.ListCompareByListEnum;
import hu.akoel.grawit.enums.list.ListSelectionByListEnum;
import hu.akoel.grawit.exceptions.ElementListContainOperationException;
import hu.akoel.grawit.exceptions.ElementException;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;
import hu.akoel.grawit.gui.interfaces.progress.ElementProgressInterface;

public class ContainListStringOperation extends ElementOperationAdapter{
	
	private static final String NAME = "CONTAINSTRING";
	private static final String ATTR_STRING = "string";
	private static final String ATTR_CONTAIN_TYPE = "type";
	private static final String ATTR_PATTERN = "pattern";
	private static final String ATTR_CONTAIN_BY = "containby";
	
	// Model
	private String stringPattern;
	private ListCompareByListEnum containBy;	
	private String stringForSearch;
	private ContainTypeListEnum containType;
	//----
	
	private Pattern pattern;
	
	public ContainListStringOperation( String stringForSearch, ContainTypeListEnum containType, String stringPattern, ListCompareByListEnum containBy ){
		this.stringForSearch = stringForSearch;
		this.containType = containType;
		this.stringPattern = stringPattern;
		this.containBy = containBy;
		
		common( stringPattern );
	}
	
	public ContainListStringOperation( Element element, Tag rootTag, Tag tag ) throws XMLMissingAttributePharseException{
		
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
		
		//ATTR_STRING
		if( !element.hasAttribute( ATTR_STRING ) ){
			throw new XMLMissingAttributePharseException( rootTag, tag, ATTR_STRING );			
		}
		stringForSearch = element.getAttribute( ATTR_STRING );	
		
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
	
	public String getStringToSearch() {
		return stringForSearch;
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
	public void doOperation( WebDriver driver, BaseElementDataModelAdapter baseElement, WebElement webElement, ElementProgressInterface elementProgress, String tab) throws ElementException {
		
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
		elementProgress.outputCommand( tab + CommonOperations.TAB_BY_SPACE + "if( optionText.equals( \"" + stringForSearch + "\" ) ){" );	
		elementProgress.outputCommand( tab + CommonOperations.TAB_BY_SPACE + CommonOperations.TAB_BY_SPACE + CommonOperations.TAB_BY_SPACE + "found = true;" );
		elementProgress.outputCommand( tab + CommonOperations.TAB_BY_SPACE + CommonOperations.TAB_BY_SPACE + CommonOperations.TAB_BY_SPACE + "break;" );
		elementProgress.outputCommand( tab + CommonOperations.TAB_BY_SPACE + "}" );				
		elementProgress.outputCommand( tab + "} //for( WebElement option: optionList )" );
		
		//Tartalmaznia kell a listanak a Stringben tarolt erteket DE nincs a listaban
		if( containType.equals( ContainTypeListEnum.CONTAINS ) ){			
			elementProgress.outputCommand( tab + "if( !found ){" );
			elementProgress.outputCommand( tab + CommonOperations.TAB_BY_SPACE + "System.err.println(\"Stopped because the expection is: '" + ContainTypeListEnum.CONTAINS.getTranslatedName() + "' BUT '" + stringForSearch + "' is NOT in the list\");");
			elementProgress.outputCommand( tab + CommonOperations.TAB_BY_SPACE + "System.exit(-1);");
			elementProgress.outputCommand( tab + "}" );			
		//Nem szabad tartalmaznia DE megis a listaban van 	
		}else if( containType.equals( ContainTypeListEnum.NOCONTAINS ) ){			
			elementProgress.outputCommand( tab + "if( found ){" );
			elementProgress.outputCommand( tab + CommonOperations.TAB_BY_SPACE + "System.err.println(\"Stopped because the expection is: '" + ContainTypeListEnum.NOCONTAINS.getTranslatedName() + "' BUT '" + stringForSearch + "' IS in the list\");");
			elementProgress.outputCommand( tab + CommonOperations.TAB_BY_SPACE + "System.exit(-1);");
			elementProgress.outputCommand( tab + "}" );
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
			if( optionText.equals( stringForSearch ) ){
				found = true;
				break;
			}
			
		}		
		
		//Tartalmaznia kell a listanak a Stringben tarolt erteket DE nincs a listaban
		if( containType.equals( ContainTypeListEnum.CONTAINS ) && !found ){
			
			if( baseElement instanceof NormalBaseElementDataModel ){

				throw new ElementListContainOperationException( (NormalBaseElementDataModel)baseElement, containType, stringForSearch, false, new Exception() );

			}
			
		//Nem szabad tartalmaznia DE megis a listaban van 	
		}else if( containType.equals( ContainTypeListEnum.NOCONTAINS ) && found ){
			
			if( baseElement instanceof NormalBaseElementDataModel ){
					
				throw new ElementListContainOperationException( (NormalBaseElementDataModel)baseElement, containType, stringForSearch, true, new Exception() );
			}			
		}
	}
	
	@Override
	public void setXMLAttribute(Document document, Element element) {
		
		Attr attr = document.createAttribute( ATTR_STRING );
		attr.setValue( stringForSearch );
		element.setAttributeNode(attr);	
		
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
		String stringToCompare = new String( this.stringForSearch );
		String stringPattern = new String( this.stringPattern );

		//CompareTypeListEnum compareType = this.compareType;			
		//ListCompareByListEnum compareBy = this.compareBy;	
		
		return new ContainListStringOperation(stringToCompare, containType, stringPattern, containBy);
	}
	
	@Override
	public String getOperationNameToString() {		
		return "CompareListToString()";
	}
}
