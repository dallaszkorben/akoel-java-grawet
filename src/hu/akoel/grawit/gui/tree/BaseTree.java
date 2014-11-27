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
import hu.akoel.grawit.core.treenodedatamodel.BaseDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.DataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseNodeDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.BasePageDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.NormalBaseElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.SpecialBaseElementDataModel;
import hu.akoel.grawit.enums.ActionCommand;
import hu.akoel.grawit.gui.GUIFrame;
import hu.akoel.grawit.gui.editor.DataEditor;
import hu.akoel.grawit.gui.editor.DataEditor.EditMode;
import hu.akoel.grawit.gui.editor.base.NormalBaseElementEditor;
import hu.akoel.grawit.gui.editor.base.BaseNodeEditor;
import hu.akoel.grawit.gui.editor.base.BasePageEditor;
import hu.akoel.grawit.gui.editor.base.SpecialBaseElementEditor;
import hu.akoel.grawit.gui.editor.EmptyEditor;

public class BaseTree extends Tree{

	private static final long serialVersionUID = -5965897830877262588L;
	private GUIFrame guiFrame;
	
	public BaseTree(GUIFrame guiFrame, BaseRootDataModel rootDataModel) {
		super(guiFrame, rootDataModel);
		this.guiFrame = guiFrame;
	}

	@Override
	public ImageIcon getIcon(DataModelAdapter actualNode, boolean expanded) {

    	ImageIcon pageIcon = CommonOperations.createImageIcon("tree/base-page-icon.png");
    	ImageIcon normalElementIcon = CommonOperations.createImageIcon("tree/base-element-normal-icon.png");
    	ImageIcon specialElementIcon = CommonOperations.createImageIcon("tree/base-element-special-icon.png");
    	ImageIcon nodeClosedIcon = CommonOperations.createImageIcon("tree/base-node-closed-icon.png");
    	ImageIcon nodeOpenIcon = CommonOperations.createImageIcon("tree/base-node-open-icon.png");
  
    	//Iconja a NODE-nak
    	if( actualNode instanceof BasePageDataModel){
            return pageIcon;
    	}else if( actualNode instanceof NormalBaseElementDataModel ){
            return normalElementIcon;
    	}else if( actualNode instanceof SpecialBaseElementDataModel ){
            return specialElementIcon;
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
	public void doViewWhenSelectionChanged(DataModelAdapter selectedNode) {
	
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
						
		}else if( selectedNode instanceof NormalBaseElementDataModel ){
			NormalBaseElementEditor normalBaseElementEditor = new NormalBaseElementEditor( this, (NormalBaseElementDataModel)selectedNode, EditMode.VIEW );								
			guiFrame.showEditorPanel( normalBaseElementEditor);		

		}else if( selectedNode instanceof SpecialBaseElementDataModel ){
			SpecialBaseElementEditor specialBaseElementEditor = new SpecialBaseElementEditor( this, (SpecialBaseElementDataModel)selectedNode, EditMode.VIEW );								
			guiFrame.showEditorPanel( specialBaseElementEditor);		
								
		}			
	}

	@Override
	public void doModifyWithPopupEdit(DataModelAdapter selectedNode) {
		
		if( selectedNode instanceof BaseNodeDataModel ){
							
			BaseNodeEditor baseNodeEditor = new BaseNodeEditor( this, (BaseNodeDataModel)selectedNode, DataEditor.EditMode.MODIFY );								
			guiFrame.showEditorPanel( baseNodeEditor);								
								
		}else if( selectedNode instanceof BasePageDataModel ){
								
			BasePageEditor basePageEditor = new BasePageEditor( this, (BasePageDataModel)selectedNode, DataEditor.EditMode.MODIFY );								
			guiFrame.showEditorPanel( basePageEditor);		
								
		}else if( selectedNode instanceof NormalBaseElementDataModel ){

			NormalBaseElementEditor normalBaseElementEditor = new NormalBaseElementEditor( this, (NormalBaseElementDataModel)selectedNode, DataEditor.EditMode.MODIFY );								
			guiFrame.showEditorPanel( normalBaseElementEditor);		
								
		}else if( selectedNode instanceof SpecialBaseElementDataModel ){

			SpecialBaseElementEditor specialBaseElementEditor = new SpecialBaseElementEditor( this, (SpecialBaseElementDataModel)selectedNode, DataEditor.EditMode.MODIFY );								
			guiFrame.showEditorPanel( specialBaseElementEditor);
								
		}	
	}

	@Override
	public void doPopupInsert( final JPopupMenu popupMenu, final DataModelAdapter selectedNode) {

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
			JMenuItem insertPageMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.base.page") );
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

			//Insert Normal Element
			JMenuItem insertNormalElementMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.base.normalelement") );
			insertNormalElementMenu.setActionCommand( ActionCommand.CAPTURE.name());
			insertNormalElementMenu.addActionListener( new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					
					NormalBaseElementEditor baseNodeEditor = new NormalBaseElementEditor( BaseTree.this, (BasePageDataModel)selectedNode );								
					guiFrame.showEditorPanel( baseNodeEditor);								
				
				}
			});
			popupMenu.add ( insertNormalElementMenu );

			//Insert Special Element
			JMenuItem insertSpecialElementMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.base.specialelement") );
			insertSpecialElementMenu.setActionCommand( ActionCommand.CAPTURE.name());
			insertSpecialElementMenu.addActionListener( new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					
					SpecialBaseElementEditor baseNodeEditor = new SpecialBaseElementEditor( BaseTree.this, (BasePageDataModel)selectedNode );								
					guiFrame.showEditorPanel( baseNodeEditor);								
				
				}
			});
			popupMenu.add ( insertSpecialElementMenu );
			
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
				if( selectedNode.getParent() instanceof BaseDataModelAdapter ){
					
					//Akkor megduplikalja 
					BaseDataModelAdapter duplicated = (BaseDataModelAdapter)selectedNode.clone();
					
					//Es hozzaadja a szulohoz
					((BaseDataModelAdapter)selectedNode.getParent()).add( duplicated );

					//Felfrissitem a Tree-t
					BaseTree.this.changed();
				
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
						BaseTree.this.setSelectionRow(selectedRow - 1);
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
				
				
				BaseNodeEditor baseNodeEditor = new BaseNodeEditor( BaseTree.this, (BaseNodeDataModel)selectedNode );								
				guiFrame.showEditorPanel( baseNodeEditor);								
			
			}
		});
		popupMenu.add ( insertNodeMenu );			
		
	}

	@Override
	public boolean possibleHierarchy(DefaultMutableTreeNode draggedNode, Object dropObject) {

		//Node elhelyezese Node-ba vagy Root-ba
		if( draggedNode instanceof BaseNodeDataModel && dropObject instanceof BaseNodeDataModel ){
			return true;

		//Page elhelyezese Node-ba de nem Root-ba	
		}else if( draggedNode instanceof BasePageDataModel && dropObject instanceof BaseNodeDataModel && !( dropObject instanceof BaseRootDataModel ) ){
			return true;
		
		//NormalElement elhelyezese Page-ben	
		}else if( draggedNode instanceof NormalBaseElementDataModel && dropObject instanceof BasePageDataModel ){
			return true;

		//SpecialElement elhelyezese Page-ben	
		}else if( draggedNode instanceof SpecialBaseElementDataModel && dropObject instanceof BasePageDataModel ){
			return true;

		}
		
		return false;
	}

}
