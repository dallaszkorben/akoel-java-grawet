package hu.akoel.grawit.gui;

import java.awt.Component;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.elements.ElementBase;
import hu.akoel.grawit.tree.PageBaseTree;
import hu.akoel.grawit.tree.node.PageBaseDataModelElement;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class PageBaseElementPanel extends DataPanel{
	
	private static final long serialVersionUID = 165396704460481021L;
	
	private PageBaseTree tree;
	private ElementBase elementBase;
	
	private JTextField fieldName;
	private JTextField fieldIdentifier;

	public PageBaseElementPanel( PageBaseTree tree, PageBaseDataModelElement selectedNode, Mode mode ){
		//super( mode, selectedNode.getElementBase().getName() + " :: " + CommonOperations.getTranslation("tree.elementbase"));
		super( mode, CommonOperations.getTranslation("tree.elementbase") );

		this.tree = tree;
		elementBase = selectedNode.getElementBase();
		
		//Name
		JLabel labelName = new JLabel( CommonOperations.getTranslation("section.title.name") + ": ");
		fieldName = new JTextField( elementBase.getName());
		
		//Identifier
		JLabel labelIdentifier = new JLabel( CommonOperations.getTranslation("section.title.identifier") + ": ");
		fieldIdentifier = new JTextField( elementBase.getIdentifier() );

		this.add( labelName, fieldName );
		this.add( labelIdentifier, fieldIdentifier );
	}

	@Override
	public void save() {
		
		//Az esetleges hibak szamara legyartva
		LinkedHashMap<Component, String> errorList = new LinkedHashMap<Component, String>();

		//Ertekek trimmelese		
		fieldName.setText( fieldName.getText().trim() );
		fieldIdentifier.setText( fieldIdentifier.getText().trim() );
		
		//Hibak eseten a hibas mezok osszegyujtese
		if( fieldName.getText().length() == 0 ){
			errorList.put( fieldName, "Üres a név mező" );
		}
		if( fieldIdentifier.getText().length() == 0 ){
			errorList.put( fieldName, "Üres az Identifier mező" );
		}
		
		//Volt hiba
		if( errorList.size() != 0 ){
			
			//Hibajelzes
			this.errorAt( errorList );
		
		//Ha nem volt hiba akkor a valtozok veglegesitese
		}else{
		
			elementBase.setName( fieldName.getText() );
			elementBase.setIdentifier( fieldIdentifier.getText() );
			
			//A fa-ban is modositja a nevet (ha az valtozott)
			tree.refresh();
		}
		
	}
	
}
