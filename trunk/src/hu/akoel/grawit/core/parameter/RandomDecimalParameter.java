package hu.akoel.grawit.core.parameter;

import hu.akoel.grawit.CommonOperations;

public class RandomDecimalParameter implements ElementParameter{
	String name;
	int intLength;
	int decimalLength;
	
	RandomDecimalParameter( String name, int intLength, int decimalLength ){
		this.name = name;
		this.intLength = intLength;
		this.decimalLength = decimalLength;
	}
	
	@Override
	public String getName( ) {		
		return name;
	}

	@Override
	public String getValue() {
		return CommonOperations.getRandomStringDecimal(intLength, decimalLength);
	}

}
