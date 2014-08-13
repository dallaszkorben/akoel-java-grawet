package hu.akoel.grawit.gui.tree;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import hu.akoel.grawit.ActionCommand;
import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.gui.GUIFrame;
import hu.akoel.grawit.gui.editor.EmptyEditor;
import hu.akoel.grawit.gui.tree.datamodel.PageBaseElementDataModel;
import hu.akoel.grawit.gui.tree.datamodel.ParamPageNodeDataModel;
import hu.akoel.grawit.gui.tree.datamodel.ParamPageDataModelInterface;
import hu.akoel.grawit.gui.tree.datamodel.ParamPageElementDataModel;
import hu.akoel.grawit.gui.tree.datamodel.ParamPagePageDataModel;
import hu.akoel.grawit.gui.tree.datamodel.ParamPageRootDataModel;

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

public class ParamPageTree extends JTree{

	private static final long serialVersionUID = 2628772390313043643L;

	private GUIFrame guiFrame;
	
	private DefaultMutableTreeNode selectedNode;

	private DefaultTreeModel treeModel;
	
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
	
	public ParamPageTree( GUIFrame guiFrame, ParamPageRootDataModel paramPageRootDataModel ){
	
		super( new DefaultTreeModel(paramPageRootDataModel) );
		
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

			private static final long serialVersionUID = -560402048003401680L;

			@Override
		    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean isLeaf, int row, boolean focused) {
		    	Component c = super.getTreeCellRendererComponent(tree, value, selected, expanded, isLeaf, row, focused);
		    	
		    	ImageIcon pageIcon = CommonOperations.createImageIcon("tree/pagebase-page-icon.png");
		    	ImageIcon elementIcon = CommonOperations.createImageIcon("tree/pagebase-element-icon.png");
		    	ImageIcon nodeClosedIcon = CommonOperations.createImageIcon("tree/node-closed-icon.png");
		    	ImageIcon nodeOpenIcon = CommonOperations.createImageIcon("tree/node-open-icon.png");
		    	
		    	//Felirata a NODE-nak
		    	setText( ((ParamPageDataModelInterface)value).getNameToString() );
		    	
		    	//Iconja a NODE-nak
		    	if( value instanceof ParamPagePageDataModel){
		            setIcon(pageIcon);
		    	}else if( value instanceof PageBaseElementDataModel ){
		            setIcon(elementIcon);
		    	}else if( value instanceof ParamPageNodeDataModel){
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
			
			//System.err.println("changed to: " + e.getNewLeadSelectionPath());
			//A kivalasztott NODE			
			
			//Nincs kivalasztva semmi
			if( null == e.getNewLeadSelectionPath() ){
				EmptyEditor emptyPanel = new EmptyEditor();								
				guiFrame.showEditorPanel( emptyPanel);
			}else{
			
				selectedNode = (DefaultMutableTreeNode)e.getNewLeadSelectionPath().getLastPathComponent();
				//selectedNode = (DefaultMutableTreeNode)PageBaseTree.this.getLastSelectedPathComponent();
			
				//Ha egyaltalan valamilyen egergombot benyomtam
				if( selectedNode instanceof ParamPageRootDataModel ){
					EmptyEditor emptyPanel = new EmptyEditor();								
					guiFrame.showEditorPanel( emptyPanel );
				
				}else if( selectedNode instanceof ParamPageNodeDataModel ){
//					PageBaseNodeEditor pageBaseNodePanel = new ParamPageNodeEditor(ParamPageTree.this, (ParamPageNodeDataModel)selectedNode, EditMode.VIEW);
//					guiFrame.showEditorPanel( pageBaseNodePanel);								
				
				}else if( selectedNode instanceof ParamPagePageDataModel ){
//					ParamPagePageEditor pageBasePagePanel = new ParamPagePageEditor( ParamPageTree.this, (ParamPagePageDataModel)selectedNode, EditMode.VIEW );								
//					guiFrame.showEditorPanel( pageBasePagePanel);				
								
				}else if( selectedNode instanceof ParamPageElementDataModel ){
//					PageBaseElementEditor pageBaseElementPanel = new ParamPageElementEditor( ParamPageTree.this, (ParamPageElementDataModel)selectedNode, EditMode.VIEW );								
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
			selectedNode = (DefaultMutableTreeNode)ParamPageTree.this.getLastSelectedPathComponent();
		
			//Ha jobb-eger gombot nyomtam - Akkor popup menu jelenik meg
			if (SwingUtilities.isRightMouseButton(e)) {

				//A kivalasztott elem sora - kell a sor kiszinezesehez es a PopUp menu poziciojahoz
				int row = ParamPageTree.this.getClosestRowForLocation(e.getX(), e.getY());
				//int row = PageBaseTree.this.getRowForLocation(e.getX(), e.getY());
				
				//Kiszinezi a sort
				ParamPageTree.this.setSelectionRow(row);

				//Jelzi, hogy mostantol, hiaba nem bal-egerrel valasztottam ki a node-ot, megis kivalasztott lesz
				selectedNode = (DefaultMutableTreeNode)ParamPageTree.this.getLastSelectedPathComponent();

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
		private ParamPageDataModelInterface selectedNode;
		private TreePath selectedPath;
		private DefaultTreeModel totalTreeModel;
		private int selectedIndexInTheNode;
		private int elementsInTheNode;
		
		//public PopUpMenu( final int selectedRow, final DefaultMutableTreeNode selectedNode, final TreePath selectedPath ){
		public PopUpMenu( final int selectedRow ){
			super();

			//A teljes fastruktura modell-je			
			totalTreeModel = (DefaultTreeModel)ParamPageTree.this.getModel();

			//A kivalasztott NODE			
			selectedNode = (ParamPageDataModelInterface)ParamPageTree.this.getLastSelectedPathComponent();

			//A kivalasztott node-ig vezeto PATH
			selectedPath = ParamPageTree.this.getSelectionPath();	

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
								ParamPageTree.this.setSelectionRow(selectedRow - 1);
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
								ParamPageTree.this.setSelectionRow(selectedRow + 1);
							}	
							
						}
					});
					this.add ( downMenu );
					
				}
				
				//
				//Szerkesztes
				//
				JMenuItem editMenu = new JMenuItem( CommonOperations.getTranslation( "popupmenu.edit") );
				editMenu.setActionCommand( ActionCommand.EDIT.name());
				editMenu.addActionListener( new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						
						if( selectedNode instanceof ParamPageNodeDataModel ){
							
//							ParamPageNodeEditor paramPageNodePanel = new ParamPageNodeEditor( ParamPageTree.this, (ParamPageNodeDataModel)selectedNode, DataEditor.EditMode.MODIFY );								
//							guiFrame.showEditorPanel( paramPageNodePanel);								
								
						}else if( selectedNode instanceof ParamPagePageDataModel ){
								
//							ParamPagePageEditor paramPagePagePanel = new ParamPagePageEditor( ParamPageTree.this, (ParamPagePageDataModel)selectedNode, DataEditor.EditMode.MODIFY );								
//							guiFrame.showEditorPanel( paramPagePagePanel);		
								
						}else if( selectedNode instanceof ParamPageElementDataModel ){

//							ParamPageElementEditor paramPageElementPanel = new ParamPageElementEditor( ParamPageTree.this, (ParamPageElementDataModel)selectedNode, DataEditor.EditMode.MODIFY );								
//							guiFrame.showEditorPanel( paramPageElementPanel);		
								
						}
					}
				});
				this.add ( editMenu );
				
				//
				// Csomopont eseten
				//
				if( selectedNode instanceof ParamPageNodeDataModel ){

					//Insert Node
					JMenuItem insertNodeMenu = new JMenuItem( CommonOperations.getTranslation( "popupmenu.insert.node") );
					insertNodeMenu.setActionCommand( ActionCommand.CAPTURE.name());
					insertNodeMenu.addActionListener( new ActionListener() {
					
						@Override
						public void actionPerformed(ActionEvent e) {
							
//							ParamPageNodeEditor paramPageNodePanel = new ParamPageNodeEditor( ParamPageTree.this, (ParamPageNodeDataModel)selectedNode );								
//							guiFrame.showEditorPanel( paramPageNodePanel);								
						
						}
					});
					this.add ( insertNodeMenu );

					//Insert Page
					JMenuItem insertPageMenu = new JMenuItem( CommonOperations.getTranslation( "popupmenu.insert.page") );
					insertPageMenu.setActionCommand( ActionCommand.CAPTURE.name());
					insertPageMenu.addActionListener( new ActionListener() {
					
						@Override
						public void actionPerformed(ActionEvent e) {
							
//							ParamPagePageEditor paramPageNodePanel = new ParamPagePageEditor( ParamPageTree.this, (ParamPageNodeDataModel)selectedNode );								
//							guiFrame.showEditorPanel( paramPageNodePanel);								
						
						}
					});
					this.add ( insertPageMenu );
					
				}		
				
				//
				// Page eseten
				//
				if( selectedNode instanceof ParamPagePageDataModel ){

					//Insert Element
					JMenuItem insertElementMenu = new JMenuItem( CommonOperations.getTranslation( "popupmenu.insert.element") );
					insertElementMenu.setActionCommand( ActionCommand.CAPTURE.name());
					insertElementMenu.addActionListener( new ActionListener() {
					
						@Override
						public void actionPerformed(ActionEvent e) {
							
//							ParamPageElementEditor paramPageNodePanel = new ParamPageElementEditor( ParamPageTree.this, (ParamPagePageDataModel)selectedNode );								
//							guiFrame.showEditorPanel( paramPageNodePanel);								
						
						}
					});
					this.add ( insertElementMenu );
				
				}
				
				//
				// Torles
				// Ha nincs alatta ujabb elem
				//
				if( selectedNode.getChildCount() == 0 ){
					
				
					JMenuItem deleteMenu = new JMenuItem( CommonOperations.getTranslation( "popupmenu.delete") );
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
									"Valóban torolni kívánod a(z) " + selectedNode.getNameToString() + " nevü " + selectedNode.getTypeToString() + "-t ?",
									CommonOperations.getTranslation("section.windowtitle.confirmation.delete"),
									JOptionPane.YES_NO_CANCEL_OPTION,
									JOptionPane.QUESTION_MESSAGE,
									null,
									options,
									options[0]);

							if( n == 1 ){
								totalTreeModel.removeNodeFromParent( selectedNode);
								ParamPageTree.this.setSelectionRow(selectedRow - 1);
							}							
						}
					});
					this.add ( deleteMenu );
					
				}
				
			//ROOT volt kivalasztva
			}else{
				
				//Insert Node
				JMenuItem insertNodeMenu = new JMenuItem( CommonOperations.getTranslation( "popupmenu.insert.node") );
				insertNodeMenu.setActionCommand( ActionCommand.CAPTURE.name());
				insertNodeMenu.addActionListener( new ActionListener() {
				
					@Override
					public void actionPerformed(ActionEvent e) {
						
//						ParamPageNodeEditor paramPageNodePanel = new ParamPageNodeEditor( ParamPageTree.this, (ParamPageNodeDataModel)selectedNode );								
//						guiFrame.showEditorPanel( paramPageNodePanel);								
					
					}
				});
				this.add ( insertNodeMenu );
				
			}

		}
	
	}
}
