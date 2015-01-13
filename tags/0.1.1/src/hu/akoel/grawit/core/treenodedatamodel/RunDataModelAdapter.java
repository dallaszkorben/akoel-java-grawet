package hu.akoel.grawit.core.treenodedatamodel;

import hu.akoel.grawit.enums.Tag;


public abstract class RunDataModelAdapter extends DataModelAdapter{

	private static final long serialVersionUID = 962315514644510157L;

//	public abstract void add( RunDataModelAdapter node );

	public static Tag getRootTag(){
		return Tag.RUNROOT;
	}
}
