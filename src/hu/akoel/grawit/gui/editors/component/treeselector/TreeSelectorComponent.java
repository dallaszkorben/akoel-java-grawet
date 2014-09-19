package hu.akoel.grawit.gui.editors.component.treeselector;

import hu.akoel.grawit.core.treenodedatamodel.DataModelInterface;
import hu.akoel.grawit.gui.editors.component.EditorComponentInterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

public abstract class TreeSelectorComponent<F extends DataModelInterface> extends JPanel implements EditorComponentInterface{

	private static final long serialVersionUID = 2246129334894062585L;
	
	private static final Color FIELD_BACKGROUND = new Color(209,224,224);
	private JButton button;
	private JTextField field = new JTextField();
	private F selectedDataModel;
	private Class<F> classForSelect;
	
	/**
	 * Uj rogzites
	 * 
	 * @param rootDataModel
	 */
	public TreeSelectorComponent( Class<F> classForSelect, DataModelInterface rootDataModel ){
		super();
	
		common( classForSelect, rootDataModel, null );		
	}
	
	/**
	 * Modositas
	 * 
	 * @param rootDataModel
	 * @param selectedDataModel
	 */
	public TreeSelectorComponent( Class<F> classForSelect, DataModelInterface rootDataModel, F selectedDataModel ){
		super();
	
		common( classForSelect, rootDataModel, selectedDataModel );

		setSelectedDataModelToField( selectedDataModel );
		
	}
	
	private void common( Class<F> classForSelect, final DataModelInterface rootDataModel, final F selectedDataModel ){	
		
		this.classForSelect = classForSelect;
		
		this.setLayout(new BorderLayout());
		
		field.setEditable( false );
		field.setBackground(FIELD_BACKGROUND);
				
		button = new JButton("...");
		
		//Ha benyomom a gombot
		this.button.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				//Akkor megnyitja a Dialogus ablakot a Page valasztashoz
				new SelectorDialog( TreeSelectorComponent.this, rootDataModel, selectedDataModel );
			}
		} );

		this.add( field, BorderLayout.CENTER);
		this.add( button, BorderLayout.EAST );
	}
	
	public Document getDocument(){
		return field.getDocument();
	}

	@Override
	public void setEnableModify(boolean enable) {
		button.setEnabled( enable );
	}

	@Override
	public Component getComponent() {	
		return this;
	}
	
	public F getSelectedDataModel(){
		if( null == selectedDataModel ){
			return null;
		}		return selectedDataModel;
	}
	
	/**
	 * A parameterkent megkapott kivalasztott elemet elhelyezi a valtozoban e megjeleniti a mezoben
	 * 
	 * @param selectedDataModel
	 */
	public void setSelectedDataModelToField( F selectedDataModel ){
		this.selectedDataModel = selectedDataModel;	
		field.setText( getSelectedDataModelToString(selectedDataModel) );
	}
	
	/**
	 * A parameterkent megadott DataModel-bol general egy azonositot, amit megjelenit a mezoben
	 * 	
	 * @param selectedDataModel
	 * @return
	 */
	public abstract String getSelectedDataModelToString( F selectedDataModel );

	/**
	 * A parameterkent megadott Node-hoz rendel egy ikont
	 * 
	 * @param actualNode
	 * @return
	 */
	public abstract ImageIcon getIcon( DataModelInterface actualNode, boolean expanded );

	/**
	 * Megmondja, hogy a parameterkent megadott Path nyiljon-e ki vagy sem
	 * Ennak a metodusnak a segitsegevel bizonyos tipusu node-okat letilthatunk a kinyitastol
	 * 
	 *  return !( path.getLastPathComponent() instanceof BasePageDataModel ) );
	 *  hasznalata eseten a BASEPAGE node hoz kapcsolodo ujabb agak mindig zarva maradnak
	 *  
	 * @param path
	 * @param state
	 * @return
	 */
	public abstract boolean needToExpand( TreePath path, boolean state);

	/**
	 * 
	 * Modalis tipusu PageBasePage selector ablak.
	 * A "..." nyomogomb hatasara nyilik ki
	 * A fa strukturat jeleniti meg
	 * 
	 * @author akoel
	 *
	 */
	class SelectorDialog extends JDialog{

		private static final long serialVersionUID = 1607956458285776550L;
	
		public SelectorDialog( TreeSelectorComponent<F> treeSelectorComponent, DataModelInterface rootDataModel, F selectedDataModel ){

			super( );

			//Modalis a PageBasePage selector ablak
			this.setModal( true );
		
			//A fo ablak kozepere igazitja a dialogus ablakot
			this.setLocationRelativeTo( treeSelectorComponent );

			this.setLayout( new BorderLayout() );

			//Elkesziti a BasePage faszerkezetet
			TreeForSelect pageBaseTree = new TreeForSelect( rootDataModel );
			
			if( null != selectedDataModel ){
				pageBaseTree.setSelectionPath( new TreePath( selectedDataModel.getPath() ) );
			}			
		
			//Becsomagolom a BasePage faszerkezetet hogy scroll-ozhato legyen
			JScrollPane scrolledPageBaseTree = new JScrollPane( pageBaseTree );
		
			//Kiteszem a Treet az ablakba
			this.add( scrolledPageBaseTree, BorderLayout.CENTER );
		
			scrolledPageBaseTree.revalidate();
		
			this.setSize(300 , 500);
		
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
	
		/**
		 * Ez a tulajdonkeppeni fa
		 * 
		 * @author akoel
		 *
		 */
		class TreeForSelect extends JTree{

			private static final long serialVersionUID = 800888675922537771L;
			
			public TreeForSelect( DataModelInterface rootDataModel ){
		
				super( new DefaultTreeModel(rootDataModel) );
			
				//this.treeModel = (DefaultTreeModel)this.getModel();
			
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
			    	
						//Felirata a NODE-nak
						setText( ((DataModelInterface)value).getName() );
			    	
						//Ikon a NODE-nak
						setIcon( TreeSelectorComponent.this.getIcon( (DataModelInterface)value, expanded ) );
							    	
						return c;
					}
				});		

				/**
				 * A eger benyomasara reagalok
				 */
				this.addMouseListener( new MouseListener() {
					
					@Override
					public void mouseClicked(MouseEvent e) {			
				
						//Ha bal-eger gombot nyomtam 
						if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2) {
						
							//A kivalasztott NODE			
							DataModelInterface selectedNode = (DataModelInterface)TreeForSelect.this.getLastSelectedPathComponent();

							//Ha megfelelo tipusu elemet valasztottam
							if( classForSelect.isInstance( selectedNode )){							
							
								//A kivalasztott NODE			
								TreeSelectorComponent.this.setSelectedDataModelToField( ((F)selectedNode) );
								SelectorDialog.this.close();
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
	
				} );		
			}
		
			/**
			 * 
			 * Letiltom az adott tipusu node lenyitasat, igy nem latszanak az Element-ek
			 * 
			 */
			protected void setExpandedState(TreePath path, boolean state) {
	       
				if (state) {

					if( needToExpand( path, state)){

						super.setExpandedState(path, state);
					}
				}else{
					
					super.setExpandedState(path, state);
					
				}
			}
		}
	}
}


	