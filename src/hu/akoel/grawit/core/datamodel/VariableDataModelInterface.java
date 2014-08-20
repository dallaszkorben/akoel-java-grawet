package hu.akoel.grawit.core.datamodel;

import hu.akoel.grawit.enums.Tag;


public abstract class VariableDataModelInterface extends DataModelInterface{

	private static final long serialVersionUID = -7007642084409026708L;

	public abstract void add( VariableDataModelInterface node );
	
	public static Tag getRootTag(){
		return Tag.VARIABLEROOT;
	}
}

