package hu.akoel.grawit.gui.editor;

import java.awt.Component;
import java.text.MessageFormat;
import java.util.LinkedHashMap;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.pages.BasePage;
import hu.akoel.grawit.core.pages.ParamPage;
import hu.akoel.grawit.gui.editor.component.CheckBoxComponent;
import hu.akoel.grawit.gui.editor.component.BasePagePageSelectorComponent;
import hu.akoel.grawit.gui.editor.component.TextFieldComponent;
import hu.akoel.grawit.gui.tree.ParamPageTree;
import hu.akoel.grawit.gui.tree.datamodel.BasePagePageDataModel;
import hu.akoel.grawit.gui.tree.datamodel.BasePageRootDataModel;
import hu.akoel.grawit.gui.tree.datamodel.ParamPageNodeDataModel;
import hu.akoel.grawit.gui.tree.datamodel.ParamPagePageDataModel;

import javax.swing.JLabel;
import javax.swing.tree.TreeNode;

public class ParamPagePageEditor extends DataEditor{
	
	private static final long serialVersionUID = -9038879802467565947L;

	private ParamPageTree tree; 
	private ParamPagePageDataModel nodeForModify;
	private ParamPageNodeDataModel nodeForCapture;
	private BasePageRootDataModel basePageRootDataModel;
	private EditMode mode;
	
	private JLabel labelName;
	private TextFieldComponent fieldName;
	private JLabel labelPageBasePageSelector;
	private BasePagePageSelectorComponent fieldPageBasePageSelector;	
	
	//Itt biztos beszuras van
	public ParamPagePageEditor( ParamPageTree tree, ParamPageNodeDataModel selectedNode, BasePageRootDataModel basePageRootDataModel ){
		super( CommonOperations.getTranslation("tree.nodetype.parampage") );
				
		this.tree = tree;
		this.nodeForCapture = selectedNode;
		this.mode = null;
		
		//Name
		fieldName = new TextFieldComponent( "" );
		
		//BasePage - letrehozasa uresen (nincs kivalasztott PAGEBASE)
		fieldPageBasePageSelector = new BasePagePageSelectorComponent( basePageRootDataModel );		
		
		common( basePageRootDataModel );
		
	}
	
	//Itt lehet hogy modositas vagy megtekintes van
	public ParamPagePageEditor( ParamPageTree tree, ParamPagePageDataModel selectedNode, BasePageRootDataModel basePageRootDataModel, EditMode mode ){
		super( mode, CommonOperations.getTranslation( "tree.nodetype.parampage" ) );

		this.tree = tree;
		this.nodeForModify = selectedNode;
		this.mode = mode;
		
		ParamPage paramPage = selectedNode.getParamPage();
		
		//Name		
		fieldName = new TextFieldComponent( paramPage.getName());
	
		//Az eredetileg kivalasztott PAGE BASE
		BasePage selectedPageBase = paramPage.getBasePage();
		
		//PAGEBASEPAGE SELECTOR COMBO
		fieldPageBasePageSelector =  new BasePagePageSelectorComponent( basePageRootDataModel, selectedPageBase );
		
		common( basePageRootDataModel );
		
	}
	
	private void common( BasePageRootDataModel basePageRootDataModel ){
		
		this.basePageRootDataModel = basePageRootDataModel;
		
		labelName = new JLabel( CommonOperations.getTranslation("editor.title.name") + ": ");
		labelPageBasePageSelector = new JLabel( CommonOperations.getTranslation("editor.title.pagebase") + ": ");
		
		this.add( labelName, fieldName );		
		this.add( labelPageBasePageSelector, fieldPageBasePageSelector );
		
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
		if( null == fieldPageBasePageSelector.getPageBase() ){
			errorList.put( 
					fieldPageBasePageSelector,
					MessageFormat.format(
							CommonOperations.getTranslation("editor.errormessage.emptyfield"), 
							"'"+labelPageBasePageSelector.getText()+"'"
					)
			);
		}
		
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
				if( levelNode instanceof BasePagePageDataModel ){
					
					//Ha azonos a nev
					if( ((ParamPagePageDataModel) levelNode).getParamPage().getName().equals( fieldName.getText() ) ){
					
						//Ha rogzites van, vagy ha modositas, de a vizsgalt node kulonbozik a modositott-tol
						if( null == mode || ( mode.equals( EditMode.MODIFY ) && !levelNode.equals(nodeForModify) ) ){
										
							errorList.put( 
								fieldName, 
								MessageFormat.format( 
										CommonOperations.getTranslation("editor.errormessage.duplicateelement"), 
										fieldName.getText(), 
										CommonOperations.getTranslation("tree.nodetype.parampage") 
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
				
				ParamPage paramPage = new ParamPage( fieldName.getText(), fieldPageBasePageSelector.getPageBase() );				
				ParamPagePageDataModel newParamPagePage = new ParamPagePageDataModel( paramPage, basePageRootDataModel );
				nodeForCapture.add( newParamPagePage );
				
			//Modositas eseten
			}else if( mode.equals(EditMode.MODIFY ) ){
				
				ParamPage paramPage = nodeForModify.getParamPage(); 
				
				paramPage.setName( fieldName.getText() );
//				paramPage.setDetails( fieldDetails.getText() );
			
				//Ebbe a nodba kell majd visszaallni
				//pathToOpen = new TreePath(nodeForModify.getPath());
			}			
			
			//A fa-ban is modositja a nevet (ha az valtozott)
			tree.changed();
		}
	}
}
