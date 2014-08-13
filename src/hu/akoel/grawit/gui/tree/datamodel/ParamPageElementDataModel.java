package hu.akoel.grawit.gui.tree.datamodel;

import javax.swing.tree.MutableTreeNode;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.elements.ElementBase;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ParamPageElementDataModel  extends ParamPageDataModelInterface{

	private static final long serialVersionUID = -8916078747948054716L;
	private ElementBase elementBase;

	public ParamPageElementDataModel( ElementBase elementBase ){
		super();
		this.elementBase = elementBase;
	}

	@Override
	public void add( ParamPageDataModelInterface node ) {
		super.add( (MutableTreeNode)node );
	}
	
	public String getNameToString(){
		return elementBase.getName();
	}
	
	public String getTypeToString(){
		return CommonOperations.getTranslation( "tree.nodetype.elementbase");
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
