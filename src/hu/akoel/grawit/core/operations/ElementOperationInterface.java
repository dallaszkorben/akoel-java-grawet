package hu.akoel.grawit.core.operations;

import org.openqa.selenium.WebDriver;

import hu.akoel.grawit.core.treenodedatamodel.param.ParamElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.variable.VariableElementDataModel;
import hu.akoel.grawit.enums.Operation;
import hu.akoel.grawit.exceptions.ElementInvalidSelectorException;
import hu.akoel.grawit.exceptions.ElementNotFoundException;

public interface ElementOperationInterface {
	
	public void doAction( WebDriver driver, ParamElementDataModel element ) throws ElementNotFoundException, ElementInvalidSelectorException;
	
	/**
	 * Visszaadja a muveletet
	 * @return
	 */
	public Operation getOperation();
	
	public VariableElementDataModel getVariableElement();
}
