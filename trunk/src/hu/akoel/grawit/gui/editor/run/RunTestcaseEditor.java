package hu.akoel.grawit.gui.editor.run;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.text.MessageFormat;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JViewport;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.tree.TreeNode;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.Player;
import hu.akoel.grawit.core.treenodedatamodel.driver.DriverDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseCaseDataModel;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseFolderDataModel;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseStepDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseRootDataModel;
import hu.akoel.grawit.exceptions.CompilationException;
import hu.akoel.grawit.exceptions.PageException;
import hu.akoel.grawit.exceptions.StoppedByUserException;
import hu.akoel.grawit.gui.editor.BaseEditor;
import hu.akoel.grawit.gui.interfaces.progress.ElementProgressInterface;
import hu.akoel.grawit.gui.interfaces.progress.PageProgressInterface;
import hu.akoel.grawit.gui.interfaces.progress.TestcaseProgressInterface;
import hu.akoel.grawit.gui.tree.Tree;

public class RunTestcaseEditor extends BaseEditor implements Player{
	
	private static final long serialVersionUID = -7285419881714492620L;
	
	public static final int OUTPUT_PANEL_WIDTH = 300;
	public static final int RESULT_PANEL_WIDTH = 400;
	public static final int RESULT_PANEL_COLUMN_SUCCESS_WIDTH = 80;

	private TestcaseDataModelAdapter selectedTestcase;
	
	private PageProgress pageProgress;
	private ElementProgress elementProgres;
	private TestcaseProgress testcaseProgress;
	
	private JButton startButton;
	private JButton stopButton;
	private JButton pauseButton;
	
	private JTextPane consolPanel;
	private ResultPanel resultPanel;	
	private JTextArea outputPanel;
	private DefaultStyledDocument consolDocument;
	
	private SimpleAttributeSet attributeOK;
	private SimpleAttributeSet attributeFailed;
	private SimpleAttributeSet attributeError;
	private SimpleAttributeSet attributePageFinished;
	private SimpleAttributeSet attributeElementFinished;	
	
	private boolean needToStop = false;	
	
	private void setNeedToStop( boolean needToStop ){
		this.needToStop = needToStop;
	}
	
	@Override
	public boolean isStopped() {
		return needToStop;
	}

	public RunTestcaseEditor( Tree tree, TestcaseDataModelAdapter testcaseDataModel, DriverDataModelAdapter driverDataModel ){	

		super(
				( testcaseDataModel instanceof TestcaseRootDataModel) ? 
						CommonOperations.getTranslation( "editor.label.runtest.testrootwindowtitle" ) :
				( testcaseDataModel instanceof TestcaseCaseDataModel ) ? 
						CommonOperations.getTranslation( "editor.label.runtest.testcasewindowtitle" ) : 
				( testcaseDataModel instanceof TestcaseFolderDataModel) ? 
						CommonOperations.getTranslation( "editor.label.runtest.testnodewindowtitle" ) : 
				
				"");
		
		this.setScrollEnabled(false);
		this.selectedTestcase = testcaseDataModel;

		pageProgress = new PageProgress();
		elementProgres = new ElementProgress();	
		testcaseProgress = new TestcaseProgress();	
		
		ImageIcon startIcon = CommonOperations.createImageIcon("control/control-play.png");
		ImageIcon startDisabledIcon = CommonOperations.createImageIcon("control/control-play-disabled.png");
		ImageIcon startRolloverIcon = CommonOperations.createImageIcon("control/control-play-rollover.png");
		ImageIcon startPressedIcon = CommonOperations.createImageIcon("control/control-play-pressed.png");
		ImageIcon startSelectedIcon = CommonOperations.createImageIcon("control/control-play-selected.png");		
		
		ImageIcon stopIcon = CommonOperations.createImageIcon("control/control-stop.png");
		ImageIcon stopDisabledIcon = CommonOperations.createImageIcon("control/control-stop-disabled.png");
		ImageIcon stopRolloverIcon = CommonOperations.createImageIcon("control/control-stop-rollover.png");
		ImageIcon stopPressedIcon = CommonOperations.createImageIcon("control/control-stop-pressed.png");
		ImageIcon stopSelectedIcon = CommonOperations.createImageIcon("control/control-stop-selected.png");
		
		ImageIcon pauseIcon = CommonOperations.createImageIcon("control/control-pause.png");
		ImageIcon pauseDisabledIcon = CommonOperations.createImageIcon("control/control-pause-disabled.png");
		ImageIcon pauseRolloverIcon = CommonOperations.createImageIcon("control/control-pause-rollover.png");
		ImageIcon pausePressedIcon = CommonOperations.createImageIcon("control/control-pause-pressed.png");
		ImageIcon pauseSelectedIcon = CommonOperations.createImageIcon("control/control-pause-selected.png");
		
		GridBagConstraints c = new GridBagConstraints();

		// -------------
		//
		// CONTROL PANEL
		//
		// -------------
		
		//
		// START
		//
		startButton = new JButton();
		
		startButton.setBorderPainted(false);
		startButton.setBorder(null);
		startButton.setFocusable(false);
		startButton.setMargin(new Insets(0, 0, 0, 0));
		startButton.setContentAreaFilled(false);

		startButton.setSelectedIcon(startSelectedIcon);
		startButton.setRolloverIcon((startRolloverIcon));
		startButton.setPressedIcon(startPressedIcon);
		startButton.setDisabledIcon(startDisabledIcon);		
		startButton.setIcon(startIcon);
		
		startButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
								
				new Thread( new Runnable() {

					@Override
					public void run() {
						
						//Letiltja a Inditas gombot
						RunTestcaseEditor.this.startButton.setEnabled( false );
						setNeedToStop( false );
						//Engedelyezi a Stop gombot
						RunTestcaseEditor.this.stopButton.setEnabled( true );

						//Torli a panelek tartalmat
						outputPanel.setText("");
						resultPanel.clear();
						consolPanel.setText("");
//for(int i=0; i<20; i++){						
						//Vegrehajtja a teszteset(ek)et
						throughTestcases( RunTestcaseEditor.this.selectedTestcase );
//}				    	
						//Engedelyezi az Inditas gombot
				    	RunTestcaseEditor.this.startButton.setEnabled( true );
				    	//Tiltja a Stop gombot
				    	RunTestcaseEditor.this.stopButton.setEnabled( false );
			
					}				 
				 
				 }).start();
			}
						
		});		

		//
		// STOP
		//
		stopButton = new JButton();
		
		stopButton.setBorderPainted(false);
		stopButton.setBorder(null);
		stopButton.setFocusable(false);
		stopButton.setMargin(new Insets(0, 0, 0, 0));
		stopButton.setContentAreaFilled(false);

		stopButton.setSelectedIcon(stopSelectedIcon);
		stopButton.setRolloverIcon((stopRolloverIcon));
		stopButton.setPressedIcon(stopPressedIcon);
		stopButton.setDisabledIcon(stopDisabledIcon);
		stopButton.setIcon(stopIcon);

		stopButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
								
				setNeedToStop( true );
				
			}
						
		});	
		
		//
		// PAUSE
		//
		pauseButton = new JButton();
		
		pauseButton.setBorderPainted(false);
		pauseButton.setBorder(null);
		pauseButton.setFocusable(false);
		pauseButton.setMargin(new Insets(0, 0, 0, 0));
		pauseButton.setContentAreaFilled(false);

		pauseButton.setSelectedIcon(pauseSelectedIcon);
		pauseButton.setRolloverIcon((pauseRolloverIcon));
		pauseButton.setPressedIcon(pausePressedIcon);
		pauseButton.setDisabledIcon(pauseDisabledIcon);
		pauseButton.setIcon(pauseIcon);

		//Indulasi beallitas
		stopButton.setEnabled( false );
		pauseButton.setEnabled( false );
		
		//Ha nincs Driver definialva, akkor nem indulhat el egy teszt sem
		if( null == ((TestcaseRootDataModel)selectedTestcase.getRoot()).getDriverDataModel() ){
			startButton.setEnabled( false );
		}else{
			startButton.setEnabled( true );
		}
		
		
		// CONTROL
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout( new FlowLayout() );
		controlPanel.add(startButton);
		controlPanel.add(stopButton);
		controlPanel.add( pauseButton );		
		
		//---------------
		//
		// Consol panel
		//
		//---------------
		StyleContext consolrStyleContext = new StyleContext();
		consolDocument = new DefaultStyledDocument(consolrStyleContext);
		consolPanel = new JTextPane(consolDocument);
		consolPanel.setEditable( false );
		DefaultCaret consolCaret = (DefaultCaret)consolPanel.getCaret();
		consolCaret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		JScrollPane consolScrollablePanel = new JScrollPane(consolPanel);		
		JPanel lowerPanel = new JPanel();
		lowerPanel.setLayout( new BorderLayout() );
		lowerPanel.add( consolScrollablePanel, BorderLayout.CENTER );
		lowerPanel.setAutoscrolls(true);

		//Result
		resultPanel = new ResultPanel();
		JScrollPane scrollPaneForResultPanel = new JScrollPane(resultPanel);
		scrollPaneForResultPanel.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPaneForResultPanel.setHorizontalScrollBarPolicy( JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPaneForResultPanel.setPreferredSize( new Dimension( RESULT_PANEL_WIDTH, 1 ) );		
		
//Color color = scrollPaneForResultPanel.getViewport().getBackground();		
//scrollPaneForResultPanel.getViewport().setBackground(new Color( color.getRGB()));


		//OK attribute
		attributeOK = new SimpleAttributeSet();
		StyleConstants.setForeground( attributeOK, Color.GREEN );
		StyleConstants.setBold( attributeOK, true);
		
		//Failed attribute
		attributeFailed = new SimpleAttributeSet();
		StyleConstants.setForeground( attributeFailed, Color.RED );
		StyleConstants.setBold( attributeFailed, true);
		 
		//Error attribute
		attributeError = new SimpleAttributeSet();
		StyleConstants.setForeground( attributeError, Color.RED );
		StyleConstants.setBold( attributeError, true);
		 
		//Page finished attribute
		attributePageFinished = new SimpleAttributeSet();
		StyleConstants.setForeground( attributePageFinished, Color.BLACK );
		 
		//Element finished attribute
		attributeElementFinished = new SimpleAttributeSet();
		StyleConstants.setForeground( attributeElementFinished, Color.BLACK );
		
		//---------------
		//
		// Upper panel
		//
		//---------------		
		JPanel upperPanel = new JPanel();
		upperPanel.setLayout(new GridBagLayout());	
		upperPanel.setBorder( BorderFactory.createEmptyBorder( 0, 0, 0, 0 ) );

		// -----------
		//
		// Output panel
		//
		// -----------
		outputPanel = new JTextArea(2, 25);
		outputPanel.setEditable(false);
		
		JScrollPane scrollPaneForOutputPanel = new JScrollPane(outputPanel);
		scrollPaneForOutputPanel.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPaneForOutputPanel.setHorizontalScrollBarPolicy( JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
		scrollPaneForOutputPanel.setSize(1, OUTPUT_PANEL_WIDTH);

		// ------------------------
		//	RESULT PANEL felirat
		// ------------------------
		c.gridx = 1;		
		c.gridy = 0;		
		c.insets = new Insets(0,0,0,0);
		c.gridwidth = 1;
		c.gridheight = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0;
		c.weighty = 0;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		upperPanel.add( new JLabel( CommonOperations.getTranslation( "editor.label.runtest.result" ) ), c );

		// ------------------------
		// OUTPUT PANEL felirat
		// ------------------------
		c.gridx = 2;
		c.gridy = 0;
		c.insets = new Insets(0,0,0,0);
		c.gridwidth = 1;
		c.gridheight = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0;
		c.weighty = 0;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		upperPanel.add( new JLabel( CommonOperations.getTranslation( "editor.label.runtest.output" ) ), c );


		// ------------------------
		// RESULT PANEL elhelyezese
		// ------------------------
		c.gridx = 1;		
		c.gridy = 1;		
		c.insets = new Insets(0,0,0,0);
		c.gridwidth = 1;
		c.gridheight = 2;
		c.fill = GridBagConstraints.VERTICAL;
		c.weightx = 0;
		c.weighty = 1;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		upperPanel.add( scrollPaneForResultPanel, c );
		
		// ------------------------
		// OUTPUT PANEL elhelyezese
		// ------------------------
		c.gridx = 2;
		c.gridy = 1;
		c.insets = new Insets(0,0,0,0);
		c.gridwidth = 1;
		c.gridheight = 2;
		c.fill = GridBagConstraints.VERTICAL;
		c.weightx = 0;
		c.weighty = 1;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		upperPanel.add( scrollPaneForOutputPanel, c );


		
		// -------------------------
		// CONTROL PANEL elhelyezese
		// -------------------------\
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(0,0,0,0);
		c.gridwidth = 1;
		c.gridheight = 1;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 1;
		c.weighty = 0;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		upperPanel.add( controlPanel, c );	
	
				
		// -----------
		// DATASECTION
		// -----------		
		JPanel dataSection = new JPanel();
		dataSection.setLayout( new GridBagLayout() );
		
		//Utolso sorba egy kitolto elem helyezese, hogy felfele legyen igazitva az oldal
		c.insets = new Insets(0,0,0,0);
		c.gridx = 0;
		c.gridy = 2;		
		c.gridwidth = 0;
		c.weighty = 1;		//Ezzel tol fel minden felette levot
		dataSection.add( new JLabel(), c );		
		
		c.gridx = 0;
		c.gridy = 2;		
		c.insets = new Insets(0,0,0,0);
		c.gridwidth = 1;
		c.gridheight = 1;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		upperPanel.add( dataSection, c );	
		
		final JSplitPane horizontalSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, upperPanel, lowerPanel);
		horizontalSplitPane.setOneTouchExpandable(false);
//		horizontalSplitPane.setDividerLocation( 400 );	
		horizontalSplitPane.addComponentListener(new ComponentListener() {
			@Override
			public void componentShown(ComponentEvent e) {}
			
			@Override
			public void componentResized(ComponentEvent e) {
				int loc = horizontalSplitPane.getDividerLocation();
				int height = RunTestcaseEditor.this.getHeight();
				horizontalSplitPane.setDividerLocation( height - 300 );
				
			}			
			@Override
			public void componentMoved(ComponentEvent e) {}			
			@Override
			public void componentHidden(ComponentEvent e) {}
		});		
			
		this.add( horizontalSplitPane, BorderLayout.CENTER);
		
	}

	private void throughTestcases( TestcaseDataModelAdapter testcase ){

		if( isStopped() ){
			return;
		}
		
		//Ha egy TESTCASE-t kaptam, akkor azt vegrehajtatom
		if( testcase instanceof TestcaseCaseDataModel ){
			
			executeTestcase( (TestcaseCaseDataModel)testcase );
			
		//Ha egy csomopontot valasztottam ki, akkor annak elemein megyek keresztul
		}else if( testcase instanceof TestcaseFolderDataModel){
				
			//Vegig a teszteset csomopontjainak elemein
			int childrens = testcase.getChildCount();
			for( int i = 0; i < childrens; i++ ){
				
				Object object = testcase.getChildAt( i );
				
				throughTestcases( (TestcaseDataModelAdapter)object );
			}
			
		}		
		return;		
	}
	
	private void executeTestcase( TestcaseCaseDataModel actualTestcase ){

		//Ha be van kapcsolav		
		if( actualTestcase.isOn() ){

			
elementProgres.outputCommand( "import org.openqa.selenium.By;" );
elementProgres.outputCommand( "import org.openqa.selenium.WebDriver;" );
elementProgres.outputCommand( "import org.openqa.selenium.WebElement;" );
elementProgres.outputCommand( "import org.openqa.selenium.firefox.FirefoxDriver;" );
elementProgres.outputCommand( "import org.openqa.selenium.firefox.FirefoxProfile;" );
elementProgres.outputCommand( "import org.openqa.selenium.support.ui.Select;" );
elementProgres.outputCommand( "import org.openqa.selenium.support.ui.WebDriverWait;" );	
elementProgres.outputCommand( "import org.openqa.selenium.WebDriverException;" );	
elementProgres.outputCommand( "import org.openqa.selenium.support.ui.ExpectedConditions;" );
elementProgres.outputCommand( "import org.openqa.selenium.JavascriptExecutor;");
elementProgres.outputCommand( "import org.openqa.selenium.Keys;" );
elementProgres.outputCommand( "import org.openqa.selenium.support.ui.UnexpectedTagNameException;" );
elementProgres.outputCommand( "" );				  

elementProgres.outputCommand( "public class Test{ ");
elementProgres.outputCommand( "	" );
elementProgres.outputCommand( "	WebDriverWait wait = null;");
elementProgres.outputCommand( "	By by = null;" );
elementProgres.outputCommand( "	WebElement webElement = null;");
elementProgres.outputCommand( "	Select select = null;");	
elementProgres.outputCommand( "	Integer index = 0;" );
elementProgres.outputCommand( "	WebDriver driver = null;" );
elementProgres.outputCommand( "	FirefoxProfile profile = null;");
elementProgres.outputCommand( "	JavascriptExecutor executor = null;");

elementProgres.outputCommand( "	String origText;" );
elementProgres.outputCommand( "	Matcher matcher;" );
elementProgres.outputCommand( "	Pattern pattern;" );

elementProgres.outputCommand( "	" );
elementProgres.outputCommand( "	public static void main( String[] args ){" );
elementProgres.outputCommand( "		new Test();" );
elementProgres.outputCommand( "	}" );
elementProgres.outputCommand( "	" );
elementProgres.outputCommand( "	public Test(){" );	
elementProgres.outputCommand( "	" );


			WebDriver webDriver = ((TestcaseRootDataModel)actualTestcase.getRoot()).getDriverDataModel().getDriver(elementProgres);

			try{				

				int childCount = actualTestcase.getChildCount();
    		
				testcaseProgress.testcaseStarted( actualTestcase.getName() );
    		
				//A teszteset Page-einek futtatasa
				for( int index = 0; index < childCount; index++ ){
					
					TreeNode treeNode = actualTestcase.getChildAt(index);
					
					//Ha sima Page
					if( treeNode instanceof TestcaseStepDataModelAdapter ){
						
						TestcaseStepDataModelAdapter pageToRun = (TestcaseStepDataModelAdapter)treeNode;
						pageToRun.doAction(webDriver, this, pageProgress, elementProgres );

					}					
					
				}					
    		
				testcaseProgress.testcaseEnded( actualTestcase.getName() );
    		
				resultPanel.addNewStatus( actualTestcase, ResultStatus.SUCCESS );

			}catch( CompilationException compillationException ){
    		
				try {
					consolDocument.insertString( consolDocument.getLength(), "\n" + compillationException.getMessage() + "\n\n", attributeError );
				} catch (BadLocationException e) {e.printStackTrace();}
    		
				resultPanel.addNewStatus( actualTestcase, ResultStatus.FAILED );
    		
			}catch( PageException pageException ){
    		
				try {
					consolDocument.insertString(consolDocument.getLength(), "\n" + pageException.getMessage() + "\n\n", attributeError );
				} catch (BadLocationException e) {e.printStackTrace();}
    	
				resultPanel.addNewStatus( actualTestcase, ResultStatus.FAILED );
    		
			}catch( StoppedByUserException stoppedByUserException ){
    		
				try {
					consolDocument.insertString(consolDocument.getLength(), "\n" + stoppedByUserException.getMessage() + "\n\n", attributeError );
				} catch (BadLocationException e) {e.printStackTrace();}
    		
				resultPanel.addNewStatus( actualTestcase, ResultStatus.STOPPED );
    		
			//Nem kezbentartott hiba
			}catch( Exception exception ){
    		
				try {
					consolDocument.insertString(consolDocument.getLength(), "\n" + exception.getMessage() + "\n\n", attributeError );
				} catch (BadLocationException e) {e.printStackTrace();}
				
				resultPanel.addNewStatus( actualTestcase, ResultStatus.FAILED );
    		
			}
    	
elementProgres.outputCommand( "	}");				    	
elementProgres.outputCommand( "}");	

		}
	}
	

	
	class TestcaseProgress implements TestcaseProgressInterface{

		@Override
		public void testcaseStarted(String testcaseName) {
			
			try {
				consolDocument.insertString(consolDocument.getLength(), 
						MessageFormat.format(
								CommonOperations.getTranslation("editor.runtestcase.message.testcasestarted"), 
								testcaseName
						) + "\n", null 
				);
			} catch (BadLocationException e) {e.printStackTrace();}			
		}
				
		@Override
		public void testcaseEnded(String testcaseName) {
			
			try {
				consolDocument.insertString(consolDocument.getLength(), 
						MessageFormat.format(
								CommonOperations.getTranslation("editor.runtestcase.message.testcaseended"), 
								testcaseName
						) + "\n\n", null 
				);
			} catch (BadLocationException e) {e.printStackTrace();}			
		}		
	}
	
	class PageProgress implements PageProgressInterface{

		@Override
		public void pageStarted(String pageID, String nodeType) {			
			try {
				consolDocument.insertString(
						consolDocument.getLength(), "        " +  
						MessageFormat.format(
								CommonOperations.getTranslation("editor.runtestcase.message.pagestarted"), 
								pageID, nodeType
						) + "\n", 
						attributePageFinished 
				);		
			} catch (BadLocationException e) {e.printStackTrace();}			
		}

		@Override
		public void pageEnded(String pageID, String nodeType) {
			
			try {
				consolDocument.insertString(
						consolDocument.getLength(), "        " + 
						MessageFormat.format(
								CommonOperations.getTranslation("editor.runtestcase.message.pageended"), 
								pageID, nodeType
						) + "\n", 
						attributePageFinished 
				);								
			} catch (BadLocationException e) {e.printStackTrace();}
		
		}		
	}
	
	class ElementProgress implements ElementProgressInterface{
		
		@Override
		public void elementStarted(String name, String operation ) {
			try {
				//consolDocument.insertString(consolDocument.getLength(), "        " + 
				consolDocument.insertString(consolDocument.getLength(), "                " + 
						MessageFormat.format(
								CommonOperations.getTranslation("editor.runtestcase.message.elementstarted"), 
								operation, name
						) + " - ", null 
				);
			} catch (BadLocationException e) {e.printStackTrace();}			
		}
	
		
		
		@Override
		public void elementEnded(String name, String operation ) {
			
			try {
				consolDocument.insertString(consolDocument.getLength(), 
				CommonOperations.getTranslation("editor.runtestcase.message.elementended-short") 
				 + "\n", null 
				);
			} catch (BadLocationException e) {e.printStackTrace();}		
									
		}

		@Override
		public void outputValue(String outputValue, String message ) {
		
			if( null == message || message.trim().length() == 0 ){
				RunTestcaseEditor.this.outputPanel.append( outputValue + "\n" );
			}else{
				RunTestcaseEditor.this.outputPanel.append( message + ": " + outputValue + "\n" );
			}
			outputPanel.revalidate();
			outputPanel.repaint();
		}

		@Override
		public void outputCommand(String command) {
			
			System.out.println( command );
			
		}		
	}
		
	/**
	 * Result Panel
	 * 
	 * @author akoel
	 *
	 */
	public static class ResultPanel extends JTable{

		private static final long serialVersionUID = -7503019119455856208L;
		
		public ResultPanel(){
			super();
			
			PanelTableModel model = new PanelTableModel();
			CustomCellRenderer renderer = new CustomCellRenderer();

			//Ez kell ahhoz, hogy a hatter atszinezhetove valjon
			this.setOpaque(true);	
			//this.setFillsViewportHeight(true);
		
			this.setModel(model);
			this.setDefaultRenderer(Object.class, renderer);

TableColumn testcaseColumn = this.getColumnModel().getColumn(0);
testcaseColumn.setPreferredWidth(RESULT_PANEL_WIDTH - RESULT_PANEL_COLUMN_SUCCESS_WIDTH);
			
			TableColumn resultColumn = this.getColumnModel().getColumn(1);
			resultColumn.setPreferredWidth(RESULT_PANEL_COLUMN_SUCCESS_WIDTH);
			resultColumn.setMaxWidth(RESULT_PANEL_COLUMN_SUCCESS_WIDTH);
			
			//This is for always see the last row
			this.addComponentListener(new ComponentAdapter() {
			    public void componentResized(ComponentEvent e) {
			        ResultPanel.this.scrollRectToVisible(ResultPanel.this.getCellRect(ResultPanel.this.getRowCount()-1, 0, true));
			    }
			});
			
//Ez teszi lehetove hogy automatikusan megjelenjen a horizontalis scrollbar, ha kell
this.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );
			
/*			addNewStatus( new TestcaseCaseDataModel( "testeset1", ""), ResultStatus.FAILED);
			addNewStatus( new TestcaseCaseDataModel( "testeset2", ""), ResultStatus.SUCCESS);
			addNewStatus( new TestcaseCaseDataModel( "testeset3 adfsdf ds sdafd df dfasd sdaf 999999991 1 1", ""), ResultStatus.STOPPED);
			addNewStatus( new TestcaseCaseDataModel( "testeset4 ftrhty rtyrty rt rtu et uttrr", ""), ResultStatus.SUCCESS);
			addNewStatus( new TestcaseCaseDataModel( "testeset5", ""), ResultStatus.SUCCESS);
			addNewStatus( new TestcaseCaseDataModel( "testeset6", ""), ResultStatus.SUCCESS);
			addNewStatus( new TestcaseCaseDataModel( "testeset7", ""), ResultStatus.SUCCESS);
			addNewStatus( new TestcaseCaseDataModel( "testeset8", ""), ResultStatus.SUCCESS);
			addNewStatus( new TestcaseCaseDataModel( "testeset9", ""), ResultStatus.SUCCESS);
			addNewStatus( new TestcaseCaseDataModel( "testeset10", ""), ResultStatus.SUCCESS);
			addNewStatus( new TestcaseCaseDataModel( "testeset11", ""), ResultStatus.SUCCESS);
			addNewStatus( new TestcaseCaseDataModel( "testeset12", ""), ResultStatus.SUCCESS);
			addNewStatus( new TestcaseCaseDataModel( "testeset13", ""), ResultStatus.SUCCESS);
*/			
		}
		
		public void clear(){
			DefaultTableModel dm = (DefaultTableModel) getModel();
			int rowCount = dm.getRowCount();
			for (int i = rowCount - 1; i >= 0; i--) {
			    dm.removeRow(i);
			}			
			this.revalidate();
			this.repaint();
		}
		
		public void addNewStatus( TestcaseCaseDataModel testcase, ResultStatus resultStatus ){
			DefaultTableModel model = (DefaultTableModel) this.getModel();
	        model.addRow(new Object[]{testcase, resultStatus });
//	        this.revalidate();
//			this.repaint();
		}
		  
		class CustomCellRenderer extends DefaultTableCellRenderer {
			private static final long serialVersionUID = 2281876158763458436L;

			@Override
		    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		    	 Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		    	JLabel returnLabel = new JLabel();
		    	
		    	if( column == 0 ){
		    		returnLabel =  new JLabel( ((TestcaseCaseDataModel)value).getName() );
		    	}else if( column == 1 ){
		    		returnLabel = ((ResultStatus)value).getLabel();
		    	}
		    	returnLabel.setOpaque( true ); 					//Ez kell ahhoz, hogy szinezhetove valjon
		    	
		    	if( isSelected ){
		    		returnLabel.setBackground( c.getBackground() );	
		    	}else{
		    		Color color = ((JViewport)ResultPanel.this.getParent()).getBackground();		    	
		    		returnLabel.setBackground( new Color( color.getRGB()) );
		    	}
		        return returnLabel;
		    }

		}
		
		class PanelTableModel extends DefaultTableModel {

		    private static final long serialVersionUID = 1L;

		    @Override
		    public int getColumnCount() {
		        return 2;
		    }

		    @Override
		    public boolean isCellEditable( int row, int column ){
		    	return false;
		    }
		    
		    @Override
		    public String getColumnName( int column ){
		    	if( column == 0 ){
		    		return CommonOperations.getTranslation( "editor.label.runtest.resultwindow.testcase" );
		    	}else if( column == 1){
		    		return CommonOperations.getTranslation( "editor.label.runtest.resultwindow.result" );
		    	}else{
		    		return "";
		    	}
		    }
	    

		}
		
	}
	
	/**
	 * Result Status represented Class
	 * 
	 * @author akoel
	 *
	 */
	public static enum ResultStatus{
		SUCCESS( CommonOperations.getTranslation( "editor.label.runtest.resultstates.success" ), Color.green ),
		FAILED( CommonOperations.getTranslation( "editor.label.runtest.resultstates.failed" ), Color.red ),
		STOPPED( CommonOperations.getTranslation( "editor.label.runtest.resultstates.stopped" ), Color.blue );
		
		String name;
		Color color;
		
		private ResultStatus( String name, Color color ){
			this.name = name;
			this.color = color;
		}
		
		public String getName(){
			return name;
		}
		
		public Color getColor(){
			return color;
		}
		
		public JLabel getLabel(){
			JLabel label = new JLabel( getName() );
			label.setForeground( getColor() );
			Font font = new Font( label.getFont().getName(), Font.BOLD, label.getFont().getSize() );
			label.setFont( font );
			return label;
		}
	}

}
