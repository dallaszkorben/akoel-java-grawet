package hu.akoel.grawit.core.operations;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.operation.interfaces.CompareElementOperationInterface;
import hu.akoel.grawit.core.operation.interfaces.ElementOperationAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseElementDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.NormalBaseElementDataModel;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.enums.list.CompareTypeListEnum;
import hu.akoel.grawit.exceptions.ElementCompareOperationException;
import hu.akoel.grawit.exceptions.ElementException;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;
import hu.akoel.grawit.gui.interfaces.progress.ProgressIndicatorInterface;

public class CompareTextToStringOperation extends ElementOperationAdapter implements CompareElementOperationInterface{
	
	private static final String NAME = "COMPARETEXTTOSTRING";
	private static final String ATTR_STRING = "string";
	private static final String ATTR_COMPARE_TYPE = "type";
	private static final String ATTR_PATTERN = "pattern";
	
	private boolean isInLoop = false;
	
	private Pattern pattern;
	
	//--- Data model
	private String stringPattern;
	private String compareWithString;
	private CompareTypeListEnum compareType;
	//---
	
	public CompareTextToStringOperation( String compareWithString, CompareTypeListEnum compareType, String stringPattern ){
		this.compareWithString = compareWithString;
		this.compareType = compareType;
		this.stringPattern = stringPattern;
		
		common( stringPattern );
	}
	
	public CompareTextToStringOperation( Element element, Tag rootTag, Tag tag ) throws XMLMissingAttributePharseException{
		
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
			
			elementProgress.printSourceLn( tab + "origText = webElement.getText();" );
			if( null != pattern ){
				elementProgress.printSourceLn( tab + "pattern = Pattern.compile( \"" + pattern.pattern().replace("\\", "\\\\") + "\" );" );
				elementProgress.printSourceLn( tab + "matcher = pattern.matcher( origText );");				
				elementProgress.printSourceLn( tab + "if( matcher.find() ){" );			
				elementProgress.printSourceLn( tab + CommonOperations.TAB_BY_SPACE + CommonOperations.TAB_BY_SPACE + "origText = matcher.group();" );
				elementProgress.printSourceLn( tab + "}" );
			}
			
			if( compareType.equals( CompareTypeListEnum.EQUAL ) ){			
				elementProgress.printSourceLn( tab + "if( !origText.equals( \"" + compareWithString + "\" ) ){" );
				if( isInLoop() ){
					elementProgress.printSourceLn( tab + CommonOperations.TAB_BY_SPACE + "break; //because the element '" + baseElement.getNameAsVariable() + "' does NOT equal to '" + compareWithString + " + \"'." );
				}else{
					elementProgress.printSourceLn( tab + CommonOperations.TAB_BY_SPACE + "fail(\"Stopped because the element '" + baseElement.getNameAsVariable() + "': '\" + origText + \"' does NOT equal to '" + compareWithString + "' but it should.\");");
				}
				elementProgress.printSourceLn( tab + "}" );
			}else if( compareType.equals( CompareTypeListEnum.DIFFERENT ) ){
				elementProgress.printSourceLn( tab + "if( origText.equals( \"" + compareWithString + "\" ) ){" );
				if( isInLoop() ){
					elementProgress.printSourceLn( tab + CommonOperations.TAB_BY_SPACE + "break; //because the element '" + baseElement.getNameAsVariable() + "' equals to '" + compareWithString + " + \"'.");
				}else{
					elementProgress.printSourceLn( tab + CommonOperations.TAB_BY_SPACE + "fail(\"Stopped because the element '" + baseElement.getNameAsVariable() + "': '\" + origText + \"' equals to '" + compareWithString + "' but it should NOT.\");");
				}
				elementProgress.printSourceLn( tab + "}" );
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
			
			if( !origText.equals( compareWithString ) ){

//				if( baseElement instanceof NormalBaseElementDataModel ){
				throw new ElementCompareOperationException( (NormalBaseElementDataModel)baseElement, origText, this, new Exception() );
//					throw new ElementCompareOperationException(compareType, compareWithString, baseElement.getName(), ((NormalBaseElementDataModel)baseElement).getSelector(), origText, new Exception() );
//				//Special
//				}else{
//					throw new ElementCompareOperationException(compareType, compareWithString, baseElement.getName(), "special", origText, new Exception() );
//				}
			}
			
		}else if( compareType.equals( CompareTypeListEnum.DIFFERENT ) ){
			
			if( origText.equals( compareWithString ) ){

//				if( baseElement instanceof NormalBaseElementDataModel ){
				throw new ElementCompareOperationException( (NormalBaseElementDataModel)baseElement, origText, this, new Exception() );
//					throw new ElementCompareOperationException(compareType, compareWithString, baseElement.getName(), ((NormalBaseElementDataModel)baseElement).getSelector(), origText, new Exception() );
//				//Special
//				}else{
//					throw new ElementCompareOperationException(compareType, compareWithString, baseElement.getName(), "special", origText, new Exception() );
//				}
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
		
		attr = document.createAttribute( ATTR_PATTERN );
		attr.setValue( stringPattern );
		element.setAttributeNode(attr);	
	}

	@Override
	public String getOperationNameToString() {		
		return "CompareTextToString()";
	}

	@Override
	public String getCompareTo() {		
		return compareWithString;
	}
	
	@Override
	public Object clone() {

		String stringPattern = new String( this.stringPattern );
		String stringToCompare = new String( this.compareWithString );
		
		return new CompareTextToStringOperation(stringToCompare, compareType, stringPattern);
	}
	

}
