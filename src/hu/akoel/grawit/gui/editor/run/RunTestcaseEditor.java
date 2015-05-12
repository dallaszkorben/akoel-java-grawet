package hu.akoel.grawit.gui.editor.run;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.JViewport;
import javax.swing.SwingConstants;
import javax.swing.event.MouseInputAdapter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Element;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.tree.TreeNode;

import org.openqa.selenium.WebDriver;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.Player;
import hu.akoel.grawit.WorkingDirectory;
import hu.akoel.grawit.core.treenodedatamodel.DataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.driver.DriverDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.step.StepElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseCaseDataModel;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseFolderDataModel;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseStepDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseRootDataModel;
import hu.akoel.grawit.exceptions.CompilationException;
import hu.akoel.grawit.exceptions.StepException;
import hu.akoel.grawit.exceptions.StoppedByUserException;
import hu.akoel.grawit.gui.editor.BaseEditor;
import hu.akoel.grawit.gui.interfaces.progress.ProgressIndicatorInterface;
import hu.akoel.grawit.gui.output.message.AttributedOutputMessage;
import hu.akoel.grawit.gui.output.message.LinkOutputMessage;
import hu.akoel.grawit.gui.output.message.OutputMessageAdapter;
import hu.akoel.grawit.gui.tree.LinkToNodeInTreeListener;
import hu.akoel.grawit.gui.tree.Tree;

public class RunTestcaseEditor extends BaseEditor implements Player{
	
	private static final long serialVersionUID = -7285419881714492620L;
	
	private static final String CLASS_NAME = "MyTest";

	private TestcaseDataModelAdapter selectedTestcase;

	private ProgressIndicator progressIndicator;
	
	private ArrayList<LinkToNodeInTreeListener> linkToNodeInTreeListeners = new ArrayList<LinkToNodeInTreeListener>();
	
	private JButton startButton;
	private JButton stopButton;
	private JButton pauseButton;
	private JButton continueButton;

	private ResultPanel resultPanel;
	private JTextPane sourceCodePanel;
	private DefaultStyledDocument sourceCodeDocument;
	private JTextPane outputPanel;
	private DefaultStyledDocument outputDocument;	
	
	private SimpleAttributeSet attributeSourceCodeNormal;
	
	private boolean needToStop = false;	
	private boolean needToPause = false;
	
	private void setNeedToStop( boolean needToStop ){
		this.needToStop = needToStop;
	}
	
	private void setNeedToPause( boolean needToPause ){
		this.needToPause = needToPause;
	}

	@Override
	public boolean isStopped() {
		return needToStop;
	}

	@Override
	public boolean isPaused() {
		return needToPause;
	}

	public RunTestcaseEditor( Tree tree, TestcaseDataModelAdapter testcaseDataModel, DriverDataModelAdapter driverDataModel ){	

		super(
				( testcaseDataModel instanceof TestcaseRootDataModel ) ? CommonOperations.getTranslation( "editor.label.runtest.testrootwindowtitle" ) :
				( testcaseDataModel instanceof TestcaseCaseDataModel ) ? CommonOperations.getTranslation( "editor.label.runtest.testcasewindowtitle" ) : 
				( testcaseDataModel instanceof TestcaseFolderDataModel ) ? CommonOperations.getTranslation( "editor.label.runtest.testnodewindowtitle" ) : 
				
				"");
		
		this.setScrollEnabled(false);
		
		this.selectedTestcase = testcaseDataModel;

		progressIndicator = new ProgressIndicator();		
		
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
		
		ImageIcon continueIcon = CommonOperations.createImageIcon("control/control-continue.png");
		ImageIcon continueDisabledIcon = CommonOperations.createImageIcon("control/control-continue-disabled.png");
		ImageIcon continueRolloverIcon = CommonOperations.createImageIcon("control/control-continue.png");
		ImageIcon continuePressedIcon = CommonOperations.createImageIcon("control/control-continue.png");
		ImageIcon continueSelectedIcon = CommonOperations.createImageIcon("control/control-continue.png");
		
		
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
						setNeedToPause( false );
						
						//Engedelyezi a Stop gombot
						RunTestcaseEditor.this.stopButton.setEnabled( true );

						//Engedelyezi a Pause gombot
						RunTestcaseEditor.this.pauseButton.setEnabled( true );
						
						//Tiltja a Continue gombot
						RunTestcaseEditor.this.continueButton.setEnabled( false );
						
						//Torli a panelek tartalmat
						outputPanel.setText("");
						resultPanel.clear();
						sourceCodePanel.setText("");
						
//for(int i=0; i<20; i++){						
						//Vegrehajtja a teszteset(ek)et
						runTestcases( RunTestcaseEditor.this.selectedTestcase );
						//throughTestcases( RunTestcaseEditor.this.selectedTestcase );
//}				    	
						//Engedelyezi az Inditas gombot
				    	RunTestcaseEditor.this.startButton.setEnabled( true );
				    	
				    	//Tiltja a Stop gombot
				    	RunTestcaseEditor.this.stopButton.setEnabled( false );
				    	
				    	//Tiltja a Pause gombot
				    	RunTestcaseEditor.this.pauseButton.setEnabled( false );		
				    	
						//Tiltja a Continue gombot
						RunTestcaseEditor.this.continueButton.setEnabled( false );
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
				setNeedToPause( false );

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
		
		pauseButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
								
				//Letiltja a Pause gombot
				RunTestcaseEditor.this.pauseButton.setEnabled( false );
				
				//Engedelyezi a Continue gombot
				RunTestcaseEditor.this.continueButton.setEnabled( true );
				
				setNeedToPause( true );

			}						
		});	
		
		//
		// CONTINUE
		//
		continueButton = new JButton();
		
		continueButton.setBorderPainted(false);
		continueButton.setBorder(null);
		continueButton.setFocusable(false);
		continueButton.setMargin(new Insets(0, 0, 0, 0));
		continueButton.setContentAreaFilled(false);

		continueButton.setSelectedIcon(continueSelectedIcon);
		continueButton.setRolloverIcon((continueRolloverIcon));
		continueButton.setPressedIcon(continuePressedIcon);		
		continueButton.setDisabledIcon(continueDisabledIcon);
		continueButton.setIcon(continueIcon);
		
		continueButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
								
				//Engedelyezem a Pause gombot
				RunTestcaseEditor.this.pauseButton.setEnabled( true );
				
				//Tiltom a Continue gombot
				RunTestcaseEditor.this.continueButton.setEnabled( false );
				
				setNeedToPause( false );	
			}						
		});	

		//Indulasi beallitas
		stopButton.setEnabled( false );
		pauseButton.setEnabled( false );
		continueButton.setEnabled( false );
		
		//Ha nincs Driver definialva, akkor nem indulhat el egy teszt sem
		if( null == ((TestcaseRootDataModel)selectedTestcase.getRoot()).getDriverDataModel() ){
			startButton.setEnabled( false );
		}else{
			startButton.setEnabled( true );
		}
		
		//---------------
		// Control panel
		//---------------
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout( new FlowLayout() );
		controlPanel.add(startButton);
		controlPanel.add(stopButton);
		controlPanel.add( pauseButton );
		controlPanel.add( continueButton );
		
		//---------------
		//
		// Source code panel
		//
		//---------------		
		int OUTPUTPANEL_WIDTH=300;
		StyleContext consolrStyleContext = new StyleContext();
		sourceCodeDocument = new DefaultStyledDocument(consolrStyleContext);
		sourceCodePanel = new JTextPane(sourceCodeDocument);
		sourceCodePanel.setEditable( false );		
		JPanel sourceCodeNoWrapPanel = new JPanel( new BorderLayout() );
		sourceCodeNoWrapPanel.add( sourceCodePanel );		
		DefaultCaret consolCaret = (DefaultCaret)sourceCodePanel.getCaret();
		consolCaret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);		
		JScrollPane sourceCodeScrollablePanel = new JScrollPane(sourceCodeNoWrapPanel);
		sourceCodeScrollablePanel.setHorizontalScrollBarPolicy( JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
		sourceCodeScrollablePanel.setMinimumSize( new Dimension( OUTPUTPANEL_WIDTH, 100 ) );
		sourceCodeScrollablePanel.setPreferredSize( new Dimension( OUTPUTPANEL_WIDTH, 100 ) );
		
		//Source Code - Normal
		attributeSourceCodeNormal = new SimpleAttributeSet();
		StyleConstants.setForeground( attributeSourceCodeNormal, Color.BLACK );
		StyleConstants.setBold( attributeSourceCodeNormal, false);
		
		// -----------
		//
		// Output panel
		//
		// -----------		
		StyleContext outputStyleContext = new StyleContext();
		outputDocument = new DefaultStyledDocument( outputStyleContext );		
		outputPanel = new JTextPane( outputDocument );		
		outputPanel.setEditable( false );
		JPanel outputNoWrapPanel = new JPanel( new BorderLayout() );
		outputNoWrapPanel.add( outputPanel );
		DefaultCaret outputCaret = (DefaultCaret)outputPanel.getCaret();
		outputCaret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);		
		JScrollPane outputScrollablePanel = new JScrollPane(outputNoWrapPanel);
		outputScrollablePanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);		
		outputScrollablePanel.setMinimumSize( new Dimension( OUTPUTPANEL_WIDTH, 100 ) );
		outputScrollablePanel.setPreferredSize( new Dimension( OUTPUTPANEL_WIDTH, 100 ) );
		
		//Ha linkre mozgatom a kurzort (alahuzott), akkor egy tenyeret mutat a kurzor
		outputPanel.addMouseMotionListener( new MouseInputAdapter() {
			public void mouseMoved( MouseEvent e ) {
	               Element elem = outputDocument.getCharacterElement( outputPanel.viewToModel(e.getPoint()));
	               AttributeSet as = elem.getAttributes();
	               
	               DataModelAdapter dataModel = (DataModelAdapter)as.getAttribute(LinkOutputMessage.LINK_ATTRIBUTE);
	               if(dataModel != null){
	            	   outputPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
	               }else{
	            	   outputPanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					}

	               //if(StyleConstants.isUnderline(as))
	               //     outputPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
	               //else
	               //     outputPanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	          }
		});
		
		//Ha linkre kattintok, akkor a megfelelo DataModelElement tree nyilik meg
		outputPanel.addMouseListener( new MouseAdapter( ) {
			public void mouseClicked( MouseEvent e ) {
				try{
					Element elem = outputDocument.getCharacterElement( outputPanel.viewToModel(e.getPoint()));
					AttributeSet as = elem.getAttributes();
					DataModelAdapter dataModel = (DataModelAdapter)as.getAttribute(LinkOutputMessage.LINK_ATTRIBUTE);
					if(dataModel != null){
						for( LinkToNodeInTreeListener linkToNodeInTreeListener: getLinkToNodeInTreeListeners() ){
							
							//Kinyitja a kivant Tree ablakot
							linkToNodeInTreeListener.linkToNode( dataModel );
						}						
					}
				}
				catch(Exception x) {
					x.printStackTrace();
				}
			}
		});
	
		//------------
		//Result panel
		//------------
		int RESULTPANEL_WIDTH = 400;
		resultPanel = new ResultPanel( RESULTPANEL_WIDTH );
		JScrollPane scrollPaneForResultPanel = new JScrollPane(resultPanel);
		Rectangle bounds = scrollPaneForResultPanel.getViewportBorderBounds();
		scrollPaneForResultPanel.setMinimumSize( new Dimension( RESULTPANEL_WIDTH + Math.abs( bounds.width ), 100 ) );
		scrollPaneForResultPanel.setPreferredSize( new Dimension( RESULTPANEL_WIDTH + Math.abs( bounds.width ), 100 ) );
		 
		//----------
		//Main panel
		//----------
		JPanel mainPanel = new JPanel();		
		GridBagConstraints c = new GridBagConstraints();		
		mainPanel.setLayout(new GridBagLayout());	
		mainPanel.setBorder( BorderFactory.createEmptyBorder( 0, 0, 0, 0 ) );
	
		// CONTROL PANEL elhelyezese
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(0,0,0,0);
		c.gridwidth = 1;
		c.gridheight = 1;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 0;
		c.weighty = 0;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		mainPanel.add( controlPanel, c );
		
		// Ideiglenes kitolto az options-helyett
		c.gridx = 0;
		c.gridy = 2;
		c.insets = new Insets(0,0,0,0);
		c.gridwidth = 2;
		c.gridheight = 1;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0;
		c.weighty = 0;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		JPanel b1 = new JPanel();
		//b1.setBackground( Color.BLUE );
		mainPanel.add( b1, c );		

		c.gridx = 0;
		c.gridy = 3;
		c.insets = new Insets(0,0,0,0);
		c.gridwidth = 2;
		c.gridheight = 3;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0;
		c.weighty = 0;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		//mainPanel.add( new JLabel(), c );		
		JPanel b2 = new JPanel();
		//b2.setBackground( Color.BLUE );
		mainPanel.add( b2, c );	
		
		// Kitolto a control mellett jobbra
		c.gridx = 1;
		c.gridy = 1;
		c.insets = new Insets(0,0,0,0);
		c.gridwidth = 1;
		c.gridheight = 1;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0;
		c.weighty = 0;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		//mainPanel.add( new JLabel(), c );		
		JPanel b3 = new JPanel();
		b3.setPreferredSize(new Dimension(50,10));
		//b3.setBackground( Color.BLUE );
		mainPanel.add( b3, c );	
		
		//	RESULT PANEL felirat
		c.gridx = 2;		
		c.gridy = 0;		
		c.insets = new Insets(0,0,0,0);
		c.gridwidth = 1;
		c.gridheight = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0;
		c.weighty = 0;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		mainPanel.add( new JLabel( CommonOperations.getTranslation( "editor.label.runtest.result" ) ), c );

		// RESULT PANEL elhelyezese
		c.gridx = 2;		
		c.gridy = 1;		
		c.insets = new Insets(0,0,0,0);
		c.gridwidth = 1;
		c.gridheight = 5;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0;
		c.weighty = 1;
		c.anchor = GridBagConstraints.FIRST_LINE_START;		
		mainPanel.add( scrollPaneForResultPanel, c );
	
		// OUTPUT PANEL felirat
		c.gridx = 3;
		c.gridy = 0;
		c.insets = new Insets(0,0,0,0);
		c.gridwidth = 1;
		c.gridheight = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.weighty = 0;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		mainPanel.add( new JLabel( CommonOperations.getTranslation( "editor.label.runtest.output" ) ), c );
		
		// OUTPUT PANEL elhelyezese
		c.gridx = 3;
		c.gridy = 1;
		c.insets = new Insets(0,0,0,0);
		c.gridwidth = 1;
		c.gridheight = 3;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		mainPanel.add( outputScrollablePanel, c );
		
		// SOURCE CODE felirat
		c.gridx = 3;
		c.gridy = 4;		
		c.insets = new Insets(0,0,0,0);
		c.gridwidth = 1;
		c.gridheight = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0;
		c.weighty = 0;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		mainPanel.add( new JLabel( CommonOperations.getTranslation( "editor.label.runtest.sourcecode" ) ), c );	

		// SOURCE CODE
		c.gridx = 3;
		c.gridy = 5;		
		c.insets = new Insets(0,0,0,0);
		c.gridwidth = 1;
		c.gridheight = 1;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		mainPanel.add( sourceCodeScrollablePanel, c );	
		
		this.add( mainPanel, BorderLayout.CENTER);
		
	}

	public void addLinkToNodeInTreeListener( LinkToNodeInTreeListener linkToNodeInTreeListener ){
		linkToNodeInTreeListeners.add( linkToNodeInTreeListener);
	}

	public ArrayList<LinkToNodeInTreeListener> getLinkToNodeInTreeListeners(){
		return linkToNodeInTreeListeners;
	}
	
	
	
	
	
	
	

	
	
	
	
	
	
	
	
	private void runTestcases( TestcaseDataModelAdapter testcase ){
				
		progressIndicator.printSourceLn( "import org.openqa.selenium.By;" );
		progressIndicator.printSourceLn( "import org.openqa.selenium.WebDriver;" );
		progressIndicator.printSourceLn( "import org.openqa.selenium.WebElement;" );
		progressIndicator.printSourceLn( "import org.openqa.selenium.firefox.FirefoxDriver;" );
		progressIndicator.printSourceLn( "import org.openqa.selenium.firefox.FirefoxProfile;" );
		progressIndicator.printSourceLn( "import org.openqa.selenium.interactions.Actions;" );
		progressIndicator.printSourceLn( "import org.openqa.selenium.WebDriverException;" );	
		progressIndicator.printSourceLn( "import org.openqa.selenium.JavascriptExecutor;");		
		progressIndicator.printSourceLn( "import org.openqa.selenium.ie.InternetExplorerDriver;" );
		progressIndicator.printSourceLn( "import org.openqa.selenium.remote.CapabilityType;" );
		progressIndicator.printSourceLn( "import org.openqa.selenium.remote.DesiredCapabilities;" );		
		progressIndicator.printSourceLn( "import org.openqa.selenium.Keys;" );
		
		progressIndicator.printSourceLn( "import org.openqa.selenium.support.ui.Select;" );
		progressIndicator.printSourceLn( "import org.openqa.selenium.support.ui.WebDriverWait;" );
		progressIndicator.printSourceLn( "import org.openqa.selenium.support.ui.UnexpectedTagNameException;" );
		progressIndicator.printSourceLn( "import org.openqa.selenium.support.ui.ExpectedConditions;" );
		
		progressIndicator.printSourceLn( "" );
		
		progressIndicator.printSourceLn( "import java.util.concurrent.TimeUnit;" );
		progressIndicator.printSourceLn( "import java.util.NoSuchElementException;" );
		progressIndicator.printSourceLn( "import java.util.ArrayList;" );
		progressIndicator.printSourceLn( "import java.util.List;" );			
		progressIndicator.printSourceLn( "import java.util.Iterator;");
		progressIndicator.printSourceLn( "import java.util.regex.Matcher;");
		progressIndicator.printSourceLn( "import java.util.regex.Pattern;");
		progressIndicator.printSourceLn( "import java.util.Calendar;");
		progressIndicator.printSourceLn( "import java.util.Date;");
		
		progressIndicator.printSourceLn( "" );

		progressIndicator.printSourceLn( "import org.junit.Test;" );
		progressIndicator.printSourceLn( "import static org.junit.Assert.*;" );				
		progressIndicator.printSourceLn( "import org.junit.runner.JUnitCore;" );
		progressIndicator.printSourceLn( "import org.junit.runner.Result;" );
		progressIndicator.printSourceLn( "import org.junit.runner.notification.Failure;" );
		
		progressIndicator.printSourceLn( "" );
		
		progressIndicator.printSourceLn( "abstract class ScriptClass{");

		progressIndicator.printSourceLn( CommonOperations.TAB_BY_SPACE + "ArrayList<String> parameters = new ArrayList<>();" );	
		progressIndicator.printSourceLn( CommonOperations.TAB_BY_SPACE + "abstract public void runScript() throws Exception;" );	
		progressIndicator.printSourceLn( CommonOperations.TAB_BY_SPACE + "public void addParameter( String parameter ){" );	
		progressIndicator.printSourceLn( CommonOperations.TAB_BY_SPACE + CommonOperations.TAB_BY_SPACE + "this.parameters.add( parameter );" );
		progressIndicator.printSourceLn( CommonOperations.TAB_BY_SPACE + "}" );	
		progressIndicator.printSourceLn( CommonOperations.TAB_BY_SPACE + "public void clearParameters(){" );	
		progressIndicator.printSourceLn( CommonOperations.TAB_BY_SPACE + CommonOperations.TAB_BY_SPACE + "this.parameters.clear();" );
		progressIndicator.printSourceLn( CommonOperations.TAB_BY_SPACE + "}" );	
		progressIndicator.printSourceLn( CommonOperations.TAB_BY_SPACE + "public Iterator<String> getParameterIterator(){" );	
		progressIndicator.printSourceLn( CommonOperations.TAB_BY_SPACE + CommonOperations.TAB_BY_SPACE + "return parameters.iterator();" );
		progressIndicator.printSourceLn( CommonOperations.TAB_BY_SPACE + "}" );	
		progressIndicator.printSourceLn( "}" );	
		
		progressIndicator.printSourceLn( "" );	
		
		progressIndicator.printSourceLn( "public class " + CLASS_NAME + "{ ");
		progressIndicator.printSourceLn( "" );
		progressIndicator.printSourceLn( CommonOperations.TAB_BY_SPACE + "WebDriverWait wait = null;");
		progressIndicator.printSourceLn( CommonOperations.TAB_BY_SPACE + "By by = null;" );
		progressIndicator.printSourceLn( CommonOperations.TAB_BY_SPACE + "WebElement webElement = null;");
		progressIndicator.printSourceLn( CommonOperations.TAB_BY_SPACE + "Select select = null;");	
		progressIndicator.printSourceLn( CommonOperations.TAB_BY_SPACE + "Integer index = 0;" );
		progressIndicator.printSourceLn( CommonOperations.TAB_BY_SPACE + "WebDriver driver = null;" );
		progressIndicator.printSourceLn( CommonOperations.TAB_BY_SPACE + "FirefoxProfile profile = null;");
		progressIndicator.printSourceLn( CommonOperations.TAB_BY_SPACE + "JavascriptExecutor executor = null;");
		
		progressIndicator.printSourceLn( CommonOperations.TAB_BY_SPACE + "List<WebElement> optionList;" );
		progressIndicator.printSourceLn( CommonOperations.TAB_BY_SPACE + "boolean found = false;" );
		progressIndicator.printSourceLn( CommonOperations.TAB_BY_SPACE + "String origText;" );
		progressIndicator.printSourceLn( CommonOperations.TAB_BY_SPACE + "String optionText;" );
		progressIndicator.printSourceLn( CommonOperations.TAB_BY_SPACE + "Matcher matcher;" );
		progressIndicator.printSourceLn( CommonOperations.TAB_BY_SPACE + "Pattern pattern;" );
		
		//This part is for running the Testcase as a normal Application from consol
		progressIndicator.printSourceLn( "" );
		progressIndicator.printSourceLn( CommonOperations.TAB_BY_SPACE + "//For running as an Application from command line" );
		progressIndicator.printSourceLn( CommonOperations.TAB_BY_SPACE + "public static void main( String[] args ){" );
		progressIndicator.printSourceLn( CommonOperations.TAB_BY_SPACE + CommonOperations.TAB_BY_SPACE + "Result result = JUnitCore.runClasses( " + CLASS_NAME + ".class);" );
		progressIndicator.printSourceLn( CommonOperations.TAB_BY_SPACE + CommonOperations.TAB_BY_SPACE + "for (Failure failure : result.getFailures()) {" );
		progressIndicator.printSourceLn( CommonOperations.TAB_BY_SPACE + CommonOperations.TAB_BY_SPACE + CommonOperations.TAB_BY_SPACE + "System.out.println(failure.toString());" );
		progressIndicator.printSourceLn( CommonOperations.TAB_BY_SPACE + CommonOperations.TAB_BY_SPACE + "}" );
		progressIndicator.printSourceLn( CommonOperations.TAB_BY_SPACE + "}" );
		progressIndicator.printSourceLn( "" );
		
		throughTestcases( testcase );
		
		//Closes the MyTest Class
		progressIndicator.printSourceLn( "}");	
	}
	
	/**
	 * Rekurziven vegig megy az osszes test case-en a parameterkent megadott
	 * pontbol kiindulva
	 *  
	 * @param testcase
	 */
	private void throughTestcases( TestcaseDataModelAdapter testcase ){

		//TODO Lehet, hogy ez nem is kell. Ellenorizni!!!
		if( isStopped() ){
			return;
		}
		
		//Ha egy TESTCASE-t kaptam, akkor azt vegrehajtatom
		if( testcase instanceof TestcaseCaseDataModel ){
			
			executeTestcase( (TestcaseCaseDataModel)testcase );
			
		//Ha egy csomopontot valasztottam ki, akkor annak elemein megyek keresztul
		}else if( testcase instanceof TestcaseFolderDataModel || testcase instanceof TestcaseRootDataModel ){
				
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
		
		Set<String> definedElementSet = new HashSet<>();

		//Ha be van kapcsolva	
		if( actualTestcase.isOn() ){

			String testcaseMethodName = actualTestcase.getName().replaceAll("[^a-zA-Z0-9]+","");
			progressIndicator.printSourceLn( CommonOperations.TAB_BY_SPACE + "@Test" );
			progressIndicator.printSourceLn( CommonOperations.TAB_BY_SPACE + "public void " + testcaseMethodName + "(){" );	
			progressIndicator.printSourceLn( "" );
			progressIndicator.printSourceLn( CommonOperations.TAB_BY_SPACE + CommonOperations.TAB_BY_SPACE + "int actualLoop = 0;" );
			progressIndicator.printSourceLn( CommonOperations.TAB_BY_SPACE + CommonOperations.TAB_BY_SPACE + "int maxLoopNumber;" );
			progressIndicator.printSourceLn( CommonOperations.TAB_BY_SPACE + CommonOperations.TAB_BY_SPACE + "int oneLoopLength;" );
			progressIndicator.printSourceLn( CommonOperations.TAB_BY_SPACE + CommonOperations.TAB_BY_SPACE + "Date actualDate;" );
			progressIndicator.printSourceLn( CommonOperations.TAB_BY_SPACE + CommonOperations.TAB_BY_SPACE + "Date startDate;" );			
			progressIndicator.printSourceLn( "" );

			int testcaseRow = -1;
			
			try{				

				WebDriver webDriver = ((TestcaseRootDataModel)actualTestcase.getRoot()).getDriverDataModel().getDriver( progressIndicator, CommonOperations.TAB_BY_SPACE + CommonOperations.TAB_BY_SPACE );

				//IMPLICIT WAIT
				progressIndicator.printSourceLn( CommonOperations.TAB_BY_SPACE + CommonOperations.TAB_BY_SPACE + "//IMPLICIT WAIT" );			
				progressIndicator.printSourceLn( CommonOperations.TAB_BY_SPACE + CommonOperations.TAB_BY_SPACE + "driver.manage().timeouts().implicitlyWait(" + WorkingDirectory.getInstance().getWaitingTime() + ", TimeUnit.SECONDS);" );
				progressIndicator.printSourceLn( "" );
				
				webDriver.manage().timeouts().implicitlyWait(WorkingDirectory.getInstance().getWaitingTime(), TimeUnit.SECONDS );

				int childCount = actualTestcase.getChildCount();
    		
				progressIndicator.testcaseStarted( actualTestcase );
				testcaseRow = resultPanel.startNewTestcase( actualTestcase );
    		
				//A teszteset Page-einek futtatasa
				for( int index = 0; index < childCount; index++ ){
					
					TreeNode treeNode = actualTestcase.getChildAt(index);
					
					//Ha sima Page
					if( treeNode instanceof TestcaseStepDataModelAdapter ){
						
						TestcaseStepDataModelAdapter pageToRun = (TestcaseStepDataModelAdapter)treeNode;					
						pageToRun.doAction(webDriver, this, progressIndicator, CommonOperations.TAB_BY_SPACE + CommonOperations.TAB_BY_SPACE, definedElementSet );
					}					
				}					
    		
				//TODO kerdeses. Lehet, hogy beletehetnem a testcaseEnded()-be az addnewstatus-t, hogy majd o irja ki				
				resultPanel.finishTestcase( testcaseRow, ResultStatus.CHECKED );

			}catch( CompilationException compillationException ){
  		
				compillationException.printMessage( outputDocument );
				resultPanel.finishTestcase( testcaseRow, ResultStatus.FAILED );
    		
			}catch( StepException pageException ){
    		
				pageException.printMessage( outputDocument );
				resultPanel.finishTestcase( testcaseRow, ResultStatus.FAILED );
    		
			}catch( StoppedByUserException stoppedByUserException ){
    		
				stoppedByUserException.printMessage( outputDocument );
				resultPanel.finishTestcase( testcaseRow, ResultStatus.STOPPED );
    		
			//Nem kezbentartott hiba
			}catch( Exception exception ){
				
				progressIndicator.printOutput( "Exception", exception.getMessage() + "(" + this.getClass().getSimpleName() + ")", progressIndicator.ATTRIBUTE_MESSAGE_ERROR );
				resultPanel.finishTestcase( testcaseRow, ResultStatus.FAILED );   		
			
			}

			//Closes the Test method
			progressIndicator.testcaseEnded( actualTestcase );
			progressIndicator.printSourceLn( CommonOperations.TAB_BY_SPACE + "}");
			progressIndicator.printSourceLn( "" );			
		}
	}
	
	class ProgressIndicator implements ProgressIndicatorInterface{
		
		public SimpleAttributeSet ATTRIBUTE_LABEL;
		public SimpleAttributeSet ATTRIBUTE_MESSAGE_NORMAL;
		public SimpleAttributeSet ATTRIBUTE_MESSAGE_ERROR;
		public SimpleAttributeSet ATTRIBUTE_MESSAGE_INFO;
		public SimpleAttributeSet ATTRIBUTE_TESTCASE_TITLE;
		public SimpleAttributeSet ATTRIBUTE_TESTCASE_TITLE_LINK;
	
		public ProgressIndicator(){

			ATTRIBUTE_LABEL = new SimpleAttributeSet();
			StyleConstants.setForeground( ATTRIBUTE_LABEL, Color.GRAY );
			StyleConstants.setBold( ATTRIBUTE_LABEL, true);
		
			ATTRIBUTE_MESSAGE_NORMAL = new SimpleAttributeSet();
			StyleConstants.setForeground( ATTRIBUTE_MESSAGE_NORMAL, Color.BLACK );
			StyleConstants.setBold( ATTRIBUTE_MESSAGE_NORMAL, true);
			
			ATTRIBUTE_MESSAGE_ERROR = new SimpleAttributeSet();
			StyleConstants.setForeground( ATTRIBUTE_MESSAGE_ERROR, Color.RED );
			StyleConstants.setBold( ATTRIBUTE_MESSAGE_ERROR, true);

			ATTRIBUTE_MESSAGE_INFO = new SimpleAttributeSet();
			StyleConstants.setForeground( ATTRIBUTE_MESSAGE_INFO, Color.BLUE );
			StyleConstants.setBold( ATTRIBUTE_MESSAGE_INFO, true);

			ATTRIBUTE_TESTCASE_TITLE = new SimpleAttributeSet();
			StyleConstants.setForeground( ATTRIBUTE_TESTCASE_TITLE, Color.GRAY );
			StyleConstants.setBold( ATTRIBUTE_TESTCASE_TITLE, false);
			StyleConstants.setItalic(ATTRIBUTE_TESTCASE_TITLE, true);
			StyleConstants.setFontSize(ATTRIBUTE_TESTCASE_TITLE, 16);
			
			ATTRIBUTE_TESTCASE_TITLE_LINK = new SimpleAttributeSet();
			StyleConstants.setForeground( ATTRIBUTE_TESTCASE_TITLE_LINK, new Color( 0, 0, 153 ) );
			StyleConstants.setBold( ATTRIBUTE_TESTCASE_TITLE_LINK, false );
			StyleConstants.setItalic( ATTRIBUTE_TESTCASE_TITLE_LINK, true );
			StyleConstants.setUnderline( ATTRIBUTE_TESTCASE_TITLE_LINK, true );
			StyleConstants.setFontSize( ATTRIBUTE_TESTCASE_TITLE_LINK, 16 );
		}

		@Override
		public void printOutput(String label, String message, SimpleAttributeSet attributeMessage) {
			if( null == attributeMessage ){
				attributeMessage = ATTRIBUTE_MESSAGE_NORMAL;
			}
			
			try {				
				if( null != label && label.trim().length() != 0 ){
					RunTestcaseEditor.this.outputDocument.insertString( outputDocument.getLength(), label + ": ", ATTRIBUTE_LABEL );
				}
				RunTestcaseEditor.this.outputDocument.insertString( outputDocument.getLength(), message, attributeMessage );
								
			} catch (BadLocationException e) {}		
		}

		@Override
		public void printOutputLn(String label, String message, SimpleAttributeSet attributeMessage) {
			if( null == attributeMessage ){
				attributeMessage = ATTRIBUTE_MESSAGE_NORMAL;
			}
			
			printOutput( label, message, attributeMessage );
			
			try {				
				RunTestcaseEditor.this.outputDocument.insertString( outputDocument.getLength(), "\n", attributeMessage );
								
			} catch (BadLocationException e) {}					
		}
		
		@Override
		public void printSource(String sourceCode) {
			try {
				sourceCodeDocument.insertString( sourceCodeDocument.getLength(), sourceCode, attributeSourceCodeNormal );
			} catch (BadLocationException e) {}
		}
		
		@Override
		public void printSourceLn( String sourceCode ) {
			printSource( sourceCode );
			try {
				sourceCodeDocument.insertString( sourceCodeDocument.getLength(), "\n", attributeSourceCodeNormal );
			} catch (BadLocationException e) {}
		}

		@Override
		public void elementStarted(StepElementDataModel stepElement) {
//			traceTree.select( stepElement );
		}

		@Override
		public void elementEnded(StepElementDataModel stepElement) {
		}

		@Override
		public void testcaseStarted(TestcaseCaseDataModel testcase) {

			//RunTestcaseEditor.this.outputDocument.insertString( outputDocument.getLength(), "---" + testcase.getName() + "---" + "\n", ATTRIBUTE_TESTCASE_TITLE );
			ArrayList<OutputMessageAdapter> outputMessage = new ArrayList<>();
			outputMessage.add( new AttributedOutputMessage( "---", ATTRIBUTE_TESTCASE_TITLE ) );
			outputMessage.add( new LinkOutputMessage( testcase, ATTRIBUTE_TESTCASE_TITLE_LINK ) );
			outputMessage.add( new AttributedOutputMessage( "---\n", ATTRIBUTE_TESTCASE_TITLE ) );
			for( OutputMessageAdapter message: outputMessage ){
				message.printOut( outputDocument );
			}
		}

		@Override
		public void testcaseEnded(TestcaseCaseDataModel testcase) {
			try {
				RunTestcaseEditor.this.outputDocument.insertString( outputDocument.getLength(), "\n", ATTRIBUTE_TESTCASE_TITLE );
			} catch (BadLocationException e) {}			

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
		
		private static final int RESULT_WIDTH = 80;
		
		public ResultPanel( int preferredWidth ){
			super();
			
			PanelTableModel model = new PanelTableModel();
			CustomCellRenderer renderer = new CustomCellRenderer();

			//Ez kell ahhoz, hogy a hatter atszinezhetove valjon
			this.setOpaque(true);	
			//this.setFillsViewportHeight(true);
		
			this.setModel(model);
			this.setDefaultRenderer(Object.class, renderer);

			TableColumn testcaseColumn = this.getColumnModel().getColumn(0);
			testcaseColumn.setPreferredWidth( preferredWidth - RESULT_WIDTH );
			testcaseColumn.setMinWidth( preferredWidth - RESULT_WIDTH );

			TableColumn resultColumn = this.getColumnModel().getColumn(1);
			resultColumn.setPreferredWidth( RESULT_WIDTH );
			resultColumn.setMinWidth( RESULT_WIDTH );

			//This is for always see the last row
			this.addComponentListener(new ComponentAdapter() {
			    public void componentResized(ComponentEvent e) {
			        ResultPanel.this.scrollRectToVisible(ResultPanel.this.getCellRect(ResultPanel.this.getRowCount()-1, 0, true));
			    }
			});
			
			//Ez teszi lehetove hogy automatikusan megjelenjen a horizontalis scrollbar, ha kell
			this.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );				
		}
		
		/*public boolean getScrollableTracksViewportWidth(){
            return getPreferredSize().width < getParent().getWidth();
        }*/
		
		public void clear(){
			DefaultTableModel dm = (DefaultTableModel) getModel();
			int rowCount = dm.getRowCount();
			for (int i = rowCount - 1; i >= 0; i--) {
			    dm.removeRow(i);
			}			
			this.revalidate();
			this.repaint();
		}
		
		public int startNewTestcase( TestcaseCaseDataModel testcase ){
			DefaultTableModel model = (DefaultTableModel) this.getModel();
	        model.addRow(new Object[]{testcase, ResultStatus.INPROGRESS });
	        int actualRow = model.getRowCount() - 1;
	        this.setRowSelectionInterval( actualRow, actualRow );
	        return actualRow;
		}
		
		public void finishTestcase( int row, ResultStatus resultStatus ){
			DefaultTableModel model = (DefaultTableModel) this.getModel();
			model.setValueAt( resultStatus, row, 1 );
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
		CHECKED( CommonOperations.createImageIcon("result/result-checked.png") ),
		FAILED( CommonOperations.createImageIcon("result/result-failed.png") ),
		STOPPED( CommonOperations.createImageIcon("result/result-stopped.png") ),
		INPROGRESS( CommonOperations.createImageIcon("result/result-inprogress.png") );
		;
		
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
