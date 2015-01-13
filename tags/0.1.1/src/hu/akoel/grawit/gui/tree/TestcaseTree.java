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
import hu.akoel.grawit.core.treenodedatamodel.TestcaseDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.driver.DriverRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamLoopCollectorDataModel;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamNormalCollectorDataModel;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseCaseDataModel;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseFolderDataModel;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseParamContainerDataModel;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseRootDataModel;
import hu.akoel.grawit.enums.ActionCommand;
import hu.akoel.grawit.gui.GUIFrame;
import hu.akoel.grawit.gui.editor.DataEditor.EditMode;
import hu.akoel.grawit.gui.editor.testcase.TestcaseCaseEditor;
import hu.akoel.grawit.gui.editor.testcase.TestcaseFolderEditor;
import hu.akoel.grawit.gui.editor.testcase.TestcaseParamCollectorEditor;
import hu.akoel.grawit.gui.editor.testcase.TestcaseRootEditor;

public class TestcaseTree extends Tree {

	private static final long serialVersionUID = -7537783206534337777L;
	private GUIFrame guiFrame;
	
//	private BaseRootDataModel baseRootDataModel;
	private ParamRootDataModel paramRootDataModel;
	private DriverRootDataModel driverRootDataModel;
	
	public TestcaseTree(GUIFrame guiFrame, BaseRootDataModel baseRootDataModel, ParamRootDataModel paramRootDataModel, DriverRootDataModel driverRootDataModel, TestcaseRootDataModel testcaseRootDataModel ) {	
		super(guiFrame, testcaseRootDataModel);
		
		this.guiFrame = guiFrame;
//		this.baseRootDataModel = baseRootDataModel;
		this.paramRootDataModel = paramRootDataModel;
		this.driverRootDataModel = driverRootDataModel;
		
		this.enablePopupModifyAtRoot();
		
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
            
    	}else if( actualNode instanceof TestcaseParamContainerDataModel ){
    		
    		TestcaseParamContainerDataModel testCasePage = (TestcaseParamContainerDataModel)actualNode;
    	    if( testCasePage.getParamPage() instanceof ParamNormalCollectorDataModel ){
    	    	return paramContainer;	
    	    }else if( testCasePage.getParamPage() instanceof ParamLoopCollectorDataModel ){
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

    	}else if( actualNode instanceof TestcaseParamContainerDataModel ){
    		
    		TestcaseParamContainerDataModel testCasePage = (TestcaseParamContainerDataModel)actualNode;
    	    if( testCasePage.getParamPage() instanceof ParamNormalCollectorDataModel ){
    	    	return containerOffIcon;	
    	    }else if( testCasePage.getParamPage() instanceof ParamLoopCollectorDataModel ){
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

		}else if( selectedNode instanceof TestcaseParamContainerDataModel ){
			TestcaseParamCollectorEditor testcaseParamPageEditor = new TestcaseParamCollectorEditor( this, (TestcaseParamContainerDataModel)selectedNode, paramRootDataModel, EditMode.VIEW );	
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

		}else if( selectedNode instanceof TestcaseParamContainerDataModel ){

			TestcaseParamCollectorEditor testcaseParamPageEditor = new TestcaseParamCollectorEditor( this, (TestcaseParamContainerDataModel)selectedNode, paramRootDataModel, EditMode.MODIFY );
			guiFrame.showEditorPanel( testcaseParamPageEditor);		
			
		}else if( selectedNode instanceof TestcaseFolderDataModel ){
			
			TestcaseFolderEditor testcaseNodeEditor = new TestcaseFolderEditor( this, (TestcaseFolderDataModel)selectedNode, EditMode.MODIFY );								
			guiFrame.showEditorPanel( testcaseNodeEditor);								
				
		}else if( selectedNode instanceof TestcaseCaseDataModel ){
			
			TestcaseCaseEditor testcaseCaseEditor = new TestcaseCaseEditor( this, (TestcaseCaseDataModel)selectedNode, driverRootDataModel, EditMode.MODIFY );							                                            
			guiFrame.showEditorPanel( testcaseCaseEditor);		

		}		
	}

	@Override
	public void doPopupInsert( JPopupMenu popupMenu, final DataModelAdapter selectedNode) {
		
		//
		// Case eseten
		//
		if( selectedNode instanceof TestcaseCaseDataModel ){

			//Insert Page
			JMenuItem insertParamPageMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.testcase.paramcollector") );
			insertParamPageMenu.setActionCommand( ActionCommand.CAPTURE.name());
			insertParamPageMenu.addActionListener( new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					
					TestcaseParamCollectorEditor testcaseParamPageEditor = new TestcaseParamCollectorEditor( TestcaseTree.this, (TestcaseCaseDataModel)selectedNode, paramRootDataModel );								
					guiFrame.showEditorPanel( testcaseParamPageEditor);								
				
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
		
		//Insert Folder
		JMenuItem insertNodeMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.folder") );
		insertNodeMenu.setActionCommand( ActionCommand.CAPTURE.name());
		insertNodeMenu.addActionListener( new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent e) {
				
				TestcaseFolderEditor paramNodeEditor = new TestcaseFolderEditor( TestcaseTree.this, (TestcaseFolderDataModel)selectedNode );								
				guiFrame.showEditorPanel( paramNodeEditor);								
			
			}
		});
		popupMenu.add ( insertNodeMenu );
		
	}

	@Override
	public boolean possibleHierarchy(DefaultMutableTreeNode draggedNode, Object dropObject) {
		
		//Node elhelyezese Node-ba vagy Root-ba
		if( draggedNode instanceof TestcaseFolderDataModel && dropObject instanceof TestcaseFolderDataModel ){
			return true;
		
		//Case elhelyezese Node-ba, de nem Root-ba
		}else if( draggedNode instanceof TestcaseCaseDataModel && dropObject instanceof TestcaseFolderDataModel && !( dropObject instanceof TestcaseRootDataModel ) ){
			return true;
		
		//Page elhelyezese Case-ben
		}else if( draggedNode instanceof TestcaseParamContainerDataModel && dropObject instanceof TestcaseCaseDataModel ){
			return true;

		}
		
		return false;
	}


}
