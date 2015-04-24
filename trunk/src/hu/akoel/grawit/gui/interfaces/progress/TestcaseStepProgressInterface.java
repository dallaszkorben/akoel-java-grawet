package hu.akoel.grawit.gui.interfaces.progress;

import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseStepCollectorDataModel;

public interface TestcaseStepProgressInterface {

	public void stepStarted( TestcaseStepCollectorDataModel testcaseStepCollector );
	
	public void stepEnded( TestcaseStepCollectorDataModel testcaseStepCollector );
}
