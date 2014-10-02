package hu.akoel.grawit.gui.editor.run;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;

import javax.swing.BorderFactory;
import javax.swing.JButton;
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

import org.openqa.selenium.WebDriver;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.ElementProgressInterface;
import hu.akoel.grawit.ExecutablePageInterface;
import hu.akoel.grawit.PageProgressInterface;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseCaseDataModel;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcasePageModelInterface;
import hu.akoel.grawit.exceptions.CompilationException;
import hu.akoel.grawit.exceptions.PageException;
import hu.akoel.grawit.gui.editor.BaseEditor;
import hu.akoel.grawit.gui.tree.Tree;

public class RunTestcaseEditor extends BaseEditor{
	
	private static final long serialVersionUID = -7285419881714492620L;
	
	private TestcaseCaseDataModel selectedTestcase;
	
	private PageProgress pageProgress;
	private ElementProgress elementProgress;
	
	private JButton runButton;
	private JTextPane consolPanel;
	private JTextArea statusPanel;	
	private JTextArea valuePanel;
	private DefaultStyledDocument consolDocument;
	private SimpleAttributeSet attributeError;
	private SimpleAttributeSet attributePageFinished;
	private SimpleAttributeSet attributeElementFinished;	
	
	public RunTestcaseEditor( Tree tree, TestcaseCaseDataModel testcaseCaseElement ){		

		super( CommonOperations.getTranslation( "editor.label.runtest.windowtitle" ) );

		this.selectedTestcase = testcaseCaseElement;

		pageProgress = new PageProgress();
		elementProgress = new ElementProgress();
		
		runButton = new JButton( CommonOperations.getTranslation("editor.label.runtest.runbutton") );
		runButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
								
				new Thread( new Runnable() {

					@Override
					public void run() {						
						
						RunTestcaseEditor.this.runButton.setEnabled( false );
						
						valuePanel.setText("");
						statusPanel.setText("");
						consolPanel.setText("");
												
				    	TestcaseCaseDataModel selectedTestcase = RunTestcaseEditor.this.selectedTestcase;
						
				    	ExecutablePageInterface openPage = selectedTestcase.getOpenPage();
				    	ExecutablePageInterface closePage = selectedTestcase.getClosePage();
				    	WebDriver webDriver = selectedTestcase.getDriverDataModel().getDriver();
			
				    	try{				
			
				    		openPage.doAction( webDriver, pageProgress, elementProgress );
				
				    		int childCount = selectedTestcase.getChildCount();
				    		for( int index = 0; index < childCount; index++ ){
				    			TestcasePageModelInterface pageToRun = (TestcasePageModelInterface)selectedTestcase.getChildAt(index);
				    			pageToRun.doAction(webDriver, pageProgress, elementProgress);
				    		}					
				
				    		//Ha van closePage definialva, akkor vegrehajtom
				    		if( null != closePage ){
				    			closePage.doAction( webDriver, pageProgress, elementProgress );
				    		}
				
				    	}catch( CompilationException compillationException ){
				    		
				    		//reportList.append( compillationException.getMessage() + "\n\n" );
				    		try {
								consolDocument.insertString(consolDocument.getLength(), compillationException.getMessage() + "\n\n", attributeError );
							} catch (BadLocationException e) {e.printStackTrace();}
				    		
				    	}catch( PageException pageException ){
				    		
				    		//reportList.append( pageException.getMessage() +  "\n\n" );
				    		try {
								consolDocument.insertString(consolDocument.getLength(), pageException.getMessage() + "\n\n", attributeError );
							} catch (BadLocationException e) {e.printStackTrace();}
				    	
				    	//Nem kezbentartott hiba
				    	}catch( Exception exception ){
				    		
				    		try {
								consolDocument.insertString(consolDocument.getLength(), exception.getMessage() + "\n\n", attributeError );
							} catch (BadLocationException e) {e.printStackTrace();}
				    	
				    		
/*				    	}catch( org.openqa.selenium.TimeoutException timeOutException ){
				    		timeoutException.
				    		System.out.println(timeOutException.getClass());
				    		errorList.append( timeOutException.getMessage() +  "\n\n" );
*/				    		
				    	}
				    	
				    	RunTestcaseEditor.this.runButton.setEnabled( true );
			
					}				 
				 
				 }).start();
			}
						
		});		

	
		
		GridBagConstraints c = new GridBagConstraints();
		
		JPanel controlPanel = getDataSection();		
		controlPanel.setBorder( BorderFactory.createEmptyBorder( 0, 0, 0, 0 ) );
		
		//Error list panel		
		//reportList = new JTextArea( 4, 11);
		
		 StyleContext sc = new StyleContext();
		 consolDocument = new DefaultStyledDocument(sc);
		 consolPanel = new JTextPane(consolDocument);
		 
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
		
		 //Automatic scroll
		 DefaultCaret reportListCaret = (DefaultCaret)consolPanel.getCaret();
		 reportListCaret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		 
		 //Scroll panel
		 JScrollPane scrollPaneForConsolPanel = new JScrollPane(consolPanel);
//		 scrollPaneForConsolPanel.setPreferredSize(new Dimension(10,100));
		 scrollPaneForConsolPanel.setAutoscrolls(true);
		 
//		 this.add( scrollPaneForConsolPanel, BorderLayout.SOUTH );
		 
		 
		 
		    
		
		
		//Status panel
		statusPanel = new JTextArea(2, 25);
		statusPanel.setEditable(false);
		DefaultCaret pageListCaret = (DefaultCaret)statusPanel.getCaret();
		pageListCaret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		JScrollPane scrollPaneForStatusPanel = new JScrollPane(statusPanel);

		//value panel
		valuePanel = new JTextArea(2, 15);
		valuePanel.setEditable(false);
		DefaultCaret valueCaret = (DefaultCaret)statusPanel.getCaret();
		valueCaret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		JScrollPane scrollPaneForValuePanel = new JScrollPane(valuePanel);
		
		//filler panel
		JPanel fillerPanel = new JPanel();
		
		c.gridy = 0;
		c.gridx = 2;
		c.insets = new Insets(0,0,0,0);
		c.gridwidth = 1;
		c.gridheight = 2;
		c.fill = GridBagConstraints.VERTICAL;
		c.weightx = 0;
		c.weighty = 1;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		controlPanel.add( scrollPaneForStatusPanel, c );
		
		c.gridy = 0;
		c.gridx = 3;
		c.insets = new Insets(0,0,0,0);
		c.gridwidth = 1;
		c.gridheight = 2;
		c.fill = GridBagConstraints.VERTICAL;
		c.weightx = 0;
		c.weighty = 1;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		controlPanel.add( scrollPaneForValuePanel, c );
		
		c.gridy = 0;
		c.gridx = 1;
		c.insets = new Insets(0,0,0,0);
		c.gridwidth = 1;
		c.gridheight = 1;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 0;
		c.weighty = 0;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		controlPanel.add( runButton, c );	
		
		c.gridy = 0;
		c.gridx = 0;
		c.insets = new Insets(0,0,0,0);
		c.gridwidth = 1;
		c.gridheight = 1;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 1;
		c.weighty = 0;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		controlPanel.add( fillerPanel, c );	
			
		
		MyHorizontalSplitPane horizontalSplitPane = new MyHorizontalSplitPane(JSplitPane.VERTICAL_SPLIT, controlPanel, scrollPaneForConsolPanel);
		horizontalSplitPane.setOneTouchExpandable(false);
		horizontalSplitPane.setFlippedDividerLocation( 200 );	        
		this.add( horizontalSplitPane, BorderLayout.CENTER);
		
		
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
	
	class PageProgress implements PageProgressInterface{

		@Override
		public void pageStarted(String pageID, String nodeType) {			
			try {
				consolDocument.insertString(
						consolDocument.getLength(),  
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
						consolDocument.getLength(),  
						MessageFormat.format(
								CommonOperations.getTranslation("editor.runtestcase.message.pageended"), 
								pageID, nodeType
						) + "\n", 
						attributePageFinished 
				);				
			} catch (BadLocationException e) {e.printStackTrace();}
			
			RunTestcaseEditor.this.statusPanel.append( pageID + " OK\n");
			
		}		
	}
	
	class ElementProgress implements ElementProgressInterface{
		
		@Override
		public void elementStarted(String name ) {
			try {
				consolDocument.insertString(consolDocument.getLength(), "    " + 
						MessageFormat.format(
								CommonOperations.getTranslation("editor.runtestcase.message.elementstarted"), 
								"'"+name+"'"
						) + "\n", null 
				);
			} catch (BadLocationException e) {e.printStackTrace();}
			
		}
	
		@Override
		public void elementEnded(String name) {
			
			try {
				consolDocument.insertString(consolDocument.getLength(), "    " + 
						MessageFormat.format(
								CommonOperations.getTranslation("editor.runtestcase.message.elementended"), 
								"'"+name+"'"
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
			
		}
		
	}
}
