package hu.akoel.grawit.gui;

import java.awt.Component;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.IdentificationType;
import hu.akoel.grawit.VariableSample;
import hu.akoel.grawit.elements.ElementBase;
import hu.akoel.grawit.tree.PageBaseTree;
import hu.akoel.grawit.tree.node.PageBaseDataModelElement;
import hu.akoel.grawit.tree.node.PageBaseDataModelPage;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.tree.TreeNode;

public class PageBaseElementPanel extends DataPanel{
	
	private static final long serialVersionUID = 165396704460481021L;
	
	private PageBaseTree tree;
	private PageBaseDataModelElement selectedNode;
	private ElementBase elementBase;
	
	private JTextField fieldName;
	private JTextField fieldIdentifier;
	private JComboBox<String> fieldVariable;
	private JRadioButton idButton;
	private JRadioButton cssButton;

	public PageBaseElementPanel( PageBaseTree tree, PageBaseDataModelElement selectedNode, Mode mode ){
		//super( mode, selectedNode.getElementBase().getName() + " :: " + CommonOperations.getTranslation("tree.elementbase"));
		super( mode, CommonOperations.getTranslation("tree.elementbase") );

		this.tree = tree;
		this.selectedNode = selectedNode;
		elementBase = selectedNode.getElementBase();
		
		//Name
		JLabel labelName = new JLabel( CommonOperations.getTranslation("section.title.name") + ": ");
		fieldName = new JTextField( elementBase.getName());
		
		//Identifier
		JLabel labelIdentifier = new JLabel( CommonOperations.getTranslation("section.title.identifier") + ": ");
		fieldIdentifier = new JTextField( elementBase.getIdentifier() );

		//Identifier tipus
		JLabel labelIdentifierType = new JLabel( CommonOperations.getTranslation("section.title.identifiertype") + ": ");
		idButton = new JRadioButton( CommonOperations.getTranslation("section.title.identifiertype.id") );
		cssButton = new JRadioButton( CommonOperations.getTranslation("section.title.identifiertype.css") );
		ButtonGroup group = new ButtonGroup();
	    group.add(idButton);
	    group.add(cssButton);
	    IdentificationType idType = elementBase.getIdentificationType();
	    if( idType.equals( IdentificationType.ID ) ){
	    	idButton.setSelected(true);
	    }else if( idType.equals( IdentificationType.CSS ) ){
	    	cssButton.setSelected(true);
	    }		
		
		//Variable
		JLabel labelVariable = new JLabel( CommonOperations.getTranslation("section.title.variable") + ": " );
		fieldVariable = new JComboBox<>();
		fieldVariable.addItem( CommonOperations.getTranslation( "section.title.variable.no") );
		fieldVariable.addItem( CommonOperations.getTranslation( "section.title.variable.pre") );
		fieldVariable.addItem( CommonOperations.getTranslation( "section.title.variable.post") );
		VariableSample varSamp = elementBase.getVariableSample();
		if( varSamp.equals( VariableSample.NO ) ){
			fieldVariable.setSelectedIndex( 0 );
		}else if( varSamp.equals( VariableSample.PRE ) ){
			fieldVariable.setSelectedIndex( 1 );
		}else if( varSamp.equals( VariableSample.POST ) ){
			fieldVariable.setSelectedIndex( 2 );
		} 		
		
		this.add( labelName, fieldName );
		this.add( labelIdentifier, fieldIdentifier );
		this.add( labelIdentifierType, idButton );
		this.add( new JLabel(), cssButton );
		this.add( labelVariable, fieldVariable );
	}

	@Override
	public void save() {
		
		//Az esetleges hibak szamara legyartva
		LinkedHashMap<Component, String> errorList = new LinkedHashMap<Component, String>();

		//Ertekek trimmelese		
		fieldName.setText( fieldName.getText().trim() );
		fieldIdentifier.setText( fieldIdentifier.getText().trim() );
		
		//
		//Hibak eseten a hibas mezok osszegyujtese
		//
		if( fieldName.getText().length() == 0 ){
			errorList.put( fieldName, "Üres a név mező" );
		}else{

			//Megnezi, hogy a szulo node-jaban van-e masik azonos nevu elem
			TreeNode parentNode = selectedNode.getParent();
			int childrenCount = parentNode.getChildCount();
			for( int i = 0; i < childrenCount; i++ ){
				TreeNode childrenNode = parentNode.getChildAt( i );
				if( childrenNode instanceof PageBaseDataModelElement ){
					if( ((PageBaseDataModelElement) childrenNode).getElementBase().getName().equals( fieldName.getText() ) ){
						errorList.put( 
								fieldName, 
								MessageFormat.format( 
										CommonOperations.getTranslation("section.errormessage.duplicateelement"), 
										fieldName.getText(), 
										CommonOperations.getTranslation("tree.elementbase") 
								) 
						);
						break;
					}
				}
			}
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
			
			int selectedIndex = fieldVariable.getSelectedIndex();
			if( selectedIndex == VariableSample.NO.getIndex() ){
				elementBase.setVariableSample( VariableSample.NO );
			}else if( selectedIndex == VariableSample.PRE.getIndex() ){
				elementBase.setVariableSample( VariableSample.PRE );
			}else if( selectedIndex == VariableSample.POST.getIndex() ){
				elementBase.setVariableSample( VariableSample.POST );
			} 
			
			if( idButton.isSelected() ){
				elementBase.setIdentificationType( IdentificationType.ID );
			}else if( cssButton.isSelected() ){
				elementBase.setIdentificationType( IdentificationType.CSS );
			}
			
			//A fa-ban is modositja a nevet (ha az valtozott)
			tree.refresh();
		}
		
	}
	
}
