package hu.akoel.grawit.gui.editor;

import java.awt.Component;
import java.text.MessageFormat;
import java.util.LinkedHashMap;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.elements.BaseElement;
import hu.akoel.grawit.core.elements.ParamElement;
import hu.akoel.grawit.core.operations.ButtonOperation;
import hu.akoel.grawit.core.operations.CheckboxOperation;
import hu.akoel.grawit.core.operations.ElementOperationInterface;
import hu.akoel.grawit.core.operations.ElementOperationInterface.Operation;
import hu.akoel.grawit.core.operations.FieldOperation;
import hu.akoel.grawit.core.operations.LinkOperation;
import hu.akoel.grawit.core.operations.RadioButtonOperation;
import hu.akoel.grawit.core.pages.BasePage;
import hu.akoel.grawit.core.parameter.StringParameter;
import hu.akoel.grawit.gui.editor.component.ComboBoxComponent;
import hu.akoel.grawit.gui.editor.component.BasePageElementSelectorComponent;
import hu.akoel.grawit.gui.editor.component.TextFieldComponent;
import hu.akoel.grawit.gui.tree.ParamPageTree;
import hu.akoel.grawit.gui.tree.datamodel.BasePageElementDataModel;
import hu.akoel.grawit.gui.tree.datamodel.BasePagePageDataModel;
import hu.akoel.grawit.gui.tree.datamodel.BasePageRootDataModel;
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
	private JLabel labelPageBaseElementSelector;
	private BasePageElementSelectorComponent fieldBasePageElementSelector;	
	
	/**
	 *  Uj ParamPageElement rogzitese - Insert
	 *  
	 * @param tree
	 * @param selectedNode
	 */
	public ParamPageElementEditor( ParamPageTree tree, ParamPagePageDataModel selectedNode, BasePageRootDataModel basePageRootDataModel ){
		super( CommonOperations.getTranslation("tree.nodetype.paramelement") );

		this.tree = tree;
		this.nodeForCapture = selectedNode;
		this.mode = null;

		commonPre();
		
		//Name
		fieldName.setText( "" );

		//Base Element
		BasePage basePage = selectedNode.getParamPage().getBasePage();
		BasePagePageDataModel basePagePageDataModel = CommonOperations.getBasePagePageDataModelByBasePage(basePageRootDataModel, basePage);
		fieldBasePageElementSelector = new BasePageElementSelectorComponent( basePagePageDataModel ); 

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
	public ParamPageElementEditor( ParamPageTree tree, ParamPageElementDataModel selectedNode, BasePageRootDataModel basePageRootDataModel, EditMode mode ){		
		super( mode, CommonOperations.getTranslation("tree.nodetype.paramelement") );

		this.tree = tree;
		this.nodeForModify = selectedNode;
		this.mode = mode;
	
		ParamElement paramElement = selectedNode.getParamElement();
		BaseElement baseElement = selectedNode.getParamElement().getBaseElement();	
		
		commonPre();
		
		//Name
		fieldName.setText( paramElement.getName() );

		//Base Element
		BasePagePageDataModel basePagePageDataModel = CommonOperations.getBasePagePageDataModelByBaseElement(basePageRootDataModel, baseElement);
				
		fieldBasePageElementSelector = new BasePageElementSelectorComponent( basePagePageDataModel, baseElement ); 
		
		//Operation
		Operation op = paramElement.getElementOperation().getOperation();
		fieldOperation.setSelectedIndex( op.getIndex() );
	
		commonPost();
		
	}

	private void commonPre(){
		
		//Name
		fieldName = new TextFieldComponent();
		
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
		labelPageBaseElementSelector = new JLabel("hello");
		
		this.add( labelName, fieldName );
		this.add( labelPageBaseElementSelector, fieldBasePageElementSelector );
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
				if( levelNode instanceof BasePageElementDataModel ){
					
					//Ha azonos a nev
					if( ((BasePageElementDataModel) levelNode).getBaseElement().getName().equals( fieldName.getText() ) ){
					
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
			
			//Megnezik hogy van-e masik azonos BaseElement
			childrenCount = nodeForSearch.getChildCount();
			for( int i = 0; i < childrenCount; i++ ){
				TreeNode levelNode = nodeForSearch.getChildAt( i );
				
				//Ha Element-rol van szo 
				if( levelNode instanceof ParamPageElementDataModel ){
				
					//Ha azonos a BaseElement					
					if( ((ParamPageElementDataModel)levelNode).getParamElement().getBaseElement().equals( fieldBasePageElementSelector.getBaseElement() ) ){
					
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
					
			BaseElement baseElement = fieldBasePageElementSelector.getBaseElement();
			
			//Uj rogzites eseten
			if( null == mode ){			
				
				ParamElement paramElement = new ParamElement( fieldName.getText(), baseElement, elementOperation  );				
				ParamPageElementDataModel newParamPageElement = new ParamPageElementDataModel( paramElement );
				
				nodeForCapture.add( newParamPageElement );
				
			//Modositas eseten
			}else if( mode.equals(EditMode.MODIFY ) ){
		
				ParamElement paramElement = nodeForModify.getParamElement(); 
				
				paramElement.setName( fieldName.getText() );
				paramElement.setOperation( elementOperation );
				paramElement.setBaseElement(baseElement);
				
			}
			
			//A fa-ban is modositja a nevet (ha az valtozott)
			tree.changed();
		}
		
	}
	
}
