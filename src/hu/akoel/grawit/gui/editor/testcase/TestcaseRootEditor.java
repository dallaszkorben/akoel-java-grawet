package hu.akoel.grawit.gui.editor.testcase;

import java.awt.Component;
import java.text.MessageFormat;
import java.util.LinkedHashMap;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.DriverDataModelInterface;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseRootDataModel;
import hu.akoel.grawit.gui.editor.DataEditor;
import hu.akoel.grawit.gui.editors.component.TextAreaComponent;
import hu.akoel.grawit.gui.editors.component.treeselector.DriverTreeSelectorComponent;
import hu.akoel.grawit.gui.tree.Tree;

import javax.swing.JLabel;

public class TestcaseRootEditor extends DataEditor{

	private static final long serialVersionUID = 2644128362590221646L;
	
	private Tree tree;
	private TestcaseRootDataModel nodeForModify;
	private TestcaseRootDataModel nodeForCapture;
	private EditMode mode;
	
	private TextAreaComponent fieldDetails;
	
	private JLabel labelDriverTreeSelector;
	private DriverTreeSelectorComponent fieldDriverTreeSelector;
/*
	//Itt biztos beszuras van
	public TestcaseRootEditor( Tree tree, TestcaseNodeDataModel selectedNode ){
		super( TestcaseNodeDataModel.getModelNameToShowStatic() );
		
		this.tree = tree;
		this.nodeForCapture = selectedNode;
		this.mode = null;
		
		//Name
		fieldName = new TextFieldComponent( "" );
		
		//Details
		fieldDetails = new TextAreaComponent( "", 5, 15);
		
		common();
		
	}
*/	
	//Itt modositas van
	public TestcaseRootEditor( Tree tree, TestcaseRootDataModel selectedNode, DriverDataModelInterface driverDataModel, EditMode mode ){		
		super( mode, selectedNode.getNodeTypeToShow());

		this.tree = tree;
		this.nodeForModify = selectedNode;
		this.mode = mode;		
		
		//DriverTreeSelector
		fieldDriverTreeSelector = new DriverTreeSelectorComponent( driverDataModel, selectedNode.getDriverDataModel() );
		labelDriverTreeSelector = new JLabel( CommonOperations.getTranslation("editor.label.testcase.driver") + ": ");		
		
		//Details
		fieldDetails = new TextAreaComponent( selectedNode.getDetails(), NOTE_ROWS, 15);
		
		common();
	}
	
	private void common(){
		
		//Name
		//JLabel labelName = new JLabel( CommonOperations.getTranslation("editor.label.name") + ": ");

		//Details
		JLabel labelDetails = new JLabel( CommonOperations.getTranslation("editor.label.details") + ": ");		
		//this.add( labelName, fieldName );
		this.add( labelDriverTreeSelector, fieldDriverTreeSelector );
		this.add( labelDetails, fieldDetails );		
	}
	
	@Override
	public void save() {

		//Ertekek trimmelese
		//fieldName.setText( fieldName.getText().trim() );
		fieldDetails.setText( fieldDetails.getText().trim() );
		
		//
		//Hibak eseten a hibas mezok osszegyujtese
		//		
		LinkedHashMap<Component, String> errorList = new LinkedHashMap<Component, String>();	
		if( null == fieldDriverTreeSelector.getSelectedDataModel() ){
			errorList.put( 
					fieldDriverTreeSelector,
					MessageFormat.format(
							CommonOperations.getTranslation("editor.errormessage.emptyfield"), 
							"'"+labelDriverTreeSelector.getText()+"'"
					)
			);
		
/*		if( fieldName.getText().length() == 0 ){
			errorList.put( 
					fieldName,
					MessageFormat.format(
							CommonOperations.getTranslation("editor.errormessage.emptyfield"), 
							"'"+labelName.getText()+"'"
					)
			);
*/			
		}else{

/*			TreeNode nodeForSearch = null;
			
			if( null == mode ){
				
				nodeForSearch = nodeForCapture;
				
			}else if( mode.equals( EditMode.MODIFY )){
				
				nodeForSearch = nodeForModify.getParent();
				
			}
*/			
/*			
			//Megnezi, hogy a node-ban van-e masik azonos nevu elem
			int childrenCount = nodeForSearch.getChildCount();
			for( int i = 0; i < childrenCount; i++ ){
				TreeNode levelNode = nodeForSearch.getChildAt( i );
				
				//Ha Node-rol van szo
				if( levelNode instanceof TestcaseNodeDataModel ){
					
					//Ha azonos a nev
					if( ((TestcaseNodeDataModel) levelNode).getName().equals( fieldName.getText() ) ){
						
						//Ha rogzites van, vagy ha modositas, de a vizsgalt node kulonbozik a modositott-tol
						if( null == mode || ( mode.equals( EditMode.MODIFY ) && !levelNode.equals(nodeForModify) ) ){
							
							//Akkor hiba van
							errorList.put( 
								fieldName, 
								MessageFormat.format( 
										CommonOperations.getTranslation("editor.errormessage.duplicateelement"), 
										fieldName.getText(), 
										CommonOperations.getTranslation("tree.nodetype.testcase.node") 
								) 
							);	
							break;
						}
					}
				}
			//}
		}
*/		
		}
		//Ha volt hiba
		if( errorList.size() != 0 ){
			
			//Hibajelzes
			this.errorAt( errorList );
		
		//Ha nem volt hiba akkor a valtozok veglegesitese
		}else{

			//TreePath pathToOpen = null;
			
//			//Uj rogzites eseten
//			if( null == mode ){
			
//				TestcaseNodeDataModel newTestcaseNode = new TestcaseNodeDataModel( fieldName.getText(), fieldDetails.getText() );				
//				nodeForCapture.add( newTestcaseNode );
				
//			//Modositas eseten
//			}else if( mode.equals(EditMode.MODIFY ) ){

				//Modositja a valtozok erteket
				//nodeForModify.setName( fieldName.getText() );
				nodeForModify.setDetails( fieldDetails.getText() );
				nodeForModify.setDriverDataModel( fieldDriverTreeSelector.getSelectedDataModel() );
			
//			}			
			
			//A fa-ban is modositja a nevet (ha az valtozott)
			tree.changed();
		}		
	}
}
