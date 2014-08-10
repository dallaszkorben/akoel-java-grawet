package hu.akoel.grawit.gui;

import java.awt.Component;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.tree.PageBaseTree;
import hu.akoel.grawit.tree.node.PageBaseDataModelNode;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.tree.TreeNode;

public class PageBaseNodePanel extends DataPanel{
	
	private static final long serialVersionUID = 165396704460481021L;
	
	private PageBaseTree tree;
	private PageBaseDataModelNode selectedNode;
	
	private JTextField fieldName;
	private JTextArea fieldDetails;

	public PageBaseNodePanel( PageBaseTree tree, PageBaseDataModelNode selectedNode, Mode mode ){
		//super( mode, selectedNode.getName() + " :: " + CommonOperations.getTranslation("tree.node"));
		super( mode, CommonOperations.getTranslation("tree.node") );

		this.tree = tree;
		this.selectedNode = selectedNode;
				
		//Name
		JLabel labelName = new JLabel( CommonOperations.getTranslation("section.title.name") + ": ");
		fieldName = new JTextField( selectedNode.getName());
		
		//Details
		JLabel labelDetails = new JLabel( CommonOperations.getTranslation("section.title.details") + ": ");
		fieldDetails = new JTextArea( selectedNode.getDetails(), 5, 15);
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
		
		//
		if( fieldName.getText().length() == 0 ){
			
			errorList.put( fieldName, "Üres a név mező" );
		
		}else{

			TreeNode parentNode = selectedNode.getParent();
			int childrenCount = parentNode.getChildCount();
			for( int i = 0; i < childrenCount; i++ ){
				TreeNode childrenNode = parentNode.getChildAt( i );
				if( childrenNode instanceof PageBaseDataModelNode ){
					if( ((PageBaseDataModelNode) childrenNode).getName().equals( fieldName.getText() ) ){
						errorList.put( fieldName, "Van már ilyen nevü elem" );
						break;
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
		
			//Modositja a valtozok erteket
			selectedNode.setName( fieldName.getText() );
			selectedNode.setDetails( fieldDetails.getText() );
			
			//A fa-ban is modositja a nevet (ha az valtozott)
			tree.refresh();
		}

		
	}
}
