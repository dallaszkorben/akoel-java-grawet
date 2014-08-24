package hu.akoel.grawit.gui.editor.component;

import hu.akoel.grawit.core.datamodel.VariableTypeDataModel;
import hu.akoel.grawit.enums.VariableType;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class VariableTypeComponent extends JPanel implements EditorComponentInterface{

	private static final long serialVersionUID = -3681477011797868779L;

	private ComboBoxComponent<String> fieldVariableType;
	private VariableTypeDataModel variableTypeDataModel;
	
	public VariableTypeComponent(  ){
		super();
		
		this.setLayout( new GridBagLayout());
		
		this.variableTypeDataModel = new VariableTypeDataModel();		
		
		fieldVariableType = new ComboBoxComponent<String>();
		fieldVariableType.addItem( VariableType.getVariableParameterTypeByIndex(0).getTranslatedName() );
		fieldVariableType.addItem( VariableType.getVariableParameterTypeByIndex(1).getTranslatedName() );
		fieldVariableType.addItem( VariableType.getVariableParameterTypeByIndex(2).getTranslatedName() );
		fieldVariableType.addItem( VariableType.getVariableParameterTypeByIndex(3).getTranslatedName() );
				
		fieldVariableType.addItemListener( new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
			
				//Ha megvaltoztattam a tipust
				if( e.getStateChange() == java.awt.event.ItemEvent.SELECTED ){ 
					
					//Akkor 
					int index = fieldVariableType.getSelectedIndex();
					variableTypeDataModel.setType( VariableType.getVariableParameterTypeByIndex(index));
					VariableTypeComponent.this.removeAll();
									
					int gridY = 0;
					GridBagConstraints c = new GridBagConstraints();
					c.insets = new Insets(0,0,0,0);
					c.gridy = gridY;
					c.gridx = 0;
					c.gridwidth = 2;
					c.weighty = 0;
					c.fill = GridBagConstraints.HORIZONTAL;
					c.weightx = 0;
					c.anchor = GridBagConstraints.WEST;
					VariableTypeComponent.this.add( fieldVariableType, c );
					
					for( int i = 0; i < variableTypeDataModel.getParameterSize(); i++ ){
						gridY++;
						c.insets = new Insets(0,0,0,0);
						c.gridy = gridY;
						c.gridx = 0;
						c.gridwidth = 1;
						c.weighty = 0;
						c.fill = GridBagConstraints.NONE;
						c.weightx = 0;
						c.anchor = GridBagConstraints.WEST;						
						VariableTypeComponent.this.add( new JLabel( variableTypeDataModel.getParameterNames().get(i) + ": "), c);
						
						c.insets = new Insets(0,0,0,0);
						c.gridy = gridY;
						c.gridx = 1;
						c.gridwidth = 1;
						c.weighty = 0;
						c.fill = GridBagConstraints.HORIZONTAL;
						c.weightx = 1;
						c.anchor = GridBagConstraints.FIRST_LINE_START;						
						VariableTypeComponent.this.add( new JTextField( variableTypeDataModel.getParameterValues().get(i)), c);						
					}
					VariableTypeComponent.this.revalidate();	

				}				
			}
		});
		
		fieldVariableType.setSelectedIndex(1);
		fieldVariableType.setSelectedIndex(0);
		
	}
	
	public void setModel( VariableTypeDataModel variableTypeDataModel ){
		
		//Ennek hatasara a itemListener aktivalodik
		fieldVariableType.setSelectedIndex( variableTypeDataModel.getType().getIndex() );
		
	}
	
	public VariableTypeDataModel getModel(){
		return this.variableTypeDataModel;
	}
	
	public void setTypeByIndex( int index ){
		fieldVariableType.setSelectedIndex( index );
	}
	
	@Override
	public void setEnableModify(boolean enable) {
		fieldVariableType.setEnabled( enable );		
	}

	@Override
	public Component getComponent() {
		return this;
	}

	public VariableTypeDataModel getDataModel(){
		return variableTypeDataModel;
	}
	
}
