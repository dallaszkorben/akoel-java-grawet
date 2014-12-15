package hu.akoel.grawit.core.treenodedatamodel.testcase;

import hu.akoel.grawit.core.treenodedatamodel.DriverDataModelInterface;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamRootDataModel;
import hu.akoel.grawit.exceptions.XMLPharseException;

import org.w3c.dom.Element;

public abstract class TestcaseControlDataModelAdapter extends TestcaseNodeDataModel{

	public TestcaseControlDataModelAdapter(Element element,	ParamRootDataModel baseRootDataModel, DriverDataModelInterface driverRootDataModel) throws XMLPharseException {
		super(element, baseRootDataModel, driverRootDataModel);	
	}

	private static final long serialVersionUID = -2299367902298659319L;


}
