package hu.akoel.grawit.gui.interfaces.progress;

import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseCaseDataModel;

public interface TestcaseProgressInterface {

	public void testcaseStarted( TestcaseCaseDataModel testcase  );
	
	public void testcaseEnded( TestcaseCaseDataModel testcase );
	
}
