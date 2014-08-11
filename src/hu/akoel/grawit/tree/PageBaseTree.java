package hu.akoel.grawit.tree;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import hu.akoel.grawit.ActionCommand;
import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.gui.DataPanel;
import hu.akoel.grawit.gui.EmptyPanel;
import hu.akoel.grawit.gui.PageBaseElementPanel;
import hu.akoel.grawit.gui.PageBaseNodePanel;
import hu.akoel.grawit.gui.GUIFrame;
import hu.akoel.grawit.gui.PageBasePagePanel;
import hu.akoel.grawit.tree.datamodel.PageBaseDataModelElement;
import hu.akoel.grawit.tree.datamodel.PageBaseDataModelNode;
import hu.akoel.grawit.tree.datamodel.PageBaseDataModelPage;
import hu.akoel.grawit.tree.datamodel.PageBaseDataModelRoot;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

public class PageBaseTree extends JTree{

	private static final long serialVersionUID = -3929758449314068678L;
	
	private GUIFrame guiFrame;
	
	private DefaultMutableTreeNode selectedNode;

	private DefaultTreeModel treeModel;
	private PageBaseDataModelRoot pageBaseDataModelRoot;
	
	/**
	 * 
	 * Ertesiti a tree-t, hogy valtozas tortent
	 * 
	 */
	public void changed(){
				
		((DefaultTreeModel)this.getModel()).reload();
		
		//this.setModel( new DefaultTreeModel( pageBaseDataModelRoot ) );
		//treeModel.nodeChanged( pageBaseDataModelRoot );
	}
	
	public PageBaseTree( GUIFrame guiFrame, PageBaseDataModelRoot pageBaseDataModelRoot ){
	
		super( new DefaultTreeModel(pageBaseDataModelRoot) );
		
		this.pageBaseDataModelRoot = pageBaseDataModelRoot;		
		treeModel = (DefaultTreeModel)this.getModel();
		
		this.guiFrame = guiFrame;
		this.setShowsRootHandles(true);
		//this.setRootVisible(false);
		
		//Csak egy elem lehet kivalasztva
		this.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		
		
		/**
		 * Ikonokat helyezek el az egyes csomopontok ele
		 */
		this.setCellRenderer(new DefaultTreeCellRenderer() {
		    //private Icon loadIcon = UIManager.getIcon("OptionPane.errorIcon");
		    //private Icon saveIcon = UIManager.getIcon("OptionPane.informationIcon");

			private static final long serialVersionUID = 1323618892737458100L;

			@Override
		    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean isLeaf, int row, boolean focused) {
		    	Component c = super.getTreeCellRendererComponent(tree, value, selected, expanded, isLeaf, row, focused);
		    	
		    	ImageIcon pageIcon = CommonOperations.createImageIcon("pagebase-page-icon.png");
		    	ImageIcon elementIcon = CommonOperations.createImageIcon("pagebase-element-icon.png");
		    	ImageIcon nodeClosedIcon = CommonOperations.createImageIcon("node-closed-icon.png");
		    	ImageIcon nodeOpenIcon = CommonOperations.createImageIcon("node-open-icon.png");
		    	
		    	if( value instanceof PageBaseDataModelPage){
		            setIcon(pageIcon);
		    	}else if( value instanceof PageBaseDataModelElement ){
		            setIcon(elementIcon);
		    	}else if( value instanceof PageBaseDataModelNode){
		    		if( expanded ){
		    			setIcon(nodeOpenIcon);
		    		}else{
		    			setIcon(nodeClosedIcon);
		    		}
		        }
		 
		    	return c;
		    }
		});
		

		
		/**
		 * A eger benyomasara reagalok
		 */
		this.addMouseListener( new TreeMouseListener() );
		//this.addTreeSelectionListener( new MyTreeSelectionListener() );
	
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
			
//TODO jobb egergobbal kivalasztva a selectedNode=null !			
			//A kivalasztott NODE			
			selectedNode = (DefaultMutableTreeNode)PageBaseTree.this.getLastSelectedPathComponent();
/*			
			//Ha egyaltalan valamilyen egergombot benyomtam
			if( selectedNode instanceof PageBaseDataModelNode ){
				
				PageBaseNodePanel pageBaseNodePanel = new PageBaseNodePanel( PageBaseTree.this, (PageBaseDataModelNode)selectedNode, DataPanel.Mode.SHOW );								
				guiFrame.showEditorPanel( pageBaseNodePanel);								
				
			}else if( selectedNode instanceof PageBaseDataModelPage ){
				
				PageBasePagePanel pageBasePagePanel = new PageBasePagePanel( PageBaseTree.this, (PageBaseDataModelPage)selectedNode, DataPanel.Mode.SHOW );								
				guiFrame.showEditorPanel( pageBasePagePanel);				
								
			}else if( selectedNode instanceof PageBaseDataModelElement ){
				PageBaseElementPanel pageBaseElementPanel = new PageBaseElementPanel( PageBaseTree.this, (PageBaseDataModelElement)selectedNode, DataPanel.Mode.SHOW );								
				guiFrame.showEditorPanel( pageBaseElementPanel);		
			
			}
*/			
			//Ha jobb-eger gombot nyomtam - Akkor popup menu jelenik meg
			if (SwingUtilities.isRightMouseButton(e)) {

				//A kivalasztott elem sora - kell a sor kiszinezesehez es a PopUp menu poziciojahoz
				int row = PageBaseTree.this.getClosestRowForLocation(e.getX(), e.getY());
				//int row = PageBaseTree.this.getRowForLocation(e.getX(), e.getY());
				
				//Kiszinezi a sort
				PageBaseTree.this.setSelectionRow(row);

				//Jelzi, hogy mostantol, hiaba nem bal-egerrel valasztottam ki a node-ot, megis kivalasztott lesz
				selectedNode = (DefaultMutableTreeNode)PageBaseTree.this.getLastSelectedPathComponent();

//				TreePath path = PageBaseTree.this.getSelectionPath();
				
				//Letrehozza a PopUpMenu-t
				//PopUpMenu popUpMenu = new PopUpMenu( row, node, path );
				PopUpMenu popUpMenu = new PopUpMenu( row );
				
				//Megjeleniti a popup menut
				popUpMenu.show( e.getComponent(), e.getX(), e.getY() );
				
			}
			
			//Ha egyaltalan valamilyen egergombot benyomtam
			if( selectedNode instanceof PageBaseDataModelNode ){
				
				PageBaseNodePanel pageBaseNodePanel = new PageBaseNodePanel( PageBaseTree.this, (PageBaseDataModelNode)selectedNode, DataPanel.Mode.SHOW );								
				guiFrame.showEditorPanel( pageBaseNodePanel);								
				
			}else if( selectedNode instanceof PageBaseDataModelPage ){
				
				PageBasePagePanel pageBasePagePanel = new PageBasePagePanel( PageBaseTree.this, (PageBaseDataModelPage)selectedNode, DataPanel.Mode.SHOW );								
				guiFrame.showEditorPanel( pageBasePagePanel);				
								
			}else if( selectedNode instanceof PageBaseDataModelElement ){
				PageBaseElementPanel pageBaseElementPanel = new PageBaseElementPanel( PageBaseTree.this, (PageBaseDataModelElement)selectedNode, DataPanel.Mode.SHOW );								
				guiFrame.showEditorPanel( pageBaseElementPanel);		
			
			}else if( selectedNode instanceof PageBaseDataModelRoot ){
				EmptyPanel emptyPanel = new EmptyPanel();								
				guiFrame.showEditorPanel( emptyPanel);									
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
		private DefaultMutableTreeNode selectedNode;
		private TreePath selectedPath;
		private DefaultTreeModel totalTreeModel;
		private int selectedIndexInTheNode;
		private int elementsInTheNode;
		
		//public PopUpMenu( final int selectedRow, final DefaultMutableTreeNode selectedNode, final TreePath selectedPath ){
		public PopUpMenu( final int selectedRow ){
			super();

			//A teljes fastruktura modell-je			
			totalTreeModel = (DefaultTreeModel)PageBaseTree.this.getModel();

			//A kivalasztott NODE			
			selectedNode = (DefaultMutableTreeNode)PageBaseTree.this.getLastSelectedPathComponent();

			//A kivalasztott node-ig vezeto PATH
			selectedPath = PageBaseTree.this.getSelectionPath();	

			//A kivalasztott node fastrukturaban elfoglalt melyseget adja vissza. 1 jelenti a gyokeret
			int pathLevel = selectedPath.getPathCount();

			//Jelzem, hogy nincs kivalasztva meg
			selectedIndexInTheNode = -1;

			//Ha nem a ROOT volt kivalasztva, akkor a FEL es LE elemeket helyezem el
			if( pathLevel > 1 ){

				//A kivalasztott node szulo node-ja
				parentNode = (DefaultMutableTreeNode)selectedPath.getPathComponent( pathLevel - 2 );

				//Hany elem van a szulo csomopontjaban
				elementsInTheNode = totalTreeModel.getChildCount( parentNode );
				
				//A kivalasztott elem sorszama a szulo node-jaban
				selectedIndexInTheNode = totalTreeModel.getIndexOfChild( parentNode, selectedNode );
				
				//Ha nem a legelso elem
				if( selectedIndexInTheNode >= 1 ){
					
					//Akkor mozoghat felfele, letrehozhatom a fel menuelemet
					JMenuItem upMenu = new JMenuItem( CommonOperations.getTranslation( "popupmenu.up") );
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
								PageBaseTree.this.setSelectionRow(selectedRow - 1);
							}							
						}
					});
					this.add ( upMenu );

					
				}
				
				//Ha nem a legutolso elem
				if( selectedIndexInTheNode < elementsInTheNode - 1 ){
					
					//Akkor mozoghat lefele, letrehozhatom a le nemuelement
					JMenuItem downMenu = new JMenuItem( CommonOperations.getTranslation( "popupmenu.down")  );
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
								PageBaseTree.this.setSelectionRow(selectedRow + 1);
							}	
							
						}
					});
					this.add ( downMenu );
					
				}
				
				//Szerkesztes
				JMenuItem editMenu = new JMenuItem( CommonOperations.getTranslation( "popupmenu.edit") );
				editMenu.setActionCommand( ActionCommand.EDIT.name());
				editMenu.addActionListener( new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						
//						if( selectedIndexInTheNode >= 1 ) {
							
							if( selectedNode instanceof PageBaseDataModelNode ){
							
								PageBaseNodePanel pageBaseNodePanel = new PageBaseNodePanel( PageBaseTree.this, (PageBaseDataModelNode)selectedNode, DataPanel.Mode.MODIFY );								
								guiFrame.showEditorPanel( pageBaseNodePanel);								
								
							}else if( selectedNode instanceof PageBaseDataModelPage ){
								
								PageBasePagePanel pageBasePagePanel = new PageBasePagePanel( PageBaseTree.this, (PageBaseDataModelPage)selectedNode, DataPanel.Mode.MODIFY );								
								guiFrame.showEditorPanel( pageBasePagePanel);		
								
							}else if( selectedNode instanceof PageBaseDataModelElement ){

								PageBaseElementPanel pageBaseElementPanel = new PageBaseElementPanel( PageBaseTree.this, (PageBaseDataModelElement)selectedNode, DataPanel.Mode.MODIFY );								
								guiFrame.showEditorPanel( pageBaseElementPanel);		
								
							}
					}
				});
				this.add ( editMenu );
				
			}
	
			//Ha a ROOT-ot valasztottam
			if( selectedNode instanceof PageBaseDataModelRoot ){			
			
				//Capture
				JMenuItem captureMenu = new JMenuItem( CommonOperations.getTranslation( "popupmenu.insert.node") );
				captureMenu.setActionCommand( ActionCommand.CAPTURE.name());
				captureMenu.addActionListener( new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
							
						PageBaseNodePanel pageBaseNodePanel = new PageBaseNodePanel( PageBaseTree.this, (PageBaseDataModelRoot)selectedNode, DataPanel.Mode.CAPTURE );								
						guiFrame.showEditorPanel( pageBaseNodePanel);								
						
					}
				});
				this.add ( captureMenu );
			}	
			
			
			// Szerkesztes
			
			


			
		}
		
		
	}
}
