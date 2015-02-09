package hu.akoel.grawit.gui.editors.component.treeselector;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.DataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.ParamDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamFolderDataModel;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamNodeDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamNormalCollectorDataModel;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamRootDataModel;

import javax.swing.ImageIcon;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class ParamElementTreeSelectorComponent extends TreeSelectorComponent<ParamElementDataModel>{

	private static final long serialVersionUID = 8754108739802478258L;

	public ParamElementTreeSelectorComponent( ParamDataModelAdapter paramRootDataModel ) {
		super(CommonOperations.getTranslation("window.title.selector.paramelement"), ParamElementDataModel.class, paramRootDataModel, null, false, false);
	}

	public ParamElementTreeSelectorComponent( ParamDataModelAdapter paramRootDataModel, ParamElementDataModel selectedParamPageDataModel ) {
		super(CommonOperations.getTranslation("window.title.selector.paramelement"), ParamElementDataModel.class, paramRootDataModel, selectedParamPageDataModel, true, false);
	}
	
	@Override
	public String getSelectedDataModelToString( ParamElementDataModel selectedDataModel) {
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
		return !( path.getLastPathComponent() instanceof ParamElementDataModel );
	}
	
	@Override
	public ImageIcon getIcon(DataModelAdapter actualNode, boolean expanded ) {
	
		ImageIcon pageIcon = CommonOperations.createImageIcon("tree/param-page-icon.png");
    	ImageIcon elementIcon = CommonOperations.createImageIcon("tree/param-element-icon.png");
    	ImageIcon folderClosedIcon = CommonOperations.createImageIcon("tree/param-folder-closed-icon.png");
    	ImageIcon folderOpenIcon = CommonOperations.createImageIcon("tree/param-folder-open-icon.png");
    	ImageIcon rootIcon = CommonOperations.createImageIcon("tree/root-icon.png");
    	
    	//Iconja a NODE-nak
    	if( actualNode instanceof ParamRootDataModel){
            return rootIcon;
    	}else if( actualNode instanceof ParamNormalCollectorDataModel){
            return pageIcon;
    	}else if( actualNode instanceof ParamElementDataModel ){
            return elementIcon;
    	}else if( actualNode instanceof ParamFolderDataModel){
    		if( expanded ){
    			return folderOpenIcon;
    		}else{
    			return folderClosedIcon;
    		}
        }
		return null;
	}
	
}
