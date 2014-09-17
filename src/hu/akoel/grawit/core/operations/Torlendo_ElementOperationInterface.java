package hu.akoel.grawit.core.operations;

import org.openqa.selenium.WebDriver;

import hu.akoel.grawit.ElementProgressInterface;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamElementDataModel;
import hu.akoel.grawit.enums.list.Torlendo_Operation;
import hu.akoel.grawit.exceptions.ElementException;

public interface Torlendo_ElementOperationInterface {
	
	public void doAction( WebDriver driver, ParamElementDataModel element, ElementProgressInterface elementProgress ) throws ElementException;
	
	/**
	 * Visszaadja a muveletet
	 * 
	 * @return
	 */
	public Torlendo_Operation getOperation();
	
	/**
	 * Visszaadja a muvelethez tartozo valtozot - ha van
	 * 
	 * @return
	 */
	//public VariableElementDataModel getVariableElement();
	
	/**
	 * Visszaadja hogy ha az operation "List" akkor a kivalasztasa mi alapjan tortenik
	 * @return
	 */
	//public ListSelectionType getListSelectionType();
	
}
