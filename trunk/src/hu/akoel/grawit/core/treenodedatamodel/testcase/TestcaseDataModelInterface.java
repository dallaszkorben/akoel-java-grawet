package hu.akoel.grawit.core.treenodedatamodel.testcase;

import hu.akoel.grawit.core.treenodedatamodel.DataModelInterface;
import hu.akoel.grawit.enums.Tag;


public abstract class TestcaseDataModelInterface extends DataModelInterface{

	private static final long serialVersionUID = 962315514644510157L;

	public abstract void add( TestcaseDataModelInterface node );

	public static Tag getRootTag(){
		return Tag.TESTCASEROOT;
	}
}
