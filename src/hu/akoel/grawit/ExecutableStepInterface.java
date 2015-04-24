package hu.akoel.grawit;

import org.openqa.selenium.WebDriver;

import hu.akoel.grawit.exceptions.CompilationException;
import hu.akoel.grawit.exceptions.PageException;
import hu.akoel.grawit.exceptions.StoppedByUserException;
import hu.akoel.grawit.gui.interfaces.progress.ElementProgressInterface;
import hu.akoel.grawit.gui.interfaces.progress.TestcaseStepProgressInterface;

public interface ExecutableStepInterface {

	public String getName();
	
	public void doAction(WebDriver driver, Player player, TestcaseStepProgressInterface pageProgress, ElementProgressInterface elementProgress, String tab ) throws PageException, CompilationException, StoppedByUserException;
	
}
