package hu.akoel.grawit.enums;

public enum Tag{
	BASEROOT("base"),									
	BASEFOLDER("node"),
	BASECOLLECTOR("collector"),							
	BASEELEMENT("element"),
	
	STEPROOT("param"),									
	STEPFOLDER("node"),
	STEPNORMALELEMENTCOLLECTOR("normalelementcollector"),		
	STEPLOOPELEMENTCOLLECTOR("loopelementcollector"),			
	STEPELEMENT("element"),
	
	VARIABLEROOT("variable"),
	VARIABLEFOLDER("node"),
	VARIABLEELEMENT("element"),
	VARIABLEPARAMETER("parameter"),		

	TESTCASEROOT("testcase"),
	TESTCASEFOLDER("node"),
	TESTCASECASE("case"),
	TESTCASEPARAMCOLLECTOR("paramcollector"),				
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
	DRIVERFOLDER("node"),
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
