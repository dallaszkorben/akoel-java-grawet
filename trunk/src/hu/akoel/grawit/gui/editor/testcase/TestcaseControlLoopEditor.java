package hu.akoel.grawit.gui.editor.testcase;

import java.awt.Component;
import java.text.MessageFormat;
import java.util.LinkedHashMap;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.operations.ElementOperationAdapter;
import hu.akoel.grawit.core.treenodedatamodel.BaseElementDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseCaseDataModel;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseControlLoopDataModel;
import hu.akoel.grawit.core.treenodedatamodel.variable.VariableRootDataModel;
import hu.akoel.grawit.enums.list.ElementTypeListEnum;
import hu.akoel.grawit.enums.list.elementtypeoperations.compare.ButtonElementTypeOperationsCompareListEnum;
import hu.akoel.grawit.enums.list.elementtypeoperations.compare.CheckboxElementTypeOperationsCompareListEnum;
import hu.akoel.grawit.enums.list.elementtypeoperations.compare.FieldElementTypeOperationsCompareListEnum;
import hu.akoel.grawit.enums.list.elementtypeoperations.compare.LinkElementTypeOperationsCompareListEnum;
import hu.akoel.grawit.enums.list.elementtypeoperations.compare.ListElementTypeOperationsCompareListEnum;
import hu.akoel.grawit.enums.list.elementtypeoperations.compare.RadiobuttonElementTypeOperationsCompareListEnum;
import hu.akoel.grawit.enums.list.elementtypeoperations.compare.ScriptElementTypeOperationsCompareListEnum;
import hu.akoel.grawit.enums.list.elementtypeoperations.compare.TextElementTypeOperationsCompareListEnum;
import hu.akoel.grawit.gui.editors.component.TextFieldComponent;
import hu.akoel.grawit.gui.editors.component.elementtype.compare.ButtonElementTypeComponentCompare;
import hu.akoel.grawit.gui.editors.component.elementtype.compare.CheckboxElementTypeComponentCompare;
import hu.akoel.grawit.gui.editors.component.elementtype.compare.ElementTypeComponentCompareInterface;
import hu.akoel.grawit.gui.editors.component.elementtype.compare.EmptyElementTypeComponentCompare;
import hu.akoel.grawit.gui.editors.component.elementtype.compare.FieldElementTypeComponentCompare;
import hu.akoel.grawit.gui.editors.component.elementtype.compare.LinkElementTypeComponentCompare;
import hu.akoel.grawit.gui.editors.component.elementtype.compare.ListElementTypeComponentCompare;
import hu.akoel.grawit.gui.editors.component.elementtype.compare.RadiobuttonElementTypeComponentCompare;
import hu.akoel.grawit.gui.editors.component.elementtype.compare.ScriptElementTypeComponentCompare;
import hu.akoel.grawit.gui.editors.component.elementtype.compare.TextElementTypeComponentCompare;
import hu.akoel.grawit.gui.editors.component.treeselector.BaseElementTreeSelectorComponent;
import hu.akoel.grawit.gui.tree.Tree;

import javax.swing.JLabel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.tree.TreeNode;

public class TestcaseControlLoopEditor extends TestcaseControlAdapter{

	private static final long serialVersionUID = -8459964508143979145L;
	
	private Tree tree;
	private TestcaseControlLoopDataModel nodeForModify;
	private TestcaseCaseDataModel nodeForCapture;
	private EditMode mode;
	
	private JLabel labelName;
	private TextFieldComponent fieldName;

	private JLabel labelBaseElementSelector;
	private BaseElementTreeSelectorComponent fieldCompareBaseElementSelector;	
	
	private JLabel labelElementTypeSelector;
	private ElementTypeComponentCompareInterface<?> elementTypeComponent;
	
	BaseRootDataModel baseRootDataModel;
	VariableRootDataModel variableRootDataModel;

	//Itt biztos beszuras van
	public TestcaseControlLoopEditor( Tree tree, TestcaseCaseDataModel selectedNode, BaseRootDataModel baseRootDataModel ){
		
		super( TestcaseControlLoopDataModel.getModelNameToShowStatic() );
		
		this.tree = tree;
		this.nodeForCapture = selectedNode;
		this.mode = null;
		
		commonPre( baseRootDataModel, variableRootDataModel );
		
		//Name
		fieldName = new TextFieldComponent( "" );
		
		fieldCompareBaseElementSelector = new BaseElementTreeSelectorComponent( baseRootDataModel );			

		commonPost( null );
		
	}
	
	//Itt modositas van
	public TestcaseControlLoopEditor( Tree tree, TestcaseControlLoopDataModel selectedControlLoop, BaseRootDataModel baseRootDataModel, EditMode mode ){		
		
		super( mode, selectedControlLoop.getNodeTypeToShow());

		this.tree = tree;
		this.nodeForModify = selectedControlLoop;
		this.mode = mode;		
		
		commonPre( baseRootDataModel, variableRootDataModel );
		
		//Name
		fieldName = new TextFieldComponent( selectedControlLoop.getName());

		//Selector a BaseElement valasztashoz - A root a basePage (nem latszik)
		BaseElementDataModelAdapter baseElement = selectedControlLoop.getCompareBaseElement();
		
		fieldCompareBaseElementSelector = new BaseElementTreeSelectorComponent( baseRootDataModel, baseElement );
		
		commonPost( baseElement );
	}
	
	private void commonPre( final BaseRootDataModel baseRootDataModel, final VariableRootDataModel variableRootDataModel ){
		
		//Name
		fieldName = new TextFieldComponent();
		
		this.baseRootDataModel = baseRootDataModel;
		this.variableRootDataModel = variableRootDataModel;

	}
	
	private void commonPost(BaseElementDataModelAdapter baseElement ){
		
		if( null != fieldCompareBaseElementSelector ){
		
			fieldCompareBaseElementSelector.getDocument().addDocumentListener( new DocumentListener(){
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
					BaseElementDataModelAdapter baseElement = TestcaseControlLoopEditor.this.fieldCompareBaseElementSelector.getSelectedDataModel();
					changeOperation( baseElement );
				}
			});
		}
		
		labelName = new JLabel( CommonOperations.getTranslation("editor.label.name") + ": ");
		labelBaseElementSelector = new JLabel( CommonOperations.getTranslation("editor.label.param.baseelement") + ": " );
		labelElementTypeSelector = new JLabel( "");
		
		this.add( labelName, fieldName );
		
		if( null != fieldCompareBaseElementSelector ){
			this.add( labelBaseElementSelector, fieldCompareBaseElementSelector );
		}
		
		elementTypeComponent = new EmptyElementTypeComponentCompare();
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
		TestcaseControlLoopEditor.this.remove( labelElementTypeSelector, elementTypeComponent.getComponent() );
		
		ElementOperationAdapter elementOperation;
		
		//Uj
//		if( null == nodeForModify ){
			elementOperation = null;
		
		//Modositas
//		}else{
//			elementOperation = nodeForModify.getElementOperation();
//		}		
		
		//Ha uj es elso alkalom
		if( null == baseElement ){		 
		
			elementTypeComponent = new EmptyElementTypeComponentCompare();
	
		//SCRIPT
		}else if( baseElement.getElementType().name().equals( ElementTypeListEnum.SCRIPT.name() ) ){
				
			elementTypeComponent = new ScriptElementTypeComponentCompare<ScriptElementTypeOperationsCompareListEnum>( baseElement.getElementType(), elementOperation, baseRootDataModel, variableRootDataModel);  
			
		//FIELD
		}else if( baseElement.getElementType().name().equals( ElementTypeListEnum.FIELD.name() ) ){
			
			elementTypeComponent = new FieldElementTypeComponentCompare<FieldElementTypeOperationsCompareListEnum>( baseElement.getElementType(), elementOperation, baseRootDataModel, variableRootDataModel);  
			
		//TEXT
		}else if( baseElement.getElementType().name().equals(  ElementTypeListEnum.TEXT.name() ) ){

			elementTypeComponent = new TextElementTypeComponentCompare<TextElementTypeOperationsCompareListEnum>( baseElement.getElementType(), elementOperation, baseRootDataModel, variableRootDataModel );
			
		//LINK	
		}else if( baseElement.getElementType().name().equals(  ElementTypeListEnum.LINK.name() ) ){

			elementTypeComponent = new LinkElementTypeComponentCompare<LinkElementTypeOperationsCompareListEnum>( baseElement.getElementType(), elementOperation, baseRootDataModel, variableRootDataModel );
			
		//LIST
		}else if( baseElement.getElementType().name().equals(  ElementTypeListEnum.LIST.name() ) ){
			
			elementTypeComponent = new ListElementTypeComponentCompare<ListElementTypeOperationsCompareListEnum>( baseElement.getElementType(), elementOperation, baseRootDataModel, variableRootDataModel );
			
		//BUTTON
		}else if( baseElement.getElementType().name().equals(  ElementTypeListEnum.BUTTON.name() ) ){
			
			elementTypeComponent = new ButtonElementTypeComponentCompare<ButtonElementTypeOperationsCompareListEnum>( baseElement.getElementType(), elementOperation );
			
		//RADIOBUTTON
		}else if( baseElement.getElementType().name().equals(  ElementTypeListEnum.RADIOBUTTON.name() ) ){

			elementTypeComponent = new RadiobuttonElementTypeComponentCompare<RadiobuttonElementTypeOperationsCompareListEnum>( baseElement.getElementType(), elementOperation, baseRootDataModel, variableRootDataModel );
			
		//CHECKBOX
		}else if( baseElement.getElementType().name().equals(  ElementTypeListEnum.CHECKBOX.name() ) ){
			
			elementTypeComponent = new CheckboxElementTypeComponentCompare<CheckboxElementTypeOperationsCompareListEnum>( baseElement.getElementType(), elementOperation, baseRootDataModel, variableRootDataModel );
					
		}		
		
		//Elhelyezi az ujat		
		TestcaseControlLoopEditor.this.add( labelElementTypeSelector, elementTypeComponent.getComponent() );
		TestcaseControlLoopEditor.this.repaint();
		
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
		}else if( null != fieldCompareBaseElementSelector && null == fieldCompareBaseElementSelector.getSelectedDataModel()){
			errorList.put( 
					fieldCompareBaseElementSelector,
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
			
			BaseElementDataModelAdapter baseElement = fieldCompareBaseElementSelector.getSelectedDataModel();
			ElementOperationAdapter elementOperation = elementTypeComponent.getElementOperation();			

			//Uj rogzites eseten
			if( null == mode ){			
				
				TestcaseControlLoopDataModel newTestcaseControlLoop = new TestcaseControlLoopDataModel(fieldName.getText(), baseElement, 10, elementOperation);
				
				nodeForCapture.add( newTestcaseControlLoop );
				
			//Modositas eseten
			}else if( mode.equals(EditMode.MODIFY ) ){
		
				nodeForModify.setName( fieldName.getText() );
				nodeForModify.setCompareBaseElement(baseElement);
				nodeForModify.setOperation( elementOperation );
			}
			
			//A fa-ban is modositja a nevet (ha az valtozott)
			tree.changed();
		}
		
	}
	
}