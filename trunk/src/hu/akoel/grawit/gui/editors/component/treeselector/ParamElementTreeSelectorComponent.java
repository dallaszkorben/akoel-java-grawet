package hu.akoel.grawit.gui.editors.component.treeselector;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.DataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.CollectorDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.collector.CollectorParamElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.collector.CollectorNodeDataModel;
import hu.akoel.grawit.core.treenodedatamodel.collector.CollectorNormalDataModel;

import javax.swing.ImageIcon;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class ParamElementTreeSelectorComponent extends TreeSelectorComponent<CollectorParamElementDataModel>{

	private static final long serialVersionUID = 8754108739802478258L;

	public ParamElementTreeSelectorComponent( CollectorDataModelAdapter paramRootDataModel ) {
		super(CommonOperations.getTranslation("window.title.selector.paramelement"), CollectorParamElementDataModel.class, paramRootDataModel, null, false);
	}

	public ParamElementTreeSelectorComponent( CollectorDataModelAdapter paramRootDataModel, CollectorParamElementDataModel selectedParamPageDataModel ) {
		super(CommonOperations.getTranslation("window.title.selector.paramelement"), CollectorParamElementDataModel.class, paramRootDataModel, selectedParamPageDataModel, false);
	}
	
	@Override
	public String getSelectedDataModelToString( CollectorParamElementDataModel selectedDataModel) {
		StringBuffer out = new StringBuffer();
		boolean hasHyphen = false;
		for( TreeNode node: selectedDataModel.getPath() ){
			
			DataModelAdapter dataModel = (DataModelAdapter)node;

			if( !dataModel.isRoot() ){
				if( !hasHyphen ){
					hasHyphen = true;
				}else{
					out.append("-");
				}
				out.append( dataModel.getName() );
			}			
		}
		return out.toString();
	}

	@Override
	public boolean needToExpand(TreePath path, boolean state) {
		return !( path.getLastPathComponent() instanceof CollectorParamElementDataModel );
	}
	
	@Override
	public ImageIcon getIcon(DataModelAdapter actualNode, boolean expanded ) {
	
		ImageIcon pageIcon = CommonOperations.createImageIcon("tree/param-page-icon.png");
    	ImageIcon elementIcon = CommonOperations.createImageIcon("tree/param-element-icon.png");
    	ImageIcon nodeClosedIcon = CommonOperations.createImageIcon("tree/param-node-closed-icon.png");
    	ImageIcon nodeOpenIcon = CommonOperations.createImageIcon("tree/param-node-open-icon.png");
    	
    	//Iconja a NODE-nak
    	if( actualNode instanceof CollectorNormalDataModel){
            return pageIcon;
    	}else if( actualNode instanceof CollectorParamElementDataModel ){
            return elementIcon;
    	}else if( actualNode instanceof CollectorNodeDataModel){
    		if( expanded ){
    			return nodeOpenIcon;
    		}else{
    			return nodeClosedIcon;
    		}
        }
		return null;
	}
	
}
