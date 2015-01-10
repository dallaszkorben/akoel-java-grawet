package hu.akoel.grawit.gui.editors.component.treeselector;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.BaseDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.BaseElementDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.DataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseCollectorDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseFolderDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.NormalBaseElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.ScriptBaseElementDataModel;

import javax.swing.ImageIcon;
import javax.swing.tree.TreePath;

public class BaseElementTreeSelectorComponent extends TreeSelectorComponent<BaseElementDataModelAdapter>{

	private static final long serialVersionUID = -5178610032767904794L;

	//Specific Page egyik eleme - Parameterkent szukseges megkapni a BasePage elereset
	public BaseElementTreeSelectorComponent( BaseDataModelAdapter rootDataModel ) {
		super(CommonOperations.getTranslation("window.title.selector.baseelement"), BaseElementDataModelAdapter.class, rootDataModel, null, false);
	}

	//NonSpecific Page Egyik eleme - Parameterkent szukseges megkapni a Root Base-t
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
		ImageIcon normalElementIcon = CommonOperations.createImageIcon("tree/base-element-normal-icon.png");
		ImageIcon scriptElementIcon = CommonOperations.createImageIcon("tree/base-element-script-icon.png");
		ImageIcon nodeClosedIcon = CommonOperations.createImageIcon("tree/base-folder-closed-icon.png");
		ImageIcon nodeOpenIcon = CommonOperations.createImageIcon("tree/base-folder-open-icon.png");
		ImageIcon rootIcon = CommonOperations.createImageIcon("tree/root-icon.png");

		//Iconja a NODE-nak
		if( actualNode instanceof BaseRootDataModel){
            return rootIcon;
		}else if( actualNode instanceof BaseCollectorDataModel){
			return (pageIcon);
		}else if( actualNode instanceof NormalBaseElementDataModel ){
			return (normalElementIcon);
		}else if( actualNode instanceof ScriptBaseElementDataModel ){
			return (scriptElementIcon);
		}else if( actualNode instanceof BaseFolderDataModel){
			if( expanded ){
				return (nodeOpenIcon);
			}else{
				return (nodeClosedIcon);
			}
		}
		return null;
	}
	
}

