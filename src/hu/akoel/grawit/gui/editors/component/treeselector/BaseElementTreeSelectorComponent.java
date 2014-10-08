package hu.akoel.grawit.gui.editors.component.treeselector;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.BaseDataModelInterface;
import hu.akoel.grawit.core.treenodedatamodel.DataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseNodeDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.BasePageDataModel;

import javax.swing.ImageIcon;
import javax.swing.tree.TreePath;

public class BaseElementTreeSelectorComponent extends TreeSelectorComponent<BaseElementDataModel>{

	private static final long serialVersionUID = -5178610032767904794L;

	public BaseElementTreeSelectorComponent( BaseDataModelInterface rootDataModel ) {
		super(BaseElementDataModel.class, rootDataModel, null, false);
	}

	public BaseElementTreeSelectorComponent( BaseDataModelInterface rootDataModel, BaseElementDataModel selectedBaseElementDataModel ) {
		super(BaseElementDataModel.class, rootDataModel, selectedBaseElementDataModel, false);
	}
	
	@Override
	public String getSelectedDataModelToString( BaseElementDataModel selectedDataModel) {
		StringBuffer out = new StringBuffer();
		out.append( selectedDataModel.getName() );
		return out.toString();
	}

	@Override
	public boolean needToExpand(TreePath path, boolean state) {
		return true;
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

