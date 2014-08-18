package hu.akoel.grawit.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.datamodel.roots.BaseRootDataModel;
import hu.akoel.grawit.core.datamodel.roots.ParamRootDataModel;
import hu.akoel.grawit.exceptions.XMLPharseException;
import hu.akoel.grawit.gui.editor.DataEditor;
import hu.akoel.grawit.gui.editor.EmptyEditor;
import hu.akoel.grawit.gui.tree.BasePageTree;
import hu.akoel.grawit.gui.tree.ParamPageTree;
import hu.akoel.grawit.gui.tree.datamodel.ParamPageNodeDataModel;

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
import javax.swing.tree.DefaultTreeModel;
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
import org.xml.sax.SAXException;

public class GUIFrame extends JFrame{
	private static final long serialVersionUID = 5462215116385991087L;
	
	private String appNameAndVersion;
	
	private static int treePanelStartWidth = 200;
	
	//Ki/be kapcsolhato menuelemeek
	private JMenuItem editPageBaseMenuItem;
	private JMenuItem editParamPageMenuItem;
	private JMenuItem editTestCaseMenuItem;
	private JMenuItem fileSaveMenuItem;
	
	private TreePanel treePanel;
	private EditorPanel editorPanel;
	private AssistantPanel assistantPanel;
	
	private BaseRootDataModel baseRootDataModel = new BaseRootDataModel();
	private ParamRootDataModel paramRootDataModel = new ParamRootDataModel();
	
	private File usedDirectory = null;
	
	//Esemenyfigyelok a menupontokhoz
	private NewActionListener newActionListener;
	private OpenActionListener openActionListener;
	private SaveAsActionListener saveAsActionListener;
	private SaveActionListener saveActionListener;
	private EditPageBaseActionListener editPageBaseActionListener;
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

        //
        //File menu almenui  
        //
        
        //New test Suit
        JMenuItem menuItem = new JMenuItem( CommonOperations.getTranslation("menu.element.file.newtestsuit") );
        menuItem.setMnemonic( KeyStroke.getKeyStroke(CommonOperations.getTranslation("menu.mnemonic.file.newtestsuit")).getKeyCode());
        newActionListener = new NewActionListener();
        menuItem.addActionListener( newActionListener );
        menu.add( menuItem ); //New menu
        
        //Open Test Suits     
        menuItem = new JMenuItem( CommonOperations.getTranslation("menu.element.file.opentestsuit") );
        menuItem.setMnemonic( KeyStroke.getKeyStroke(CommonOperations.getTranslation("menu.mnemonic.file.opentestsuit")).getKeyCode()); //KeyEvent.VK_O
        //menuItem.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_O, ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription("This doesn't really do anything");
        openActionListener = new OpenActionListener();
        menuItem.addActionListener( openActionListener );
        menu.add(menuItem); //Open menu

        //Save
        fileSaveMenuItem = new JMenuItem( CommonOperations.getTranslation("menu.element.file.savetestsuit") );
        fileSaveMenuItem.setMnemonic( KeyStroke.getKeyStroke(CommonOperations.getTranslation("menu.mnemonic.file.savetestsuit")).getKeyCode()); //KeyEvent.VK_S );
        //menuItem.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_S, ActionEvent.ALT_MASK));
        //menuItem.setMnemonic(KeyEvent.VK_S);
        saveActionListener = new SaveActionListener();
        fileSaveMenuItem.addActionListener( saveActionListener );
        fileSaveMenuItem.setEnabled( false );
        menu.add(fileSaveMenuItem);        
        
        //Save AS
        menuItem = new JMenuItem( CommonOperations.getTranslation("menu.element.file.saveastestsuit") );
        menuItem.setMnemonic( KeyStroke.getKeyStroke(CommonOperations.getTranslation("menu.mnemonic.file.saveastestsuit")).getKeyCode()); //KeyEvent.VK_S );
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
        editPageBaseMenuItem = new JMenuItem( CommonOperations.getTranslation("menu.element.edit.pagebase") );
        editPageBaseMenuItem.setMnemonic( KeyStroke.getKeyStroke(CommonOperations.getTranslation("menu.mnemonic.edit.pagebase") ).getKeyCode()); // KeyEvent.VK_B);
        //menuItem.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_B, ActionEvent.ALT_MASK));
        editPageBaseActionListener = new EditPageBaseActionListener();
        editPageBaseMenuItem.addActionListener( editPageBaseActionListener );
        editPageBaseMenuItem.setEnabled( false );
        menu.add(editPageBaseMenuItem);

        //Edit Pages        
        editParamPageMenuItem = new JMenuItem( CommonOperations.getTranslation("menu.element.edit.page") );
        editParamPageMenuItem.setMnemonic(  KeyStroke.getKeyStroke(CommonOperations.getTranslation("menu.mnemonic.edit.page") ).getKeyCode() ); //KeyEvent.VK_P);
        //menuItem.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_P, ActionEvent.ALT_MASK));
        editParameterizedPageActionListener = new EditParameterizedPageActionListener();
        editParamPageMenuItem.addActionListener( editParameterizedPageActionListener );
        editParamPageMenuItem.setEnabled( false );
        menu.add(editParamPageMenuItem);

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
        
        makeNewTestSuit();
	}
	
	public void showTreePanel( JTree tree ){
		treePanel.show(tree);
	}
	
	public void hideTreePanel(){
		treePanel.hide();
	}
	
	public void showEditorPanel( DataEditor panel ){
		editorPanel.hide();
		editorPanel.show( panel );
	}
	
	private void makeNewTestSuit(){
		//Kikapcsolom a PAGEBASE szerkesztesi menut
		editParamPageMenuItem.setEnabled( false );
		editPageBaseMenuItem.setEnabled( false );
		editTestCaseMenuItem.setEnabled( false );
		
		//Ablak cimenek beallitasa
		setTitle( appNameAndVersion );
		
		baseRootDataModel.removeAllChildren();
		paramRootDataModel.removeAllChildren();
				
		JTree tree = treePanel.getTree();
		if ( null != tree ){
			((DefaultTreeModel)tree.getModel()).reload();
		}
		treePanel.hide();
		editorPanel.hide();
		
		//Bekapcsolom a PAGEBASE szerkesztesi menut
		editParamPageMenuItem.setEnabled( true );
		editPageBaseMenuItem.setEnabled( true );
		editTestCaseMenuItem.setEnabled( true );
	}
	
	private void saveTestSuit( File file ) throws ParserConfigurationException, TransformerException{
	
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();

		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.newDocument();

		Element rootElement = doc.createElement("grawit");
		doc.appendChild(rootElement);
			
		//PAGE BASE mentese
		Element pageBaseElement = baseRootDataModel.getXMLElement(doc);	
		rootElement.appendChild( pageBaseElement );
		
		//PARAM PAGE mentese
		Element paramPageElement = paramRootDataModel.getXMLElement(doc);	
		rootElement.appendChild( paramPageElement );
						
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		
		// Stream letrehozasa
		StreamResult result = new StreamResult( file );

		// Iras
		transformer.transform(source, result);
		
		//Tajekoztatas a sikeres metesrol
//		JOptionPane.showMessageDialog( GUIFrame.this, CommonOperations.getTranslation("mesage.information.savesuccessful"));
		
	}
	
	
	class SaveActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {

			if( null != usedDirectory ){

				try{

					//Konvertalas es mentes
					saveTestSuit( usedDirectory );
				
					//Tajekoztatas a sikeres metesrol
					JOptionPane.showMessageDialog( GUIFrame.this, CommonOperations.getTranslation("mesage.information.savesuccessful"));
					
				} catch (ParserConfigurationException | TransformerException e1) {
					
					//Tajekoztatas a sikertelen metesrol
					JOptionPane.showMessageDialog(GUIFrame.this, CommonOperations.getTranslation("mesage.error.savefailed") + ": \n" + e1.getMessage(), "Hiba", JOptionPane.ERROR_MESSAGE);
					fileSaveMenuItem.setEnabled(false);
				}	
			}			
		}		
	}
	
	/**
	 * 
	 * New
	 * 
	 * @author akoel
	 *
	 */
	class NewActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {			
			makeNewTestSuit();
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
			
			if( null == baseRootDataModel ){
				return;
			}
			
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			try{

/*				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
				Document doc = docBuilder.newDocument();

				Element rootElement = doc.createElement("grawit");
				doc.appendChild(rootElement);

				//PAGE BASE mentese
				Element pageBaseElement = baseRootDataModel.getXMLElement(doc);	
				rootElement.appendChild( pageBaseElement );
				
				//PARAM PAGE mentese
				Element paramPageElement = paramRootDataModel.getXMLElement(doc);	
				rootElement.appendChild( paramPageElement );
				
				
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(doc);
				
				//StreamResult result = new StreamResult("hello.xml");
*/
				// Iras
				
				JFileChooser fc;
				if (null == usedDirectory) {
					fc = new JFileChooser(System.getProperty("user.dir"));
				} else {
					fc = new JFileChooser(usedDirectory);
				}

				// Filechooser inicializalasa a felhasznalo munkakonyvtaraba

				// A dialogus ablak cime
				fc.setDialogTitle( CommonOperations.getTranslation( "window.title.savetestsuit" ));

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

					//Konvertalas es mentes elvegzese
					saveTestSuit(file);
					
					
/*					// Stream letrehozasa
					StreamResult result = new StreamResult(file);

					// Iras
					transformer.transform(source, result);
*/
					setTitle( appNameAndVersion + " :: " + file.getName());

					usedDirectory = file;
					fileSaveMenuItem.setEnabled(true);

					//Tajekoztatas a sikeres metesrol
					JOptionPane.showMessageDialog( GUIFrame.this, CommonOperations.getTranslation("mesage.information.savesuccessful"));

				}						
				
			} catch (ParserConfigurationException | TransformerException e1) {
				
				//Tajekoztatas a sikertelen metesrol
				JOptionPane.showMessageDialog(GUIFrame.this, CommonOperations.getTranslation("mesage.error.savefailed") + ": \n" + e1.getMessage(), "Hiba", JOptionPane.ERROR_MESSAGE);
				fileSaveMenuItem.setEnabled(false);

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
				
			//
			// Menuk tiltasa
			//
			
			//Kikapcsolom a PAGEBASE szerkesztesi menut
			editParamPageMenuItem.setEnabled( false );
			
			//Kikapcsolom a PAGE szerkesztesi menut
			editPageBaseMenuItem.setEnabled( false );
			
			//Kikapcsolom a TESTCASE szerkesztesi menut
			editTestCaseMenuItem.setEnabled( false );
			
			JFileChooser fc;
			if (null == usedDirectory) {
				fc = new JFileChooser(System.getProperty("user.dir"));
			} else {
				fc = new JFileChooser(usedDirectory);
			}
			
			//Dialogusablak cime
			fc.setDialogTitle( CommonOperations.getTranslation( "window.title.opentestsuit"));
			
			// Csak az XML kiterjesztesu fajlokat lathatom
			FileNameExtensionFilter filter = new FileNameExtensionFilter("xml", "xml");
			fc.setFileFilter(filter);

			// Nem engedi meg az "All" filter hasznalatat
			fc.setAcceptAllFileFilterUsed(false);

			// Dialogus ablak inditasa
			int returnVal = fc.showOpenDialog( GUIFrame.this );
			
			//File valasztas tortent
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				
				File file = fc.getSelectedFile();

				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder;
				try {
					
					dBuilder = dbFactory.newDocumentBuilder();

					// Error kimenetre irja hogy [Fatal Error] es csak utanna megy a catch agba
					Document doc = dBuilder.parse(file);

					// Recommended
					doc.getDocumentElement().normalize();

					// Root element = "grawit"
					// doc.getDocumentElement().getNodeName();

					//basePageRootDataModel = new BaseRootDataModel();
					paramRootDataModel = new ParamRootDataModel(); //Torli
				
					// BASEPAGE
					baseRootDataModel = new BaseRootDataModel(doc);
		
					setTitle( appNameAndVersion + " :: " + file.getName());

					usedDirectory = file;
					fileSaveMenuItem.setEnabled(true);

				} catch (ParserConfigurationException | SAXException | IOException e1) {

					JOptionPane.showMessageDialog(GUIFrame.this, CommonOperations.getTranslation("mesage.error.openfailed") + ": \n" + e1.getMessage(), "Hiba", JOptionPane.ERROR_MESSAGE);

				} catch( XMLPharseException e2 ){
					
					JOptionPane.showMessageDialog(GUIFrame.this, CommonOperations.getTranslation("mesage.error.openfailed") + ": \n" + e2.getMessage(), "Hiba", JOptionPane.ERROR_MESSAGE);
				}

				//
				// Ablakok zarasa
				//
				
				//Ha volt nyitva tree, akkor azt zarjuk, mert hogy bonyolult lenne kitalalnom, hogy mi volt nyitva. De vegul is meg lehetne csinalni TODO
				treePanel.hide();	
				
				//Ha volt nyitva Editor, akkor azt zarjuk
				editorPanel.hide();
			}
			
			
			
//TODO tree toltes			
			// TODO Be kell majd toltenem fajlbol az adatokat
			// Most csak direktben beirok valamit
			// Tulajdonkeppen a basePageRootDataModel-ot toltom fel
			// Ez tartalmazza az adatmodellt
			
			//
			// PAGEBASE
			//
/*
			//Root
			basePageRootDataModel = new BasePageRootDataModel(); //Torli

			//Node
			BaseNodeDataModel basePosNode = new BaseNodeDataModel("POS", "POS applikaciok tesztelese");			
			BaseNodeDataModel baseRevNode = new BaseNodeDataModel("REV", "REV applikaciok tesztelese");			
			BaseNodeDataModel baseDslNode = new BaseNodeDataModel("DS", "DS applikaciok tesztelese" );
			
			//Page
			BasePageDataModel basePosPage1 = new BasePageDataModel("Google kereso oldal", "Ez az elso oldal");
			BasePageDataModel basePosPage2 = new BasePageDataModel("Ez valami masik oldal", "Ez a kovetkezo oldal");
			
			//Element
			BaseElementDataModel basePosPage1Element1 = new BaseElementDataModel("SearchField", "gbqfq", IdentificationType.ID, VariableSample.POST );
			BaseElementDataModel basePosPage1Element2 = new BaseElementDataModel("SearchButton", "gbqfb", IdentificationType.ID, VariableSample.NO );
			BaseElementDataModel basePosPage1Element3 = new BaseElementDataModel("Mezo 1", "aaa", IdentificationType.CSS, VariableSample.PRE );

			//Assignment
			//Element -> Page
			basePosPage1.add(basePosPage1Element1);
			basePosPage1.add(basePosPage1Element2);
			basePosPage1.add(basePosPage1Element3);
			
			//Page -> Node
			basePosNode.add( basePosPage1);
			basePosNode.add( basePosPage2);
			
			//Node -> Root
			basePageRootDataModel.add(basePosNode);
			basePageRootDataModel.add(baseRevNode);
			basePageRootDataModel.add(baseDslNode);
*/			
			//
			// PARAMPAGE
			//
			paramRootDataModel = new ParamRootDataModel(); //Torli

			ParamPageNodeDataModel paramPosNode = new ParamPageNodeDataModel("POS PARAM", "POS applikaciok tesztelese");
			paramRootDataModel.add( paramPosNode );
/*

			BasePage firstPageBase = new BasePage( "Google kereso oldal", "Ez az elso oldal");
			BaseElement searchField = new BaseElement("SearchField", "gbqfq", IdentificationType.ID, VariableSample.POST );
			firstPageBase.addElement(searchField);
			BaseElement searchButton = new BaseElement("SearchButton", "gbqfb", IdentificationType.ID, VariableSample.NO );
			firstPageBase.addElement(searchButton);

			BasePagePageDataModel firstPageNode = new BasePagePageDataModel(firstPageBase);
			posNode.add( firstPageNode );

			BasePageElementDataModel searchFieldNode = new BasePageElementDataModel( searchField );
			firstPageNode.add( searchFieldNode );

			BasePageElementDataModel searchButtonNode = new BasePageElementDataModel( searchButton );
			firstPageNode.add(searchButtonNode);
*/

			paramRootDataModel.add( new ParamPageNodeDataModel("REV PARAM", "REV applikaciok tesztelese" ) );
			paramRootDataModel.add( new ParamPageNodeDataModel("DS PARAM", "DS applikaciok tesztelese" ) );			
			



			
			//
			// Menuk engedelyezese
			//
			
			//Bekapcsolom a PAGEBASE szerkesztesi menut
			editPageBaseMenuItem.setEnabled( true );
			
			//Bekapcsolom a PAGE szerkesztesi menut
			editParamPageMenuItem.setEnabled( true );

			//Bekapcsolom a TESTCASE szerkesztesi menut
			editTestCaseMenuItem.setEnabled( false );
	
		}
	}
	/**
	 * 
	 * Edit PurePage menu selection listener
	 * 
	 * @author akoel
	 *
	 */
	class EditPageBaseActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
	
			//Legyartja a JTREE-t a modell alapjan
			BasePageTree tree = new BasePageTree( GUIFrame.this, baseRootDataModel );
			
			treePanel.hide();
			treePanel.show( tree );
			
		}		
	}
	
	class EditParameterizedPageActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
						
			//Legyartja a JTREE-t a modell alapjan
			ParamPageTree tree = new ParamPageTree( GUIFrame.this, paramRootDataModel, baseRootDataModel );
			
			treePanel.hide();
			treePanel.show( tree );
			
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
		private JTree tree = null;

		public TreePanel(){
				
			//Layout beallitas, hogy lehetoseg legyen teljes szelessegben megjeleniteni a tree-t
			this.setLayout( new BorderLayout());
			this.setBackground( Color.gray );			
		}
		
		public void show( JTree tree ){

			this.tree = tree;
			
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
			EmptyEditor emptyPanel = new EmptyEditor();								
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
		
		public JTree getTree(){
			return tree;
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

		public void show( DataEditor panel ){

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








