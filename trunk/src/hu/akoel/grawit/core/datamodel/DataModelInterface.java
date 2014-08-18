package hu.akoel.grawit.core.datamodel;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class DataModelInterface extends DefaultMutableTreeNode{
	
	private static final long serialVersionUID = 3951879392831974974L;

	/**
	 * Az adatmodel tipusanak olvashato megnevezese
	 * 
	 * @return
	 */
	public abstract String getTypeToString();
	
	public abstract String getNameToString();
	
	public abstract Element getXMLElement( Document document );
	
	public final String getPathToString() {		
		StringBuffer out = new StringBuffer();
		for( TreeNode node: this.getPath() ){
			
			
			out.append( ((DataModelInterface)node).getNameToString() );
			out.append("/");
		}		
		return out.toString();
	}
	
}
