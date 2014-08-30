package hu.akoel.grawit.gui.tree;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.tree.DefaultTreeModel;

import hu.akoel.grawit.ActionCommand;
import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.DataModelInterface;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseCaseDataModel;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseNodeDataModel;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcasePageDataModel;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.variable.VariableRootDataModel;
import hu.akoel.grawit.gui.GUIFrame;
import hu.akoel.grawit.gui.editor.EmptyEditor;
import hu.akoel.grawit.gui.editor.DataEditor.EditMode;
import hu.akoel.grawit.gui.editor.testcase.TestcaseCaseEditor;
import hu.akoel.grawit.gui.editor.testcase.TestcaseNodeEditor;
import hu.akoel.grawit.gui.editor.testcase.TestcasePageEditor;

public class TestcaseTree extends Tree {

	private static final long serialVersionUID = -7537783206534337777L;
	private GUIFrame guiFrame;
	private BaseRootDataModel baseRootDataModel;
	private ParamRootDataModel paramRootDataModel;
	
	public TestcaseTree(GUIFrame guiFrame, BaseRootDataModel baseRootDataModel, ParamRootDataModel paramRootDataModel, TestcaseRootDataModel rootDataModel ) {
		super(guiFrame, rootDataModel);
		
		this.guiFrame = guiFrame;
		this.baseRootDataModel = baseRootDataModel;
		this.paramRootDataModel = paramRootDataModel;
		
	}

	@Override
	public ImageIcon getIcon(DataModelInterface actualNode, boolean expanded) {

    	ImageIcon pageIcon = CommonOperations.createImageIcon("tree/testcase-page-icon.png");
    	ImageIcon caseIcon = CommonOperations.createImageIcon("tree/testcase-case-icon.png");
    	ImageIcon nodeClosedIcon = CommonOperations.createImageIcon("tree/testcase-node-closed-icon.png");
    	ImageIcon nodeOpenIcon = CommonOperations.createImageIcon("tree/testcase-node-open-icon.png");
    	
    	//Iconja a NODE-nak
    	if( actualNode instanceof TestcaseCaseDataModel){
            return caseIcon;
    	}else if( actualNode instanceof TestcasePageDataModel ){
            return pageIcon;
    	}else if( actualNode instanceof TestcaseNodeDataModel){
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
			TestcaseNodeEditor testcaseNodeEditor = new TestcaseNodeEditor( this, (TestcaseNodeDataModel)selectedNode, EditMode.VIEW);
			guiFrame.showEditorPanel( testcaseNodeEditor);								
		
		}else if( selectedNode instanceof TestcaseCaseDataModel ){
			TestcaseCaseEditor testcaseCaseEditor = new TestcaseCaseEditor( this, (TestcaseCaseDataModel)selectedNode, EditMode.VIEW );								
			guiFrame.showEditorPanel( testcaseCaseEditor);				
							
		}else if( selectedNode instanceof TestcasePageDataModel ){
			TestcasePageEditor testcasePageEditor = new TestcasePageEditor( this, (TestcasePageDataModel)selectedNode, paramRootDataModel, EditMode.VIEW );	
			guiFrame.showEditorPanel( testcasePageEditor);									
			
		}
		
	}

	@Override
	public void doModifyWithPopupEdit(DataModelInterface selectedNode) {
		
		if( selectedNode instanceof TestcaseNodeDataModel ){
			
			TestcaseNodeEditor testcaseNodeEditor = new TestcaseNodeEditor( this, (TestcaseNodeDataModel)selectedNode, EditMode.MODIFY );								
			guiFrame.showEditorPanel( testcaseNodeEditor);								
				
		}else if( selectedNode instanceof TestcaseCaseDataModel ){
			
			TestcaseCaseEditor testcaseCaseEditor = new TestcaseCaseEditor( this, (TestcaseCaseDataModel)selectedNode, EditMode.MODIFY );							                                            
			guiFrame.showEditorPanel( testcaseCaseEditor);		
				
		}else if( selectedNode instanceof ParamElementDataModel ){

			TestcasePageEditor testcasePageEditor = new TestcasePageEditor( this, (TestcasePageDataModel)selectedNode, paramRootDataModel, EditMode.MODIFY );
			guiFrame.showEditorPanel( testcasePageEditor);		
				
		}		
	}

	@Override
	public void doPopupInsert( JPopupMenu popupMenu, final DataModelInterface selectedNode) {
		
		//
		// Csomopont eseten
		//
		if( selectedNode instanceof TestcaseNodeDataModel ){

			//Insert Node
			JMenuItem insertNodeMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.node") );
			insertNodeMenu.setActionCommand( ActionCommand.CAPTURE.name());
			insertNodeMenu.addActionListener( new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					
					TestcaseNodeEditor paramNodeEditor = new TestcaseNodeEditor( TestcaseTree.this, (TestcaseNodeDataModel)selectedNode );								
					guiFrame.showEditorPanel( paramNodeEditor);								
				
				}
			});
			popupMenu.add ( insertNodeMenu );

			//Insert Case
			JMenuItem insertPageMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.case") );
			insertPageMenu.setActionCommand( ActionCommand.CAPTURE.name());
			insertPageMenu.addActionListener( new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					
					TestcaseCaseEditor testcaseCaseEditor = new TestcaseCaseEditor( TestcaseTree.this, (TestcaseNodeDataModel)selectedNode );								
					guiFrame.showEditorPanel( testcaseCaseEditor);								
				
				}
			});
			popupMenu.add ( insertPageMenu );
			
		}		
		
		//
		// Case eseten
		//
		if( selectedNode instanceof TestcaseCaseDataModel ){

			//Insert Element
			JMenuItem insertElementMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.casepage") );
			insertElementMenu.setActionCommand( ActionCommand.CAPTURE.name());
			insertElementMenu.addActionListener( new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					
					TestcasePageEditor testcasePageEditor = new TestcasePageEditor( TestcaseTree.this, (TestcaseCaseDataModel)selectedNode, paramRootDataModel );								
					guiFrame.showEditorPanel( testcasePageEditor);								
				
				}
			});
			popupMenu.add ( insertElementMenu );
		
		}
		
	}

	@Override
	public void doPopupDelete( final JPopupMenu popupMenu, final DataModelInterface selectedNode, final int selectedRow, final DefaultTreeModel totalTreeModel) {
	
		if( selectedNode.getChildCount() == 0 ){
			
			
			JMenuItem deleteMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.delete") );
			deleteMenu.setActionCommand( ActionCommand.UP.name());
			deleteMenu.addActionListener( new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {

					//Megerosito kerdes
					Object[] options = {
							CommonOperations.getTranslation("button.no"),
							CommonOperations.getTranslation("button.yes")								
					};
					
					int n = JOptionPane.showOptionDialog(guiFrame,
							"Valóban torolni kívánod a(z) " + selectedNode.getTag() + " nevü " + selectedNode.getModelNameToShow() + "-t ?",
							CommonOperations.getTranslation("editor.windowtitle.confirmation.delete"),
							JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.QUESTION_MESSAGE,
							null,
							options,
							options[0]);

					if( n == 1 ){
						totalTreeModel.removeNodeFromParent( selectedNode);
						TestcaseTree.this.setSelectionRow(selectedRow - 1);
					}							
				}
			});
			popupMenu.add ( deleteMenu );
			
		}		
	}

	@Override
	public void doPopupRootInsert( JPopupMenu popupMenu, final DataModelInterface selectedNode ) {
		
		//Insert Node
		JMenuItem insertNodeMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.node") );
		insertNodeMenu.setActionCommand( ActionCommand.CAPTURE.name());
		insertNodeMenu.addActionListener( new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent e) {
				
				TestcaseNodeEditor paramNodeEditor = new TestcaseNodeEditor( TestcaseTree.this, (TestcaseNodeDataModel)selectedNode );								
				guiFrame.showEditorPanel( paramNodeEditor);								
			
			}
		});
		popupMenu.add ( insertNodeMenu );
		
	}

}
