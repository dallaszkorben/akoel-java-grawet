package hu.akoel.grawit.core.treenodedatamodel;

import hu.akoel.grawit.enums.Tag;


public abstract class VDMInterface extends DataModelInterface{

	private static final long serialVersionUID = -7007642084409026708L;

	public abstract void add( VDMInterface node );
	
	public static Tag getRootTag(){
		return Tag.VARIABLEROOT;
	}
}

