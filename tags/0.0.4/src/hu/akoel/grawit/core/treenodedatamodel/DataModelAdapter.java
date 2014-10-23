package hu.akoel.grawit.core.treenodedatamodel;

import hu.akoel.grawit.enums.Tag;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class DataModelAdapter extends DefaultMutableTreeNode{
		
	private static final long serialVersionUID = 3951879392831974974L;
		
	public static final String ATTR_NAME = "name";

	private Boolean isOn = true;
	private Boolean enabledToTurnOnOff = false;
	
	/**
	 * Megmondja, hogy a Node Ki/Be van kapcsolva
	 * 
	 * @return
	 */
	public Boolean isOn(){
		return isOn;
	}
	
	/**
	 * Ki/be kapcsolja a Node-ot
	 * 
	 * @param on
	 */
	public void setOn( boolean on ){
		this.isOn = on;
	}
	
	/**
	 * Megmondja, hogy engedelyezett-e a Node Ki/Be kapcsolasa
	 * 
	 * @return
	 */
	public Boolean isEnabledToTurnOnOff(){
		return enabledToTurnOnOff;
	}
	
	/**
	 * A Node ki/be kapcsolhatosagat szabalyozza
	 * 
	 * @param enabled
	 */
	public void setEnabledToTurnOnOff( boolean enabled ){
		this.enabledToTurnOnOff = enabled;
	}
	
	/**
	 * Az adatmodel tipusanak olvashato megnevezese
	 * 
	 * @return
	 */
	public abstract String getNodeTypeToShow();
	
	/**
	 * Az adatmodel tag-je (basepage, parampage, page, node, element)
	 * @return
	 */
	public abstract Tag getTag();
	
	/**
	 * Az adatmodel tag-jenek azonosito attributum neve (name)
	 * @return
	 */
//	public abstract String getIDName();

	/**
	 * Az adatmodel "name" nevu attributumanak erteke
	 * @return
	 */
	public abstract String getName();
	
	
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
			
			DataModelAdapter dataModel = (DataModelAdapter)node;

			if( !dataModel.isRoot() ){
				out.append( dataModel.getOpenTag() );
			}			
		}
		
		for( int i = nodeArray.length - 1; i >= 0; i-- ){
			DataModelAdapter dataModel = ((DataModelAdapter)nodeArray[i]);
			if( !dataModel.isRoot() ){
				out.append( dataModel.getCloseTag() );
			}			
		}
		return out.toString();
	}
	
	public final String getOpenTag(){
		return "<" + this.getTag().getName() + " " + ATTR_NAME + "='" + this.getName() + "'>";
	}
	
	public final String getCloseTag(){
		return "</" + this.getTag().getName() + ">";
	}
}
