package hu.akoel.grawit.gui.editor.component;

import hu.akoel.grawit.enums.Tag;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


public interface VariableParametersComponentInterface extends EditorComponentInterface{

	public static final Tag TAG = Tag.VARIABLEPARAMETER; 
			
	public void getXMLElement(Document document, Element elementNode );
	
	public Tag getTag();

}
