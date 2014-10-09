package hu.akoel.grawit.gui.editor.base;

import java.awt.Component;
import java.text.MessageFormat;
import java.util.LinkedHashMap;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.Properties;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.BasePageDataModel;
import hu.akoel.grawit.enums.SelectorType;
import hu.akoel.grawit.enums.list.ElementTypeListEnum;
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
	private RadioButtonComponent buttonID;
	private RadioButtonComponent buttonCSS;	
	private JLabel labelWaitingTime;
	private TextFieldComponent fieldWaitingTime;
	
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
		
    	//WaitingTime
    	fieldWaitingTime = new TextFieldComponent( "" );

		//Identifier type
    	buttonID.setSelected( true );
    	
    	//Element type
    	comboElementType.setSelectedIndex( 0 );
    	
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
		
		//Waiting time
		Integer waitingTime = selectedNode.getWaitingTime();
		if( null == waitingTime ){
			fieldWaitingTime = new TextFieldComponent( "" );
		}else{
			fieldWaitingTime = new TextFieldComponent( waitingTime.toString() );
		}
	
		//Identifier type
	    SelectorType idType = selectedNode.getSelectorType();	    
	   	if( idType.equals( SelectorType.ID ) ){
	   		buttonID.setSelected(true);
	   	}else if( idType.equals( SelectorType.CSS ) ){
	   		buttonCSS.setSelected(true);
	   	}
			
	   	//Element type
	   	comboElementType.setSelectedIndex( selectedNode.getElementType().getIndex() );
	   	
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
			
	}

	private void commonPost(){
		
		labelName = new JLabel( CommonOperations.getTranslation("editor.label.name") + ": ");
		labelFrame = new JLabel( CommonOperations.getTranslation("editor.label.base.frame") + ": ");
		labelIdentifier = new JLabel( CommonOperations.getTranslation("editor.label.base.identifier") + ": ");
		labelWaitingTime = new JLabel( CommonOperations.getTranslation("editor.label.base.waitingtime") + ": ");
		JLabel labelIdentifierType = new JLabel( CommonOperations.getTranslation("editor.label.base.identifiertype") + ": ");
		JLabel labelElementType = new JLabel( CommonOperations.getTranslation("editor.label.base.elementtype") + ": ");
		
    	//WaitingTime
		
		this.add( labelName, fieldName );
		this.add( labelElementType, comboElementType );
		this.add( labelFrame, fieldFrame );
		this.add( labelWaitingTime, fieldWaitingTime );
		this.add( labelIdentifier, fieldIdentifier );
		this.add( labelIdentifierType, buttonID );		
		this.add( new JLabel(), buttonCSS );
		
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
			
			SelectorType identificationType = null;
			if( buttonID.isSelected() ){
				identificationType = SelectorType.ID;
			}else if( buttonCSS.isSelected() ){
				identificationType = SelectorType.CSS;
			}
			
			Integer waitingTime = null;				
			try{
				waitingTime = new Integer( fieldWaitingTime.getText() );
			}catch( Exception e ){}
			
			//Uj rogzites eseten
			if( null == mode ){
								
				BaseElementDataModel newBaseElement = new BaseElementDataModel( fieldName.getText(), elementType, fieldIdentifier.getText(), identificationType, waitingTime, fieldFrame.getText()  );
			
				nodeForCapture.add( newBaseElement );
			
			//Modositas eseten
			}else if( mode.equals(EditMode.MODIFY ) ){
				
				nodeForModify.setName( fieldName.getText() );
				nodeForModify.setFrame( fieldFrame.getText() );
				nodeForModify.setIdentifier( fieldIdentifier.getText() );
				nodeForModify.setElementType(elementType);
				nodeForModify.setWaitingTime( waitingTime );
				nodeForModify.setIdentificationType( identificationType );
				
			}
			
			//A fa-ban is modositja a nevet (ha az valtozott)
			tree.changed();
		}
		
	}
	
}
