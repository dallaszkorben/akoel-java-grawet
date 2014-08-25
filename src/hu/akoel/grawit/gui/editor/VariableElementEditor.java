package hu.akoel.grawit.gui.editor;

import java.awt.Component;
import java.text.MessageFormat;
import java.util.LinkedHashMap;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.elements.VariableElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.nodes.VariableNodeDataModel;
import hu.akoel.grawit.gui.editor.component.TextFieldComponent;
import hu.akoel.grawit.gui.editor.component.VariableTypeComponent;
import hu.akoel.grawit.gui.tree.Tree;

import javax.swing.JLabel;
import javax.swing.tree.TreeNode;

public class VariableElementEditor extends DataEditor{
	
	private static final long serialVersionUID = -7285419881714492620L;
	
	private Tree tree;
	private VariableElementDataModel nodeForModify;
	private VariableNodeDataModel nodeForCapture;
	private EditMode mode;
	
	private JLabel labelName;
	private TextFieldComponent fieldName;
	private JLabel labelVariableType;
	private VariableTypeComponent fieldVariableType;
	
	/**
	 *  Uj VariableElement rogzitese - Insert
	 *  
	 * @param tree
	 * @param selectedNode
	 */
	public VariableElementEditor( Tree tree, VariableNodeDataModel selectedNode ){
		super( CommonOperations.getTranslation( "tree.nodetype.variableelement" ) );

		this.tree = tree;
		this.nodeForCapture = selectedNode;
		this.mode = null;

		commonPre();
		
		//Name
		fieldName.setText( "" );
		
		commonPost();
	}
		
	/**
	 * 
	 * Mar letezo VariableElement modositasa vagy megtekintese
	 * 
	 * @param tree
	 * @param selectedElement
	 * @param mode
	 */
	public VariableElementEditor( Tree tree, VariableElementDataModel selectedElement, EditMode mode ){		
		super( mode, CommonOperations.getTranslation("tree.nodetype.variableelement") );

		this.tree = tree;
		this.nodeForModify = selectedElement;
		this.mode = mode;
	
		commonPre();
		
		//Name
		fieldName.setText( selectedElement.getName() );
		
		//Variable Type
		//Tipus beallitas 
		fieldVariableType.setType(selectedElement.getElementParameter().getType());
		//Valtozo beallitas
		for( int i = 0; i < selectedElement.getElementParameter().getParameterNumber(); i++ ){
			fieldVariableType.setParameterValue( selectedElement.getElementParameter().getParameterValue(i), i);
		}
				
		commonPost();
		
	}

	private void commonPre(){
		
		//Name
		fieldName = new TextFieldComponent();
	
		//VariableTypeSelector		
		fieldVariableType = new VariableTypeComponent();
		
	}
	
	private void commonPost(){
		
		labelName = new JLabel( CommonOperations.getTranslation("editor.title.name") + ": ");
		labelVariableType = new JLabel( CommonOperations.getTranslation("editor.title.variabletype") + ": ");
		
		this.add( labelName, fieldName );
		this.add( labelVariableType, fieldVariableType );
		
		fieldVariableType.revalidate();

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
				if( levelNode instanceof VariableElementDataModel ){
					
					//Ha azonos a nev
					if( ((VariableElementDataModel) levelNode).getName().equals( fieldName.getText() ) ){
					
						//Ha rogzites van, vagy ha modositas, de a vizsgalt node kulonbozik a modositott-tol
						if( null == mode || ( mode.equals( EditMode.MODIFY ) && !levelNode.equals(nodeForModify) ) ){
							
							errorList.put( 
								fieldName, 
								MessageFormat.format( 
										CommonOperations.getTranslation("editor.errormessage.duplicateelement"), 
										fieldName.getText(), 
										CommonOperations.getTranslation("tree.nodetype.variableelement") 
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
			
			//Uj rogzites eseten
			if( null == mode ){			

				fieldVariableType.setParameterName(fieldName.getText());				
				VariableElementDataModel newParamElement = new VariableElementDataModel( fieldVariableType.getElementParameter() );	
			
				nodeForCapture.add( newParamElement );
				
			//Modositas eseten
			}else if( mode.equals(EditMode.MODIFY ) ){
		
//				nodeForModify.setName( fieldName.getText() );
//				nodeForModify.setOperation( elementOperation );
//				nodeForModify.setBaseElement(baseElement);
				
			}
			
			//A fa-ban is modositja a nevet (ha az valtozott)
			tree.changed();
		}
		
	}
	
}
