package hu.akoel.grawit.gui;

import java.awt.Component;
import java.text.MessageFormat;
import java.util.LinkedHashMap;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.pages.PageBase;
import hu.akoel.grawit.tree.PageBaseTree;
import hu.akoel.grawit.tree.datamodel.PageBaseNodeDataModel;
import hu.akoel.grawit.tree.datamodel.PageBasePageDataModel;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.tree.TreeNode;

public class PageBasePagePanel extends DataPanel{
	
	private static final long serialVersionUID = -9038879802467565947L;

	private PageBaseTree tree; 
	private PageBasePageDataModel nodeForModify;
	private PageBaseNodeDataModel nodeForCapture;
	private Mode mode;
	
	private JLabel labelName;
	private JTextField fieldName;
	private JLabel labelDetails;
	private JTextArea fieldDetails;
	
	//Itt biztos beszuras van
	public PageBasePagePanel( PageBaseTree tree, PageBaseNodeDataModel selectedNode ){
		super( Mode.CAPTURE, CommonOperations.getTranslation("tree.pagebase") );
		
		this.tree = tree;
		this.nodeForCapture = selectedNode;
		this.mode = Mode.CAPTURE;
		
		//Name
		fieldName = new JTextField( "" );
		
		//Details
		fieldDetails = new JTextArea( "", 5, 15);

		common();
		
	}
	
	//Itt lehet hogy modositas vagy megtekintes van
	public PageBasePagePanel( PageBaseTree tree, PageBasePageDataModel selectedNode, Mode mode ){
		super( mode, CommonOperations.getTranslation("tree.pagebase") );

		this.tree = tree;
		this.nodeForModify = selectedNode;
		this.mode = mode;
		
		PageBase pageBase = selectedNode.getPageBase();
		
		//Name		
		fieldName = new JTextField( pageBase.getName());
			
		//Details
		fieldDetails = new JTextArea( pageBase.getDetails(), 5, 15);
		
		common();
		
	}
	
	private void common(){
		labelName = new JLabel( CommonOperations.getTranslation("section.title.name") + ": ");
		labelDetails = new JLabel( CommonOperations.getTranslation("section.title.details") + ": ");
		JScrollPane scrollDetails = new JScrollPane(fieldDetails);
		
		this.add( labelName, fieldName );
		this.add( labelDetails, scrollDetails );
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
						
			if( mode.equals(Mode.CAPTURE)){
				
				nodeForSearch = nodeForCapture;
				
			}else if( mode.equals( Mode.MODIFY )){
				
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
						if( mode.equals( Mode.CAPTURE ) || ( mode.equals( Mode.MODIFY ) && !levelNode.equals(nodeForModify) ) ){
										
							errorList.put( 
								fieldName, 
								MessageFormat.format( 
										CommonOperations.getTranslation("section.errormessage.duplicateelement"), 
										fieldName.getText(), 
										CommonOperations.getTranslation("tree.pagebase") 
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
			
			//Modositas eseten
			if( mode.equals(Mode.MODIFY ) ){
				
				PageBase pageBase = nodeForModify.getPageBase(); 
				
				pageBase.setName( fieldName.getText() );
				pageBase.setDetails( fieldDetails.getText() );
			
			//Uj rogzites eseten
			}else if( mode.equals( Mode.CAPTURE ) ){
				
				//DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode)selectedNode.getParent();
				//int selectedNodeIndex = parentNode.getIndex( selectedNode );
				PageBase pageBase = new PageBase( fieldName.getText(), fieldDetails.getText() );				
				PageBasePageDataModel newPageBasePage = new PageBasePageDataModel( pageBase );
				//parentNode.insert( newPageBasePage, selectedNodeIndex);
				nodeForCapture.add( newPageBasePage );
			}
			
			//A fa-ban is modositja a nevet (ha az valtozott)
			tree.changed();
		}
	}
}
