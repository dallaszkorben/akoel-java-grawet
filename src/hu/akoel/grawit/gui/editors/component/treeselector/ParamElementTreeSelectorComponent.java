package hu.akoel.grawit.gui.editors.component.treeselector;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.DataModelInterface;
import hu.akoel.grawit.core.treenodedatamodel.ParamDataModelInterface;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamNodeDataModel;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamPageDataModel;

import javax.swing.ImageIcon;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class ParamElementTreeSelectorComponent extends TreeSelectorComponent<ParamElementDataModel>{

	private static final long serialVersionUID = 8754108739802478258L;

	public ParamElementTreeSelectorComponent( ParamDataModelInterface paramRootDataModel ) {
		super(ParamElementDataModel.class, paramRootDataModel, null, false);
	}

	public ParamElementTreeSelectorComponent( ParamDataModelInterface paramRootDataModel, ParamElementDataModel selectedParamPageDataModel ) {
		super(ParamElementDataModel.class, paramRootDataModel, selectedParamPageDataModel, false);
	}
	
	@Override
	public String getSelectedDataModelToString( ParamElementDataModel selectedDataModel) {
		StringBuffer out = new StringBuffer();
		boolean hasHyphen = false;
		for( TreeNode node: selectedDataModel.getPath() ){
			
			DataModelInterface dataModel = (DataModelInterface)node;

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
		return !( path.getLastPathComponent() instanceof ParamElementDataModel );
	}
	
	@Override
	public ImageIcon getIcon(DataModelInterface actualNode, boolean expanded ) {
	
		ImageIcon pageIcon = CommonOperations.createImageIcon("tree/param-page-icon.png");
    	ImageIcon elementIcon = CommonOperations.createImageIcon("tree/param-element-icon.png");
    	ImageIcon nodeClosedIcon = CommonOperations.createImageIcon("tree/param-node-closed-icon.png");
    	ImageIcon nodeOpenIcon = CommonOperations.createImageIcon("tree/param-node-open-icon.png");
    	
    	//Iconja a NODE-nak
    	if( actualNode instanceof ParamPageDataModel){
            return pageIcon;
    	}else if( actualNode instanceof ParamElementDataModel ){
            return elementIcon;
    	}else if( actualNode instanceof ParamNodeDataModel){
    		if( expanded ){
    			return nodeOpenIcon;
    		}else{
    			return nodeClosedIcon;
    		}
        }
		return null;
	}


	
}
