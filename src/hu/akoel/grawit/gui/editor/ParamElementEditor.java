package hu.akoel.grawit.gui.editor;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.MessageFormat;
import java.util.LinkedHashMap;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.operations.ButtonOperation;
import hu.akoel.grawit.core.operations.CheckboxOperation;
import hu.akoel.grawit.core.operations.ElementOperationInterface;
import hu.akoel.grawit.core.operations.ElementOperationInterface.Operation;
import hu.akoel.grawit.core.operations.LinkOperation;
import hu.akoel.grawit.core.operations.RadioButtonOperation;
import hu.akoel.grawit.core.treenodedatamodel.elements.BaseElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.elements.ParamElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.pages.BasePageDataModel;
import hu.akoel.grawit.core.treenodedatamodel.pages.ParamPageDataModel;
import hu.akoel.grawit.core.treenodedatamodel.roots.VariableRootDataModel;
import hu.akoel.grawit.gui.editor.component.BaseElementTreeSelectorComponent;
import hu.akoel.grawit.gui.editor.component.ComboBoxComponent;
import hu.akoel.grawit.gui.editor.component.ParameterElementTreeSelectorComponent;
import hu.akoel.grawit.gui.editor.component.TextFieldComponent;
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
	private JLabel labelPageBaseElementSelector;
	private BaseElementTreeSelectorComponent fieldBaseElementSelector;	
	private JLabel labelParameterElementSelector;
	private ParameterElementTreeSelectorComponent fieldParameterElementSelector;
	
	private Operation operation;
	
	private VariableRootDataModel rootDataModel;
	
	/**
	 *  Uj ParamPageElement rogzitese - Insert
	 *  
	 * @param tree
	 * @param selectedPage
	 */
	public ParamElementEditor( Tree tree, ParamPageDataModel selectedPage, VariableRootDataModel rootDataModel ){
		//super( CommonOperations.getTranslation("tree.nodetype.paramelement") );
		super( ParamElementDataModel.getModelNameToShowStatic());
		
		this.tree = tree;
		this.nodeForCapture = selectedPage;
		this.rootDataModel = rootDataModel;
		this.mode = null;

		commonPre();
		
		//Name
		fieldName.setText( "" );

		//Base Element
		BasePageDataModel basePage = selectedPage.getBasePage();
		fieldBaseElementSelector = new BaseElementTreeSelectorComponent( basePage ); 

		operation = Operation.LINK;
    	//Variable
//		fieldOperation.setSelectedIndex( 0 );

		//fieldParameterElementSelector = new ParameterElementTreeSelectorComponent(rootDataModel);
		commonPost();
	}
		
	/**
	 * 
	 * Mar letezo ParamPageElement modositasa vagy megtekintese
	 * @param tree
	 * @param selectedElement
	 * @param mode
	 */
	public ParamElementEditor( Tree tree, ParamElementDataModel selectedElement, VariableRootDataModel rootDataModel, EditMode mode ){		
		//super( mode, CommonOperations.getTranslation("tree.nodetype.paramelement") );
		super( mode, selectedElement.getModelNameToShow());

		this.tree = tree;
		this.nodeForModify = selectedElement;
		this.mode = mode;
	

		commonPre();
		
		//Name
		fieldName.setText( selectedElement.getName() );

		//Selector az elem valasztashoz - A root a basePage (nem latszik)
		BaseElementDataModel baseElement = selectedElement.getBaseElement();
		BasePageDataModel basePage = ((ParamPageDataModel)selectedElement.getParent()).getBasePage();		
		fieldBaseElementSelector = new BaseElementTreeSelectorComponent( basePage, baseElement );
		
		//Operation
		operation = selectedElement.getElementOperation().getOperation();
//		fieldOperation.setSelectedIndex( op.getIndex() );
		

//TODO ki kell majd javitani
		//selectedElement
		//fieldParameterElementSelector = new ParameterElementTreeSelectorComponent(rootDataModel);
		
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
		fieldOperation.addItemListener( new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {

				int index = fieldOperation.getSelectedIndex();					

				//Ha megvaltoztattam a tipust
				if( e.getStateChange() == java.awt.event.ItemEvent.SELECTED ){ 
					
					 operation = Operation.getOperationByIndex(index);
					 
						//STRING_PARAMETER
						if( operation.equals( Operation.FIELD ) ){

							//Nem ez az elso valtoztatas
							if( null != fieldParameterElementSelector ){
								ParamElementEditor.this.remove(fieldParameterElementSelector.getComponent());
								fieldParameterElementSelector = new ParameterElementTreeSelectorComponent( rootDataModel );
							}else{
								//Modositas volt
								if( null != nodeForModify ){
									fieldParameterElementSelector = new ParameterElementTreeSelectorComponent( rootDataModel, nodeForModify.getElementOperation().getVariableElement() );
								}else{
									fieldParameterElementSelector = new ParameterElementTreeSelectorComponent( rootDataModel );
								}
							}
							
						}else if( operation.equals( Operation.BUTTON ) ){
							if( null != fieldParameterElementSelector ){
								ParamElementEditor.this.remove(fieldParameterElementSelector.getComponent());
							}
						
						}else if( operation.equals( Operation.CHECKBOX ) ){
							if( null != fieldParameterElementSelector ){
								ParamElementEditor.this.remove(fieldParameterElementSelector.getComponent());
							}
						
						}else if( operation.equals( Operation.RADIOBUTTON ) ){
							if( null != fieldParameterElementSelector ){
								ParamElementEditor.this.remove(fieldParameterElementSelector.getComponent());
							}
						
						}else if( operation.equals( Operation.LINK ) ){
							if( null != fieldParameterElementSelector ){
								ParamElementEditor.this.remove(fieldParameterElementSelector.getComponent());
							}					
						}
						
						ParamElementEditor.this.add( labelParameterElementSelector, fieldParameterElementSelector );
						ParamElementEditor.this.revalidate();
				}
			}
		});
	}
	
	private void commonPost(){
		
		labelName = new JLabel( CommonOperations.getTranslation("editor.title.name") + ": ");
		labelOperation = new JLabel( CommonOperations.getTranslation("editor.title.operation") + ": ");
		labelPageBaseElementSelector = new JLabel( CommonOperations.getTranslation("editor.title.baseelement") + ": " );
		labelParameterElementSelector = new JLabel( "Valassz aprametert" );
		
		this.add( labelName, fieldName );
		this.add( labelPageBaseElementSelector, fieldBaseElementSelector );
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
				if( levelNode instanceof ParamElementDataModel ){
				
					//Ha azonos a BaseElement					
					if( ((ParamElementDataModel)levelNode).getBaseElement().equals( fieldBaseElementSelector.getSelectedDataModel() ) ){
					
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
//TODO folytatni ---
//Utanna nezni, hogy miert van ez a ParamElementDataModel-nel is				
//				elementOperation = new FieldOperation( new SParameter("param1", "111" ) );
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
