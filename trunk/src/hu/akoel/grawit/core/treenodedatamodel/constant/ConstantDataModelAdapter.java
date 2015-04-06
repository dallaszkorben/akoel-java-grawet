package hu.akoel.grawit.core.treenodedatamodel.constant;

import hu.akoel.grawit.core.treenodedatamodel.DataModelAdapter;
import hu.akoel.grawit.enums.Tag;

public abstract class ConstantDataModelAdapter extends DataModelAdapter{

	private static final long serialVersionUID = 5051738894429183426L;

	public abstract void add( ConstantDataModelAdapter node );
	
	public static Tag getRootTag(){
		return Tag.CONSTANTROOT;
	}
}
