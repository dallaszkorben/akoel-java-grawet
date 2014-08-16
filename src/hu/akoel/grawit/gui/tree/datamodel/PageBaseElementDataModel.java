package hu.akoel.grawit.gui.tree.datamodel;

import javax.swing.tree.MutableTreeNode;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.elements.BaseElement;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class PageBaseElementDataModel  extends PageBaseDataModelInterface{

	private static final long serialVersionUID = -8916078747948054716L;
	private BaseElement baseElement;

	public PageBaseElementDataModel( BaseElement baseElement ){
		super();
		this.baseElement = baseElement;
	}

	@Override
	public void add(PageBaseDataModelInterface node) {
		super.add( (MutableTreeNode)node );
	}
	
	public String getNameToString(){
		return baseElement.getName();
	}
	
	public String getTypeToString(){
		return CommonOperations.getTranslation( "tree.nodetype.elementbase");
	}
	
	@Override
	public String getPathToString() {		
		return this.getPath().toString();
	}	
	
	public BaseElement getElementBase(){
		return baseElement;
	}

	@Override
	public Element getXMLElement(Document document) {
		Attr attr;
//System.err.println("   Elem");		
		//Node element
		Element elementElement = document.createElement("element");
		attr = document.createAttribute("name");
		attr.setValue( baseElement.getName() );
		elementElement.setAttributeNode(attr);	
		
		attr = document.createAttribute("frame");
		attr.setValue( baseElement.getFrame() );
		elementElement.setAttributeNode(attr);	

		attr = document.createAttribute("variablesemple");
		attr.setValue( baseElement.getVariableSample().name() );
		elementElement.setAttributeNode(attr);
		
		attr = document.createAttribute("identifier");
		attr.setValue( baseElement.getIdentifier() );
		elementElement.setAttributeNode(attr);	

		attr = document.createAttribute("identificationType");
		attr.setValue( baseElement.getIdentificationType().name() );
		elementElement.setAttributeNode(attr);	

		return elementElement;	
	}
}
