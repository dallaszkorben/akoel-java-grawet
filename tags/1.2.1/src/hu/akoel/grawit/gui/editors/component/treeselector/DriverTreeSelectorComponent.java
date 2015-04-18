package hu.akoel.grawit.gui.editors.component.treeselector;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.DataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.driver.DriverBrowserDataModelInterface;
import hu.akoel.grawit.core.treenodedatamodel.driver.DriverDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.driver.DriverExplorerCapabilityDataModel;
import hu.akoel.grawit.core.treenodedatamodel.driver.DriverExplorerDataModel;
import hu.akoel.grawit.core.treenodedatamodel.driver.DriverFirefoxDataModel;
import hu.akoel.grawit.core.treenodedatamodel.driver.DriverFirefoxPropertyDataModel;
import hu.akoel.grawit.core.treenodedatamodel.driver.DriverFolderDataModel;
import hu.akoel.grawit.core.treenodedatamodel.driver.DriverRootDataModel;
import hu.akoel.grawit.gui.editor.driver.DriverNodeEditor;

import javax.swing.ImageIcon;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class DriverTreeSelectorComponent extends TreeSelectorComponent<DriverBrowserDataModelInterface>{

	private static final long serialVersionUID = -3698310168899684818L;

	public DriverTreeSelectorComponent( DriverDataModelAdapter rootDataModel ) {
		super(CommonOperations.getTranslation("window.title.selector.driver"), DriverBrowserDataModelInterface.class, rootDataModel, null, false, false );
	}

	public DriverTreeSelectorComponent( DriverDataModelAdapter rootDataModel, DriverBrowserDataModelInterface selectedSpecialDataModel ) {
		super(CommonOperations.getTranslation("window.title.selector.driver"), DriverBrowserDataModelInterface.class, rootDataModel, selectedSpecialDataModel, true, false);
	}
	
	@Override
	public String getSelectedDataModelToString( DriverBrowserDataModelInterface selectedDataModel ) {
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
		return !( path.getLastPathComponent() instanceof DriverBrowserDataModelInterface );
	}
	
	@Override
	public ImageIcon getIcon(DataModelAdapter actualNode, boolean expanded ) {
	
		ImageIcon nodeIcon = CommonOperations.createImageIcon("tree/driver-root-open-icon.png");
    	ImageIcon explorerIcon = CommonOperations.createImageIcon("tree/driver-explorer-icon.png");
    	ImageIcon explorerCapabilityIcon = CommonOperations.createImageIcon("tree/driver-explorer-capability-icon.png");
    	ImageIcon firefoxIcon = CommonOperations.createImageIcon("tree/driver-firefox-icon.png");
    	ImageIcon firefoxPropertyIcon = CommonOperations.createImageIcon("tree/driver-firefox-property-icon.png");
    	ImageIcon nodeClosedIcon = CommonOperations.createImageIcon("tree/driver-node-closed-icon.png");
    	ImageIcon nodeOpenIcon = CommonOperations.createImageIcon("tree/driver-node-open-icon.png");
    	ImageIcon rootIcon = CommonOperations.createImageIcon("tree/root-icon.png");
  
    	//Iconja a NODE-nak
    	if( actualNode instanceof DriverRootDataModel){
            return rootIcon;
    	}else if( actualNode instanceof DriverRootDataModel){
            return nodeIcon;
    	}else if( actualNode instanceof DriverFirefoxPropertyDataModel ){
    		return firefoxPropertyIcon;
    	}else if( actualNode instanceof DriverExplorerCapabilityDataModel ){
    		return explorerCapabilityIcon;            
    	}else if( actualNode instanceof DriverExplorerDataModel ){
            return explorerIcon;
    	}else if( actualNode instanceof DriverFirefoxDataModel ){
    		return firefoxIcon;
    	
    	}else if( actualNode instanceof DriverFolderDataModel){
    		if( expanded ){
    			return nodeOpenIcon;
    		}else{
    			return nodeClosedIcon;
    		}
    	}
    	
		return null;
	}




	
}
