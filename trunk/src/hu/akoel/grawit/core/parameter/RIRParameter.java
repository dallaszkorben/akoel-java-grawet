package hu.akoel.grawit.core.parameter;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.enums.VariableType;

public class RIRParameter implements EParameter{

	private final static int parameterNumber = 2;	
	private final static VariableType type = VariableType.RANDOM_INTEGER_PARAMETER;
	
	String name;
	Integer fromValue;
	String fromTitle;
	Integer toValue;
	String toTitle;
	
	public RIRParameter(){
		this.name = "";
		this.fromValue = 0;
		this.toValue = 100;
		
		common();
	}
	
	public RIRParameter( String name, int from, int to ){
		this.name = name;
		this.fromValue = from;
		this.toValue = to;
		
		common();
	}
	
	private void common(){
		this.fromTitle = CommonOperations.getTranslation("editor.title.variabletype.randominteger.from");
		this.toTitle = CommonOperations.getTranslation("editor.title.variabletype.randominteger.to");		
	}
	
	@Override
	public String getName() {		
		return name;
	}

	@Override
	public String getValue() {
		return CommonOperations.getRandomStringIntegerRange(fromValue, toValue);
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
			attr.setValue( fromTitle );
			elementElement.setAttributeNode(attr);	
		
			//Value
			attr = document.createAttribute( ATTR_VALUE );
			attr.setValue( fromValue.toString() );
			elementElement.setAttributeNode(attr);	

			return elementElement;
			
		}else if( index == 1 ){

			//Name
			attr = document.createAttribute( ATTR_NAME );
			attr.setValue( toTitle );
			elementElement.setAttributeNode(attr);	
		
			//Value
			attr = document.createAttribute( ATTR_VALUE );
			attr.setValue( toValue.toString() );
			elementElement.setAttributeNode(attr);	

			return elementElement;
			
		}else{
		
			return null;
			
		}
	}
	
	@Override
	public String getParameterName(int index) {
		if( index == 0 ){

			return fromTitle;
			
		}else if( index == 1 ){

			return toTitle;
			
		}
		return null;
	}

	@Override
	public Object getParameterValue(int index) {
		if( index == 0 ){

			return fromValue;
			
		}else if( index == 1 ){

			return toValue;
			
		}
		return null;
	}

	@Override
	public Class<?> getParameterClass(int index) {
		if( index == 0 ){

			return fromValue.getClass();
			
		}else if( index == 1 ){

			return toValue.getClass();
			
		}
		return null;
	}

	@Override
	public void setParameterValue(Object value, int index) {
		if( index == 0 ){

			fromValue = (Integer)value;
			
		}else if( index == 1 ){

			toValue = (Integer)value;
			
		}
		
	}

}
