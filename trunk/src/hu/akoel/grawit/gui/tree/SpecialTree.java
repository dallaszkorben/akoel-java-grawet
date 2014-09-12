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
import hu.akoel.grawit.core.treenodedatamodel.special.SpecialCustomDataModel;
import hu.akoel.grawit.core.treenodedatamodel.special.SpecialCloseDataModel;
import hu.akoel.grawit.core.treenodedatamodel.special.SpecialNodeDataModel;
import hu.akoel.grawit.core.treenodedatamodel.special.SpecialOpenDataModel;
import hu.akoel.grawit.core.treenodedatamodel.special.SpecialRootDataModel;
import hu.akoel.grawit.enums.ActionCommand;
import hu.akoel.grawit.gui.GUIFrame;
import hu.akoel.grawit.gui.editor.DataEditor;
import hu.akoel.grawit.gui.editor.DataEditor.EditMode;
import hu.akoel.grawit.gui.editor.special.SpecialCloseEditor;
import hu.akoel.grawit.gui.editor.special.SpecialCustomEditor;
import hu.akoel.grawit.gui.editor.special.SpecialNodeEditor;
import hu.akoel.grawit.gui.editor.special.SpecialOpenEditor;
import hu.akoel.grawit.gui.editor.EmptyEditor;

public class SpecialTree extends Tree{

	private static final long serialVersionUID = -148050591040023129L;
	
	private GUIFrame guiFrame;
	
	public SpecialTree(GUIFrame guiFrame, SpecialRootDataModel rootDataModel) {
		super(guiFrame, rootDataModel);
		this.guiFrame = guiFrame;
	}

	@Override
	public ImageIcon getIcon(DataModelInterface actualNode, boolean expanded) {

		ImageIcon closeIcon = CommonOperations.createImageIcon("tree/special-close-icon.png");
		ImageIcon openIcon = CommonOperations.createImageIcon("tree/special-open-icon.png");
		ImageIcon customIcon = CommonOperations.createImageIcon("tree/special-custom-icon.png");
    	ImageIcon nodeClosedIcon = CommonOperations.createImageIcon("tree/special-node-closed-icon.png");
    	ImageIcon nodeOpenIcon = CommonOperations.createImageIcon("tree/special-node-open-icon.png");
    	
  
    	//Iconja a NODE-nak
    	if( actualNode instanceof SpecialOpenDataModel ){
            return openIcon;
    	}else if( actualNode instanceof SpecialCloseDataModel ){
            return closeIcon;
    	}else if( actualNode instanceof SpecialCustomDataModel ){
            return customIcon;
    	}else if( actualNode instanceof SpecialNodeDataModel){
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
		if( selectedNode instanceof SpecialRootDataModel ){
			EmptyEditor emptyPanel = new EmptyEditor();								
			guiFrame.showEditorPanel( emptyPanel );
		
		}else if( selectedNode instanceof SpecialNodeDataModel ){
			SpecialNodeEditor specialNodeEditor = new SpecialNodeEditor(this, (SpecialNodeDataModel)selectedNode, EditMode.VIEW);
			guiFrame.showEditorPanel( specialNodeEditor);								
					
		}else if( selectedNode instanceof SpecialCustomDataModel ){
			SpecialCustomEditor specialCustomEditor = new SpecialCustomEditor( this, (SpecialCustomDataModel)selectedNode, EditMode.VIEW );								
			guiFrame.showEditorPanel( specialCustomEditor);		
			
		}else if( selectedNode instanceof SpecialOpenDataModel ){
			SpecialOpenEditor specialOpenEditor = new SpecialOpenEditor( this, (SpecialOpenDataModel)selectedNode, EditMode.VIEW );								
			guiFrame.showEditorPanel( specialOpenEditor);		
								
		}else if( selectedNode instanceof SpecialCloseDataModel ){
			SpecialCloseEditor specialCloseEditor = new SpecialCloseEditor( this, (SpecialCloseDataModel)selectedNode, EditMode.VIEW );								
			guiFrame.showEditorPanel( specialCloseEditor);		
								
		}				
	}

	@Override
	public void doModifyWithPopupEdit(DataModelInterface selectedNode) {
		
		if( selectedNode instanceof SpecialNodeDataModel ){
							
			SpecialNodeEditor specialNodeEditor = new SpecialNodeEditor( this, (SpecialNodeDataModel)selectedNode, DataEditor.EditMode.MODIFY );								
			guiFrame.showEditorPanel( specialNodeEditor);								
					
		}else if( selectedNode instanceof SpecialCustomDataModel ){

			SpecialCustomEditor specialCustomEditor = new SpecialCustomEditor( this, (SpecialCustomDataModel)selectedNode, DataEditor.EditMode.MODIFY );								
			guiFrame.showEditorPanel( specialCustomEditor);	
			
		}else if( selectedNode instanceof SpecialOpenDataModel ){

			SpecialOpenEditor specialOpenEditor = new SpecialOpenEditor( this, (SpecialOpenDataModel)selectedNode, DataEditor.EditMode.MODIFY );								
			guiFrame.showEditorPanel( specialOpenEditor);		
								
		}else if( selectedNode instanceof SpecialCloseDataModel ){

			SpecialCloseEditor specialCloseEditor = new SpecialCloseEditor( this, (SpecialCloseDataModel)selectedNode, DataEditor.EditMode.MODIFY );								
			guiFrame.showEditorPanel( specialCloseEditor);		
								
		}		
	}

	@Override
	public void doPopupInsert( final JPopupMenu popupMenu, final DataModelInterface selectedNode) {

		//
		// Csomopont eseten
		//
		if( selectedNode instanceof SpecialNodeDataModel ){

			//Insert Node
			JMenuItem insertNodeMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.node") );
			insertNodeMenu.setActionCommand( ActionCommand.CAPTURE.name());
			insertNodeMenu.addActionListener( new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					
					SpecialNodeEditor specialNodeEditor = new SpecialNodeEditor( SpecialTree.this, (SpecialNodeDataModel)selectedNode );								
					guiFrame.showEditorPanel( specialNodeEditor);								
				
				}
			});
			popupMenu.add ( insertNodeMenu );
		
			//Insert Open
			JMenuItem insertCloseMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.special.openpage") );
			insertCloseMenu.setActionCommand( ActionCommand.CAPTURE.name());
			insertCloseMenu.addActionListener( new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					
					SpecialOpenEditor specialOpenEditor = new SpecialOpenEditor( SpecialTree.this, (SpecialNodeDataModel)selectedNode );								
					guiFrame.showEditorPanel( specialOpenEditor);								
				
				}
			});
			popupMenu.add ( insertCloseMenu );
			
			//Insert Close
			JMenuItem insertOpenMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.special.closepage") );
			insertOpenMenu.setActionCommand( ActionCommand.CAPTURE.name());
			insertOpenMenu.addActionListener( new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					
					SpecialCloseEditor specialCloseEditor = new SpecialCloseEditor( SpecialTree.this, (SpecialNodeDataModel)selectedNode );								
					guiFrame.showEditorPanel( specialCloseEditor);								
				
				}
			});
			popupMenu.add ( insertOpenMenu );

			//Insert Custom
			JMenuItem insertCustomMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.special.custompage") );
			insertCustomMenu.setActionCommand( ActionCommand.CAPTURE.name());
			insertCustomMenu.addActionListener( new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					
					SpecialCustomEditor specialCloseEditor = new SpecialCustomEditor( SpecialTree.this, (SpecialNodeDataModel)selectedNode );								
					guiFrame.showEditorPanel( specialCloseEditor);								
				
				}
			});
			popupMenu.add ( insertCustomMenu );
			
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
							"Valóban torolni kívánod a(z) " + selectedNode.getTag().getName() + " nevü " + selectedNode.getNodeTypeToShow() + "-t ?",
							CommonOperations.getTranslation("editor.windowtitle.confirmation.delete"),
							JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.QUESTION_MESSAGE,
							null,
							options,
							options[0]);

					if( n == 1 ){
						totalTreeModel.removeNodeFromParent( selectedNode);
						SpecialTree.this.setSelectionRow(selectedRow - 1);
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
				
				
				SpecialNodeEditor specialNodeEditor = new SpecialNodeEditor( SpecialTree.this, (SpecialNodeDataModel)selectedNode );								
				guiFrame.showEditorPanel( specialNodeEditor);								
			
			}
		});
		popupMenu.add ( insertNodeMenu );			
		
	}

}
