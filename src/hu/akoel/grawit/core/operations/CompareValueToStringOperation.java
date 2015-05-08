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
import hu.akoel.grawit.core.treenodedatamodel.base.BaseElementDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.NormalBaseElementDataModel;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.enums.list.CompareTypeListEnum;
import hu.akoel.grawit.enums.list.ElementTypeListEnum;
import hu.akoel.grawit.exceptions.ElementCompareOperationException;
import hu.akoel.grawit.exceptions.ElementException;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;
import hu.akoel.grawit.gui.interfaces.progress.ProgressIndicatorInterface;

public class CompareValueToStringOperation extends ElementOperationAdapter implements CompareOperationInterface{
	
	private static final String NAME = "COMPAREVALUETOSTRING";
	private static final String ATTR_STRING = "string";
	private static final String ATTR_COMPARE_TYPE = "type";
	private static final String ATTR_PATTERN = "pattern";
	
	private boolean isInLoop = false;
	
	private Pattern pattern;
	
	//--- Data model
	private String stringPattern;
	private String stringToCompare;
	private CompareTypeListEnum compareType;
	//---
	
	public CompareValueToStringOperation( String stringToCompare, CompareTypeListEnum compareType, String stringPattern ){
		this.stringToCompare = stringToCompare;
		this.compareType = compareType;
		this.stringPattern = stringPattern;
		
		common( stringPattern );
	}
	
	public CompareValueToStringOperation( Element element, Tag rootTag, Tag tag ) throws XMLMissingAttributePharseException{
		
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
		stringToCompare = element.getAttribute( ATTR_STRING );	
		
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
	
	public String getStringToShow() {
		return stringToCompare;
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
				elementProgress.printSource( tab + "if( !origText.equals( \"" + stringToCompare + "\" ) ){" );
				if( isInLoop() ){
					elementProgress.printSource( tab + CommonOperations.TAB_BY_SPACE + "break; //because the element '" + baseElement.getNameAsVariable() + "' does NOT equal to '" + stringToCompare + "'.");
				}else{
					elementProgress.printSource( tab + CommonOperations.TAB_BY_SPACE + "fail(\"Stopped because the element '" + baseElement.getNameAsVariable() + "': '\" + origText + \"' does NOT equal to '" + stringToCompare + "' but it should.\");");
				}
				elementProgress.printSource( tab + "}" );
			}else if( compareType.equals( CompareTypeListEnum.DIFFERENT ) ){
				elementProgress.printSource( tab + "if( origText.equals( \"" + stringToCompare + "\" ) ){" );
				if( isInLoop() ){
					elementProgress.printSource( tab + CommonOperations.TAB_BY_SPACE + "break; //because the element '" + baseElement.getNameAsVariable() + "' equals to '" + stringToCompare + "'.");
				}else{
					elementProgress.printSource( tab + CommonOperations.TAB_BY_SPACE + "fail(\"Stopped because the element '" + baseElement.getNameAsVariable() + "': '\" + origText + \"' equals to '" + stringToCompare + "' but it should NOT.\");");
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
			
			if( !origText.equals( stringToCompare ) ){

				if( baseElement instanceof NormalBaseElementDataModel ){
					throw new ElementCompareOperationException(compareType, stringToCompare, baseElement.getName(), ((NormalBaseElementDataModel)baseElement).getSelector(), origText, new Exception() );
				//Special
				}else{
					throw new ElementCompareOperationException(compareType, stringToCompare, baseElement.getName(), "special", origText, new Exception() );
				}
			}
			
		}else if( compareType.equals( CompareTypeListEnum.DIFFERENT ) ){
			
			if( origText.equals( stringToCompare ) ){
				
				if( baseElement instanceof NormalBaseElementDataModel ){
					throw new ElementCompareOperationException(compareType, stringToCompare, baseElement.getName(), ((NormalBaseElementDataModel)baseElement).getSelector(), origText, new Exception() );
				//Special
				}else{
					throw new ElementCompareOperationException(compareType, stringToCompare, baseElement.getName(), "special", origText, new Exception() );
				}
			}			
		}
	}
	
	@Override
	public void setXMLAttribute(Document document, Element element) {
		
		Attr attr = document.createAttribute( ATTR_STRING );
		attr.setValue( stringToCompare );
		element.setAttributeNode(attr);	
		
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
		String stringToCompare = new String( this.stringToCompare );
		//ompareTypeListEnum compareType = this.compareType;
		
		return new CompareValueToStringOperation(stringToCompare, compareType, stringPattern);
	}
	
	@Override
	public String getOperationNameToString() {		
		return "CompareValueToString()";
	}
	
}
