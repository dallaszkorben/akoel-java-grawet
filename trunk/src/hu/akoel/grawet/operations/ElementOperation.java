package hu.akoel.grawet.operations;

import org.openqa.selenium.WebDriver;

import hu.akoel.grawet.elements.ParameterizedElement;
import hu.akoel.grawet.exceptions.ElementException;

public interface ElementOperation {

	public void doAction( WebDriver driver, ParameterizedElement element ) throws ElementException;
}
