package hu.akoel.grawit.core.elements;

import hu.akoel.grawit.IdentificationType;
import hu.akoel.grawit.VariableSample;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ElementBase {
//	private WebDriver driver;
	private String name;
	//private By by;
	private VariableSample variableSample;
	private String frame;
	private String identifier;
	private IdentificationType identificationType;


//	public ElementBase( WebDriver driver, String name, By by, VariableSample variableSample, String frame){
	public ElementBase( String name, String identifier, IdentificationType identificationType, VariableSample variableSample, String frame){
		//common( driver, name, by, variableSample, frame );
		common( name, identifier, identificationType, variableSample, frame );	
	}

//	public ElementBase( WebDriver driver, String name, By by, VariableSample variableSample ){
	public ElementBase( String name, String identifier, IdentificationType identificationType, VariableSample variableSample ){
		//common( driver, name, by, variableSample, null );		
		common( name, identifier, identificationType, variableSample, null );
	}

	public ElementBase( ElementBase element ){
//		this.driver = element.getDriver();
		this.name = element.getName();
		this.identifier = getIdentifier();
		this.identificationType = getIdentificationType();
		this.variableSample = element.getVariableSample();
	}

//	private void common( WebDriver driver, String name, By by, VariableSample variableSample, String frame ){
	private void common( String name, String identifier, IdentificationType identificationType, VariableSample variableSample, String frame ){		
//		this.driver = driver;
		this.name = name;
		this.identifier = identifier;
		this.identificationType = identificationType;
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

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public IdentificationType getIdentificationType() {
		return identificationType;
	}

	public void setIdentificationType(IdentificationType identificationType) {
		this.identificationType = identificationType;
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
