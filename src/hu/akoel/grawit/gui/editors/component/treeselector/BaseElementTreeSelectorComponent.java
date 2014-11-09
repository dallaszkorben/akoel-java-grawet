package hu.akoel.grawit.gui.editors.component.treeselector;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.BaseDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.BaseElementDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.DataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseNodeDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.BasePageDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.NormalBaseElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.SpecialBaseElementDataModel;

import javax.swing.ImageIcon;
import javax.swing.tree.TreePath;

public class BaseElementTreeSelectorComponent extends TreeSelectorComponent<BaseElementDataModelAdapter>{

	private static final long serialVersionUID = -5178610032767904794L;

	public BaseElementTreeSelectorComponent( BaseDataModelAdapter rootDataModel ) {
		super(CommonOperations.getTranslation("window.title.selector.baseelement"), BaseElementDataModelAdapter.class, rootDataModel, null, false);
	}

	public BaseElementTreeSelectorComponent( BaseDataModelAdapter rootDataModel, BaseElementDataModelAdapter selectedBaseElementDataModel ) {
		super(CommonOperations.getTranslation("window.title.selector.baseelement"),BaseElementDataModelAdapter.class, rootDataModel, selectedBaseElementDataModel, false);
	}
	
	@Override
	public String getSelectedDataModelToString( BaseElementDataModelAdapter selectedDataModel) {
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
		ImageIcon normalElementIcon = CommonOperations.createImageIcon("tree/base-element-icon.png");
		ImageIcon specialElementIcon = CommonOperations.createImageIcon("tree/base-element-icon.png");
		ImageIcon nodeClosedIcon = CommonOperations.createImageIcon("tree/base-node-closed-icon.png");
		ImageIcon nodeOpenIcon = CommonOperations.createImageIcon("tree/base-node-open-icon.png");

		//Iconja a NODE-nak
		if( actualNode instanceof BasePageDataModel){
			return (pageIcon);
		}else if( actualNode instanceof NormalBaseElementDataModel ){
			return (normalElementIcon);
		}else if( actualNode instanceof SpecialBaseElementDataModel ){
			return (specialElementIcon);
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

