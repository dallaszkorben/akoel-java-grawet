package hu.akoel.grawit.core.treenodedatamodel.testcase;

import org.openqa.selenium.WebDriver;

import hu.akoel.grawit.Player;
import hu.akoel.grawit.exceptions.CompilationException;
import hu.akoel.grawit.exceptions.PageException;
import hu.akoel.grawit.exceptions.StoppedByUserException;
import hu.akoel.grawit.gui.interfaces.progress.ElementProgressInterface;
import hu.akoel.grawit.gui.interfaces.progress.StepProgressInterface;

public abstract class TestcaseStepDataModelAdapter extends TestcaseDataModelAdapter{

	private static final long serialVersionUID = -3451125455593871748L;
	
	public abstract void doAction( WebDriver driver, Player player, StepProgressInterface pageProgress, ElementProgressInterface elementProgress, String tab ) throws PageException, CompilationException, StoppedByUserException;

}
