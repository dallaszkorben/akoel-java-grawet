package hu.akoel.grawet;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import hu.akoel.grawet.elements.ElementBase;
import hu.akoel.grawet.pages.PageBase;
import hu.akoel.grawet.tree.nodes.PageBaseLeaf;
import hu.akoel.grawet.tree.nodes.PageBaseRoot;
import hu.akoel.grawet.tree.nodes.PageBaseNode;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;

import org.openqa.selenium.By;

public class GUIFrame extends JFrame{

	private static final long serialVersionUID = 5462215116385991087L;
	
	private JMenuBar menuBar;
	private JMenu menu, submenu;
	private JMenuItem menuItem;
	
	TreePanel treePanel;
	EditorPanel editorPanel;
	AssistantPanel assistantPanel;
	
	PageBaseRoot pageBaseRoot = new PageBaseRoot();
	
	//Esemenyfigyelok a menupontokhoz
	private EditPurePageActionListener editPurePageActionListener;
	private EditParameterizedPageActionListener editParameterizedPageActionListener;
    
	public GUIFrame( String title ){
		super( title );
		
		//make sure the program exits when the frame closes
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(300,250);
      
        //This will center the JFrame in the middle of the screen
        this.setLocationRelativeTo(null);
        //this.setDefaultLookAndFeelDecorated(false);
        
        try {
        	UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
			//UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
			System.exit( -1 );
		}

        
        //
        // MENU
        //
     
        menuBar = new JMenuBar();

        //
        //File fomenu
        //
        menu = new JMenu( CommonOperations.getTranslation("menu.element.file"));
        menu.setMnemonic(KeyEvent.VK_F); 
        menuBar.add(menu);

        //File menu almenui
        //Open Test Suits                                                 
        menuItem = new JMenuItem( CommonOperations.getTranslation("menu.element.file.opentestsuits") );
        menuItem.setMnemonic( KeyStroke.getKeyStroke(CommonOperations.getTranslation("menu.mnemonic.file.opentestsuits")).getKeyCode()); //KeyEvent.VK_O
        //menuItem.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_O, ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription("This doesn't really do anything");
        menu.add(menuItem); //Open menu

        //Save Test Suits        
        menuItem = new JMenuItem( CommonOperations.getTranslation("menu.element.file.savetestsuits") );
        menuItem.setMnemonic( KeyStroke.getKeyStroke(CommonOperations.getTranslation("menu.mnemonic.file.savetestsuits")).getKeyCode()); //KeyEvent.VK_S );
        //menuItem.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_S, ActionEvent.ALT_MASK));
        //menuItem.setMnemonic(KeyEvent.VK_S);
        menu.add(menuItem); //Save menu

        //a group of check box menu items
        menu.addSeparator();
      
        //Submenu
        submenu = new JMenu("Submenu");
//      submenu.setMnemonic(KeyEvent.VK_S);
        menuItem = new JMenuItem("Submemu eleme");
//      menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.ALT_MASK));
        submenu.add(menuItem);
        menu.add(submenu);

        //
        //Edit fomenu
        //
        menu = new JMenu( CommonOperations.getTranslation("menu.element.edit") );
        menu.setMnemonic( KeyStroke.getKeyStroke( CommonOperations.getTranslation("menu.mnemonic.edit") ).getKeyCode()); // KeyEvent.VK_E);
        //menu.getAccessibleContext().setAccessibleDescription("This menu does nothing");
        menuBar.add(menu);

        //Edit menu almenui
        //Edit Base Pages
        editPurePageActionListener = new EditPurePageActionListener();
        menuItem = new JMenuItem( CommonOperations.getTranslation("menu.element.edit.pagebase") );
        menuItem.setMnemonic( KeyStroke.getKeyStroke(CommonOperations.getTranslation("menu.mnemonic.edit.pagebase") ).getKeyCode()); // KeyEvent.VK_B);
        //menuItem.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_B, ActionEvent.ALT_MASK));
        menuItem.addActionListener( editPurePageActionListener );
        menu.add(menuItem);

        //Edit Pages
        editParameterizedPageActionListener = new EditParameterizedPageActionListener();
        menuItem = new JMenuItem( CommonOperations.getTranslation("menu.element.edit.page") );
        menuItem.setMnemonic(  KeyStroke.getKeyStroke(CommonOperations.getTranslation("menu.mnemonic.edit.page") ).getKeyCode() ); //KeyEvent.VK_P);
        //menuItem.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_P, ActionEvent.ALT_MASK));
        menuItem.addActionListener( editParameterizedPageActionListener );
        menu.add(menuItem);

        //Edit Test Cases
        menuItem = new JMenuItem( CommonOperations.getTranslation("menu.element.edit.testcase") );
        menuItem.setMnemonic(  KeyStroke.getKeyStroke(CommonOperations.getTranslation("menu.mnemonic.edit.testcase") ).getKeyCode() ); //KeyEvent.VK_T;
        //menuItem.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_T, ActionEvent.ALT_MASK));
        menu.add(menuItem);

        this.setJMenuBar(menuBar);
        
        //
        // Layout
        //
        this.setLayout( new BorderLayout() );

        //Panelek elhelyezese
        this.add( new StatusPanel(), BorderLayout.SOUTH);        
        treePanel = new TreePanel();        
        editorPanel = new EditorPanel();        
        assistantPanel = new AssistantPanel();
        
        JSplitPane splitPaneLeft = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treePanel, editorPanel);
        splitPaneLeft.setOneTouchExpandable(false);
        splitPaneLeft.setDividerLocation(120);
        
        this.add( splitPaneLeft, BorderLayout.CENTER);
        
        
        //JSplitPane splitPaneRight = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, editorPanel, assistantPanel);
        //splitPaneRight.setOneTouchExpandable(false);
        //splitPaneRight.setDividerLocation(300);
        
        //this.add( splitPaneRight, BorderLayout.CENTER);
        this.add( assistantPanel, BorderLayout.EAST );
        
        
        
		//make sure the JFrame is visible
        this.setVisible(true);
	}
	
	class EditPurePageActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {

// Ez valamikor fel lett toltve. //			
pageBaseRoot = new PageBaseRoot();

DefaultTreeModel treeModel = new DefaultTreeModel(pageBaseRoot);

PageBaseNode pos = new PageBaseNode("POS", "POS applikaciok tesztelese");
pageBaseRoot.add( pos );

PageBaseNode pos_with = new PageBaseNode("WITH", "POS with something else");
pos.add( pos_with );


PageBase elsoOldal = new PageBase( "Google kereso oldal");
ElementBase searchField = new ElementBase("SearchField", By.id("gbqfq"), VariableSample.POST );
elsoOldal.addElement(searchField);

PageBaseLeaf firstPage = new PageBaseLeaf(elsoOldal);
pos_with.add( firstPage );







pageBaseRoot.add( new PageBaseNode("REV", "REV applikaciok tesztelese" ) );
pageBaseRoot.add( new PageBaseNode("DS", "DS applikaciok tesztelese" ) );
//
	
			//Legyartja a JTREE-t a modell alapjan
			JTree tree = new JTree( treeModel );
			tree.setShowsRootHandles(true);
			
			
			
			
			
			
			
ImageIcon leafIcon = CommonOperations.createImageIcon("page-html-icon.png");

tree.setCellRenderer(new DefaultTreeCellRenderer() {
    private Icon loadIcon = UIManager.getIcon("OptionPane.errorIcon");
    private Icon saveIcon = UIManager.getIcon("OptionPane.informationIcon");
   
    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean isLeaf, int row, boolean focused) {
    	Component c = super.getTreeCellRendererComponent(tree, value, selected, expanded, isLeaf, row, focused);
    	
    	if( value instanceof PageBaseLeaf)
            setIcon(this.loadIcon);
//        else
//            setIcon(saveIcon);
        return c;
    }
});



/*if (leafIcon != null) {
	DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
	//renderer.setLeafIcon(leafIcon);
	renderer.setIcon(leafIcon);
	tree.setCellRenderer(renderer);
} else {
	System.err.println("Leaf icon missing; using default.");
}
*/			
			
			//Es megjeleniti
			treePanel.show( tree );
			
		}		
	}
	
	class EditParameterizedPageActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			treePanel.hide();
			
		}
		
	}
	
	/**
	 * 
	 * @author afoldvarszky
	 *
	 */
	class TreePanel extends JPanel{
		
		private static final long serialVersionUID = -60536416293858503L;
		private JScrollPane panelToView = null;

		public TreePanel(){
				
			//Layout beallitas, hogy lehetoseg legyen teljes szelessegben megjeleniteni a tree-t
			this.setLayout( new BorderLayout());
			this.setBackground( Color.gray );			
		}
		
		public void show( JTree tree ){

			//Ha volt valamilyen mas Tree az ablakban akkor azt eltavolitom
			if( null != panelToView ){
				this.remove( panelToView );
			}
			
			//Becsomagolom a Tree-t hogy scroll-ozhato legyen
			panelToView = new JScrollPane( (Component)tree );		
				
			//Kiteszem a Treet az ablakba
			this.add( panelToView, BorderLayout.CENTER );
			
			//Ujrarajzoltatom
			this.revalidate();

		}
		
		public void hide(){
			
			//Ha volt valamilyen Tree az ablakban, azt eltavolitom
			if( null != panelToView ){
				this.remove( panelToView );
			}
			
			//Ujrarajzoltatom
			this.repaint();
			this.revalidate();
		}
			
	}
	
	/**
	 * 
	 * @author afoldvarszky
	 *
	 */
	class EditorPanel extends JPanel{
		
		private static final long serialVersionUID = 6460964071977967820L;

		public EditorPanel(){
			this.setBackground(Color.white);
		}

	}
	
	/**
	 * 
	 * @author afoldvarszky
	 *
	 */
	class AssistantPanel extends JPanel{
		
		public AssistantPanel(){
			this.setBackground(Color.blue);
		}

	}
	
	/**
	 * 
	 * @author afoldvarszky
	 *
	 */
	class StatusPanel  extends JPanel{
		public StatusPanel(){
			
			this.setBorder(	BorderFactory.createLoweredBevelBorder() );
			
		}

	}

}








