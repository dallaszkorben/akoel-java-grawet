package hu.akoel.grawit.gui.editor.run;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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
	//private JTextArea reportList;
	private JTextPane reportList;
	private DefaultStyledDocument reportDocument;
	private SimpleAttributeSet attributeError;
	private SimpleAttributeSet attributePageFinished;
	private SimpleAttributeSet attributeElementFinished;
	
	private JTextArea pageList;	
	
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
						
						pageList.setText("");
						reportList.setText("");
												
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
				
//				    		closePage.doAction( webDriver, pageProgress, elementProgress );
				
				    	}catch( CompilationException compillationException ){
				    		
				    		//reportList.append( compillationException.getMessage() + "\n\n" );
				    		try {
								reportDocument.insertString(reportDocument.getLength(), compillationException.getMessage() + "\n\n", attributeError );
							} catch (BadLocationException e) {e.printStackTrace();}
				    		
				    	}catch( PageException pageException ){
				    		
				    		//reportList.append( pageException.getMessage() +  "\n\n" );
				    		try {
								reportDocument.insertString(reportDocument.getLength(), pageException.getMessage() + "\n\n", attributeError );
							} catch (BadLocationException e) {e.printStackTrace();}
				    	
				    	//Nem kezbentartott hiba
				    	}catch( Exception exception ){
				    		
				    		try {
								reportDocument.insertString(reportDocument.getLength(), exception.getMessage() + "\n\n", attributeError );
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
		 reportDocument = new DefaultStyledDocument(sc);
		 reportList = new JTextPane(reportDocument);
		 
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
		 DefaultCaret reportListCaret = (DefaultCaret)reportList.getCaret();
		 reportListCaret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		 
		 //Scroll panel
		 JScrollPane scrollPaneForReportList = new JScrollPane(reportList);
		 scrollPaneForReportList.setPreferredSize(new Dimension(10,100));
		 
		 this.add( scrollPaneForReportList, BorderLayout.SOUTH );
		 scrollPaneForReportList.setAutoscrolls(true);
		 
		 
		    
		
		
		//Page list
		pageList = new JTextArea(2, 25);
		pageList.setEditable(false);
		DefaultCaret pageListCaret = (DefaultCaret)pageList.getCaret();
		pageListCaret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		JScrollPane spForPageList = new JScrollPane(pageList);
		
		c.gridy = 0;
		c.gridx = 1;
		c.insets = new Insets(0,0,0,0);
		c.gridwidth = 1;
		c.gridheight = 2;
		c.fill = GridBagConstraints.VERTICAL;
		c.weightx = 0;
		c.weighty = 1;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		controlPanel.add( spForPageList, c );
		
		c.gridy = 0;
		c.gridx = 0;
		c.insets = new Insets(0,0,0,0);
		c.gridwidth = 1;
		c.gridheight = 1;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 0;
		c.weighty = 0;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		controlPanel.add( runButton, c );
		
		
		
		
	}
	
	class PageProgress implements PageProgressInterface{

		@Override
		public void pageStarted(String pageID, String nodeType) {			
			try {
				reportDocument.insertString(
						reportDocument.getLength(),  
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
				reportDocument.insertString(
						reportDocument.getLength(),  
						MessageFormat.format(
								CommonOperations.getTranslation("editor.runtestcase.message.pageended"), 
								pageID, nodeType
						) + "\n", 
						attributePageFinished 
				);				
			} catch (BadLocationException e) {e.printStackTrace();}
			
			RunTestcaseEditor.this.pageList.append( pageID + " OK\n");
			
		}		
	}
	
	class ElementProgress implements ElementProgressInterface{
		
		@Override
		public void elementStarted(String name ) {
			try {
				reportDocument.insertString(reportDocument.getLength(), "    " + 
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
				reportDocument.insertString(reportDocument.getLength(), "    " + 
						MessageFormat.format(
								CommonOperations.getTranslation("editor.runtestcase.message.elementended"), 
								"'"+name+"'"
						) + "\n", null 
				);
			} catch (BadLocationException e) {e.printStackTrace();}		
			
		}

		@Override
		public void outputValue(String outputValue) {
System.err.println(outputValue);
			
		}
		
	}
}
