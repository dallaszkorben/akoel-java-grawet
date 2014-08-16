package hu.akoel.grawit.gui.editor;

import java.awt.Component;
import java.text.MessageFormat;
import java.util.LinkedHashMap;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.pages.PageBase;
import hu.akoel.grawit.gui.editor.component.TextAreaComponent;
import hu.akoel.grawit.gui.editor.component.TextFieldComponent;
import hu.akoel.grawit.gui.tree.PageBaseTree;
import hu.akoel.grawit.gui.tree.datamodel.PageBaseNodeDataModel;
import hu.akoel.grawit.gui.tree.datamodel.PageBasePageDataModel;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.tree.TreeNode;

public class PageBasePageEditor extends DataEditor{
	
	private static final long serialVersionUID = -9038879802467565947L;

	private PageBaseTree tree; 
	private PageBasePageDataModel nodeForModify;
	private PageBaseNodeDataModel nodeForCapture;
	private EditMode mode;
	
	private JLabel labelName;
	private TextFieldComponent fieldName;
	private JLabel labelDetails;
	private TextAreaComponent fieldDetails;
	
	//Itt biztos beszuras van
	public PageBasePageEditor( PageBaseTree tree, PageBaseNodeDataModel selectedNode ){
		super( CommonOperations.getTranslation("tree.nodetype.pagebase") );
		
		this.tree = tree;
		this.nodeForCapture = selectedNode;
		this.mode = null;
		
		//Name
		fieldName = new TextFieldComponent( "" );
		
		//Details
		fieldDetails = new TextAreaComponent( "", 5, 15);

		common();
		
	}
	
	//Itt lehet hogy modositas vagy megtekintes van
	public PageBasePageEditor( PageBaseTree tree, PageBasePageDataModel selectedNode, EditMode mode ){
		super( mode, CommonOperations.getTranslation("tree.nodetype.pagebase") );

		this.tree = tree;
		this.nodeForModify = selectedNode;
		this.mode = mode;
		
		PageBase pageBase = selectedNode.getPageBase();
		
		//Name		
		fieldName = new TextFieldComponent( pageBase.getName());
			
		//Details
		fieldDetails = new TextAreaComponent( pageBase.getDetails(), 5, 15);
		
		common();
		
	}
	
	private void common(){
		labelName = new JLabel( CommonOperations.getTranslation("editor.title.name") + ": ");
		labelDetails = new JLabel( CommonOperations.getTranslation("editor.title.details") + ": ");
		
		this.add( labelName, fieldName );
		this.add( labelDetails, fieldDetails );
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
					if( ((PageBasePageDataModel) levelNode).getPageBase().getName().equals( fieldName.getText() ) ){
					
						//Ha rogzites van, vagy ha modositas, de a vizsgalt node kulonbozik a modositott-tol
						if( null == mode || ( mode.equals( EditMode.MODIFY ) && !levelNode.equals(nodeForModify) ) ){
										
							errorList.put( 
								fieldName, 
								MessageFormat.format( 
										CommonOperations.getTranslation("editor.errormessage.duplicateelement"), 
										fieldName.getText(), 
										CommonOperations.getTranslation("tree.nodetype.pagebase") 
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
			
				//DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode)selectedNode.getParent();
				//int selectedNodeIndex = parentNode.getIndex( selectedNode );
				PageBase pageBase = new PageBase( fieldName.getText(), fieldDetails.getText() );				
				PageBasePageDataModel newPageBasePage = new PageBasePageDataModel( pageBase );
				//parentNode.insert( newPageBasePage, selectedNodeIndex);
				nodeForCapture.add( newPageBasePage );

				//Ebbe a nodba kell majd visszaallni
				//pathToOpen = new TreePath(newPageBasePage.getPath());
				
			//Modositas eseten
			}else if( mode.equals(EditMode.MODIFY ) ){
				
				PageBase pageBase = nodeForModify.getPageBase(); 
				
				pageBase.setName( fieldName.getText() );
				pageBase.setDetails( fieldDetails.getText() );
			
				//Ebbe a nodba kell majd visszaallni
				//pathToOpen = new TreePath(nodeForModify.getPath());
			}			
			
			//A fa-ban is modositja a nevet (ha az valtozott)
			tree.changed();
		}
	}
}
