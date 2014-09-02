package hu.akoel.grawit.gui.tree;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.tree.DefaultTreeModel;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.DataModelInterface;
import hu.akoel.grawit.core.treenodedatamodel.variable.VariableElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.variable.VariableNodeDataModel;
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
	public ImageIcon getIcon(DataModelInterface actualNode, boolean expanded) {

    	//ImageIcon pageIcon = CommonOperations.createImageIcon("tree/pagebase-page-icon.png");
    	ImageIcon elementIcon = CommonOperations.createImageIcon("tree/variable-element-icon.png");
    	ImageIcon nodeClosedIcon = CommonOperations.createImageIcon("tree/variable-node-closed-icon.png");
    	ImageIcon nodeOpenIcon = CommonOperations.createImageIcon("tree/variable-node-open-icon.png");
  
    	//Iconja a NODE-nak
    	if( actualNode instanceof VariableNodeDataModel){
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
	public void doViewWhenSelectionChanged(DataModelInterface selectedNode) {
	
		//Ha egyaltalan valamilyen egergombot benyomtam
		if( selectedNode instanceof VariableRootDataModel ){
			EmptyEditor emptyPanel = new EmptyEditor();								
			guiFrame.showEditorPanel( emptyPanel );
		
		}else if( selectedNode instanceof VariableNodeDataModel ){
			VariableNodeEditor variableNodeEditor = new VariableNodeEditor(this, (VariableNodeDataModel)selectedNode, EditMode.VIEW);
			guiFrame.showEditorPanel( variableNodeEditor);								
		
		}else if( selectedNode instanceof VariableElementDataModel ){
			VariableElementEditor variableElementEditor = new VariableElementEditor( this, (VariableElementDataModel)selectedNode, EditMode.VIEW );								
			guiFrame.showEditorPanel( variableElementEditor);		
								
		}		
	}

	@Override
	public void doModifyWithPopupEdit(DataModelInterface selectedNode) {
		
		if( selectedNode instanceof VariableNodeDataModel ){
			
			VariableNodeEditor variableNodeEditor = new VariableNodeEditor( VariableTree.this, (VariableNodeDataModel)selectedNode, DataEditor.EditMode.MODIFY );								
			guiFrame.showEditorPanel( variableNodeEditor);								
			
		}else if( selectedNode instanceof VariableElementDataModel ){
			
			VariableElementEditor variableElementEditor = new VariableElementEditor( VariableTree.this, (VariableElementDataModel)selectedNode, DataEditor.EditMode.MODIFY );								
			guiFrame.showEditorPanel( variableElementEditor);	
		}
	}

	@Override
	public void doPopupInsert( final JPopupMenu popupMenu, final DataModelInterface selectedNode) {

		//
		// Csomopont eseten
		//
		if( selectedNode instanceof VariableNodeDataModel ){

			//Insert Node
			JMenuItem insertNodeMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.node") );
			insertNodeMenu.setActionCommand( ActionCommand.CAPTURE.name());
			insertNodeMenu.addActionListener( new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					
					VariableNodeEditor variableNodeEditor = new VariableNodeEditor( VariableTree.this, (VariableNodeDataModel)selectedNode );								
					guiFrame.showEditorPanel( variableNodeEditor);								
				
				}
			});
			popupMenu.add ( insertNodeMenu );

			//Insert Element
			JMenuItem insertElementMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.variable") );
			insertElementMenu.setActionCommand( ActionCommand.CAPTURE.name());
			insertElementMenu.addActionListener( new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
							
					VariableElementEditor variableElementEditor = new VariableElementEditor( VariableTree.this, (VariableNodeDataModel)selectedNode );								
					guiFrame.showEditorPanel( variableElementEditor);								
				
				}
			});
			popupMenu.add ( insertElementMenu );
			
		}	
	
		
	}

	@Override
	public void doPopupDelete( final JPopupMenu popupMenu, final DataModelInterface selectedNode, final int selectedRow, final DefaultTreeModel totalTreeModel ) {
	
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
							"Valóban torolni kívánod a(z) " + selectedNode.getName() + " nevü " + selectedNode.getModelNameToShow() + "-t ?",
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
				
				VariableNodeEditor variableNodeEditor = new VariableNodeEditor( VariableTree.this, (VariableNodeDataModel)selectedNode );								
				guiFrame.showEditorPanel( variableNodeEditor);								
			
			}
		});
		popupMenu.add ( insertNodeMenu );
		
		
		//Insert Element
		JMenuItem insertElementMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.variable") );
		insertElementMenu.setActionCommand( ActionCommand.CAPTURE.name());
		insertElementMenu.addActionListener( new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent e) {
							
				VariableElementEditor variableElementEditor = new VariableElementEditor( VariableTree.this, (VariableNodeDataModel)selectedNode );								
				guiFrame.showEditorPanel( variableElementEditor);								
			
			}
		});
		popupMenu.add ( insertElementMenu );
		
	}

}
