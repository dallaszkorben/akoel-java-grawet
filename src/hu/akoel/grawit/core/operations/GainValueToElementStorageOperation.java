package hu.akoel.grawit.core.operations;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseElementDataModelAdapter;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.enums.list.ElementTypeListEnum;
import hu.akoel.grawit.exceptions.ElementException;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;
import hu.akoel.grawit.gui.interfaces.progress.ElementProgressInterface;

public class GainValueToElementStorageOperation extends ElementOperationAdapter{
	
	private static final String NAME = "GAINVALUETOELEMENT";
	private static final String ATTR_PATTERN = "pattern";
	
	private Pattern pattern;
	private Matcher matcher;
	
	//--- Data model
	private String stringPattern;
	//---
	
	public GainValueToElementStorageOperation( String stringPattern ){
		this.stringPattern = stringPattern;
		
		common( stringPattern );
	}
	
	public GainValueToElementStorageOperation( Element element, Tag rootTag, Tag tag ) throws XMLMissingAttributePharseException{
		
		if( !element.hasAttribute( ATTR_PATTERN ) ){
			throw new XMLMissingAttributePharseException( rootTag, tag, ATTR_PATTERN );			
		}
		stringPattern = element.getAttribute( ATTR_PATTERN );	
		
		common( stringPattern );
		
	}
	
	private void common( String stringPattern ){
		
		if( stringPattern.trim().length() == 0 ){
			pattern = null;
		}else{		
			pattern = Pattern.compile( stringPattern );
		}
		
	}
	
	public static String getStaticName(){
		return NAME;
	}
	
	@Override
	public String getName() {
		return getStaticName();
	}
	
	public String getStringPattern(){
		return stringPattern;
	}

	@Override
	public ArrayList<String> doOperation(WebDriver driver, BaseElementDataModelAdapter baseElement, WebElement webElement, ElementProgressInterface elementProgress) throws ElementException {
		ArrayList<String> returnArray = new ArrayList<>();
		
		String origText = "";

		//CHECKBOX/RADIOBUTTON
		if( baseElement.getElementType().equals(ElementTypeListEnum.CHECKBOX) || baseElement.getElementType().equals(ElementTypeListEnum.RADIOBUTTON) ){

			if( webElement.isSelected() ){
				origText = "on";
			}else{
				origText = "off";
			}
			
			returnArray.add( "if( webElement.isSelected() ){" );
			returnArray.add( CommonOperations.TAB_BY_SPACE + "origText = \"on\";" );
			returnArray.add( "}else{" );
			returnArray.add( CommonOperations.TAB_BY_SPACE + "origText = \"off\";" );
			returnArray.add( "}" );
			
		//Ha FIELD
		}else{		

			origText = webElement.getAttribute("value");
			
			returnArray.add( "origText = webElement.getAttribute(\"value\");" );
		}
	
		//Elmenti az elem tartalmat a valtozoba		
		if( null == pattern ){
			baseElement.setStoredValue( origText );
			
			returnArray.add( "String " + CommonOperations.STORAGE_NAME_PREFIX + String.valueOf( baseElement.hashCode() ) + " = origText;" );
		}else{
			matcher = pattern.matcher( origText );
			if( matcher.find() ){
				String resultText = matcher.group();
				baseElement.setStoredValue( resultText );
				
				returnArray.add( "pattern = Pattern.compile( " + pattern.pattern() + " );" );
				returnArray.add( "matcher = pattern.matcher( origText );");
				returnArray.add( "String " + CommonOperations.STORAGE_NAME_PREFIX + String.valueOf( baseElement.hashCode() ) + " = matcher.group();" );
				
			}			
		}	
		
		return returnArray;
	}
	
	@Override
	public void setXMLAttribute(Document document, Element element) {
		Attr attr = document.createAttribute( ATTR_PATTERN );
		attr.setValue( stringPattern );
		element.setAttributeNode(attr);		
	}

	@Override
	public Object clone() {
		
		String stringPattern = new String( this.stringPattern );
		
		return new GainValueToElementStorageOperation(stringPattern);
	}

	@Override
	public String getOperationNameToString() {		
		return "GainValueToElementStorage()";
	}

	
}

