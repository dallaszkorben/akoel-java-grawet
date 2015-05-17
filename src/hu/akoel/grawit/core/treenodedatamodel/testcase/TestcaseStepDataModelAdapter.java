package hu.akoel.grawit.core.treenodedatamodel.testcase;

import java.util.Set;

import org.openqa.selenium.WebDriver;

import hu.akoel.grawit.exceptions.CompilationException;
import hu.akoel.grawit.exceptions.StepException;
import hu.akoel.grawit.exceptions.StoppedByUserException;
import hu.akoel.grawit.gui.interfaces.progress.ProgressIndicatorInterface;
import hu.akoel.grawit.gui.tree.ControlPanel;

public abstract class TestcaseStepDataModelAdapter extends TestcaseDataModelAdapter{

	private static final long serialVersionUID = -3451125455593871748L;
	
	public abstract void doAction( WebDriver driver, ControlPanel controlPanel, ProgressIndicatorInterface progressIndicator, String tab, Set<String> definedElementSet ) throws StepException, CompilationException, StoppedByUserException;

}
