package hu.akoel.grawit.gui.editor.step;

import java.awt.Component;
import java.text.MessageFormat;
import java.util.LinkedHashMap;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.operations.ElementOperationAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseElementDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.constant.ConstantRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.step.StepCollectorDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.step.StepElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.step.StepRootDataModel;
import hu.akoel.grawit.enums.list.ElementTypeListEnum;
import hu.akoel.grawit.enums.list.elementtypeoperations.full.ButtonElementTypeOperationsFullListEnum;
import hu.akoel.grawit.enums.list.elementtypeoperations.full.CheckboxElementTypeOperationsFullListEnum;
import hu.akoel.grawit.enums.list.elementtypeoperations.full.FieldElementTypeOperationsFullListEnum;
import hu.akoel.grawit.enums.list.elementtypeoperations.full.LinkElementTypeOperationsFullListEnum;
import hu.akoel.grawit.enums.list.elementtypeoperations.full.ListElementTypeOperationsFullListEnum;
import hu.akoel.grawit.enums.list.elementtypeoperations.full.RadiobuttonElementTypeOperationsFullListEnum;
import hu.akoel.grawit.enums.list.elementtypeoperations.full.ScriptElementTypeOperationsFullListEnum;
import hu.akoel.grawit.enums.list.elementtypeoperations.full.TextElementTypeOperationsFullListEnum;
import hu.akoel.grawit.gui.editor.DataEditor;
import hu.akoel.grawit.gui.editors.component.TextFieldComponent;
import hu.akoel.grawit.gui.editors.component.elementtype.full.ButtonElementTypeComponentFull;
import hu.akoel.grawit.gui.editors.component.elementtype.full.CheckboxElementTypeComponentFull;
import hu.akoel.grawit.gui.editors.component.elementtype.full.ElementTypeComponentFullInterface;
import hu.akoel.grawit.gui.editors.component.elementtype.full.EmptyElementTypeComponentFull;
import hu.akoel.grawit.gui.editors.component.elementtype.full.FieldElementTypeComponentFull;
import hu.akoel.grawit.gui.editors.component.elementtype.full.LinkElementTypeComponentFull;
import hu.akoel.grawit.gui.editors.component.elementtype.full.ListElementTypeComponentFull;
import hu.akoel.grawit.gui.editors.component.elementtype.full.RadiobuttonElementTypeComponentFull;
import hu.akoel.grawit.gui.editors.component.elementtype.full.ScriptElementTypeComponentFull;
import hu.akoel.grawit.gui.editors.component.elementtype.full.TextElementTypeComponentFull;
import hu.akoel.grawit.gui.editors.component.treeselector.BaseElementTreeSelectorComponent;
import hu.akoel.grawit.gui.tree.Tree;

import javax.swing.JLabel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.tree.TreeNode;

public class StepElementEditor extends DataEditor{
	
	private static final long serialVersionUID = -7285419881714492620L;
	
	private Tree tree;
	private StepElementDataModel nodeForModify;	

	private StepCollectorDataModelAdapter nodeForCapture;
	private EditMode mode;
	
	private JLabel labelName;
	private TextFieldComponent fieldName;

	private JLabel labelBaseElementSelector;
	private BaseElementTreeSelectorComponent fieldBaseElementSelector;	
	
	private JLabel labelElementTypeSelector;
	private ElementTypeComponentFullInterface<?> elementTypeComponent;
	
	BaseRootDataModel baseRootDataModel;
	ConstantRootDataModel constantRootDataModel;
	
	/**
	 *  Uj ParamPageElement rogzitese - Insert
	 *  
	 * @param tree
	 * @param selectedPage
	 */
	public StepElementEditor( Tree tree, StepCollectorDataModelAdapter selectedPage, BaseRootDataModel baseRootDataModel, StepRootDataModel paramRootDataModel, ConstantRootDataModel constantRootDataModel ){

		super( StepElementDataModel.getModelNameToShowStatic());
		
		this.tree = tree;
		this.nodeForCapture = selectedPage;
		this.mode = null;

		commonPre( baseRootDataModel, paramRootDataModel, constantRootDataModel );
		
		//Name
		fieldName.setText( "" );

			BaseElementDataModelAdapter lastBaseElement = ((StepCollectorDataModelAdapter)selectedPage).getLastBaseElement();
			fieldBaseElementSelector = new BaseElementTreeSelectorComponent( baseRootDataModel, lastBaseElement, false );		
					
		commonPost( null );
	}
		

	
	/**
	 * 
	 * Mar letezo ParamPageElement modositasa vagy megtekintese
	 * 
	 * @param tree
	 * @param selectedElement
	 * @param mode
	 */
	public StepElementEditor( Tree tree, StepElementDataModel selectedElement, BaseRootDataModel baseRootDataModel, StepRootDataModel paramRootDataModel, ConstantRootDataModel constantRootDataModel, EditMode mode ){

		super( mode, selectedElement.getNodeTypeToShow());

		this.tree = tree;
		this.nodeForModify = selectedElement;
		this.mode = mode;	
		
		commonPre( baseRootDataModel, paramRootDataModel, constantRootDataModel );
		
		//Name
		fieldName.setText( selectedElement.getName() );

		//Selector a BaseElement valasztashoz - A root a basePage (nem latszik)
		BaseElementDataModelAdapter baseElement = selectedElement.getBaseElement();
		
		fieldBaseElementSelector = new BaseElementTreeSelectorComponent( baseRootDataModel, baseElement, true );
		
		commonPost( baseElement );
		
	}

	private void commonPre( final BaseRootDataModel baseRootDataModel, final StepRootDataModel paramRootDataModel, final ConstantRootDataModel constantRootDataModel ){
				
		//Name
		fieldName = new TextFieldComponent();
		
		this.baseRootDataModel = baseRootDataModel;
		this.constantRootDataModel = constantRootDataModel;

	}
	
	private void commonPost(BaseElementDataModelAdapter baseElement ){
		
		if( null != fieldBaseElementSelector ){
		
			fieldBaseElementSelector.getDocument().addDocumentListener( new DocumentListener(){
				@Override
				public void insertUpdate(DocumentEvent e) {
					change();				
				}
				@Override
				public void removeUpdate(DocumentEvent e) {
					change();
				}
				@Override
				public void changedUpdate(DocumentEvent e) {
					change();
				}			
				
				//Ha megvaltozott a BaseElement
				private void change(){
					
					//Akkor ezt a valtozast jelzem a changeOperation()-nak
					BaseElementDataModelAdapter baseElement = StepElementEditor.this.fieldBaseElementSelector.getSelectedDataModel();
					changeOperation( baseElement );
				}
			});
		}
		
		labelName = new JLabel( CommonOperations.getTranslation("editor.label.name") + ": ");
		labelBaseElementSelector = new JLabel( CommonOperations.getTranslation("editor.label.step.baseelement") + ": " );
		labelElementTypeSelector = new JLabel( "");
		
		this.add( labelName, fieldName );
		
		if( null != fieldBaseElementSelector ){
			this.add( labelBaseElementSelector, fieldBaseElementSelector );
		}
		
		elementTypeComponent = new EmptyElementTypeComponentFull();
		changeOperation(baseElement);
		
	}
	
	/**
	 * 
	 * Az aktualis elemtipusnak megfelelo komponenst mutatja meg
	 *  
	 * @param baseElement
	 */
	private void changeOperation( BaseElementDataModelAdapter baseElement ){

		//Eltavolitja az ott levot
		StepElementEditor.this.remove( labelElementTypeSelector, elementTypeComponent.getComponent() );
		
		ElementOperationAdapter elementOperation;
		
		//Uj
		if( null == nodeForModify ){
			elementOperation = null;
		
		//Modositas
		}else{
			elementOperation = nodeForModify.getElementOperation();
		}		
		
		//Ha uj es elso alkalom
		if( null == baseElement ){		 
		
			elementTypeComponent = new EmptyElementTypeComponentFull();
	
		//SCRIPT
		}else if( baseElement.getElementType().name().equals( ElementTypeListEnum.SCRIPT.name() ) ){
				
			elementTypeComponent = new ScriptElementTypeComponentFull<ScriptElementTypeOperationsFullListEnum>( baseElement, elementOperation, baseRootDataModel, constantRootDataModel);  

//		//VARIABLE
//		}else if( baseElement.getElementType().name().equals( ElementTypeListEnum.VARIABLE.name() ) ){
					
//			elementTypeComponent = new VariableElementTypeComponentFull<VariableElementTypeOperationsFullListEnum>( baseElement, elementOperation, baseRootDataModel, constantRootDataModel);
//TODO folytatni			

		//FIELD
		}else if( baseElement.getElementType().name().equals( ElementTypeListEnum.FIELD.name() ) ){
			
			elementTypeComponent = new FieldElementTypeComponentFull<FieldElementTypeOperationsFullListEnum>( baseElement, elementOperation, baseRootDataModel, constantRootDataModel);  
			
		//TEXT
		}else if( baseElement.getElementType().name().equals(  ElementTypeListEnum.TEXT.name() ) ){

			elementTypeComponent = new TextElementTypeComponentFull<TextElementTypeOperationsFullListEnum>( baseElement, elementOperation, baseRootDataModel, constantRootDataModel );
			
		//LINK	
		}else if( baseElement.getElementType().name().equals(  ElementTypeListEnum.LINK.name() ) ){

			elementTypeComponent = new LinkElementTypeComponentFull<LinkElementTypeOperationsFullListEnum>( baseElement, elementOperation, baseRootDataModel, constantRootDataModel );
			
		//LIST
		}else if( baseElement.getElementType().name().equals(  ElementTypeListEnum.LIST.name() ) ){
			
			elementTypeComponent = new ListElementTypeComponentFull<ListElementTypeOperationsFullListEnum>( baseElement, elementOperation, baseRootDataModel, constantRootDataModel );

		//BUTTON
		}else if( baseElement.getElementType().name().equals(  ElementTypeListEnum.BUTTON.name() ) ){
			
			elementTypeComponent = new ButtonElementTypeComponentFull<ButtonElementTypeOperationsFullListEnum>( baseElement, elementOperation );
		
		//RADIOBUTTON
		}else if( baseElement.getElementType().name().equals(  ElementTypeListEnum.RADIOBUTTON.name() ) ){

			elementTypeComponent = new RadiobuttonElementTypeComponentFull<RadiobuttonElementTypeOperationsFullListEnum>( baseElement, elementOperation, baseRootDataModel, constantRootDataModel );
			
		//CHECKBOX
		}else if( baseElement.getElementType().name().equals(  ElementTypeListEnum.CHECKBOX.name() ) ){
			
			elementTypeComponent = new CheckboxElementTypeComponentFull<CheckboxElementTypeOperationsFullListEnum>( baseElement, elementOperation, baseRootDataModel, constantRootDataModel );					
		}		
		
		//Elhelyezi az ujat		
		StepElementEditor.this.add( labelElementTypeSelector, elementTypeComponent.getComponent() );
		StepElementEditor.this.repaint();		
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
		}else if( null != fieldBaseElementSelector && null == fieldBaseElementSelector.getSelectedDataModel()){
			errorList.put( 
					fieldBaseElementSelector,
					MessageFormat.format(
							CommonOperations.getTranslation("editor.errormessage.emptyfield"), 
							"'"+labelBaseElementSelector.getText()+"'"
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
				if( levelNode instanceof StepElementDataModel ){
					
					//Ha azonos a nev azzal amit most mentenek
					if( ((StepElementDataModel) levelNode).getName().equals( fieldName.getText() ) ){
					
						//Ha rogzites van, vagy ha modositas, de a vizsgalt node kulonbozik a modositott-tol
						if( null == mode || ( mode.equals( EditMode.MODIFY ) && !levelNode.equals(nodeForModify) ) ){
							
							errorList.put( 
								fieldName, 
								MessageFormat.format( 
										CommonOperations.getTranslation("editor.errormessage.duplicateelement"), 
										fieldName.getText(), 
										CommonOperations.getTranslation("tree.nodetype.step.element") 
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
			
			BaseElementDataModelAdapter baseElement = fieldBaseElementSelector.getSelectedDataModel();
			ElementOperationAdapter elementOperation = elementTypeComponent.getElementOperation();			

			//Uj rogzites eseten
			if( null == mode ){			
				
				StepElementDataModel newParamElement = new StepElementDataModel( fieldName.getText(), baseElement, elementOperation );				
				nodeForCapture.add( newParamElement );
				
				//A new ParamElementDataModel()-ben nem vegrehajthato, mert akkor meg nincs a tree-hez rendelve es igy nincs szuloje
				newParamElement.setBaseElement(baseElement);
				
				//A fa-ban modositja a strukturat
				//tree.refreshTreeAfterStructureChanged( nodeForCapture, nodeForCapture );
				tree.refreshTreeAfterStructureChanged( nodeForCapture );
				
			//Modositas eseten
			}else if( mode.equals(EditMode.MODIFY ) ){
		
				nodeForModify.setName( fieldName.getText() );
				nodeForModify.setBaseElement(baseElement);
				nodeForModify.setOperation( elementOperation );
				
				//A fa-ban modositja a nevet (ha az valtozott)
				//tree.refreshTreeAfterChanged( nodeForModify );
				tree.refreshTreeAfterChanged();
			}
		}		
	}	
}
