package hu.akoel.grawit.core.operations;

import org.openqa.selenium.WebDriver;

import hu.akoel.grawit.core.treenodedatamodel.param.ParamElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.variable.VariableElementDataModel;
import hu.akoel.grawit.enums.Operation;
import hu.akoel.grawit.exceptions.ElementException;

public interface ElementOperationInterface {
	
	public void doAction( WebDriver driver, ParamElementDataModel element ) throws ElementException;
	
	/**
	 * Visszaadja a muveletet
	 * @return
	 */
	public Operation getOperation();
	
	public VariableElementDataModel getVariableElement();
}
