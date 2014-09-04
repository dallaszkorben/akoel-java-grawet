package hu.akoel.grawit.gui.editor.special;

import java.awt.Component;
import java.text.MessageFormat;
import java.util.LinkedHashMap;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.special.SpecialCloseDataModel;
import hu.akoel.grawit.core.treenodedatamodel.special.SpecialCustomDataModel;
import hu.akoel.grawit.core.treenodedatamodel.special.SpecialNodeDataModel;
import hu.akoel.grawit.gui.editor.DataEditor;
import hu.akoel.grawit.gui.editors.component.TextAreaComponent;
import hu.akoel.grawit.gui.editors.component.TextFieldComponent;
import hu.akoel.grawit.gui.tree.Tree;

import javax.swing.JLabel;
import javax.swing.tree.TreeNode;
import javax.tools.JavaCompiler.CompilationTask;

public class SpecialCustomEditor extends DataEditor{

	private static final long serialVersionUID = 157539958460178584L;
	
	private static final int ROWS = 10;
	private static final int COLUMNS = 20;

	private Tree tree; 
	private SpecialCustomDataModel nodeForModify;
	private SpecialNodeDataModel nodeForCapture;
	private EditMode mode;
	
	private JLabel labelName;
	private TextFieldComponent fieldName;
	
	private JLabel labelScript;
	private TextAreaComponent fieldScript;
	
	//Itt biztos beszuras van
	public SpecialCustomEditor( Tree tree, SpecialNodeDataModel selectedNode ){

		super( SpecialCustomDataModel.getModelNameToShowStatic());
		
		this.tree = tree;
		this.nodeForCapture = selectedNode;
		this.mode = null;
		
		//Name
		fieldName = new TextFieldComponent( "" );
		
		//Script
		fieldScript = new TextAreaComponent( "", ROWS, COLUMNS );

		common();
		
	}
	
	//Itt lehet hogy modositas vagy megtekintes van
	public SpecialCustomEditor( Tree tree, SpecialCustomDataModel selectedNode, EditMode mode ){

		super( mode, selectedNode.getModelNameToShow());

		this.tree = tree;
		this.nodeForModify = selectedNode;
		this.mode = mode;
		
		//Name		
		fieldName = new TextFieldComponent( selectedNode.getName());
		
		//Script
		fieldScript = new TextAreaComponent( selectedNode.getScript(), ROWS, COLUMNS );
		
		common();
		
	}
	
	private void common(){
		labelName = new JLabel( CommonOperations.getTranslation("editor.label.name") + ": ");
		labelScript = new JLabel( CommonOperations.getTranslation("editor.label.special.script") + ": ");
		
		this.add( labelName, fieldName );
		this.add( labelScript, fieldScript );

	}
	
	
	@Override
	public void save() {
		
		//Ertekek trimmelese
		fieldName.setText( fieldName.getText().trim() );
		
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
				
		}else if( fieldScript.getText().length() == 0 ){
			errorList.put( 
					fieldScript,
					MessageFormat.format(
							CommonOperations.getTranslation("editor.errormessage.emptyfield"), 
							"'"+labelScript.getText()+"'"
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
				
				//Ha Close-rol van szo (Lehetne meg Node/Page/Close is) TODO 
				if( levelNode instanceof SpecialCustomDataModel ){
					
					//Ha azonos a nev
					if( ((SpecialCustomDataModel) levelNode).getName().equals( fieldName.getText() ) ){
					
						//Ha rogzites van, vagy ha modositas, de a vizsgalt node kulonbozik a modositott-tol
						if( null == mode || ( mode.equals( EditMode.MODIFY ) && !levelNode.equals(nodeForModify) ) ){
										
							errorList.put( 
								fieldName, 
								MessageFormat.format( 
										CommonOperations.getTranslation("editor.errormessage.duplicateelement"), 
										fieldName.getText(), 
										CommonOperations.getTranslation("tree.nodetype.special.open") 
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
			
			SpecialCustomDataModel customDataModel = new SpecialCustomDataModel( fieldName.getText(), fieldScript.getText() );				
			
			//Kod legyartasa
			CompilationTask task = customDataModel.generateTheCode();
			
			//Kod forditasa
			boolean success = customDataModel.compileTheCode( task );

			//Ha NEM sikerult a forditas
			if( !success ){
				errorList.put( 
						fieldScript, 
						MessageFormat.format( 
								CommonOperations.getTranslation("editor.errormessage.formaterrorcustomscript") + "\n\n" + 
								customDataModel.getDiagnostic(), 
								fieldScript.getText(), 
								CommonOperations.getTranslation("tree.nodetype.special.custom") 
						) 
				);
				
				//Hibajelzes
				this.errorAt( errorList );
				return;
			}					
			
			//Uj rogzites eseten
			if( null == mode ){
			
				SpecialCustomDataModel newSpecial = new SpecialCustomDataModel( fieldName.getText(), fieldScript.getText() );				
				nodeForCapture.add( newSpecial );

				
			//Modositas eseten
			}else if( mode.equals(EditMode.MODIFY ) ){
				
				nodeForModify.setName( fieldName.getText() );
				nodeForModify.setScript( fieldScript.getText() );
			
			}			
			
			//Ellenorzom, hogy jo-e a kod
			
			
			
			//A fa-ban is modositja a nevet (ha az valtozott)
			tree.changed();
		}
	}
}
