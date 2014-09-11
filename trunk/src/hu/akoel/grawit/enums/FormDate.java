package hu.akoel.grawit.enums;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.text.MaskFormatter;

public enum FormDate{		
	ddMMyyyy_slash( 0, "dd/MM/yyyy", "dd/MM/yyyy", "##/##/####" ),
	ddMMyyyy_hyphen( 1, "dd-MM-yyyy", "dd-MM-yyyy", "##-##-####" ),
	ddMMyyyy_dot( 2, "dd.MM.yyyy", "dd.MM.yyyy", "##.##.####" ),	
	;
	
	private String translatedName;
	private int index;
	private String stringFormat;
	private String stringMask;
	
	private FormDate( int index, String translatedName, String stringFormat, String stringMask ){
	
		this.index = index;
		this.translatedName = translatedName;
		this.stringFormat = stringFormat;
		this.stringMask = stringMask;
	}
	
	public String getTranslatedName(){
		return translatedName;
	}	
	
	public int getIndex(){
		return index;
	}
	
	public DateFormat getDateFormat(){
		return new SimpleDateFormat( stringFormat );
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
	
	public static FormDate getFormatByIndex( int index ){
		switch (index){
		case 0:	return ddMMyyyy_slash;
		case 1: return ddMMyyyy_hyphen;
		case 2: return ddMMyyyy_dot;
		default: return ddMMyyyy_slash;
		}
	}
}
