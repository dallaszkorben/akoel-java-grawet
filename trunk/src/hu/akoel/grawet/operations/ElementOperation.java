package hu.akoel.grawet.operations;

import hu.akoel.grawet.elements.ParameterizedElement;
import hu.akoel.grawet.exceptions.ElementException;

public interface ElementOperation {

	public void doAction( ParameterizedElement element ) throws ElementException;
}
