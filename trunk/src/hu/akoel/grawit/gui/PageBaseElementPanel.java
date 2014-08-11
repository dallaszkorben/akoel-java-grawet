package hu.akoel.grawit.gui;

import java.awt.Component;
import java.text.MessageFormat;
import java.util.LinkedHashMap;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.IdentificationType;
import hu.akoel.grawit.VariableSample;
import hu.akoel.grawit.elements.ElementBase;
import hu.akoel.grawit.gui.DataPanel.Mode;
import hu.akoel.grawit.pages.PageBase;
import hu.akoel.grawit.tree.PageBaseTree;
import hu.akoel.grawit.tree.datamodel.PageBaseDataModelElement;
import hu.akoel.grawit.tree.datamodel.PageBaseDataModelPage;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

public class PageBaseElementPanel extends DataPanel{
	
	private static final long serialVersionUID = 165396704460481021L;
	
	private PageBaseTree tree;
	private PageBaseDataModelElement selectedNode;
	private Mode mode;
	private ElementBase elementBase;
	
	private JLabel labelName;
	private JTextField fieldName;
	private JLabel labelIdentifier;
	private JTextField fieldIdentifier;
	private JComboBox<String> fieldVariable;
	private JRadioButton idButton;
	private JRadioButton cssButton;

	public PageBaseElementPanel( PageBaseTree tree, PageBaseDataModelElement selectedNode, Mode mode ){
		//super( mode, selectedNode.getElementBase().getName() + " :: " + CommonOperations.getTranslation("tree.elementbase"));
		super( mode, CommonOperations.getTranslation("tree.elementbase") );

		this.tree = tree;
		this.selectedNode = selectedNode;
		this.mode = mode;
		
		elementBase = selectedNode.getElementBase();
		
		//Name
		labelName = new JLabel( CommonOperations.getTranslation("section.title.name") + ": ");
		if( mode.equals( Mode.CAPTURE ) ){
			fieldName = new JTextField( "" );
		}else{
			fieldName = new JTextField( elementBase.getName());
		}
		
		//Identifier
		labelIdentifier = new JLabel( CommonOperations.getTranslation("section.title.identifier") + ": ");
		if( mode.equals( Mode.CAPTURE ) ){	
			fieldIdentifier = new JTextField( "" );
		}else{
			fieldIdentifier = new JTextField( elementBase.getIdentifier() );
		}

		//Identifier tipus
		JLabel labelIdentifierType = new JLabel( CommonOperations.getTranslation("section.title.identifiertype") + ": ");
		idButton = new JRadioButton( CommonOperations.getTranslation("section.title.identifiertype.id") );
		cssButton = new JRadioButton( CommonOperations.getTranslation("section.title.identifiertype.css") );
		ButtonGroup group = new ButtonGroup();
	    group.add(idButton);
	    group.add(cssButton);
	    IdentificationType idType = elementBase.getIdentificationType();	    
	    if( mode.equals( Mode.CAPTURE ) ){
	    	idButton.setSelected( true );
	    }else{
	    	if( idType.equals( IdentificationType.ID ) ){
	    		idButton.setSelected(true);
	    	}else if( idType.equals( IdentificationType.CSS ) ){
	    		cssButton.setSelected(true);
	    	}
	    }
		
		//Variable
		JLabel labelVariable = new JLabel( CommonOperations.getTranslation("section.title.variable") + ": " );
		fieldVariable = new JComboBox<>();
		fieldVariable.addItem( CommonOperations.getTranslation( "section.title.variable.no") );
		fieldVariable.addItem( CommonOperations.getTranslation( "section.title.variable.pre") );
		fieldVariable.addItem( CommonOperations.getTranslation( "section.title.variable.post") );
		VariableSample varSamp = elementBase.getVariableSample();
		if( mode.equals( Mode.CAPTURE ) ){
			fieldVariable.setSelectedIndex( 0 );
		}else{
			if( varSamp.equals( VariableSample.NO ) ){
				fieldVariable.setSelectedIndex( 0 );
			}else if( varSamp.equals( VariableSample.PRE ) ){
				fieldVariable.setSelectedIndex( 1 );
			}else if( varSamp.equals( VariableSample.POST ) ){
				fieldVariable.setSelectedIndex( 2 );
			} 		
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
			errorList.put( 
					fieldName,
					MessageFormat.format(
							CommonOperations.getTranslation("section.errormessage.emptyfield"), 
							"'"+labelName.getText()+"'"
					)
			);
		}else{

			//Megnezi, hogy a szulo node-jaban van-e masik azonos nevu elem
			TreeNode parentNode = selectedNode.getParent();
			int childrenCount = parentNode.getChildCount();
			for( int i = 0; i < childrenCount; i++ ){
				TreeNode childrenNode = parentNode.getChildAt( i );
				
				//Ha Element-rol van szo (nyilvan az, nem lehet mas)
				if( childrenNode instanceof PageBaseDataModelElement ){
					
					//Ha azonos a nev
					if( ((PageBaseDataModelPage) childrenNode).getPageBase().getName().equals( fieldName.getText() ) ){
					
						//Ha rogzites van, vagy ha modositas, de a vizsgalt node kulonbozik a modositott-tol
						if( mode.equals( Mode.CAPTURE ) || ( mode.equals( Mode.MODIFY ) && !childrenNode.equals(selectedNode) ) ){
							
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
		}

		
		if( fieldIdentifier.getText().length() == 0 ){
			errorList.put( 
					fieldIdentifier,
					MessageFormat.format(
							CommonOperations.getTranslation("section.errormessage.emptyfield"), 
							"'"+labelIdentifier.getText()+"'"
					)
			);
		}				
		
		//Volt hiba
		if( errorList.size() != 0 ){
			
			//Hibajelzes
			this.errorAt( errorList );
		
		//Ha nem volt hiba akkor a valtozok veglegesitese
		}else{
			
			VariableSample variableSample = null;
			int selectedIndex = fieldVariable.getSelectedIndex();
			if( selectedIndex == VariableSample.NO.getIndex() ){
				variableSample = VariableSample.NO;
			}else if( selectedIndex == VariableSample.PRE.getIndex() ){
				variableSample = VariableSample.PRE;
			}else if( selectedIndex == VariableSample.POST.getIndex() ){
				variableSample = VariableSample.POST;
			} 
			
			IdentificationType identificationType = null;
			if( idButton.isSelected() ){
				identificationType = IdentificationType.ID;
			}else if( cssButton.isSelected() ){
				identificationType = IdentificationType.CSS;
			}
			
			//Modositas eseten
			if( mode.equals(Mode.MODIFY ) ){
		
				elementBase.setName( fieldName.getText() );
				elementBase.setIdentifier( fieldIdentifier.getText() );				
				elementBase.setVariableSample( variableSample );
				elementBase.setIdentificationType( identificationType );
			
				//Uj rogzites eseten
			}else if( mode.equals( Mode.CAPTURE ) ){
					
				DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode)selectedNode.getParent();
				int selectedNodeIndex = parentNode.getIndex( selectedNode );
				
				ElementBase elementBase = new ElementBase( fieldName.getText(), fieldIdentifier.getText(), identificationType, variableSample  );				
				PageBaseDataModelElement newPageBaseElement = new PageBaseDataModelElement( elementBase );
				parentNode.insert( newPageBaseElement, selectedNodeIndex);
					
			}
			//A fa-ban is modositja a nevet (ha az valtozott)
			tree.changed();
		}
		
	}
	
}
