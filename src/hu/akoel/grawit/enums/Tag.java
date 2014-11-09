package hu.akoel.grawit.enums;

public enum Tag{
	BASEROOT("basepage"),
	BASENODE("node"),
	BASEPAGE("page"),
	NORMALBASEELEMENT("element"),
	SPECIALBASEELEMENT("specialelement"),
		
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
	TESTCASEPARAMPAGE("param"),
	TESTCASECUSTOMPAGE("custom"),
	
	SPECIALROOT("special"),
	SPECIALNODE("node"),
	SPECIALOPEN("open"),
	SPECIALCLOSE("close"),
	SPECIALCUSTOM("custom"),
		
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
