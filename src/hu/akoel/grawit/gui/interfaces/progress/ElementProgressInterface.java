package hu.akoel.grawit.gui.interfaces.progress;

import javax.swing.text.SimpleAttributeSet;

import hu.akoel.grawit.core.treenodedatamodel.step.StepElementDataModel;

public interface ElementProgressInterface {
	
	//public void elementStarted( BaseElementDataModelAdapter baseElement, ElementOperationAdapter operation );	
	//public void elementEnded( BaseElementDataModelAdapter baseElement, ElementOperationAdapter operation );
	
	public void elementStarted( StepElementDataModel stepElement );	
	
	public void elementEnded( StepElementDataModel stepElement );
	
	public void printOutput( String label, String message, SimpleAttributeSet attributeMessage );
	
	public void printSource( String sourceCode );
}
