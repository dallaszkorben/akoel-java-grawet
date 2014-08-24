package hu.akoel.grawit.core.parameter;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.enums.VariableType;

public interface ElementParameter {
	static Tag TAG = Tag.VARIABLEPARAMETER;
	
	static String ATTR_NAME = "name";
	static String ATTR_VALUE = "value";

	public String getName();
	public void setName( String name );
	
	public String getValue();
	
	public VariableType getType();
	public int getParameterNumber();
	public Element getXMLElement(Document document, int index);
}
