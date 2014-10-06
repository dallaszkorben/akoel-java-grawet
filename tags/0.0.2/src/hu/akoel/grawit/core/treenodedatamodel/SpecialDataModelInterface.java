package hu.akoel.grawit.core.treenodedatamodel;

import hu.akoel.grawit.enums.Tag;


public abstract class SpecialDataModelInterface extends DataModelInterface{

	private static final long serialVersionUID = 3348330370163136213L;

	public abstract void add( SpecialDataModelInterface node );

	public static Tag getRootTag(){
		return Tag.SPECIALROOT;
	}
}
