package hu.akoel.grawit.gui;

import java.awt.Component;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.pages.PageBase;
import hu.akoel.grawit.tree.PageBaseTree;
import hu.akoel.grawit.tree.node.PageBaseDataModelPage;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class PageBasePagePanel extends DataPanel{
	
	private static final long serialVersionUID = -9038879802467565947L;

	private PageBaseTree tree; 
	private PageBase pageBase;
	
	private JTextField fieldName;
	private JTextArea fieldDetails;
	
	public PageBasePagePanel( PageBaseTree tree, PageBaseDataModelPage selectedNode, Mode mode ){
		//super( mode, selectedNode.getPageBase().getName() + " :: " + CommonOperations.getTranslation("tree.pagebase"));
		super( mode, CommonOperations.getTranslation("tree.pagebase") );

		this.tree = tree;
		pageBase = selectedNode.getPageBase();
		
		//Name
		JLabel labelName = new JLabel( CommonOperations.getTranslation("section.title.name") + ": ");
		fieldName = new JTextField( pageBase.getName());
		
		//Details
		JLabel labelDetails = new JLabel( CommonOperations.getTranslation("section.title.details") + ": ");
		fieldDetails = new JTextArea( pageBase.getDetails(), 5, 15);
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
		
		//Hibak eseten a hibas mezok osszegyujtese
		if( fieldName.getText().length() == 0 ){
			errorList.put( fieldName, "Üres a név mező" );
		}
		
		//Volt hiba
		if( errorList.size() != 0 ){
			
			//Hibajelzes
			this.errorAt( errorList );
		
		//Ha nem volt hiba akkor a valtozok veglegesitese
		}else{
			pageBase.setName( fieldName.getText() );
			pageBase.setDetails( fieldDetails.getText() );
			
			//A fa-ban is modositja a nevet (ha az valtozott)
			tree.refresh();
		}
	}
}
