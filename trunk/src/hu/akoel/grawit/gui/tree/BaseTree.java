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
import hu.akoel.grawit.core.treenodedatamodel.base.BaseElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseNodeDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.BasePageDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseRootDataModel;
import hu.akoel.grawit.enums.ActionCommand;
import hu.akoel.grawit.gui.GUIFrame;
import hu.akoel.grawit.gui.editor.DataEditor;
import hu.akoel.grawit.gui.editor.DataEditor.EditMode;
import hu.akoel.grawit.gui.editor.base.BaseElementEditor;
import hu.akoel.grawit.gui.editor.base.BaseNodeEditor;
import hu.akoel.grawit.gui.editor.base.BasePageEditor;
import hu.akoel.grawit.gui.editor.EmptyEditor;

public class BaseTree extends Tree{

	private static final long serialVersionUID = -5965897830877262588L;
	private GUIFrame guiFrame;
	
	public BaseTree(GUIFrame guiFrame, BaseRootDataModel rootDataModel) {
		super(guiFrame, rootDataModel);
		this.guiFrame = guiFrame;
	}

	@Override
	public ImageIcon getIcon(DataModelInterface actualNode, boolean expanded) {

    	ImageIcon pageIcon = CommonOperations.createImageIcon("tree/base-page-icon.png");
    	ImageIcon elementIcon = CommonOperations.createImageIcon("tree/base-element-icon.png");
    	ImageIcon nodeClosedIcon = CommonOperations.createImageIcon("tree/base-node-closed-icon.png");
    	ImageIcon nodeOpenIcon = CommonOperations.createImageIcon("tree/base-node-open-icon.png");
  
    	//Iconja a NODE-nak
    	if( actualNode instanceof BasePageDataModel){
            return pageIcon;
    	}else if( actualNode instanceof BaseElementDataModel ){
            return elementIcon;
    	}else if( actualNode instanceof BaseNodeDataModel){
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
	
		//Ha egyaltalan valamilyen egergombot benyomtam
		if( selectedNode instanceof BaseRootDataModel ){
			EmptyEditor emptyPanel = new EmptyEditor();								
			guiFrame.showEditorPanel( emptyPanel );
		
		}else if( selectedNode instanceof BaseNodeDataModel ){
			BaseNodeEditor baseNodeEditor = new BaseNodeEditor(this, (BaseNodeDataModel)selectedNode, EditMode.VIEW);
			guiFrame.showEditorPanel( baseNodeEditor);								
		
		}else if( selectedNode instanceof BasePageDataModel ){
			BasePageEditor basePageEditor = new BasePageEditor( this, (BasePageDataModel)selectedNode, EditMode.VIEW );								
			guiFrame.showEditorPanel( basePageEditor);				
						
		}else if( selectedNode instanceof BaseElementDataModel ){
			BaseElementEditor baseElementEditor = new BaseElementEditor( this, (BaseElementDataModel)selectedNode, EditMode.VIEW );								
			guiFrame.showEditorPanel( baseElementEditor);		
								
		}			
	}

	@Override
	public void doModifyWithPopupEdit(DataModelInterface selectedNode) {
		
		if( selectedNode instanceof BaseNodeDataModel ){
							
			BaseNodeEditor baseNodeEditor = new BaseNodeEditor( this, (BaseNodeDataModel)selectedNode, DataEditor.EditMode.MODIFY );								
			guiFrame.showEditorPanel( baseNodeEditor);								
								
		}else if( selectedNode instanceof BasePageDataModel ){
								
			BasePageEditor basePageEditor = new BasePageEditor( this, (BasePageDataModel)selectedNode, DataEditor.EditMode.MODIFY );								
			guiFrame.showEditorPanel( basePageEditor);		
								
		}else if( selectedNode instanceof BaseElementDataModel ){

			BaseElementEditor baseElementEditor = new BaseElementEditor( this, (BaseElementDataModel)selectedNode, DataEditor.EditMode.MODIFY );								
			guiFrame.showEditorPanel( baseElementEditor);		
								
		}	
	}

	@Override
	public void doPopupInsert( final JPopupMenu popupMenu, final DataModelInterface selectedNode) {

		//
		// Csomopont eseten
		//
		if( selectedNode instanceof BaseNodeDataModel ){

			//Insert Node
			JMenuItem insertNodeMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.node") );
			insertNodeMenu.setActionCommand( ActionCommand.CAPTURE.name());
			insertNodeMenu.addActionListener( new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					
					BaseNodeEditor baseNodeEditor = new BaseNodeEditor( BaseTree.this, (BaseNodeDataModel)selectedNode );								
					guiFrame.showEditorPanel( baseNodeEditor);								
				
				}
			});
			popupMenu.add ( insertNodeMenu );

			//Insert Page
			JMenuItem insertPageMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.page") );
			insertPageMenu.setActionCommand( ActionCommand.CAPTURE.name());
			insertPageMenu.addActionListener( new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					
					BasePageEditor baseNodeEditor = new BasePageEditor( BaseTree.this, (BaseNodeDataModel)selectedNode );								
					guiFrame.showEditorPanel( baseNodeEditor);								
				
				}
			});
			popupMenu.add ( insertPageMenu );
			
		}		
		
		
		//
		// Page eseten
		//
		
		if( selectedNode instanceof BasePageDataModel ){

			//Insert Element
			JMenuItem insertElementMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.element") );
			insertElementMenu.setActionCommand( ActionCommand.CAPTURE.name());
			insertElementMenu.addActionListener( new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					
					BaseElementEditor baseNodeEditor = new BaseElementEditor( BaseTree.this, (BasePageDataModel)selectedNode );								
					guiFrame.showEditorPanel( baseNodeEditor);								
				
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
							"Valóban torolni kívánod a(z) " + selectedNode.getTag().getName() + " nevü " + selectedNode.getModelNameToShow() + "-t ?",
							CommonOperations.getTranslation("editor.windowtitle.confirmation.delete"),
							JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.QUESTION_MESSAGE,
							null,
							options,
							options[0]);

					if( n == 1 ){
						totalTreeModel.removeNodeFromParent( selectedNode);
						BaseTree.this.setSelectionRow(selectedRow - 1);
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
				
				
				BaseNodeEditor baseNodeEditor = new BaseNodeEditor( BaseTree.this, (BaseNodeDataModel)selectedNode );								
				guiFrame.showEditorPanel( baseNodeEditor);								
			
			}
		});
		popupMenu.add ( insertNodeMenu );			
		
	}

}
