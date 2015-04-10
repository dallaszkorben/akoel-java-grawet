package hu.akoel.grawit.gui.editors.component.constantparameter;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.enums.list.ConstantTypeListEnum;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ConstantRandomDoubleComponent extends JPanel implements ConstantComponentInterface{

	private static final long serialVersionUID = 3522710006727176792L;
	
	private static final String DEFAULT_FROM = "1";
	private static final String DEFAULT_TO = "100";
	private static final String DEFAULT_DECIMALLENGTH = "2";
	private static final int PARAMETERORDER_FROM = 0;
	private static final int PARAMETERORDER_TO = 1;
	private static final int PARAMETERORDER_DECIMALLENGTH = 2;
	
	//--- Adatmodel
	private JTextField fieldFrom;
	private JTextField fieldTo;
	private JTextField fieldDecimalLength;
	//---
	
	private ArrayList<Object> parameterList;

	/**
	 * Uj lista
	 * 
	 * @param type
	 */
	public ConstantRandomDoubleComponent( ConstantTypeListEnum type ){
		super();

		//parameter lista letrehozasa es feltoltese default ertekekkel
		this.parameterList = new ArrayList<>();
		this.parameterList.add( DEFAULT_FROM );
		this.parameterList.add( DEFAULT_TO );
		this.parameterList.add( DEFAULT_DECIMALLENGTH );
		
		common( type );		
		
	}
	
	/**
	 * Letezo lista
	 * 
	 * @param type
	 * @param parameterList
	 */
	public ConstantRandomDoubleComponent( ConstantTypeListEnum type, ArrayList<Object> parameterList ){
		super();
		
		//Parameter lista feltoltese a letezo ertekekkel
		this.parameterList = parameterList;
		
		common( type );		
		
	}
	
	private void common( ConstantTypeListEnum type ){
//		this.type = type;
		
		this.setLayout( new GridBagLayout() );
		
		//
		// From field
		//
		
		JLabel labelFrom = new JLabel( CommonOperations.getTranslation("editor.label.constant.parametertype.randomdouble.from") );
		
		fieldFrom = new JTextField( parameterList.get(PARAMETERORDER_FROM).toString());
		fieldFrom.setColumns(5);
		fieldFrom.setInputVerifier( new CommonOperations.ValueVerifier(parameterList, type, DEFAULT_FROM, PARAMETERORDER_FROM) );
		
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
		JLabel labelTo = new JLabel( CommonOperations.getTranslation("editor.label.constant.parametertype.randomdouble.to") );

		fieldTo = new JTextField( parameterList.get(PARAMETERORDER_TO).toString());
		fieldTo.setColumns(5);
		fieldTo.setInputVerifier( new CommonOperations.ValueVerifier(parameterList, type, DEFAULT_TO, PARAMETERORDER_TO) );
		
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
		// Decimal length field
		//
		JLabel labelDecimalLength = new JLabel( CommonOperations.getTranslation("editor.label.constant.parametertype.randomdouble.decimallength") );

		fieldDecimalLength = new JTextField( parameterList.get(PARAMETERORDER_DECIMALLENGTH).toString());
		fieldDecimalLength.setColumns(5);
		fieldDecimalLength.setInputVerifier( new CommonOperations.ValueVerifier(parameterList, type, DEFAULT_DECIMALLENGTH, PARAMETERORDER_DECIMALLENGTH ) );
		
		gridY=0;
		
		c.gridy = 0;
		c.gridx = 2;
		c.gridwidth = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0;
		c.anchor = GridBagConstraints.WEST;
		this.add( labelDecimalLength, c );
		
		gridY++;
		c.gridy = 1;
		c.gridx = 2;
		c.gridwidth = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0;
		c.anchor = GridBagConstraints.WEST;
		this.add( fieldDecimalLength, c );
				
		
		
		//
		//Kitolto
		//
		gridY++;
		c.gridy = 1;
		c.gridx = 3;
		c.gridwidth = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.anchor = GridBagConstraints.WEST;
		this.add( new JLabel(), c );
		
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

}
