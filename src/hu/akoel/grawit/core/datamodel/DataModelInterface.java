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
	
	/**
	 * Az adatmodel tag-jenek neve (basepage, parampage, page, node, element)
	 * @return
	 */
	public abstract String getTagName();
	
	/**
	 * Az adatmodel tag-jenek azonosito attributum neve (name)
	 * @return
	 */
	public abstract String getIDName();

	/**
	 * Az adatmodel tag-jenek azonosito attributumanak erteke
	 * @return
	 */
	public abstract String getIDValue();
	
	/**
	 * Visszaadja az adatmodel xml strukturajat (rekurzivan)
	 * 
	 * @param document
	 * @return
	 */
	public abstract Element getXMLElement( Document document );
	
	public final String getTaggedPathToString() {		
		StringBuffer out = new StringBuffer();
		for( TreeNode node: this.getPath() ){
			
			DataModelInterface dataModel = (DataModelInterface)node;

			if( !dataModel.isRoot() ){
				out.append( dataModel.getTaggedElementToString() );
			}
			
		}		
		return out.toString();
	}
	
	public final String getTaggedElementToString(){
		return "<" + this.getTagName() + " " + this.getIDName() + "=" + this.getIDValue() + ">";
	}
	
}
