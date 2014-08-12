package hu.akoel.grawit.gui.editor;

import java.awt.Component;
import java.text.MessageFormat;
import java.util.LinkedHashMap;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.gui.tree.PageBaseTree;
import hu.akoel.grawit.gui.tree.datamodel.PageBaseNodeDataModel;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class PageBaseNodeEditor extends DataEditor{
	
	private static final long serialVersionUID = 165396704460481021L;
	
	private PageBaseTree tree;
	private PageBaseNodeDataModel nodeForModify;
	private PageBaseNodeDataModel nodeForCapture;
	private  EditMode mode;
	
	private JLabel labelName;
	private JTextField fieldName;
	private JTextArea fieldDetails;

	private void common(){
		
		//Name
		labelName = new JLabel( CommonOperations.getTranslation("section.title.name") + ": ");

		//Details
		JLabel labelDetails = new JLabel( CommonOperations.getTranslation("section.title.details") + ": ");
		JScrollPane scrollDetails = new JScrollPane(fieldDetails);

		this.add( labelName, fieldName );
		this.add( labelDetails, scrollDetails );

		
	}
	
	//Itt biztos beszuras van
	public PageBaseNodeEditor( PageBaseTree tree, PageBaseNodeDataModel selectedNode ){
		super( CommonOperations.getTranslation("tree.node") );
		
		this.tree = tree;
		this.nodeForCapture = selectedNode;
		this.mode = null;
		
		//Name
		fieldName = new JTextField( "" );
		
		//Details
		fieldDetails = new JTextArea( "", 5, 15);
		
		common();
		
	}
	
	//Itt modisitas van
	public PageBaseNodeEditor( PageBaseTree pageBaseTree, PageBaseNodeDataModel selectedNode, EditMode mode ){		
		super( mode, CommonOperations.getTranslation("tree.node") );

		this.tree = pageBaseTree;
		this.nodeForModify = selectedNode;
		this.mode = mode;
		
		
		//Name
		fieldName = new JTextField( selectedNode.getName());
		
		//Details
		fieldDetails = new JTextArea( selectedNode.getDetails(), 5, 15);
		
		common();
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
							CommonOperations.getTranslation("section.errormessage.emptyfield"), 
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
			
			//Megnezi, hogy a node-ban van-e masik azonos nevu elem
			int childrenCount = nodeForSearch.getChildCount();
			for( int i = 0; i < childrenCount; i++ ){
				TreeNode levelNode = nodeForSearch.getChildAt( i );
				
				//Ha Node-rol van szo
				if( levelNode instanceof PageBaseNodeDataModel ){
					
					//Ha azonos a nev
					if( ((PageBaseNodeDataModel) levelNode).getName().equals( fieldName.getText() ) ){
						
						//Ha rogzites van, vagy ha modositas, de a vizsgalt node kulonbozik a modositott-tol
						if( null == mode || ( mode.equals( EditMode.MODIFY ) && !levelNode.equals(nodeForModify) ) ){
							
							//Akkor hiba van
							errorList.put( 
								fieldName, 
								MessageFormat.format( 
										CommonOperations.getTranslation("section.errormessage.duplicateelement"), 
										fieldName.getText(), 
										CommonOperations.getTranslation("tree.node") 
								) 
							);	
							break;
						}
					}
				}
			}
		}
		
		//Ha volt hiba
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
				PageBaseNodeDataModel newPageBaseNode = new PageBaseNodeDataModel( fieldName.getText(), fieldDetails.getText() );				
				//parentNode.insert( newPageBaseNode, selectedNodeIndex);
				nodeForCapture.add( newPageBaseNode );
			
				//Ebbe a nodba kell majd visszaallni
				//pathToOpen = new TreePath(newPageBaseNode.getPath());
				
			//Modositas eseten
			}else if( mode.equals(EditMode.MODIFY ) ){

				//Modositja a valtozok erteket
				nodeForModify.setName( fieldName.getText() );
				nodeForModify.setDetails( fieldDetails.getText() );
			
				//Ebbe a nodba kell majd visszaallni
				//pathToOpen = new TreePath(nodeForModify.getPath());
			}			
			
			//A fa-ban is modositja a nevet (ha az valtozott)
			tree.changed();
		}		
	}
}
