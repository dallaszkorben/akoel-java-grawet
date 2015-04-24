package hu.akoel.grawit.gui.editor.run;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.concurrent.TimeUnit;

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
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import org.openqa.selenium.WebDriver;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.Player;
import hu.akoel.grawit.WorkingDirectory;
import hu.akoel.grawit.core.operations.ElementOperationAdapter;
import hu.akoel.grawit.core.treenodedatamodel.DataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseElementDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.NormalBaseElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.driver.DriverDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.step.StepCollectorDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.step.StepElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseCaseDataModel;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseFolderDataModel;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseStepCollectorDataModel;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseStepDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.trace.TraceDataModel;
import hu.akoel.grawit.enums.SelectorType;
import hu.akoel.grawit.enums.list.ElementTypeListEnum;
import hu.akoel.grawit.exceptions.CompilationException;
import hu.akoel.grawit.exceptions.PageException;
import hu.akoel.grawit.exceptions.StoppedByUserException;
import hu.akoel.grawit.gui.GUIFrame;
import hu.akoel.grawit.gui.editor.BaseEditor;
import hu.akoel.grawit.gui.editor.DataEditor;
import hu.akoel.grawit.gui.interfaces.progress.ElementProgressInterface;
import hu.akoel.grawit.gui.interfaces.progress.TestcaseStepProgressInterface;
import hu.akoel.grawit.gui.interfaces.progress.TestcaseProgressInterface;
import hu.akoel.grawit.gui.tree.RunTree;
import hu.akoel.grawit.gui.tree.TraceTree;
import hu.akoel.grawit.gui.tree.Tree;

public class RunTestcaseEditor extends BaseEditor implements Player{
	
	private static final long serialVersionUID = -7285419881714492620L;
	
	public static final int OUTPUT_PANEL_WIDTH = 300;
	public static final int RESULT_PANEL_WIDTH = 400;
	public static final int RESULT_PANEL_COLUMN_SUCCESS_WIDTH = 80;

	private TestcaseDataModelAdapter selectedTestcase;
	
	private TestcaseStepProgress pageProgress;
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
	
	private TraceTree traceTree;
	
	private void setNeedToStop( boolean needToStop ){
		this.needToStop = needToStop;
	}
	
	@Override
	public boolean isStopped() {
		return needToStop;
	}

	public RunTestcaseEditor( Tree tree, TestcaseDataModelAdapter testcaseDataModel, DriverDataModelAdapter driverDataModel ){	

		super(
				( testcaseDataModel instanceof TestcaseRootDataModel ) ? CommonOperations.getTranslation( "editor.label.runtest.testrootwindowtitle" ) :
				( testcaseDataModel instanceof TestcaseCaseDataModel ) ? CommonOperations.getTranslation( "editor.label.runtest.testcasewindowtitle" ) : 
				( testcaseDataModel instanceof TestcaseFolderDataModel ) ? CommonOperations.getTranslation( "editor.label.runtest.testnodewindowtitle" ) : 
				
				"");
		
		this.setScrollEnabled(false);
		this.selectedTestcase = testcaseDataModel;

		pageProgress = new TestcaseStepProgress();
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
		
		//------------
		//
		// Lower panel
		//
		//------------
		JPanel lowerPanel = new JPanel();
		lowerPanel.setLayout( new BorderLayout() );
		lowerPanel.add( consolScrollablePanel, BorderLayout.CENTER );
		lowerPanel.setAutoscrolls(true);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		  
		
		
		
		TraceDataModel newModel = buildDataModel( ((DataModelAdapter)testcaseDataModel), null );
		
		traceTree = new TraceTree( newModel );
		lowerPanel.add( traceTree, BorderLayout.WEST );
	
		
		
		
		
		
		
		
		
		

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

	
	
	
	
	
	
	
	
	
	
	/**
	 * Rekurzivan vegig megy a Teszteseteken a fa strukturat kovetve es letrehoz egy ujabb strukturat
	 * Amiben a Tesztesettol kezdve az elemekig minden szerepel.
	 * 
	 * @param actualOrigElement
	 * @param newStructure
	 * @return
	 */
	public static TraceDataModel buildDataModel(DataModelAdapter actualOrigElement, TraceDataModel newStructure) {

		TraceDataModel traceDataModelElement;
		
		//Ha TestcaseStepCollectorDataModel, akkor azt lecsereljuk StepCollectorDataModelAdapter-re
		if( actualOrigElement instanceof TestcaseStepCollectorDataModel ){			
			DataModelAdapter stepCollectorDataModel = ((TestcaseStepCollectorDataModel) actualOrigElement).getStepCollector();
			traceDataModelElement = new TraceDataModel( actualOrigElement );
			actualOrigElement = stepCollectorDataModel;
		}else{
			traceDataModelElement = new TraceDataModel( actualOrigElement );
		}

		if (null == newStructure) {
			
			//Letrehozzuk a root elemet
			newStructure = traceDataModelElement;
		} else {
			
			//Vegul aztan hozzaadjuk a szulojehez
			newStructure.add(traceDataModelElement);
		}			
			
		Enumeration children = actualOrigElement.children();

		if (children != null) {
			while (children.hasMoreElements()) {

				DataModelAdapter nextElement = (DataModelAdapter) children.nextElement();
				buildDataModel( nextElement, traceDataModelElement);
			}
		}		
		return newStructure;
	}

	

	
	
	
	
	
	
	
	
	
	
	
	/**
	 * Rekurziven vegig megy az osszes test case-en a parameterkent megadott
	 * pontbol kiindulva
	 *  
	 * @param testcase
	 */
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
	
	/**
	 * Adott test case vegrehajtasa
	 * 
	 * @param actualTestcase
	 */
	private void executeTestcase( TestcaseCaseDataModel actualTestcase ){

		//Ha be van kapcsolat		
		if( actualTestcase.isOn() ){
			
			elementProgres.printSource( "import org.openqa.selenium.By;" );
			elementProgres.printSource( "import org.openqa.selenium.WebDriver;" );
			elementProgres.printSource( "import org.openqa.selenium.WebElement;" );
			elementProgres.printSource( "import org.openqa.selenium.firefox.FirefoxDriver;" );
			elementProgres.printSource( "import org.openqa.selenium.firefox.FirefoxProfile;" );

			elementProgres.printSource( "import org.openqa.selenium.WebDriverException;" );	
			elementProgres.printSource( "import org.openqa.selenium.JavascriptExecutor;");
			elementProgres.printSource( "import org.openqa.selenium.Keys;" );
			
			elementProgres.printSource( "import org.openqa.selenium.support.ui.Select;" );
			elementProgres.printSource( "import org.openqa.selenium.support.ui.WebDriverWait;" );
			elementProgres.printSource( "import org.openqa.selenium.support.ui.UnexpectedTagNameException;" );
			elementProgres.printSource( "import org.openqa.selenium.support.ui.ExpectedConditions;" );
			
			elementProgres.printSource( "" );
			
			elementProgres.printSource( "import java.util.concurrent.TimeUnit;" );
			elementProgres.printSource( "import java.util.NoSuchElementException;" );
			elementProgres.printSource( "import java.util.ArrayList;" );
			elementProgres.printSource( "import java.util.List;" );			
			elementProgres.printSource( "import java.util.Iterator;");
			elementProgres.printSource( "import java.util.regex.Matcher;");
			elementProgres.printSource( "import java.util.regex.Pattern;");
			
			elementProgres.printSource( "" );				  
			
			elementProgres.printSource( "abstract class ScriptClass{");

			elementProgres.printSource( CommonOperations.TAB_BY_SPACE + "ArrayList<String> parameters = new ArrayList<>();" );	
			elementProgres.printSource( CommonOperations.TAB_BY_SPACE + "abstract public void runScript() throws Exception;" );	
			elementProgres.printSource( CommonOperations.TAB_BY_SPACE + "public void addParameter( String parameter ){" );	
			elementProgres.printSource( CommonOperations.TAB_BY_SPACE + CommonOperations.TAB_BY_SPACE + "this.parameters.add( parameter );" );
			elementProgres.printSource( CommonOperations.TAB_BY_SPACE + "}" );	
			elementProgres.printSource( CommonOperations.TAB_BY_SPACE + "public void clearParameters(){" );	
			elementProgres.printSource( CommonOperations.TAB_BY_SPACE + CommonOperations.TAB_BY_SPACE + "this.parameters.clear();" );
			elementProgres.printSource( CommonOperations.TAB_BY_SPACE + "}" );	
			elementProgres.printSource( CommonOperations.TAB_BY_SPACE + "public Iterator<String> getParameterIterator(){" );	
			elementProgres.printSource( CommonOperations.TAB_BY_SPACE + CommonOperations.TAB_BY_SPACE + "return parameters.iterator();" );
			elementProgres.printSource( CommonOperations.TAB_BY_SPACE + "}" );	
			elementProgres.printSource( "}" );	
			
			elementProgres.printSource( "" );	
			
			elementProgres.printSource( "public class Test{ ");
			elementProgres.printSource( "" );
			elementProgres.printSource( CommonOperations.TAB_BY_SPACE + "WebDriverWait wait = null;");
			elementProgres.printSource( CommonOperations.TAB_BY_SPACE + "By by = null;" );
			elementProgres.printSource( CommonOperations.TAB_BY_SPACE + "WebElement webElement = null;");
			elementProgres.printSource( CommonOperations.TAB_BY_SPACE + "Select select = null;");	
			elementProgres.printSource( CommonOperations.TAB_BY_SPACE + "Integer index = 0;" );
			elementProgres.printSource( CommonOperations.TAB_BY_SPACE + "WebDriver driver = null;" );
			elementProgres.printSource( CommonOperations.TAB_BY_SPACE + "FirefoxProfile profile = null;");
			elementProgres.printSource( CommonOperations.TAB_BY_SPACE + "JavascriptExecutor executor = null;");
			
			elementProgres.printSource( CommonOperations.TAB_BY_SPACE + "List<WebElement> optionList;" );
			elementProgres.printSource( CommonOperations.TAB_BY_SPACE + "boolean found = false;" );
			elementProgres.printSource( CommonOperations.TAB_BY_SPACE + "String origText;" );
			elementProgres.printSource( CommonOperations.TAB_BY_SPACE + "String optionText;" );
			elementProgres.printSource( CommonOperations.TAB_BY_SPACE + "Matcher matcher;" );
			elementProgres.printSource( CommonOperations.TAB_BY_SPACE + "Pattern pattern;" );		

			elementProgres.printSource( "" );
			elementProgres.printSource( CommonOperations.TAB_BY_SPACE + "public static void main( String[] args ){" );
			elementProgres.printSource( CommonOperations.TAB_BY_SPACE + CommonOperations.TAB_BY_SPACE + "new Test();" );
			elementProgres.printSource( CommonOperations.TAB_BY_SPACE + "}" );
			elementProgres.printSource( "" );
			
			elementProgres.printSource( CommonOperations.TAB_BY_SPACE + "public Test(){" );	
			elementProgres.printSource( "" );

			WebDriver webDriver = ((TestcaseRootDataModel)actualTestcase.getRoot()).getDriverDataModel().getDriver( elementProgres, CommonOperations.TAB_BY_SPACE + CommonOperations.TAB_BY_SPACE );

			//IMPLICIT WAIT
			elementProgres.printSource( CommonOperations.TAB_BY_SPACE + CommonOperations.TAB_BY_SPACE + "//IMPLICIT WAIT" );			
			elementProgres.printSource( CommonOperations.TAB_BY_SPACE + CommonOperations.TAB_BY_SPACE + "driver.manage().timeouts().implicitlyWait(" + WorkingDirectory.getInstance().getWaitingTime() + ", TimeUnit.SECONDS);" );
			elementProgres.printSource( "" );
			
			webDriver.manage().timeouts().implicitlyWait(WorkingDirectory.getInstance().getWaitingTime(), TimeUnit.SECONDS );

			try{				

				int childCount = actualTestcase.getChildCount();
    		
				testcaseProgress.testcaseStarted( actualTestcase );
    		
				//A teszteset Page-einek futtatasa
				for( int index = 0; index < childCount; index++ ){
					
					TreeNode treeNode = actualTestcase.getChildAt(index);
					
					//Ha sima Page
					if( treeNode instanceof TestcaseStepDataModelAdapter ){
						
						TestcaseStepDataModelAdapter pageToRun = (TestcaseStepDataModelAdapter)treeNode;
						pageToRun.doAction(webDriver, this, pageProgress, elementProgres, CommonOperations.TAB_BY_SPACE + CommonOperations.TAB_BY_SPACE );
					}					
				}					
    		
				testcaseProgress.testcaseEnded( actualTestcase );    		
				resultPanel.addNewStatus( actualTestcase, ResultStatus.SUCCESS );

			}catch( CompilationException compillationException ){
    		
				elementProgres.printOutput( compillationException.getMessage() + "\n\n", null );				   		
				resultPanel.addNewStatus( actualTestcase, ResultStatus.FAILED );
    		
			}catch( PageException pageException ){
    		
				elementProgres.printOutput( pageException.getMessage() + "\n\n", null );	
				resultPanel.addNewStatus( actualTestcase, ResultStatus.FAILED );
    		
			}catch( StoppedByUserException stoppedByUserException ){
    		
				elementProgres.printOutput( stoppedByUserException + "\n\n", null );	
				resultPanel.addNewStatus( actualTestcase, ResultStatus.STOPPED );
    		
			//Nem kezbentartott hiba
			}catch( Exception exception ){
    		
				elementProgres.printOutput( exception.getMessage() + "\n\n", null );					
				resultPanel.addNewStatus( actualTestcase, ResultStatus.FAILED );    		
			}
    	
			elementProgres.printSource( CommonOperations.TAB_BY_SPACE + "}");				    	
			elementProgres.printSource( "}");	
		}
	}
	
	class TestcaseProgress implements TestcaseProgressInterface{

		@Override
		public void testcaseStarted(TestcaseCaseDataModel testcase) {
			traceTree.select( testcase );			
		}

		@Override
		public void testcaseEnded(TestcaseCaseDataModel testcase) {		
			traceTree.collapse( testcase );
		}		
	}
	
	class TestcaseStepProgress implements TestcaseStepProgressInterface{

		@Override
		public void stepStarted(TestcaseStepCollectorDataModel testcaseStepCollector) {
			traceTree.select( testcaseStepCollector );			
		}

		@Override
		public void stepEnded(TestcaseStepCollectorDataModel testcaseStepCollector) {
			traceTree.collapse( testcaseStepCollector );
			//traceTree.collapsePath( CommonOperations.getTreePathFromNode( stepCollector.getParent() ) );
		}		
	}
	
	class ElementProgress implements ElementProgressInterface{		
	
		@Override
		public void elementStarted(StepElementDataModel stepElement) {
			traceTree.select( stepElement );			
		}

		@Override
		public void elementEnded(StepElementDataModel stepElement) {
			
		}
		
		@Override
		public void printOutput( String outputValue, String message ) {
		
			if( null == message || message.trim().length() == 0 ){
				RunTestcaseEditor.this.outputPanel.append( outputValue + "\n" );
			}else{
				RunTestcaseEditor.this.outputPanel.append( message + " " + outputValue + "\n" );
			}
			outputPanel.revalidate();
			outputPanel.repaint();
		}

		@Override
		public void printSource(String command) {
			
			System.out.println( command );			
		}
	}
		
	/**
	 * 
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
		    		returnLabel.setHorizontalAlignment(SwingConstants.CENTER );		    		
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
	 * 
	 * Result Status represented Class
	 * 
	 * @author akoel
	 *
	 */
	public static enum ResultStatus{
		SUCCESS( CommonOperations.createImageIcon("result/result-checked.png") ),
		FAILED( CommonOperations.createImageIcon("result/result-failed.png") ),
		STOPPED( CommonOperations.createImageIcon("result/result-stopped.png") );
		
		String name;
		ImageIcon icon;	
		
		private ResultStatus( ImageIcon icon ){
			this.icon = icon;
		}
		
		public JLabel getLabel(){
			JLabel label = new JLabel();
			label.setIcon( this.icon );
			return label;
		}		
	}
}
