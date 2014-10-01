package hu.akoel.grawit.gui.editors.component.variableparameter;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.enums.list.FormDateListEnum;
import hu.akoel.grawit.enums.list.ParameterTypeListEnum;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.text.MaskFormatter;

public class VariableParametersTodayDateComponent extends JPanel implements VariableParametersComponentInterface{

	private static final long serialVersionUID = -3266616532152280622L;
	
	private static final String DEFAULT_FORMAT = "dd/mm/yyyy";
	private static final String DEFAULT_MASK = "##/##/####";

	private static final int PARAMETERORDER_FORMAT = 0;	
	private static final int PARAMETERORDER_MASK = 1;	
	
	private FormDateComboBox fieldFormatDate;
	
	DateFormat dateFormat;
	MaskFormatter maskFormatterFrom;
	MaskFormatter maskFormatterTo;
	
	private ParameterTypeListEnum type;
	
	private ArrayList<Object> parameterList;

	/**
	 * Uj lista
	 * 
	 * @param type
	 */
	public VariableParametersTodayDateComponent( ParameterTypeListEnum type ){
		super();
		
		this.parameterList = new ArrayList<>();

		this.parameterList.add( DEFAULT_FORMAT );
		this.parameterList.add( DEFAULT_MASK );		
		
		common( type );		
		
	}
	
	/**
	 * Letezo lista
	 * 
	 * @param type
	 * @param parameterList
	 */
	public VariableParametersTodayDateComponent( ParameterTypeListEnum type, ArrayList<Object> parameterList ){
		super();
		
		//Parameter lista feltoltese a letezo ertekekkel
		this.parameterList = parameterList;
		
		common( type );		
		
	}
	
	private void common( ParameterTypeListEnum type ){
		this.type = type;
		
		this.setLayout( new GridBagLayout() );
		
		dateFormat = new SimpleDateFormat((String)parameterList.get(PARAMETERORDER_FORMAT));
		dateFormat.setLenient( false );

/*		try {
			maskFormatterFrom = new MaskFormatter((String)parameterList.get(PARAMETERORDER_MASK));
			maskFormatterTo = new MaskFormatter((String)parameterList.get(PARAMETERORDER_MASK));
		} catch (ParseException e2) {
			e2.printStackTrace();
			//TODO kezelni valahogy
		}
*/		
		//
		// From field
		//		
		int gridY = 0;
		GridBagConstraints c = new GridBagConstraints();		
		c.insets = new Insets(0,0,0,0);
		
		//
		//FormDate date
		//
		JLabel labelFormatDate = new JLabel( CommonOperations.getTranslation("editor.label.variable.parametertype.randomdate.format") );
		
		fieldFormatDate = new FormDateComboBox();
		//Feltoltom a listat
		for( int i = 0; i < FormDateListEnum.getSize(); i++ ){		
			fieldFormatDate.addItem( FormDateListEnum.getFormDateByIndex(i) );
		}
		fieldFormatDate.addItemListener( new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {

				int index = fieldFormatDate.getSelectedIndex();	
				
				//Ha megvaltoztattam a tipust
				if( e.getStateChange() == java.awt.event.ItemEvent.SELECTED ){ 
					
					DateFormat newDateFormat = FormDateListEnum.getFormDateByIndex(index).getDateFormat();
					
					parameterList.set(PARAMETERORDER_FORMAT, FormDateListEnum.getFormDateByIndex(index).getStringDateFormat() );
					parameterList.set(PARAMETERORDER_MASK, FormDateListEnum.getFormDateByIndex(index).getStringMask() );

					dateFormat = newDateFormat;
				}
				
			}
		});	
		
		gridY=0;		
		c.gridy = gridY;
		c.gridx = 0;
		c.gridwidth = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0;
		c.anchor = GridBagConstraints.WEST;
		this.add( labelFormatDate, c );
		
		gridY++;
		c.gridy = gridY;
		c.gridx = 0;
		c.gridwidth = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0;
		c.anchor = GridBagConstraints.WEST;
		this.add( fieldFormatDate, c );

	
		
		
		//Kitolto
		c.gridy = gridY;
		c.gridx = 1;
		c.gridwidth = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.anchor = GridBagConstraints.WEST;
		this.add( new JLabel(), c );
		
		//Default dd/MM/yyy
//		fieldFormatDate.setSelectedIndex( FormDate.ddMMyyyy_dot.getIndex() );
	
		
		FormDateListEnum fd = FormDateListEnum.getFormDateByMask( (String)parameterList.get(PARAMETERORDER_MASK) );
		fieldFormatDate.setSelectedIndex( fd.getIndex() );	
	}	
	
	@Override
	public void setEnableModify(boolean enable) {
		fieldFormatDate.setEnabled(enable);
	}

	@Override
	public Component getComponent() {
		return this;
	}

	@Override
	public ArrayList<Object> getParameters() {
		return parameterList;
	}
	
	class FormDateComboBox extends JComboBox<FormDateListEnum>{

		public FormDateComboBox(){
			this.setRenderer(new MyRenderer());
		}
		
		class MyRenderer extends BasicComboBoxRenderer {

			private static final long serialVersionUID = -4562181616721578685L;

			@Override
			public Component getListCellRendererComponent(JList list, Object value,	int index, boolean isSelected, boolean cellHasFocus) {
								
				Component c = super.getListCellRendererComponent(list, ((FormDateListEnum)value).getTranslatedName(), index, isSelected, cellHasFocus);

				return this;
			}
		}		
	}
}
