package hu.akoel.grawit.gui;

import java.awt.Component;
import java.text.MessageFormat;
import java.util.LinkedHashMap;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.tree.PageBaseTree;
import hu.akoel.grawit.tree.datamodel.PageBaseNodeDataModel;
import hu.akoel.grawit.tree.datamodel.PageBaseRootDataModel;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

public class PageBaseNodePanel extends DataPanel{
	
	private static final long serialVersionUID = 165396704460481021L;
	
	private PageBaseTree tree;
	private PageBaseNodeDataModel selectedNode;
	private  Mode mode;
	
	private JLabel labelName;
	private JTextField fieldName;
	private JTextArea fieldDetails;

	private void common(){
		
	}
	
	public PageBaseNodePanel( PageBaseTree tree, PageBaseRootDataModel selectedNode, Mode mode ){
		super( mode, CommonOperations.getTranslation("tree.node") );
		
	}
	
	public PageBaseNodePanel( PageBaseTree tree, PageBaseNodeDataModel selectedNode, Mode mode ){
		//super( mode, selectedNode.getName() + " :: " + CommonOperations.getTranslation("tree.node"));
		super( mode, CommonOperations.getTranslation("tree.node") );

		this.tree = tree;
		this.selectedNode = selectedNode;
		this.mode = mode;
		
//TODO lehetne ezt az egeszet automatizalni valahogy. meg az ellenorzest is				
		
		//Name
		labelName = new JLabel( CommonOperations.getTranslation("section.title.name") + ": ");
		if( mode.equals( Mode.CAPTURE ) ){
			fieldName = new JTextField( "" );
		}else{
			fieldName = new JTextField( selectedNode.getName());
		}
		
		//Details
		JLabel labelDetails = new JLabel( CommonOperations.getTranslation("section.title.details") + ": ");
		if( mode.equals( Mode.CAPTURE ) ){
			fieldDetails = new JTextArea( "", 5, 15);
		}else{
			fieldDetails = new JTextArea( selectedNode.getDetails(), 5, 15);
		}
		JScrollPane scrollDetails = new JScrollPane(fieldDetails);

		this.add( labelName, fieldName );
		this.add( labelDetails, scrollDetails );
	}
	
	@Override
	public void save() {

		//Az esetleges hibak szamara legyartva
		LinkedHashMap<Component, String> errorList = new LinkedHashMap<Component, String>();

		//Ertekek trimmelese
		fieldName.setText( fieldName.getText().trim() );
		fieldDetails.setText( fieldDetails.getText().trim() );
		
		//
		//Hibak eseten a hibas mezok osszegyujtese
		//		
		
		if( fieldName.getText().length() == 0 ){
			errorList.put( 
					fieldName,
					MessageFormat.format(
							CommonOperations.getTranslation("section.errormessage.emptyfield"), 
							"'"+labelName.getText()+"'"
					)
			);
		}else{

			//Megnezi, hogy a node-ban van-e masik azonos nevu elem
			//TreeNode parentNode = selectedNode.getParent();
			int nodeCount = selectedNode.getChildCount();
			for( int i = 0; i < nodeCount; i++ ){
				TreeNode node = selectedNode.getChildAt( i );
				
				//Ha Node-rol van szo (nyilvan az, nem lehet mas)
				if( node instanceof PageBaseNodeDataModel ){
					
					//Ha azonos a nev
					if( ((PageBaseNodeDataModel) node).getName().equals( fieldName.getText() ) ){
						
						//Ha rogzites van, vagy ha modositas, de a vizsgalt node kulonbozik a modositott-tol
						if( mode.equals( Mode.CAPTURE ) || ( mode.equals( Mode.MODIFY ) && !node.equals(selectedNode) ) ){

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

			//Modositas eseten
			if( mode.equals(Mode.MODIFY ) ){

				//Modositja a valtozok erteket
				selectedNode.setName( fieldName.getText() );
				selectedNode.setDetails( fieldDetails.getText() );
				
			//Uj rogzites eseten
			}else if( mode.equals( Mode.CAPTURE ) ){
				
				//DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode)selectedNode.getParent();
				//int selectedNodeIndex = parentNode.getIndex( selectedNode );
				PageBaseNodeDataModel newPageBaseNode = new PageBaseNodeDataModel( fieldName.getText(), fieldDetails.getText() );				
				//parentNode.insert( newPageBaseNode, selectedNodeIndex);
				selectedNode.add( newPageBaseNode );
			}
			
			//A fa-ban is modositja a nevet (ha az valtozott)
			tree.changed();
		}		
	}
}
