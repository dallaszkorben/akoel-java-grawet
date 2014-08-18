package hu.akoel.grawit.core.datamodel;

import hu.akoel.grawit.ModelType;


public abstract class BaseDataModelInterface extends DataModelInterface{

	private static final long serialVersionUID = 1210521016363737236L;
	
	public abstract void add( BaseDataModelInterface node );

	public static ModelType getModelType(){
		return ModelType.BASE;
	}
}
