package hu.akoel.grawit.gui.tree;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.enums.ActionCommand;
import hu.akoel.grawit.gui.GUIFrame;
import hu.akoel.grawit.gui.editor.EmptyEditor;
import hu.akoel.grawit.core.treenodedatamodel.DataModelAdapter;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
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
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Enumeration;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

public abstract class Tree extends JTree{

	private static final long serialVersionUID = -3929758449314068678L;
	
	private GUIFrame guiFrame;
	
	private DataModelAdapter selectedNode;
	
	private TreeMouseListener treeMouseListener;
	
	private boolean needPopupUp = true;
	private boolean needPopupDown = true;
	private boolean needPopupModify = true;
	private boolean needPopupModifyAtRoot = false;
	
	Insets autoscrollInsets = new Insets(20, 20, 20, 20);
	
	public Tree( GUIFrame guiFrame, DataModelAdapter rootDataModel ){
	
		super( new DefaultTreeModel(rootDataModel) );
		
		//CTRL-T - Node ki/be kapcsolasa
		KeyStroke ctrlTKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.CTRL_MASK, false);
		Action ctrlTAction = new AbstractAction() {
			private static final long serialVersionUID = -1790341706165622733L;
			public void actionPerformed(ActionEvent e) {
				if( null != selectedNode && selectedNode.isEnabledToTurnOnOff() ){
				
					if( selectedNode.isOn() ){
						selectedNode.setOn( false );						
					}else{
						selectedNode.setOn( true );
					}
					((DefaultTreeModel)Tree.this.getModel()).reload(selectedNode);
				}
			}
		};		
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(ctrlTKeyStroke, "CTRL-T");
		this.getActionMap().put("CTRL-T", ctrlTAction);			
		
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
		this.setCellRenderer(new MyTreeCellRenderer() );
					
		/**
		 * A eger benyomasara reagalok
		 */
		treeMouseListener = new TreeMouseListener();
		this.addMouseListener( treeMouseListener );
		this.addTreeSelectionListener( new SelectionChangedListener() );
	
		new TreeTransferHandler(this, DnDConstants.ACTION_MOVE, true );
		//this.setDragEnabled( true );
	}
	

	/**
	 * A parameterkent megadott Node-hoz rendel egy ikont
	 * 
	 * @param actualNode
	 * @return
	 */
	public abstract ImageIcon getIcon( DataModelAdapter actualNode, boolean expanded );
	
	public abstract void doViewWhenSelectionChanged( DataModelAdapter selectedNode );
	
	public abstract void doModifyWithPopupEdit( DataModelAdapter selectedNode );
		
	public abstract void doPopupInsert( JPopupMenu popupMenu, DataModelAdapter selectedNode );
	
	public abstract void doPopupDelete( final JPopupMenu popupMenu, DataModelAdapter selectedNode, int selectedRow, DefaultTreeModel totalTreeModel );
	
	public abstract void doDuplicate( final JPopupMenu popupMenu, DataModelAdapter selectedNode, int selectedRow, DefaultTreeModel totalTreeModel );
	
	public abstract void doPopupRootInsert( JPopupMenu popupMenu, DataModelAdapter selectedNode );
	
	public abstract boolean possibleHierarchy( DefaultMutableTreeNode draggedNode, Object targetObject );

	public ImageIcon getIconOff( DataModelAdapter actualNode, boolean expanded ){
		return getIcon(actualNode, expanded);
	}

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
	
	public void enablePopupModifyAtRoot(){
		this.needPopupModifyAtRoot = true;
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
	
	class MyTreeCellRenderer extends JLabel implements TreeCellRenderer {

		private static final long serialVersionUID = 1323618892737458100L;
		
		@Override
	    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean isLeaf, int row, boolean focused) {

			//Ha nem engedelyezett a Ki/Be kapcsolas vagy ha engedelyezett, de be van kapcsolava
			if( !((DataModelAdapter)value).isEnabledToTurnOnOff() || ((DataModelAdapter)value).isOn() ){
    			
				setIcon( Tree.this.getIcon( (DataModelAdapter)value, expanded ) );		
    			setForeground( Color.black );	   
    			
    		//Ha engedelyezett a Ki/Be kapcsolas es ki van kapcsolva	
    		}else{
    			
    			setIcon( Tree.this.getIconOff( (DataModelAdapter)value, expanded ) );
    			setForeground( Color.gray );
   			
    		}				
							
			//setText( ((DataModelAdapter)value).getName()  + " - " + ((DataModelAdapter)value).hashCode());
			setText( ((DataModelAdapter)value).getName() );
			
			return this;
	    }	
	}
	
	
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
				selectedNode = (DataModelAdapter)e.getNewLeadSelectionPath().getLastPathComponent();			
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
			selectedNode = (DataModelAdapter)Tree.this.getLastSelectedPathComponent();
		
			//Ha jobb-eger gombot nyomtam - Akkor popup menu jelenik meg
			if (SwingUtilities.isRightMouseButton(e)) {

				//A kivalasztott elem sora - kell a sor kiszinezesehez es a PopUp menu poziciojahoz
				int row = Tree.this.getClosestRowForLocation(e.getX(), e.getY());
				
				//Kiszinezi a sort
				Tree.this.setSelectionRow(row);

				//Jelzi, hogy mostantol, hiaba nem bal-egerrel valasztottam ki a node-ot, megis kivalasztott lesz
				selectedNode = (DataModelAdapter)Tree.this.getLastSelectedPathComponent();

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
		private DataModelAdapter selectedNode;
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
			selectedNode = (DataModelAdapter)Tree.this.getLastSelectedPathComponent();

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
				
				//
				//On/Off
				//
				if( selectedNode.isEnabledToTurnOnOff() ){

					JMenuItem disableEnableMenu;
					
					//Ha be van kapcsolva a csomopont
					if( selectedNode.isOn() ){
						
						disableEnableMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.off")  );
						disableEnableMenu.setActionCommand( ActionCommand.DISABLE.name() );
						disableEnableMenu.addActionListener( new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
	
								selectedNode.setOn( false );								
						
							}
						});						
						
					//Ha ki van kapcsolva a csomopont
					}else{

						disableEnableMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.on")  );
						disableEnableMenu.setActionCommand( ActionCommand.ENABLE.name() );
						disableEnableMenu.addActionListener( new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
	
								selectedNode.setOn( true );
						
							}
						});
						
					}
					
					this.add ( disableEnableMenu );
				}
				
				doPopupInsert( this, selectedNode );				
				doPopupDelete( this, selectedNode, selectedRow, totalTreeModel );
				doDuplicate( this, selectedNode, selectedRow, totalTreeModel );
				
			//ROOT volt kivalasztva
			}else{
				
				doPopupRootInsert( this, selectedNode );
				
				if( needPopupModifyAtRoot ){
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
				}
				
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
 * Fa csomopontjanak athelyezeset vegzo absztrakt osztaly
 * 
 * @author akoel
 *
 */
class TreeTransferHandler implements DragGestureListener, DragSourceListener, DropTargetListener {

    private Tree tree;
    private DragSource dragSource; // dragsource
    private DropTarget dropTarget; //droptarget
    private static DefaultMutableTreeNode draggedNode; 
    private DefaultMutableTreeNode draggedNodeParent; 
    private static BufferedImage shadowImage = null; //buff image
    
    private Rectangle shadowBound = new Rectangle();
    private Rectangle sourceBound = new Rectangle();
    private Rectangle targetBound = new Rectangle();    
    
    private boolean drawImage;
    private Boolean insertLineUp = null; 

    

    protected TreeTransferHandler(Tree tree, int action, boolean drawIcon) {
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
         
    /**
     * 
     * Felismeri a DRAG mozdulatot
     * 
     */
    public final void dragGestureRecognized(DragGestureEvent dge) {
         TreePath path = tree.getSelectionPath(); 
         if (path != null) { 
              draggedNode = (DefaultMutableTreeNode)path.getLastPathComponent();
              draggedNodeParent = (DefaultMutableTreeNode)draggedNode.getParent();
              if (drawImage) {
            	  sourceBound = tree.getPathBounds(path);
            	  JComponent lbl = (JComponent)tree.getCellRenderer().getTreeCellRendererComponent(tree, draggedNode, false , tree.isExpanded(path),((DefaultTreeModel)tree.getModel()).isLeaf(path.getLastPathComponent()), 0,false);
            	  lbl.setBounds(sourceBound);
            	  
            	  shadowImage = new BufferedImage(lbl.getWidth(), lbl.getHeight(), java.awt.image.BufferedImage.TYPE_INT_ARGB_PRE);
            	  Graphics2D graphics = shadowImage.createGraphics();
            	  graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));   
            	  lbl.setOpaque(false);
            	  lbl.paint(graphics);
            	  graphics.dispose();    
            	  
              }
              dragSource.startDrag(dge, DragSource.DefaultMoveNoDrop , shadowImage, new Point(0,0), new TransferableNode(draggedNode), this);               
         }      
    }

    /**
     * 
     * Elindult a DRAG
     * 
     */
    public final void dragEnter(DropTargetDragEvent dtde) {
         Point pt = dtde.getLocation();
         int action = dtde.getDropAction();
     
         if (drawImage) {
              paintImage(pt);
paintInsertLine(pt, tree);              
         }
         if (canPerformAction(tree, draggedNode, action, pt)) {
              dtde.acceptDrag(action);               
         }else {
              dtde.rejectDrag();
         }
    }

    /**
     * 
     * Veget ert a DRAG
     * 
     */
    public final void dragExit(DropTargetEvent dte) {
         if (drawImage) {
              clearImage();
         }
    }

    //Folyamatosan hivodik
    public final void dragOver(DropTargetDragEvent dtde) {
         Point pt = dtde.getLocation();
         int action = dtde.getDropAction();
         tree.autoscroll(pt);
         if (drawImage) {
              paintImage(pt);
paintInsertLine(pt, tree);              
         }
         if (canPerformAction(tree, draggedNode, action, pt)) {
              dtde.acceptDrag(action);               
         }else {
              dtde.rejectDrag();
         }
    }

    public final void dropActionChanged(DropTargetDragEvent dtde) {
         Point pt = dtde.getLocation();
         int action = dtde.getDropAction();
         if (drawImage) {
              paintImage(pt);
paintInsertLine(pt, tree);              
         }
         if (canPerformAction(tree, draggedNode, action, pt)) {
              dtde.acceptDrag(action);               
         }else {
              dtde.rejectDrag();
         }
    }

    /**
     * 
     * Lehelyezi a felemelt elemet
     * 
     */
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
         }catch (Exception e) {     
              System.out.println(e);
              dtde.rejectDrop();
              dtde.dropComplete(false);
         }     
    }
    
    /**
     * 
     * Kirajzolja a megadott pozicioba a felemelt elem arnyekat
     * 
     * @param pt
     */
    private final void paintImage(Point pt) {
    	int x = shadowBound.x;
    	int y = shadowBound.y;
    	if( pt.x != x || pt.y != y ){    	
    		tree.repaint(shadowBound.getBounds());
    		tree.paintImmediately(shadowBound.getBounds());
    	}
    	shadowBound.setRect((int) pt.getX(),(int) pt.getY(),shadowImage.getWidth(),shadowImage.getHeight());
    	tree.getGraphics().drawImage(shadowImage,(int) pt.getX(),(int) pt.getY(),tree);
    }

    /**
     * 
     * Kirajzolja a megadott pozicioba az ele/utan beszuras vonalat
     * 
     * @param pt
     */
    
    public boolean paintInsertLine(Point pt, Tree target) {
    	int x = pt.x;
    	int y = pt.y;
 	    	
        TreePath pathTarget = target.getPathForLocation(x, y);
        if ( null == pathTarget ) {
             target.setSelectionPath(null);
             return(false);
        }
    	
        targetBound = tree.getPathBounds(pathTarget);
        
  	  	Graphics2D g2 = (Graphics2D)tree.getGraphics();
  	  	g2.setColor( Color.red );

  	  	//g2.clearRect( targetBound.x, targetBound.y, targetBound.width, targetBound.height );
  	  
  	  	double d = y - targetBound.y;
  	  	g2.setStroke(new BasicStroke(2));
  	  	
  	  	//Ha inkabb az alja fele van kozel a kurzor
  	  	if( d > targetBound.height / 2 ){
  	  		if( null == insertLineUp || insertLineUp ){
  	  			tree.repaint(targetBound.getBounds());
  	  		}
  	  		g2.drawLine( targetBound.x, targetBound.y + targetBound.height - 1, targetBound.x + targetBound.width, targetBound.y + targetBound.height - 1 );
  	  		insertLineUp = false;
  	  	}else{
  	  		if( null == insertLineUp || !insertLineUp ){
  	  			tree.repaint(targetBound.getBounds());
  	  		}
  	  		g2.drawLine( targetBound.x, targetBound.y + 1, targetBound.x + targetBound.width, targetBound.y + 1 );
  	  		insertLineUp = true;
  	  	}
	  	    
  	  	g2.dispose();
  	  	
        return false;
    
    }
    
    private final void clearImage() {
    	tree.repaint(shadowBound.getBounds());
    	tree.paintImmediately(shadowBound.getBounds());
    	
    	tree.repaint(targetBound.getBounds());    	
    	tree.paintImmediately(targetBound.getBounds());
    	
    	insertLineUp = null;
    }

    public boolean canPerformAction(Tree target, DefaultMutableTreeNode draggedNode, int action, Point location) {
        TreePath pathTarget = target.getPathForLocation(location.x, location.y);
        if ( null == pathTarget ) {
             target.setSelectionPath(null);
             return(false);
        }
        target.setSelectionPath(pathTarget);
     
        return tree.possibleHierarchy( draggedNode, pathTarget.getLastPathComponent() );
/*        
        if( !tree.possibleHierarchy( draggedNode, pathTarget.getLastPathComponent() ) ){
        	return false;
        }
        
        //Ha masolas vagy mozgatas
        if(action == DnDConstants.ACTION_COPY || action == DnDConstants.ACTION_MOVE) {     
             DefaultMutableTreeNode parentNode =(DefaultMutableTreeNode)pathTarget.getLastPathComponent();                    
     
             //Ha a mozgatando elem ROOT vagy ugyan olyan tipusu mint a 
             if (draggedNode.isRoot() || parentNode == draggedNode.getParent() || draggedNode.isNodeDescendant(parentNode)) {                         
                  return(false);     
             }else {
                  return(true);
             }                     
        }else {          
             return(false);     
        }
*/        
   }

   public boolean executeDrop(Tree target, DefaultMutableTreeNode draggedNode, DefaultMutableTreeNode newParentNode, int action) { 
/*        if (action == DnDConstants.ACTION_COPY) {
             DefaultMutableTreeNode newNode = target.makeDeepCopy(draggedNode);
             ((DefaultTreeModel)target.getModel()).insertNodeInto(newNode,newParentNode,newParentNode.getChildCount());
             TreePath treePath = new TreePath(newNode.getPath());
             target.scrollPathToVisible(treePath);
             target.setSelectionPath(treePath);     
             return(true);
        }
*/        
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
