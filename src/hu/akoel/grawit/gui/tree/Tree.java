package hu.akoel.grawit.gui.tree;

import java.awt.AlphaComposite;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Enumeration;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.DataModelInterface;
import hu.akoel.grawit.enums.ActionCommand;
import hu.akoel.grawit.gui.GUIFrame;
import hu.akoel.grawit.gui.editor.EmptyEditor;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

public abstract class Tree extends JTree{

	private static final long serialVersionUID = -3929758449314068678L;
	
	private GUIFrame guiFrame;
	
	private DataModelInterface selectedNode;
	
	private TreeMouseListener treeMouseListener;
	
	private boolean needPopupUp = true;
	private boolean needPopupDown = true;
	private boolean needPopupModify = true;
	
	Insets autoscrollInsets = new Insets(20, 20, 20, 20);

	public Tree( GUIFrame guiFrame, DataModelInterface rootDataModel ){
	
		super( new DefaultTreeModel(rootDataModel) );
		
		treeModel = (DefaultTreeModel)this.getModel();
		
		this.guiFrame = guiFrame;
		
		//A Root-nak csak az ikonja jelenik meg
		this.setShowsRootHandles(false);
		//this.setRootVisible(false);
		
		//Csak egy elem lehet kivalasztva
		this.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);		
		
		/**
		 * Ikonokat helyezek el az egyes csomopontok ele
		 */
		this.setCellRenderer(new DefaultTreeCellRenderer() {

			private static final long serialVersionUID = 1323618892737458100L;

			@Override
		    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean isLeaf, int row, boolean focused) {
		    	Component c = super.getTreeCellRendererComponent(tree, value, selected, expanded, isLeaf, row, focused);
		    	

		    	if( null != value && value instanceof DataModelInterface ){
		    		
			    	//Felirata a NODE-nak		    	
		    		setText( ((DataModelInterface)value).getName() );
		    		    	
		    		//Ikon a NODE-nak
		    		setIcon( Tree.this.getIcon( (DataModelInterface)value, expanded ) );
		    	}
		 
		    	return c;
		    }
		});
					
		/**
		 * A eger benyomasara reagalok
		 */
		treeMouseListener = new TreeMouseListener();
		this.addMouseListener( treeMouseListener );
		this.addTreeSelectionListener( new SelectionChangedListener() );
	
		new DefaultTreeTransferHandler(this, DnDConstants.ACTION_MOVE);
		//this.setDragEnabled( true );
	}
	
	/**
	 * A parameterkent megadott Node-hoz rendel egy ikont
	 * 
	 * @param actualNode
	 * @return
	 */
	public abstract ImageIcon getIcon( DataModelInterface actualNode, boolean expanded );
	
	public abstract void doViewWhenSelectionChanged( DataModelInterface selectedNode );
	
	public abstract void doModifyWithPopupEdit( DataModelInterface selectedNode );
		
	public abstract void doPopupInsert( JPopupMenu popupMenu, DataModelInterface selectedNode );
	
	public abstract void doPopupDelete( final JPopupMenu popupMenu, DataModelInterface selectedNode, int selectedRow, DefaultTreeModel totalTreeModel );
	
	public abstract void doPopupRootInsert( JPopupMenu popupMenu, DataModelInterface selectedNode );
	
	public abstract boolean possibleHierarchy( DefaultMutableTreeNode draggedNode, Object dropObject );
	
	/**
	 * 
	 * Ertesiti a tree-t, hogy valtozas tortent
	 * 
	 */
	public void changed(){

		TreePath path = new TreePath(selectedNode.getPath());
		boolean isExpanded = this.isExpanded( path );
		
		//Ujratolti a modellt
		((DefaultTreeModel)this.getModel()).reload();

		//this.setSelectionPath(pathToSelect);

		//Kivalasztja azt a csomopontot ami eleve ki volt valasztva
		this.setSelectionPath( path );
		
		//Es ha ki volt terjesztve
		if( isExpanded ){
			
			//Akkor kiterjeszti
			this.expandPath( path );
		}
		
	}
	
	public void removePopupModify(){
		needPopupModify = false;
	}
	
	public void removePopupUp(){
		needPopupUp = false;
	}
	
	public void removePopupDown(){
		needPopupDown = false;
	}
	
/*	public void setEnablePopupMenuEdit( boolean enable ){
		
		this.removeMouseListener( treeMouseListener );
		
		if( enable ){
			this.addMouseListener(treeMouseListener );
		}
	}
*/
	
	/**
	 * Azt figyeli, hogy barmi miatt megvaltozott-e a kivalasztott elem.
	 * Ez lehet eger vagy billentyu, vagy akar maga a program
	 * 
	 * @author afoldvarszky
	 *
	 */
	class SelectionChangedListener implements TreeSelectionListener{

		@Override
		public void valueChanged(TreeSelectionEvent e) {
			
			//System.err.println("changed to: " + e.getNewLeadSelectionPath());
			//A kivalasztott NODE			
			
			//Nincs kivalasztva semmi
			if( null == e.getNewLeadSelectionPath() ){
				EmptyEditor emptyPanel = new EmptyEditor();								
				guiFrame.showEditorPanel( emptyPanel);
			}else{
			
				selectedNode = (DataModelInterface)e.getNewLeadSelectionPath().getLastPathComponent();
			
				doViewWhenSelectionChanged( selectedNode );
				

			}
		}		
	}
		
	
	/**
	 * A jobb-eger gomb benyomasara reagalo osztaly
	 * 
	 * @author akoel
	 *
	 */
	class TreeMouseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			
			//A kivalasztott NODE			
			selectedNode = (DataModelInterface)Tree.this.getLastSelectedPathComponent();
		
			//Ha jobb-eger gombot nyomtam - Akkor popup menu jelenik meg
			if (SwingUtilities.isRightMouseButton(e)) {

				//A kivalasztott elem sora - kell a sor kiszinezesehez es a PopUp menu poziciojahoz
				int row = Tree.this.getClosestRowForLocation(e.getX(), e.getY());
				//int row = BaseTree.this.getRowForLocation(e.getX(), e.getY());
				
				//Kiszinezi a sort
				Tree.this.setSelectionRow(row);

				//Jelzi, hogy mostantol, hiaba nem bal-egerrel valasztottam ki a node-ot, megis kivalasztott lesz
				selectedNode = (DataModelInterface)Tree.this.getLastSelectedPathComponent();

				//Letrehozza a PopUpMenu-t
				//PopUpMenu popUpMenu = new PopUpMenu( row, node, path );
				PopUpMenu popUpMenu = new PopUpMenu( row );
				
				//Megjeleniti a popup menut
				popUpMenu.show( e.getComponent(), e.getX(), e.getY() );
				
			}		
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}	 
	}	
	
	/**
	 * A jobb-eger gomb hatasara megjeleno menu
	 * 
	 * @author akoel
	 *
	 */
	class PopUpMenu extends JPopupMenu{
		
		private static final long serialVersionUID = -2476473336416059356L;

		private DefaultMutableTreeNode parentNode;
		private DataModelInterface selectedNode;
		private TreePath selectedPath;
		private DefaultTreeModel totalTreeModel;
		private int selectedIndexInTheNode;
		private int elementsInTheNode;
		
		//public PopUpMenu( final int selectedRow, final DefaultMutableTreeNode selectedNode, final TreePath selectedPath ){
		public PopUpMenu( final int selectedRow ){
			super();

			//A teljes fastruktura modell-je			
			totalTreeModel = (DefaultTreeModel)Tree.this.getModel();

			//A kivalasztott NODE			
			selectedNode = (DataModelInterface)Tree.this.getLastSelectedPathComponent();

			//A kivalasztott node-ig vezeto PATH
			selectedPath = Tree.this.getSelectionPath();	

			//A kivalasztott node fastrukturaban elfoglalt melyseget adja vissza. 1 jelenti a gyokeret
			int pathLevel = selectedPath.getPathCount();

			//Jelzem, hogy nincs kivalasztva meg
			selectedIndexInTheNode = -1;

			//Ha NEM ROOT volt kivalasztva, akkor a FEL es LE elemeket helyezem el
			if( pathLevel > 1 ){

				//A kivalasztott node szulo node-ja
				parentNode = (DefaultMutableTreeNode)selectedPath.getPathComponent( pathLevel - 2 );

				//Hany elem van a szulo csomopontjaban
				elementsInTheNode = totalTreeModel.getChildCount( parentNode );
				
				//A kivalasztott elem sorszama a szulo node-jaban
				selectedIndexInTheNode = totalTreeModel.getIndexOfChild( parentNode, selectedNode );
				
				//
				// Felfele mozgat
				// Ha nem a legelso elem
				//
				if( needPopupUp && selectedIndexInTheNode >= 1 ){
					
					//Akkor mozoghat felfele, letrehozhatom a fel menuelemet
					JMenuItem upMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.up") );
					upMenu.setActionCommand( ActionCommand.UP.name());
					upMenu.addActionListener( new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							
							if( selectedIndexInTheNode >= 1 ) {
								
								//Torolni kell a fastrukturabol az eredeti sort
								totalTreeModel.removeNodeFromParent(selectedNode);
								
								//Majd el kell helyezni eggyel feljebb
								totalTreeModel.insertNodeInto(selectedNode, (DefaultMutableTreeNode)parentNode, selectedIndexInTheNode - 1);    // move the node
								
								//Ujra ki kell szinezni az eredetileg kivalasztott sort
								Tree.this.setSelectionRow(selectedRow - 1);
							}							
						}
					});
					this.add ( upMenu );
					
				}
				
				//
				// Lefele mozgat
				// Ha nem a legutolso elem
				//
				if( needPopupDown && selectedIndexInTheNode < elementsInTheNode - 1 ){
					
					//Akkor mozoghat lefele, letrehozhatom a le nemuelement
					JMenuItem downMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.down")  );
					downMenu.setActionCommand( ActionCommand.DOWN.name() );
					downMenu.addActionListener( new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							
							if(selectedIndexInTheNode < elementsInTheNode - 1 ) {
								
								//Torolni kell a fastrukturabol az eredeti sort
								totalTreeModel.removeNodeFromParent(selectedNode);
								
								//Majd el kell helyezni eggyel lejebb
								totalTreeModel.insertNodeInto(selectedNode, (DefaultMutableTreeNode)parentNode, selectedIndexInTheNode + 1);    
								
								//Ujra ki kell szinezni az eredetileg kivalasztott sort
								Tree.this.setSelectionRow(selectedRow + 1);
							}	
							
						}
					});
					this.add ( downMenu );
					
				}
				
				//
				//Szerkesztes
				//
				if( needPopupModify ){
				
					JMenuItem editMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.edit") );
					editMenu.setActionCommand( ActionCommand.EDIT.name());
					editMenu.addActionListener( new ActionListener() {
					
						@Override
						public void actionPerformed(ActionEvent e) {					

							doModifyWithPopupEdit( selectedNode );
						
						}
					});
					this.add ( editMenu );
				}
				
				doPopupInsert( this, selectedNode );
				
				doPopupDelete( this, selectedNode, selectedRow, totalTreeModel );
				
			//ROOT volt kivalasztva
			}else{
				
				doPopupRootInsert( this, selectedNode );
				
			}

		}
	
	}
	
	/**
	 * 
	 * Masolatot keszit a parameterkent megkapott Node-rol es azok gyermekeirol rekurziven
	 * 
	 * @param node
	 * @return
	 */
    public static DefaultMutableTreeNode makeDeepCopy(DefaultMutableTreeNode node) {
        DefaultMutableTreeNode copy = new DefaultMutableTreeNode(node.getUserObject());
        for (Enumeration e = node.children(); e.hasMoreElements();) {     
             copy.add(makeDeepCopy((DefaultMutableTreeNode)e.nextElement()));
        }
        return(copy);
   }
    
    public void autoscroll(Point cursorLocation)  {
        Insets insets = getAutoscrollInsets();
        Rectangle outer = getVisibleRect();
        Rectangle inner = new Rectangle(outer.x+insets.left, outer.y+insets.top, outer.width-(insets.left+insets.right), outer.height-(insets.top+insets.bottom));
        if (!inner.contains(cursorLocation))  {
             Rectangle scrollRect = new Rectangle(cursorLocation.x-insets.left, cursorLocation.y-insets.top,     insets.left+insets.right, insets.top+insets.bottom);
             scrollRectToVisible(scrollRect);
        }
   }
    
    public Insets getAutoscrollInsets()  {
        return (autoscrollInsets);
   }
}





/**
 * 
 * A drag and drop kezeleset vegzo osztaly
 * 
 * @author akoel
 *
 */
class DefaultTreeTransferHandler extends AbstractTreeTransferHandler {
	private Tree tree;
	
    public DefaultTreeTransferHandler(Tree tree, int action) {
         super(tree, action, true);
         this.tree = tree;
    }
    
    public boolean canPerformAction(Tree target, DefaultMutableTreeNode draggedNode, int action, Point location) {
         TreePath pathTarget = target.getPathForLocation(location.x, location.y);
         if (pathTarget == null) {
              target.setSelectionPath(null);
              return(false);
         }
         target.setSelectionPath(pathTarget);
      
         if( !tree.possibleHierarchy( draggedNode, pathTarget.getLastPathComponent() ) ){
        	 return false;
         }
         
         if(action == DnDConstants.ACTION_COPY) {
              return(true);
         }else if(action == DnDConstants.ACTION_MOVE) {     
              DefaultMutableTreeNode parentNode =(DefaultMutableTreeNode)pathTarget.getLastPathComponent();                    
              if (draggedNode.isRoot() || parentNode == draggedNode.getParent() || draggedNode.isNodeDescendant(parentNode)) {                         
                   return(false);     
              }
              else {
                   return(true);
              }                     
         }
         else {          
              return(false);     
         }
    }

    public boolean executeDrop(Tree target, DefaultMutableTreeNode draggedNode, DefaultMutableTreeNode newParentNode, int action) { 
         if (action == DnDConstants.ACTION_COPY) {
              DefaultMutableTreeNode newNode = target.makeDeepCopy(draggedNode);
              ((DefaultTreeModel)target.getModel()).insertNodeInto(newNode,newParentNode,newParentNode.getChildCount());
              TreePath treePath = new TreePath(newNode.getPath());
              target.scrollPathToVisible(treePath);
              target.setSelectionPath(treePath);     
              return(true);
         }
         if (action == DnDConstants.ACTION_MOVE) {
              draggedNode.removeFromParent();
              ((DefaultTreeModel)target.getModel()).insertNodeInto(draggedNode,newParentNode,newParentNode.getChildCount());
              TreePath treePath = new TreePath(draggedNode.getPath());
              target.scrollPathToVisible(treePath);
              target.setSelectionPath(treePath);
              return(true);
         }
         return(false);
    }
}
















/**
 * Fa csomopontjanak athelyezeset vegzo absztrakt osztaly
 * 
 * @author akoel
 *
 */
abstract class AbstractTreeTransferHandler implements DragGestureListener, DragSourceListener, DropTargetListener {

    private Tree tree;
    private DragSource dragSource; // dragsource
    private DropTarget dropTarget; //droptarget
    private static DefaultMutableTreeNode draggedNode; 
    private DefaultMutableTreeNode draggedNodeParent; 
    private static BufferedImage image = null; //buff image
    private Rectangle rect2D = new Rectangle();
    private boolean drawImage;

    protected AbstractTreeTransferHandler(Tree tree, int action, boolean drawIcon) {
         this.tree = tree;
         drawImage = drawIcon;
         dragSource = new DragSource();
         dragSource.createDefaultDragGestureRecognizer(tree, action, this);
         dropTarget = new DropTarget(tree, action, this);
    }

    /* Methods for DragSourceListener */
    public void dragDropEnd(DragSourceDropEvent dsde) {
         if (dsde.getDropSuccess() && dsde.getDropAction()==DnDConstants.ACTION_MOVE && draggedNodeParent != null) {
              ((DefaultTreeModel)tree.getModel()).nodeStructureChanged(draggedNodeParent);                    
         }
    }
    public final void dragEnter(DragSourceDragEvent dsde)  {
         int action = dsde.getDropAction();
         if (action == DnDConstants.ACTION_COPY)  {
              dsde.getDragSourceContext().setCursor(DragSource.DefaultCopyDrop);
         } 
         else {
              if (action == DnDConstants.ACTION_MOVE) {
                   dsde.getDragSourceContext().setCursor(DragSource.DefaultMoveDrop);
              } 
              else {
                   dsde.getDragSourceContext().setCursor(DragSource.DefaultMoveNoDrop);
              }
         }
    }
    public final void dragOver(DragSourceDragEvent dsde) {
         int action = dsde.getDropAction();
         if (action == DnDConstants.ACTION_COPY) {
              dsde.getDragSourceContext().setCursor(DragSource.DefaultCopyDrop);
         } 
         else  {
              if (action == DnDConstants.ACTION_MOVE) {
                   dsde.getDragSourceContext().setCursor(DragSource.DefaultMoveDrop);
              } 
              else  {
                   dsde.getDragSourceContext().setCursor(DragSource.DefaultMoveNoDrop);
              }
         }
    }
    public final void dropActionChanged(DragSourceDragEvent dsde)  {
         int action = dsde.getDropAction();
         if (action == DnDConstants.ACTION_COPY) {
              dsde.getDragSourceContext().setCursor(DragSource.DefaultCopyDrop);
         }
         else  {
              if (action == DnDConstants.ACTION_MOVE) {
                   dsde.getDragSourceContext().setCursor(DragSource.DefaultMoveDrop);
              } 
              else {
                   dsde.getDragSourceContext().setCursor(DragSource.DefaultMoveNoDrop);
              }
         }
    }
    public final void dragExit(DragSourceEvent dse) {
       dse.getDragSourceContext().setCursor(DragSource.DefaultMoveNoDrop);
    }     
         
    /* Methods for DragGestureListener */
    public final void dragGestureRecognized(DragGestureEvent dge) {
         TreePath path = tree.getSelectionPath(); 
         if (path != null) { 
              draggedNode = (DefaultMutableTreeNode)path.getLastPathComponent();
              draggedNodeParent = (DefaultMutableTreeNode)draggedNode.getParent();
              if (drawImage) {
                   Rectangle pathBounds = tree.getPathBounds(path); //getpathbounds of selectionpath
                   JComponent lbl = (JComponent)tree.getCellRenderer().getTreeCellRendererComponent(tree, draggedNode, false , tree.isExpanded(path),((DefaultTreeModel)tree.getModel()).isLeaf(path.getLastPathComponent()), 0,false);//returning the label
                   lbl.setBounds(pathBounds);//setting bounds to lbl
                   image = new BufferedImage(lbl.getWidth(), lbl.getHeight(), java.awt.image.BufferedImage.TYPE_INT_ARGB_PRE);//buffered image reference passing the label's ht and width
                   Graphics2D graphics = image.createGraphics();//creating the graphics for buffered image
                   graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));     //Sets the Composite for the Graphics2D context
                   lbl.setOpaque(false);
                   lbl.paint(graphics); //painting the graphics to label
                   graphics.dispose();                    
              }
              dragSource.startDrag(dge, DragSource.DefaultMoveNoDrop , image, new Point(0,0), new TransferableNode(draggedNode), this);               
         }      
    }

    /* Methods for DropTargetListener */

    public final void dragEnter(DropTargetDragEvent dtde) {
         Point pt = dtde.getLocation();
         int action = dtde.getDropAction();
         if (drawImage) {
              paintImage(pt);
         }
         if (canPerformAction(tree, draggedNode, action, pt)) {
              dtde.acceptDrag(action);               
         }
         else {
              dtde.rejectDrag();
         }
    }

    public final void dragExit(DropTargetEvent dte) {
         if (drawImage) {
              clearImage();
         }
    }

    public final void dragOver(DropTargetDragEvent dtde) {
         Point pt = dtde.getLocation();
         int action = dtde.getDropAction();
         tree.autoscroll(pt);
         if (drawImage) {
              paintImage(pt);
         }
         if (canPerformAction(tree, draggedNode, action, pt)) {
              dtde.acceptDrag(action);               
         }
         else {
              dtde.rejectDrag();
         }
    }

    public final void dropActionChanged(DropTargetDragEvent dtde) {
         Point pt = dtde.getLocation();
         int action = dtde.getDropAction();
         if (drawImage) {
              paintImage(pt);
         }
         if (canPerformAction(tree, draggedNode, action, pt)) {
              dtde.acceptDrag(action);               
         }
         else {
              dtde.rejectDrag();
         }
    }

    public final void drop(DropTargetDropEvent dtde) {
         try {
              if (drawImage) {
                   clearImage();
              }
              int action = dtde.getDropAction();
              Transferable transferable = dtde.getTransferable();
              Point pt = dtde.getLocation();
              if (transferable.isDataFlavorSupported(TransferableNode.NODE_FLAVOR) && canPerformAction(tree, draggedNode, action, pt)) {
                   TreePath pathTarget = tree.getPathForLocation(pt.x, pt.y);
                   DefaultMutableTreeNode node = (DefaultMutableTreeNode) transferable.getTransferData(TransferableNode.NODE_FLAVOR);
                   DefaultMutableTreeNode newParentNode =(DefaultMutableTreeNode)pathTarget.getLastPathComponent();
                   if (executeDrop(tree, node, newParentNode, action)) {
                        dtde.acceptDrop(action);                    
                        dtde.dropComplete(true);
                        return;                         
                   }
              }
              dtde.rejectDrop();
              dtde.dropComplete(false);
         }          
         catch (Exception e) {     
              System.out.println(e);
              dtde.rejectDrop();
              dtde.dropComplete(false);
         }     
    }
    
    private final void paintImage(Point pt) {
//    	tree.repaint(rect2D.getBounds());
    	tree.paintImmediately(rect2D.getBounds());
    	rect2D.setRect((int) pt.getX(),(int) pt.getY(),image.getWidth(),image.getHeight());
    	tree.getGraphics().drawImage(image,(int) pt.getX(),(int) pt.getY(),tree);
    }

    private final void clearImage() {
//    	tree.repaint(rect2D.getBounds());
    	tree.paintImmediately(rect2D.getBounds());
    }

    public abstract boolean canPerformAction(Tree target, DefaultMutableTreeNode draggedNode, int action, Point location);

    public abstract boolean executeDrop(Tree tree, DefaultMutableTreeNode draggedNode, DefaultMutableTreeNode newParentNode, int action);
}








/**
 * 
 * Mozgathato csomopontokat reprezentalo osztaly
 * 
 * @author akoel
 *
 */
class TransferableNode implements Transferable {
    public static final DataFlavor NODE_FLAVOR = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType, "Node");
    private DefaultMutableTreeNode node;
    private DataFlavor[] flavors = { NODE_FLAVOR };

    public TransferableNode(DefaultMutableTreeNode nd) {
         node = nd;
    }  

    public synchronized Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
         if (flavor == NODE_FLAVOR) {
              return node;
         }
         else {
              throw new UnsupportedFlavorException(flavor);     
         }               
    }

    public DataFlavor[] getTransferDataFlavors() {
         return flavors;
    }

    public boolean isDataFlavorSupported(DataFlavor flavor) {
         return Arrays.asList(flavors).contains(flavor);
    }
}
