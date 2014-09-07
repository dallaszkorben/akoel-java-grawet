package hu.akoel.grawit.gui.editor.driver;

import java.awt.Component;
import java.text.MessageFormat;
import java.util.LinkedHashMap;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.driver.DriverFirefoxDataModel;
import hu.akoel.grawit.core.treenodedatamodel.driver.DriverFirefoxPropertyDataModel;
import hu.akoel.grawit.gui.editor.DataEditor;
import hu.akoel.grawit.gui.editors.component.TextFieldComponent;
import hu.akoel.grawit.gui.editors.component.keyvaluepair.KeyValuePairComponent;
import hu.akoel.grawit.gui.tree.Tree;

import javax.swing.JLabel;
import javax.swing.tree.TreeNode;

public class DriverFirefoxPropertyEditor extends DataEditor{

	private static final long serialVersionUID = -4445464302143095963L;
	
	private Tree tree; 
	private DriverFirefoxPropertyDataModel nodeForModify;
	private DriverFirefoxDataModel nodeForCapture;
	private EditMode mode;
	
/*	private JLabel labelKey;
	private TextFieldComponent fieldKey;
	private JLabel labelValue;
	private TextFieldComponent fieldValue;
*/
	
	private KeyValuePairComponent fieldKeyValuePair;
	
	//Itt biztos beszuras van
	public DriverFirefoxPropertyEditor( Tree tree, DriverFirefoxDataModel selectedNode ){

		super( DriverFirefoxDataModel.getModelNameToShowStatic());
		
		this.tree = tree;
		this.nodeForCapture = selectedNode;
		this.mode = null;
		
		fieldKeyValuePair = new KeyValuePairComponent();

		common();
		
	}
	
	//Itt lehet hogy modositas vagy megtekintes van
	public DriverFirefoxPropertyEditor( Tree tree, DriverFirefoxPropertyDataModel selectedNode, EditMode mode ){

		super( mode, selectedNode.getModelNameToShow());

		this.tree = tree;
		this.nodeForModify = selectedNode;
		this.mode = mode;
		
		fieldKeyValuePair = new KeyValuePairComponent( selectedNode.getName(), selectedNode.getValue() );
		
		common();
		
	}
	
	private void common(){
		JLabel label = new JLabel( "");
		
		this.add( label, fieldKeyValuePair );
	}
	
	
	@Override
	public void save() {
		
		//Ertekek trimmelese
//		fieldKey.setText( fieldKey.getText().trim() );
//		fieldValue.setText( fieldValue.getText().trim() );
		
		//
		//Hibak eseten a hibas mezok osszegyujtese
		//
		LinkedHashMap<Component, String> errorList = new LinkedHashMap<Component, String>();		
		if( false ){//fieldKey.getText().length() == 0 ){
/*			errorList.put( 
					fieldKey,
					MessageFormat.format(
							CommonOperations.getTranslation("editor.errormessage.emptyfield"), 
							"'"+labelKey.getText()+"'"
					)
			);
*/		}else{

			TreeNode nodeForSearch = null;

			//CAPTURE
			if( null == mode){
				
				nodeForSearch = nodeForCapture;
				
			//MODIFY
			}else if( mode.equals( EditMode.MODIFY )){
				
				nodeForSearch = nodeForModify.getParent();
				
			}
			
			//Megnezi, hogy van-e masik azonos nevu key
/*			int childrenCount = nodeForSearch.getChildCount();
			for( int i = 0; i < childrenCount; i++ ){
				TreeNode levelNode = nodeForSearch.getChildAt( i );
				
				//Ha Page-rol van szo (Lehetne meg NODE is)
				if( levelNode instanceof DriverFirefoxPropertyDataModel ){
					
					//Ha azonos a nev
					if( ((DriverFirefoxPropertyDataModel) levelNode).getName().equals( fieldKey.getText() ) ){
					
						//Ha rogzites van, vagy ha modositas, de a vizsgalt node kulonbozik a modositott-tol
						if( null == mode || ( mode.equals( EditMode.MODIFY ) && !levelNode.equals(nodeForModify) ) ){
										
							errorList.put( 
								fieldKey, 
								MessageFormat.format( 
										CommonOperations.getTranslation("editor.errormessage.duplicateelement"), 
										fieldKey.getText(), 
										CommonOperations.getTranslation("tree.nodetype.driver.firefox.property") 
								) 
							);
							break;
						}
					}
				}
			}
*/			
		}
		
		//Volt hiba
		if( errorList.size() != 0 ){
			
			//Hibajelzes
			this.errorAt( errorList );
		
		//Ha nem volt hiba akkor a valtozok veglegesitese
		}else{

			//Uj rogzites eseten
			if( null == mode ){
			
				DriverFirefoxPropertyDataModel newFirefoxPropertyDataModel = new DriverFirefoxPropertyDataModel( fieldKeyValuePair.getKey(), fieldKeyValuePair.getValue() );				
				nodeForCapture.add( newFirefoxPropertyDataModel );
				
			//Modositas eseten
			}else if( mode.equals(EditMode.MODIFY ) ){
				
				nodeForModify.setName( fieldKeyValuePair.getKey() );
				nodeForModify.setValue( fieldKeyValuePair.getValue() );
			
			}			
			
			//A fa-ban is modositja a nevet (ha az valtozott)
			tree.changed();
		}
	}
}
