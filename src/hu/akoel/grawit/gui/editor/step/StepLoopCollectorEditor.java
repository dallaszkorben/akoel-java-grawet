package hu.akoel.grawit.gui.editor.step;

import java.awt.Component;
import java.text.MessageFormat;
import java.util.LinkedHashMap;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.operations.ElementOperationAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseElementDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.constant.ConstantRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.step.StepElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.step.StepFolderDataModel;
import hu.akoel.grawit.core.treenodedatamodel.step.StepLoopCollectorDataModel;
import hu.akoel.grawit.enums.list.ElementTypeListEnum;
import hu.akoel.grawit.enums.list.elementtypeoperations.compare.CheckboxElementTypeOperationsCompareListEnum;
import hu.akoel.grawit.enums.list.elementtypeoperations.compare.FieldElementTypeOperationsCompareListEnum;
import hu.akoel.grawit.enums.list.elementtypeoperations.compare.LinkElementTypeOperationsCompareListEnum;
import hu.akoel.grawit.enums.list.elementtypeoperations.compare.ListElementTypeOperationsCompareListEnum;
import hu.akoel.grawit.enums.list.elementtypeoperations.compare.RadiobuttonElementTypeOperationsCompareListEnum;
import hu.akoel.grawit.enums.list.elementtypeoperations.compare.TextElementTypeOperationsCompareListEnum;
import hu.akoel.grawit.gui.editor.DataEditor;
import hu.akoel.grawit.gui.editors.component.IntegerFieldComponent;
import hu.akoel.grawit.gui.editors.component.TextAreaComponent;
import hu.akoel.grawit.gui.editors.component.TextFieldComponent;
import hu.akoel.grawit.gui.editors.component.elementtype.compare.CheckboxElementTypeComponentCompare;
import hu.akoel.grawit.gui.editors.component.elementtype.compare.ElementTypeComponentCompareInterface;
import hu.akoel.grawit.gui.editors.component.elementtype.compare.EmptyElementTypeComponentCompare;
import hu.akoel.grawit.gui.editors.component.elementtype.compare.FieldElementTypeComponentCompare;
import hu.akoel.grawit.gui.editors.component.elementtype.compare.LinkElementTypeComponentCompare;
import hu.akoel.grawit.gui.editors.component.elementtype.compare.ListElementTypeComponentCompare;
import hu.akoel.grawit.gui.editors.component.elementtype.compare.RadiobuttonElementTypeComponentCompare;
import hu.akoel.grawit.gui.editors.component.elementtype.compare.TextElementTypeComponentCompare;
import hu.akoel.grawit.gui.editors.component.treeselector.BaseElementTreeSelectorComponent;
import hu.akoel.grawit.gui.tree.Tree;

import javax.swing.JLabel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.tree.TreeNode;

public class StepLoopCollectorEditor extends DataEditor{

	private static final long serialVersionUID = -8459964508143979145L;
	
	private Tree tree;
	private StepLoopCollectorDataModel nodeForModify;
	private StepFolderDataModel nodeForCapture;
	private EditMode mode;
	
	private JLabel labelName;
	private TextFieldComponent fieldName;
	private TextAreaComponent fieldDetails;

	private JLabel labelOneLoopLength;
	private IntegerFieldComponent fieldOneLoopLength;
	
	private JLabel labelMaxLoopNumber;
	private IntegerFieldComponent fieldMaxLoopNumber;

	private JLabel labelBaseElementSelector;
	private BaseElementTreeSelectorComponent fieldCompareBaseElementSelector;	
	
	private JLabel labelElementTypeSelector;
	private ElementTypeComponentCompareInterface<?> elementTypeComponent;
	
	BaseRootDataModel baseRootDataModel;
	ConstantRootDataModel constantRootDataModel;

	//Itt biztos beszuras van
	public StepLoopCollectorEditor( Tree tree, StepFolderDataModel selectedNode, ConstantRootDataModel constantRootDataModel, BaseRootDataModel baseRootDataModel ){
		
		super( StepLoopCollectorDataModel.getModelNameToShowStatic() );
		
		this.tree = tree;
		this.nodeForCapture = selectedNode;
		this.mode = null;
		
		commonPre( baseRootDataModel, constantRootDataModel );
		
		//Name
		fieldName = new TextFieldComponent( "" );
		
		//Details
		fieldDetails = new TextAreaComponent( "", NOTE_ROWS, 15);
				
		//One loop length
		fieldOneLoopLength = new IntegerFieldComponent( "1" );
		
		//Max loop number
		fieldMaxLoopNumber = new IntegerFieldComponent( "1" );
		
		fieldCompareBaseElementSelector = new BaseElementTreeSelectorComponent( baseRootDataModel );			

		commonPost( null );
		
	}
	
	//Itt modositas van
	public StepLoopCollectorEditor( Tree tree, StepLoopCollectorDataModel selectedControlLoop, ConstantRootDataModel constantRootDataModel, BaseRootDataModel baseRootDataModel, EditMode mode ){		
		
		super( mode, selectedControlLoop.getNodeTypeToShow());

		this.tree = tree;
		this.nodeForModify = selectedControlLoop;
		this.mode = mode;		
	
		commonPre( baseRootDataModel, constantRootDataModel );
		
		//Name
		fieldName = new TextFieldComponent( selectedControlLoop.getName());

		//Details
		fieldDetails = new TextAreaComponent( selectedControlLoop.getDetails(), NOTE_ROWS, 15);
		
		//Selector a BaseElement valasztashoz - A root a basePage (nem latszik)
		BaseElementDataModelAdapter baseElement = selectedControlLoop.getCompareBaseElement();
		
		//One loop length
		fieldOneLoopLength = new IntegerFieldComponent( selectedControlLoop.getOneLoopLength().toString() );
		
		//Max loop number
		fieldMaxLoopNumber = new IntegerFieldComponent( selectedControlLoop.getMaxLoopNumber().toString() );
		
		fieldCompareBaseElementSelector = new BaseElementTreeSelectorComponent( baseRootDataModel, baseElement, true );
		
		commonPost( baseElement );
	}
	
	private void commonPre( final BaseRootDataModel baseRootDataModel, final ConstantRootDataModel constantRootDataModel ){
		
		//Name
		fieldName = new TextFieldComponent();
		
		this.baseRootDataModel = baseRootDataModel;
		this.constantRootDataModel = constantRootDataModel;

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
					BaseElementDataModelAdapter baseElement = StepLoopCollectorEditor.this.fieldCompareBaseElementSelector.getSelectedDataModel();
					changeOperation( baseElement );
				}
			});
		}
		
		labelName = new JLabel( CommonOperations.getTranslation("editor.label.name") + ": " );
		labelBaseElementSelector = new JLabel( CommonOperations.getTranslation("editor.label.step.baseelement") + ": " );
		labelElementTypeSelector = new JLabel( "");
		labelOneLoopLength = new JLabel( CommonOperations.getTranslation("editor.label.step.loop.onelooplength") + ": " );
		labelMaxLoopNumber = new JLabel( CommonOperations.getTranslation("editor.label.step.loop.maxloopnumber") + ": " );
		JLabel labelDetails = new JLabel( CommonOperations.getTranslation("editor.label.details") + ": ");	
		
		this.add( labelName, fieldName );
		this.add( labelDetails, fieldDetails );
		this.add( labelOneLoopLength, fieldOneLoopLength );
		this.add( labelMaxLoopNumber, fieldMaxLoopNumber );
		
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
		StepLoopCollectorEditor.this.remove( labelElementTypeSelector, elementTypeComponent.getComponent() );
		
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
		
			elementTypeComponent = new EmptyElementTypeComponentCompare();
	
		//SCRIPT
		}else if( baseElement.getElementType().name().equals( ElementTypeListEnum.SCRIPT.name() ) ){
		
			elementTypeComponent = new EmptyElementTypeComponentCompare();
			
		//FIELD
		}else if( baseElement.getElementType().name().equals( ElementTypeListEnum.FIELD.name() ) ){
			
			elementTypeComponent = new FieldElementTypeComponentCompare<FieldElementTypeOperationsCompareListEnum>( baseElement.getElementType(), elementOperation, baseRootDataModel, constantRootDataModel);  
			
		//TEXT
		}else if( baseElement.getElementType().name().equals(  ElementTypeListEnum.TEXT.name() ) ){

			elementTypeComponent = new TextElementTypeComponentCompare<TextElementTypeOperationsCompareListEnum>( baseElement.getElementType(), elementOperation, baseRootDataModel, constantRootDataModel );
			
		//LINK	
		}else if( baseElement.getElementType().name().equals(  ElementTypeListEnum.LINK.name() ) ){

			elementTypeComponent = new LinkElementTypeComponentCompare<LinkElementTypeOperationsCompareListEnum>( baseElement.getElementType(), elementOperation, baseRootDataModel, constantRootDataModel );
			
		//LIST
		}else if( baseElement.getElementType().name().equals(  ElementTypeListEnum.LIST.name() ) ){
			
			elementTypeComponent = new ListElementTypeComponentCompare<ListElementTypeOperationsCompareListEnum>( baseElement.getElementType(), elementOperation, baseRootDataModel, constantRootDataModel );
			
		//BUTTON
		}else if( baseElement.getElementType().name().equals(  ElementTypeListEnum.BUTTON.name() ) ){
			
			elementTypeComponent = new EmptyElementTypeComponentCompare();
//			elementTypeComponent = new ButtonElementTypeComponentCompare<ButtonElementTypeOperationsCompareListEnum>( baseElement.getElementType(), elementOperation );
			
		//RADIOBUTTON
		}else if( baseElement.getElementType().name().equals(  ElementTypeListEnum.RADIOBUTTON.name() ) ){

			elementTypeComponent = new RadiobuttonElementTypeComponentCompare<RadiobuttonElementTypeOperationsCompareListEnum>( baseElement.getElementType(), elementOperation, baseRootDataModel, constantRootDataModel );
			
		//CHECKBOX
		}else if( baseElement.getElementType().name().equals(  ElementTypeListEnum.CHECKBOX.name() ) ){
			
			elementTypeComponent = new CheckboxElementTypeComponentCompare<CheckboxElementTypeOperationsCompareListEnum>( baseElement.getElementType(), elementOperation, baseRootDataModel, constantRootDataModel );
					
		}		
		
		//Elhelyezi az ujat		
		StepLoopCollectorEditor.this.add( labelElementTypeSelector, elementTypeComponent.getComponent() );
		StepLoopCollectorEditor.this.repaint();
		
	}
	
	
	@Override
	public void save() {
		
		//
		//Ertekek trimmelese
		//
		fieldName.setText( fieldName.getText().trim() );
				
		Integer oneLoopLength = Integer.valueOf( fieldOneLoopLength.getText() );
		Integer maxLoopNumber = Integer.valueOf( fieldMaxLoopNumber.getText() );
		
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
			
			BaseElementDataModelAdapter baseElement = fieldCompareBaseElementSelector.getSelectedDataModel();
			ElementOperationAdapter elementOperation = elementTypeComponent.getElementOperation();			

			//Uj rogzites eseten
			if( null == mode ){			
				
				StepLoopCollectorDataModel newParamLoop = new StepLoopCollectorDataModel( fieldName.getText(), fieldDetails.getText(), baseElement, oneLoopLength, maxLoopNumber, elementOperation );
				
				nodeForCapture.add( newParamLoop );
				
				//A fa-ban modositja a strukturat
				//tree.refreshTreeAfterStructureChanged( nodeForCapture, nodeForCapture );
				tree.refreshTreeAfterStructureChanged( nodeForCapture );
								
			//Modositas eseten
			}else if( mode.equals(EditMode.MODIFY ) ){
		
				nodeForModify.setName( fieldName.getText() );
				nodeForModify.setDetails( fieldDetails.getText() );
				nodeForModify.setOneLoopLength(oneLoopLength);
				nodeForModify.setMaxLoopNumber(maxLoopNumber);
				nodeForModify.setCompareBaseElement(baseElement);
				nodeForModify.setOperation( elementOperation );
				
				//A fa-ban modositja a nevet (ha az valtozott)
				//tree.refreshTreeAfterChanged( nodeForModify );
				tree.refreshTreeAfterChanged();
			}

		}
		
	}
	
}