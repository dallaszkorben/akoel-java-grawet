package hu.akoel.grawit.core.treenodedatamodel;

import hu.akoel.grawit.enums.Tag;


public abstract class ParamDataModelAdapter extends DataModelAdapter{

	private static final long serialVersionUID = 3339665940651468923L;
	
	public abstract void add( ParamDataModelAdapter node );
	
	public static Tag getRootTag(){
		return Tag.PARAMROOT;
	}
}

