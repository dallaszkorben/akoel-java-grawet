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
	public abstract String getTypeToShow();
	
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
	
	/**
	 * Az aktualis Adatmodellt keresi fel a root-tol kezdve egyenes uton
	 * es keszit xml formaju tag-eket az ut egyes csomopontjait hasznalva
	 * Magat a root-ot kihagyja
	 * 
	 * @return
	 */
	public final String getPathTag() {		
		StringBuffer out = new StringBuffer();
		TreeNode[] nodeArray = this.getPath();
		for( TreeNode node: nodeArray ){
			
			DataModelInterface dataModel = (DataModelInterface)node;

			if( !dataModel.isRoot() ){
				out.append( dataModel.getOpenTag() );
			}			
		}
		
		for( int i = nodeArray.length - 1; i >= 0; i-- ){
			DataModelInterface dataModel = ((DataModelInterface)nodeArray[i]);
			if( !dataModel.isRoot() ){
				out.append( dataModel.getCloseTag() );
			}			
		}
		return out.toString();
	}
	
	public final String getOpenTag(){
		return "<" + this.getTagName() + " " + this.getIDName() + "='" + this.getIDValue() + "'>";
	}
	
	public final String getCloseTag(){
		return "</" + this.getTagName() + ">";
	}
}
