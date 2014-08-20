package hu.akoel.grawit.gui.editor;

import java.awt.Component;
import java.text.MessageFormat;
import java.util.LinkedHashMap;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.datamodel.elements.BaseElementDataModel;
import hu.akoel.grawit.core.datamodel.pages.BasePageDataModel;
import hu.akoel.grawit.enums.IdentificationType;
import hu.akoel.grawit.enums.VariableSample;
import hu.akoel.grawit.gui.editor.component.ComboBoxComponent;
import hu.akoel.grawit.gui.editor.component.RadioButtonComponent;
import hu.akoel.grawit.gui.editor.component.TextFieldComponent;
import hu.akoel.grawit.gui.tree.BaseTree;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.tree.TreeNode;

public class BaseElementEditor extends DataEditor{
	
	private static final long serialVersionUID = 165396704460481021L;
	
	private BaseTree tree;
	private BaseElementDataModel nodeForModify;
	private BasePageDataModel nodeForCapture;
	private EditMode mode;
	
	private JLabel labelName;
	private TextFieldComponent fieldName;
	private JLabel labelFrame;
	private TextFieldComponent fieldFrame;
	private JLabel labelIdentifier;
	private TextFieldComponent fieldIdentifier;
	private ComboBoxComponent<String> fieldVariable;
	private RadioButtonComponent buttonID;
	private RadioButtonComponent buttonCSS;
	
	//Insert
	public BaseElementEditor( BaseTree tree, BasePageDataModel selectedNode ){
		super( CommonOperations.getTranslation("tree.nodetype.baseelement") );

		this.tree = tree;
		this.nodeForCapture = selectedNode;
		this.mode = null;

		commonPre();
		
		//Name
		fieldName = new TextFieldComponent( "" );
		
		//Frame
		fieldFrame = new TextFieldComponent( "" );

		//Identifier
		fieldIdentifier = new TextFieldComponent( "" );

		//Identifier type
    	buttonID.setSelected( true );
    	
    	//Variable
		fieldVariable.setSelectedIndex( 0 );
		
		commonPost();
	}
		
	
	
	//Modositas vagy View
	public BaseElementEditor( BaseTree tree, BaseElementDataModel selectedNode, EditMode mode ){		
		super( mode, CommonOperations.getTranslation("tree.nodetype.baseelement") );

		this.tree = tree;
		this.nodeForModify = selectedNode;
		this.mode = mode;
		
		commonPre();
		
		//Name
		fieldName = new TextFieldComponent( selectedNode.getName() );
		
		//Frame
		fieldFrame = new TextFieldComponent( selectedNode.getFrame() );
				
		//Identifier
		fieldIdentifier = new TextFieldComponent( selectedNode.getIdentifier() );
	
		//Identifier type
	    IdentificationType idType = selectedNode.getIdentificationType();	    
	   	if( idType.equals( IdentificationType.ID ) ){
	   		buttonID.setSelected(true);
	   	}else if( idType.equals( IdentificationType.CSS ) ){
	   		buttonCSS.setSelected(true);
	   	}
			
		//Variable	
		fieldVariable.setSelectedIndex( selectedNode.getVariableSample().getIndex() );
	
		commonPost();
		
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

	private void commonPost(){
		
		labelName = new JLabel( CommonOperations.getTranslation("editor.title.name") + ": ");
		labelFrame = new JLabel( CommonOperations.getTranslation("editor.title.frame") + ": ");
		labelIdentifier = new JLabel( CommonOperations.getTranslation("editor.title.identifier") + ": ");
		JLabel labelIdentifierType = new JLabel( CommonOperations.getTranslation("editor.title.identifiertype") + ": ");
		JLabel labelVariable = new JLabel( CommonOperations.getTranslation("editor.title.variable") + ": " );
		
		this.add( labelName, fieldName );
		this.add( labelFrame, fieldFrame );
		this.add( labelIdentifier, fieldIdentifier );
		this.add( labelIdentifierType, buttonID );
		this.add( new JLabel(), buttonCSS );
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
		
		//Empty Name
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
				if( levelNode instanceof BaseElementDataModel ){
					
					//Ha azonos a nev
					if( ((BaseElementDataModel) levelNode).getName().equals( fieldName.getText() ) ){
					
						//Ha rogzites van, vagy ha modositas, de a vizsgalt node kulonbozik a modositott-tol
						if( null == mode || ( mode.equals( EditMode.MODIFY ) && !levelNode.equals(nodeForModify) ) ){
							
							errorList.put( 
								fieldName, 
								MessageFormat.format( 
										CommonOperations.getTranslation("editor.errormessage.duplicateelement"), 
										fieldName.getText(), 
										CommonOperations.getTranslation("tree.nodetype.baseelement") 
								) 
							);
							break;
						}
					}
				}
			}
		}

		//Empty identifier
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
			
				BaseElementDataModel newBaseElement = new BaseElementDataModel( fieldName.getText(), fieldIdentifier.getText(), identificationType, variableSample  );				
				//BaseElementDataModel newPageBaseElement = new BaseElementDataModel( baseElement );
			
				nodeForCapture.add( newBaseElement );
				
				//Ebbe a nodba kell majd visszaallni
				//pathToOpen = new TreePath(newPageBaseElement.getPath());
			
			//Modositas eseten
			}else if( mode.equals(EditMode.MODIFY ) ){
				
				nodeForModify.setName( fieldName.getText() );
				nodeForModify.setFrame( fieldFrame.getText() );
				nodeForModify.setIdentifier( fieldIdentifier.getText() );				
				nodeForModify.setVariableSample( variableSample );
				nodeForModify.setIdentificationType( identificationType );
				
			}
			
			//A fa-ban is modositja a nevet (ha az valtozott)
			tree.changed();
		}
		
	}
	
}
