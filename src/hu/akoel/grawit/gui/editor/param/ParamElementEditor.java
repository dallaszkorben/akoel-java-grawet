package hu.akoel.grawit.gui.editor.param;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.MessageFormat;
import java.util.LinkedHashMap;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.operations.ButtonOperation;
import hu.akoel.grawit.core.operations.CheckboxOperation;
import hu.akoel.grawit.core.operations.ElementOperationInterface;
import hu.akoel.grawit.core.operations.FieldParamElementOperation;
import hu.akoel.grawit.core.operations.FieldVariableOperation;
import hu.akoel.grawit.core.operations.LinkOperation;
import hu.akoel.grawit.core.operations.ListVariableOperation;
import hu.akoel.grawit.core.operations.RadioButtonOperation;
import hu.akoel.grawit.core.operations.TabOperation;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.BasePageDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamPageDataModel;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.variable.VariableElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.variable.VariableRootDataModel;
import hu.akoel.grawit.enums.ListSelectionType;
import hu.akoel.grawit.enums.Operation;
import hu.akoel.grawit.gui.editor.DataEditor;
import hu.akoel.grawit.gui.editors.component.ComboBoxComponent;
import hu.akoel.grawit.gui.editors.component.TextFieldComponent;
import hu.akoel.grawit.gui.editors.component.treeselector.BaseElementTreeSelectorComponent;
import hu.akoel.grawit.gui.editors.component.treeselector.ParamElementTreeSelectorComponent;
import hu.akoel.grawit.gui.editors.component.treeselector.VariableTreeSelectorComponent;
import hu.akoel.grawit.gui.tree.Tree;

import javax.swing.JLabel;
import javax.swing.tree.TreeNode;

public class ParamElementEditor extends DataEditor{
	
	private static final long serialVersionUID = -7285419881714492620L;
	
	private Tree tree;
	private ParamElementDataModel nodeForModify;
	private ParamPageDataModel nodeForCapture;
	private EditMode mode;
	
	private JLabel labelName;
	private TextFieldComponent fieldName;
	private JLabel labelOperation;
	private ComboBoxComponent<String> fieldOperation;
	private JLabel labelBaseElementSelector;
	private BaseElementTreeSelectorComponent fieldBaseElementSelector;	
	private JLabel labelVariableSelector;
	private VariableTreeSelectorComponent fieldVariableSelector;
	private JLabel labelFieldBaseElementSelector;
	private BaseElementTreeSelectorComponent fieldFieldBaseElementSelector;
	private JLabel labelListSelectionType;
	private ComboBoxComponent<String> fieldListSelectionType;
	
	private Operation operation;
	
	BaseRootDataModel baseRootDataModel;
	
	//private VariableRootDataModel rootDataModel;
	
	/**
	 *  Uj ParamPageElement rogzitese - Insert
	 *  
	 * @param tree
	 * @param selectedPage
	 */
	public ParamElementEditor( Tree tree, ParamPageDataModel selectedPage, ParamRootDataModel paramRootDataModel, VariableRootDataModel variableRootDataModel ){

		super( ParamElementDataModel.getModelNameToShowStatic());
		
		this.tree = tree;
		this.nodeForCapture = selectedPage;
		this.mode = null;

		commonPre(  paramRootDataModel, variableRootDataModel );
		
		//Name
		fieldName.setText( "" );

		//Base Element
		BasePageDataModel basePage = selectedPage.getBasePage();
		fieldBaseElementSelector = new BaseElementTreeSelectorComponent( basePage ); 

		operation = Operation.LINK;

		baseRootDataModel = (BaseRootDataModel)basePage.getRoot();
		
		commonPost();
	}
		
	/**
	 * 
	 * Mar letezo ParamPageElement modositasa vagy megtekintese
	 * 
	 * @param tree
	 * @param selectedElement
	 * @param mode
	 */
	public ParamElementEditor( Tree tree, ParamElementDataModel selectedElement, ParamRootDataModel paramRootDataModel, VariableRootDataModel variableRootDataModel, EditMode mode ){		

		super( mode, selectedElement.getNodeTypeToShow());

		this.tree = tree;
		this.nodeForModify = selectedElement;
		this.mode = mode;
	

		commonPre( paramRootDataModel, variableRootDataModel );
		
		//Name
		fieldName.setText( selectedElement.getName() );

		//Selector a BaseElement valasztashoz - A root a basePage (nem latszik)
		BaseElementDataModel baseElement = selectedElement.getBaseElement();
		BasePageDataModel basePage = ((ParamPageDataModel)selectedElement.getParent()).getBasePage();		
		fieldBaseElementSelector = new BaseElementTreeSelectorComponent( basePage, baseElement );
		
		//Operation
		operation = selectedElement.getElementOperation().getOperation();
		
//TODO ki kell majd javitani
		//selectedElement
		//fieldParameterElementSelector = new ParameterElementTreeSelectorComponent(rootDataModel);
		baseRootDataModel = (BaseRootDataModel)basePage.getRoot();
		
		
		commonPost();
		
	}

	private void commonPre( final ParamRootDataModel paramRootDataModel, final VariableRootDataModel variableRootDataModel ){
		
		
		//this.rootDataModel = rootDataModel;
		
		//Name
		fieldName = new TextFieldComponent();
		
		//List selection type
		fieldListSelectionType = new ComboBoxComponent<>();
		fieldListSelectionType.addItem( ListSelectionType.getListSelectionTypeByOrder(0).getTranslatedName());
		fieldListSelectionType.addItem( ListSelectionType.getListSelectionTypeByOrder(1).getTranslatedName());
		fieldListSelectionType.addItem( ListSelectionType.getListSelectionTypeByOrder(2).getTranslatedName());
		
		//Operation
		fieldOperation = new ComboBoxComponent<>();
		for(int i = 0; i < Operation.getSize(); i++ ){
			fieldOperation.addItem( Operation.getOperationByIndex(i).getTranslatedName() );
		}
		fieldOperation.addItemListener( new ItemListener() {
			
			private boolean hasBeenHere = false;
			
			@Override
			public void itemStateChanged(ItemEvent e) {

				int index = fieldOperation.getSelectedIndex();					

				//Ha megvaltoztattam a tipust
				if( e.getStateChange() == java.awt.event.ItemEvent.SELECTED ){ 
					
					 operation = Operation.getOperationByIndex(index);
					 
					 //Mindenkeppen torolni kell, ha letezett
					 if( null != fieldVariableSelector ){
						 ParamElementEditor.this.remove( labelVariableSelector, fieldVariableSelector.getComponent() );
						 ParamElementEditor.this.remove( labelListSelectionType, fieldListSelectionType );						
						 ParamElementEditor.this.repaint();
					 }else if( null != fieldFieldBaseElementSelector ){
						 ParamElementEditor.this.remove( labelFieldBaseElementSelector, fieldFieldBaseElementSelector );
						 ParamElementEditor.this.repaint();
					 }
					 
					 //LIST
					 if( operation.equals( Operation.LIST_VARIABLE ) ){
						
						 //Ha mar volt valtoztatas, vagy uj ParameterElem szerkesztes tortenik 
						 if( hasBeenHere || null == nodeForModify ){
							 
							 //Akkor uresen kell kapnom a mezot
							 fieldVariableSelector = new VariableTreeSelectorComponent( variableRootDataModel );
							 fieldListSelectionType.setSelectedIndex( ListSelectionType.BYVISIBLETEXT.getIndex() );
							 
						 //Ha viszont most van itt eloszor es a ParameterElem modositasa tortenik
						 }else{
							 ListVariableOperation op = (ListVariableOperation)nodeForModify.getElementOperation();
							 
							 //akkor latnom kell a kivalasztott tartalmat
							 fieldVariableSelector = new VariableTreeSelectorComponent( variableRootDataModel, op.getVariableElement() );
							 fieldListSelectionType.setSelectedIndex(op.getListSelectionType().getIndex());
						 }
							
						 ParamElementEditor.this.add(labelListSelectionType, fieldListSelectionType );
						 ParamElementEditor.this.add( labelVariableSelector, fieldVariableSelector ); 
						 
					 //FIELD_VARIABLE
					 }else if( operation.equals( Operation.FIELD_VARIABLE ) ){
							 
						 //Ha mar volt valtoztatas, vagy uj ParameterElem szerkesztes tortenik 
						 if( hasBeenHere || null == nodeForModify ){
							 
							 //Akkor uresen kell kapnom a mezot
							 fieldVariableSelector = new VariableTreeSelectorComponent( variableRootDataModel );
						 
						 //Ha viszont most van itt eloszor es a ParameterElem modositasa tortenik
						 }else{
							 
							 FieldVariableOperation op = (FieldVariableOperation)nodeForModify.getElementOperation();
							 
							 //akkor latnom kell a kivalasztott tartalmat
							 fieldVariableSelector = new VariableTreeSelectorComponent( variableRootDataModel, op.getVariableElement() );
						 }
							
						 ParamElementEditor.this.add( labelVariableSelector, fieldVariableSelector );
//						 ParamElementEditor.this.revalidate();

					 //FIELD_ELEMENT
					 }else if( operation.equals( Operation.FIELD_ELEMENT ) ){
								 
						 //Ha mar volt valtoztatas, vagy uj ParameterElem szerkesztes tortenik 
						 if( hasBeenHere || null == nodeForModify ){
								 
							 //Akkor uresen kell kapnom a mezot
							 fieldFieldBaseElementSelector = new BaseElementTreeSelectorComponent( baseRootDataModel );
							 
						 //Ha viszont most van itt eloszor es a ParameterElem modositasa tortenik
						 }else{
								 
							 FieldParamElementOperation op = (FieldParamElementOperation)nodeForModify.getElementOperation();
					 
							 //akkor latnom kell a kivalasztott tartalmat
							 fieldFieldBaseElementSelector = new BaseElementTreeSelectorComponent( baseRootDataModel, op.getBaseElement() );
						 }
								
						 ParamElementEditor.this.add( labelFieldBaseElementSelector, fieldFieldBaseElementSelector );
							 
					 }else if( operation.equals( Operation.BUTTON ) ){
/*						 if( null != fieldParameterElementSelector ){
							 ParamElementEditor.this.remove(fieldParameterElementSelector.getComponent());
						 }
*/						
					 }else if( operation.equals( Operation.CHECKBOX ) ){
/*						 if( null != fieldParameterElementSelector ){
							 ParamElementEditor.this.remove(fieldParameterElementSelector.getComponent());
						 }
*/						
					 }else if( operation.equals( Operation.RADIOBUTTON ) ){
/*						 if( null != fieldParameterElementSelector ){
							 ParamElementEditor.this.remove(fieldParameterElementSelector.getComponent());
						 }
*/						
					 }else if( operation.equals( Operation.LINK ) ){
/*						 if( null != fieldParameterElementSelector ){
							 ParamElementEditor.this.remove(fieldParameterElementSelector.getComponent());
						 }					
*/				 
					}else if( operation.equals( Operation.TAB ) ){
/*						 if( null != fieldParameterElementSelector ){
						 	ParamElementEditor.this.remove(fieldParameterElementSelector.getComponent());
						 }					
					*/					 }
					 hasBeenHere = true;						
				}				
			}
		});
	}
	
	private void commonPost(){
		
		labelName = new JLabel( CommonOperations.getTranslation("editor.label.name") + ": ");
		labelOperation = new JLabel( CommonOperations.getTranslation("editor.label.param.operation") + ": ");
		labelBaseElementSelector = new JLabel( CommonOperations.getTranslation("editor.label.param.baseelement") + ": " );
		labelVariableSelector = new JLabel( CommonOperations.getTranslation("editor.label.param.variable" ) + ": " );
		labelFieldBaseElementSelector = new JLabel( CommonOperations.getTranslation("editor.label.param.paramelement" ) + ": " );
		labelListSelectionType = new JLabel( CommonOperations.getTranslation("editor.label.param.operation.list" ) + ": " );
		
		this.add( labelName, fieldName );
		this.add( labelBaseElementSelector, fieldBaseElementSelector );
		this.add( labelOperation, fieldOperation );
		//this.add( labelParameterElementSelector, fieldParameterElementSelector );

		//Arra kenyszeritem, hogy az elso igazi valasztas is kivaltson egy esemenyt
		fieldOperation.setSelectedIndex(-1);		
		fieldOperation.setSelectedIndex( operation.getIndex() );	
		
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
		
		//Nincs nev megadva
		if( fieldName.getText().length() == 0 ){
			errorList.put( 
					fieldName,
					MessageFormat.format(
							CommonOperations.getTranslation("editor.errormessage.emptyfield"), 
							"'"+labelName.getText()+"'"
					)
			);
		
		//Nincs BaseElement kivalasztva
		}else if( null == fieldBaseElementSelector.getSelectedDataModel()){
			errorList.put( 
					fieldBaseElementSelector,
					MessageFormat.format(
							CommonOperations.getTranslation("editor.errormessage.emptyfield"), 
							"'"+labelBaseElementSelector.getText()+"'"
					)
			);	
			
		//Van lista valasztas, de nincs valtozo
		}else if( operation.equals(Operation.LIST_VARIABLE) && null == fieldVariableSelector.getSelectedDataModel() ){
			errorList.put( 
					fieldVariableSelector,
					MessageFormat.format(
							CommonOperations.getTranslation("editor.errormessage.emptyfield"), 
							"'"+labelVariableSelector.getText()+"'"
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
				if( levelNode instanceof ParamElementDataModel ){
					
					//Ha azonos a nev azzal amit most mentenek
					if( ((ParamElementDataModel) levelNode).getName().equals( fieldName.getText() ) ){
					
						//Ha rogzites van, vagy ha modositas, de a vizsgalt node kulonbozik a modositott-tol
						if( null == mode || ( mode.equals( EditMode.MODIFY ) && !levelNode.equals(nodeForModify) ) ){
							
							errorList.put( 
								fieldName, 
								MessageFormat.format( 
										CommonOperations.getTranslation("editor.errormessage.duplicateelement"), 
										fieldName.getText(), 
										CommonOperations.getTranslation("tree.nodetype.param.element") 
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
			
			if( operation.equals( Operation.FIELD_VARIABLE ) ){
				VariableElementDataModel variableElementDataModel = fieldVariableSelector.getSelectedDataModel();			
				elementOperation = new FieldVariableOperation( variableElementDataModel );
			
			}else if( operation.equals( Operation.FIELD_ELEMENT ) ){
				BaseElementDataModel paramElementDataModel = fieldFieldBaseElementSelector.getSelectedDataModel();			
				elementOperation = new FieldParamElementOperation( paramElementDataModel );
				
			}else if( operation.equals( Operation.LIST_VARIABLE ) ){
				VariableElementDataModel variableElementDataModel = fieldVariableSelector.getSelectedDataModel();
				ListSelectionType listSelectionType = ListSelectionType.getListSelectionTypeByOrder( fieldListSelectionType.getSelectedIndex() );
				elementOperation = new ListVariableOperation( listSelectionType, variableElementDataModel );
				
			}else if( operation.equals( Operation.LINK ) ){
				elementOperation = new LinkOperation();
			}else if( operation.equals( Operation.BUTTON ) ){
				elementOperation = new ButtonOperation();
			}else if( operation.equals( Operation.CHECKBOX ) ){
				elementOperation = new CheckboxOperation();
			}else if( operation.equals( Operation.RADIOBUTTON ) ){
				elementOperation = new RadioButtonOperation();
			}else if( operation.equals( Operation.TAB ) ){
				elementOperation = new TabOperation();
			}else {
				elementOperation = new LinkOperation();
			}
					
			BaseElementDataModel baseElement = fieldBaseElementSelector.getSelectedDataModel();
			
			//Uj rogzites eseten
			if( null == mode ){			
				
				ParamElementDataModel newParamElement = new ParamElementDataModel( fieldName.getText(), baseElement, elementOperation  );			
				
				nodeForCapture.add( newParamElement );
				
			//Modositas eseten
			}else if( mode.equals(EditMode.MODIFY ) ){
		
				nodeForModify.setName( fieldName.getText() );
				nodeForModify.setOperation( elementOperation );
				nodeForModify.setBaseElement(baseElement);			
			}
			
			//A fa-ban is modositja a nevet (ha az valtozott)
			tree.changed();
		}
		
	}
	
}
