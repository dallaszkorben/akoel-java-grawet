package hu.akoel.grawit.gui.editors.component.treeselector;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.DataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.SpecialDataModelInterface;

import javax.swing.ImageIcon;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import TODELETE.hu.akoel.grawit.core.treenodedatamodel.special.SpecialCloseDataModel;
import TODELETE.hu.akoel.grawit.core.treenodedatamodel.special.SpecialCustomDataModel;
import TODELETE.hu.akoel.grawit.core.treenodedatamodel.special.SpecialNodeDataModel;
import TODELETE.hu.akoel.grawit.core.treenodedatamodel.special.SpecialOpenDataModel;

public class SpecialCustomTreeSelectorComponent extends TreeSelectorComponent<SpecialCustomDataModel>{

	private static final long serialVersionUID = -3698310168899684818L;

	public SpecialCustomTreeSelectorComponent( SpecialDataModelInterface rootDataModel ) {
		super(CommonOperations.getTranslation("window.title.selector.specialcustom"), SpecialCustomDataModel.class, rootDataModel, null, false );
	}

	public SpecialCustomTreeSelectorComponent( SpecialDataModelInterface rootDataModel, SpecialCustomDataModel selectedSpecialDataModel ) {
		super(CommonOperations.getTranslation("window.title.selector.specialcustom"), SpecialCustomDataModel.class, rootDataModel, selectedSpecialDataModel, false);
	}
	
	@Override
	public String getSelectedDataModelToString( SpecialCustomDataModel selectedDataModel ) {
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
		return true;
	}
	
	@Override
	public ImageIcon getIcon(DataModelAdapter actualNode, boolean expanded ) {
	
		ImageIcon customIcon = CommonOperations.createImageIcon("tree/special-custom-icon.png");
		ImageIcon closeIcon = CommonOperations.createImageIcon("tree/special-close-icon.png");
		ImageIcon openIcon = CommonOperations.createImageIcon("tree/special-open-icon.png");
    	ImageIcon nodeClosedIcon = CommonOperations.createImageIcon("tree/special-node-closed-icon.png");
    	ImageIcon nodeOpenIcon = CommonOperations.createImageIcon("tree/special-node-open-icon.png");
       	
       	//Iconja a NODE-nak
    	if( actualNode instanceof SpecialOpenDataModel ){
    			return openIcon;
   		}else if( actualNode instanceof SpecialCloseDataModel ){
    			return closeIcon;
   		}else if( actualNode instanceof SpecialCustomDataModel ){
            return customIcon;
   		}else if( actualNode instanceof SpecialOpenDataModel ){
            return openIcon;
    	}else if( actualNode instanceof SpecialNodeDataModel){
    		if( expanded ){
    			return nodeOpenIcon;
    		}else{
    			return nodeClosedIcon;
    		}
        }
    	
		return null;
	}




	
}
