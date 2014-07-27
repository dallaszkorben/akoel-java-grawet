package hu.akoel.grawet.parameter;

import java.text.SimpleDateFormat;

import hu.akoel.grawet.CommonOperations;

public class RandomYearMonthParameter implements ElementParameter{
	String name;
	int beginYear;
	int beginMonth;
	int endYear;
	int endMonth;
	SimpleDateFormat yearMonthFormat;
	
	public RandomYearMonthParameter( String name, int beginYear, int beginMonth, int endYear, int endMonth, SimpleDateFormat yearMonthFormat ){
		this.name = name;
		this.beginYear = beginYear;
		this.beginMonth = beginMonth;
		this.endYear = endYear;
		this.endMonth = endMonth;
		this.yearMonthFormat = yearMonthFormat;
	}
	
	@Override
	public String getName() {		
		return name;
	}

	@Override
	public String getValue() {
		return CommonOperations.getRandomStringYearMonth(beginYear, beginMonth, endYear, endMonth, yearMonthFormat);
	}

}
