package hu.akoel.grawet.views;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

public class PurePageTree extends JTree implements TreeInterface{

	private static final long serialVersionUID = -254758216814274164L;
	private static DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");

	public PurePageTree(){
		super( root );
	}
	
	public void addNode( PurePageNode purePageNode ){
		DefaultMutableTreeNode book = new DefaultMutableTreeNode( purePageNode );
		root.add( book );
	}
}
