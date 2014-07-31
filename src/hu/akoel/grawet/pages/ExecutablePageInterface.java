package hu.akoel.grawet.pages;

import hu.akoel.grawet.exceptions.CompilationException;
import hu.akoel.grawet.exceptions.PageException;

public interface ExecutablePageInterface {

	public String getName();
	
	public void doAction() throws PageException, CompilationException;
	
	public PageProgressInterface getPageProgressInterface();
	
}
