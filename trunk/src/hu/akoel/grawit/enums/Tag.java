package hu.akoel.grawit.enums;

public enum Tag{
	BASEROOT("base"),									
	BASENODE("node"),
	BASECOLLECTOR("collector"),							
	BASEELEMENT("element"),
	
	PARAMROOT("param"),									
	PARAMNODE("node"),
	PARAMNORMALELEMENTCOLLECTOR("normalelementcollector"),		
	PARAMLOOPELEMENTCOLLECTOR("loopelementcollector"),			
	PARAMELEMENT("element"),
	
	VARIABLEROOT("variable"),
	VARIABLENODE("node"),
	VARIABLEELEMENT("element"),
	VARIABLEPARAMETER("parameter"),		

	TESTCASEROOT("testcase"),
	TESTCASENODE("node"),
	TESTCASECASE("case"),
	TESTCASECOLLECTOR("paramcollector"),				
	TESTCASECUSTOMPAGE("custom"),	

	RUNROOT("run"),
	RUNNODE("node"),
	
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
