package hu.akoel.grawit.core.treenodedatamodel.trace;

import hu.akoel.grawit.core.treenodedatamodel.DataModelAdapter;

import javax.swing.tree.DefaultMutableTreeNode;

public class TraceDataModel extends DefaultMutableTreeNode{
	private static final long serialVersionUID = -2179127840127180131L;
	private DataModelAdapter dataModel;
	
	public TraceDataModel( DataModelAdapter dataModel ){
		this.dataModel = dataModel;
	}
	
	public DataModelAdapter getDataModel(){
		return dataModel;
	}
}

