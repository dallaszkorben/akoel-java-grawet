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
import hu.akoel.grawit.core.treenodedatamodel.base.BaseCollectorDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseFolderDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.NormalBaseElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.ScriptBaseElementDataModel;
import hu.akoel.grawit.enums.ActionCommand;
import hu.akoel.grawit.gui.GUIFrame;
import hu.akoel.grawit.gui.editor.DataEditor;
import hu.akoel.grawit.gui.editor.DataEditor.EditMode;
import hu.akoel.grawit.gui.editor.base.NormalBaseElementEditor;
import hu.akoel.grawit.gui.editor.base.BaseFolderEditor;
import hu.akoel.grawit.gui.editor.base.BasePageEditor;
import hu.akoel.grawit.gui.editor.base.ScriptBaseElementEditor;
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
    	ImageIcon scriptElementIcon = CommonOperations.createImageIcon("tree/base-element-script-icon.png");
    	ImageIcon nodeClosedIcon = CommonOperations.createImageIcon("tree/base-folder-closed-icon.png");
    	ImageIcon nodeOpenIcon = CommonOperations.createImageIcon("tree/base-folder-open-icon.png");
    	ImageIcon rootIcon = CommonOperations.createImageIcon("tree/root-icon.png");
  
    	//Iconja a NODE-nak
    	if( actualNode instanceof BaseRootDataModel){
            return rootIcon;
    	}else if( actualNode instanceof BaseCollectorDataModel){
            return pageIcon;
    	}else if( actualNode instanceof NormalBaseElementDataModel ){
            return normalElementIcon;
    	}else if( actualNode instanceof ScriptBaseElementDataModel ){
            return scriptElementIcon;
    	}else if( actualNode instanceof BaseFolderDataModel){
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
		
		}else if( selectedNode instanceof BaseFolderDataModel ){
			BaseFolderEditor baseNodeEditor = new BaseFolderEditor(this, (BaseFolderDataModel)selectedNode, EditMode.VIEW);
			guiFrame.showEditorPanel( baseNodeEditor);								
		
		}else if( selectedNode instanceof BaseCollectorDataModel ){
			BasePageEditor basePageEditor = new BasePageEditor( this, (BaseCollectorDataModel)selectedNode, EditMode.VIEW );								
			guiFrame.showEditorPanel( basePageEditor);				
						
		}else if( selectedNode instanceof NormalBaseElementDataModel ){
			NormalBaseElementEditor normalBaseElementEditor = new NormalBaseElementEditor( this, (NormalBaseElementDataModel)selectedNode, EditMode.VIEW );								
			guiFrame.showEditorPanel( normalBaseElementEditor);		

		}else if( selectedNode instanceof ScriptBaseElementDataModel ){
			ScriptBaseElementEditor specialBaseElementEditor = new ScriptBaseElementEditor( this, (ScriptBaseElementDataModel)selectedNode, EditMode.VIEW );								
			guiFrame.showEditorPanel( specialBaseElementEditor);		
								
		}			
	}

	@Override
	public void doModifyWithPopupEdit(DataModelAdapter selectedNode) {
		
		if( selectedNode instanceof BaseFolderDataModel ){
							
			BaseFolderEditor baseNodeEditor = new BaseFolderEditor( this, (BaseFolderDataModel)selectedNode, DataEditor.EditMode.MODIFY );								
			guiFrame.showEditorPanel( baseNodeEditor);								
								
		}else if( selectedNode instanceof BaseCollectorDataModel ){
								
			BasePageEditor basePageEditor = new BasePageEditor( this, (BaseCollectorDataModel)selectedNode, DataEditor.EditMode.MODIFY );								
			guiFrame.showEditorPanel( basePageEditor);		
								
		}else if( selectedNode instanceof NormalBaseElementDataModel ){

			NormalBaseElementEditor normalBaseElementEditor = new NormalBaseElementEditor( this, (NormalBaseElementDataModel)selectedNode, DataEditor.EditMode.MODIFY );								
			guiFrame.showEditorPanel( normalBaseElementEditor);		
								
		}else if( selectedNode instanceof ScriptBaseElementDataModel ){

			ScriptBaseElementEditor specialBaseElementEditor = new ScriptBaseElementEditor( this, (ScriptBaseElementDataModel)selectedNode, DataEditor.EditMode.MODIFY );								
			guiFrame.showEditorPanel( specialBaseElementEditor);
								
		}	
	}

	@Override
	public void doPopupInsert( final JPopupMenu popupMenu, final DataModelAdapter selectedNode) {

		//
		// Csomopont eseten
		//
		if( selectedNode instanceof BaseFolderDataModel ){

			//Insert Folder
			JMenuItem insertNodeMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.folder") );
			insertNodeMenu.setActionCommand( ActionCommand.CAPTURE.name());
			insertNodeMenu.addActionListener( new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					
					BaseFolderEditor baseNodeEditor = new BaseFolderEditor( BaseTree.this, (BaseFolderDataModel)selectedNode );								
					guiFrame.showEditorPanel( baseNodeEditor);								
				
				}
			});
			popupMenu.add ( insertNodeMenu );

			//Insert Page
			JMenuItem insertPageMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.base.collector") );
			insertPageMenu.setActionCommand( ActionCommand.CAPTURE.name());
			insertPageMenu.addActionListener( new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					
					BasePageEditor baseNodeEditor = new BasePageEditor( BaseTree.this, (BaseFolderDataModel)selectedNode );								
					guiFrame.showEditorPanel( baseNodeEditor);								
				
				}
			});
			popupMenu.add ( insertPageMenu );
			
			//Insert Normal Element
			JMenuItem insertNormalElementMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.base.normalelement") );
			insertNormalElementMenu.setActionCommand( ActionCommand.CAPTURE.name());
			insertNormalElementMenu.addActionListener( new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					
					NormalBaseElementEditor baseNodeEditor = new NormalBaseElementEditor( BaseTree.this, (BaseFolderDataModel)selectedNode );								
					guiFrame.showEditorPanel( baseNodeEditor);								
				
				}
			});
			popupMenu.add ( insertNormalElementMenu );
			
			//Insert Script Element
			JMenuItem insertScriptElementMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.base.specialelement") );
			insertScriptElementMenu.setActionCommand( ActionCommand.CAPTURE.name());
			insertScriptElementMenu.addActionListener( new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					
					ScriptBaseElementEditor baseNodeEditor = new ScriptBaseElementEditor( BaseTree.this, (BaseFolderDataModel)selectedNode );								
					guiFrame.showEditorPanel( baseNodeEditor);								
				
				}
			});
			popupMenu.add ( insertScriptElementMenu );
		}		
		
		
		//
		// Page eseten
		//
		
		if( selectedNode instanceof BaseCollectorDataModel ){

			//Insert Normal Element
			JMenuItem insertNormalElementMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.base.normalelement") );
			insertNormalElementMenu.setActionCommand( ActionCommand.CAPTURE.name());
			insertNormalElementMenu.addActionListener( new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					
					NormalBaseElementEditor baseNodeEditor = new NormalBaseElementEditor( BaseTree.this, (BaseCollectorDataModel)selectedNode );								
					guiFrame.showEditorPanel( baseNodeEditor);								
				
				}
			});
			popupMenu.add ( insertNormalElementMenu );

			//Insert Script Element
			JMenuItem insertScriptElementMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.base.specialelement") );
			insertScriptElementMenu.setActionCommand( ActionCommand.CAPTURE.name());
			insertScriptElementMenu.addActionListener( new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					
					ScriptBaseElementEditor baseNodeEditor = new ScriptBaseElementEditor( BaseTree.this, (BaseCollectorDataModel)selectedNode );								
					guiFrame.showEditorPanel( baseNodeEditor);								
				
				}
			});
			popupMenu.add ( insertScriptElementMenu );
			
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

		//Insert Folder
		JMenuItem insertNodeMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.folder") );
		insertNodeMenu.setActionCommand( ActionCommand.CAPTURE.name());
		insertNodeMenu.addActionListener( new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
				BaseFolderEditor baseNodeEditor = new BaseFolderEditor( BaseTree.this, (BaseFolderDataModel)selectedNode );								
				guiFrame.showEditorPanel( baseNodeEditor);								
			
			}
		});
		popupMenu.add ( insertNodeMenu );			
		
	}

	@Override
	public boolean possibleHierarchy(DefaultMutableTreeNode draggedNode, Object dropObject) {

		//Node elhelyezese Node-ba vagy Root-ba
		if( draggedNode instanceof BaseFolderDataModel && dropObject instanceof BaseFolderDataModel ){
			return true;

		//Page elhelyezese Node-ba de nem Root-ba	
		}else if( draggedNode instanceof BaseCollectorDataModel && dropObject instanceof BaseFolderDataModel && !( dropObject instanceof BaseRootDataModel ) ){
			return true;
		
		//NormalElement elhelyezese Page-ben	
		}else if( draggedNode instanceof NormalBaseElementDataModel && dropObject instanceof BaseCollectorDataModel ){
			return true;

		//SpecialElement elhelyezese Page-ben	
		}else if( draggedNode instanceof ScriptBaseElementDataModel && dropObject instanceof BaseCollectorDataModel ){
			return true;

		//NormalElement elhelyezese Node-ban	
		}else if( draggedNode instanceof NormalBaseElementDataModel && dropObject instanceof BaseFolderDataModel ){
			return true;

		//SpecialElement elhelyezese Node-ban	
		}else if( draggedNode instanceof ScriptBaseElementDataModel && dropObject instanceof BaseFolderDataModel ){
			return true;

		}
		
		return false;
	}

}
