package hu.akoel.grawit.core.parameter;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.enums.VariableType;

public class RandomStringParameter implements ElementParameter{
	private String name;
	private String lengthTitle;
	private Integer lengthValue;
	private String sampleStringTitle;
	private String sampleStringValue;
	
	private final static int parameterNumber = 2;
	
	private final static VariableType type = VariableType.RANDOM_STRING_PARAMETER;
	
	public RandomStringParameter(){
		super();
		this.name = "";
		this.lengthValue = 1;
		this.sampleStringValue = "";
		common();
	}
	
	public RandomStringParameter( String name, String sampleStringValue, Integer lengthValue ){
		super();
		this.name = name;
		this.lengthValue = lengthValue;
		this.sampleStringValue = sampleStringValue;
		
		common();
	}

	private void common(){
		this.lengthTitle = CommonOperations.getTranslation("editor.title.variabletype.randomstring.length");
		this.sampleStringTitle = CommonOperations.getTranslation("editor.title.variabletype.randomstring.samplestring");		
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public void setName(String name) {
		this.name = name;		
	}

	@Override
	public String getValue() {
		return CommonOperations.getRandomString(sampleStringValue, lengthValue );
	}
	
	@Override
	public VariableType getType(){
		return type;
	}

	@Override
	public int getParameterNumber() {
		return parameterNumber;
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
			attr.setValue( sampleStringTitle );
			elementElement.setAttributeNode(attr);	
		
			//Value
			attr = document.createAttribute( ATTR_VALUE );
			attr.setValue( sampleStringValue );
			elementElement.setAttributeNode(attr);	

			return elementElement;
			
		}else if( index == 1 ){

			//Name
			attr = document.createAttribute( ATTR_NAME );
			attr.setValue( lengthTitle );
			elementElement.setAttributeNode(attr);	
		
			//Value
			attr = document.createAttribute( ATTR_VALUE );
			attr.setValue( lengthValue.toString() );
			elementElement.setAttributeNode(attr);	

			return elementElement;
			
		}else{
		
			return null;
			
		}
	}

	@Override
	public String getParameterName(int index) {
		if( index == 0 ){

			return sampleStringTitle;
			
		}else if( index == 1 ){

			return lengthTitle;
			
		}
		return null;
	}

	@Override
	public Object getParameterValue(int index) {
		if( index == 0 ){

			return sampleStringValue;
			
		}else if( index == 1 ){

			return lengthValue;
			
		}
		return null;
	}

	@Override
	public Class<?> getParameterClass(int index) {
		if( index == 0 ){

			return sampleStringValue.getClass();
			
		}else if( index == 1 ){

			return lengthValue.getClass();
			
		}
		return null;
	}

	@Override
	public void setParameterValue(Object value, int index) {
		if( index == 0 ){

			sampleStringValue = (String)value;
			
		}else if( index == 1 ){

			lengthValue = (Integer)value;
			
		}
		
	}

	
	
	
}
