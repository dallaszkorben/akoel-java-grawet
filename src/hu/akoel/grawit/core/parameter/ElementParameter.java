package hu.akoel.grawit.core.parameter;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.enums.VariableType;

public interface ElementParameter {
	
	static Tag TAG = Tag.VARIABLEPARAMETER;
	
	static String ATTR_NAME = "name";
	static String ATTR_VALUE = "value";

	/**
	 * A valtozo azonositoja
	 * 
	 * @return
	 */
	public String getName();
	
	/**
	 * A valtozo erteke
	 * 
	 * @return
	 */
	public String getValue();

	/**
	 * Az azonosito megadasa
	 * 
	 * @param name
	 */
	public void setName( String name );
	
	
	public VariableType getType();
	
	public int getParameterNumber();
	public String getParameterName( int index );
	public Object getParameterValue( int index );
	public void setParameterValue( Object value, int index );
	public Class<?> getParameterClass( int index );
	public Element getXMLElement(Document document, int index);
	
}
