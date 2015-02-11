package hu.akoel.grawit.gui.editors.component.treeselector;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.DataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.ParamDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.step.StepElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.step.StepFolderDataModel;
import hu.akoel.grawit.core.treenodedatamodel.step.StepNodeDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.step.StepNormalCollectorDataModel;
import hu.akoel.grawit.core.treenodedatamodel.step.StepRootDataModel;

import javax.swing.ImageIcon;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class ParamElementTreeSelectorComponent extends TreeSelectorComponent<StepElementDataModel>{

	private static final long serialVersionUID = 8754108739802478258L;

	public ParamElementTreeSelectorComponent( ParamDataModelAdapter paramRootDataModel ) {
		super(CommonOperations.getTranslation("window.title.selector.paramelement"), StepElementDataModel.class, paramRootDataModel, null, false, false);
	}

	public ParamElementTreeSelectorComponent( ParamDataModelAdapter paramRootDataModel, StepElementDataModel selectedParamPageDataModel ) {
		super(CommonOperations.getTranslation("window.title.selector.paramelement"), StepElementDataModel.class, paramRootDataModel, selectedParamPageDataModel, true, false);
	}
	
	@Override
	public String getSelectedDataModelToString( StepElementDataModel selectedDataModel) {
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
		return !( path.getLastPathComponent() instanceof StepElementDataModel );
	}
	
	@Override
	public ImageIcon getIcon(DataModelAdapter actualNode, boolean expanded ) {
	
		ImageIcon pageIcon = CommonOperations.createImageIcon("tree/param-page-icon.png");
    	ImageIcon elementIcon = CommonOperations.createImageIcon("tree/param-element-icon.png");
    	ImageIcon folderClosedIcon = CommonOperations.createImageIcon("tree/param-folder-closed-icon.png");
    	ImageIcon folderOpenIcon = CommonOperations.createImageIcon("tree/param-folder-open-icon.png");
    	ImageIcon rootIcon = CommonOperations.createImageIcon("tree/root-icon.png");
    	
    	//Iconja a NODE-nak
    	if( actualNode instanceof StepRootDataModel){
            return rootIcon;
    	}else if( actualNode instanceof StepNormalCollectorDataModel){
            return pageIcon;
    	}else if( actualNode instanceof StepElementDataModel ){
            return elementIcon;
    	}else if( actualNode instanceof StepFolderDataModel){
    		if( expanded ){
    			return folderOpenIcon;
    		}else{
    			return folderClosedIcon;
    		}
        }
		return null;
	}
	
}
