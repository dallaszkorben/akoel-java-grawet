package hu.akoel.grawit.gui.editor.testcase;

import java.awt.Component;
import java.text.MessageFormat;
import java.util.LinkedHashMap;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.DriverDataModelInterface;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseCaseDataModel;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseNodeDataModel;
import hu.akoel.grawit.gui.editor.DataEditor;
import hu.akoel.grawit.gui.editors.component.TextAreaComponent;
import hu.akoel.grawit.gui.editors.component.TextFieldComponent;
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
	
//	private JLabel labelDriverTreeSelector;
//	private DriverTreeSelectorComponent fieldDriverTreeSelector;

	//Itt biztos beszuras van
	//public TestcaseCaseEditor( Tree tree, TestcaseNodeDataModel selectedNode, DriverDataModelInterface driverDataModel ){	
	public TestcaseCaseEditor( Tree tree, TestcaseNodeDataModel selectedNode ){
		
		super( TestcaseCaseDataModel.getModelNameToShowStatic() );
		
		this.tree = tree;
		this.nodeForCapture = selectedNode;
		this.mode = null;
		
		//Name
		fieldName = new TextFieldComponent( "" );
		
		//Details
		fieldDetails = new TextAreaComponent( "", NOTE_ROWS, 15);
			
		//DriverTreeSelector
//		fieldDriverTreeSelector = new DriverTreeSelectorComponent(driverDataModel);
		
		common();
		
	}
	
	//Itt modositas van
	public TestcaseCaseEditor( Tree tree, TestcaseCaseDataModel selectedNode, DriverDataModelInterface driverDataModel, EditMode mode ){		
		
		super( mode, selectedNode.getNodeTypeToShow());

		this.tree = tree;
		this.nodeForModify = selectedNode;
		this.mode = mode;		
		
		//Name
		fieldName = new TextFieldComponent( selectedNode.getName());
		
		//Details
		fieldDetails = new TextAreaComponent( selectedNode.getDetails(), NOTE_ROWS, 15);

		//DriverTreeSelector
		//fieldDriverTreeSelector = new DriverTreeSelectorComponent( driverDataModel, selectedNode.getDriverDataModel() );
//		fieldDriverTreeSelector = new DriverTreeSelectorComponent( driverDataModel );
				
		common();
	}
	
	private void common(){
		
		//Name
		labelName = new JLabel( CommonOperations.getTranslation("editor.label.name") + ": ");
		
		//WebDriver
//		labelDriverTreeSelector = new JLabel( CommonOperations.getTranslation("editor.label.testcase.driver") + ": ");
		
		//Details
		JLabel labelDetails = new JLabel( CommonOperations.getTranslation("editor.label.details") + ": ");	
		
		this.add( labelName, fieldName );
		this.add( labelDetails, fieldDetails );
//		this.add( labelDriverTreeSelector, fieldDriverTreeSelector );
		
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
		
/*		}else if( null == fieldDriverTreeSelector.getSelectedDataModel() ){
			errorList.put( 
					fieldDriverTreeSelector,
					MessageFormat.format(
							CommonOperations.getTranslation("editor.errormessage.emptyfield"), 
							"'"+labelDriverTreeSelector.getText()+"'"
					)
			);	
*/			
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
			
				//TestcaseCaseDataModel newTestcaseCase = new TestcaseCaseDataModel( fieldName.getText(), fieldDetails.getText(), fieldDriverTreeSelector.getSelectedDataModel() );				
				TestcaseCaseDataModel newTestcaseCase = new TestcaseCaseDataModel( fieldName.getText(), fieldDetails.getText() );
				nodeForCapture.add( newTestcaseCase );
				
			//Modositas eseten
			}else if( mode.equals(EditMode.MODIFY ) ){

				//Modositja a valtozok erteket
				nodeForModify.setName( fieldName.getText() );
				nodeForModify.setDetails( fieldDetails.getText() );
				//nodeForModify.setDriverDataModel( fieldDriverTreeSelector.getSelectedDataModel() );
			
			}			
			
			//A fa-ban is modositja a nevet (ha az valtozott)
			tree.changed();
		}		
	}
}
