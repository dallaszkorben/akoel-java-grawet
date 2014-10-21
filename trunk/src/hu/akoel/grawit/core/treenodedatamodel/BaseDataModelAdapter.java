package hu.akoel.grawit.core.treenodedatamodel;

import hu.akoel.grawit.enums.Tag;


public abstract class BaseDataModelAdapter extends DataModelAdapter{

	private static final long serialVersionUID = 1210521016363737236L;
	
	public abstract void add( BaseDataModelAdapter node );

	public static Tag getRootTag(){
		return Tag.BASEROOT;
	}
}
