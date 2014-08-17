package hu.akoel.grawit.gui.tree.datamodel;

public abstract class BasePageDataModelInterface extends DataModelInterface{

	private static final long serialVersionUID = 1210521016363737236L;
	
	public abstract String getPathToString();
	
	public abstract void add( BasePageDataModelInterface node );

}
