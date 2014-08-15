package hu.akoel.grawit.gui.editor;

import java.awt.Component;
import java.text.MessageFormat;
import java.util.LinkedHashMap;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.pages.PageBase;
import hu.akoel.grawit.core.pages.ParamPage;
import hu.akoel.grawit.gui.GUIFrame;
import hu.akoel.grawit.gui.container.TreeSelectionCombo;
import hu.akoel.grawit.gui.tree.PageBaseTree;
import hu.akoel.grawit.gui.tree.ParamPageTree;
import hu.akoel.grawit.gui.tree.datamodel.PageBaseNodeDataModel;
import hu.akoel.grawit.gui.tree.datamodel.PageBasePageDataModel;
import hu.akoel.grawit.gui.tree.datamodel.PageBaseRootDataModel;
import hu.akoel.grawit.gui.tree.datamodel.ParamPageNodeDataModel;
import hu.akoel.grawit.gui.tree.datamodel.ParamPagePageDataModel;
import hu.akoel.grawit.gui.tree.datamodel.ParamPageRootDataModel;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.tree.TreeNode;

public class ParamPagePageEditor extends DataEditor{
	
	private static final long serialVersionUID = -9038879802467565947L;

	private ParamPageTree tree; 
	private ParamPagePageDataModel nodeForModify;
	private ParamPageNodeDataModel nodeForCapture;
	private EditMode mode;
	
	private JLabel labelName;
	private JTextField fieldName;
//	private JLabel labelDetails;
	private JLabel labelBasePagePath;
	private TreeSelectionCombo fieldBasePagePath;
//	private JTextArea fieldDetails;
	
	//Itt biztos beszuras van
	public ParamPagePageEditor( GUIFrame parent, ParamPageTree tree, ParamPageNodeDataModel selectedNode, PageBaseRootDataModel pageBaseRootDataModel ){
		super( CommonOperations.getTranslation("tree.nodetype.parampage") );
				
		this.tree = tree;
		this.nodeForCapture = selectedNode;
		this.mode = null;
		
		//Name
		fieldName = new JTextField( "" );
		
		//BasePage
		fieldBasePagePath = new TreeSelectionCombo( parent, pageBaseRootDataModel );

		common();
		
	}
	
	//Itt lehet hogy modositas vagy megtekintes van
	public ParamPagePageEditor( GUIFrame parent, ParamPageTree tree, ParamPagePageDataModel selectedNode, PageBaseRootDataModel pageBaseRootDataModel, EditMode mode ){
		super( mode, CommonOperations.getTranslation( "tree.nodetype.parampage" ) );

		this.tree = tree;
		this.nodeForModify = selectedNode;
		this.mode = mode;
		
		ParamPage paramPage = selectedNode.getParamPage();
		
		//Name		
		fieldName = new JTextField( paramPage.getName());
	
//TODO meg kell oldani !!!!1		
fieldBasePagePath =  new TreeSelectionCombo( parent, pageBaseRootDataModel );
		//BasePage
//		fieldBasePagePath = new TreeSelectionCombo( parent, selectedNode.getParamPage().getPageBase() );
		
		common();
		
	}
	
	private void common(){
		
		labelName = new JLabel( CommonOperations.getTranslation("section.title.name") + ": ");
		labelBasePagePath = new JLabel( "cim" + ": "); //TODO
		
		this.add( labelName, fieldName );		
		this.add( labelBasePagePath, fieldBasePagePath );

//		this.add( labelDetails, scrollDetails );
	}
	
	
	@Override
	public void save() {
		
		//Ertekek trimmelese
		fieldName.setText( fieldName.getText().trim() );
//		fieldDetails.setText( fieldDetails.getText().trim() );
		
		//
		//Hibak eseten a hibas mezok osszegyujtese
		//
		LinkedHashMap<Component, String> errorList = new LinkedHashMap<Component, String>();		
		if( fieldName.getText().length() == 0 ){
			errorList.put( 
					fieldName,
					MessageFormat.format(
							CommonOperations.getTranslation("section.errormessage.emptyfield"), 
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
				
				//Ha Page-rol van szo (Lehetne meg NODE is)
				if( levelNode instanceof PageBasePageDataModel ){
					
					//Ha azonos a nev
					if( ((ParamPagePageDataModel) levelNode).getParamPage().getName().equals( fieldName.getText() ) ){
					
						//Ha rogzites van, vagy ha modositas, de a vizsgalt node kulonbozik a modositott-tol
						if( null == mode || ( mode.equals( EditMode.MODIFY ) && !levelNode.equals(nodeForModify) ) ){
										
							errorList.put( 
								fieldName, 
								MessageFormat.format( 
										CommonOperations.getTranslation("section.errormessage.duplicateelement"), 
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
			
			//TreePath pathToOpen = null;
			
			//Uj rogzites eseten
			if( null == mode ){
			
				PageBase pageBase = new PageBase("", "");
				
				ParamPage paramPage = new ParamPage( fieldName.getText(), pageBase );				
				ParamPagePageDataModel newParamPagePage = new ParamPagePageDataModel( paramPage );
				nodeForCapture.add( newParamPagePage );

				//Ebbe a nodba kell majd visszaallni
				//pathToOpen = new TreePath(newPageBasePage.getPath());
				
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
