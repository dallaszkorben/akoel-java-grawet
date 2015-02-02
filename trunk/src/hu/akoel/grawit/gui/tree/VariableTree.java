package hu.akoel.grawit.gui.tree;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.BaseDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.DataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.ParamDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.VariableDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseParamContainerDataModel;
import hu.akoel.grawit.core.treenodedatamodel.variable.VariableElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.variable.VariableFolderNodeDataModel;
import hu.akoel.grawit.core.treenodedatamodel.variable.VariableRootDataModel;
import hu.akoel.grawit.enums.ActionCommand;
import hu.akoel.grawit.gui.GUIFrame;
import hu.akoel.grawit.gui.editor.DataEditor;
import hu.akoel.grawit.gui.editor.DataEditor.EditMode;
import hu.akoel.grawit.gui.editor.variable.VariableElementEditor;
import hu.akoel.grawit.gui.editor.variable.VariableNodeEditor;
import hu.akoel.grawit.gui.editor.EmptyEditor;

public class VariableTree extends Tree{

	private static final long serialVersionUID = 6810815920672285062L;
	private GUIFrame guiFrame;
	
	public VariableTree(GUIFrame guiFrame, VariableRootDataModel variableRootDataModel) {
		super(guiFrame, variableRootDataModel);
		this.guiFrame = guiFrame;
	}

	@Override
	public ImageIcon getIcon(DataModelAdapter actualNode, boolean expanded) {

    	//ImageIcon pageIcon = CommonOperations.createImageIcon("tree/pagebase-page-icon.png");
    	ImageIcon elementIcon = CommonOperations.createImageIcon("tree/variable-element-icon.png");
    	ImageIcon nodeClosedIcon = CommonOperations.createImageIcon("tree/variable-node-closed-icon.png");
    	ImageIcon nodeOpenIcon = CommonOperations.createImageIcon("tree/variable-node-open-icon.png");
    	ImageIcon rootIcon = CommonOperations.createImageIcon("tree/root-icon.png");
  
    	//Iconja a NODE-nak
    	if( actualNode instanceof VariableRootDataModel){
            return rootIcon;
            
    	}else if( actualNode instanceof VariableFolderNodeDataModel){
    		if( expanded ){
    			return nodeOpenIcon;
    		}else{
    			return nodeClosedIcon;
    		}
        
    	}if( actualNode instanceof VariableElementDataModel ){
    		return elementIcon;
    	}
    	return null;
	}

	@Override
	public void doViewWhenSelectionChanged(DataModelAdapter selectedNode) {
	
		//Ha egyaltalan valamilyen egergombot benyomtam
		if( selectedNode instanceof VariableRootDataModel ){
			EmptyEditor emptyPanel = new EmptyEditor();								
			guiFrame.showEditorPanel( emptyPanel );
		
		}else if( selectedNode instanceof VariableFolderNodeDataModel ){
			VariableNodeEditor variableNodeEditor = new VariableNodeEditor(this, (VariableFolderNodeDataModel)selectedNode, EditMode.VIEW);
			guiFrame.showEditorPanel( variableNodeEditor);								
		
		}else if( selectedNode instanceof VariableElementDataModel ){
			VariableElementEditor variableElementEditor = new VariableElementEditor( this, (VariableElementDataModel)selectedNode, EditMode.VIEW );								
			guiFrame.showEditorPanel( variableElementEditor);		
								
		}		
	}

	@Override
	public void doModifyWithPopupEdit(DataModelAdapter selectedNode) {
		
		if( selectedNode instanceof VariableFolderNodeDataModel ){
			
			VariableNodeEditor variableNodeEditor = new VariableNodeEditor( VariableTree.this, (VariableFolderNodeDataModel)selectedNode, DataEditor.EditMode.MODIFY );								
			guiFrame.showEditorPanel( variableNodeEditor);								
			
		}else if( selectedNode instanceof VariableElementDataModel ){
			
			VariableElementEditor variableElementEditor = new VariableElementEditor( VariableTree.this, (VariableElementDataModel)selectedNode, DataEditor.EditMode.MODIFY );								
			guiFrame.showEditorPanel( variableElementEditor);	
		}
	}

	@Override
	public void doPopupInsert( final JPopupMenu popupMenu, final DataModelAdapter selectedNode) {

		//
		// Csomopont eseten
		//
		if( selectedNode instanceof VariableFolderNodeDataModel ){

			//Insert Node
			JMenuItem insertNodeMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.folder") );
			insertNodeMenu.setActionCommand( ActionCommand.CAPTURE.name());
			insertNodeMenu.addActionListener( new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					
					VariableNodeEditor variableNodeEditor = new VariableNodeEditor( VariableTree.this, (VariableFolderNodeDataModel)selectedNode );								
					guiFrame.showEditorPanel( variableNodeEditor);								
				
				}
			});
			popupMenu.add ( insertNodeMenu );

			//Insert Element
			JMenuItem insertElementMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.variable.element") );
			insertElementMenu.setActionCommand( ActionCommand.CAPTURE.name());
			insertElementMenu.addActionListener( new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
							
					VariableElementEditor variableElementEditor = new VariableElementEditor( VariableTree.this, (VariableFolderNodeDataModel)selectedNode );								
					guiFrame.showEditorPanel( variableElementEditor);								
				
				}
			});
			popupMenu.add ( insertElementMenu );
			
		}	
	
		
	}

	@Override
	public void doDuplicate( final JPopupMenu popupMenu, final DataModelAdapter selectedNode, final int selectedRow, final DefaultTreeModel totalTreeModel) {
		
		JMenuItem duplicateMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.duplicate") );
		duplicateMenu.setActionCommand( ActionCommand.DUPLICATE.name());
		duplicateMenu.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				//Ha a kivalasztott csomopont szuloje VariableDataModel - annak kell lennie :)
				if( selectedNode.getParent() instanceof VariableDataModelAdapter ){
					
					//Akkor megduplikalja 
					VariableDataModelAdapter duplicated = (VariableDataModelAdapter)selectedNode.clone();
					
					//!!! Ki kell torolni a szulot, hiszen a kovetkezo add() fuggveny fogja ezt neki adni !!!
					duplicated.setParent(null);

					//Es hozzaadja a szulohoz
					((VariableDataModelAdapter)selectedNode.getParent()).add( duplicated );

					//Felfrissitem a Tree-t
					VariableTree.this.changed();
				
				}

			}
		});
		popupMenu.add ( duplicateMenu );
	}
	
	@Override
	public void doPopupDelete( final JPopupMenu popupMenu, final DataModelAdapter selectedNode, final int selectedRow, final DefaultTreeModel totalTreeModel ) {
	
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
					
/*				
				//Eloszor is vegig vizsgalom, hogy van-e hivatkozas a Variable-re vagy valamelyik gyermekere a Testcase-ben
				//Akkor megnezi, hogy van-e hivatkozas a tartalmazott elemekre a Testcase fastrukturaban
				ArrayList<ParamDataModelAdapter> foundParamNodeList = findAllBaseInParam( (BaseDataModelAdapter)selectedNode, paramRootDataModel, new ArrayList<ParamDataModelAdapter>() );

				
				
				
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
					
					if( n == 1 ){
						totalTreeModel.removeNodeFromParent( selectedNode);
						VariableTree.this.setSelectionRow(selectedRow - 1);
					}	
*/											
				}
			});
			popupMenu.add ( deleteMenu );
			

		
	}

	@Override
	public void doPopupRootInsert( JPopupMenu popupMenu, final DataModelAdapter selectedNode ) {

		//Insert Node
		JMenuItem insertNodeMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.folder") );
		insertNodeMenu.setActionCommand( ActionCommand.CAPTURE.name());
		insertNodeMenu.addActionListener( new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent e) {
				
				VariableNodeEditor variableNodeEditor = new VariableNodeEditor( VariableTree.this, (VariableFolderNodeDataModel)selectedNode );								
				guiFrame.showEditorPanel( variableNodeEditor);								
			
			}
		});
		popupMenu.add ( insertNodeMenu );
		
		
		//Insert Element
		JMenuItem insertElementMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.variable.element" ) );
		insertElementMenu.setActionCommand( ActionCommand.CAPTURE.name());
		insertElementMenu.addActionListener( new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent e) {
							
				VariableElementEditor variableElementEditor = new VariableElementEditor( VariableTree.this, (VariableFolderNodeDataModel)selectedNode );								
				guiFrame.showEditorPanel( variableElementEditor);								
			
			}
		});
		popupMenu.add ( insertElementMenu );
		
	}

	@Override
	public boolean possibleHierarchy(DefaultMutableTreeNode draggedNode, Object dropObject) {

		//Node elhelyezese Node-ba
		if( draggedNode instanceof VariableFolderNodeDataModel && dropObject instanceof VariableFolderNodeDataModel ){
			return true;
		
		//Element elhelyezese Node-ba
		}else if( draggedNode instanceof VariableElementDataModel && dropObject instanceof VariableFolderNodeDataModel ){
			return true;
		
		//Node elhelyezese Root-ba			
		}else if( draggedNode instanceof VariableFolderNodeDataModel && dropObject instanceof VariableRootDataModel ){
			return true;
		
		//Elem elhelyezese Root-ba	
		}else if( draggedNode instanceof VariableElementDataModel && dropObject instanceof VariableRootDataModel ){
			return true;
			
		}

		//Minden egyeb eset tilos
		return false;
	}

}





