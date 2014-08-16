package hu.akoel.grawit.gui.editor;

import java.awt.Component;
import java.text.MessageFormat;
import java.util.LinkedHashMap;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.IdentificationType;
import hu.akoel.grawit.VariableSample;
import hu.akoel.grawit.core.elements.BaseElement;
import hu.akoel.grawit.core.elements.ParamElement;
import hu.akoel.grawit.core.operations.ButtonOperation;
import hu.akoel.grawit.core.operations.CheckboxOperation;
import hu.akoel.grawit.core.operations.ElementOperationInterface;
import hu.akoel.grawit.core.operations.ElementOperationInterface.Operation;
import hu.akoel.grawit.core.operations.FieldOperation;
import hu.akoel.grawit.core.operations.LinkOperation;
import hu.akoel.grawit.core.operations.RadioButtonOperation;
import hu.akoel.grawit.core.parameter.StringParameter;
import hu.akoel.grawit.gui.editor.component.ComboBoxComponent;
import hu.akoel.grawit.gui.editor.component.RadioButtonComponent;
import hu.akoel.grawit.gui.editor.component.TextFieldComponent;
import hu.akoel.grawit.gui.tree.ParamPageTree;
import hu.akoel.grawit.gui.tree.datamodel.PageBaseElementDataModel;
import hu.akoel.grawit.gui.tree.datamodel.ParamPageElementDataModel;
import hu.akoel.grawit.gui.tree.datamodel.ParamPagePageDataModel;

import javax.swing.JLabel;
import javax.swing.tree.TreeNode;

public class ParamPageElementEditor extends DataEditor{
	
	private static final long serialVersionUID = -7285419881714492620L;
	
	private ParamPageTree tree;
	private ParamPageElementDataModel nodeForModify;
	private ParamPagePageDataModel nodeForCapture;
	private EditMode mode;
	
	private JLabel labelName;
	private TextFieldComponent fieldName;
	private JLabel labelOperation;
	private ComboBoxComponent<String> fieldOperation;
	
	/**
	 *  Uj ParamPageElement rogzitese - Insert
	 *  
	 * @param tree
	 * @param selectedNode
	 */
	public ParamPageElementEditor( ParamPageTree tree, ParamPagePageDataModel selectedNode ){
		super( CommonOperations.getTranslation("tree.nodetype.paramelement") );

		this.tree = tree;
		this.nodeForCapture = selectedNode;
		this.mode = null;

		commonPre();
		
		//Name
		fieldName = new TextFieldComponent( "" );

    	//Variable
		fieldOperation.setSelectedIndex( 0 );
		
		commonPost();
	}
		
	/**
	 * 
	 * Mar letezo ParamPageElement modositasa vagy megtekintese
	 * @param tree
	 * @param selectedNode
	 * @param mode
	 */
	public ParamPageElementEditor( ParamPageTree tree, ParamPageElementDataModel selectedNode, EditMode mode ){		
		super( mode, CommonOperations.getTranslation("tree.nodetype.paramelement") );

		this.tree = tree;
		this.nodeForModify = selectedNode;
		this.mode = mode;
		
		BaseElement baseElement = selectedNode.getParamElement().getBaseElement();
		ParamElement paramElement = selectedNode.getParamElement();
		
		commonPre();
		
		//Name
		fieldName = new TextFieldComponent( baseElement.getName());

		//Operation
		Operation op = paramElement.getElementOperation().getOperation();
		fieldOperation.setSelectedIndex( op.getIndex() );
	
		commonPost();
		
	}

	private void commonPre(){
		
		//Operation
		fieldOperation = new ComboBoxComponent<>();		
		fieldOperation.addItem( Operation.getOperationByIndex(0).getTranslatedName() );
		fieldOperation.addItem( Operation.getOperationByIndex(1).getTranslatedName() );
		fieldOperation.addItem( Operation.getOperationByIndex(2).getTranslatedName() );
		fieldOperation.addItem( Operation.getOperationByIndex(3).getTranslatedName() );
		fieldOperation.addItem( Operation.getOperationByIndex(4).getTranslatedName() );
		
	}
	
	private void commonPost(){
		
		labelName = new JLabel( CommonOperations.getTranslation("editor.title.name") + ": ");
		labelOperation = new JLabel( CommonOperations.getTranslation("editor.title.operation") + ": ");
		
		this.add( labelName, fieldName );
		this.add( labelOperation, fieldOperation );
		
	}
		
	@Override
	public void save() {
		
		//
		//Ertekek trimmelese
		//
		fieldName.setText( fieldName.getText().trim() );
				
		//
		//Hibak eseten a hibas mezok osszegyujtese
		//
		
		//fieldName
		LinkedHashMap<Component, String> errorList = new LinkedHashMap<Component, String>();
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

		//Operation
		
		//Volt hiba
		if( errorList.size() != 0 ){
			
			//Hibajelzes
			this.errorAt( errorList );
		
		//Ha nem volt hiba akkor a valtozok veglegesitese
		}else{
			
			ElementOperationInterface elementOperation = null;
			Operation operation = Operation.getOperationByIndex( fieldOperation.getSelectedIndex() );
			if( operation.equals( Operation.FIELD ) ){
				elementOperation = new FieldOperation( new StringParameter("param1", "111" ) );
			}else if( operation.equals( Operation.LINK ) ){
				elementOperation = new LinkOperation();
			}else if( operation.equals( Operation.BUTTON ) ){
				elementOperation = new ButtonOperation();
			}else if( operation.equals( Operation.CHECKBOX ) ){
				elementOperation = new CheckboxOperation();
			}else if( operation.equals( Operation.RADIOBUTTON ) ){
				elementOperation = new RadioButtonOperation();
			}else {
				elementOperation = new LinkOperation();
			}
/*					
			//Uj rogzites eseten
			if( null == mode ){
				
				BaseElement baseElement = nodeForCapture.getParamElement().getBaseElement();
				ParamElement paramElement = nodeForCapture.getParamElement();
				
				ParamElement paramElement = new ParamElement( fieldName.getText(), elemetb  );				
				PageBaseElementDataModel newPageBaseElement = new PageBaseElementDataModel( elementBase );
			
				nodeForCapture.add( newPageBaseElement );
				
				//Ebbe a nodba kell majd visszaallni
				//pathToOpen = new TreePath(newPageBaseElement.getPath());
			
			//Modositas eseten
			}else if( mode.equals(EditMode.MODIFY ) ){
		
				ElementBase elementBase = nodeForModify.getElementBase(); 
				
				elementBase.setName( fieldName.getText() );
				elementBase.setIdentifier( fieldIdentifier.getText() );				
				elementBase.setVariableSample( variableSample );
				elementBase.setIdentificationType( identificationType );
				
				//Ebbe a nodba kell majd visszaallni
				//pathToOpen = new TreePath(nodeForModify.getPath());
					
			}
*/			
			//A fa-ban is modositja a nevet (ha az valtozott)
			tree.changed();
		}
		
	}
	
}
