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
import hu.akoel.grawit.core.datamodel.DataModelInterface;
import hu.akoel.grawit.core.datamodel.elements.ParamElementDataModel;
import hu.akoel.grawit.core.datamodel.nodes.ParamNodeDataModel;
import hu.akoel.grawit.core.datamodel.pages.ParamPageDataModel;
import hu.akoel.grawit.core.datamodel.roots.BaseRootDataModel;
import hu.akoel.grawit.core.datamodel.roots.ParamRootDataModel;
import hu.akoel.grawit.gui.GUIFrame;
import hu.akoel.grawit.gui.editor.EmptyEditor;
import hu.akoel.grawit.gui.editor.ParamElementEditor;
import hu.akoel.grawit.gui.editor.ParamNodeEditor;
import hu.akoel.grawit.gui.editor.ParamPageEditor;
import hu.akoel.grawit.gui.editor.DataEditor.EditMode;

public class ParamTree extends Tree {

	private static final long serialVersionUID = -7537783206534337777L;
	private GUIFrame guiFrame;
	private BaseRootDataModel baseRootDataModel;
	
	public ParamTree(GUIFrame guiFrame, ParamRootDataModel rootDataModel, BaseRootDataModel baseRootDataModel) {
		super(guiFrame, rootDataModel);
		
		this.guiFrame = guiFrame;
		this.baseRootDataModel = baseRootDataModel;
	}

	@Override
	public ImageIcon getIcon(DataModelInterface actualNode, boolean expanded) {

    	ImageIcon pageIcon = CommonOperations.createImageIcon("tree/pagebase-page-icon.png");
    	ImageIcon elementIcon = CommonOperations.createImageIcon("tree/pagebase-element-icon.png");
    	ImageIcon nodeClosedIcon = CommonOperations.createImageIcon("tree/node-closed-icon.png");
    	ImageIcon nodeOpenIcon = CommonOperations.createImageIcon("tree/node-open-icon.png");
    	
    	//Iconja a NODE-nak
    	if( actualNode instanceof ParamPageDataModel){
            return pageIcon;
    	}else if( actualNode instanceof ParamElementDataModel ){
            return elementIcon;
    	}else if( actualNode instanceof ParamNodeDataModel){
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
		
		//Ha a root-ot valasztottam
		if( selectedNode instanceof ParamRootDataModel ){
			EmptyEditor emptyPanel = new EmptyEditor();								
			guiFrame.showEditorPanel( emptyPanel );
			
		}else if( selectedNode instanceof ParamNodeDataModel ){
			ParamNodeEditor paramNodeEditor = new ParamNodeEditor( this, (ParamNodeDataModel)selectedNode, EditMode.VIEW);
			guiFrame.showEditorPanel( paramNodeEditor);								
			
		}else if( selectedNode instanceof ParamPageDataModel ){
			ParamPageEditor paramPageEditor = new ParamPageEditor( this, (ParamPageDataModel)selectedNode, EditMode.VIEW );								
			guiFrame.showEditorPanel( paramPageEditor);				
							
		}else if( selectedNode instanceof ParamElementDataModel ){
			ParamElementEditor pageBaseElementEditor = new ParamElementEditor( this, (ParamElementDataModel)selectedNode, EditMode.VIEW );	
			guiFrame.showEditorPanel( pageBaseElementEditor);									
			
		}
		
	}

	@Override
	public void doModifyWithPopupEdit(DataModelInterface selectedNode) {
		if( selectedNode instanceof ParamNodeDataModel ){
			
			ParamNodeEditor paramNodeEditor = new ParamNodeEditor( this, (ParamNodeDataModel)selectedNode, EditMode.MODIFY );								
			guiFrame.showEditorPanel( paramNodeEditor);								
				
		}else if( selectedNode instanceof ParamPageDataModel ){
			
			ParamPageEditor paramPageEditor = new ParamPageEditor( this, (ParamPageDataModel)selectedNode, EditMode.MODIFY );							                                            
			guiFrame.showEditorPanel( paramPageEditor);		
				
		}else if( selectedNode instanceof ParamElementDataModel ){

			ParamElementEditor paramElementEditor = new ParamElementEditor( this, (ParamElementDataModel)selectedNode, EditMode.MODIFY );
			guiFrame.showEditorPanel( paramElementEditor);		
				
		}		
	}

	@Override
	public void doPopupInsert( JPopupMenu popupMenu, final DataModelInterface selectedNode) {
		
		//
		// Csomopont eseten
		//
		if( selectedNode instanceof ParamNodeDataModel ){

			//Insert Node
			JMenuItem insertNodeMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.node") );
			insertNodeMenu.setActionCommand( ActionCommand.CAPTURE.name());
			insertNodeMenu.addActionListener( new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					
					ParamNodeEditor paramNodeEditor = new ParamNodeEditor( ParamTree.this, (ParamNodeDataModel)selectedNode );								
					guiFrame.showEditorPanel( paramNodeEditor);								
				
				}
			});
			popupMenu.add ( insertNodeMenu );

			//Insert Page
			JMenuItem insertPageMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.page") );
			insertPageMenu.setActionCommand( ActionCommand.CAPTURE.name());
			insertPageMenu.addActionListener( new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					ParamPageEditor paramPageEditor = new ParamPageEditor( ParamTree.this, (ParamNodeDataModel)selectedNode, ParamTree.this.baseRootDataModel );								
					guiFrame.showEditorPanel( paramPageEditor);								
				
				}
			});
			popupMenu.add ( insertPageMenu );
			
		}		
		
		//
		// Page eseten
		//
		if( selectedNode instanceof ParamPageDataModel ){

			//Insert Element
			JMenuItem insertElementMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.element") );
			insertElementMenu.setActionCommand( ActionCommand.CAPTURE.name());
			insertElementMenu.addActionListener( new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					
					ParamElementEditor paramPageNodeEditor = new ParamElementEditor( ParamTree.this, (ParamPageDataModel)selectedNode );								
					guiFrame.showEditorPanel( paramPageNodeEditor);								
				
				}
			});
			popupMenu.add ( insertElementMenu );
		
		}
		
	}

	@Override
	public void doPopupDelete( final JPopupMenu popupMenu, final DataModelInterface selectedNode, final int selectedRow,	final DefaultTreeModel totalTreeModel) {
	
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
						ParamTree.this.setSelectionRow(selectedRow - 1);
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
				
				ParamNodeEditor paramNodeEditor = new ParamNodeEditor( ParamTree.this, (ParamNodeDataModel)selectedNode );								
				guiFrame.showEditorPanel( paramNodeEditor);								
			
			}
		});
		popupMenu.add ( insertNodeMenu );
		
	}

}
