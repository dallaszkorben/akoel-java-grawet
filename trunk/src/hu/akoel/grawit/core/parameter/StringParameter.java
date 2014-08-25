package hu.akoel.grawit.core.parameter;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.enums.VariableType;

public class StringParameter implements ElementParameter{
	private String name;
	private String stringValue;
	private String stringTitle;
	
	private final static int parameterNumber = 1;
	
	private final static VariableType type = VariableType.STRING_PARAMETER;
	
	public StringParameter(){
		this.name = "";
		this.stringValue = "";		
		common();
	}
	
	public StringParameter( String name, String constant ){
		this.name = name;
		this.stringValue = constant;
		
		common();
	}

	private void common(){
		this.stringTitle = CommonOperations.getTranslation("editor.title.variabletype.string.string");
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
		return stringValue;
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
			attr.setValue( stringTitle );
			elementElement.setAttributeNode(attr);	
		
			//Value
			attr = document.createAttribute( ATTR_VALUE );
			attr.setValue( stringValue );
			elementElement.setAttributeNode(attr);	

			return elementElement;
			
		}else{
			
			return null;
			
		}
	}

	@Override
	public String getParameterName(int index) {
		if( index == 0 ){

			return stringTitle;
			
		}
		return null;
	}

	@Override
	public Object getParameterValue(int index) {
		if( index == 0 ){

			return stringValue;
			
		}
		return null;
	}

	@Override
	public Class<?> getParameterClass(int index) {
		if( index == 0 ){

			return stringValue.getClass();
			
		}
		return null;
	}

	@Override
	public void setParameterValue(Object value, int index) {
		if( index == 0 ){
			stringValue = (String)value;
		}
		
	}


	
	
	
}
