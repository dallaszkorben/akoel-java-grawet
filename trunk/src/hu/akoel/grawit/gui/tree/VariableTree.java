package hu.akoel.grawit.gui.tree;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import hu.akoel.grawit.ActionCommand;
import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.datamodel.BaseDataModelInterface;
import hu.akoel.grawit.core.datamodel.VariableDataModelInterface;
import hu.akoel.grawit.core.datamodel.nodes.VariableNodeDataModel;
import hu.akoel.grawit.core.datamodel.roots.VariableRootDataModel;
import hu.akoel.grawit.gui.GUIFrame;
import hu.akoel.grawit.gui.editor.DataEditor;
import hu.akoel.grawit.gui.editor.EmptyEditor;
import hu.akoel.grawit.gui.editor.DataEditor.EditMode;
import hu.akoel.grawit.gui.editor.VariableNodeEditor;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
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

public class VariableTree extends JTree{

	private static final long serialVersionUID = 4781755363503730543L;

	private GUIFrame guiFrame;
	
	private DefaultMutableTreeNode selectedNode;

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
	
	public VariableTree( GUIFrame guiFrame, VariableRootDataModel variablePageRootDataModel ){
	
		super( new DefaultTreeModel(variablePageRootDataModel) );
		
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

			private static final long serialVersionUID = 1323618892737458100L;

			@Override
		    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean isLeaf, int row, boolean focused) {
		    	Component c = super.getTreeCellRendererComponent(tree, value, selected, expanded, isLeaf, row, focused);
		    	
		    	ImageIcon pageIcon = CommonOperations.createImageIcon("tree/pagebase-page-icon.png");
		    	ImageIcon elementIcon = CommonOperations.createImageIcon("tree/pagebase-element-icon.png");
		    	ImageIcon nodeClosedIcon = CommonOperations.createImageIcon("tree/node-closed-icon.png");
		    	ImageIcon nodeOpenIcon = CommonOperations.createImageIcon("tree/node-open-icon.png");
		    	
		    	//Felirata a NODE-nak
		    	setText( ((VariableDataModelInterface)value).getName() );
//TODO javitani		    	
		    	//Iconja a NODE-nak
//		    	if( value instanceof VariableElementDataModel ){
//		            setIcon(elementIcon);
//		    	}else 
		    	if( value instanceof VariableNodeDataModel){
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
		this.addTreeSelectionListener( new SelectionChangedListener() );
	
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
			
			//Nincs kivalasztva semmi
			if( null == e.getNewLeadSelectionPath() ){
				EmptyEditor emptyPanel = new EmptyEditor();								
				guiFrame.showEditorPanel( emptyPanel);
			}else{
			
				selectedNode = (DefaultMutableTreeNode)e.getNewLeadSelectionPath().getLastPathComponent();
			
				//Ha egyaltalan valamilyen egergombot benyomtam
				if( selectedNode instanceof VariableRootDataModel ){
					EmptyEditor emptyPanel = new EmptyEditor();								
					guiFrame.showEditorPanel( emptyPanel );
				
				}else if( selectedNode instanceof VariableNodeDataModel ){
					VariableNodeEditor pageBaseNodePanel = new VariableNodeEditor(VariableTree.this, (VariableNodeDataModel)selectedNode, EditMode.VIEW);
					guiFrame.showEditorPanel( pageBaseNodePanel);								
//TODO befejezes				
//				}else if( selectedNode instanceof VariableElementDataModel ){
//					BaseElementEditor pageBaseElementPanel = new BaseElementEditor( VariableTree.this, (BaseElementDataModel)selectedNode, EditMode.VIEW );								
//					guiFrame.showEditorPanel( pageBaseElementPanel);		
										
				}
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
			selectedNode = (DefaultMutableTreeNode)VariableTree.this.getLastSelectedPathComponent();
		
			//Ha jobb-eger gombot nyomtam - Akkor popup menu jelenik meg
			if (SwingUtilities.isRightMouseButton(e)) {

				//A kivalasztott elem sora - kell a sor kiszinezesehez es a PopUp menu poziciojahoz
				int row = VariableTree.this.getClosestRowForLocation(e.getX(), e.getY());
				//int row = BaseTree.this.getRowForLocation(e.getX(), e.getY());
				
				//Kiszinezi a sort
				VariableTree.this.setSelectionRow(row);

				//Jelzi, hogy mostantol, hiaba nem bal-egerrel valasztottam ki a node-ot, megis kivalasztott lesz
				selectedNode = (DefaultMutableTreeNode)VariableTree.this.getLastSelectedPathComponent();

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
		private VariableDataModelInterface selectedNode;
		private TreePath selectedPath;
		private DefaultTreeModel totalTreeModel;
		private int selectedIndexInTheNode;
		private int elementsInTheNode;
		
		//public PopUpMenu( final int selectedRow, final DefaultMutableTreeNode selectedNode, final TreePath selectedPath ){
		public PopUpMenu( final int selectedRow ){
			super();

			//A teljes fastruktura modell-je			
			totalTreeModel = (DefaultTreeModel)VariableTree.this.getModel();

			//A kivalasztott NODE			
			selectedNode = (VariableDataModelInterface)VariableTree.this.getLastSelectedPathComponent();

			//A kivalasztott node-ig vezeto PATH
			selectedPath = VariableTree.this.getSelectionPath();	

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
				if( selectedIndexInTheNode >= 1 ){
					
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
								VariableTree.this.setSelectionRow(selectedRow - 1);
							}							
						}
					});
					this.add ( upMenu );

					
				}
				
				//
				// Lefele mozgat
				// Ha nem a legutolso elem
				//
				if( selectedIndexInTheNode < elementsInTheNode - 1 ){
					
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
								VariableTree.this.setSelectionRow(selectedRow + 1);
							}	
							
						}
					});
					this.add ( downMenu );
					
				}
				
				//
				//Szerkesztes
				//
				JMenuItem editMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.edit") );
				editMenu.setActionCommand( ActionCommand.EDIT.name());
				editMenu.addActionListener( new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						
						if( selectedNode instanceof VariableNodeDataModel ){
							
							VariableNodeEditor pageBaseNodePanel = new VariableNodeEditor( VariableTree.this, (VariableNodeDataModel)selectedNode, DataEditor.EditMode.MODIFY );								
							guiFrame.showEditorPanel( pageBaseNodePanel);								
//TODO befejezni								
//							}else if( selectedNode instanceof VariableElementDataModel ){
//								VariableElementEditor pageBaseElementPanel = new VariableElementEditor( VariableTree.this, (VariableElementDataModel)selectedNode, DataEditor.EditMode.MODIFY );								
//								guiFrame.showEditorPanel( pageBaseElementPanel);		
							
						}
					}
				});
				this.add ( editMenu );
				
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
							
							VariableNodeEditor pageBaseNodePanel = new VariableNodeEditor( VariableTree.this, (VariableNodeDataModel)selectedNode );								
							guiFrame.showEditorPanel( pageBaseNodePanel);								
						
						}
					});
					this.add ( insertNodeMenu );

					//Insert Element
					JMenuItem insertElementMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.element") );
					insertElementMenu.setActionCommand( ActionCommand.CAPTURE.name());
					insertElementMenu.addActionListener( new ActionListener() {
					
						@Override
						public void actionPerformed(ActionEvent e) {
//TODO befejezni							
//							VariableElementEditor paramPageNodeEditor = new ParamElementEditor( ParamTree.this, (VariablePageDataModel)selectedNode );								
//							guiFrame.showEditorPanel( paramPageNodeEditor);								
						
						}
					});
					this.add ( insertElementMenu );
					
				}		
				
				//
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
									"Valóban torolni kívánod a(z) " + selectedNode.getTag() + " nevü " + selectedNode.getTypeToShow() + "-t ?",
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
					this.add ( deleteMenu );
					
				}
				
			//ROOT volt kivalasztva
			}else{
				
				//Insert Node
				JMenuItem insertNodeMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.node") );
				insertNodeMenu.setActionCommand( ActionCommand.CAPTURE.name());
				insertNodeMenu.addActionListener( new ActionListener() {
				
					@Override
					public void actionPerformed(ActionEvent e) {
						
						VariableNodeEditor pageBaseNodePanel = new VariableNodeEditor( VariableTree.this, (VariableNodeDataModel)selectedNode );								
						guiFrame.showEditorPanel( pageBaseNodePanel);								
					
					}
				});
				this.add ( insertNodeMenu );
				
			}

		}
	
	}
}
