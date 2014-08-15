package hu.akoel.grawit.gui.container;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.gui.GUIFrame;
import hu.akoel.grawit.gui.tree.datamodel.PageBaseDataModelInterface;
import hu.akoel.grawit.gui.tree.datamodel.PageBaseElementDataModel;
import hu.akoel.grawit.gui.tree.datamodel.PageBaseNodeDataModel;
import hu.akoel.grawit.gui.tree.datamodel.PageBasePageDataModel;
import hu.akoel.grawit.gui.tree.datamodel.PageBaseRootDataModel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

public class TreeSelectionCombo extends JPanel{

	private static final long serialVersionUID = 6475998839112722226L;

	private JButton button;
	private JTextField field = new JTextField();
	private TreePath treePath;
	
	//private PageBasePageDataModel pageBase = null;
	
	private GUIFrame parent;
	
	public TreeSelectionCombo( GUIFrame parent, PageBaseRootDataModel pageBaseRootDataModel ){
		super();
	
		common( parent, pageBaseRootDataModel );		
	}
	
	private void common( GUIFrame parent, final PageBaseRootDataModel pageBaseRootDataModel ){
		this.parent = parent;		
		this.setLayout(new BorderLayout());
		
		field.setEditable( false );
		button = new JButton("...");
		
		this.button.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
	
				TreeSelector selector = new TreeSelector( TreeSelectionCombo.this, pageBaseRootDataModel );
			}
		} );

		this.add( field, BorderLayout.CENTER);
		this.add( button, BorderLayout.EAST );
	}
	
	public GUIFrame getParent(){
		return parent;
	}
	
	public void setSelectedElement( TreePath treePath ){
		this.treePath = treePath;
		PageBasePageDataModel pageDataModel = (PageBasePageDataModel)treePath.getLastPathComponent();
		field.setText( pageDataModel.getPathToString());
	}
	
}

class TreeSelector extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1607956458285776550L;
	
	public TreeSelector( TreeSelectionCombo treeSelectionCombo, PageBaseRootDataModel pageBaseRootDataModel ){
		super( treeSelectionCombo.getParent(), true );

		//A fo ablak kozepere igazitja a dialogus ablakot
		this.setLocationRelativeTo( treeSelectionCombo.getParent() );

		this.setLayout( new BorderLayout() );

		PageBaseTreeForSelect pageBaseTree = new PageBaseTreeForSelect( treeSelectionCombo, pageBaseRootDataModel );
		
		//Becsomagolom a Tree-t hogy scroll-ozhato legyen
		JScrollPane scrolledPageBaseTree = new JScrollPane( pageBaseTree );
		
		//Kiteszem a Treet az ablakba
		this.add( scrolledPageBaseTree, BorderLayout.CENTER );
		
		scrolledPageBaseTree.revalidate();
		
		this.pack();
		this.setVisible( true );
	}
	
	public void actionPerformed(ActionEvent e) {
System.err.println("lezart");		
	    setVisible(false); 
	    dispose(); 
	    
	  }
}


class PageBaseTreeForSelect extends JTree{

	private static final long serialVersionUID = 800888675922537771L;
	
	private DefaultMutableTreeNode selectedNode;
	private  TreeSelectionCombo treeSelectionCombo;

	public PageBaseTreeForSelect( TreeSelectionCombo treeSelectionCombo, PageBaseRootDataModel pageBaseRootDataModel ){
	
		super( new DefaultTreeModel(pageBaseRootDataModel) );
		
		this.treeSelectionCombo = treeSelectionCombo;
		this.treeModel = (DefaultTreeModel)this.getModel();
		
		//Ne latszodjon a root
		this.setRootVisible( false );

		//Alapesetben ennyi sor latszodjon
		this.setVisibleRowCount( 10 );
		
		//Csak egy elem lehet kivalasztva
		this.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
	
		/**
		 * Ikonokat helyezek el az egyes csomopontok ele
		 */
		this.setCellRenderer(new DefaultTreeCellRenderer() {

			private static final long serialVersionUID = 757338184891022316L;

			@Override
		    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean isLeaf, int row, boolean focused) {
		    	Component c = super.getTreeCellRendererComponent(tree, value, selected, expanded, isLeaf, row, focused);
		    	
		    	ImageIcon pageIcon = CommonOperations.createImageIcon("tree/pagebase-page-icon.png");
		    	ImageIcon elementIcon = CommonOperations.createImageIcon("tree/pagebase-element-icon.png");
		    	ImageIcon nodeClosedIcon = CommonOperations.createImageIcon("tree/node-closed-icon.png");
		    	ImageIcon nodeOpenIcon = CommonOperations.createImageIcon("tree/node-open-icon.png");
		    	
		    	//Felirata a NODE-nak
		    	setText( ((PageBaseDataModelInterface)value).getNameToString() );
		    	
		    	//Iconja a NODE-nak
		    	if( value instanceof PageBasePageDataModel){
		            setIcon(pageIcon);
		    	}else if( value instanceof PageBaseElementDataModel ){
		            setIcon(elementIcon);
		    	}else if( value instanceof PageBaseNodeDataModel){
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
	
	}
	
	/**
	 * 
	 * Letiltom a Page node lenyitasat, igy nem latszanak az Element-ek
	 * 
	 */
	protected void setExpandedState(TreePath path, boolean state) {
       
        if (state) {
        
        	if( !( path.getLastPathComponent() instanceof PageBasePageDataModel ) ){
        		super.setExpandedState(path, state);
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
			selectedNode = (DefaultMutableTreeNode)PageBaseTreeForSelect.this.getLastSelectedPathComponent();
			treeSelectionCombo.setSelectedElement( PageBaseTreeForSelect.this.getSelectionPath() );
			
aaa			
			
//TODO itt valasztok		
	
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
	