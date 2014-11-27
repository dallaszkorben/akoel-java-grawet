package hu.akoel.grawit.gui.editor.testcase;

import java.awt.Component;
import java.text.MessageFormat;
import java.util.LinkedHashMap;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.ParamDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamPageSpecificDataModel;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseCaseDataModel;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseParamPageDataModel;
import hu.akoel.grawit.gui.editor.DataEditor;
import hu.akoel.grawit.gui.editors.component.TextAreaComponent;
import hu.akoel.grawit.gui.editors.component.TextFieldComponent;
import hu.akoel.grawit.gui.editors.component.treeselector.ParamPageTreeSelectorComponent;
import hu.akoel.grawit.gui.tree.Tree;

import javax.swing.JLabel;
import javax.swing.tree.TreeNode;

public class TestcaseParamPageEditor extends DataEditor{

	private static final long serialVersionUID = -8169618880309437186L;
	
	private Tree tree;
	private TestcaseParamPageDataModel nodeForModify;
	private TestcaseCaseDataModel nodeForCapture;
	private EditMode mode;
	
	private JLabel labelName;
	private TextFieldComponent fieldName;
	private JLabel labelDetails;
	private TextAreaComponent fieldDetails;
	private JLabel labelParamPageTreeSelector;
	private ParamPageTreeSelectorComponent fieldParamPageTreeSelector;	

	//Itt biztos beszuras van
	public TestcaseParamPageEditor( Tree tree, TestcaseCaseDataModel selectedNode, ParamDataModelAdapter paramDataModel ){
		super( TestcaseParamPageDataModel.getModelNameToShowStatic() );
		
		this.tree = tree;
		this.nodeForCapture = selectedNode;
		this.mode = null;
		
		//Name
		fieldName = new TextFieldComponent( "" );
		
		//Details
		fieldDetails = new TextAreaComponent( "", 5, 15);
		
		//ParamPageTreeSelector
		fieldParamPageTreeSelector = new ParamPageTreeSelectorComponent(paramDataModel);
		
		common();
		
	}
	
	//Itt modositas van
	public TestcaseParamPageEditor( Tree testcaseTree, TestcaseParamPageDataModel selectedNode, ParamDataModelAdapter paramDataModel, EditMode mode ){		
		super( mode, selectedNode.getNodeTypeToShow());

		this.tree = testcaseTree;
		this.nodeForModify = selectedNode;
		this.mode = mode;		
		
		//Name
		fieldName = new TextFieldComponent( selectedNode.getName());
		
		//Details
		fieldDetails = new TextAreaComponent( selectedNode.getDetails(), 5, 15);
		
		//ParamPageTreeSelector
		fieldParamPageTreeSelector = new ParamPageTreeSelectorComponent( paramDataModel, selectedNode.getParamPage() );
				
		common();
	}
	
	private void common(){
		
		//Name
		labelName = new JLabel( CommonOperations.getTranslation("editor.label.name") + ": ");

		//Details
		labelDetails = new JLabel( CommonOperations.getTranslation("editor.label.details") + ": ");	
		
		//Param Page
		labelParamPageTreeSelector = new JLabel( CommonOperations.getTranslation("editor.label.testcase.parampage") + ": ");
		
		
		this.add( labelName, fieldName );
		this.add( labelDetails, fieldDetails );
		this.add( labelParamPageTreeSelector, fieldParamPageTreeSelector );
		
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
		}else if( null == fieldParamPageTreeSelector.getSelectedDataModel() ){
			errorList.put( 
					fieldParamPageTreeSelector,
					MessageFormat.format(
							CommonOperations.getTranslation("editor.errormessage.emptyfield"), 
							"'"+labelParamPageTreeSelector.getText()+"'"
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
				if( levelNode instanceof TestcaseParamPageDataModel ){
					
					//Ha azonos a nev
					if( ((TestcaseParamPageDataModel) levelNode).getName().equals( fieldName.getText() ) ){
						
						//Ha rogzites van, vagy ha modositas, de a vizsgalt node kulonbozik a modositott-tol
						if( null == mode || ( mode.equals( EditMode.MODIFY ) && !levelNode.equals(nodeForModify) ) ){
							
							//Akkor hiba van
							errorList.put( 
								fieldName, 
								MessageFormat.format( 
										CommonOperations.getTranslation("editor.errormessage.duplicateelement"), 
										fieldName.getText(), 
										CommonOperations.getTranslation("tree.nodetype.testcase.page") 
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
			ParamPageSpecificDataModel paramPage = fieldParamPageTreeSelector.getSelectedDataModel();			
			
			//Uj rogzites eseten
			if( null == mode ){
			
				TestcaseParamPageDataModel newTestcasePage = new TestcaseParamPageDataModel( fieldName.getText(), fieldDetails.getText(), paramPage );				
				nodeForCapture.add( newTestcasePage );
				
			//Modositas eseten
			}else if( mode.equals(EditMode.MODIFY ) ){

				//Modositja a valtozok erteket
				nodeForModify.setName( fieldName.getText() );
				nodeForModify.setDetails( fieldDetails.getText() );
				nodeForModify.setParamPage(paramPage);
			
			}			
			
			//A fa-ban is modositja a nevet (ha az valtozott)
			tree.changed();
		}		
	}
}
