package hu.akoel.grawit.gui.editor.run;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

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
	
	private Tree tree;
	
	private TestcaseCaseDataModel selectedTestcase;
	
	private PageProgress pageProgress;
	
	public RunTestcaseEditor( Tree tree, TestcaseCaseDataModel testcaseCaseElement ){		

		super( "Teszteset futtatása" );

		this.selectedTestcase = testcaseCaseElement;
		
		this.tree = tree;	
		
		pageProgress = new PageProgress();
		
		GridBagConstraints c = new GridBagConstraints();
		
		JButton runButton = new JButton( "Run" );
		runButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
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
					
				}catch( PageException pageException ){
					
				}
				
			}
			
		});		
		
		c.gridy = 0;
		c.gridx = 0;
		c.insets = new Insets(0,0,0,0);
		c.gridwidth = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 0;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		getDataSection().add( runButton, c );
		
	}
	
	
	
	class PageProgress implements PageProgressInterface{

		@Override
		public void pageStarted(String idName, String modelName) {
			System.err.println( "'" + idName + "' azonosítójú '" +  modelName + "' típusú oldal ELINDULT" );
			
		}

		@Override
		public void pageEnded(String idName, String modelName) {
			System.err.println( "'" + idName + "' azonosítójú '" +  modelName + "' típusú oldal BEFEJEZŐDÖTT" );
			
		}
		
	}
}
