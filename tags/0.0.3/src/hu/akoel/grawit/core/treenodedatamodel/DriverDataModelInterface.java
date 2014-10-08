package hu.akoel.grawit.core.treenodedatamodel;

import hu.akoel.grawit.enums.Tag;

public abstract class DriverDataModelInterface extends DataModelInterface{

	private static final long serialVersionUID = 1016570017093967416L;

	public abstract void add( DriverDataModelInterface node );
	
	public static Tag getRootTag(){
		return Tag.DRIVERROOT;
	}

}
