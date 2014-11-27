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

import TODELETE.hu.akoel.grawit.core.treenodedatamodel.script.ScriptElementDataModel;
import TODELETE.hu.akoel.grawit.core.treenodedatamodel.script.ScriptNodeDataModel;
import TODELETE.hu.akoel.grawit.core.treenodedatamodel.script.ScriptRootDataModel;
import TODELETE.hu.akoel.grawit.gui.editor.script.ScriptElementEditor;
import TODELETE.hu.akoel.grawit.gui.editor.script.ScriptNodeEditor;
import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.DataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.ScriptDataModelAdapter;
import hu.akoel.grawit.enums.ActionCommand;
import hu.akoel.grawit.gui.GUIFrame;
import hu.akoel.grawit.gui.editor.DataEditor;
import hu.akoel.grawit.gui.editor.DataEditor.EditMode;
import hu.akoel.grawit.gui.editor.EmptyEditor;

public class ScriptTree extends Tree{

	private static final long serialVersionUID = 6810815920672285062L;
	private GUIFrame guiFrame;
	
	public ScriptTree(GUIFrame guiFrame, ScriptRootDataModel scriptRootDataModel) {
		super(guiFrame, scriptRootDataModel);
		this.guiFrame = guiFrame;
	}

	@Override
	public ImageIcon getIcon(DataModelAdapter actualNode, boolean expanded) {

    	ImageIcon elementIcon = CommonOperations.createImageIcon("tree/script-element-icon.png");
    	ImageIcon nodeClosedIcon = CommonOperations.createImageIcon("tree/script-node-closed-icon.png");
    	ImageIcon nodeOpenIcon = CommonOperations.createImageIcon("tree/script-node-open-icon.png");
  
    	//Iconja a NODE-nak
    	if( actualNode instanceof ScriptNodeDataModel){
    		if( expanded ){
    			return nodeOpenIcon;
    		}else{
    			return nodeClosedIcon;
    		}
        
    	}if( actualNode instanceof ScriptElementDataModel ){
    		return elementIcon;
    	}
    	return null;
	}

	@Override
	public void doViewWhenSelectionChanged(DataModelAdapter selectedNode) {
	
		//Ha egyaltalan valamilyen egergombot benyomtam
		if( selectedNode instanceof ScriptRootDataModel ){
			EmptyEditor emptyPanel = new EmptyEditor();								
			guiFrame.showEditorPanel( emptyPanel );
		
		}else if( selectedNode instanceof ScriptNodeDataModel ){
			ScriptNodeEditor scriptNodeEditor = new ScriptNodeEditor(this, (ScriptNodeDataModel)selectedNode, EditMode.VIEW);
			guiFrame.showEditorPanel( scriptNodeEditor);								
		
		}else if( selectedNode instanceof ScriptElementDataModel ){
			ScriptElementEditor scriptElementEditor = new ScriptElementEditor( this, (ScriptElementDataModel)selectedNode, EditMode.VIEW );								
			guiFrame.showEditorPanel( scriptElementEditor);		
								
		}		
	}

	@Override
	public void doModifyWithPopupEdit(DataModelAdapter selectedNode) {
		
		if( selectedNode instanceof ScriptNodeDataModel ){
			
			ScriptNodeEditor scriptNodeEditor = new ScriptNodeEditor( ScriptTree.this, (ScriptNodeDataModel)selectedNode, DataEditor.EditMode.MODIFY );								
			guiFrame.showEditorPanel( scriptNodeEditor);								
			
		}else if( selectedNode instanceof ScriptElementDataModel ){
			
			ScriptElementEditor scriptElementEditor = new ScriptElementEditor( ScriptTree.this, (ScriptElementDataModel)selectedNode, DataEditor.EditMode.MODIFY );								
			guiFrame.showEditorPanel( scriptElementEditor);	
		}
	}

	@Override
	public void doPopupInsert( final JPopupMenu popupMenu, final DataModelAdapter selectedNode) {

		//
		// Csomopont eseten
		//
		if( selectedNode instanceof ScriptNodeDataModel ){

			//Insert Node
			JMenuItem insertNodeMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.node") );
			insertNodeMenu.setActionCommand( ActionCommand.CAPTURE.name());
			insertNodeMenu.addActionListener( new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					
					ScriptNodeEditor scriptNodeEditor = new ScriptNodeEditor( ScriptTree.this, (ScriptNodeDataModel)selectedNode );								
					guiFrame.showEditorPanel( scriptNodeEditor);								
				
				}
			});
			popupMenu.add ( insertNodeMenu );

			//Insert Element
			JMenuItem insertElementMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.script.element") );
			insertElementMenu.setActionCommand( ActionCommand.CAPTURE.name());
			insertElementMenu.addActionListener( new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
							
					ScriptElementEditor scriptElementEditor = new ScriptElementEditor( ScriptTree.this, (ScriptNodeDataModel)selectedNode );								
					guiFrame.showEditorPanel( scriptElementEditor);								
				
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

				//Ha a kivalasztott csomopont szuloje ScriptDataModel - annak kell lennie :)
				if( selectedNode.getParent() instanceof ScriptDataModelAdapter ){
					
					//Akkor megduplikalja 
					ScriptDataModelAdapter duplicated = (ScriptDataModelAdapter)selectedNode.clone();
					
					//Es hozzaadja a szulohoz
					((ScriptDataModelAdapter)selectedNode.getParent()).add( duplicated );

					//Felfrissitem a Tree-t
					ScriptTree.this.changed();
				
				}

			}
		});
		popupMenu.add ( duplicateMenu );
	}
	
	@Override
	public void doPopupDelete( final JPopupMenu popupMenu, final DataModelAdapter selectedNode, final int selectedRow, final DefaultTreeModel totalTreeModel ) {
	
		// Torles
		// Ha nincs alatta ujabb elem
		//
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
						ScriptTree.this.setSelectionRow(selectedRow - 1);
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
				
				ScriptNodeEditor scriptNodeEditor = new ScriptNodeEditor( ScriptTree.this, (ScriptNodeDataModel)selectedNode );								
				guiFrame.showEditorPanel( scriptNodeEditor);								
			
			}
		});
		popupMenu.add ( insertNodeMenu );
		
		
		//Insert Element
		JMenuItem insertElementMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.script.element" ) );
		insertElementMenu.setActionCommand( ActionCommand.CAPTURE.name());
		insertElementMenu.addActionListener( new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent e) {
							
				ScriptElementEditor scriptElementEditor = new ScriptElementEditor( ScriptTree.this, (ScriptNodeDataModel)selectedNode );								
				guiFrame.showEditorPanel( scriptElementEditor);								
			
			}
		});
		popupMenu.add ( insertElementMenu );
		
	}

	@Override
	public boolean possibleHierarchy(DefaultMutableTreeNode draggedNode, Object dropObject) {

		//Node elhelyezese Node-ba
		if( draggedNode instanceof ScriptNodeDataModel && dropObject instanceof ScriptNodeDataModel ){
			return true;
		
		//Element elhelyezese Node-ba
		}else if( draggedNode instanceof ScriptElementDataModel && dropObject instanceof ScriptNodeDataModel ){
			return true;
		
		//Node elhelyezese Root-ba			
		}else if( draggedNode instanceof ScriptNodeDataModel && dropObject instanceof ScriptRootDataModel ){
			return true;
		
		//Elem elhelyezese Root-ba	
		}else if( draggedNode instanceof ScriptElementDataModel && dropObject instanceof ScriptRootDataModel ){
			return true;
			
		}

		//Minden egyeb eset tilos
		return false;
	}

}





