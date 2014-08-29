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
import hu.akoel.grawit.core.treenodedatamodel.roots.BaseRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.roots.ParamRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.roots.VariableRootDataModel;
import hu.akoel.grawit.exceptions.XMLPharseException;
import hu.akoel.grawit.gui.editor.DataEditor;
import hu.akoel.grawit.gui.editor.EmptyEditor;
import hu.akoel.grawit.gui.tree.BaseTree;
import hu.akoel.grawit.gui.tree.ParamTree;
import hu.akoel.grawit.gui.tree.VariableTree;

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
	private JMenuItem editBaseMenuItem;
	private JMenuItem editParamMenuItem;
	private JMenuItem editVariableMenuItem;
	private JMenuItem editTestCaseMenuItem;
	private JMenuItem fileSaveMenuItem;
	
	private TreePanel treePanel;
	private EditorPanel editorPanel;
	private AssistantPanel assistantPanel;
	
	private BaseRootDataModel baseRootDataModel = new BaseRootDataModel();
	private ParamRootDataModel paramRootDataModel = new ParamRootDataModel();
	private VariableRootDataModel variableRootDataModel = new VariableRootDataModel();
	
	private File usedDirectory = null;
	
	//Esemenyfigyelok a menupontokhoz
	private NewActionListener newActionListener;
	private OpenActionListener openActionListener;
	private SaveAsActionListener saveAsActionListener;
	private SaveActionListener saveActionListener;
	private EditVariableActionListener editVariableActionListener;
	private EditBaseActionListener editBaseActionListener;
	private EditParamActionListener editParamActionListener;
    
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

        //Edit Variable Parameter      
        editVariableMenuItem = new JMenuItem( CommonOperations.getTranslation("menu.element.edit.variable") );
        editVariableMenuItem.setMnemonic(  KeyStroke.getKeyStroke(CommonOperations.getTranslation("menu.mnemonic.edit.variable") ).getKeyCode() ); //KeyEvent.VK_P);
        editVariableActionListener = new EditVariableActionListener();
        editVariableMenuItem.addActionListener( editVariableActionListener );
        editVariableMenuItem.setEnabled( false );
        menu.add(editVariableMenuItem);
        
        //Edit menu almenui
        //Edit Base
        editBaseMenuItem = new JMenuItem( CommonOperations.getTranslation("menu.element.edit.basepage") );
        editBaseMenuItem.setMnemonic( KeyStroke.getKeyStroke(CommonOperations.getTranslation("menu.mnemonic.edit.basepage") ).getKeyCode()); // KeyEvent.VK_B);
        editBaseActionListener = new EditBaseActionListener();
        editBaseMenuItem.addActionListener( editBaseActionListener );
        editBaseMenuItem.setEnabled( false );
        menu.add(editBaseMenuItem);

        //Edit Param      
        editParamMenuItem = new JMenuItem( CommonOperations.getTranslation("menu.element.edit.parampage") );
        editParamMenuItem.setMnemonic(  KeyStroke.getKeyStroke(CommonOperations.getTranslation("menu.mnemonic.edit.parampage") ).getKeyCode() ); //KeyEvent.VK_P);
        editParamActionListener = new EditParamActionListener();
        editParamMenuItem.addActionListener( editParamActionListener );
        editParamMenuItem.setEnabled( false );
        menu.add(editParamMenuItem);
        
        //Edit Test Cases
        editTestCaseMenuItem = new JMenuItem( CommonOperations.getTranslation("menu.element.edit.testcase") );
        editTestCaseMenuItem.setMnemonic(  KeyStroke.getKeyStroke(CommonOperations.getTranslation("menu.mnemonic.edit.testcase") ).getKeyCode() ); //KeyEvent.VK_T;
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
		editVariableMenuItem.setEnabled( false );
		editParamMenuItem.setEnabled( false );
		editBaseMenuItem.setEnabled( false );
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
		editVariableMenuItem.setEnabled( true );
		editParamMenuItem.setEnabled( true );
		editBaseMenuItem.setEnabled( true );
		editTestCaseMenuItem.setEnabled( true );
	}
	
	private void saveTestSuit( File file ) throws ParserConfigurationException, TransformerException{
	
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();

		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.newDocument();

		Element rootElement = doc.createElement("grawit");
		doc.appendChild(rootElement);
			
		//VARIABLE PARAMETER mentese
		Element variableRootElement = variableRootDataModel.getXMLElement(doc);	
		rootElement.appendChild( variableRootElement );
		
		//PAGE BASEROOT mentese
		Element baseRootElement = baseRootDataModel.getXMLElement(doc);	
		rootElement.appendChild( baseRootElement );
		
		//PARAMROOT PAGE mentese
		Element paramRootElement = paramRootDataModel.getXMLElement(doc);	
		rootElement.appendChild( paramRootElement );
						
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

				//PAGE BASEROOT mentese
				Element pageBaseElement = baseRootDataModel.getXMLElement(doc);	
				rootElement.appendChild( pageBaseElement );
				
				//PARAMROOT PAGE mentese
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
					//Ablak cimenek allitasa
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
			
			editVariableMenuItem.setEnabled( false );
			
			//Kikapcsolom a PAGEBASE szerkesztesi menut
			editParamMenuItem.setEnabled( false );
			
			//Kikapcsolom a PAGE szerkesztesi menut
			editBaseMenuItem.setEnabled( false );
			
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

					// VARIABLEPARAMETER
					variableRootDataModel = new VariableRootDataModel(doc);
					
					// BASEROOT
					baseRootDataModel = new BaseRootDataModel(doc);
					
					// PARAMROOT
					paramRootDataModel = new ParamRootDataModel(doc, baseRootDataModel, variableRootDataModel );
						
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
			
			//
			// Menuk engedelyezese
			//
			
			editVariableMenuItem.setEnabled( true );
			
			//Bekapcsolom a PAGEBASE szerkesztesi menut
			editBaseMenuItem.setEnabled( true );
			
			//Bekapcsolom a PAGE szerkesztesi menut
			editParamMenuItem.setEnabled( true );

			//Bekapcsolom a TESTCASE szerkesztesi menut
			editTestCaseMenuItem.setEnabled( false );
	
		}
	}
	
	class EditVariableActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
	
			//Legyartja a JTREE-t a modell alapjan
			VariableTree tree = new VariableTree( GUIFrame.this, variableRootDataModel );
			
			treePanel.hide();
			treePanel.show( tree );
			
		}		
	}
	
	/**
	 * 
	 * Edit PurePage menu selection listener
	 * 
	 * @author akoel
	 *
	 */
	class EditBaseActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
	
			//Legyartja a JTREE-t a modell alapjan
			BaseTree tree = new BaseTree( GUIFrame.this, baseRootDataModel );
			
			treePanel.hide();
			treePanel.show( tree );
			
		}		
	}
	
	class EditParamActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
						
			//Legyartja a JTREE-t a modell alapjan
			ParamTree tree = new ParamTree( GUIFrame.this, paramRootDataModel, baseRootDataModel, variableRootDataModel );
			
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








