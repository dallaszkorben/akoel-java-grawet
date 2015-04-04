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
import hu.akoel.grawit.core.treenodedatamodel.base.BaseRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.driver.DriverExplorerCapabilityDataModel;
import hu.akoel.grawit.core.treenodedatamodel.driver.DriverExplorerDataModel;
import hu.akoel.grawit.core.treenodedatamodel.driver.DriverFirefoxDataModel;
import hu.akoel.grawit.core.treenodedatamodel.driver.DriverFirefoxPropertyDataModel;
import hu.akoel.grawit.core.treenodedatamodel.driver.DriverFolderDataModel;
import hu.akoel.grawit.core.treenodedatamodel.driver.DriverRootDataModel;
import hu.akoel.grawit.enums.ActionCommand;
import hu.akoel.grawit.gui.GUIFrame;
import hu.akoel.grawit.gui.editor.DataEditor;
import hu.akoel.grawit.gui.editor.DataEditor.EditMode;
import hu.akoel.grawit.gui.editor.driver.DriverExplorerCapabilityEditor;
import hu.akoel.grawit.gui.editor.driver.DriverExplorerEditor;
import hu.akoel.grawit.gui.editor.driver.DriverFirefoxEditor;
import hu.akoel.grawit.gui.editor.driver.DriverFirefoxPropertyEditor;
import hu.akoel.grawit.gui.editor.driver.DriverNodeEditor;
import hu.akoel.grawit.gui.editor.EmptyEditor;

public class DriverTree extends Tree{

	private static final long serialVersionUID = -2576284128681697627L;
	
	private GUIFrame guiFrame;
	
	public DriverTree(  String functionName, GUIFrame guiFrame, DriverRootDataModel rootDataModel ) {
		super( functionName, guiFrame, rootDataModel);
		this.guiFrame = guiFrame;

	}

	@Override
	public ImageIcon getIcon(DataModelAdapter actualNode, boolean expanded) {
		
    	ImageIcon nodeIcon = CommonOperations.createImageIcon("tree/driver-root-open-icon.png");
    	ImageIcon explorerIcon = CommonOperations.createImageIcon("tree/driver-explorer-icon.png");
    	ImageIcon explorerCapabilityIcon = CommonOperations.createImageIcon("tree/driver-explorer-capability-icon.png");
    	ImageIcon firefoxIcon = CommonOperations.createImageIcon("tree/driver-firefox-icon.png");
    	ImageIcon firefoxPropertyIcon = CommonOperations.createImageIcon("tree/driver-firefox-property-icon.png");
    	ImageIcon nodeClosedIcon = CommonOperations.createImageIcon("tree/driver-node-closed-icon.png");
    	ImageIcon nodeOpenIcon = CommonOperations.createImageIcon("tree/driver-node-open-icon.png");
    	ImageIcon rootIcon = CommonOperations.createImageIcon("tree/root-icon.png");
  
    	//Iconja a NODE-nak
    	if( actualNode instanceof DriverRootDataModel){
            return rootIcon;
    	}else if( actualNode instanceof DriverRootDataModel){
            return nodeIcon;
    	}else if( actualNode instanceof DriverFirefoxPropertyDataModel ){
    		return firefoxPropertyIcon;
    	}else if( actualNode instanceof DriverExplorerCapabilityDataModel ){
    		return explorerCapabilityIcon;            
    	}else if( actualNode instanceof DriverExplorerDataModel ){
            return explorerIcon;
    	}else if( actualNode instanceof DriverFirefoxDataModel ){
    		return firefoxIcon;
    	
    	}else if( actualNode instanceof DriverFolderDataModel){
    		if( expanded ){
    			return nodeOpenIcon;
    		}else{
    			return nodeClosedIcon;
    		}
    	}
    	
    	return null;
	}

	/**
	 * VIEW
	 */
	@Override
	public void doViewWhenSelectionChanged(DataModelAdapter selectedNode) {
	
		//Ha egyaltalan valamilyen egergombot benyomtam
		if( selectedNode instanceof DriverRootDataModel ){
			EmptyEditor emptyPanel = new EmptyEditor();								
			guiFrame.showEditorPanel( emptyPanel );
		
		}else if( selectedNode instanceof DriverFolderDataModel ){
			DriverNodeEditor driverNodeEditor = new DriverNodeEditor(this, (DriverFolderDataModel)selectedNode, EditMode.VIEW);
			guiFrame.showEditorPanel( driverNodeEditor);								
		
		}else if( selectedNode instanceof DriverExplorerDataModel ){
			DriverExplorerEditor driverExplorerEditor = new DriverExplorerEditor( this, (DriverExplorerDataModel)selectedNode, EditMode.VIEW );								
			guiFrame.showEditorPanel( driverExplorerEditor);				
						
		}else if( selectedNode instanceof DriverFirefoxDataModel ){
			DriverFirefoxEditor driverFirefoxEditor = new DriverFirefoxEditor( this, (DriverFirefoxDataModel)selectedNode, EditMode.VIEW );								
			guiFrame.showEditorPanel( driverFirefoxEditor);		
			
		}else if( selectedNode instanceof DriverFirefoxPropertyDataModel ){
			DriverFirefoxPropertyEditor driverFirefoxPropertyEditor = new DriverFirefoxPropertyEditor( this, (DriverFirefoxPropertyDataModel)selectedNode, EditMode.VIEW );								
			guiFrame.showEditorPanel( driverFirefoxPropertyEditor);		

		}else if( selectedNode instanceof DriverExplorerCapabilityDataModel ){
			DriverExplorerCapabilityEditor driverExplorerCapabilityEditor = new DriverExplorerCapabilityEditor( this, (DriverExplorerCapabilityDataModel)selectedNode, EditMode.VIEW );								
			guiFrame.showEditorPanel( driverExplorerCapabilityEditor);		

		}			
	}

	/**
	 * MODIFY
	 */
	@Override
	public void doModifyWithPopupEdit(DataModelAdapter selectedNode) {
		
		if( selectedNode instanceof DriverFolderDataModel ){
							
			DriverNodeEditor driverNodeEditor = new DriverNodeEditor( this, (DriverFolderDataModel)selectedNode, DataEditor.EditMode.MODIFY );								
			guiFrame.showEditorPanel( driverNodeEditor);								
								
		}else if( selectedNode instanceof DriverExplorerDataModel ){
								
			DriverExplorerEditor driverExplorerEditor = new DriverExplorerEditor( this, (DriverExplorerDataModel)selectedNode, DataEditor.EditMode.MODIFY );								
			guiFrame.showEditorPanel( driverExplorerEditor);		
								
		}else if( selectedNode instanceof DriverFirefoxDataModel ){

			DriverFirefoxEditor driverFirefoxEditor = new DriverFirefoxEditor( this, (DriverFirefoxDataModel)selectedNode, DataEditor.EditMode.MODIFY );								
			guiFrame.showEditorPanel( driverFirefoxEditor);		

		}else if( selectedNode instanceof DriverFirefoxPropertyDataModel ){
			DriverFirefoxPropertyEditor driverFirefoxPropertyEditor = new DriverFirefoxPropertyEditor( this, (DriverFirefoxPropertyDataModel)selectedNode, EditMode.MODIFY );								
			guiFrame.showEditorPanel( driverFirefoxPropertyEditor);		

		}else if( selectedNode instanceof DriverExplorerCapabilityDataModel ){
			DriverExplorerCapabilityEditor driverExplorerCapabilityEditor = new DriverExplorerCapabilityEditor( this, (DriverExplorerCapabilityDataModel)selectedNode, EditMode.MODIFY );								
			guiFrame.showEditorPanel( driverExplorerCapabilityEditor);		
								
		}	
	}

	@Override
	public void doPopupInsert( final JPopupMenu popupMenu, final DataModelAdapter selectedNode) {

		//
		// Csomopont eseten
		//
		if( selectedNode instanceof DriverFolderDataModel ){

			//Insert Folder
			JMenuItem insertNodeMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.folder") );
			insertNodeMenu.setActionCommand( ActionCommand.CAPTURE.name());
			insertNodeMenu.addActionListener( new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					
					DriverNodeEditor driverNodeEditor = new DriverNodeEditor( DriverTree.this, (DriverFolderDataModel)selectedNode );								
					guiFrame.showEditorPanel( driverNodeEditor);								
				
				}
			});
			popupMenu.add ( insertNodeMenu );

			//Insert Explorer
			JMenuItem insertExplorerMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.driver.explorer") );
			insertExplorerMenu.setActionCommand( ActionCommand.CAPTURE.name());
			insertExplorerMenu.addActionListener( new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					
					DriverExplorerEditor driverExplorerEditor = new DriverExplorerEditor( DriverTree.this, (DriverFolderDataModel)selectedNode );								
					guiFrame.showEditorPanel( driverExplorerEditor);								
				
				}
			});
			popupMenu.add ( insertExplorerMenu );
			
			//Insert Firefox
			JMenuItem insertFirefoxMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.driver.firefox") );
			insertFirefoxMenu.setActionCommand( ActionCommand.CAPTURE.name());
			insertFirefoxMenu.addActionListener( new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					
					DriverFirefoxEditor baseNodeEditor = new DriverFirefoxEditor( DriverTree.this, (DriverFolderDataModel)selectedNode );								
					guiFrame.showEditorPanel( baseNodeEditor);								
				
				}
			});
			popupMenu.add ( insertFirefoxMenu );
		}		
		
		//
		// Firefox eseten
		//		
		if( selectedNode instanceof DriverFirefoxDataModel ){

			//Insert Property
			JMenuItem insertElementMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.driver.firefox.property") );
			insertElementMenu.setActionCommand( ActionCommand.CAPTURE.name());
			insertElementMenu.addActionListener( new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					
					DriverFirefoxPropertyEditor driverFirefoxPropertyEditor = new DriverFirefoxPropertyEditor( DriverTree.this, (DriverFirefoxDataModel)selectedNode );								
					guiFrame.showEditorPanel( driverFirefoxPropertyEditor);								
				
				}
			});
			popupMenu.add ( insertElementMenu );
		
		}
		
		//
		// Explorer eseten
		//		
		if( selectedNode instanceof DriverExplorerDataModel ){

			//Insert Capability
			JMenuItem insertElementMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.driver.explorer.capability") );
			insertElementMenu.setActionCommand( ActionCommand.CAPTURE.name());
			insertElementMenu.addActionListener( new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					
					DriverExplorerCapabilityEditor driverExplorerCapabilityEditor = new DriverExplorerCapabilityEditor( DriverTree.this, (DriverExplorerDataModel)selectedNode );								
					guiFrame.showEditorPanel( driverExplorerCapabilityEditor);								
				
				}
			});
			popupMenu.add ( insertElementMenu );
		
		}
		
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
						DriverTree.this.setSelectionRow(selectedRow - 1);
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
					
				DriverNodeEditor driverNodeEditor = new DriverNodeEditor( DriverTree.this, (DriverFolderDataModel)selectedNode );								
				guiFrame.showEditorPanel( driverNodeEditor);								
			
			}
		});
		popupMenu.add ( insertNodeMenu );			
		
	}

	@Override
	public boolean possibleHierarchy(DefaultMutableTreeNode draggedNode, Object targetObject) {

		if( draggedNode.equals( targetObject )){
			return false;
		
		//Node elhelyezese Node-ba vagy Root-ba
		}else if( draggedNode instanceof DriverFolderDataModel && targetObject instanceof DriverFolderDataModel ){
			return true;
		
		//Firefox elhelyezese Node-ba de nem Root-ba
		}else if( draggedNode instanceof DriverFirefoxDataModel && targetObject instanceof DriverFolderDataModel && !( targetObject instanceof DriverRootDataModel ) ){
			return true;
			
		//Firefox property elhelyezese Firefox-ban
		}else if( draggedNode instanceof DriverFirefoxPropertyDataModel && targetObject instanceof DriverFirefoxDataModel ){
			return true;
		
		//Explorer elhelyezese Node-ba de nem Root-ba
		}else if( draggedNode instanceof DriverExplorerDataModel && targetObject instanceof DriverFolderDataModel && !( targetObject instanceof DriverRootDataModel ) ){
			return true;

		//Explorer property elhelyezese Explorer-ben
		}else if( draggedNode instanceof DriverExplorerCapabilityDataModel && targetObject instanceof DriverExplorerDataModel ){
			return true;
		
		}
					
		return false;
	}

	@Override
	public void doDuplicate(JPopupMenu popupMenu, DataModelAdapter selectedNode, int selectedRow, DefaultTreeModel totalTreeModel) {
		//NO DUPLICATION ENABLED
	}

}
