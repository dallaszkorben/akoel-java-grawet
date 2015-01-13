package hu.akoel.grawit;

import java.util.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.tree.*;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.*;
import java.awt.image.BufferedImage;

public class DnDTree extends JTree {

     Insets autoscrollInsets = new Insets(20, 20, 20, 20); // insets

     public DnDTree(DefaultMutableTreeNode root) {
          setAutoscrolls(true);
          DefaultTreeModel treemodel = new  DefaultTreeModel(root);
          setModel(treemodel);
          setRootVisible(true); 
          setShowsRootHandles(false);//to show the root icon
          getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION); //set single selection for the Tree
          setEditable(false);
          new DefaultTreeTransferHandler(this, DnDConstants.ACTION_COPY_OR_MOVE);
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

     public static DefaultMutableTreeNode makeDeepCopy(DefaultMutableTreeNode node) {
          DefaultMutableTreeNode copy = new DefaultMutableTreeNode(node.getUserObject());
          for (Enumeration e = node.children(); e.hasMoreElements();) {     
               copy.add(makeDeepCopy((DefaultMutableTreeNode)e.nextElement()));
          }
          return(copy);
     }

     public static DefaultMutableTreeNode createTree() {
          DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
          DefaultMutableTreeNode node1 = new DefaultMutableTreeNode("node1");
          DefaultMutableTreeNode node2 = new DefaultMutableTreeNode("node2");     
          root.add(node1);
          root.add(node2);
          node1.add(new DefaultMutableTreeNode("sub1_1"));               
          node1.add(new DefaultMutableTreeNode("sub1_2"));          
          node1.add(new DefaultMutableTreeNode("sub1_3"));
          node2.add(new DefaultMutableTreeNode("sub2_1"));               
          node2.add(new DefaultMutableTreeNode("sub2_2"));          
          node2.add(new DefaultMutableTreeNode("sub2_3"));
          return(root);
     }
     
     public static void main(String[] args) { 
          try {
               UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
               JFrame frame = new JFrame();
               Container contentPane  = frame.getContentPane();
               contentPane.setLayout(new GridLayout(1,2));
               DefaultMutableTreeNode root1 = DnDTree.createTree();
               DnDTree tree1 = new DnDTree(root1);
               DefaultMutableTreeNode root2 = DnDTree.createTree();
               DnDTree tree2 = new DnDTree(root2);
               contentPane.add(new JScrollPane(tree1));
               contentPane.add(new JScrollPane(tree2));
               frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
               frame.setSize(400,400);
               frame.setVisible(true);
          } 
          catch (Exception e) {
               System.out.println(e);
          }
     }
}



























class DefaultTreeTransferHandler extends AbstractTreeTransferHandler {

    public DefaultTreeTransferHandler(DnDTree tree, int action) {
         super(tree, action, true);
    }

    public boolean canPerformAction(DnDTree target, DefaultMutableTreeNode draggedNode, int action, Point location) {
         TreePath pathTarget = target.getPathForLocation(location.x, location.y);
         if (pathTarget == null) {
              target.setSelectionPath(null);
              return(false);
         }
         target.setSelectionPath(pathTarget);
         if(action == DnDConstants.ACTION_COPY) {
              return(true);
         }
         else
         if(action == DnDConstants.ACTION_MOVE) {     
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

    public boolean executeDrop(DnDTree target, DefaultMutableTreeNode draggedNode, DefaultMutableTreeNode newParentNode, int action) { 
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
















abstract class AbstractTreeTransferHandler implements DragGestureListener, DragSourceListener, DropTargetListener {

    private DnDTree tree;
    private DragSource dragSource; // dragsource
    private DropTarget dropTarget; //droptarget
    private static DefaultMutableTreeNode draggedNode; 
    private DefaultMutableTreeNode draggedNodeParent; 
    private static BufferedImage image = null; //buff image
    private Rectangle rect2D = new Rectangle();
    private boolean drawImage;

    protected AbstractTreeTransferHandler(DnDTree tree, int action, boolean drawIcon) {
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
         tree.paintImmediately(rect2D.getBounds());
         rect2D.setRect((int) pt.getX(),(int) pt.getY(),image.getWidth(),image.getHeight());
         tree.getGraphics().drawImage(image,(int) pt.getX(),(int) pt.getY(),tree);
    }

    private final void clearImage() {
         tree.paintImmediately(rect2D.getBounds());
    }

    public abstract boolean canPerformAction(DnDTree target, DefaultMutableTreeNode draggedNode, int action, Point location);

    public abstract boolean executeDrop(DnDTree tree, DefaultMutableTreeNode draggedNode, DefaultMutableTreeNode newParentNode, int action);
}










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