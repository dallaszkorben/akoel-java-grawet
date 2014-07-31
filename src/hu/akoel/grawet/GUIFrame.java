package hu.akoel.grawet;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import hu.akoel.grawet.views.PurePageNode;
import hu.akoel.grawet.views.PurePageTree;
import hu.akoel.grawet.views.TreeInterface;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class GUIFrame extends JFrame{

	private static final long serialVersionUID = 5462215116385991087L;
	
	private JMenuBar menuBar;
	private JMenu menu, submenu;
	private JMenuItem menuItem;
	
	TreePanel treePanel;
	EditorPanel editorPanel;
	AssistantPanel assistantPanel;
	
	PurePageTree purePageTree = new PurePageTree();
	
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
			
			
purePageTree.addNode( new PurePageNode("POS", "POS applikaciok tesztelese" ) );
purePageTree.addNode( new PurePageNode("DS", "DS applikaciok tesztelese" ) );
purePageTree.addNode( new PurePageNode("REV", "REV applikaciok tesztelese" ) );
			
			treePanel.show( purePageTree );

			
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
		
		public void show( TreeInterface tree ){

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








