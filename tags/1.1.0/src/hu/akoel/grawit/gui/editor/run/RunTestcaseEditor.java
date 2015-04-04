package hu.akoel.grawit.gui.editor.run;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.tree.TreeNode;

import org.openqa.selenium.WebDriver;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.Player;
import hu.akoel.grawit.core.treenodedatamodel.DriverDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.TestcaseDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseCaseDataModel;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseFolderDataModel;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseParamDataModelAdapter;
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
		scrollPaneForResultPanel.setPreferredSize( new Dimension( 300, 1 ) );
		
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
		// Value panel
		//
		// -----------
		outputPanel = new JTextArea(2, 15);
		outputPanel.setEditable(false);
		
		JScrollPane scrollPaneForOutputPanel = new JScrollPane(outputPanel);
		scrollPaneForOutputPanel.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPaneForOutputPanel.setHorizontalScrollBarPolicy( JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
		scrollPaneForOutputPanel.setSize(1, 200);

		// ------------------------
		//	STATUS PANEL felirat
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
		upperPanel.add( new JLabel("Result"), c );

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
		upperPanel.add( new JLabel("Output"), c );


		// ------------------------
		// STATUS PANEL elhelyezese
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
					if( treeNode instanceof TestcaseParamDataModelAdapter ){
						
						TestcaseParamDataModelAdapter pageToRun = (TestcaseParamDataModelAdapter)treeNode;
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
	public class ResultPanel extends JPanel{

		private static final long serialVersionUID = -7503019119455856208L;
		
		private JLabel testcaseLabel;
		private JLabel filler;
		private GridBagConstraints c;
		private int actualLine = 0;
		private int positionStatus = 1;
		private int positionTestcase = 0;
		
		private JLabel resultLabel;
		private JLabel resultLabelSuccess;
		private JLabel resultLabelFailed;
		private JLabel resultLabelStopped;
		private Font resultFont;		
		
		private int widthOfLongestResult;
		
		public ResultPanel(){
			
			c = new GridBagConstraints();
			
			this.setBackground( Color.white );
			this.setLayout( new GridBagLayout() );
			this.filler = new JLabel("");
			
			c.insets = new Insets( -2, -2, -2, -2 );
			c.ipadx = 0;
			c.ipady = 0;
			
			resultLabelSuccess = ResultStatus.SUCCESS.getLabel();
			resultLabelFailed = ResultStatus.FAILED.getLabel();
			resultLabelStopped = ResultStatus.STOPPED.getLabel();
			widthOfLongestResult = Math.max( resultLabelSuccess.getPreferredSize().width, resultLabelFailed.getPreferredSize().width );
			widthOfLongestResult = Math.max( widthOfLongestResult, resultLabelStopped.getPreferredSize().width );
			
//			this.setPreferredSize( new Dimension( 300, 1 ) );
		}
		
		public void clear(){
			this.removeAll();
			this.revalidate();
			this.repaint();
		}
		
		public void addNewStatus( TestcaseCaseDataModel testcase, ResultStatus statusValue ){

			int widthOfPanel = this.getWidth();
			
			//Ha volt filler, akkor ezt eltavolitja
			this.remove( filler );

			//Testcase maximalis hosszanak megallapitasa
			int maxWidthOfTestcase = widthOfPanel - widthOfLongestResult;
			
			c.gridy = actualLine;
			c.weighty = 0;
			
			//Testcase
			c.gridx = positionTestcase;
			c.gridwidth = 1;
			c.fill = GridBagConstraints.NONE;
			c.weightx = 0;
			c.anchor = GridBagConstraints.WEST;
			testcaseLabel = new JLabel( testcase.getName() );
			testcaseLabel.setMaximumSize(new Dimension(maxWidthOfTestcase, 20));
			testcaseLabel.setPreferredSize(new Dimension(maxWidthOfTestcase, 20));
			testcaseLabel.setMinimumSize(new Dimension(maxWidthOfTestcase, 20));
			this.add( testcaseLabel, c );
			
			//Result
			c.gridx = positionStatus;
			c.gridwidth = 1;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 1;
			c.anchor = GridBagConstraints.WEST;	

			if( statusValue.equals( ResultStatus.FAILED ) ){
				resultLabel = ResultStatus.FAILED.getLabel();				
			}else if( statusValue.equals( ResultStatus.SUCCESS ) ){
				resultLabel = ResultStatus.SUCCESS.getLabel();
			}else if( statusValue.equals( ResultStatus.STOPPED ) ){
				resultLabel = ResultStatus.STOPPED.getLabel();
			}else{
				resultLabel = new JLabel("");
			}			
			
			this.add( resultLabel, c );
			
			//Filler
			c.gridy = c.gridy + 1;
			c.gridx = 0;
			c.gridwidth = 1;
			c.weighty = 1;
			c.fill = GridBagConstraints.VERTICAL;
			c.weightx = 1;
			c.anchor = GridBagConstraints.WEST;
			this.add( filler, c );
				
			actualLine++;
			
			this.revalidate();
			this.repaint();
			
		}
	}
	
	/**
	 * Result Status represented Class
	 * 
	 * @author akoel
	 *
	 */
	public static enum ResultStatus{
		SUCCESS( "Succes", Color.green ),
		FAILED( "Failed", Color.red ),
		STOPPED( "Stopped", Color.blue );
		
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
