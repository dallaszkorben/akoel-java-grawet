package hu.akoel.grawit.core.operations;

import org.openqa.selenium.WebDriver;

import hu.akoel.grawit.core.elements.ParameterizedElement;
import hu.akoel.grawit.exceptions.ElementException;

public interface ElementOperation {

	public void doAction( WebDriver driver, ParameterizedElement element ) throws ElementException;
}
