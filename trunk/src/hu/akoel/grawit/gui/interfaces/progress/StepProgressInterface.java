package hu.akoel.grawit.gui.interfaces.progress;

import hu.akoel.grawit.core.treenodedatamodel.step.StepCollectorDataModelAdapter;

public interface StepProgressInterface {

	public void stepStarted( StepCollectorDataModelAdapter stepCollector  );
	
	public void stepEnded( StepCollectorDataModelAdapter stepCollector );
}
