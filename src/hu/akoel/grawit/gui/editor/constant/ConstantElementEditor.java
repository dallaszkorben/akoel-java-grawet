package hu.akoel.grawit.gui.editor.constant;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.MessageFormat;
import java.util.LinkedHashMap;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.constant.ConstantElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.constant.ConstantFolderNodeDataModel;
import hu.akoel.grawit.enums.list.VariableTypeListEnum;
import hu.akoel.grawit.gui.editor.DataEditor;
import hu.akoel.grawit.gui.editors.component.ComboBoxComponent;
import hu.akoel.grawit.gui.editors.component.TextFieldComponent;
import hu.akoel.grawit.gui.editors.component.constantparameter.ConstantComponentInterface;
import hu.akoel.grawit.gui.editors.component.constantparameter.ConstantRandomDateComponent;
import hu.akoel.grawit.gui.editors.component.constantparameter.ConstantRandomDoubleComponent;
import hu.akoel.grawit.gui.editors.component.constantparameter.ConstantRandomIntegerComponent;
import hu.akoel.grawit.gui.editors.component.constantparameter.ConstantRandomStringComponent;
import hu.akoel.grawit.gui.editors.component.constantparameter.ConstantStringComponent;
import hu.akoel.grawit.gui.editors.component.constantparameter.ConstantTodayDateComponent;
import hu.akoel.grawit.gui.editors.component.constantparameter.ConstantVariableComponent;
import hu.akoel.grawit.gui.tree.Tree;

import javax.swing.JLabel;
import javax.swing.tree.TreeNode;

public class ConstantElementEditor extends DataEditor{
	
	private static final long serialVersionUID = -7285419881714492620L;
	
	private Tree tree;
	private ConstantElementDataModel nodeForModify;
	private ConstantFolderNodeDataModel nodeForCapture;
	private EditMode mode;
	
	private JLabel labelName;
	private TextFieldComponent fieldName;
	private JLabel labelVariableType;
	private ComboBoxComponent<VariableTypeListEnum> fieldVariableType;
	private JLabel labelVariableParameters;
	private ConstantComponentInterface fieldVariableParameters;
	
	private VariableTypeListEnum type;
	
	/**
	 *  Uj VariableElement rogzitese - Insert
	 *  
	 * @param tree
	 * @param selectedNode
	 */
	public ConstantElementEditor( Tree tree, ConstantFolderNodeDataModel selectedNode ){

		super( ConstantElementDataModel.getModelNameToShowStatic());

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
	public ConstantElementEditor( Tree tree, ConstantElementDataModel selectedElement, EditMode mode ){		

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
							ConstantElementEditor.this.remove(labelVariableParameters, fieldVariableParameters.getComponent());
							fieldVariableParameters = new ConstantStringComponent(type);
						}else{
							//Modositas volt
							if( null != nodeForModify ){
								fieldVariableParameters = new ConstantStringComponent(type, nodeForModify.getParameters() );
							}else{
								fieldVariableParameters = new ConstantStringComponent(type);
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
							ConstantElementEditor.this.remove(labelVariableParameters, fieldVariableParameters.getComponent());
							fieldVariableParameters = new ConstantRandomStringComponent(type);
						}else{
							//Modositas volt
							if( null != nodeForModify ){
								fieldVariableParameters = new ConstantRandomStringComponent(type, nodeForModify.getParameters() );
							}else{
								fieldVariableParameters = new ConstantRandomStringComponent(type);
							}
						}
						
						
					
					//RANDOM_INTEGER_PARAMETER
					}else if( VariableTypeListEnum.getVariableParameterTypeByIndex(index).equals(VariableTypeListEnum.RANDOM_INTEGER_PARAMETER ) ){

						//Nem ez az elso valtoztatas
						if( null != fieldVariableParameters ){
							ConstantElementEditor.this.remove(labelVariableParameters, fieldVariableParameters.getComponent());
							fieldVariableParameters = new ConstantRandomIntegerComponent(type);
						}else{
							//Modositas volt
							if( null != nodeForModify ){
								fieldVariableParameters = new ConstantRandomIntegerComponent(type, nodeForModify.getParameters() );
							}else{
								fieldVariableParameters = new ConstantRandomIntegerComponent(type);
							}
						}
						
						
						
					//RANDOM_DECIMAL_PARAMETER	
					}else if( VariableTypeListEnum.getVariableParameterTypeByIndex(index).equals(VariableTypeListEnum.RANDOM_DOUBLE_PARAMETER ) ){
						
						//Nem ez az elso valtoztatas
						if( null != fieldVariableParameters ){
							ConstantElementEditor.this.remove(labelVariableParameters, fieldVariableParameters.getComponent());
							fieldVariableParameters = new ConstantRandomDoubleComponent(type);
						}else{
							//Modositas volt
							if( null != nodeForModify ){
								fieldVariableParameters = new ConstantRandomDoubleComponent(type, nodeForModify.getParameters() );
							}else{
								fieldVariableParameters = new ConstantRandomDoubleComponent(type);
							}
						}
										
					//RANDOM_DATE_PARAMETER
					}else if( VariableTypeListEnum.getVariableParameterTypeByIndex(index).equals(VariableTypeListEnum.RANDOM_DATE_PARAMETER ) ){
						
						//Nem ez az elso valtoztatas
						if( null != fieldVariableParameters ){
							ConstantElementEditor.this.remove(labelVariableParameters, fieldVariableParameters.getComponent());
							fieldVariableParameters = new ConstantRandomDateComponent(type);
						}else{
							//Modositas volt
							if( null != nodeForModify ){
								fieldVariableParameters = new ConstantRandomDateComponent(type, nodeForModify.getParameters() );
							}else{
								fieldVariableParameters = new ConstantRandomDateComponent(type);
							}
						}										
						
					//TODAY_DATE_PARAMETER
					}else if( VariableTypeListEnum.getVariableParameterTypeByIndex(index).equals(VariableTypeListEnum.TODAY_DATE_PARAMETER ) ){

						//Nem ez az elso valtoztatas
						if( null != fieldVariableParameters ){
							ConstantElementEditor.this.remove(labelVariableParameters, fieldVariableParameters.getComponent());
							fieldVariableParameters = new ConstantTodayDateComponent(type);
						}else{
							//Modositas volt
							if( null != nodeForModify ){
								fieldVariableParameters = new ConstantTodayDateComponent(type, nodeForModify.getParameters() );
							}else{
								fieldVariableParameters = new ConstantTodayDateComponent(type);
							}
						}						
						
/*					//VARIABLE_PARAMETER
					}else if( VariableTypeListEnum.getVariableParameterTypeByIndex(index).equals(VariableTypeListEnum.STORE_PARAMETER ) ){
							
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
*/
					}
					
					ConstantElementEditor.this.add( labelVariableParameters, fieldVariableParameters );
					ConstantElementEditor.this.revalidate();
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
				if( levelNode instanceof ConstantElementDataModel ){
					
					//Ha azonos a nev
					if( ((ConstantElementDataModel) levelNode).getName().equals( fieldName.getText() ) ){
					
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

				ConstantElementDataModel newVariableElement = new ConstantElementDataModel( fieldName.getText(), type, fieldVariableParameters.getParameters());
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
