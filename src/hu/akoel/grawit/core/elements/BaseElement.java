package hu.akoel.grawit.core.elements;

import hu.akoel.grawit.IdentificationType;
import hu.akoel.grawit.VariableSample;

public class BaseElement {
	private String name;
	private VariableSample variableSample;
	private String frame;
	private String identifier;
	private IdentificationType identificationType;


	public BaseElement( String name, String identifier, IdentificationType identificationType, VariableSample variableSample, String frame){
		common( name, identifier, identificationType, variableSample, frame );	
	}

	public BaseElement( String name, String identifier, IdentificationType identificationType, VariableSample variableSample ){
		common( name, identifier, identificationType, variableSample, null );
	}

	public BaseElement( BaseElement element ){
		this.name = element.getName();
		this.identifier = getIdentifier();
		this.identificationType = getIdentificationType();
		this.variableSample = element.getVariableSample();
	}

	private void common( String name, String identifier, IdentificationType identificationType, VariableSample variableSample, String frame ){		
		this.name = name;
		this.identifier = identifier;
		this.identificationType = identificationType;
		this.variableSample = variableSample;
		this.frame = frame;
	}

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
