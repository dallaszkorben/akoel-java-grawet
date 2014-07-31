package hu.akoel.grawet.elements;

import hu.akoel.grawet.VariableSample;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ElementBase {
//	private WebDriver driver;
	private String name;
	private By by;
	private VariableSample variableSample;
	private String frame;


//	public ElementBase( WebDriver driver, String name, By by, VariableSample variableSample, String frame){
	public ElementBase( String name, By by, VariableSample variableSample, String frame){
		//common( driver, name, by, variableSample, frame );
		common( name, by, variableSample, frame );	
	}

//	public ElementBase( WebDriver driver, String name, By by, VariableSample variableSample ){
	public ElementBase( String name, By by, VariableSample variableSample ){
		//common( driver, name, by, variableSample, null );		
		common( name, by, variableSample, null );
	}

	public ElementBase( ElementBase element ){
//		this.driver = element.getDriver();
		this.name = element.getName();
		this.by = element.getBy();
		this.variableSample = element.getVariableSample();
	}

//	private void common( WebDriver driver, String name, By by, VariableSample variableSample, String frame ){
	private void common( String name, By by, VariableSample variableSample, String frame ){		
//		this.driver = driver;
		this.name = name;
		this.by = by;
		this.variableSample = variableSample;
		this.frame = frame;
	}


/*	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}
*/
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public By getBy() {
		return by;
	}

	public void setBy(By by) {
		this.by = by;
	}

	public VariableSample getVariableSample() {
		return variableSample;
	}

	public void setVariableSample(VariableSample variableSample) {
		this.variableSample = variableSample;
	}

	public String getFrame(){
		return frame;
	}
	
	public void setFrame( String frame ){
		this.frame = frame;
	}
	
}
