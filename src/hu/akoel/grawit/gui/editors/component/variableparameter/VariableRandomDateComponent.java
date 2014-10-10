package hu.akoel.grawit.gui.editors.component.variableparameter;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.ListRenderer;
import hu.akoel.grawit.enums.list.DateFormListEnum;
import hu.akoel.grawit.enums.list.ParameterTypeListEnum;

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
import javax.swing.JPanel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

public class VariableRandomDateComponent extends JPanel implements VariableComponentInterface{

	private static final long serialVersionUID = -3266616532152280622L;
	
	private static final String DEFAULT_FROM = "01/01/1980";
	private static final String DEFAULT_TO = "01/01/2000";
	private static final String DEFAULT_FORMAT = "dd/MM/yyyy";
	private static final String DEFAULT_MASK = "##/##/####";
	private static final int PARAMETERORDER_FROM = 0;
	private static final int PARAMETERORDER_TO = 1;
	private static final int PARAMETERORDER_FORMAT = 2;	
	private static final int PARAMETERORDER_MASK = 3;	
	
	private JFormattedTextField fieldFrom;
	private JFormattedTextField fieldTo;
	private FormDateComboBox fieldFormatDate;
	
	private DateFormat dateFormat;
	private MaskFormatter maskFormatterFrom;
	private MaskFormatter maskFormatterTo;
	
	private ArrayList<Object> parameterList;

	/**
	 * Uj lista
	 * 
	 * @param type
	 */
	public VariableRandomDateComponent( ParameterTypeListEnum type ){
		super();
		
		this.parameterList = new ArrayList<>();

		this.parameterList.add( DEFAULT_FROM );
		this.parameterList.add( DEFAULT_TO );
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
	public VariableRandomDateComponent( ParameterTypeListEnum type, ArrayList<Object> parameterList ){
		super();
		
		//Parameter lista feltoltese a letezo ertekekkel
		this.parameterList = parameterList;
		
		common( type );		
		
	}
	
	private void common( ParameterTypeListEnum type ){
		
		this.setLayout( new GridBagLayout() );
		
		dateFormat = new SimpleDateFormat((String)parameterList.get(PARAMETERORDER_FORMAT));
		dateFormat.setLenient( false );
		Date dateFrom = null;
		Date dateTo = null;
		try {
			maskFormatterFrom = new MaskFormatter((String)parameterList.get(PARAMETERORDER_MASK));
			maskFormatterTo = new MaskFormatter((String)parameterList.get(PARAMETERORDER_MASK));
			dateFrom = dateFormat.parse( (String)parameterList.get(PARAMETERORDER_FROM) );
			dateTo = dateFormat.parse( (String)parameterList.get(PARAMETERORDER_TO) );
		} catch (ParseException e2) {
			e2.printStackTrace();
			//TODO kezelni valahogy
		}
		
		//
		// From field
		//		
		JLabel labelFrom = new JLabel( CommonOperations.getTranslation("editor.label.variable.parametertype.randomdate.from") );
		
		fieldFrom = new JFormattedTextField( maskFormatterFrom );		
		fieldFrom.setText( dateFormat.format( dateFrom ) );
		
		fieldFrom.setColumns(7);
		fieldFrom.setInputVerifier( new CommonOperations.ValueVerifier(parameterList, type, DEFAULT_FROM, PARAMETERORDER_FROM){
			public Object getConverted( String possibleValue ) throws Exception{
				dateFormat.setLenient(false);
				Date param = dateFormat.parse( possibleValue ); 
				return dateFormat.format(param);				
			}
		});
		
		int gridY = 0;
		GridBagConstraints c = new GridBagConstraints();		
		c.insets = new Insets(0,0,0,0);
		
		c.gridy = gridY;
		c.gridx = 0;
		c.gridwidth = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0;
		c.anchor = GridBagConstraints.WEST;
		this.add( labelFrom, c );
		
		gridY++;
		c.gridy = gridY;
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
		
		fieldTo = new JFormattedTextField( maskFormatterTo );		
		fieldTo.setText( dateFormat.format( dateTo ) );
	
		fieldTo.setColumns(7);
		fieldTo.setInputVerifier( new CommonOperations.ValueVerifier(parameterList, type, DEFAULT_TO, PARAMETERORDER_TO){
			public Object getConverted( String possibleValue ) throws Exception{	
				dateFormat.setLenient(false);
				Date param = dateFormat.parse( possibleValue ); 
				return dateFormat.format(param);	
			}
		});
		
		gridY=0;
		
		c.gridy = gridY;
		c.gridx = 1;
		c.gridwidth = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0;
		c.anchor = GridBagConstraints.WEST;
		this.add( labelTo, c );
		
		gridY++;
		c.gridy = gridY;
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
		//Feltoltom a listat
		for( int i = 0; i < DateFormListEnum.getSize(); i++ ){		
			fieldFormatDate.addItem( DateFormListEnum.getFormDateByIndex(i) );
		}
		fieldFormatDate.addItemListener( new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {

				int index = fieldFormatDate.getSelectedIndex();					

				//Ha megvaltoztattam a tipust
				if( e.getStateChange() == java.awt.event.ItemEvent.SELECTED ){ 
					
					//DateFormat oldDateFormat = (DateFormat)parameterList.get(PARAMETERORDER_FORMAT);
					DateFormat newDateFormat = DateFormListEnum.getFormDateByIndex(index).getDateFormat();

					String oldFromText = fieldFrom.getText();
					String oldToText = fieldTo.getText();
					
					try {						
						
						MaskFormatter newMaskFormatterFrom = DateFormListEnum.getFormDateByIndex(index).getMask();
						MaskFormatter newMaskFormatterTo = DateFormListEnum.getFormDateByIndex(index).getMask();
						DefaultFormatterFactory fromFactory = new DefaultFormatterFactory(newMaskFormatterFrom);		
						DefaultFormatterFactory toFactory = new DefaultFormatterFactory(newMaskFormatterTo);	
						fieldFrom.setFormatterFactory(fromFactory);
						fieldTo.setFormatterFactory(toFactory);
						
						//Date newFromDate = oldDateFormat.parse(oldFromText);
						//Date newToDate = oldDateFormat.parse(oldToText);
						Date newDateFrom = dateFormat.parse(oldFromText);
						Date newDateTo = dateFormat.parse(oldToText);
						
						fieldFrom.setValue( newDateFormat.format(newDateFrom ) );
						fieldTo.setValue( newDateFormat.format( newDateTo ) );					
						
						parameterList.set(PARAMETERORDER_FROM, newDateFormat.format(newDateFrom ) );
						parameterList.set(PARAMETERORDER_TO, newDateFormat.format(newDateTo ) );
						parameterList.set(PARAMETERORDER_FORMAT, DateFormListEnum.getFormDateByIndex(index).getStringDateFormat() );
						parameterList.set(PARAMETERORDER_MASK, DateFormListEnum.getFormDateByIndex(index).getStringMask() );

						dateFormat = newDateFormat;
						maskFormatterFrom = newMaskFormatterFrom;
						maskFormatterTo = newMaskFormatterTo;
						
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}					
				}
				
			}
		});	
		
		gridY=0;		
		c.gridy = gridY;
		c.gridx = 2;
		c.gridwidth = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0;
		c.anchor = GridBagConstraints.WEST;
		this.add( labelFormatDate, c );
		
		gridY++;
		c.gridy = gridY;
		c.gridx = 2;
		c.gridwidth = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0;
		c.anchor = GridBagConstraints.WEST;
		this.add( fieldFormatDate, c );

		
		
		 
		
		
		
		
		//Kitolto
		//gridY++;
		c.gridy = gridY;
		c.gridx = 3;
		c.gridwidth = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.anchor = GridBagConstraints.WEST;
		this.add( new JLabel(), c );
		
		//Default dd/MM/yyy
//		fieldFormatDate.setSelectedIndex( FormDate.ddMMyyyy_dot.getIndex() );
	
		
		DateFormListEnum fd = DateFormListEnum.getDateFormByMask( (String)parameterList.get(PARAMETERORDER_MASK) );
		fieldFormatDate.setSelectedIndex( fd.getIndex() );	
	}	
	
	@Override
	public void setEnableModify(boolean enable) {
		fieldFrom.setEditable( enable );		
		fieldTo.setEditable( enable );
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
	
	class FormDateComboBox extends JComboBox<DateFormListEnum>{

		public FormDateComboBox(){
			this.setRenderer(new ListRenderer<DateFormListEnum>());
		}
			
	}
}
