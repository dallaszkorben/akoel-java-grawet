package hu.akoel.grawit.core.operations;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.operation.interfaces.ElementOperationAdapter;
import hu.akoel.grawit.core.operation.interfaces.CompareListOperationInterface;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseElementDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.NormalBaseElementDataModel;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.enums.list.CompareTypeListEnum;
import hu.akoel.grawit.enums.list.ListCompareByListEnum;
import hu.akoel.grawit.enums.list.ListSelectionByListEnum;
import hu.akoel.grawit.exceptions.ElementException;
import hu.akoel.grawit.exceptions.ElementListCompareSelectedOperationException;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;
import hu.akoel.grawit.gui.interfaces.progress.ProgressIndicatorInterface;

public class CompareSelectedListToStringOperation extends ElementOperationAdapter implements CompareListOperationInterface{
	
	private static final String NAME = "COMPARELISTTOSTRING";
	private static final String ATTR_STRING = "string";
	private static final String ATTR_COMPARE_TYPE = "type";
	private static final String ATTR_PATTERN = "pattern";
	private static final String ATTR_COMPARE_BY = "compareby";
	
	private boolean isInLoop = false;
	
	// Model
	private String stringPattern;
	private ListCompareByListEnum compareBy;	
	private String compareWithString;
	private CompareTypeListEnum compareType;
	//----
	
	private Pattern pattern;
	
	public CompareSelectedListToStringOperation( String compareWithString, CompareTypeListEnum compareType, String stringPattern, ListCompareByListEnum compareBy ){
		this.compareWithString = compareWithString;
		this.compareType = compareType;
		this.stringPattern = stringPattern;
		this.compareBy = compareBy;
		
		common( stringPattern );
	}
	
	public CompareSelectedListToStringOperation( Element element, Tag rootTag, Tag tag ) throws XMLMissingAttributePharseException{
		
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
		
		//ATTR_STRING
		if( !element.hasAttribute( ATTR_STRING ) ){
			throw new XMLMissingAttributePharseException( rootTag, tag, ATTR_STRING );			
		}
		compareWithString = element.getAttribute( ATTR_STRING );	
		
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
	
	@Override
	public ListCompareByListEnum getCompareBy(){
		return compareBy;
	}

	@Override
	public CompareTypeListEnum getCompareType(){
		return compareType;
	}

	public static String getStaticName(){
		return NAME;
	}	

	@Override
	public String getCompareTo() {
		return this.compareWithString;
	}
	
	@Override
	public String getName() {		
		return getStaticName();
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
	public void doOperation( WebDriver driver, BaseElementDataModelAdapter baseElement, WebElement webElement, ProgressIndicatorInterface elementProgress, String tab, Set<String> definedElementSet, boolean needToPrintSource ) throws ElementException {

		//
		// SOURCE Starts
		//		
		if( needToPrintSource ){
			elementProgress.printSource( tab + "origText = \"\";" );
			elementProgress.printSource( tab + "select = new Select(webElement);" );
		
			//VALUE
			if( compareBy.equals( ListCompareByListEnum.BYVALUE ) ){
				elementProgress.printSource( tab + "origText = select.getFirstSelectedOption().getAttribute(\"value\");" );
			
			//TEXT
			}else if( compareBy.equals( ListCompareByListEnum.BYVISIBLETEXT ) ){
				elementProgress.printSource( tab + "origText = select.getFirstSelectedOption().getText();" );
			}
			
			if( null != pattern ){
				elementProgress.printSource( tab + "pattern = Pattern.compile( \"" + pattern.pattern().replace("\\", "\\\\") + "\" );" );
				elementProgress.printSource( tab + "matcher = pattern.matcher( origText );");				
				elementProgress.printSource( tab + "if( matcher.find() ){" );	
				elementProgress.printSource( tab + CommonOperations.TAB_BY_SPACE + "origText = matcher.group();" );
				elementProgress.printSource( tab + "}" );
			}
		
			if( compareType.equals( CompareTypeListEnum.EQUAL ) ){			
				elementProgress.printSource( tab + "if( !origText.equals( \"" + compareWithString + "\" ) ){" );
				if( isInLoop() ){
					elementProgress.printSource( tab + CommonOperations.TAB_BY_SPACE + "break; //because the selected element in the Select '" + baseElement.getNameAsVariable() + "' does NOT equal to '" + compareWithString + " + \"'.");
				}else{
					elementProgress.printSource( tab + CommonOperations.TAB_BY_SPACE + "fail(\"Stopped because the selected element in the Select '" + baseElement.getNameAsVariable() + "': '\" + origText + \"' does NOT equal to '" + compareWithString + "' but it should.\");");
				}
				elementProgress.printSource( tab + "}" );
			
			}else if( compareType.equals( CompareTypeListEnum.DIFFERENT ) ){
				elementProgress.printSource( tab + "if( origText.equals( \"" + compareWithString + "\" ) ){" );
				if( isInLoop() ){
					elementProgress.printSource( tab + CommonOperations.TAB_BY_SPACE + "break; //because the selected element in the Select '" + baseElement.getNameAsVariable() + "' equals to '" + compareWithString + " + \"'.");
				}else{
					elementProgress.printSource( tab + CommonOperations.TAB_BY_SPACE + "fail(\"Stopped because the selected element in the Select '" + baseElement.getNameAsVariable() + "': '\" + origText + \"' equals to '" + compareWithString + "' but it should NOT.\");");
				}
				elementProgress.printSource( tab + "}" );
			}		
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

		//Egyezoseg az elvart
		if( compareType.equals( CompareTypeListEnum.EQUAL ) ){
			
			//Megis kulonbozik a kivalasztott az osszehasonlitandotol
			if( !origText.equals( compareWithString ) ){
				throw new ElementListCompareSelectedOperationException( (NormalBaseElementDataModel)baseElement, origText, this, new Exception() );
			}
			
		//Kulonbozoseg az elvart
		}else if( compareType.equals( CompareTypeListEnum.DIFFERENT ) ){
			
			//Megis egyezik a kivalasztott az osszehasonlitandotol
			if( origText.equals( compareWithString ) ){				
				throw new ElementListCompareSelectedOperationException( (NormalBaseElementDataModel)baseElement, origText, this, new Exception() );				 
			}			
		}
	}
	
	@Override
	public void setXMLAttribute(Document document, Element element) {
		
		Attr attr = document.createAttribute( ATTR_STRING );
		attr.setValue( compareWithString );
		element.setAttributeNode(attr);	
		
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
		String stringToCompare = new String( this.compareWithString );
		String stringPattern = new String( this.stringPattern );

		//CompareTypeListEnum compareType = this.compareType;			
		//ListCompareByListEnum compareBy = this.compareBy;	
		
		return new CompareSelectedListToStringOperation(stringToCompare, compareType, stringPattern, compareBy);
	}
	
	@Override
	public String getOperationNameToString() {		
		return "CompareListToString()";
	}

}
