package hu.akoel.grawit.gui.editor.component;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.pages.PageBase;
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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

public class PageBaseElementSelectorComponent extends JPanel implements EditorComponentInterface{

	private static final long serialVersionUID = -326596399207552100L;
	
	private JButton button;
	private JTextField field = new JTextField();
	private PageBasePageDataModel pageBasePageDataModel;
	
	public PageBaseElementSelectorComponent( PageBaseRootDataModel pageBaseRootDataModel ){
		super();
	
		common( pageBaseRootDataModel );		
	}
	
	public PageBaseElementSelectorComponent( PageBaseRootDataModel pageBaseRootDataModel, PageBase selectedPageBase ){
		super();
	
		common( pageBaseRootDataModel );

		PageBasePageDataModel pageBasePageDataModel = CommonOperations.getPageBasePageDataModelByPageBase(pageBaseRootDataModel, selectedPageBase);
		setSelectedPathToPageBase( pageBasePageDataModel );
		
	}
	
	private void common( final PageBaseRootDataModel pageBaseRootDataModel ){	
		this.setLayout(new BorderLayout());
		
		field.setEditable( false );
		button = new JButton("...");
		
		//Ha benyomom a gombot
		this.button.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				//Akkor megnyitja a Dialogus ablakot a Page valasztashoz
				//SelectorPageBasePageDialog selector = 
				new SelectorPageBasePageDialog( PageBaseElementSelectorComponent.this, pageBaseRootDataModel );
			}
		} );

		this.add( field, BorderLayout.CENTER);
		this.add( button, BorderLayout.EAST );
	}
	

	@Override
	public void setEnableModify(boolean enable) {
		button.setEnabled( enable );
	}

	@Override
	public Component getComponent() {	
		return this;
	}
	
	public PageBase getPageBase(){
		if( null == pageBasePageDataModel ){
			return null;
		}
		return pageBasePageDataModel.getPageBase();
	}
	
	public void setSelectedPathToPageBase( PageBasePageDataModel selectedPageBase ){
		this.pageBasePageDataModel = selectedPageBase;
		field.setText( selectedPageBase.getPathToString() );		
	}
	
}

/**
 * 
 * Modalis tipusu PageBasePage selector ablak.
 * A "..." nyomogomb hatasara nyilik ki
 * 
 * @author akoel
 *
 */
class SelectorPageBasePageDialog extends JDialog{

	private static final long serialVersionUID = 1607956458285776550L;
	
	public SelectorPageBasePageDialog( PageBaseElementSelectorComponent pageBasePageSelectorComponent, PageBaseRootDataModel pageBaseRootDataModel ){
		//super( pageBasePageSelectorComponent.getParent(), true );
		super( );

		//Modalis a PageBasePage selector ablak
		this.setModal( true );
		
		//A fo ablak kozepere igazitja a dialogus ablakot
		this.setLocationRelativeTo( pageBasePageSelectorComponent );

		this.setLayout( new BorderLayout() );

		//Elkesziti a PageBase faszerkezetet
		PageBaseTreeForSelect pageBaseTree = new PageBaseTreeForSelect( pageBasePageSelectorComponent, pageBaseRootDataModel );
		
		//Becsomagolom a PageBase faszerkezetet hogy scroll-ozhato legyen
		JScrollPane scrolledPageBaseTree = new JScrollPane( pageBaseTree );
		
		//Kiteszem a Treet az ablakba
		this.add( scrolledPageBaseTree, BorderLayout.CENTER );
		
		scrolledPageBaseTree.revalidate();
		
		this.setSize(200 , 200);
		
		//this.pack();
		this.setVisible( true );
	}
	
	/**
	 * 
	 * Lezarja a Dialog-ot
	 * 
	 */
	public void close() {
	    setVisible(false); 
	    dispose();    
	  }
	
	class PageBaseTreeForSelect extends JTree{

		private static final long serialVersionUID = 800888675922537771L;
		
		private PageBaseDataModelInterface selectedNode;
		private  PageBaseElementSelectorComponent pageBasePageSelectorComponent;

		public PageBaseTreeForSelect( PageBaseElementSelectorComponent pageBasePageSelectorComponent, PageBaseRootDataModel pageBaseRootDataModel ){
		
			super( new DefaultTreeModel(pageBaseRootDataModel) );
			
			this.pageBasePageSelectorComponent = pageBasePageSelectorComponent;
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
			
				//Ha bal-eger gombot nyomtam 
				if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2) {
					
					//A kivalasztott NODE			
					selectedNode = (PageBaseDataModelInterface)PageBaseTreeForSelect.this.getLastSelectedPathComponent();
					
					//Ha PAGEBASE PAGE-t valasztottam ki
					if( selectedNode instanceof PageBasePageDataModel ){
						
						//A kivalasztott NODE			
						pageBasePageSelectorComponent.setSelectedPathToPageBase( ((PageBasePageDataModel)selectedNode) );
						SelectorPageBasePageDialog.this.close();
					}
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
}



	