package hu.akoel.grawit.enums;

public enum Tag{
	BASEROOT("base"),									//BASEROOT("basepage")
	BASENODE("node"),
	BASECOLLECTOR("collector"),							//BASECOLLECTOR("page"),
	BASEELEMENT("element"),
	
	PARAMROOT("param"),									//PARAMROOT("parampage"), 
	PARAMNODE("node"),
	PARAMNORMALELEMENTCOLLECTOR("normalelementcollector"),		//PARAMNORMALELEMENTCOLLECTOR("page"), //
	PARAMLOOPELEMENTCOLLECTOR("loopelementcollector"),			//PARAMLOOPELEMENTCOLLECTOR("paramloop"),
	PARAMELEMENT("element"),
	
	VARIABLEROOT("variable"),
	VARIABLENODE("node"),
	VARIABLEELEMENT("element"),
	VARIABLEPARAMETER("parameter"),		

	TESTCASEROOT("testcase"),
	TESTCASENODE("node"),
	TESTCASECASE("case"),
	TESTCASECOLLECTOR("paramcollector"),				//TESTCASECOLLECTOR("param"),
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
