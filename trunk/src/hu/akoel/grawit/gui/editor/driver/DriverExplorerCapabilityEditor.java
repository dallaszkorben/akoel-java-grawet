package hu.akoel.grawit.gui.editor.driver;

import java.awt.Component;
import java.text.MessageFormat;
import java.util.LinkedHashMap;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.driver.DriverExplorerCapabilityDataModel;
import hu.akoel.grawit.core.treenodedatamodel.driver.DriverExplorerDataModel;
import hu.akoel.grawit.core.treenodedatamodel.driver.DriverFirefoxDataModel;
import hu.akoel.grawit.gui.editor.DataEditor;
import hu.akoel.grawit.gui.editors.component.TextAreaComponent;
import hu.akoel.grawit.gui.editors.component.keyvaluepair.KeyValuePairComponent;
import hu.akoel.grawit.gui.tree.Tree;

import javax.swing.JLabel;
import javax.swing.tree.TreeNode;

public class DriverExplorerCapabilityEditor extends DataEditor{

	private static final long serialVersionUID = -1083891382777197092L;
	
	private Tree tree; 
	private DriverExplorerCapabilityDataModel nodeForModify;
	private DriverExplorerDataModel nodeForCapture;
	private EditMode mode;

	private JLabel labelKeyValuePair;
	private KeyValuePairComponent fieldKeyValuePair;
	
	private JLabel labelDetails;
	private TextAreaComponent fieldDetails;
	
	//Itt biztos beszuras van
	public DriverExplorerCapabilityEditor( Tree tree, DriverExplorerDataModel selectedNode ){

		super( DriverFirefoxDataModel.getModelNameToShowStatic());
		
		this.tree = tree;
		this.nodeForCapture = selectedNode;
		this.mode = null;
		
		//Key-Value pair
		fieldKeyValuePair = new KeyValuePairComponent();

		//Details
		fieldDetails = new TextAreaComponent( "", NOTE_ROWS, 15);
		
		common();
		
	}
	
	//Itt lehet hogy modositas vagy megtekintes van
	public DriverExplorerCapabilityEditor( Tree tree, DriverExplorerCapabilityDataModel selectedNode, EditMode mode ){

		super( mode, selectedNode.getNodeTypeToShow());

		this.tree = tree;
		this.nodeForModify = selectedNode;
		this.mode = mode;
		
		//Key-Value pair
		fieldKeyValuePair = new KeyValuePairComponent( selectedNode.getName(), selectedNode.getValue() );
		
		//Details
		fieldDetails = new TextAreaComponent( selectedNode.getDetails(), NOTE_ROWS, 15);
				
		common();
		
	}
	
	private void common(){
		labelKeyValuePair = new JLabel( "");
		labelDetails = new JLabel( CommonOperations.getTranslation("editor.label.details") + ": ");
		
		this.add( labelKeyValuePair, fieldKeyValuePair );
		this.add( labelDetails, fieldDetails );
	}
	
	
	@Override
	public void save() {
		
/*		//Ertekek trimmelese
		fieldKey.setText( fieldKey.getText().trim() );
		fieldValue.setText( fieldValue.getText().trim() );
*/		
		//
		//Hibak eseten a hibas mezok osszegyujtese
		//
		LinkedHashMap<Component, String> errorList = new LinkedHashMap<Component, String>();		
		if( fieldKeyValuePair.getKey().length() == 0 ){
			errorList.put( 
					fieldKeyValuePair,
					MessageFormat.format(
							CommonOperations.getTranslation("editor.errormessage.emptyfield"), 
							"'"+fieldKeyValuePair.getLabelKeyText()+"'"
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
			
			//Megnezi, hogy van-e masik azonos nevu key
			int childrenCount = nodeForSearch.getChildCount();
			for( int i = 0; i < childrenCount; i++ ){
				TreeNode levelNode = nodeForSearch.getChildAt( i );
				
				//Ha Page-rol van szo (Lehetne meg NODE is)
				if( levelNode instanceof DriverExplorerCapabilityDataModel ){
					
					//Ha azonos a nev
					if( ((DriverExplorerCapabilityDataModel) levelNode).getName().equals( fieldKeyValuePair.getKey() ) ){
					
						//Ha rogzites van, vagy ha modositas, de a vizsgalt node kulonbozik a modositott-tol
						if( null == mode || ( mode.equals( EditMode.MODIFY ) && !levelNode.equals(nodeForModify) ) ){
										
							errorList.put( 
									fieldKeyValuePair, 
									MessageFormat.format( 
											CommonOperations.getTranslation("editor.errormessage.duplicateelement"), 
											fieldKeyValuePair.getKey(), 
											CommonOperations.getTranslation("tree.nodetype.driver.firefox.property") 
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
			
				DriverExplorerCapabilityDataModel newFirefoxPropertyDataModel = new DriverExplorerCapabilityDataModel( fieldKeyValuePair.getKey(), fieldKeyValuePair.getValue(), fieldDetails.getText() );				
				nodeForCapture.add( newFirefoxPropertyDataModel );
				
				//A fa-ban modositja a strukturat
				//tree.refreshTreeAfterStructureChanged( nodeForCapture, nodeForCapture );
				tree.refreshTreeAfterStructureChanged( nodeForCapture );

			//Modositas eseten
			}else if( mode.equals(EditMode.MODIFY ) ){
				
				nodeForModify.setName( fieldKeyValuePair.getKey() );
				nodeForModify.setValue( fieldKeyValuePair.getValue() );
				nodeForModify.setDetails( fieldDetails.getText() );
			
				//A fa-ban modositja a nevet (ha az valtozott)
				//tree.refreshTreeAfterChanged( nodeForModify );	
				tree.refreshTreeAfterChanged();
			}
		}
	}
}
