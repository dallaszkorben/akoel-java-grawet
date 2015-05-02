package hu.akoel.grawit.gui.interfaces.progress;

import javax.swing.text.SimpleAttributeSet;

import hu.akoel.grawit.core.treenodedatamodel.step.StepElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseCaseDataModel;

public interface ProgressIndicatorInterface {
	
	public void elementStarted( StepElementDataModel stepElement );	
	
	public void elementEnded( StepElementDataModel stepElement );
	
	public void testcaseStarted( TestcaseCaseDataModel testcase );
	
	public void testcaseEnded( TestcaseCaseDataModel testcase );
	
	public void printOutput( String label, String message, SimpleAttributeSet attributeMessage );
	
	public void printSource( String sourceCode );
}
