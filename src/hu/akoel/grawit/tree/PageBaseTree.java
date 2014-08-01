package hu.akoel.grawit.tree;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.tree.tags.PageBaseTreeElement;
import hu.akoel.grawit.tree.tags.PageBaseTreeNode;
import hu.akoel.grawit.tree.tags.PageBaseTreePage;
import hu.akoel.grawit.tree.tags.PageBaseTreeRoot;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;

public class PageBaseTree extends JTree{

	private static final long serialVersionUID = -3929758449314068678L;

	public PageBaseTree( PageBaseTreeRoot pageBaseTreeRoot ){
		super( new DefaultTreeModel(pageBaseTreeRoot) );
		
		this.setShowsRootHandles(false);
		
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
		    	
		    	if( value instanceof PageBaseTreePage){
		            setIcon(pageIcon);
		    	}else if( value instanceof PageBaseTreeElement ){
		            setIcon(elementIcon);
		    	}else if( value instanceof PageBaseTreeNode){
		    		if( expanded ){
		    			setIcon(nodeOpenIcon);
		    		}else{
		    			setIcon(nodeClosedIcon);
		    		}
		        }
		 
		    	return c;
		    }
		});
		
		this.addMouseListener( new RightMouseListener() );
		
	
	}
	
	class RightMouseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			
						
			
			 if (SwingUtilities.isRightMouseButton(e)) {

		            int row = PageBaseTree.this.getClosestRowForLocation(e.getX(), e.getY());
		            PageBaseTree.this.setSelectionRow(row);
		            
		            JPopupMenu menu = new JPopupMenu ();
                    menu.add ( new JMenuItem ( "Up" ) );
                    menu.add ( new JMenuItem ( "Down" ) );
		            
		            menu.show(e.getComponent(), e.getX(), e.getY());
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
}
