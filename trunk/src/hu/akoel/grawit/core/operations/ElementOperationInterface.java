package hu.akoel.grawit.core.operations;

import org.openqa.selenium.WebDriver;

import hu.akoel.grawit.core.treenodedatamodel.param.ParamElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.variable.VariableElementDataModel;
import hu.akoel.grawit.enums.ListSelectionType;
import hu.akoel.grawit.enums.Operation;
import hu.akoel.grawit.exceptions.ElementException;

public interface ElementOperationInterface {
	
	public void doAction( WebDriver driver, ParamElementDataModel element ) throws ElementException;
	
	/**
	 * Visszaadja a muveletet
	 * 
	 * @return
	 */
	public Operation getOperation();
	
	/**
	 * Visszaadja a muvelethez tartozo valtozot - ha van
	 * 
	 * @return
	 */
	public VariableElementDataModel getVariableElement();
	
	/**
	 * Visszaadja hogy ha az operation "List" akkor a kivalasztasa mi alapjan tortenik
	 * @return
	 */
	public ListSelectionType getListSelectionType();
	
}
