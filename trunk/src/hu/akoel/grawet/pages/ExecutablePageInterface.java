package hu.akoel.grawet.pages;

import org.openqa.selenium.WebDriver;

import hu.akoel.grawet.exceptions.CompilationException;
import hu.akoel.grawet.exceptions.PageException;

public interface ExecutablePageInterface {

	public String getName();
	
	public void doAction(WebDriver driver) throws PageException, CompilationException;
	
	public PageProgressInterface getPageProgressInterface();
	
}
