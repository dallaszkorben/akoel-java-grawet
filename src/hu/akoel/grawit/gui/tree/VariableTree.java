package hu.akoel.grawit.gui.tree;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.TransferHandler;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

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
		
		
		
		/*
		this.setDragEnabled(true);
		final DefaultTreeModel model = (DefaultTreeModel)this.getModel();
		this.setTransferHandler(new TransferHandler() {
	          public boolean canImport(TransferHandler.TransferSupport support) {
	              if (!support.isDataFlavorSupported(DataFlavor.stringFlavor) ||
	                  !support.isDrop()) {
	                return false;
	              }

	              JTree.DropLocation dropLocation = (JTree.DropLocation)support.getDropLocation();

	              return dropLocation.getPath() != null;
	            }

	            public boolean importData(TransferHandler.TransferSupport support) {
	              if (!canImport(support)) {
	                return false;
	              }

	              JTree.DropLocation dropLocation = (JTree.DropLocation)support.getDropLocation();

	              TreePath path = dropLocation.getPath();

	              Transferable transferable = support.getTransferable();

	              String transferData;
	              try {
	                transferData = (String)transferable.getTransferData( DataFlavor.stringFlavor);
	              } catch (IOException e) {
	                return false;
	              } catch (UnsupportedFlavorException e) {
	                return false;
	              }

	              int childIndex = dropLocation.getChildIndex();
	              if (childIndex == -1) {
	                childIndex = model.getChildCount(path.getLastPathComponent());
	              }

	              DefaultMutableTreeNode newNode = 
	                new DefaultMutableTreeNode(transferData);
	              DefaultMutableTreeNode parentNode =
	                (DefaultMutableTreeNode)path.getLastPathComponent();
	              model.insertNodeInto(newNode, parentNode, childIndex);

	              TreePath newPath = path.pathByAddingChild(newNode);
	              VariableTree.this.makeVisible(newPath);
	              VariableTree.this.scrollRectToVisible(VariableTree.this.getPathBounds(newPath));

	              return true;
	            }
	          });
		
		
		*/
		
		
		
		
		
		
		
		
		
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
			JMenuItem insertElementMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.variable.element") );
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
							"Valóban torolni kívánod a(z) " + selectedNode.getName() + " nevü " + selectedNode.getNodeTypeToShow() + "-t ?",
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
		JMenuItem insertElementMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.variable.element" ) );
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






/**
 * 
 * @author afoldvarszky
 *
 */
class JTreeTransferHandler extends TransferHandler{

	private static final long serialVersionUID = 6312372741671647529L;
	
	protected DefaultTreeModel tree;	 

	public JTreeTransferHandler(VariableTree tree){
		super();
		this.tree = (DefaultTreeModel) tree.getModel();
	}
 
	@Override
	public int getSourceActions(JComponent c){
		return TransferHandler.MOVE;
	}
/*
	@Override
	protected Transferable createTransferable(JComponent c){
		if (c instanceof VariableTree){
			return new DnDTreeList((( VariableTree) c).getSelection());
		}
		else{
			return null;
		}
	}
 */
	@Override
	protected void exportDone(JComponent c, Transferable t, int action)	{
		if (action == TransferHandler.MOVE)	{
/*			
			// we need to remove items imported from the appropriate source.
			try	{
				// get back the list of items that were transfered
				ArrayList<TreePath> list = ((DnDTreeList) t
						.getTransferData(DnDTreeList.DnDTreeList_FLAVOR)).getNodes();
				for (int i = 0; i < list.size(); i++){
					// remove them
					this.tree.removeNodeFromParent((DnDNode) list.get(i).getLastPathComponent());
				}
			}
			catch (UnsupportedFlavorException exception){
				// for debugging purposes (and to make the compiler happy). In
				// theory, this shouldn't be reached.
				exception.printStackTrace();
			}
			catch (IOException exception){
				// for debugging purposes (and to make the compiler happy). In
				// theory, this shouldn't be reached.
				exception.printStackTrace();
			}
*/			
		}
	}
 
	@Override
	public boolean canImport(TransferSupport supp){
		
		supp.setShowDropLocation(true);
		if (supp.isDataFlavorSupported(DataFlavor.stringFlavor)){

			TreePath dropPath = ((JTree.DropLocation) supp.getDropLocation()).getPath();

			if (dropPath == null){
				System.out.println("Drop path somehow came out null");
				return false;
			}
			
			if ( dropPath.getLastPathComponent() instanceof VariableNodeDataModel )	{
System.err.println("can import");
/*				try	{
					// using the node-defined checker, see if that node will
					// accept
					// every selected node as a child.
					DnDNode parent = (DnDNode) dropPath.getLastPathComponent();
					ArrayList<TreePath> list = ((DnDTreeList) supp.getTransferable()
							.getTransferData(DnDTreeList.DnDTreeList_FLAVOR)).getNodes();
					for (int i = 0; i < list.size(); i++)	{
						if (parent.getAddIndex((DnDNode) list.get(i).getLastPathComponent()) < 0){
							return false;
						}
					}
 
					return true;
				}
				catch (UnsupportedFlavorException exception){
					// Don't allow dropping of other data types. As of right
					// now,
					// only DnDNode_FLAVOR and DnDTreeList_FLAVOR are supported.
					exception.printStackTrace();
				}
				catch (IOException exception)	{
					// to make the compiler happy.
					exception.printStackTrace();
				}
*/				
			}
		}
		// something prevented this import from going forward
		return false;
	}
 
	@Override
	public boolean importData(TransferSupport supp){
		if (this.canImport(supp)){
System.err.println("importData");			
 /*
			try	{
				// Fetch the data to transfer
				Transferable t = supp.getTransferable();
				ArrayList<TreePath> list = ((DnDTreeList) t
						.getTransferData(DnDTreeList.DnDTreeList_FLAVOR)).getNodes();
				// Fetch the drop location
				TreePath loc = ((javax.swing.JTree.DropLocation) supp.getDropLocation()).getPath();
				// Insert the data at this location
				for (int i = 0; i < list.size(); i++){
					this.tree.insertNodeInto((DnDNode) list.get(i).getLastPathComponent(),
							(DnDNode) loc.getLastPathComponent(), ((DnDNode) loc
									.getLastPathComponent()).getAddIndex((DnDNode) list.get(i)
									.getLastPathComponent()));
				}
				// success!
				return true;
			}
			catch (UnsupportedFlavorException e){

				e.printStackTrace();
			}
			catch (IOException e){

				e.printStackTrace();
			}
*/			
		}

		return false;
	}
	
}
