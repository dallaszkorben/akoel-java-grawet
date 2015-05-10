package hu.akoel.grawit.core.operations;

import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.operation.interfaces.ScriptOperationAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseElementDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.ScriptBaseElementDataModel;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.exceptions.CompilationException;
import hu.akoel.grawit.exceptions.ElementException;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;
import hu.akoel.grawit.gui.interfaces.progress.ProgressIndicatorInterface;

public class ScriptElementExecuteOperation extends ScriptOperationAdapter{
	
	private static final String NAME = "EXECUTE_SCRIPT";
	
	//--- Data model
	//---
	
	public ScriptElementExecuteOperation(){
	}
	
	public ScriptElementExecuteOperation( Element element, Tag rootTag, Tag tag ) throws XMLMissingAttributePharseException{
	}

	public static String getStaticName(){
		return NAME;
	}
	
	@Override
	public String getName() {		
		return getStaticName();
	}
	

	
	@Override
	public void doOperation(WebDriver driver, BaseElementDataModelAdapter baseElement, WebElement webElement, ProgressIndicatorInterface elementProgress, String tab, Set<String> definedElementSet, boolean needToPrintSource ) throws ElementException, CompilationException{
		
		//HA SPECIALBASEELEMENT - annak kell lennie
		if( baseElement instanceof ScriptBaseElementDataModel ){

			if( needToPrintSource ){
				outputScripClass(driver, baseElement, webElement, elementProgress, tab, definedElementSet );
				elementProgress.printSource( tab + "try{" );
				elementProgress.printSource( tab + CommonOperations.TAB_BY_SPACE + baseElement.getNameAsScript() + ".runScript();" );
				elementProgress.printSource( tab + "}catch( Exception e ){" );
				elementProgress.printSource( tab + CommonOperations.TAB_BY_SPACE + "e.printStackTrace();" );
				elementProgress.printSource( tab + CommonOperations.TAB_BY_SPACE + "fail( e.getMessage() );" );
				elementProgress.printSource( tab + "}" );
				elementProgress.printSource( "" );
			}
			((ScriptBaseElementDataModel)baseElement).doAction(driver);
			
		}
	
	}
	
	@Override
	public void setXMLAttribute(Document document, Element element) {
		
/*		Attr attr = document.createAttribute( ATTR_STRING );
		attr.setValue( stringToParameter );
		element.setAttributeNode(attr);	
*/		
	}

	@Override
	public Object clone() {

		return new ScriptElementExecuteOperation();
		
	}
		
	@Override
	public String getOperationNameToString() {		
		return "ExecuteScriptElement";
	}
	
}
