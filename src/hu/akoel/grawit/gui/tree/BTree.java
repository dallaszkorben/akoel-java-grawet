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
import hu.akoel.grawit.core.datamodel.BaseDataModelInterface;
import hu.akoel.grawit.core.datamodel.DataModelInterface;
import hu.akoel.grawit.core.datamodel.elements.BaseElementDataModel;
import hu.akoel.grawit.core.datamodel.nodes.BaseNodeDataModel;
import hu.akoel.grawit.core.datamodel.pages.BasePageDataModel;
import hu.akoel.grawit.core.datamodel.roots.BaseRootDataModel;
import hu.akoel.grawit.gui.GUIFrame;
import hu.akoel.grawit.gui.editor.BaseElementEditor;
import hu.akoel.grawit.gui.editor.BaseNodeEditor;
import hu.akoel.grawit.gui.editor.BasePageEditor;
import hu.akoel.grawit.gui.editor.DataEditor;
import hu.akoel.grawit.gui.editor.DataEditor.EditMode;
import hu.akoel.grawit.gui.editor.EmptyEditor;

public class BTree extends Tree{

	private static final long serialVersionUID = -5965897830877262588L;
	private GUIFrame guiFrame;
	
	public BTree(GUIFrame guiFrame, BaseRootDataModel rootDataModel) {
		super(guiFrame, rootDataModel);
		this.guiFrame = guiFrame;
	}

	@Override
	public ImageIcon getIcon(DataModelInterface actualNode, boolean expanded) {

    	ImageIcon pageIcon = CommonOperations.createImageIcon("tree/pagebase-page-icon.png");
    	ImageIcon elementIcon = CommonOperations.createImageIcon("tree/pagebase-element-icon.png");
    	ImageIcon nodeClosedIcon = CommonOperations.createImageIcon("tree/node-closed-icon.png");
    	ImageIcon nodeOpenIcon = CommonOperations.createImageIcon("tree/node-open-icon.png");
  
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
	public void doWiewWhenSelectionChanged(DataModelInterface selectedNode) {
	
		//Ha egyaltalan valamilyen egergombot benyomtam
		if( selectedNode instanceof BaseRootDataModel ){
			EmptyEditor emptyPanel = new EmptyEditor();								
			guiFrame.showEditorPanel( emptyPanel );
		
		}else if( selectedNode instanceof BaseNodeDataModel ){
			BaseNodeEditor pageBaseNodePanel = new BaseNodeEditor(this, (BaseNodeDataModel)selectedNode, EditMode.VIEW);
			guiFrame.showEditorPanel( pageBaseNodePanel);								
		
		}else if( selectedNode instanceof BasePageDataModel ){
			BasePageEditor pageBasePagePanel = new BasePageEditor( this, (BasePageDataModel)selectedNode, EditMode.VIEW );								
			guiFrame.showEditorPanel( pageBasePagePanel);				
						
		}else if( selectedNode instanceof BaseElementDataModel ){
			BaseElementEditor pageBaseElementPanel = new BaseElementEditor( this, (BaseElementDataModel)selectedNode, EditMode.VIEW );								
			guiFrame.showEditorPanel( pageBaseElementPanel);		
								
		}		
	}

	@Override
	public void doModifyWithPopupEdit(DataModelInterface selectedNode) {
		
		if( selectedNode instanceof BaseNodeDataModel ){
							
			BaseNodeEditor pageBaseNodePanel = new BaseNodeEditor( this, (BaseNodeDataModel)selectedNode, DataEditor.EditMode.MODIFY );								
			guiFrame.showEditorPanel( pageBaseNodePanel);								
								
		}else if( selectedNode instanceof BasePageDataModel ){
								
			BasePageEditor pageBasePagePanel = new BasePageEditor( this, (BasePageDataModel)selectedNode, DataEditor.EditMode.MODIFY );								
			guiFrame.showEditorPanel( pageBasePagePanel);		
								
		}else if( selectedNode instanceof BaseElementDataModel ){

			BaseElementEditor pageBaseElementPanel = new BaseElementEditor( this, (BaseElementDataModel)selectedNode, DataEditor.EditMode.MODIFY );								
			guiFrame.showEditorPanel( pageBaseElementPanel);		
								
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
					
					BaseNodeEditor pageBaseNodePanel = new BaseNodeEditor( BTree.this, (BaseNodeDataModel)selectedNode );								
					guiFrame.showEditorPanel( pageBaseNodePanel);								
				
				}
			});
			this.add ( insertNodeMenu );

			//Insert Page
			JMenuItem insertPageMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.page") );
			insertPageMenu.setActionCommand( ActionCommand.CAPTURE.name());
			insertPageMenu.addActionListener( new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					
					BasePageEditor pageBaseNodePanel = new BasePageEditor( BTree.this, (BaseNodeDataModel)selectedNode );								
					guiFrame.showEditorPanel( pageBaseNodePanel);								
				
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
					
					BaseElementEditor pageBaseNodePanel = new BaseElementEditor( BTree.this, (BasePageDataModel)selectedNode );								
					guiFrame.showEditorPanel( pageBaseNodePanel);								
				
				}
			});
			popupMenu.add ( insertElementMenu );
		
		}
	
		
	}

	@Override
	public void doPopupDelete( final DataModelInterface selectedNode, final int selectedRow, final DefaultTreeModel totalTreeModel ) {
	
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
							"Valóban torolni kívánod a(z) " + selectedNode.getTag() + " nevü " + selectedNode.getTypeToShow() + "-t ?",
							CommonOperations.getTranslation("editor.windowtitle.confirmation.delete"),
							JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.QUESTION_MESSAGE,
							null,
							options,
							options[0]);

					if( n == 1 ){
						totalTreeModel.removeNodeFromParent( selectedNode);
						BTree.this.setSelectionRow(selectedRow - 1);
					}							
				}
			});
			this.add ( deleteMenu );
			
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
				
				
				BaseNodeEditor pageBaseNodePanel = new BaseNodeEditor( BTree.this, (BaseNodeDataModel)selectedNode );								
				guiFrame.showEditorPanel( pageBaseNodePanel);								
			
			}
		});
		popupMenu.add ( insertNodeMenu );			
		
	}

}
