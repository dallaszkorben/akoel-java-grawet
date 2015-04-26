package hu.akoel.grawit;

import org.openqa.selenium.WebDriver;

import hu.akoel.grawit.exceptions.CompilationException;
import hu.akoel.grawit.exceptions.PageException;
import hu.akoel.grawit.exceptions.StoppedByUserException;
import hu.akoel.grawit.gui.interfaces.progress.ProgressIndicatorInterface;

public interface ExecutableStepInterface {

	public String getName();
	
	public void doAction(WebDriver driver, Player player, ProgressIndicatorInterface progressIndicator, String tab ) throws PageException, CompilationException, StoppedByUserException;
	
}
