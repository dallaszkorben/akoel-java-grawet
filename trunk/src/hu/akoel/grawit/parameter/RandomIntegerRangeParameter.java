package hu.akoel.grawit.parameter;

import hu.akoel.grawit.CommonOperations;

public class RandomIntegerRangeParameter implements ElementParameter{
	String name;
	int from;
	int to;
	
	public RandomIntegerRangeParameter( String name, int from, int to ){
		this.name = name;
		this.from = from;
		this.to = to;
	}
	
	@Override
	public String getName() {		
		return name;
	}

	@Override
	public String getValue() {
		return CommonOperations.getRandomStringIntegerRange(from, to);
	}

}
