package hu.akoel.grawit.gui.editor;

import java.awt.Component;
import java.text.MessageFormat;
import java.util.LinkedHashMap;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.pages.BasePage;
import hu.akoel.grawit.gui.editor.component.TextAreaComponent;
import hu.akoel.grawit.gui.editor.component.TextFieldComponent;
import hu.akoel.grawit.gui.tree.BasePageTree;
import hu.akoel.grawit.gui.tree.datamodel.BasePageNodeDataModel;
import hu.akoel.grawit.gui.tree.datamodel.BasePagePageDataModel;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.tree.TreeNode;

public class BasePagePageEditor extends DataEditor{
	
	private static final long serialVersionUID = -9038879802467565947L;

	private BasePageTree tree; 
	private BasePagePageDataModel nodeForModify;
	private BasePageNodeDataModel nodeForCapture;
	private EditMode mode;
	
	private JLabel labelName;
	private TextFieldComponent fieldName;
	private JLabel labelDetails;
	private TextAreaComponent fieldDetails;
	
	//Itt biztos beszuras van
	public BasePagePageEditor( BasePageTree tree, BasePageNodeDataModel selectedNode ){
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
	public BasePagePageEditor( BasePageTree tree, BasePagePageDataModel selectedNode, EditMode mode ){
		super( mode, CommonOperations.getTranslation("tree.nodetype.pagebase") );

		this.tree = tree;
		this.nodeForModify = selectedNode;
		this.mode = mode;
		
		BasePage basePage = selectedNode.getBasePage();
		
		//Name		
		fieldName = new TextFieldComponent( basePage.getName());
			
		//Details
		fieldDetails = new TextAreaComponent( basePage.getDetails(), 5, 15);
		
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
				if( levelNode instanceof BasePagePageDataModel ){
					
					//Ha azonos a nev
					if( ((BasePagePageDataModel) levelNode).getBasePage().getName().equals( fieldName.getText() ) ){
					
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
				BasePage basePage = new BasePage( fieldName.getText(), fieldDetails.getText() );				
				BasePagePageDataModel newPageBasePage = new BasePagePageDataModel( basePage );
				//parentNode.insert( newPageBasePage, selectedNodeIndex);
				nodeForCapture.add( newPageBasePage );

				//Ebbe a nodba kell majd visszaallni
				//pathToOpen = new TreePath(newPageBasePage.getPath());
				
			//Modositas eseten
			}else if( mode.equals(EditMode.MODIFY ) ){
				
				BasePage basePage = nodeForModify.getBasePage(); 
				
				basePage.setName( fieldName.getText() );
				basePage.setDetails( fieldDetails.getText() );
			
				//Ebbe a nodba kell majd visszaallni
				//pathToOpen = new TreePath(nodeForModify.getPath());
			}			
			
			//A fa-ban is modositja a nevet (ha az valtozott)
			tree.changed();
		}
	}
}
