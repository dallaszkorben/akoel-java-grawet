package hu.akoel.grawit.gui.editors.component;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.BaseDataModelInterface;
import hu.akoel.grawit.core.treenodedatamodel.DataModelInterface;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseNodeDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.BasePageDataModel;

import javax.swing.ImageIcon;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class BasePageTreeSelectorComponent extends TreeSelectorComponent<BasePageDataModel>{
	private static final long serialVersionUID = 1194717514083971251L;

	public BasePageTreeSelectorComponent( BaseDataModelInterface rootDataModel ) {
		super(BasePageDataModel.class, rootDataModel);
	}

	public BasePageTreeSelectorComponent( BaseDataModelInterface rootDataModel, BasePageDataModel selectedBasePageDataModel ) {
		super(BasePageDataModel.class, rootDataModel, selectedBasePageDataModel);
	}
	
	@Override
	public String getSelectedDataModelToString( BasePageDataModel selectedDataModel) {
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
		return !( path.getLastPathComponent() instanceof BasePageDataModel );
	}
	
	@Override
	public ImageIcon getIcon(DataModelInterface actualNode, boolean expanded ) {
		ImageIcon pageIcon = CommonOperations.createImageIcon("tree/pagebase-page-icon.png");
		ImageIcon elementIcon = CommonOperations.createImageIcon("tree/pagebase-element-icon.png");
		ImageIcon nodeClosedIcon = CommonOperations.createImageIcon("tree/node-closed-icon.png");
		ImageIcon nodeOpenIcon = CommonOperations.createImageIcon("tree/node-open-icon.png");

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
