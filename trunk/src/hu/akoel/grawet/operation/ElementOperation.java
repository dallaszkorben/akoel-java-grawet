package hu.akoel.grawet.operation;

import hu.akoel.grawet.element.ParameterizedElement;
import hu.akoel.grawet.exceptions.ElementException;

public interface ElementOperation {

	public void doAction( ParameterizedElement element ) throws ElementException;
}
