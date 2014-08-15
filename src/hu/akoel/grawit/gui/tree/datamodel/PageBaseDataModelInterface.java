package hu.akoel.grawit.gui.tree.datamodel;

import javax.swing.tree.DefaultMutableTreeNode;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class PageBaseDataModelInterface extends DefaultMutableTreeNode{

	private static final long serialVersionUID = 1210521016363737236L;

	public abstract String getTypeToString();
	
	public abstract String getNameToString();
	
	public abstract String getPathToString();
	
	public abstract void add( PageBaseDataModelInterface node );
	
/*	public void add( MutableTreeNode node ){
		throw new ParameterError( 1, "Instead of '" + MutableTreeNode.class.getSimpleName() + "' it should have been: " + ParamPageDataModelInterface.class.getSimpleName() );
	}
*/	
	public abstract Element getXMLElement( Document document );
}
