package hu.akoel.grawit.gui.editor.param;

import java.awt.Component;
import java.text.MessageFormat;
import java.util.LinkedHashMap;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseCollectorDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamNodeDataModel;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamNormalCollectorDataModel;
import hu.akoel.grawit.gui.editor.DataEditor;
import hu.akoel.grawit.gui.editors.component.TextFieldComponent;
import hu.akoel.grawit.gui.editors.component.treeselector.BaseCollectorTreeSelectorComponent;
import hu.akoel.grawit.gui.tree.Tree;

import javax.swing.JLabel;
import javax.swing.tree.TreeNode;

public class ParamNormalCollectorEditor extends DataEditor{
	
	private static final long serialVersionUID = -9038879802467565947L;

	private Tree tree; 
	private ParamNormalCollectorDataModel nodeForModify;
	private ParamNodeDataModel nodeForCapture;
	private EditMode mode;
	
	private JLabel labelName;
	private TextFieldComponent fieldName;
	private JLabel labelBasePageSelector;
	private BaseCollectorTreeSelectorComponent fieldBasePageSelector;
	private BaseRootDataModel baseRootDataModel;
	
	//Itt biztos beszuras van
	public ParamNormalCollectorEditor( Tree tree, ParamNodeDataModel selectedNode, BaseRootDataModel baseRootDataModel ){

		super( ParamNormalCollectorDataModel.getModelNameToShowStatic());
				
		this.tree = tree;
		this.nodeForCapture = selectedNode;
		this.mode = null;
		
		//Name
		fieldName = new TextFieldComponent( "" );
		
		//BasePage - letrehozasa uresen (nincs kivalasztott PAGEBASE)	
		fieldBasePageSelector = new BaseCollectorTreeSelectorComponent( baseRootDataModel, true );
		this.baseRootDataModel = baseRootDataModel;
		
		common();
		
	}
	
	//Itt lehet hogy modositas vagy megtekintes van
	public ParamNormalCollectorEditor( Tree tree, ParamNormalCollectorDataModel selectedCollector, BaseRootDataModel baseRootDataModel, EditMode mode ){

		super( mode, selectedCollector.getNodeTypeToShow());

		this.tree = tree;
		this.nodeForModify = selectedCollector;
		this.mode = mode;
		
		BaseCollectorDataModel basePage = selectedCollector.getBasePage();
		
		//Name		
		fieldName = new TextFieldComponent( selectedCollector.getName());
		
		//PAGEBASEPAGE SELECTOR COMBO
		fieldBasePageSelector =  new BaseCollectorTreeSelectorComponent( baseRootDataModel, basePage, true );
		this.baseRootDataModel = baseRootDataModel;
		
		common();
		
	}
	
	private void common(){
		
		labelName = new JLabel( CommonOperations.getTranslation("editor.label.name") + ": ");
		labelBasePageSelector = new JLabel( CommonOperations.getTranslation("editor.label.param.basepage") + ": ");
		
		this.add( labelName, fieldName );		
		this.add( labelBasePageSelector, fieldBasePageSelector );
		
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
		}
		
		/*if( null == fieldBasePageSelector.getSelectedDataModel() ){
			errorList.put( 
					fieldBasePageSelector,
					MessageFormat.format(
							CommonOperations.getTranslation("editor.errormessage.emptyfield"), 
							"'"+labelBasePageSelector.getText()+"'"
					)
			);
		}
		*/
		
		//
		//Ha nem volt hiba, akkor rogzitheto
		//
		if( errorList.size() == 0 ){

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
				
				//Ha Page-rol van szo (Lehetne meg NODE is)
				if( levelNode instanceof ParamNormalCollectorDataModel ){
					
					//Ha azonos a nev
					if( ((ParamNormalCollectorDataModel) levelNode).getName().equals( fieldName.getText() ) ){
					
						//Ha rogzites van, vagy ha modositas, de a vizsgalt node kulonbozik a modositott-tol
						if( null == mode || ( mode.equals( EditMode.MODIFY ) && !levelNode.equals(nodeForModify) ) ){
										
							errorList.put( 
								fieldName, 
								MessageFormat.format( 
										CommonOperations.getTranslation("editor.errormessage.duplicateelement"), 
										fieldName.getText(), 
										CommonOperations.getTranslation("tree.nodetype.param.page") 
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
				
				ParamNormalCollectorDataModel newParamPage = new ParamNormalCollectorDataModel( fieldName.getText(), fieldBasePageSelector.getSelectedDataModel() );
				nodeForCapture.add( newParamPage );
				
			//Modositas eseten
			}else if( mode.equals(EditMode.MODIFY ) ){
				
				nodeForModify.setName( fieldName.getText() );

			}			
			
			//A fa-ban is modositja a nevet (ha az valtozott)
			tree.changed();
		}
	}
}
