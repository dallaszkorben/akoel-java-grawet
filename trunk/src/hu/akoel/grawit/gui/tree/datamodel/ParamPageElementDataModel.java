package hu.akoel.grawit.gui.tree.datamodel;

import javax.swing.tree.MutableTreeNode;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.elements.ParamElement;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ParamPageElementDataModel  extends ParamPageDataModelInterface{

	private static final long serialVersionUID = -8916078747948054716L;
	private ParamElement paramElement;

	public ParamPageElementDataModel( ParamElement paramElement ){
		super();
		this.paramElement = paramElement;
	}

	@Override
	public void add( ParamPageDataModelInterface node ) {
		super.add( (MutableTreeNode)node );
	}
	
	public String getNameToString(){
		return paramElement.getName();
	}
	
	public String getTypeToString(){
		return CommonOperations.getTranslation( "tree.nodetype.elementbase");
	}
	
	public ParamElement getParamElement(){
		return paramElement;
	}

	@Override
	public Element getXMLElement(Document document) {
		Attr attr;
	
		//Node element
		Element elementElement = document.createElement("element");
		attr = document.createAttribute("name");
		attr.setValue( paramElement.getName() );
		elementElement.setAttributeNode(attr);	
/*		
		attr = document.createAttribute("frame");
		attr.setValue( paramElement.getFrame() );
		elementElement.setAttributeNode(attr);	

		attr = document.createAttribute("variablesemple");
		attr.setValue( paramElement.getVariableSample().name() );
		elementElement.setAttributeNode(attr);
		
		attr = document.createAttribute("identifier");
		attr.setValue( paramElement.getIdentifier() );
		elementElement.setAttributeNode(attr);	

		attr = document.createAttribute("identificationType");
		attr.setValue( paramElement.getIdentificationType().name() );
		elementElement.setAttributeNode(attr);	
*/
		return elementElement;	
	}
}
