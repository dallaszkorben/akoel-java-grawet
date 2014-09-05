package hu.akoel.grawit;

import org.openqa.selenium.WebDriver;

import hu.akoel.grawit.exceptions.CompilationException;
import hu.akoel.grawit.exceptions.PageException;

public interface ExecutablePageInterface {

	public String getName();
	
	public void doAction(WebDriver driver) throws PageException, CompilationException;
	
	public PageProgressInterface getPageProgressInterface();
	
}
