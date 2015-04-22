package hu.akoel.grawit.core.operations;

import java.util.StringTokenizer;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseElementDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.ScriptBaseElementDataModel;
import hu.akoel.grawit.gui.interfaces.progress.ElementProgressInterface;

public abstract class ScriptOperationAdapter extends ElementOperationAdapter{
		
	public void outputScripClass( WebDriver driver, BaseElementDataModelAdapter baseElement, WebElement webElement, ElementProgressInterface elementProgress, String tab ){
		
		elementProgress.printCommand( tab + "//Script: " + baseElement.getName() + " (" + this.getName() + ") - " + CommonOperations.SCRIPT_NAME_PREFIX + baseElement.hashCode()  );
		elementProgress.printCommand( tab + "ScriptClass " + CommonOperations.SCRIPT_NAME_PREFIX + String.valueOf( baseElement.hashCode() ) + " = new ScriptClass(){" );
		elementProgress.printCommand( tab + CommonOperations.TAB_BY_SPACE + "@Override" );
		elementProgress.printCommand( tab + CommonOperations.TAB_BY_SPACE + "public void runScript() throws Exception{" );		
		String script = ((ScriptBaseElementDataModel)baseElement).getScript();
		StringTokenizer tokenize = new StringTokenizer( script, "\n" );
		while( tokenize.hasMoreTokens() ){			
			elementProgress.printCommand( tab + CommonOperations.TAB_BY_SPACE + CommonOperations.TAB_BY_SPACE + tokenize.nextToken().trim() );			
		}		
		elementProgress.printCommand( tab + CommonOperations.TAB_BY_SPACE + "}" );
		elementProgress.printCommand( tab + "};" );		
	}

}
