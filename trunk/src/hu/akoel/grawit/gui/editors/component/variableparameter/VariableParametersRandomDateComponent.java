package hu.akoel.grawit.gui.editors.component.variableparameter;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.enums.FormDate;
import hu.akoel.grawit.enums.ParameterType;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

public class VariableParametersRandomDateComponent extends JPanel implements VariableParametersComponentInterface{

	private static final long serialVersionUID = -3266616532152280622L;
	
	private static final String DEFAULT_FROM = "1980/01/01";
	private static final String DEFAULT_TO = "2000/01/01";
	private static final int PARAMETERORDER_FROM = 0;
	private static final int PARAMETERORDER_TO = 1;
	private static final int PARAMETERORDER_FORMAT = 2;	
	
//	private JTextField fieldFrom;
//	private JTextField fieldTo;
	private JFormattedTextField fieldFrom;
	private JFormattedTextField fieldTo;
	private FormDateComboBox fieldFormatDate;
	
	private ParameterType type;
	
	private ArrayList<Object> parameterList;
	
	DateFormat defaultFormat = new SimpleDateFormat("yyyy/MM/dd");
	MaskFormatter defautlFromMaskFormatter = null;
	MaskFormatter defautlToMaskFormatter = null;

	/**
	 * Uj lista
	 * 
	 * @param type
	 */
	public VariableParametersRandomDateComponent( ParameterType type ){
		super();
		
		try {
			defautlFromMaskFormatter = new MaskFormatter("####/##/##");
			defautlToMaskFormatter = new MaskFormatter("####/##/##");
		} catch (ParseException e1) {}
		this.parameterList = new ArrayList<>();
		try {
			this.parameterList.add( defaultFormat.parse(DEFAULT_FROM) );
			this.parameterList.add( defaultFormat.parse(DEFAULT_TO) );
			this.parameterList.add( defaultFormat );
		} catch (ParseException e) {
			e.printStackTrace();
			System.exit(-1);
		}		
		
		common( type );		
		
	}
	
	/**
	 * Letezo lista
	 * 
	 * @param type
	 * @param parameterList
	 */
	public VariableParametersRandomDateComponent( ParameterType type, ArrayList<Object> parameterList ){
		super();
		
		//Parameter lista feltoltese a letezo ertekekkel
		this.parameterList = parameterList;
		
		common( type );		
		
	}
	
	private void common( ParameterType type ){
		this.type = type;
		
		this.setLayout( new GridBagLayout() );
		
		//
		// From field
		//		
		JLabel labelFrom = new JLabel( CommonOperations.getTranslation("editor.label.variable.parametertype.randomdate.from") );
		
		fieldFrom = new JFormattedTextField( defautlFromMaskFormatter );		
		fieldFrom.setText( ((DateFormat)parameterList.get(PARAMETERORDER_FORMAT)).format( ((Date)parameterList.get(PARAMETERORDER_FROM))) );
		
		fieldFrom.setColumns(6);
		//fieldFrom.setInputVerifier( new CommonOperations.ValueVerifier(parameterList, type, DEFAULT_FROM, PARAMETERORDER_FROM) );
		/*fieldFrom.setInputVerifier(new InputVerifier() {
			String goodValue = DEFAULT_FROM;
			
			@Override
			public boolean verify(JComponent input) {
				JTextField text = (JTextField)input;
				String possibleValue = text.getText();

				try {
					//Kiprobalja, hogy konvertalhato-e
					Object value = DateIntervalComponent.this.type.getParameterClass(PARAMETERORDER_FROM).getConstructor(String.class).newInstance(possibleValue);
					parameterList.set( PARAMETERORDER_FROM, value );
					goodValue = possibleValue;
					
				} catch (Exception e) {
					text.setText( goodValue );
					return false;
				}				
				return true;
			}
		});*/
		
		int gridY = 0;
		GridBagConstraints c = new GridBagConstraints();		
		c.insets = new Insets(0,0,0,0);
		
		c.gridy = 0;
		c.gridx = 0;
		c.gridwidth = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0;
		c.anchor = GridBagConstraints.WEST;
		this.add( labelFrom, c );
		
		gridY++;
		c.gridy = 1;
		c.gridx = 0;
		c.gridwidth = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0;
		c.anchor = GridBagConstraints.WEST;
		this.add( fieldFrom, c );		

		//
		// To field
		//
		JLabel labelTo = new JLabel( CommonOperations.getTranslation("editor.label.variable.parametertype.randomdate.to") );

		fieldTo = new JFormattedTextField( defautlToMaskFormatter);
		fieldTo.setText( ((DateFormat)parameterList.get(PARAMETERORDER_FORMAT)).format( ((Date)parameterList.get(PARAMETERORDER_TO))) );
	
		fieldTo.setColumns(6);
		//fieldTo.setInputVerifier( new CommonOperations.ValueVerifier(parameterList, type, DEFAULT_TO, PARAMETERORDER_TO) );
		/*fieldTo.setInputVerifier(new InputVerifier() {
			String goodValue = DEFAULT_TO;
			
			@Override
			public boolean verify(JComponent input) {
				JTextField text = (JTextField)input;
				String possibleValue = text.getText();

				try {
					//Kiprobalja, hogy konvertalhato-e
					Object value = DateIntervalComponent.this.type.getParameterClass(PARAMETERORDER_TO).getConstructor(String.class).newInstance(possibleValue);
					parameterList.set( PARAMETERORDER_TO, value );
					goodValue = possibleValue;
					
				} catch (Exception e) {
					text.setText( goodValue );
					return false;
				}				
				return true;
			}
		});*/
		
		gridY=0;
		
		c.gridy = 0;
		c.gridx = 1;
		c.gridwidth = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0;
		c.anchor = GridBagConstraints.WEST;
		this.add( labelTo, c );
		
		gridY++;
		c.gridy = 1;
		c.gridx = 1;
		c.gridwidth = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0;
		c.anchor = GridBagConstraints.WEST;
		this.add( fieldTo, c );
		
		//
		//FormDate date
		//
		JLabel labelFormatDate = new JLabel( CommonOperations.getTranslation("editor.label.variable.parametertype.randomdate.format") );
		
		fieldFormatDate = new FormDateComboBox();
		fieldFormatDate.addItem( FormDate.getFormatByIndex(0) );
		fieldFormatDate.addItem( FormDate.getFormatByIndex(1) );
		fieldFormatDate.addItem( FormDate.getFormatByIndex(2) );
		fieldFormatDate.addItemListener( new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {

				int index = fieldFormatDate.getSelectedIndex();					

				//Ha megvaltoztattam a tipust
				if( e.getStateChange() == java.awt.event.ItemEvent.SELECTED ){ 
					
					DateFormat oldDateFormat = (DateFormat)parameterList.get(PARAMETERORDER_FORMAT);					
					DateFormat newDateFormat = FormDate.getFormatByIndex(index).getDateFormat();
					//dateFormat.parse( fieldTo.getText() )
					
					try {
				
						String oldFromText = fieldFrom.getText();
						String oldToText = fieldTo.getText();
						
						MaskFormatter frommf = FormDate.getFormatByIndex(index).getMask();
						MaskFormatter tomf = FormDate.getFormatByIndex(index).getMask();
						DefaultFormatterFactory fromFactory = new DefaultFormatterFactory(frommf);		
						DefaultFormatterFactory toFactory = new DefaultFormatterFactory(tomf);	
						fieldFrom.setFormatterFactory(fromFactory);
						fieldTo.setFormatterFactory(toFactory);
						
						fieldTo.setText( newDateFormat.format( oldDateFormat.parse(oldToText) ).toString() );
						fieldFrom.setText( newDateFormat.format( oldDateFormat.parse(oldFromText) ).toString() );
						parameterList.set(PARAMETERORDER_FORMAT, newDateFormat );
						
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}					
				}
				
			}
		});	
		
		gridY=0;		
		c.gridy = 0;
		c.gridx = 2;
		c.gridwidth = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0;
		c.anchor = GridBagConstraints.WEST;
		this.add( labelFormatDate, c );
		
		gridY++;
		c.gridy = 1;
		c.gridx = 2;
		c.gridwidth = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0;
		c.anchor = GridBagConstraints.WEST;
		this.add( fieldFormatDate, c );

		
		
		 
		
		
		
		
		//Kitolto
		gridY++;
		c.gridy = 1;
		c.gridx = 2;
		c.gridwidth = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.anchor = GridBagConstraints.WEST;
		this.add( new JLabel(), c );
		
		//Default dd/MM/yyy
		fieldFormatDate.setSelectedIndex( FormDate.ddMMyyyy_dot.getIndex() );
		fieldFormatDate.setSelectedIndex( FormDate.ddMMyyyy_slash.getIndex() );	
	}	
	
	@Override
	public void setEnableModify(boolean enable) {
		fieldFrom.setEditable( enable );		
		fieldTo.setEditable( enable );
	}

	@Override
	public Component getComponent() {
		return this;
	}

	@Override
	public ArrayList<Object> getParameters() {
		return parameterList;
	}
	
	class FormDateComboBox extends JComboBox<FormDate>{
				
/*		public void actionPerformed(ActionEvent e) {
			JComboBox cb = (JComboBox)e.getSource();
			String petName = (String)cb.getSelectedItem();
			FormDateComboBox.this.setupdateLabel(petName);
		}
*/
		public FormDateComboBox(){
			this.setRenderer(new MyRenderer());
		}
		
		class MyRenderer extends BasicComboBoxRenderer {

			private static final long serialVersionUID = -4562181616721578685L;

			@Override
			public Component getListCellRendererComponent(JList list, Object value,	int index, boolean isSelected, boolean cellHasFocus) {
								
				Component c = super.getListCellRendererComponent(list, ((FormDate)value).getTranslatedName(), index, isSelected, cellHasFocus);
				// DisableItem item = (DisableItem) value;
				// if (!item.isEnabled ()) {
				// c.setForeground (Color.GRAY);
				// }
				return this;
			}
		}		
	}
}
