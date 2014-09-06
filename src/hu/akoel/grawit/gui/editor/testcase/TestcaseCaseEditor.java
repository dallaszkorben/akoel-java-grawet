package hu.akoel.grawit.gui.editor.testcase;

import java.awt.Component;
import java.text.MessageFormat;
import java.util.LinkedHashMap;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.DriverDataModelInterface;
import hu.akoel.grawit.core.treenodedatamodel.SpecialDataModelInterface;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseCaseDataModel;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseNodeDataModel;
import hu.akoel.grawit.gui.editor.DataEditor;
import hu.akoel.grawit.gui.editors.component.TextAreaComponent;
import hu.akoel.grawit.gui.editors.component.TextFieldComponent;
import hu.akoel.grawit.gui.editors.component.treeselector.DriverTreeSelectorComponent;
import hu.akoel.grawit.gui.editors.component.treeselector.SpecialCloseTreeSelectorComponent;
import hu.akoel.grawit.gui.editors.component.treeselector.SpecialOpenTreeSelectorComponent;
import hu.akoel.grawit.gui.tree.Tree;

import javax.swing.JLabel;
import javax.swing.tree.TreeNode;

public class TestcaseCaseEditor extends DataEditor{

	private static final long serialVersionUID = 42866622420069363L;
	
	private Tree tree;
	private TestcaseCaseDataModel nodeForModify;
	private TestcaseNodeDataModel nodeForCapture;
	private EditMode mode;
	
	private JLabel labelName;
	private TextFieldComponent fieldName;
	private TextAreaComponent fieldDetails;
	
	private JLabel labelOpenSpecialTreeSelector;
	private SpecialOpenTreeSelectorComponent fieldOpenTreeSelector;
	private JLabel labelCloseSpecialTreeSelector;
	private SpecialCloseTreeSelectorComponent fieldCloseTreeSelector;	
	private JLabel labelDriverTreeSelector;
	private DriverTreeSelectorComponent fieldDriverTreeSelector;

	//Itt biztos beszuras van
	public TestcaseCaseEditor( Tree tree, TestcaseNodeDataModel selectedNode, SpecialDataModelInterface specialDataModel, DriverDataModelInterface driverDataModel ){
		
		super( TestcaseCaseDataModel.getModelNameToShowStatic() );
		
		this.tree = tree;
		this.nodeForCapture = selectedNode;
		this.mode = null;
		
		//Name
		fieldName = new TextFieldComponent( "" );
		
		//Details
		fieldDetails = new TextAreaComponent( "", 5, 15);
		
		//SpecialOpenTreeSelector
		fieldOpenTreeSelector = new SpecialOpenTreeSelectorComponent(specialDataModel);
		
		//SpecialCloseTreeSelector
		fieldCloseTreeSelector = new SpecialCloseTreeSelectorComponent(specialDataModel);
			
		//DriverTreeSelector
		fieldDriverTreeSelector = new DriverTreeSelectorComponent(driverDataModel);
		
		common();
		
	}
	
	//Itt modositas van
	public TestcaseCaseEditor( Tree tree, TestcaseCaseDataModel selectedNode, SpecialDataModelInterface specialDataModel, DriverDataModelInterface driverDataModel, EditMode mode ){		
		
		super( mode, selectedNode.getModelNameToShow());

		this.tree = tree;
		this.nodeForModify = selectedNode;
		this.mode = mode;		
		
		//Name
		fieldName = new TextFieldComponent( selectedNode.getName());
		
		//Details
		fieldDetails = new TextAreaComponent( selectedNode.getDetails(), 5, 15);
//TODO lehetne-e inkabb a selectedNode-bol kivenni a masodik parametert?
		//SpecialOpenTreeSelector
		fieldOpenTreeSelector = new SpecialOpenTreeSelectorComponent( specialDataModel, selectedNode.getOpenPage() );
		
		//SpecialOpenTreeSelector
		fieldCloseTreeSelector = new SpecialCloseTreeSelectorComponent( specialDataModel, selectedNode.getClosePage() );
				
		//DriverTreeSelector
		fieldDriverTreeSelector = new DriverTreeSelectorComponent( driverDataModel, selectedNode.getDriverDataModel() );
				
		common();
	}
	
	private void common(){
		
		//Name
		labelName = new JLabel( CommonOperations.getTranslation("editor.label.name") + ": ");
		
		//Open page
		labelOpenSpecialTreeSelector = new JLabel( CommonOperations.getTranslation("editor.label.testcase.openpage") + ": ");
		
		//Close page
		labelCloseSpecialTreeSelector = new JLabel( CommonOperations.getTranslation("editor.label.testcase.closepage") + ": ");

		//WebDriver
		labelDriverTreeSelector = new JLabel( CommonOperations.getTranslation("editor.label.testcase.driver") + ": ");
		
		//Details
		JLabel labelDetails = new JLabel( CommonOperations.getTranslation("editor.label.details") + ": ");	
		
		this.add( labelName, fieldName );
		this.add( labelDetails, fieldDetails );
		this.add( labelOpenSpecialTreeSelector, fieldOpenTreeSelector );
		this.add( labelCloseSpecialTreeSelector, fieldCloseTreeSelector );
		this.add( labelDriverTreeSelector, fieldDriverTreeSelector );
		
	}
	
	@Override
	public void save() {

		//Ertekek trimmelese
		fieldName.setText( fieldName.getText().trim() );
		fieldDetails.setText( fieldDetails.getText().trim() );
		
		//
		//Hibak eseten a hibas mezok osszegyujtese
		//		
		LinkedHashMap<Component, String> errorList = new LinkedHashMap<Component, String>();		
		if( fieldName.getText().length() == 0 ){
			errorList.put( 
					fieldName,
					MessageFormat.format(
							CommonOperations.getTranslation("editor.errormessage.emptyfield"), 
							"'"+labelName.getText()+"'"
					)
			);
		
		}else if( null == fieldOpenTreeSelector.getSelectedDataModel() ){
			errorList.put( 
					fieldOpenTreeSelector,
					MessageFormat.format(
							CommonOperations.getTranslation("editor.errormessage.emptyfield"), 
							"'"+labelOpenSpecialTreeSelector.getText()+"'"
					)
			);			
			
		}else if( null == fieldCloseTreeSelector.getSelectedDataModel() ){
			errorList.put( 
					fieldCloseTreeSelector,
					MessageFormat.format(
							CommonOperations.getTranslation("editor.errormessage.emptyfield"), 
							"'"+labelCloseSpecialTreeSelector.getText()+"'"
					)
			);			
			
		}else if( null == fieldDriverTreeSelector.getSelectedDataModel() ){
			errorList.put( 
					fieldDriverTreeSelector,
					MessageFormat.format(
							CommonOperations.getTranslation("editor.errormessage.emptyfield"), 
							"'"+labelDriverTreeSelector.getText()+"'"
					)
			);	
			
		}else{

			TreeNode nodeForSearch = null;
			
			//Insert
			if( null == mode ){
				
				nodeForSearch = nodeForCapture;
				
			//Modify
			}else if( mode.equals( EditMode.MODIFY )){
				
				nodeForSearch = nodeForModify.getParent();
				
			}
			
			//Megnezi, hogy a node-ban van-e masik azonos nevu elem
			int childrenCount = nodeForSearch.getChildCount();
			for( int i = 0; i < childrenCount; i++ ){
				TreeNode levelNode = nodeForSearch.getChildAt( i );
				
				//Ha Case-rol van szo
				if( levelNode instanceof TestcaseCaseDataModel ){
					
					//Ha azonos a nev
					if( ((TestcaseCaseDataModel) levelNode).getName().equals( fieldName.getText() ) ){
						
						//Ha rogzites van, vagy ha modositas, de a vizsgalt node kulonbozik a modositott-tol
						if( null == mode || ( mode.equals( EditMode.MODIFY ) && !levelNode.equals(nodeForModify) ) ){
							
							//Akkor hiba van
							errorList.put( 
								fieldName, 
								MessageFormat.format( 
										CommonOperations.getTranslation("editor.errormessage.duplicateelement"), 
										fieldName.getText(), 
										CommonOperations.getTranslation("tree.nodetype.testcase.case") 
								) 
							);	
							break;
						}
					}
				}
			}
		}
		
		//Ha volt hiba
		if( errorList.size() != 0 ){
			
			//Hibajelzes
			this.errorAt( errorList );
		
		//Ha nem volt hiba akkor a valtozok veglegesitese
		}else{

			//TreePath pathToOpen = null;
			
			//Uj rogzites eseten
			if( null == mode ){
			
				TestcaseCaseDataModel newTestcaseCase = new TestcaseCaseDataModel( fieldName.getText(), fieldDetails.getText(), fieldOpenTreeSelector.getSelectedDataModel(), fieldCloseTreeSelector.getSelectedDataModel(), fieldDriverTreeSelector.getSelectedDataModel() );				
				nodeForCapture.add( newTestcaseCase );
				
			//Modositas eseten
			}else if( mode.equals(EditMode.MODIFY ) ){

				//Modositja a valtozok erteket
				nodeForModify.setName( fieldName.getText() );
				nodeForModify.setDetails( fieldDetails.getText() );
				nodeForModify.setSpecialOpenDataModel( fieldOpenTreeSelector.getSelectedDataModel() );
				nodeForModify.setSpecialCloseDataModel( fieldCloseTreeSelector.getSelectedDataModel() );
				nodeForModify.setDriverDataModel( fieldDriverTreeSelector.getSelectedDataModel() );
			
			}			
			
			//A fa-ban is modositja a nevet (ha az valtozott)
			tree.changed();
		}		
	}
}
