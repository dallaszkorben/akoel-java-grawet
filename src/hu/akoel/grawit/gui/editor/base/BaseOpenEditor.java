package hu.akoel.grawit.gui.editor.base;

import java.awt.Component;
import java.text.MessageFormat;
import java.util.LinkedHashMap;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseNodeDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseOpenDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.BasePageDataModel;
import hu.akoel.grawit.gui.editor.DataEditor;
import hu.akoel.grawit.gui.editor.DataEditor.EditMode;
import hu.akoel.grawit.gui.editors.component.TextAreaComponent;
import hu.akoel.grawit.gui.editors.component.TextFieldComponent;
import hu.akoel.grawit.gui.tree.Tree;

import javax.swing.JLabel;
import javax.swing.tree.TreeNode;

public class BaseOpenEditor extends DataEditor{

	private static final long serialVersionUID = 788757736294718359L;
	
	private Tree tree; 
	private BaseOpenDataModel nodeForModify;
	private BaseNodeDataModel nodeForCapture;
	private EditMode mode;
	
	private JLabel labelName;
	private TextFieldComponent fieldName;
	private JLabel labelURL;
	private TextFieldComponent fieldURL;
	
	//Itt biztos beszuras van
	public BaseOpenEditor( Tree tree, BaseNodeDataModel selectedNode ){

		super( BaseOpenDataModel.getModelNameToShowStatic());
		
		this.tree = tree;
		this.nodeForCapture = selectedNode;
		this.mode = null;
		
		//Name
		fieldName = new TextFieldComponent( "" );
		
		//Details
		fieldURL = new TextFieldComponent( "" );

		common();
		
	}
	
	//Itt lehet hogy modositas vagy megtekintes van
	public BaseOpenEditor( Tree tree, BaseOpenDataModel selectedNode, EditMode mode ){

		super( mode, selectedNode.getModelNameToShow());

		this.tree = tree;
		this.nodeForModify = selectedNode;
		this.mode = mode;
		
		//Name		
		fieldName = new TextFieldComponent( selectedNode.getName());
			
		//Details
		fieldURL = new TextFieldComponent( selectedNode.getURL() );
		
		common();
		
	}
	
	private void common(){
		labelName = new JLabel( CommonOperations.getTranslation("editor.label.name") + ": ");
		labelURL = new JLabel( CommonOperations.getTranslation("editor.label.url") + ": ");
		
		this.add( labelName, fieldName );
		this.add( labelURL, fieldURL );
	}
	
	
	@Override
	public void save() {
		
		//Ertekek trimmelese
		fieldName.setText( fieldName.getText().trim() );
		fieldURL.setText( fieldURL.getText().trim() );
		
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
			
		}else if( fieldURL.getText().length() == 0 ){
			errorList.put( 
					fieldURL,
					MessageFormat.format(
							CommonOperations.getTranslation("editor.errormessage.emptyfield"), 
							"'"+labelName.getText()+"'"
					)
			);
			
		}else{

			TreeNode nodeForSearch = null;

			//CAPTURE
			if( null == mode){
				
				nodeForSearch = nodeForCapture;
				
			//MODIFY
			}else if( mode.equals( EditMode.MODIFY )){
				
				nodeForSearch = nodeForModify.getParent();
				
			}
			
			//Megnezi, hogy van-e masik azonos nevu elem
			int childrenCount = nodeForSearch.getChildCount();
			for( int i = 0; i < childrenCount; i++ ){
				TreeNode levelNode = nodeForSearch.getChildAt( i );
				
				//Ha Open-rol van szo (Lehetne meg Nod/Page is) TODO 
				if( levelNode instanceof BaseOpenDataModel ){
					
					//Ha azonos a nev
					if( ((BaseOpenDataModel) levelNode).getName().equals( fieldName.getText() ) ){
					
						//Ha rogzites van, vagy ha modositas, de a vizsgalt node kulonbozik a modositott-tol
						if( null == mode || ( mode.equals( EditMode.MODIFY ) && !levelNode.equals(nodeForModify) ) ){
										
							errorList.put( 
								fieldName, 
								MessageFormat.format( 
										CommonOperations.getTranslation("editor.errormessage.duplicateelement"), 
										fieldName.getText(), 
										CommonOperations.getTranslation("tree.nodetype.baseopen") 
								) 
							);
							break;
						}
					}
				}
			}
		}
		
		//Volt hiba
		if( errorList.size() != 0 ){
			
			//Hibajelzes
			this.errorAt( errorList );
		
		//Ha nem volt hiba akkor a valtozok veglegesitese
		}else{
			
			//Uj rogzites eseten
			if( null == mode ){
			
				BaseOpenDataModel newBasePage = new BaseOpenDataModel( fieldName.getText(), fieldURL.getText() );				
				nodeForCapture.add( newBasePage );
				
			//Modositas eseten
			}else if( mode.equals(EditMode.MODIFY ) ){
				
				nodeForModify.setName( fieldName.getText() );
				nodeForModify.setURL( fieldURL.getText() );
			
			}			
			
			//A fa-ban is modositja a nevet (ha az valtozott)
			tree.changed();
		}
	}
}
