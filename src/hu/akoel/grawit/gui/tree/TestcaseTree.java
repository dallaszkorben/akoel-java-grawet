package hu.akoel.grawit.gui.tree;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.text.MessageFormat;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.DataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.driver.DriverRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.step.StepLoopCollectorDataModel;
import hu.akoel.grawit.core.treenodedatamodel.step.StepNormalCollectorDataModel;
import hu.akoel.grawit.core.treenodedatamodel.step.StepRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseCaseDataModel;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseFolderDataModel;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseNodeDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseStepCollectorDataModel;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseRootDataModel;
import hu.akoel.grawit.enums.ActionCommand;
import hu.akoel.grawit.gui.GUIFrame;
import hu.akoel.grawit.gui.editor.DataEditor.EditMode;
import hu.akoel.grawit.gui.editor.testcase.TestcaseCaseEditor;
import hu.akoel.grawit.gui.editor.testcase.TestcaseFolderEditor;
import hu.akoel.grawit.gui.editor.testcase.TestcaseStepCollectorEditor;
import hu.akoel.grawit.gui.editor.testcase.TestcaseRootEditor;

public class TestcaseTree extends Tree {

	private static final long serialVersionUID = -7537783206579337777L;
	private GUIFrame guiFrame;
	
	private StepRootDataModel paramRootDataModel;
	private DriverRootDataModel driverRootDataModel;
	private TestcaseRootDataModel testcaseRootDataModel;
	
	public TestcaseTree(  String functionName, GUIFrame guiFrame, BaseRootDataModel baseRootDataModel, StepRootDataModel stepRootDataModel, DriverRootDataModel driverRootDataModel, TestcaseRootDataModel testcaseRootDataModel ) {	
		super( functionName, guiFrame, testcaseRootDataModel );
		
		this.guiFrame = guiFrame;
		this.paramRootDataModel = stepRootDataModel;
		this.driverRootDataModel = driverRootDataModel;
		this.testcaseRootDataModel = testcaseRootDataModel;
		
		this.enablePopupModifyAtRoot();

	}

	@Override
	public void refreshTreeAfterStructureChanged( DataModelAdapter nodeToSelect, DataModelAdapter parentNode ){
		
		super.refreshTreeAfterStructureChanged(nodeToSelect, parentNode);

		//Itt jelzek a TestCaseDataModelListener-nek, hogy valtozas volt.
		//Ezt figyelem a RunTree-ben, es ha elkaptam, akkor
/*		ArrayList<TestcaseDataModelListener> listeners = testcaseRootDataModel.getDataModelListener();
		for( TestcaseDataModelListener listener: listeners ){
			listener.structureChanged(nodeToSelect, parentNode);
		}
*/		
	}

	@Override
	public ImageIcon getIcon(DataModelAdapter actualNode, boolean expanded) {

    	ImageIcon folderClosedIcon = CommonOperations.createImageIcon("tree/testcase-folder-closed-icon.png");
    	ImageIcon folderOpenIcon = CommonOperations.createImageIcon("tree/testcase-folder-open-icon.png");
    	ImageIcon paramContainer = CommonOperations.createImageIcon("tree/testcase-container-icon.png");
    	ImageIcon caseIcon = CommonOperations.createImageIcon("tree/testcase-case-icon.png");
    	ImageIcon loopOpenIcon = CommonOperations.createImageIcon("tree/testcase-loop-icon.png");
    	ImageIcon rootIcon = CommonOperations.createImageIcon("tree/root-icon.png");
    	
    	//Iconja a NODE-nak
    	if( actualNode instanceof TestcaseRootDataModel){
            return rootIcon;
            
    	}else if( actualNode instanceof TestcaseCaseDataModel){
            return caseIcon;
            
    	}else if( actualNode instanceof TestcaseStepCollectorDataModel ){
    		
    		TestcaseStepCollectorDataModel testCasePage = (TestcaseStepCollectorDataModel)actualNode;
    	    if( testCasePage.getParamPage() instanceof StepNormalCollectorDataModel ){
    	    	return paramContainer;	
    	    }else if( testCasePage.getParamPage() instanceof StepLoopCollectorDataModel ){
    	    	return loopOpenIcon;
    	    }
    		
    	}else if( actualNode instanceof TestcaseFolderDataModel){
    		if( expanded ){
    			return folderOpenIcon;
    		}else{
    			return folderClosedIcon;
    		}
    		
        }
  	
		return null;
	}
	
	@Override
	public ImageIcon getIconOff(DataModelAdapter actualNode, boolean expanded) {

    	ImageIcon containerOffIcon = CommonOperations.createImageIcon("tree/testcase-container-off-icon.png");
    	ImageIcon loopOffIcon = CommonOperations.createImageIcon("tree/testcase-loop-off-icon.png");
    	ImageIcon caseOffIcon = CommonOperations.createImageIcon("tree/testcase-case-off-icon.png");

    	
    	if( actualNode instanceof TestcaseCaseDataModel){
            return caseOffIcon;

    	}else if( actualNode instanceof TestcaseStepCollectorDataModel ){
    		
    		TestcaseStepCollectorDataModel testCasePage = (TestcaseStepCollectorDataModel)actualNode;
    	    if( testCasePage.getParamPage() instanceof StepNormalCollectorDataModel ){
    	    	return containerOffIcon;	
    	    }else if( testCasePage.getParamPage() instanceof StepLoopCollectorDataModel ){
    	    	return loopOffIcon;
    	    }
    	    return containerOffIcon;
    	    
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

		}else if( selectedNode instanceof TestcaseStepCollectorDataModel ){
			TestcaseStepCollectorEditor testcaseParamPageEditor = new TestcaseStepCollectorEditor( this, (TestcaseStepCollectorDataModel)selectedNode, paramRootDataModel, EditMode.VIEW );	
			guiFrame.showEditorPanel( testcaseParamPageEditor);				
			
		}else if( selectedNode instanceof TestcaseFolderDataModel ){
			TestcaseFolderEditor testcaseNodeEditor = new TestcaseFolderEditor( this, (TestcaseFolderDataModel)selectedNode, EditMode.VIEW);
			guiFrame.showEditorPanel( testcaseNodeEditor);								
		
		}else if( selectedNode instanceof TestcaseCaseDataModel ){
			TestcaseCaseEditor testcaseCaseEditor = new TestcaseCaseEditor( this, (TestcaseCaseDataModel)selectedNode, driverRootDataModel, EditMode.VIEW );								
			guiFrame.showEditorPanel( testcaseCaseEditor);				
			
		}
	}

	@Override
	public void doModifyWithPopupEdit(DataModelAdapter selectedNode) {
		
		//Ha a root-ot valasztottam
		if( selectedNode instanceof TestcaseRootDataModel ){
			
			TestcaseRootEditor testcaseRootNodeEditor = new TestcaseRootEditor( this, (TestcaseRootDataModel)selectedNode, driverRootDataModel, EditMode.MODIFY);
			guiFrame.showEditorPanel( testcaseRootNodeEditor);

		}else if( selectedNode instanceof TestcaseStepCollectorDataModel ){

			TestcaseStepCollectorEditor testcaseParamPageEditor = new TestcaseStepCollectorEditor( this, (TestcaseStepCollectorDataModel)selectedNode, paramRootDataModel, EditMode.MODIFY );
			guiFrame.showEditorPanel( testcaseParamPageEditor);		
			
		}else if( selectedNode instanceof TestcaseFolderDataModel ){
			
			TestcaseFolderEditor testcaseNodeEditor = new TestcaseFolderEditor( this, (TestcaseFolderDataModel)selectedNode, EditMode.MODIFY );								
			guiFrame.showEditorPanel( testcaseNodeEditor);								
				
		}else if( selectedNode instanceof TestcaseCaseDataModel ){
			
			TestcaseCaseEditor testcaseCaseEditor = new TestcaseCaseEditor( this, (TestcaseCaseDataModel)selectedNode, driverRootDataModel, EditMode.MODIFY );							                                            
			guiFrame.showEditorPanel( testcaseCaseEditor);		

		}	
		
TestcaseTree.this.treeHasChanged();
	}

	@Override
	public void doPopupInsert( JPopupMenu popupMenu, final DataModelAdapter selectedNode) {
		
		//
		// Case eseten
		//
		if( selectedNode instanceof TestcaseCaseDataModel ){

			//Insert Page
			JMenuItem insertParamPageMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.testcase.stepcollector") );
			insertParamPageMenu.setActionCommand( ActionCommand.CAPTURE.name());
			insertParamPageMenu.addActionListener( new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					
					TestcaseStepCollectorEditor testcaseStepCollectorEditor = new TestcaseStepCollectorEditor( TestcaseTree.this, (TestcaseCaseDataModel)selectedNode, paramRootDataModel );								
					guiFrame.showEditorPanel( testcaseStepCollectorEditor);	
TestcaseTree.this.treeHasChanged();					
				
				}
			});
			popupMenu.add ( insertParamPageMenu );
		
		//
		// Csomopont eseten
		//
		}else if( selectedNode instanceof TestcaseFolderDataModel ){

			//Insert Node  
			JMenuItem insertNodeMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.folder") );
			insertNodeMenu.setActionCommand( ActionCommand.CAPTURE.name());
			insertNodeMenu.addActionListener( new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					
					TestcaseFolderEditor paramNodeEditor = new TestcaseFolderEditor( TestcaseTree.this, (TestcaseFolderDataModel)selectedNode );								
					guiFrame.showEditorPanel( paramNodeEditor);	
TestcaseTree.this.treeHasChanged();					
				
				}
			});
			popupMenu.add ( insertNodeMenu );

			//Insert Case
			JMenuItem insertParamPageMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.testcase.case") );
			insertParamPageMenu.setActionCommand( ActionCommand.CAPTURE.name());
			insertParamPageMenu.addActionListener( new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					
					//TestcaseCaseEditor testcaseCaseEditor = new TestcaseCaseEditor( TestcaseTree.this, (TestcaseNodeDataModel)selectedNode, driverRootDataModel );								
					TestcaseCaseEditor testcaseCaseEditor = new TestcaseCaseEditor( TestcaseTree.this, (TestcaseFolderDataModel)selectedNode );
					guiFrame.showEditorPanel( testcaseCaseEditor);	
TestcaseTree.this.treeHasChanged();					
				
				}
			});
			popupMenu.add ( insertParamPageMenu );
			
		}		
		

		
	}

	@Override
	public void doPopupDuplicate( final JPopupMenu popupMenu, final DataModelAdapter selectedNode, final int selectedRow, final DefaultTreeModel totalTreeModel) {
		
		JMenuItem duplicateMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.duplicate") );
		duplicateMenu.setActionCommand( ActionCommand.DUPLICATE.name());
		duplicateMenu.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				//Ha a kivalasztott csomopont szuloje BaseDataModel - annak kell lennie :)
				if( selectedNode.getParent() instanceof TestcaseDataModelAdapter ){
					
					//Akkor megduplikalja 
					TestcaseDataModelAdapter duplicated = (TestcaseDataModelAdapter)selectedNode.clone();
					
					//!!! Ki kell torolni a szulot, hiszen a kovetkezo add() fuggveny fogja ezt neki adni !!!
					duplicated.setParent(null);

					//Es hozzaadja a szulohoz
					((TestcaseDataModelAdapter)selectedNode.getParent()).add( duplicated );

					//Felfrissitem a Tree-t
					TestcaseTree.this.refreshTreeAfterStructureChanged( (DataModelAdapter)selectedNode, (DataModelAdapter)selectedNode.getParent() );
					//TestcaseTree.this.nodeChanged();
TestcaseTree.this.treeHasChanged();				
				}

			}
		});
		popupMenu.add ( duplicateMenu );
	}
	
	@Override
	public void doPopupDelete( final JPopupMenu popupMenu, final DataModelAdapter selectedNode, final int selectedRow, final DefaultTreeModel totalTreeModel) {
	
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
					
				//Ha nincsenek gyermekei
				if( selectedNode.getChildCount() == 0 ){
					
					int n = JOptionPane.showOptionDialog(guiFrame,							
							MessageFormat.format( 
									CommonOperations.getTranslation("mesage.question.delete.treeelement.alone"), 
									selectedNode.getNodeTypeToShow(),
									selectedNode.getName()
									),							
									CommonOperations.getTranslation("editor.windowtitle.confirmation.delete"),
									JOptionPane.YES_NO_CANCEL_OPTION,
									JOptionPane.QUESTION_MESSAGE,
									null,
									options,
									options[0]);
					
					//A tenyleges torles
					if( n == 1 ){
						
						//Tulajdonkeppen csak levalasztom a fastrukturarol
						totalTreeModel.removeNodeFromParent( selectedNode);
						TestcaseTree.this.setSelectionRow(selectedRow - 1);
TestcaseTree.this.treeHasChanged();
					}
					
				//Ha vannak gyerekei
				}else{
					
					int n = JOptionPane.showOptionDialog(guiFrame,							
							MessageFormat.format( 
									CommonOperations.getTranslation("mesage.question.delete.treeelement.withchildren"), 
									selectedNode.getNodeTypeToShow(),
									selectedNode.getName()
									),							
									CommonOperations.getTranslation("editor.windowtitle.confirmation.delete"),
									JOptionPane.YES_NO_CANCEL_OPTION,
									JOptionPane.WARNING_MESSAGE,
									null,
									options,
									options[0]);					

					//A tenyleges torles
					if( n == 1 ){
						
						//Tulajdonkeppen csak levalasztom a fastrukturarol
						totalTreeModel.removeNodeFromParent( selectedNode);
						TestcaseTree.this.setSelectionRow(selectedRow - 1);
TestcaseTree.this.treeHasChanged();
					}
					
				}					
					
			}
		});
		popupMenu.add ( deleteMenu );
			
//		}		
	}

	@Override
	public void doPopupRootInsert( JPopupMenu popupMenu, final DataModelAdapter selectedNode ) {
		
		//Insert Folder
		JMenuItem insertNodeMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.folder") );
		insertNodeMenu.setActionCommand( ActionCommand.CAPTURE.name());
		insertNodeMenu.addActionListener( new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent e) {
				
				TestcaseFolderEditor paramNodeEditor = new TestcaseFolderEditor( TestcaseTree.this, (TestcaseNodeDataModelAdapter)selectedNode );								
				guiFrame.showEditorPanel( paramNodeEditor);								
			
			}
		});
		popupMenu.add ( insertNodeMenu );
		
	}

	@Override
	public boolean possibleHierarchy(DefaultMutableTreeNode draggedNode, Object targetObject) {
		
		if( draggedNode.equals( targetObject )){
			return false;
		
		//Node elhelyezese Node-ba vagy Root-ba
		}else if( draggedNode instanceof TestcaseFolderDataModel && targetObject instanceof TestcaseFolderDataModel ){
			return true;
		
		//Case elhelyezese Node-ba, de nem Root-ba
		}else if( draggedNode instanceof TestcaseCaseDataModel && targetObject instanceof TestcaseFolderDataModel && !( targetObject instanceof TestcaseRootDataModel ) ){
			return true;
		
		//Page elhelyezese Case-ben
		}else if( draggedNode instanceof TestcaseStepCollectorDataModel && targetObject instanceof TestcaseCaseDataModel ){
			return true;

		}
		
		return false;
	}

	@Override
	public void doPopupLink(JPopupMenu popupMenu, DataModelAdapter selectedNode) {
		// TODO Auto-generated method stub
		
	}


}
