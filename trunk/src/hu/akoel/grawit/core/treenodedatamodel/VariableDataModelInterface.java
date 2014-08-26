package hu.akoel.grawit.core.treenodedatamodel;

import hu.akoel.grawit.enums.Tag;

public abstract class VariableDataModelInterface extends DataModelInterface{

	private static final long serialVersionUID = 5051738894429183426L;

	public static Tag getRootTag(){
		return Tag.VARIABLEROOT;
	}
}
