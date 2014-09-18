package hu.akoel.grawit.core.operations;

import org.openqa.selenium.WebDriver;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import hu.akoel.grawit.ElementProgressInterface;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamElementDataModel;
import hu.akoel.grawit.exceptions.ElementException;

public interface ElementOperationInterface {
	
	public void doAction( WebDriver driver, ParamElementDataModel element, ElementProgressInterface elementProgress ) throws ElementException;
		
	public String getName();
		
	public void setXMLAttribute( Document document, Element element );
}
