package hu.akoel.grawit.core.treenodedatamodel;

import hu.akoel.grawit.enums.Tag;


public abstract class TestcaseDataModelAdapter extends DataModelAdapter{

	private static final long serialVersionUID = 962315514644510157L;

	public abstract void add( TestcaseDataModelAdapter node );

	public static Tag getRootTag(){
		return Tag.TESTCASEROOT;
	}
}
