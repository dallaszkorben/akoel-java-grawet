package hu.akoel.grawit.enums;

public enum Tag{
	BASEROOT("basepage"),
	BASENODE("node"),
	BASEPAGE("page"),
	BASEELEMENT("element"),
	//NORMALBASEELEMENT("element"),
	//SPECIALBASEELEMENT("element"),
		
	PARAMROOT("parampage"),
	PARAMNODE("node"),
	PARAMPAGESPECIFIC("page"),
	PARAMPAGENOSPECIFIC("pagenospecific"),
	PARAMELEMENT("element"),
	//PARAMELEMENTRELATIVE("element"),
	//PARAMELEMENTABSOLUTE("elementabsolute"),
	
	VARIABLEROOT("variable"),
	VARIABLENODE("node"),
	VARIABLEELEMENT("element"),
	VARIABLEPARAMETER("parameter"),		

	TESTCASEROOT("testcase"),
	TESTCASENODE("node"),
	TESTCASECASE("case"),
	TESTCASEPARAMPAGE("param"),
	TESTCASECUSTOMPAGE("custom"),
	
	SPECIALROOT("special"),
	SPECIALNODE("node"),
	SPECIALOPEN("open"),
	SPECIALCLOSE("close"),
	SPECIALCUSTOM("custom"),
		
	SCRIPTROOT("script"),
	SCRIPTNODE("node"),
	SCRIPTELEMENT("element"),
	
	DRIVERROOT("driver"),
	DRIVERNODE("node"),
	DRIVEREXPLORER("explorer"),
	DRIVERFIREFOX("firefox"),
	DRIVERFIREFOXPROPERTY("property"),
	DRIVEREXPLORERCAPABILITY("capability"),
	
	;
	private String name;
	
	private Tag( String name ){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
}
