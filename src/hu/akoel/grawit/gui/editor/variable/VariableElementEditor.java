package hu.akoel.grawit.gui.editor.variable;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.MessageFormat;
import java.util.LinkedHashMap;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.variable.VariableElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.variable.VariableNodeDataModel;
import hu.akoel.grawit.enums.list.VariableTypeListEnum;
import hu.akoel.grawit.gui.editor.DataEditor;
import hu.akoel.grawit.gui.editors.component.ComboBoxComponent;
import hu.akoel.grawit.gui.editors.component.TextFieldComponent;
import hu.akoel.grawit.gui.editors.component.variableparameter.VariableComponentInterface;
import hu.akoel.grawit.gui.editors.component.variableparameter.VariableRandomDateComponent;
import hu.akoel.grawit.gui.editors.component.variableparameter.VariableRandomDoubleComponent;
import hu.akoel.grawit.gui.editors.component.variableparameter.VariableRandomIntegerComponent;
import hu.akoel.grawit.gui.editors.component.variableparameter.VariableRandomStringComponent;
import hu.akoel.grawit.gui.editors.component.variableparameter.VariableStringComponent;
import hu.akoel.grawit.gui.editors.component.variableparameter.VariableTodayDateComponent;
import hu.akoel.grawit.gui.editors.component.variableparameter.VariableVariableComponent;
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
	private ComboBoxComponent<VariableTypeListEnum> fieldVariableType;
	private JLabel labelVariableParameters;
	private VariableComponentInterface fieldVariableParameters;
	
	private VariableTypeListEnum type;
	
	/**
	 *  Uj VariableElement rogzitese - Insert
	 *  
	 * @param tree
	 * @param selectedNode
	 */
	public VariableElementEditor( Tree tree, VariableNodeDataModel selectedNode ){

		super( VariableElementDataModel.getModelNameToShowStatic());

		this.tree = tree;
		this.nodeForCapture = selectedNode;
		this.mode = null;

		commonPre();
		
		//Name
		fieldName.setText( "" );
		
		//Type - String
		type = VariableTypeListEnum.STRING_PARAMETER;		
				
		//Parameters
		//fieldVariableParameters = new VariableParametersStringComponent(type);				
				
		commonPost( );
				
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

		super( mode, selectedElement.getNodeTypeToShow() );

		this.tree = tree;
		this.nodeForModify = selectedElement;
		this.mode = mode;
	
		commonPre();
		
		//Name
		fieldName.setText( selectedElement.getName() );

		//Variable Type
		type = selectedElement.getType();
		
		//Parameters
		//fieldVariableParameters = new VariableParametersStringComponent(type, selectedElement.getParameters() );
		
		commonPost( );		
		
	}

	private void commonPre(){
		
		//Name
		fieldName = new TextFieldComponent();
	
		//VariableTypeSelector		
		fieldVariableType = new ComboBoxComponent<>();
		for( int i = 0; i < VariableTypeListEnum.getSize(); i++ ){
			fieldVariableType.addItem( VariableTypeListEnum.getVariableParameterTypeByIndex(i) );
		}
		fieldVariableType.addItemListener( new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {

				int index = fieldVariableType.getSelectedIndex();					

				//Ha megvaltoztattam a tipust
				if( e.getStateChange() == java.awt.event.ItemEvent.SELECTED ){ 
					
					type = VariableTypeListEnum.getVariableParameterTypeByIndex(index);
					
					//STRING_PARAMETER
					if( VariableTypeListEnum.getVariableParameterTypeByIndex(index).equals(VariableTypeListEnum.STRING_PARAMETER ) ){

						//Nem ez az elso valtoztatas
						if( null != fieldVariableParameters ){
							VariableElementEditor.this.remove(labelVariableParameters, fieldVariableParameters.getComponent());
							fieldVariableParameters = new VariableStringComponent(type);
						}else{
							//Modositas volt
							if( null != nodeForModify ){
								fieldVariableParameters = new VariableStringComponent(type, nodeForModify.getParameters() );
							}else{
								fieldVariableParameters = new VariableStringComponent(type);
							}
						}
						
						
/*					}else if( ParameterTypeListEnum.getVariableParameterTypeByIndex(index).equals(ParameterTypeListEnum.INTEGER_PARAMETER ) ){

						//Nem ez az elso valtoztatas
						if( null != fieldVariableParameters ){
							VariableElementEditor.this.remove(labelVariableParameters, fieldVariableParameters.getComponent());
							fieldVariableParameters = new VariableParametersIntegerComponent(type);
						}else{
							//Modositas volt
							if( null != nodeForModify ){
								fieldVariableParameters = new VariableParametersIntegerComponent(type, nodeForModify.getParameters() );
							}else{
								fieldVariableParameters = new VariableParametersIntegerComponent(type);
							}
						}
*/						
					//RANDOM_STRING_PARAMETER	
					}else if( VariableTypeListEnum.getVariableParameterTypeByIndex(index).equals(VariableTypeListEnum.RANDOM_STRING_PARAMETER ) ){
						
						//Nem ez az elso valtoztatas
						if( null != fieldVariableParameters ){
							VariableElementEditor.this.remove(labelVariableParameters, fieldVariableParameters.getComponent());
							fieldVariableParameters = new VariableRandomStringComponent(type);
						}else{
							//Modositas volt
							if( null != nodeForModify ){
								fieldVariableParameters = new VariableRandomStringComponent(type, nodeForModify.getParameters() );
							}else{
								fieldVariableParameters = new VariableRandomStringComponent(type);
							}
						}
						
						
					
					//RANDOM_INTEGER_PARAMETER
					}else if( VariableTypeListEnum.getVariableParameterTypeByIndex(index).equals(VariableTypeListEnum.RANDOM_INTEGER_PARAMETER ) ){

						//Nem ez az elso valtoztatas
						if( null != fieldVariableParameters ){
							VariableElementEditor.this.remove(labelVariableParameters, fieldVariableParameters.getComponent());
							fieldVariableParameters = new VariableRandomIntegerComponent(type);
						}else{
							//Modositas volt
							if( null != nodeForModify ){
								fieldVariableParameters = new VariableRandomIntegerComponent(type, nodeForModify.getParameters() );
							}else{
								fieldVariableParameters = new VariableRandomIntegerComponent(type);
							}
						}
						
						
						
					//RANDOM_DECIMAL_PARAMETER	
					}else if( VariableTypeListEnum.getVariableParameterTypeByIndex(index).equals(VariableTypeListEnum.RANDOM_DOUBLE_PARAMETER ) ){
						
						//Nem ez az elso valtoztatas
						if( null != fieldVariableParameters ){
							VariableElementEditor.this.remove(labelVariableParameters, fieldVariableParameters.getComponent());
							fieldVariableParameters = new VariableRandomDoubleComponent(type);
						}else{
							//Modositas volt
							if( null != nodeForModify ){
								fieldVariableParameters = new VariableRandomDoubleComponent(type, nodeForModify.getParameters() );
							}else{
								fieldVariableParameters = new VariableRandomDoubleComponent(type);
							}
						}
										
					//RANDOM_DATE_PARAMETER
					}else if( VariableTypeListEnum.getVariableParameterTypeByIndex(index).equals(VariableTypeListEnum.RANDOM_DATE_PARAMETER ) ){
						
						//Nem ez az elso valtoztatas
						if( null != fieldVariableParameters ){
							VariableElementEditor.this.remove(labelVariableParameters, fieldVariableParameters.getComponent());
							fieldVariableParameters = new VariableRandomDateComponent(type);
						}else{
							//Modositas volt
							if( null != nodeForModify ){
								fieldVariableParameters = new VariableRandomDateComponent(type, nodeForModify.getParameters() );
							}else{
								fieldVariableParameters = new VariableRandomDateComponent(type);
							}
						}										
						
					//TODAY_DATE_PARAMETER
					}else if( VariableTypeListEnum.getVariableParameterTypeByIndex(index).equals(VariableTypeListEnum.TODAY_DATE_PARAMETER ) ){

						//Nem ez az elso valtoztatas
						if( null != fieldVariableParameters ){
							VariableElementEditor.this.remove(labelVariableParameters, fieldVariableParameters.getComponent());
							fieldVariableParameters = new VariableTodayDateComponent(type);
						}else{
							//Modositas volt
							if( null != nodeForModify ){
								fieldVariableParameters = new VariableTodayDateComponent(type, nodeForModify.getParameters() );
							}else{
								fieldVariableParameters = new VariableTodayDateComponent(type);
							}
						}						
						
					//VARIABLE_PARAMETER
					}else if( VariableTypeListEnum.getVariableParameterTypeByIndex(index).equals(VariableTypeListEnum.VARIABLE_PARAMETER ) ){
							
						//Nem ez az elso valtoztatas
						if( null != fieldVariableParameters ){
							VariableElementEditor.this.remove(labelVariableParameters, fieldVariableParameters.getComponent());
							fieldVariableParameters = new VariableVariableComponent(type);
						}else{
							//Modositas volt
							if( null != nodeForModify ){
								fieldVariableParameters = new VariableVariableComponent(type, nodeForModify.getParameters() );
							}else{
								fieldVariableParameters = new VariableVariableComponent(type);
							}
						}

					}
					
					VariableElementEditor.this.add( labelVariableParameters, fieldVariableParameters );
					VariableElementEditor.this.revalidate();
				}
			}
		});		
	}
	
	private void commonPost(){
		
		labelName = new JLabel( CommonOperations.getTranslation("editor.label.name") + ": ");
		labelVariableType = new JLabel( CommonOperations.getTranslation("editor.label.variable.parametertype") + ": ");
		labelVariableParameters = new JLabel("");
		
		//this.add( labelName, fieldName );
		//jx
		this.add( labelName, fieldName );
		this.add( labelVariableType, fieldVariableType );
//		this.add( labelVariableParameters, fieldVariableParameters );

		//Arra kenyszeritem, hogy az elso igazi valasztas is kivaltson egy esemenyt
		fieldVariableType.setSelectedIndex(-1);		
		fieldVariableType.setSelectedIndex( type.getIndex() );	
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
										CommonOperations.getTranslation("tree.nodetype.variable.element") 
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

				VariableElementDataModel newVariableElement = new VariableElementDataModel( fieldName.getText(), type, fieldVariableParameters.getParameters());
				nodeForCapture.add( newVariableElement );
				
			//Modositas eseten
			}else if( mode.equals(EditMode.MODIFY ) ){
		
				nodeForModify.setName( fieldName.getText() );
				nodeForModify.setType( type );
				nodeForModify.setParameters( fieldVariableParameters.getParameters() );
				
			}
			
			//A fa-ban is modositja a nevet (ha az valtozott)
			tree.changed();

		}
		
	}
	
}
