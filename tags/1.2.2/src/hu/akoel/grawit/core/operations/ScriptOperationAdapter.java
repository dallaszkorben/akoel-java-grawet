package hu.akoel.grawit.core.operations;

import java.util.Set;
import java.util.StringTokenizer;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseElementDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.ScriptBaseElementDataModel;
import hu.akoel.grawit.gui.interfaces.progress.ProgressIndicatorInterface;

public abstract class ScriptOperationAdapter extends ElementOperationAdapter{
		
	public void outputScripClass( WebDriver driver, BaseElementDataModelAdapter baseElement, WebElement webElement, ProgressIndicatorInterface elementProgress, String tab, Set<String> definedElementSet ){
		
		//Meg nem volt definialva a script
		if( !definedElementSet.contains( baseElement.getNameAsScript() ) ){
			
			elementProgress.printSource( tab + "//Script: " + baseElement.getName() + " (" + this.getName() + ") - " + baseElement.getNameAsScript()  );			
		
			elementProgress.printSource( tab + "ScriptClass " + baseElement.getNameAsScript() + " = new ScriptClass(){" );
			elementProgress.printSource( tab + CommonOperations.TAB_BY_SPACE + "@Override" );
			elementProgress.printSource( tab + CommonOperations.TAB_BY_SPACE + "public void runScript() throws Exception{" );		
			String script = ((ScriptBaseElementDataModel)baseElement).getScript();
			StringTokenizer tokenize = new StringTokenizer( script, "\n" );
			while( tokenize.hasMoreTokens() ){			
				elementProgress.printSource( tab + CommonOperations.TAB_BY_SPACE + CommonOperations.TAB_BY_SPACE + tokenize.nextToken().trim() );			
			}		
			elementProgress.printSource( tab + CommonOperations.TAB_BY_SPACE + "}" );
			elementProgress.printSource( tab + "};" );
			
			//Jelzem, hogy mar definialtam a script-et
			definedElementSet.add( baseElement.getNameAsScript() );
		}
	}
}
