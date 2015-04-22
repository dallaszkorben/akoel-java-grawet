package hu.akoel.grawit.gui.editor.constant;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.MessageFormat;
import java.util.LinkedHashMap;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.constant.ConstantElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.constant.ConstantFolderNodeDataModel;
import hu.akoel.grawit.enums.list.ConstantTypeListEnum;
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
	private JLabel labelConstantType;
	private ComboBoxComponent<ConstantTypeListEnum> fieldConstantType;
	private JLabel labelConstantParameters;
	private ConstantComponentInterface fieldConstantParameters;
	
	private ConstantTypeListEnum type;
	
	/**
	 *  Uj ConstantElement rogzitese - Insert
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
		type = ConstantTypeListEnum.STRING_PARAMETER;		
				
		commonPost( );
				
	}
		
	/**
	 * 
	 * Mar letezo ConstantElement modositasa vagy megtekintese
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

		//Constant Type
		type = selectedElement.getType();
		
		commonPost( );		
		
	}

	private void commonPre(){
		
		//Name
		fieldName = new TextFieldComponent();
	
		//CosntantTypeSelector		
		fieldConstantType = new ComboBoxComponent<>();
		for( int i = 0; i < ConstantTypeListEnum.getSize(); i++ ){
			fieldConstantType.addItem( ConstantTypeListEnum.getConstantParameterTypeByIndex(i) );
		}
		fieldConstantType.addItemListener( new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {

				int index = fieldConstantType.getSelectedIndex();					

				//Ha megvaltoztattam a tipust
				if( e.getStateChange() == java.awt.event.ItemEvent.SELECTED ){ 
					
					type = ConstantTypeListEnum.getConstantParameterTypeByIndex(index);
					
					//STRING_PARAMETER
					if( ConstantTypeListEnum.getConstantParameterTypeByIndex(index).equals(ConstantTypeListEnum.STRING_PARAMETER ) ){

						//Nem ez az elso valtoztatas
						if( null != fieldConstantParameters ){
							ConstantElementEditor.this.remove(labelConstantParameters, fieldConstantParameters.getComponent());
							fieldConstantParameters = new ConstantStringComponent(type);
						}else{
							//Modositas volt
							if( null != nodeForModify ){
								fieldConstantParameters = new ConstantStringComponent(type, nodeForModify.getParameters() );
							}else{
								fieldConstantParameters = new ConstantStringComponent(type);
							}
						}
						
												
					//RANDOM_STRING_PARAMETER	
					}else if( ConstantTypeListEnum.getConstantParameterTypeByIndex(index).equals(ConstantTypeListEnum.RANDOM_STRING_PARAMETER ) ){
						
						//Nem ez az elso valtoztatas
						if( null != fieldConstantParameters ){
							ConstantElementEditor.this.remove(labelConstantParameters, fieldConstantParameters.getComponent());
							fieldConstantParameters = new ConstantRandomStringComponent(type);
						}else{
							//Modositas volt
							if( null != nodeForModify ){
								fieldConstantParameters = new ConstantRandomStringComponent(type, nodeForModify.getParameters() );
							}else{
								fieldConstantParameters = new ConstantRandomStringComponent(type);
							}
						}
						
						
					
					//RANDOM_INTEGER_PARAMETER
					}else if( ConstantTypeListEnum.getConstantParameterTypeByIndex(index).equals(ConstantTypeListEnum.RANDOM_INTEGER_PARAMETER ) ){

						//Nem ez az elso valtoztatas
						if( null != fieldConstantParameters ){
							ConstantElementEditor.this.remove(labelConstantParameters, fieldConstantParameters.getComponent());
							fieldConstantParameters = new ConstantRandomIntegerComponent(type);
						}else{
							//Modositas volt
							if( null != nodeForModify ){
								fieldConstantParameters = new ConstantRandomIntegerComponent(type, nodeForModify.getParameters() );
							}else{
								fieldConstantParameters = new ConstantRandomIntegerComponent(type);
							}
						}
						
						
						
					//RANDOM_DECIMAL_PARAMETER	
					}else if( ConstantTypeListEnum.getConstantParameterTypeByIndex(index).equals(ConstantTypeListEnum.RANDOM_DOUBLE_PARAMETER ) ){
						
						//Nem ez az elso valtoztatas
						if( null != fieldConstantParameters ){
							ConstantElementEditor.this.remove(labelConstantParameters, fieldConstantParameters.getComponent());
							fieldConstantParameters = new ConstantRandomDoubleComponent(type);
						}else{
							//Modositas volt
							if( null != nodeForModify ){
								fieldConstantParameters = new ConstantRandomDoubleComponent(type, nodeForModify.getParameters() );
							}else{
								fieldConstantParameters = new ConstantRandomDoubleComponent(type);
							}
						}
										
					//RANDOM_DATE_PARAMETER
					}else if( ConstantTypeListEnum.getConstantParameterTypeByIndex(index).equals(ConstantTypeListEnum.RANDOM_DATE_PARAMETER ) ){
						
						//Nem ez az elso valtoztatas
						if( null != fieldConstantParameters ){
							ConstantElementEditor.this.remove(labelConstantParameters, fieldConstantParameters.getComponent());
							fieldConstantParameters = new ConstantRandomDateComponent(type);
						}else{
							//Modositas volt
							if( null != nodeForModify ){
								fieldConstantParameters = new ConstantRandomDateComponent(type, nodeForModify.getParameters() );
							}else{
								fieldConstantParameters = new ConstantRandomDateComponent(type);
							}
						}										
						
					//TODAY_DATE_PARAMETER
					}else if( ConstantTypeListEnum.getConstantParameterTypeByIndex(index).equals(ConstantTypeListEnum.TODAY_DATE_PARAMETER ) ){

						//Nem ez az elso valtoztatas
						if( null != fieldConstantParameters ){
							ConstantElementEditor.this.remove(labelConstantParameters, fieldConstantParameters.getComponent());
							fieldConstantParameters = new ConstantTodayDateComponent(type);
						}else{
							//Modositas volt
							if( null != nodeForModify ){
								fieldConstantParameters = new ConstantTodayDateComponent(type, nodeForModify.getParameters() );
							}else{
								fieldConstantParameters = new ConstantTodayDateComponent(type);
							}
						}						
						
					}
					
					ConstantElementEditor.this.add( labelConstantParameters, fieldConstantParameters );
					ConstantElementEditor.this.revalidate();
				}
			}
		});		
	}
	
	private void commonPost(){
		
		labelName = new JLabel( CommonOperations.getTranslation("editor.label.name") + ": ");
		labelConstantType = new JLabel( CommonOperations.getTranslation("editor.label.constant.parametertype") + ": ");
		labelConstantParameters = new JLabel("");
		
		//jx
		this.add( labelName, fieldName );
		this.add( labelConstantType, fieldConstantType );

		//Arra kenyszeritem, hogy az elso igazi valasztas is kivaltson egy esemenyt
		fieldConstantType.setSelectedIndex(-1);		
		fieldConstantType.setSelectedIndex( type.getIndex() );	
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
										CommonOperations.getTranslation("tree.nodetype.constant.element") 
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

				ConstantElementDataModel newConstantElement = new ConstantElementDataModel( fieldName.getText(), type, fieldConstantParameters.getParameters());
				nodeForCapture.add( newConstantElement );
				
				//A fa-ban modositja a strukturat
				//tree.refreshTreeAfterStructureChanged( nodeForCapture, nodeForCapture );
				tree.refreshTreeAfterStructureChanged( nodeForCapture );
				
			//Modositas eseten
			}else if( mode.equals(EditMode.MODIFY ) ){
		
				nodeForModify.setName( fieldName.getText() );
				nodeForModify.setType( type );
				nodeForModify.setParameters( fieldConstantParameters.getParameters() );
				
				//A fa-ban modositja a nevet (ha az valtozott)
				//tree.refreshTreeAfterChanged( nodeForModify );	
				tree.refreshTreeAfterChanged();
			}
		}		
	}	
}
