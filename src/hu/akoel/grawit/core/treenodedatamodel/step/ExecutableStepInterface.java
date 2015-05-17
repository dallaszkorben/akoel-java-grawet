package hu.akoel.grawit.core.treenodedatamodel.step;

import java.util.Set;

import org.openqa.selenium.WebDriver;

import hu.akoel.grawit.exceptions.CompilationException;
import hu.akoel.grawit.exceptions.StepException;
import hu.akoel.grawit.exceptions.StoppedByUserException;
import hu.akoel.grawit.gui.interfaces.progress.ProgressIndicatorInterface;
import hu.akoel.grawit.gui.tree.ControlPanel;

public interface ExecutableStepInterface {

	public String getName();
	
	public void doAction(WebDriver driver, ControlPanel controlPanel, ProgressIndicatorInterface progressIndicator, String tab, Set<String> definedElementSet ) throws StepException, CompilationException, StoppedByUserException;
	
}
