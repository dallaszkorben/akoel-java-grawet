package hu.akoel.grawit.gui.tree;

import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JPopupMenu;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.DataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.TestcaseDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseCaseDataModel;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseNodeDataModel;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseRootDataModel;
import hu.akoel.grawit.gui.GUIFrame;
import hu.akoel.grawit.gui.editor.EmptyEditor;
import hu.akoel.grawit.gui.editor.run.RunTestcaseEditor;

public class RunTree extends Tree {

	private static final long serialVersionUID = -7537783206534337777L;
	private GUIFrame guiFrame;
	private EmptyEditor emptyPanel;		
	
	private HashMap<TestcaseDataModelAdapter, RunTestcaseEditor> testcaseMap = new HashMap<>();

	public RunTree(GUIFrame guiFrame, TestcaseRootDataModel testcaseRootDataModel ) {		
		super(guiFrame, testcaseRootDataModel);
		
		this.guiFrame = guiFrame;
		
		this.removePopupUp();
		this.removePopupDown();
		this.removePopupModify();
		
		emptyPanel = new EmptyEditor();
	}
	
	@Override
	public ImageIcon getIcon(DataModelAdapter actualNode, boolean expanded) {

//		ImageIcon customIcon = CommonOperations.createImageIcon("tree/testcase-custom-icon.png");
//    	ImageIcon pageIcon = CommonOperations.createImageIcon("tree/testcase-page-icon.png");
    	ImageIcon caseIcon = CommonOperations.createImageIcon("tree/testcase-case-icon.png");
    	ImageIcon nodeClosedIcon = CommonOperations.createImageIcon("tree/testcase-node-closed-icon.png");
    	ImageIcon nodeOpenIcon = CommonOperations.createImageIcon("tree/testcase-node-open-icon.png");
    	  	
    	if( actualNode instanceof TestcaseCaseDataModel){
            return caseIcon;
/*    	}else if( actualNode instanceof TestcaseParamPageDataModel ){
            return pageIcon;
    	}else if( actualNode instanceof TestcaseCustomDataModel ){
            return customIcon;            
*/    	}else if( actualNode instanceof TestcaseNodeDataModel){
    		if( expanded ){
    			return nodeOpenIcon;
    		}else{
    			return nodeClosedIcon;
    		}
        }
    	
		return null;
	}

	@Override
	public ImageIcon getIconOff(DataModelAdapter actualNode, boolean expanded) {

    	ImageIcon caseIcon = CommonOperations.createImageIcon("tree/testcase-case-off-icon.png");
    	
    	if( actualNode instanceof TestcaseCaseDataModel){
            return caseIcon;
    	}else{
    		return getIcon(actualNode, expanded);
        }
	}
	
	@Override
	public void doViewWhenSelectionChanged(DataModelAdapter selectedNode) {
		
		//Ha a root-ot valasztottam
		if( selectedNode instanceof TestcaseRootDataModel ){									
			guiFrame.showEditorPanel( emptyPanel );
			
		}else if( selectedNode instanceof TestcaseNodeDataModel ){
			//guiFrame.showEditorPanel( emptyPanel );
		
			RunTestcaseEditor editor = testcaseMap.get(selectedNode);
			if( null == editor ){
				editor = new RunTestcaseEditor( this, (TestcaseNodeDataModel)selectedNode );
				testcaseMap.put((TestcaseNodeDataModel)selectedNode, editor );
			}

			guiFrame.showEditorPanel( editor );
			
		}else if( selectedNode instanceof TestcaseCaseDataModel ){
			
			RunTestcaseEditor editor = testcaseMap.get(selectedNode);
			if( null == editor ){
				editor = new RunTestcaseEditor( this, (TestcaseCaseDataModel)selectedNode );
				testcaseMap.put((TestcaseCaseDataModel)selectedNode, editor );
			}
				
			guiFrame.showEditorPanel( editor );
					
		}
	}

	@Override
	public void doModifyWithPopupEdit(DataModelAdapter selectedNode) {
	}

	@Override
	public void doPopupInsert(JPopupMenu popupMenu,	DataModelAdapter selectedNode) {
	}

	@Override
	public void doPopupDelete(JPopupMenu popupMenu, DataModelAdapter selectedNode, int selectedRow, DefaultTreeModel totalTreeModel) {
	}

	@Override
	public void doPopupRootInsert(JPopupMenu popupMenu, DataModelAdapter selectedNode) {
	}

	/**
	 * 
	 * Megakadalyozza a teszteset kinyitasat
	 * 
	 */
	protected void setExpandedState(TreePath path, boolean state) {
	       
		if( !( path.getLastPathComponent() instanceof TestcaseCaseDataModel ) ){
				
			super.setExpandedState(path, state);
		}
	}

	@Override
	public boolean possibleHierarchy(DefaultMutableTreeNode draggedNode, Object dropObject) {
		return false;
	}

	@Override
	public void doDuplicate(JPopupMenu popupMenu, DataModelAdapter selectedNode, int selectedRow, DefaultTreeModel totalTreeModel) {
		//NO DUPLICATION ENABLED		
	}

}
