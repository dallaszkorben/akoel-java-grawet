package hu.akoel.grawit.gui.editor;

import java.awt.Component;
import java.text.MessageFormat;
import java.util.LinkedHashMap;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.IdentificationType;
import hu.akoel.grawit.VariableSample;
import hu.akoel.grawit.core.elements.BaseElement;
import hu.akoel.grawit.gui.editor.component.ComboBoxComponent;
import hu.akoel.grawit.gui.editor.component.RadioButtonComponent;
import hu.akoel.grawit.gui.editor.component.TextFieldComponent;
import hu.akoel.grawit.gui.tree.PageBaseTree;
import hu.akoel.grawit.gui.tree.datamodel.PageBaseElementDataModel;
import hu.akoel.grawit.gui.tree.datamodel.PageBasePageDataModel;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

public class PageBaseElementEditor extends DataEditor{
	
	private static final long serialVersionUID = 165396704460481021L;
	
	private PageBaseTree tree;
	private PageBaseElementDataModel nodeForModify;
	private PageBasePageDataModel nodeForCapture;
	private EditMode mode;
	
	private JLabel labelName;
	private TextFieldComponent fieldName;
	private JLabel labelIdentifier;
	private TextFieldComponent fieldIdentifier;
	private ComboBoxComponent<String> fieldVariable;
	private RadioButtonComponent buttonID;
	private RadioButtonComponent buttonCSS;
	
	//Insert
	public PageBaseElementEditor( PageBaseTree tree, PageBasePageDataModel selectedNode ){
		super( CommonOperations.getTranslation("tree.nodetype.baseelement") );

		this.tree = tree;
		this.nodeForCapture = selectedNode;
		this.mode = null;

		commonPre();
		
		//Name
		fieldName = new TextFieldComponent( "" );

		//Identifier
		fieldIdentifier = new TextFieldComponent( "" );

		//Identifier type
    	buttonID.setSelected( true );
    	
    	//Variable
		fieldVariable.setSelectedIndex( 0 );
		
		commonPost();
	}
		
	
	
	//Modositas vagy View
	public PageBaseElementEditor( PageBaseTree tree, PageBaseElementDataModel selectedNode, EditMode mode ){		
		super( mode, CommonOperations.getTranslation("tree.nodetype.baseelement") );

		this.tree = tree;
		this.nodeForModify = selectedNode;
		this.mode = mode;
		
		BaseElement baseElement = selectedNode.getElementBase();
		
		commonPre();
		
		//Name
		fieldName = new TextFieldComponent( baseElement.getName());
				
		//Identifier
		fieldIdentifier = new TextFieldComponent( baseElement.getIdentifier() );
	
		//Identifier type
	    IdentificationType idType = baseElement.getIdentificationType();	    
	   	if( idType.equals( IdentificationType.ID ) ){
	   		buttonID.setSelected(true);
	   	}else if( idType.equals( IdentificationType.CSS ) ){
	   		buttonCSS.setSelected(true);
	   	}
			
		//Variable	
		fieldVariable.setSelectedIndex( baseElement.getVariableSample().getIndex() );
	
		commonPost();
		
	}

	private void commonPost(){
		
		labelName = new JLabel( CommonOperations.getTranslation("editor.title.name") + ": ");
		labelIdentifier = new JLabel( CommonOperations.getTranslation("editor.title.identifier") + ": ");
		JLabel labelIdentifierType = new JLabel( CommonOperations.getTranslation("editor.title.identifiertype") + ": ");
		JLabel labelVariable = new JLabel( CommonOperations.getTranslation("editor.title.variable") + ": " );
		
		this.add( labelName, fieldName );
		this.add( labelIdentifier, fieldIdentifier );
		this.add( labelIdentifierType, buttonID );
		this.add( new JLabel(), buttonCSS );
		this.add( labelVariable, fieldVariable );
		
	}
	
	private void commonPre(){
		
		//Identifier tipus
		buttonID = new RadioButtonComponent( CommonOperations.getTranslation("editor.title.identifiertype.id") );
		buttonCSS = new RadioButtonComponent( CommonOperations.getTranslation("editor.title.identifiertype.css") );
		ButtonGroup group = new ButtonGroup();
		group.add(buttonID);
		group.add(buttonCSS);
		
		//Variable
		fieldVariable = new ComboBoxComponent<>();
		fieldVariable.addItem( VariableSample.getVariableSampleByIndex(0).getTranslatedName() );
		fieldVariable.addItem( VariableSample.getVariableSampleByIndex(1).getTranslatedName() );
		fieldVariable.addItem( VariableSample.getVariableSampleByIndex(2).getTranslatedName() );
		
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
							CommonOperations.getTranslation("editor.errormessage.emptyfield"), 
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
										CommonOperations.getTranslation("editor.errormessage.duplicateelement"), 
										fieldName.getText(), 
										CommonOperations.getTranslation("tree.nodetype.elementbase") 
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
							CommonOperations.getTranslation("editor.errormessage.emptyfield"), 
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
			if( buttonID.isSelected() ){
				identificationType = IdentificationType.ID;
			}else if( buttonCSS.isSelected() ){
				identificationType = IdentificationType.CSS;
			}
			
			//TreePath pathToOpen = null;
			
			//Uj rogzites eseten
			if( null == mode ){
				
				//DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode)selectedNode.getParent();
				//int selectedNodeIndex = parentNode.getIndex( selectedNode );				
				BaseElement baseElement = new BaseElement( fieldName.getText(), fieldIdentifier.getText(), identificationType, variableSample  );				
				PageBaseElementDataModel newPageBaseElement = new PageBaseElementDataModel( baseElement );
				//parentNode.insert( newPageBaseElement, selectedNodeIndex);
			
				nodeForCapture.add( newPageBaseElement );
				
				//Ebbe a nodba kell majd visszaallni
				//pathToOpen = new TreePath(newPageBaseElement.getPath());
			
			//Modositas eseten
			}else if( mode.equals(EditMode.MODIFY ) ){
		
				BaseElement baseElement = nodeForModify.getElementBase(); 
				
				baseElement.setName( fieldName.getText() );
				baseElement.setIdentifier( fieldIdentifier.getText() );				
				baseElement.setVariableSample( variableSample );
				baseElement.setIdentificationType( identificationType );
				
				//Ebbe a nodba kell majd visszaallni
				//pathToOpen = new TreePath(nodeForModify.getPath());
					
			}
			
			//A fa-ban is modositja a nevet (ha az valtozott)
			tree.changed();
		}
		
	}
	
}
