package hu.akoel.grawit.gui.editor.run;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeoutException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import org.openqa.selenium.WebDriver;

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
	
//	private Tree tree;
	
	private TestcaseCaseDataModel selectedTestcase;
	
	private PageProgress pageProgress;
	
	private static RunTestcaseEditor instance = null;
	
	private JButton runButton;
	private JTextArea errorList;
	private JTextArea pageList;	
	
	public static RunTestcaseEditor getInstance( Tree tree, TestcaseCaseDataModel testcaseCaseElement ){
		if( null == instance ){
			instance = new RunTestcaseEditor( tree, testcaseCaseElement );
		}
		return instance; 
	}
	
	private RunTestcaseEditor( Tree tree, TestcaseCaseDataModel testcaseCaseElement ){		

		super( "Teszteset futtatása" );

		this.selectedTestcase = testcaseCaseElement;
		
//		this.tree = tree;	
		
		pageProgress = new PageProgress();
		
		
		
		runButton = new JButton( "Run" );
		runButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
								
				new Thread( new Runnable() {

					@Override
					public void run() {						
						
						RunTestcaseEditor.this.runButton.setEnabled( false );
						
						pageList.setText("");
						errorList.setText("");
						
				    	TestcaseCaseDataModel selectedTestcase = RunTestcaseEditor.this.selectedTestcase;
						
				    	ExecutablePageInterface openPage = selectedTestcase.getOpenPage();
				    	ExecutablePageInterface closePage = selectedTestcase.getClosePage();
				    	WebDriver webDriver = selectedTestcase.getDriverDataModel().getDriver();
			
				    	try{				
			
				    		openPage.doAction( webDriver, pageProgress );
				
				    		int childCount = selectedTestcase.getChildCount();
				    		for( int index = 0; index < childCount; index++ ){
				    			TestcasePageModelInterface pageToRun = (TestcasePageModelInterface)selectedTestcase.getChildAt(index);
				    			pageToRun.doAction(webDriver, pageProgress );
				    		}					
				
				    		closePage.doAction( webDriver, pageProgress );
				
				    	}catch( CompilationException compillationException ){
				    		
				    		errorList.append( compillationException.getMessage() + "\n\n" );
				    		
				    	}catch( PageException pageException ){
				    		
				    		errorList.append( pageException.getMessage() +  "\n\n" );
				    	
//				    	}catch( Exception exception ){
				    		
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
		errorList = new JTextArea( 4, 11);
		errorList.setBorder(BorderFactory.createEmptyBorder());
		errorList.setForeground(Color.red);
		JScrollPane spForErrorList = new JScrollPane(errorList);
		this.add( spForErrorList, BorderLayout.SOUTH );
		
		//Page list
		pageList = new JTextArea(2, 15);
		pageList.setEditable(false);
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
		public void pageStarted(String idName, String modelName) {
			RunTestcaseEditor.this.pageList.append( idName + " started\n");
			
			//System.err.println( "'" + idName + "' azonosítójú '" +  modelName + "' típusú oldal ELINDULT" );
			
		}

		@Override
		public void pageEnded(String idName, String modelName) {
			//System.err.println( "'" + idName + "' azonosítójú '" +  modelName + "' típusú oldal BEFEJEZŐDÖTT" );
			
			RunTestcaseEditor.this.pageList.append( idName + " ended\n");
		}
		
	}
}
