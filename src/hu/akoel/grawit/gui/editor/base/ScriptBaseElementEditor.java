package hu.akoel.grawit.gui.editor.base;

import java.awt.Component;
import java.text.MessageFormat;
import java.util.LinkedHashMap;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.BaseDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.BasePageDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.NormalBaseElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.ScriptBaseElementDataModel;
import hu.akoel.grawit.gui.editor.DataEditor;
import hu.akoel.grawit.gui.editors.component.ScriptComponent;
import hu.akoel.grawit.gui.editors.component.TextFieldComponent;
import hu.akoel.grawit.gui.tree.Tree;

import javax.swing.JLabel;
import javax.swing.tree.TreeNode;
import javax.tools.JavaCompiler.CompilationTask;

public class ScriptBaseElementEditor extends DataEditor{
	
	private static final long serialVersionUID = 165396704460481021L;
	
	private Tree tree;
	private ScriptBaseElementDataModel nodeForModify;
	//private BasePageDataModel nodeForCapture;
	private BaseDataModelAdapter nodeForCapture;
	private EditMode mode;
	
	private JLabel labelName;
	private TextFieldComponent fieldName;
	
	private JLabel labelScript;
	private ScriptComponent fieldScript;
	
	//Insert
	//public ScriptBaseElementEditor( Tree tree, BasePageDataModel selectedNode ){
	public ScriptBaseElementEditor( Tree tree, BaseDataModelAdapter selectedNode ){

		super( ScriptBaseElementDataModel.getModelNameToShowStatic());

		this.tree = tree;
		this.nodeForCapture = selectedNode;
		this.mode = null;

		commonPre();
		
		//Name
		fieldName = new TextFieldComponent( "" );
/*		
		//Frame
		fieldFrame = new TextFieldComponent( "" );

		//Identifier
		fieldIdentifier = new TextFieldComponent( "" );
		
    	//WaitingTime
    	fieldWaitingTime = new TextFieldComponent( "" );

		//Identifier type
    	buttonID.setSelected( true );
    	
    	//Element type
    	comboElementType.setSelectedIndex( 0 );
*/    	
		commonPost( "" );
	}
	
	//Modositas vagy View
	public ScriptBaseElementEditor( Tree tree, ScriptBaseElementDataModel selectedNode, EditMode mode ){		

		super( mode, selectedNode.getNodeTypeToShow());

		this.tree = tree;
		this.nodeForModify = selectedNode;
		this.mode = mode;
		
		commonPre();
		
		//Name
		fieldName = new TextFieldComponent( selectedNode.getName() );
/*		
		//Frame
		fieldFrame = new TextFieldComponent( selectedNode.getFrame() );
				
		//Identifier
		fieldIdentifier = new TextFieldComponent( selectedNode.getSelector() );
		
		//Waiting time
		Integer waitingTime = selectedNode.getWaitingTime();
		if( null == waitingTime ){
			fieldWaitingTime = new TextFieldComponent( "" );
		}else{
			fieldWaitingTime = new TextFieldComponent( waitingTime.toString() );
		}
	
		//Identifier type
	    SelectorType idType = selectedNode.getSelectorType();	    
	   	if( idType.equals( SelectorType.ID ) ){
	   		buttonID.setSelected(true);
	   	}else if( idType.equals( SelectorType.CSS ) ){
	   		buttonCSS.setSelected(true);
	   	}
			
	   	//Element type
	   	comboElementType.setSelectedIndex( selectedNode.getElementType().getIndex() );
*/	   	
		commonPost( selectedNode.getScript() );
		
	}
	
	private void commonPre(){
		
	}

	private void commonPost( String script ){
		
		labelName = new JLabel( CommonOperations.getTranslation("editor.label.name") + ": ");
		labelScript = new JLabel( CommonOperations.getTranslation("editor.label.base.script.script") + ": ");

		//Script
		fieldScript = new ScriptComponent( ScriptBaseElementDataModel.getCodePre(), script, ScriptBaseElementDataModel.getCodePost() );	
		
		this.add( labelName, fieldName );
		this.add( labelScript, fieldScript );
		
	}
	
	@Override
	public void save() {
		
		//Az esetleges hibak szamara legyartva
		LinkedHashMap<Component, String> errorList = new LinkedHashMap<Component, String>();

		//Ertekek trimmelese		
		fieldName.setText( fieldName.getText().trim() );
//		fieldIdentifier.setText( fieldIdentifier.getText().trim() );
		
		//
		//Hibak eseten a hibas mezok osszegyujtese
		//
		
		//Empty Name
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

			//Megnezi, hogy van-e masik azonos nevu elem			
			int childrenCount = nodeForSearch.getChildCount();
			for( int i = 0; i < childrenCount; i++ ){
				TreeNode levelNode = nodeForSearch.getChildAt( i );
				
				//Ha Element-rol van szo 
				if( levelNode instanceof NormalBaseElementDataModel ){
					
					//Ha azonos a nev
					if( ((NormalBaseElementDataModel) levelNode).getName().equals( fieldName.getText() ) ){
					
						//Ha rogzites van, vagy ha modositas, de a vizsgalt node kulonbozik a modositott-tol
						if( null == mode || ( mode.equals( EditMode.MODIFY ) && !levelNode.equals(nodeForModify) ) ){
							
							errorList.put( 
								fieldName, 
								MessageFormat.format( 
										CommonOperations.getTranslation("editor.errormessage.duplicateelement"), 
										fieldName.getText(), 
										CommonOperations.getTranslation("tree.nodetype.base.element") 
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
		
			//Akkor eloszor a kod szintaktikai ellenorzese kovetkezik
			ScriptBaseElementDataModel specialBaseElement = new ScriptBaseElementDataModel( fieldName.getText(), fieldScript.getScript() );				
			
			//Kod legyartasa
			CompilationTask task = specialBaseElement.generateTheCode();
			
			//Kod forditasa
			boolean success = specialBaseElement.compileTheCode( task );

			//Ha NEM sikerult a forditas
			if( !success ){
				errorList.put( 
						fieldScript, 
						//MessageFormat.format( 
								CommonOperations.getTranslation("editor.errormessage.formaterrorcustomscript") + "\n\n" + 
								specialBaseElement.getDiagnostic()
								/*, 
								fieldScript.getScript(), 
								CommonOperations.getTranslation("tree.nodetype.special.custom")*/ 
						//) 
				);
			
				//Hibajelzes
				this.errorAt( errorList );
			
			//Hibatlan minden szempontbol
			}else{			
			
				//Uj rogzites eseten
				if( null == mode ){
			
					nodeForCapture.add( specialBaseElement );
				
					//Modositas eseten
				}else if( mode.equals(EditMode.MODIFY ) ){
				
					nodeForModify.setName( fieldName.getText() );
					nodeForModify.setScript( fieldScript.getScript() );
			
				}			
			
				//A fa-ban is modositja a nevet (ha az valtozott)
				tree.changed();
			}			
			
		}
		
	}
	
}
