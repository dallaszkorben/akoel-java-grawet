package hu.akoel.grawit.core.parameter;

import hu.akoel.grawit.CommonOperations;

public class RandomYearParameter implements ElementParameter {
	String name;
	int begin;
	int end; 
	 
	public RandomYearParameter( String name, int begin, int end ){
		this.name = name;
		this.begin = begin;
		this.end = end;
	}
	
	@Override
	public String getName() {		
		return null;
	}

	@Override
	public String getValue() {		
		return CommonOperations.getRandomStringYear(begin, end);
	}

}
