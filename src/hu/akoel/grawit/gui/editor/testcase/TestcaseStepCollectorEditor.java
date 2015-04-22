package hu.akoel.grawit.gui.editor.testcase;

import java.awt.Component;
import java.text.MessageFormat;
import java.util.LinkedHashMap;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.step.StepCollectorDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.step.StepDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.step.StepNormalCollectorDataModel;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseCaseDataModel;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseStepCollectorDataModel;
import hu.akoel.grawit.gui.editor.DataEditor;
import hu.akoel.grawit.gui.editors.component.TextAreaComponent;
import hu.akoel.grawit.gui.editors.component.TextFieldComponent;
import hu.akoel.grawit.gui.editors.component.treeselector.BaseElementTreeSelectorComponent;
import hu.akoel.grawit.gui.editors.component.treeselector.StepCollectorTreeSelectorComponent;
import hu.akoel.grawit.gui.tree.Tree;

import javax.swing.JLabel;
import javax.swing.tree.TreeNode;

public class TestcaseStepCollectorEditor extends DataEditor{

	private static final long serialVersionUID = -8169618880309437186L;
	
	private Tree tree;
	private TestcaseStepCollectorDataModel nodeForModify;
	private TestcaseDataModelAdapter nodeForCapture;
	private EditMode mode;
	
	private JLabel labelName;
	private TextFieldComponent fieldName;
	private JLabel labelDetails;
	private TextAreaComponent fieldDetails;
	private JLabel labelStepCollectorTreeSelector;
	private StepCollectorTreeSelectorComponent fieldStepCollectorTreeSelector;	

	//Itt biztos beszuras van
	public TestcaseStepCollectorEditor( Tree tree, TestcaseCaseDataModel selectedNode, StepDataModelAdapter stepDataModel ){
		super( TestcaseStepCollectorDataModel.getModelNameToShowStatic() );
		
		this.tree = tree;
		this.nodeForCapture = selectedNode;
		this.mode = null;
		
		//Name
		fieldName = new TextFieldComponent( "" );
		
		//Details
		fieldDetails = new TextAreaComponent( "", NOTE_ROWS, 15);
		
		//ParamPageTreeSelector
		//fieldParamPageTreeSelector = new ParamPageTreeSelectorComponent(paramDataModel);
		StepCollectorDataModelAdapter lastStepCollector = selectedNode.getLastStepCollector();
		fieldStepCollectorTreeSelector = new StepCollectorTreeSelectorComponent( stepDataModel, lastStepCollector, false );		
		
		common();
		
	}
	
	//Itt modositas van
	public TestcaseStepCollectorEditor( Tree testcaseTree, TestcaseStepCollectorDataModel selectedNode, StepDataModelAdapter paramDataModel, EditMode mode ){		
		super( mode, selectedNode.getNodeTypeToShow());

		this.tree = testcaseTree;
		this.nodeForModify = selectedNode;
		this.mode = mode;		
		
		//Name
		fieldName = new TextFieldComponent( selectedNode.getName());
		
		//Details
		fieldDetails = new TextAreaComponent( selectedNode.getDetails(), NOTE_ROWS, 15);
		
		//ParamPageTreeSelector
		fieldStepCollectorTreeSelector = new StepCollectorTreeSelectorComponent( paramDataModel, selectedNode.getParamPage() );
				
		common();
	}
	
	private void common(){
		
		//Name
		labelName = new JLabel( CommonOperations.getTranslation("editor.label.name") + ": ");

		//Details
		labelDetails = new JLabel( CommonOperations.getTranslation("editor.label.details") + ": ");	
		
		//Param Page
		labelStepCollectorTreeSelector = new JLabel( CommonOperations.getTranslation("editor.label.testcase.stepcollector") + ": ");
		
		
		this.add( labelName, fieldName );
		this.add( labelDetails, fieldDetails );
		this.add( labelStepCollectorTreeSelector, fieldStepCollectorTreeSelector );
		
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
		
		//Ha nincs nev megadva
		if( fieldName.getText().length() == 0 ){
			errorList.put( 
					fieldName,
					MessageFormat.format(
							CommonOperations.getTranslation("editor.errormessage.emptyfield"), 
							"'"+labelName.getText()+"'"
					)
			);
			
		//Ha nincs ParamPage kivalasztva
		}else if( null == fieldStepCollectorTreeSelector.getSelectedDataModel() ){
			errorList.put( 
					fieldStepCollectorTreeSelector,
					MessageFormat.format(
							CommonOperations.getTranslation("editor.errormessage.emptyfield"), 
							"'"+labelStepCollectorTreeSelector.getText()+"'"
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
				if( levelNode instanceof TestcaseStepCollectorDataModel ){
					
					//Ha azonos a nev
					if( ((TestcaseStepCollectorDataModel) levelNode).getName().equals( fieldName.getText() ) ){
						
						//Ha rogzites van, vagy ha modositas, de a vizsgalt node kulonbozik a modositott-tol
						if( null == mode || ( mode.equals( EditMode.MODIFY ) && !levelNode.equals(nodeForModify) ) ){
							
							//Akkor hiba van
							errorList.put( 
								fieldName, 
								MessageFormat.format( 
										CommonOperations.getTranslation("editor.errormessage.duplicateelement"), 
										fieldName.getText(), 
										CommonOperations.getTranslation("tree.nodetype.testcase.collector") 
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

			//A kivalasztott paramPage
			StepCollectorDataModelAdapter paramCollector = fieldStepCollectorTreeSelector.getSelectedDataModel();			
			
			//Uj rogzites eseten
			if( null == mode ){
			
				TestcaseStepCollectorDataModel newTestcaseParamContainer = new TestcaseStepCollectorDataModel( fieldName.getText(), fieldDetails.getText(), paramCollector );				
				nodeForCapture.add( newTestcaseParamContainer );
				
				//A new ParamElementDataModel()-ben nem vegrehajthato, mert akkor meg nincs a tree-hez rendelve es igy nincs szuloje
				newTestcaseParamContainer.setParamCollector(paramCollector);
				
				//A fa-ban modositja a strukturat
				//tree.refreshTreeAfterStructureChanged( nodeForCapture, nodeForCapture );
				tree.refreshTreeAfterStructureChanged( nodeForCapture );
				
			//Modositas eseten
			}else if( mode.equals(EditMode.MODIFY ) ){

				//Modositja a valtozok erteket
				nodeForModify.setName( fieldName.getText() );
				nodeForModify.setDetails( fieldDetails.getText() );
				nodeForModify.setParamCollector(paramCollector);
			
				//A fa-ban modositja a nevet (ha az valtozott)
				//tree.refreshTreeAfterChanged( nodeForModify );
				tree.refreshTreeAfterChanged();
				
			}			
		}		
	}
}
