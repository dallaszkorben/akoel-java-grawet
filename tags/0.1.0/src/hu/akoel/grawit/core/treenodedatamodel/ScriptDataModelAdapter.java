package hu.akoel.grawit.core.treenodedatamodel;

import hu.akoel.grawit.enums.Tag;


public abstract class ScriptDataModelAdapter extends DataModelAdapter{

	private static final long serialVersionUID = -7087298075678402395L;

	public abstract void add( ScriptDataModelAdapter node );

	public static Tag getRootTag(){
		return Tag.SCRIPTROOT;
	}
}
