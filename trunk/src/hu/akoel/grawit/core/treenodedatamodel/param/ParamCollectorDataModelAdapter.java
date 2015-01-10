package hu.akoel.grawit.core.treenodedatamodel.param;

import org.w3c.dom.Element;

import hu.akoel.grawit.ExecutablePageInterface;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.variable.VariableRootDataModel;
import hu.akoel.grawit.exceptions.XMLPharseException;

public abstract class ParamCollectorDataModelAdapter extends ParamNodeDataModelAdapter implements ExecutablePageInterface{ //extends ParamDataModelAdapter implements ExecutablePageInterface{

	public ParamCollectorDataModelAdapter(Element element, BaseRootDataModel baseRootDataModel, VariableRootDataModel variableRootDataModel) throws XMLPharseException {
		super(element, baseRootDataModel, variableRootDataModel);

	}

	public ParamCollectorDataModelAdapter(String name, String details) {
		super(name, details);
	}

	private static final long serialVersionUID = 6053404210638476702L;

}
