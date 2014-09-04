package hu.akoel.grawit.gui.editor.testcase;

import java.awt.Component;
import java.text.MessageFormat;
import java.util.LinkedHashMap;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.SpecialDataModelInterface;
import hu.akoel.grawit.core.treenodedatamodel.special.SpecialCustomDataModel;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseCaseDataModel;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseCustomDataModel;
import hu.akoel.grawit.gui.editor.DataEditor;
import hu.akoel.grawit.gui.editors.component.TextAreaComponent;
import hu.akoel.grawit.gui.editors.component.TextFieldComponent;
import hu.akoel.grawit.gui.editors.component.selector.SpecialCustomTreeSelectorComponent;
import hu.akoel.grawit.gui.tree.Tree;

import javax.swing.JLabel;
import javax.swing.tree.TreeNode;

//TODO custom editort meg kell szerkesztenem. nem is nyultam meg hozza

public class TestcaseCustomPageEditor extends DataEditor{

	private static final long serialVersionUID = -8169618880309437186L;
	
	private Tree tree;
	private TestcaseCustomDataModel nodeForModify;
	private TestcaseCaseDataModel nodeForCapture;
	private EditMode mode;
	
	private JLabel labelName;
	private TextFieldComponent fieldName;
	private JLabel labelDetails;
	private TextAreaComponent fieldDetails;
	private JLabel labelSpecialTreeSelector;
	private SpecialCustomTreeSelectorComponent specialCustomTreeSelector;

	//Itt biztos beszuras van
	public TestcaseCustomPageEditor( Tree tree, TestcaseCaseDataModel selectedNode, SpecialDataModelInterface specialDataModel ){

		super( TestcaseCustomDataModel.getModelNameToShowStatic() );
		
		this.tree = tree;
		this.nodeForCapture = selectedNode;
		this.mode = null;
		
		//Name
		fieldName = new TextFieldComponent( "" );
		
		//Details
		fieldDetails = new TextAreaComponent( "", 5, 15);
		
		//ParamPageTreeSelector
		specialCustomTreeSelector = new SpecialCustomTreeSelectorComponent(specialDataModel);
		
		common();
		
	}
	
	//Itt modositas van
	public TestcaseCustomPageEditor( Tree tree, TestcaseCustomDataModel selectedNode, SpecialDataModelInterface paramDataModel, EditMode mode ){		
		super( mode, selectedNode.getModelNameToShow());

		this.tree = tree;
		this.nodeForModify = selectedNode;
		this.mode = mode;		
		
		//Name
		fieldName = new TextFieldComponent( selectedNode.getName());
		
		//Details
		fieldDetails = new TextAreaComponent( selectedNode.getDetails(), 5, 15);
		
		//ParamPageTreeSelector
		specialCustomTreeSelector = new SpecialCustomTreeSelectorComponent( paramDataModel, selectedNode.getCustomPage() );
				
		common();
	}
	
	private void common(){
		
		//Name
		labelName = new JLabel( CommonOperations.getTranslation("editor.label.name") + ": ");

		//Details
		labelDetails = new JLabel( CommonOperations.getTranslation("editor.label.details") + ": ");	
		
		//Param Page
		labelSpecialTreeSelector = new JLabel( CommonOperations.getTranslation("editor.label.testcase.custompage") + ": ");
		
		
		this.add( labelName, fieldName );
		this.add( labelDetails, fieldDetails );
		this.add( labelSpecialTreeSelector, specialCustomTreeSelector );
		
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
		}else if( null == specialCustomTreeSelector.getSelectedDataModel() ){
			errorList.put( 
					specialCustomTreeSelector,
					MessageFormat.format(
							CommonOperations.getTranslation("editor.errormessage.emptyfield"), 
							"'"+labelSpecialTreeSelector.getText()+"'"
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
				if( levelNode instanceof SpecialDataModelInterface ){
					
					//Ha azonos a nev
					if( ((SpecialDataModelInterface) levelNode).getName().equals( fieldName.getText() ) ){
						
						//Ha rogzites van, vagy ha modositas, de a vizsgalt node kulonbozik a modositott-tol
						if( null == mode || ( mode.equals( EditMode.MODIFY ) && !levelNode.equals(nodeForModify) ) ){
							
							//Akkor hiba van
							errorList.put( 
								fieldName, 
								MessageFormat.format( 
										CommonOperations.getTranslation("editor.errormessage.duplicateelement"), 
										fieldName.getText(), 
										CommonOperations.getTranslation(TestcaseCustomDataModel.getModelNameToShowStatic() ) 
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

			//A kivalasztott Special page
			SpecialCustomDataModel specialCustomPage = specialCustomTreeSelector.getSelectedDataModel();			
			
			//Uj rogzites eseten
			if( null == mode ){
			
				TestcaseCustomDataModel newTestcasePage = new TestcaseCustomDataModel( fieldName.getText(), fieldDetails.getText(), specialCustomPage );				
				nodeForCapture.add( newTestcasePage );
				
			//Modositas eseten
			}else if( mode.equals(EditMode.MODIFY ) ){

				//Modositja a valtozok erteket
				nodeForModify.setName( fieldName.getText() );
				nodeForModify.setDetails( fieldDetails.getText() );
				nodeForModify.setCustomPage(specialCustomPage);
			
			}			
			
			//A fa-ban is modositja a nevet (ha az valtozott)
			tree.changed();
		}		
	}
}
