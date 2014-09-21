package hu.akoel.grawit.gui.editors.component.treeselector;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.DataModelInterface;
import hu.akoel.grawit.core.treenodedatamodel.VariableDataModelInterface;
import hu.akoel.grawit.core.treenodedatamodel.variable.VariableElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.variable.VariableNodeDataModel;

import javax.swing.ImageIcon;
import javax.swing.tree.TreePath;

public class VariableTreeSelectorComponent extends TreeSelectorComponent<VariableElementDataModel>{

	private static final long serialVersionUID = 5692189257383238770L;

	public VariableTreeSelectorComponent( VariableDataModelInterface rootDataModel ) {
		super(VariableElementDataModel.class, rootDataModel);
	}

	public VariableTreeSelectorComponent( VariableDataModelInterface rootDataModel, VariableElementDataModel selectedVariableElementDataModel ) {
		super(VariableElementDataModel.class, rootDataModel, selectedVariableElementDataModel);
	}
	
	@Override
	public String getSelectedDataModelToString( VariableElementDataModel selectedDataModel) {
		StringBuffer out = new StringBuffer();
		out.append( selectedDataModel.getName() );
		return out.toString();
	}

	@Override
	public boolean needToExpand(TreePath path, boolean state) {
		return true;
		//return !( path.getLastPathComponent() instanceof VariableElementDataModel );
	}
	
	@Override
	public ImageIcon getIcon(DataModelInterface actualNode, boolean expanded ) {
		//ImageIcon pageIcon = CommonOperations.createImageIcon("tree/variable-page-icon.png");
		ImageIcon elementIcon = CommonOperations.createImageIcon("tree/variable-element-icon.png");
		ImageIcon nodeClosedIcon = CommonOperations.createImageIcon("tree/variable-node-closed-icon.png");
		ImageIcon nodeOpenIcon = CommonOperations.createImageIcon("tree/variable-node-open-icon.png");

		//Iconja a NODE-nak
		if( actualNode instanceof VariableElementDataModel ){
			return (elementIcon);
		}else if( actualNode instanceof VariableNodeDataModel){
			if( expanded ){
				return (nodeOpenIcon);
			}else{
				return (nodeClosedIcon);
			}
		}
		return null;
	}
	
}

