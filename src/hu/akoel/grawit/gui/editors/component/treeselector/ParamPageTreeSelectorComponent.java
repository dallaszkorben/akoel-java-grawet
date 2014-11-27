package hu.akoel.grawit.gui.editors.component.treeselector;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.DataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.ParamDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamNodeDataModel;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamPageDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamPageNonSpecificDataModel;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamPageSpecificDataModel;

import javax.swing.ImageIcon;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class ParamPageTreeSelectorComponent extends TreeSelectorComponent<ParamPageDataModelAdapter>{

	private static final long serialVersionUID = 1064181673121972602L;

	public ParamPageTreeSelectorComponent( ParamDataModelAdapter rootDataModel ) {
		super(CommonOperations.getTranslation("window.title.selector.parampage"), ParamPageDataModelAdapter.class, rootDataModel, null, false);
	}

	public ParamPageTreeSelectorComponent( ParamDataModelAdapter rootDataModel, ParamPageDataModelAdapter selectedParamPageDataModel ) {
		super(CommonOperations.getTranslation("window.title.selector.parampage"), ParamPageDataModelAdapter.class, rootDataModel, selectedParamPageDataModel, false);
	}
	
	@Override
	public String getSelectedDataModelToString( ParamPageDataModelAdapter selectedDataModel) {
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
		return !( path.getLastPathComponent() instanceof ParamPageDataModelAdapter );
	}
	
	@Override
	public ImageIcon getIcon(DataModelAdapter actualNode, boolean expanded ) {
	
		ImageIcon pageSpecificIcon = CommonOperations.createImageIcon("tree/param-page-specific-icon.png");
		ImageIcon pageNonSpecificIcon = CommonOperations.createImageIcon("tree/param-page-nonspecific-icon.png");
    	ImageIcon elementIcon = CommonOperations.createImageIcon("tree/param-element-icon.png");
    	ImageIcon nodeClosedIcon = CommonOperations.createImageIcon("tree/param-node-closed-icon.png");
    	ImageIcon nodeOpenIcon = CommonOperations.createImageIcon("tree/param-node-open-icon.png");
    	
    	//Iconja a NODE-nak
    	if( actualNode instanceof ParamPageSpecificDataModel){
            return pageSpecificIcon;
    	}else if( actualNode instanceof ParamPageNonSpecificDataModel){
    		return pageNonSpecificIcon;
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
