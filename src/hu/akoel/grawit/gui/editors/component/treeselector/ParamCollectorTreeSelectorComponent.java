package hu.akoel.grawit.gui.editors.component.treeselector;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.BaseDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.BaseElementDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.DataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.ParamDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.step.StepCollectorDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.step.StepElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.step.StepFolderDataModel;
import hu.akoel.grawit.core.treenodedatamodel.step.StepLoopCollectorDataModel;
import hu.akoel.grawit.core.treenodedatamodel.step.StepNormalCollectorDataModel;
import hu.akoel.grawit.core.treenodedatamodel.step.StepRootDataModel;

import javax.swing.ImageIcon;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class ParamCollectorTreeSelectorComponent extends TreeSelectorComponent<StepCollectorDataModelAdapter>{

	private static final long serialVersionUID = 1064181673121972602L;

	//TODO torlendo
	public ParamCollectorTreeSelectorComponent( ParamDataModelAdapter rootDataModel ) {
		super(CommonOperations.getTranslation("window.title.selector.parampage"), StepCollectorDataModelAdapter.class, rootDataModel, null, false, false);
	}

	//TODO torlendo
	public ParamCollectorTreeSelectorComponent( ParamDataModelAdapter rootDataModel, StepCollectorDataModelAdapter selectedParamPageDataModel ) {
		super(CommonOperations.getTranslation("window.title.selector.parampage"), StepCollectorDataModelAdapter.class, rootDataModel, selectedParamPageDataModel, true, false);
	}
	
	public ParamCollectorTreeSelectorComponent( ParamDataModelAdapter rootDataModel, StepCollectorDataModelAdapter selectedParamPageDataModel, boolean setSelectedElementToFieldFirst ) {
		super(CommonOperations.getTranslation("window.title.selector.baseelement"), StepCollectorDataModelAdapter.class, rootDataModel, selectedParamPageDataModel, setSelectedElementToFieldFirst, false);
	}
	
	@Override
	public String getSelectedDataModelToString( StepCollectorDataModelAdapter selectedDataModel) {
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
		return !( path.getLastPathComponent() instanceof StepCollectorDataModelAdapter );
	}
	
	@Override
	public ImageIcon getIcon(DataModelAdapter actualNode, boolean expanded ) {
	
		ImageIcon pageIcon = CommonOperations.createImageIcon("tree/param-page-icon.png");
    	ImageIcon elementIcon = CommonOperations.createImageIcon("tree/param-element-icon.png");
    	ImageIcon folderClosedIcon = CommonOperations.createImageIcon("tree/param-folder-closed-icon.png");
    	ImageIcon folderOpenIcon = CommonOperations.createImageIcon("tree/param-folder-open-icon.png");
    	ImageIcon loopOpenIcon = CommonOperations.createImageIcon("tree/param-loop-icon.png");
    	ImageIcon loopClosedIcon = CommonOperations.createImageIcon("tree/param-loop-icon.png");
    	ImageIcon rootIcon = CommonOperations.createImageIcon("tree/root-icon.png");
    	
    	//Iconja a NODE-nak
    	if( actualNode instanceof StepRootDataModel){
            return rootIcon;
    	}else if( actualNode instanceof StepNormalCollectorDataModel){
            return pageIcon;    	
    	}else if( actualNode instanceof StepElementDataModel ){
            return elementIcon;
    	}else if( actualNode instanceof StepLoopCollectorDataModel ){
    		if( expanded ){
    			return loopOpenIcon;
    		}else{
    			return loopClosedIcon;    			
    		}
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
