package hu.akoel.grawit.core.datamodel.elements;

import javax.swing.tree.MutableTreeNode;

import org.openqa.selenium.WebDriver;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.datamodel.ParamDataModelInterface;
import hu.akoel.grawit.core.operations.ElementOperationInterface;
import hu.akoel.grawit.exceptions.ElementException;

public class ParamElementDataModel extends ParamDataModelInterface{
	private static final long serialVersionUID = -8916078747948054716L;

	private String name;
	private ElementOperationInterface elementOperation;
	private BaseElementDataModel baseElement;	
	private String variableValue = "";
	

	public ParamElementDataModel( String name, BaseElementDataModel baseElement, ElementOperationInterface operation){
		this.name = name;
		this.baseElement = baseElement;
		this.elementOperation = operation;
	}

	/**
	 * 
	 * Executes the defined Operation with the defined Parameter
	 * @throws ElementException 
	 * 
	 */
	public void doAction( WebDriver driver ) throws ElementException{
		this.getElementOperation().doAction( driver, this );
	}
	
	public void setName( String name ){
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
	
	public ElementOperationInterface getElementOperation() {
		return elementOperation;
	}

	public void setOperation(ElementOperationInterface elementOperation) {
		this.elementOperation = elementOperation;
	}	
	
	public String getVariableValue() {
		return variableValue;
	}

	public void setVariableValue(String variableValue) {
		this.variableValue = variableValue;
	}

	public void setBaseElement( BaseElementDataModel baseElement ){
		this.baseElement = baseElement;
	}
	
	public BaseElementDataModel getBaseElement(){
		return baseElement;
	}
	
	@Override
	public void add( ParamDataModelInterface node ) {
		super.add( (MutableTreeNode)node );
	}
	
	public String getNameToString(){
		return getName();
	}
	
	public String getTypeToString(){
		return CommonOperations.getTranslation( "tree.nodetype.paramelement");
	}
	
	@Override
	public Element getXMLElement(Document document) {
		Attr attr;
	
		//Node element
		Element elementElement = document.createElement("element");
		attr = document.createAttribute("name");
		attr.setValue( getName() );
		elementElement.setAttributeNode(attr);	

		return elementElement;	
	}
	
	/**
	 * 
	 * Akkor egyenlo a ket objektum, ha azonosak, vagy azonos az baseElement tagjuk
	 * 
	 */
/*	public boolean equals( Object obj ){
		if( null == obj ){
			return false;
		}
		if( !( obj instanceof ParamElementDataModel )){
			return false;
		}
		if( obj == this ){
			return true;
		}
//TODO ezt meg at kell gondolni
System.err.println("itt van az osszehasonlitas a ParamElement-re");
		ParamElementDataModel eo = (ParamElementDataModel)obj;
		if( eo.getBaseElement() != this.getBaseElement() ){
			return false;
		}
		return true;
	}
*/	
	/**
	 * 
	 * Azonos Hash erteket kap minden objektum melynek azonos az elemet tagja
	 * 
	 */
/*	public int hashCode() {
        return new HashCodeBuilder(17, 31).
            append(baseElement).
            toHashCode();
    }
 */
	
}
