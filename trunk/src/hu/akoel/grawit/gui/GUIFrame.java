package hu.akoel.grawit.gui;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.driver.DriverRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.step.StepRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.variable.VariableRootDataModel;
import hu.akoel.grawit.exceptions.XMLPharseException;
import hu.akoel.grawit.gui.editor.BaseEditor;
import hu.akoel.grawit.gui.editor.EmptyEditor;
import hu.akoel.grawit.gui.tree.BaseTree;
import hu.akoel.grawit.gui.tree.DriverTree;
import hu.akoel.grawit.gui.tree.ParamTree;
import hu.akoel.grawit.gui.tree.RunTree;
import hu.akoel.grawit.gui.tree.TestcaseTree;
import hu.akoel.grawit.gui.tree.Tree;
import hu.akoel.grawit.gui.tree.VariableTree;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
	
	private String appName;
	private String appVersion;
	private String appDesigner;
	
	private static int treePanelStartWidth = 200;
	
	//Ki/be kapcsolhato menuelemeek
	private JMenuItem fileSaveMenuItem;
	private JMenuItem editDriverMenuItem;
	private JMenuItem editBaseMenuItem;
	private JMenuItem editParamMenuItem;
	private JMenuItem editVariableMenuItem;
	private JMenuItem editTestCaseMenuItem;
	private JMenuItem runRunMenuItem;
	private JMenuItem helpAboutMenuItem;
	
	private TreePanel treePanel;
	private EditorPanel editorPanel;

	private VariableRootDataModel variableRootDataModel = new VariableRootDataModel();
	private BaseRootDataModel baseRootDataModel = new BaseRootDataModel();
	private StepRootDataModel paramRootDataModel = new StepRootDataModel();	
	private TestcaseRootDataModel testcaseRootDataModel = new TestcaseRootDataModel();
	private DriverRootDataModel driverRootDataModel = new DriverRootDataModel();
	
	private File usedDirectory = null;
	
	//Esemenyfigyelok a menupontokhoz
	private NewActionListener newActionListener;
	private OpenActionListener openActionListener;
	private SaveAsActionListener saveAsActionListener;
	private SaveActionListener saveActionListener;
	private EditVariableActionListener editVariableActionListener;
	private EditBaseActionListener editBaseActionListener;
	private EditParamActionListener editParamActionListener;
	private EditTestcaseActionListener editTestcaseActionListener;
	private EditDriverActionListener editDriverActionListener;
	private RunRunActionListener runRunActionListener;
	private RunTree runTree = null;
	
	public GUIFrame( String appName, String appVersion, String appDesigner, int frameWidth, int frameHeight ){
		super( appName );
		
		//Mindig legefelul jelenik meg
		//this.setAlwaysOnTop(true);
		
		this.appName = appName;
		this.appVersion = appVersion;
		this.appDesigner = appDesigner;
		
		//Icon
		this.setIconImage(CommonOperations.createImageIcon("grawit_16x16.png").getImage());
		
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
        
        //
        //Edit fomenu
        //
        
        menu = new JMenu( CommonOperations.getTranslation("menu.element.edit") );
        menu.setMnemonic( KeyStroke.getKeyStroke( CommonOperations.getTranslation("menu.mnemonic.edit") ).getKeyCode()); // KeyEvent.VK_E);
        //menu.getAccessibleContext().setAccessibleDescription("This menu does nothing");
        menuBar.add(menu);

        //Edit Driver      
        editDriverMenuItem = new JMenuItem( CommonOperations.getTranslation("menu.element.edit.driver") );
        editDriverMenuItem.setMnemonic(  KeyStroke.getKeyStroke(CommonOperations.getTranslation("menu.mnemonic.edit.driver") ).getKeyCode() ); //KeyEvent.VK_P);
        editDriverActionListener = new EditDriverActionListener();
        editDriverMenuItem.addActionListener( editDriverActionListener );
        editDriverMenuItem.setEnabled( false );
        menu.add(editDriverMenuItem);  
        
        //Edit Variable Parameter      
        editVariableMenuItem = new JMenuItem( CommonOperations.getTranslation("menu.element.edit.variable") );
        editVariableMenuItem.setMnemonic(  KeyStroke.getKeyStroke(CommonOperations.getTranslation("menu.mnemonic.edit.variable") ).getKeyCode() ); //KeyEvent.VK_P);
        editVariableActionListener = new EditVariableActionListener();
        editVariableMenuItem.addActionListener( editVariableActionListener );
        editVariableMenuItem.setEnabled( false );
        menu.add(editVariableMenuItem);        
        
        menu.addSeparator();
        
        //Edit Base
        editBaseMenuItem = new JMenuItem( CommonOperations.getTranslation("menu.element.edit.basepage") );
        editBaseMenuItem.setMnemonic( KeyStroke.getKeyStroke(CommonOperations.getTranslation("menu.mnemonic.edit.basepage") ).getKeyCode()); // KeyEvent.VK_B);
        editBaseActionListener = new EditBaseActionListener();
        editBaseMenuItem.addActionListener( editBaseActionListener );
        editBaseMenuItem.setEnabled( false );
        menu.add(editBaseMenuItem);

        menu.addSeparator();
        
        //Edit Param      
        editParamMenuItem = new JMenuItem( CommonOperations.getTranslation("menu.element.edit.step") );
        editParamMenuItem.setMnemonic(  KeyStroke.getKeyStroke(CommonOperations.getTranslation("menu.mnemonic.edit.step") ).getKeyCode() ); //KeyEvent.VK_P);
        editParamActionListener = new EditParamActionListener();
        editParamMenuItem.addActionListener( editParamActionListener );
        editParamMenuItem.setEnabled( false );
        menu.add(editParamMenuItem);
        
        menu.addSeparator();
        
        //Edit Test Cases
        editTestCaseMenuItem = new JMenuItem( CommonOperations.getTranslation("menu.element.edit.testcase") );
        editTestCaseMenuItem.setMnemonic(  KeyStroke.getKeyStroke(CommonOperations.getTranslation("menu.mnemonic.edit.testcase") ).getKeyCode() ); //KeyEvent.VK_T;
        editTestcaseActionListener = new EditTestcaseActionListener();
        editTestCaseMenuItem.addActionListener( editTestcaseActionListener );
        editTestCaseMenuItem.setEnabled( false );
        menu.add(editTestCaseMenuItem);

        
        //
        //Run fomenu
        //
        
        menu = new JMenu( CommonOperations.getTranslation("menu.element.run") );
        menu.setMnemonic( KeyStroke.getKeyStroke( CommonOperations.getTranslation("menu.mnemonic.run") ).getKeyCode()); // KeyEvent.VK_E);
        menuBar.add(menu);

        //Run      
        runRunMenuItem = new JMenuItem( CommonOperations.getTranslation("menu.element.run.run") );
        runRunMenuItem.setMnemonic(  KeyStroke.getKeyStroke(CommonOperations.getTranslation("menu.mnemonic.run.run") ).getKeyCode() ); //KeyEvent.VK_P);
        runRunActionListener = new RunRunActionListener();
        runRunMenuItem.addActionListener( runRunActionListener );
        runRunMenuItem.setEnabled( false );
        menu.add(runRunMenuItem);  
        
     
        //
        //Help fomenu
        // 
        
        menu = new JMenu( CommonOperations.getTranslation("menu.element.help") );
        menu.setMnemonic( KeyStroke.getKeyStroke( CommonOperations.getTranslation("menu.mnemonic.help") ).getKeyCode()); // KeyEvent.VK_E);
        menuBar.add(menu);
        
        //About
        helpAboutMenuItem = new JMenuItem( CommonOperations.getTranslation("menu.element.help.about") );
        helpAboutMenuItem.setMnemonic(  KeyStroke.getKeyStroke(CommonOperations.getTranslation("menu.mnemonic.help.about") ).getKeyCode() ); //KeyEvent.VK_P);
        helpAboutMenuItem.addActionListener( new AboutHelpActionListener() );
        menu.add( helpAboutMenuItem );  
        
        //
        //About fomenu on the right side
        //
        
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
        //assistantPanel = new AssistantPanel();
        
        JSplitPane splitPaneLeft = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treePanel, editorPanel);
        splitPaneLeft.setOneTouchExpandable(false);
        splitPaneLeft.setDividerLocation( treePanelStartWidth );
        
        this.add( splitPaneLeft, BorderLayout.CENTER);
        
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
	
	public void showEditorPanel( BaseEditor panel ){
		editorPanel.hide();
		editorPanel.show( panel );
	}
	
	private void makeNewTestSuit(){
		
		runTree = null;
		
		//Kikapcsolom a PAGEBASE szerkesztesi menut
		editDriverMenuItem.setEnabled( false );
		editVariableMenuItem.setEnabled( false );
		editParamMenuItem.setEnabled( false );
		editBaseMenuItem.setEnabled( false );
		editTestCaseMenuItem.setEnabled( false );
		runRunMenuItem.setEnabled( false );
		
		//Ablak cimenek beallitasa
		setTitle( getWindowTitle() );
		
		baseRootDataModel.removeAllChildren();
		paramRootDataModel.removeAllChildren();
		variableRootDataModel.removeAllChildren();
		testcaseRootDataModel.removeAllChildren();
				
		JTree tree = treePanel.getTree();
		if ( null != tree ){
			((DefaultTreeModel)tree.getModel()).reload();
		}
		treePanel.hide();
		editorPanel.hide();
		
		//Bekapcsolom a PAGEBASE szerkesztesi menut
		editDriverMenuItem.setEnabled( true );
		editVariableMenuItem.setEnabled( true );
		editParamMenuItem.setEnabled( true );
		editBaseMenuItem.setEnabled( true );
		editTestCaseMenuItem.setEnabled( true );
		runRunMenuItem.setEnabled( true );
	}
	
	private void saveTestSuit( File file ) throws ParserConfigurationException, TransformerException{
	
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();

		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.newDocument();

		Element rootElement = doc.createElement("grawit");
		doc.appendChild(rootElement);
			
		//DRIVER mentese
		Element driverRootElement = driverRootDataModel.getXMLElement(doc);	
		rootElement.appendChild( driverRootElement );
		
		//VARIABLE PARAMETER mentese
		Element variableRootElement = variableRootDataModel.getXMLElement(doc);	
		rootElement.appendChild( variableRootElement );
		
		//PAGE BASEROOT mentese
		Element baseRootElement = baseRootDataModel.getXMLElement(doc);	
		rootElement.appendChild( baseRootElement );	
				
		//PARAMROOT PAGE mentese
		Element paramRootElement = paramRootDataModel.getXMLElement(doc);	
		rootElement.appendChild( paramRootElement );
				
		//TESTCASE mentese
		Element testcaseRootElement = testcaseRootDataModel.getXMLElement(doc);	
		rootElement.appendChild( testcaseRootElement );		
		
		
		
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
	 * 
	 * Save 
	 * 
	 * @author akoel
	 *
	 */
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
			
			DocumentBuilderFactory.newInstance();
			try{

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
										
					usedDirectory = file;
					fileSaveMenuItem.setEnabled(true);

					//Ablak cimenek allitasa
					setTitle( getWindowTitle() );
					
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
				
			runTree = null;
			
			//
			// Menuk tiltasa
			//
			editDriverMenuItem.setEnabled( false );
			
			editVariableMenuItem.setEnabled( false );
			
			//Kikapcsolom a PAGEBASE szerkesztesi menut
			editParamMenuItem.setEnabled( false );
			
			//Kikapcsolom a PAGE szerkesztesi menut
			editBaseMenuItem.setEnabled( false );
			
			//Kikapcsolom a TESTCASE szerkesztesi menut
			editTestCaseMenuItem.setEnabled( false );
			
			//Bekapcsolom a Run menut
			runRunMenuItem.setEnabled( false );
			
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

					// DRIVERS
					driverRootDataModel = new DriverRootDataModel(doc);
					
					// BASEROOT
					baseRootDataModel = new BaseRootDataModel(doc);
					
					// VARIABLEPARAMETER
					variableRootDataModel = new VariableRootDataModel(doc, baseRootDataModel );
					
					// PARAMROOT
					paramRootDataModel = new StepRootDataModel(doc, variableRootDataModel, baseRootDataModel );
						
					// TESTCASE
					//testcaseRootDataModel = new TestcaseRootDataModel(doc, variableRootDataModel, paramRootDataModel, specialRootDataModel, driverRootDataModel, scriptRootDataModel );
					testcaseRootDataModel = new TestcaseRootDataModel(doc, variableRootDataModel, baseRootDataModel, paramRootDataModel, driverRootDataModel );

					usedDirectory = file;
					fileSaveMenuItem.setEnabled(true);
					
					setTitle( getWindowTitle() );

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
			
			editDriverMenuItem.setEnabled( true );
			
			editVariableMenuItem.setEnabled( true );
			
			//Bekapcsolom a PAGEBASE szerkesztesi menut
			editBaseMenuItem.setEnabled( true );
			
			//Bekapcsolom a PAGE szerkesztesi menut
			editParamMenuItem.setEnabled( true );

			//Bekapcsolom a TESTCASE szerkesztesi menut
			editTestCaseMenuItem.setEnabled( true );
			
			//Bekapcsolom a Run menut
			runRunMenuItem.setEnabled( true );
	
		}
	}
	
	/**
	 * 
	 * Edit Driver menu selection listener
	 * 
	 * @author afoldvarszky
	 *
	 */
	class EditDriverActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
	
			//Legyartja a JTREE-t a modell alapjan
			DriverTree tree = new DriverTree( GUIFrame.this, driverRootDataModel );
			
			treePanel.hide();
			treePanel.show( tree );
			
		}		
	}
	
	/**
	 * 
	 * Edit Variable menu selection listener
	 * 
	 * @author akoel
	 *
	 */
	class EditVariableActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
	
			//Legyartja a JTREE-t a modell alapjan
			VariableTree tree = new VariableTree( GUIFrame.this, variableRootDataModel, paramRootDataModel );
			
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
			BaseTree tree = new BaseTree( GUIFrame.this, baseRootDataModel, paramRootDataModel );
			
			treePanel.hide();
			treePanel.show( tree );
			
		}		
	}
	
	/**
	 * 
	 * Edit Param menu selection listener 
	 * @author akoel
	 *
	 */
	class EditParamActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
						
			//Legyartja a JTREE-t a modell alapjan
			ParamTree tree = new ParamTree( GUIFrame.this, variableRootDataModel, baseRootDataModel, paramRootDataModel, testcaseRootDataModel );
			
			treePanel.hide();
			treePanel.show( tree );
			
		}
		
	}
	
	/**
	 * 
	 * Edit Testcase menu selection listener
	 * 
	 * @author akoel
	 *
	 */
	class EditTestcaseActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
						
			//Legyartja a JTREE-t a modell alapjan
			//TestcaseTree tree = new TestcaseTree( GUIFrame.this, baseRootDataModel, specialRootDataModel, paramRootDataModel, driverRootDataModel, testcaseRootDataModel, scriptRootDataModel );
			TestcaseTree tree = new TestcaseTree( GUIFrame.this, baseRootDataModel, paramRootDataModel, driverRootDataModel, testcaseRootDataModel );
			
			treePanel.hide();
			treePanel.show( tree );
			
		}
		
	}
		
	/**
	 * 
	 * Run Run menu selection listener 
	 * 
	 * @author akoel
	 *
	 */
	class RunRunActionListener implements ActionListener{

//		RunTree tree = null;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			if( null == runTree ){
			
				//Legyartja a JTREE-t a modell alapjan
				runTree = new RunTree( GUIFrame.this, driverRootDataModel, testcaseRootDataModel );
			
			}
			
			treePanel.hide();
			treePanel.show( runTree );
			
		}
		
	}
	
	/**
	 * About Help menu selecotion listener
	 * 
	 * @author afoldvarszky
	 *
	 */
	//TODO megcsinalni static-ra az AboutDialog-ot, hogy csak egyszer kelljen letrehozni
	//Mert hogy csak visible(false)-szal zarom le
	class AboutHelpActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			AboutDialog aboutDialog = new AboutDialog( GUIFrame.this );
	        aboutDialog.setLocationRelativeTo(GUIFrame.this);
	        aboutDialog.pack();
            aboutDialog.setVisible(true);            
		
		}		
		
		class AboutDialog extends JDialog{

			private static final long serialVersionUID = 1L;
			
			private JOptionPane optionPane;
			
			public AboutDialog( JFrame frame ){
			
				super( frame, true );
				setTitle( CommonOperations.getTranslation("menu.element.help.about") );
				
				//Application Name
				JLabel labelAppName = new JLabel();		
				labelAppName.setText( appName );
				labelAppName.setFont(new Font( labelAppName.getFont().getName(), Font.BOLD, 20 ));
				
				//Version
				JLabel labelAppVersion = new JLabel();		
				labelAppVersion.setText( appVersion );
				labelAppVersion.setFont(new Font( labelAppVersion.getFont().getName(), Font.PLAIN, 13 ));
				
				//Designer
				JLabel labelAppDesigner = new JLabel();		
				labelAppDesigner.setText( appDesigner );
				labelAppDesigner.setFont(new Font( labelAppDesigner.getFont().getName(), Font.ITALIC, 10 ));
				
				Object[] array = { 
						labelAppName,
						labelAppVersion,
						" ",
						" ",
						" ",
						labelAppDesigner
				};
			        
				String btnString1 = CommonOperations.getTranslation("button.ok");
				Object[] options = {btnString1};
				
		        //Create the JOptionPane.
		        optionPane = new JOptionPane(array,
		                                    JOptionPane.PLAIN_MESSAGE,
		                                    JOptionPane.OK_OPTION,
		                                    CommonOperations.createImageIcon("grawit_64x64.png"),
		                                    options,
		                                    options[0]);
		 
		        optionPane.addPropertyChangeListener(new PropertyChangeListener() {
					
					@Override
					public void propertyChange(PropertyChangeEvent evt) {
						if (isVisible()){
							setVisible( false );
						}
						
					}
				});
		        
		        //Make this dialog display it.
		        setContentPane(optionPane);	        
		        
				setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);				
				addWindowListener(new WindowAdapter() {
		                public void windowClosing(WindowEvent we) {
		                	/*
		                	 * Instead of directly closing the window,
		                	 * we're going to change the JOptionPane's
		                	 * value property.
		                	 */	                	
		                	optionPane.setValue(new Integer(JOptionPane.OK_OPTION));
		            }
		        });				
			}			
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

			EmptyEditor emptyPanel = new EmptyEditor();								
			showEditorPanel( emptyPanel);
			
			if( tree instanceof Tree && tree.getSelectionCount() != 0 ){
				
				Tree runTree = (Tree)tree;
				runTree.changed();
				
			}
			
			
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
			this.setBorder(BorderFactory.createEmptyBorder());
		}

		public void show( BaseEditor panel ){
			this.removeAll();

			if( panel.isScrollEnabled() ){
				
				panelToView = new JScrollPane( (Component)panel );	
				panelToView.setBorder(BorderFactory.createEmptyBorder());
				this.add( panelToView, BorderLayout.CENTER );				
				
			}else{
				this.add( panel, BorderLayout.CENTER );		
			}
	
			//Ujrarajzoltatom
			this.revalidate();

		}
		
		public void hide(){
			
			//Ha volt valamilyen Tree az ablakban, azt eltavolitom
//			if( null != panelToView ){
//				this.remove( panelToView );
//			}
this.removeAll();			
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
	class StatusPanel  extends JPanel{

		private static final long serialVersionUID = 1L;

		public StatusPanel(){
			
			this.setBorder(	BorderFactory.createLoweredBevelBorder() );
			
		}

	}
	
	private String getWindowTitle(){
		if( null == usedDirectory ){
			return appName;
		}
		return usedDirectory.getName() + " - " + appName;
	}

}








