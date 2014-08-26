package hu.akoel.grawit.core.parameter;

import hu.akoel.grawit.CommonOperations;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class RDParameter implements EParameter{
	String name;
	Calendar begin;
	Calendar end;
	SimpleDateFormat dateFormat;
	
	public RDParameter( String name, Calendar begin, Calendar end, SimpleDateFormat dateFormat ){
		this.name = name;
		this.begin = begin;
		this.end = end;
		this.dateFormat = dateFormat;		
	}
	
	@Override
	public String getName() {		
		return name;
	}

	@Override
	public String getValue() {		
		return CommonOperations.getRandomStringDate(begin, end, dateFormat);
	}

}
