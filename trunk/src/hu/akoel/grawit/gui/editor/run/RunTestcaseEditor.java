package hu.akoel.grawit.gui.editor.run;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import hu.akoel.grawit.core.treenodedatamodel.DriverDataModelInterface;
import hu.akoel.grawit.core.treenodedatamodel.TestcaseDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseCaseDataModel;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseNodeDataModel;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcasePageModelAdapter;
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
	private JTextPane statusPanel;	
	private JTextArea valuePanel;
	private DefaultStyledDocument consolDocument;
	private DefaultStyledDocument statusDocument;
	
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

	public RunTestcaseEditor( Tree tree, TestcaseDataModelAdapter testcaseDataModel, DriverDataModelInterface driverDataModel ){	

		super(
				( testcaseDataModel instanceof TestcaseRootDataModel) ? 
						CommonOperations.getTranslation( "editor.label.runtest.testrootwindowtitle" ) :
				( testcaseDataModel instanceof TestcaseCaseDataModel ) ? 
						CommonOperations.getTranslation( "editor.label.runtest.testcasewindowtitle" ) : 
				( testcaseDataModel instanceof TestcaseNodeDataModel) ? 
						CommonOperations.getTranslation( "editor.label.runtest.testnodewindowtitle" ) : 
				
				"");
		
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
						valuePanel.setText("");
						statusPanel.setText("");
						consolPanel.setText("");
						
						//Vegrehajtja a teszteset(ek)et
						throughTestcases( RunTestcaseEditor.this.selectedTestcase );
				    	
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
		JScrollPane lowerPanel = new JScrollPane(consolPanel);
		//scrollPaneForConsolPanel.setPreferredSize(new Dimension(10,100));
		lowerPanel.setAutoscrolls(true);

		//Status document
		StyleContext statusStyleContext = new StyleContext();
		statusDocument = new DefaultStyledDocument(statusStyleContext);
		statusPanel = new JTextPane(statusDocument);
		statusPanel.setEditable( false );
		statusPanel.setPreferredSize(new Dimension(170,1));
		DefaultCaret statusCaret = (DefaultCaret)statusPanel.getCaret();
		statusCaret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		JScrollPane scrollPaneForStatusPanel = new JScrollPane(statusPanel);
		scrollPaneForStatusPanel.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPaneForStatusPanel.setHorizontalScrollBarPolicy( JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	    //scrollPaneForStatusPanel.setPreferredSize(new Dimension(70,100));
		//scrollPaneForStatusPanel.setAutoscrolls(true);
		

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
		valuePanel = new JTextArea(2, 15);
		valuePanel.setEditable(false);
		
		DefaultCaret valueCaret = (DefaultCaret)statusPanel.getCaret();
		valueCaret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		JScrollPane scrollPaneForValuePanel = new JScrollPane(valuePanel);
		scrollPaneForValuePanel.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPaneForValuePanel.setHorizontalScrollBarPolicy( JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
		scrollPaneForValuePanel.setSize(1, 200);

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
		upperPanel.add( new JLabel("Status"), c );

		// ------------------------
		// VALUE PANEL felirat
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
		upperPanel.add( new JLabel("Value"), c );


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
		upperPanel.add( scrollPaneForStatusPanel, c );
		
		// ------------------------
		// VALUE PANEL elhelyezese
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
		upperPanel.add( scrollPaneForValuePanel, c );


		
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
		
		
		MyHorizontalSplitPane horizontalSplitPane = new MyHorizontalSplitPane(JSplitPane.VERTICAL_SPLIT, upperPanel, lowerPanel);
		horizontalSplitPane.setOneTouchExpandable(false);
		horizontalSplitPane.setFlippedDividerLocation( 200 );	        
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
		}else if( testcase instanceof TestcaseNodeDataModel){
				
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

//    	TestcaseCaseDataModel selectedTestcase = RunTestcaseEditor.this.selectedTestcase;
//    	WebDriver webDriver = actualTestcase.getDriverDataModel().getDriver( elementProgres );

			WebDriver webDriver = ((TestcaseRootDataModel)actualTestcase.getRoot()).getDriverDataModel().getDriver(elementProgres);

			try{				

				int childCount = actualTestcase.getChildCount();
    		
				testcaseProgress.testcaseStarted( actualTestcase.getName() );
    		
				//A teszteset Page-einek futtatasa
				for( int index = 0; index < childCount; index++ ){
					
					TreeNode treeNode = actualTestcase.getChildAt(index);
					
					//Ha sima Page
					if( treeNode instanceof TestcasePageModelAdapter ){
						
						TestcasePageModelAdapter pageToRun = (TestcasePageModelAdapter)treeNode;
						pageToRun.doAction(webDriver, this, pageProgress, elementProgres );

					}					
					
				}					
    		
				testcaseProgress.testcaseEnded( actualTestcase.getName() );
    		
				setStatusOfTestCase( actualTestcase, true );

			}catch( CompilationException compillationException ){
    		
				try {
					consolDocument.insertString(consolDocument.getLength(), compillationException.getMessage() + "\n\n", attributeError );
				} catch (BadLocationException e) {e.printStackTrace();}
    		
				setStatusOfTestCase( actualTestcase, false );
    		
			}catch( PageException pageException ){
    		
				try {
					consolDocument.insertString(consolDocument.getLength(), pageException.getMessage() + "\n\n", attributeError );
				} catch (BadLocationException e) {e.printStackTrace();}
    	
				setStatusOfTestCase( actualTestcase, false );
    		
			}catch( StoppedByUserException stoppedByUserException ){
    		
				try {
					consolDocument.insertString(consolDocument.getLength(), stoppedByUserException.getMessage() + "\n\n", attributeError );
				} catch (BadLocationException e) {e.printStackTrace();}
    		
				setStatusOfTestCase( actualTestcase, false );
    		
			//Nem kezbentartott hiba
			}catch( Exception exception ){
    		
				try {
					consolDocument.insertString(consolDocument.getLength(), exception.getMessage() + "\n\n", attributeError );
				} catch (BadLocationException e) {e.printStackTrace();}
				
				setStatusOfTestCase( actualTestcase, false );
    		
			}
    	
elementProgres.outputCommand( "	}");				    	
elementProgres.outputCommand( "}");	

		}
	}
	
	private void setStatusOfTestCase( TestcaseCaseDataModel selectedTestcase, boolean ok ){
	
		try {
			statusDocument.insertString(statusDocument.getLength(), selectedTestcase.getName(), null );
			
			if( ok ){
				statusDocument.insertString(statusDocument.getLength(), " OK\n", attributeOK );
			}else{
				statusDocument.insertString(statusDocument.getLength(), " Failed\n", attributeFailed );
			}
			
		} catch (BadLocationException e) {e.printStackTrace();}

	}
	
	public class MyHorizontalSplitPane extends JSplitPane {

		private static final long serialVersionUID = 5556490319250974754L;
	    
		private boolean hasAbsoluteLocation = false;
		private int absoluteLocation = 200;
	    private boolean isPainted = false;

	    public MyHorizontalSplitPane( int orientation, Component upperComponent, Component lowerComponent ){
	    	super( orientation, upperComponent, lowerComponent );
	    }
	    	    
	    public void setFlippedDividerLocation(int absoluteLocation) {
	        if (!isPainted) {
	            hasAbsoluteLocation = true;
	            this.absoluteLocation = absoluteLocation;
	        } else {
	            super.setDividerLocation(absoluteLocation);
	        }
	        setResizeWeight( 1.0 );
	    }
	    
	    public void paint(Graphics g) {
	        super.paint(g);
	        if (!isPainted) {
	            if (hasAbsoluteLocation) {
	                super.setDividerLocation( this.getHeight() - absoluteLocation);
	            }
	            isPainted = true;
	        }
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
						consolDocument.getLength(), "    " +  
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
						consolDocument.getLength(), "    " + 
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
		public void elementStarted(String name ) {
			try {
				consolDocument.insertString(consolDocument.getLength(), "        " + 
						MessageFormat.format(
								CommonOperations.getTranslation("editor.runtestcase.message.elementstarted"), 
								name
						) + "\n", null 
				);
			} catch (BadLocationException e) {e.printStackTrace();}			
		}
	
		@Override
		public void elementEnded(String name) {
			
			try {
				consolDocument.insertString(consolDocument.getLength(), "        " + 
						MessageFormat.format(
								CommonOperations.getTranslation("editor.runtestcase.message.elementended"), 
								name
						) + "\n", null 
				);
			} catch (BadLocationException e) {e.printStackTrace();}			
		}

		@Override
		public void outputValue(String outputValue, String message ) {
		
			if( null == message || message.trim().length() == 0 ){
				RunTestcaseEditor.this.valuePanel.append( outputValue + "\n" );
			}else{
				RunTestcaseEditor.this.valuePanel.append( message + ": " + outputValue + "\n" );
			}
			valuePanel.revalidate();
			valuePanel.repaint();
		}

		@Override
		public void outputCommand(String command) {
			
			System.out.println( command );
			
		}		
	}

}
