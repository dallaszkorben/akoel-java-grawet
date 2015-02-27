package hu.akoel.grawit.gui.editor.constant;

import java.awt.Component;
import java.text.MessageFormat;
import java.util.LinkedHashMap;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.constant.ConstantFolderNodeDataModel;
import hu.akoel.grawit.gui.editor.DataEditor;
import hu.akoel.grawit.gui.editor.DataEditor.EditMode;
import hu.akoel.grawit.gui.editors.component.TextAreaComponent;
import hu.akoel.grawit.gui.editors.component.TextFieldComponent;
import hu.akoel.grawit.gui.tree.VariableTree;

import javax.swing.JLabel;
import javax.swing.tree.TreeNode;

public class ConstantNodeEditor extends DataEditor{

	private static final long serialVersionUID = -6272133454002585188L;
	
	private VariableTree tree;
	private ConstantFolderNodeDataModel nodeForModify;
	private ConstantFolderNodeDataModel nodeForCapture;
	private EditMode mode;
	
	private JLabel labelName;
	private TextFieldComponent fieldName;
	private TextAreaComponent fieldDetails;

	private void common(){
		
		//Name
		labelName = new JLabel( CommonOperations.getTranslation("editor.label.name") + ": ");

		//Details
		JLabel labelDetails = new JLabel( CommonOperations.getTranslation("editor.label.details") + ": ");		
		this.add( labelName, fieldName );
		this.add( labelDetails, fieldDetails );

		
	}
	
	//Itt biztos beszuras van
	public ConstantNodeEditor( VariableTree tree, ConstantFolderNodeDataModel selectedNode ){

		super( ConstantFolderNodeDataModel.getModelNameToShowStatic());
		
		this.tree = tree;
		this.nodeForCapture = selectedNode;
		this.mode = null;
		
		//Name
		fieldName = new TextFieldComponent( "" );
		
		//Details
		fieldDetails = new TextAreaComponent( "", NOTE_ROWS, 15);
		
		common();
		
	}
	
	//Itt modisitas van
	public ConstantNodeEditor( VariableTree variableTree, ConstantFolderNodeDataModel selectedNode, EditMode mode ){		

		super( mode, selectedNode.getNodeTypeToShow());

		this.tree = variableTree;
		this.nodeForModify = selectedNode;
		this.mode = mode;		
		
		//Name
		fieldName = new TextFieldComponent( selectedNode.getName());
		
		//Details
		fieldDetails = new TextAreaComponent( selectedNode.getDetails(), NOTE_ROWS, 15);
		
		common();
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
		}else{

			TreeNode nodeForSearch = null;
			
			if( null == mode ){
				
				nodeForSearch = nodeForCapture;
				
			}else if( mode.equals( EditMode.MODIFY )){
				
				nodeForSearch = nodeForModify.getParent();
				
			}
			
			//Megnezi, hogy a node-ban van-e masik azonos nevu elem
			int childrenCount = nodeForSearch.getChildCount();
			for( int i = 0; i < childrenCount; i++ ){
				TreeNode levelNode = nodeForSearch.getChildAt( i );
				
				//Ha Node-rol van szo
				if( levelNode instanceof ConstantFolderNodeDataModel ){
					
					//Ha azonos a nev
					if( ((ConstantFolderNodeDataModel) levelNode).getName().equals( fieldName.getText() ) ){
						
						//Ha rogzites van, vagy ha modositas, de a vizsgalt node kulonbozik a modositott-tol
						if( null == mode || ( mode.equals( EditMode.MODIFY ) && !levelNode.equals(nodeForModify) ) ){
							
							//Akkor hiba van
							errorList.put( 
								fieldName, 
								MessageFormat.format( 
										CommonOperations.getTranslation("editor.errormessage.duplicateelement"), 
										fieldName.getText(), 
										CommonOperations.getTranslation("tree.nodetype.variable.node") 
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
			
				ConstantFolderNodeDataModel newVariableNode = new ConstantFolderNodeDataModel( fieldName.getText(), fieldDetails.getText() );				
				nodeForCapture.add( newVariableNode );
				
			//Modositas eseten
			}else if( mode.equals(EditMode.MODIFY ) ){

				//Modositja a valtozok erteket
				nodeForModify.setName( fieldName.getText() );
				nodeForModify.setDetails( fieldDetails.getText() );
			
				//Ebbe a nodba kell majd visszaallni
				//pathToOpen = new TreePath(nodeForModify.getPath());
			}			
			
			//A fa-ban is modositja a nevet (ha az valtozott)
			tree.changed();
		}		
	}
}
