package hu.akoel.grawit.gui.editors.component.treeselector;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.BaseDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.DataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseNodeDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.BasePageDataModel;

import javax.swing.ImageIcon;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class BasePageTreeSelectorComponent extends TreeSelectorComponent<BasePageDataModel>{
	private static final long serialVersionUID = 1194717514083971251L;

	public BasePageTreeSelectorComponent( BaseDataModelAdapter rootDataModel ) {
		super(CommonOperations.getTranslation("window.title.selector.basepage"), BasePageDataModel.class, rootDataModel, null, false );
	}

	public BasePageTreeSelectorComponent( BaseDataModelAdapter rootDataModel, BasePageDataModel selectedBasePageDataModel ) {
		super(CommonOperations.getTranslation("window.title.selector.basepage"), BasePageDataModel.class, rootDataModel, selectedBasePageDataModel, false);
	}
	
	@Override
	public String getSelectedDataModelToString( BasePageDataModel selectedDataModel) {
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
		return !( path.getLastPathComponent() instanceof BasePageDataModel );
	}
	
	@Override
	public ImageIcon getIcon(DataModelAdapter actualNode, boolean expanded ) {
		ImageIcon pageIcon = CommonOperations.createImageIcon("tree/base-page-icon.png");
		ImageIcon elementIcon = CommonOperations.createImageIcon("tree/base-element-icon.png");
		ImageIcon nodeClosedIcon = CommonOperations.createImageIcon("tree/base-node-closed-icon.png");
		ImageIcon nodeOpenIcon = CommonOperations.createImageIcon("tree/base-node-open-icon.png");

		//Iconja a NODE-nak
		if( actualNode instanceof BasePageDataModel){
			return (pageIcon);
		}else if( actualNode instanceof BaseElementDataModel ){
			return (elementIcon);
		}else if( actualNode instanceof BaseNodeDataModel){
			if( expanded ){
				return (nodeOpenIcon);
			}else{
				return (nodeClosedIcon);
			}
		}
		return null;
	}


	
}
