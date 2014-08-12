package hu.akoel.grawit.gui.tree.datamodel;

import hu.akoel.grawit.elements.ElementBase;

import javax.swing.tree.DefaultMutableTreeNode;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class PageBaseElementDataModel  extends DefaultMutableTreeNode implements DataModelInterface{

	private static final long serialVersionUID = -8916078747948054716L;
	private ElementBase elementBase;

	public PageBaseElementDataModel( ElementBase elementBase ){
		super();
		this.elementBase = elementBase;
	}
	
	public String toString(){
		return elementBase.getName();
	}
	
	public ElementBase getElementBase(){
		return elementBase;
	}

	@Override
	public Element getXMLElement(Document document) {
		Attr attr;
//System.err.println("   Elem");		
		//Node element
		Element elementElement = document.createElement("element");
		attr = document.createAttribute("name");
		attr.setValue( elementBase.getName() );
		elementElement.setAttributeNode(attr);	
		
		attr = document.createAttribute("frame");
		attr.setValue( elementBase.getFrame() );
		elementElement.setAttributeNode(attr);	

		attr = document.createAttribute("variablesemple");
		attr.setValue( elementBase.getVariableSample().name() );
		elementElement.setAttributeNode(attr);
		
		attr = document.createAttribute("identifier");
		attr.setValue( elementBase.getIdentifier() );
		elementElement.setAttributeNode(attr);	

		attr = document.createAttribute("identificationType");
		attr.setValue( elementBase.getIdentificationType().name() );
		elementElement.setAttributeNode(attr);	

		return elementElement;	
	}
}
