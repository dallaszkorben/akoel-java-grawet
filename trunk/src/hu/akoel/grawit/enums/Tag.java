package hu.akoel.grawit.enums;

public enum Tag{
	BASEROOT("base"),									
	BASEFOLDER("node"),
	BASECOLLECTOR("collector"),							
	BASEELEMENT("element"),
	
	STEPROOT("step"),									
	STEPFOLDER("node"),
	STEPNORMALELEMENTCOLLECTOR("normalelementcollector"),		
	STEPLOOPELEMENTCOLLECTOR("loopelementcollector"),			
	STEPELEMENT("element"),
	
	CONSTANTROOT("constant"),
	CONSTANTFOLDER("node"),
	CONSTANTELEMENT("element"),
	CONSTANTPARAMETER("parameter"),		

	TESTCASEROOT("testcase"),
	TESTCASEFOLDER("node"),
	TESTCASECASE("case"),
	TESTCASESTEPCOLLECTOR("stepcollector"),				
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
