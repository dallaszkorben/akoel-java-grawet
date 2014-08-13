package hu.akoel.grawit.gui.tree.datamodel;

import javax.swing.tree.DefaultMutableTreeNode;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class DataModelInterface extends DefaultMutableTreeNode{
	
	private static final long serialVersionUID = 3732681623705157215L;

	public abstract String getTypeToString();
	
	public abstract String getNameToString();
	
	public abstract Element getXMLElement( Document document );
}
