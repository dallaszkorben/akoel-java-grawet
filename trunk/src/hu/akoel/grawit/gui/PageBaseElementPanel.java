package hu.akoel.grawit.gui;

import java.awt.Component;
import java.text.MessageFormat;
import java.util.LinkedHashMap;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.IdentificationType;
import hu.akoel.grawit.VariableSample;
import hu.akoel.grawit.elements.ElementBase;
import hu.akoel.grawit.gui.DataPanel.EditMode;
import hu.akoel.grawit.pages.PageBase;
import hu.akoel.grawit.tree.PageBaseTree;
import hu.akoel.grawit.tree.datamodel.PageBaseElementDataModel;
import hu.akoel.grawit.tree.datamodel.PageBaseNodeDataModel;
import hu.akoel.grawit.tree.datamodel.PageBasePageDataModel;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class PageBaseElementPanel extends DataPanel{
	
	private static final long serialVersionUID = 165396704460481021L;
	
	private PageBaseTree tree;
	private PageBaseElementDataModel nodeForModify;
	private PageBasePageDataModel nodeForCapture;
	private EditMode mode;
	
	private JLabel labelName;
	private JTextField fieldName;
	private JLabel labelIdentifier;
	private JTextField fieldIdentifier;
	private JComboBox<String> fieldVariable;
	private JRadioButton idButton;
	private JRadioButton cssButton;
	
	//Insert
	public PageBaseElementPanel( PageBaseTree tree, PageBasePageDataModel selectedNode ){
		super( CommonOperations.getTranslation("tree.elementbase") );

		this.tree = tree;
		this.nodeForCapture = selectedNode;
		this.mode = null;

		commonPre();
		
		//Name
		fieldName = new JTextField( "" );

		//Identifier
		fieldIdentifier = new JTextField( "" );

		//Identifier type
    	idButton.setSelected( true );
    	
    	//Variable
		fieldVariable.setSelectedIndex( 0 );
		
		commonPost();
	}
		
	
	
	//Modositas vagy View
	public PageBaseElementPanel( PageBaseTree tree, PageBaseElementDataModel selectedNode, EditMode mode ){		
		super( mode, CommonOperations.getTranslation("tree.elementbase") );

		this.tree = tree;
		this.nodeForModify = selectedNode;
		this.mode = mode;
		
		ElementBase elementBase = selectedNode.getElementBase();
		
		commonPre();
		
		//Name
		fieldName = new JTextField( elementBase.getName());
				
		//Identifier
		fieldIdentifier = new JTextField( elementBase.getIdentifier() );
	
		//Identifier type
	    IdentificationType idType = elementBase.getIdentificationType();	    
	   	if( idType.equals( IdentificationType.ID ) ){
	   		idButton.setSelected(true);
	   	}else if( idType.equals( IdentificationType.CSS ) ){
	   		cssButton.setSelected(true);
	   	}
			
		//Variable	
		VariableSample varSamp = elementBase.getVariableSample();
		if( varSamp.equals( VariableSample.NO ) ){
			fieldVariable.setSelectedIndex( 0 );
		}else if( varSamp.equals( VariableSample.PRE ) ){
			fieldVariable.setSelectedIndex( 1 );
		}else if( varSamp.equals( VariableSample.POST ) ){
			fieldVariable.setSelectedIndex( 2 );
		} 		
	
		commonPost();
		
	}

	private void commonPost(){
		
		labelName = new JLabel( CommonOperations.getTranslation("section.title.name") + ": ");
		labelIdentifier = new JLabel( CommonOperations.getTranslation("section.title.identifier") + ": ");
		JLabel labelIdentifierType = new JLabel( CommonOperations.getTranslation("section.title.identifiertype") + ": ");
		JLabel labelVariable = new JLabel( CommonOperations.getTranslation("section.title.variable") + ": " );
		
		this.add( labelName, fieldName );
		this.add( labelIdentifier, fieldIdentifier );
		this.add( labelIdentifierType, idButton );
		this.add( new JLabel(), cssButton );
		this.add( labelVariable, fieldVariable );
		
		
	}
	
	private void commonPre(){
		
		//Identifier tipus
		idButton = new JRadioButton( CommonOperations.getTranslation("section.title.identifiertype.id") );
		cssButton = new JRadioButton( CommonOperations.getTranslation("section.title.identifiertype.css") );
		ButtonGroup group = new ButtonGroup();
		group.add(idButton);
		group.add(cssButton);
		
		//Variable
		fieldVariable = new JComboBox<>();
		fieldVariable.addItem( CommonOperations.getTranslation( "section.title.variable.no") );
		fieldVariable.addItem( CommonOperations.getTranslation( "section.title.variable.pre") );
		fieldVariable.addItem( CommonOperations.getTranslation( "section.title.variable.post") );
		
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

			TreeNode nodeForSearch = null;
			
			if( null == mode ){
				
				nodeForSearch = nodeForCapture;
				
			}else if( mode.equals( EditMode.MODIFY )){
				
				nodeForSearch = nodeForModify.getParent();
				
			}

			//Megnezi, hogy van-e masik azonos nevu elem			
			int childrenCount = nodeForSearch.getChildCount();
			for( int i = 0; i < childrenCount; i++ ){
				TreeNode levelNode = nodeForSearch.getChildAt( i );
				
				//Ha Element-rol van szo 
				if( levelNode instanceof PageBaseElementDataModel ){
					
					//Ha azonos a nev
					if( ((PageBaseElementDataModel) levelNode).getElementBase().getName().equals( fieldName.getText() ) ){
					
						//Ha rogzites van, vagy ha modositas, de a vizsgalt node kulonbozik a modositott-tol
						if( null == mode || ( mode.equals( EditMode.MODIFY ) && !levelNode.equals(nodeForModify) ) ){
							
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
			
			TreePath pathToOpen = null;
			
			//Uj rogzites eseten
			if( null == mode ){
				
				//DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode)selectedNode.getParent();
				//int selectedNodeIndex = parentNode.getIndex( selectedNode );				
				ElementBase elementBase = new ElementBase( fieldName.getText(), fieldIdentifier.getText(), identificationType, variableSample  );				
				PageBaseElementDataModel newPageBaseElement = new PageBaseElementDataModel( elementBase );
				//parentNode.insert( newPageBaseElement, selectedNodeIndex);
			
				nodeForCapture.add( newPageBaseElement );
				
				//Ebbe a nodba kell majd visszaallni
				pathToOpen = new TreePath(newPageBaseElement.getPath());
			
			//Modositas eseten
			}else if( mode.equals(EditMode.MODIFY ) ){
		
				ElementBase elementBase = nodeForModify.getElementBase(); 
				
				elementBase.setName( fieldName.getText() );
				elementBase.setIdentifier( fieldIdentifier.getText() );				
				elementBase.setVariableSample( variableSample );
				elementBase.setIdentificationType( identificationType );
				
				//Ebbe a nodba kell majd visszaallni
				pathToOpen = new TreePath(nodeForModify.getPath());
					
			}
			
			//A fa-ban is modositja a nevet (ha az valtozott)
			tree.changed( pathToOpen );
		}
		
	}
	
}
