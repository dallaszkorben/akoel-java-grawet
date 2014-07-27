package hu.akoel.grawet.parameter;

import hu.akoel.grawet.CommonOperations;

public class RandomStringParameter implements ElementParameter{
	String name;
	String sampleString;
	int size;

	public RandomStringParameter( String name, String sampleString, int size ){
		this.name = name;
		this.sampleString = sampleString;
		this.size = size;
	}
	
	@Override
	public String getName() {		
		return name;
	}

	@Override
	public String getValue() {		
		return CommonOperations.getRandomString(sampleString, size);
	}

}
