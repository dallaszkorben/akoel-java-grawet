package hu.akoel.grawit.core.treenodedatamodel.driver;

import hu.akoel.grawit.core.treenodedatamodel.DataModelAdapter;
import hu.akoel.grawit.enums.Tag;

public abstract class DriverDataModelAdapter extends DataModelAdapter{

	private static final long serialVersionUID = 1016570017093967416L;

	public abstract void add( DriverDataModelAdapter node );
	
	public static Tag getRootTag(){
		return Tag.DRIVERROOT;
	}

}
