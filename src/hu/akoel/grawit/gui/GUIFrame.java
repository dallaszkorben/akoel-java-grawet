package hu.akoel.grawit.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.IdentificationType;
import hu.akoel.grawit.VariableSample;
import hu.akoel.grawit.elements.ElementBase;
import hu.akoel.grawit.pages.PageBase;
import hu.akoel.grawit.tree.PageBaseTree;
import hu.akoel.grawit.tree.datamodel.PageBaseElementDataModel;
import hu.akoel.grawit.tree.datamodel.PageBaseNodeDataModel;
import hu.akoel.grawit.tree.datamodel.PageBasePageDataModel;
import hu.akoel.grawit.tree.datamodel.PageBaseRootDataModel;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class GUIFrame extends JFrame{
	private static final long serialVersionUID = 5462215116385991087L;
	
	private String appNameAndVersion;
	
	private static int treePanelStartWidth = 200;
	
	//Ki/be kapcsolhato menuelemeek
	private JMenuItem editPurePageMenuItem;
	private JMenuItem editPageMenuItem;
	private JMenuItem editTestCaseMenuItem;
	private JMenuItem fileSaveMenuItem;
	
	private TreePanel treePanel;
	private EditorPanel editorPanel;
	private AssistantPanel assistantPanel;
	
	private PageBaseRootDataModel pageBaseRootDataModel = null;
	//DefaultTreeModel pageBaseTreeModel = new DefaultTreeModel(pageBaseRootDataModel);
	
	private File usedDirectory = null;
	
	//Esemenyfigyelok a menupontokhoz
	private OpenActionListener openActionListener;
	private SaveAsActionListener saveAsActionListener;
	private SaveActionListener saveActionListener;
	private EditPurePageActionListener editPurePageActionListener;
	private EditParameterizedPageActionListener editParameterizedPageActionListener;
    
	public GUIFrame( String appNameAndVersion, int frameWidth, int frameHeight ){
		super( appNameAndVersion );
		
		this.appNameAndVersion = appNameAndVersion;
		
		//make sure the program exits when the frame closes
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize( frameWidth, frameHeight );
      
        //This will center the JFrame in the middle of the screen
        this.setLocationRelativeTo(null);
        //this.setDefaultLookAndFeelDecorated(false);
        
        try {
        	
        	// Ha esetleg Nimbus nincs implementalva az adott verzion
        	//UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
        	//UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
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

        //Save
        fileSaveMenuItem = new JMenuItem( CommonOperations.getTranslation("menu.element.file.savetestsuits") );
        fileSaveMenuItem.setMnemonic( KeyStroke.getKeyStroke(CommonOperations.getTranslation("menu.mnemonic.file.savetestsuits")).getKeyCode()); //KeyEvent.VK_S );
        //menuItem.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_S, ActionEvent.ALT_MASK));
        //menuItem.setMnemonic(KeyEvent.VK_S);
        saveActionListener = new SaveActionListener();
        fileSaveMenuItem.addActionListener( saveActionListener );
        fileSaveMenuItem.setEnabled( false );
        menu.add(fileSaveMenuItem);        
        
        //Save AS
        menuItem = new JMenuItem( CommonOperations.getTranslation("menu.element.file.savetestsuitsas") );
        menuItem.setMnemonic( KeyStroke.getKeyStroke(CommonOperations.getTranslation("menu.mnemonic.file.savetestsuitsas")).getKeyCode()); //KeyEvent.VK_S );
        //menuItem.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_S, ActionEvent.ALT_MASK));
        //menuItem.setMnemonic(KeyEvent.VK_S);
        saveAsActionListener = new SaveAsActionListener();
        menuItem.addActionListener( saveAsActionListener );
        menu.add(menuItem);
        
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
        
        
        this.setLayout( new BorderLayout(0,0) );

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
	
	public void showTreePanel( JTree tree ){
		treePanel.show(tree);
	}
	
	public void hideTreePanel(){
		treePanel.hide();
	}
	
	public void showEditorPanel( DataPanel panel ){
		editorPanel.hide();
		editorPanel.show( panel );
	}
	
/*	public void hideEditorPanel(){
		editorPanel.hide();
	}
*/
	class SaveActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	/**
	 * Save As..
	 * 
	 * @author akoel
	 *
	 */
	class SaveAsActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			if( null == pageBaseRootDataModel ){
				return;
			}
			
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			try{

				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
				Document doc = docBuilder.newDocument();

				Element rootElement = doc.createElement("grawit");
				doc.appendChild(rootElement);

				Element pageBaseElement = pageBaseRootDataModel.getXMLElement(doc);	
				rootElement.appendChild( pageBaseElement );
				
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(doc);
				
				//StreamResult result = new StreamResult("hello.xml");

				// Iras
				//transformer.transform(source, result);

				
				JFileChooser fc;
				if (null == usedDirectory) {
					fc = new JFileChooser(System.getProperty("user.dir"));
				} else {
					fc = new JFileChooser(usedDirectory);
				}

				// Filechooser inicializalasa a felhasznalo munkakonyvtaraba

				// A dialogus ablak cime
				fc.setDialogTitle("Save the plan");

				// Csak az XML kiterjesztesu fajlokat lathatom
				FileNameExtensionFilter filter = new FileNameExtensionFilter("xml", "xml");
				fc.setFileFilter(filter);

				// Nem engedi meg az "All" filter hasznalatat
				fc.setAcceptAllFileFilterUsed(false);

				// Dialogus ablak inditasa
				int returnVal = fc.showSaveDialog(GUIFrame.this);

				// Ha kivalasztottam a nevet
				if (returnVal == JFileChooser.APPROVE_OPTION) {

					File file = fc.getSelectedFile();
					String filePath = file.getPath();

					// Mindenkeppen XML lesz a kiterjesztese
					if (!filePath.toLowerCase().endsWith(".xml")) {
						file = new File(filePath + ".xml");
					}

					// Stream letrehozasa
					StreamResult result = new StreamResult(file);

					// Iras
					transformer.transform(source, result);

					setTitle( appNameAndVersion + " :: " + file.getName());

					usedDirectory = file;
					fileSaveMenuItem.setEnabled(true);

				}
						
				
			} catch (ParserConfigurationException | TransformerException e1) {
				JOptionPane.showMessageDialog(GUIFrame.this, "Nem sikerült a file mentése: \n" + e1.getMessage(), "Hiba", JOptionPane.ERROR_MESSAGE);
			}
			
		}
		
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

//TODO tree toltes			
			// TODO Be kell majd toltenem fajlbol az adatokat
			// Most csak direktben beirok valamit
			// Tulajdonkeppen a pageBaseRootDataModel-ot toltom fel
			// Ez tartalmazza az adatmodellt
			pageBaseRootDataModel = new PageBaseRootDataModel(); //Torli
			//DefaultTreeModel pageBaseTreeModel = new DefaultTreeModel(pageBaseRootDataModel);

			PageBaseNodeDataModel posNode = new PageBaseNodeDataModel("POS", "POS applikaciok tesztelese");
			pageBaseRootDataModel.add( posNode );


			PageBase firstPageBase = new PageBase( "Google kereso oldal", "Ez az elso oldal");
			ElementBase searchField = new ElementBase("SearchField", "gbqfq", IdentificationType.ID, VariableSample.POST );
			firstPageBase.addElement(searchField);
			ElementBase searchButton = new ElementBase("SearchButton", "gbqfb", IdentificationType.ID, VariableSample.NO );
			firstPageBase.addElement(searchButton);

			PageBasePageDataModel firstPageNode = new PageBasePageDataModel(firstPageBase);
			posNode.add( firstPageNode );

			PageBaseElementDataModel searchFieldNode = new PageBaseElementDataModel( searchField );
			firstPageNode.add( searchFieldNode );

			PageBaseElementDataModel searchButtonNode = new PageBaseElementDataModel( searchButton );
			firstPageNode.add(searchButtonNode);


			pageBaseRootDataModel.add( new PageBaseNodeDataModel("REV", "REV applikaciok tesztelese" ) );
			pageBaseRootDataModel.add( new PageBaseNodeDataModel("DS", "DS applikaciok tesztelese" ) );
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
			PageBaseTree tree = new PageBaseTree( GUIFrame.this, pageBaseRootDataModel );
			
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
	 * A TREE megjelenitesenek helye
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
			
			//Ures szerkesztoi ablak
			EmptyPanel emptyPanel = new EmptyPanel();								
			showEditorPanel( emptyPanel);		
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

		private JScrollPane panelToView = null;
		
		public EditorPanel(){
			this.setLayout( new BorderLayout(0,0));
			this.setBackground( Color.red);		
		}

		public void show( DataPanel panel ){

			//Ha volt valamilyen mas EditorPanel az ablakban akkor azt eltavolitom
			if( null != panelToView ){
				this.remove( panelToView );
			}
			
			//Becsomagolom az EditorPanel-t hogy scroll-ozhato legyen
			panelToView = new JScrollPane( (Component)panel );		
				
			//Valamiert generalodik egy boder, amit most el kell tuntetnem
			panelToView.setBorder(BorderFactory.createEmptyBorder());
			
			//Kiteszem az ablakba
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








