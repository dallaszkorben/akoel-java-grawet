package hu.akoel.grawit.gui.tree;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultTreeModel;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.DataModelInterface;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.driver.DriverRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.special.SpecialRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseCaseDataModel;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseNodeDataModel;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseParamPageDataModel;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseCustomDataModel;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseRootDataModel;
import hu.akoel.grawit.enums.ActionCommand;
import hu.akoel.grawit.gui.GUIFrame;
import hu.akoel.grawit.gui.editor.EmptyEditor;
import hu.akoel.grawit.gui.editor.DataEditor.EditMode;
import hu.akoel.grawit.gui.editor.testcase.TestcaseCaseEditor;
import hu.akoel.grawit.gui.editor.testcase.TestcaseCustomPageEditor;
import hu.akoel.grawit.gui.editor.testcase.TestcaseNodeEditor;
import hu.akoel.grawit.gui.editor.testcase.TestcaseParamPageEditor;

public class TestcaseTree extends Tree {

	private static final long serialVersionUID = -7537783206534337777L;
	private GUIFrame guiFrame;
	
//	private TestcaseRootDataModel testcaseRootDataModel;
	private ParamRootDataModel paramRootDataModel;
	private SpecialRootDataModel specialRootDataModel;
	private DriverRootDataModel driverRootDataModel;
	
	public TestcaseTree(GUIFrame guiFrame, BaseRootDataModel baseRootDataModel, SpecialRootDataModel specialRootDataModel, ParamRootDataModel paramRootDataModel, DriverRootDataModel driverRootDataModel, TestcaseRootDataModel testcaseRootDataModel ) {
		super(guiFrame, testcaseRootDataModel);
		
		this.guiFrame = guiFrame;
//		this.testcaseRootDataModel = testcaseRootDataModel;
		this.specialRootDataModel = specialRootDataModel;
		this.paramRootDataModel = paramRootDataModel;
		this.driverRootDataModel = driverRootDataModel;
		
	}

	@Override
	public ImageIcon getIcon(DataModelInterface actualNode, boolean expanded) {

//		ImageIcon closeIcon = CommonOperations.createImageIcon("tree/testcase-close-icon.png");
//		ImageIcon openIcon = CommonOperations.createImageIcon("tree/testcase-open-icon.png");
		ImageIcon customIcon = CommonOperations.createImageIcon("tree/testcase-custom-icon.png");
    	ImageIcon pageIcon = CommonOperations.createImageIcon("tree/testcase-page-icon.png");
    	ImageIcon caseIcon = CommonOperations.createImageIcon("tree/testcase-case-icon.png");
    	ImageIcon nodeClosedIcon = CommonOperations.createImageIcon("tree/testcase-node-closed-icon.png");
    	ImageIcon nodeOpenIcon = CommonOperations.createImageIcon("tree/testcase-node-open-icon.png");
    	
    	//Iconja a NODE-nak
/*    	if( actualNode instanceof TestcaseSpecialDataModel){
    		if( ((TestcaseSpecialDataModel)actualNode).getSpecialPage() instanceof SpecialOpenDataModel ){
    			return openIcon;
    		}else if( ((TestcaseSpecialDataModel)actualNode).getSpecialPage() instanceof SpecialCloseDataModel ){
    			return closeIcon;
    		} 
    		
            return null;
*/   	
    	if( actualNode instanceof TestcaseCaseDataModel){
            return caseIcon;
    	}else if( actualNode instanceof TestcaseParamPageDataModel ){
            return pageIcon;
    	}else if( actualNode instanceof TestcaseCustomDataModel ){
            return customIcon;            
    	}else if( actualNode instanceof TestcaseNodeDataModel){
    		if( expanded ){
    			return nodeOpenIcon;
    		}else{
    			return nodeClosedIcon;
    		}
        }
    	
    	//TODO ide jon meg a custom page is
    	//else if( actualNode instanceof TestcaseCustomDataModel)
    	
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
			TestcaseCaseEditor testcaseCaseEditor = new TestcaseCaseEditor( this, (TestcaseCaseDataModel)selectedNode, specialRootDataModel, driverRootDataModel, EditMode.VIEW );								
			guiFrame.showEditorPanel( testcaseCaseEditor);				
							
		}else if( selectedNode instanceof TestcaseParamPageDataModel ){
			TestcaseParamPageEditor testcasePageEditor = new TestcaseParamPageEditor( this, (TestcaseParamPageDataModel)selectedNode, paramRootDataModel, EditMode.VIEW );	
			guiFrame.showEditorPanel( testcasePageEditor);									
			
		}else if( selectedNode instanceof TestcaseCustomDataModel ){
			TestcaseCustomPageEditor testcaseCustomEditor = new TestcaseCustomPageEditor( this, (TestcaseCustomDataModel)selectedNode, specialRootDataModel, EditMode.VIEW );	
			guiFrame.showEditorPanel( testcaseCustomEditor);
			
		}
	}

	@Override
	public void doModifyWithPopupEdit(DataModelInterface selectedNode) {
		
		if( selectedNode instanceof TestcaseNodeDataModel ){
			
			TestcaseNodeEditor testcaseNodeEditor = new TestcaseNodeEditor( this, (TestcaseNodeDataModel)selectedNode, EditMode.MODIFY );								
			guiFrame.showEditorPanel( testcaseNodeEditor);								
				
		}else if( selectedNode instanceof TestcaseCaseDataModel ){
			
			TestcaseCaseEditor testcaseCaseEditor = new TestcaseCaseEditor( this, (TestcaseCaseDataModel)selectedNode, specialRootDataModel, driverRootDataModel, EditMode.MODIFY );							                                            
			guiFrame.showEditorPanel( testcaseCaseEditor);		
				
		}else if( selectedNode instanceof TestcaseParamPageDataModel ){

			TestcaseParamPageEditor testcasePageEditor = new TestcaseParamPageEditor( this, (TestcaseParamPageDataModel)selectedNode, paramRootDataModel, EditMode.MODIFY );
			guiFrame.showEditorPanel( testcasePageEditor);		
				
		}else if( selectedNode instanceof TestcaseCustomDataModel ){
			TestcaseCustomPageEditor testcaseCustomEditor = new TestcaseCustomPageEditor( this, (TestcaseCustomDataModel)selectedNode, specialRootDataModel, EditMode.MODIFY );	
			guiFrame.showEditorPanel( testcaseCustomEditor);
			
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
			JMenuItem insertPageMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.testcase.case") );
			insertPageMenu.setActionCommand( ActionCommand.CAPTURE.name());
			insertPageMenu.addActionListener( new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					
					TestcaseCaseEditor testcaseCaseEditor = new TestcaseCaseEditor( TestcaseTree.this, (TestcaseNodeDataModel)selectedNode, specialRootDataModel, driverRootDataModel );								
					guiFrame.showEditorPanel( testcaseCaseEditor);								
				
				}
			});
			popupMenu.add ( insertPageMenu );
			
		}		
		
		//
		// Case eseten
		//
		if( selectedNode instanceof TestcaseCaseDataModel ){

			//Insert Page
			JMenuItem insertElementMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.testcase.parampage") );
			insertElementMenu.setActionCommand( ActionCommand.CAPTURE.name());
			insertElementMenu.addActionListener( new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					
					TestcaseParamPageEditor testcasePageEditor = new TestcaseParamPageEditor( TestcaseTree.this, (TestcaseCaseDataModel)selectedNode, paramRootDataModel );								
					guiFrame.showEditorPanel( testcasePageEditor);								
				
				}
			});
			popupMenu.add ( insertElementMenu );
		
			//Insert Custom
			JMenuItem insertSpecialMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.testcase.custompage") );
			insertSpecialMenu.setActionCommand( ActionCommand.CAPTURE.name());
			insertSpecialMenu.addActionListener( new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					
					TestcaseCustomPageEditor testcaseCustomEditor = new TestcaseCustomPageEditor( TestcaseTree.this, (TestcaseCaseDataModel)selectedNode, specialRootDataModel );
					guiFrame.showEditorPanel( testcaseCustomEditor);								
				
				}
			});
			popupMenu.add ( insertSpecialMenu );
		
			
			
			
//Insert Run
JMenuItem insertRun = new JMenuItem("Run"); //JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.testcase.custompage") );
insertRun.setActionCommand( ActionCommand.CAPTURE.name());
insertRun.addActionListener( new ActionListener() {
			
	@Override
	public void actionPerformed(ActionEvent e) {
		
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		    	((TestcaseCaseDataModel)selectedNode).doAction();     
		    }
		});
		System.err.println("tovabbment");
	}
});
popupMenu.add ( insertRun );
			
				

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
