package hu.akoel.grawit.enums.list;


import hu.akoel.grawit.enums.list.elementtypeoperations.ElementTypeOperationsListEnumInterface;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.text.MaskFormatter;

public enum FormDateListEnum implements ElementTypeOperationsListEnumInterface{		
	ddMMyyyy_slash( 0, "dd/MM/yyyy", "dd/MM/yyyy", "##/##/####" ),
	ddMMyyyy_hyphen( 1, "dd-MM-yyyy", "dd-MM-yyyy", "##-##-####" ),
	ddMMyyyy_dot( 2, "dd.MM.yyyy", "dd.MM.yyyy", "##.##.####" ),
	MMyyyy_slash( 3, "MM/yyyy", "MM/yyyy", "##/####" ),
	MMyyyy_hyphen( 4, "MM-yyyy", "MM-yyyy", "##-####" ),
	MMyyyy_dot( 5, "MM.yyyy", "MM.yyyy", "##.####" ),
	yyyy( 6, "yyyy", "yyyy", "####" ),
	;
	
	private String translatedName;
	private int index;
	private String stringFormat;
	private String stringMask;
	
	private FormDateListEnum( int index, String translatedName, String stringFormat, String stringMask ){
	
		this.index = index;
		this.translatedName = translatedName;
		this.stringFormat = stringFormat;
		this.stringMask = stringMask;
	}
	
	public static int getSize(){
		return FormDateListEnum.values().length;
	}
	
	@Override
	public String getTranslatedName(){
		return translatedName;
	}	
	
	@Override
	public int getIndex(){
		return index;
	}
	
	public DateFormat getDateFormat(){
		return new SimpleDateFormat( stringFormat );
	}
	
	public String getStringDateFormat(){
		return stringFormat;
	}
	
	public String getStringMask(){
		return stringMask;
	}
	
	public MaskFormatter getMask(){
		
		MaskFormatter formatter = null;
		try {
			formatter = new MaskFormatter(stringMask);
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		return formatter;
	}
	
	public static FormDateListEnum getFormDateByMask( String mask ){
		
		for( int i = 0; i < getSize(); i++ ){
			if( getFormDateByIndex(i).stringMask.equals( mask ) ){
				return getFormDateByIndex(i);
			}
		}
		
		return null;
		
	}
	
	public static FormDateListEnum getFormDateByIndex( int index ){
		switch (index){
		case 0:	return ddMMyyyy_slash;
		case 1: return ddMMyyyy_hyphen;
		case 2: return ddMMyyyy_dot;	
		case 3: return MMyyyy_slash;
		case 4: return MMyyyy_hyphen;
		case 5: return MMyyyy_dot;
		case 6: return yyyy;		
		default: return ddMMyyyy_slash;
		}
	}
}
