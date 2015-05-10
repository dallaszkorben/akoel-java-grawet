package hu.akoel.grawit.gui.tree;

import java.text.MessageFormat;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.DataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.driver.DriverRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseCaseDataModel;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseFolderDataModel;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseRootDataModel;
import hu.akoel.grawit.enums.ActionCommand;
import hu.akoel.grawit.gui.GUIFrame;
import hu.akoel.grawit.gui.editor.run.RunTestcaseEditor;

public class RunTree extends Tree {

	private static final long serialVersionUID = -7539183206534337777L;
	private GUIFrame guiFrame;
	private DriverRootDataModel driverRootDataModel;
	
	private HashMap<TestcaseDataModelAdapter, RunTestcaseEditor> testcaseMap = new HashMap<>();

	public RunTree(  String functionName, GUIFrame guiFrame, DriverRootDataModel driverRootDataModel, TestcaseRootDataModel testcaseRootDataModel ) {		
		super( functionName, guiFrame, testcaseRootDataModel );
		
		this.guiFrame = guiFrame;
		this.driverRootDataModel = driverRootDataModel;
		
		this.removePopupUp();
		this.removePopupDown();
		this.removePopupModify();
	}
	
	@Override
	public ImageIcon getIcon(DataModelAdapter actualNode, boolean expanded) {

    	ImageIcon caseIcon = CommonOperations.createImageIcon("tree/testcase-case-icon.png");
    	ImageIcon nodeClosedIcon = CommonOperations.createImageIcon("tree/testcase-folder-closed-icon.png");
    	ImageIcon nodeOpenIcon = CommonOperations.createImageIcon("tree/testcase-folder-open-icon.png");
    	ImageIcon rootIcon = CommonOperations.createImageIcon("tree/root-icon.png");
    	  
    	if( actualNode instanceof TestcaseRootDataModel){
            return rootIcon;
    	}else if( actualNode instanceof TestcaseCaseDataModel){
            return caseIcon;
    	}else if( actualNode instanceof TestcaseFolderDataModel){
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
			//guiFrame.showEditorPanel( emptyPanel );
			
			RunTestcaseEditor editor = testcaseMap.get(selectedNode);
			if( null == editor ){
				editor = new RunTestcaseEditor( this, (TestcaseRootDataModel)selectedNode, driverRootDataModel );
				for( LinkToNodeInTreeListener linkToNodeInTreeListener: getLinkToNodeInTreeListeners() ){
					editor.addLinkToNodeInTreeListener( linkToNodeInTreeListener );
				}
				testcaseMap.put((TestcaseRootDataModel)selectedNode, editor );
			}

			guiFrame.showEditorPanel( editor );
			
		}else if( selectedNode instanceof TestcaseFolderDataModel ){
			//guiFrame.showEditorPanel( emptyPanel );
		
			RunTestcaseEditor editor = testcaseMap.get(selectedNode);
			if( null == editor ){
				editor = new RunTestcaseEditor( this, (TestcaseFolderDataModel)selectedNode, driverRootDataModel );
				for( LinkToNodeInTreeListener linkToNodeInTreeListener: getLinkToNodeInTreeListeners() ){
					editor.addLinkToNodeInTreeListener( linkToNodeInTreeListener );
				}
				testcaseMap.put((TestcaseFolderDataModel)selectedNode, editor );
			}

			guiFrame.showEditorPanel( editor );
			
		}else if( selectedNode instanceof TestcaseCaseDataModel ){
			
			RunTestcaseEditor editor = testcaseMap.get(selectedNode);
			if( null == editor ){
				editor = new RunTestcaseEditor( this, (TestcaseCaseDataModel)selectedNode, driverRootDataModel );
				for( LinkToNodeInTreeListener linkToNodeInTreeListener: getLinkToNodeInTreeListeners() ){
					editor.addLinkToNodeInTreeListener( linkToNodeInTreeListener );
				}
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
	public void doPopupDuplicate(JPopupMenu popupMenu, DataModelAdapter selectedNode, int selectedRow, DefaultTreeModel totalTreeModel) {
		//NO DUPLICATION ENABLED		
	}

	@Override
	public void doPopupLink(JPopupMenu popupMenu, DataModelAdapter selectedNode) {

		if( selectedNode instanceof TestcaseCaseDataModel ){
			
			TestcaseCaseDataModel testcaseCase = ((TestcaseCaseDataModel)selectedNode);
			
			JMenuItem linkToStepMenu = new JMenuItem(
				MessageFormat.format( 
					CommonOperations.getTranslation("tree.popupmenu.linkto.testcase"), 
					testcaseCase.getName() 
				) 					
			);
			linkToStepMenu.setActionCommand( ActionCommand.LINK.name());
			linkToStepMenu.addActionListener( new LinkToElementListener( testcaseCase ) );
				
			popupMenu.addSeparator();
			popupMenu.add( linkToStepMenu );
			
		}
	}

}
