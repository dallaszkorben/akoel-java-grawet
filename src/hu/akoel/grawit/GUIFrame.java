package hu.akoel.grawit;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import hu.akoel.grawit.elements.ElementBase;
import hu.akoel.grawit.pages.PageBase;
import hu.akoel.grawit.tree.PageBaseTree;
import hu.akoel.grawit.tree.tags.PageBaseTreeElement;
import hu.akoel.grawit.tree.tags.PageBaseTreeNode;
import hu.akoel.grawit.tree.tags.PageBaseTreePage;
import hu.akoel.grawit.tree.tags.PageBaseTreeRoot;

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
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;

import org.openqa.selenium.By;

public class GUIFrame extends JFrame{
	private static final long serialVersionUID = 5462215116385991087L;
	
	private static int frameWidth = 600;
	private static int frameHeight = 300;
	private static int treePanelStartWidth = 200;
	
	//Ki/be kapcsolhato menuelemeek
	JMenuItem editPurePageMenuItem;
	JMenuItem editPageMenuItem;
	JMenuItem editTestCaseMenuItem;
	
	TreePanel treePanel;
	EditorPanel editorPanel;
	AssistantPanel assistantPanel;
	
	PageBaseTreeRoot pageBaseTreeRoot = null;
	//DefaultTreeModel pageBaseTreeModel = new DefaultTreeModel(pageBaseTreeRoot);
	
	//Esemenyfigyelok a menupontokhoz
	private OpenActionListener openActionListener;
	private EditPurePageActionListener editPurePageActionListener;
	private EditParameterizedPageActionListener editParameterizedPageActionListener;
    
	public GUIFrame( String title ){
		super( title );
		
		//make sure the program exits when the frame closes
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize( frameWidth, frameHeight );
      
        //This will center the JFrame in the middle of the screen
        this.setLocationRelativeTo(null);
        //this.setDefaultLookAndFeelDecorated(false);
        
        try {
        	
        	// Ha esetleg Nimbus nincs implementalva az adott verzion
        	//UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
        	UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
        	//UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        	//UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        	
        	for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            	        		
        		if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }                
            }

			//UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
			System.exit( -1 );
		}

        
        //---------
        //
        // MENU
        //
        //---------
        JMenuBar menuBar = new JMenuBar();

        //
        //File fomenu
        //
        JMenu menu = new JMenu( CommonOperations.getTranslation("menu.element.file"));
        menu.setMnemonic(KeyEvent.VK_F); 
        menuBar.add(menu);

        //File menu almenui
        //Open Test Suits     
        JMenuItem menuItem = new JMenuItem( CommonOperations.getTranslation("menu.element.file.opentestsuits") );
        menuItem.setMnemonic( KeyStroke.getKeyStroke(CommonOperations.getTranslation("menu.mnemonic.file.opentestsuits")).getKeyCode()); //KeyEvent.VK_O
        //menuItem.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_O, ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription("This doesn't really do anything");
        openActionListener = new OpenActionListener();
        menuItem.addActionListener( openActionListener );
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
        JMenu submenu = new JMenu("Submenu");
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
        editPurePageMenuItem = new JMenuItem( CommonOperations.getTranslation("menu.element.edit.pagebase") );
        editPurePageMenuItem.setMnemonic( KeyStroke.getKeyStroke(CommonOperations.getTranslation("menu.mnemonic.edit.pagebase") ).getKeyCode()); // KeyEvent.VK_B);
        //menuItem.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_B, ActionEvent.ALT_MASK));
        editPurePageActionListener = new EditPurePageActionListener();
        editPurePageMenuItem.addActionListener( editPurePageActionListener );
        editPurePageMenuItem.setEnabled( false );
        menu.add(editPurePageMenuItem);

        //Edit Pages        
        editPageMenuItem = new JMenuItem( CommonOperations.getTranslation("menu.element.edit.page") );
        editPageMenuItem.setMnemonic(  KeyStroke.getKeyStroke(CommonOperations.getTranslation("menu.mnemonic.edit.page") ).getKeyCode() ); //KeyEvent.VK_P);
        //menuItem.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_P, ActionEvent.ALT_MASK));
        editParameterizedPageActionListener = new EditParameterizedPageActionListener();
        editPageMenuItem.addActionListener( editParameterizedPageActionListener );
        editPageMenuItem.setEnabled( false );
        menu.add(editPageMenuItem);

        //Edit Test Cases
        editTestCaseMenuItem = new JMenuItem( CommonOperations.getTranslation("menu.element.edit.testcase") );
        editTestCaseMenuItem.setMnemonic(  KeyStroke.getKeyStroke(CommonOperations.getTranslation("menu.mnemonic.edit.testcase") ).getKeyCode() ); //KeyEvent.VK_T;
        //menuItem.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_T, ActionEvent.ALT_MASK));
        editTestCaseMenuItem.setEnabled( false );
        menu.add(editTestCaseMenuItem);

        this.setJMenuBar(menuBar);
        
        //--------
        //
        // Layout
        //
        //--------
        
        this.setLayout( new BorderLayout() );

        //Panelek elhelyezese
        this.add( new StatusPanel(), BorderLayout.SOUTH);        
        treePanel = new TreePanel();        
        editorPanel = new EditorPanel();        
        assistantPanel = new AssistantPanel();
        
        JSplitPane splitPaneLeft = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treePanel, editorPanel);
        splitPaneLeft.setOneTouchExpandable(false);
        splitPaneLeft.setDividerLocation( treePanelStartWidth );
        
        this.add( splitPaneLeft, BorderLayout.CENTER);
        
        
        //JSplitPane splitPaneRight = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, editorPanel, assistantPanel);
        //splitPaneRight.setOneTouchExpandable(false);
        //splitPaneRight.setDividerLocation(300);
        
        //this.add( splitPaneRight, BorderLayout.CENTER);
        this.add( assistantPanel, BorderLayout.EAST );
                
		//make sure the JFrame is visible
        this.setVisible(true);
	}
	
	/**
	 * 
	 * Open menu selection listener
	 * 
	 * @author akoel
	 *
	 */
	class OpenActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
				
			//Kikapcsolom a PAGEBASE szerkesztesi menut
			editPageMenuItem.setEnabled( false );
			editPurePageMenuItem.setEnabled( false );
			editTestCaseMenuItem.setEnabled( false );

			
			// TODO Be kell majd toltenem fajlbol az adatokat
			// Most csak direktben beirok valamit
			// Tulajdonkeppen a pageBaseTreeRoot-ot toltom fel
			// Ez tartalmazza az adatmodellt
			pageBaseTreeRoot = new PageBaseTreeRoot(); //Torli
			//DefaultTreeModel pageBaseTreeModel = new DefaultTreeModel(pageBaseTreeRoot);

			PageBaseTreeNode posNode = new PageBaseTreeNode("POS", "POS applikaciok tesztelese");
			pageBaseTreeRoot.add( posNode );


			PageBase firstPageBase = new PageBase( "Google kereso oldal");
			ElementBase searchField = new ElementBase("SearchField", By.id("gbqfq"), VariableSample.POST );
			firstPageBase.addElement(searchField);
			ElementBase searchButton = new ElementBase("SearchButton", By.id("gbqfb"), VariableSample.NO );
			firstPageBase.addElement(searchButton);

			PageBaseTreePage firstPageNode = new PageBaseTreePage(firstPageBase);
			posNode.add( firstPageNode );

			PageBaseTreeElement searchFieldNode = new PageBaseTreeElement( searchField );
			firstPageNode.add( searchFieldNode );

			PageBaseTreeElement searchButtonNode = new PageBaseTreeElement( searchButton );
			firstPageNode.add(searchButtonNode);


			pageBaseTreeRoot.add( new PageBaseTreeNode("REV", "REV applikaciok tesztelese" ) );
			pageBaseTreeRoot.add( new PageBaseTreeNode("DS", "DS applikaciok tesztelese" ) );
			//
			
			//Bakapcsolom a PAGEBASE szerkesztesi menut
			editPurePageMenuItem.setEnabled( true );
			
		}
		
	}
	
	/**
	 * 
	 * Edit PurePage menu selection listener
	 * 
	 * @author akoel
	 *
	 */
	class EditPurePageActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {


	
			//Legyartja a JTREE-t a modell alapjan
			PageBaseTree tree = new PageBaseTree( pageBaseTreeRoot );
			
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








