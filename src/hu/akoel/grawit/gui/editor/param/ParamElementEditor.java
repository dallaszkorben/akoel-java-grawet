package hu.akoel.grawit.gui.editor.param;

import java.awt.Component;
import java.text.MessageFormat;
import java.util.LinkedHashMap;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.operations.ElementOperationAdapter;
import hu.akoel.grawit.core.treenodedatamodel.BaseElementDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.ParamDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseCollectorDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamCollectorDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamNormalCollectorDataModel;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.variable.VariableRootDataModel;
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

public class ParamElementEditor extends DataEditor{
	
	private static final long serialVersionUID = -7285419881714492620L;
	
	private Tree tree;
	private ParamElementDataModel nodeForModify;	
	private ParamDataModelAdapter nodeForCapture;
	private EditMode mode;
	
	private JLabel labelName;
	private TextFieldComponent fieldName;

	private JLabel labelBaseElementSelector;
	private BaseElementTreeSelectorComponent fieldBaseElementSelector;	
	
	private JLabel labelElementTypeSelector;
	private ElementTypeComponentFullInterface<?> elementTypeComponent;
	
	BaseRootDataModel baseRootDataModel;
	VariableRootDataModel variableRootDataModel;
	
	/**
	 *  Uj ParamPageElement rogzitese - Insert
	 *  
	 * @param tree
	 * @param selectedPage
	 */
	public ParamElementEditor( Tree tree, ParamCollectorDataModelAdapter selectedPage, BaseRootDataModel baseRootDataModel, ParamRootDataModel paramRootDataModel, VariableRootDataModel variableRootDataModel ){

		super( ParamElementDataModel.getModelNameToShowStatic());
		
		this.tree = tree;
		this.nodeForCapture = selectedPage;
		this.mode = null;
		
		//this.baseRootDataModel = baseRootDataModel;

		commonPre( baseRootDataModel, paramRootDataModel, variableRootDataModel );
		
		//Name
		fieldName.setText( "" );

BaseCollectorDataModel baseCollector = ((ParamNormalCollectorDataModel)selectedPage).getBaseCollector();		
fieldBaseElementSelector = new BaseElementTreeSelectorComponent( baseRootDataModel, baseCollector, null );		
		
/*		if( selectedPage instanceof ParamNormalCollectorDataModel ){

			//Base Element
			BaseCollectorDataModel basePage = ((ParamNormalCollectorDataModel)selectedPage).getBaseCollector();
			if( null != basePage ){
				fieldBaseElementSelector = new BaseElementTreeSelectorComponent( basePage );
				baseRootDataModel = (BaseRootDataModel)basePage.getRoot();
			
			}else{
				fieldBaseElementSelector = new BaseElementTreeSelectorComponent( baseRootDataModel );
			
			}
		}else{
			fieldBaseElementSelector = new BaseElementTreeSelectorComponent( baseRootDataModel );
		}
*/			
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
	//public ParamElementEditor( Tree tree, ParamElementDataModel selectedElement, BaseRootDataModel baseRootDataModel, ParamRootDataModel paramRootDataModel, VariableRootDataModel variableRootDataModel, EditMode mode ){		
	public ParamElementEditor( Tree tree, ParamElementDataModel selectedElement, BaseRootDataModel baseRootDataModel, ParamRootDataModel paramRootDataModel, VariableRootDataModel variableRootDataModel, EditMode mode ){

		super( mode, selectedElement.getNodeTypeToShow());

		this.tree = tree;
		this.nodeForModify = selectedElement;
		this.mode = mode;	
		
		commonPre( baseRootDataModel, paramRootDataModel, variableRootDataModel );
		
		//Name
		fieldName.setText( selectedElement.getName() );

		//Selector a BaseElement valasztashoz - A root a basePage (nem latszik)
		BaseElementDataModelAdapter baseElement = selectedElement.getBaseElement();
		
fieldBaseElementSelector = new BaseElementTreeSelectorComponent( baseRootDataModel, null, baseElement );
/*		
		ParamDataModelAdapter parent = (ParamDataModelAdapter)selectedElement.getParent();
		if( parent instanceof ParamNormalCollectorDataModel ){
		
			BaseCollectorDataModel basePage = ((ParamNormalCollectorDataModel)selectedElement.getParent()).getBaseCollector();
			if( null != basePage ){
				fieldBaseElementSelector = new BaseElementTreeSelectorComponent( basePage, baseElement );
				baseRootDataModel = (BaseRootDataModel)basePage.getRoot();
			
			}else{
				fieldBaseElementSelector = new BaseElementTreeSelectorComponent( baseRootDataModel, baseElement );
			}
		}else{
			fieldBaseElementSelector = new BaseElementTreeSelectorComponent( baseRootDataModel, baseElement );
		}
*/		
		commonPost( baseElement );
		
	}

	private void commonPre( final BaseRootDataModel baseRootDataModel, final ParamRootDataModel paramRootDataModel, final VariableRootDataModel variableRootDataModel ){
				
		//Name
		fieldName = new TextFieldComponent();
		
		this.baseRootDataModel = baseRootDataModel;
		this.variableRootDataModel = variableRootDataModel;

	}
	
	private void commonPost(BaseElementDataModelAdapter baseElement ){
		
//		if( null != basePage ){
//			baseRootDataModel = (BaseRootDataModel)basePage.getRoot();
//		}	
		
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
				private void change(){
					BaseElementDataModelAdapter baseElement = ParamElementEditor.this.fieldBaseElementSelector.getSelectedDataModel();
					changeOperation( baseElement );
				}
			});
		}
		
		labelName = new JLabel( CommonOperations.getTranslation("editor.label.name") + ": ");
		labelBaseElementSelector = new JLabel( CommonOperations.getTranslation("editor.label.param.baseelement") + ": " );
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
		ParamElementEditor.this.remove( labelElementTypeSelector, elementTypeComponent.getComponent() );
		
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
				
			elementTypeComponent = new ScriptElementTypeComponentFull<ScriptElementTypeOperationsFullListEnum>( baseElement.getElementType(), elementOperation, baseRootDataModel, variableRootDataModel);  
			
		//FIELD
		}else if( baseElement.getElementType().name().equals( ElementTypeListEnum.FIELD.name() ) ){
			
			elementTypeComponent = new FieldElementTypeComponentFull<FieldElementTypeOperationsFullListEnum>( baseElement.getElementType(), elementOperation, baseRootDataModel, variableRootDataModel);  
			
		//TEXT
		}else if( baseElement.getElementType().name().equals(  ElementTypeListEnum.TEXT.name() ) ){

			elementTypeComponent = new TextElementTypeComponentFull<TextElementTypeOperationsFullListEnum>( baseElement.getElementType(), elementOperation, baseRootDataModel, variableRootDataModel );
			
		//LINK	
		}else if( baseElement.getElementType().name().equals(  ElementTypeListEnum.LINK.name() ) ){

			elementTypeComponent = new LinkElementTypeComponentFull<LinkElementTypeOperationsFullListEnum>( baseElement.getElementType(), elementOperation, baseRootDataModel, variableRootDataModel );
			
		//LIST
		}else if( baseElement.getElementType().name().equals(  ElementTypeListEnum.LIST.name() ) ){
			
			elementTypeComponent = new ListElementTypeComponentFull<ListElementTypeOperationsFullListEnum>( baseElement.getElementType(), elementOperation, baseRootDataModel, variableRootDataModel );
			
		//BUTTON
		}else if( baseElement.getElementType().name().equals(  ElementTypeListEnum.BUTTON.name() ) ){
			
			elementTypeComponent = new ButtonElementTypeComponentFull<ButtonElementTypeOperationsFullListEnum>( baseElement.getElementType(), elementOperation );
			
		//RADIOBUTTON
		}else if( baseElement.getElementType().name().equals(  ElementTypeListEnum.RADIOBUTTON.name() ) ){

			elementTypeComponent = new RadiobuttonElementTypeComponentFull<RadiobuttonElementTypeOperationsFullListEnum>( baseElement.getElementType(), elementOperation, baseRootDataModel, variableRootDataModel );
			
		//CHECKBOX
		}else if( baseElement.getElementType().name().equals(  ElementTypeListEnum.CHECKBOX.name() ) ){
			
			elementTypeComponent = new CheckboxElementTypeComponentFull<CheckboxElementTypeOperationsFullListEnum>( baseElement.getElementType(), elementOperation, baseRootDataModel, variableRootDataModel );
					
		}		
		
		//Elhelyezi az ujat		
		ParamElementEditor.this.add( labelElementTypeSelector, elementTypeComponent.getComponent() );
		ParamElementEditor.this.repaint();
		
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
			
			BaseElementDataModelAdapter baseElement = fieldBaseElementSelector.getSelectedDataModel();
			ElementOperationAdapter elementOperation = elementTypeComponent.getElementOperation();			

			//Uj rogzites eseten
			if( null == mode ){			
				
				ParamElementDataModel newParamElement = new ParamElementDataModel( fieldName.getText(), baseElement, elementOperation );			
				
				nodeForCapture.add( newParamElement );
				
			//Modositas eseten
			}else if( mode.equals(EditMode.MODIFY ) ){
		
				nodeForModify.setName( fieldName.getText() );
				nodeForModify.setBaseElement(baseElement);
				nodeForModify.setOperation( elementOperation );
			}
			
			//A fa-ban is modositja a nevet (ha az valtozott)
			tree.changed();
		}
		
	}
	
}
