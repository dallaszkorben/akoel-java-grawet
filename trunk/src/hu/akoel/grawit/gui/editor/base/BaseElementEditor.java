package hu.akoel.grawit.gui.editor.base;

import java.awt.Component;
import java.text.MessageFormat;
import java.util.LinkedHashMap;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.BasePageDataModel;
import hu.akoel.grawit.enums.SelectorType;
import hu.akoel.grawit.enums.list.ElementTypeListEnum;
import hu.akoel.grawit.enums.list.VariableSampleListEnum;
import hu.akoel.grawit.gui.editor.DataEditor;
import hu.akoel.grawit.gui.editors.component.ComboBoxComponent;
import hu.akoel.grawit.gui.editors.component.RadioButtonComponent;
import hu.akoel.grawit.gui.editors.component.TextFieldComponent;
import hu.akoel.grawit.gui.tree.Tree;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.tree.TreeNode;

public class BaseElementEditor extends DataEditor{
	
	private static final long serialVersionUID = 165396704460481021L;
	
	private Tree tree;
	private BaseElementDataModel nodeForModify;
	private BasePageDataModel nodeForCapture;
	private EditMode mode;
	
	private JLabel labelName;
	private TextFieldComponent fieldName;
	private JLabel labelFrame;
	private TextFieldComponent fieldFrame;
	private JLabel labelIdentifier;
	private TextFieldComponent fieldIdentifier;
	private ComboBoxComponent<ElementTypeListEnum> comboElementType;
	private ComboBoxComponent<VariableSampleListEnum> comboVariable;
	private RadioButtonComponent buttonID;
	private RadioButtonComponent buttonCSS;
	
	//Insert
	public BaseElementEditor( Tree tree, BasePageDataModel selectedNode ){

		super( BaseElementDataModel.getModelNameToShowStatic());

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
    	
    	//Element type
    	comboElementType.setSelectedIndex( 0 );
    	
    	//Variable
		comboVariable.setSelectedIndex( 0 );
		
		commonPost();
	}
		
	
	
	//Modositas vagy View
	public BaseElementEditor( Tree tree, BaseElementDataModel selectedNode, EditMode mode ){		

		super( mode, selectedNode.getNodeTypeToShow());

		this.tree = tree;
		this.nodeForModify = selectedNode;
		this.mode = mode;
		
		commonPre();
		
		//Name
		fieldName = new TextFieldComponent( selectedNode.getName() );
		
		//Frame
		fieldFrame = new TextFieldComponent( selectedNode.getFrame() );
				
		//Identifier
		fieldIdentifier = new TextFieldComponent( selectedNode.getSelector() );
	
		//Identifier type
	    SelectorType idType = selectedNode.getSelectorType();	    
	   	if( idType.equals( SelectorType.ID ) ){
	   		buttonID.setSelected(true);
	   	}else if( idType.equals( SelectorType.CSS ) ){
	   		buttonCSS.setSelected(true);
	   	}
			
	   	//Element type
	   	comboElementType.setSelectedIndex( selectedNode.getElementType().getIndex() );
	   	
		//Variable	
		comboVariable.setSelectedIndex( selectedNode.getVariableSample().getIndex() );
	
		commonPost();
		
	}
	
	private void commonPre(){
		
		//Identifier tipus
		buttonID = new RadioButtonComponent( CommonOperations.getTranslation("editor.label.base.identifiertype.id") );
		buttonCSS = new RadioButtonComponent( CommonOperations.getTranslation("editor.label.base.identifiertype.css") );
		ButtonGroup group = new ButtonGroup();
		group.add(buttonID);
		group.add(buttonCSS);
		
		//Element type
		comboElementType = new ComboBoxComponent<>();
		for(int i = 0; i < ElementTypeListEnum.getSize(); i++ ){
			comboElementType.addItem( ElementTypeListEnum.getElementTypeByIndex(i) );
		}
		
		//Variable
		comboVariable = new ComboBoxComponent<>();
		for(int i = 0; i < VariableSampleListEnum.getSize(); i++ ){
			comboVariable.addItem( VariableSampleListEnum.getVariableSampleByIndex(i) );
		}
	
	}

	private void commonPost(){
		
		labelName = new JLabel( CommonOperations.getTranslation("editor.label.name") + ": ");
		labelFrame = new JLabel( CommonOperations.getTranslation("editor.label.base.frame") + ": ");
		labelIdentifier = new JLabel( CommonOperations.getTranslation("editor.label.base.identifier") + ": ");
		JLabel labelIdentifierType = new JLabel( CommonOperations.getTranslation("editor.label.base.identifiertype") + ": ");
		JLabel labelElementType = new JLabel( CommonOperations.getTranslation("editor.label.base.elementtype") + ": ");
		JLabel labelVariable = new JLabel( CommonOperations.getTranslation("editor.label.base.variablesample") + ": " );
		
		this.add( labelName, fieldName );
		this.add( labelElementType, comboElementType );
		this.add( labelFrame, fieldFrame );
		this.add( labelIdentifier, fieldIdentifier );
		this.add( labelIdentifierType, buttonID );
		this.add( new JLabel(), buttonCSS );
		this.add( labelVariable, comboVariable );
		
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
										CommonOperations.getTranslation("tree.nodetype.base.element") 
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

			//Element type
			ElementTypeListEnum elementType = null;
			elementType = (ElementTypeListEnum)comboElementType.getSelectedItem();
			
			//Variable sample
			VariableSampleListEnum variableSample = null;
			//int selectedIndex = comboVariable.getSelectedIndex();
			//variableSample = VariableSample.getVariableSampleByIndex( selectedIndex );
			variableSample = (VariableSampleListEnum)comboVariable.getSelectedItem();
			
			SelectorType identificationType = null;
			if( buttonID.isSelected() ){
				identificationType = SelectorType.ID;
			}else if( buttonCSS.isSelected() ){
				identificationType = SelectorType.CSS;
			}
			
			//TreePath pathToOpen = null;
			
			//Uj rogzites eseten
			if( null == mode ){
			
				BaseElementDataModel newBaseElement = new BaseElementDataModel( fieldName.getText(), elementType, fieldIdentifier.getText(), identificationType, variableSample, fieldFrame.getText()  );				
				//BaseElementDataModel newPageBaseElement = new BaseElementDataModel( baseElement );
			
				nodeForCapture.add( newBaseElement );
				
				//Ebbe a nodba kell majd visszaallni
				//pathToOpen = new TreePath(newPageBaseElement.getPath());
			
			//Modositas eseten
			}else if( mode.equals(EditMode.MODIFY ) ){
				
				nodeForModify.setName( fieldName.getText() );
				nodeForModify.setFrame( fieldFrame.getText() );
				nodeForModify.setIdentifier( fieldIdentifier.getText() );
				nodeForModify.setElementType(elementType);
				nodeForModify.setVariableSample( variableSample );
				nodeForModify.setIdentificationType( identificationType );
				
			}
			
			//A fa-ban is modositja a nevet (ha az valtozott)
			tree.changed();
		}
		
	}
	
}
