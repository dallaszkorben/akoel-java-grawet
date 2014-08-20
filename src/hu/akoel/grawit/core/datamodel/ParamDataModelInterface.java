package hu.akoel.grawit.core.datamodel;

import hu.akoel.grawit.enums.ModelType;


public abstract class ParamDataModelInterface extends DataModelInterface{

	private static final long serialVersionUID = 3339665940651468923L;
	
	public abstract void add( ParamDataModelInterface node );
	
	public static ModelType getModelType(){
		return ModelType.PARAM;
	}
}

