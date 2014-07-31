package hu.akoel.grawet;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import hu.akoel.grawet.CommonOperations.Browser;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSplitPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class GUIFrame extends JFrame{

	private static final long serialVersionUID = 5462215116385991087L;
	
	private JMenuBar menuBar;
	private JMenu menu, submenu;
	private JMenuItem menuItem;
    
	public GUIFrame( String title ){
		super( title );
		
		//make sure the program exits when the frame closes
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(300,250);
      
        //This will center the JFrame in the middle of the screen
        this.setLocationRelativeTo(null);

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

        JRadioButtonMenuItem rbMenuItem;
        JCheckBoxMenuItem cbMenuItem;
        
        menuBar = new JMenuBar();

        //
        //File menu
        //
        menu = new JMenu("File");
        menu.setMnemonic(KeyEvent.VK_F); 
        menuBar.add(menu);

        //File menu almenui
        //Open
        menuItem = new JMenuItem("Open test suit",KeyEvent.VK_O);
        menuItem.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_O, ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription("This doesn't really do anything");
        menu.add(menuItem); //Open menu

        //Save
        menuItem = new JMenuItem("Save test suit", KeyEvent.VK_S);
        menuItem.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_S, ActionEvent.ALT_MASK));
        menuItem.setMnemonic(KeyEvent.VK_S);
        menu.add(menuItem); //Save menu

        //a group of check box menu items
        menu.addSeparator();
      
        //Submenu
        submenu = new JMenu("Submenu");
//      submenu.setMnemonic(KeyEvent.VK_S);

        menuItem = new JMenuItem("Submemu eleme");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.ALT_MASK));
        submenu.add(menuItem);
        menu.add(submenu);

        //Edit menu
        menu = new JMenu("Edit");
        menu.setMnemonic(KeyEvent.VK_E);
        menu.getAccessibleContext().setAccessibleDescription("This menu does nothing");
        menuBar.add(menu);

        //Edit menu almenui
        //Edit Base Pages
        menuItem = new JMenuItem("Edit Base Pages",KeyEvent.VK_B);
        menuItem.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_B, ActionEvent.ALT_MASK));
        menu.add(menuItem);

        //Edit Pages
        menuItem = new JMenuItem("Edit Pages",KeyEvent.VK_P);
        menuItem.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_P, ActionEvent.ALT_MASK));
        menu.add(menuItem);

        //Edit Test Cases
        menuItem = new JMenuItem("Edit Test Cases",KeyEvent.VK_T);
        menuItem.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_T, ActionEvent.ALT_MASK));
        menu.add(menuItem);

        this.setJMenuBar(menuBar);
        
        //
        // Layout
        //
        this.setLayout( new BorderLayout() );
        
        //Status sor elhelyezese
        this.add( new StatusList(), BorderLayout.SOUTH);
        
        TreePanel treePanel = new TreePanel();
        
        EditorPanel editorPanel = new EditorPanel();
        
        AssistantPanel assistantPanel = new AssistantPanel();
        
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
}

/**
 * 
 * @author akoel
 *
 */
class StatusList extends JPanel{
	public StatusList(){
		
		this.setBorder(	BorderFactory.createLoweredBevelBorder() );
	}
}

class TreePanel extends JPanel{
	
	public TreePanel(){
		this.setBackground( Color.green );
	}	
}

class EditorPanel extends JPanel{
	
	public EditorPanel(){
		this.setBackground(Color.white);
	}
}

class AssistantPanel extends JPanel{
	
	public AssistantPanel(){
		this.setBackground(Color.blue);
	}
	
}