package hu.akoel.grawit.core.operations;

import java.util.StringTokenizer;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseElementDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.ScriptBaseElementDataModel;
import hu.akoel.grawit.gui.interfaces.progress.ElementProgressInterface;

public abstract class ScriptOperationAdapter extends ElementOperationAdapter{
		
	public void outputScripClasst( WebDriver driver, BaseElementDataModelAdapter baseElement, WebElement webElement, ElementProgressInterface elementProgress, String tab ){
		elementProgress.outputCommand( tab + "//Script: " + baseElement.getName() + " (" + this.getName() + ") - " + CommonOperations.SCRIPT_NAME_PREFIX + baseElement.hashCode()  );
		elementProgress.outputCommand( tab + "ScriptClass " + CommonOperations.SCRIPT_NAME_PREFIX + String.valueOf( baseElement.hashCode() ) + " = new ScriptClass(){" );
		elementProgress.outputCommand( tab + CommonOperations.TAB_BY_SPACE + "@Override" );
		elementProgress.outputCommand( tab + CommonOperations.TAB_BY_SPACE + "public void runScript() throws Exception{" );
		
		String script = ((ScriptBaseElementDataModel)baseElement).getScript();
		StringTokenizer tokenize = new StringTokenizer( script, "\n" );
		while( tokenize.hasMoreTokens() ){
			
			elementProgress.outputCommand( tab + CommonOperations.TAB_BY_SPACE + CommonOperations.TAB_BY_SPACE + tokenize.nextToken().trim() );
			
		}
		
		elementProgress.outputCommand( tab + CommonOperations.TAB_BY_SPACE + "}" );
		elementProgress.outputCommand( tab + "};" );		
	}

}
