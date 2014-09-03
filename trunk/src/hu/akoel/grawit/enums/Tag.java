package hu.akoel.grawit.enums;

public enum Tag{
	BASEROOT("basepage"),
	BASENODE("node"),
	BASEPAGE("page"),
	BASEELEMENT("element"),
		
	PARAMROOT("parampage"),
	PARAMNODE("node"),
	PARAMPAGE("page"),
	PARAMELEMENT("element"),
	
	VARIABLEROOT("variable"),
	VARIABLENODE("node"),
	VARIABLEELEMENT("element"),
	VARIABLEPARAMETER("parameter"),		

	TESTCASEROOT("testcase"),
	TESTCASENODE("node"),
	TESTCASECASE("case"),
	TESTCASEPAGE("page"),
	TESTCASECUSTOM("special"),
	
	SPECIALROOT("special"),
	SPECIALNODE("node"),
	SPECIALOPEN("open"),
	SPECIALCLOSE("close"),
	
	;
	private String name;
	
	private Tag( String name ){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
}
