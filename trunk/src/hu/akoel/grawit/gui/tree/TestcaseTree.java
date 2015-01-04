package hu.akoel.grawit.gui.tree;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.DataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.DriverDataModelInterface;
import hu.akoel.grawit.core.treenodedatamodel.TestcaseDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.driver.DriverRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseCaseDataModel;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseControlLoopDataModel;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseNodeDataModel;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseParamPageDataModel;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseRootDataModel;
import hu.akoel.grawit.enums.ActionCommand;
import hu.akoel.grawit.gui.GUIFrame;
import hu.akoel.grawit.gui.editor.DataEditor.EditMode;
import hu.akoel.grawit.gui.editor.testcase.TestcaseCaseEditor;
import hu.akoel.grawit.gui.editor.testcase.TestcaseControlAdapter;
import hu.akoel.grawit.gui.editor.testcase.TestcaseControlLoopEditor;
import hu.akoel.grawit.gui.editor.testcase.TestcaseNodeEditor;
import hu.akoel.grawit.gui.editor.testcase.TestcaseParamPageEditor;
import hu.akoel.grawit.gui.editor.testcase.TestcaseRootEditor;

public class TestcaseTree extends Tree {

	private static final long serialVersionUID = -7537783206534337777L;
	private GUIFrame guiFrame;
	
	private BaseRootDataModel baseRootDataModel;
	private ParamRootDataModel paramRootDataModel;
	private DriverRootDataModel driverRootDataModel;
	
	public TestcaseTree(GUIFrame guiFrame, BaseRootDataModel baseRootDataModel, ParamRootDataModel paramRootDataModel, DriverRootDataModel driverRootDataModel, TestcaseRootDataModel testcaseRootDataModel ) {	
		super(guiFrame, testcaseRootDataModel);
		
		this.guiFrame = guiFrame;
		this.baseRootDataModel = baseRootDataModel;
		this.paramRootDataModel = paramRootDataModel;
		this.driverRootDataModel = driverRootDataModel;
		
		this.enablePopupModifyAtRoot();
		
	}

	@Override
	public ImageIcon getIcon(DataModelAdapter actualNode, boolean expanded) {

    	ImageIcon pageIcon = CommonOperations.createImageIcon("tree/testcase-page-specific-icon.png");
    	ImageIcon caseIcon = CommonOperations.createImageIcon("tree/testcase-case-icon.png");
    	ImageIcon loopOpenIcon = CommonOperations.createImageIcon("tree/testcase-control-loop-open-icon.png");
    	ImageIcon loopClosedIcon = CommonOperations.createImageIcon("tree/testcase-control-loop-open-icon.png");
    	ImageIcon nodeClosedIcon = CommonOperations.createImageIcon("tree/testcase-node-closed-icon.png");
    	ImageIcon nodeOpenIcon = CommonOperations.createImageIcon("tree/testcase-node-open-icon.png");
    	
    	//Iconja a NODE-nak
    	if( actualNode instanceof TestcaseCaseDataModel){
            return caseIcon;
            
    	}else if( actualNode instanceof TestcaseParamPageDataModel ){    		   		
    		return pageIcon;
    		
    	}else if( actualNode instanceof TestcaseNodeDataModel){
    		if( expanded ){
    			return nodeOpenIcon;
    		}else{
    			return nodeClosedIcon;
    		}
    		
    	}else if( actualNode instanceof TestcaseControlLoopDataModel ){

    		if( expanded ){
    			return loopOpenIcon;
    		}else{
    			return loopClosedIcon;
    			
    		}
    		
        }
  	
		return null;
	}
	
	@Override
	public ImageIcon getIconOff(DataModelAdapter actualNode, boolean expanded) {

    	ImageIcon pageIcon = CommonOperations.createImageIcon("tree/testcase-page-off-icon.png");
    	ImageIcon caseIcon = CommonOperations.createImageIcon("tree/testcase-case-off-icon.png");
    	
    	if( actualNode instanceof TestcaseCaseDataModel){
            return caseIcon;
    	}else if( actualNode instanceof TestcaseParamPageDataModel ){
            return pageIcon;
    	}else{
    		return getIcon(actualNode, expanded);
        }
	}

	@Override
	public void doViewWhenSelectionChanged(DataModelAdapter selectedNode) {
		
		//Ha a root-ot valasztottam
		if( selectedNode instanceof TestcaseRootDataModel ){
			TestcaseRootEditor testcaseRootEditor = new TestcaseRootEditor( this, (TestcaseRootDataModel)selectedNode, driverRootDataModel, EditMode.VIEW);
			guiFrame.showEditorPanel( testcaseRootEditor);
			//EmptyEditor emptyPanel = new EmptyEditor();								
			//guiFrame.showEditorPanel( emptyPanel );
			
		}else if( selectedNode instanceof TestcaseNodeDataModel ){
			TestcaseNodeEditor testcaseNodeEditor = new TestcaseNodeEditor( this, (TestcaseNodeDataModel)selectedNode, EditMode.VIEW);
			guiFrame.showEditorPanel( testcaseNodeEditor);								
		
		}else if( selectedNode instanceof TestcaseCaseDataModel ){
			TestcaseCaseEditor testcaseCaseEditor = new TestcaseCaseEditor( this, (TestcaseCaseDataModel)selectedNode, driverRootDataModel, EditMode.VIEW );								
			guiFrame.showEditorPanel( testcaseCaseEditor);				
							
		}else if( selectedNode instanceof TestcaseParamPageDataModel ){
			TestcaseParamPageEditor testcaseParamPageEditor = new TestcaseParamPageEditor( this, (TestcaseParamPageDataModel)selectedNode, paramRootDataModel, EditMode.VIEW );	
			guiFrame.showEditorPanel( testcaseParamPageEditor);									
			
		}else if( selectedNode instanceof TestcaseControlLoopDataModel ){
			TestcaseControlLoopEditor testcaseControlLoopEditor = new TestcaseControlLoopEditor( this, (TestcaseControlLoopDataModel)selectedNode, baseRootDataModel, EditMode.VIEW );
			guiFrame.showEditorPanel( testcaseControlLoopEditor);									
			
		}
	}

	@Override
	public void doModifyWithPopupEdit(DataModelAdapter selectedNode) {
		
		//Ha a root-ot valasztottam
		if( selectedNode instanceof TestcaseRootDataModel ){
			
			TestcaseRootEditor testcaseRootNodeEditor = new TestcaseRootEditor( this, (TestcaseRootDataModel)selectedNode, driverRootDataModel, EditMode.MODIFY);
			guiFrame.showEditorPanel( testcaseRootNodeEditor);
					
		}else if( selectedNode instanceof TestcaseNodeDataModel ){
			
			TestcaseNodeEditor testcaseNodeEditor = new TestcaseNodeEditor( this, (TestcaseNodeDataModel)selectedNode, EditMode.MODIFY );								
			guiFrame.showEditorPanel( testcaseNodeEditor);								
				
		}else if( selectedNode instanceof TestcaseCaseDataModel ){
			
			TestcaseCaseEditor testcaseCaseEditor = new TestcaseCaseEditor( this, (TestcaseCaseDataModel)selectedNode, driverRootDataModel, EditMode.MODIFY );							                                            
			guiFrame.showEditorPanel( testcaseCaseEditor);		
				
		}else if( selectedNode instanceof TestcaseParamPageDataModel ){

			TestcaseParamPageEditor testcaseParamPageEditor = new TestcaseParamPageEditor( this, (TestcaseParamPageDataModel)selectedNode, paramRootDataModel, EditMode.MODIFY );
			guiFrame.showEditorPanel( testcaseParamPageEditor);		
			
		}else if( selectedNode instanceof TestcaseControlLoopDataModel ){
			TestcaseControlLoopEditor testcaseControlLoopEditor = new TestcaseControlLoopEditor( this, (TestcaseControlLoopDataModel)selectedNode, baseRootDataModel, EditMode.MODIFY );
			guiFrame.showEditorPanel( testcaseControlLoopEditor);									
			
		}		
	}

	@Override
	public void doPopupInsert( JPopupMenu popupMenu, final DataModelAdapter selectedNode) {
		
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
			JMenuItem insertParamPageMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.testcase.case") );
			insertParamPageMenu.setActionCommand( ActionCommand.CAPTURE.name());
			insertParamPageMenu.addActionListener( new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					
					TestcaseCaseEditor testcaseCaseEditor = new TestcaseCaseEditor( TestcaseTree.this, (TestcaseNodeDataModel)selectedNode, driverRootDataModel );								
					guiFrame.showEditorPanel( testcaseCaseEditor);								
				
				}
			});
			popupMenu.add ( insertParamPageMenu );
			
		}		
		
		//
		// Case eseten
		//
		if( selectedNode instanceof TestcaseCaseDataModel ){

			//Insert Page
			JMenuItem insertParamPageMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.testcase.parampage") );
			insertParamPageMenu.setActionCommand( ActionCommand.CAPTURE.name());
			insertParamPageMenu.addActionListener( new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					
					TestcaseParamPageEditor testcaseParamPageEditor = new TestcaseParamPageEditor( TestcaseTree.this, (TestcaseCaseDataModel)selectedNode, paramRootDataModel );								
					guiFrame.showEditorPanel( testcaseParamPageEditor);								
				
				}
			});
			popupMenu.add ( insertParamPageMenu );
		
			//Insert Control Loop
			JMenuItem insertLoopMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.testcase.control.loop") );
			insertLoopMenu.setActionCommand( ActionCommand.CAPTURE.name());
			insertLoopMenu.addActionListener( new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					
					TestcaseControlLoopEditor testcaseControlLoopEditor = new TestcaseControlLoopEditor( TestcaseTree.this, (TestcaseCaseDataModel)selectedNode, baseRootDataModel );
					guiFrame.showEditorPanel( testcaseControlLoopEditor);			
					
				
				}
			});
			popupMenu.add ( insertLoopMenu );
			
/*			//Insert Custom
			JMenuItem insertSpecialMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.testcase.custompage") );
			insertSpecialMenu.setActionCommand( ActionCommand.CAPTURE.name());
			insertSpecialMenu.addActionListener( new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					
					TestcaseCustomPageEditor testcaseCustomPageEditor = new TestcaseCustomPageEditor( TestcaseTree.this, (TestcaseCaseDataModel)selectedNode, specialRootDataModel );
					guiFrame.showEditorPanel( testcaseCustomPageEditor);								
				
				}
			});
			popupMenu.add ( insertSpecialMenu );			
*/
			
		}
		
		//
		// Control LOOP eseten
		//
		if( selectedNode instanceof TestcaseControlLoopDataModel ){

			//Insert Page
			JMenuItem insertParamPageMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.testcase.parampage") );
			insertParamPageMenu.setActionCommand( ActionCommand.CAPTURE.name());
			insertParamPageMenu.addActionListener( new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					
					TestcaseParamPageEditor testcaseParamPageEditor = new TestcaseParamPageEditor( TestcaseTree.this, (TestcaseControlLoopDataModel)selectedNode, paramRootDataModel );								
					guiFrame.showEditorPanel( testcaseParamPageEditor);								
				
				}
			});
			popupMenu.add ( insertParamPageMenu );
		}
		
	}

	@Override
	public void doDuplicate( final JPopupMenu popupMenu, final DataModelAdapter selectedNode, final int selectedRow, final DefaultTreeModel totalTreeModel) {
		
		JMenuItem duplicateMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.duplicate") );
		duplicateMenu.setActionCommand( ActionCommand.DUPLICATE.name());
		duplicateMenu.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				//Ha a kivalasztott csomopont szuloje BaseDataModel - annak kell lennie :)
				if( selectedNode.getParent() instanceof TestcaseDataModelAdapter ){
					
					//Akkor megduplikalja 
					TestcaseDataModelAdapter duplicated = (TestcaseDataModelAdapter)selectedNode.clone();
					
					//Es hozzaadja a szulohoz
					((TestcaseDataModelAdapter)selectedNode.getParent()).add( duplicated );

					//Felfrissitem a Tree-t
					TestcaseTree.this.changed();
				
				}

			}
		});
		popupMenu.add ( duplicateMenu );
	}
	
	@Override
	public void doPopupDelete( final JPopupMenu popupMenu, final DataModelAdapter selectedNode, final int selectedRow, final DefaultTreeModel totalTreeModel) {
	
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
							MessageFormat.format( 
									CommonOperations.getTranslation("mesage.question.delete.treeelement"), 
									selectedNode.getNodeTypeToShow(),
									selectedNode.getName()
							),							
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
	public void doPopupRootInsert( JPopupMenu popupMenu, final DataModelAdapter selectedNode ) {
		
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

	@Override
	public boolean possibleHierarchy(DefaultMutableTreeNode draggedNode, Object dropObject) {
		
		//Node elhelyezese Node-ba vagy Root-ba
		if( draggedNode instanceof TestcaseNodeDataModel && dropObject instanceof TestcaseNodeDataModel ){
			return true;
		
		//Case elhelyezese Node-ba, de nem Root-ba
		}else if( draggedNode instanceof TestcaseCaseDataModel && dropObject instanceof TestcaseNodeDataModel && !( dropObject instanceof TestcaseRootDataModel ) ){
			return true;
		
		//Page elhelyezese Case-ben
		}else if( draggedNode instanceof TestcaseParamPageDataModel && dropObject instanceof TestcaseCaseDataModel ){
			return true;
			
		//Loop elhelyezese Case-ben
		}else if( draggedNode instanceof TestcaseControlLoopDataModel && dropObject instanceof TestcaseCaseDataModel ){
			return true;

		//Page elhelyezese Loop-ban
		}else if( draggedNode instanceof TestcaseParamPageDataModel && dropObject instanceof TestcaseControlLoopDataModel ){
			return true;

		}
		
		return false;
	}


}
