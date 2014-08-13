package hu.akoel.grawit.gui.tree.datamodel;

import hu.akoel.grawit.exceptions.ParameterError;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class ParamPageDataModelInterface extends DefaultMutableTreeNode{
	
	private static final long serialVersionUID = 3732681623705157215L;

	public abstract String getTypeToString();
	
	public abstract String getNameToString();
	
	public abstract void add( ParamPageDataModelInterface node );
	
	public void add( MutableTreeNode node ){
		throw new ParameterError( 1, "Instead of '" + MutableTreeNode.class.getSimpleName() + "' it should have been: " + ParamPageDataModelInterface.class.getSimpleName() );
	}
	
	public abstract Element getXMLElement( Document document );
}

