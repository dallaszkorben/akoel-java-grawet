package hu.akoel.grawit.core.treenodedatamodel;

import hu.akoel.grawit.enums.Tag;


public abstract class StepDataModelAdapter extends DataModelAdapter{

	private static final long serialVersionUID = 3339665940651468923L;
	
	public abstract void add( StepDataModelAdapter node );
	
	public static Tag getRootTag(){
		return Tag.STEPROOT;
	}
}

