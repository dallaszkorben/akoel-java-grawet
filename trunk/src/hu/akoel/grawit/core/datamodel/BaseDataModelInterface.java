package hu.akoel.grawit.core.datamodel;


public abstract class BaseDataModelInterface extends DataModelInterface{

	private static final long serialVersionUID = 1210521016363737236L;
	
	public abstract String getPathToString();
	
	public abstract void add( BaseDataModelInterface node );

}
