package hu.akoel.grawit.core.datamodel;

import javax.swing.tree.DefaultMutableTreeNode;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class DataModelInterface extends DefaultMutableTreeNode{

	private static final long serialVersionUID = 3951879392831974974L;

	public abstract String getTypeToString();
	
	public abstract String getNameToString();
	
	public abstract Element getXMLElement( Document document );
}
