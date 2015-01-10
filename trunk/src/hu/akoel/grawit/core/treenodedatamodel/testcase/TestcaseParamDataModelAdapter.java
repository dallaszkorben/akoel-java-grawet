package hu.akoel.grawit.core.treenodedatamodel.testcase;

import org.openqa.selenium.WebDriver;
import hu.akoel.grawit.Player;
import hu.akoel.grawit.core.treenodedatamodel.TestcaseDataModelAdapter;
import hu.akoel.grawit.exceptions.CompilationException;
import hu.akoel.grawit.exceptions.PageException;
import hu.akoel.grawit.exceptions.StoppedByUserException;
import hu.akoel.grawit.gui.interfaces.progress.ElementProgressInterface;
import hu.akoel.grawit.gui.interfaces.progress.PageProgressInterface;

public abstract class TestcaseParamDataModelAdapter extends TestcaseDataModelAdapter{
//public abstract class TestcaseParamCollectorDataModelAdapter extends TestcaseNodeDataModel{

/*	public TestcaseParamCollectorDataModelAdapter(String name, String details) {
		super(name, details);
	}

	public TestcaseParamCollectorDataModelAdapter(Element element, VariableRootDataModel variableRootDataModel, BaseRootDataModel baseRootDataModel, ParamRootDataModel paramRootDataModel, DriverDataModelAdapter driverRootDataModel) throws XMLPharseException {
		super(element, variableRootDataModel, baseRootDataModel, paramRootDataModel, driverRootDataModel);
	}
*/
	private static final long serialVersionUID = -3451125455593871748L;
	
	public abstract void doAction( WebDriver driver, Player player, PageProgressInterface pageProgress, ElementProgressInterface elementProgress ) throws PageException, CompilationException, StoppedByUserException;

}
