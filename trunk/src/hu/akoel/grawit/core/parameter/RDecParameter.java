package hu.akoel.grawit.core.parameter;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.enums.VariableType;

public class RDecParameter implements EParameter{
	private final static int parameterNumber = 2;	
	private final static VariableType type = VariableType.RANDOM_DECIMAL_PARAMETER;
	
	String name;
	String intLengthTitle;
	Integer intLengthValue;
	String decimalLengthTitle;
	Integer decimalLengthValue;
	
	public RDecParameter(){
		this.name = "";
		this.intLengthValue = 3;
		this.decimalLengthValue = 2;
		common();
	}
	
	public RDecParameter( String name, int intLength, int decimalLength ){
		this.name = name;
		this.intLengthValue = intLength;
		this.decimalLengthValue = decimalLength;
		common();
	}
	
	private void common(){
		this.intLengthTitle = CommonOperations.getTranslation("editor.title.variabletype.randomdecimal.intlength");
		this.decimalLengthTitle = CommonOperations.getTranslation("editor.title.variabletype.randomdecimal.decimallength");		
	}
	
	@Override
	public String getName( ) {		
		return name;
	}

	@Override
	public String getValue() {
		return CommonOperations.getRandomStringDecimal(intLengthValue, decimalLengthValue);
	}

	@Override
	public void setName(String name) {
		this.name = name;		
	}

	@Override
	public VariableType getType() {
		return type;
	}

	@Override
	public int getParameterNumber() {
		return parameterNumber ;
	}

	@Override
	public Element getXMLElement(Document document, int index) {
		Attr attr;
		
		//
		//Node element
		//
		Element elementElement = document.createElement( TAG.getName() );
		
		if( index == 0 ){

			//Name
			attr = document.createAttribute( ATTR_NAME );
			attr.setValue( intLengthTitle );
			elementElement.setAttributeNode(attr);	
		
			//Value
			attr = document.createAttribute( ATTR_VALUE );
			attr.setValue( intLengthValue.toString() );
			elementElement.setAttributeNode(attr);	

			return elementElement;
			
		}else if( index == 1 ){

			//Name
			attr = document.createAttribute( ATTR_NAME );
			attr.setValue( decimalLengthTitle );
			elementElement.setAttributeNode(attr);	
		
			//Value
			attr = document.createAttribute( ATTR_VALUE );
			attr.setValue( decimalLengthValue.toString() );
			elementElement.setAttributeNode(attr);	

			return elementElement;
			
		}else{
		
			return null;
			
		}
	}

	@Override
	public String getParameterName(int index) {
		if( index == 0 ){

			return intLengthTitle;
			
		}else if( index == 1 ){

			return decimalLengthTitle;
			
		}
		return null;
	}

	@Override
	public Object getParameterValue(int index) {
		if( index == 0 ){

			return intLengthValue;
			
		}else if( index == 1 ){

			return decimalLengthValue;
			
		}
		return null;
	}

	@Override
	public Class<?> getParameterClass(int index) {
		if( index == 0 ){

			return intLengthValue.getClass();
			
		}else if( index == 1 ){

			return decimalLengthValue.getClass();
			
		}
		return null;
	}

	//TODO atalakitas
	@Override
	public void setParameterValue(Object value, int index) {
		if( index == 0 ){

			intLengthValue = (Integer)value;
			
		}else if( index == 1 ){

			decimalLengthValue = (Integer)value;
			
		}
		
	}

}
