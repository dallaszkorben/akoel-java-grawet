package hu.akoel.grawit.gui.editors.component.treeselector;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.DataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.CollectorDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.collector.CollectorParamElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.collector.CollectorLoopDataModel;
import hu.akoel.grawit.core.treenodedatamodel.collector.CollectorNodeDataModel;
import hu.akoel.grawit.core.treenodedatamodel.collector.CollectorNormalDataModel;
import hu.akoel.grawit.core.treenodedatamodel.collector.CollectorExecutableDataModelAdapter;

import javax.swing.ImageIcon;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class ParamPageTreeSelectorComponent extends TreeSelectorComponent<CollectorExecutableDataModelAdapter>{

	private static final long serialVersionUID = 1064181673121972602L;

	public ParamPageTreeSelectorComponent( CollectorDataModelAdapter rootDataModel ) {
		super(CommonOperations.getTranslation("window.title.selector.parampage"), CollectorExecutableDataModelAdapter.class, rootDataModel, null, false);
	}

	public ParamPageTreeSelectorComponent( CollectorDataModelAdapter rootDataModel, CollectorExecutableDataModelAdapter selectedParamPageDataModel ) {
		super(CommonOperations.getTranslation("window.title.selector.parampage"), CollectorExecutableDataModelAdapter.class, rootDataModel, selectedParamPageDataModel, false);
	}
	
	@Override
	public String getSelectedDataModelToString( CollectorExecutableDataModelAdapter selectedDataModel) {
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
		return !( path.getLastPathComponent() instanceof CollectorExecutableDataModelAdapter );
	}
	
	@Override
	public ImageIcon getIcon(DataModelAdapter actualNode, boolean expanded ) {
	
		ImageIcon pageIcon = CommonOperations.createImageIcon("tree/param-page-icon.png");
    	ImageIcon elementIcon = CommonOperations.createImageIcon("tree/param-element-icon.png");
    	ImageIcon nodeClosedIcon = CommonOperations.createImageIcon("tree/param-node-closed-icon.png");
    	ImageIcon nodeOpenIcon = CommonOperations.createImageIcon("tree/param-node-open-icon.png");
    	ImageIcon loopOpenIcon = CommonOperations.createImageIcon("tree/param-loop-icon.png");
    	ImageIcon loopClosedIcon = CommonOperations.createImageIcon("tree/param-loop-icon.png");
    	
    	//Iconja a NODE-nak
    	if( actualNode instanceof CollectorNormalDataModel){
            return pageIcon;    	
    	}else if( actualNode instanceof CollectorParamElementDataModel ){
            return elementIcon;
    	}else if( actualNode instanceof CollectorLoopDataModel ){
    		if( expanded ){
    			return loopOpenIcon;
    		}else{
    			return loopClosedIcon;    			
    		}
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
