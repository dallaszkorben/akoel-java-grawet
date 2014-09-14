package hu.akoel.grawit.gui.tree;

import javax.swing.ImageIcon;
import javax.swing.JPopupMenu;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.DataModelInterface;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamPageDataModel;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseCaseDataModel;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseNodeDataModel;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseRootDataModel;
import hu.akoel.grawit.gui.GUIFrame;
import hu.akoel.grawit.gui.editor.EmptyEditor;
import hu.akoel.grawit.gui.editor.run.RunTestcaseEditor;

public class RunTree extends Tree {

	private static final long serialVersionUID = -7537783206534337777L;
	private GUIFrame guiFrame;
	
//	private ParamRootDataModel paramRootDataModel;
//	private SpecialRootDataModel specialRootDataModel;
//	private DriverRootDataModel driverRootDataModel;
	
	//public RunTree(GUIFrame guiFrame, BaseRootDataModel baseRootDataModel, SpecialRootDataModel specialRootDataModel, ParamRootDataModel paramRootDataModel, DriverRootDataModel driverRootDataModel, TestcaseRootDataModel testcaseRootDataModel ) {
	public RunTree(GUIFrame guiFrame, TestcaseRootDataModel testcaseRootDataModel ) {		
		super(guiFrame, testcaseRootDataModel);
		
		this.guiFrame = guiFrame;
//		this.specialRootDataModel = specialRootDataModel;
//		this.paramRootDataModel = paramRootDataModel;
//		this.driverRootDataModel = driverRootDataModel;
		
		this.removePopupUp();
		this.removePopupDown();
		this.removePopupModify();
	}
	
	@Override
	public ImageIcon getIcon(DataModelInterface actualNode, boolean expanded) {

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
	public void doViewWhenSelectionChanged(DataModelInterface selectedNode) {
		
		//Ha a root-ot valasztottam
		if( selectedNode instanceof TestcaseRootDataModel ){
			EmptyEditor emptyPanel = new EmptyEditor();								
			guiFrame.showEditorPanel( emptyPanel );
			
		}else if( selectedNode instanceof TestcaseNodeDataModel ){
//			TestcaseNodeEditor testcaseNodeEditor = new TestcaseNodeEditor( this, (TestcaseNodeDataModel)selectedNode, EditMode.VIEW);
//			guiFrame.showEditorPanel( testcaseNodeEditor);								
		
		}else if( selectedNode instanceof TestcaseCaseDataModel ){
			
			//RunTestcaseEditor testcaseCaseEditor = new RunTestcaseEditor( this, (TestcaseCaseDataModel)selectedNode );
			
			guiFrame.showEditorPanel( RunTestcaseEditor.getInstance( this, (TestcaseCaseDataModel)selectedNode ) );				
							
/*		}else if( selectedNode instanceof TestcaseParamPageDataModel ){
			TestcaseParamPageEditor testcasePageEditor = new TestcaseParamPageEditor( this, (TestcaseParamPageDataModel)selectedNode, paramRootDataModel, EditMode.VIEW );	
			guiFrame.showEditorPanel( testcasePageEditor);									
			
		}else if( selectedNode instanceof TestcaseCustomDataModel ){
			TestcaseCustomPageEditor testcaseCustomEditor = new TestcaseCustomPageEditor( this, (TestcaseCustomDataModel)selectedNode, specialRootDataModel, EditMode.VIEW );	
			guiFrame.showEditorPanel( testcaseCustomEditor);
*/			
		}
	}

	@Override
	public void doModifyWithPopupEdit(DataModelInterface selectedNode) {
	}

	@Override
	public void doPopupInsert(JPopupMenu popupMenu,	DataModelInterface selectedNode) {
	}

	@Override
	public void doPopupDelete(JPopupMenu popupMenu, DataModelInterface selectedNode, int selectedRow, DefaultTreeModel totalTreeModel) {
	}

	@Override
	public void doPopupRootInsert(JPopupMenu popupMenu, DataModelInterface selectedNode) {
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

}
