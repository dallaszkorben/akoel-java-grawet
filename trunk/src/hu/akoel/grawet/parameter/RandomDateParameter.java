package hu.akoel.grawet.parameter;

import hu.akoel.grawet.CommonOperations;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class RandomDateParameter implements ElementParameter{
	String name;
	Calendar begin;
	Calendar end;
	SimpleDateFormat dateFormat;
	
	public RandomDateParameter( String name, Calendar begin, Calendar end, SimpleDateFormat dateFormat ){
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
