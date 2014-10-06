package hu.akoel.grawit.gui.editors.component.variableparameter;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.enums.list.DateDigressionListEnum;
import hu.akoel.grawit.enums.list.DateFormListEnum;
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

import javax.swing.InputVerifier;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

public class VariableParametersTodayDateComponent extends JPanel implements VariableParametersComponentInterface{

	private static final long serialVersionUID = -3266616532152280622L;
	
	private static final String DEFAULT_FORMAT = "dd/MM/yyyy";
	private static final String DEFAULT_MASK = "##/##/####";
	private static final String DEFAULT_DIGRESSION = DateDigressionListEnum.NONE.name();
	private static final String DEFAULT_DAYS = "0";

	private static final int PARAMETERORDER_FORMAT = 0;	
	private static final int PARAMETERORDER_MASK = 1;
	private static final int PARAMETERORDER_DIGRESSION = 2;
	private static final int PARAMETERORDER_DAYS = 3;
	
	private DateFormComboBox fieldDateFormat;	
	private DateDigressionComboBox fieldDateDigression;
	private JFormattedTextField fieldDays;
	private JLabel labelDays;
	
	//--- Model
	private DateFormat dateFormat;
	//---
	
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
		this.parameterList.add( DEFAULT_DIGRESSION );
		this.parameterList.add( DEFAULT_DAYS );
		
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
		
		this.setLayout( new GridBagLayout() );
		
		labelDays = new JLabel( "Napok" );
		fieldDays = new JFormattedTextField( new DefaultFormatterFactory( new NumberFormatter() ) );
		fieldDays.setText( parameterList.get(PARAMETERORDER_DAYS).toString() );
		fieldDays.setInputVerifier( new ValueVerifier(parameterList, type, DEFAULT_DAYS, PARAMETERORDER_DAYS) );
		
		dateFormat = new SimpleDateFormat((String)parameterList.get(PARAMETERORDER_FORMAT));
		dateFormat.setLenient( false );

		int gridY = 0;
		GridBagConstraints c = new GridBagConstraints();		
		c.insets = new Insets(0,0,0,0);
		
		//
		//DateForm
		//
		JLabel labelDateFormat = new JLabel( CommonOperations.getTranslation("editor.label.variable.parametertype.todaydate.format") );
		
		fieldDateFormat = new DateFormComboBox();
		for( int i = 0; i < DateFormListEnum.getSize(); i++ ){		
			fieldDateFormat.addItem( DateFormListEnum.getFormDateByIndex(i) );
		}
		fieldDateFormat.addItemListener( new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {

				int index = fieldDateFormat.getSelectedIndex();	
				
				//Ha megvaltoztattam a tipust
				if( e.getStateChange() == java.awt.event.ItemEvent.SELECTED ){ 
					
					DateFormat newDateFormat = DateFormListEnum.getFormDateByIndex(index).getDateFormat();
					
					parameterList.set(PARAMETERORDER_FORMAT, DateFormListEnum.getFormDateByIndex(index).getStringDateFormat() );
					parameterList.set(PARAMETERORDER_MASK, DateFormListEnum.getFormDateByIndex(index).getStringMask() );

					dateFormat = newDateFormat;
				}
				
			}
		});		
		
		//
		//DateDigression
		//
		JLabel labelDateDigression = new JLabel( CommonOperations.getTranslation("editor.label.variable.parametertype.todaydate.digression") );
		
		fieldDateDigression = new DateDigressionComboBox();
		for( int i = 0; i < DateDigressionListEnum.getSize(); i++ ){		
			fieldDateDigression.addItem( DateDigressionListEnum.getDateDigressionByIndex(i) );
		}
		fieldDateDigression.addItemListener( new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {

				int index = fieldDateDigression.getSelectedIndex();	
				
				//Ha megvaltoztattam a tipust
				if( e.getStateChange() == java.awt.event.ItemEvent.SELECTED ){ 
					
					parameterList.set( PARAMETERORDER_DIGRESSION, DateDigressionListEnum.getDateDigressionByIndex(index).name() );

					VariableParametersTodayDateComponent.this.remove( labelDays );
					VariableParametersTodayDateComponent.this.remove( fieldDays );
					
					//PLUS/MINUS
					if( index == DateDigressionListEnum.PLUS.getIndex() || index == DateDigressionListEnum.MINUS.getIndex() ){

						GridBagConstraints c = new GridBagConstraints();		
						c.insets = new Insets(0,0,0,0);
						
						//DAYS
						int gridY=0;		
						c.gridy = gridY;
						c.gridx = 2;
						c.gridwidth = 1;
						c.weighty = 0;
						c.fill = GridBagConstraints.HORIZONTAL;
						c.weightx = 0;
						c.anchor = GridBagConstraints.WEST;
						VariableParametersTodayDateComponent.this.add( labelDays, c );
						
						gridY++;
						c.gridy = gridY;
						c.gridx = 2;
						c.gridwidth = 1;
						c.weighty = 0;
						c.fill = GridBagConstraints.HORIZONTAL;
						c.weightx = 0;
						c.anchor = GridBagConstraints.WEST;
						VariableParametersTodayDateComponent.this.add( fieldDays, c );		
						
					}					
					VariableParametersTodayDateComponent.this.revalidate();					
				}				
			}
		});	
		
		//DATE FORMAT
		gridY=0;		
		c.gridy = gridY;
		c.gridx = 0;
		c.gridwidth = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0;
		c.anchor = GridBagConstraints.WEST;
		this.add( labelDateFormat, c );
		
		gridY++;
		c.gridy = gridY;
		c.gridx = 0;
		c.gridwidth = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0;
		c.anchor = GridBagConstraints.WEST;
		this.add( fieldDateFormat, c );

		//DATE DIGRESSION
		gridY=0;		
		c.gridy = gridY;
		c.gridx = 1;
		c.gridwidth = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0;
		c.anchor = GridBagConstraints.WEST;
		this.add( labelDateDigression, c );
		
		gridY++;
		c.gridy = gridY;
		c.gridx = 1;
		c.gridwidth = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0;
		c.anchor = GridBagConstraints.WEST;
		this.add( fieldDateDigression, c );
		
		//Kitolto
		c.gridy = gridY;
		c.gridx = 3;
		c.gridwidth = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.anchor = GridBagConstraints.WEST;
		this.add( new JLabel(), c );
		
		fieldDateFormat.setSelectedIndex(-1);
		fieldDateDigression.setSelectedIndex(-1);
		
		DateFormListEnum fd = DateFormListEnum.getDateFormByMask( (String)parameterList.get(PARAMETERORDER_MASK) );
		fieldDateFormat.setSelectedIndex( fd.getIndex() );
		
		fieldDateDigression.setSelectedIndex( DateDigressionListEnum.getDateDigressionByName( (String)parameterList.get(PARAMETERORDER_DIGRESSION) ).getIndex() );

	}	
	
	@Override
	public void setEnableModify(boolean enable) {
		fieldDateFormat.setEnabled(enable);
		fieldDateDigression.setEnabled(enable);
	}

	@Override
	public Component getComponent() {
		return this;
	}

	@Override
	public ArrayList<Object> getParameters() {
		return parameterList;
	}
	
	class DateFormComboBox extends JComboBox<DateFormListEnum>{

		public DateFormComboBox(){
			this.setRenderer(new MyRenderer());
		}
		
		class MyRenderer extends BasicComboBoxRenderer {

			private static final long serialVersionUID = -4562181616721578685L;

			@Override
			public Component getListCellRendererComponent(JList list, Object value,	int index, boolean isSelected, boolean cellHasFocus) {
								
				Component c = super.getListCellRendererComponent(list, ((DateFormListEnum)value).getTranslatedName(), index, isSelected, cellHasFocus);

				return this;
			}
		}		
	}
	
	class DateDigressionComboBox extends JComboBox<DateDigressionListEnum>{

		public DateDigressionComboBox(){
			this.setRenderer(new MyRenderer());
		}
		
		class MyRenderer extends BasicComboBoxRenderer {

			private static final long serialVersionUID = -2705481306186913190L;

			@Override
			public Component getListCellRendererComponent(JList list, Object value,	int index, boolean isSelected, boolean cellHasFocus) {
								
				Component c = super.getListCellRendererComponent(list, ((DateDigressionListEnum)value).getTranslatedName(), index, isSelected, cellHasFocus);

				return this;
			}
		}		
	}
	
	public static class ValueVerifier extends InputVerifier{
		private ArrayList<Object> parameterList;
		private String defaultValue;
		private int parameterOrder;
		private ParameterTypeListEnum type;
		
		String goodValue = defaultValue;
		
		public ValueVerifier( ArrayList<Object> parameterList, ParameterTypeListEnum type, String defaultValue, int parameterOrder ){
			this.parameterList = parameterList;
			this.defaultValue = defaultValue;
			this.parameterOrder = parameterOrder;
			this.type = type;
		
			goodValue = defaultValue;
		}	
		
		@Override
		public boolean verify(JComponent input) {
			JTextField text = (JTextField)input;
			String possibleValue = text.getText();

			try {
				//Kiprobalja, hogy konvertalhato-e
				Object value = getConverted(possibleValue);//type.getParameterClass(parameterOrder).getConstructor(String.class).newInstance(possibleValue);
				parameterList.set( parameterOrder, value );
				goodValue = possibleValue;
				
				parameterList.set(PARAMETERORDER_DAYS, goodValue );
				
			} catch (Exception e) {
				text.setText( goodValue );
				return false;
			}				
			return true;
		}
		
		public Object getConverted( String possibleValue ) throws Exception{
			return type.getParameterClass(parameterOrder).getConstructor(String.class).newInstance(possibleValue);
		}
 } 	
}
